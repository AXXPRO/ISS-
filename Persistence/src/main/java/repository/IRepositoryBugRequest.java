package repository;

import model.BugRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IRepositoryBugRequest extends JpaRepository<BugRequest, Long> {

    @Query("SELECT b FROM BugRequest b WHERE b.status = 'UNRESOLVED'")
    public List<BugRequest> getUnresolvedBugRequests();
}
