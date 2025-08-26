package com.android.systemui.keyguard;

import android.os.Handler;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.sec.ims.configuration.DATA;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ViewMediatorProvider viewMediatorProvider = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider.showMsg.invoke()).intValue());
            case 1:
                ViewMediatorProvider viewMediatorProvider2 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider2 == null) {
                    viewMediatorProvider2 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider2.hideMsg.invoke()).intValue());
            case 2:
                int i = 0;
                if (StringsKt__StringsKt.contains("24,10,30,48,60,80,120", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, false)) {
                    i = 80;
                } else if (StringsKt__StringsKt.contains("24,10,30,48,60,80,120", DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, false)) {
                    i = 96;
                }
                return Integer.valueOf(i);
            case 3:
                int i2 = 0;
                if (StringsKt__StringsKt.contains("", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, false)) {
                    i2 = 80;
                } else if (StringsKt__StringsKt.contains("", DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, false)) {
                    i2 = 96;
                }
                return Integer.valueOf(i2);
            case 4:
                ViewMediatorProvider viewMediatorProvider3 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider3 == null) {
                    viewMediatorProvider3 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider3.resetMsg.invoke()).intValue());
            case 5:
                ViewMediatorProvider viewMediatorProvider4 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider4 == null) {
                    viewMediatorProvider4 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider4.notifyFinishedGoingToSleepMsg.invoke()).intValue());
            case 6:
                ViewMediatorProvider viewMediatorProvider5 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider5 == null) {
                    viewMediatorProvider5 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider5.keyguardDoneMsg.invoke()).intValue());
            case 7:
                ViewMediatorProvider viewMediatorProvider6 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider6 == null) {
                    viewMediatorProvider6 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider6.keyguardDoneDrawingMsg.invoke()).intValue());
            case 8:
                ViewMediatorProvider viewMediatorProvider7 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider7 == null) {
                    viewMediatorProvider7 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider7.setOccludedMsg.invoke()).intValue());
            case 9:
                ViewMediatorProvider viewMediatorProvider8 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider8 == null) {
                    viewMediatorProvider8 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider8.keyguardDOnePendingTimeoutMsg.invoke()).intValue());
            case 10:
                ViewMediatorProvider viewMediatorProvider9 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider9 == null) {
                    viewMediatorProvider9 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider9.keyguardTimeoutMsg.invoke()).intValue());
            case 11:
                ViewMediatorProvider viewMediatorProvider10 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider10 == null) {
                    viewMediatorProvider10 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider10.dismissMsg.invoke()).intValue());
            case 12:
                ViewMediatorProvider viewMediatorProvider11 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider11 == null) {
                    viewMediatorProvider11 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider11.startKeyguardExitAnimMsg.invoke()).intValue());
            case 13:
                ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) this.f$0.shadeWindowControllerHelper$delegate.getValue())).setForceInvisible(true);
                return Unit.INSTANCE;
            case 14:
                ViewMediatorProvider viewMediatorProvider12 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider12 == null) {
                    viewMediatorProvider12 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider12.notifyStartedWakingUoMsg.invoke()).intValue());
            case 15:
                ViewMediatorProvider viewMediatorProvider13 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider13 == null) {
                    viewMediatorProvider13 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider13.notifyStartedGoingToSleepMsg.invoke()).intValue());
            case 16:
                ViewMediatorProvider viewMediatorProvider14 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider14 == null) {
                    viewMediatorProvider14 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider14.systemReadyMsg.invoke()).intValue());
            case 17:
                ViewMediatorProvider viewMediatorProvider15 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider15 == null) {
                    viewMediatorProvider15 = null;
                }
                return Integer.valueOf(((Number) viewMediatorProvider15.cancelKeyguardExitAnimMsg.invoke()).intValue());
            case 18:
                ViewMediatorProvider viewMediatorProvider16 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider16 == null) {
                    viewMediatorProvider16 = null;
                }
                return (KeyguardInteractor) viewMediatorProvider16.keyguardInteractor.invoke();
            case 19:
                ViewMediatorProvider viewMediatorProvider17 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider17 == null) {
                    viewMediatorProvider17 = null;
                }
                return (Handler) viewMediatorProvider17.handler.invoke();
            case 20:
                ViewMediatorProvider viewMediatorProvider18 = this.f$0.viewMediatorProvider;
                if (viewMediatorProvider18 == null) {
                    viewMediatorProvider18 = null;
                }
                return viewMediatorProvider18.lock.invoke();
            default:
                return ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) this.f$0.notificationShadeWindowControllerLazy.get())).mHelper;
        }
    }
}
