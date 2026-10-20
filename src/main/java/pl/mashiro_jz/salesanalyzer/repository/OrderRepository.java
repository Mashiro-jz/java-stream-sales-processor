package pl.mashiro_jz.salesanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mashiro_jz.salesanalyzer.entity.OrderEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByDate(LocalDate date);

    List<OrderEntity> findByIsCompletedTrue();

    List<OrderEntity> findByCategory(String category);
}