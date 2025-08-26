package com.android.systemui.audio.soundcraft.model.common;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import com.android.systemui.audio.soundcraft.model.phone.DolbyEnum;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.equalizerList, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.dolbyOldList, (list == null ? 0 : list.hashCode()) * 31, 31), 31);
        Boolean bool = this.spatialAudio;
        int iHashCode = (iM + (bool == null ? 0 : bool.hashCode())) * 31;
        Boolean bool2 = this.headTracking;
        int iHashCode2 = (iHashCode + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        Boolean bool3 = this.voiceBoost;
        int iHashCode3 = (iHashCode2 + (bool3 == null ? 0 : bool3.hashCode())) * 31;
        Boolean bool4 = this.volumeNormalization;
        return iHashCode3 + (bool4 != null ? bool4.hashCode() : 0);
    }

    public final String toString() {
        return "EffectModel(dolbyList=" + this.dolbyList + ", dolbyOldList=" + this.dolbyOldList + ", equalizerList=" + this.equalizerList + ", spatialAudio=" + this.spatialAudio + ", headTracking=" + this.headTracking + ", voiceBoost=" + this.voiceBoost + ", volumeNormalization=" + this.volumeNormalization + ")";
    }

    public EffectModel(List list, List list2, List list3, Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        list = (i & 1) != 0 ? null : list;
        if ((i & 2) != 0) {
            EnumEntries<DolbyEnum> enumEntries = DolbyEnum.$ENTRIES;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(enumEntries, 10));
            for (DolbyEnum dolbyEnum : enumEntries) {
                arrayList.add(dolbyEnum.getRealIndex() == 0 ? new Dolby(dolbyEnum.name(), true) : new Dolby(dolbyEnum.name(), false));
            }
            list2 = arrayList;
        }
        if ((i & 4) != 0) {
            EnumEntries enumEntries2 = SoundAliveEqEnum.$ENTRIES;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(enumEntries2, 10));
            Iterator<E> it = enumEntries2.iterator();
            while (it.hasNext()) {
                arrayList2.add(new Equalizer(((SoundAliveEqEnum) it.next()).name(), false));
            }
            list3 = arrayList2;
        }
        this(list, list2, list3, (i & 8) != 0 ? null : bool, (i & 16) != 0 ? null : bool2, (i & 32) != 0 ? null : bool3, (i & 64) != 0 ? null : bool4);
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
