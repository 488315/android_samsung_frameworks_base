package android.app.search;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SearchSessionId implements Parcelable {
    public static final Parcelable.Creator<SearchSessionId> CREATOR = new Parcelable.Creator<SearchSessionId>() { // from class: android.app.search.SearchSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchSessionId createFromParcel(Parcel parcel) {
            return new SearchSessionId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchSessionId[] newArray(int i) {
            return new SearchSessionId[i];
        }
    };
    private final String mId;
    private final int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SearchSessionId(String str, int i) {
        this.mId = str;
        this.mUserId = i;
    }

    private SearchSessionId(Parcel parcel) {
        this.mId = parcel.readString();
        this.mUserId = parcel.readInt();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public boolean equals(Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        SearchSessionId searchSessionId = (SearchSessionId) obj;
        return this.mId.equals(searchSessionId.mId) && this.mUserId == searchSessionId.mUserId;
    }

    public String toString() {
        return this.mId + "," + this.mUserId;
    }

    public int hashCode() {
        return Objects.hash(this.mId, Integer.valueOf(this.mUserId));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mUserId);
    }
}
