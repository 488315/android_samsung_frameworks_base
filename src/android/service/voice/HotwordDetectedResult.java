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
    public static final Parcelable.Creator<HotwordDetectedResult> CREATOR = new Parcelable.Creator<HotwordDetectedResult>() { // from class: android.service.voice.HotwordDetectedResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectedResult[] newArray(int i) {
            return new HotwordDetectedResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectedResult createFromParcel(Parcel parcel) {
            return new HotwordDetectedResult(parcel);
        }
    };
    private static final String EXTRA_PROXIMITY = "android.service.voice.extra.PROXIMITY";
    public static final int HOTWORD_OFFSET_UNSET = -1;
    private static final int LIMIT_AUDIO_CHANNEL_MAX_VALUE = 63;
    private static final int LIMIT_HOTWORD_OFFSET_MAX_VALUE = 3600000;
    public static final int PROXIMITY_FAR = 2;
    public static final int PROXIMITY_NEAR = 1;
    public static final int PROXIMITY_UNKNOWN = -1;
    private static int sMaxBundleSize = -1;
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

    @Deprecated
    private void __metadata() {
    }

    private static int bitCount(long j) {
        int i = 0;
        while (j > 0) {
            i++;
            j >>= 1;
        }
        return i;
    }

    private int convertToProximityLevel(double d) {
        if (d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return -1;
        }
        return d <= 3.0d ? 1 : 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultBackgroundAudioPower() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultHotwordPhraseId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultPersonalizedScore() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultScore() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSpeakerId() {
        return 0;
    }

    public static int getMaxBackgroundAudioPower() {
        return 255;
    }

    public static int getMaxHotwordPhraseId() {
        return 63;
    }

    public static int getMaxScore() {
        return 255;
    }

    public static int getMaxSpeakerId() {
        return 15;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<HotwordAudioStream> defaultAudioStreams() {
        return Collections.EMPTY_LIST;
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

    public static int getParcelableSize(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        int dataSize = obtain.dataSize();
        obtain.recycle();
        return dataSize;
    }

    public static int getUsageSize(HotwordDetectedResult hotwordDetectedResult) {
        int bitCount = hotwordDetectedResult.getConfidenceLevel() != defaultConfidenceLevel() ? bitCount(6L) : 0;
        if (hotwordDetectedResult.getHotwordOffsetMillis() != -1) {
            bitCount += bitCount(3600000L);
        }
        if (hotwordDetectedResult.getHotwordDurationMillis() != 0) {
            bitCount += bitCount(AudioRecord.getMaxSharedAudioHistoryMillis());
        }
        if (hotwordDetectedResult.getAudioChannel() != -1) {
            bitCount += bitCount(63L);
        }
        int i = bitCount + 1;
        if (hotwordDetectedResult.getScore() != defaultScore()) {
            i += bitCount(getMaxScore());
        }
        if (hotwordDetectedResult.getPersonalizedScore() != defaultPersonalizedScore()) {
            i += bitCount(getMaxScore());
        }
        if (hotwordDetectedResult.getHotwordPhraseId() != defaultHotwordPhraseId()) {
            i += bitCount(getMaxHotwordPhraseId());
        }
        PersistableBundle extras = hotwordDetectedResult.getExtras();
        if (!extras.isEmpty()) {
            i += getParcelableSize(extras) * 8;
        }
        return hotwordDetectedResult.getBackgroundAudioPower() != defaultBackgroundAudioPower() ? i + bitCount(getMaxBackgroundAudioPower()) : i;
    }

    private void onConstructed() {
        Preconditions.checkArgumentInRange(this.mSpeakerId, 0, getMaxSpeakerId(), "speakerId");
        Preconditions.checkArgumentInRange(this.mScore, 0, getMaxScore(), SemShareConstants.SHARE_STAR_KEY_SCORE);
        Preconditions.checkArgumentInRange(this.mPersonalizedScore, 0, getMaxScore(), "personalizedScore");
        Preconditions.checkArgumentInRange(this.mHotwordPhraseId, 0, getMaxHotwordPhraseId(), "hotwordPhraseId");
        int i = this.mBackgroundAudioPower;
        if (i != -1) {
            Preconditions.checkArgumentInRange(i, 0, getMaxBackgroundAudioPower(), "backgroundAudioPower");
        }
        Preconditions.checkArgumentInRange(this.mHotwordDurationMillis, 0L, AudioRecord.getMaxSharedAudioHistoryMillis(), "hotwordDurationMillis");
        int i2 = this.mHotwordOffsetMillis;
        if (i2 != -1) {
            Preconditions.checkArgumentInRange(i2, 0, 3600000, "hotwordOffsetMillis");
        }
        int i3 = this.mAudioChannel;
        if (i3 != -1) {
            Preconditions.checkArgumentInRange(i3, 0, 63, "audioChannel");
        }
        if (this.mExtras.isEmpty()) {
            return;
        }
        if (this.mExtras.containsKey(EXTRA_PROXIMITY)) {
            int i4 = this.mExtras.getInt(EXTRA_PROXIMITY);
            this.mExtras.remove(EXTRA_PROXIMITY);
            if (this.mExtras.size() > 0) {
                Preconditions.checkArgumentInRange(getParcelableSize(this.mExtras), 0, getMaxBundleSize(), AppSearchShortcutInfo.KEY_EXTRAS);
            }
            this.mExtras.putInt(EXTRA_PROXIMITY, i4);
            return;
        }
        Preconditions.checkArgumentInRange(getParcelableSize(this.mExtras), 0, getMaxBundleSize(), AppSearchShortcutInfo.KEY_EXTRAS);
    }

    public List<HotwordAudioStream> getAudioStreams() {
        return List.copyOf(this.mAudioStreams);
    }

    public void setProximity(double d) {
        int convertToProximityLevel = convertToProximityLevel(d);
        if (convertToProximityLevel != -1) {
            this.mExtras.putInt(EXTRA_PROXIMITY, convertToProximityLevel);
        }
    }

    public int getProximity() {
        return this.mExtras.getInt(EXTRA_PROXIMITY, -1);
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }

        public Builder setAudioStreams(List<HotwordAudioStream> list) {
            Objects.requireNonNull(list, "value should not be null");
            Builder builder = (Builder) this;
            builder.mBuilderFieldsSet |= 1024;
            builder.mAudioStreams = List.copyOf(list);
            return builder;
        }
    }

    public Builder buildUpon() {
        return new Builder().setConfidenceLevel(this.mConfidenceLevel).setMediaSyncEvent(this.mMediaSyncEvent).setHotwordOffsetMillis(this.mHotwordOffsetMillis).setHotwordDurationMillis(this.mHotwordDurationMillis).setAudioChannel(this.mAudioChannel).setHotwordDetectionPersonalized(this.mHotwordDetectionPersonalized).setScore(this.mScore).setPersonalizedScore(this.mPersonalizedScore).setHotwordPhraseId(this.mHotwordPhraseId).setAudioStreams(this.mAudioStreams).setExtras(this.mExtras).setBackgroundAudioPower(this.mBackgroundAudioPower).setSpeakerId(this.mSpeakerId);
    }

    public static String confidenceLevelToString(int i) {
        switch (i) {
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
                return Integer.toHexString(i);
        }
    }

    static String limitToString(int i) {
        if (i == 63) {
            return "LIMIT_AUDIO_CHANNEL_MAX_VALUE";
        }
        if (i == 3600000) {
            return "LIMIT_HOTWORD_OFFSET_MAX_VALUE";
        }
        return Integer.toHexString(i);
    }

    public static String proximityToString(int i) {
        if (i == -1) {
            return "PROXIMITY_UNKNOWN";
        }
        if (i == 1) {
            return "PROXIMITY_NEAR";
        }
        if (i == 2) {
            return "PROXIMITY_FAR";
        }
        return Integer.toHexString(i);
    }

    HotwordDetectedResult(int i, int i2, MediaSyncEvent mediaSyncEvent, int i3, int i4, int i5, boolean z, int i6, int i7, int i8, List<HotwordAudioStream> list, PersistableBundle persistableBundle, int i9) {
        this.mMediaSyncEvent = null;
        this.mHotwordOffsetMillis = -1;
        this.mHotwordDurationMillis = 0;
        this.mAudioChannel = -1;
        this.mHotwordDetectionPersonalized = false;
        this.mSpeakerId = i;
        this.mConfidenceLevel = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) HotwordConfidenceLevelValue.class, (Annotation) null, i2);
        this.mMediaSyncEvent = mediaSyncEvent;
        this.mHotwordOffsetMillis = i3;
        this.mHotwordDurationMillis = i4;
        this.mAudioChannel = i5;
        this.mHotwordDetectionPersonalized = z;
        this.mScore = i6;
        this.mPersonalizedScore = i7;
        this.mHotwordPhraseId = i8;
        this.mAudioStreams = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        this.mExtras = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
        this.mBackgroundAudioPower = i9;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            HotwordDetectedResult hotwordDetectedResult = (HotwordDetectedResult) obj;
            if (this.mSpeakerId == hotwordDetectedResult.mSpeakerId && this.mConfidenceLevel == hotwordDetectedResult.mConfidenceLevel && Objects.equals(this.mMediaSyncEvent, hotwordDetectedResult.mMediaSyncEvent) && this.mHotwordOffsetMillis == hotwordDetectedResult.mHotwordOffsetMillis && this.mHotwordDurationMillis == hotwordDetectedResult.mHotwordDurationMillis && this.mAudioChannel == hotwordDetectedResult.mAudioChannel && this.mHotwordDetectionPersonalized == hotwordDetectedResult.mHotwordDetectionPersonalized && this.mScore == hotwordDetectedResult.mScore && this.mPersonalizedScore == hotwordDetectedResult.mPersonalizedScore && this.mHotwordPhraseId == hotwordDetectedResult.mHotwordPhraseId && Objects.equals(this.mAudioStreams, hotwordDetectedResult.mAudioStreams) && Objects.equals(this.mExtras, hotwordDetectedResult.mExtras) && this.mBackgroundAudioPower == hotwordDetectedResult.mBackgroundAudioPower) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((this.mSpeakerId + 31) * 31) + this.mConfidenceLevel) * 31) + Objects.hashCode(this.mMediaSyncEvent)) * 31) + this.mHotwordOffsetMillis) * 31) + this.mHotwordDurationMillis) * 31) + this.mAudioChannel) * 31) + Boolean.hashCode(this.mHotwordDetectionPersonalized)) * 31) + this.mScore) * 31) + this.mPersonalizedScore) * 31) + this.mHotwordPhraseId) * 31) + Objects.hashCode(this.mAudioStreams)) * 31) + Objects.hashCode(this.mExtras)) * 31) + this.mBackgroundAudioPower;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = this.mHotwordDetectionPersonalized ? 64 : 0;
        if (this.mMediaSyncEvent != null) {
            i2 |= 4;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.mSpeakerId);
        parcel.writeInt(this.mConfidenceLevel);
        MediaSyncEvent mediaSyncEvent = this.mMediaSyncEvent;
        if (mediaSyncEvent != null) {
            parcel.writeTypedObject(mediaSyncEvent, i);
        }
        parcel.writeInt(this.mHotwordOffsetMillis);
        parcel.writeInt(this.mHotwordDurationMillis);
        parcel.writeInt(this.mAudioChannel);
        parcel.writeInt(this.mScore);
        parcel.writeInt(this.mPersonalizedScore);
        parcel.writeInt(this.mHotwordPhraseId);
        parcel.writeParcelableList(this.mAudioStreams, i);
        parcel.writeTypedObject(this.mExtras, i);
        parcel.writeInt(this.mBackgroundAudioPower);
    }

    HotwordDetectedResult(Parcel parcel) {
        this.mMediaSyncEvent = null;
        this.mHotwordOffsetMillis = -1;
        this.mHotwordDurationMillis = 0;
        this.mAudioChannel = -1;
        this.mHotwordDetectionPersonalized = false;
        int readInt = parcel.readInt();
        boolean z = (readInt & 64) != 0;
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        MediaSyncEvent mediaSyncEvent = (readInt & 4) == 0 ? null : (MediaSyncEvent) parcel.readTypedObject(MediaSyncEvent.CREATOR);
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        int readInt6 = parcel.readInt();
        int readInt7 = parcel.readInt();
        int readInt8 = parcel.readInt();
        int readInt9 = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, HotwordAudioStream.class.getClassLoader());
        PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
        int readInt10 = parcel.readInt();
        this.mSpeakerId = readInt2;
        this.mConfidenceLevel = readInt3;
        AnnotationValidations.validate((Class<? extends Annotation>) HotwordConfidenceLevelValue.class, (Annotation) null, readInt3);
        this.mMediaSyncEvent = mediaSyncEvent;
        this.mHotwordOffsetMillis = readInt4;
        this.mHotwordDurationMillis = readInt5;
        this.mAudioChannel = readInt6;
        this.mHotwordDetectionPersonalized = z;
        this.mScore = readInt7;
        this.mPersonalizedScore = readInt8;
        this.mHotwordPhraseId = readInt9;
        this.mAudioStreams = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mExtras = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
        this.mBackgroundAudioPower = readInt10;
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

        public Builder setSpeakerId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mSpeakerId = i;
            return this;
        }

        public Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mConfidenceLevel = i;
            return this;
        }

        public Builder setMediaSyncEvent(MediaSyncEvent mediaSyncEvent) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mMediaSyncEvent = mediaSyncEvent;
            return this;
        }

        public Builder setHotwordOffsetMillis(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mHotwordOffsetMillis = i;
            return this;
        }

        public Builder setHotwordDurationMillis(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mHotwordDurationMillis = i;
            return this;
        }

        public Builder setAudioChannel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAudioChannel = i;
            return this;
        }

        public Builder setHotwordDetectionPersonalized(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mHotwordDetectionPersonalized = z;
            return this;
        }

        public Builder setScore(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mScore = i;
            return this;
        }

        public Builder setPersonalizedScore(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            this.mPersonalizedScore = i;
            return this;
        }

        public Builder setHotwordPhraseId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 512;
            this.mHotwordPhraseId = i;
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2048;
            this.mExtras = persistableBundle;
            return this;
        }

        public Builder setBackgroundAudioPower(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4096;
            this.mBackgroundAudioPower = i;
            return this;
        }

        public HotwordDetectedResult build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 8192;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mSpeakerId = HotwordDetectedResult.defaultSpeakerId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mConfidenceLevel = HotwordDetectedResult.defaultConfidenceLevel();
            }
            long j2 = this.mBuilderFieldsSet;
            if ((4 & j2) == 0) {
                this.mMediaSyncEvent = null;
            }
            if ((8 & j2) == 0) {
                this.mHotwordOffsetMillis = -1;
            }
            if ((16 & j2) == 0) {
                this.mHotwordDurationMillis = 0;
            }
            if ((32 & j2) == 0) {
                this.mAudioChannel = -1;
            }
            if ((64 & j2) == 0) {
                this.mHotwordDetectionPersonalized = false;
            }
            if ((j2 & 128) == 0) {
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
            return new HotwordDetectedResult(this.mSpeakerId, this.mConfidenceLevel, this.mMediaSyncEvent, this.mHotwordOffsetMillis, this.mHotwordDurationMillis, this.mAudioChannel, this.mHotwordDetectionPersonalized, this.mScore, this.mPersonalizedScore, this.mHotwordPhraseId, this.mAudioStreams, this.mExtras, this.mBackgroundAudioPower);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8192) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
