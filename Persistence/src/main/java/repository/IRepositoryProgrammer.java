package repository;

import model.Programmer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryProgrammer extends  JpaRepository<Programmer, Long>{

    @Query("SELECT p FROM Programmer p WHERE p.email = ?1")
    Programmer loginProgrammer(String email);
}
