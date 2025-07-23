package androidx.compose.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ParcelableSnapshotMutableFloatState extends SnapshotMutableFloatStateImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelableSnapshotMutableFloatState> CREATOR;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        CREATOR = new Parcelable.Creator<ParcelableSnapshotMutableFloatState>() { // from class: androidx.compose.runtime.ParcelableSnapshotMutableFloatState$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public final ParcelableSnapshotMutableFloatState createFromParcel(Parcel parcel) {
                return new ParcelableSnapshotMutableFloatState(parcel.readFloat());
            }

            @Override // android.os.Parcelable.Creator
            public final ParcelableSnapshotMutableFloatState[] newArray(int i) {
                return new ParcelableSnapshotMutableFloatState[i];
            }
        };
    }

    public ParcelableSnapshotMutableFloatState(float f) {
        super(f);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(getFloatValue());
    }
}
