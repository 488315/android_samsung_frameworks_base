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
/* loaded from: classes.dex */
public class AudioMix implements Parcelable {
    private static final int CALLBACK_FLAGS_ALL = 1;
    public static final int CALLBACK_FLAG_NOTIFY_ACTIVITY = 1;
    public static final Parcelable.Creator<AudioMix> CREATOR = new Parcelable.Creator<AudioMix>() { // from class: android.media.audiopolicy.AudioMix.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMix createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            builder.setRouteFlags(parcel.readInt());
            builder.setCallbackFlags(parcel.readInt());
            builder.setDevice(parcel.readInt(), parcel.readString8());
            builder.setFormat(AudioFormat.CREATOR.createFromParcel(parcel));
            builder.setMixingRule(AudioMixingRule.CREATOR.createFromParcel(parcel));
            builder.setToken(parcel.readStrongBinder());
            builder.setVirtualDeviceId(parcel.readInt());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMix[] newArray(int i) {
            return new AudioMix[i];
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
    public @interface RouteFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AudioMix(AudioMixingRule audioMixingRule, AudioFormat audioFormat, int i, int i2, int i3, String str, IBinder iBinder, int i4) {
        this.mMixType = -1;
        this.mMixState = -1;
        this.mRule = (AudioMixingRule) Objects.requireNonNull(audioMixingRule);
        this.mFormat = (AudioFormat) Objects.requireNonNull(audioFormat);
        this.mRouteFlags = i;
        this.mMixType = audioMixingRule.getTargetMixType();
        this.mCallbackFlags = i2;
        this.mDeviceSystemType = i3;
        this.mDeviceAddress = str == null ? new String("") : str;
        this.mToken = iBinder;
        this.mVirtualDeviceId = i4;
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

    void setRegistration(String str) {
        this.mDeviceAddress = str;
    }

    public void setAudioMixingRule(AudioMixingRule audioMixingRule) {
        if (this.mRule.getTargetMixType() != audioMixingRule.getTargetMixType()) {
            throw new UnsupportedOperationException("Target mix role of updated rule doesn't match the mix role of the AudioMix");
        }
        this.mRule = (AudioMixingRule) Objects.requireNonNull(audioMixingRule);
    }

    public String getRegistration() {
        return this.mDeviceAddress;
    }

    public boolean isAffectingUsage(int i) {
        return this.mRule.isAffectingUsage(i);
    }

    public boolean containsMatchAttributeRuleForUsage(int i) {
        return this.mRule.containsMatchAttributeRuleForUsage(i);
    }

    public boolean isRoutedToDevice(int i, String str) {
        return (this.mRouteFlags & 1) == 1 && i == this.mDeviceSystemType && str.equals(this.mDeviceAddress);
    }

    public static String canBeUsedForPrivilegedMediaCapture(AudioFormat audioFormat) {
        int sampleRate = audioFormat.getSampleRate();
        if (sampleRate > PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE || sampleRate <= 0) {
            return "Privileged audio capture sample rate " + sampleRate + " can not be over 16000kHz";
        }
        int channelCount = audioFormat.getChannelCount();
        if (channelCount > 1 || channelCount <= 0) {
            return "Privileged audio capture channel count " + channelCount + " can not be over 1";
        }
        int encoding = audioFormat.getEncoding();
        if (!AudioFormat.isPublicEncoding(encoding) || !AudioFormat.isEncodingLinearPcm(encoding)) {
            return "Privileged audio capture encoding " + encoding + "is not linear";
        }
        if (AudioFormat.getBytesPerSample(encoding) <= 2) {
            return null;
        }
        return "Privileged audio capture encoding " + encoding + " can not be over 2 bytes per sample";
    }

    public boolean isForCallRedirection() {
        return this.mRule.isForCallRedirection();
    }

    public boolean matchesVirtualDeviceId(int i) {
        return this.mVirtualDeviceId == i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioMix audioMix = (AudioMix) obj;
            boolean equals = Flags.audioMixOwnership() ? Objects.equals(this.mToken, audioMix.mToken) : true;
            if (Objects.equals(Integer.valueOf(this.mRouteFlags), Integer.valueOf(audioMix.mRouteFlags)) && Objects.equals(this.mRule, audioMix.mRule) && Objects.equals(Integer.valueOf(this.mMixType), Integer.valueOf(audioMix.mMixType)) && Objects.equals(this.mFormat, audioMix.mFormat) && equals) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (Flags.audioMixOwnership()) {
            return Objects.hash(Integer.valueOf(this.mRouteFlags), this.mRule, Integer.valueOf(this.mMixType), this.mFormat, this.mToken);
        }
        return Objects.hash(Integer.valueOf(this.mRouteFlags), this.mRule, Integer.valueOf(this.mMixType), this.mFormat);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRouteFlags);
        parcel.writeInt(this.mCallbackFlags);
        parcel.writeInt(this.mDeviceSystemType);
        parcel.writeString8(this.mDeviceAddress);
        this.mFormat.writeToParcel(parcel, i);
        this.mRule.writeToParcel(parcel, i);
        parcel.writeStrongBinder(this.mToken);
        parcel.writeInt(this.mVirtualDeviceId);
    }

    public void setVirtualDeviceId(int i) {
        this.mVirtualDeviceId = i;
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

        public Builder(AudioMixingRule audioMixingRule) throws IllegalArgumentException {
            this.mRule = null;
            this.mFormat = null;
            this.mRouteFlags = 0;
            this.mCallbackFlags = 0;
            this.mToken = null;
            this.mVirtualDeviceId = 0;
            this.mDeviceSystemType = 0;
            this.mDeviceAddress = null;
            if (audioMixingRule == null) {
                throw new IllegalArgumentException("Illegal null AudioMixingRule argument");
            }
            this.mRule = audioMixingRule;
        }

        Builder setMixingRule(AudioMixingRule audioMixingRule) throws IllegalArgumentException {
            if (audioMixingRule == null) {
                throw new IllegalArgumentException("Illegal null AudioMixingRule argument");
            }
            this.mRule = audioMixingRule;
            return this;
        }

        Builder setToken(IBinder iBinder) {
            this.mToken = iBinder;
            return this;
        }

        Builder setVirtualDeviceId(int i) {
            this.mVirtualDeviceId = i;
            return this;
        }

        Builder setCallbackFlags(int i) throws IllegalArgumentException {
            if (i != 0 && (i & 1) == 0) {
                throw new IllegalArgumentException("Illegal callback flags 0x" + Integer.toHexString(i).toUpperCase());
            }
            this.mCallbackFlags = i;
            return this;
        }

        public Builder setDevice(int i, String str) {
            this.mDeviceSystemType = i;
            this.mDeviceAddress = str;
            return this;
        }

        public Builder setFormat(AudioFormat audioFormat) throws IllegalArgumentException {
            if (audioFormat == null) {
                throw new IllegalArgumentException("Illegal null AudioFormat argument");
            }
            this.mFormat = audioFormat;
            return this;
        }

        public Builder setRouteFlags(int i) throws IllegalArgumentException {
            if (i == 0) {
                throw new IllegalArgumentException("Illegal empty route flags");
            }
            if ((i & 3) == 0) {
                throw new IllegalArgumentException("Invalid route flags 0x" + Integer.toHexString(i) + "when configuring an AudioMix");
            }
            if ((i & (-4)) != 0) {
                throw new IllegalArgumentException("Unknown route flags 0x" + Integer.toHexString(i) + "when configuring an AudioMix");
            }
            this.mRouteFlags = i;
            return this;
        }

        public Builder setDevice(AudioDeviceInfo audioDeviceInfo) throws IllegalArgumentException {
            if (audioDeviceInfo == null) {
                throw new IllegalArgumentException("Illegal null AudioDeviceInfo argument");
            }
            if (!audioDeviceInfo.isSink()) {
                throw new IllegalArgumentException("Unsupported device type on mix, not a sink");
            }
            this.mDeviceSystemType = AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType());
            this.mDeviceAddress = audioDeviceInfo.getAddress();
            return this;
        }

        public AudioMix build() throws IllegalArgumentException {
            String canBeUsedForPrivilegedMediaCapture;
            if (this.mRule == null) {
                throw new IllegalArgumentException("Illegal null AudioMixingRule");
            }
            if (this.mRouteFlags == 0) {
                this.mRouteFlags = 2;
            }
            AudioFormat audioFormat = this.mFormat;
            if (audioFormat == null) {
                int primaryOutputSamplingRate = AudioSystem.getPrimaryOutputSamplingRate();
                if (primaryOutputSamplingRate <= 0) {
                    primaryOutputSamplingRate = 44100;
                }
                this.mFormat = new AudioFormat.Builder().setSampleRate(primaryOutputSamplingRate).build();
            } else if ((audioFormat.getPropertySetMask() & 4) != 0 && this.mFormat.getChannelCount() == 1 && this.mFormat.getChannelMask() == 16) {
                this.mFormat = new AudioFormat.Builder(this.mFormat).setChannelMask(4).build();
            }
            if ((this.mRouteFlags & 2) == 2) {
                int i = this.mDeviceSystemType;
                if (i == 0) {
                    this.mDeviceSystemType = getLoopbackDeviceSystemTypeForAudioMixingRule(this.mRule);
                } else if (!AudioSystem.isRemoteSubmixDevice(i)) {
                    throw new IllegalArgumentException("Device " + AudioSystem.getDeviceName(this.mDeviceSystemType) + "is not supported for loopback mix.");
                }
            }
            if ((this.mRouteFlags & 1) == 1) {
                if (this.mDeviceSystemType == 0) {
                    throw new IllegalArgumentException("Can't have flag ROUTE_FLAG_RENDER without an audio device");
                }
                if (AudioSystem.DEVICE_IN_ALL_SET.contains(Integer.valueOf(this.mDeviceSystemType))) {
                    throw new IllegalArgumentException("Input device is not supported with ROUTE_FLAG_RENDER");
                }
                if (this.mRule.getTargetMixType() == 1) {
                    throw new IllegalArgumentException("ROUTE_FLAG_RENDER/ROUTE_FLAG_LOOP_BACK_RENDER is not supported for non-playback mix rule");
                }
            }
            if (this.mRule.allowPrivilegedMediaPlaybackCapture() && (canBeUsedForPrivilegedMediaCapture = AudioMix.canBeUsedForPrivilegedMediaCapture(this.mFormat)) != null) {
                throw new IllegalArgumentException(canBeUsedForPrivilegedMediaCapture);
            }
            if (this.mToken == null) {
                this.mToken = new Binder();
            }
            return new AudioMix(this.mRule, this.mFormat, this.mRouteFlags, this.mCallbackFlags, this.mDeviceSystemType, this.mDeviceAddress, this.mToken, this.mVirtualDeviceId);
        }

        private int getLoopbackDeviceSystemTypeForAudioMixingRule(AudioMixingRule audioMixingRule) {
            int targetMixType = this.mRule.getTargetMixType();
            if (targetMixType == 0) {
                return 32768;
            }
            if (targetMixType == 1) {
                return AudioSystem.DEVICE_IN_REMOTE_SUBMIX;
            }
            throw new IllegalArgumentException("Unknown mixing rule type - 0x" + Integer.toHexString(audioMixingRule.getTargetMixType()));
        }
    }
}
