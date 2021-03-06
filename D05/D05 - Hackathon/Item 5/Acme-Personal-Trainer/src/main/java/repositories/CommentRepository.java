
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.customer.id = ?1")
	Collection<Comment> findCommentByCustomer(int id);

	@Query("select distinct c from Comment c join c.article a where a.nutritionist.id=?1")
	Collection<Comment> findCommentByArticlesByNutritionist(int nutritionistId);

	@Query("select distinct c from Comment c join c.article a where a.id=?1")
	Collection<Comment> findCommentsByArticle(int articleId);

}
