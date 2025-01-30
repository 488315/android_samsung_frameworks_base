package com.android.systemui.volume;

import android.content.Context;
import android.content.Intent;
import android.util.FeatureFlagUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.media.dialog.MediaOutputDialog;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.plugins.VolumeDialogController;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda6 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda6(VolumeDialogImpl volumeDialogImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = volumeDialogImpl;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = 2;
        switch (this.$r8$classId) {
            case 0:
                VolumeDialogImpl volumeDialogImpl = this.f$0;
                Prefs.putBoolean(volumeDialogImpl.mContext, "TouchedRingerToggle", true);
                VolumeDialogController.StreamState streamState = volumeDialogImpl.mState.states.get(2);
                if (streamState != null) {
                    boolean hasVibrator = volumeDialogImpl.mController.hasVibrator();
                    int i2 = volumeDialogImpl.mState.ringerModeInternal;
                    if (i2 == 2) {
                        if (hasVibrator) {
                            i = 1;
                        }
                        i = 0;
                    } else {
                        if (i2 != 1) {
                            if (streamState.level == 0) {
                                volumeDialogImpl.mController.setStreamVolume(2, 1);
                            }
                        }
                        i = 0;
                    }
                    volumeDialogImpl.setRingerMode(i);
                    break;
                }
                break;
            case 1:
                VolumeDialogImpl volumeDialogImpl2 = this.f$0;
                volumeDialogImpl2.getClass();
                Events.writeEvent(8, new Object[0]);
                volumeDialogImpl2.dismissH(5);
                volumeDialogImpl2.mMediaOutputDialogFactory.getClass();
                MediaOutputDialog mediaOutputDialog = MediaOutputDialogFactory.mediaOutputDialog;
                if (mediaOutputDialog != null) {
                    mediaOutputDialog.dismiss();
                }
                MediaOutputDialogFactory.mediaOutputDialog = null;
                if (!FeatureFlagUtils.isEnabled(volumeDialogImpl2.mContext, "settings_volume_panel_in_systemui")) {
                    volumeDialogImpl2.mActivityStarter.startActivity(new Intent("android.settings.panel.action.VOLUME"), true);
                    break;
                } else {
                    volumeDialogImpl2.mVolumePanelFactory.create();
                    break;
                }
            case 2:
                VolumeDialogImpl volumeDialogImpl3 = this.f$0;
                boolean z = volumeDialogImpl3.mIsRingerDrawerOpen;
                if (!z) {
                    if (!z) {
                        volumeDialogImpl3.mRingerDrawerVibrateIcon.setVisibility(volumeDialogImpl3.mState.ringerModeInternal == 1 ? 4 : 0);
                        volumeDialogImpl3.mRingerDrawerMuteIcon.setVisibility(volumeDialogImpl3.mState.ringerModeInternal == 0 ? 4 : 0);
                        volumeDialogImpl3.mRingerDrawerNormalIcon.setVisibility(volumeDialogImpl3.mState.ringerModeInternal != 2 ? 0 : 4);
                        volumeDialogImpl3.mRingerDrawerNewSelectionBg.setAlpha(0.0f);
                        if (volumeDialogImpl3.isLandscape()) {
                            volumeDialogImpl3.mRingerDrawerNewSelectionBg.setTranslationX(volumeDialogImpl3.getTranslationInDrawerForRingerMode(volumeDialogImpl3.mState.ringerModeInternal));
                        } else {
                            volumeDialogImpl3.mRingerDrawerNewSelectionBg.setTranslationY(volumeDialogImpl3.getTranslationInDrawerForRingerMode(volumeDialogImpl3.mState.ringerModeInternal));
                        }
                        if (volumeDialogImpl3.isLandscape()) {
                            volumeDialogImpl3.mRingerDrawerContainer.setTranslationX((volumeDialogImpl3.mRingerCount - 1) * volumeDialogImpl3.mRingerDrawerItemSize);
                        } else {
                            volumeDialogImpl3.mRingerDrawerContainer.setTranslationY((volumeDialogImpl3.mRingerCount - 1) * volumeDialogImpl3.mRingerDrawerItemSize);
                        }
                        volumeDialogImpl3.mRingerDrawerContainer.setAlpha(0.0f);
                        volumeDialogImpl3.mRingerDrawerContainer.setVisibility(0);
                        int i3 = volumeDialogImpl3.mState.ringerModeInternal == 1 ? 175 : IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
                        ViewPropertyAnimator animate = volumeDialogImpl3.mRingerDrawerContainer.animate();
                        Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
                        long j = i3;
                        animate.setInterpolator(interpolator).setDuration(j).setStartDelay(volumeDialogImpl3.mState.ringerModeInternal == 1 ? 75L : 0L).alpha(1.0f).translationX(0.0f).translationY(0.0f).start();
                        volumeDialogImpl3.mSelectedRingerContainer.animate().setInterpolator(interpolator).setDuration(250L).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda2(volumeDialogImpl3, 8));
                        volumeDialogImpl3.mAnimateUpBackgroundToMatchDrawer.setDuration(j);
                        volumeDialogImpl3.mAnimateUpBackgroundToMatchDrawer.setInterpolator(interpolator);
                        volumeDialogImpl3.mAnimateUpBackgroundToMatchDrawer.start();
                        if (volumeDialogImpl3.isLandscape()) {
                            volumeDialogImpl3.mSelectedRingerContainer.animate().translationX(volumeDialogImpl3.getTranslationInDrawerForRingerMode(volumeDialogImpl3.mState.ringerModeInternal)).start();
                        } else {
                            volumeDialogImpl3.mSelectedRingerContainer.animate().translationY(volumeDialogImpl3.getTranslationInDrawerForRingerMode(volumeDialogImpl3.mState.ringerModeInternal)).start();
                        }
                        ViewGroup viewGroup = volumeDialogImpl3.mSelectedRingerContainer;
                        Context context = volumeDialogImpl3.mContext;
                        int i4 = volumeDialogImpl3.mState.ringerModeInternal;
                        viewGroup.setContentDescription(context.getString(i4 != 0 ? i4 != 1 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_status_vibrate : R.string.volume_ringer_status_silent));
                        volumeDialogImpl3.mIsRingerDrawerOpen = true;
                        break;
                    }
                } else {
                    volumeDialogImpl3.hideRingerDrawer();
                    break;
                }
                break;
            default:
                this.f$0.hideCaptionsTooltip();
                Events.writeEvent(22, new Object[0]);
                break;
        }
    }
}
