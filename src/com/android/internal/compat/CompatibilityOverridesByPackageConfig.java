package com.android.internal.compat;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public final class CompatibilityOverridesByPackageConfig implements Parcelable {
    public static final Parcelable.Creator<CompatibilityOverridesByPackageConfig> CREATOR = new Parcelable.Creator<CompatibilityOverridesByPackageConfig>() { // from class: com.android.internal.compat.CompatibilityOverridesByPackageConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverridesByPackageConfig createFromParcel(Parcel parcel) {
            return new CompatibilityOverridesByPackageConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverridesByPackageConfig[] newArray(int i) {
            return new CompatibilityOverridesByPackageConfig[i];
        }
    };
    public final Map<String, CompatibilityOverrideConfig> packageNameToOverrides;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CompatibilityOverridesByPackageConfig(Map<String, CompatibilityOverrideConfig> map) {
        this.packageNameToOverrides = map;
    }

    private CompatibilityOverridesByPackageConfig(Parcel parcel) {
        int i = parcel.readInt();
        this.packageNameToOverrides = new HashMap();
        for (int i2 = 0; i2 < i; i2++) {
            this.packageNameToOverrides.put(parcel.readString(), CompatibilityOverrideConfig.CREATOR.createFromParcel(parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.packageNameToOverrides.size());
        for (String str : this.packageNameToOverrides.keySet()) {
            parcel.writeString(str);
            this.packageNameToOverrides.get(str).writeToParcel(parcel, 0);
        }
    }
}
