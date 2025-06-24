package intelcia.qa.Back_end.controller;


import intelcia.qa.Back_end.dto.CorrectionDTO;
import intelcia.qa.Back_end.model.CodeAnalysisEntity;
import intelcia.qa.Back_end.model.CorrectorModel;
import intelcia.qa.Back_end.service.CorrectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import intelcia.qa.Back_end.Repository.CodeAnalysisRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/corrector")
public class CorrectorController {

    @Autowired
    private CorrectionService correctionService;
    @Autowired
    private CodeAnalysisRepository codeAnalysisRepository;

    @PostMapping("/save")
    public CodeAnalysisEntity save(@RequestBody CodeAnalysisEntity input) {
        return correctionService.save(
                input.getFilename(),
                input.getOriginalCode(),
                input.getJavaparserAnalysis(),
                input.getAiAnalysis(),
                input.getAiCorrection()
        );
    }

    @PostMapping("/file")
    public CorrectorModel Codecorrector(@RequestParam("file") MultipartFile file ,@RequestParam("problems") String Problems) throws Exception{
        return correctionService.Codecorrector(file,Problems);
    }

    @GetMapping("/all")
    public List<CodeAnalysisEntity> getAllAnalyses() {
        return codeAnalysisRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorrectionDTO> getCorrectionById(@PathVariable Long id) {
        return codeAnalysisRepository.findById(id)
                .map(entity -> ResponseEntity.ok(new CorrectionDTO(entity)))
                .orElse(ResponseEntity.notFound().build());
    }



}
