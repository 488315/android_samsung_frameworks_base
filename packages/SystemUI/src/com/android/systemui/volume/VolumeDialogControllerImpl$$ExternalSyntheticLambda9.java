package com.android.systemui.volume;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;
import com.android.systemui.R;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.VolumeDialogControllerImpl;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import com.android.systemui.volume.util.BluetoothIconServerUtils;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogControllerImpl f$0;

    public /* synthetic */ VolumeDialogControllerImpl$$ExternalSyntheticLambda9(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = volumeDialogControllerImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        VolumeDialogControllerImpl volumeDialogControllerImpl = this.f$0;
        switch (i) {
            case 0:
                int i2 = volumeDialogControllerImpl.mWakefulnessLifecycle.mWakefulness;
                boolean z = i2 == 0 || i2 == 3 || !volumeDialogControllerImpl.mDeviceInteractive;
                volumeDialogControllerImpl.mState.aodEnabled = z;
                if (z) {
                    volumeDialogControllerImpl.onVolumeChangedW(3, 1);
                    break;
                }
                break;
            case 1:
                Context context = volumeDialogControllerImpl.mContext;
                BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                String str = ScspUtils.FILE_PATH_ROOT;
                Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context, null, new Handler(Looper.getMainLooper())));
                volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
                break;
            case 2:
                volumeDialogControllerImpl.mIsSupportTvVolumeControl = (Boolean) obj;
                break;
            case 3:
                volumeDialogControllerImpl.mIsDLNAEnabled = (Boolean) obj;
                break;
            case 4:
                Boolean bool = (Boolean) obj;
                String str2 = VolumeDialogControllerImpl.TAG;
                if (volumeDialogControllerImpl.streamStateW(20).muted != bool.booleanValue()) {
                    volumeDialogControllerImpl.updateStreamMuteW(20, bool.booleanValue());
                }
                if (volumeDialogControllerImpl.mIsVolumeDialogShowing) {
                    int i3 = volumeDialogControllerImpl.mSmartViewFlag;
                    volumeDialogControllerImpl.onVolumeChangedW(20, i3 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i3 : 1);
                    if (volumeDialogControllerImpl.isSmartViewEnabled()) {
                        volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
                        break;
                    }
                }
                break;
            case 5:
                String str3 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                volumeDialogControllerImpl.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                break;
            case 6:
                String str4 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                volumeDialogControllerImpl.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                break;
            case 7:
                Integer num = (Integer) obj;
                VolumeDialogControllerImpl.C c = volumeDialogControllerImpl.mCallbacks;
                String str5 = VolumeDialogControllerImpl.TAG;
                VolumeDialogController.State state = volumeDialogControllerImpl.mState;
                if (num != null) {
                    if (!state.states.contains(99)) {
                        VolumeDialogController.StreamState streamStateW = volumeDialogControllerImpl.streamStateW(99);
                        streamStateW.dynamic = true;
                        AudioSharingInteractor audioSharingInteractor = volumeDialogControllerImpl.mAudioSharingInteractor;
                        audioSharingInteractor.getClass();
                        streamStateW.levelMin = 0;
                        streamStateW.levelMax = audioSharingInteractor.getVolumeMax();
                        streamStateW.routedToBluetooth = true;
                        if (streamStateW.level != num.intValue()) {
                            streamStateW.level = num.intValue();
                        }
                        String string = volumeDialogControllerImpl.mContext.getString(R.string.audio_sharing_description);
                        if (!Objects.equals(streamStateW.remoteLabel, string)) {
                            streamStateW.name = -1;
                            streamStateW.remoteLabel = string;
                        }
                        Slog.d(str5, "updateState, new audio sharing stream volume = " + num);
                        c.onStateChanged(state);
                        break;
                    } else {
                        VolumeDialogController.StreamState streamState = state.states.get(99);
                        if (streamState.level != num.intValue()) {
                            streamState.level = num.intValue();
                            Slog.d(str5, "updateState, audio sharing stream volume = " + num);
                            c.onStateChanged(state);
                            break;
                        }
                    }
                } else if (state.states.contains(99)) {
                    state.states.remove(99);
                    Slog.d(str5, "Remove audio sharing stream");
                    c.onStateChanged(state);
                    break;
                }
                break;
            default:
                String str6 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                ((Boolean) obj).getClass();
                break;
        }
    }
}
