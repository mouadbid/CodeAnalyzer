package intelcia.qa.Back_end.dto;

import intelcia.qa.Back_end.model.CodeAnalysisEntity;

public class CorrectionDTO {
    private String filename;
    private String originalCode;
    private String aiCorrection;
    private String dateAnalyse;

    public CorrectionDTO(CodeAnalysisEntity entity) {
        this.filename = entity.getFilename();
        this.originalCode = entity.getOriginalCode();
        this.aiCorrection = entity.getAiCorrection();
        this.dateAnalyse = entity.getDateAnalyse();
    }

    // Getters
    public String getFilename() { return filename; }
    public String getOriginalCode() { return originalCode; }
    public String getAiCorrection() { return aiCorrection; }
    public String getDateAnalyse() { return dateAnalyse; }
}
