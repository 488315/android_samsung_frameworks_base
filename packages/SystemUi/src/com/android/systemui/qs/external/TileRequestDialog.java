package com.android.systemui.qs.external;

import android.content.Context;
import android.graphics.drawable.Icon;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TileRequestDialog extends SystemUIDialog {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TileData {
        public final CharSequence appName;
        public final Icon icon;
        public final CharSequence label;

        public TileData(CharSequence charSequence, CharSequence charSequence2, Icon icon) {
            this.appName = charSequence;
            this.label = charSequence2;
            this.icon = icon;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TileData)) {
                return false;
            }
            TileData tileData = (TileData) obj;
            return Intrinsics.areEqual(this.appName, tileData.appName) && Intrinsics.areEqual(this.label, tileData.label) && Intrinsics.areEqual(this.icon, tileData.icon);
        }

        public final int hashCode() {
            int hashCode = (this.label.hashCode() + (this.appName.hashCode() * 31)) * 31;
            Icon icon = this.icon;
            return hashCode + (icon == null ? 0 : icon.hashCode());
        }

        public final String toString() {
            return "TileData(appName=" + ((Object) this.appName) + ", label=" + ((Object) this.label) + ", icon=" + this.icon + ")";
        }
    }

    static {
        new Companion(null);
    }

    public TileRequestDialog(Context context) {
        super(context, 2132018528);
    }
}
