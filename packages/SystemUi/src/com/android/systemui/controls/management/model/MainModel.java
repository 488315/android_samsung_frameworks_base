package com.android.systemui.controls.management.model;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MainModel {
    public boolean needToMakeDim;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Type {
        STRUCTURE,
        CONTROL,
        SMALL_CONTROL,
        COMPONENT,
        PANEL
    }

    public abstract Type getType();
}
