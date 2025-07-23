package com.samsung.android.sdk.scs.ai.visual.c2pa;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Ingredients {
    private final String activeManifest;
    private final String relationship;
    private final String title;
    private final List<ValidationStatus> validationStatus;

    public Ingredients(String str, String str2, String str3, List<ValidationStatus> list) {
        this.title = str;
        this.activeManifest = str2;
        this.relationship = str3;
        this.validationStatus = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Ingredients copy$default(Ingredients ingredients, String str, String str2, String str3, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = ingredients.title;
        }
        if ((i & 2) != 0) {
            str2 = ingredients.activeManifest;
        }
        if ((i & 4) != 0) {
            str3 = ingredients.relationship;
        }
        if ((i & 8) != 0) {
            list = ingredients.validationStatus;
        }
        return ingredients.copy(str, str2, str3, list);
    }

    public final String component1() {
        return this.title;
    }

    public final String component2() {
        return this.activeManifest;
    }

    public final String component3() {
        return this.relationship;
    }

    public final List<ValidationStatus> component4() {
        return this.validationStatus;
    }

    public final Ingredients copy(String str, String str2, String str3, List<ValidationStatus> list) {
        return new Ingredients(str, str2, str3, list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ingredients)) {
            return false;
        }
        Ingredients ingredients = (Ingredients) obj;
        return Intrinsics.areEqual(this.title, ingredients.title) && Intrinsics.areEqual(this.activeManifest, ingredients.activeManifest) && Intrinsics.areEqual(this.relationship, ingredients.relationship) && Intrinsics.areEqual(this.validationStatus, ingredients.validationStatus);
    }

    public final String getActiveManifest() {
        return this.activeManifest;
    }

    public final String getRelationship() {
        return this.relationship;
    }

    public final String getTitle() {
        return this.title;
    }

    public final List<ValidationStatus> getValidationStatus() {
        return this.validationStatus;
    }

    public int hashCode() {
        String str = this.title;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.activeManifest;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.relationship;
        return this.validationStatus.hashCode() + ((hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31);
    }

    public String toString() {
        return "Ingredients(title=" + this.title + ", activeManifest=" + this.activeManifest + ", relationship=" + this.relationship + ", validationStatus=" + this.validationStatus + ')';
    }
}
