package com.android.systemui.wallpaper;

import android.util.Log;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper;
import com.android.systemui.wallpaper.view.SystemUIWallpaper;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardWallpaperController$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardWallpaperController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardWallpaperController$$ExternalSyntheticLambda9(KeyguardWallpaperController keyguardWallpaperController, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardWallpaperController;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardWallpaperController keyguardWallpaperController = this.f$0;
                boolean z = this.f$1;
                keyguardWallpaperController.mRunnableCleanUp = null;
                Log.i("KeyguardWallpaperController", "cleanUpOnUiThread(), view = " + keyguardWallpaperController.mWallpaperView);
                ViewGroup viewGroup = keyguardWallpaperController.mRootView;
                if (viewGroup != null && viewGroup.getChildCount() > 0 && z) {
                    keyguardWallpaperController.removeAllChildViews(keyguardWallpaperController.mRootView, true);
                }
                SystemUIWallpaperBase systemUIWallpaperBase = keyguardWallpaperController.mWallpaperView;
                if (systemUIWallpaperBase != null) {
                    systemUIWallpaperBase.cleanUp();
                    keyguardWallpaperController.mWallpaperView = null;
                    break;
                }
                break;
            default:
                KeyguardWallpaperController keyguardWallpaperController2 = this.f$0;
                boolean z2 = this.f$1;
                if (keyguardWallpaperController2.mWallpaperView != null) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onTransitionAod: isScreenOff = ", z2, "KeyguardWallpaperController");
                    if (!z2 || keyguardWallpaperController2.mDozeParameters.mControlScreenOffAnimation) {
                        if (((PluginWallpaperManagerImpl) keyguardWallpaperController2.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                            if (z2) {
                                if (keyguardWallpaperController2.mTransitionView == null) {
                                    KeyguardTransitionWallpaper keyguardTransitionWallpaper = new KeyguardTransitionWallpaper(keyguardWallpaperController2.mContext, keyguardWallpaperController2.mUpdateMonitor, null, keyguardWallpaperController2.mPluginWallpaperManager, keyguardWallpaperController2.mExecutor, keyguardWallpaperController2.mWcgConsumer, false, keyguardWallpaperController2.mWallpaperView, keyguardWallpaperController2.mOccluded);
                                    keyguardWallpaperController2.mTransitionView = keyguardTransitionWallpaper;
                                    keyguardTransitionWallpaper.mUpdateListener = keyguardWallpaperController2.mTransitionListener;
                                    ViewGroup viewGroup2 = keyguardWallpaperController2.mRootView;
                                    if (viewGroup2 != null) {
                                        viewGroup2.addView(keyguardTransitionWallpaper);
                                    }
                                }
                                SystemUIWallpaperBase systemUIWallpaperBase2 = keyguardWallpaperController2.mWallpaperView;
                                if (!(((SystemUIWallpaper) systemUIWallpaperBase2).mTransitionAnimationListener != null)) {
                                    systemUIWallpaperBase2.setTransitionAnimationListener(keyguardWallpaperController2.mTransitionAnimationListener);
                                }
                            } else if (keyguardWallpaperController2.mPendingRotationForTransitionView) {
                                keyguardWallpaperController2.disableRotateIfNeeded();
                                keyguardWallpaperController2.mPendingRotationForTransitionView = false;
                            }
                            SystemUIWallpaperBase systemUIWallpaperBase3 = keyguardWallpaperController2.mTransitionView;
                            if (systemUIWallpaperBase3 != null) {
                                if (z2) {
                                    ((KeyguardTransitionWallpaper) systemUIWallpaperBase3).mWallpaperView = keyguardWallpaperController2.mWallpaperView;
                                } else {
                                    ((KeyguardTransitionWallpaper) systemUIWallpaperBase3).mValueAnimator.start();
                                }
                            }
                        }
                        if (!((PluginWallpaperManagerImpl) keyguardWallpaperController2.mPluginWallpaperManager).isDynamicWallpaperEnabled() && WallpaperUtils.getWallpaperType() == 8) {
                            Log.d("KeyguardWallpaperController", "onTransitionAod: Ignore transition animation.");
                            break;
                        } else {
                            SystemUIWallpaper systemUIWallpaper = (SystemUIWallpaper) keyguardWallpaperController2.mWallpaperView;
                            systemUIWallpaper.mIsScreenOffAnimation = z2;
                            if (!z2) {
                                systemUIWallpaper.animate().setDuration(500L).scaleX(1.0f).scaleY(1.0f).setListener(systemUIWallpaper.mAnimatorListener).start();
                                break;
                            } else {
                                systemUIWallpaper.animate().setDuration(800L).scaleX(1.02f).scaleY(1.02f).setListener(systemUIWallpaper.mAnimatorListener).start();
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }
}
