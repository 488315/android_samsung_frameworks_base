package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.Log;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.ViewMediatorProvider;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.CoverUtil;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import dagger.Lazy;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverHostImpl implements CoverHost {
    public final CentralSurfaces mCentralSurfaces;
    public final Context mContext;
    public CoverState mCoverState;
    public final CoverUtil mCoverUtil;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final ViewMediatorCallback mKeyguardViewMediatorCallback;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final Lazy mPluginAODManagerLazy;
    public final PluginLockManager mPluginLockManager;
    private final SettingsHelper mSettingsHelper;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarWindowController mStatusBarWindowController;
    public final SysuiStatusBarStateController mSysuiStatusBarStateController;
    public boolean mIsCoverClosed = false;
    public boolean mIsCoverAppCovered = false;

    public CoverHostImpl(Context context, CentralSurfaces centralSurfaces, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewMediator keyguardViewMediator, ViewMediatorCallback viewMediatorCallback, NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowController statusBarWindowController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardStateController keyguardStateController, CoverUtil coverUtil, Lazy lazy, PluginLockManager pluginLockManager, SettingsHelper settingsHelper) {
        this.mContext = context;
        this.mCentralSurfaces = centralSurfaces;
        this.mSysuiStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardViewMediatorCallback = viewMediatorCallback;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mStatusBarWindowController = statusBarWindowController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mCoverUtil = coverUtil;
        this.mPluginAODManagerLazy = lazy;
        this.mPluginLockManager = pluginLockManager;
        this.mSettingsHelper = settingsHelper;
    }

    public final boolean isAutomaticUnlock(CoverState coverState) {
        if (coverState == null || !coverState.getAttachState() || !this.mSettingsHelper.isAutomaticUnlockEnabled()) {
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

    public final void updateCoverState(CoverState coverState) {
        if (coverState == null) {
            return;
        }
        this.mCoverState = coverState;
        if (LsRune.COVER_SAFEMODE) {
            return;
        }
        boolean z = !coverState.switchState;
        int i = coverState.type;
        LogUtil.d("CoverHostImpl", "updateCoverState: attach = %s, cover closed = %s, type = %d", Boolean.valueOf(coverState.getAttachState()), Boolean.valueOf(z), Integer.valueOf(i));
        this.mKeyguardUpdateMonitor.dispatchCoverState(this.mCoverState);
        if (LsRune.WALLPAPER_VIRTUAL_DISPLAY && DeviceState.isCoverUiWithWallpaper(this.mCoverState.getType())) {
            this.mPluginLockManager.onCoverStateChanged(this.mCoverState);
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
                    if (((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isDesktopMode()) {
                        Log.d("CoverHostImpl", "Don't need to update doKeyguardLaterLocked by desktopMode");
                    } else if (!this.mStatusBarKeyguardViewManager.canHandleBackPressed() && !isAutomaticUnlock(this.mCoverState)) {
                        if (this.mCoverState.switchState) {
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mKeyguardViewMediator.mHelper;
                            synchronized (keyguardViewMediatorHelperImpl.lock$delegate.getValue()) {
                                keyguardViewMediatorHelperImpl.cancelLockWhenCoverIsOpened(true);
                                Unit unit = Unit.INSTANCE;
                            }
                        } else if (!this.mKeyguardUpdateMonitor.isKeyguardVisible()) {
                            ViewMediatorProvider viewMediatorProvider = this.mKeyguardViewMediator.mHelper.viewMediatorProvider;
                            if (viewMediatorProvider == null) {
                                viewMediatorProvider = null;
                            }
                            viewMediatorProvider.doKeyguardLaterLocked.invoke();
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
                    ((CentralSurfacesImpl) this.mCentralSurfaces).mShadeController.instantCollapseShade();
                }
            }
        }
        this.mCoverUtil.updateCoverState(coverState);
    }

    public final void updateCoverWindow() {
        Log.d("CoverHostImpl", "updateCoverWindow: START");
        CoverState coverState = this.mCoverState;
        int i = coverState == null ? 2 : coverState.type;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mHelper;
        boolean z = this.mIsCoverClosed;
        boolean z2 = this.mIsCoverAppCovered;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        currentState.isCoverClosed = z;
        currentState.coverAppShowing = z2;
        currentState.coverType = i;
        secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        boolean z3 = this.mIsCoverClosed && ((!this.mIsCoverAppCovered && DeviceState.isCoverUIType(i)) || i == 15 || i == 16);
        StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
        StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
        if (state.mChangeStatusBarHeight != z3) {
            state.mChangeStatusBarHeight = z3;
            statusBarWindowController.apply(state);
        }
        boolean z4 = this.mIsCoverClosed;
        Lazy lazy = this.mPluginAODManagerLazy;
        if (z4 || this.mIsCoverAppCovered) {
            ((PluginAODManager) lazy.get()).disableStatusBar(65536);
            this.mStatusBarKeyguardViewManager.onBackPressed();
        } else {
            ((PluginAODManager) lazy.get()).disableStatusBar(0);
        }
        Log.d("CoverHostImpl", "updateCoverWindow: END");
    }
}
