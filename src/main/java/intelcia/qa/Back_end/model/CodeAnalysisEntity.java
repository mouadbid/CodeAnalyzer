package intelcia.qa.Back_end.model;

import jakarta.persistence.*;

@Entity
public class CodeAnalysisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private String originalCode;

    @Lob
    private String javaparserAnalysis;

    @Lob
    private String aiAnalysis;

    @Lob
    private String aiCorrection;

    private String dateAnalyse;

    // Getters et setters


    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJavaparserAnalysis() {
        return javaparserAnalysis;
    }

    public void setJavaparserAnalysis(String javaparserAnalysis) {
        this.javaparserAnalysis = javaparserAnalysis;
    }

    public String getAiCorrection() {
        return aiCorrection;
    }

    public void setAiCorrection(String aiCorrection) {
        this.aiCorrection = aiCorrection;
    }

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    public String getDateAnalyse() {
        return dateAnalyse;
    }

    public void setDateAnalyse(String dateAnalyse) {
        this.dateAnalyse = dateAnalyse;
    }
}
