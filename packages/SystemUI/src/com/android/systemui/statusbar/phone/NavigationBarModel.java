package com.android.systemui.statusbar.phone;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NavigationBarModel {
    public final boolean directReplying;
    public final boolean forceDarkForScrim;
    public final boolean forceLightForScrim;
    public final boolean hasLightNavigationBarFlag;
    public final boolean isLightOpaque;
    public final String logText;
    public final boolean navbarColorManagedByIme;
    public final String packageName;
    public final boolean qsCustomizing;
    public final boolean qsExpanded;

    public NavigationBarModel(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String str2) {
        this.logText = str;
        this.isLightOpaque = z;
        this.hasLightNavigationBarFlag = z2;
        this.directReplying = z3;
        this.navbarColorManagedByIme = z4;
        this.forceDarkForScrim = z5;
        this.forceLightForScrim = z6;
        this.qsCustomizing = z7;
        this.qsExpanded = z8;
        this.packageName = str2;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof NavigationBarModel) && Intrinsics.areEqual(((NavigationBarModel) obj).logText, this.logText)) {
            return true;
        }
        return super.equals(obj);
    }

    public final int hashCode() {
        return this.packageName.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.logText.hashCode() * 31, 31, this.isLightOpaque), 31, this.hasLightNavigationBarFlag), 31, this.directReplying), 31, this.navbarColorManagedByIme), 31, this.forceDarkForScrim), 31, this.forceLightForScrim), 31, this.qsCustomizing), 31, this.qsExpanded);
    }

    public final String toString() {
        String str = this.packageName;
        if (StringsKt__StringsKt.contains(str, "com.att", false)) {
            str = "";
        }
        return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(new StringBuilder("("), this.logText, ") ", "isLightOpaque:" + this.isLightOpaque + ", hasLightNavigationBarFlag:" + this.hasLightNavigationBarFlag + ", packageName:" + str + ", DirectReplying:" + this.directReplying + ", NavBarColorMangedByIme:" + this.navbarColorManagedByIme + ", ForceDarkForScrim:" + this.forceDarkForScrim + ", ForceLightForScrim:" + this.forceLightForScrim + ", QsCustomizing:" + this.qsCustomizing + ", QsExpanded:" + this.qsExpanded);
    }
}
