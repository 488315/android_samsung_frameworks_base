package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.audiopolicy.AudioMixingRule;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@SystemApi
/* loaded from: classes.dex */
public class AudioMixingRule implements Parcelable {
    public static final Parcelable.Creator<AudioMixingRule> CREATOR = new Parcelable.Creator<AudioMixingRule>() { // from class: android.media.audiopolicy.AudioMixingRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixingRule createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            builder.allowPrivilegedPlaybackCapture(parcel.readBoolean());
            builder.voiceCommunicationCaptureAllowed(parcel.readBoolean());
            builder.setTargetMixRole(parcel.readInt());
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                builder.addRuleInternal(AudioMixMatchCriterion.CREATOR.createFromParcel(parcel));
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixingRule[] newArray(int i) {
            return new AudioMixingRule[i];
        }
    };
    public static final int MIX_ROLE_INJECTOR = 1;
    public static final int MIX_ROLE_PLAYERS = 0;
    public static final int RULE_EXCLUDE_ATTRIBUTE_CAPTURE_PRESET = 32770;
    public static final int RULE_EXCLUDE_ATTRIBUTE_USAGE = 32769;
    public static final int RULE_EXCLUDE_AUDIO_SESSION_ID = 32784;
    public static final int RULE_EXCLUDE_UID = 32772;
    public static final int RULE_EXCLUDE_USERID = 32776;
    private static final int RULE_EXCLUSION_MASK = 32768;
    public static final int RULE_MATCH_ATTRIBUTE_CAPTURE_PRESET = 2;
    public static final int RULE_MATCH_ATTRIBUTE_USAGE = 1;
    public static final int RULE_MATCH_AUDIO_SESSION_ID = 16;
    public static final int RULE_MATCH_UID = 4;
    public static final int RULE_MATCH_USERID = 8;
    private boolean mAllowPrivilegedPlaybackCapture;
    private final ArrayList<AudioMixMatchCriterion> mCriteria;
    private final int mTargetMixType;
    private boolean mVoiceCommunicationCaptureAllowed;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MixRole {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAudioAttributeRule(int i) {
        return i == 1 || i == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPlayerRule(int i) {
        int i2 = i & (-32769);
        return i2 == 1 || i2 == 8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRecorderRule(int i) {
        return (i & (-32769)) == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidAttributesSystemApiRule(int i) {
        return i == 1 || i == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRule(int i) {
        int i2 = i & (-32769);
        return i2 == 1 || i2 == 2 || i2 == 4 || i2 == 8 || i2 == 16;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidSystemApiRule(int i) {
        return i == 1 || i == 2 || i == 4 || i == 8 || i == 16;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AudioMixingRule(int i, Collection<AudioMixMatchCriterion> collection, boolean z, boolean z2) {
        this.mAllowPrivilegedPlaybackCapture = false;
        this.mVoiceCommunicationCaptureAllowed = false;
        this.mCriteria = new ArrayList<>(collection);
        this.mTargetMixType = i;
        this.mAllowPrivilegedPlaybackCapture = z;
        this.mVoiceCommunicationCaptureAllowed = z2;
    }

    public static final class AudioMixMatchCriterion implements Parcelable {
        public static final Parcelable.Creator<AudioMixMatchCriterion> CREATOR = new Parcelable.Creator<AudioMixMatchCriterion>() { // from class: android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioMixMatchCriterion createFromParcel(Parcel parcel) {
                return new AudioMixMatchCriterion(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioMixMatchCriterion[] newArray(int i) {
                return new AudioMixMatchCriterion[i];
            }
        };
        final AudioAttributes mAttr;
        final int mIntProp;
        final int mRule;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AudioMixMatchCriterion(AudioAttributes audioAttributes, int i) {
            this.mAttr = audioAttributes;
            this.mIntProp = Integer.MIN_VALUE;
            this.mRule = i;
        }

        public AudioMixMatchCriterion(Integer num, int i) {
            this.mAttr = null;
            this.mIntProp = num.intValue();
            this.mRule = i;
        }

        private AudioMixMatchCriterion(Parcel parcel) {
            Objects.requireNonNull(parcel);
            int readInt = parcel.readInt();
            this.mRule = readInt;
            int i = (-32769) & readInt;
            if (i == 1 || i == 2) {
                this.mAttr = AudioAttributes.CREATOR.createFromParcel(parcel);
                this.mIntProp = Integer.MIN_VALUE;
            } else if (i == 4 || i == 8 || i == 16) {
                this.mIntProp = parcel.readInt();
                this.mAttr = null;
            } else {
                parcel.readInt();
                throw new IllegalArgumentException("Illegal rule value " + readInt + " in parcel");
            }
        }

        public int hashCode() {
            return Objects.hash(this.mAttr, Integer.valueOf(this.mIntProp), Integer.valueOf(this.mRule));
        }

        public boolean equals(Object obj) {
            if (obj != null && getClass() == obj.getClass()) {
                if (obj == this) {
                    return true;
                }
                AudioMixMatchCriterion audioMixMatchCriterion = (AudioMixMatchCriterion) obj;
                if (this.mRule == audioMixMatchCriterion.mRule && this.mIntProp == audioMixMatchCriterion.mIntProp && Objects.equals(this.mAttr, audioMixMatchCriterion.mAttr)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mRule);
            int i2 = this.mRule & (-32769);
            if (i2 == 1 || i2 == 2) {
                this.mAttr.writeToParcel(parcel, 1);
                return;
            }
            if (i2 == 4 || i2 == 8 || i2 == 16) {
                parcel.writeInt(this.mIntProp);
                return;
            }
            Log.e("AudioMixMatchCriterion", "Unknown match rule" + i2 + " when writing to Parcel");
            parcel.writeInt(-1);
        }

        public AudioAttributes getAudioAttributes() {
            return this.mAttr;
        }

        public int getIntProp() {
            return this.mIntProp;
        }

        public int getRule() {
            return this.mRule;
        }
    }

    boolean isAffectingUsage(int i) {
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion next = it.next();
            if ((next.mRule & 1) != 0 && next.mAttr != null && next.mAttr.getSystemUsage() == i) {
                return true;
            }
        }
        return false;
    }

    boolean containsMatchAttributeRuleForUsage(int i) {
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion next = it.next();
            if (next.mRule == 1 && next.mAttr != null && next.mAttr.getSystemUsage() == i) {
                return true;
            }
        }
        return false;
    }

    int getTargetMixType() {
        return this.mTargetMixType;
    }

    public int getTargetMixRole() {
        return this.mTargetMixType == 1 ? 1 : 0;
    }

    public ArrayList<AudioMixMatchCriterion> getCriteria() {
        return this.mCriteria;
    }

    public boolean allowPrivilegedMediaPlaybackCapture() {
        return this.mAllowPrivilegedPlaybackCapture;
    }

    public boolean voiceCommunicationCaptureAllowed() {
        return this.mVoiceCommunicationCaptureAllowed;
    }

    public void setVoiceCommunicationCaptureAllowed(boolean z) {
        this.mVoiceCommunicationCaptureAllowed = z;
    }

    public boolean isForCallRedirection() {
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion next = it.next();
            if (next.mAttr != null && next.mAttr.isForCallRedirection() && ((next.mRule == 1 && (next.mAttr.getUsage() == 2 || next.mAttr.getUsage() == 3)) || (next.mRule == 2 && next.mAttr.getCapturePreset() == 7))) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioMixingRule audioMixingRule = (AudioMixingRule) obj;
            if (this.mTargetMixType == audioMixingRule.mTargetMixType && Objects.equals(this.mCriteria, audioMixingRule.mCriteria) && this.mAllowPrivilegedPlaybackCapture == audioMixingRule.mAllowPrivilegedPlaybackCapture && this.mVoiceCommunicationCaptureAllowed == audioMixingRule.mVoiceCommunicationCaptureAllowed) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTargetMixType), this.mCriteria, Boolean.valueOf(this.mAllowPrivilegedPlaybackCapture), Boolean.valueOf(this.mVoiceCommunicationCaptureAllowed));
    }

    public static class Builder {
        private int mTargetMixType = -1;
        private boolean mAllowPrivilegedMediaPlaybackCapture = false;
        private boolean mVoiceCommunicationCaptureAllowed = false;
        private final Set<AudioMixMatchCriterion> mCriteria = new HashSet();

        public Builder addRule(AudioAttributes audioAttributes, int i) throws IllegalArgumentException {
            if (!AudioMixingRule.isValidAttributesSystemApiRule(i)) {
                throw new IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i, audioAttributes);
        }

        public Builder excludeRule(AudioAttributes audioAttributes, int i) throws IllegalArgumentException {
            if (!AudioMixingRule.isValidAttributesSystemApiRule(i)) {
                throw new IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i | 32768, audioAttributes);
        }

        public Builder addMixRule(int i, Object obj) throws IllegalArgumentException {
            if (!AudioMixingRule.isValidSystemApiRule(i)) {
                throw new IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i, obj);
        }

        public Builder excludeMixRule(int i, Object obj) throws IllegalArgumentException {
            if (!AudioMixingRule.isValidSystemApiRule(i)) {
                throw new IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i | 32768, obj);
        }

        public Builder allowPrivilegedPlaybackCapture(boolean z) {
            this.mAllowPrivilegedMediaPlaybackCapture = z;
            return this;
        }

        public Builder voiceCommunicationCaptureAllowed(boolean z) {
            this.mVoiceCommunicationCaptureAllowed = z;
            return this;
        }

        public Builder setTargetMixRole(int i) {
            if (i != 0 && i != 1) {
                throw new IllegalArgumentException("Illegal argument for mix role");
            }
            if (this.mCriteria.stream().map(new Function() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((AudioMixingRule.AudioMixMatchCriterion) obj).getRule());
                }
            }).anyMatch(i == 0 ? new Predicate() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean isRecorderRule;
                    isRecorderRule = AudioMixingRule.isRecorderRule(((Integer) obj).intValue());
                    return isRecorderRule;
                }
            } : new Predicate() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean isPlayerRule;
                    isPlayerRule = AudioMixingRule.isPlayerRule(((Integer) obj).intValue());
                    return isPlayerRule;
                }
            })) {
                throw new IllegalArgumentException("Target mix role is not compatible with mix rules.");
            }
            this.mTargetMixType = i != 1 ? 0 : 1;
            return this;
        }

        private Builder checkAddRuleObjInternal(int i, Object obj) throws IllegalArgumentException {
            if (obj == null) {
                throw new IllegalArgumentException("Illegal null argument for mixing rule");
            }
            if (!AudioMixingRule.isValidRule(i)) {
                throw new IllegalArgumentException("Illegal rule value " + i);
            }
            if (AudioMixingRule.isAudioAttributeRule((-32769) & i)) {
                if (!(obj instanceof AudioAttributes)) {
                    throw new IllegalArgumentException("Invalid AudioAttributes argument");
                }
                return addRuleInternal(new AudioMixMatchCriterion((AudioAttributes) obj, i));
            }
            if (!(obj instanceof Integer)) {
                throw new IllegalArgumentException("Invalid Integer argument");
            }
            return addRuleInternal(new AudioMixMatchCriterion((Integer) obj, i));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Builder addRuleInternal(AudioMixMatchCriterion audioMixMatchCriterion) throws IllegalArgumentException {
            int i = audioMixMatchCriterion.mRule;
            if (this.mTargetMixType == -1) {
                if (AudioMixingRule.isPlayerRule(i)) {
                    this.mTargetMixType = 0;
                } else if (AudioMixingRule.isRecorderRule(i)) {
                    this.mTargetMixType = 1;
                }
            } else if ((AudioMixingRule.isPlayerRule(i) && this.mTargetMixType != 0) || (AudioMixingRule.isRecorderRule(i) && this.mTargetMixType != 1)) {
                throw new IllegalArgumentException("Incompatible rule for mix");
            }
            synchronized (this.mCriteria) {
                final int i2 = i ^ 32768;
                if (this.mCriteria.stream().anyMatch(new Predicate() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return AudioMixingRule.Builder.lambda$addRuleInternal$0(i2, (AudioMixingRule.AudioMixMatchCriterion) obj);
                    }
                })) {
                    throw new IllegalArgumentException("AudioMixingRule cannot contain RULE_MATCH_* and RULE_EXCLUDE_* for the same dimension.");
                }
                this.mCriteria.add(audioMixMatchCriterion);
            }
            return this;
        }

        static /* synthetic */ boolean lambda$addRuleInternal$0(int i, AudioMixMatchCriterion audioMixMatchCriterion) {
            return audioMixMatchCriterion.mRule == i;
        }

        public AudioMixingRule build() {
            if (this.mCriteria.isEmpty()) {
                throw new IllegalArgumentException("Cannot build AudioMixingRule with no rules.");
            }
            int i = this.mTargetMixType;
            if (i == -1) {
                i = 0;
            }
            return new AudioMixingRule(i, this.mCriteria, this.mAllowPrivilegedMediaPlaybackCapture, this.mVoiceCommunicationCaptureAllowed);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mAllowPrivilegedPlaybackCapture);
        parcel.writeBoolean(this.mVoiceCommunicationCaptureAllowed);
        parcel.writeInt(this.mTargetMixType);
        parcel.writeInt(this.mCriteria.size());
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, i);
        }
    }
}
