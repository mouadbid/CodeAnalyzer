package intelcia.qa.Back_end.Repository;

import intelcia.qa.Back_end.model.CodeAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeAnalysisRepository extends JpaRepository<CodeAnalysisEntity, Long> {
}
