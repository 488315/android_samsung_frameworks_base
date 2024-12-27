package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
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
public class AudioMixingRule implements Parcelable {
    public static final Parcelable.Creator<AudioMixingRule> CREATOR =
            new Parcelable.Creator<
                    AudioMixingRule>() { // from class: android.media.audiopolicy.AudioMixingRule.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioMixingRule createFromParcel(Parcel source) {
                    Builder ruleBuilder = new Builder();
                    ruleBuilder.allowPrivilegedPlaybackCapture(source.readBoolean());
                    ruleBuilder.voiceCommunicationCaptureAllowed(source.readBoolean());
                    ruleBuilder.setTargetMixRole(source.readInt());
                    int nbRules = source.readInt();
                    for (int j = 0; j < nbRules; j++) {
                        ruleBuilder.addRuleInternal(
                                AudioMixMatchCriterion.CREATOR.createFromParcel(source));
                    }
                    return ruleBuilder.build();
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioMixingRule[] newArray(int size) {
                    return new AudioMixingRule[size];
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
    public @interface MixRole {}

    private AudioMixingRule(
            int mixType,
            Collection<AudioMixMatchCriterion> criteria,
            boolean allowPrivilegedMediaPlaybackCapture,
            boolean voiceCommunicationCaptureAllowed) {
        this.mAllowPrivilegedPlaybackCapture = false;
        this.mVoiceCommunicationCaptureAllowed = false;
        this.mCriteria = new ArrayList<>(criteria);
        this.mTargetMixType = mixType;
        this.mAllowPrivilegedPlaybackCapture = allowPrivilegedMediaPlaybackCapture;
        this.mVoiceCommunicationCaptureAllowed = voiceCommunicationCaptureAllowed;
    }

    public static final class AudioMixMatchCriterion implements Parcelable {
        public static final Parcelable.Creator<AudioMixMatchCriterion> CREATOR =
                new Parcelable.Creator<AudioMixMatchCriterion>() { // from class:
                    // android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AudioMixMatchCriterion createFromParcel(Parcel p) {
                        return new AudioMixMatchCriterion(p);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AudioMixMatchCriterion[] newArray(int size) {
                        return new AudioMixMatchCriterion[size];
                    }
                };
        final AudioAttributes mAttr;
        final int mIntProp;
        final int mRule;

        public AudioMixMatchCriterion(AudioAttributes attributes, int rule) {
            this.mAttr = attributes;
            this.mIntProp = Integer.MIN_VALUE;
            this.mRule = rule;
        }

        public AudioMixMatchCriterion(Integer intProp, int rule) {
            this.mAttr = null;
            this.mIntProp = intProp.intValue();
            this.mRule = rule;
        }

        private AudioMixMatchCriterion(Parcel in) {
            Objects.requireNonNull(in);
            this.mRule = in.readInt();
            int match_rule = this.mRule & (-32769);
            switch (match_rule) {
                case 1:
                case 2:
                    this.mAttr = AudioAttributes.CREATOR.createFromParcel(in);
                    this.mIntProp = Integer.MIN_VALUE;
                    return;
                case 4:
                case 8:
                case 16:
                    this.mIntProp = in.readInt();
                    this.mAttr = null;
                    return;
                default:
                    in.readInt();
                    throw new IllegalArgumentException(
                            "Illegal rule value " + this.mRule + " in parcel");
            }
        }

        public int hashCode() {
            return Objects.hash(
                    this.mAttr, Integer.valueOf(this.mIntProp), Integer.valueOf(this.mRule));
        }

        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            if (object == this) {
                return true;
            }
            AudioMixMatchCriterion other = (AudioMixMatchCriterion) object;
            if (this.mRule != other.mRule
                    || this.mIntProp != other.mIntProp
                    || !Objects.equals(this.mAttr, other.mAttr)) {
                return false;
            }
            return true;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mRule);
            int match_rule = this.mRule & (-32769);
            switch (match_rule) {
                case 1:
                case 2:
                    this.mAttr.writeToParcel(dest, 1);
                    break;
                case 4:
                case 8:
                case 16:
                    dest.writeInt(this.mIntProp);
                    break;
                default:
                    Log.e(
                            "AudioMixMatchCriterion",
                            "Unknown match rule" + match_rule + " when writing to Parcel");
                    dest.writeInt(-1);
                    break;
            }
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

    boolean isAffectingUsage(int usage) {
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion criterion = it.next();
            if ((criterion.mRule & 1) != 0
                    && criterion.mAttr != null
                    && criterion.mAttr.getSystemUsage() == usage) {
                return true;
            }
        }
        return false;
    }

    boolean containsMatchAttributeRuleForUsage(int usage) {
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion criterion = it.next();
            if (criterion.mRule == 1
                    && criterion.mAttr != null
                    && criterion.mAttr.getSystemUsage() == usage) {
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

    public void setVoiceCommunicationCaptureAllowed(boolean allowed) {
        this.mVoiceCommunicationCaptureAllowed = allowed;
    }

    public boolean isForCallRedirection() {
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion criterion = it.next();
            if (criterion.mAttr != null
                    && criterion.mAttr.isForCallRedirection()
                    && ((criterion.mRule == 1
                                    && (criterion.mAttr.getUsage() == 2
                                            || criterion.mAttr.getUsage() == 3))
                            || (criterion.mRule == 2 && criterion.mAttr.getCapturePreset() == 7))) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioMixingRule that = (AudioMixingRule) o;
        if (this.mTargetMixType == that.mTargetMixType
                && Objects.equals(this.mCriteria, that.mCriteria)
                && this.mAllowPrivilegedPlaybackCapture == that.mAllowPrivilegedPlaybackCapture
                && this.mVoiceCommunicationCaptureAllowed
                        == that.mVoiceCommunicationCaptureAllowed) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mTargetMixType),
                this.mCriteria,
                Boolean.valueOf(this.mAllowPrivilegedPlaybackCapture),
                Boolean.valueOf(this.mVoiceCommunicationCaptureAllowed));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidSystemApiRule(int rule) {
        switch (rule) {
            case 1:
            case 2:
            case 4:
            case 8:
            case 16:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidAttributesSystemApiRule(int rule) {
        switch (rule) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRule(int rule) {
        int match_rule = (-32769) & rule;
        switch (match_rule) {
            case 1:
            case 2:
            case 4:
            case 8:
            case 16:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPlayerRule(int rule) {
        int match_rule = (-32769) & rule;
        switch (match_rule) {
            case 1:
            case 8:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRecorderRule(int rule) {
        int match_rule = (-32769) & rule;
        switch (match_rule) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAudioAttributeRule(int match_rule) {
        switch (match_rule) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    public static class Builder {
        private int mTargetMixType = -1;
        private boolean mAllowPrivilegedMediaPlaybackCapture = false;
        private boolean mVoiceCommunicationCaptureAllowed = false;
        private final Set<AudioMixMatchCriterion> mCriteria = new HashSet();

        public Builder addRule(AudioAttributes attrToMatch, int rule)
                throws IllegalArgumentException {
            if (!AudioMixingRule.isValidAttributesSystemApiRule(rule)) {
                throw new IllegalArgumentException("Illegal rule value " + rule);
            }
            return checkAddRuleObjInternal(rule, attrToMatch);
        }

        public Builder excludeRule(AudioAttributes attrToMatch, int rule)
                throws IllegalArgumentException {
            if (!AudioMixingRule.isValidAttributesSystemApiRule(rule)) {
                throw new IllegalArgumentException("Illegal rule value " + rule);
            }
            return checkAddRuleObjInternal(32768 | rule, attrToMatch);
        }

        public Builder addMixRule(int rule, Object property) throws IllegalArgumentException {
            if (!AudioMixingRule.isValidSystemApiRule(rule)) {
                throw new IllegalArgumentException("Illegal rule value " + rule);
            }
            return checkAddRuleObjInternal(rule, property);
        }

        public Builder excludeMixRule(int rule, Object property) throws IllegalArgumentException {
            if (!AudioMixingRule.isValidSystemApiRule(rule)) {
                throw new IllegalArgumentException("Illegal rule value " + rule);
            }
            return checkAddRuleObjInternal(32768 | rule, property);
        }

        public Builder allowPrivilegedPlaybackCapture(boolean allow) {
            this.mAllowPrivilegedMediaPlaybackCapture = allow;
            return this;
        }

        public Builder voiceCommunicationCaptureAllowed(boolean allowed) {
            this.mVoiceCommunicationCaptureAllowed = allowed;
            return this;
        }

        public Builder setTargetMixRole(int mixRole) {
            if (mixRole != 0 && mixRole != 1) {
                throw new IllegalArgumentException("Illegal argument for mix role");
            }
            if (this.mCriteria.stream()
                    .map(
                            new Function() { // from class:
                                // android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return Integer.valueOf(
                                            ((AudioMixingRule.AudioMixMatchCriterion) obj)
                                                    .getRule());
                                }
                            })
                    .anyMatch(
                            mixRole == 0
                                    ? new Predicate() { // from class:
                                        // android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean isRecorderRule;
                                            isRecorderRule =
                                                    AudioMixingRule.isRecorderRule(
                                                            ((Integer) obj).intValue());
                                            return isRecorderRule;
                                        }
                                    }
                                    : new Predicate() { // from class:
                                        // android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean isPlayerRule;
                                            isPlayerRule =
                                                    AudioMixingRule.isPlayerRule(
                                                            ((Integer) obj).intValue());
                                            return isPlayerRule;
                                        }
                                    })) {
                throw new IllegalArgumentException(
                        "Target mix role is not compatible with mix rules.");
            }
            this.mTargetMixType = mixRole != 1 ? 0 : 1;
            return this;
        }

        private Builder checkAddRuleObjInternal(int rule, Object property)
                throws IllegalArgumentException {
            if (property == null) {
                throw new IllegalArgumentException("Illegal null argument for mixing rule");
            }
            if (!AudioMixingRule.isValidRule(rule)) {
                throw new IllegalArgumentException("Illegal rule value " + rule);
            }
            int match_rule = (-32769) & rule;
            if (AudioMixingRule.isAudioAttributeRule(match_rule)) {
                if (!(property instanceof AudioAttributes)) {
                    throw new IllegalArgumentException("Invalid AudioAttributes argument");
                }
                return addRuleInternal(
                        new AudioMixMatchCriterion((AudioAttributes) property, rule));
            }
            if (!(property instanceof Integer)) {
                throw new IllegalArgumentException("Invalid Integer argument");
            }
            return addRuleInternal(new AudioMixMatchCriterion((Integer) property, rule));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Builder addRuleInternal(AudioMixMatchCriterion criterion)
                throws IllegalArgumentException {
            int rule = criterion.mRule;
            if (this.mTargetMixType == -1) {
                if (AudioMixingRule.isPlayerRule(rule)) {
                    this.mTargetMixType = 0;
                } else if (AudioMixingRule.isRecorderRule(rule)) {
                    this.mTargetMixType = 1;
                }
            } else if ((AudioMixingRule.isPlayerRule(rule) && this.mTargetMixType != 0)
                    || (AudioMixingRule.isRecorderRule(rule) && this.mTargetMixType != 1)) {
                throw new IllegalArgumentException("Incompatible rule for mix");
            }
            synchronized (this.mCriteria) {
                final int oppositeRule = 32768 ^ rule;
                if (this.mCriteria.stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                    // android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return AudioMixingRule.Builder.lambda$addRuleInternal$0(
                                                oppositeRule,
                                                (AudioMixingRule.AudioMixMatchCriterion) obj);
                                    }
                                })) {
                    throw new IllegalArgumentException(
                            "AudioMixingRule cannot contain RULE_MATCH_* and RULE_EXCLUDE_* for the"
                                    + " same dimension.");
                }
                this.mCriteria.add(criterion);
            }
            return this;
        }

        static /* synthetic */ boolean lambda$addRuleInternal$0(
                int oppositeRule, AudioMixMatchCriterion otherCriterion) {
            return otherCriterion.mRule == oppositeRule;
        }

        public AudioMixingRule build() {
            if (this.mCriteria.isEmpty()) {
                throw new IllegalArgumentException("Cannot build AudioMixingRule with no rules.");
            }
            return new AudioMixingRule(
                    this.mTargetMixType == -1 ? 0 : this.mTargetMixType,
                    this.mCriteria,
                    this.mAllowPrivilegedMediaPlaybackCapture,
                    this.mVoiceCommunicationCaptureAllowed);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mAllowPrivilegedPlaybackCapture);
        dest.writeBoolean(this.mVoiceCommunicationCaptureAllowed);
        dest.writeInt(this.mTargetMixType);
        dest.writeInt(this.mCriteria.size());
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion criterion = it.next();
            criterion.writeToParcel(dest, flags);
        }
    }
}
