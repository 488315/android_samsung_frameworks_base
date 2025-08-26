package com.android.internal.compat;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public final class CompatibilityOverridesToRemoveByPackageConfig implements Parcelable {
    public static final Parcelable.Creator<CompatibilityOverridesToRemoveByPackageConfig> CREATOR = new Parcelable.Creator<CompatibilityOverridesToRemoveByPackageConfig>() { // from class: com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverridesToRemoveByPackageConfig createFromParcel(Parcel parcel) {
            return new CompatibilityOverridesToRemoveByPackageConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverridesToRemoveByPackageConfig[] newArray(int i) {
            return new CompatibilityOverridesToRemoveByPackageConfig[i];
        }
    };
    public final Map<String, CompatibilityOverridesToRemoveConfig> packageNameToOverridesToRemove;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CompatibilityOverridesToRemoveByPackageConfig(Map<String, CompatibilityOverridesToRemoveConfig> map) {
        this.packageNameToOverridesToRemove = map;
    }

    private CompatibilityOverridesToRemoveByPackageConfig(Parcel parcel) {
        int i = parcel.readInt();
        this.packageNameToOverridesToRemove = new HashMap();
        for (int i2 = 0; i2 < i; i2++) {
            this.packageNameToOverridesToRemove.put(parcel.readString(), CompatibilityOverridesToRemoveConfig.CREATOR.createFromParcel(parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.packageNameToOverridesToRemove.size());
        for (String str : this.packageNameToOverridesToRemove.keySet()) {
            parcel.writeString(str);
            this.packageNameToOverridesToRemove.get(str).writeToParcel(parcel, 0);
        }
    }
}
