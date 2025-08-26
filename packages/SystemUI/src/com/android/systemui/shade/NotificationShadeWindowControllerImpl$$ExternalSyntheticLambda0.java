package com.android.systemui.shade;

import android.app.SemWallpaperColors;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean zIsWhiteKeyguardWallpaper;
        int iCopyFrom;
        ViewGroup viewGroup;
        switch (this.$r8$classId) {
            case 0:
                try {
                    WindowManagerGlobal.getWindowManagerService().onNotificationShadeExpanded((IBinder) this.f$0, ((Boolean) this.f$1).booleanValue());
                    break;
                } catch (RemoteException e) {
                    Log.e("NotificationShadeWindowController", "Failed to call onNotificationShadeExpanded", e);
                    return;
                }
            default:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.f$0;
                NotificationShadeWindowState notificationShadeWindowState = (NotificationShadeWindowState) this.f$1;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
                secNotificationShadeWindowControllerHelperImpl.getClass();
                if ((LsRune.SECURITY_BOUNCER_WINDOW || SafeUIState.isSysUiSafeModeEnabled()) && secNotificationShadeWindowControllerHelperImpl.bouncerContainer != null) {
                    if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                        zIsWhiteKeyguardWallpaper = true;
                        SemWallpaperColors cachedSemWallpaperColors = WallpaperUtils.getCachedSemWallpaperColors(!((KeyguardFoldControllerImpl) secNotificationShadeWindowControllerHelperImpl.keyguardFoldController).isFoldOpened());
                        if (cachedSemWallpaperColors == null || cachedSemWallpaperColors.get(512L).getFontColor() != 1) {
                            zIsWhiteKeyguardWallpaper = false;
                        }
                    } else {
                        zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY);
                    }
                    boolean z = notificationShadeWindowState.bouncerShowing;
                    if (!z || !notificationShadeWindowState.keyguardShowing || notificationShadeWindowState.coverAppShowing || notificationShadeWindowState.isCoverClosed) {
                        WindowManager.LayoutParams layoutParams = secNotificationShadeWindowControllerHelperImpl.bouncerLpChanged;
                        if (layoutParams != null) {
                            layoutParams.flags = (layoutParams.flags | 8) & (-131073);
                            secNotificationShadeWindowControllerHelperImpl.applyBouncerWindowBlur(0.0f, zIsWhiteKeyguardWallpaper);
                            if (!secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation) {
                                layoutParams.screenOrientation = -1;
                            }
                        }
                    } else if (z) {
                        if (LsRune.SECURITY_CAPTURED_BLUR && (viewGroup = secNotificationShadeWindowControllerHelperImpl.bouncerContainer) != null) {
                            if (notificationShadeWindowState.keyguardOccluded) {
                                if (zIsWhiteKeyguardWallpaper) {
                                    viewGroup.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.bouncer_background_color_occluded_no_blur_white_bg, null));
                                } else {
                                    viewGroup.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.bouncer_background_color_occluded, null));
                                }
                            } else if (zIsWhiteKeyguardWallpaper) {
                                viewGroup.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.bouncer_background_color_no_blur_white_bg, null));
                            } else {
                                viewGroup.setBackgroundColor(0);
                            }
                        }
                        WindowManager.LayoutParams layoutParams2 = secNotificationShadeWindowControllerHelperImpl.bouncerLpChanged;
                        if (layoutParams2 != null) {
                            int i = layoutParams2.flags;
                            int i2 = i & (-25);
                            layoutParams2.flags = i2;
                            if (notificationShadeWindowState.keyguardNeedsInput) {
                                layoutParams2.flags = i & (-131097);
                            } else {
                                layoutParams2.flags = 131072 | i2;
                            }
                            if (LsRune.SECURITY_BLUR) {
                                secNotificationShadeWindowControllerHelperImpl.applyBouncerWindowBlur(1.0f, zIsWhiteKeyguardWallpaper);
                            }
                            if (SafeUIState.isSysUiSafeModeEnabled()) {
                                layoutParams2.userActivityTimeout = -1L;
                                layoutParams2.screenDimDuration = -1L;
                            } else {
                                SecNotificationShadeWindowControllerHelperImpl.Provider provider = secNotificationShadeWindowControllerHelperImpl.provider;
                                WindowManager.LayoutParams layoutParams3 = (WindowManager.LayoutParams) (provider != null ? provider : null).lpSupplier.get();
                                if (layoutParams3 != null) {
                                    long j = layoutParams3.userActivityTimeout;
                                    if (j < 10000) {
                                        j = 10000;
                                    }
                                    layoutParams2.userActivityTimeout = j;
                                    layoutParams2.screenDimDuration = layoutParams3.screenDimDuration;
                                }
                            }
                            if (!secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation && notificationShadeWindowState.keyguardOccluded) {
                                layoutParams2.screenOrientation = 5;
                            }
                        }
                    }
                    WindowManager.LayoutParams layoutParams4 = secNotificationShadeWindowControllerHelperImpl.bouncerLpChanged;
                    if (layoutParams4 != null) {
                        layoutParams4.height = (notificationShadeWindowState.keyguardShowing || secNotificationShadeWindowControllerHelperImpl.keyguardTransitionInteractor.getCurrentState() == KeyguardState.PRIMARY_BOUNCER) ? -1 : 0;
                        if ((layoutParams4.flags & 67108864) != 0) {
                            layoutParams4.subtreeSystemUiVisibility |= PeripheralConstants.ErrorCode.ERROR_PLUGIN_CUSTOM_BASE;
                        }
                    }
                    ViewGroup viewGroup2 = secNotificationShadeWindowControllerHelperImpl.bouncerContainer;
                    if (viewGroup2 != null && notificationShadeWindowState.bouncerShowing) {
                        int systemUiVisibility = viewGroup2.getSystemUiVisibility();
                        viewGroup2.setSystemUiVisibility(zIsWhiteKeyguardWallpaper ? systemUiVisibility | 16 : systemUiVisibility & (-17));
                    }
                    WindowManager.LayoutParams layoutParams5 = secNotificationShadeWindowControllerHelperImpl.bouncerLpChanged;
                    if (layoutParams5 != null) {
                        layoutParams5.flags = (!notificationShadeWindowState.bouncerShowing || (LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW && secNotificationShadeWindowControllerHelperImpl.engineerModeManager.isCaptureEnabled)) ? layoutParams5.flags & (-8193) : layoutParams5.flags | 8192;
                    }
                    WindowManager.LayoutParams layoutParams6 = secNotificationShadeWindowControllerHelperImpl.bouncerLp;
                    if (layoutParams6 != null && (iCopyFrom = layoutParams6.copyFrom(layoutParams5)) != 0) {
                        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(layoutParams6.height, "Bouncer LP changed!!! = 0x", Integer.toHexString(iCopyFrom), ", h = ", "NotificationShadeWindowController");
                        secNotificationShadeWindowControllerHelperImpl.windowManager.updateViewLayout(secNotificationShadeWindowControllerHelperImpl.bouncerContainer, layoutParams6);
                        break;
                    }
                }
                break;
        }
    }
}
