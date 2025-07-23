package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Panel.Panel;

/* loaded from: classes6.dex */
public class Element {
    protected String TAG = getClass().getSimpleName();
    protected VEContext context;
    protected ElementType elementType;
    protected int id;
    protected String name;

    public Panel getPanel() {
        return null;
    }

    public Element setOpacity(float f) {
        return null;
    }

    protected Element(VEContext vEContext, ElementType elementType, int i, String str) {
        this.context = vEContext;
        this.elementType = elementType;
        this.id = i;
        this.name = str;
    }

    public VEContext getContext() {
        return this.context;
    }

    public void update() {
        this.context.getNativeInterface().update(this);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ElementType getElementType() {
        return this.elementType;
    }
}
