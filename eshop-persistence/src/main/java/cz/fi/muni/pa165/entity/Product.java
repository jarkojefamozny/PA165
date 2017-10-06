package cz.fi.muni.pa165.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false,unique=true)
    private String name;

    private String description;

    @Enumerated
    private Color color;
    
    @Temporal(TemporalType.DATE)
    private java.util.Date addedDate;
    
    public Product() {}
    
    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }
    public java.util.Date getAddedDate() {
        return addedDate;
    }
    public void setAddedDate(java.util.Date addedDate) {
        this.addedDate = addedDate;
    }   
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
