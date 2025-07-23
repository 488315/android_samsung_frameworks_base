package android.content;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class LocusId implements Parcelable {
    public static final Parcelable.Creator<LocusId> CREATOR = new Parcelable.Creator<LocusId>() { // from class: android.content.LocusId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocusId createFromParcel(Parcel parcel) {
            return new LocusId(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocusId[] newArray(int i) {
            return new LocusId[i];
        }
    };
    private final String mId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocusId(String str) {
        this.mId = (String) Preconditions.checkStringNotEmpty(str, "id cannot be empty");
    }

    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        String str = this.mId;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LocusId locusId = (LocusId) obj;
        String str = this.mId;
        if (str == null) {
            if (locusId.mId != null) {
                return false;
            }
        } else if (!str.equals(locusId.mId)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "LocusId[" + getSanitizedId() + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print("id:");
        printWriter.println(getSanitizedId());
    }

    private String getSanitizedId() {
        return this.mId.length() + "_chars";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
    }
}
