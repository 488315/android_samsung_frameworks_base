package com.samsung.android.sdk.scs.ai.visual.c2pa;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Parameters {
    private final List<Author> author;
    private final Ingredient ingredient;
    private final String type;
    private final String value;
    private final String version;

    public Parameters(Ingredient ingredient, String str, String str2, String str3, List<Author> list) {
        this.ingredient = ingredient;
        this.type = str;
        this.version = str2;
        this.value = str3;
        this.author = list;
    }

    public static /* synthetic */ Parameters copy$default(Parameters parameters, Ingredient ingredient, String str, String str2, String str3, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            ingredient = parameters.ingredient;
        }
        if ((i & 2) != 0) {
            str = parameters.type;
        }
        if ((i & 4) != 0) {
            str2 = parameters.version;
        }
        if ((i & 8) != 0) {
            str3 = parameters.value;
        }
        if ((i & 16) != 0) {
            list = parameters.author;
        }
        List list2 = list;
        String str4 = str2;
        return parameters.copy(ingredient, str, str4, str3, list2);
    }

    public final Ingredient component1() {
        return this.ingredient;
    }

    public final String component2() {
        return this.type;
    }

    public final String component3() {
        return this.version;
    }

    public final String component4() {
        return this.value;
    }

    public final List<Author> component5() {
        return this.author;
    }

    public final Parameters copy(Ingredient ingredient, String str, String str2, String str3, List<Author> list) {
        return new Parameters(ingredient, str, str2, str3, list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Parameters)) {
            return false;
        }
        Parameters parameters = (Parameters) obj;
        return Intrinsics.areEqual(this.ingredient, parameters.ingredient) && Intrinsics.areEqual(this.type, parameters.type) && Intrinsics.areEqual(this.version, parameters.version) && Intrinsics.areEqual(this.value, parameters.value) && Intrinsics.areEqual(this.author, parameters.author);
    }

    public final List<Author> getAuthor() {
        return this.author;
    }

    public final Ingredient getIngredient() {
        return this.ingredient;
    }

    public final String getType() {
        return this.type;
    }

    public final String getValue() {
        return this.value;
    }

    public final String getVersion() {
        return this.version;
    }

    public int hashCode() {
        Ingredient ingredient = this.ingredient;
        int iHashCode = (ingredient == null ? 0 : ingredient.hashCode()) * 31;
        String str = this.type;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.version;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.value;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        List<Author> list = this.author;
        return iHashCode4 + (list != null ? list.hashCode() : 0);
    }

    public String toString() {
        return "Parameters(ingredient=" + this.ingredient + ", type=" + this.type + ", version=" + this.version + ", value=" + this.value + ", author=" + this.author + ')';
    }
}
