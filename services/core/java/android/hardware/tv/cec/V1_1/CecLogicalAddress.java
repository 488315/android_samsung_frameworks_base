package android.hardware.tv.cec.V1_1;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class CecLogicalAddress {
    public static final String toString(int i) {
        return i == 0 ? "TV" : i == 1 ? "RECORDER_1" : i == 2 ? "RECORDER_2" : i == 3 ? "TUNER_1" : i == 4 ? "PLAYBACK_1" : i == 5 ? "AUDIO_SYSTEM" : i == 6 ? "TUNER_2" : i == 7 ? "TUNER_3" : i == 8 ? "PLAYBACK_2" : i == 9 ? "RECORDER_3" : i == 10 ? "TUNER_4" : i == 11 ? "PLAYBACK_3" : i == 14 ? "FREE_USE" : i == 15 ? "UNREGISTERED" : i == 15 ? "BROADCAST" : i == 12 ? "BACKUP_1" : i == 13 ? "BACKUP_2" : AudioChannelMask$$ExternalSyntheticOutline0.m(new StringBuilder("0x"), i);
    }
}
