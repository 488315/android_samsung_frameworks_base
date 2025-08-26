package com.android.systemui.coverlauncher.utils;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class CoverLauncherWidgetOptions {
    public String appIconPkgOption;
    public int uiModeOption;
    public boolean visibleOption;

    public CoverLauncherWidgetOptions() {
        this(false, null, 0, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoverLauncherWidgetOptions)) {
            return false;
        }
        CoverLauncherWidgetOptions coverLauncherWidgetOptions = (CoverLauncherWidgetOptions) obj;
        return this.visibleOption == coverLauncherWidgetOptions.visibleOption && Intrinsics.areEqual(this.appIconPkgOption, coverLauncherWidgetOptions.appIconPkgOption) && this.uiModeOption == coverLauncherWidgetOptions.uiModeOption;
    }

    public final int hashCode() {
        int iHashCode = Boolean.hashCode(this.visibleOption) * 31;
        String str = this.appIconPkgOption;
        return Integer.hashCode(this.uiModeOption) + ((iHashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        boolean z = this.visibleOption;
        String str = this.appIconPkgOption;
        int i = this.uiModeOption;
        StringBuilder sb = new StringBuilder("CoverLauncherWidgetOptions(visibleOption=");
        sb.append(z);
        sb.append(", appIconPkgOption=");
        sb.append(str);
        sb.append(", uiModeOption=");
        return ReorderTile$$ExternalSyntheticOutline0.m(i, ")", sb);
    }

    public CoverLauncherWidgetOptions(boolean z, String str, int i) {
        this.visibleOption = z;
        this.appIconPkgOption = str;
        this.uiModeOption = i;
    }

    public /* synthetic */ CoverLauncherWidgetOptions(boolean z, String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? 0 : i);
    }
}
