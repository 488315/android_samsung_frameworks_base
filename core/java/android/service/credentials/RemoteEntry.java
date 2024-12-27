package android.service.credentials;

import android.annotation.NonNull;
import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

public final class RemoteEntry implements Parcelable {
    public static final Parcelable.Creator<RemoteEntry> CREATOR =
            new Parcelable.Creator<
                    RemoteEntry>() { // from class: android.service.credentials.RemoteEntry.1
                @Override // android.os.Parcelable.Creator
                public RemoteEntry createFromParcel(Parcel in) {
                    return new RemoteEntry(in);
                }

                @Override // android.os.Parcelable.Creator
                public RemoteEntry[] newArray(int size) {
                    return new RemoteEntry[size];
                }
            };
    private final Slice mSlice;

    private RemoteEntry(Parcel in) {
        this.mSlice = (Slice) in.readTypedObject(Slice.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mSlice, flags);
    }

    public RemoteEntry(Slice slice) {
        this.mSlice = slice;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSlice);
    }

    public Slice getSlice() {
        return this.mSlice;
    }
}
