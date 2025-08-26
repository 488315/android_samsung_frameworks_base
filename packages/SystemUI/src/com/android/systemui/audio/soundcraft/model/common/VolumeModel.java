package com.android.systemui.audio.soundcraft.model.common;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

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
        return Boolean.hashCode(this.isDisallowAdjustVolume) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.isZenMode, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.device, ReorderTile$$ExternalSyntheticOutline0.m(this.maxVolume, ReorderTile$$ExternalSyntheticOutline0.m(this.minVolume, Integer.hashCode(this.volume) * 31, 31), 31), 31), 31, this.enable), 31, this.isBroadcast), 31, this.isAllSoundMute), 31), 31, this.isZenModeDisabled), 31, this.isSmartViewEnabled), 31, this.isMusicShareEnabled);
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
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isDisallowAdjustVolume, ")");
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

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ VolumeModel(int r2, int r3, int r4, int r5, boolean r6, boolean r7, boolean r8, int r9, boolean r10, boolean r11, boolean r12, boolean r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r1 = this;
            r15 = r14 & 1
            if (r15 == 0) goto L6
            r2 = 70
        L6:
            r15 = r14 & 2
            r0 = 0
            if (r15 == 0) goto Lc
            r3 = r0
        Lc:
            r15 = r14 & 4
            if (r15 == 0) goto L12
            r4 = 150(0x96, float:2.1E-43)
        L12:
            r15 = r14 & 8
            if (r15 == 0) goto L17
            r5 = r0
        L17:
            r15 = r14 & 16
            if (r15 == 0) goto L1c
            r6 = 1
        L1c:
            r15 = r14 & 32
            if (r15 == 0) goto L21
            r7 = r0
        L21:
            r15 = r14 & 64
            if (r15 == 0) goto L26
            r8 = r0
        L26:
            r15 = r14 & 128(0x80, float:1.8E-43)
            if (r15 == 0) goto L2b
            r9 = r0
        L2b:
            r15 = r14 & 256(0x100, float:3.59E-43)
            if (r15 == 0) goto L30
            r10 = r0
        L30:
            r15 = r14 & 512(0x200, float:7.17E-43)
            if (r15 == 0) goto L35
            r11 = r0
        L35:
            r15 = r14 & 1024(0x400, float:1.435E-42)
            if (r15 == 0) goto L3a
            r12 = r0
        L3a:
            r14 = r14 & 2048(0x800, float:2.87E-42)
            if (r14 == 0) goto L4c
            r15 = r0
            r13 = r11
            r14 = r12
            r11 = r9
            r12 = r10
            r9 = r7
            r10 = r8
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            goto L59
        L4c:
            r15 = r13
            r14 = r12
            r12 = r10
            r13 = r11
            r10 = r8
            r11 = r9
            r8 = r6
            r9 = r7
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
        L59:
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.model.common.VolumeModel.<init>(int, int, int, int, boolean, boolean, boolean, int, boolean, boolean, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
