package com.android.systemui.audio.soundcraft.model.common;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EffectModel {
    public final List dolbyList;
    public List dolbyOldList;
    public List equalizerList;
    public Boolean headTracking;
    public Boolean spatialAudio;
    public Boolean voiceBoost;
    public Boolean volumeNormalization;

    public EffectModel() {
        this(null, null, null, null, null, null, null, 127, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EffectModel)) {
            return false;
        }
        EffectModel effectModel = (EffectModel) obj;
        return Intrinsics.areEqual(this.dolbyList, effectModel.dolbyList) && Intrinsics.areEqual(this.dolbyOldList, effectModel.dolbyOldList) && Intrinsics.areEqual(this.equalizerList, effectModel.equalizerList) && Intrinsics.areEqual(this.spatialAudio, effectModel.spatialAudio) && Intrinsics.areEqual(this.headTracking, effectModel.headTracking) && Intrinsics.areEqual(this.voiceBoost, effectModel.voiceBoost) && Intrinsics.areEqual(this.volumeNormalization, effectModel.volumeNormalization);
    }

    public final int hashCode() {
        List list = this.dolbyList;
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.equalizerList, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.dolbyOldList, (list == null ? 0 : list.hashCode()) * 31, 31), 31);
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
        return "EffectModel(dolbyList=" + this.dolbyList + ", dolbyOldList=" + this.dolbyOldList + ", equalizerList=" + this.equalizerList + ", spatialAudio=" + this.spatialAudio + ", headTracking=" + this.headTracking + ", voiceBoost=" + this.voiceBoost + ", volumeNormalization=" + this.volumeNormalization + ")";
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public EffectModel(java.util.List r7, java.util.List r8, java.util.List r9, java.lang.Boolean r10, java.lang.Boolean r11, java.lang.Boolean r12, java.lang.Boolean r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r6 = this;
            r15 = r14 & 1
            r0 = 0
            if (r15 == 0) goto L6
            r7 = r0
        L6:
            r15 = r14 & 2
            r1 = 0
            r2 = 10
            if (r15 == 0) goto L47
            kotlin.enums.EnumEntries r8 = com.android.systemui.audio.soundcraft.model.phone.DolbyEnum.$ENTRIES
            java.util.ArrayList r15 = new java.util.ArrayList
            int r3 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r8, r2)
            r15.<init>(r3)
            java.util.Iterator r8 = r8.iterator()
        L1c:
            boolean r3 = r8.hasNext()
            if (r3 == 0) goto L46
            java.lang.Object r3 = r8.next()
            com.android.systemui.audio.soundcraft.model.phone.DolbyEnum r3 = (com.android.systemui.audio.soundcraft.model.phone.DolbyEnum) r3
            int r4 = r3.getRealIndex()
            if (r4 != 0) goto L39
            com.android.systemui.audio.soundcraft.model.phone.Dolby r4 = new com.android.systemui.audio.soundcraft.model.phone.Dolby
            java.lang.String r3 = r3.name()
            r5 = 1
            r4.<init>(r3, r5)
            goto L42
        L39:
            com.android.systemui.audio.soundcraft.model.phone.Dolby r4 = new com.android.systemui.audio.soundcraft.model.phone.Dolby
            java.lang.String r3 = r3.name()
            r4.<init>(r3, r1)
        L42:
            r15.add(r4)
            goto L1c
        L46:
            r8 = r15
        L47:
            r15 = r14 & 4
            if (r15 == 0) goto L74
            kotlin.enums.EnumEntries r9 = com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum.$ENTRIES
            java.util.ArrayList r15 = new java.util.ArrayList
            int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r9, r2)
            r15.<init>(r2)
            java.util.Iterator r9 = r9.iterator()
        L5a:
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto L73
            java.lang.Object r2 = r9.next()
            com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum r2 = (com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum) r2
            com.android.systemui.audio.soundcraft.model.common.Equalizer r3 = new com.android.systemui.audio.soundcraft.model.common.Equalizer
            java.lang.String r2 = r2.name()
            r3.<init>(r2, r1)
            r15.add(r3)
            goto L5a
        L73:
            r9 = r15
        L74:
            r15 = r14 & 8
            if (r15 == 0) goto L79
            r10 = r0
        L79:
            r15 = r14 & 16
            if (r15 == 0) goto L7e
            r11 = r0
        L7e:
            r15 = r14 & 32
            if (r15 == 0) goto L83
            r12 = r0
        L83:
            r14 = r14 & 64
            if (r14 == 0) goto L88
            r13 = r0
        L88:
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.model.common.EffectModel.<init>(java.util.List, java.util.List, java.util.List, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public EffectModel(List<Dolby> list, List<Dolby> list2, List<Equalizer> list3, Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4) {
        this.dolbyList = list;
        this.dolbyOldList = list2;
        this.equalizerList = list3;
        this.spatialAudio = bool;
        this.headTracking = bool2;
        this.voiceBoost = bool3;
        this.volumeNormalization = bool4;
    }
}
