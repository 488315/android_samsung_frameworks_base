package android.media;

import android.hardware.Camera;
import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioFormat implements Parcelable {
    public static final int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_INDEX_MASK = 8;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_MASK = 4;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_ENCODING = 1;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_NONE = 0;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_SAMPLE_RATE = 2;

    @Deprecated
    public static final int CHANNEL_CONFIGURATION_DEFAULT = 1;

    @Deprecated
    public static final int CHANNEL_CONFIGURATION_INVALID = 0;

    @Deprecated
    public static final int CHANNEL_CONFIGURATION_MONO = 2;

    @Deprecated
    public static final int CHANNEL_CONFIGURATION_STEREO = 3;
    public static final int CHANNEL_INVALID = 0;
    public static final int CHANNEL_IN_2POINT0POINT2 = 6291468;
    public static final int CHANNEL_IN_2POINT1POINT2 = 7340044;
    public static final int CHANNEL_IN_3POINT0POINT2 = 6553612;
    public static final int CHANNEL_IN_3POINT1POINT2 = 7602188;
    public static final int CHANNEL_IN_5POINT1 = 1507340;
    public static final int CHANNEL_IN_BACK = 32;
    public static final int CHANNEL_IN_BACK_LEFT = 65536;
    public static final int CHANNEL_IN_BACK_PROCESSED = 512;
    public static final int CHANNEL_IN_BACK_RIGHT = 131072;
    public static final int CHANNEL_IN_CENTER = 262144;
    public static final int CHANNEL_IN_DEFAULT = 1;
    public static final int CHANNEL_IN_FRONT = 16;
    public static final int CHANNEL_IN_FRONT_BACK = 48;
    public static final int CHANNEL_IN_FRONT_PROCESSED = 256;
    public static final int CHANNEL_IN_LEFT = 4;
    public static final int CHANNEL_IN_LEFT_PROCESSED = 64;
    public static final int CHANNEL_IN_LOW_FREQUENCY = 1048576;
    public static final int CHANNEL_IN_MONO = 16;
    public static final int CHANNEL_IN_PRESSURE = 1024;
    public static final int CHANNEL_IN_RIGHT = 8;
    public static final int CHANNEL_IN_RIGHT_PROCESSED = 128;
    public static final int CHANNEL_IN_STEREO = 12;
    public static final int CHANNEL_IN_TOP_LEFT = 2097152;
    public static final int CHANNEL_IN_TOP_RIGHT = 4194304;
    public static final int CHANNEL_IN_VOICE_DNLINK = 32768;
    public static final int CHANNEL_IN_VOICE_UPLINK = 16384;
    public static final int CHANNEL_IN_X_AXIS = 2048;
    public static final int CHANNEL_IN_Y_AXIS = 4096;
    public static final int CHANNEL_IN_Z_AXIS = 8192;
    public static final int CHANNEL_OUT_13POINT0 = 30136348;
    public static final int CHANNEL_OUT_22POINT2 = 67108860;
    public static final int CHANNEL_OUT_5POINT1 = 252;
    public static final int CHANNEL_OUT_5POINT1POINT2 = 3145980;
    public static final int CHANNEL_OUT_5POINT1POINT4 = 737532;
    public static final int CHANNEL_OUT_5POINT1_SIDE = 6204;
    public static final int CHANNEL_OUT_6POINT1 = 1276;

    @Deprecated
    public static final int CHANNEL_OUT_7POINT1 = 1020;
    public static final int CHANNEL_OUT_7POINT1POINT2 = 3152124;
    public static final int CHANNEL_OUT_7POINT1POINT4 = 743676;
    public static final int CHANNEL_OUT_7POINT1_SURROUND = 6396;
    public static final int CHANNEL_OUT_9POINT1POINT4 = 202070268;
    public static final int CHANNEL_OUT_9POINT1POINT6 = 205215996;
    public static final int CHANNEL_OUT_BACK_CENTER = 1024;
    public static final int CHANNEL_OUT_BACK_LEFT = 64;
    public static final int CHANNEL_OUT_BACK_RIGHT = 128;
    public static final int CHANNEL_OUT_BOTTOM_FRONT_CENTER = 8388608;
    public static final int CHANNEL_OUT_BOTTOM_FRONT_LEFT = 4194304;
    public static final int CHANNEL_OUT_BOTTOM_FRONT_RIGHT = 16777216;
    public static final int CHANNEL_OUT_DEFAULT = 1;
    public static final int CHANNEL_OUT_FRONT_CENTER = 16;
    public static final int CHANNEL_OUT_FRONT_LEFT = 4;
    public static final int CHANNEL_OUT_FRONT_LEFT_OF_CENTER = 256;
    public static final int CHANNEL_OUT_FRONT_RIGHT = 8;
    public static final int CHANNEL_OUT_FRONT_RIGHT_OF_CENTER = 512;
    public static final int CHANNEL_OUT_FRONT_WIDE_LEFT = 67108864;
    public static final int CHANNEL_OUT_FRONT_WIDE_RIGHT = 134217728;
    public static final int CHANNEL_OUT_HAPTIC_A = 536870912;
    public static final int CHANNEL_OUT_HAPTIC_B = 268435456;
    public static final int CHANNEL_OUT_LOW_FREQUENCY = 32;
    public static final int CHANNEL_OUT_LOW_FREQUENCY_2 = 33554432;
    public static final int CHANNEL_OUT_MONO = 4;
    public static final int CHANNEL_OUT_QUAD = 204;
    public static final int CHANNEL_OUT_QUAD_SIDE = 6156;
    public static final int CHANNEL_OUT_SIDE_LEFT = 2048;
    public static final int CHANNEL_OUT_SIDE_RIGHT = 4096;
    public static final int CHANNEL_OUT_STEREO = 12;
    public static final int CHANNEL_OUT_SURROUND = 1052;
    public static final int CHANNEL_OUT_TOP_BACK_CENTER = 262144;
    public static final int CHANNEL_OUT_TOP_BACK_LEFT = 131072;
    public static final int CHANNEL_OUT_TOP_BACK_RIGHT = 524288;
    public static final int CHANNEL_OUT_TOP_CENTER = 8192;
    public static final int CHANNEL_OUT_TOP_FRONT_CENTER = 32768;
    public static final int CHANNEL_OUT_TOP_FRONT_LEFT = 16384;
    public static final int CHANNEL_OUT_TOP_FRONT_RIGHT = 65536;
    public static final int CHANNEL_OUT_TOP_SIDE_LEFT = 1048576;
    public static final int CHANNEL_OUT_TOP_SIDE_RIGHT = 2097152;
    public static final int ENCODING_AAC_ADTS_HE_V1 = 201;
    public static final int ENCODING_AAC_ADTS_HE_V2 = 202;
    public static final int ENCODING_AAC_ADTS_LC = 200;
    public static final int ENCODING_AAC_ELD = 15;
    public static final int ENCODING_AAC_HE_V1 = 11;
    public static final int ENCODING_AAC_HE_V2 = 12;
    public static final int ENCODING_AAC_LC = 10;
    public static final int ENCODING_AAC_XHE = 16;
    public static final int ENCODING_AC3 = 5;
    public static final int ENCODING_AC4 = 17;
    public static final int ENCODING_AC4_L4 = 32;
    public static final int ENCODING_DEFAULT = 1;
    public static final int ENCODING_DOLBY_MAT = 19;
    public static final int ENCODING_DOLBY_TRUEHD = 14;
    public static final int ENCODING_DRA = 28;
    public static final int ENCODING_DSD = 31;
    public static final int ENCODING_DTS = 7;
    public static final int ENCODING_DTS_HD = 8;
    public static final int ENCODING_DTS_HD_MA = 29;

    @Deprecated
    public static final int ENCODING_DTS_UHD = 27;
    public static final int ENCODING_DTS_UHD_P1 = 27;
    public static final int ENCODING_DTS_UHD_P2 = 30;
    public static final int ENCODING_E_AC3 = 6;
    public static final int ENCODING_E_AC3_JOC = 18;
    public static final int ENCODING_IAMF_BASE_ENHANCED_PROFILE_AAC = 42;
    public static final int ENCODING_IAMF_BASE_ENHANCED_PROFILE_FLAC = 43;
    public static final int ENCODING_IAMF_BASE_ENHANCED_PROFILE_OPUS = 41;
    public static final int ENCODING_IAMF_BASE_ENHANCED_PROFILE_PCM = 44;
    public static final int ENCODING_IAMF_BASE_PROFILE_AAC = 38;
    public static final int ENCODING_IAMF_BASE_PROFILE_FLAC = 39;
    public static final int ENCODING_IAMF_BASE_PROFILE_OPUS = 37;
    public static final int ENCODING_IAMF_BASE_PROFILE_PCM = 40;
    public static final int ENCODING_IAMF_SIMPLE_PROFILE_AAC = 34;
    public static final int ENCODING_IAMF_SIMPLE_PROFILE_FLAC = 35;
    public static final int ENCODING_IAMF_SIMPLE_PROFILE_OPUS = 33;
    public static final int ENCODING_IAMF_SIMPLE_PROFILE_PCM = 36;
    public static final int ENCODING_IEC61937 = 13;
    public static final int ENCODING_INVALID = 0;
    public static final int ENCODING_LEGACY_SHORT_ARRAY_THRESHOLD = 20;
    public static final int ENCODING_MP3 = 9;
    public static final int ENCODING_MPEGH_BL_L3 = 23;
    public static final int ENCODING_MPEGH_BL_L4 = 24;
    public static final int ENCODING_MPEGH_LC_L3 = 25;
    public static final int ENCODING_MPEGH_LC_L4 = 26;
    public static final int ENCODING_OPUS = 20;
    public static final int ENCODING_PCM_16BIT = 2;
    public static final int ENCODING_PCM_24BIT_PACKED = 21;
    public static final int ENCODING_PCM_32BIT = 22;
    public static final int ENCODING_PCM_8BIT = 3;
    public static final int ENCODING_PCM_FLOAT = 4;
    public static final int SAMPLE_RATE_UNSPECIFIED = 0;
    private final int mChannelCount;
    private final int mChannelIndexMask;
    private final int mChannelMask;
    private final int mEncoding;
    private final int mFrameSizeInBytes;
    private final int mPropertySetMask;
    private final int mSampleRate;
    public static final int SAMPLE_RATE_HZ_MIN = AudioSystem.SAMPLE_RATE_HZ_MIN;
    public static final int SAMPLE_RATE_HZ_MAX = AudioSystem.SAMPLE_RATE_HZ_MAX;
    public static final Parcelable.Creator<AudioFormat> CREATOR = new Parcelable.Creator<AudioFormat>() { // from class: android.media.AudioFormat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioFormat createFromParcel(Parcel parcel) {
            return new AudioFormat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioFormat[] newArray(int i) {
            return new AudioFormat[i];
        }
    };
    public static final int[] SURROUND_SOUND_ENCODING = {5, 6, 7, 8, 10, 14, 17, 32, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30};

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChannelOut {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Encoding {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncodingCanBeInvalid {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SurroundSoundEncoding {
    }

    public static int convertChannelOutMaskToNativeMask(int i) {
        return i >> 2;
    }

    public static int convertNativeChannelMaskToOutMask(int i) {
        return i << 2;
    }

    public static boolean isPublicEncoding(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                return true;
            default:
                switch (i) {
                    case 200:
                    case 201:
                    case 202:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static boolean isValidEncoding(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                return true;
            default:
                switch (i) {
                    case 200:
                    case 201:
                    case 202:
                        return true;
                    default:
                        return false;
                }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String toLogFriendlyEncoding(int i) {
        if (i == 0) {
            return "ENCODING_INVALID";
        }
        switch (i) {
            case 2:
                return "ENCODING_PCM_16BIT";
            case 3:
                return "ENCODING_PCM_8BIT";
            case 4:
                return "ENCODING_PCM_FLOAT";
            case 5:
                return "ENCODING_AC3";
            case 6:
                return "ENCODING_E_AC3";
            case 7:
                return "ENCODING_DTS";
            case 8:
                return "ENCODING_DTS_HD";
            case 9:
                return "ENCODING_MP3";
            case 10:
                return "ENCODING_AAC_LC";
            case 11:
                return "ENCODING_AAC_HE_V1";
            case 12:
                return "ENCODING_AAC_HE_V2";
            case 13:
                return "ENCODING_IEC61937";
            case 14:
                return "ENCODING_DOLBY_TRUEHD";
            case 15:
                return "ENCODING_AAC_ELD";
            case 16:
                return "ENCODING_AAC_XHE";
            case 17:
                return "ENCODING_AC4";
            case 18:
                return "ENCODING_E_AC3_JOC";
            case 19:
                return "ENCODING_DOLBY_MAT";
            case 20:
                return "ENCODING_OPUS";
            case 21:
                return "ENCODING_PCM_24BIT_PACKED";
            case 22:
                return "ENCODING_PCM_32BIT";
            case 23:
                return "ENCODING_MPEGH_BL_L3";
            case 24:
                return "ENCODING_MPEGH_BL_L4";
            case 25:
                return "ENCODING_MPEGH_LC_L3";
            case 26:
                return "ENCODING_MPEGH_LC_L4";
            case 27:
                return "ENCODING_DTS_UHD_P1";
            case 28:
                return "ENCODING_DRA";
            case 29:
                return "ENCODING_DTS_HD_MA";
            case 30:
                return "ENCODING_DTS_UHD_P2";
            case 31:
                return "ENCODING_DSD";
            case 32:
                return "ENCODING_AC4_L4";
            case 33:
                return "ENCODING_IAMF_SIMPLE_PROFILE_OPUS";
            case 34:
                return "ENCODING_IAMF_SIMPLE_PROFILE_AAC";
            case 35:
                return "ENCODING_IAMF_SIMPLE_PROFILE_FLAC";
            case 36:
                return "ENCODING_IAMF_SIMPLE_PROFILE_PCM";
            case 37:
                return "ENCODING_IAMF_BASE_PROFILE_OPUS";
            case 38:
                return "ENCODING_IAMF_BASE_PROFILE_AAC";
            case 39:
                return "ENCODING_IAMF_BASE_PROFILE_FLAC";
            case 40:
                return "ENCODING_IAMF_BASE_PROFILE_PCM";
            case 41:
                return "ENCODING_IAMF_BASE_ENHANCED_PROFILE_OPUS";
            case 42:
                return "ENCODING_IAMF_BASE_ENHANCED_PROFILE_AAC";
            case 43:
                return "ENCODING_IAMF_BASE_ENHANCED_PROFILE_FLAC";
            case 44:
                return "ENCODING_IAMF_BASE_ENHANCED_PROFILE_PCM";
            default:
                switch (i) {
                    case 200:
                        return "ENCODING_AAC_ADTS_LC";
                    case 201:
                        return "ENCODING_AAC_ADTS_HE_V1";
                    case 202:
                        return "ENCODING_AAC_ADTS_HE_V2";
                    default:
                        return "invalid encoding " + i;
                }
        }
    }

    public static int inChannelMaskFromOutChannelMask(int i) throws IllegalArgumentException {
        if (i == 1) {
            throw new IllegalArgumentException("Illegal CHANNEL_OUT_DEFAULT channel mask for input.");
        }
        int iChannelCountFromOutChannelMask = channelCountFromOutChannelMask(i);
        if (iChannelCountFromOutChannelMask == 1) {
            return 16;
        }
        if (iChannelCountFromOutChannelMask == 2) {
            return 12;
        }
        throw new IllegalArgumentException("Unsupported channel configuration for input.");
    }

    public static int channelCountFromInChannelMask(int i) {
        return Integer.bitCount(i);
    }

    public static int channelCountFromOutChannelMask(int i) {
        return Integer.bitCount(i);
    }

    public static String javaChannelOutMaskToString(int i) {
        int i2 = (-805306369) & i;
        StringBuilder sb = new StringBuilder("");
        switch (i2) {
            case 4:
                sb.append(Camera.Parameters.EFFECT_MONO);
                break;
            case 12:
                sb.append("stereo");
                break;
            case 204:
                sb.append("quad");
                break;
            case 252:
                sb.append("5.1");
                break;
            case 1020:
                sb.append("7.1 (5 fronts)");
                break;
            case 1052:
                sb.append("4.0");
                break;
            case 1276:
                sb.append("6.1");
                break;
            case CHANNEL_OUT_QUAD_SIDE /* 6156 */:
                sb.append("quad side");
                break;
            case CHANNEL_OUT_5POINT1_SIDE /* 6204 */:
                sb.append("5.1 side");
                break;
            case CHANNEL_OUT_7POINT1_SURROUND /* 6396 */:
                sb.append("7.1");
                break;
            case CHANNEL_OUT_5POINT1POINT4 /* 737532 */:
                sb.append("5.1.4");
                break;
            case CHANNEL_OUT_7POINT1POINT4 /* 743676 */:
                sb.append("7.1.4");
                break;
            case CHANNEL_OUT_5POINT1POINT2 /* 3145980 */:
                sb.append("5.1.2");
                break;
            case CHANNEL_OUT_7POINT1POINT2 /* 3152124 */:
                sb.append("7.1.2");
                break;
            case CHANNEL_OUT_13POINT0 /* 30136348 */:
                sb.append("360RA 13ch");
                break;
            case CHANNEL_OUT_22POINT2 /* 67108860 */:
                sb.append("22.2");
                break;
            case CHANNEL_OUT_9POINT1POINT4 /* 202070268 */:
                sb.append("9.1.4");
                break;
            case CHANNEL_OUT_9POINT1POINT6 /* 205215996 */:
                sb.append("9.1.6");
                break;
            default:
                sb.append("0x");
                sb.append(Integer.toHexString(i2));
                break;
        }
        if ((805306368 & i) != 0) {
            sb.append("(+haptic ");
            if ((i & 536870912) == 536870912) {
                sb.append("A");
            }
            if ((i & 268435456) == 268435456) {
                sb.append(GnssSignalType.CODE_TYPE_B);
            }
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        return sb.toString();
    }

    public static int getBytesPerSample(int i) {
        int i2 = 1;
        if (i != 1 && i != 2) {
            if (i != 3) {
                i2 = 4;
                if (i != 4) {
                    if (i != 13) {
                        if (i == 21) {
                            return 3;
                        }
                        if (i != 22) {
                            throw new IllegalArgumentException("Bad audio format " + i);
                        }
                    }
                }
            }
            return i2;
        }
        return 2;
    }

    public static boolean isEncodingLinearPcm(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 21:
            case 22:
                return true;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                return false;
            default:
                switch (i) {
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                        return false;
                    default:
                        switch (i) {
                            case 200:
                            case 201:
                            case 202:
                                return false;
                            default:
                                throw new IllegalArgumentException("Bad audio format " + i);
                        }
                }
        }
    }

    public static boolean isEncodingLinearFrames(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 13:
            case 21:
            case 22:
                return true;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                return false;
            default:
                switch (i) {
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                        return false;
                    default:
                        switch (i) {
                            case 200:
                            case 201:
                            case 202:
                                return false;
                            default:
                                throw new IllegalArgumentException("Bad audio format " + i);
                        }
                }
        }
    }

    public static int[] filterPublicFormats(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length);
        int i = 0;
        for (int i2 = 0; i2 < iArrCopyOf.length; i2++) {
            if (isPublicEncoding(iArrCopyOf[i2])) {
                if (i != i2) {
                    iArrCopyOf[i] = iArrCopyOf[i2];
                }
                i++;
            }
        }
        return Arrays.copyOf(iArrCopyOf, i);
    }

    public AudioFormat() {
        throw new UnsupportedOperationException("There is no valid usage of this constructor");
    }

    private AudioFormat(int i, int i2, int i3, int i4) {
        this(15, i, i2, i3, i4);
    }

    private AudioFormat(int i, int i2, int i3, int i4, int i5) {
        int bytesPerSample;
        this.mPropertySetMask = i;
        int i6 = 0;
        i2 = (i & 1) == 0 ? 0 : i2;
        this.mEncoding = i2;
        this.mSampleRate = (i & 2) == 0 ? 0 : i3;
        this.mChannelMask = (i & 4) == 0 ? 0 : i4;
        this.mChannelIndexMask = (i & 8) == 0 ? 0 : i5;
        int iBitCount = Integer.bitCount(getChannelIndexMask());
        int iChannelCountFromOutChannelMask = channelCountFromOutChannelMask(getChannelMask());
        if (iChannelCountFromOutChannelMask == 0) {
            i6 = iBitCount;
        } else if (iChannelCountFromOutChannelMask == iBitCount || iBitCount == 0) {
            i6 = iChannelCountFromOutChannelMask;
        }
        this.mChannelCount = i6;
        try {
            bytesPerSample = getBytesPerSample(i2) * i6;
        } catch (IllegalArgumentException unused) {
            bytesPerSample = 1;
        }
        this.mFrameSizeInBytes = bytesPerSample != 0 ? bytesPerSample : 1;
    }

    public int getEncoding() {
        return this.mEncoding;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getChannelMask() {
        return this.mChannelMask;
    }

    public int getChannelIndexMask() {
        return this.mChannelIndexMask;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getFrameSizeInBytes() {
        return this.mFrameSizeInBytes;
    }

    public int getPropertySetMask() {
        return this.mPropertySetMask;
    }

    public String toLogFriendlyString() {
        return String.format("%dch %dHz %s", Integer.valueOf(this.mChannelCount), Integer.valueOf(this.mSampleRate), toLogFriendlyEncoding(this.mEncoding));
    }

    public static class Builder {
        private int mChannelIndexMask;
        private int mChannelMask;
        private int mEncoding;
        private int mPropertySetMask;
        private int mSampleRate;

        public Builder() {
            this.mEncoding = 0;
            this.mSampleRate = 0;
            this.mChannelMask = 0;
            this.mChannelIndexMask = 0;
            this.mPropertySetMask = 0;
        }

        public Builder(AudioFormat audioFormat) {
            this.mEncoding = 0;
            this.mSampleRate = 0;
            this.mChannelMask = 0;
            this.mChannelIndexMask = 0;
            this.mPropertySetMask = 0;
            this.mEncoding = audioFormat.mEncoding;
            this.mSampleRate = audioFormat.mSampleRate;
            this.mChannelMask = audioFormat.mChannelMask;
            this.mChannelIndexMask = audioFormat.mChannelIndexMask;
            this.mPropertySetMask = audioFormat.mPropertySetMask;
        }

        public AudioFormat build() {
            return new AudioFormat(this.mPropertySetMask, this.mEncoding, this.mSampleRate, this.mChannelMask, this.mChannelIndexMask);
        }

        public Builder setEncoding(int i) throws IllegalArgumentException {
            switch (i) {
                case 1:
                    this.mEncoding = 2;
                    this.mPropertySetMask |= 1;
                    return this;
                default:
                    switch (i) {
                        case 200:
                        case 201:
                        case 202:
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid encoding " + i);
                    }
                    this.mPropertySetMask |= 1;
                    return this;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                    this.mEncoding = i;
                    this.mPropertySetMask |= 1;
                    return this;
            }
        }

        public Builder setChannelMask(int i) {
            if (i == 0) {
                throw new IllegalArgumentException("Invalid zero channel mask");
            }
            if (this.mChannelIndexMask != 0 && Integer.bitCount(i) != Integer.bitCount(this.mChannelIndexMask)) {
                throw new IllegalArgumentException("Mismatched channel count for mask " + Integer.toHexString(i).toUpperCase());
            }
            this.mChannelMask = i;
            this.mPropertySetMask |= 4;
            return this;
        }

        public Builder setChannelIndexMask(int i) {
            if (i == 0) {
                throw new IllegalArgumentException("Invalid zero channel index mask");
            }
            if (this.mChannelMask != 0 && Integer.bitCount(i) != Integer.bitCount(this.mChannelMask)) {
                throw new IllegalArgumentException("Mismatched channel count for index mask " + Integer.toHexString(i).toUpperCase());
            }
            this.mChannelIndexMask = i;
            this.mPropertySetMask |= 8;
            return this;
        }

        public Builder setSampleRate(int i) throws IllegalArgumentException {
            if ((i < AudioFormat.SAMPLE_RATE_HZ_MIN || i > AudioFormat.SAMPLE_RATE_HZ_MAX) && i != 0) {
                throw new IllegalArgumentException("Invalid sample rate " + i);
            }
            this.mSampleRate = i;
            this.mPropertySetMask |= 2;
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AudioFormat audioFormat = (AudioFormat) obj;
        int i = this.mPropertySetMask;
        if (i != audioFormat.mPropertySetMask) {
            return false;
        }
        return ((i & 1) == 0 || this.mEncoding == audioFormat.mEncoding) && ((i & 2) == 0 || this.mSampleRate == audioFormat.mSampleRate) && (((i & 4) == 0 || this.mChannelMask == audioFormat.mChannelMask) && ((i & 8) == 0 || this.mChannelIndexMask == audioFormat.mChannelIndexMask));
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPropertySetMask), Integer.valueOf(this.mSampleRate), Integer.valueOf(this.mEncoding), Integer.valueOf(this.mChannelMask), Integer.valueOf(this.mChannelIndexMask));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPropertySetMask);
        parcel.writeInt(this.mEncoding);
        parcel.writeInt(this.mSampleRate);
        parcel.writeInt(this.mChannelMask);
        parcel.writeInt(this.mChannelIndexMask);
    }

    private AudioFormat(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public String toString() {
        return new String("AudioFormat: props=" + this.mPropertySetMask + " enc=" + this.mEncoding + " chan=0x" + Integer.toHexString(this.mChannelMask).toUpperCase() + " chan_index=0x" + Integer.toHexString(this.mChannelIndexMask).toUpperCase() + " rate=" + this.mSampleRate);
    }

    public static String toDisplayName(int i) {
        if (i == 5) {
            return "Dolby Digital";
        }
        if (i == 6) {
            return "Dolby Digital Plus";
        }
        if (i == 7) {
            return "DTS";
        }
        if (i == 8) {
            return "DTS HD";
        }
        if (i == 10) {
            return "AAC";
        }
        if (i == 14) {
            return "Dolby TrueHD";
        }
        if (i != 32) {
            switch (i) {
                case 17:
                    return "Dolby AC-4 levels 0-3";
                case 18:
                    return "Dolby Atmos in Dolby Digital Plus";
                case 19:
                    return "Dolby MAT";
                default:
                    switch (i) {
                        case 23:
                            return "MPEG-H 3D Audio baseline profile level 3";
                        case 24:
                            return "MPEG-H 3D Audio baseline profile level 4";
                        case 25:
                            return "MPEG-H 3D Audio low complexity profile level 3";
                        case 26:
                            return "MPEG-H 3D Audio low complexity profile level 4";
                        case 27:
                            return "DTS UHD Profile 1";
                        case 28:
                            return "DRA";
                        case 29:
                            return "DTS HD Master Audio";
                        case 30:
                            return "DTS UHD Profile 2";
                        default:
                            return "Unknown surround sound format";
                    }
            }
        }
        return "Dolby AC-4 level 4";
    }
}
