package com.android.server.soundtrigger_middleware;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.Uuid;
import android.hardware.soundtrigger.V2_0.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_1.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent;
import android.media.audio.common.AidlConversion;
import android.media.audio.common.AudioConfig;
import android.media.audio.common.AudioConfigBase;
import android.media.audio.common.AudioOffloadInfo;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionEvent;
import android.media.soundtrigger.SoundModel;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.HidlMemory;
import android.os.HidlMemoryUtil;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;
import java.util.regex.Matcher;

public abstract class ConversionUtil {
    public static int aidl2hidlRecognitionModes(int i) {
        int i2 = (i & 1) != 0 ? 1 : 0;
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        return (i & 8) != 0 ? i2 | 8 : i2;
    }

    public static ISoundTriggerHw.SoundModel aidl2hidlSoundModel(SoundModel soundModel) {
        int i;
        ISoundTriggerHw.SoundModel soundModel2 = new ISoundTriggerHw.SoundModel();
        ISoundTriggerHw.SoundModel soundModel3 = (ISoundTriggerHw.SoundModel) soundModel2.header;
        int i2 = soundModel.type;
        if (i2 != 0) {
            i = 1;
            if (i2 != 1) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i2, "Unknown sound model type: "));
            }
        } else {
            i = 0;
        }
        soundModel3.type = i;
        soundModel3.uuid = aidl2hidlUuid(soundModel.uuid);
        ((ISoundTriggerHw.SoundModel) soundModel2.header).vendorUuid =
                aidl2hidlUuid(soundModel.vendorUuid);
        ParcelFileDescriptor parcelFileDescriptor = soundModel.data;
        int i3 = soundModel.dataSize;
        soundModel2.data =
                i3 > 0
                        ? HidlMemoryUtil.fileDescriptorToHidlMemory(
                                parcelFileDescriptor.getFileDescriptor(), i3)
                        : HidlMemoryUtil.fileDescriptorToHidlMemory((FileDescriptor) null, 0);
        return soundModel2;
    }

    public static Uuid aidl2hidlUuid(String str) {
        Matcher matcher = UuidUtil.PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Illegal format for UUID: ", str));
        }
        Uuid uuid = new Uuid();
        uuid.timeLow = Integer.parseUnsignedInt(matcher.group(1), 16);
        uuid.timeMid = (short) Integer.parseUnsignedInt(matcher.group(2), 16);
        uuid.versionAndTimeHigh = (short) Integer.parseUnsignedInt(matcher.group(3), 16);
        uuid.variantAndClockSeqHigh = (short) Integer.parseUnsignedInt(matcher.group(4), 16);
        uuid.node =
                new byte[] {
                    (byte) Integer.parseUnsignedInt(matcher.group(5), 16),
                    (byte) Integer.parseUnsignedInt(matcher.group(6), 16),
                    (byte) Integer.parseUnsignedInt(matcher.group(7), 16),
                    (byte) Integer.parseUnsignedInt(matcher.group(8), 16),
                    (byte) Integer.parseUnsignedInt(matcher.group(9), 16),
                    (byte) Integer.parseUnsignedInt(matcher.group(10), 16)
                };
        return uuid;
    }

    public static Properties hidl2aidlProperties(
            android.hardware.soundtrigger.V2_3.Properties properties) {
        ISoundTriggerHw.Properties properties2 = properties.base;
        Properties properties3 = new Properties();
        properties3.implementor = properties2.implementor;
        properties3.description = properties2.description;
        properties3.version = properties2.version;
        Uuid uuid = properties2.uuid;
        byte[] bArr = uuid.node;
        if (bArr == null || bArr.length != 6) {
            throw new IllegalArgumentException("UUID.node must be of length 6.");
        }
        properties3.uuid =
                String.format(
                        "%08x-%04x-%04x-%04x-%02x%02x%02x%02x%02x%02x",
                        Integer.valueOf(uuid.timeLow),
                        Short.valueOf(uuid.timeMid),
                        Short.valueOf(uuid.versionAndTimeHigh),
                        Short.valueOf(uuid.variantAndClockSeqHigh),
                        Byte.valueOf(uuid.node[0]),
                        Byte.valueOf(uuid.node[1]),
                        Byte.valueOf(uuid.node[2]),
                        Byte.valueOf(uuid.node[3]),
                        Byte.valueOf(uuid.node[4]),
                        Byte.valueOf(uuid.node[5]));
        properties3.maxSoundModels = properties2.maxSoundModels;
        properties3.maxKeyPhrases = properties2.maxKeyPhrases;
        properties3.maxUsers = properties2.maxUsers;
        properties3.recognitionModes = hidl2aidlRecognitionModes(properties2.recognitionModes);
        properties3.captureTransition = properties2.captureTransition;
        properties3.maxBufferMs = properties2.maxBufferMs;
        properties3.concurrentCapture = properties2.concurrentCapture;
        properties3.triggerInEvent = properties2.triggerInEvent;
        properties3.powerConsumptionMw = properties2.powerConsumptionMw;
        properties3.supportedModelArch = properties.supportedModelArch;
        int i = properties.audioCapabilities;
        int i2 = (i & 1) != 0 ? 1 : 0;
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        properties3.audioCapabilities = i2;
        return properties3;
    }

    public static RecognitionEvent hidl2aidlRecognitionEvent(
            ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent) {
        int i;
        int i2;
        android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback$RecognitionEvent
                iSoundTriggerHwCallback$RecognitionEvent2 =
                        (android.hardware.soundtrigger.V2_0
                                        .ISoundTriggerHwCallback$RecognitionEvent)
                                iSoundTriggerHwCallback$RecognitionEvent.header;
        RecognitionEvent recognitionEvent = new RecognitionEvent();
        int i3 = iSoundTriggerHwCallback$RecognitionEvent2.status;
        if (i3 == 0) {
            i = 0;
        } else if (i3 != 1) {
            i = 2;
            if (i3 != 2) {
                if (i3 != 3) {
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i3, "Unknown recognition status: "));
                }
                i = 3;
            }
        } else {
            i = 1;
        }
        recognitionEvent.status = i;
        int i4 = iSoundTriggerHwCallback$RecognitionEvent2.type;
        if (i4 == 0) {
            i2 = 0;
        } else {
            if (i4 != 1) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i4, "Unknown sound model type: "));
            }
            i2 = 1;
        }
        recognitionEvent.type = i2;
        recognitionEvent.captureAvailable =
                iSoundTriggerHwCallback$RecognitionEvent2.captureAvailable;
        recognitionEvent.captureDelayMs = iSoundTriggerHwCallback$RecognitionEvent2.captureDelayMs;
        recognitionEvent.capturePreambleMs =
                iSoundTriggerHwCallback$RecognitionEvent2.capturePreambleMs;
        recognitionEvent.triggerInData = iSoundTriggerHwCallback$RecognitionEvent2.triggerInData;
        AudioConfig audioConfig = new AudioConfig();
        android.hardware.audio.common.V2_0.AudioConfig audioConfig2 =
                iSoundTriggerHwCallback$RecognitionEvent2.audioConfig;
        int i5 = audioConfig2.sampleRateHz;
        int i6 = audioConfig2.channelMask;
        int i7 = audioConfig2.format;
        AudioConfigBase audioConfigBase = new AudioConfigBase();
        audioConfigBase.sampleRate = i5;
        audioConfigBase.channelMask =
                AidlConversion.legacy2aidl_audio_channel_mask_t_AudioChannelLayout(i6, true);
        audioConfigBase.format =
                AidlConversion.legacy2aidl_audio_format_t_AudioFormatDescription(i7);
        audioConfig.base = audioConfigBase;
        AudioOffloadInfo audioOffloadInfo = new AudioOffloadInfo();
        android.hardware.audio.common.V2_0.AudioOffloadInfo audioOffloadInfo2 =
                audioConfig2.offloadInfo;
        int i8 = audioOffloadInfo2.sampleRateHz;
        int i9 = audioOffloadInfo2.channelMask;
        int i10 = audioOffloadInfo2.format;
        AudioConfigBase audioConfigBase2 = new AudioConfigBase();
        audioConfigBase2.sampleRate = i8;
        audioConfigBase2.channelMask =
                AidlConversion.legacy2aidl_audio_channel_mask_t_AudioChannelLayout(i9, false);
        audioConfigBase2.format =
                AidlConversion.legacy2aidl_audio_format_t_AudioFormatDescription(i10);
        audioOffloadInfo.base = audioConfigBase2;
        audioOffloadInfo.streamType =
                AidlConversion.legacy2aidl_audio_stream_type_t_AudioStreamType(
                        audioOffloadInfo2.streamType);
        audioOffloadInfo.bitRatePerSecond = audioOffloadInfo2.bitRatePerSecond;
        audioOffloadInfo.durationUs = audioOffloadInfo2.durationMicroseconds;
        audioOffloadInfo.hasVideo = audioOffloadInfo2.hasVideo;
        audioOffloadInfo.isStreaming = audioOffloadInfo2.isStreaming;
        audioOffloadInfo.bitWidth = audioOffloadInfo2.bitWidth;
        audioOffloadInfo.offloadBufferSize = audioOffloadInfo2.bufferSize;
        audioOffloadInfo.usage =
                AidlConversion.legacy2aidl_audio_usage_t_AudioUsage(audioOffloadInfo2.usage);
        audioConfig.offloadInfo = audioOffloadInfo;
        audioConfig.frameCount = audioConfig2.frameCount;
        recognitionEvent.audioConfig = audioConfig;
        recognitionEvent.data = new byte[iSoundTriggerHwCallback$RecognitionEvent2.data.size()];
        int i11 = 0;
        while (true) {
            byte[] bArr = recognitionEvent.data;
            if (i11 >= bArr.length) {
                break;
            }
            bArr[i11] =
                    ((Byte) iSoundTriggerHwCallback$RecognitionEvent2.data.get(i11)).byteValue();
            i11++;
        }
        recognitionEvent.recognitionStillActive = recognitionEvent.status == 3;
        recognitionEvent.data =
                HidlMemoryUtil.hidlMemoryToByteArray(
                        (HidlMemory) iSoundTriggerHwCallback$RecognitionEvent.data);
        return recognitionEvent;
    }

    public static int hidl2aidlRecognitionModes(int i) {
        int i2 = (i & 1) != 0 ? 1 : 0;
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        return (i & 8) != 0 ? i2 | 8 : i2;
    }
}
