package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

@SystemApi
public final class TokenInfo implements Parcelable {
    public static final Parcelable.Creator<TokenInfo> CREATOR =
            new Parcelable.Creator<
                    TokenInfo>() { // from class: android.app.ondeviceintelligence.TokenInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TokenInfo[] newArray(int size) {
                    return new TokenInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TokenInfo createFromParcel(Parcel in) {
                    return new TokenInfo(in.readLong(), in.readPersistableBundle());
                }
            };
    private final long mCount;
    private final PersistableBundle mInfoParams;

    public TokenInfo(long count, PersistableBundle persistableBundle) {
        this.mCount = count;
        this.mInfoParams = persistableBundle;
    }

    public TokenInfo(long count) {
        this.mCount = count;
        this.mInfoParams = new PersistableBundle();
    }

    public long getCount() {
        return this.mCount;
    }

    public PersistableBundle getInfoParams() {
        return this.mInfoParams;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mCount);
        dest.writePersistableBundle(this.mInfoParams);
    }
}
