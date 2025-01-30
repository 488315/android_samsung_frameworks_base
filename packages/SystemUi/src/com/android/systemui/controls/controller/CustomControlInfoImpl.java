package com.android.systemui.controls.controller;

import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlInfoImpl {
    public int layoutType;

    public CustomControlInfoImpl(int i) {
        this.layoutType = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CustomControlInfoImpl) && this.layoutType == ((CustomControlInfoImpl) obj).layoutType;
    }

    public final int hashCode() {
        return Integer.hashCode(this.layoutType);
    }

    public final String toString() {
        return LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("CustomControlInfoImpl(layoutType=", this.layoutType, ")");
    }
}
