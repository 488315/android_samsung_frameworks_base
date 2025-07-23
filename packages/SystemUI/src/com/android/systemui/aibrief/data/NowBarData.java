package com.android.systemui.aibrief.data;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NowBarData {
    public static final int $stable = 8;
    private final ArrayList<Integer> background;
    private final ArrayList<Integer> backgroundForDark;
    private final String fullBackground;
    private final String fullBackgroundForDark;
    private final byte[] icon;
    private final String subTitle;
    private final String title;

    public NowBarData(byte[] bArr, String str, String str2, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, String str3, String str4) {
        this.icon = bArr;
        this.title = str;
        this.subTitle = str2;
        this.background = arrayList;
        this.backgroundForDark = arrayList2;
        this.fullBackground = str3;
        this.fullBackgroundForDark = str4;
    }

    public static /* synthetic */ NowBarData copy$default(NowBarData nowBarData, byte[] bArr, String str, String str2, ArrayList arrayList, ArrayList arrayList2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            bArr = nowBarData.icon;
        }
        if ((i & 2) != 0) {
            str = nowBarData.title;
        }
        if ((i & 4) != 0) {
            str2 = nowBarData.subTitle;
        }
        if ((i & 8) != 0) {
            arrayList = nowBarData.background;
        }
        if ((i & 16) != 0) {
            arrayList2 = nowBarData.backgroundForDark;
        }
        if ((i & 32) != 0) {
            str3 = nowBarData.fullBackground;
        }
        if ((i & 64) != 0) {
            str4 = nowBarData.fullBackgroundForDark;
        }
        String str5 = str3;
        String str6 = str4;
        ArrayList arrayList3 = arrayList2;
        String str7 = str2;
        return nowBarData.copy(bArr, str, str7, arrayList, arrayList3, str5, str6);
    }

    public final byte[] component1() {
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
        return this.fullBackground;
    }

    public final String component7() {
        return this.fullBackgroundForDark;
    }

    public final NowBarData copy(byte[] bArr, String str, String str2, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, String str3, String str4) {
        return new NowBarData(bArr, str, str2, arrayList, arrayList2, str3, str4);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NowBarData)) {
            return false;
        }
        NowBarData nowBarData = (NowBarData) obj;
        return Intrinsics.areEqual(this.icon, nowBarData.icon) && Intrinsics.areEqual(this.title, nowBarData.title) && Intrinsics.areEqual(this.subTitle, nowBarData.subTitle) && Intrinsics.areEqual(this.background, nowBarData.background) && Intrinsics.areEqual(this.backgroundForDark, nowBarData.backgroundForDark) && Intrinsics.areEqual(this.fullBackground, nowBarData.fullBackground) && Intrinsics.areEqual(this.fullBackgroundForDark, nowBarData.fullBackgroundForDark);
    }

    public final ArrayList<Integer> getBackground() {
        return this.background;
    }

    public final ArrayList<Integer> getBackgroundForDark() {
        return this.backgroundForDark;
    }

    public final String getFullBackground() {
        return this.fullBackground;
    }

    public final String getFullBackgroundForDark() {
        return this.fullBackgroundForDark;
    }

    public final byte[] getIcon() {
        return this.icon;
    }

    public final String getSubTitle() {
        return this.subTitle;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        byte[] bArr = this.icon;
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((bArr == null ? 0 : Arrays.hashCode(bArr)) * 31, 31, this.title);
        String str = this.subTitle;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        ArrayList<Integer> arrayList = this.background;
        int hashCode2 = (hashCode + (arrayList == null ? 0 : arrayList.hashCode())) * 31;
        ArrayList<Integer> arrayList2 = this.backgroundForDark;
        int hashCode3 = (hashCode2 + (arrayList2 == null ? 0 : arrayList2.hashCode())) * 31;
        String str2 = this.fullBackground;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.fullBackgroundForDark;
        return hashCode4 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        String arrays = Arrays.toString(this.icon);
        String str = this.title;
        String str2 = this.subTitle;
        ArrayList<Integer> arrayList = this.background;
        ArrayList<Integer> arrayList2 = this.backgroundForDark;
        String str3 = this.fullBackground;
        String str4 = this.fullBackgroundForDark;
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("NowBarData(icon=", arrays, ", title=", str, ", subTitle=");
        m.append(str2);
        m.append(", background=");
        m.append(arrayList);
        m.append(", backgroundForDark=");
        m.append(arrayList2);
        m.append(", fullBackground=");
        m.append(str3);
        m.append(", fullBackgroundForDark=");
        return TransitionKt$$ExternalSyntheticOutline0.m(m, str4, ")");
    }

    public /* synthetic */ NowBarData(byte[] bArr, String str, String str2, ArrayList arrayList, ArrayList arrayList2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : bArr, str, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : arrayList, (i & 16) != 0 ? null : arrayList2, (i & 32) != 0 ? null : str3, (i & 64) != 0 ? null : str4);
    }
}
