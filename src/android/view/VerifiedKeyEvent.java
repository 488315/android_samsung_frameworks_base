package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class VerifiedKeyEvent extends VerifiedInputEvent implements Parcelable {
    public static final Parcelable.Creator<VerifiedKeyEvent> CREATOR = new Parcelable.Creator<VerifiedKeyEvent>() { // from class: android.view.VerifiedKeyEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifiedKeyEvent[] newArray(int i) {
            return new VerifiedKeyEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifiedKeyEvent createFromParcel(Parcel parcel) {
            return new VerifiedKeyEvent(parcel);
        }
    };
    private static final String TAG = "VerifiedKeyEvent";
    private int mAction;
    private long mDownTimeNanos;
    private int mFlags;
    private int mKeyCode;
    private int mMetaState;
    private int mRepeatCount;
    private int mScanCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyEventAction {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.view.VerifiedInputEvent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Boolean getFlag(int i) {
        if (i == 32 || i == 2048) {
            return Boolean.valueOf((this.mFlags & i) != 0);
        }
        return null;
    }

    public VerifiedKeyEvent(int i, long j, int i2, int i3, int i4, long j2, int i5, int i6, int i7, int i8, int i9) {
        super(1, i, j, i2, i3);
        this.mAction = i4;
        AnnotationValidations.validate((Class<? extends Annotation>) KeyEventAction.class, (Annotation) null, i4);
        this.mDownTimeNanos = j2;
        this.mFlags = i5;
        this.mKeyCode = i6;
        this.mScanCode = i7;
        this.mMetaState = i8;
        this.mRepeatCount = i9;
    }

    public int getAction() {
        return this.mAction;
    }

    public long getDownTimeNanos() {
        return this.mDownTimeNanos;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getKeyCode() {
        return this.mKeyCode;
    }

    public int getScanCode() {
        return this.mScanCode;
    }

    public int getMetaState() {
        return this.mMetaState;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    @Override // android.view.VerifiedInputEvent
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VerifiedKeyEvent verifiedKeyEvent = (VerifiedKeyEvent) obj;
            if (super.equals(verifiedKeyEvent) && this.mAction == verifiedKeyEvent.mAction && this.mDownTimeNanos == verifiedKeyEvent.mDownTimeNanos && this.mFlags == verifiedKeyEvent.mFlags && this.mKeyCode == verifiedKeyEvent.mKeyCode && this.mScanCode == verifiedKeyEvent.mScanCode && this.mMetaState == verifiedKeyEvent.mMetaState && this.mRepeatCount == verifiedKeyEvent.mRepeatCount) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.VerifiedInputEvent
    public int hashCode() {
        return ((((((((((((((super.hashCode() + 31) * 31) + this.mAction) * 31) + Long.hashCode(this.mDownTimeNanos)) * 31) + this.mFlags) * 31) + this.mKeyCode) * 31) + this.mScanCode) * 31) + this.mMetaState) * 31) + this.mRepeatCount;
    }

    @Override // android.view.VerifiedInputEvent, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mAction);
        parcel.writeLong(this.mDownTimeNanos);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mKeyCode);
        parcel.writeInt(this.mScanCode);
        parcel.writeInt(this.mMetaState);
        parcel.writeInt(this.mRepeatCount);
    }

    VerifiedKeyEvent(Parcel parcel) {
        super(parcel, 1);
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        int readInt6 = parcel.readInt();
        this.mAction = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) KeyEventAction.class, (Annotation) null, readInt);
        this.mDownTimeNanos = readLong;
        this.mFlags = readInt2;
        this.mKeyCode = readInt3;
        this.mScanCode = readInt4;
        this.mMetaState = readInt5;
        this.mRepeatCount = readInt6;
    }
}
