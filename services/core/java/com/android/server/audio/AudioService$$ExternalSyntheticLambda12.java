package com.android.server.audio;

import android.media.AudioDeviceAttributes;
import android.media.IMuteAwaitConnectionCallback;
import android.os.RemoteException;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioService$$ExternalSyntheticLambda12 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioService f$0;
    public final /* synthetic */ AudioDeviceAttributes f$1;
    public final /* synthetic */ int[] f$2;

    public /* synthetic */ AudioService$$ExternalSyntheticLambda12(AudioService audioService, AudioDeviceAttributes audioDeviceAttributes, int[] iArr, int i) {
        this.$r8$classId = i;
        this.f$0 = audioService;
        this.f$1 = audioDeviceAttributes;
        this.f$2 = iArr;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                AudioService audioService = this.f$0;
                AudioDeviceAttributes audioDeviceAttributes = this.f$1;
                int[] iArr = this.f$2;
                IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback = (IMuteAwaitConnectionCallback) obj;
                Boolean bool = (Boolean) obj2;
                int i = AudioService.BECOMING_NOISY_DELAY_MS;
                audioService.getClass();
                try {
                    if (!bool.booleanValue()) {
                        audioDeviceAttributes = AudioService.anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes);
                    }
                    iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(3, audioDeviceAttributes, iArr);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            case 1:
                AudioService audioService2 = this.f$0;
                AudioDeviceAttributes audioDeviceAttributes2 = this.f$1;
                int[] iArr2 = this.f$2;
                IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback2 = (IMuteAwaitConnectionCallback) obj;
                Boolean bool2 = (Boolean) obj2;
                int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                audioService2.getClass();
                try {
                    if (!bool2.booleanValue()) {
                        audioDeviceAttributes2 = AudioService.anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes2);
                    }
                    iMuteAwaitConnectionCallback2.dispatchOnMutedUntilConnection(audioDeviceAttributes2, iArr2);
                    break;
                } catch (RemoteException unused2) {
                    return;
                }
            default:
                AudioService audioService3 = this.f$0;
                AudioDeviceAttributes audioDeviceAttributes3 = this.f$1;
                int[] iArr3 = this.f$2;
                IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback3 = (IMuteAwaitConnectionCallback) obj;
                Boolean bool3 = (Boolean) obj2;
                int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                audioService3.getClass();
                try {
                    if (!bool3.booleanValue()) {
                        audioDeviceAttributes3 = AudioService.anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes3);
                    }
                    iMuteAwaitConnectionCallback3.dispatchOnUnmutedEvent(1, audioDeviceAttributes3, iArr3);
                    break;
                } catch (RemoteException unused3) {
                    return;
                }
        }
    }
}
