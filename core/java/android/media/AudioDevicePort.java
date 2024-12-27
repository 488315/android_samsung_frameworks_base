package android.media;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class AudioDevicePort extends AudioPort {
    private final String mAddress;
    private final int[] mEncapsulationMetadataTypes;
    private final int[] mEncapsulationModes;
    private final int mType;

    public static AudioDevicePort createForTesting(int type, String name, String address) {
        return new AudioDevicePort(
                new AudioHandle(0), name, null, null, null, null, null, type, address, null, null);
    }

    AudioDevicePort(
            AudioHandle handle,
            String deviceName,
            int[] samplingRates,
            int[] channelMasks,
            int[] channelIndexMasks,
            int[] formats,
            AudioGain[] gains,
            int type,
            String address,
            int[] encapsulationModes,
            int[] encapsulationMetadataTypes) {
        super(
                handle,
                AudioManager.isInputDevice(type) ? 1 : 2,
                deviceName,
                samplingRates,
                channelMasks,
                channelIndexMasks,
                formats,
                gains);
        this.mType = type;
        this.mAddress = address;
        this.mEncapsulationModes = encapsulationModes;
        this.mEncapsulationMetadataTypes = encapsulationMetadataTypes;
    }

    AudioDevicePort(
            AudioHandle handle,
            String deviceName,
            List<AudioProfile> profiles,
            AudioGain[] gains,
            int type,
            String address,
            int[] encapsulationModes,
            int[] encapsulationMetadataTypes,
            List<AudioDescriptor> descriptors) {
        super(
                handle,
                AudioManager.isInputDevice(type) ? 1 : 2,
                deviceName,
                profiles,
                gains,
                descriptors);
        this.mType = type;
        this.mAddress = address;
        this.mEncapsulationModes = encapsulationModes;
        this.mEncapsulationMetadataTypes = encapsulationMetadataTypes;
    }

    public int type() {
        return this.mType;
    }

    public String address() {
        return this.mAddress;
    }

    public int[] encapsulationModes() {
        if (this.mEncapsulationModes == null) {
            return new int[0];
        }
        return Arrays.stream(this.mEncapsulationModes)
                .boxed()
                .filter(
                        new Predicate() { // from class:
                            // android.media.AudioDevicePort$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return AudioDevicePort.lambda$encapsulationModes$0((Integer) obj);
                            }
                        })
                .mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                .toArray();
    }

    static /* synthetic */ boolean lambda$encapsulationModes$0(Integer mode) {
        return mode.intValue() != 2;
    }

    public int[] encapsulationMetadataTypes() {
        if (this.mEncapsulationMetadataTypes == null) {
            return new int[0];
        }
        int[] encapsulationMetadataTypes = new int[this.mEncapsulationMetadataTypes.length];
        System.arraycopy(
                this.mEncapsulationMetadataTypes,
                0,
                encapsulationMetadataTypes,
                0,
                this.mEncapsulationMetadataTypes.length);
        return encapsulationMetadataTypes;
    }

    @Override // android.media.AudioPort
    public AudioDevicePortConfig buildConfig(
            int samplingRate, int channelMask, int format, AudioGainConfig gain) {
        return new AudioDevicePortConfig(this, samplingRate, channelMask, format, gain);
    }

    @Override // android.media.AudioPort
    public boolean equals(Object o) {
        if (o == null || !(o instanceof AudioDevicePort)) {
            return false;
        }
        AudioDevicePort other = (AudioDevicePort) o;
        if (this.mType != other.type()) {
            return false;
        }
        if ((this.mAddress == null && other.address() != null)
                || !this.mAddress.equals(other.address())) {
            return false;
        }
        return super.equals(o);
    }

    @Override // android.media.AudioPort
    public String toString() {
        String type;
        if (this.mRole == 1) {
            type = AudioSystem.getInputDeviceName(this.mType);
        } else {
            type = AudioSystem.getOutputDeviceName(this.mType);
        }
        return "{"
                + super.toString()
                + ", mType: "
                + type
                + ", mAddress: "
                + Utils.anonymizeBluetoothAddress(this.mType, this.mAddress)
                + "}";
    }
}
