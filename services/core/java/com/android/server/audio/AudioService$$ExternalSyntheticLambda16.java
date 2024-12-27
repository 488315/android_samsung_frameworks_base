package com.android.server.audio;

import android.media.AudioDeviceAttributes;
import android.media.IMuteAwaitConnectionCallback;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.os.RemoteException;
import android.util.Log;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioService$$ExternalSyntheticLambda16 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioService$$ExternalSyntheticLambda16(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                ((AudioService) obj2).getClass();
                return;
            case 1:
                AudioService audioService = (AudioService) obj2;
                final AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) obj;
                synchronized (audioService.mMuteAwaitConnectionLock) {
                    try {
                        if (audioDeviceAttributes.equals(audioService.mMutingExpectedDevice)) {
                            Log.i(
                                    "AS.AudioService",
                                    "muteAwaitConnection timeout, clearing expected device "
                                            + audioService.mMutingExpectedDevice);
                            final int[] iArr = audioService.mMutedUsagesAwaitingConnection;
                            audioService.mMutingExpectedDevice = null;
                            audioService.mMutedUsagesAwaitingConnection = null;
                            audioService.dispatchMuteAwaitConnection(
                                    new BiConsumer() { // from class:
                                                       // com.android.server.audio.AudioService$$ExternalSyntheticLambda19
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj3, Object obj4) {
                                            AudioDeviceAttributes audioDeviceAttributes2 =
                                                    audioDeviceAttributes;
                                            int[] iArr2 = iArr;
                                            IMuteAwaitConnectionCallback
                                                    iMuteAwaitConnectionCallback =
                                                            (IMuteAwaitConnectionCallback) obj3;
                                            int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                                            try {
                                                iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(
                                                        2, audioDeviceAttributes2, iArr2);
                                            } catch (RemoteException unused) {
                                            }
                                        }
                                    });
                            return;
                        }
                        return;
                    } finally {
                    }
                }
            case 2:
                AudioService audioService2 = (AudioService) obj2;
                Integer num = (Integer) obj;
                SpatializerHelper spatializerHelper = audioService2.mSpatializerHelper;
                float intValue = (float) ((num.intValue() * 3.141592653589793d) / 180.0d);
                synchronized (spatializerHelper) {
                    if (spatializerHelper.checkSpatializer("setDisplayOrientation")) {
                        try {
                            spatializerHelper.mSpat.setDisplayOrientation(intValue);
                        } catch (RemoteException e) {
                            Log.e("AS.SpatializerHelper", "Error calling setDisplayOrientation", e);
                        }
                    }
                }
                AudioService.sendMsg(
                        audioService2.mAudioHandler, 48, 0, 0, 0, "rotation=" + num, 0);
                return;
            case 3:
                AudioService audioService3 = (AudioService) obj2;
                Boolean bool = (Boolean) obj;
                SpatializerHelper spatializerHelper2 = audioService3.mSpatializerHelper;
                boolean booleanValue = bool.booleanValue();
                synchronized (spatializerHelper2) {
                    if (spatializerHelper2.checkSpatializer("setFoldState")) {
                        try {
                            spatializerHelper2.mSpat.setFoldState(booleanValue);
                        } catch (RemoteException e2) {
                            Log.e("AS.SpatializerHelper", "Error calling setFoldState", e2);
                        }
                    }
                }
                AudioService.sendMsg(
                        audioService3.mAudioHandler,
                        49,
                        0,
                        0,
                        0,
                        "device_folded=".concat(bool.booleanValue() ? "on" : "off"),
                        0);
                return;
            default:
                ((AudioMix) obj).setAudioMixingRule((AudioMixingRule) obj2);
                return;
        }
    }
}
