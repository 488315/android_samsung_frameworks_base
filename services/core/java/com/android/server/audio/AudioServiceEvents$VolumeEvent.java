package com.android.server.audio;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.MediaMetrics;
import android.net.INetd;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioServiceEvents$VolumeEvent extends EventLogger.Event {
    public final String mCaller;
    public final String mGroupName;
    public final int mOp;
    public final int mStream;
    public final int mVal1;
    public final int mVal2;
    public final int mVal3;

    public AudioServiceEvents$VolumeEvent(int i) {
        this.mOp = 4;
        this.mVal1 = i;
        this.mVal2 = 0;
        this.mVal3 = -1;
        this.mStream = -1;
        this.mCaller = null;
        this.mGroupName = null;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(int i, int i2) {
        this.mOp = 3;
        this.mVal1 = i;
        this.mVal2 = i2;
        this.mVal3 = -1;
        this.mStream = -1;
        this.mCaller = null;
        this.mGroupName = null;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(int i, int i2, int i3) {
        this.mOp = 7;
        this.mStream = i2;
        this.mVal1 = i3;
        this.mVal2 = i;
        this.mVal3 = -1;
        this.mCaller = null;
        this.mGroupName = null;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(int i, int i2, int i3, int i4, int i5, String str) {
        this.mOp = 2;
        this.mStream = i;
        this.mVal1 = i2;
        this.mVal2 = i3;
        this.mVal3 = i4;
        this.mCaller = str;
        this.mGroupName = null;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(int i, int i2, int i3, int i4, String str) {
        this.mOp = i;
        this.mStream = i2;
        this.mVal1 = i3;
        this.mVal2 = i4;
        this.mCaller = str;
        this.mVal3 = -1;
        this.mGroupName = null;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(int i, int i2, int i3, String str, String str2) {
        this.mOp = i;
        this.mStream = -1;
        this.mVal1 = i2;
        this.mVal2 = i3;
        this.mCaller = str2;
        this.mGroupName = str;
        this.mVal3 = -1;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(int i, int i2, boolean z) {
        this.mOp = 6;
        this.mStream = i;
        this.mVal1 = i2;
        this.mVal2 = z ? 1 : 0;
        this.mVal3 = -1;
        this.mCaller = null;
        this.mGroupName = null;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(int i, boolean z) {
        this.mOp = 9;
        this.mStream = i;
        this.mVal1 = z ? 1 : 0;
        this.mVal2 = 0;
        this.mCaller = null;
        this.mGroupName = null;
        this.mVal3 = -1;
        logMetricEvent$1();
    }

    public AudioServiceEvents$VolumeEvent(boolean z) {
        this.mOp = 12;
        this.mStream = -1;
        this.mVal1 = z ? 1 : 0;
        this.mVal2 = 0;
        this.mVal3 = -1;
        this.mCaller = null;
        this.mGroupName = null;
        logMetricEvent$1();
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final String eventToString() {
        String str = this.mGroupName;
        String str2 = this.mCaller;
        int i = this.mStream;
        int i2 = this.mVal2;
        int i3 = this.mVal1;
        int i4 = this.mOp;
        switch (i4) {
            case 0:
                return "adjustSuggestedStreamVolume(sugg:"
                        + AudioSystem.streamToString(i)
                        + " dir:"
                        + AudioManager.adjustToString(i3)
                        + " flags:0x"
                        + Integer.toHexString(i2)
                        + ") from "
                        + str2;
            case 1:
                return "adjustStreamVolume(stream:"
                        + AudioSystem.streamToString(i)
                        + " dir:"
                        + AudioManager.adjustToString(i3)
                        + " flags:0x"
                        + Integer.toHexString(i2)
                        + ") from "
                        + str2;
            case 2:
                StringBuilder sb = new StringBuilder("setStreamVolume(stream:");
                sb.append(AudioSystem.streamToString(i));
                sb.append(" index:");
                sb.append(i3);
                sb.append(" flags:0x");
                BatteryService$$ExternalSyntheticOutline0.m(i2, sb, " oldIndex:");
                sb.append(this.mVal3);
                sb.append(") from ");
                sb.append(str2);
                return sb.toString();
            case 3:
                return ArrayUtils$$ExternalSyntheticOutline0.m(
                        i3, i2, "setHearingAidVolume: index:", " gain dB:");
            case 4:
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i3, "setAvrcpVolume: index:");
            case 5:
                return "adjustStreamVolumeForUid(stream:"
                        + AudioSystem.streamToString(i)
                        + " dir:"
                        + AudioManager.adjustToString(i3)
                        + " flags:0x"
                        + Integer.toHexString(i2)
                        + ") from "
                        + str2;
            case 6:
                StringBuilder sb2 = new StringBuilder("Voice activity change (");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i3,
                        i2 == 1 ? "active" : "inactive",
                        ") causes setting HEARING_AID volume to idx:",
                        " stream:",
                        sb2);
                sb2.append(AudioSystem.streamToString(i));
                return sb2.toString();
            case 7:
                return "setMode("
                        + AudioSystem.modeToString(i2)
                        + ") causes setting HEARING_AID volume to idx:"
                        + i3
                        + " stream:"
                        + AudioSystem.streamToString(i);
            case 8:
                StringBuilder m =
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i3,
                                "setVolumeIndexForAttributes(group: group: ",
                                str,
                                " index:",
                                " flags:0x");
                m.append(Integer.toHexString(i2));
                m.append(") from ");
                m.append(str2);
                return m.toString();
            case 9:
                StringBuilder sb3 = new StringBuilder("VolumeStreamState.muteInternally(stream:");
                sb3.append(AudioSystem.streamToString(i));
                sb3.append(i3 == 1 ? ", muted)" : ", unmuted)");
                return sb3.toString();
            case 10:
                return "setLeAudioVolume(stream:"
                        + AudioSystem.streamToString(i)
                        + " index:"
                        + i3
                        + " maxIndex:"
                        + i2;
            case 11:
                StringBuilder m2 =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "adjustVolumeGroupVolume(group:", str, " dir:");
                m2.append(AudioManager.adjustToString(i3));
                m2.append(" flags:0x");
                m2.append(Integer.toHexString(i2));
                m2.append(") from ");
                m2.append(str2);
                return m2.toString();
            case 12:
                return "Master mute:".concat(i3 == 1 ? " muted)" : " unmuted)");
            default:
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i4, "FIXME invalid op:");
        }
    }

    public final void logMetricEvent$1() {
        String str;
        String str2 = this.mGroupName;
        String str3 = INetd.IF_STATE_DOWN;
        String str4 = this.mCaller;
        int i = this.mStream;
        int i2 = this.mVal2;
        int i3 = this.mVal1;
        int i4 = this.mOp;
        switch (i4) {
            case 0:
            case 1:
            case 5:
                if (i4 == 0) {
                    str = "adjustSuggestedStreamVolume";
                } else if (i4 == 1) {
                    str = "adjustStreamVolume";
                } else if (i4 == 5) {
                    str = "adjustStreamVolumeForUid";
                }
                MediaMetrics.Item item =
                        new MediaMetrics.Item("audio.volume.event")
                                .set(MediaMetrics.Property.CALLING_PACKAGE, str4);
                MediaMetrics.Key key = MediaMetrics.Property.DIRECTION;
                if (i3 > 0) {
                    str3 = INetd.IF_STATE_UP;
                }
                item.set(key, str3)
                        .set(MediaMetrics.Property.EVENT, str)
                        .set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2))
                        .set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i))
                        .record();
                break;
            case 2:
                new MediaMetrics.Item("audio.volume.event")
                        .set(MediaMetrics.Property.CALLING_PACKAGE, str4)
                        .set(MediaMetrics.Property.EVENT, "setStreamVolume")
                        .set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2))
                        .set(MediaMetrics.Property.INDEX, Integer.valueOf(i3))
                        .set(MediaMetrics.Property.OLD_INDEX, Integer.valueOf(this.mVal3))
                        .set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i))
                        .record();
                break;
            case 3:
                new MediaMetrics.Item("audio.volume.event")
                        .set(MediaMetrics.Property.EVENT, "setHearingAidVolume")
                        .set(MediaMetrics.Property.GAIN_DB, Double.valueOf(i2))
                        .set(MediaMetrics.Property.INDEX, Integer.valueOf(i3))
                        .record();
                break;
            case 4:
                new MediaMetrics.Item("audio.volume.event")
                        .set(MediaMetrics.Property.EVENT, "setAvrcpVolume")
                        .set(MediaMetrics.Property.INDEX, Integer.valueOf(i3))
                        .record();
                break;
            case 6:
                new MediaMetrics.Item("audio.volume.event")
                        .set(MediaMetrics.Property.EVENT, "voiceActivityHearingAid")
                        .set(MediaMetrics.Property.INDEX, Integer.valueOf(i3))
                        .set(MediaMetrics.Property.STATE, i2 == 1 ? "active" : "inactive")
                        .set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i))
                        .record();
                break;
            case 7:
                new MediaMetrics.Item("audio.volume.event")
                        .set(MediaMetrics.Property.EVENT, "modeChangeHearingAid")
                        .set(MediaMetrics.Property.INDEX, Integer.valueOf(i3))
                        .set(MediaMetrics.Property.MODE, AudioSystem.modeToString(i2))
                        .set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i))
                        .record();
                break;
            case 8:
                new MediaMetrics.Item("audio.volume.event")
                        .set(MediaMetrics.Property.CALLING_PACKAGE, str4)
                        .set(MediaMetrics.Property.EVENT, "setVolumeIndexForAttributes")
                        .set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2))
                        .set(MediaMetrics.Property.GROUP, str2)
                        .set(MediaMetrics.Property.INDEX, Integer.valueOf(i3))
                        .record();
                break;
            case 10:
                new MediaMetrics.Item("audio.volume.event")
                        .set(MediaMetrics.Property.EVENT, "setLeAudioVolume")
                        .set(MediaMetrics.Property.INDEX, Integer.valueOf(i3))
                        .set(MediaMetrics.Property.MAX_INDEX, Integer.valueOf(i2))
                        .set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i))
                        .record();
                break;
            case 11:
                MediaMetrics.Item item2 =
                        new MediaMetrics.Item("audio.volume.event")
                                .set(MediaMetrics.Property.CALLING_PACKAGE, str4);
                MediaMetrics.Key key2 = MediaMetrics.Property.DIRECTION;
                if (i3 > 0) {
                    str3 = INetd.IF_STATE_UP;
                }
                item2.set(key2, str3)
                        .set(MediaMetrics.Property.EVENT, "adjustVolumeGroupVolume")
                        .set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2))
                        .set(MediaMetrics.Property.GROUP, str2)
                        .record();
                break;
        }
    }
}
