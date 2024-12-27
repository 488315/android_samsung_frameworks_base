package com.android.internal.compat;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public final class CompatibilityOverridesByPackageConfig implements Parcelable {
    public static final Parcelable.Creator<CompatibilityOverridesByPackageConfig> CREATOR =
            new Parcelable.Creator<
                    CompatibilityOverridesByPackageConfig>() { // from class:
                                                               // com.android.internal.compat.CompatibilityOverridesByPackageConfig.1
                @Override // android.os.Parcelable.Creator
                public CompatibilityOverridesByPackageConfig createFromParcel(Parcel in) {
                    return new CompatibilityOverridesByPackageConfig(in);
                }

                @Override // android.os.Parcelable.Creator
                public CompatibilityOverridesByPackageConfig[] newArray(int size) {
                    return new CompatibilityOverridesByPackageConfig[size];
                }
            };
    public final Map<String, CompatibilityOverrideConfig> packageNameToOverrides;

    public CompatibilityOverridesByPackageConfig(
            Map<String, CompatibilityOverrideConfig> packageNameToOverrides) {
        this.packageNameToOverrides = packageNameToOverrides;
    }

    private CompatibilityOverridesByPackageConfig(Parcel in) {
        int keyCount = in.readInt();
        this.packageNameToOverrides = new HashMap();
        for (int i = 0; i < keyCount; i++) {
            String key = in.readString();
            this.packageNameToOverrides.put(
                    key, CompatibilityOverrideConfig.CREATOR.createFromParcel(in));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.packageNameToOverrides.size());
        for (String key : this.packageNameToOverrides.keySet()) {
            dest.writeString(key);
            this.packageNameToOverrides.get(key).writeToParcel(dest, 0);
        }
    }
}
