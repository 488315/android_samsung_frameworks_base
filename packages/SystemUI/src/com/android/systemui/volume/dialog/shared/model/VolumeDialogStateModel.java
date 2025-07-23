package com.android.systemui.volume.dialog.shared.model;

import android.content.ComponentName;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogStateModel {
    public final int activeStream;
    public final boolean disallowAlarms;
    public final boolean disallowMedia;
    public final boolean disallowRinger;
    public final boolean disallowSystem;
    public final ComponentName effectsSuppressor;
    public final String effectsSuppressorName;
    public final boolean isHovering;
    public final VolumeDialogCsdWarningModel isShowingCsdWarning;
    public final VolumeDialogSafetyWarningModel isShowingSafetyWarning;
    public final int ringerModeExternal;
    public final int ringerModeInternal;
    public final boolean shouldShowA11ySlider;
    public final Map streamModels;
    public final int zenMode;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public VolumeDialogStateModel() {
        this(false, null, null, false, null, 0, 0, 0, null, null, 0, false, false, false, false, 32767, null);
    }

    public static VolumeDialogStateModel copy$default(VolumeDialogStateModel volumeDialogStateModel, boolean z, VolumeDialogSafetyWarningModel volumeDialogSafetyWarningModel, VolumeDialogCsdWarningModel volumeDialogCsdWarningModel, boolean z2, Map map, int i, int i2, int i3, ComponentName componentName, String str, int i4, boolean z3, boolean z4, boolean z5, boolean z6, int i5) {
        boolean z7 = (i5 & 1) != 0 ? volumeDialogStateModel.shouldShowA11ySlider : z;
        VolumeDialogSafetyWarningModel volumeDialogSafetyWarningModel2 = (i5 & 2) != 0 ? volumeDialogStateModel.isShowingSafetyWarning : volumeDialogSafetyWarningModel;
        VolumeDialogCsdWarningModel volumeDialogCsdWarningModel2 = (i5 & 4) != 0 ? volumeDialogStateModel.isShowingCsdWarning : volumeDialogCsdWarningModel;
        boolean z8 = (i5 & 8) != 0 ? volumeDialogStateModel.isHovering : z2;
        Map map2 = (i5 & 16) != 0 ? volumeDialogStateModel.streamModels : map;
        int i6 = (i5 & 32) != 0 ? volumeDialogStateModel.ringerModeInternal : i;
        int i7 = (i5 & 64) != 0 ? volumeDialogStateModel.ringerModeExternal : i2;
        int i8 = (i5 & 128) != 0 ? volumeDialogStateModel.zenMode : i3;
        ComponentName componentName2 = (i5 & 256) != 0 ? volumeDialogStateModel.effectsSuppressor : componentName;
        String str2 = (i5 & 512) != 0 ? volumeDialogStateModel.effectsSuppressorName : str;
        int i9 = (i5 & 1024) != 0 ? volumeDialogStateModel.activeStream : i4;
        boolean z9 = (i5 & 2048) != 0 ? volumeDialogStateModel.disallowAlarms : z3;
        boolean z10 = (i5 & 4096) != 0 ? volumeDialogStateModel.disallowMedia : z4;
        boolean z11 = (i5 & 8192) != 0 ? volumeDialogStateModel.disallowSystem : z5;
        boolean z12 = (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? volumeDialogStateModel.disallowRinger : z6;
        volumeDialogStateModel.getClass();
        return new VolumeDialogStateModel(z7, volumeDialogSafetyWarningModel2, volumeDialogCsdWarningModel2, z8, map2, i6, i7, i8, componentName2, str2, i9, z9, z10, z11, z12);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumeDialogStateModel)) {
            return false;
        }
        VolumeDialogStateModel volumeDialogStateModel = (VolumeDialogStateModel) obj;
        return this.shouldShowA11ySlider == volumeDialogStateModel.shouldShowA11ySlider && Intrinsics.areEqual(this.isShowingSafetyWarning, volumeDialogStateModel.isShowingSafetyWarning) && Intrinsics.areEqual(this.isShowingCsdWarning, volumeDialogStateModel.isShowingCsdWarning) && this.isHovering == volumeDialogStateModel.isHovering && Intrinsics.areEqual(this.streamModels, volumeDialogStateModel.streamModels) && this.ringerModeInternal == volumeDialogStateModel.ringerModeInternal && this.ringerModeExternal == volumeDialogStateModel.ringerModeExternal && this.zenMode == volumeDialogStateModel.zenMode && Intrinsics.areEqual(this.effectsSuppressor, volumeDialogStateModel.effectsSuppressor) && Intrinsics.areEqual(this.effectsSuppressorName, volumeDialogStateModel.effectsSuppressorName) && this.activeStream == volumeDialogStateModel.activeStream && this.disallowAlarms == volumeDialogStateModel.disallowAlarms && this.disallowMedia == volumeDialogStateModel.disallowMedia && this.disallowSystem == volumeDialogStateModel.disallowSystem && this.disallowRinger == volumeDialogStateModel.disallowRinger;
    }

    public final int hashCode() {
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.zenMode, ReorderTile$$ExternalSyntheticOutline0.m(this.ringerModeExternal, ReorderTile$$ExternalSyntheticOutline0.m(this.ringerModeInternal, (this.streamModels.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((this.isShowingCsdWarning.hashCode() + ((this.isShowingSafetyWarning.hashCode() + (Boolean.hashCode(this.shouldShowA11ySlider) * 31)) * 31)) * 31, 31, this.isHovering)) * 31, 31), 31), 31);
        ComponentName componentName = this.effectsSuppressor;
        int hashCode = (m + (componentName == null ? 0 : componentName.hashCode())) * 31;
        String str = this.effectsSuppressorName;
        return Boolean.hashCode(this.disallowRinger) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.activeStream, (hashCode + (str != null ? str.hashCode() : 0)) * 31, 31), 31, this.disallowAlarms), 31, this.disallowMedia), 31, this.disallowSystem);
    }

    public final String toString() {
        Map map = this.streamModels;
        ComponentName componentName = this.effectsSuppressor;
        StringBuilder sb = new StringBuilder("VolumeDialogStateModel(shouldShowA11ySlider=");
        sb.append(this.shouldShowA11ySlider);
        sb.append(", isShowingSafetyWarning=");
        sb.append(this.isShowingSafetyWarning);
        sb.append(", isShowingCsdWarning=");
        sb.append(this.isShowingCsdWarning);
        sb.append(", isHovering=");
        sb.append(this.isHovering);
        sb.append(", streamModels=");
        sb.append(map);
        sb.append(", ringerModeInternal=");
        sb.append(this.ringerModeInternal);
        sb.append(", ringerModeExternal=");
        sb.append(this.ringerModeExternal);
        sb.append(", zenMode=");
        sb.append(this.zenMode);
        sb.append(", effectsSuppressor=");
        sb.append(componentName);
        sb.append(", effectsSuppressorName=");
        sb.append(this.effectsSuppressorName);
        sb.append(", activeStream=");
        sb.append(this.activeStream);
        sb.append(", disallowAlarms=");
        sb.append(this.disallowAlarms);
        sb.append(", disallowMedia=");
        sb.append(this.disallowMedia);
        sb.append(", disallowSystem=");
        sb.append(this.disallowSystem);
        sb.append(", disallowRinger=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.disallowRinger, ")");
    }

    public VolumeDialogStateModel(boolean z, VolumeDialogSafetyWarningModel volumeDialogSafetyWarningModel, VolumeDialogCsdWarningModel volumeDialogCsdWarningModel, boolean z2, Map<Integer, VolumeDialogStreamModel> map, int i, int i2, int i3, ComponentName componentName, String str, int i4, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.shouldShowA11ySlider = z;
        this.isShowingSafetyWarning = volumeDialogSafetyWarningModel;
        this.isShowingCsdWarning = volumeDialogCsdWarningModel;
        this.isHovering = z2;
        this.streamModels = map;
        this.ringerModeInternal = i;
        this.ringerModeExternal = i2;
        this.zenMode = i3;
        this.effectsSuppressor = componentName;
        this.effectsSuppressorName = str;
        this.activeStream = i4;
        this.disallowAlarms = z3;
        this.disallowMedia = z4;
        this.disallowSystem = z5;
        this.disallowRinger = z6;
    }

    public /* synthetic */ VolumeDialogStateModel(boolean z, VolumeDialogSafetyWarningModel volumeDialogSafetyWarningModel, VolumeDialogCsdWarningModel volumeDialogCsdWarningModel, boolean z2, Map map, int i, int i2, int i3, ComponentName componentName, String str, int i4, boolean z3, boolean z4, boolean z5, boolean z6, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? false : z, (i5 & 2) != 0 ? VolumeDialogSafetyWarningModel.Invisible.INSTANCE : volumeDialogSafetyWarningModel, (i5 & 4) != 0 ? VolumeDialogCsdWarningModel.Invisible.INSTANCE : volumeDialogCsdWarningModel, (i5 & 8) != 0 ? false : z2, (i5 & 16) != 0 ? MapsKt__MapsKt.emptyMap() : map, (i5 & 32) != 0 ? 0 : i, (i5 & 64) != 0 ? 0 : i2, (i5 & 128) != 0 ? 0 : i3, (i5 & 256) != 0 ? null : componentName, (i5 & 512) == 0 ? str : null, (i5 & 1024) != 0 ? -1 : i4, (i5 & 2048) != 0 ? false : z3, (i5 & 4096) != 0 ? false : z4, (i5 & 8192) != 0 ? false : z5, (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? false : z6);
    }
}
