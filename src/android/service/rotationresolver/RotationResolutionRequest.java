package android.service.rotationresolver;

import android.annotation.DurationMillisLong;
import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Surface;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

@SystemApi
/* loaded from: classes3.dex */
public final class RotationResolutionRequest implements Parcelable {
    public static final Parcelable.Creator<RotationResolutionRequest> CREATOR = new Parcelable.Creator<RotationResolutionRequest>() { // from class: android.service.rotationresolver.RotationResolutionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RotationResolutionRequest[] newArray(int i) {
            return new RotationResolutionRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RotationResolutionRequest createFromParcel(Parcel parcel) {
            return new RotationResolutionRequest(parcel);
        }
    };
    private final int mCurrentRotation;
    private final String mForegroundPackageName;
    private final int mProposedRotation;
    private final boolean mShouldUseCamera;
    private final long mTimeoutMillis;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RotationResolutionRequest(String str, int i, int i2, boolean z, long j) {
        this.mForegroundPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mCurrentRotation = i;
        AnnotationValidations.validate((Class<? extends Annotation>) Surface.Rotation.class, (Annotation) null, i);
        this.mProposedRotation = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) Surface.Rotation.class, (Annotation) null, i2);
        this.mShouldUseCamera = z;
        this.mTimeoutMillis = j;
        AnnotationValidations.validate((Class<? extends Annotation>) DurationMillisLong.class, (Annotation) null, j);
    }

    public String getForegroundPackageName() {
        return this.mForegroundPackageName;
    }

    public int getCurrentRotation() {
        return this.mCurrentRotation;
    }

    public int getProposedRotation() {
        return this.mProposedRotation;
    }

    public boolean shouldUseCamera() {
        return this.mShouldUseCamera;
    }

    public long getTimeoutMillis() {
        return this.mTimeoutMillis;
    }

    public String toString() {
        return "RotationResolutionRequest { foregroundPackageName = " + this.mForegroundPackageName + ", currentRotation = " + this.mCurrentRotation + ", proposedRotation = " + this.mProposedRotation + ", shouldUseCamera = " + this.mShouldUseCamera + ", timeoutMillis = " + this.mTimeoutMillis + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mShouldUseCamera ? (byte) 8 : (byte) 0);
        parcel.writeString(this.mForegroundPackageName);
        parcel.writeInt(this.mCurrentRotation);
        parcel.writeInt(this.mProposedRotation);
        parcel.writeLong(this.mTimeoutMillis);
    }

    RotationResolutionRequest(Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        String readString = parcel.readString();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        long readLong = parcel.readLong();
        this.mForegroundPackageName = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
        this.mCurrentRotation = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) Surface.Rotation.class, (Annotation) null, readInt);
        this.mProposedRotation = readInt2;
        AnnotationValidations.validate((Class<? extends Annotation>) Surface.Rotation.class, (Annotation) null, readInt2);
        this.mShouldUseCamera = z;
        this.mTimeoutMillis = readLong;
        AnnotationValidations.validate((Class<? extends Annotation>) DurationMillisLong.class, (Annotation) null, readLong);
    }
}
