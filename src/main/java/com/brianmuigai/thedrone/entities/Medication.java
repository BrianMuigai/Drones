package com.brianmuigai.thedrone.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Entity
public class Medication {
    @Pattern(regexp = "^[A-Z0-9_-]*$")
    @NotBlank(message = "Valid code is required")
    private @Id String code;
    @Pattern(regexp = "^[A-Za-z0-9_-]*$")
    @NotBlank(message = "Valid name is required")
    private String name;
    @PositiveOrZero
    @Column(nullable = false)
    private double weight;
    @OneToOne
    @JoinColumn(name = "image_id")
    private FileModel image;

    public Medication(){}
    public Medication(String code, String name, double weight) {
        this.code = code;
        this.name = name;
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public FileModel getImage() {
        return image;
    }

    public void setImage(FileModel image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medication)) return false;
        Medication that = (Medication) o;
        return Double.compare(that.weight, weight) == 0 && code.equals(that.code) && name.equals(that.name) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, weight, image);
    }

    @Override
    public String toString() {
        return "Medication{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", image='" + image + '\'' +
                '}';
    }
}
