package com.jqh.core.recycler;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

public class MultipeItemEntityBuilder {

    private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

    public MultipeItemEntityBuilder() {
        FIELDS.clear();
    }
    public  final MultipeItemEntityBuilder setItemType(int itemType){
        FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public final MultipeItemEntityBuilder setField(Object key , Object value){
        FIELDS.put(key,value);
        return this ;
    }

    public final MultipeItemEntityBuilder setFileds(WeakHashMap<?,?> map){
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
        return new MultipleItemEntity(FIELDS);
    }

}
