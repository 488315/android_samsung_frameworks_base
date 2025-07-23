package android.service.credentials;

import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class Action implements Parcelable {
    public static final Parcelable.Creator<Action> CREATOR = new Parcelable.Creator<Action>() { // from class: android.service.credentials.Action.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Action createFromParcel(Parcel parcel) {
            return new Action(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Action[] newArray(int i) {
            return new Action[i];
        }
    };
    private final Slice mSlice;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Action(Slice slice) {
        Objects.requireNonNull(slice, "slice must not be null");
        this.mSlice = slice;
    }

    private Action(Parcel parcel) {
        this.mSlice = (Slice) parcel.readTypedObject(Slice.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mSlice, i);
    }

    public Slice getSlice() {
        return this.mSlice;
    }
}
