package com.jqh.core.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class MultipleItemEntity implements MultiItemEntity{

    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUENE = new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object,Object>> FIELDS_PEFERENCE =
            new SoftReference<>(MULTIPLE_FIELDS,ITEM_QUENE);

    MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_PEFERENCE.get().putAll(fields);
    }

    public static MultipeItemEntityBuilder builder(){
        return new MultipeItemEntityBuilder();
    }
    @Override
    public int getItemType() {
        return (int)FIELDS_PEFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    public final <T> T getField(Object key){
        return (T)FIELDS_PEFERENCE.get().get(key);
    }

    public final LinkedHashMap<?,?> getFields(){
        return FIELDS_PEFERENCE.get();
    }

    public final MultiItemEntity setField(Object key,Object value){
        FIELDS_PEFERENCE.get().put(key,value);
        return this;
    }
}
