package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class ClassificationsRequest implements Parcelable {
    public static final Parcelable.Creator<ClassificationsRequest> CREATOR = new Parcelable.Creator<ClassificationsRequest>() { // from class: android.app.contentsuggestions.ClassificationsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClassificationsRequest createFromParcel(Parcel parcel) {
            return new ClassificationsRequest(parcel.createTypedArrayList(ContentSelection.CREATOR), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClassificationsRequest[] newArray(int i) {
            return new ClassificationsRequest[i];
        }
    };
    private final Bundle mExtras;
    private final List<ContentSelection> mSelections;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ClassificationsRequest(List<ContentSelection> list, Bundle bundle) {
        this.mSelections = list;
        this.mExtras = bundle;
    }

    public List<ContentSelection> getSelections() {
        return this.mSelections;
    }

    public Bundle getExtras() {
        Bundle bundle = this.mExtras;
        return bundle == null ? new Bundle() : bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mSelections);
        parcel.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private final List<ContentSelection> mSelections;

        public Builder(List<ContentSelection> list) {
            this.mSelections = list;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public ClassificationsRequest build() {
            return new ClassificationsRequest(this.mSelections, this.mExtras);
        }
    }
}
