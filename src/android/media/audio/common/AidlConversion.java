package android.media.audio.common;

import android.media.AudioDescriptor;
import android.media.AudioDeviceAttributes;
import android.media.AudioFormat;
import android.media.AudioSystem;
import android.media.MediaFormat;
import android.media.audiopolicy.AudioMixingRule;
import android.os.Parcel;
import com.android.internal.telephony.cdma.sms.SmsEnvelope;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class AidlConversion {
    private static int aidl2api_AudioChannelLayoutBit_AudioFormatChannel(int i, boolean z) {
        if (z) {
            if (i == 1) {
                return 4;
            }
            if (i == 2) {
                return 8;
            }
            if (i == 4) {
                return 262144;
            }
            if (i == 8) {
                return 1048576;
            }
            if (i == 16) {
                return 65536;
            }
            if (i == 32) {
                return 131072;
            }
            if (i == 256) {
                return 32;
            }
            if (i != 262144) {
                return i != 524288 ? 0 : 4194304;
            }
            return 2097152;
        }
        if (i == 1) {
            return 4;
        }
        if (i == 2) {
            return 8;
        }
        switch (i) {
            case 4:
                return 16;
            case 8:
                return 32;
            case 16:
                return 64;
            case 32:
                return 128;
            case 64:
                return 256;
            case 128:
                return 512;
            case 256:
                return 1024;
            case 512:
                return 2048;
            case 1024:
                return 4096;
            case 2048:
                return 8192;
            case 4096:
                return 16384;
            case 8192:
                return 32768;
            case 16384:
                return 65536;
            case 32768:
                return 131072;
            case 65536:
                return 262144;
            case 131072:
                return 524288;
            case 262144:
                return 1048576;
            case 524288:
                return 2097152;
            case 1048576:
                return 4194304;
            case 2097152:
                return 8388608;
            case 4194304:
                return 16777216;
            case 8388608:
                return 33554432;
            case 16777216:
                return 67108864;
            case 33554432:
                return 134217728;
            case 536870912:
                return 268435456;
            case 1073741824:
                return 536870912;
            default:
                return 0;
        }
    }

    public static int aidl2api_AudioEncapsulationType_AudioProfileEncapsulationType(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    public static int aidl2api_AudioStandard_AudioDescriptorStandard(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 0;
                }
            }
        }
        return i2;
    }

    private static native int aidl2legacy_AudioChannelLayout_Parcel_audio_channel_mask_t(Parcel parcel, boolean z);

    public static native int aidl2legacy_AudioEncapsulationMode_audio_encapsulation_mode_t(int i);

    private static native int aidl2legacy_AudioFormatDescription_Parcel_audio_format_t(Parcel parcel);

    public static native int aidl2legacy_AudioStreamType_audio_stream_type_t(int i);

    public static native int aidl2legacy_AudioUsage_audio_usage_t(int i);

    public static int api2aidl_AudioDescriptorStandard_AudioStandard(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 0;
                }
            }
        }
        return i2;
    }

    public static int api2aidl_AudioProfileEncapsulationType_AudioEncapsulationType(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    private static native Parcel legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel(int i, boolean z);

    public static native int legacy2aidl_audio_encapsulation_mode_t_AudioEncapsulationMode(int i);

    private static native Parcel legacy2aidl_audio_format_t_AudioFormatDescription_Parcel(int i);

    public static native int legacy2aidl_audio_stream_type_t_AudioStreamType(int i);

    public static native int legacy2aidl_audio_usage_t_AudioUsage(int i);

    public static int aidl2legacy_AudioChannelLayout_audio_channel_mask_t(AudioChannelLayout audioChannelLayout, boolean z) {
        Parcel obtain = Parcel.obtain();
        audioChannelLayout.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        try {
            return aidl2legacy_AudioChannelLayout_Parcel_audio_channel_mask_t(obtain, z);
        } finally {
            obtain.recycle();
        }
    }

    public static AudioChannelLayout legacy2aidl_audio_channel_mask_t_AudioChannelLayout(int i, boolean z) {
        Parcel legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel = legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel(i, z);
        if (legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel != null) {
            try {
                return AudioChannelLayout.CREATOR.createFromParcel(legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel);
            } finally {
                legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel.recycle();
            }
        }
        StringBuilder sb = new StringBuilder("Failed to convert legacy audio ");
        sb.append(z ? "input" : "output");
        sb.append(" audio_channel_mask_t ");
        sb.append(i);
        sb.append(" value");
        throw new IllegalArgumentException(sb.toString());
    }

    public static int aidl2legacy_AudioFormatDescription_audio_format_t(AudioFormatDescription audioFormatDescription) {
        Parcel obtain = Parcel.obtain();
        audioFormatDescription.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        try {
            return aidl2legacy_AudioFormatDescription_Parcel_audio_format_t(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public static AudioFormatDescription legacy2aidl_audio_format_t_AudioFormatDescription(int i) {
        Parcel legacy2aidl_audio_format_t_AudioFormatDescription_Parcel = legacy2aidl_audio_format_t_AudioFormatDescription_Parcel(i);
        if (legacy2aidl_audio_format_t_AudioFormatDescription_Parcel != null) {
            try {
                return AudioFormatDescription.CREATOR.createFromParcel(legacy2aidl_audio_format_t_AudioFormatDescription_Parcel);
            } finally {
                legacy2aidl_audio_format_t_AudioFormatDescription_Parcel.recycle();
            }
        }
        throw new IllegalArgumentException("Failed to convert legacy audio_format_t value " + i);
    }

    private static int aidl2api_AudioChannelLayoutBitMask_AudioFormatChannelMask(int i, boolean z) {
        int i2 = 0;
        for (int i3 = Integer.MIN_VALUE; i3 != 0; i3 >>>= 1) {
            if ((i & i3) == i3) {
                int aidl2api_AudioChannelLayoutBit_AudioFormatChannel = aidl2api_AudioChannelLayoutBit_AudioFormatChannel(i3, z);
                if (aidl2api_AudioChannelLayoutBit_AudioFormatChannel == 0) {
                    break;
                }
                i2 |= aidl2api_AudioChannelLayoutBit_AudioFormatChannel;
                i &= ~i3;
                if (i == 0) {
                    return i2;
                }
            }
        }
        return 0;
    }

    public static int aidl2api_AudioChannelLayout_AudioFormatChannelMask(AudioChannelLayout audioChannelLayout, boolean z) {
        int tag = audioChannelLayout.getTag();
        if (tag == 0) {
            return 1;
        }
        if (tag == 2) {
            return audioChannelLayout.getIndexMask();
        }
        if (tag != 3) {
            if (tag == 4 && z) {
                int voiceMask = audioChannelLayout.getVoiceMask();
                if (voiceMask == 16384) {
                    return 16400;
                }
                if (voiceMask == 32768) {
                    return AudioMixingRule.RULE_EXCLUDE_AUDIO_SESSION_ID;
                }
                if (voiceMask == 49152) {
                    return SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49168;
                }
            }
            return 0;
        }
        if (!z) {
            switch (audioChannelLayout.getLayoutMask()) {
                case 1:
                    break;
                case 3:
                    break;
                case 7:
                    break;
                case 11:
                    break;
                case 15:
                    break;
                case 51:
                    break;
                case 55:
                    break;
                case 63:
                    break;
                case 259:
                    break;
                case 260:
                    break;
                case 263:
                    break;
                case 319:
                    break;
                case 1539:
                    break;
                case 1551:
                    break;
                case 1599:
                    break;
                case AudioChannelLayout.LAYOUT_5POINT1POINT4 /* 184383 */:
                    break;
                case AudioChannelLayout.LAYOUT_7POINT1POINT4 /* 185919 */:
                    break;
                case AudioChannelLayout.LAYOUT_2POINT0POINT2 /* 786435 */:
                    break;
                case AudioChannelLayout.LAYOUT_3POINT0POINT2 /* 786439 */:
                    break;
                case AudioChannelLayout.LAYOUT_2POINT1POINT2 /* 786443 */:
                    break;
                case AudioChannelLayout.LAYOUT_3POINT1POINT2 /* 786447 */:
                    break;
                case AudioChannelLayout.LAYOUT_5POINT1POINT2 /* 786495 */:
                    break;
                case AudioChannelLayout.LAYOUT_7POINT1POINT2 /* 788031 */:
                    break;
                case 7534087:
                    break;
                case 16777215:
                    break;
                case AudioChannelLayout.LAYOUT_9POINT1POINT4 /* 50517567 */:
                    break;
                case AudioChannelLayout.LAYOUT_9POINT1POINT6 /* 51303999 */:
                    break;
                case AudioChannelLayout.LAYOUT_MONO_HAPTIC_A /* 1073741825 */:
                    break;
                case AudioChannelLayout.LAYOUT_STEREO_HAPTIC_A /* 1073741827 */:
                    break;
                case 1610612736:
                    break;
                case AudioChannelLayout.LAYOUT_MONO_HAPTIC_AB /* 1610612737 */:
                    break;
                case AudioChannelLayout.LAYOUT_STEREO_HAPTIC_AB /* 1610612739 */:
                    break;
            }
            return 0;
        }
        int layoutMask = audioChannelLayout.getLayoutMask();
        if (layoutMask == 1) {
            return 16;
        }
        if (layoutMask == 3) {
            return 12;
        }
        if (layoutMask == 63) {
            return AudioFormat.CHANNEL_IN_5POINT1;
        }
        if (layoutMask == 260) {
            return 48;
        }
        if (layoutMask == 786435) {
            return AudioFormat.CHANNEL_IN_2POINT0POINT2;
        }
        if (layoutMask == 786439) {
            return AudioFormat.CHANNEL_IN_3POINT0POINT2;
        }
        if (layoutMask == 786443) {
            return AudioFormat.CHANNEL_IN_2POINT1POINT2;
        }
        if (layoutMask == 786447) {
            return AudioFormat.CHANNEL_IN_3POINT1POINT2;
        }
        return aidl2api_AudioChannelLayoutBitMask_AudioFormatChannelMask(audioChannelLayout.getLayoutMask(), z);
    }

    public static AudioFormat aidl2api_AudioConfig_AudioFormat(AudioConfig audioConfig, boolean z) {
        return aidl2api_AudioConfigBase_AudioFormat(audioConfig.base, z);
    }

    public static AudioFormat aidl2api_AudioConfigBase_AudioFormat(AudioConfigBase audioConfigBase, boolean z) {
        AudioFormat.Builder builder = new AudioFormat.Builder();
        builder.setSampleRate(audioConfigBase.sampleRate);
        if (audioConfigBase.channelMask.getTag() != 2) {
            builder.setChannelMask(aidl2api_AudioChannelLayout_AudioFormatChannelMask(audioConfigBase.channelMask, z));
        } else {
            builder.setChannelIndexMask(aidl2api_AudioChannelLayout_AudioFormatChannelMask(audioConfigBase.channelMask, z));
        }
        builder.setEncoding(aidl2api_AudioFormat_AudioFormatEncoding(audioConfigBase.format));
        return builder.build();
    }

    public static int aidl2api_AudioFormat_AudioFormatEncoding(AudioFormatDescription audioFormatDescription) {
        byte b = audioFormatDescription.type;
        if (b != 0) {
            if (b != 1) {
                return 0;
            }
            byte b2 = audioFormatDescription.pcm;
            if (b2 == 0) {
                return 3;
            }
            if (b2 == 1) {
                return 2;
            }
            if (b2 == 2) {
                return 22;
            }
            if (b2 == 3 || b2 == 4) {
                return 4;
            }
            return b2 != 5 ? 0 : 21;
        }
        if (audioFormatDescription.encoding == null || audioFormatDescription.encoding.isEmpty()) {
            return 1;
        }
        if (MediaFormat.MIMETYPE_AUDIO_AC3.equals(audioFormatDescription.encoding)) {
            return 5;
        }
        if (MediaFormat.MIMETYPE_AUDIO_EAC3.equals(audioFormatDescription.encoding)) {
            return 6;
        }
        if (MediaFormat.MIMETYPE_AUDIO_DTS.equals(audioFormatDescription.encoding)) {
            return 7;
        }
        if (MediaFormat.MIMETYPE_AUDIO_DTS_HD.equals(audioFormatDescription.encoding)) {
            return 8;
        }
        if ("audio/mpeg".equals(audioFormatDescription.encoding)) {
            return 9;
        }
        if (MediaFormat.MIMETYPE_AUDIO_AAC_LC.equals(audioFormatDescription.encoding)) {
            return 10;
        }
        if (MediaFormat.MIMETYPE_AUDIO_AAC_HE_V1.equals(audioFormatDescription.encoding)) {
            return 11;
        }
        if (MediaFormat.MIMETYPE_AUDIO_AAC_HE_V2.equals(audioFormatDescription.encoding)) {
            return 12;
        }
        if (MediaFormat.MIMETYPE_AUDIO_IEC61937.equals(audioFormatDescription.encoding) && audioFormatDescription.pcm == 1) {
            return 13;
        }
        if (MediaFormat.MIMETYPE_AUDIO_DOLBY_TRUEHD.equals(audioFormatDescription.encoding)) {
            return 14;
        }
        if (MediaFormat.MIMETYPE_AUDIO_AAC_ELD.equals(audioFormatDescription.encoding)) {
            return 15;
        }
        if (MediaFormat.MIMETYPE_AUDIO_AAC_XHE.equals(audioFormatDescription.encoding)) {
            return 16;
        }
        if (MediaFormat.MIMETYPE_AUDIO_AC4.equals(audioFormatDescription.encoding)) {
            return 17;
        }
        if (MediaFormat.MIMETYPE_AUDIO_EAC3_JOC.equals(audioFormatDescription.encoding)) {
            return 18;
        }
        if (MediaFormat.MIMETYPE_AUDIO_DOLBY_MAT.equals(audioFormatDescription.encoding) || audioFormatDescription.encoding.startsWith("audio/vnd.dolby.mat.")) {
            return 19;
        }
        if (MediaFormat.MIMETYPE_AUDIO_OPUS.equals(audioFormatDescription.encoding)) {
            return 20;
        }
        if (MediaFormat.MIMETYPE_AUDIO_MPEGH_BL_L3.equals(audioFormatDescription.encoding)) {
            return 23;
        }
        if (MediaFormat.MIMETYPE_AUDIO_MPEGH_BL_L4.equals(audioFormatDescription.encoding)) {
            return 24;
        }
        if (MediaFormat.MIMETYPE_AUDIO_MPEGH_LC_L3.equals(audioFormatDescription.encoding)) {
            return 25;
        }
        if (MediaFormat.MIMETYPE_AUDIO_MPEGH_LC_L4.equals(audioFormatDescription.encoding)) {
            return 26;
        }
        if (MediaFormat.MIMETYPE_AUDIO_DTS_UHD.equals(audioFormatDescription.encoding)) {
            return 27;
        }
        return MediaFormat.MIMETYPE_AUDIO_DRA.equals(audioFormatDescription.encoding) ? 28 : 0;
    }

    public static AudioPort api2aidl_AudioDeviceAttributes_AudioPort(AudioDeviceAttributes audioDeviceAttributes) {
        AudioPort audioPort = new AudioPort();
        audioPort.name = audioDeviceAttributes.getName();
        audioPort.profiles = new AudioProfile[0];
        audioPort.extraAudioDescriptors = (ExtraAudioDescriptor[]) ((List) audioDeviceAttributes.getAudioDescriptors().stream().map(new Function() { // from class: android.media.audio.common.AidlConversion$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ExtraAudioDescriptor api2aidl_AudioDescriptor_ExtraAudioDescriptor;
                api2aidl_AudioDescriptor_ExtraAudioDescriptor = AidlConversion.api2aidl_AudioDescriptor_ExtraAudioDescriptor((AudioDescriptor) obj);
                return api2aidl_AudioDescriptor_ExtraAudioDescriptor;
            }
        }).collect(Collectors.toList())).toArray(new IntFunction() { // from class: android.media.audio.common.AidlConversion$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return AidlConversion.lambda$api2aidl_AudioDeviceAttributes_AudioPort$1(i);
            }
        });
        audioPort.flags = new AudioIoFlags();
        audioPort.gains = new AudioGain[0];
        AudioPortDeviceExt audioPortDeviceExt = new AudioPortDeviceExt();
        audioPortDeviceExt.device = new AudioDevice();
        audioPortDeviceExt.encodedFormats = new AudioFormatDescription[0];
        audioPortDeviceExt.device.type = api2aidl_NativeType_AudioDeviceDescription(audioDeviceAttributes.getInternalType());
        audioPortDeviceExt.device.address = AudioDeviceAddress.id(audioDeviceAttributes.getAddress());
        audioPort.ext = AudioPortExt.device(audioPortDeviceExt);
        return audioPort;
    }

    static /* synthetic */ ExtraAudioDescriptor[] lambda$api2aidl_AudioDeviceAttributes_AudioPort$1(int i) {
        return new ExtraAudioDescriptor[i];
    }

    public static ExtraAudioDescriptor api2aidl_AudioDescriptor_ExtraAudioDescriptor(AudioDescriptor audioDescriptor) {
        ExtraAudioDescriptor extraAudioDescriptor = new ExtraAudioDescriptor();
        extraAudioDescriptor.standard = api2aidl_AudioDescriptorStandard_AudioStandard(audioDescriptor.getStandard());
        extraAudioDescriptor.audioDescriptor = audioDescriptor.getDescriptor();
        extraAudioDescriptor.encapsulationType = api2aidl_AudioProfileEncapsulationType_AudioEncapsulationType(audioDescriptor.getEncapsulationType());
        return extraAudioDescriptor;
    }

    public static AudioDescriptor aidl2api_ExtraAudioDescriptor_AudioDescriptor(ExtraAudioDescriptor extraAudioDescriptor) {
        return new AudioDescriptor(aidl2api_AudioStandard_AudioDescriptorStandard(extraAudioDescriptor.standard), aidl2api_AudioEncapsulationType_AudioProfileEncapsulationType(extraAudioDescriptor.encapsulationType), extraAudioDescriptor.audioDescriptor);
    }

    public static AudioDeviceDescription api2aidl_NativeType_AudioDeviceDescription(int i) {
        AudioDeviceDescription audioDeviceDescription = new AudioDeviceDescription();
        audioDeviceDescription.connection = "";
        switch (i) {
            case -2147483644:
                audioDeviceDescription.type = 9;
                break;
            case -2147483640:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_SCO;
                break;
            case -2147483632:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_ANALOG;
                break;
            case -2147483616:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "hdmi";
                break;
            case -2147483584:
                audioDeviceDescription.type = 12;
                break;
            case -2147483520:
                audioDeviceDescription.type = 10;
                break;
            case AudioSystem.DEVICE_IN_REMOTE_SUBMIX /* -2147483392 */:
                audioDeviceDescription.type = 11;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_VIRTUAL;
                break;
            case -2147483136:
                audioDeviceDescription.type = 14;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_ANALOG;
                break;
            case -2147482624:
                audioDeviceDescription.type = 14;
                audioDeviceDescription.connection = "usb";
                break;
            case -2147481600:
                audioDeviceDescription.type = 2;
                audioDeviceDescription.connection = "usb";
                break;
            case -2147479552:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "usb";
                break;
            case -2147475456:
                audioDeviceDescription.type = 6;
                audioDeviceDescription.connection = AudioDeviceDescription.VX_SEC_CONNECTION_FM;
                break;
            case -2147467264:
                audioDeviceDescription.type = 13;
                break;
            case -2147450880:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_ANALOG;
                break;
            case -2147418112:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "spdif";
                break;
            case AudioSystem.DEVICE_IN_BLUETOOTH_A2DP /* -2147352576 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_A2DP;
                break;
            case -2147221504:
                audioDeviceDescription.type = 8;
                break;
            case AudioSystem.DEVICE_IN_IP /* -2146959360 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_IP_V4;
                break;
            case AudioSystem.DEVICE_IN_BUS /* -2146435072 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "bus";
                break;
            case AudioSystem.DEVICE_IN_PROXY /* -2130706432 */:
                audioDeviceDescription.type = 3;
                break;
            case AudioSystem.DEVICE_IN_USB_HEADSET /* -2113929216 */:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = "usb";
                break;
            case AudioSystem.DEVICE_IN_BLUETOOTH_BLE /* -2080374784 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_LE;
                break;
            case -2013265920:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_HDMI_ARC;
                break;
            case -2013265919:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_HDMI_EARC;
                break;
            case -1879048192:
                audioDeviceDescription.type = 5;
                break;
            case -1610612736:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_LE;
                break;
            case AudioSystem.DEVICE_IN_DEFAULT /* -1073741824 */:
                audioDeviceDescription.type = 1;
                break;
            case 1:
                audioDeviceDescription.type = 141;
                break;
            case 2:
                audioDeviceDescription.type = 140;
                break;
            case 4:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_ANALOG;
                break;
            case 8:
                audioDeviceDescription.type = 136;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_ANALOG;
                break;
            case 16:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_SCO;
                break;
            case 32:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_SCO;
                break;
            case 64:
                audioDeviceDescription.type = 132;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_SCO;
                break;
            case 128:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_A2DP;
                break;
            case 256:
                audioDeviceDescription.type = 136;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_A2DP;
                break;
            case 512:
                audioDeviceDescription.type = 140;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_A2DP;
                break;
            case 1024:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "hdmi";
                break;
            case 2048:
                audioDeviceDescription.type = 145;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_ANALOG;
                break;
            case 4096:
                audioDeviceDescription.type = 145;
                audioDeviceDescription.connection = "usb";
                break;
            case 8192:
                audioDeviceDescription.type = 130;
                audioDeviceDescription.connection = "usb";
                break;
            case 16384:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "usb";
                break;
            case 32768:
                audioDeviceDescription.type = 143;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_VIRTUAL;
                break;
            case 65536:
                audioDeviceDescription.type = 144;
                break;
            case 131072:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_ANALOG;
                break;
            case 262144:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_HDMI_ARC;
                break;
            case 262145:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_HDMI_EARC;
                break;
            case 524288:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "spdif";
                break;
            case 1048576:
                audioDeviceDescription.type = 135;
                audioDeviceDescription.connection = AudioDeviceDescription.VX_SEC_CONNECTION_FM;
                break;
            case 2097152:
                audioDeviceDescription.type = 139;
                break;
            case 4194304:
                audioDeviceDescription.type = 142;
                break;
            case 8388608:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_IP_V4;
                break;
            case 8388609:
                audioDeviceDescription.type = 147;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_VIRTUAL;
                break;
            case 16777216:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "bus";
                break;
            case 33554432:
                audioDeviceDescription.type = 131;
                break;
            case 67108864:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = "usb";
                break;
            case 134217728:
                audioDeviceDescription.type = 138;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_WIRELESS;
                break;
            case 268435456:
                audioDeviceDescription.type = 134;
                break;
            case 536870912:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_LE;
                break;
            case 536870913:
                audioDeviceDescription.type = 140;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_LE;
                break;
            case 536870914:
                audioDeviceDescription.type = 146;
                audioDeviceDescription.connection = AudioDeviceDescription.CONNECTION_BT_LE;
                break;
            case 1073741824:
                audioDeviceDescription.type = 129;
                break;
            default:
                audioDeviceDescription.type = 0;
                break;
        }
        return audioDeviceDescription;
    }
}
