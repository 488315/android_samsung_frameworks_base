package android.hardware.soundtrigger.V2_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ISoundTriggerHwCallback$RecognitionEvent {
    public final AudioConfig audioConfig;
    public ArrayList data;
    public int status = 0;
    public int type = 0;
    public int model = 0;
    public boolean captureAvailable = false;
    public int captureSession = 0;
    public int captureDelayMs = 0;
    public int capturePreambleMs = 0;
    public boolean triggerInData = false;

    public ISoundTriggerHwCallback$RecognitionEvent() {
        AudioConfig audioConfig = new AudioConfig();
        audioConfig.sampleRateHz = 0;
        audioConfig.channelMask = 0;
        audioConfig.format = 0;
        AudioOffloadInfo audioOffloadInfo = new AudioOffloadInfo();
        audioOffloadInfo.sampleRateHz = 0;
        audioOffloadInfo.channelMask = 0;
        audioOffloadInfo.format = 0;
        audioOffloadInfo.streamType = 0;
        audioOffloadInfo.bitRatePerSecond = 0;
        audioOffloadInfo.durationMicroseconds = 0L;
        audioOffloadInfo.hasVideo = false;
        audioOffloadInfo.isStreaming = false;
        audioOffloadInfo.bitWidth = 0;
        audioOffloadInfo.bufferSize = 0;
        audioOffloadInfo.usage = 0;
        audioConfig.offloadInfo = audioOffloadInfo;
        audioConfig.frameCount = 0L;
        this.audioConfig = audioConfig;
        this.data = new ArrayList();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ISoundTriggerHwCallback$RecognitionEvent.class) {
            return false;
        }
        ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent =
                (ISoundTriggerHwCallback$RecognitionEvent) obj;
        return this.status == iSoundTriggerHwCallback$RecognitionEvent.status
                && this.type == iSoundTriggerHwCallback$RecognitionEvent.type
                && this.model == iSoundTriggerHwCallback$RecognitionEvent.model
                && this.captureAvailable
                        == iSoundTriggerHwCallback$RecognitionEvent.captureAvailable
                && this.captureSession == iSoundTriggerHwCallback$RecognitionEvent.captureSession
                && this.captureDelayMs == iSoundTriggerHwCallback$RecognitionEvent.captureDelayMs
                && this.capturePreambleMs
                        == iSoundTriggerHwCallback$RecognitionEvent.capturePreambleMs
                && this.triggerInData == iSoundTriggerHwCallback$RecognitionEvent.triggerInData
                && HidlSupport.deepEquals(
                        this.audioConfig, iSoundTriggerHwCallback$RecognitionEvent.audioConfig)
                && HidlSupport.deepEquals(this.data, iSoundTriggerHwCallback$RecognitionEvent.data);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.status),
                AudioConfig$$ExternalSyntheticOutline0.m(this.type),
                AudioConfig$$ExternalSyntheticOutline0.m(this.model),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.captureAvailable),
                AudioConfig$$ExternalSyntheticOutline0.m(this.captureSession),
                AudioConfig$$ExternalSyntheticOutline0.m(this.captureDelayMs),
                AudioConfig$$ExternalSyntheticOutline0.m(this.capturePreambleMs),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.triggerInData),
                Integer.valueOf(HidlSupport.deepHashCode(this.audioConfig)),
                Integer.valueOf(HidlSupport.deepHashCode(this.data)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob) {
        this.status = hwBlob.getInt32(0L);
        this.type = hwBlob.getInt32(4L);
        this.model = hwBlob.getInt32(8L);
        this.captureAvailable = hwBlob.getBool(12L);
        this.captureSession = hwBlob.getInt32(16L);
        this.captureDelayMs = hwBlob.getInt32(20L);
        this.capturePreambleMs = hwBlob.getInt32(24L);
        this.triggerInData = hwBlob.getBool(28L);
        AudioConfig audioConfig = this.audioConfig;
        audioConfig.getClass();
        audioConfig.sampleRateHz = hwBlob.getInt32(32L);
        audioConfig.channelMask = hwBlob.getInt32(36L);
        audioConfig.format = hwBlob.getInt32(40L);
        AudioOffloadInfo audioOffloadInfo = audioConfig.offloadInfo;
        audioOffloadInfo.getClass();
        audioOffloadInfo.sampleRateHz = hwBlob.getInt32(48L);
        audioOffloadInfo.channelMask = hwBlob.getInt32(52L);
        audioOffloadInfo.format = hwBlob.getInt32(56L);
        audioOffloadInfo.streamType = hwBlob.getInt32(60L);
        audioOffloadInfo.bitRatePerSecond = hwBlob.getInt32(64L);
        audioOffloadInfo.durationMicroseconds = hwBlob.getInt64(72L);
        audioOffloadInfo.hasVideo = hwBlob.getBool(80L);
        audioOffloadInfo.isStreaming = hwBlob.getBool(81L);
        audioOffloadInfo.bitWidth = hwBlob.getInt32(84L);
        audioOffloadInfo.bufferSize = hwBlob.getInt32(88L);
        audioOffloadInfo.usage = hwBlob.getInt32(92L);
        audioConfig.frameCount = hwBlob.getInt64(96L);
        int int32 = hwBlob.getInt32(112L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), 104L, true);
        this.data.clear();
        for (int i = 0; i < int32; i++) {
            this.data.add(Byte.valueOf(readEmbeddedBuffer.getInt8(i)));
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.status = ");
        int i = this.status;
        sb.append(
                i == 0
                        ? "SUCCESS"
                        : i == 1
                                ? "ABORT"
                                : i == 2
                                        ? "FAILURE"
                                        : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("0x"), i));
        sb.append(", .type = ");
        sb.append(SoundModelType.toString(this.type));
        sb.append(", .model = ");
        sb.append(this.model);
        sb.append(", .captureAvailable = ");
        sb.append(this.captureAvailable);
        sb.append(", .captureSession = ");
        sb.append(this.captureSession);
        sb.append(", .captureDelayMs = ");
        sb.append(this.captureDelayMs);
        sb.append(", .capturePreambleMs = ");
        sb.append(this.capturePreambleMs);
        sb.append(", .triggerInData = ");
        sb.append(this.triggerInData);
        sb.append(", .audioConfig = ");
        sb.append(this.audioConfig);
        sb.append(", .data = ");
        sb.append(this.data);
        sb.append("}");
        return sb.toString();
    }
}
