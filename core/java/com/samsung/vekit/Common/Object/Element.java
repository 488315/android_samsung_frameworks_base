package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Panel.Panel;

/* loaded from: classes6.dex */
public class Element {
  protected String TAG = getClass().getSimpleName();
  protected VEContext context;
  protected ElementType elementType;

  /* renamed from: id */
  protected int f3097id;
  protected String name;

  protected Element(VEContext context, ElementType type, int id, String name) {
    this.context = context;
    this.elementType = type;
    this.f3097id = id;
    this.name = name;
  }

  public VEContext getContext() {
    return this.context;
  }

  public void update() {
    this.context.getNativeInterface().update(this);
  }

  public int getId() {
    return this.f3097id;
  }

  public String getName() {
    return this.name;
  }

  public ElementType getElementType() {
    return this.elementType;
  }

  public Panel getPanel() {
    return null;
  }
}
