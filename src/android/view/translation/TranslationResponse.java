package android.view.translation;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TranslationResponse implements Parcelable {
    public static final Parcelable.Creator<TranslationResponse> CREATOR = new Parcelable.Creator<TranslationResponse>() { // from class: android.view.translation.TranslationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationResponse[] newArray(int i) {
            return new TranslationResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationResponse createFromParcel(Parcel parcel) {
            return new TranslationResponse(parcel);
        }
    };
    public static final int TRANSLATION_STATUS_CONTEXT_UNSUPPORTED = 2;
    public static final int TRANSLATION_STATUS_SUCCESS = 0;
    public static final int TRANSLATION_STATUS_UNKNOWN_ERROR = 1;
    private final boolean mFinalResponse;
    private final SparseArray<TranslationResponseValue> mTranslationResponseValues;
    private final int mTranslationStatus;
    private final SparseArray<ViewTranslationResponse> mViewTranslationResponses;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TranslationStatus {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean defaultFinalResponse() {
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static abstract class BaseBuilder {
        @Deprecated
        public abstract Builder setTranslationStatus(int i);

        BaseBuilder() {
        }

        public Builder setTranslationResponseValue(int i, TranslationResponseValue translationResponseValue) {
            Objects.requireNonNull(translationResponseValue, "value should not be null");
            Builder builder = (Builder) this;
            if (builder.mTranslationResponseValues == null) {
                builder.setTranslationResponseValues(new SparseArray<>());
            }
            builder.mTranslationResponseValues.put(i, translationResponseValue);
            return builder;
        }

        public Builder setViewTranslationResponse(int i, ViewTranslationResponse viewTranslationResponse) {
            Objects.requireNonNull(viewTranslationResponse, "value should not be null");
            Builder builder = (Builder) this;
            if (builder.mViewTranslationResponses == null) {
                builder.setViewTranslationResponses(new SparseArray<>());
            }
            builder.mViewTranslationResponses.put(i, viewTranslationResponse);
            return builder;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SparseArray<TranslationResponseValue> defaultTranslationResponseValues() {
        return new SparseArray<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SparseArray<ViewTranslationResponse> defaultViewTranslationResponses() {
        return new SparseArray<>();
    }

    public static String translationStatusToString(int i) {
        if (i == 0) {
            return "TRANSLATION_STATUS_SUCCESS";
        }
        if (i == 1) {
            return "TRANSLATION_STATUS_UNKNOWN_ERROR";
        }
        if (i == 2) {
            return "TRANSLATION_STATUS_CONTEXT_UNSUPPORTED";
        }
        return Integer.toHexString(i);
    }

    TranslationResponse(int i, SparseArray<TranslationResponseValue> sparseArray, SparseArray<ViewTranslationResponse> sparseArray2, boolean z) {
        this.mTranslationStatus = i;
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("translationStatus was " + i + " but must be one of: TRANSLATION_STATUS_SUCCESS(0), TRANSLATION_STATUS_UNKNOWN_ERROR(1), TRANSLATION_STATUS_CONTEXT_UNSUPPORTED(2)");
        }
        this.mTranslationResponseValues = sparseArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) sparseArray);
        this.mViewTranslationResponses = sparseArray2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) sparseArray2);
        this.mFinalResponse = z;
    }

    public int getTranslationStatus() {
        return this.mTranslationStatus;
    }

    public SparseArray<TranslationResponseValue> getTranslationResponseValues() {
        return this.mTranslationResponseValues;
    }

    public SparseArray<ViewTranslationResponse> getViewTranslationResponses() {
        return this.mViewTranslationResponses;
    }

    public boolean isFinalResponse() {
        return this.mFinalResponse;
    }

    public String toString() {
        return "TranslationResponse { translationStatus = " + translationStatusToString(this.mTranslationStatus) + ", translationResponseValues = " + this.mTranslationResponseValues + ", viewTranslationResponses = " + this.mViewTranslationResponses + ", finalResponse = " + this.mFinalResponse + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mFinalResponse ? (byte) 8 : (byte) 0);
        parcel.writeInt(this.mTranslationStatus);
        parcel.writeSparseArray(this.mTranslationResponseValues);
        parcel.writeSparseArray(this.mViewTranslationResponses);
    }

    TranslationResponse(Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        int i = parcel.readInt();
        SparseArray<TranslationResponseValue> sparseArray = parcel.readSparseArray(TranslationResponseValue.class.getClassLoader());
        SparseArray<ViewTranslationResponse> sparseArray2 = parcel.readSparseArray(ViewTranslationResponse.class.getClassLoader());
        this.mTranslationStatus = i;
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("translationStatus was " + i + " but must be one of: TRANSLATION_STATUS_SUCCESS(0), TRANSLATION_STATUS_UNKNOWN_ERROR(1), TRANSLATION_STATUS_CONTEXT_UNSUPPORTED(2)");
        }
        this.mTranslationResponseValues = sparseArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) sparseArray);
        this.mViewTranslationResponses = sparseArray2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) sparseArray2);
        this.mFinalResponse = z;
    }

    public static final class Builder extends BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private boolean mFinalResponse;
        private SparseArray<TranslationResponseValue> mTranslationResponseValues;
        private int mTranslationStatus;
        private SparseArray<ViewTranslationResponse> mViewTranslationResponses;

        @Override // android.view.translation.TranslationResponse.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setTranslationResponseValue(int i, TranslationResponseValue translationResponseValue) {
            return super.setTranslationResponseValue(i, translationResponseValue);
        }

        @Override // android.view.translation.TranslationResponse.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setViewTranslationResponse(int i, ViewTranslationResponse viewTranslationResponse) {
            return super.setViewTranslationResponse(i, viewTranslationResponse);
        }

        public Builder(int i) {
            this.mTranslationStatus = i;
            if (i == 0 || i == 1 || i == 2) {
                return;
            }
            throw new IllegalArgumentException("translationStatus was " + this.mTranslationStatus + " but must be one of: TRANSLATION_STATUS_SUCCESS(0), TRANSLATION_STATUS_UNKNOWN_ERROR(1), TRANSLATION_STATUS_CONTEXT_UNSUPPORTED(2)");
        }

        @Override // android.view.translation.TranslationResponse.BaseBuilder
        @Deprecated
        public Builder setTranslationStatus(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mTranslationStatus = i;
            return this;
        }

        public Builder setTranslationResponseValues(SparseArray<TranslationResponseValue> sparseArray) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationResponseValues = sparseArray;
            return this;
        }

        public Builder setViewTranslationResponses(SparseArray<ViewTranslationResponse> sparseArray) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mViewTranslationResponses = sparseArray;
            return this;
        }

        public Builder setFinalResponse(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mFinalResponse = z;
            return this;
        }

        public TranslationResponse build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 16;
            this.mBuilderFieldsSet = j;
            if ((j & 2) == 0) {
                this.mTranslationResponseValues = TranslationResponse.defaultTranslationResponseValues();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mViewTranslationResponses = TranslationResponse.defaultViewTranslationResponses();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mFinalResponse = TranslationResponse.defaultFinalResponse();
            }
            return new TranslationResponse(this.mTranslationStatus, this.mTranslationResponseValues, this.mViewTranslationResponses, this.mFinalResponse);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
