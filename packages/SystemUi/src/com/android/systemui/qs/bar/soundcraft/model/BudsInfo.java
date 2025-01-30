package com.android.systemui.qs.bar.soundcraft.model;

import com.google.gson.annotations.SerializedName;
import com.sec.ims.IMSParameter;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BudsInfo {

    @SerializedName(IMSParameter.GENERAL.CONNECTION_STATE)
    private Boolean connectionState;

    @SerializedName("equalizerList")
    private List<Equalizer> equalizerList;

    @SerializedName("headTracking")
    private Boolean headTracking;

    @SerializedName("noiseCancelingLevel")
    private Integer noiseCancelingLevel;

    @SerializedName("noiseControlsList")
    private Set<NoiseControl> noiseControlsList;

    @SerializedName("oneEarbudNoiseControls")
    private Boolean oneEarbudNoiseControls;

    @SerializedName("spatialAudio")
    private Boolean spatialAudio;

    @SerializedName("touchControls")
    private Boolean touchControls;

    @SerializedName("voiceBoost")
    private Boolean voiceBoost;

    @SerializedName("volumeNormalization")
    private Boolean volumeNormalization;

    @SerializedName("wearingL")
    private Boolean wearingL;

    @SerializedName("wearingR")
    private Boolean wearingR;

    public BudsInfo() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, 4095, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BudsInfo)) {
            return false;
        }
        BudsInfo budsInfo = (BudsInfo) obj;
        return Intrinsics.areEqual(this.connectionState, budsInfo.connectionState) && Intrinsics.areEqual(this.equalizerList, budsInfo.equalizerList) && Intrinsics.areEqual(this.noiseControlsList, budsInfo.noiseControlsList) && Intrinsics.areEqual(this.noiseCancelingLevel, budsInfo.noiseCancelingLevel) && Intrinsics.areEqual(this.touchControls, budsInfo.touchControls) && Intrinsics.areEqual(this.wearingL, budsInfo.wearingL) && Intrinsics.areEqual(this.wearingR, budsInfo.wearingR) && Intrinsics.areEqual(this.oneEarbudNoiseControls, budsInfo.oneEarbudNoiseControls) && Intrinsics.areEqual(this.spatialAudio, budsInfo.spatialAudio) && Intrinsics.areEqual(this.headTracking, budsInfo.headTracking) && Intrinsics.areEqual(this.voiceBoost, budsInfo.voiceBoost) && Intrinsics.areEqual(this.volumeNormalization, budsInfo.volumeNormalization);
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

    public final Integer getNoiseCancelingLevel() {
        return this.noiseCancelingLevel;
    }

    public final Set getNoiseControlsList() {
        return this.noiseControlsList;
    }

    public final Boolean getSpatialAudio() {
        return this.spatialAudio;
    }

    public final Boolean getVoiceBoost() {
        return this.voiceBoost;
    }

    public final Boolean getVolumeNormalization() {
        return this.volumeNormalization;
    }

    public final int hashCode() {
        Boolean bool = this.connectionState;
        int hashCode = (bool == null ? 0 : bool.hashCode()) * 31;
        List<Equalizer> list = this.equalizerList;
        int hashCode2 = (hashCode + (list == null ? 0 : list.hashCode())) * 31;
        Set<NoiseControl> set = this.noiseControlsList;
        int hashCode3 = (hashCode2 + (set == null ? 0 : set.hashCode())) * 31;
        Integer num = this.noiseCancelingLevel;
        int hashCode4 = (hashCode3 + (num == null ? 0 : num.hashCode())) * 31;
        Boolean bool2 = this.touchControls;
        int hashCode5 = (hashCode4 + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        Boolean bool3 = this.wearingL;
        int hashCode6 = (hashCode5 + (bool3 == null ? 0 : bool3.hashCode())) * 31;
        Boolean bool4 = this.wearingR;
        int hashCode7 = (hashCode6 + (bool4 == null ? 0 : bool4.hashCode())) * 31;
        Boolean bool5 = this.oneEarbudNoiseControls;
        int hashCode8 = (hashCode7 + (bool5 == null ? 0 : bool5.hashCode())) * 31;
        Boolean bool6 = this.spatialAudio;
        int hashCode9 = (hashCode8 + (bool6 == null ? 0 : bool6.hashCode())) * 31;
        Boolean bool7 = this.headTracking;
        int hashCode10 = (hashCode9 + (bool7 == null ? 0 : bool7.hashCode())) * 31;
        Boolean bool8 = this.voiceBoost;
        int hashCode11 = (hashCode10 + (bool8 == null ? 0 : bool8.hashCode())) * 31;
        Boolean bool9 = this.volumeNormalization;
        return hashCode11 + (bool9 != null ? bool9.hashCode() : 0);
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
        return "BudsInfo(connectionState=" + this.connectionState + ", equalizerList=" + this.equalizerList + ", noiseControlsList=" + this.noiseControlsList + ", noiseCancelingLevel=" + this.noiseCancelingLevel + ", touchControls=" + this.touchControls + ", wearingL=" + this.wearingL + ", wearingR=" + this.wearingR + ", oneEarbudNoiseControls=" + this.oneEarbudNoiseControls + ", spatialAudio=" + this.spatialAudio + ", headTracking=" + this.headTracking + ", voiceBoost=" + this.voiceBoost + ", volumeNormalization=" + this.volumeNormalization + ")";
    }

    public BudsInfo(Boolean bool, List<Equalizer> list, Set<NoiseControl> set, Integer num, Boolean bool2, Boolean bool3, Boolean bool4, Boolean bool5, Boolean bool6, Boolean bool7, Boolean bool8, Boolean bool9) {
        this.connectionState = bool;
        this.equalizerList = list;
        this.noiseControlsList = set;
        this.noiseCancelingLevel = num;
        this.touchControls = bool2;
        this.wearingL = bool3;
        this.wearingR = bool4;
        this.oneEarbudNoiseControls = bool5;
        this.spatialAudio = bool6;
        this.headTracking = bool7;
        this.voiceBoost = bool8;
        this.volumeNormalization = bool9;
    }

    public /* synthetic */ BudsInfo(Boolean bool, List list, Set set, Integer num, Boolean bool2, Boolean bool3, Boolean bool4, Boolean bool5, Boolean bool6, Boolean bool7, Boolean bool8, Boolean bool9, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : bool, (i & 2) != 0 ? null : list, (i & 4) != 0 ? null : set, (i & 8) != 0 ? null : num, (i & 16) != 0 ? null : bool2, (i & 32) != 0 ? null : bool3, (i & 64) != 0 ? null : bool4, (i & 128) != 0 ? null : bool5, (i & 256) != 0 ? null : bool6, (i & 512) != 0 ? null : bool7, (i & 1024) != 0 ? null : bool8, (i & 2048) != 0 ? null : bool9);
    }
}
