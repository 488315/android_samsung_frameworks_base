package android.service.voice;

import android.annotation.IntRange;
import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class VisualQueryAttentionResult implements Parcelable {
    public static final Parcelable.Creator<VisualQueryAttentionResult> CREATOR =
            new Parcelable.Creator<
                    VisualQueryAttentionResult>() { // from class:
                                                    // android.service.voice.VisualQueryAttentionResult.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VisualQueryAttentionResult[] newArray(int size) {
                    return new VisualQueryAttentionResult[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VisualQueryAttentionResult createFromParcel(Parcel in) {
                    return new VisualQueryAttentionResult(in);
                }
            };
    public static final int INTERACTION_INTENTION_AUDIO_VISUAL = 0;
    public static final int INTERACTION_INTENTION_VISUAL_ACCESSIBILITY = 1;
    private final int mEngagementLevel;
    private final int mInteractionIntention;

    @Retention(RetentionPolicy.SOURCE)
    public @interface InteractionIntention {}

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultInteractionIntention() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEngagementLevel() {
        return 100;
    }

    public Builder buildUpon() {
        return new Builder()
                .setInteractionIntention(this.mInteractionIntention)
                .setEngagementLevel(this.mEngagementLevel);
    }

    public static String interactionIntentionToString(int value) {
        switch (value) {
            case 0:
                return "INTERACTION_INTENTION_AUDIO_VISUAL";
            case 1:
                return "INTERACTION_INTENTION_VISUAL_ACCESSIBILITY";
            default:
                return Integer.toHexString(value);
        }
    }

    VisualQueryAttentionResult(int interactionIntention, int engagementLevel) {
        this.mInteractionIntention = interactionIntention;
        if (this.mInteractionIntention != 0 && this.mInteractionIntention != 1) {
            throw new IllegalArgumentException(
                    "interactionIntention was "
                            + this.mInteractionIntention
                            + " but must be one of: INTERACTION_INTENTION_AUDIO_VISUAL(0),"
                            + " INTERACTION_INTENTION_VISUAL_ACCESSIBILITY(1"
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mEngagementLevel = engagementLevel;
        AnnotationValidations.validate(
                (Class<IntRange>) IntRange.class,
                (IntRange) null,
                this.mEngagementLevel,
                "from",
                1L,
                "to",
                100L);
    }

    public int getInteractionIntention() {
        return this.mInteractionIntention;
    }

    public int getEngagementLevel() {
        return this.mEngagementLevel;
    }

    public String toString() {
        return "VisualQueryAttentionResult { interactionIntention = "
                + this.mInteractionIntention
                + ", engagementLevel = "
                + this.mEngagementLevel
                + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisualQueryAttentionResult that = (VisualQueryAttentionResult) o;
        if (this.mInteractionIntention == that.mInteractionIntention
                && this.mEngagementLevel == that.mEngagementLevel) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mInteractionIntention;
        return (_hash * 31) + this.mEngagementLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mInteractionIntention);
        dest.writeInt(this.mEngagementLevel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VisualQueryAttentionResult(Parcel in) {
        int interactionIntention = in.readInt();
        int engagementLevel = in.readInt();
        this.mInteractionIntention = interactionIntention;
        if (this.mInteractionIntention != 0 && this.mInteractionIntention != 1) {
            throw new IllegalArgumentException(
                    "interactionIntention was "
                            + this.mInteractionIntention
                            + " but must be one of: INTERACTION_INTENTION_AUDIO_VISUAL(0),"
                            + " INTERACTION_INTENTION_VISUAL_ACCESSIBILITY(1"
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mEngagementLevel = engagementLevel;
        AnnotationValidations.validate(
                (Class<IntRange>) IntRange.class,
                (IntRange) null,
                this.mEngagementLevel,
                "from",
                1L,
                "to",
                100L);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mEngagementLevel;
        private int mInteractionIntention;

        public Builder setInteractionIntention(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mInteractionIntention = value;
            return this;
        }

        public Builder setEngagementLevel(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mEngagementLevel = value;
            return this;
        }

        public VisualQueryAttentionResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mInteractionIntention =
                        VisualQueryAttentionResult.defaultInteractionIntention();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mEngagementLevel = VisualQueryAttentionResult.defaultEngagementLevel();
            }
            VisualQueryAttentionResult o =
                    new VisualQueryAttentionResult(
                            this.mInteractionIntention, this.mEngagementLevel);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
