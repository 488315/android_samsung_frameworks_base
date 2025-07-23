package android.view.translation;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.translation.TranslationContext;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TranslationCapability implements Parcelable {
    public static final Parcelable.Creator<TranslationCapability> CREATOR = new Parcelable.Creator<TranslationCapability>() { // from class: android.view.translation.TranslationCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationCapability[] newArray(int i) {
            return new TranslationCapability[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationCapability createFromParcel(Parcel parcel) {
            return new TranslationCapability(parcel);
        }
    };
    public static final int STATE_AVAILABLE_TO_DOWNLOAD = 1;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_NOT_AVAILABLE = 4;
    public static final int STATE_ON_DEVICE = 3;
    public static final int STATE_REMOVED_AND_AVAILABLE = 1000;
    private final TranslationSpec mSourceSpec;
    private final int mState;
    private final int mSupportedTranslationFlags;
    private final TranslationSpec mTargetSpec;
    private final boolean mUiTranslationEnabled;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModelState {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public TranslationCapability(int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, boolean z, int i2) {
        Objects.requireNonNull(translationSpec, "sourceSpec should not be null");
        Objects.requireNonNull(translationSpec2, "targetSpec should not be null");
        this.mState = i;
        this.mSourceSpec = translationSpec;
        this.mTargetSpec = translationSpec2;
        this.mUiTranslationEnabled = z;
        this.mSupportedTranslationFlags = i2;
    }

    public static String modelStateToString(int i) {
        if (i == 1) {
            return "STATE_AVAILABLE_TO_DOWNLOAD";
        }
        if (i == 2) {
            return "STATE_DOWNLOADING";
        }
        if (i == 3) {
            return "STATE_ON_DEVICE";
        }
        if (i == 4) {
            return "STATE_NOT_AVAILABLE";
        }
        if (i == 1000) {
            return "STATE_REMOVED_AND_AVAILABLE";
        }
        return Integer.toHexString(i);
    }

    public int getState() {
        return this.mState;
    }

    public TranslationSpec getSourceSpec() {
        return this.mSourceSpec;
    }

    public TranslationSpec getTargetSpec() {
        return this.mTargetSpec;
    }

    public boolean isUiTranslationEnabled() {
        return this.mUiTranslationEnabled;
    }

    public int getSupportedTranslationFlags() {
        return this.mSupportedTranslationFlags;
    }

    public String toString() {
        return "TranslationCapability { state = " + modelStateToString(this.mState) + ", sourceSpec = " + this.mSourceSpec + ", targetSpec = " + this.mTargetSpec + ", uiTranslationEnabled = " + this.mUiTranslationEnabled + ", supportedTranslationFlags = " + this.mSupportedTranslationFlags + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mUiTranslationEnabled ? (byte) 8 : (byte) 0);
        parcel.writeInt(this.mState);
        parcel.writeTypedObject(this.mSourceSpec, i);
        parcel.writeTypedObject(this.mTargetSpec, i);
        parcel.writeInt(this.mSupportedTranslationFlags);
    }

    TranslationCapability(Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        int readInt = parcel.readInt();
        TranslationSpec translationSpec = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
        TranslationSpec translationSpec2 = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
        int readInt2 = parcel.readInt();
        this.mState = readInt;
        if (readInt != 1 && readInt != 2 && readInt != 3 && readInt != 4 && readInt != 1000) {
            throw new IllegalArgumentException("state was " + readInt + " but must be one of: STATE_AVAILABLE_TO_DOWNLOAD(1), STATE_DOWNLOADING(2), STATE_ON_DEVICE(3), STATE_NOT_AVAILABLE(4), STATE_REMOVED_AND_AVAILABLE(1000)");
        }
        this.mSourceSpec = translationSpec;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec);
        this.mTargetSpec = translationSpec2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec2);
        this.mUiTranslationEnabled = z;
        this.mSupportedTranslationFlags = readInt2;
        AnnotationValidations.validate((Class<? extends Annotation>) TranslationContext.TranslationFlag.class, (Annotation) null, readInt2);
    }
}
