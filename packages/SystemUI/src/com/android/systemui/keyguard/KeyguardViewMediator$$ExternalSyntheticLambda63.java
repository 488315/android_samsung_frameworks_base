package com.android.systemui.keyguard;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Message;
import android.os.UserHandle;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.systemui.LsRune;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.util.LogUtil;
import java.util.function.Consumer;
import kotlin.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda63 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda63(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
        switch (i) {
            case 0:
                int intValue = ((Integer) obj).intValue();
                if (intValue == 103) {
                    keyguardViewMediatorHelperImpl.getClass();
                } else if (keyguardViewMediatorHelperImpl.curIsOccluded) {
                    ((ScrimController) keyguardViewMediatorHelperImpl.scrimControllerLazy.get()).mSecLsScrimControlHelper.setFrontScrimToBlack(false);
                }
                if (LsRune.AOD_FULLSCREEN) {
                    ((PluginAODManager) keyguardViewMediatorHelperImpl.pluginAODManagerLazy.get()).updateRefreshRate(false);
                }
                keyguardViewMediatorHelperImpl.enableLooperLogController(4, 3000L);
                keyguardViewMediatorHelperImpl.lastWakeReason = intValue;
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                    keyguardViewMediatorHelperImpl.foldControllerImpl.wakeReason = intValue;
                }
                KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 3, false, false, false, 1, intValue, 14);
                break;
            case 1:
                int intValue2 = ((Integer) obj).intValue();
                if (keyguardViewMediatorHelperImpl.extraUserPresentIntent == null) {
                    ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                    if (viewMediatorProvider == null) {
                        viewMediatorProvider = null;
                    }
                    keyguardViewMediatorHelperImpl.extraUserPresentIntent = (Intent) ((Intent) viewMediatorProvider.userPresentIntent.invoke()).clone();
                }
                Intent intent = keyguardViewMediatorHelperImpl.extraUserPresentIntent;
                if (intent != null) {
                    if (LsRune.KEYGUARD_EXTRA_USER_PRESENT) {
                        try {
                            PackageInfo packageInfo = keyguardViewMediatorHelperImpl.context.getPackageManager().getPackageInfo("com.verizon.mips.services", PackageManager.PackageInfoFlags.of(0L));
                            ApplicationInfo applicationInfo = packageInfo != null ? packageInfo.applicationInfo : null;
                            applicationInfo.getClass();
                            if ((applicationInfo.flags & 129) != 0) {
                                intent.setPackage("com.verizon.mips.services");
                                keyguardViewMediatorHelperImpl.context.sendBroadcastAsUser(intent, UserHandle.of(intValue2));
                            }
                        } catch (Exception e) {
                            Log.e("KeyguardViewMediator", "com.verizon.mips.services exception : " + e);
                        }
                    }
                    intent.setPackage("com.sec.android.daemonapp");
                    intent.addFlags(32);
                    keyguardViewMediatorHelperImpl.context.sendBroadcastAsUser(intent, UserHandle.of(intValue2));
                    intent.setPackage("com.samsung.android.privateaccesstokens");
                    keyguardViewMediatorHelperImpl.context.sendBroadcastAsUser(intent, UserHandle.of(intValue2));
                    break;
                }
                break;
            default:
                Message message = (Message) obj;
                int i2 = keyguardViewMediatorHelperImpl.handleMsgLogKey;
                if (i2 != -1) {
                    LogUtil.endTime(i2, new KeyguardViewMediatorHelperImpl$endHandleMsgTime$1(keyguardViewMediatorHelperImpl, message.what));
                    keyguardViewMediatorHelperImpl.handleMsgLogKey = -1;
                }
                int i3 = message.what;
                keyguardViewMediatorHelperImpl.handleMsgLogKey = LogUtil.startTime(-1);
                KeyguardViewMediatorHelperImpl.logD$1("handleMessage " + i3);
                int i4 = message.what;
                boolean z = true;
                Lazy lazy = keyguardViewMediatorHelperImpl.CANCEL_KEYGUARD_EXIT_ANIM$delegate;
                if (i4 != 1004) {
                    int show = keyguardViewMediatorHelperImpl.getSHOW();
                    KeyguardSysDumpTrigger keyguardSysDumpTrigger = keyguardViewMediatorHelperImpl.sysDumpTrigger;
                    if (i4 != show && i4 != ((Number) keyguardViewMediatorHelperImpl.NOTIFY_STARTED_GOING_TO_SLEEP$delegate.getValue()).intValue()) {
                        if (i4 == ((Number) lazy.getValue()).intValue() || i4 == ((Number) keyguardViewMediatorHelperImpl.START_KEYGUARD_EXIT_ANIM$delegate.getValue()).intValue() || i4 == keyguardViewMediatorHelperImpl.getSET_OCCLUDED()) {
                            keyguardSysDumpTrigger.cancel();
                            break;
                        }
                    } else {
                        dagger.Lazy lazy2 = keyguardViewMediatorHelperImpl.surfaceControllerLazy;
                        SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams = ((KeyguardSurfaceControllerImpl) lazy2.get()).lastKeyguardSurfaceParams;
                        if (surfaceParams != null) {
                            SurfaceControl surfaceControl = surfaceParams.surface;
                            z = true ^ (surfaceControl != null ? surfaceControl.isValid() : false);
                        }
                        if (!z) {
                            ((KeyguardSurfaceControllerImpl) lazy2.get()).restoreKeyguardSurface();
                        }
                        keyguardSysDumpTrigger.cancel();
                        break;
                    }
                } else if (keyguardViewMediatorHelperImpl.getHandler$1().hasMessages(((Number) lazy.getValue()).intValue())) {
                    keyguardViewMediatorHelperImpl.getHandler$1().removeMessages(((Number) lazy.getValue()).intValue());
                    if (keyguardViewMediatorHelperImpl.isSecure$2()) {
                        keyguardViewMediatorHelperImpl.updateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId());
                    }
                    keyguardViewMediatorHelperImpl.disableRemoteUnlockAnimation = true;
                    Log.d("KeyguardViewMediator", "cancel CANCEL_KEYGUARD_EXIT_ANIM");
                    break;
                }
                break;
        }
    }
}
