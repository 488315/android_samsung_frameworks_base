package com.android.systemui.qs.external;

import android.content.Context;
import android.graphics.drawable.Icon;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class TileRequestDialog extends SystemUIDialog {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class TileData {
        public final CharSequence appName;
        public final int callingUid;
        public final Icon icon;
        public final CharSequence label;
        public final String packageName;

        public TileData(int i, CharSequence charSequence, CharSequence charSequence2, Icon icon, String str) {
            this.callingUid = i;
            this.appName = charSequence;
            this.label = charSequence2;
            this.icon = icon;
            this.packageName = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TileData)) {
                return false;
            }
            TileData tileData = (TileData) obj;
            return this.callingUid == tileData.callingUid && Intrinsics.areEqual(this.appName, tileData.appName) && Intrinsics.areEqual(this.label, tileData.label) && Intrinsics.areEqual(this.icon, tileData.icon) && Intrinsics.areEqual(this.packageName, tileData.packageName);
        }

        public final int hashCode() {
            int m = ControlInfo$$ExternalSyntheticOutline0.m(ControlInfo$$ExternalSyntheticOutline0.m(Integer.hashCode(this.callingUid) * 31, 31, this.appName), 31, this.label);
            Icon icon = this.icon;
            return this.packageName.hashCode() + ((m + (icon == null ? 0 : icon.hashCode())) * 31);
        }

        public final String toString() {
            CharSequence charSequence = this.appName;
            CharSequence charSequence2 = this.label;
            Icon icon = this.icon;
            StringBuilder sb = new StringBuilder("TileData(callingUid=");
            sb.append(this.callingUid);
            sb.append(", appName=");
            sb.append((Object) charSequence);
            sb.append(", label=");
            sb.append((Object) charSequence2);
            sb.append(", icon=");
            sb.append(icon);
            sb.append(", packageName=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.packageName, ")");
        }
    }

    static {
        new Companion(null);
    }

    public TileRequestDialog(Context context) {
        super(context, R.style.Theme_SystemUI_Dialog_Alert);
    }
}
