package net.generica.bom_expansion.repository;

import net.generica.bom_expansion.domain.Bom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BomRepository extends CrudRepository<Bom, Long> {

    List<Bom> listBomByParentCode(String parentCode);

}
