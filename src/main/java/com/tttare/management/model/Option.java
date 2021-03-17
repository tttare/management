package com.tttare.management.model;

import lombok.Data;
import org.springframework.data.util.CastUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class Option implements Comparable<Option>{

    private String value;
    private String label;
    private List<Option> children = new ArrayList<>();

    public  Option(String value,String label){
        this.value = value;
        this.label = label;
    }


    @Override
    public int compareTo(Option other) {
        try{
            int a = Integer.valueOf(this.value);
            int b = Integer.valueOf(other.value);
            return a>b ? 1 : -1;
        }catch (Exception e){
            return 0;
        }
    }

}
