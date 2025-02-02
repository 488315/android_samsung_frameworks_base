package com.android.systemui.edgelighting.data.policy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PolicyInfo {
    public final int category;
    public final int color;
    public final boolean defaultOn;
    public final String item;
    public final int priority;
    public final int range;
    public final int versionCode;

    public PolicyInfo(String str, int i) {
        this.item = str;
        this.category = i;
        this.range = 0;
        this.color = 0;
        this.versionCode = 0;
        this.priority = 0;
        this.defaultOn = false;
    }

    public final String toString() {
        return "item = " + this.item + ", category = " + this.category + ", range = " + this.range + ", versionCode = " + this.versionCode + ", color = " + this.color + ", priority = " + this.priority + ", defaultOn = " + this.defaultOn;
    }

    public PolicyInfo(String str, int i, int i2, int i3, int i4) {
        this.item = str;
        this.category = i;
        this.range = i2;
        this.color = i3;
        this.versionCode = i4;
        this.priority = 0;
        this.defaultOn = false;
    }

    public PolicyInfo(String str, int i, int i2, boolean z, int i3) {
        this.item = str;
        this.category = i;
        this.priority = i2;
        this.defaultOn = z;
        this.color = i3;
        this.range = 0;
        this.versionCode = 0;
    }
}
