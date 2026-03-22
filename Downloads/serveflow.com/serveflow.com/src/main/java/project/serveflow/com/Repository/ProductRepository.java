package project.serveflow.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.serveflow.com.Model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>{
    List<Product> findAllByActiveTrue();
}
