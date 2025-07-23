package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.PendingIntent;
import android.content.res.Resources;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingCallControllerExt {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public boolean blockClickListener;
    public final LogBuffer logger;
    public final SystemClock systemClock;

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

    public OngoingCallControllerExt(Resources resources, ActivityStarter activityStarter, LogBuffer logBuffer, KeyguardCallChipController keyguardCallChipController, SystemClock systemClock, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        this.activityStarter = activityStarter;
        this.logger = logBuffer;
        this.systemClock = systemClock;
    }

    public static final void access$handleClickCallChip(OngoingCallControllerExt ongoingCallControllerExt, PendingIntent pendingIntent, OngoingCallBackgroundContainer ongoingCallBackgroundContainer) {
        if (ongoingCallControllerExt.blockClickListener) {
            Log.d("OngoingCallControllerExt", "handleClickCallChip but handleClickCallChip is true");
            return;
        }
        LogLevel logLevel = LogLevel.DEBUG;
        OngoingCallControllerExt$$ExternalSyntheticLambda0 ongoingCallControllerExt$$ExternalSyntheticLambda0 = new OngoingCallControllerExt$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = ongoingCallControllerExt.logger;
        logBuffer.commit(logBuffer.obtain("OngoingCallControllerExt", logLevel, ongoingCallControllerExt$$ExternalSyntheticLambda0, null));
        try {
            Log.d("OngoingCallControllerExt", "handleClickCallChip() The ongoing call chip was clicked");
            ongoingCallControllerExt.activityStarter.postStartActivityDismissingKeyguard(pendingIntent, ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, ongoingCallBackgroundContainer, 34, 60));
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("handleClickCallChip() ERROR: ", e, "OngoingCallControllerExt");
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_CALL_CHIP_OPEN);
    }
}
