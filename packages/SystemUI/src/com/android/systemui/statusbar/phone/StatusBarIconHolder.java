package com.android.systemui.statusbar.phone;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class StatusBarIconHolder {
    public static final Companion Companion = new Companion(null);
    public StatusBarIcon icon;
    public int tag;
    public int type;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class BindableIconHolder extends StatusBarIconHolder {
        public final ModernStatusBarViewCreator initializer;
        public boolean isVisible;
        public final String slot;
        public final int type;

        public BindableIconHolder(ModernStatusBarViewCreator modernStatusBarViewCreator, String str) {
            super(null);
            this.initializer = modernStatusBarViewCreator;
            this.slot = str;
            this.type = 5;
            this.isVisible = true;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconHolder
        public final int getType() {
            return this.type;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconHolder
        public final boolean isVisible() {
            return this.isVisible;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconHolder
        public final void setVisible(boolean z) {
            this.isVisible = z;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconHolder
        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("StatusBarIconHolder(type=BINDABLE, slot="), this.slot, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private StatusBarIconHolder() {
    }

    public int getType() {
        return this.type;
    }

    public boolean isVisible() {
        int type = getType();
        if (type != 0) {
            return (type == 3 || type != 4) ? true : true;
        }
        StatusBarIcon statusBarIcon = this.icon;
        Intrinsics.checkNotNull(statusBarIcon);
        return statusBarIcon.visible;
    }

    public void setVisible(boolean z) {
        if (isVisible() != z && getType() == 0) {
            StatusBarIcon statusBarIcon = this.icon;
            Intrinsics.checkNotNull(statusBarIcon);
            statusBarIcon.visible = z;
        }
    }

    public String toString() {
        int type = getType();
        Companion.getClass();
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(this.tag, "StatusBarIconHolder(type=", type != 0 ? type != 3 ? type != 4 ? "UNKNOWN" : "WIFI_NEW" : "MOBILE_NEW" : "ICON", " tag=", " visible="), isVisible(), ")");
    }

    public /* synthetic */ StatusBarIconHolder(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
