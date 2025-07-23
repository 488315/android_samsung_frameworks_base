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

/* loaded from: classes6.dex */
public class Manager<T> extends Element {
    protected int index;
    protected ManagerType managerType;
    protected HashMap<Integer, T> map;

    protected Manager(VEContext vEContext, ManagerType managerType) {
        super(vEContext, ElementType.MANAGER, 0, "Manager");
        this.map = new HashMap<>();
        this.managerType = managerType;
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
        Element element = (Element) t;
        this.map.put(Integer.valueOf(element.getId()), t);
        this.context.getNativeInterface().create(element);
    }

    public T get(int i) {
        return this.map.get(Integer.valueOf(i));
    }

    public Map<Integer, T> getMap() {
        return Collections.unmodifiableMap(this.map);
    }

    public int size() {
        return this.map.size();
    }

    public void remove(int i) {
        ElementType targetElementType = getTargetElementType(this.managerType);
        if (targetElementType == null) {
            Log.e(this.TAG, "Fail to remove item[" + i + "] with managerType[" + this.managerType + NavigationBarInflaterView.SIZE_MOD_END);
            return;
        }
        this.map.remove(Integer.valueOf(i));
        this.context.getNativeInterface().remove(targetElementType, i);
    }

    /* renamed from: com.samsung.vekit.Manager.Manager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ManagerType;

        static {
            int[] iArr = new int[ManagerType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$ManagerType = iArr;
            try {
                iArr[ManagerType.CONTENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.ITEM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.LAYER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.ANIMATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.FILTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private ElementType getTargetElementType(ManagerType managerType) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$ManagerType[managerType.ordinal()];
        if (i == 1) {
            return ElementType.CONTENT;
        }
        if (i == 2) {
            return ElementType.ITEM;
        }
        if (i == 3) {
            return ElementType.LAYER;
        }
        if (i == 4) {
            return ElementType.ANIMATION;
        }
        if (i == 5) {
            return ElementType.FILTER;
        }
        Log.d(this.TAG, "unexpected manager type = " + managerType);
        return null;
    }
}
