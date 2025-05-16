package intelcia.qa.Back_end.controller;

import intelcia.qa.Back_end.model.AnalysisResult;
import intelcia.qa.Back_end.service.FileAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "http://localhost:5173") //interface graphique
@RestController //demontrer que ce fichier est le controlleur
@RequestMapping("/analyze")//url(cas d'une autre contreleur
public class FileAnalysisController {

    @Autowired
    //creer le qaund je le besoin
    //de ce exemple je besoin objet de type FileAnalysisiService
    private FileAnalysisService fileAnalysisService;

    @PostMapping
    public AnalysisResult analyzeFile(@RequestParam("file") MultipartFile file) throws Exception {
        return fileAnalysisService.analyze(file);
    }
}
