package com.android.systemui.bouncer.shared.model;

import android.content.res.ColorStateList;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class BouncerShowMessageModel {
    public final ColorStateList colorStateList;
    public final String message;

    public BouncerShowMessageModel(String str, ColorStateList colorStateList) {
        this.message = str;
        this.colorStateList = colorStateList;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BouncerShowMessageModel)) {
            return false;
        }
        BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj;
        return Intrinsics.areEqual(this.message, bouncerShowMessageModel.message) && Intrinsics.areEqual(this.colorStateList, bouncerShowMessageModel.colorStateList);
    }

    public final int hashCode() {
        String str = this.message;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        ColorStateList colorStateList = this.colorStateList;
        return iHashCode + (colorStateList != null ? colorStateList.hashCode() : 0);
    }

    public final String toString() {
        return "BouncerShowMessageModel(message=" + this.message + ", colorStateList=" + this.colorStateList + ")";
    }
}
