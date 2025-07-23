package android.security.advancedprotection;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes3.dex */
public final class AdvancedProtectionFeature implements Parcelable {
    public static final Parcelable.Creator<AdvancedProtectionFeature> CREATOR = new Parcelable.Creator<AdvancedProtectionFeature>() { // from class: android.security.advancedprotection.AdvancedProtectionFeature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdvancedProtectionFeature createFromParcel(Parcel parcel) {
            return new AdvancedProtectionFeature(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdvancedProtectionFeature[] newArray(int i) {
            return new AdvancedProtectionFeature[i];
        }
    };
    private final int mId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AdvancedProtectionFeature(int i) {
        this.mId = i;
    }

    private AdvancedProtectionFeature(Parcel parcel) {
        this.mId = parcel.readInt();
    }

    public int getId() {
        return this.mId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
    }
}
