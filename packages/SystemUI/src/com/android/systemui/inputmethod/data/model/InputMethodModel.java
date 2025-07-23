package com.android.systemui.inputmethod.data.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InputMethodModel {
    public final String imeId;
    public final List subtypes;
    public final int userId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Subtype {
        public final boolean isAuxiliary;
        public final int subtypeId;

        public Subtype(int i, boolean z) {
            this.subtypeId = i;
            this.isAuxiliary = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Subtype)) {
                return false;
            }
            Subtype subtype = (Subtype) obj;
            return this.subtypeId == subtype.subtypeId && this.isAuxiliary == subtype.isAuxiliary;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isAuxiliary) + (Integer.hashCode(this.subtypeId) * 31);
        }

        public final String toString() {
            return "Subtype(subtypeId=" + this.subtypeId + ", isAuxiliary=" + this.isAuxiliary + ")";
        }
    }

    public InputMethodModel(int i, String str, List<Subtype> list) {
        this.userId = i;
        this.imeId = str;
        this.subtypes = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InputMethodModel)) {
            return false;
        }
        InputMethodModel inputMethodModel = (InputMethodModel) obj;
        return this.userId == inputMethodModel.userId && Intrinsics.areEqual(this.imeId, inputMethodModel.imeId) && Intrinsics.areEqual(this.subtypes, inputMethodModel.subtypes);
    }

    public final int hashCode() {
        return this.subtypes.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.userId) * 31, 31, this.imeId);
    }

    public final String toString() {
        return "InputMethodModel(userId=" + this.userId + ", imeId=" + this.imeId + ", subtypes=" + this.subtypes + ")";
    }
}
