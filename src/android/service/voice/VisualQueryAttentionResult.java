package android.service.voice;

import android.annotation.IntRange;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class VisualQueryAttentionResult implements Parcelable {
    public static final Parcelable.Creator<VisualQueryAttentionResult> CREATOR = new Parcelable.Creator<VisualQueryAttentionResult>() { // from class: android.service.voice.VisualQueryAttentionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualQueryAttentionResult[] newArray(int i) {
            return new VisualQueryAttentionResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualQueryAttentionResult createFromParcel(Parcel parcel) {
            return new VisualQueryAttentionResult(parcel);
        }
    };
    public static final int INTERACTION_INTENTION_AUDIO_VISUAL = 0;
    public static final int INTERACTION_INTENTION_VISUAL_ACCESSIBILITY = 1;
    private final int mEngagementLevel;
    private final int mInteractionIntention;

    @Retention(RetentionPolicy.SOURCE)
    public @interface InteractionIntention {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEngagementLevel() {
        return 100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultInteractionIntention() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Builder buildUpon() {
        return new Builder().setInteractionIntention(this.mInteractionIntention).setEngagementLevel(this.mEngagementLevel);
    }

    public static String interactionIntentionToString(int i) {
        if (i == 0) {
            return "INTERACTION_INTENTION_AUDIO_VISUAL";
        }
        if (i == 1) {
            return "INTERACTION_INTENTION_VISUAL_ACCESSIBILITY";
        }
        return Integer.toHexString(i);
    }

    VisualQueryAttentionResult(int i, int i2) {
        this.mInteractionIntention = i;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("interactionIntention was " + i + " but must be one of: INTERACTION_INTENTION_AUDIO_VISUAL(0), INTERACTION_INTENTION_VISUAL_ACCESSIBILITY(1)");
        }
        this.mEngagementLevel = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 1L, "to", 100L);
    }

    public int getInteractionIntention() {
        return this.mInteractionIntention;
    }

    public int getEngagementLevel() {
        return this.mEngagementLevel;
    }

    public String toString() {
        return "VisualQueryAttentionResult { interactionIntention = " + this.mInteractionIntention + ", engagementLevel = " + this.mEngagementLevel + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VisualQueryAttentionResult visualQueryAttentionResult = (VisualQueryAttentionResult) obj;
            if (this.mInteractionIntention == visualQueryAttentionResult.mInteractionIntention && this.mEngagementLevel == visualQueryAttentionResult.mEngagementLevel) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((this.mInteractionIntention + 31) * 31) + this.mEngagementLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mInteractionIntention);
        parcel.writeInt(this.mEngagementLevel);
    }

    VisualQueryAttentionResult(Parcel parcel) {
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        this.mInteractionIntention = i;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("interactionIntention was " + i + " but must be one of: INTERACTION_INTENTION_AUDIO_VISUAL(0), INTERACTION_INTENTION_VISUAL_ACCESSIBILITY(1)");
        }
        this.mEngagementLevel = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 1L, "to", 100L);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mEngagementLevel;
        private int mInteractionIntention;

        public Builder setInteractionIntention(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mInteractionIntention = i;
            return this;
        }

        public Builder setEngagementLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mEngagementLevel = i;
            return this;
        }

        public VisualQueryAttentionResult build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 4;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mInteractionIntention = VisualQueryAttentionResult.defaultInteractionIntention();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mEngagementLevel = VisualQueryAttentionResult.defaultEngagementLevel();
            }
            return new VisualQueryAttentionResult(this.mInteractionIntention, this.mEngagementLevel);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
