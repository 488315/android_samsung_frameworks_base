package android.media;

import android.annotation.SystemApi;
import android.media.VolumeShaper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class FadeManagerConfiguration implements Parcelable {
    public static final Parcelable.Creator<FadeManagerConfiguration> CREATOR = new Parcelable.Creator<FadeManagerConfiguration>() { // from class: android.media.FadeManagerConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FadeManagerConfiguration createFromParcel(Parcel parcel) {
            return new FadeManagerConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FadeManagerConfiguration[] newArray(int i) {
            return new FadeManagerConfiguration[i];
        }
    };
    private static final long DEFAULT_FADE_IN_DURATION_MS = 1000;
    private static final long DEFAULT_FADE_OUT_DURATION_MS = 2000;
    public static final long DURATION_NOT_SET = 0;
    public static final int FADE_STATE_DISABLED = 0;
    public static final int FADE_STATE_ENABLED_DEFAULT = 1;
    public static final String TAG = "FadeManagerConfiguration";
    public static final int VOLUME_SHAPER_SYSTEM_FADE_ID = 2;
    private final ArrayMap<AudioAttributes, FadeVolumeShaperConfigsWrapper> mAttrToFadeWrapperMap;
    private final long mFadeInDelayForOffendersMillis;
    private final long mFadeInDurationMillis;
    private final long mFadeOutDurationMillis;
    private final int mFadeState;
    private final IntArray mFadeableUsages;
    private final List<AudioAttributes> mUnfadeableAudioAttributes;
    private final IntArray mUnfadeableContentTypes;
    private final IntArray mUnfadeablePlayerTypes;
    private final IntArray mUnfadeableUids;
    private final SparseArray<FadeVolumeShaperConfigsWrapper> mUsageToFadeWrapperMap;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FadeStateEnum {
    }

    public static long getDefaultFadeInDurationMillis() {
        return 1000L;
    }

    public static long getDefaultFadeOutDurationMillis() {
        return DEFAULT_FADE_OUT_DURATION_MS;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private FadeManagerConfiguration(int i, long j, long j2, long j3, SparseArray<FadeVolumeShaperConfigsWrapper> sparseArray, ArrayMap<AudioAttributes, FadeVolumeShaperConfigsWrapper> arrayMap, IntArray intArray, IntArray intArray2, IntArray intArray3, IntArray intArray4, List<AudioAttributes> list) {
        this.mFadeState = i;
        this.mFadeOutDurationMillis = j;
        this.mFadeInDurationMillis = j2;
        this.mFadeInDelayForOffendersMillis = j3;
        this.mUsageToFadeWrapperMap = (SparseArray) Objects.requireNonNull(sparseArray, "Usage to fade wrapper map cannot be null");
        this.mAttrToFadeWrapperMap = (ArrayMap) Objects.requireNonNull(arrayMap, "Attribute to fade wrapper map cannot be null");
        this.mFadeableUsages = (IntArray) Objects.requireNonNull(intArray, "List of fadeable usages cannot be null");
        this.mUnfadeableContentTypes = (IntArray) Objects.requireNonNull(intArray2, "List of unfadeable content types cannot be null");
        this.mUnfadeablePlayerTypes = (IntArray) Objects.requireNonNull(intArray3, "List of unfadeable player types cannot be null");
        this.mUnfadeableUids = (IntArray) Objects.requireNonNull(intArray4, "List of unfadeable uids cannot be null");
        this.mUnfadeableAudioAttributes = (List) Objects.requireNonNull(list, "List of unfadeable audio attributes cannot be null");
    }

    public int getFadeState() {
        return this.mFadeState;
    }

    public List<Integer> getFadeableUsages() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mFadeableUsages);
    }

    public List<Integer> getUnfadeablePlayerTypes() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mUnfadeablePlayerTypes);
    }

    public List<Integer> getUnfadeableContentTypes() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mUnfadeableContentTypes);
    }

    public List<Integer> getUnfadeableUids() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mUnfadeableUids);
    }

    public List<AudioAttributes> getUnfadeableAudioAttributes() {
        ensureFadingIsEnabled();
        return this.mUnfadeableAudioAttributes;
    }

    public long getFadeOutDurationForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), false));
    }

    public long getFadeInDurationForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), true));
    }

    public VolumeShaper.Configuration getFadeOutVolumeShaperConfigForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), false);
    }

    public VolumeShaper.Configuration getFadeInVolumeShaperConfigForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), true);
    }

    public long getFadeOutDurationForAudioAttributes(AudioAttributes audioAttributes) {
        ensureFadingIsEnabled();
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), false));
    }

    public long getFadeInDurationForAudioAttributes(AudioAttributes audioAttributes) {
        ensureFadingIsEnabled();
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), true));
    }

    public VolumeShaper.Configuration getFadeOutVolumeShaperConfigForAudioAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        ensureFadingIsEnabled();
        return getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), false);
    }

    public VolumeShaper.Configuration getFadeInVolumeShaperConfigForAudioAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        ensureFadingIsEnabled();
        return getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), true);
    }

    public List<AudioAttributes> getAudioAttributesWithVolumeShaperConfigs() {
        return getAudioAttributesInternal();
    }

    public long getFadeInDelayForOffenders() {
        return this.mFadeInDelayForOffendersMillis;
    }

    public boolean isFadeEnabled() {
        return this.mFadeState != 0;
    }

    public boolean isUsageFadeable(int i) {
        if (isFadeEnabled()) {
            return this.mFadeableUsages.contains(i);
        }
        return false;
    }

    public boolean isContentTypeUnfadeable(int i) {
        if (isFadeEnabled()) {
            return this.mUnfadeableContentTypes.contains(i);
        }
        return true;
    }

    public boolean isPlayerTypeUnfadeable(int i) {
        if (isFadeEnabled()) {
            return this.mUnfadeablePlayerTypes.contains(i);
        }
        return true;
    }

    public boolean isAudioAttributesUnfadeable(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        if (isFadeEnabled()) {
            return this.mUnfadeableAudioAttributes.contains(audioAttributes);
        }
        return true;
    }

    public boolean isUidUnfadeable(int i) {
        if (isFadeEnabled()) {
            return this.mUnfadeableUids.contains(i);
        }
        return true;
    }

    public String toString() {
        return "FadeManagerConfiguration { fade state = " + fadeStateToString(this.mFadeState) + ", fade out duration = " + this.mFadeOutDurationMillis + ", fade in duration = " + this.mFadeInDurationMillis + ", offenders fade in delay = " + this.mFadeInDelayForOffendersMillis + ", fade volume shapers for audio attributes = " + this.mAttrToFadeWrapperMap + ", fadeable usages = " + this.mFadeableUsages.toString() + ", unfadeable content types = " + this.mUnfadeableContentTypes.toString() + ", unfadeable player types = " + this.mUnfadeablePlayerTypes.toString() + ", unfadeable uids = " + this.mUnfadeableUids.toString() + ", unfadeable audio attributes = " + this.mUnfadeableAudioAttributes + "}";
    }

    public static String fadeStateToString(int i) {
        if (i == 0) {
            return "FADE_STATE_DISABLED";
        }
        if (i == 1) {
            return "FADE_STATE_ENABLED_DEFAULT";
        }
        return "unknown fade state: " + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FadeManagerConfiguration)) {
            return false;
        }
        FadeManagerConfiguration fadeManagerConfiguration = (FadeManagerConfiguration) obj;
        return this.mUsageToFadeWrapperMap.contentEquals(fadeManagerConfiguration.mUsageToFadeWrapperMap) && this.mAttrToFadeWrapperMap.equals(fadeManagerConfiguration.mAttrToFadeWrapperMap) && Arrays.equals(this.mFadeableUsages.toArray(), fadeManagerConfiguration.mFadeableUsages.toArray()) && Arrays.equals(this.mUnfadeableContentTypes.toArray(), fadeManagerConfiguration.mUnfadeableContentTypes.toArray()) && Arrays.equals(this.mUnfadeablePlayerTypes.toArray(), fadeManagerConfiguration.mUnfadeablePlayerTypes.toArray()) && Arrays.equals(this.mUnfadeableUids.toArray(), fadeManagerConfiguration.mUnfadeableUids.toArray()) && this.mUnfadeableAudioAttributes.equals(fadeManagerConfiguration.mUnfadeableAudioAttributes) && this.mFadeState == fadeManagerConfiguration.mFadeState && this.mFadeOutDurationMillis == fadeManagerConfiguration.mFadeOutDurationMillis && this.mFadeInDurationMillis == fadeManagerConfiguration.mFadeInDurationMillis && this.mFadeInDelayForOffendersMillis == fadeManagerConfiguration.mFadeInDelayForOffendersMillis;
    }

    public int hashCode() {
        return Objects.hash(this.mUsageToFadeWrapperMap, this.mAttrToFadeWrapperMap, this.mFadeableUsages, this.mUnfadeableContentTypes, this.mUnfadeablePlayerTypes, this.mUnfadeableAudioAttributes, this.mUnfadeableUids, Integer.valueOf(this.mFadeState), Long.valueOf(this.mFadeOutDurationMillis), Long.valueOf(this.mFadeInDurationMillis), Long.valueOf(this.mFadeInDelayForOffendersMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mFadeState);
        parcel.writeLong(this.mFadeOutDurationMillis);
        parcel.writeLong(this.mFadeInDurationMillis);
        parcel.writeLong(this.mFadeInDelayForOffendersMillis);
        parcel.writeTypedSparseArray(this.mUsageToFadeWrapperMap, i);
        parcel.writeMap(this.mAttrToFadeWrapperMap);
        parcel.writeIntArray(this.mFadeableUsages.toArray());
        parcel.writeIntArray(this.mUnfadeableContentTypes.toArray());
        parcel.writeIntArray(this.mUnfadeablePlayerTypes.toArray());
        parcel.writeIntArray(this.mUnfadeableUids.toArray());
        parcel.writeTypedList(this.mUnfadeableAudioAttributes, i);
    }

    FadeManagerConfiguration(Parcel parcel) {
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        long readLong2 = parcel.readLong();
        long readLong3 = parcel.readLong();
        SparseArray<FadeVolumeShaperConfigsWrapper> createTypedSparseArray = parcel.createTypedSparseArray(FadeVolumeShaperConfigsWrapper.CREATOR);
        ArrayMap<AudioAttributes, FadeVolumeShaperConfigsWrapper> arrayMap = new ArrayMap<>();
        parcel.readMap(arrayMap, getClass().getClassLoader(), AudioAttributes.class, FadeVolumeShaperConfigsWrapper.class);
        int[] createIntArray = parcel.createIntArray();
        int[] createIntArray2 = parcel.createIntArray();
        int[] createIntArray3 = parcel.createIntArray();
        int[] createIntArray4 = parcel.createIntArray();
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, AudioAttributes.CREATOR);
        this.mFadeState = readInt;
        this.mFadeOutDurationMillis = readLong;
        this.mFadeInDurationMillis = readLong2;
        this.mFadeInDelayForOffendersMillis = readLong3;
        this.mUsageToFadeWrapperMap = createTypedSparseArray;
        this.mAttrToFadeWrapperMap = arrayMap;
        this.mFadeableUsages = IntArray.wrap(createIntArray);
        this.mUnfadeableContentTypes = IntArray.wrap(createIntArray2);
        this.mUnfadeablePlayerTypes = IntArray.wrap(createIntArray3);
        this.mUnfadeableUids = IntArray.wrap(createIntArray4);
        this.mUnfadeableAudioAttributes = arrayList;
    }

    private long getDurationForVolumeShaperConfig(VolumeShaper.Configuration configuration) {
        if (configuration != null) {
            return configuration.getDuration();
        }
        return 0L;
    }

    private VolumeShaper.Configuration getVolumeShaperConfigFromWrapper(FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper, boolean z) {
        if (fadeVolumeShaperConfigsWrapper == null) {
            return null;
        }
        if (z) {
            return fadeVolumeShaperConfigsWrapper.getFadeInVolShaperConfig();
        }
        return fadeVolumeShaperConfigsWrapper.getFadeOutVolShaperConfig();
    }

    private List<AudioAttributes> getAudioAttributesInternal() {
        ArrayList arrayList = new ArrayList(this.mAttrToFadeWrapperMap.size());
        for (int i = 0; i < this.mAttrToFadeWrapperMap.size(); i++) {
            arrayList.add(this.mAttrToFadeWrapperMap.keyAt(i));
        }
        return arrayList;
    }

    private static boolean isUsageValid(int i) {
        return AudioAttributes.isSdkUsage(i) || AudioAttributes.isSystemUsage(i) || AudioAttributes.isHiddenUsage(i);
    }

    private void ensureFadingIsEnabled() {
        if (!isFadeEnabled()) {
            throw new IllegalStateException("Method call not allowed when fade is disabled");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateUsage(int i) {
        Preconditions.checkArgument(isUsageValid(i), "Invalid usage: %s", Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IntArray convertIntegerListToIntArray(List<Integer> list) {
        if (list == null) {
            return new IntArray();
        }
        IntArray intArray = new IntArray(list.size());
        for (int i = 0; i < list.size(); i++) {
            intArray.add(list.get(i).intValue());
        }
        return intArray;
    }

    private static List<Integer> convertIntArrayToIntegerList(IntArray intArray) {
        if (intArray == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(intArray.size());
        for (int i = 0; i < intArray.size(); i++) {
            arrayList.add(Integer.valueOf(intArray.get(i)));
        }
        return arrayList;
    }

    public static final class Builder {
        private static final long DEFAULT_DELAY_FADE_IN_OFFENDERS_MS = 2000;
        private static final int INVALID_INDEX = -1;
        private static final long IS_BUILDER_USED_FIELD_SET = 1;
        private static final long IS_FADEABLE_USAGES_FIELD_SET = 2;
        private static final long IS_UNFADEABLE_CONTENT_TYPE_FIELD_SET = 4;
        private ArrayMap<AudioAttributes, FadeVolumeShaperConfigsWrapper> mAttrToFadeWrapperMap;
        private long mBuilderFieldsSet;
        private long mFadeInDelayForOffendersMillis;
        private long mFadeInDurationMillis;
        private long mFadeOutDurationMillis;
        private int mFadeState;
        private IntArray mFadeableUsages;
        private List<AudioAttributes> mUnfadeableAudioAttributes;
        private IntArray mUnfadeableContentTypes;
        private IntArray mUnfadeablePlayerTypes;
        private IntArray mUnfadeableUids;
        private SparseArray<FadeVolumeShaperConfigsWrapper> mUsageToFadeWrapperMap;
        private static final IntArray DEFAULT_UNFADEABLE_PLAYER_TYPES = IntArray.wrap(new int[]{13, 3});
        private static final IntArray DEFAULT_UNFADEABLE_CONTENT_TYPES = IntArray.wrap(new int[]{1});
        private static final IntArray DEFAULT_FADEABLE_USAGES = IntArray.wrap(new int[]{14, 1});

        public Builder() {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new SparseArray<>();
            this.mAttrToFadeWrapperMap = new ArrayMap<>();
            this.mFadeableUsages = new IntArray();
            this.mUnfadeableContentTypes = new IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new IntArray();
            this.mUnfadeableAudioAttributes = new ArrayList();
            this.mFadeOutDurationMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mFadeInDurationMillis = 1000L;
        }

        public Builder(long j, long j2) {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new SparseArray<>();
            this.mAttrToFadeWrapperMap = new ArrayMap<>();
            this.mFadeableUsages = new IntArray();
            this.mUnfadeableContentTypes = new IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new IntArray();
            this.mUnfadeableAudioAttributes = new ArrayList();
            this.mFadeOutDurationMillis = j;
            this.mFadeInDurationMillis = j2;
        }

        public Builder(FadeManagerConfiguration fadeManagerConfiguration) {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new SparseArray<>();
            this.mAttrToFadeWrapperMap = new ArrayMap<>();
            this.mFadeableUsages = new IntArray();
            this.mUnfadeableContentTypes = new IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new IntArray();
            this.mUnfadeableAudioAttributes = new ArrayList();
            this.mFadeState = fadeManagerConfiguration.mFadeState;
            copyUsageToFadeWrapperMapInternal(fadeManagerConfiguration.mUsageToFadeWrapperMap);
            this.mAttrToFadeWrapperMap = new ArrayMap<>(fadeManagerConfiguration.mAttrToFadeWrapperMap);
            this.mFadeableUsages = fadeManagerConfiguration.mFadeableUsages.m5502clone();
            setFlag(2L);
            this.mUnfadeableContentTypes = fadeManagerConfiguration.mUnfadeableContentTypes.m5502clone();
            setFlag(4L);
            this.mUnfadeablePlayerTypes = fadeManagerConfiguration.mUnfadeablePlayerTypes.m5502clone();
            this.mUnfadeableUids = fadeManagerConfiguration.mUnfadeableUids.m5502clone();
            this.mUnfadeableAudioAttributes = new ArrayList(fadeManagerConfiguration.mUnfadeableAudioAttributes);
            this.mFadeOutDurationMillis = fadeManagerConfiguration.mFadeOutDurationMillis;
            this.mFadeInDurationMillis = fadeManagerConfiguration.mFadeInDurationMillis;
        }

        public Builder setFadeState(int i) {
            validateFadeState(i);
            this.mFadeState = i;
            return this;
        }

        public Builder setFadeOutVolumeShaperConfigForUsage(int i, VolumeShaper.Configuration configuration) {
            FadeManagerConfiguration.validateUsage(i);
            getFadeVolShaperConfigWrapperForUsage(i).setFadeOutVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(i);
            return this;
        }

        public Builder setFadeInVolumeShaperConfigForUsage(int i, VolumeShaper.Configuration configuration) {
            FadeManagerConfiguration.validateUsage(i);
            getFadeVolShaperConfigWrapperForUsage(i).setFadeInVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(i);
            return this;
        }

        public Builder setFadeOutDurationForUsage(int i, long j) {
            FadeManagerConfiguration.validateUsage(i);
            setFadeOutVolumeShaperConfigForUsage(i, createVolShaperConfigForDuration(j, false));
            return this;
        }

        public Builder setFadeInDurationForUsage(int i, long j) {
            FadeManagerConfiguration.validateUsage(i);
            setFadeInVolumeShaperConfigForUsage(i, createVolShaperConfigForDuration(j, true));
            return this;
        }

        public Builder setFadeOutVolumeShaperConfigForAudioAttributes(AudioAttributes audioAttributes, VolumeShaper.Configuration configuration) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            getFadeVolShaperConfigWrapperForAttr(audioAttributes).setFadeOutVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(audioAttributes);
            return this;
        }

        public Builder setFadeInVolumeShaperConfigForAudioAttributes(AudioAttributes audioAttributes, VolumeShaper.Configuration configuration) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            getFadeVolShaperConfigWrapperForAttr(audioAttributes).setFadeInVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(audioAttributes);
            return this;
        }

        public Builder setFadeOutDurationForAudioAttributes(AudioAttributes audioAttributes, long j) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            setFadeOutVolumeShaperConfigForAudioAttributes(audioAttributes, createVolShaperConfigForDuration(j, false));
            return this;
        }

        public Builder setFadeInDurationForAudioAttributes(AudioAttributes audioAttributes, long j) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            setFadeInVolumeShaperConfigForAudioAttributes(audioAttributes, createVolShaperConfigForDuration(j, true));
            return this;
        }

        public Builder setFadeableUsages(List<Integer> list) {
            Objects.requireNonNull(list, "List of usages cannot be null");
            validateUsages(list);
            setFlag(2L);
            this.mFadeableUsages.clear();
            this.mFadeableUsages.addAll(FadeManagerConfiguration.convertIntegerListToIntArray(list));
            return this;
        }

        public Builder addFadeableUsage(int i) {
            FadeManagerConfiguration.validateUsage(i);
            setFlag(2L);
            if (!this.mFadeableUsages.contains(i)) {
                this.mFadeableUsages.add(i);
            }
            return this;
        }

        public Builder clearFadeableUsages() {
            setFlag(2L);
            this.mFadeableUsages.clear();
            return this;
        }

        public Builder setUnfadeableContentTypes(List<Integer> list) {
            Objects.requireNonNull(list, "List of content types cannot be null");
            validateContentTypes(list);
            setFlag(4L);
            this.mUnfadeableContentTypes.clear();
            this.mUnfadeableContentTypes.addAll(FadeManagerConfiguration.convertIntegerListToIntArray(list));
            return this;
        }

        public Builder addUnfadeableContentType(int i) {
            validateContentType(i);
            setFlag(4L);
            if (!this.mUnfadeableContentTypes.contains(i)) {
                this.mUnfadeableContentTypes.add(i);
            }
            return this;
        }

        public Builder clearUnfadeableContentTypes() {
            setFlag(4L);
            this.mUnfadeableContentTypes.clear();
            return this;
        }

        public Builder setUnfadeableUids(List<Integer> list) {
            Objects.requireNonNull(list, "List of uids cannot be null");
            this.mUnfadeableUids.clear();
            this.mUnfadeableUids.addAll(FadeManagerConfiguration.convertIntegerListToIntArray(list));
            return this;
        }

        public Builder addUnfadeableUid(int i) {
            if (!this.mUnfadeableUids.contains(i)) {
                this.mUnfadeableUids.add(i);
            }
            return this;
        }

        public Builder clearUnfadeableUids() {
            this.mUnfadeableUids.clear();
            return this;
        }

        public Builder setUnfadeableAudioAttributes(List<AudioAttributes> list) {
            Objects.requireNonNull(list, "List of audio attributes cannot be null");
            this.mUnfadeableAudioAttributes.clear();
            this.mUnfadeableAudioAttributes.addAll(list);
            return this;
        }

        public Builder addUnfadeableAudioAttributes(AudioAttributes audioAttributes) {
            Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
            if (!this.mUnfadeableAudioAttributes.contains(audioAttributes)) {
                this.mUnfadeableAudioAttributes.add(audioAttributes);
            }
            return this;
        }

        public Builder clearUnfadeableAudioAttributes() {
            this.mUnfadeableAudioAttributes.clear();
            return this;
        }

        public Builder setFadeInDelayForOffenders(long j) {
            Preconditions.checkArgument(j >= 0, "Delay cannot be negative");
            this.mFadeInDelayForOffendersMillis = j;
            return this;
        }

        public FadeManagerConfiguration build() {
            if (!checkNotSet(1L)) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
            setFlag(1L);
            if (checkNotSet(2L)) {
                IntArray intArray = DEFAULT_FADEABLE_USAGES;
                this.mFadeableUsages = intArray;
                setVolShaperConfigsForUsages(intArray);
            }
            if (checkNotSet(4L)) {
                this.mUnfadeableContentTypes = DEFAULT_UNFADEABLE_CONTENT_TYPES;
            }
            validateFadeConfigurations();
            return new FadeManagerConfiguration(this.mFadeState, this.mFadeOutDurationMillis, this.mFadeInDurationMillis, this.mFadeInDelayForOffendersMillis, this.mUsageToFadeWrapperMap, this.mAttrToFadeWrapperMap, this.mFadeableUsages, this.mUnfadeableContentTypes, this.mUnfadeablePlayerTypes, this.mUnfadeableUids, this.mUnfadeableAudioAttributes);
        }

        private void setFlag(long j) {
            this.mBuilderFieldsSet = j | this.mBuilderFieldsSet;
        }

        private boolean checkNotSet(long j) {
            return (this.mBuilderFieldsSet & j) == 0;
        }

        private FadeVolumeShaperConfigsWrapper getFadeVolShaperConfigWrapperForUsage(int i) {
            if (!this.mUsageToFadeWrapperMap.contains(i)) {
                this.mUsageToFadeWrapperMap.put(i, new FadeVolumeShaperConfigsWrapper());
            }
            return this.mUsageToFadeWrapperMap.get(i);
        }

        private FadeVolumeShaperConfigsWrapper getFadeVolShaperConfigWrapperForAttr(AudioAttributes audioAttributes) {
            if (!this.mAttrToFadeWrapperMap.containsKey(audioAttributes)) {
                this.mAttrToFadeWrapperMap.put(audioAttributes, new FadeVolumeShaperConfigsWrapper());
            }
            return this.mAttrToFadeWrapperMap.get(audioAttributes);
        }

        private VolumeShaper.Configuration createVolShaperConfigForDuration(long j, boolean z) {
            if (j == 0) {
                return null;
            }
            VolumeShaper.Configuration.Builder duration = new VolumeShaper.Configuration.Builder().setId(2).setOptionFlags(2).setDuration(j);
            if (z) {
                duration.setCurve(new float[]{0.0f, 0.5f, 1.0f}, new float[]{0.0f, 0.3f, 1.0f});
            } else {
                duration.setCurve(new float[]{0.0f, 0.25f, 1.0f}, new float[]{1.0f, 0.65f, 0.0f});
            }
            return duration.build();
        }

        private void cleanupInactiveWrapperEntries(int i) {
            FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper = this.mUsageToFadeWrapperMap.get(i);
            if (fadeVolumeShaperConfigsWrapper == null || !fadeVolumeShaperConfigsWrapper.isInactive()) {
                return;
            }
            this.mUsageToFadeWrapperMap.remove(i);
        }

        private void cleanupInactiveWrapperEntries(AudioAttributes audioAttributes) {
            FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper = this.mAttrToFadeWrapperMap.get(audioAttributes);
            if (fadeVolumeShaperConfigsWrapper == null || !fadeVolumeShaperConfigsWrapper.isInactive()) {
                return;
            }
            this.mAttrToFadeWrapperMap.remove(audioAttributes);
        }

        private void setVolShaperConfigsForUsages(IntArray intArray) {
            for (int i = 0; i < intArray.size(); i++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForUsage(intArray.get(i)));
            }
        }

        private void setMissingVolShaperConfigsForWrapper(FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper) {
            if (!fadeVolumeShaperConfigsWrapper.isFadeOutConfigActive()) {
                fadeVolumeShaperConfigsWrapper.setFadeOutVolShaperConfig(createVolShaperConfigForDuration(this.mFadeOutDurationMillis, false));
            }
            if (fadeVolumeShaperConfigsWrapper.isFadeInConfigActive()) {
                return;
            }
            fadeVolumeShaperConfigsWrapper.setFadeInVolShaperConfig(createVolShaperConfigForDuration(this.mFadeInDurationMillis, true));
        }

        private void copyUsageToFadeWrapperMapInternal(SparseArray<FadeVolumeShaperConfigsWrapper> sparseArray) {
            for (int i = 0; i < sparseArray.size(); i++) {
                this.mUsageToFadeWrapperMap.put(sparseArray.keyAt(i), new FadeVolumeShaperConfigsWrapper(sparseArray.valueAt(i)));
            }
        }

        private void validateFadeState(int i) {
            if (i == 0 || i == 1) {
                return;
            }
            throw new IllegalArgumentException("Unknown fade state: " + i);
        }

        private void validateUsages(List<Integer> list) {
            for (int i = 0; i < list.size(); i++) {
                FadeManagerConfiguration.validateUsage(list.get(i).intValue());
            }
        }

        private void validateContentTypes(List<Integer> list) {
            for (int i = 0; i < list.size(); i++) {
                validateContentType(list.get(i).intValue());
            }
        }

        private void validateContentType(int i) {
            Preconditions.checkArgument(AudioAttributes.isSdkContentType(i), "Invalid content type: ", Integer.valueOf(i));
        }

        private void validateFadeConfigurations() {
            validateFadeableUsages();
            validateFadeVolumeShaperConfigsWrappers();
            validateUnfadeableAudioAttributes();
        }

        private void validateFadeableUsages() {
            Preconditions.checkArgumentPositive(this.mFadeableUsages.size(), "Fadeable usage list cannot be empty when state set to enabled");
            for (int i = 0; i < this.mFadeableUsages.size(); i++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForUsage(this.mFadeableUsages.get(i)));
            }
        }

        private void validateFadeVolumeShaperConfigsWrappers() {
            for (int i = 0; i < this.mUsageToFadeWrapperMap.size(); i++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForUsage(this.mUsageToFadeWrapperMap.keyAt(i)));
            }
            for (int i2 = 0; i2 < this.mAttrToFadeWrapperMap.size(); i2++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForAttr(this.mAttrToFadeWrapperMap.keyAt(i2)));
            }
        }

        private void validateUnfadeableAudioAttributes() {
            for (int i = 0; i < this.mUnfadeableAudioAttributes.size(); i++) {
                AudioAttributes audioAttributes = this.mUnfadeableAudioAttributes.get(i);
                boolean contains = this.mFadeableUsages.contains(audioAttributes.getSystemUsage());
                Preconditions.checkArgument(!contains || (contains && !isGeneric(audioAttributes)), "Unfadeable audio attributes cannot be generic of the fadeable usage");
            }
        }

        private static boolean isGeneric(AudioAttributes audioAttributes) {
            return audioAttributes.getContentType() == 0 && audioAttributes.getFlags() == 0 && audioAttributes.getBundle() == null && audioAttributes.getTags().isEmpty();
        }
    }

    private static final class FadeVolumeShaperConfigsWrapper implements Parcelable {
        public static final Parcelable.Creator<FadeVolumeShaperConfigsWrapper> CREATOR = new Parcelable.Creator<FadeVolumeShaperConfigsWrapper>() { // from class: android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FadeVolumeShaperConfigsWrapper createFromParcel(Parcel parcel) {
                return new FadeVolumeShaperConfigsWrapper(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FadeVolumeShaperConfigsWrapper[] newArray(int i) {
                return new FadeVolumeShaperConfigsWrapper[i];
            }
        };
        private VolumeShaper.Configuration mFadeInVolShaperConfig;
        private VolumeShaper.Configuration mFadeOutVolShaperConfig;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        FadeVolumeShaperConfigsWrapper() {
        }

        FadeVolumeShaperConfigsWrapper(FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper) {
            Objects.requireNonNull(fadeVolumeShaperConfigsWrapper, "Fade volume shaper configs wrapper cannot be null");
            this.mFadeOutVolShaperConfig = fadeVolumeShaperConfigsWrapper.mFadeOutVolShaperConfig;
            this.mFadeInVolShaperConfig = fadeVolumeShaperConfigsWrapper.mFadeInVolShaperConfig;
        }

        public void setFadeOutVolShaperConfig(VolumeShaper.Configuration configuration) {
            this.mFadeOutVolShaperConfig = configuration;
        }

        public void setFadeInVolShaperConfig(VolumeShaper.Configuration configuration) {
            this.mFadeInVolShaperConfig = configuration;
        }

        public VolumeShaper.Configuration getFadeOutVolShaperConfig() {
            return this.mFadeOutVolShaperConfig;
        }

        public VolumeShaper.Configuration getFadeInVolShaperConfig() {
            return this.mFadeInVolShaperConfig;
        }

        public boolean isInactive() {
            return (isFadeOutConfigActive() || isFadeInConfigActive()) ? false : true;
        }

        boolean isFadeOutConfigActive() {
            return this.mFadeOutVolShaperConfig != null;
        }

        boolean isFadeInConfigActive() {
            return this.mFadeInVolShaperConfig != null;
        }

        public boolean equals(Object obj) {
            boolean z;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FadeVolumeShaperConfigsWrapper)) {
                return false;
            }
            FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper = (FadeVolumeShaperConfigsWrapper) obj;
            if (this.mFadeInVolShaperConfig == null && fadeVolumeShaperConfigsWrapper.mFadeInVolShaperConfig == null && this.mFadeOutVolShaperConfig == null && fadeVolumeShaperConfigsWrapper.mFadeOutVolShaperConfig == null) {
                return true;
            }
            VolumeShaper.Configuration configuration = this.mFadeOutVolShaperConfig;
            if (configuration != null) {
                z = configuration.equals(fadeVolumeShaperConfigsWrapper.mFadeOutVolShaperConfig);
            } else {
                if (fadeVolumeShaperConfigsWrapper.mFadeOutVolShaperConfig != null) {
                    return false;
                }
                z = true;
            }
            VolumeShaper.Configuration configuration2 = this.mFadeInVolShaperConfig;
            if (configuration2 != null) {
                return z && configuration2.equals(fadeVolumeShaperConfigsWrapper.mFadeInVolShaperConfig);
            }
            if (fadeVolumeShaperConfigsWrapper.mFadeInVolShaperConfig != null) {
                return false;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hash(this.mFadeOutVolShaperConfig, this.mFadeInVolShaperConfig);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mFadeOutVolShaperConfig.writeToParcel(parcel, i);
            this.mFadeInVolShaperConfig.writeToParcel(parcel, i);
        }

        FadeVolumeShaperConfigsWrapper(Parcel parcel) {
            this.mFadeOutVolShaperConfig = VolumeShaper.Configuration.CREATOR.createFromParcel(parcel);
            this.mFadeInVolShaperConfig = VolumeShaper.Configuration.CREATOR.createFromParcel(parcel);
        }
    }
}
