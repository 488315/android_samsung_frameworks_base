package com.android.server.audio;

import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.Utils;
import android.media.audio.Flags;
import android.util.Pair;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdiDeviceState {
    public final String mDeviceAddress;
    public final Pair mDeviceId;
    public final int mDeviceType;
    public boolean mHeadTrackerEnabled;
    public final int mInternalDeviceType;
    public boolean mSAEnabled;
    public int mAudioDeviceCategory = 0;
    public boolean mAutoBtCategorySet = false;
    public boolean mHasHeadTracker = false;

    public AdiDeviceState(int i, int i2, String str) {
        this.mDeviceType = i;
        if (i2 != 0) {
            this.mInternalDeviceType = i2;
        } else {
            this.mInternalDeviceType = AudioDeviceInfo.convertDeviceTypeToInternalDevice(i);
        }
        if (AudioSystem.isBluetoothDevice(this.mInternalDeviceType)) {
            Objects.requireNonNull(str);
        } else {
            str = "";
        }
        this.mDeviceAddress = str;
        this.mDeviceId = new Pair(Integer.valueOf(this.mInternalDeviceType), str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AdiDeviceState.class != obj.getClass()) {
            return false;
        }
        AdiDeviceState adiDeviceState = (AdiDeviceState) obj;
        return this.mDeviceType == adiDeviceState.mDeviceType && this.mInternalDeviceType == adiDeviceState.mInternalDeviceType && this.mDeviceAddress.equals(adiDeviceState.mDeviceAddress) && this.mSAEnabled == adiDeviceState.mSAEnabled && this.mHasHeadTracker == adiDeviceState.mHasHeadTracker && this.mHeadTrackerEnabled == adiDeviceState.mHeadTrackerEnabled && this.mAudioDeviceCategory == adiDeviceState.mAudioDeviceCategory;
    }

    public final synchronized AudioDeviceAttributes getAudioDeviceAttributes() {
        return new AudioDeviceAttributes(2, this.mDeviceType, this.mDeviceAddress);
    }

    public final synchronized int getAudioDeviceCategory() {
        return this.mAudioDeviceCategory;
    }

    public final synchronized String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public final synchronized int getInternalDeviceType() {
        return this.mInternalDeviceType;
    }

    public final synchronized boolean hasHeadTracker() {
        return this.mHasHeadTracker;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDeviceType), Integer.valueOf(this.mInternalDeviceType), this.mDeviceAddress, Boolean.valueOf(this.mSAEnabled), Boolean.valueOf(this.mHasHeadTracker), Boolean.valueOf(this.mHeadTrackerEnabled), Integer.valueOf(this.mAudioDeviceCategory));
    }

    public final synchronized boolean isHeadTrackerEnabled() {
        return this.mHeadTrackerEnabled;
    }

    public final synchronized boolean isSAEnabled() {
        return this.mSAEnabled;
    }

    public final synchronized void setAudioDeviceCategory(int i) {
        this.mAudioDeviceCategory = i;
    }

    public final synchronized void setHasHeadTracker(boolean z) {
        this.mHasHeadTracker = z;
    }

    public final synchronized void setHeadTrackerEnabled(boolean z) {
        this.mHeadTrackerEnabled = z;
    }

    public final synchronized void setSAEnabled(boolean z) {
        this.mSAEnabled = z;
    }

    public final synchronized String toPersistableString() {
        StringBuilder sb;
        try {
            sb = new StringBuilder();
            sb.append(this.mDeviceType);
            sb.append(",");
            sb.append(this.mDeviceAddress);
            sb.append(",");
            sb.append(this.mSAEnabled ? "1" : "0");
            sb.append(",");
            sb.append(this.mHasHeadTracker ? "1" : "0");
            sb.append(",");
            sb.append(this.mHeadTrackerEnabled ? "1" : "0");
            sb.append(",");
            sb.append(this.mInternalDeviceType);
            sb.append(",");
            sb.append(this.mAudioDeviceCategory);
        } catch (Throwable th) {
            throw th;
        }
        return sb.toString();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("type: ");
        sb.append(this.mDeviceType);
        sb.append(" internal type: 0x");
        int i = this.mInternalDeviceType;
        BatteryService$$ExternalSyntheticOutline0.m(i, sb, " addr: ");
        sb.append(Utils.anonymizeBluetoothAddress(i, this.mDeviceAddress));
        sb.append(" bt audio type: ");
        sb.append(AudioManager.audioDeviceCategoryToString(this.mAudioDeviceCategory));
        sb.append(" enabled: ");
        sb.append(this.mSAEnabled);
        sb.append(" HT: ");
        sb.append(this.mHasHeadTracker);
        sb.append(" HTenabled: ");
        sb.append(this.mHeadTrackerEnabled);
        return sb.toString();
    }

    public final synchronized boolean updateAudioDeviceCategory() {
        if (!Flags.automaticBtDeviceType()) {
            return false;
        }
        if (!AudioSystem.isBluetoothDevice(this.mInternalDeviceType)) {
            return false;
        }
        if (this.mAutoBtCategorySet) {
            return false;
        }
        int btDeviceCategory = BtHelper.getBtDeviceCategory(this.mDeviceAddress);
        if (btDeviceCategory == 0) {
            return false;
        }
        this.mAudioDeviceCategory = btDeviceCategory;
        this.mAutoBtCategorySet = true;
        return true;
    }
}
