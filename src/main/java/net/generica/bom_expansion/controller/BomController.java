package net.generica.bom_expansion.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.generica.bom_expansion.domain.Bom;
import net.generica.bom_expansion.service.BomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

public class BomController {

    private final BomService bomService;

    public BomController(BomService bomService) {
        this.bomService = bomService;
    }

    //Depending on the material number, BOM and back handler are developed layer by layer.
    @ResponseBody
    @RequestMapping(value="/bomexplosion", method= RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String bomExplosionByLevel(@RequestParam(value="parentCode",required=false) String parentCode, Map<String, Object> map) throws JsonProcessingException {
    	List<Bom> listBom = bomService.BomExplosionByLevel(parentCode);

        // LIST object to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String jsonlist = mapper.writeValueAsString(listBom);

    	return  jsonlist;
      }
}
