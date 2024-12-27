package android.hardware.audio.common.V2_0;

import android.os.HidlSupport;

import java.util.Objects;

public final class AudioConfig {
    public int channelMask;
    public int format;
    public long frameCount;
    public AudioOffloadInfo offloadInfo;
    public int sampleRateHz;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != AudioConfig.class) {
            return false;
        }
        AudioConfig audioConfig = (AudioConfig) obj;
        return this.sampleRateHz == audioConfig.sampleRateHz
                && this.channelMask == audioConfig.channelMask
                && this.format == audioConfig.format
                && HidlSupport.deepEquals(this.offloadInfo, audioConfig.offloadInfo)
                && this.frameCount == audioConfig.frameCount;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.sampleRateHz),
                AudioConfig$$ExternalSyntheticOutline0.m(this.channelMask),
                AudioConfig$$ExternalSyntheticOutline0.m(this.format),
                Integer.valueOf(HidlSupport.deepHashCode(this.offloadInfo)),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.frameCount))));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.sampleRateHz = ");
        sb.append(this.sampleRateHz);
        sb.append(", .channelMask = ");
        sb.append(AudioChannelMask.toString(this.channelMask));
        sb.append(", .format = ");
        sb.append(AudioFormat.toString(this.format));
        sb.append(", .offloadInfo = ");
        sb.append(this.offloadInfo);
        sb.append(", .frameCount = ");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.frameCount, "}");
    }
}
