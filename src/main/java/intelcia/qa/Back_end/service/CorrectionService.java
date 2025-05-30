package intelcia.qa.Back_end.service;

import intelcia.qa.Back_end.model.CorrectorModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;



@Service
public class CorrectionService {
    private final ChatClient corrector;

    public CorrectionService(ChatClient.Builder builder) {
        this.corrector = builder.build();
    }

    public String fileToText(MultipartFile file) throws Exception {
        return new String(file.getBytes(), StandardCharsets.UTF_8);

    }

    public CorrectorModel Codecorrector(MultipartFile file , String problems) throws Exception {
        String code = fileToText(file);
        System.out.println("problemes : " + problems);

        String prompt = "Analyze the following Java code:\n"
                + code
                + "\n\nBased on the following analysis or potential problems:\n"
                + problems
                + "\n\nIf there are problems, return only the corrected Java code."
                + "\nIf any notes are needed, include them as inline comments using '// your note here'."
                + "\nIf there are no problems, respond only with exactly: there is no problem."
                + "\nDo not include any explanation, introduction,no notes , or additional text."
                + "\nReturn only pure, valid Java code if corrections are needed."
                + "\nplease pure java code no anything except java code";


        String responseRaw = corrector
                .prompt()
                .system("You are an english AI assistant trained to find errors in Java code only and provide structured reports.")
                .user(prompt)
                .call()
                .content()
                .replaceAll("(?s)<think>.*?</think>", "")
                .replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();
        System.out.println("[AI RAW RESPONSE] ====> \n" + responseRaw);

        CorrectorModel correction = new CorrectorModel();
        correction.setCorrectedCode(responseRaw);

        return correction ;
    }
}
