package repository;

import model.Tester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryTester extends  JpaRepository<Tester, Long>{

    @Query("SELECT t FROM Tester t WHERE t.email = ?1")
    Tester loginTester(String email);
}
