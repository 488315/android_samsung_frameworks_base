package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes4.dex */
public final class Action {
    private final String action;
    private String actionTime;
    private String activeManifest;
    private String claimGenerator;

    @SerializedName("digitalSourceType")
    private final String digitalSourceType;
    private List<String> ingredientsFile;
    private Boolean isInvalid;
    private String issuer;
    private final Parameters parameters;

    @SerializedName("softwareAgent")
    private final String softwareAgent;
    private String title;

    public final class Builder {
        private String action;
        private String digitalSourceType;
        private Parameters parameters;
        private String softwareAgent;

        public static /* synthetic */ Builder parameters$default(Builder builder, String str, String str2, String str3, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            if ((i & 2) != 0) {
                str2 = null;
            }
            if ((i & 4) != 0) {
                str3 = null;
            }
            return builder.parameters(str, str2, str3, list);
        }

        public final Builder action(C2paAction c2paAction) {
            this.action = c2paAction.getStr();
            return this;
        }

        public final Action build() {
            return new Action(this.action, this.digitalSourceType, this.softwareAgent, this.parameters, null, null, null, null, null, null, EmptyList.INSTANCE);
        }

        public final Builder digitalSourceType(DigitalSourceType digitalSourceType) {
            this.digitalSourceType = digitalSourceType.getUri();
            return this;
        }

        public final Builder parameters(String str, String str2, String str3, List<String> list) {
            if (list == null) {
                this.parameters = new Parameters(null, str, str2, str3, null);
                return this;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new Author(it.next()));
            }
            this.parameters = new Parameters(null, str, str2, str3, arrayList);
            return this;
        }

        public final Builder softwareAgent(String str) {
            this.softwareAgent = str;
            return this;
        }
    }

    public Action(String str, String str2, String str3, Parameters parameters, String str4, String str5, String str6, Boolean bool, String str7, String str8, List<String> list) {
        this.action = str;
        this.digitalSourceType = str2;
        this.softwareAgent = str3;
        this.parameters = parameters;
        this.actionTime = str4;
        this.issuer = str5;
        this.claimGenerator = str6;
        this.isInvalid = bool;
        this.title = str7;
        this.activeManifest = str8;
        this.ingredientsFile = list;
    }

    public static /* synthetic */ Action copy$default(Action action, String str, String str2, String str3, Parameters parameters, String str4, String str5, String str6, Boolean bool, String str7, String str8, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = action.action;
        }
        if ((i & 2) != 0) {
            str2 = action.digitalSourceType;
        }
        if ((i & 4) != 0) {
            str3 = action.softwareAgent;
        }
        if ((i & 8) != 0) {
            parameters = action.parameters;
        }
        if ((i & 16) != 0) {
            str4 = action.actionTime;
        }
        if ((i & 32) != 0) {
            str5 = action.issuer;
        }
        if ((i & 64) != 0) {
            str6 = action.claimGenerator;
        }
        if ((i & 128) != 0) {
            bool = action.isInvalid;
        }
        if ((i & 256) != 0) {
            str7 = action.title;
        }
        if ((i & 512) != 0) {
            str8 = action.activeManifest;
        }
        if ((i & 1024) != 0) {
            list = action.ingredientsFile;
        }
        String str9 = str8;
        List list2 = list;
        Boolean bool2 = bool;
        String str10 = str7;
        String str11 = str5;
        String str12 = str6;
        String str13 = str4;
        String str14 = str3;
        return action.copy(str, str2, str14, parameters, str13, str11, str12, bool2, str10, str9, list2);
    }

    private final boolean hasAiSourceType(String str) {
        return str != null && (StringsKt__StringsKt.contains(str, DigitalSourceType.TRAINED_ALGORITHMIC_MEDIA.getUri(), false) || StringsKt__StringsKt.contains(str, DigitalSourceType.COMPOSITE_SYNTHETIC.getUri(), false) || StringsKt__StringsKt.contains(str, DigitalSourceType.COMPOSITE_WITH_TRAINED_ALGORITHMIC_MEDIA.getUri(), false));
    }

    public final String component1() {
        return this.action;
    }

    public final String component10() {
        return this.activeManifest;
    }

    public final List<String> component11() {
        return this.ingredientsFile;
    }

    public final String component2() {
        return this.digitalSourceType;
    }

    public final String component3() {
        return this.softwareAgent;
    }

    public final Parameters component4() {
        return this.parameters;
    }

    public final String component5() {
        return this.actionTime;
    }

    public final String component6() {
        return this.issuer;
    }

    public final String component7() {
        return this.claimGenerator;
    }

    public final Boolean component8() {
        return this.isInvalid;
    }

    public final String component9() {
        return this.title;
    }

    public final Action copy(String str, String str2, String str3, Parameters parameters, String str4, String str5, String str6, Boolean bool, String str7, String str8, List<String> list) {
        return new Action(str, str2, str3, parameters, str4, str5, str6, bool, str7, str8, list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Action)) {
            return false;
        }
        Action action = (Action) obj;
        return Intrinsics.areEqual(this.action, action.action) && Intrinsics.areEqual(this.digitalSourceType, action.digitalSourceType) && Intrinsics.areEqual(this.softwareAgent, action.softwareAgent) && Intrinsics.areEqual(this.parameters, action.parameters) && Intrinsics.areEqual(this.actionTime, action.actionTime) && Intrinsics.areEqual(this.issuer, action.issuer) && Intrinsics.areEqual(this.claimGenerator, action.claimGenerator) && Intrinsics.areEqual(this.isInvalid, action.isInvalid) && Intrinsics.areEqual(this.title, action.title) && Intrinsics.areEqual(this.activeManifest, action.activeManifest) && Intrinsics.areEqual(this.ingredientsFile, action.ingredientsFile);
    }

    public final String getAction() {
        return this.action;
    }

    public final String getActionTime() {
        return this.actionTime;
    }

    public final String getActiveManifest() {
        return this.activeManifest;
    }

    public final List<String> getAuthorsList() {
        ArrayList arrayList;
        List<Author> author;
        Parameters parameters = this.parameters;
        if (parameters == null || (author = parameters.getAuthor()) == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList();
            Iterator<T> it = author.iterator();
            while (it.hasNext()) {
                String name = ((Author) it.next()).getName();
                if (name != null) {
                    arrayList.add(name);
                }
            }
        }
        return arrayList == null ? EmptyList.INSTANCE : arrayList;
    }

    public final String getClaimGenerator() {
        return this.claimGenerator;
    }

    public final String getDigitalSourceType() {
        return this.digitalSourceType;
    }

    public final List<String> getIngredientsFile() {
        return this.ingredientsFile;
    }

    public final String getIssuer() {
        return this.issuer;
    }

    public final Parameters getParameters() {
        return this.parameters;
    }

    public final String getSoftwareAgent() {
        return this.softwareAgent;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.action;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.digitalSourceType;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.softwareAgent;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Parameters parameters = this.parameters;
        int iHashCode4 = (iHashCode3 + (parameters == null ? 0 : parameters.hashCode())) * 31;
        String str4 = this.actionTime;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.issuer;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.claimGenerator;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        Boolean bool = this.isInvalid;
        int iHashCode8 = (iHashCode7 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str7 = this.title;
        int iHashCode9 = (iHashCode8 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.activeManifest;
        return this.ingredientsFile.hashCode() + ((iHashCode9 + (str8 != null ? str8.hashCode() : 0)) * 31);
    }

    public final boolean isAiGenerated() {
        String str;
        Ingredient ingredient;
        if (!hasAiSourceType(this.digitalSourceType)) {
            Parameters parameters = this.parameters;
            if (!hasAiSourceType((parameters == null || (ingredient = parameters.getIngredient()) == null) ? null : ingredient.getDigitalSourceType()) || (str = this.action) == null || !StringsKt__StringsKt.contains(str, C2paAction.C2PA_PLACED.getStr(), false)) {
                return false;
            }
        }
        return true;
    }

    public final boolean isEdited() {
        String str = this.action;
        if (str == null) {
            return false;
        }
        return StringsKt__StringsKt.contains(str, C2paAction.C2PA_EDITED.getStr(), false);
    }

    public final boolean isEnhanced() {
        String str = this.digitalSourceType;
        if (str == null) {
            return false;
        }
        return StringsKt__StringsKt.contains(str, DigitalSourceType.ALGORITHMICALLY_ENHANCED.getUri(), false);
    }

    public final Boolean isInvalid() {
        return this.isInvalid;
    }

    public final void setActionTime(String str) {
        this.actionTime = str;
    }

    public final void setActiveManifest(String str) {
        this.activeManifest = str;
    }

    public final void setClaimGenerator(String str) {
        this.claimGenerator = str;
    }

    public final void setIngredientsFile(List<String> list) {
        this.ingredientsFile = list;
    }

    public final void setInvalid(Boolean bool) {
        this.isInvalid = bool;
    }

    public final void setIssuer(String str) {
        this.issuer = str;
    }

    public final void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        return "Action(action=" + this.action + ", digitalSourceType=" + this.digitalSourceType + ", softwareAgent=" + this.softwareAgent + ", parameters=" + this.parameters + ", actionTime=" + this.actionTime + ", issuer=" + this.issuer + ", claimGenerator=" + this.claimGenerator + ", isInvalid=" + this.isInvalid + ", title=" + this.title + ", activeManifest=" + this.activeManifest + ", ingredientsFile=" + this.ingredientsFile + ')';
    }
}
