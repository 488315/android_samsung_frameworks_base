package com.android.systemui.statusbar.commandline;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class Flag implements Describable {
    public final String description;
    public boolean inner;
    public final String longName;
    public final String shortName;

    public Flag(String str, String str2, String str3) {
        this.shortName = str;
        this.longName = str2;
        this.description = str3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Flag)) {
            return false;
        }
        Flag flag = (Flag) obj;
        return Intrinsics.areEqual(this.shortName, flag.shortName) && Intrinsics.areEqual(this.longName, flag.longName) && Intrinsics.areEqual(this.description, flag.description);
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getLongName() {
        return this.longName;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getShortName() {
        return this.shortName;
    }

    public final int hashCode() {
        String str = this.shortName;
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((str == null ? 0 : str.hashCode()) * 31, 31, this.longName);
        String str2 = this.description;
        return m + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Flag(shortName=");
        sb.append(this.shortName);
        sb.append(", longName=");
        sb.append(this.longName);
        sb.append(", description=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.description, ")");
    }

    public /* synthetic */ Flag(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, str2, (i & 4) != 0 ? null : str3);
    }
}
