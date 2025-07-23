package android.service.ambientcontext;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.ambientcontext.AmbientContextManager;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class AmbientContextDetectionServiceStatus implements Parcelable {
    public static final Parcelable.Creator<AmbientContextDetectionServiceStatus> CREATOR = new Parcelable.Creator<AmbientContextDetectionServiceStatus>() { // from class: android.service.ambientcontext.AmbientContextDetectionServiceStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientContextDetectionServiceStatus[] newArray(int i) {
            return new AmbientContextDetectionServiceStatus[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientContextDetectionServiceStatus createFromParcel(Parcel parcel) {
            return new AmbientContextDetectionServiceStatus(parcel);
        }
    };
    public static final String STATUS_RESPONSE_BUNDLE_KEY = "android.app.ambientcontext.AmbientContextServiceStatusBundleKey";
    private final String mPackageName;
    private final int mStatusCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AmbientContextDetectionServiceStatus(int i, String str) {
        this.mStatusCode = i;
        AnnotationValidations.validate((Class<? extends Annotation>) AmbientContextManager.StatusCode.class, (Annotation) null, i);
        this.mPackageName = str;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String toString() {
        return "AmbientContextDetectionServiceStatus { statusCode = " + this.mStatusCode + ", packageName = " + this.mPackageName + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) 0);
        parcel.writeInt(this.mStatusCode);
        parcel.writeString(this.mPackageName);
    }

    AmbientContextDetectionServiceStatus(Parcel parcel) {
        parcel.readByte();
        int readInt = parcel.readInt();
        String readString = parcel.readString();
        this.mStatusCode = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) AmbientContextManager.StatusCode.class, (Annotation) null, readInt);
        this.mPackageName = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private String mPackageName;
        private int mStatusCode;

        public Builder(String str) {
            Objects.requireNonNull(str);
            this.mPackageName = str;
        }

        public Builder setStatusCode(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mStatusCode = i;
            return this;
        }

        public AmbientContextDetectionServiceStatus build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 2;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mStatusCode = 0;
            }
            return new AmbientContextDetectionServiceStatus(this.mStatusCode, this.mPackageName);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
