package com.android.systemui.statusbar.phone;

import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NavigationBarModel {
    public final boolean directReplying;
    public final boolean forceDarkForScrim;
    public final boolean hasLightNavigationBarFlag;
    public final boolean isLightOpaque;
    public final String logText;
    public final boolean navbarColorManagedByIme;
    public final String packageName;
    public final boolean panelHasWhiteBg;
    public final boolean qsCustomizing;

    public NavigationBarModel(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String str2) {
        this.logText = str;
        this.isLightOpaque = z;
        this.hasLightNavigationBarFlag = z2;
        this.directReplying = z3;
        this.navbarColorManagedByIme = z4;
        this.forceDarkForScrim = z5;
        this.qsCustomizing = z6;
        this.panelHasWhiteBg = z7;
        this.packageName = str2;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof NavigationBarModel) && Intrinsics.areEqual(((NavigationBarModel) obj).logText, this.logText)) {
            return true;
        }
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.logText.hashCode() * 31;
        boolean z = this.isLightOpaque;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.hasLightNavigationBarFlag;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.directReplying;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.navbarColorManagedByIme;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        boolean z5 = this.forceDarkForScrim;
        int i9 = z5;
        if (z5 != 0) {
            i9 = 1;
        }
        int i10 = (i8 + i9) * 31;
        boolean z6 = this.qsCustomizing;
        int i11 = z6;
        if (z6 != 0) {
            i11 = 1;
        }
        int i12 = (i10 + i11) * 31;
        boolean z7 = this.panelHasWhiteBg;
        return this.packageName.hashCode() + ((i12 + (z7 ? 1 : z7 ? 1 : 0)) * 31);
    }

    public final String toString() {
        String str = this.packageName;
        if (StringsKt__StringsKt.contains(str, "com.att", false)) {
            str = "";
        }
        return FragmentTransaction$$ExternalSyntheticOutline0.m38m(new StringBuilder("("), this.logText, ") ", "isLightOpaque:" + this.isLightOpaque + ", hasLightNavigationBarFlag:" + this.hasLightNavigationBarFlag + ", packageName:" + str + ", DirectReplying:" + this.directReplying + ", NavBarColorMangedByIme:" + this.navbarColorManagedByIme + ", ForceDarkForScrim:" + this.forceDarkForScrim + ", QsCustomizing:" + this.qsCustomizing + ", PanelHasWhiteBg:" + this.panelHasWhiteBg);
    }
}
