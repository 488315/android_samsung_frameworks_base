package android.service.credentials;

import android.annotation.NonNull;
import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes3.dex */
public final class CreateEntry implements Parcelable {
    public static final Parcelable.Creator<CreateEntry> CREATOR = new Parcelable.Creator<CreateEntry>() { // from class: android.service.credentials.CreateEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateEntry createFromParcel(Parcel parcel) {
            return new CreateEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateEntry[] newArray(int i) {
            return new CreateEntry[i];
        }
    };
    private final Slice mSlice;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CreateEntry(Parcel parcel) {
        this.mSlice = (Slice) parcel.readTypedObject(Slice.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mSlice, i);
    }

    public CreateEntry(Slice slice) {
        this.mSlice = slice;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) slice);
    }

    public Slice getSlice() {
        return this.mSlice;
    }
}
