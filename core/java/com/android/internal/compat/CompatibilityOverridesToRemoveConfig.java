package com.android.internal.compat;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;
import java.util.Set;

public final class CompatibilityOverridesToRemoveConfig implements Parcelable {
    public static final Parcelable.Creator<CompatibilityOverridesToRemoveConfig> CREATOR =
            new Parcelable.Creator<
                    CompatibilityOverridesToRemoveConfig>() { // from class:
                                                              // com.android.internal.compat.CompatibilityOverridesToRemoveConfig.1
                @Override // android.os.Parcelable.Creator
                public CompatibilityOverridesToRemoveConfig createFromParcel(Parcel in) {
                    return new CompatibilityOverridesToRemoveConfig(in);
                }

                @Override // android.os.Parcelable.Creator
                public CompatibilityOverridesToRemoveConfig[] newArray(int size) {
                    return new CompatibilityOverridesToRemoveConfig[size];
                }
            };
    public final Set<Long> changeIds;

    public CompatibilityOverridesToRemoveConfig(Set<Long> changeIds) {
        this.changeIds = changeIds;
    }

    private CompatibilityOverridesToRemoveConfig(Parcel in) {
        int keyCount = in.readInt();
        this.changeIds = new HashSet();
        for (int i = 0; i < keyCount; i++) {
            this.changeIds.add(Long.valueOf(in.readLong()));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.changeIds.size());
        for (Long changeId : this.changeIds) {
            dest.writeLong(changeId.longValue());
        }
    }
}
