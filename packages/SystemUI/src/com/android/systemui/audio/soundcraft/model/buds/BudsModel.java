package com.android.systemui.audio.soundcraft.model.buds;

import com.android.systemui.audio.soundcraft.model.common.Equalizer;
import com.google.gson.annotations.SerializedName;
import com.sec.ims.IMSParameter;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class BudsModel {

    @SerializedName("ambientSoundVolume")
    private Integer ambientSoundVolume;

    @SerializedName(IMSParameter.GENERAL.CONNECTION_STATE)
    private Boolean connectionState;

    @SerializedName("equalizerList")
    private List<Equalizer> equalizerList;

    @SerializedName("headTracking")
    private Boolean headTracking;

    @SerializedName("hearingEnhancementsAmbientSound")
    private Boolean hearingEnhancement;

    @SerializedName("maxActiveNoiseCancelingLevel")
    private Integer maxActiveNoiseCancelingLevel;

    @SerializedName("maxAmbientSoundVolume")
    private Integer maxAmbientSoundVolume;

    @SerializedName("activeNoiseCancelingLevel")
    private Integer noiseCancelingLevel;

    @SerializedName("noiseControlsList")
    private Set<NoiseControl> noiseControlsList;

    @SerializedName("oneEarbudNoiseControls")
    private Boolean oneEarbudNoiseControls;

    @SerializedName("spatialAudio")
    private Boolean spatialAudio;

    @SerializedName("touchControls")
    private Boolean touchControls;

    @SerializedName("ultraHighQualityAudio")
    private Boolean uhq;

    @SerializedName("voiceBoost")
    private Boolean voiceBoost;

    @SerializedName("volumeNormalization")
    private Boolean volumeNormalization;

    @SerializedName("wearingL")
    private Boolean wearingL;

    @SerializedName("wearingR")
    private Boolean wearingR;

    public BudsModel() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 131071, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BudsModel)) {
            return false;
        }
        BudsModel budsModel = (BudsModel) obj;
        return Intrinsics.areEqual(this.connectionState, budsModel.connectionState) && Intrinsics.areEqual(this.equalizerList, budsModel.equalizerList) && Intrinsics.areEqual(this.noiseControlsList, budsModel.noiseControlsList) && Intrinsics.areEqual(this.maxActiveNoiseCancelingLevel, budsModel.maxActiveNoiseCancelingLevel) && Intrinsics.areEqual(this.noiseCancelingLevel, budsModel.noiseCancelingLevel) && Intrinsics.areEqual(this.maxAmbientSoundVolume, budsModel.maxAmbientSoundVolume) && Intrinsics.areEqual(this.ambientSoundVolume, budsModel.ambientSoundVolume) && Intrinsics.areEqual(this.hearingEnhancement, budsModel.hearingEnhancement) && Intrinsics.areEqual(this.touchControls, budsModel.touchControls) && Intrinsics.areEqual(this.wearingL, budsModel.wearingL) && Intrinsics.areEqual(this.wearingR, budsModel.wearingR) && Intrinsics.areEqual(this.oneEarbudNoiseControls, budsModel.oneEarbudNoiseControls) && Intrinsics.areEqual(this.spatialAudio, budsModel.spatialAudio) && Intrinsics.areEqual(this.headTracking, budsModel.headTracking) && Intrinsics.areEqual(this.voiceBoost, budsModel.voiceBoost) && Intrinsics.areEqual(this.volumeNormalization, budsModel.volumeNormalization) && Intrinsics.areEqual(this.uhq, budsModel.uhq);
    }

    public final Integer getAmbientSoundVolume() {
        return this.ambientSoundVolume;
    }

    public final Boolean getConnectionState() {
        return this.connectionState;
    }

    public final List getEqualizerList() {
        return this.equalizerList;
    }

    public final Boolean getHeadTracking() {
        return this.headTracking;
    }

    public final Boolean getHearingEnhancement() {
        return this.hearingEnhancement;
    }

    public final Integer getMaxActiveNoiseCancelingLevel() {
        return this.maxActiveNoiseCancelingLevel;
    }

    public final Integer getMaxAmbientSoundVolume() {
        return this.maxAmbientSoundVolume;
    }

    public final Integer getNoiseCancelingLevel() {
        return this.noiseCancelingLevel;
    }

    public final Set getNoiseControlsList() {
        return this.noiseControlsList;
    }

    public final Boolean getOneEarbudNoiseControls() {
        return this.oneEarbudNoiseControls;
    }

    public final Boolean getSpatialAudio() {
        return this.spatialAudio;
    }

    public final Boolean getUhq() {
        return this.uhq;
    }

    public final Boolean getVoiceBoost() {
        return this.voiceBoost;
    }

    public final Boolean getVolumeNormalization() {
        return this.volumeNormalization;
    }

    public final Boolean getWearingL() {
        return this.wearingL;
    }

    public final Boolean getWearingR() {
        return this.wearingR;
    }

    public final int hashCode() {
        Boolean bool = this.connectionState;
        int hashCode = (bool == null ? 0 : bool.hashCode()) * 31;
        List<Equalizer> list = this.equalizerList;
        int hashCode2 = (hashCode + (list == null ? 0 : list.hashCode())) * 31;
        Set<NoiseControl> set = this.noiseControlsList;
        int hashCode3 = (hashCode2 + (set == null ? 0 : set.hashCode())) * 31;
        Integer num = this.maxActiveNoiseCancelingLevel;
        int hashCode4 = (hashCode3 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.noiseCancelingLevel;
        int hashCode5 = (hashCode4 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.maxAmbientSoundVolume;
        int hashCode6 = (hashCode5 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.ambientSoundVolume;
        int hashCode7 = (hashCode6 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Boolean bool2 = this.hearingEnhancement;
        int hashCode8 = (hashCode7 + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        Boolean bool3 = this.touchControls;
        int hashCode9 = (hashCode8 + (bool3 == null ? 0 : bool3.hashCode())) * 31;
        Boolean bool4 = this.wearingL;
        int hashCode10 = (hashCode9 + (bool4 == null ? 0 : bool4.hashCode())) * 31;
        Boolean bool5 = this.wearingR;
        int hashCode11 = (hashCode10 + (bool5 == null ? 0 : bool5.hashCode())) * 31;
        Boolean bool6 = this.oneEarbudNoiseControls;
        int hashCode12 = (hashCode11 + (bool6 == null ? 0 : bool6.hashCode())) * 31;
        Boolean bool7 = this.spatialAudio;
        int hashCode13 = (hashCode12 + (bool7 == null ? 0 : bool7.hashCode())) * 31;
        Boolean bool8 = this.headTracking;
        int hashCode14 = (hashCode13 + (bool8 == null ? 0 : bool8.hashCode())) * 31;
        Boolean bool9 = this.voiceBoost;
        int hashCode15 = (hashCode14 + (bool9 == null ? 0 : bool9.hashCode())) * 31;
        Boolean bool10 = this.volumeNormalization;
        int hashCode16 = (hashCode15 + (bool10 == null ? 0 : bool10.hashCode())) * 31;
        Boolean bool11 = this.uhq;
        return hashCode16 + (bool11 != null ? bool11.hashCode() : 0);
    }

    public final void setAmbientSoundVolume(Integer num) {
        this.ambientSoundVolume = num;
    }

    public final void setConnectionState() {
        this.connectionState = Boolean.FALSE;
    }

    public final void setEqualizerList(List list) {
        this.equalizerList = list;
    }

    public final void setHeadTracking(Boolean bool) {
        this.headTracking = bool;
    }

    public final void setNoiseCancelingLevel(Integer num) {
        this.noiseCancelingLevel = num;
    }

    public final void setNoiseControlsList(Set set) {
        this.noiseControlsList = set;
    }

    public final void setSpatialAudio(Boolean bool) {
        this.spatialAudio = bool;
    }

    public final void setVoiceBoost(Boolean bool) {
        this.voiceBoost = bool;
    }

    public final void setVolumeNormalization(Boolean bool) {
        this.volumeNormalization = bool;
    }

    public final String toString() {
        return "BudsModel(connectionState=" + this.connectionState + ", equalizerList=" + this.equalizerList + ", noiseControlsList=" + this.noiseControlsList + ", maxActiveNoiseCancelingLevel=" + this.maxActiveNoiseCancelingLevel + ", noiseCancelingLevel=" + this.noiseCancelingLevel + ", maxAmbientSoundVolume=" + this.maxAmbientSoundVolume + ", ambientSoundVolume=" + this.ambientSoundVolume + ", hearingEnhancement=" + this.hearingEnhancement + ", touchControls=" + this.touchControls + ", wearingL=" + this.wearingL + ", wearingR=" + this.wearingR + ", oneEarbudNoiseControls=" + this.oneEarbudNoiseControls + ", spatialAudio=" + this.spatialAudio + ", headTracking=" + this.headTracking + ", voiceBoost=" + this.voiceBoost + ", volumeNormalization=" + this.volumeNormalization + ", uhq=" + this.uhq + ")";
    }

    public BudsModel(Boolean bool, List<Equalizer> list, Set<NoiseControl> set, Integer num, Integer num2, Integer num3, Integer num4, Boolean bool2, Boolean bool3, Boolean bool4, Boolean bool5, Boolean bool6, Boolean bool7, Boolean bool8, Boolean bool9, Boolean bool10, Boolean bool11) {
        this.connectionState = bool;
        this.equalizerList = list;
        this.noiseControlsList = set;
        this.maxActiveNoiseCancelingLevel = num;
        this.noiseCancelingLevel = num2;
        this.maxAmbientSoundVolume = num3;
        this.ambientSoundVolume = num4;
        this.hearingEnhancement = bool2;
        this.touchControls = bool3;
        this.wearingL = bool4;
        this.wearingR = bool5;
        this.oneEarbudNoiseControls = bool6;
        this.spatialAudio = bool7;
        this.headTracking = bool8;
        this.voiceBoost = bool9;
        this.volumeNormalization = bool10;
        this.uhq = bool11;
    }

    public /* synthetic */ BudsModel(Boolean bool, List list, Set set, Integer num, Integer num2, Integer num3, Integer num4, Boolean bool2, Boolean bool3, Boolean bool4, Boolean bool5, Boolean bool6, Boolean bool7, Boolean bool8, Boolean bool9, Boolean bool10, Boolean bool11, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : bool, (i & 2) != 0 ? null : list, (i & 4) != 0 ? null : set, (i & 8) != 0 ? null : num, (i & 16) != 0 ? null : num2, (i & 32) != 0 ? null : num3, (i & 64) != 0 ? null : num4, (i & 128) != 0 ? null : bool2, (i & 256) != 0 ? null : bool3, (i & 512) != 0 ? null : bool4, (i & 1024) != 0 ? null : bool5, (i & 2048) != 0 ? null : bool6, (i & 4096) != 0 ? null : bool7, (i & 8192) != 0 ? null : bool8, (i & 16384) != 0 ? null : bool9, (i & 32768) != 0 ? null : bool10, (i & 65536) != 0 ? null : bool11);
    }
}
