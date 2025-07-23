package android.view.inputmethod;

import android.graphics.PointF;
import android.os.CancellationSignal;
import android.os.CancellationSignalBeamer;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class InsertModeGesture extends CancellableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<InsertModeGesture> CREATOR = new Parcelable.Creator<InsertModeGesture>() { // from class: android.view.inputmethod.InsertModeGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsertModeGesture createFromParcel(Parcel parcel) {
            return new InsertModeGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsertModeGesture[] newArray(int i) {
            return new InsertModeGesture[i];
        }
    };
    private PointF mPoint;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private InsertModeGesture(PointF pointF, String str, CancellationSignal cancellationSignal) {
        this.mType = 128;
        this.mPoint = pointF;
        this.mFallbackText = str;
        this.mCancellationSignal = cancellationSignal;
    }

    private InsertModeGesture(Parcel parcel) {
        this.mType = 128;
        this.mFallbackText = parcel.readString8();
        this.mPoint = (PointF) parcel.readTypedObject(PointF.CREATOR);
        this.mCancellationSignalToken = parcel.readStrongBinder();
    }

    @Override // android.view.inputmethod.CancellableHandwritingGesture
    public CancellationSignal getCancellationSignal() {
        return this.mCancellationSignal;
    }

    public PointF getInsertionPoint() {
        return this.mPoint;
    }

    public static final class Builder {
        private CancellationSignal mCancellationSignal;
        private String mFallbackText;
        private PointF mPoint;

        public Builder setInsertionPoint(PointF pointF) {
            this.mPoint = pointF;
            return this;
        }

        public Builder setCancellationSignal(CancellationSignal cancellationSignal) {
            this.mCancellationSignal = cancellationSignal;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public InsertModeGesture build() {
            if (this.mPoint == null) {
                throw new IllegalArgumentException("Insertion point must be set.");
            }
            if (this.mCancellationSignal == null) {
                throw new IllegalArgumentException("CancellationSignal must be set.");
            }
            return new InsertModeGesture(this.mPoint, this.mFallbackText, this.mCancellationSignal);
        }
    }

    public int hashCode() {
        return Objects.hash(this.mPoint, this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InsertModeGesture)) {
            return false;
        }
        InsertModeGesture insertModeGesture = (InsertModeGesture) obj;
        if (Objects.equals(this.mFallbackText, insertModeGesture.mFallbackText)) {
            return Objects.equals(this.mPoint, insertModeGesture.mPoint);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mFallbackText);
        parcel.writeTypedObject(this.mPoint, i);
        parcel.writeStrongBinder(CancellationSignalBeamer.Sender.beamFromScope(this.mCancellationSignal));
    }
}
