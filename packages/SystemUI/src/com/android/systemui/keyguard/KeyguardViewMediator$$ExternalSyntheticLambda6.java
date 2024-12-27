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
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.util.LogUtil;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.jvm.internal.Intrinsics;

public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda6(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
        switch (i) {
            case 0:
                int intValue = ((Integer) obj).intValue();
                keyguardViewMediatorHelperImpl.enableLooperLogController(5, 3000L);
                keyguardViewMediatorHelperImpl.lastSleepReason = intValue;
                if (LsRune.AOD_FULLSCREEN && keyguardViewMediatorHelperImpl.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                    keyguardViewMediatorHelperImpl.getHandler$1().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onStartedGoingToSleep$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean z;
                            SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = KeyguardViewMediatorHelperImpl.this.unlockedScreenOffAnimationHelper;
                            if (secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mState == 0) {
                                KeyguardVisibilityMonitor keyguardVisibilityMonitor = secUnlockedScreenOffAnimationHelper.keyguardVisibilityMonitor;
                                if (keyguardVisibilityMonitor.isVisible() && keyguardVisibilityMonitor.panelState == 2) {
                                    z = true;
                                    secUnlockedScreenOffAnimationHelper.isPanelOpenedOnGoingToSleep = z;
                                }
                            }
                            z = false;
                            secUnlockedScreenOffAnimationHelper.isPanelOpenedOnGoingToSleep = z;
                        }
                    });
                    break;
                }
                break;
            case 1:
                int intValue2 = ((Integer) obj).intValue();
                if (intValue2 == 103) {
                    keyguardViewMediatorHelperImpl.getClass();
                } else if (keyguardViewMediatorHelperImpl.curIsOccluded) {
                    ((ScrimController) keyguardViewMediatorHelperImpl.scrimControllerLazy.get()).mSecLsScrimControlHelper.setFrontScrimToBlack(false);
                }
                if (LsRune.AOD_FULLSCREEN) {
                    ((PluginAODManager) keyguardViewMediatorHelperImpl.pluginAODManagerLazy.get()).updateRefreshRate(0);
                }
                keyguardViewMediatorHelperImpl.enableLooperLogController(4, 3000L);
                keyguardViewMediatorHelperImpl.lastWakeReason = intValue2;
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                    keyguardViewMediatorHelperImpl.foldControllerImpl.wakeReason = intValue2;
                }
                KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 3, false, false, false, 1, intValue2, 14);
                break;
            case 2:
                int intValue3 = ((Integer) obj).intValue();
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
                            Intrinsics.checkNotNull(applicationInfo);
                            if ((applicationInfo.flags & 129) != 0) {
                                intent.setPackage("com.verizon.mips.services");
                                keyguardViewMediatorHelperImpl.context.sendBroadcastAsUser(intent, UserHandle.of(intValue3));
                            }
                        } catch (Exception e) {
                            Log.e("KeyguardViewMediator", "com.verizon.mips.services exception : " + e);
                        }
                    }
                    intent.setPackage("com.sec.android.daemonapp");
                    intent.addFlags(32);
                    keyguardViewMediatorHelperImpl.context.sendBroadcastAsUser(intent, UserHandle.of(intValue3));
                    intent.setPackage("com.samsung.android.privateaccesstokens");
                    keyguardViewMediatorHelperImpl.context.sendBroadcastAsUser(intent, UserHandle.of(intValue3));
                    break;
                }
                break;
            case 3:
                Message message = (Message) obj;
                int i2 = keyguardViewMediatorHelperImpl.handleMsgLogKey;
                if (i2 != -1) {
                    LogUtil.endTime(i2, new KeyguardViewMediatorHelperImpl$endHandleMsgTime$1(keyguardViewMediatorHelperImpl, message.what));
                    keyguardViewMediatorHelperImpl.handleMsgLogKey = -1;
                }
                int i3 = message.what;
                keyguardViewMediatorHelperImpl.handleMsgLogKey = LogUtil.startTime(-1);
                KeyguardViewMediatorHelperImpl.logD("handleMessage " + i3);
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
                    KeyguardViewMediatorHelperImpl.logD("cancel CANCEL_KEYGUARD_EXIT_ANIM");
                    break;
                }
                break;
            case 4:
                keyguardViewMediatorHelperImpl.removeMessage(((Integer) obj).intValue());
                break;
            case 5:
                keyguardViewMediatorHelperImpl.switchingUserId = ((Integer) obj).intValue();
                break;
            default:
                ((Integer) obj).intValue();
                keyguardViewMediatorHelperImpl.switchingUserId = -1;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = (SecNotificationShadeWindowControllerHelperImpl) keyguardViewMediatorHelperImpl.getShadeWindowControllerHelper();
                secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation = secNotificationShadeWindowControllerHelperImpl.isLockScreenRotationAllowed();
                break;
        }
    }
}
