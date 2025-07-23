package com.android.systemui.shade;

import android.util.Log;
import com.android.systemui.statusbar.phone.IndicatorTouchHandler;
import com.android.systemui.util.DeviceType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarNotificationPanelViewControllerExt {
    public final boolean debug = DeviceType.isEngOrUTBinary();
    public final IndicatorTouchHandler indicatorTouchHandler;
    public final NotificationPanelViewController npvController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public StatusBarNotificationPanelViewControllerExt(NotificationPanelViewController notificationPanelViewController, IndicatorTouchHandler indicatorTouchHandler) {
        this.npvController = notificationPanelViewController;
        this.indicatorTouchHandler = indicatorTouchHandler;
        printLog("onInit()");
    }

    public final void printLog(String str) {
        if (this.debug) {
            Log.d("StatusBarNotificationPanelViewControllerExt", StringsKt__StringsKt.padEnd(20, str) + " npv:" + this.npvController.mView);
        }
    }
}
