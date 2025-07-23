package com.android.systemui.media.dialog;

import android.view.MotionEvent;
import android.view.View;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.MediaOutputAdapterLegacy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda4 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda4(MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaDeviceViewHolderLegacy;
        this.f$1 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                final MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = this.f$0;
                MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl seekBarVolumeControl = (MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl) this.f$1;
                int i = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.$r8$clinit;
                mediaDeviceViewHolderLegacy.getClass();
                if (seekBarVolumeControl.getVolume() != 0) {
                    seekBarVolumeControl.onMute();
                    MediaOutputSeekbar mediaOutputSeekbar = mediaDeviceViewHolderLegacy.mSeekBar;
                    mediaOutputSeekbar.setProgress(mediaOutputSeekbar.getMin());
                    seekBarVolumeControl.setVolume(0);
                    mediaDeviceViewHolderLegacy.updateMutedVolumeIcon(null);
                    mediaDeviceViewHolderLegacy.mIconAreaLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda9
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view2, MotionEvent motionEvent) {
                            MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.this.mSeekBar.dispatchTouchEvent(motionEvent);
                            return false;
                        }
                    });
                    break;
                } else {
                    seekBarVolumeControl.onUnmute();
                    mediaDeviceViewHolderLegacy.mSeekBar.setProgress(2000, true);
                    seekBarVolumeControl.setVolume(2);
                    mediaDeviceViewHolderLegacy.updateUnmutedVolumeIcon(null);
                    mediaDeviceViewHolderLegacy.mIconAreaLayout.setOnTouchListener(new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda3(0));
                    break;
                }
            default:
                this.f$0.this$0.mController.tryToLaunchInAppRoutingIntent(view, ((MediaDevice) this.f$1).getId());
                break;
        }
    }
}
