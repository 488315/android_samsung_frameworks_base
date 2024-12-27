package android.app.ondeviceintelligence;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.MessageFormat;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class FeatureDetails implements Parcelable {
    public static final Parcelable.Creator<FeatureDetails> CREATOR = new Parcelable.Creator<FeatureDetails>() { // from class: android.app.ondeviceintelligence.FeatureDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureDetails[] newArray(int size) {
            return new FeatureDetails[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureDetails createFromParcel(Parcel in) {
            return new FeatureDetails(in);
        }
    };
    public static final int FEATURE_STATUS_AVAILABLE = 3;
    public static final int FEATURE_STATUS_DOWNLOADABLE = 1;
    public static final int FEATURE_STATUS_DOWNLOADING = 2;
    public static final int FEATURE_STATUS_SERVICE_UNAVAILABLE = 4;
    public static final int FEATURE_STATUS_UNAVAILABLE = 0;
    private final PersistableBundle mFeatureDetailParams;
    private final int mFeatureStatus;

    @Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public FeatureDetails(int featureStatus, PersistableBundle featureDetailParams) {
        this.mFeatureStatus = featureStatus;
        AnnotationValidations.validate((Class<? extends Annotation>) Status.class, (Annotation) null, this.mFeatureStatus);
        this.mFeatureDetailParams = featureDetailParams;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mFeatureDetailParams);
    }

    public FeatureDetails(int featureStatus) {
        this.mFeatureStatus = featureStatus;
        AnnotationValidations.validate((Class<? extends Annotation>) Status.class, (Annotation) null, this.mFeatureStatus);
        this.mFeatureDetailParams = new PersistableBundle();
    }

    public int getFeatureStatus() {
        return this.mFeatureStatus;
    }

    public PersistableBundle getFeatureDetailParams() {
        return this.mFeatureDetailParams;
    }

    public String toString() {
        return MessageFormat.format("FeatureDetails '{' status = {0}, persistableBundle = {1} '}'", Integer.valueOf(this.mFeatureStatus), this.mFeatureDetailParams);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeatureDetails that = (FeatureDetails) o;
        if (this.mFeatureStatus == that.mFeatureStatus && Objects.equals(this.mFeatureDetailParams, that.mFeatureDetailParams)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mFeatureStatus;
        return (_hash * 31) + Objects.hashCode(this.mFeatureDetailParams);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mFeatureStatus);
        dest.writeTypedObject(this.mFeatureDetailParams, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FeatureDetails(Parcel in) {
        int status = in.readInt();
        PersistableBundle persistableBundle = (PersistableBundle) in.readTypedObject(PersistableBundle.CREATOR);
        this.mFeatureStatus = status;
        AnnotationValidations.validate((Class<? extends Annotation>) Status.class, (Annotation) null, this.mFeatureStatus);
        this.mFeatureDetailParams = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mFeatureDetailParams);
    }
}
