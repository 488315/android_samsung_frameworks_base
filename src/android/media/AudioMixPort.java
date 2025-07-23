package android.media;

import java.util.List;

/* loaded from: classes2.dex */
public class AudioMixPort extends AudioPort {
    private final int mIoHandle;

    AudioMixPort(AudioHandle audioHandle, int i, int i2, String str, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, AudioGain[] audioGainArr) {
        super(audioHandle, i2, str, iArr, iArr2, iArr3, iArr4, audioGainArr);
        this.mIoHandle = i;
    }

    AudioMixPort(AudioHandle audioHandle, int i, int i2, String str, List<AudioProfile> list, AudioGain[] audioGainArr) {
        super(audioHandle, i2, str, list, audioGainArr, null);
        this.mIoHandle = i;
    }

    @Override // android.media.AudioPort
    public AudioMixPortConfig buildConfig(int i, int i2, int i3, AudioGainConfig audioGainConfig) {
        return new AudioMixPortConfig(this, i, i2, i3, audioGainConfig);
    }

    public int ioHandle() {
        return this.mIoHandle;
    }

    @Override // android.media.AudioPort
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof AudioMixPort) && this.mIoHandle == ((AudioMixPort) obj).ioHandle()) {
            return super.equals(obj);
        }
        return false;
    }
}
