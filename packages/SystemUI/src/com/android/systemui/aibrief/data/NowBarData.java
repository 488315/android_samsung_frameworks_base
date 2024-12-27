package com.android.systemui.aibrief.data;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class NowBarData {
    public static final int $stable = 8;
    private final ArrayList<Integer> background;
    private final ArrayList<Integer> backgroundForDark;
    private final String icon;
    private final String iconBackground;
    private final String iconBackgroundForDark;
    private final String subTitle;
    private final String title;

    public NowBarData(String str, String str2, String str3, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, String str4, String str5) {
        this.icon = str;
        this.title = str2;
        this.subTitle = str3;
        this.background = arrayList;
        this.backgroundForDark = arrayList2;
        this.iconBackground = str4;
        this.iconBackgroundForDark = str5;
    }

    public static /* synthetic */ NowBarData copy$default(NowBarData nowBarData, String str, String str2, String str3, ArrayList arrayList, ArrayList arrayList2, String str4, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = nowBarData.icon;
        }
        if ((i & 2) != 0) {
            str2 = nowBarData.title;
        }
        String str6 = str2;
        if ((i & 4) != 0) {
            str3 = nowBarData.subTitle;
        }
        String str7 = str3;
        if ((i & 8) != 0) {
            arrayList = nowBarData.background;
        }
        ArrayList arrayList3 = arrayList;
        if ((i & 16) != 0) {
            arrayList2 = nowBarData.backgroundForDark;
        }
        ArrayList arrayList4 = arrayList2;
        if ((i & 32) != 0) {
            str4 = nowBarData.iconBackground;
        }
        String str8 = str4;
        if ((i & 64) != 0) {
            str5 = nowBarData.iconBackgroundForDark;
        }
        return nowBarData.copy(str, str6, str7, arrayList3, arrayList4, str8, str5);
    }

    public final String component1() {
        return this.icon;
    }

    public final String component2() {
        return this.title;
    }

    public final String component3() {
        return this.subTitle;
    }

    public final ArrayList<Integer> component4() {
        return this.background;
    }

    public final ArrayList<Integer> component5() {
        return this.backgroundForDark;
    }

    public final String component6() {
        return this.iconBackground;
    }

    public final String component7() {
        return this.iconBackgroundForDark;
    }

    public final NowBarData copy(String str, String str2, String str3, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, String str4, String str5) {
        return new NowBarData(str, str2, str3, arrayList, arrayList2, str4, str5);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NowBarData)) {
            return false;
        }
        NowBarData nowBarData = (NowBarData) obj;
        return Intrinsics.areEqual(this.icon, nowBarData.icon) && Intrinsics.areEqual(this.title, nowBarData.title) && Intrinsics.areEqual(this.subTitle, nowBarData.subTitle) && Intrinsics.areEqual(this.background, nowBarData.background) && Intrinsics.areEqual(this.backgroundForDark, nowBarData.backgroundForDark) && Intrinsics.areEqual(this.iconBackground, nowBarData.iconBackground) && Intrinsics.areEqual(this.iconBackgroundForDark, nowBarData.iconBackgroundForDark);
    }

    public final ArrayList<Integer> getBackground() {
        return this.background;
    }

    public final ArrayList<Integer> getBackgroundForDark() {
        return this.backgroundForDark;
    }

    public final String getIcon() {
        return this.icon;
    }

    public final String getIconBackground() {
        return this.iconBackground;
    }

    public final String getIconBackgroundForDark() {
        return this.iconBackgroundForDark;
    }

    public final String getSubTitle() {
        return this.subTitle;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.icon;
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((str == null ? 0 : str.hashCode()) * 31, 31, this.title);
        String str2 = this.subTitle;
        int hashCode = (m + (str2 == null ? 0 : str2.hashCode())) * 31;
        ArrayList<Integer> arrayList = this.background;
        int hashCode2 = (hashCode + (arrayList == null ? 0 : arrayList.hashCode())) * 31;
        ArrayList<Integer> arrayList2 = this.backgroundForDark;
        int hashCode3 = (hashCode2 + (arrayList2 == null ? 0 : arrayList2.hashCode())) * 31;
        String str3 = this.iconBackground;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.iconBackgroundForDark;
        return hashCode4 + (str4 != null ? str4.hashCode() : 0);
    }

    public String toString() {
        String str = this.icon;
        String str2 = this.title;
        String str3 = this.subTitle;
        ArrayList<Integer> arrayList = this.background;
        ArrayList<Integer> arrayList2 = this.backgroundForDark;
        String str4 = this.iconBackground;
        String str5 = this.iconBackgroundForDark;
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("NowBarData(icon=", str, ", title=", str2, ", subTitle=");
        m.append(str3);
        m.append(", background=");
        m.append(arrayList);
        m.append(", backgroundForDark=");
        m.append(arrayList2);
        m.append(", iconBackground=");
        m.append(str4);
        m.append(", iconBackgroundForDark=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(m, str5, ")");
    }

    public /* synthetic */ NowBarData(String str, String str2, String str3, ArrayList arrayList, ArrayList arrayList2, String str4, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : arrayList, (i & 16) != 0 ? null : arrayList2, (i & 32) != 0 ? null : str4, (i & 64) != 0 ? null : str5);
    }
}
