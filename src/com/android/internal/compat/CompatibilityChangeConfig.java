package com.android.internal.compat;

import android.compat.Compatibility;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes5.dex */
public final class CompatibilityChangeConfig implements Parcelable {
    public static final Parcelable.Creator<CompatibilityChangeConfig> CREATOR = new Parcelable.Creator<CompatibilityChangeConfig>() { // from class: com.android.internal.compat.CompatibilityChangeConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityChangeConfig createFromParcel(Parcel parcel) {
            return new CompatibilityChangeConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityChangeConfig[] newArray(int i) {
            return new CompatibilityChangeConfig[i];
        }
    };
    private final Compatibility.ChangeConfig mChangeConfig;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CompatibilityChangeConfig(Compatibility.ChangeConfig changeConfig) {
        this.mChangeConfig = changeConfig;
    }

    public Set<Long> enabledChanges() {
        return this.mChangeConfig.getEnabledSet();
    }

    public Set<Long> disabledChanges() {
        return this.mChangeConfig.getDisabledSet();
    }

    public boolean isChangeEnabled(long j) {
        if (this.mChangeConfig.isForceEnabled(j)) {
            return true;
        }
        if (this.mChangeConfig.isForceDisabled(j)) {
            return false;
        }
        throw new IllegalStateException("Change " + j + " is not defined.");
    }

    private CompatibilityChangeConfig(Parcel parcel) {
        this.mChangeConfig = new Compatibility.ChangeConfig(toLongSet(parcel.createLongArray()), toLongSet(parcel.createLongArray()));
    }

    private static Set<Long> toLongSet(long[] jArr) {
        HashSet hashSet = new HashSet();
        for (long j : jArr) {
            hashSet.add(Long.valueOf(j));
        }
        return hashSet;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        long[] enabledChangesArray = this.mChangeConfig.getEnabledChangesArray();
        long[] disabledChangesArray = this.mChangeConfig.getDisabledChangesArray();
        parcel.writeLongArray(enabledChangesArray);
        parcel.writeLongArray(disabledChangesArray);
    }
}
