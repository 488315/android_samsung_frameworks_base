package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioSystem;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public class AudioMix implements Parcelable {
    private static final int CALLBACK_FLAGS_ALL = 1;
    public static final int CALLBACK_FLAG_NOTIFY_ACTIVITY = 1;
    public static final Parcelable.Creator<AudioMix> CREATOR =
            new Parcelable.Creator<AudioMix>() { // from class: android.media.audiopolicy.AudioMix.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioMix createFromParcel(Parcel p) {
                    Builder mixBuilder = new Builder();
                    mixBuilder.setRouteFlags(p.readInt());
                    mixBuilder.setCallbackFlags(p.readInt());
                    mixBuilder.setDevice(p.readInt(), p.readString8());
                    mixBuilder.setFormat(AudioFormat.CREATOR.createFromParcel(p));
                    mixBuilder.setMixingRule(AudioMixingRule.CREATOR.createFromParcel(p));
                    mixBuilder.setToken(p.readStrongBinder());
                    mixBuilder.setVirtualDeviceId(p.readInt());
                    return mixBuilder.build();
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioMix[] newArray(int size) {
                    return new AudioMix[size];
                }
            };
    public static final int MIX_STATE_DISABLED = -1;
    public static final int MIX_STATE_IDLE = 0;
    public static final int MIX_STATE_MIXING = 1;
    public static final int MIX_TYPE_INVALID = -1;
    public static final int MIX_TYPE_PLAYERS = 0;
    public static final int MIX_TYPE_RECORDERS = 1;
    private static final int PRIVILEDGED_CAPTURE_MAX_BYTES_PER_SAMPLE = 2;
    private static final int PRIVILEDGED_CAPTURE_MAX_CHANNEL_NUMBER = 1;
    private static final int PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE = 16000;
    public static final int ROUTE_FLAG_LOOP_BACK = 2;
    public static final int ROUTE_FLAG_LOOP_BACK_RENDER = 3;
    public static final int ROUTE_FLAG_RENDER = 1;
    private static final int ROUTE_FLAG_SUPPORTED = 3;
    int mCallbackFlags;
    String mDeviceAddress;
    final int mDeviceSystemType;
    private AudioFormat mFormat;
    int mMixState;
    private int mMixType;
    private int mRouteFlags;
    private AudioMixingRule mRule;
    private final IBinder mToken;
    private int mVirtualDeviceId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RouteFlags {}

    private AudioMix(
            AudioMixingRule rule,
            AudioFormat format,
            int routeFlags,
            int callbackFlags,
            int deviceType,
            String deviceAddress,
            IBinder token,
            int virtualDeviceId) {
        this.mMixType = -1;
        this.mMixState = -1;
        this.mRule = (AudioMixingRule) Objects.requireNonNull(rule);
        this.mFormat = (AudioFormat) Objects.requireNonNull(format);
        this.mRouteFlags = routeFlags;
        this.mMixType = rule.getTargetMixType();
        this.mCallbackFlags = callbackFlags;
        this.mDeviceSystemType = deviceType;
        this.mDeviceAddress = deviceAddress == null ? new String("") : deviceAddress;
        this.mToken = token;
        this.mVirtualDeviceId = virtualDeviceId;
    }

    public int getMixState() {
        return this.mMixState;
    }

    public int getRouteFlags() {
        return this.mRouteFlags;
    }

    public AudioFormat getFormat() {
        return this.mFormat;
    }

    public AudioMixingRule getRule() {
        return this.mRule;
    }

    public int getMixType() {
        return this.mMixType;
    }

    void setRegistration(String regId) {
        this.mDeviceAddress = regId;
    }

    public void setAudioMixingRule(AudioMixingRule rule) {
        if (this.mRule.getTargetMixType() != rule.getTargetMixType()) {
            throw new UnsupportedOperationException(
                    "Target mix role of updated rule doesn't match the mix role of the AudioMix");
        }
        this.mRule = (AudioMixingRule) Objects.requireNonNull(rule);
    }

    public String getRegistration() {
        return this.mDeviceAddress;
    }

    public boolean isAffectingUsage(int usage) {
        return this.mRule.isAffectingUsage(usage);
    }

    public boolean containsMatchAttributeRuleForUsage(int usage) {
        return this.mRule.containsMatchAttributeRuleForUsage(usage);
    }

    public boolean isRoutedToDevice(int deviceType, String deviceAddress) {
        return (this.mRouteFlags & 1) == 1
                && deviceType == this.mDeviceSystemType
                && deviceAddress.equals(this.mDeviceAddress);
    }

    public static String canBeUsedForPrivilegedMediaCapture(AudioFormat format) {
        int sampleRate = format.getSampleRate();
        if (sampleRate > PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE || sampleRate <= 0) {
            return "Privileged audio capture sample rate "
                    + sampleRate
                    + " can not be over "
                    + PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE
                    + "kHz";
        }
        int channelCount = format.getChannelCount();
        if (channelCount > 1 || channelCount <= 0) {
            return "Privileged audio capture channel count " + channelCount + " can not be over 1";
        }
        int encoding = format.getEncoding();
        if (!AudioFormat.isPublicEncoding(encoding) || !AudioFormat.isEncodingLinearPcm(encoding)) {
            return "Privileged audio capture encoding " + encoding + "is not linear";
        }
        if (AudioFormat.getBytesPerSample(encoding) > 2) {
            return "Privileged audio capture encoding "
                    + encoding
                    + " can not be over 2 bytes per sample";
        }
        return null;
    }

    public boolean isForCallRedirection() {
        return this.mRule.isForCallRedirection();
    }

    public boolean matchesVirtualDeviceId(int deviceId) {
        return this.mVirtualDeviceId == deviceId;
    }

    public boolean equals(Object o) {
        boolean tokenMatch;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioMix that = (AudioMix) o;
        if (Flags.audioMixOwnership()) {
            tokenMatch = Objects.equals(this.mToken, that.mToken);
        } else {
            tokenMatch = true;
        }
        if (Objects.equals(Integer.valueOf(this.mRouteFlags), Integer.valueOf(that.mRouteFlags))
                && Objects.equals(this.mRule, that.mRule)
                && Objects.equals(Integer.valueOf(this.mMixType), Integer.valueOf(that.mMixType))
                && Objects.equals(this.mFormat, that.mFormat)
                && tokenMatch) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (Flags.audioMixOwnership()) {
            return Objects.hash(
                    Integer.valueOf(this.mRouteFlags),
                    this.mRule,
                    Integer.valueOf(this.mMixType),
                    this.mFormat,
                    this.mToken);
        }
        return Objects.hash(
                Integer.valueOf(this.mRouteFlags),
                this.mRule,
                Integer.valueOf(this.mMixType),
                this.mFormat);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRouteFlags);
        dest.writeInt(this.mCallbackFlags);
        dest.writeInt(this.mDeviceSystemType);
        dest.writeString8(this.mDeviceAddress);
        this.mFormat.writeToParcel(dest, flags);
        this.mRule.writeToParcel(dest, flags);
        dest.writeStrongBinder(this.mToken);
        dest.writeInt(this.mVirtualDeviceId);
    }

    public void setVirtualDeviceId(int virtualDeviceId) {
        this.mVirtualDeviceId = virtualDeviceId;
    }

    public static class Builder {
        private int mCallbackFlags;
        private String mDeviceAddress;
        private int mDeviceSystemType;
        private AudioFormat mFormat;
        private int mRouteFlags;
        private AudioMixingRule mRule;
        private IBinder mToken;
        private int mVirtualDeviceId;

        Builder() {
            this.mRule = null;
            this.mFormat = null;
            this.mRouteFlags = 0;
            this.mCallbackFlags = 0;
            this.mToken = null;
            this.mVirtualDeviceId = 0;
            this.mDeviceSystemType = 0;
            this.mDeviceAddress = null;
        }

        public Builder(AudioMixingRule rule) throws IllegalArgumentException {
            this.mRule = null;
            this.mFormat = null;
            this.mRouteFlags = 0;
            this.mCallbackFlags = 0;
            this.mToken = null;
            this.mVirtualDeviceId = 0;
            this.mDeviceSystemType = 0;
            this.mDeviceAddress = null;
            if (rule == null) {
                throw new IllegalArgumentException("Illegal null AudioMixingRule argument");
            }
            this.mRule = rule;
        }

        Builder setMixingRule(AudioMixingRule rule) throws IllegalArgumentException {
            if (rule == null) {
                throw new IllegalArgumentException("Illegal null AudioMixingRule argument");
            }
            this.mRule = rule;
            return this;
        }

        Builder setToken(IBinder token) {
            this.mToken = token;
            return this;
        }

        Builder setVirtualDeviceId(int virtualDeviceId) {
            this.mVirtualDeviceId = virtualDeviceId;
            return this;
        }

        Builder setCallbackFlags(int flags) throws IllegalArgumentException {
            if (flags != 0 && (flags & 1) == 0) {
                throw new IllegalArgumentException(
                        "Illegal callback flags 0x" + Integer.toHexString(flags).toUpperCase());
            }
            this.mCallbackFlags = flags;
            return this;
        }

        public Builder setDevice(int deviceType, String address) {
            this.mDeviceSystemType = deviceType;
            this.mDeviceAddress = address;
            return this;
        }

        public Builder setFormat(AudioFormat format) throws IllegalArgumentException {
            if (format == null) {
                throw new IllegalArgumentException("Illegal null AudioFormat argument");
            }
            this.mFormat = format;
            return this;
        }

        public Builder setRouteFlags(int routeFlags) throws IllegalArgumentException {
            if (routeFlags == 0) {
                throw new IllegalArgumentException("Illegal empty route flags");
            }
            if ((routeFlags & 3) == 0) {
                throw new IllegalArgumentException(
                        "Invalid route flags 0x"
                                + Integer.toHexString(routeFlags)
                                + "when configuring an AudioMix");
            }
            if ((routeFlags & (-4)) != 0) {
                throw new IllegalArgumentException(
                        "Unknown route flags 0x"
                                + Integer.toHexString(routeFlags)
                                + "when configuring an AudioMix");
            }
            this.mRouteFlags = routeFlags;
            return this;
        }

        public Builder setDevice(AudioDeviceInfo device) throws IllegalArgumentException {
            if (device == null) {
                throw new IllegalArgumentException("Illegal null AudioDeviceInfo argument");
            }
            if (!device.isSink()) {
                throw new IllegalArgumentException("Unsupported device type on mix, not a sink");
            }
            this.mDeviceSystemType =
                    AudioDeviceInfo.convertDeviceTypeToInternalDevice(device.getType());
            this.mDeviceAddress = device.getAddress();
            return this;
        }

        public AudioMix build() throws IllegalArgumentException {
            String error;
            if (this.mRule == null) {
                throw new IllegalArgumentException("Illegal null AudioMixingRule");
            }
            if (this.mRouteFlags == 0) {
                this.mRouteFlags = 2;
            }
            if (this.mFormat == null) {
                int rate = AudioSystem.getPrimaryOutputSamplingRate();
                if (rate <= 0) {
                    rate = 44100;
                }
                this.mFormat = new AudioFormat.Builder().setSampleRate(rate).build();
            } else if ((this.mFormat.getPropertySetMask() & 4) != 0
                    && this.mFormat.getChannelCount() == 1
                    && this.mFormat.getChannelMask() == 16) {
                this.mFormat = new AudioFormat.Builder(this.mFormat).setChannelMask(4).build();
            }
            if ((this.mRouteFlags & 2) == 2) {
                if (this.mDeviceSystemType == 0) {
                    this.mDeviceSystemType =
                            getLoopbackDeviceSystemTypeForAudioMixingRule(this.mRule);
                } else if (!AudioSystem.isRemoteSubmixDevice(this.mDeviceSystemType)) {
                    throw new IllegalArgumentException(
                            "Device "
                                    + AudioSystem.getDeviceName(this.mDeviceSystemType)
                                    + "is not supported for loopback mix.");
                }
            }
            if ((this.mRouteFlags & 1) == 1) {
                if (this.mDeviceSystemType == 0) {
                    throw new IllegalArgumentException(
                            "Can't have flag ROUTE_FLAG_RENDER without an audio device");
                }
                if (AudioSystem.DEVICE_IN_ALL_SET.contains(
                        Integer.valueOf(this.mDeviceSystemType))) {
                    throw new IllegalArgumentException(
                            "Input device is not supported with ROUTE_FLAG_RENDER");
                }
                if (this.mRule.getTargetMixType() == 1) {
                    throw new IllegalArgumentException(
                            "ROUTE_FLAG_RENDER/ROUTE_FLAG_LOOP_BACK_RENDER is not supported for"
                                    + " non-playback mix rule");
                }
            }
            if (this.mRule.allowPrivilegedMediaPlaybackCapture()
                    && (error = AudioMix.canBeUsedForPrivilegedMediaCapture(this.mFormat))
                            != null) {
                throw new IllegalArgumentException(error);
            }
            if (this.mToken == null) {
                this.mToken = new Binder();
            }
            return new AudioMix(
                    this.mRule,
                    this.mFormat,
                    this.mRouteFlags,
                    this.mCallbackFlags,
                    this.mDeviceSystemType,
                    this.mDeviceAddress,
                    this.mToken,
                    this.mVirtualDeviceId);
        }

        private int getLoopbackDeviceSystemTypeForAudioMixingRule(AudioMixingRule rule) {
            switch (this.mRule.getTargetMixType()) {
                case 0:
                    return 32768;
                case 1:
                    return AudioSystem.DEVICE_IN_REMOTE_SUBMIX;
                default:
                    throw new IllegalArgumentException(
                            "Unknown mixing rule type - 0x"
                                    + Integer.toHexString(rule.getTargetMixType()));
            }
        }
    }
}
