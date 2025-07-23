package android.service.settings.preferences;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class MetadataRequest implements Parcelable {
    public static final Parcelable.Creator<MetadataRequest> CREATOR = new Parcelable.Creator<MetadataRequest>() { // from class: android.service.settings.preferences.MetadataRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MetadataRequest createFromParcel(Parcel parcel) {
            return new MetadataRequest();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MetadataRequest[] newArray(int i) {
            return new MetadataRequest[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    private MetadataRequest() {
    }

    public static final class Builder {
        public MetadataRequest build() {
            return new MetadataRequest();
        }
    }
}
