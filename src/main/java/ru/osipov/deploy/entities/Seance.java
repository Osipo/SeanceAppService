package ru.osipov.deploy.entities;

import com.google.common.base.Objects;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "seance")
@IdClass(SeancePK.class)
public class Seance {

    @Id
    @Column(name = "cid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Id
    @Column(name = "fid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    @Column(name = "begining", nullable = false)
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance s = (Seance) o;
        return  Objects.equal(cid, s.cid) &&
                Objects.equal(fid,s.fid) &&
                Objects.equal(date,s.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cid,fid,date);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("cid",cid)
                .add("fid",fid)
                .add("date",date)
                .toString();
    }
}
