package com.samsung.android.sdk.scs.ai.visual.c2pa;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Ingredient {

    @SerializedName("digitalSourceType")
    private final String digitalSourceType;
    private final String url;

    public Ingredient(String str, String str2) {
        this.url = str;
        this.digitalSourceType = str2;
    }

    public static /* synthetic */ Ingredient copy$default(Ingredient ingredient, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = ingredient.url;
        }
        if ((i & 2) != 0) {
            str2 = ingredient.digitalSourceType;
        }
        return ingredient.copy(str, str2);
    }

    public final String component1() {
        return this.url;
    }

    public final String component2() {
        return this.digitalSourceType;
    }

    public final Ingredient copy(String str, String str2) {
        return new Ingredient(str, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ingredient)) {
            return false;
        }
        Ingredient ingredient = (Ingredient) obj;
        return Intrinsics.areEqual(this.url, ingredient.url) && Intrinsics.areEqual(this.digitalSourceType, ingredient.digitalSourceType);
    }

    public final String getDigitalSourceType() {
        return this.digitalSourceType;
    }

    public final String getUrl() {
        return this.url;
    }

    public int hashCode() {
        String str = this.url;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.digitalSourceType;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Ingredient(url=");
        sb.append(this.url);
        sb.append(", digitalSourceType=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.digitalSourceType, ')');
    }
}
