package com.android.internal.compat;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes5.dex */
public final class CompatibilityOverridesToRemoveConfig implements Parcelable {
    public static final Parcelable.Creator<CompatibilityOverridesToRemoveConfig> CREATOR = new Parcelable.Creator<CompatibilityOverridesToRemoveConfig>() { // from class: com.android.internal.compat.CompatibilityOverridesToRemoveConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverridesToRemoveConfig createFromParcel(Parcel parcel) {
            return new CompatibilityOverridesToRemoveConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverridesToRemoveConfig[] newArray(int i) {
            return new CompatibilityOverridesToRemoveConfig[i];
        }
    };
    public final Set<Long> changeIds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CompatibilityOverridesToRemoveConfig(Set<Long> set) {
        this.changeIds = set;
    }

    private CompatibilityOverridesToRemoveConfig(Parcel parcel) {
        int readInt = parcel.readInt();
        this.changeIds = new HashSet();
        for (int i = 0; i < readInt; i++) {
            this.changeIds.add(Long.valueOf(parcel.readLong()));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.changeIds.size());
        Iterator<Long> it = this.changeIds.iterator();
        while (it.hasNext()) {
            parcel.writeLong(it.next().longValue());
        }
    }
}
