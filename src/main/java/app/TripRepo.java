package app;

import app.NSObjects.TripExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepo extends JpaRepository<TripExp, Integer> {

    // custom query to search to blog post by title or content

}


