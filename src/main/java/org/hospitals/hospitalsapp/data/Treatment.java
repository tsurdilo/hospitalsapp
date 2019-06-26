package org.hospitals.hospitalsapp.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Treatment implements Serializable {
    private String name;
    private boolean isOngoing;
    private Date performedOn;
    private List<Test> tests;

}