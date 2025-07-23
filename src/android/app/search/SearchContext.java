package android.app.search;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SearchContext implements Parcelable {
    public static final Parcelable.Creator<SearchContext> CREATOR = new Parcelable.Creator<SearchContext>() { // from class: android.app.search.SearchContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchContext createFromParcel(Parcel parcel) {
            return new SearchContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchContext[] newArray(int i) {
            return new SearchContext[i];
        }
    };
    private final Bundle mExtras;
    private String mPackageName;
    private final int mResultTypes;
    private final int mTimeoutMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SearchContext(int i, int i2) {
        this(i, i2, new Bundle());
    }

    public SearchContext(int i, int i2, Bundle bundle) {
        this.mResultTypes = i;
        this.mTimeoutMillis = i2;
        this.mExtras = (Bundle) Objects.requireNonNull(bundle);
    }

    private SearchContext(Parcel parcel) {
        this.mResultTypes = parcel.readInt();
        this.mTimeoutMillis = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    void setPackageName(String str) {
        this.mPackageName = str;
    }

    public int getTimeoutMillis() {
        return this.mTimeoutMillis;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getResultTypes() {
        return this.mResultTypes;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultTypes);
        parcel.writeInt(this.mTimeoutMillis);
        parcel.writeString(this.mPackageName);
        parcel.writeBundle(this.mExtras);
    }
}
