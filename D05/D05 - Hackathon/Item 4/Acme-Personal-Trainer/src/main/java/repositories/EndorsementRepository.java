
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select e from Endorsement e where e.trainer.id = ?1 and e.trainerToCustomer = false")
	Collection<Endorsement> findReceivedEndorsementsByTrainer(int trainerId);

	@Query("select e from Endorsement e where e.trainer.id = ?1 and e.trainerToCustomer = true")
	Collection<Endorsement> findSendEndorsementsByTrainer(int trainerId);

	@Query("select e from Endorsement e where e.customer.id = ?1 and e.trainerToCustomer = false")
	Collection<Endorsement> findSendEndorsementsByCustomer(int customerId);

	@Query("select e from Endorsement e where e.customer.id = ?1 and e.trainerToCustomer = true")
	Collection<Endorsement> findReceivedEndorsementsByCustomer(int customerId);

	// Requirement 37.5.1
	@Query("select min(1.0 * (select count(e) from Endorsement e where e.trainer.id = t.id)), max(1.0 * (select count(e) from Endorsement e where e.trainer.id = t.id)), avg(1.0 * (select count(e) from Endorsement e where e.trainer.id = t.id)), stddev(1.0 * (select count(e) from Endorsement e where e.trainer.id = t.id)) from Trainer t)")
	Double[] findDataNumberEndorsementPerTrainer();

	@Query("select e from Endorsement e where e.trainer.id = ?1")
	Collection<Endorsement> findSentReceivedEndorsementsByTrainer(int trainerId);

	@Query("select e from Endorsement e where e.customer.id = ?1")
	Collection<Endorsement> findSentReceivedEndorsementsByCustomer(int customerId);

	@Query("select avg(e.mark) from Endorsement e where e.trainer.id = ?1 and e.trainerToCustomer = false")
	Double avgMarkByTrainer(int trainerId);

	@Query("select avg(e.mark) from Endorsement e where e.customer.id = ?1 and e.trainerToCustomer = true")
	Double avgMarkByCustomer(int customerId);

}
