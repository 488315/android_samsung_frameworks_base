package com.android.systemui.volume.dialog.ringer.shared.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.shared.model.RingerMode;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class VolumeDialogRingerModel {
    public final List availableModes;
    public final int currentRingerMode;
    public final boolean isMuted;
    public final boolean isSingleVolume;
    public final int level;
    public final int levelMax;

    public /* synthetic */ VolumeDialogRingerModel(List list, int i, boolean z, int i2, int i3, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, i, z, i2, i3, z2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumeDialogRingerModel)) {
            return false;
        }
        VolumeDialogRingerModel volumeDialogRingerModel = (VolumeDialogRingerModel) obj;
        if (!Intrinsics.areEqual(this.availableModes, volumeDialogRingerModel.availableModes)) {
            return false;
        }
        Set set = RingerMode.supportedRingerModes;
        return this.currentRingerMode == volumeDialogRingerModel.currentRingerMode && this.isMuted == volumeDialogRingerModel.isMuted && this.level == volumeDialogRingerModel.level && this.levelMax == volumeDialogRingerModel.levelMax && this.isSingleVolume == volumeDialogRingerModel.isSingleVolume;
    }

    public final int hashCode() {
        int iHashCode = this.availableModes.hashCode() * 31;
        Set set = RingerMode.supportedRingerModes;
        return Boolean.hashCode(this.isSingleVolume) + ReorderTile$$ExternalSyntheticOutline0.m(this.levelMax, ReorderTile$$ExternalSyntheticOutline0.m(this.level, TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.currentRingerMode, iHashCode, 31), 31, this.isMuted), 31), 31);
    }

    public final String toString() {
        return "VolumeDialogRingerModel(availableModes=" + this.availableModes + ", currentRingerMode=" + RingerMode.m994toStringimpl(this.currentRingerMode) + ", isMuted=" + this.isMuted + ", level=" + this.level + ", levelMax=" + this.levelMax + ", isSingleVolume=" + this.isSingleVolume + ")";
    }

    private VolumeDialogRingerModel(List<RingerMode> list, int i, boolean z, int i2, int i3, boolean z2) {
        this.availableModes = list;
        this.currentRingerMode = i;
        this.isMuted = z;
        this.level = i2;
        this.levelMax = i3;
        this.isSingleVolume = z2;
    }
}
