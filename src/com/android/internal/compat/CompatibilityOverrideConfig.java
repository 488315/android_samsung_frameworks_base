package com.android.internal.compat;

import android.app.compat.PackageOverride;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public final class CompatibilityOverrideConfig implements Parcelable {
    public static final Parcelable.Creator<CompatibilityOverrideConfig> CREATOR = new Parcelable.Creator<CompatibilityOverrideConfig>() { // from class: com.android.internal.compat.CompatibilityOverrideConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverrideConfig createFromParcel(Parcel parcel) {
            return new CompatibilityOverrideConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityOverrideConfig[] newArray(int i) {
            return new CompatibilityOverrideConfig[i];
        }
    };
    public final Map<Long, PackageOverride> overrides;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CompatibilityOverrideConfig(Map<Long, PackageOverride> map) {
        this.overrides = map;
    }

    private CompatibilityOverrideConfig(Parcel parcel) {
        int readInt = parcel.readInt();
        this.overrides = new HashMap();
        for (int i = 0; i < readInt; i++) {
            this.overrides.put(Long.valueOf(parcel.readLong()), PackageOverride.createFromParcel(parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.overrides.size());
        for (Long l : this.overrides.keySet()) {
            parcel.writeLong(l.longValue());
            this.overrides.get(l).writeToParcel(parcel);
        }
    }
}
