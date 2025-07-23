package android.media;

/* loaded from: classes2.dex */
public class AudioDevicePortConfig extends AudioPortConfig {
    AudioDevicePortConfig(AudioDevicePort audioDevicePort, int i, int i2, int i3, AudioGainConfig audioGainConfig) {
        super(audioDevicePort, i, i2, i3, audioGainConfig);
    }

    AudioDevicePortConfig(AudioDevicePortConfig audioDevicePortConfig) {
        this(audioDevicePortConfig.port(), audioDevicePortConfig.samplingRate(), audioDevicePortConfig.channelMask(), audioDevicePortConfig.format(), audioDevicePortConfig.gain());
    }

    @Override // android.media.AudioPortConfig
    public AudioDevicePort port() {
        return (AudioDevicePort) this.mPort;
    }
}
