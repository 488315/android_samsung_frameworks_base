package android.app.search;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class Query implements Parcelable {
    public static final Parcelable.Creator<Query> CREATOR = new Parcelable.Creator<Query>() { // from class: android.app.search.Query.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Query createFromParcel(Parcel parcel) {
            return new Query(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Query[] newArray(int i) {
            return new Query[i];
        }
    };
    public static final String EXTRA_IME_HEIGHT = "android.app.search.extra.IME_HEIGHT";
    private final Bundle mExtras;
    private final String mInput;
    private final long mTimestampMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Query(String str, long j, Bundle bundle) {
        this.mInput = str;
        this.mTimestampMillis = j;
        this.mExtras = bundle == null ? new Bundle() : bundle;
    }

    public Query(String str, long j) {
        this(str, j, new Bundle());
    }

    private Query(Parcel parcel) {
        this.mInput = parcel.readString();
        this.mTimestampMillis = parcel.readLong();
        this.mExtras = parcel.readBundle();
    }

    public String getInput() {
        return this.mInput;
    }

    @Deprecated
    public long getTimestamp() {
        return this.mTimestampMillis;
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    public Bundle getExtras() {
        Bundle bundle = this.mExtras;
        return bundle == null ? new Bundle() : bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mInput);
        parcel.writeLong(this.mTimestampMillis);
        parcel.writeBundle(this.mExtras);
    }
}
