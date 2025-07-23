package android.media;

/* loaded from: classes2.dex */
public class AudioMixPortConfig extends AudioPortConfig {
    AudioMixPortConfig(AudioMixPort audioMixPort, int i, int i2, int i3, AudioGainConfig audioGainConfig) {
        super(audioMixPort, i, i2, i3, audioGainConfig);
    }

    @Override // android.media.AudioPortConfig
    public AudioMixPort port() {
        return (AudioMixPort) this.mPort;
    }
}
