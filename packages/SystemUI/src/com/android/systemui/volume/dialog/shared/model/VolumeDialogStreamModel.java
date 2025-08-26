package com.android.systemui.volume.dialog.shared.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.VolumeDialogController;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class VolumeDialogStreamModel {
    public final boolean isActive;
    public final boolean isDynamic;
    public final int level;
    public final int levelMax;
    public final int levelMin;
    public final boolean muteSupported;
    public final boolean muted;
    public final int name;
    public final String remoteLabel;
    public final boolean routedToBluetooth;
    public final int stream;

    public VolumeDialogStreamModel(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, boolean z4, int i5, String str, boolean z5) {
        this.stream = i;
        this.isDynamic = z;
        this.isActive = z2;
        this.level = i2;
        this.levelMin = i3;
        this.levelMax = i4;
        this.muted = z3;
        this.muteSupported = z4;
        this.name = i5;
        this.remoteLabel = str;
        this.routedToBluetooth = z5;
    }

    public static VolumeDialogStreamModel copy$default(VolumeDialogStreamModel volumeDialogStreamModel, int i) {
        int i2 = volumeDialogStreamModel.stream;
        boolean z = volumeDialogStreamModel.isDynamic;
        boolean z2 = volumeDialogStreamModel.isActive;
        int i3 = volumeDialogStreamModel.levelMin;
        int i4 = volumeDialogStreamModel.levelMax;
        boolean z3 = volumeDialogStreamModel.muted;
        boolean z4 = volumeDialogStreamModel.muteSupported;
        int i5 = volumeDialogStreamModel.name;
        String str = volumeDialogStreamModel.remoteLabel;
        boolean z5 = volumeDialogStreamModel.routedToBluetooth;
        volumeDialogStreamModel.getClass();
        return new VolumeDialogStreamModel(i2, z, z2, i, i3, i4, z3, z4, i5, str, z5);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumeDialogStreamModel)) {
            return false;
        }
        VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
        return this.stream == volumeDialogStreamModel.stream && this.isDynamic == volumeDialogStreamModel.isDynamic && this.isActive == volumeDialogStreamModel.isActive && this.level == volumeDialogStreamModel.level && this.levelMin == volumeDialogStreamModel.levelMin && this.levelMax == volumeDialogStreamModel.levelMax && this.muted == volumeDialogStreamModel.muted && this.muteSupported == volumeDialogStreamModel.muteSupported && this.name == volumeDialogStreamModel.name && Intrinsics.areEqual(this.remoteLabel, volumeDialogStreamModel.remoteLabel) && this.routedToBluetooth == volumeDialogStreamModel.routedToBluetooth;
    }

    public final int hashCode() {
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.name, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.levelMax, ReorderTile$$ExternalSyntheticOutline0.m(this.levelMin, ReorderTile$$ExternalSyntheticOutline0.m(this.level, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.stream) * 31, 31, this.isDynamic), 31, this.isActive), 31), 31), 31), 31, this.muted), 31, this.muteSupported), 31);
        String str = this.remoteLabel;
        return Boolean.hashCode(this.routedToBluetooth) + ((iM + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VolumeDialogStreamModel(stream=");
        sb.append(this.stream);
        sb.append(", isDynamic=");
        sb.append(this.isDynamic);
        sb.append(", isActive=");
        sb.append(this.isActive);
        sb.append(", level=");
        sb.append(this.level);
        sb.append(", levelMin=");
        sb.append(this.levelMin);
        sb.append(", levelMax=");
        sb.append(this.levelMax);
        sb.append(", muted=");
        sb.append(this.muted);
        sb.append(", muteSupported=");
        sb.append(this.muteSupported);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", remoteLabel=");
        sb.append(this.remoteLabel);
        sb.append(", routedToBluetooth=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.routedToBluetooth, ")");
    }

    public /* synthetic */ VolumeDialogStreamModel(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, boolean z4, int i5, String str, boolean z5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i6 & 2) != 0 ? false : z, z2, (i6 & 8) != 0 ? 0 : i2, (i6 & 16) != 0 ? 0 : i3, (i6 & 32) != 0 ? 0 : i4, (i6 & 64) != 0 ? false : z3, (i6 & 128) != 0 ? false : z4, (i6 & 256) != 0 ? 0 : i5, (i6 & 512) != 0 ? null : str, (i6 & 1024) != 0 ? false : z5);
    }

    public VolumeDialogStreamModel(int i, boolean z, VolumeDialogController.StreamState streamState) {
        this(i, streamState.dynamic, z, streamState.level, streamState.levelMin, streamState.levelMax, streamState.muted, streamState.muteSupported, streamState.name, streamState.remoteLabel, streamState.routedToBluetooth);
    }
}
