package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer;
import java.io.PrintWriter;
import java.util.List;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationIconAreaControllerViewBinderWrapperImpl implements NotificationIconAreaController {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void getUnsupported() {
            throw new IllegalStateException("Code path not supported when com.android.systemui.notifications_icon_container_refactor is disabled");
        }

        private Companion() {
        }
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void dump(PrintWriter printWriter) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final View getNotificationInnerAreaView() {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final int getShowingIconCount() {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void onDensityOrFontScaleChanged(Context context) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void onThemeChanged() {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setAnimationsEnabled(boolean z) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setIsolatedIconLocation(Rect rect, boolean z) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setKeyguardNotifIcon(NotificationIconContainer notificationIconContainer) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setKeyguardNotifIconTint(int i) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setShelfIcons(SecShelfNotificationIconContainer secShelfNotificationIconContainer) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setupAodIcons() {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void showIconIsolated(StatusBarIconView statusBarIconView, boolean z) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateAodNotificationIcons() {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateNotificationIcons(List list) {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateStatusBarIcons() {
        Companion.getClass();
        Companion.getUnsupported();
        throw null;
    }
}
