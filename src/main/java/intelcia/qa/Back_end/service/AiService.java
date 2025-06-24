package intelcia.qa.Back_end.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import intelcia.qa.Back_end.Repository.CodeAnalysisRepository;
import intelcia.qa.Back_end.model.Aimodel;
import intelcia.qa.Back_end.model.CodeAnalysisEntity;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
public class AiService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public AiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
        this.objectMapper = new ObjectMapper();
    }

    public String fileToText(MultipartFile file) throws Exception {
        return new String(file.getBytes(), StandardCharsets.UTF_8);

    }

    public Aimodel codeAnalyzer(MultipartFile file) throws Exception {
        String text = fileToText(file);
        System.out.println(text);

        String prompt = "Analyze the following Java/angular code.\n\n" +
                "Classify all detected issues into exactly one of the following types:\n" +
                "- Vulnerability\n" +
                "- Bug\n" +
                "- Security Hotspot\n" +
                "- Code Smell\n\n" +
                "Return ONLY a pure JSON object. NO extra explanation, no introduction, no notes.\n\n" +
                "Each category must be a list of plain text strings, NOT objects.\n\n" +
                "Format the result exactly like this:\n" +
                "{\n" +
                "  \"Vulnerability\": [],\n" +
                "  \"Bug\": [\"Short description in MethodName() at line 10\", ...],\n" +
                "  \"Security Hotspot\": [\"Short description in MethodName() at line 4\", ...],\n" +
                "  \"Code Smell\": [\"Short description in MethodName() at line 0\", ...]\n" +
                "}\n\n" +
                "- Each string must include:\n" +
                "  • a short explanation of the problem,\n" +
                "  • the method name in the format MethodName(),\n" +
                "  • and the line number (e.g., 'at line 5') all in the same string.\n" +
                "- Use empty arrays ([]) if no issues are found.\n" +
                "- If the code is not valid Java/angular, respond only with: \"this is not java/angular code\"\n" +
                "- **IMPORTANT:** Do NOT use any code concatenation (like '+ args[0] +'or \" or anythig like that) inside the strings because answer will be inside arraylist, return the full code snippet as a simple text.\n\n" +
                "Java/angular code to analyze:\n" + text;



        // Analyse des bugs
        String responseRaw = chatClient
                .prompt()
                .system("You are an english AI assistant trained to find errors in Java/angular code only and provide structured reports.")
                .user(prompt)
                .call()
                .content()
                .replaceAll("(?s)<think>.*?</think>", "")
                .replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();

        responseRaw = responseRaw.replaceAll(": None", ": null");
        responseRaw = responseRaw.replaceAll("\\+" , "add");



        System.out.println("[AI RAW RESPONSE] ====> \n" + responseRaw);

        Aimodel resultModel = new Aimodel();

        try {
            if (responseRaw.trim().equalsIgnoreCase("this is not java/angular code")) {
                resultModel.setBugs(Map.of("Error", List.of("This is not Java/angular code")));
                return resultModel;
            }
            responseRaw = responseRaw.replaceAll(",(\\s*])", "$1");
            responseRaw = responseRaw.replaceAll("\\+\\s*args\\[0\\]\\s*\\+", "' + args[0] + '");
            // Essayez de parser normalement
            Map<String, List<String>> bugMap = objectMapper.readValue(responseRaw, new TypeReference<>() {});
            resultModel.setBugs(bugMap);

        } catch (Exception e) {
            System.err.println("[ERROR PARSING RESPONSE] " + e.getMessage());
            System.err.println("[AI RAW RESPONSE] ====> \n" + responseRaw);
            resultModel.setBugs(Map.of("Error", List.of("Failed to parse AI response.")));
        }


        return resultModel;
    }
}
