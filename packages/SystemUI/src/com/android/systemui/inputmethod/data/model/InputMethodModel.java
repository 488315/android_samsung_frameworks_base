package com.android.systemui.inputmethod.data.model;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class InputMethodModel {
    public final String imeId;
    public final List subtypes;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public InputMethodModel(String str, List<Subtype> list) {
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
        return Intrinsics.areEqual(this.imeId, inputMethodModel.imeId) && Intrinsics.areEqual(this.subtypes, inputMethodModel.subtypes);
    }

    public final int hashCode() {
        return this.subtypes.hashCode() + (this.imeId.hashCode() * 31);
    }

    public final String toString() {
        return "InputMethodModel(imeId=" + this.imeId + ", subtypes=" + this.subtypes + ")";
    }
}
