package com.android.systemui.audio.soundcraft.model.common;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class VolumeModel {
    public final int device;
    public final boolean enable;
    public final boolean isAllSoundMute;
    public final boolean isBroadcast;
    public final boolean isDisallowAdjustVolume;
    public final boolean isMusicShareEnabled;
    public final boolean isSmartViewEnabled;
    public final int isZenMode;
    public final boolean isZenModeDisabled;
    public final int maxVolume;
    public final int minVolume;
    public final int volume;

    public VolumeModel() {
        this(0, 0, 0, 0, false, false, false, 0, false, false, false, false, 4095, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumeModel)) {
            return false;
        }
        VolumeModel volumeModel = (VolumeModel) obj;
        return this.volume == volumeModel.volume && this.minVolume == volumeModel.minVolume && this.maxVolume == volumeModel.maxVolume && this.device == volumeModel.device && this.enable == volumeModel.enable && this.isBroadcast == volumeModel.isBroadcast && this.isAllSoundMute == volumeModel.isAllSoundMute && this.isZenMode == volumeModel.isZenMode && this.isZenModeDisabled == volumeModel.isZenModeDisabled && this.isSmartViewEnabled == volumeModel.isSmartViewEnabled && this.isMusicShareEnabled == volumeModel.isMusicShareEnabled && this.isDisallowAdjustVolume == volumeModel.isDisallowAdjustVolume;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isDisallowAdjustVolume) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.isZenMode, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.device, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.maxVolume, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.minVolume, Integer.hashCode(this.volume) * 31, 31), 31), 31), 31, this.enable), 31, this.isBroadcast), 31, this.isAllSoundMute), 31), 31, this.isZenModeDisabled), 31, this.isSmartViewEnabled), 31, this.isMusicShareEnabled);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VolumeModel(volume=");
        sb.append(this.volume);
        sb.append(", minVolume=");
        sb.append(this.minVolume);
        sb.append(", maxVolume=");
        sb.append(this.maxVolume);
        sb.append(", device=");
        sb.append(this.device);
        sb.append(", enable=");
        sb.append(this.enable);
        sb.append(", isBroadcast=");
        sb.append(this.isBroadcast);
        sb.append(", isAllSoundMute=");
        sb.append(this.isAllSoundMute);
        sb.append(", isZenMode=");
        sb.append(this.isZenMode);
        sb.append(", isZenModeDisabled=");
        sb.append(this.isZenModeDisabled);
        sb.append(", isSmartViewEnabled=");
        sb.append(this.isSmartViewEnabled);
        sb.append(", isMusicShareEnabled=");
        sb.append(this.isMusicShareEnabled);
        sb.append(", isDisallowAdjustVolume=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isDisallowAdjustVolume, ")");
    }

    public VolumeModel(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, int i5, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.volume = i;
        this.minVolume = i2;
        this.maxVolume = i3;
        this.device = i4;
        this.enable = z;
        this.isBroadcast = z2;
        this.isAllSoundMute = z3;
        this.isZenMode = i5;
        this.isZenModeDisabled = z4;
        this.isSmartViewEnabled = z5;
        this.isMusicShareEnabled = z6;
        this.isDisallowAdjustVolume = z7;
    }

    public /* synthetic */ VolumeModel(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, int i5, boolean z4, boolean z5, boolean z6, boolean z7, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 70 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? 150 : i3, (i6 & 8) != 0 ? 0 : i4, (i6 & 16) != 0 ? true : z, (i6 & 32) != 0 ? false : z2, (i6 & 64) != 0 ? false : z3, (i6 & 128) != 0 ? 0 : i5, (i6 & 256) != 0 ? false : z4, (i6 & 512) != 0 ? false : z5, (i6 & 1024) != 0 ? false : z6, (i6 & 2048) == 0 ? z7 : false);
    }
}
