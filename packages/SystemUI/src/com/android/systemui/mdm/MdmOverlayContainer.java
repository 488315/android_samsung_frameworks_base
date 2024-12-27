package com.android.systemui.mdm;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.knox.lockscreen.LockscreenOverlay;
import com.samsung.android.knox.lockscreen.LockscreenOverlayView;
import dagger.Lazy;

public final class MdmOverlayContainer {
    public final Context mContext;
    public LockscreenOverlay mLockscreenOverlay;
    public LockscreenOverlayView mMdmOverlayView;
    public int mPreviousState;
    public CentralSurfaces mStatusBar;
    public final Lazy mStatusBarStateControllerLazy;
    public FrameLayout mView;

    public MdmOverlayContainer(Context context, Lazy lazy) {
        this.mContext = context;
        this.mStatusBarStateControllerLazy = lazy;
    }

    public final void updateMdmPolicy() {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mStatusBar;
        if (((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).mState == 1) {
            KeyguardStateController keyguardStateController = centralSurfacesImpl.mKeyguardStateController;
            if (((KeyguardStateControllerImpl) keyguardStateController).mShowing && !centralSurfacesImpl.mDozing && !((KeyguardStateControllerImpl) keyguardStateController).mOccluded) {
                try {
                    if (this.mLockscreenOverlay == null) {
                        this.mLockscreenOverlay = LockscreenOverlay.getInstance(this.mContext);
                    }
                    if (!this.mLockscreenOverlay.isConfigured()) {
                        FrameLayout frameLayout = this.mView;
                        if (frameLayout != null) {
                            LockscreenOverlayView lockscreenOverlayView = this.mMdmOverlayView;
                            if (lockscreenOverlayView != null) {
                                frameLayout.removeView(lockscreenOverlayView);
                                this.mMdmOverlayView = null;
                            }
                            this.mView.setVisibility(8);
                            return;
                        }
                        return;
                    }
                    if (this.mView == null) {
                        Log.d("MdmOverlayContainer", "mMDMOverlayContainer is null");
                        return;
                    }
                    if (this.mMdmOverlayView == null) {
                        LockscreenOverlayView lockscreenOverlayView2 = new LockscreenOverlayView(this.mContext);
                        this.mMdmOverlayView = lockscreenOverlayView2;
                        this.mView.addView(lockscreenOverlayView2, -1, -1);
                    } else {
                        Log.d("MdmOverlayContainer", "mMdmOverlayView is not null!!");
                        this.mMdmOverlayView.setVisibility(0);
                    }
                    this.mView.setVisibility(0);
                    return;
                } catch (Exception e) {
                    AbsAdapter$1$$ExternalSyntheticOutline0.m("Lockscren Overlay creation fails: ", e, "MdmOverlayContainer");
                    return;
                }
            }
        }
        FrameLayout frameLayout2 = this.mView;
        if (frameLayout2 != null) {
            LockscreenOverlayView lockscreenOverlayView3 = this.mMdmOverlayView;
            if (lockscreenOverlayView3 != null) {
                frameLayout2.removeView(lockscreenOverlayView3);
                this.mMdmOverlayView = null;
            }
            this.mView.setVisibility(8);
        }
    }
}
