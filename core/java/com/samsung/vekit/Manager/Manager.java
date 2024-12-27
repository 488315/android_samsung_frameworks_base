package com.samsung.vekit.Manager;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Manager<T> extends Element {
    protected int index;
    protected ManagerType managerType;
    protected HashMap<Integer, T> map;

    protected Manager(VEContext context, ManagerType type) {
        super(context, ElementType.MANAGER, 0, "Manager");
        this.map = new HashMap<>();
        this.managerType = type;
        this.index = 0;
    }

    protected int generateUniqueId() throws Exception {
        if (this.map.size() >= 2147483646) {
            throw new Exception("Map is full");
        }
        while (this.map.containsKey(Integer.valueOf(this.index))) {
            int i = this.index + 1;
            this.index = i;
            this.index = i % Integer.MAX_VALUE;
        }
        return this.index;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void add(T t) {
        this.map.put(Integer.valueOf(((Element) t).getId()), t);
        this.context.getNativeInterface().create((Element) t);
    }

    public T get(int id) {
        return this.map.get(Integer.valueOf(id));
    }

    public Map<Integer, T> getMap() {
        return Collections.unmodifiableMap(this.map);
    }

    public int size() {
        return this.map.size();
    }

    public void remove(int id) {
        ElementType targetElementType = getTargetElementType(this.managerType);
        if (targetElementType == null) {
            Log.e(
                    this.TAG,
                    "Fail to remove item["
                            + id
                            + "] with managerType["
                            + this.managerType
                            + NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            this.map.remove(Integer.valueOf(id));
            this.context.getNativeInterface().remove(targetElementType, id);
        }
    }

    private ElementType getTargetElementType(ManagerType managerType) {
        switch (managerType) {
            case CONTENT:
                return ElementType.CONTENT;
            case ITEM:
                return ElementType.ITEM;
            case LAYER:
                return ElementType.LAYER;
            case ANIMATION:
                return ElementType.ANIMATION;
            case FILTER:
                return ElementType.FILTER;
            default:
                Log.d(this.TAG, "unexpected manager type = " + managerType);
                return null;
        }
    }
}
