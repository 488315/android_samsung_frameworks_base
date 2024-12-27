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

public final class TranslationContext implements Parcelable {
    public static final Parcelable.Creator<TranslationContext> CREATOR =
            new Parcelable.Creator<
                    TranslationContext>() { // from class:
                                            // android.view.translation.TranslationContext.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TranslationContext[] newArray(int size) {
                    return new TranslationContext[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TranslationContext createFromParcel(Parcel in) {
                    return new TranslationContext(in);
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
    public @interface TranslationFlag {}

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultTranslationFlags() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ActivityId defaultActivityId() {
        return null;
    }

    private void parcelActivityId(Parcel dest, int flags) {
        dest.writeBoolean(this.mActivityId != null);
        if (this.mActivityId != null) {
            this.mActivityId.writeToParcel(dest, flags);
        }
    }

    private ActivityId unparcelActivityId(Parcel in) {
        boolean hasActivityId = in.readBoolean();
        if (hasActivityId) {
            return new ActivityId(in);
        }
        return null;
    }

    @SystemApi
    public ActivityId getActivityId() {
        return this.mActivityId;
    }

    abstract static class BaseBuilder {
        BaseBuilder() {}
    }

    public static String translationFlagToString(int value) {
        return BitUtils.flagsToString(
                value,
                new IntFunction() { // from class:
                                    // android.view.translation.TranslationContext$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return TranslationContext.singleTranslationFlagToString(i);
                    }
                });
    }

    static String singleTranslationFlagToString(int value) {
        switch (value) {
            case 1:
                return "FLAG_LOW_LATENCY";
            case 2:
                return "FLAG_TRANSLITERATION";
            case 3:
            default:
                return Integer.toHexString(value);
            case 4:
                return "FLAG_DEFINITIONS";
        }
    }

    TranslationContext(
            TranslationSpec sourceSpec,
            TranslationSpec targetSpec,
            int translationFlags,
            ActivityId activityId) {
        this.mSourceSpec = sourceSpec;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSourceSpec);
        this.mTargetSpec = targetSpec;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTargetSpec);
        this.mTranslationFlags = translationFlags;
        Preconditions.checkFlagsArgument(this.mTranslationFlags, 7);
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
        return "TranslationContext { sourceSpec = "
                + this.mSourceSpec
                + ", targetSpec = "
                + this.mTargetSpec
                + ", translationFlags = "
                + translationFlagToString(this.mTranslationFlags)
                + ", activityId = "
                + this.mActivityId
                + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mActivityId != null ? (byte) (0 | 8) : (byte) 0;
        dest.writeByte(flg);
        dest.writeTypedObject(this.mSourceSpec, flags);
        dest.writeTypedObject(this.mTargetSpec, flags);
        dest.writeInt(this.mTranslationFlags);
        parcelActivityId(dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationContext(Parcel in) {
        in.readByte();
        TranslationSpec sourceSpec = (TranslationSpec) in.readTypedObject(TranslationSpec.CREATOR);
        TranslationSpec targetSpec = (TranslationSpec) in.readTypedObject(TranslationSpec.CREATOR);
        int translationFlags = in.readInt();
        ActivityId activityId = unparcelActivityId(in);
        this.mSourceSpec = sourceSpec;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSourceSpec);
        this.mTargetSpec = targetSpec;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTargetSpec);
        this.mTranslationFlags = translationFlags;
        Preconditions.checkFlagsArgument(this.mTranslationFlags, 7);
        this.mActivityId = activityId;
    }

    public static final class Builder extends BaseBuilder {
        private ActivityId mActivityId;
        private long mBuilderFieldsSet = 0;
        private TranslationSpec mSourceSpec;
        private TranslationSpec mTargetSpec;
        private int mTranslationFlags;

        public Builder(TranslationSpec sourceSpec, TranslationSpec targetSpec) {
            this.mSourceSpec = sourceSpec;
            AnnotationValidations.validate(
                    (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSourceSpec);
            this.mTargetSpec = targetSpec;
            AnnotationValidations.validate(
                    (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTargetSpec);
        }

        public Builder setTranslationFlags(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mTranslationFlags = value;
            return this;
        }

        public Builder setActivityId(ActivityId value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mActivityId = value;
            return this;
        }

        public TranslationContext build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mTranslationFlags = TranslationContext.defaultTranslationFlags();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mActivityId = TranslationContext.defaultActivityId();
            }
            TranslationContext o =
                    new TranslationContext(
                            this.mSourceSpec,
                            this.mTargetSpec,
                            this.mTranslationFlags,
                            this.mActivityId);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
