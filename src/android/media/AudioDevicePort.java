package android.media;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class AudioDevicePort extends AudioPort {
    private final String mAddress;
    private final int[] mEncapsulationMetadataTypes;
    private final int[] mEncapsulationModes;
    private final int mSpeakerLayoutChannelMask;
    private final int mType;

    public static AudioDevicePort createForTesting(int i, String str, String str2) {
        return new AudioDevicePort(new AudioHandle(0), str, null, null, null, null, null, i, str2, null, null);
    }

    public static AudioDevicePort createForTesting(int i) {
        return new AudioDevicePort(new AudioHandle(0), "testAudioDevicePort", new ArrayList(), null, 2, "testAddress", i, null, null, new ArrayList());
    }

    AudioDevicePort(AudioHandle audioHandle, String str, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, AudioGain[] audioGainArr, int i, String str2, int[] iArr5, int[] iArr6) {
        super(audioHandle, !AudioManager.isInputDevice(i) ? 2 : 1, str, iArr, iArr2, iArr3, iArr4, audioGainArr);
        this.mType = i;
        this.mAddress = str2;
        this.mSpeakerLayoutChannelMask = 0;
        this.mEncapsulationModes = iArr5;
        this.mEncapsulationMetadataTypes = iArr6;
    }

    AudioDevicePort(AudioHandle audioHandle, String str, List<AudioProfile> list, AudioGain[] audioGainArr, int i, String str2, int i2, int[] iArr, int[] iArr2, List<AudioDescriptor> list2) {
        super(audioHandle, AudioManager.isInputDevice(i) ? 1 : 2, str, list, audioGainArr, list2);
        this.mType = i;
        this.mAddress = str2;
        this.mSpeakerLayoutChannelMask = i2;
        this.mEncapsulationModes = iArr;
        this.mEncapsulationMetadataTypes = iArr2;
    }

    public int type() {
        return this.mType;
    }

    public String address() {
        return this.mAddress;
    }

    public int speakerLayoutChannelMask() {
        return this.mSpeakerLayoutChannelMask;
    }

    public int[] encapsulationModes() {
        int[] iArr = this.mEncapsulationModes;
        if (iArr == null) {
            return new int[0];
        }
        return Arrays.stream(iArr).boxed().filter(new Predicate() { // from class: android.media.AudioDevicePort$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AudioDevicePort.lambda$encapsulationModes$0((Integer) obj);
            }
        }).mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    static /* synthetic */ boolean lambda$encapsulationModes$0(Integer num) {
        return num.intValue() != 2;
    }

    public int[] encapsulationMetadataTypes() {
        int[] iArr = this.mEncapsulationMetadataTypes;
        if (iArr == null) {
            return new int[0];
        }
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    @Override // android.media.AudioPort
    public AudioDevicePortConfig buildConfig(int i, int i2, int i3, AudioGainConfig audioGainConfig) {
        return new AudioDevicePortConfig(this, i, i2, i3, audioGainConfig);
    }

    @Override // android.media.AudioPort
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AudioDevicePort)) {
            return false;
        }
        AudioDevicePort audioDevicePort = (AudioDevicePort) obj;
        if (this.mType != audioDevicePort.type()) {
            return false;
        }
        if ((this.mAddress != null || audioDevicePort.address() == null) && this.mAddress.equals(audioDevicePort.address())) {
            return super.equals(obj);
        }
        return false;
    }

    public boolean isSameAs(AudioDevicePort audioDevicePort) {
        if (this.mType != audioDevicePort.type()) {
            return false;
        }
        return (this.mAddress != null || audioDevicePort.address() == null) && this.mAddress.equals(audioDevicePort.address());
    }

    @Override // android.media.AudioPort
    public String toString() {
        String outputDeviceName;
        if (this.mRole == 1) {
            outputDeviceName = AudioSystem.getInputDeviceName(this.mType);
        } else {
            outputDeviceName = AudioSystem.getOutputDeviceName(this.mType);
        }
        return "{" + super.toString() + ", mType: " + outputDeviceName + ", mAddress: " + Utils.anonymizeBluetoothAddress(this.mType, this.mAddress) + "}";
    }
}
