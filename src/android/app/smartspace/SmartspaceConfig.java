package android.app.smartspace;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SmartspaceConfig implements Parcelable {
    public static final Parcelable.Creator<SmartspaceConfig> CREATOR = new Parcelable.Creator<SmartspaceConfig>() { // from class: android.app.smartspace.SmartspaceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceConfig createFromParcel(Parcel parcel) {
            return new SmartspaceConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceConfig[] newArray(int i) {
            return new SmartspaceConfig[i];
        }
    };
    private final Bundle mExtras;
    private String mPackageName;
    private final int mSmartspaceTargetCount;
    private final String mUiSurface;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SmartspaceConfig(String str, int i, String str2, Bundle bundle) {
        this.mUiSurface = str;
        this.mSmartspaceTargetCount = i;
        this.mPackageName = str2;
        this.mExtras = bundle;
    }

    private SmartspaceConfig(Parcel parcel) {
        this.mUiSurface = parcel.readString();
        this.mSmartspaceTargetCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getSmartspaceTargetCount() {
        return this.mSmartspaceTargetCount;
    }

    public String getUiSurface() {
        return this.mUiSurface;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUiSurface);
        parcel.writeInt(this.mSmartspaceTargetCount);
        parcel.writeString(this.mPackageName);
        parcel.writeBundle(this.mExtras);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SmartspaceConfig smartspaceConfig = (SmartspaceConfig) obj;
            if (this.mSmartspaceTargetCount == smartspaceConfig.mSmartspaceTargetCount && Objects.equals(this.mUiSurface, smartspaceConfig.mUiSurface) && Objects.equals(this.mPackageName, smartspaceConfig.mPackageName) && Objects.equals(this.mExtras, smartspaceConfig.mExtras)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSmartspaceTargetCount), this.mUiSurface, this.mPackageName, this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private final String mPackageName;
        private final String mUiSurface;
        private int mSmartspaceTargetCount = 5;
        private Bundle mExtras = Bundle.EMPTY;

        @SystemApi
        public Builder(Context context, String str) {
            this.mPackageName = context.getPackageName();
            this.mUiSurface = str;
        }

        public Builder setSmartspaceTargetCount(int i) {
            this.mSmartspaceTargetCount = i;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public SmartspaceConfig build() {
            return new SmartspaceConfig(this.mUiSurface, this.mSmartspaceTargetCount, this.mPackageName, this.mExtras);
        }
    }
}
