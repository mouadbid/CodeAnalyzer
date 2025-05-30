package intelcia.qa.Back_end.controller;


import intelcia.qa.Back_end.model.CorrectorModel;
import intelcia.qa.Back_end.service.CorrectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/corrector")
public class CorrectorController {

    @Autowired
    private CorrectionService correctionService;

    @PostMapping
    public CorrectorModel Codecorrector(@RequestParam("file") MultipartFile file ,@RequestParam("problems") String Problems) throws Exception{
        return correctionService.Codecorrector(file,Problems);
    }
}
