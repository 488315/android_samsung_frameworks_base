package android.service.games;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class GameSessionActivityResult implements Parcelable {
    public static final Parcelable.Creator<GameSessionActivityResult> CREATOR = new Parcelable.Creator<GameSessionActivityResult>() { // from class: android.service.games.GameSessionActivityResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameSessionActivityResult createFromParcel(Parcel parcel) {
            return new GameSessionActivityResult(parcel.readInt(), (Intent) parcel.readParcelable(Intent.class.getClassLoader(), Intent.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameSessionActivityResult[] newArray(int i) {
            return new GameSessionActivityResult[i];
        }
    };
    private final Intent mData;
    private final int mResultCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GameSessionActivityResult(int i, Intent intent) {
        this.mResultCode = i;
        this.mData = intent;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public Intent getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeParcelable(this.mData, i);
    }
}
