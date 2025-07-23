package android.app.wallpapereffectsgeneration;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class CinematicEffectRequest implements Parcelable {
    public static final Parcelable.Creator<CinematicEffectRequest> CREATOR = new Parcelable.Creator<CinematicEffectRequest>() { // from class: android.app.wallpapereffectsgeneration.CinematicEffectRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CinematicEffectRequest createFromParcel(Parcel parcel) {
            return new CinematicEffectRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CinematicEffectRequest[] newArray(int i) {
            return new CinematicEffectRequest[i];
        }
    };
    private Bitmap mBitmap;
    private String mTaskId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CinematicEffectRequest(Parcel parcel) {
        this.mTaskId = parcel.readString();
        this.mBitmap = Bitmap.CREATOR.createFromParcel(parcel);
    }

    public CinematicEffectRequest(String str, Bitmap bitmap) {
        this.mTaskId = str;
        this.mBitmap = bitmap;
    }

    public String getTaskId() {
        return this.mTaskId;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mTaskId.equals(((CinematicEffectRequest) obj).mTaskId);
    }

    public int hashCode() {
        return Objects.hash(this.mTaskId);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mTaskId);
        this.mBitmap.writeToParcel(parcel, i);
    }
}
