package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ParcelableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<ParcelableHandwritingGesture> CREATOR = new Parcelable.Creator<ParcelableHandwritingGesture>() { // from class: android.view.inputmethod.ParcelableHandwritingGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableHandwritingGesture createFromParcel(Parcel parcel) {
            return new ParcelableHandwritingGesture(ParcelableHandwritingGesture.createFromParcelInternal(parcel.readInt(), parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableHandwritingGesture[] newArray(int i) {
            return new ParcelableHandwritingGesture[i];
        }
    };
    private final HandwritingGesture mGesture;
    private final Parcelable mGestureAsParcelable;

    /* JADX WARN: Multi-variable type inference failed */
    private ParcelableHandwritingGesture(HandwritingGesture handwritingGesture) {
        this.mGesture = handwritingGesture;
        this.mGestureAsParcelable = (Parcelable) handwritingGesture;
    }

    public static ParcelableHandwritingGesture of(HandwritingGesture handwritingGesture) {
        return new ParcelableHandwritingGesture((HandwritingGesture) Objects.requireNonNull(handwritingGesture));
    }

    public HandwritingGesture get() {
        return this.mGesture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HandwritingGesture createFromParcelInternal(int i, Parcel parcel) {
        if (i == 0) {
            throw new UnsupportedOperationException("GESTURE_TYPE_NONE is not supported");
        }
        if (i == 1) {
            return SelectGesture.CREATOR.createFromParcel(parcel);
        }
        if (i == 2) {
            return InsertGesture.CREATOR.createFromParcel(parcel);
        }
        if (i == 4) {
            return DeleteGesture.CREATOR.createFromParcel(parcel);
        }
        if (i == 8) {
            return RemoveSpaceGesture.CREATOR.createFromParcel(parcel);
        }
        if (i == 16) {
            return JoinOrSplitGesture.CREATOR.createFromParcel(parcel);
        }
        if (i == 32) {
            return SelectRangeGesture.CREATOR.createFromParcel(parcel);
        }
        if (i == 64) {
            return DeleteRangeGesture.CREATOR.createFromParcel(parcel);
        }
        if (i == 128) {
            return InsertModeGesture.CREATOR.createFromParcel(parcel);
        }
        throw new UnsupportedOperationException("Unknown type=" + i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mGestureAsParcelable.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mGesture.getGestureType());
        this.mGestureAsParcelable.writeToParcel(parcel, i);
    }
}
