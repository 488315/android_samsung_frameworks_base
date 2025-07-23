package com.android.systemui.statusbar.pipeline.mobile.ui.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileContentDescription$Cellular {
    public final int levelDescriptionRes;
    public final String networkName;

    public MobileContentDescription$Cellular(String str, int i) {
        this.networkName = str;
        this.levelDescriptionRes = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileContentDescription$Cellular)) {
            return false;
        }
        MobileContentDescription$Cellular mobileContentDescription$Cellular = (MobileContentDescription$Cellular) obj;
        return Intrinsics.areEqual(this.networkName, mobileContentDescription$Cellular.networkName) && this.levelDescriptionRes == mobileContentDescription$Cellular.levelDescriptionRes;
    }

    public final int hashCode() {
        return Integer.hashCode(this.levelDescriptionRes) + (this.networkName.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Cellular(networkName=");
        sb.append(this.networkName);
        sb.append(", levelDescriptionRes=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.levelDescriptionRes, ")", sb);
    }
}
