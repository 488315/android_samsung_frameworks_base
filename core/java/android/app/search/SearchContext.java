package android.app.search;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@SystemApi
public final class SearchContext implements Parcelable {
    public static final Parcelable.Creator<SearchContext> CREATOR =
            new Parcelable.Creator<
                    SearchContext>() { // from class: android.app.search.SearchContext.1
                @Override // android.os.Parcelable.Creator
                public SearchContext createFromParcel(Parcel parcel) {
                    return new SearchContext(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public SearchContext[] newArray(int size) {
                    return new SearchContext[size];
                }
            };
    private final Bundle mExtras;
    private String mPackageName;
    private final int mResultTypes;
    private final int mTimeoutMillis;

    public SearchContext(int resultTypes, int timeoutMillis) {
        this(resultTypes, timeoutMillis, new Bundle());
    }

    public SearchContext(int resultTypes, int timeoutMillis, Bundle extras) {
        this.mResultTypes = resultTypes;
        this.mTimeoutMillis = timeoutMillis;
        this.mExtras = (Bundle) Objects.requireNonNull(extras);
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

    void setPackageName(String packageName) {
        this.mPackageName = packageName;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mResultTypes);
        dest.writeInt(this.mTimeoutMillis);
        dest.writeString(this.mPackageName);
        dest.writeBundle(this.mExtras);
    }
}
