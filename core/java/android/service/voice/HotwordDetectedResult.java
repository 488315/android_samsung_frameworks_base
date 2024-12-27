package android.service.voice;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.content.pm.AppSearchShortcutInfo;
import android.content.res.Resources;
import android.hardware.scontext.SContextConstants;
import android.media.AudioRecord;
import android.media.MediaSyncEvent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.R;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import com.samsung.android.share.SemShareConstants;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class HotwordDetectedResult implements Parcelable {
    public static final int AUDIO_CHANNEL_UNSET = -1;
    public static final int BACKGROUND_AUDIO_POWER_UNSET = -1;
    public static final int CONFIDENCE_LEVEL_HIGH = 5;
    public static final int CONFIDENCE_LEVEL_LOW = 1;
    public static final int CONFIDENCE_LEVEL_LOW_MEDIUM = 2;
    public static final int CONFIDENCE_LEVEL_MEDIUM = 3;
    public static final int CONFIDENCE_LEVEL_MEDIUM_HIGH = 4;
    public static final int CONFIDENCE_LEVEL_NONE = 0;
    public static final int CONFIDENCE_LEVEL_VERY_HIGH = 6;
    private static final String EXTRA_PROXIMITY = "android.service.voice.extra.PROXIMITY";
    public static final int HOTWORD_OFFSET_UNSET = -1;
    private static final int LIMIT_AUDIO_CHANNEL_MAX_VALUE = 63;
    private static final int LIMIT_HOTWORD_OFFSET_MAX_VALUE = 3600000;
    public static final int PROXIMITY_FAR = 2;
    public static final int PROXIMITY_NEAR = 1;
    public static final int PROXIMITY_UNKNOWN = -1;
    private int mAudioChannel;
    private final List<HotwordAudioStream> mAudioStreams;
    private final int mBackgroundAudioPower;
    private final int mConfidenceLevel;
    private final PersistableBundle mExtras;
    private boolean mHotwordDetectionPersonalized;
    private int mHotwordDurationMillis;
    private int mHotwordOffsetMillis;
    private final int mHotwordPhraseId;
    private MediaSyncEvent mMediaSyncEvent;
    private final int mPersonalizedScore;
    private final int mScore;
    private final int mSpeakerId;
    private static int sMaxBundleSize = -1;
    public static final Parcelable.Creator<HotwordDetectedResult> CREATOR = new Parcelable.Creator<HotwordDetectedResult>() { // from class: android.service.voice.HotwordDetectedResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectedResult[] newArray(int size) {
            return new HotwordDetectedResult[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectedResult createFromParcel(Parcel in) {
            return new HotwordDetectedResult(in);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfidenceLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface HotwordConfidenceLevelValue {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface Limit {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Proximity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProximityValue {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSpeakerId() {
        return 0;
    }

    public static int getMaxSpeakerId() {
        return 15;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultScore() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultPersonalizedScore() {
        return 0;
    }

    public static int getMaxScore() {
        return 255;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultHotwordPhraseId() {
        return 0;
    }

    public static int getMaxHotwordPhraseId() {
        return 63;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<HotwordAudioStream> defaultAudioStreams() {
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PersistableBundle defaultExtras() {
        return new PersistableBundle();
    }

    public static int getMaxBundleSize() {
        if (sMaxBundleSize < 0) {
            sMaxBundleSize = Resources.getSystem().getInteger(R.integer.config_hotwordDetectedResultMaxBundleSize);
        }
        return sMaxBundleSize;
    }

    public MediaSyncEvent getMediaSyncEvent() {
        return this.mMediaSyncEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultBackgroundAudioPower() {
        return -1;
    }

    public static int getMaxBackgroundAudioPower() {
        return 255;
    }

    public static int getParcelableSize(Parcelable parcelable) {
        Parcel p = Parcel.obtain();
        parcelable.writeToParcel(p, 0);
        p.setDataPosition(0);
        int size = p.dataSize();
        p.recycle();
        return size;
    }

    public static int getUsageSize(HotwordDetectedResult hotwordDetectedResult) {
        int totalBits = hotwordDetectedResult.getConfidenceLevel() != defaultConfidenceLevel() ? 0 + bitCount(6L) : 0;
        if (hotwordDetectedResult.getHotwordOffsetMillis() != -1) {
            totalBits += bitCount(3600000L);
        }
        if (hotwordDetectedResult.getHotwordDurationMillis() != 0) {
            totalBits += bitCount(AudioRecord.getMaxSharedAudioHistoryMillis());
        }
        if (hotwordDetectedResult.getAudioChannel() != -1) {
            totalBits += bitCount(63L);
        }
        int totalBits2 = totalBits + 1;
        if (hotwordDetectedResult.getScore() != defaultScore()) {
            totalBits2 += bitCount(getMaxScore());
        }
        if (hotwordDetectedResult.getPersonalizedScore() != defaultPersonalizedScore()) {
            totalBits2 += bitCount(getMaxScore());
        }
        if (hotwordDetectedResult.getHotwordPhraseId() != defaultHotwordPhraseId()) {
            totalBits2 += bitCount(getMaxHotwordPhraseId());
        }
        PersistableBundle persistableBundle = hotwordDetectedResult.getExtras();
        if (!persistableBundle.isEmpty()) {
            totalBits2 += getParcelableSize(persistableBundle) * 8;
        }
        if (hotwordDetectedResult.getBackgroundAudioPower() != defaultBackgroundAudioPower()) {
            return totalBits2 + bitCount(getMaxBackgroundAudioPower());
        }
        return totalBits2;
    }

    private static int bitCount(long value) {
        int bits = 0;
        while (value > 0) {
            bits++;
            value >>= 1;
        }
        return bits;
    }

    private void onConstructed() {
        Preconditions.checkArgumentInRange(this.mSpeakerId, 0, getMaxSpeakerId(), "speakerId");
        Preconditions.checkArgumentInRange(this.mScore, 0, getMaxScore(), SemShareConstants.SHARE_STAR_KEY_SCORE);
        Preconditions.checkArgumentInRange(this.mPersonalizedScore, 0, getMaxScore(), "personalizedScore");
        Preconditions.checkArgumentInRange(this.mHotwordPhraseId, 0, getMaxHotwordPhraseId(), "hotwordPhraseId");
        if (this.mBackgroundAudioPower != -1) {
            Preconditions.checkArgumentInRange(this.mBackgroundAudioPower, 0, getMaxBackgroundAudioPower(), "backgroundAudioPower");
        }
        Preconditions.checkArgumentInRange(this.mHotwordDurationMillis, 0L, AudioRecord.getMaxSharedAudioHistoryMillis(), "hotwordDurationMillis");
        if (this.mHotwordOffsetMillis != -1) {
            Preconditions.checkArgumentInRange(this.mHotwordOffsetMillis, 0, LIMIT_HOTWORD_OFFSET_MAX_VALUE, "hotwordOffsetMillis");
        }
        if (this.mAudioChannel != -1) {
            Preconditions.checkArgumentInRange(this.mAudioChannel, 0, 63, "audioChannel");
        }
        if (!this.mExtras.isEmpty()) {
            if (this.mExtras.containsKey(EXTRA_PROXIMITY)) {
                int proximityValue = this.mExtras.getInt(EXTRA_PROXIMITY);
                this.mExtras.remove(EXTRA_PROXIMITY);
                if (this.mExtras.size() > 0) {
                    Preconditions.checkArgumentInRange(getParcelableSize(this.mExtras), 0, getMaxBundleSize(), AppSearchShortcutInfo.KEY_EXTRAS);
                }
                this.mExtras.putInt(EXTRA_PROXIMITY, proximityValue);
                return;
            }
            Preconditions.checkArgumentInRange(getParcelableSize(this.mExtras), 0, getMaxBundleSize(), AppSearchShortcutInfo.KEY_EXTRAS);
        }
    }

    public List<HotwordAudioStream> getAudioStreams() {
        return List.copyOf(this.mAudioStreams);
    }

    public void setProximity(double distance) {
        int proximityLevel = convertToProximityLevel(distance);
        if (proximityLevel != -1) {
            this.mExtras.putInt(EXTRA_PROXIMITY, proximityLevel);
        }
    }

    public int getProximity() {
        return this.mExtras.getInt(EXTRA_PROXIMITY, -1);
    }

    private int convertToProximityLevel(double distance) {
        if (distance < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return -1;
        }
        if (distance <= 3.0d) {
            return 1;
        }
        return 2;
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }

        public Builder setAudioStreams(List<HotwordAudioStream> value) {
            Objects.requireNonNull(value, "value should not be null");
            Builder builder = (Builder) this;
            builder.mBuilderFieldsSet |= 1024;
            builder.mAudioStreams = List.copyOf(value);
            return builder;
        }
    }

    public Builder buildUpon() {
        return new Builder().setConfidenceLevel(this.mConfidenceLevel).setMediaSyncEvent(this.mMediaSyncEvent).setHotwordOffsetMillis(this.mHotwordOffsetMillis).setHotwordDurationMillis(this.mHotwordDurationMillis).setAudioChannel(this.mAudioChannel).setHotwordDetectionPersonalized(this.mHotwordDetectionPersonalized).setScore(this.mScore).setPersonalizedScore(this.mPersonalizedScore).setHotwordPhraseId(this.mHotwordPhraseId).setAudioStreams(this.mAudioStreams).setExtras(this.mExtras).setBackgroundAudioPower(this.mBackgroundAudioPower).setSpeakerId(this.mSpeakerId);
    }

    public static String confidenceLevelToString(int value) {
        switch (value) {
            case 0:
                return "CONFIDENCE_LEVEL_NONE";
            case 1:
                return "CONFIDENCE_LEVEL_LOW";
            case 2:
                return "CONFIDENCE_LEVEL_LOW_MEDIUM";
            case 3:
                return "CONFIDENCE_LEVEL_MEDIUM";
            case 4:
                return "CONFIDENCE_LEVEL_MEDIUM_HIGH";
            case 5:
                return "CONFIDENCE_LEVEL_HIGH";
            case 6:
                return "CONFIDENCE_LEVEL_VERY_HIGH";
            default:
                return Integer.toHexString(value);
        }
    }

    static String limitToString(int value) {
        switch (value) {
            case 63:
                return "LIMIT_AUDIO_CHANNEL_MAX_VALUE";
            case LIMIT_HOTWORD_OFFSET_MAX_VALUE /* 3600000 */:
                return "LIMIT_HOTWORD_OFFSET_MAX_VALUE";
            default:
                return Integer.toHexString(value);
        }
    }

    public static String proximityToString(int value) {
        switch (value) {
            case -1:
                return "PROXIMITY_UNKNOWN";
            case 0:
            default:
                return Integer.toHexString(value);
            case 1:
                return "PROXIMITY_NEAR";
            case 2:
                return "PROXIMITY_FAR";
        }
    }

    HotwordDetectedResult(int speakerId, int confidenceLevel, MediaSyncEvent mediaSyncEvent, int hotwordOffsetMillis, int hotwordDurationMillis, int audioChannel, boolean hotwordDetectionPersonalized, int score, int personalizedScore, int hotwordPhraseId, List<HotwordAudioStream> audioStreams, PersistableBundle extras, int backgroundAudioPower) {
        this.mMediaSyncEvent = null;
        this.mHotwordOffsetMillis = -1;
        this.mHotwordDurationMillis = 0;
        this.mAudioChannel = -1;
        this.mHotwordDetectionPersonalized = false;
        this.mSpeakerId = speakerId;
        this.mConfidenceLevel = confidenceLevel;
        AnnotationValidations.validate((Class<? extends Annotation>) HotwordConfidenceLevelValue.class, (Annotation) null, this.mConfidenceLevel);
        this.mMediaSyncEvent = mediaSyncEvent;
        this.mHotwordOffsetMillis = hotwordOffsetMillis;
        this.mHotwordDurationMillis = hotwordDurationMillis;
        this.mAudioChannel = audioChannel;
        this.mHotwordDetectionPersonalized = hotwordDetectionPersonalized;
        this.mScore = score;
        this.mPersonalizedScore = personalizedScore;
        this.mHotwordPhraseId = hotwordPhraseId;
        this.mAudioStreams = audioStreams;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mAudioStreams);
        this.mExtras = extras;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mExtras);
        this.mBackgroundAudioPower = backgroundAudioPower;
        onConstructed();
    }

    public int getSpeakerId() {
        return this.mSpeakerId;
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public int getHotwordOffsetMillis() {
        return this.mHotwordOffsetMillis;
    }

    public int getHotwordDurationMillis() {
        return this.mHotwordDurationMillis;
    }

    public int getAudioChannel() {
        return this.mAudioChannel;
    }

    public boolean isHotwordDetectionPersonalized() {
        return this.mHotwordDetectionPersonalized;
    }

    public int getScore() {
        return this.mScore;
    }

    public int getPersonalizedScore() {
        return this.mPersonalizedScore;
    }

    public int getHotwordPhraseId() {
        return this.mHotwordPhraseId;
    }

    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    public int getBackgroundAudioPower() {
        return this.mBackgroundAudioPower;
    }

    public String toString() {
        return "HotwordDetectedResult { speakerId = " + this.mSpeakerId + ", confidenceLevel = " + this.mConfidenceLevel + ", mediaSyncEvent = " + this.mMediaSyncEvent + ", hotwordOffsetMillis = " + this.mHotwordOffsetMillis + ", hotwordDurationMillis = " + this.mHotwordDurationMillis + ", audioChannel = " + this.mAudioChannel + ", hotwordDetectionPersonalized = " + this.mHotwordDetectionPersonalized + ", score = " + this.mScore + ", personalizedScore = " + this.mPersonalizedScore + ", hotwordPhraseId = " + this.mHotwordPhraseId + ", audioStreams = " + this.mAudioStreams + ", extras = " + this.mExtras + ", backgroundAudioPower = " + this.mBackgroundAudioPower + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotwordDetectedResult that = (HotwordDetectedResult) o;
        if (this.mSpeakerId == that.mSpeakerId && this.mConfidenceLevel == that.mConfidenceLevel && Objects.equals(this.mMediaSyncEvent, that.mMediaSyncEvent) && this.mHotwordOffsetMillis == that.mHotwordOffsetMillis && this.mHotwordDurationMillis == that.mHotwordDurationMillis && this.mAudioChannel == that.mAudioChannel && this.mHotwordDetectionPersonalized == that.mHotwordDetectionPersonalized && this.mScore == that.mScore && this.mPersonalizedScore == that.mPersonalizedScore && this.mHotwordPhraseId == that.mHotwordPhraseId && Objects.equals(this.mAudioStreams, that.mAudioStreams) && Objects.equals(this.mExtras, that.mExtras) && this.mBackgroundAudioPower == that.mBackgroundAudioPower) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mSpeakerId;
        return (((((((((((((((((((((((_hash * 31) + this.mConfidenceLevel) * 31) + Objects.hashCode(this.mMediaSyncEvent)) * 31) + this.mHotwordOffsetMillis) * 31) + this.mHotwordDurationMillis) * 31) + this.mAudioChannel) * 31) + Boolean.hashCode(this.mHotwordDetectionPersonalized)) * 31) + this.mScore) * 31) + this.mPersonalizedScore) * 31) + this.mHotwordPhraseId) * 31) + Objects.hashCode(this.mAudioStreams)) * 31) + Objects.hashCode(this.mExtras)) * 31) + this.mBackgroundAudioPower;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int flg = this.mHotwordDetectionPersonalized ? 0 | 64 : 0;
        if (this.mMediaSyncEvent != null) {
            flg |= 4;
        }
        dest.writeInt(flg);
        dest.writeInt(this.mSpeakerId);
        dest.writeInt(this.mConfidenceLevel);
        if (this.mMediaSyncEvent != null) {
            dest.writeTypedObject(this.mMediaSyncEvent, flags);
        }
        dest.writeInt(this.mHotwordOffsetMillis);
        dest.writeInt(this.mHotwordDurationMillis);
        dest.writeInt(this.mAudioChannel);
        dest.writeInt(this.mScore);
        dest.writeInt(this.mPersonalizedScore);
        dest.writeInt(this.mHotwordPhraseId);
        dest.writeParcelableList(this.mAudioStreams, flags);
        dest.writeTypedObject(this.mExtras, flags);
        dest.writeInt(this.mBackgroundAudioPower);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    HotwordDetectedResult(Parcel in) {
        this.mMediaSyncEvent = null;
        this.mHotwordOffsetMillis = -1;
        this.mHotwordDurationMillis = 0;
        this.mAudioChannel = -1;
        this.mHotwordDetectionPersonalized = false;
        int flg = in.readInt();
        boolean hotwordDetectionPersonalized = (flg & 64) != 0;
        int speakerId = in.readInt();
        int confidenceLevel = in.readInt();
        MediaSyncEvent mediaSyncEvent = (flg & 4) == 0 ? null : (MediaSyncEvent) in.readTypedObject(MediaSyncEvent.CREATOR);
        int hotwordOffsetMillis = in.readInt();
        int hotwordDurationMillis = in.readInt();
        int audioChannel = in.readInt();
        int score = in.readInt();
        int personalizedScore = in.readInt();
        int hotwordPhraseId = in.readInt();
        ArrayList arrayList = new ArrayList();
        in.readParcelableList(arrayList, HotwordAudioStream.class.getClassLoader());
        PersistableBundle extras = (PersistableBundle) in.readTypedObject(PersistableBundle.CREATOR);
        int backgroundAudioPower = in.readInt();
        this.mSpeakerId = speakerId;
        this.mConfidenceLevel = confidenceLevel;
        AnnotationValidations.validate((Class<? extends Annotation>) HotwordConfidenceLevelValue.class, (Annotation) null, this.mConfidenceLevel);
        this.mMediaSyncEvent = mediaSyncEvent;
        this.mHotwordOffsetMillis = hotwordOffsetMillis;
        this.mHotwordDurationMillis = hotwordDurationMillis;
        this.mAudioChannel = audioChannel;
        this.mHotwordDetectionPersonalized = hotwordDetectionPersonalized;
        this.mScore = score;
        this.mPersonalizedScore = personalizedScore;
        this.mHotwordPhraseId = hotwordPhraseId;
        this.mAudioStreams = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mAudioStreams);
        this.mExtras = extras;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mExtras);
        this.mBackgroundAudioPower = backgroundAudioPower;
        onConstructed();
    }

    public static final class Builder extends BaseBuilder {
        private int mAudioChannel;
        private List<HotwordAudioStream> mAudioStreams;
        private int mBackgroundAudioPower;
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;
        private PersistableBundle mExtras;
        private boolean mHotwordDetectionPersonalized;
        private int mHotwordDurationMillis;
        private int mHotwordOffsetMillis;
        private int mHotwordPhraseId;
        private MediaSyncEvent mMediaSyncEvent;
        private int mPersonalizedScore;
        private int mScore;
        private int mSpeakerId;

        @Override // android.service.voice.HotwordDetectedResult.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setAudioStreams(List list) {
            return super.setAudioStreams(list);
        }

        public Builder setSpeakerId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mSpeakerId = value;
            return this;
        }

        public Builder setConfidenceLevel(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mConfidenceLevel = value;
            return this;
        }

        public Builder setMediaSyncEvent(MediaSyncEvent value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mMediaSyncEvent = value;
            return this;
        }

        public Builder setHotwordOffsetMillis(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mHotwordOffsetMillis = value;
            return this;
        }

        public Builder setHotwordDurationMillis(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mHotwordDurationMillis = value;
            return this;
        }

        public Builder setAudioChannel(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAudioChannel = value;
            return this;
        }

        public Builder setHotwordDetectionPersonalized(boolean value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mHotwordDetectionPersonalized = value;
            return this;
        }

        public Builder setScore(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mScore = value;
            return this;
        }

        public Builder setPersonalizedScore(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            this.mPersonalizedScore = value;
            return this;
        }

        public Builder setHotwordPhraseId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 512;
            this.mHotwordPhraseId = value;
            return this;
        }

        public Builder setExtras(PersistableBundle value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2048;
            this.mExtras = value;
            return this;
        }

        public Builder setBackgroundAudioPower(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4096;
            this.mBackgroundAudioPower = value;
            return this;
        }

        public HotwordDetectedResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8192;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mSpeakerId = HotwordDetectedResult.defaultSpeakerId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mConfidenceLevel = HotwordDetectedResult.defaultConfidenceLevel();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mMediaSyncEvent = null;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mHotwordOffsetMillis = -1;
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mHotwordDurationMillis = 0;
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mAudioChannel = -1;
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mHotwordDetectionPersonalized = false;
            }
            if ((this.mBuilderFieldsSet & 128) == 0) {
                this.mScore = HotwordDetectedResult.defaultScore();
            }
            if ((this.mBuilderFieldsSet & 256) == 0) {
                this.mPersonalizedScore = HotwordDetectedResult.defaultPersonalizedScore();
            }
            if ((this.mBuilderFieldsSet & 512) == 0) {
                this.mHotwordPhraseId = HotwordDetectedResult.defaultHotwordPhraseId();
            }
            if ((this.mBuilderFieldsSet & 1024) == 0) {
                this.mAudioStreams = HotwordDetectedResult.defaultAudioStreams();
            }
            if ((this.mBuilderFieldsSet & 2048) == 0) {
                this.mExtras = HotwordDetectedResult.defaultExtras();
            }
            if ((this.mBuilderFieldsSet & 4096) == 0) {
                this.mBackgroundAudioPower = HotwordDetectedResult.defaultBackgroundAudioPower();
            }
            HotwordDetectedResult o = new HotwordDetectedResult(this.mSpeakerId, this.mConfidenceLevel, this.mMediaSyncEvent, this.mHotwordOffsetMillis, this.mHotwordDurationMillis, this.mAudioChannel, this.mHotwordDetectionPersonalized, this.mScore, this.mPersonalizedScore, this.mHotwordPhraseId, this.mAudioStreams, this.mExtras, this.mBackgroundAudioPower);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8192) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
