package intelcia.qa.Back_end.controller;
import intelcia.qa.Back_end.model.Aimodel;
import intelcia.qa.Back_end.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:5173") //interface graphique
@RestController
@RequestMapping("/ai")
public class AiController {
    @Autowired
    private AiService aiService ;

    @PostMapping
    public Aimodel analyzeCode(@RequestParam("file") MultipartFile file) throws Exception {
        return aiService.codeAnalyzer(file);
    }


}
