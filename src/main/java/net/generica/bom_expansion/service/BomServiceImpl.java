package net.generica.bom_expansion.service;

import net.generica.bom_expansion.domain.Bom;
import net.generica.bom_expansion.repository.BomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BomServiceImpl implements BomService {

    private final BomRepository bomRepository;

    public BomServiceImpl(BomRepository bomRepository) {
        this.bomRepository = bomRepository;
    }


    // BOM Expansion Function in Service
    // Depending on the material number, BOM is developed layer by layer and Service is implemented.
    @Transactional(readOnly = true)
    public List<Bom> BomExplosionByLevel(String parentCode) {
        //listBom stores the results of expanding BOM layer by layer, and gets the first layer initially.
        List<Bom> listBom = bomRepository.listBomByParentCode(parentCode);     //Using the query function in Repository

        // Level assignment at the first level
        Integer level = 1;
        for (int i = 0; i < listBom.size(); i++) {
            Bom bom = listBom.get(i);
            bom.setLevel(level);
        }

        // listBomt01 and listBomt02 alternately expand the temporary two list objects of BOM layer by layer and assign initial values.
        List<Bom> listBomt01 = bomRepository.listBomByParentCode(parentCode); //Using the query function in Repository
        List<Bom> listBomt02 = new ArrayList<Bom>();

        // Begin to expand BOM layer by layer alternately, each BOM node to be expanded is put into the result listBom,
        // while the temporary object listBomt01 or listBomt02 is placed, waiting for the continuation of the loop
        // to expand down, and the temporary object is initialized before it is put in.
        while (listBomt01.size() > 0) {

            level = level + 1;
            listBomt02.clear();
            // When the current layer is listBomt01, the results are output to listBom and listBomt02
            for (int i = 0; i < listBomt01.size(); i++) {
                Bom bom = listBomt01.get(i);
                String subcode = bom.getSubItemCode();
                Integer itemQty = bom.getQty();
                List<Bom> listBomt01sub = bomRepository.listBomByParentCode(subcode);

                for (int i1 = 0; i1 < listBomt01sub.size(); i1++) {
                    Bom bom1 = listBomt01sub.get(i1);
                    bom1.setLevel(level);
                    Integer qty = bom1.getQty();
                    bom1.setQty(qty * itemQty);
                    listBom.add(bom1);
                    listBomt02.add(bom1);
                }
            }

            level = level + 1;
            listBomt01.clear();
            // When the current layer is listBomt02, the results are output to listBom and listBomt01.
            for (int i = 0; i < listBomt02.size(); i++) {
                Bom bom = listBomt02.get(i);
                String subcode = bom.getSubItemCode();
                Integer itemqty = bom.getQty();
                List<Bom> listBomt02sub = bomRepository.listBomByParentCode(subcode);

                for (int i2 = 0; i2 < listBomt02sub.size(); i2++) {
                    Bom bom2 = listBomt02sub.get(i2);
                    bom2.setLevel(level);
                    Integer qty = bom2.getQty();
                    bom2.setQty(qty * itemqty);
                    listBom.add(bom2);
                    listBomt01.add(bom2);
                }
            }

        }  // When Ends

        // Returns the expanded result listBom
        return listBom;
    }
}