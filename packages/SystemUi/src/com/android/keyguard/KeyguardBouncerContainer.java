package com.android.keyguard;

import android.content.Context;
import android.media.session.MediaSessionLegacyHelper;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.SafeUIState;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBouncerContainer extends FrameLayout {
    public final CentralSurfaces mService;
    public final StatusBarStateController mStatusBarStateController;

    public KeyguardBouncerContainer(Context context, CentralSurfaces centralSurfaces, StatusBarStateController statusBarStateController) {
        super(context);
        this.mService = centralSurfaces;
        this.mStatusBarStateController = statusBarStateController;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0070 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0071  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        boolean z = keyEvent.getAction() == 0;
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 4) {
            if (!z) {
                ((CentralSurfacesImpl) this.mService).onBackPressed();
            }
            return true;
        }
        if (keyCode != 62) {
            if (keyCode != 82) {
                if ((keyCode == 24 || keyCode == 25) && this.mStatusBarStateController.isDozing()) {
                    MediaSessionLegacyHelper.getHelper(((FrameLayout) this).mContext).sendVolumeKeyEvent(keyEvent, VideoPlayer.MEDIA_ERROR_SYSTEM, true);
                    return true;
                }
                if (((CentralSurfacesImpl) this.mService).interceptMediaKey(keyEvent)) {
                    return super.dispatchKeyEvent(keyEvent);
                }
                return true;
            }
            if (!z) {
                return ((CentralSurfacesImpl) this.mService).onMenuPressed();
            }
        }
        if (!z) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mService;
            if (!centralSurfacesImpl.mDeviceInteractive || centralSurfacesImpl.mState == 0) {
                return false;
            }
            ((ShadeControllerImpl) centralSurfacesImpl.mShadeController).animateCollapsePanels(1.0f, 0, true, false);
            return true;
        }
        if (((CentralSurfacesImpl) this.mService).interceptMediaKey(keyEvent)) {
        }
    }
}
