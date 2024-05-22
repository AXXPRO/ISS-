package repository;

import model.SysAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IRepositorySysAdmin extends  JpaRepository<SysAdmin, Long>{
        @Query("SELECT s FROM SysAdmin s WHERE s.email = ?1")
        SysAdmin loginSysAdmin(String email);
}
