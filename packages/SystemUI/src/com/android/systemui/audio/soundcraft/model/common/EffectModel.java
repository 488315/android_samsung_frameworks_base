package com.android.systemui.audio.soundcraft.model.common;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class EffectModel {
    public final List dolbyList;
    public List equalizerList;
    public Boolean headTracking;
    public Boolean spatialAudio;
    public Boolean voiceBoost;
    public Boolean volumeNormalization;

    public EffectModel() {
        this(null, null, null, null, null, null, 63, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EffectModel)) {
            return false;
        }
        EffectModel effectModel = (EffectModel) obj;
        return Intrinsics.areEqual(this.dolbyList, effectModel.dolbyList) && Intrinsics.areEqual(this.equalizerList, effectModel.equalizerList) && Intrinsics.areEqual(this.spatialAudio, effectModel.spatialAudio) && Intrinsics.areEqual(this.headTracking, effectModel.headTracking) && Intrinsics.areEqual(this.voiceBoost, effectModel.voiceBoost) && Intrinsics.areEqual(this.volumeNormalization, effectModel.volumeNormalization);
    }

    public final int hashCode() {
        List list = this.dolbyList;
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.equalizerList, (list == null ? 0 : list.hashCode()) * 31, 31);
        Boolean bool = this.spatialAudio;
        int hashCode = (m + (bool == null ? 0 : bool.hashCode())) * 31;
        Boolean bool2 = this.headTracking;
        int hashCode2 = (hashCode + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        Boolean bool3 = this.voiceBoost;
        int hashCode3 = (hashCode2 + (bool3 == null ? 0 : bool3.hashCode())) * 31;
        Boolean bool4 = this.volumeNormalization;
        return hashCode3 + (bool4 != null ? bool4.hashCode() : 0);
    }

    public final String toString() {
        return "EffectModel(dolbyList=" + this.dolbyList + ", equalizerList=" + this.equalizerList + ", spatialAudio=" + this.spatialAudio + ", headTracking=" + this.headTracking + ", voiceBoost=" + this.voiceBoost + ", volumeNormalization=" + this.volumeNormalization + ")";
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public EffectModel(java.util.List r5, java.util.List r6, java.lang.Boolean r7, java.lang.Boolean r8, java.lang.Boolean r9, java.lang.Boolean r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r4 = this;
            r12 = r11 & 1
            r0 = 0
            if (r12 == 0) goto L6
            r5 = r0
        L6:
            r12 = r11 & 2
            if (r12 == 0) goto L36
            kotlin.enums.EnumEntries r6 = com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum.$ENTRIES
            java.util.ArrayList r12 = new java.util.ArrayList
            r1 = 10
            int r1 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r6, r1)
            r12.<init>(r1)
            java.util.Iterator r6 = r6.iterator()
        L1b:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L35
            java.lang.Object r1 = r6.next()
            com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum r1 = (com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum) r1
            com.android.systemui.audio.soundcraft.model.common.Equalizer r2 = new com.android.systemui.audio.soundcraft.model.common.Equalizer
            java.lang.String r1 = r1.name()
            r3 = 0
            r2.<init>(r1, r3)
            r12.add(r2)
            goto L1b
        L35:
            r6 = r12
        L36:
            r12 = r11 & 4
            if (r12 == 0) goto L3b
            r7 = r0
        L3b:
            r12 = r11 & 8
            if (r12 == 0) goto L40
            r8 = r0
        L40:
            r12 = r11 & 16
            if (r12 == 0) goto L45
            r9 = r0
        L45:
            r11 = r11 & 32
            if (r11 == 0) goto L4a
            r10 = r0
        L4a:
            r4.<init>(r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.model.common.EffectModel.<init>(java.util.List, java.util.List, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public EffectModel(List<Dolby> list, List<Equalizer> list2, Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4) {
        this.dolbyList = list;
        this.equalizerList = list2;
        this.spatialAudio = bool;
        this.headTracking = bool2;
        this.voiceBoost = bool3;
        this.volumeNormalization = bool4;
    }
}
