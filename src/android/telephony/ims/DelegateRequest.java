package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public final class DelegateRequest implements Parcelable {
    public static final Parcelable.Creator<DelegateRequest> CREATOR = new Parcelable.Creator<DelegateRequest>() { // from class: android.telephony.ims.DelegateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DelegateRequest createFromParcel(Parcel parcel) {
            return new DelegateRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DelegateRequest[] newArray(int i) {
            return new DelegateRequest[i];
        }
    };
    private final ArrayList<String> mFeatureTags;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DelegateRequest(Set<String> set) {
        if (set == null) {
            throw new IllegalStateException("Invalid arguments, featureTags List can not be null");
        }
        this.mFeatureTags = new ArrayList<>(set);
    }

    public Set<String> getFeatureTags() {
        return new ArraySet(this.mFeatureTags);
    }

    private DelegateRequest(Parcel parcel) {
        ArrayList<String> arrayList = new ArrayList<>();
        this.mFeatureTags = arrayList;
        parcel.readList(arrayList, null, String.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.mFeatureTags);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mFeatureTags.equals(((DelegateRequest) obj).mFeatureTags);
    }

    public int hashCode() {
        return Objects.hash(this.mFeatureTags);
    }

    public String toString() {
        return "DelegateRequest{mFeatureTags=" + this.mFeatureTags + '}';
    }
}
