package com.jqh.core.recycler;

import java.util.ArrayList;

public abstract class DataConverter {

    protected  final ArrayList<MultipleItemEntity> ENTITES = new ArrayList<>();

    private String mjsonData = null ;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.mjsonData = json;
        return this ;
    }

    protected  String getJsonData(){
        if(mjsonData == null || mjsonData.isEmpty()){
            throw  new NullPointerException("Data is null");
        }
        return mjsonData;
    }
}
