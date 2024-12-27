package android.content;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.Preconditions;

import java.io.PrintWriter;

public final class LocusId implements Parcelable {
    public static final Parcelable.Creator<LocusId> CREATOR =
            new Parcelable.Creator<LocusId>() { // from class: android.content.LocusId.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LocusId createFromParcel(Parcel parcel) {
                    return new LocusId(parcel.readString());
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LocusId[] newArray(int size) {
                    return new LocusId[size];
                }
            };
    private final String mId;

    public LocusId(String id) {
        this.mId = (String) Preconditions.checkStringNotEmpty(id, "id cannot be empty");
    }

    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        int result = (1 * 31) + (this.mId == null ? 0 : this.mId.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LocusId other = (LocusId) obj;
        if (this.mId == null) {
            if (other.mId != null) {
                return false;
            }
        } else if (!this.mId.equals(other.mId)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "LocusId[" + getSanitizedId() + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public void dump(PrintWriter pw) {
        pw.print("id:");
        pw.println(getSanitizedId());
    }

    private String getSanitizedId() {
        int size = this.mId.length();
        return size + "_chars";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.mId);
    }
}
