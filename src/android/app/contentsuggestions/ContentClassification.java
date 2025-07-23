package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class ContentClassification implements Parcelable {
    public static final Parcelable.Creator<ContentClassification> CREATOR = new Parcelable.Creator<ContentClassification>() { // from class: android.app.contentsuggestions.ContentClassification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentClassification createFromParcel(Parcel parcel) {
            return new ContentClassification(parcel.readString(), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentClassification[] newArray(int i) {
            return new ContentClassification[i];
        }
    };
    private final String mClassificationId;
    private final Bundle mExtras;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentClassification(String str, Bundle bundle) {
        this.mClassificationId = str;
        this.mExtras = bundle;
    }

    public String getId() {
        return this.mClassificationId;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mClassificationId);
        parcel.writeBundle(this.mExtras);
    }
}
