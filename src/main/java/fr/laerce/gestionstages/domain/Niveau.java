package fr.laerce.gestionstages.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "niveau")
public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(length = 8, nullable = false, name="code", unique = true)
    private String code;
    @Column(length = 15)
    private String libelleCourt;
    @Column(length = 50)
    private String libelleLong;
    @OneToMany(mappedBy = "niveau")
    private List<Division> divisions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelleCourt() {
        return libelleCourt;
    }

    public void setLibelleCourt(String libelleCourt) {
        this.libelleCourt = libelleCourt;
    }

    public String getLibelleLong() {
        return libelleLong;
    }

    public void setLibelleLong(String libelleLong) {
        this.libelleLong = libelleLong;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Niveau niveau = (Niveau) o;
        return Objects.equals(getId(), niveau.getId()) &&
                Objects.equals(getCode(), niveau.getCode()) &&
                Objects.equals(getLibelleCourt(), niveau.getLibelleCourt()) &&
                Objects.equals(getLibelleLong(), niveau.getLibelleLong());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getCode(), getLibelleCourt(), getLibelleLong());
    }
}
