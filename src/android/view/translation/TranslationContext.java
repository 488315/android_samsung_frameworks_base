package android.view.translation;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.assist.ActivityId;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.IntFunction;

/* loaded from: classes4.dex */
public final class TranslationContext implements Parcelable {
    public static final Parcelable.Creator<TranslationContext> CREATOR = new Parcelable.Creator<TranslationContext>() { // from class: android.view.translation.TranslationContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationContext[] newArray(int i) {
            return new TranslationContext[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationContext createFromParcel(Parcel parcel) {
            return new TranslationContext(parcel);
        }
    };
    public static final int FLAG_DEFINITIONS = 4;
    public static final int FLAG_LOW_LATENCY = 1;
    public static final int FLAG_TRANSLITERATION = 2;
    private final ActivityId mActivityId;
    private final TranslationSpec mSourceSpec;
    private final TranslationSpec mTargetSpec;
    private final int mTranslationFlags;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TranslationFlag {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ActivityId defaultActivityId() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultTranslationFlags() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void parcelActivityId(Parcel parcel, int i) {
        parcel.writeBoolean(this.mActivityId != null);
        ActivityId activityId = this.mActivityId;
        if (activityId != null) {
            activityId.writeToParcel(parcel, i);
        }
    }

    private ActivityId unparcelActivityId(Parcel parcel) {
        if (parcel.readBoolean()) {
            return new ActivityId(parcel);
        }
        return null;
    }

    @SystemApi
    public ActivityId getActivityId() {
        return this.mActivityId;
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }
    }

    public static String translationFlagToString(int i) {
        return BitUtils.flagsToString(i, new IntFunction() { // from class: android.view.translation.TranslationContext$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return TranslationContext.singleTranslationFlagToString(i2);
            }
        });
    }

    static String singleTranslationFlagToString(int i) {
        if (i == 1) {
            return "FLAG_LOW_LATENCY";
        }
        if (i == 2) {
            return "FLAG_TRANSLITERATION";
        }
        if (i == 4) {
            return "FLAG_DEFINITIONS";
        }
        return Integer.toHexString(i);
    }

    TranslationContext(TranslationSpec translationSpec, TranslationSpec translationSpec2, int i, ActivityId activityId) {
        this.mSourceSpec = translationSpec;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec);
        this.mTargetSpec = translationSpec2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec2);
        this.mTranslationFlags = i;
        Preconditions.checkFlagsArgument(i, 7);
        this.mActivityId = activityId;
    }

    public TranslationSpec getSourceSpec() {
        return this.mSourceSpec;
    }

    public TranslationSpec getTargetSpec() {
        return this.mTargetSpec;
    }

    public int getTranslationFlags() {
        return this.mTranslationFlags;
    }

    public String toString() {
        return "TranslationContext { sourceSpec = " + this.mSourceSpec + ", targetSpec = " + this.mTargetSpec + ", translationFlags = " + translationFlagToString(this.mTranslationFlags) + ", activityId = " + this.mActivityId + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mActivityId != null ? (byte) 8 : (byte) 0);
        parcel.writeTypedObject(this.mSourceSpec, i);
        parcel.writeTypedObject(this.mTargetSpec, i);
        parcel.writeInt(this.mTranslationFlags);
        parcelActivityId(parcel, i);
    }

    TranslationContext(Parcel parcel) {
        parcel.readByte();
        TranslationSpec translationSpec = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
        TranslationSpec translationSpec2 = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
        int readInt = parcel.readInt();
        ActivityId unparcelActivityId = unparcelActivityId(parcel);
        this.mSourceSpec = translationSpec;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec);
        this.mTargetSpec = translationSpec2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec2);
        this.mTranslationFlags = readInt;
        Preconditions.checkFlagsArgument(readInt, 7);
        this.mActivityId = unparcelActivityId;
    }

    public static final class Builder extends BaseBuilder {
        private ActivityId mActivityId;
        private long mBuilderFieldsSet = 0;
        private TranslationSpec mSourceSpec;
        private TranslationSpec mTargetSpec;
        private int mTranslationFlags;

        public Builder(TranslationSpec translationSpec, TranslationSpec translationSpec2) {
            this.mSourceSpec = translationSpec;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec);
            this.mTargetSpec = translationSpec2;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) translationSpec2);
        }

        public Builder setTranslationFlags(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mTranslationFlags = i;
            return this;
        }

        public Builder setActivityId(ActivityId activityId) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mActivityId = activityId;
            return this;
        }

        public TranslationContext build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 16;
            this.mBuilderFieldsSet = j;
            if ((j & 4) == 0) {
                this.mTranslationFlags = TranslationContext.defaultTranslationFlags();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mActivityId = TranslationContext.defaultActivityId();
            }
            return new TranslationContext(this.mSourceSpec, this.mTargetSpec, this.mTranslationFlags, this.mActivityId);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
