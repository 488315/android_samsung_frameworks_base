package com.android.systemui.volume;

import android.R;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import com.android.settingslib.Utils;
import com.android.systemui.Prefs;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.VolumeDialogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda2(VolumeDialogImpl volumeDialogImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = volumeDialogImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final ImageButton imageButton;
        VolumeDialogImpl.CustomDialog customDialog;
        switch (this.$r8$classId) {
            case 0:
                VolumeDialogImpl volumeDialogImpl = this.f$0;
                synchronized (volumeDialogImpl.mSafetyWarningLock) {
                    volumeDialogImpl.mCsdDialog = null;
                }
                volumeDialogImpl.recheckH(null);
                return;
            case 1:
                final VolumeDialogImpl volumeDialogImpl2 = this.f$0;
                LayerDrawable layerDrawable = (LayerDrawable) volumeDialogImpl2.mRingerAndDrawerContainer.getBackground();
                if (layerDrawable == null || layerDrawable.getNumberOfLayers() <= 0) {
                    return;
                }
                volumeDialogImpl2.mRingerAndDrawerContainerBackground = layerDrawable.getDrawable(0);
                volumeDialogImpl2.updateBackgroundForDrawerClosedAmount();
                if (volumeDialogImpl2.mTopContainer == null) {
                    return;
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(new Drawable[]{new ColorDrawable(Utils.getColorAttrDefaultColor(R.^attr-private.colorSurface, volumeDialogImpl2.mContext, 0))});
                layerDrawable2.setLayerSize(0, volumeDialogImpl2.mDialogWidth, !volumeDialogImpl2.isLandscape() ? volumeDialogImpl2.mDialogRowsView.getHeight() : volumeDialogImpl2.mDialogRowsView.getHeight() + volumeDialogImpl2.mDialogCornerRadius);
                layerDrawable2.setLayerInsetTop(0, !volumeDialogImpl2.isLandscape() ? volumeDialogImpl2.mDialogRowsViewContainer.getTop() : volumeDialogImpl2.mDialogRowsViewContainer.getTop() - volumeDialogImpl2.mDialogCornerRadius);
                layerDrawable2.setLayerGravity(0, 53);
                if (volumeDialogImpl2.isLandscape()) {
                    volumeDialogImpl2.mRingerAndDrawerContainer.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.volume.VolumeDialogImpl.6
                        @Override // android.view.ViewOutlineProvider
                        public final void getOutline(View view, Outline outline) {
                            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), VolumeDialogImpl.this.mDialogCornerRadius);
                        }
                    });
                    volumeDialogImpl2.mRingerAndDrawerContainer.setClipToOutline(true);
                }
                volumeDialogImpl2.mTopContainer.setBackground(layerDrawable2);
                return;
            case 2:
                View view = this.f$0.mODICaptionsTooltipView;
                if (view != null) {
                    view.setVisibility(4);
                    return;
                }
                return;
            case 3:
                final VolumeDialogImpl volumeDialogImpl3 = this.f$0;
                if (Prefs.getBoolean(volumeDialogImpl3.mContext, "TouchedRingerToggle", false) || (imageButton = volumeDialogImpl3.mRingerIcon) == null) {
                    return;
                }
                imageButton.postOnAnimationDelayed(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        VolumeDialogImpl volumeDialogImpl4 = VolumeDialogImpl.this;
                        ImageButton imageButton2 = imageButton;
                        volumeDialogImpl4.getClass();
                        if (imageButton2 != null) {
                            imageButton2.setPressed(true);
                            imageButton2.postOnAnimationDelayed(new VolumeDialogImpl$$ExternalSyntheticLambda12(imageButton2, 0), 200L);
                        }
                    }
                }, 1500L);
                return;
            case 4:
                VolumeDialogImpl volumeDialogImpl4 = this.f$0;
                volumeDialogImpl4.mHandler.postDelayed(new VolumeDialogImpl$$ExternalSyntheticLambda2(volumeDialogImpl4, 6), 50L);
                return;
            case 5:
                VolumeDialogImpl volumeDialogImpl5 = this.f$0;
                View view2 = volumeDialogImpl5.mODICaptionsTooltipView;
                if (view2 != null) {
                    view2.setAlpha(0.0f);
                    volumeDialogImpl5.mODICaptionsTooltipView.setTranslationY((volumeDialogImpl5.mODICaptionsIcon.getLocationOnScreen()[1] - volumeDialogImpl5.mODICaptionsTooltipView.getLocationOnScreen()[1]) - ((volumeDialogImpl5.mODICaptionsTooltipView.getHeight() - volumeDialogImpl5.mODICaptionsIcon.getHeight()) / 2.0f));
                    volumeDialogImpl5.mODICaptionsTooltipView.animate().alpha(1.0f).setStartDelay(volumeDialogImpl5.mDialogShowAnimationDurationMs).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda2(volumeDialogImpl5, 9)).start();
                    return;
                }
                return;
            case 6:
                VolumeDialogImpl volumeDialogImpl6 = this.f$0;
                VolumeDialogController volumeDialogController = volumeDialogImpl6.mController;
                if (volumeDialogController != null) {
                    volumeDialogController.notifyVisible(false);
                }
                VolumeDialogImpl.CustomDialog customDialog2 = volumeDialogImpl6.mDialog;
                if (customDialog2 != null) {
                    customDialog2.dismiss();
                }
                if (volumeDialogImpl6.mHasSeenODICaptionsTooltip && volumeDialogImpl6.mODICaptionsTooltipView != null && (customDialog = volumeDialogImpl6.mDialog) != null) {
                    ((ViewGroup) customDialog.findViewById(com.android.systemui.R.id.volume_dialog_container)).removeView(volumeDialogImpl6.mODICaptionsTooltipView);
                    volumeDialogImpl6.mODICaptionsTooltipView = null;
                }
                volumeDialogImpl6.mIsAnimatingDismiss = false;
                volumeDialogImpl6.hideRingerDrawer();
                return;
            case 7:
                this.f$0.mRingerDrawerContainer.setVisibility(4);
                return;
            case 8:
                VolumeDialogImpl volumeDialogImpl7 = this.f$0;
                volumeDialogImpl7.getDrawerIconViewForMode(volumeDialogImpl7.mState.ringerModeInternal).setVisibility(0);
                return;
            default:
                final VolumeDialogImpl volumeDialogImpl8 = this.f$0;
                volumeDialogImpl8.getClass();
                if (C3599D.BUG) {
                    Log.d(VolumeDialogImpl.TAG, "tool:checkODICaptionsTooltip() putBoolean true");
                }
                Prefs.putBoolean(volumeDialogImpl8.mContext, "HasSeenODICaptionsTooltip", true);
                volumeDialogImpl8.mHasSeenODICaptionsTooltip = true;
                final CaptionsToggleImageButton captionsToggleImageButton = volumeDialogImpl8.mODICaptionsIcon;
                if (captionsToggleImageButton != null) {
                    captionsToggleImageButton.postOnAnimation(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            VolumeDialogImpl volumeDialogImpl42 = VolumeDialogImpl.this;
                            ImageButton imageButton2 = captionsToggleImageButton;
                            volumeDialogImpl42.getClass();
                            if (imageButton2 != null) {
                                imageButton2.setPressed(true);
                                imageButton2.postOnAnimationDelayed(new VolumeDialogImpl$$ExternalSyntheticLambda12(imageButton2, 0), 200L);
                            }
                        }
                    });
                    return;
                }
                return;
        }
    }
}
