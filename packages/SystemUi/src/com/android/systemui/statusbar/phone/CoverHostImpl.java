package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.basic.util.CoverUtilWrapper$$ExternalSyntheticLambda0;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.PluginLockManagerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.CoverUtil;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import dagger.Lazy;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CoverHostImpl implements CoverHost {
    public final CentralSurfaces mCentralSurfaces;
    public final Context mContext;
    public CoverState mCoverState;
    public final CoverUtil mCoverUtil;
    public final IndicatorCoverManager mIndicatorCoverManager;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final ViewMediatorCallback mKeyguardViewMediatorCallback;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final Lazy mPluginAODManagerLazy;
    public final PluginLockManager mPluginLockManager;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarWindowController mStatusBarWindowController;
    public final SysuiStatusBarStateController mSysuiStatusBarStateController;
    public boolean mIsCoverClosed = false;
    public boolean mIsCoverAppCovered = false;

    public CoverHostImpl(Context context, CentralSurfaces centralSurfaces, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewMediator keyguardViewMediator, ViewMediatorCallback viewMediatorCallback, NotificationShadeWindowController notificationShadeWindowController, IndicatorCoverManager indicatorCoverManager, StatusBarWindowController statusBarWindowController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardStateController keyguardStateController, CoverUtil coverUtil, Lazy lazy, PluginLockManager pluginLockManager) {
        this.mContext = context;
        this.mCentralSurfaces = centralSurfaces;
        this.mSysuiStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardViewMediatorCallback = viewMediatorCallback;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mIndicatorCoverManager = indicatorCoverManager;
        this.mStatusBarWindowController = statusBarWindowController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mCoverUtil = coverUtil;
        this.mPluginAODManagerLazy = lazy;
        this.mPluginLockManager = pluginLockManager;
    }

    public final boolean isAutomaticUnlock(CoverState coverState) {
        if (coverState == null || !coverState.getAttachState() || !((SettingsHelper) Dependency.get(SettingsHelper.class)).isAutomaticUnlockEnabled()) {
            return false;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        return !keyguardUpdateMonitor.isSecure() || keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser());
    }

    public final boolean isNeedScrimAnimation() {
        if (LsRune.COVER_VIRTUAL_DISPLAY && this.mIsCoverClosed) {
            CoverState coverState = this.mCoverState;
            if (17 == (coverState == null ? 2 : coverState.type)) {
                Log.d("CoverHostImpl", "isNeedScrimAnimation false");
                return false;
            }
        }
        return true;
    }

    public final void updateCoverState(final CoverState coverState) {
        if (coverState == null) {
            return;
        }
        this.mCoverState = coverState;
        if (LsRune.COVER_SAFEMODE) {
            return;
        }
        boolean z = !coverState.switchState;
        int i = coverState.type;
        LogUtil.m223d("CoverHostImpl", "updateCoverState: attach = %s, cover closed = %s, type = %d", Boolean.valueOf(coverState.getAttachState()), Boolean.valueOf(z), Integer.valueOf(i));
        this.mKeyguardUpdateMonitor.dispatchCoverState(this.mCoverState);
        boolean z2 = LsRune.WALLPAPER_VIRTUAL_DISPLAY;
        if (z2) {
            int type = this.mCoverState.getType();
            Point point = DeviceState.sDisplaySize;
            if (type == 17) {
                PluginLockManager pluginLockManager = this.mPluginLockManager;
                CoverState coverState2 = this.mCoverState;
                PluginLockManagerImpl pluginLockManagerImpl = (PluginLockManagerImpl) pluginLockManager;
                pluginLockManagerImpl.getClass();
                Log.d("PluginLockManagerImpl", "onCoverStateChanged " + coverState2);
                if (z2) {
                    if ((coverState2.getType() == 17) && pluginLockManagerImpl.mCoverState != coverState2) {
                        pluginLockManagerImpl.mCoverState = coverState2;
                        pluginLockManagerImpl.onFolderStateChanged(coverState2.switchState);
                    }
                }
            }
        }
        if (!coverState.getAttachState()) {
            this.mIsCoverClosed = false;
            this.mIsCoverAppCovered = false;
            this.mStatusBarKeyguardViewManager.onCoverSwitchStateChanged(false);
            if (DeviceState.isCoverUIType(i)) {
                updateCoverWindow();
            }
        } else if (this.mIsCoverClosed != z) {
            this.mIsCoverClosed = z;
            this.mStatusBarKeyguardViewManager.onCoverSwitchStateChanged(z);
            if (DeviceState.isCoverUIType(i)) {
                if (DeviceState.isCoverUIType(this.mCoverState.type)) {
                    if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode()) {
                        Log.d("CoverHostImpl", "Don't need to update doKeyguardLaterLocked by desktopMode");
                    } else if (!this.mStatusBarKeyguardViewManager.canHandleBackPressed() && !isAutomaticUnlock(this.mCoverState)) {
                        if (this.mCoverState.switchState) {
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mKeyguardViewMediator.mHelper;
                            synchronized (keyguardViewMediatorHelperImpl.getLock()) {
                                keyguardViewMediatorHelperImpl.cancelLockWhenCoverIsOpened(true);
                                Unit unit = Unit.INSTANCE;
                            }
                        } else if (!this.mKeyguardUpdateMonitor.isKeyguardVisible()) {
                            this.mKeyguardViewMediator.mHelper.getViewMediatorProvider().doKeyguardLaterLocked.invoke();
                        }
                    }
                }
                updateCoverWindow();
            }
            if (!this.mIsCoverClosed) {
                if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && isAutomaticUnlock(this.mCoverState)) {
                    KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_COVER_OPENED);
                    KeyguardUnlockInfo.setAuthDetail(KeyguardSecurityModel.SecurityMode.None);
                    this.mKeyguardViewMediatorCallback.keyguardDone(KeyguardUpdateMonitor.getCurrentUser());
                    Log.d("CoverHostImpl", "updateCoverWindow: keyguard dismissed by cover");
                }
                if (((StatusBarStateControllerImpl) this.mSysuiStatusBarStateController).mState == 0) {
                    CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
                    centralSurfacesImpl.mNotificationPanelViewController.instantCollapse();
                    ((ShadeControllerImpl) centralSurfacesImpl.mShadeController).runPostCollapseRunnables();
                }
            }
        }
        CoverUtil coverUtil = this.mCoverUtil;
        coverUtil.mCoverState = coverState;
        coverUtil.mCoverStateChangedListeners.forEach(new Consumer() { // from class: com.android.systemui.util.CoverUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Runnable runnable;
                CoverState coverState3 = coverState;
                CoverUtilWrapper coverUtilWrapper = ((CoverUtilWrapper$$ExternalSyntheticLambda0) obj).f$0;
                HashMap hashMap = (HashMap) coverUtilWrapper.mListeners;
                if (hashMap.isEmpty()) {
                    return;
                }
                coverUtilWrapper.mCoverState = coverState3;
                boolean z3 = true;
                final boolean z4 = !coverState3.getSwitchState();
                final int type2 = coverState3.getType();
                if (z4 || (coverUtilWrapper.mCoverState.getType() != 15 && coverUtilWrapper.mCoverState.getType() != 16 && coverUtilWrapper.mCoverState.getType() != 8)) {
                    z3 = false;
                }
                if (z3 && (runnable = coverUtilWrapper.mActionBeforeSecureConfirm) != null) {
                    runnable.run();
                    coverUtilWrapper.mActionBeforeSecureConfirm = null;
                }
                hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.basic.util.CoverUtilWrapper$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        ((BiConsumer) obj3).accept(Boolean.valueOf(z4), Integer.valueOf(type2));
                    }
                });
            }
        });
    }

    public final void updateCoverWindow() {
        boolean z;
        Log.d("CoverHostImpl", "updateCoverWindow: START");
        CoverState coverState = this.mCoverState;
        int i = coverState == null ? 2 : coverState.type;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mHelper;
        boolean z2 = this.mIsCoverClosed;
        boolean z3 = this.mIsCoverAppCovered;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        currentState.isCoverClosed = z2;
        currentState.coverAppShowing = z3;
        currentState.coverType = i;
        secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        boolean z4 = this.mIsCoverClosed && ((!this.mIsCoverAppCovered && DeviceState.isCoverUIType(i)) || i == 15 || i == 16);
        StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
        StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
        if (state.mChangeStatusBarHeight != z4) {
            state.mChangeStatusBarHeight = z4;
            statusBarWindowController.apply(state, false);
        }
        boolean z5 = this.mIsCoverClosed;
        IndicatorCoverManager indicatorCoverManager = this.mIndicatorCoverManager;
        Lazy lazy = this.mPluginAODManagerLazy;
        if (z5 || (z = this.mIsCoverAppCovered)) {
            ((PluginAODManager) lazy.get()).disableStatusBar(65536);
            CoverState coverState2 = this.mCoverState;
            if (coverState2 != null) {
                indicatorCoverManager.updateCoverMargin(coverState2.type, this.mIsCoverAppCovered);
            }
            this.mStatusBarKeyguardViewManager.onBackPressed();
        } else {
            indicatorCoverManager.updateCoverMargin(2, z);
            ((PluginAODManager) lazy.get()).disableStatusBar(0);
        }
        Log.d("CoverHostImpl", "updateCoverWindow: END");
    }
}
