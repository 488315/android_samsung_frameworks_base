package com.android.systemui.statusbar.phone;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StatusBarIconHolder {
    public static final Companion Companion = new Companion(null);
    public StatusBarIcon icon;
    public int tag;
    public int type;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BindableIconHolder extends StatusBarIconHolder {
        public final ModernStatusBarViewCreator initializer;
        public boolean isVisible;
        public int type;

        public BindableIconHolder(ModernStatusBarViewCreator modernStatusBarViewCreator) {
            super(null);
            this.initializer = modernStatusBarViewCreator;
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
            return "StatusBarIconHolder(type=BINDABLE)";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private StatusBarIconHolder() {
    }

    public /* synthetic */ StatusBarIconHolder(DefaultConstructorMarker defaultConstructorMarker) {
        this();
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(AbstractC0950x8906c950.m92m("StatusBarIconHolder(type=", type != 0 ? type != 3 ? type != 4 ? "UNKNOWN" : "WIFI_NEW" : "MOBILE_NEW" : "ICON", " tag=", this.tag, " visible="), isVisible(), ")");
    }
}
