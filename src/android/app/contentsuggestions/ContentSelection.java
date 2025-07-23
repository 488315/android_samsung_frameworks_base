package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class ContentSelection implements Parcelable {
    public static final Parcelable.Creator<ContentSelection> CREATOR = new Parcelable.Creator<ContentSelection>() { // from class: android.app.contentsuggestions.ContentSelection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentSelection createFromParcel(Parcel parcel) {
            return new ContentSelection(parcel.readString(), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentSelection[] newArray(int i) {
            return new ContentSelection[i];
        }
    };
    private final Bundle mExtras;
    private final String mSelectionId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentSelection(String str, Bundle bundle) {
        this.mSelectionId = str;
        this.mExtras = bundle;
    }

    public String getId() {
        return this.mSelectionId;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSelectionId);
        parcel.writeBundle(this.mExtras);
    }
}
