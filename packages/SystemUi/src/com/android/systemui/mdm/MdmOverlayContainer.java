package com.android.systemui.mdm;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.samsung.android.knox.lockscreen.LockscreenOverlay;
import com.samsung.android.knox.lockscreen.LockscreenOverlayView;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        if (!(((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).mState == 1 && centralSurfacesImpl.isKeyguardShowing() && !centralSurfacesImpl.mDozing && !centralSurfacesImpl.isOccluded())) {
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
        try {
            LockscreenOverlay lockscreenOverlay = this.mLockscreenOverlay;
            Context context = this.mContext;
            if (lockscreenOverlay == null) {
                this.mLockscreenOverlay = LockscreenOverlay.getInstance(context);
            }
            if (!this.mLockscreenOverlay.isConfigured()) {
                FrameLayout frameLayout2 = this.mView;
                if (frameLayout2 != null) {
                    LockscreenOverlayView lockscreenOverlayView2 = this.mMdmOverlayView;
                    if (lockscreenOverlayView2 != null) {
                        frameLayout2.removeView(lockscreenOverlayView2);
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
                LockscreenOverlayView lockscreenOverlayView3 = new LockscreenOverlayView(context);
                this.mMdmOverlayView = lockscreenOverlayView3;
                this.mView.addView(lockscreenOverlayView3, -1, -1);
            } else {
                Log.d("MdmOverlayContainer", "mMdmOverlayView is not null!!");
                this.mMdmOverlayView.setVisibility(0);
            }
            this.mView.setVisibility(0);
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Lockscren Overlay creation fails: ", e, "MdmOverlayContainer");
        }
    }
}
