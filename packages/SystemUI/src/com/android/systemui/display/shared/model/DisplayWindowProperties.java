package com.android.systemui.display.shared.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisplayWindowProperties {
    public final Context context;
    public final int displayId;
    public final LayoutInflater layoutInflater;
    public final WindowManager windowManager;
    public final int windowType;

    public DisplayWindowProperties(int i, int i2, Context context, WindowManager windowManager, LayoutInflater layoutInflater) {
        this.displayId = i;
        this.windowType = i2;
        this.context = context;
        this.windowManager = windowManager;
        this.layoutInflater = layoutInflater;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisplayWindowProperties)) {
            return false;
        }
        DisplayWindowProperties displayWindowProperties = (DisplayWindowProperties) obj;
        return this.displayId == displayWindowProperties.displayId && this.windowType == displayWindowProperties.windowType && Intrinsics.areEqual(this.context, displayWindowProperties.context) && Intrinsics.areEqual(this.windowManager, displayWindowProperties.windowManager) && Intrinsics.areEqual(this.layoutInflater, displayWindowProperties.layoutInflater);
    }

    public final int hashCode() {
        return this.layoutInflater.hashCode() + ((this.windowManager.hashCode() + ((this.context.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.windowType, Integer.hashCode(this.displayId) * 31, 31)) * 31)) * 31);
    }

    public final String toString() {
        return "DisplayWindowProperties(displayId=" + this.displayId + ", windowType=" + this.windowType + ", context=" + this.context + ", windowManager=" + this.windowManager + ", layoutInflater=" + this.layoutInflater + ")";
    }
}
