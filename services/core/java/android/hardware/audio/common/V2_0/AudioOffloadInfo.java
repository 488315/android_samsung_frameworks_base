package android.hardware.audio.common.V2_0;

import android.os.HidlSupport;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AudioOffloadInfo {
    public int bitRatePerSecond;
    public int bitWidth;
    public int bufferSize;
    public int channelMask;
    public long durationMicroseconds;
    public int format;
    public boolean hasVideo;
    public boolean isStreaming;
    public int sampleRateHz;
    public int streamType;
    public int usage;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != AudioOffloadInfo.class) {
            return false;
        }
        AudioOffloadInfo audioOffloadInfo = (AudioOffloadInfo) obj;
        return this.sampleRateHz == audioOffloadInfo.sampleRateHz
                && this.channelMask == audioOffloadInfo.channelMask
                && this.format == audioOffloadInfo.format
                && this.streamType == audioOffloadInfo.streamType
                && this.bitRatePerSecond == audioOffloadInfo.bitRatePerSecond
                && this.durationMicroseconds == audioOffloadInfo.durationMicroseconds
                && this.hasVideo == audioOffloadInfo.hasVideo
                && this.isStreaming == audioOffloadInfo.isStreaming
                && this.bitWidth == audioOffloadInfo.bitWidth
                && this.bufferSize == audioOffloadInfo.bufferSize
                && this.usage == audioOffloadInfo.usage;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.sampleRateHz),
                AudioConfig$$ExternalSyntheticOutline0.m(this.channelMask),
                AudioConfig$$ExternalSyntheticOutline0.m(this.format),
                AudioConfig$$ExternalSyntheticOutline0.m(this.streamType),
                AudioConfig$$ExternalSyntheticOutline0.m(this.bitRatePerSecond),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.durationMicroseconds))),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.hasVideo),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.isStreaming),
                AudioConfig$$ExternalSyntheticOutline0.m(this.bitWidth),
                AudioConfig$$ExternalSyntheticOutline0.m(this.bufferSize),
                AudioConfig$$ExternalSyntheticOutline0.m(this.usage));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.sampleRateHz = ");
        sb.append(this.sampleRateHz);
        sb.append(", .channelMask = ");
        sb.append(AudioChannelMask.toString(this.channelMask));
        sb.append(", .format = ");
        sb.append(AudioFormat.toString(this.format));
        sb.append(", .streamType = ");
        int i = this.streamType;
        String str = "CNT";
        sb.append(
                i == -1
                        ? "DEFAULT"
                        : i == 0
                                ? "MIN"
                                : i == 0
                                        ? "VOICE_CALL"
                                        : i == 1
                                                ? "SYSTEM"
                                                : i == 2
                                                        ? "RING"
                                                        : i == 3
                                                                ? "MUSIC"
                                                                : i == 4
                                                                        ? "ALARM"
                                                                        : i == 5
                                                                                ? "NOTIFICATION"
                                                                                : i == 6
                                                                                        ? "BLUETOOTH_SCO"
                                                                                        : i == 7
                                                                                                ? "ENFORCED_AUDIBLE"
                                                                                                : i
                                                                                                                == 8
                                                                                                        ? "DTMF"
                                                                                                        : i
                                                                                                                        == 9
                                                                                                                ? "TTS"
                                                                                                                : i
                                                                                                                                == 10
                                                                                                                        ? "ACCESSIBILITY"
                                                                                                                        : i
                                                                                                                                        == 11
                                                                                                                                ? "REROUTING"
                                                                                                                                : i
                                                                                                                                                == 12
                                                                                                                                        ? "PATCH"
                                                                                                                                        : i
                                                                                                                                                        == 11
                                                                                                                                                ? "PUBLIC_CNT"
                                                                                                                                                : i
                                                                                                                                                                == 12
                                                                                                                                                        ? "FOR_POLICY_CNT"
                                                                                                                                                        : i
                                                                                                                                                                        == 13
                                                                                                                                                                ? "CNT"
                                                                                                                                                                : AudioChannelMask$$ExternalSyntheticOutline0
                                                                                                                                                                        .m(
                                                                                                                                                                                new StringBuilder(
                                                                                                                                                                                        "0x"),
                                                                                                                                                                                i));
        sb.append(", .bitRatePerSecond = ");
        sb.append(this.bitRatePerSecond);
        sb.append(", .durationMicroseconds = ");
        sb.append(this.durationMicroseconds);
        sb.append(", .hasVideo = ");
        sb.append(this.hasVideo);
        sb.append(", .isStreaming = ");
        sb.append(this.isStreaming);
        sb.append(", .bitWidth = ");
        sb.append(this.bitWidth);
        sb.append(", .bufferSize = ");
        sb.append(this.bufferSize);
        sb.append(", .usage = ");
        int i2 = this.usage;
        if (i2 == 0) {
            str = "UNKNOWN";
        } else if (i2 == 1) {
            str = "MEDIA";
        } else if (i2 == 2) {
            str = "VOICE_COMMUNICATION";
        } else if (i2 == 3) {
            str = "VOICE_COMMUNICATION_SIGNALLING";
        } else if (i2 == 4) {
            str = "ALARM";
        } else if (i2 == 5) {
            str = "NOTIFICATION";
        } else if (i2 == 6) {
            str = "NOTIFICATION_TELEPHONY_RINGTONE";
        } else if (i2 == 7) {
            str = "NOTIFICATION_COMMUNICATION_REQUEST";
        } else if (i2 == 8) {
            str = "NOTIFICATION_COMMUNICATION_INSTANT";
        } else if (i2 == 9) {
            str = "NOTIFICATION_COMMUNICATION_DELAYED";
        } else if (i2 == 10) {
            str = "NOTIFICATION_EVENT";
        } else if (i2 == 11) {
            str = "ASSISTANCE_ACCESSIBILITY";
        } else if (i2 == 12) {
            str = "ASSISTANCE_NAVIGATION_GUIDANCE";
        } else if (i2 == 13) {
            str = "ASSISTANCE_SONIFICATION";
        } else if (i2 == 14) {
            str = "GAME";
        } else if (i2 == 15) {
            str = "VIRTUAL_SOURCE";
        } else if (i2 == 16) {
            str = "ASSISTANT";
        } else if (i2 != 17) {
            str =
                    i2 == 16
                            ? "MAX"
                            : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("0x"), i2);
        }
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str, "}");
    }
}
