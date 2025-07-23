package com.android.settingslib.volume.shared.model;

import android.media.AudioSystem;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.shared.model.AudioStream;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioStreamModel {
    public final int audioStream;
    public final boolean isAffectedByMute;
    public final boolean isAffectedByRingerMode;
    public final boolean isMuted;
    public final int maxVolume;
    public final int minVolume;
    public final int volume;

    public /* synthetic */ AudioStreamModel(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, i4, z, z2, z3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioStreamModel)) {
            return false;
        }
        AudioStreamModel audioStreamModel = (AudioStreamModel) obj;
        int i = audioStreamModel.audioStream;
        AudioStream.Companion companion = AudioStream.Companion;
        return this.audioStream == i && this.volume == audioStreamModel.volume && this.minVolume == audioStreamModel.minVolume && this.maxVolume == audioStreamModel.maxVolume && this.isAffectedByMute == audioStreamModel.isAffectedByMute && this.isAffectedByRingerMode == audioStreamModel.isAffectedByRingerMode && this.isMuted == audioStreamModel.isMuted;
    }

    public final int hashCode() {
        AudioStream.Companion companion = AudioStream.Companion;
        return Boolean.hashCode(this.isMuted) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.maxVolume, ReorderTile$$ExternalSyntheticOutline0.m(this.minVolume, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, Integer.hashCode(this.audioStream) * 31, 31), 31), 31), 31, this.isAffectedByMute), 31, this.isAffectedByRingerMode);
    }

    public final String toString() {
        AudioStream.Companion companion = AudioStream.Companion;
        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("AudioStreamModel(audioStream=", AudioSystem.streamToString(this.audioStream), ", volume=");
        m.append(this.volume);
        m.append(", minVolume=");
        m.append(this.minVolume);
        m.append(", maxVolume=");
        m.append(this.maxVolume);
        m.append(", isAffectedByMute=");
        m.append(this.isAffectedByMute);
        m.append(", isAffectedByRingerMode=");
        m.append(this.isAffectedByRingerMode);
        m.append(", isMuted=");
        return MoveResult$$ExternalSyntheticOutline0.m(m, this.isMuted, ")");
    }

    private AudioStreamModel(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        this.audioStream = i;
        this.volume = i2;
        this.minVolume = i3;
        this.maxVolume = i4;
        this.isAffectedByMute = z;
        this.isAffectedByRingerMode = z2;
        this.isMuted = z3;
    }
}
