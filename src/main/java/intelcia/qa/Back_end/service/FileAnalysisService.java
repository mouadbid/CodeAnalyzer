package intelcia.qa.Back_end.service;

import intelcia.qa.Back_end.Core.*;
import intelcia.qa.Back_end.model.AnalysisResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileAnalysisService {
    public AnalysisResult analyze(MultipartFile multipartFile) throws Exception {
        // Enregistrer temporairement le fichier
        File tempFile = File.createTempFile("upload-", ".java");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }

        String filePath = tempFile.getAbsolutePath();
        ShowAllMethods extractor = new ShowAllMethods(filePath);
        NotImportantVar notImportant = new NotImportantVar();
        IdenticMethode identic = new IdenticMethode(filePath);
        LeakResource leak = new LeakResource(filePath);
        SecurityHotspot sec = new SecurityHotspot(filePath);

        // Créer le résultat
        AnalysisResult result = new AnalysisResult();
        result.setMethods(extractor.Allmethods());
        result.setVariables(extractor.Allvariables());
        result.setExpressions(extractor.extractNameExpressions());
        result.setNotImportantVariables(
                notImportant.VariablesNotImportanat(extractor.Allvariables(), extractor.extractNameExpressions()));
        result.setIdenticMethods(identic.identicMethods(filePath));
        result.setLeakResources(leak.ObjectCreation());
        result.setSecurityProblems(sec.securityproblems());

        // Supprimer le fichier temporaire
        tempFile.delete();

        return result;
    }
}
