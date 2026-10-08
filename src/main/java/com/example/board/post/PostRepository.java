package com.example.board.post;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByOrderByIdDesc();

	@Query("""
			select p from Post p
			where (:keyword is null or p.title like concat('%', :keyword, '%'))
			  and (:start is null or p.createdAt >= :start)
			  and (:end is null or p.createdAt <= :end)
			order by p.id desc
			""")
	List<Post> search(@Param("keyword") String keyword, @Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end);

}
