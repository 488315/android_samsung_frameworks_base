package android.credentials.selection;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class DisabledProviderData extends ProviderData implements Parcelable {
    public static final Parcelable.Creator<DisabledProviderData> CREATOR = new Parcelable.Creator<DisabledProviderData>() { // from class: android.credentials.selection.DisabledProviderData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisabledProviderData createFromParcel(Parcel in) {
            return new DisabledProviderData(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisabledProviderData[] newArray(int size) {
            return new DisabledProviderData[size];
        }
    };

    public DisabledProviderData(String providerFlattenedComponentName) {
        super(providerFlattenedComponentName);
    }

    public DisabledProviderInfo toDisabledProviderInfo() {
        return new DisabledProviderInfo(getProviderFlattenedComponentName());
    }

    private DisabledProviderData(Parcel in) {
        super(in);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
