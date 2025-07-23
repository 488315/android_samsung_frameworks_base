package com.android.systemui.statusbar.phone;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarIconHolder {
    public static final Companion Companion = new Companion(null);
    public StatusBarIcon icon;
    public int tag;
    public int type;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("StatusBarIconHolder(type=BINDABLE, slot="), this.slot, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ StatusBarIconHolder(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public int getType() {
        return this.type;
    }

    public boolean isVisible() {
        if (getType() != 0) {
            return true;
        }
        StatusBarIcon statusBarIcon = this.icon;
        statusBarIcon.getClass();
        return statusBarIcon.visible;
    }

    public void setVisible(boolean z) {
        if (isVisible() != z && getType() == 0) {
            StatusBarIcon statusBarIcon = this.icon;
            statusBarIcon.getClass();
            statusBarIcon.visible = z;
        }
    }

    public String toString() {
        int type = getType();
        Companion.getClass();
        return MoveResult$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(this.tag, "StatusBarIconHolder(type=", type != 0 ? type != 3 ? type != 4 ? "UNKNOWN" : "WIFI_NEW" : "MOBILE_NEW" : "ICON", " tag=", " visible="), isVisible(), ")");
    }

    private StatusBarIconHolder() {
    }
}
