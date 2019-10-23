package net.generica.bom_expansion.service;

import net.generica.bom_expansion.domain.Bom;

import java.util.List;

public interface BomService {

    List<Bom> BomExplosionByLevel(String parentcode);

}
