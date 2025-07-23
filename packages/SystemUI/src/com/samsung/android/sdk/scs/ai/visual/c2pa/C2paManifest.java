package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class C2paManifest {
    private final List<C2paAssertion> assertions;
    private JsonArray assertionsJsonArray;
    private final String claimGenerator;
    private final String format;
    private final List<Ingredients> ingredients;
    private final String instanceId;
    private boolean isInvalid;
    private final String label;
    private final SignatureInfo signatureInfo;
    private final String title;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        private String claimGenerator = Constant.CLAIM_GENERATOR;
        private List<C2paAssertion> assertions = new ArrayList();

        public final Builder addAssertion(C2paAssertion c2paAssertion) {
            this.assertions.add(c2paAssertion);
            return this;
        }

        public final String build() {
            return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create().toJson(new C2paManifest(this.claimGenerator, null, null, null, null, this.assertions, null, null, false, null));
        }

        public final Builder claimGenerator(String str) {
            this.claimGenerator = str;
            return this;
        }
    }

    public C2paManifest(String str, String str2, String str3, String str4, List<Ingredients> list, List<C2paAssertion> list2, SignatureInfo signatureInfo, String str5, boolean z, JsonArray jsonArray) {
        this.claimGenerator = str;
        this.title = str2;
        this.format = str3;
        this.instanceId = str4;
        this.ingredients = list;
        this.assertions = list2;
        this.signatureInfo = signatureInfo;
        this.label = str5;
        this.isInvalid = z;
        this.assertionsJsonArray = jsonArray;
    }

    public static /* synthetic */ C2paManifest copy$default(C2paManifest c2paManifest, String str, String str2, String str3, String str4, List list, List list2, SignatureInfo signatureInfo, String str5, boolean z, JsonArray jsonArray, int i, Object obj) {
        if ((i & 1) != 0) {
            str = c2paManifest.claimGenerator;
        }
        if ((i & 2) != 0) {
            str2 = c2paManifest.title;
        }
        if ((i & 4) != 0) {
            str3 = c2paManifest.format;
        }
        if ((i & 8) != 0) {
            str4 = c2paManifest.instanceId;
        }
        if ((i & 16) != 0) {
            list = c2paManifest.ingredients;
        }
        if ((i & 32) != 0) {
            list2 = c2paManifest.assertions;
        }
        if ((i & 64) != 0) {
            signatureInfo = c2paManifest.signatureInfo;
        }
        if ((i & 128) != 0) {
            str5 = c2paManifest.label;
        }
        if ((i & 256) != 0) {
            z = c2paManifest.isInvalid;
        }
        if ((i & 512) != 0) {
            jsonArray = c2paManifest.assertionsJsonArray;
        }
        boolean z2 = z;
        JsonArray jsonArray2 = jsonArray;
        SignatureInfo signatureInfo2 = signatureInfo;
        String str6 = str5;
        List list3 = list;
        List list4 = list2;
        return c2paManifest.copy(str, str2, str3, str4, list3, list4, signatureInfo2, str6, z2, jsonArray2);
    }

    public final String component1() {
        return this.claimGenerator;
    }

    public final JsonArray component10() {
        return this.assertionsJsonArray;
    }

    public final String component2() {
        return this.title;
    }

    public final String component3() {
        return this.format;
    }

    public final String component4() {
        return this.instanceId;
    }

    public final List<Ingredients> component5() {
        return this.ingredients;
    }

    public final List<C2paAssertion> component6() {
        return this.assertions;
    }

    public final SignatureInfo component7() {
        return this.signatureInfo;
    }

    public final String component8() {
        return this.label;
    }

    public final boolean component9() {
        return this.isInvalid;
    }

    public final C2paManifest copy(String str, String str2, String str3, String str4, List<Ingredients> list, List<C2paAssertion> list2, SignatureInfo signatureInfo, String str5, boolean z, JsonArray jsonArray) {
        return new C2paManifest(str, str2, str3, str4, list, list2, signatureInfo, str5, z, jsonArray);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2paManifest)) {
            return false;
        }
        C2paManifest c2paManifest = (C2paManifest) obj;
        return Intrinsics.areEqual(this.claimGenerator, c2paManifest.claimGenerator) && Intrinsics.areEqual(this.title, c2paManifest.title) && Intrinsics.areEqual(this.format, c2paManifest.format) && Intrinsics.areEqual(this.instanceId, c2paManifest.instanceId) && Intrinsics.areEqual(this.ingredients, c2paManifest.ingredients) && Intrinsics.areEqual(this.assertions, c2paManifest.assertions) && Intrinsics.areEqual(this.signatureInfo, c2paManifest.signatureInfo) && Intrinsics.areEqual(this.label, c2paManifest.label) && this.isInvalid == c2paManifest.isInvalid && Intrinsics.areEqual(this.assertionsJsonArray, c2paManifest.assertionsJsonArray);
    }

    public final List<Action> getActions() {
        String str;
        String str2;
        List<String> list;
        List<C2paAssertion> list2 = this.assertions;
        List list3 = null;
        if (list2 != null) {
            List arrayList = new ArrayList();
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                Data data = ((C2paAssertion) it.next()).getData();
                List<Action> actions = data != null ? data.getActions() : null;
                if (actions != null) {
                    arrayList.add(actions);
                }
            }
            list3 = arrayList;
        }
        if (list3 == null) {
            list3 = EmptyList.INSTANCE;
        }
        List<Action> flatten = CollectionsKt__IterablesKt.flatten(list3);
        if (!flatten.isEmpty()) {
            ArrayList arrayList2 = (ArrayList) flatten;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                Action action = (Action) obj;
                SignatureInfo signatureInfo = this.signatureInfo;
                if (signatureInfo == null || (str = signatureInfo.getTime()) == null) {
                    str = C2paManifestList.UNKNOWN_TIME;
                }
                action.setActionTime(str);
                SignatureInfo signatureInfo2 = this.signatureInfo;
                String str3 = C2paManifestList.UNKNOWN_VALUE;
                if (signatureInfo2 == null || (str2 = signatureInfo2.getIssuer()) == null) {
                    str2 = C2paManifestList.UNKNOWN_VALUE;
                }
                action.setIssuer(str2);
                String str4 = this.claimGenerator;
                if (str4 != null) {
                    str3 = str4;
                }
                action.setClaimGenerator(str3);
                action.setInvalid(Boolean.valueOf(this.isInvalid));
                action.setTitle(this.title);
                List<Ingredients> list4 = this.ingredients;
                if (list4 != null) {
                    list = new ArrayList<>();
                    Iterator<T> it2 = list4.iterator();
                    while (it2.hasNext()) {
                        String title = ((Ingredients) it2.next()).getTitle();
                        if (title != null) {
                            list.add(title);
                        }
                    }
                } else {
                    list = EmptyList.INSTANCE;
                }
                action.setIngredientsFile(list);
            }
        }
        return flatten;
    }

    public final List<C2paAssertion> getAssertions() {
        return this.assertions;
    }

    public final JsonArray getAssertionsJsonArray() {
        return this.assertionsJsonArray;
    }

    public final String getClaimGenerator() {
        return this.claimGenerator;
    }

    public final String getFormat() {
        return this.format;
    }

    public final List<Ingredients> getIngredients() {
        return this.ingredients;
    }

    public final String getInstanceId() {
        return this.instanceId;
    }

    public final String getLabel() {
        return this.label;
    }

    public final SignatureInfo getSignatureInfo() {
        return this.signatureInfo;
    }

    public final String getTitle() {
        return this.title;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.claimGenerator;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.title;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.format;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.instanceId;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        List<Ingredients> list = this.ingredients;
        int hashCode5 = (hashCode4 + (list == null ? 0 : list.hashCode())) * 31;
        List<C2paAssertion> list2 = this.assertions;
        int hashCode6 = (hashCode5 + (list2 == null ? 0 : list2.hashCode())) * 31;
        SignatureInfo signatureInfo = this.signatureInfo;
        int hashCode7 = (hashCode6 + (signatureInfo == null ? 0 : signatureInfo.hashCode())) * 31;
        String str5 = this.label;
        int hashCode8 = (hashCode7 + (str5 == null ? 0 : str5.hashCode())) * 31;
        boolean z = this.isInvalid;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode8 + i) * 31;
        JsonArray jsonArray = this.assertionsJsonArray;
        return i2 + (jsonArray != null ? jsonArray.hashCode() : 0);
    }

    public final boolean isInvalid() {
        return this.isInvalid;
    }

    public final void setAssertionsJsonArray(JsonArray jsonArray) {
        this.assertionsJsonArray = jsonArray;
    }

    public final void setInvalid(boolean z) {
        this.isInvalid = z;
    }

    public String toString() {
        return "C2paManifest(claimGenerator=" + this.claimGenerator + ", title=" + this.title + ", format=" + this.format + ", instanceId=" + this.instanceId + ", ingredients=" + this.ingredients + ", assertions=" + this.assertions + ", signatureInfo=" + this.signatureInfo + ", label=" + this.label + ", isInvalid=" + this.isInvalid + ", assertionsJsonArray=" + this.assertionsJsonArray + ')';
    }

    public /* synthetic */ C2paManifest(String str, String str2, String str3, String str4, List list, List list2, SignatureInfo signatureInfo, String str5, boolean z, JsonArray jsonArray, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, list, list2, signatureInfo, str5, (i & 256) != 0 ? false : z, jsonArray);
    }
}
