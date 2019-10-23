package net.generica.bom_expansion.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class Bom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer parentId;
    private String parentCode;
    private String parentName;
    private Integer subItemId;
    private String subItemCode;
    private String subItemName;
    private String unit;
    private Integer qty;
    private Integer level;
    private Date createDate;
    private String username;
    private String subItemFlag;
}