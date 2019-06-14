package com.betbull.social.football.dto;

import com.betbull.social.football.entity.Footballer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class FootBallerRequest {

    @NotNull
    @Size(max = 100, message = "{validation.footballer.request.name.size}")
    private String fullName;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    @NotNull
    private String nationality;

    @NotNull
    @Min(value = 16, message = "{validation.footballer.request.age.size}")
    private Integer age;

    @NotNull
    private Integer weight;


    @NotNull
    private Integer height;

    @NotNull
    private Integer shirtNumber;

    @NotNull
    @Size(max = 20)
    private String position;

    @NotNull
    private int experienceMonth;




    public Footballer convertToFootballer() {
        final Footballer footballer = new Footballer();
        footballer.setFullName(fullName);
        footballer.setDateOfBirth(dateOfBirth);
        footballer.setNationality(nationality);
        footballer.setAge(age);
        footballer.setWeight(weight);
        footballer.setHeight(height);
        footballer.setShirtNumber(shirtNumber);
        footballer.setPosition(position);
        footballer.setExperienceMonth(experienceMonth);
        return footballer;
    }


}
