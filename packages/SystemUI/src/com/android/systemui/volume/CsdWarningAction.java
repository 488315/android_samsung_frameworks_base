package com.android.systemui.volume;

import android.content.Intent;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class CsdWarningAction {
    public final Intent intent;
    public final boolean isActivity;
    public final String label;

    public CsdWarningAction() {
        this(null, null, false, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CsdWarningAction)) {
            return false;
        }
        CsdWarningAction csdWarningAction = (CsdWarningAction) obj;
        return Intrinsics.areEqual(this.label, csdWarningAction.label) && Intrinsics.areEqual(this.intent, csdWarningAction.intent) && this.isActivity == csdWarningAction.isActivity;
    }

    public final int hashCode() {
        String str = this.label;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Intent intent = this.intent;
        return Boolean.hashCode(this.isActivity) + ((iHashCode + (intent != null ? intent.hashCode() : 0)) * 31);
    }

    public final String toString() {
        Intent intent = this.intent;
        StringBuilder sb = new StringBuilder("CsdWarningAction(label=");
        sb.append(this.label);
        sb.append(", intent=");
        sb.append(intent);
        sb.append(", isActivity=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isActivity, ")");
    }

    public CsdWarningAction(String str, Intent intent, boolean z) {
        this.label = str;
        this.intent = intent;
        this.isActivity = z;
    }

    public /* synthetic */ CsdWarningAction(String str, Intent intent, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : intent, (i & 4) != 0 ? false : z);
    }
}
