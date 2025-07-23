package android.credentials.selection;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class DisabledProviderData extends ProviderData implements Parcelable {
    public static final Parcelable.Creator<DisabledProviderData> CREATOR = new Parcelable.Creator<DisabledProviderData>() { // from class: android.credentials.selection.DisabledProviderData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisabledProviderData createFromParcel(Parcel parcel) {
            return new DisabledProviderData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisabledProviderData[] newArray(int i) {
            return new DisabledProviderData[i];
        }
    };

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisabledProviderData(String str) {
        super(str);
    }

    public DisabledProviderInfo toDisabledProviderInfo() {
        return new DisabledProviderInfo(getProviderFlattenedComponentName());
    }

    private DisabledProviderData(Parcel parcel) {
        super(parcel);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
