package repository;

import model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryReport extends JpaRepository<Report, Long>{
}
