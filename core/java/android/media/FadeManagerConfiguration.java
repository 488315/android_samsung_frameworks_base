package android.media;

import android.annotation.SystemApi;
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
public final class FadeManagerConfiguration implements Parcelable {
    public static final Parcelable.Creator<FadeManagerConfiguration> CREATOR =
            new Parcelable.Creator<FadeManagerConfiguration>() { // from class:
                // android.media.FadeManagerConfiguration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FadeManagerConfiguration createFromParcel(Parcel in) {
                    return new FadeManagerConfiguration(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FadeManagerConfiguration[] newArray(int size) {
                    return new FadeManagerConfiguration[size];
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
    public @interface FadeStateEnum {}

    private FadeManagerConfiguration(
            int fadeState,
            long fadeOutDurationMillis,
            long fadeInDurationMillis,
            long offendersFadeInDelayMillis,
            SparseArray<FadeVolumeShaperConfigsWrapper> usageToFadeWrapperMap,
            ArrayMap<AudioAttributes, FadeVolumeShaperConfigsWrapper> attrToFadeWrapperMap,
            IntArray fadeableUsages,
            IntArray unfadeableContentTypes,
            IntArray unfadeablePlayerTypes,
            IntArray unfadeableUids,
            List<AudioAttributes> unfadeableAudioAttributes) {
        this.mFadeState = fadeState;
        this.mFadeOutDurationMillis = fadeOutDurationMillis;
        this.mFadeInDurationMillis = fadeInDurationMillis;
        this.mFadeInDelayForOffendersMillis = offendersFadeInDelayMillis;
        this.mUsageToFadeWrapperMap =
                (SparseArray)
                        Objects.requireNonNull(
                                usageToFadeWrapperMap, "Usage to fade wrapper map cannot be null");
        this.mAttrToFadeWrapperMap =
                (ArrayMap)
                        Objects.requireNonNull(
                                attrToFadeWrapperMap,
                                "Attribute to fade wrapper map cannot be null");
        this.mFadeableUsages =
                (IntArray)
                        Objects.requireNonNull(
                                fadeableUsages, "List of fadeable usages cannot be null");
        this.mUnfadeableContentTypes =
                (IntArray)
                        Objects.requireNonNull(
                                unfadeableContentTypes,
                                "List of unfadeable content types cannot be null");
        this.mUnfadeablePlayerTypes =
                (IntArray)
                        Objects.requireNonNull(
                                unfadeablePlayerTypes,
                                "List of unfadeable player types cannot be null");
        this.mUnfadeableUids =
                (IntArray)
                        Objects.requireNonNull(
                                unfadeableUids, "List of unfadeable uids cannot be null");
        this.mUnfadeableAudioAttributes =
                (List)
                        Objects.requireNonNull(
                                unfadeableAudioAttributes,
                                "List of unfadeable audio attributes cannot be null");
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

    public long getFadeOutDurationForUsage(int usage) {
        ensureFadingIsEnabled();
        validateUsage(usage);
        return getDurationForVolumeShaperConfig(
                getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(usage), false));
    }

    public long getFadeInDurationForUsage(int usage) {
        ensureFadingIsEnabled();
        validateUsage(usage);
        return getDurationForVolumeShaperConfig(
                getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(usage), true));
    }

    public VolumeShaper.Configuration getFadeOutVolumeShaperConfigForUsage(int usage) {
        ensureFadingIsEnabled();
        validateUsage(usage);
        return getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(usage), false);
    }

    public VolumeShaper.Configuration getFadeInVolumeShaperConfigForUsage(int usage) {
        ensureFadingIsEnabled();
        validateUsage(usage);
        return getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(usage), true);
    }

    public long getFadeOutDurationForAudioAttributes(AudioAttributes audioAttributes) {
        ensureFadingIsEnabled();
        return getDurationForVolumeShaperConfig(
                getVolumeShaperConfigFromWrapper(
                        this.mAttrToFadeWrapperMap.get(audioAttributes), false));
    }

    public long getFadeInDurationForAudioAttributes(AudioAttributes audioAttributes) {
        ensureFadingIsEnabled();
        return getDurationForVolumeShaperConfig(
                getVolumeShaperConfigFromWrapper(
                        this.mAttrToFadeWrapperMap.get(audioAttributes), true));
    }

    public VolumeShaper.Configuration getFadeOutVolumeShaperConfigForAudioAttributes(
            AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        ensureFadingIsEnabled();
        return getVolumeShaperConfigFromWrapper(
                this.mAttrToFadeWrapperMap.get(audioAttributes), false);
    }

    public VolumeShaper.Configuration getFadeInVolumeShaperConfigForAudioAttributes(
            AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        ensureFadingIsEnabled();
        return getVolumeShaperConfigFromWrapper(
                this.mAttrToFadeWrapperMap.get(audioAttributes), true);
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

    public boolean isUsageFadeable(int usage) {
        if (!isFadeEnabled()) {
            return false;
        }
        return this.mFadeableUsages.contains(usage);
    }

    public boolean isContentTypeUnfadeable(int contentType) {
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeableContentTypes.contains(contentType);
    }

    public boolean isPlayerTypeUnfadeable(int playerType) {
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeablePlayerTypes.contains(playerType);
    }

    public boolean isAudioAttributesUnfadeable(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeableAudioAttributes.contains(audioAttributes);
    }

    public boolean isUidUnfadeable(int uid) {
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeableUids.contains(uid);
    }

    public static long getDefaultFadeOutDurationMillis() {
        return DEFAULT_FADE_OUT_DURATION_MS;
    }

    public static long getDefaultFadeInDurationMillis() {
        return 1000L;
    }

    public String toString() {
        return "FadeManagerConfiguration { fade state = "
                + fadeStateToString(this.mFadeState)
                + ", fade out duration = "
                + this.mFadeOutDurationMillis
                + ", fade in duration = "
                + this.mFadeInDurationMillis
                + ", offenders fade in delay = "
                + this.mFadeInDelayForOffendersMillis
                + ", fade volume shapers for audio attributes = "
                + this.mAttrToFadeWrapperMap
                + ", fadeable usages = "
                + this.mFadeableUsages.toString()
                + ", unfadeable content types = "
                + this.mUnfadeableContentTypes.toString()
                + ", unfadeable player types = "
                + this.mUnfadeablePlayerTypes.toString()
                + ", unfadeable uids = "
                + this.mUnfadeableUids.toString()
                + ", unfadeable audio attributes = "
                + this.mUnfadeableAudioAttributes
                + "}";
    }

    public static String fadeStateToString(int fadeState) {
        switch (fadeState) {
            case 0:
                return "FADE_STATE_DISABLED";
            case 1:
                return "FADE_STATE_ENABLED_DEFAULT";
            default:
                return "unknown fade state: " + fadeState;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FadeManagerConfiguration)) {
            return false;
        }
        FadeManagerConfiguration rhs = (FadeManagerConfiguration) o;
        return this.mUsageToFadeWrapperMap.contentEquals(rhs.mUsageToFadeWrapperMap)
                && this.mAttrToFadeWrapperMap.equals(rhs.mAttrToFadeWrapperMap)
                && Arrays.equals(this.mFadeableUsages.toArray(), rhs.mFadeableUsages.toArray())
                && Arrays.equals(
                        this.mUnfadeableContentTypes.toArray(),
                        rhs.mUnfadeableContentTypes.toArray())
                && Arrays.equals(
                        this.mUnfadeablePlayerTypes.toArray(), rhs.mUnfadeablePlayerTypes.toArray())
                && Arrays.equals(this.mUnfadeableUids.toArray(), rhs.mUnfadeableUids.toArray())
                && this.mUnfadeableAudioAttributes.equals(rhs.mUnfadeableAudioAttributes)
                && this.mFadeState == rhs.mFadeState
                && this.mFadeOutDurationMillis == rhs.mFadeOutDurationMillis
                && this.mFadeInDurationMillis == rhs.mFadeInDurationMillis
                && this.mFadeInDelayForOffendersMillis == rhs.mFadeInDelayForOffendersMillis;
    }

    public int hashCode() {
        return Objects.hash(
                this.mUsageToFadeWrapperMap,
                this.mAttrToFadeWrapperMap,
                this.mFadeableUsages,
                this.mUnfadeableContentTypes,
                this.mUnfadeablePlayerTypes,
                this.mUnfadeableAudioAttributes,
                this.mUnfadeableUids,
                Integer.valueOf(this.mFadeState),
                Long.valueOf(this.mFadeOutDurationMillis),
                Long.valueOf(this.mFadeInDurationMillis),
                Long.valueOf(this.mFadeInDelayForOffendersMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mFadeState);
        dest.writeLong(this.mFadeOutDurationMillis);
        dest.writeLong(this.mFadeInDurationMillis);
        dest.writeLong(this.mFadeInDelayForOffendersMillis);
        dest.writeTypedSparseArray(this.mUsageToFadeWrapperMap, flags);
        dest.writeMap(this.mAttrToFadeWrapperMap);
        dest.writeIntArray(this.mFadeableUsages.toArray());
        dest.writeIntArray(this.mUnfadeableContentTypes.toArray());
        dest.writeIntArray(this.mUnfadeablePlayerTypes.toArray());
        dest.writeIntArray(this.mUnfadeableUids.toArray());
        dest.writeTypedList(this.mUnfadeableAudioAttributes, flags);
    }

    FadeManagerConfiguration(Parcel in) {
        int fadeState = in.readInt();
        long fadeOutDurationMillis = in.readLong();
        long fadeInDurationMillis = in.readLong();
        long fadeInDelayForOffenders = in.readLong();
        SparseArray<FadeVolumeShaperConfigsWrapper> usageToWrapperMap =
                in.createTypedSparseArray(FadeVolumeShaperConfigsWrapper.CREATOR);
        ArrayMap<AudioAttributes, FadeVolumeShaperConfigsWrapper> attrToFadeWrapperMap =
                new ArrayMap<>();
        in.readMap(
                attrToFadeWrapperMap,
                getClass().getClassLoader(),
                AudioAttributes.class,
                FadeVolumeShaperConfigsWrapper.class);
        int[] fadeableUsages = in.createIntArray();
        int[] unfadeableContentTypes = in.createIntArray();
        int[] unfadeablePlayerTypes = in.createIntArray();
        int[] unfadeableUids = in.createIntArray();
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, AudioAttributes.CREATOR);
        this.mFadeState = fadeState;
        this.mFadeOutDurationMillis = fadeOutDurationMillis;
        this.mFadeInDurationMillis = fadeInDurationMillis;
        this.mFadeInDelayForOffendersMillis = fadeInDelayForOffenders;
        this.mUsageToFadeWrapperMap = usageToWrapperMap;
        this.mAttrToFadeWrapperMap = attrToFadeWrapperMap;
        this.mFadeableUsages = IntArray.wrap(fadeableUsages);
        this.mUnfadeableContentTypes = IntArray.wrap(unfadeableContentTypes);
        this.mUnfadeablePlayerTypes = IntArray.wrap(unfadeablePlayerTypes);
        this.mUnfadeableUids = IntArray.wrap(unfadeableUids);
        this.mUnfadeableAudioAttributes = arrayList;
    }

    private long getDurationForVolumeShaperConfig(VolumeShaper.Configuration config) {
        if (config != null) {
            return config.getDuration();
        }
        return 0L;
    }

    private VolumeShaper.Configuration getVolumeShaperConfigFromWrapper(
            FadeVolumeShaperConfigsWrapper wrapper, boolean isFadeIn) {
        if (wrapper == null) {
            return null;
        }
        if (isFadeIn) {
            return wrapper.getFadeInVolShaperConfig();
        }
        return wrapper.getFadeOutVolShaperConfig();
    }

    private List<AudioAttributes> getAudioAttributesInternal() {
        List<AudioAttributes> attrs = new ArrayList<>(this.mAttrToFadeWrapperMap.size());
        for (int index = 0; index < this.mAttrToFadeWrapperMap.size(); index++) {
            attrs.add(this.mAttrToFadeWrapperMap.keyAt(index));
        }
        return attrs;
    }

    private static boolean isUsageValid(int usage) {
        return AudioAttributes.isSdkUsage(usage)
                || AudioAttributes.isSystemUsage(usage)
                || AudioAttributes.isHiddenUsage(usage);
    }

    private void ensureFadingIsEnabled() {
        if (!isFadeEnabled()) {
            throw new IllegalStateException("Method call not allowed when fade is disabled");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateUsage(int usage) {
        Preconditions.checkArgument(
                isUsageValid(usage), "Invalid usage: %s", Integer.valueOf(usage));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IntArray convertIntegerListToIntArray(List<Integer> integerList) {
        if (integerList == null) {
            return new IntArray();
        }
        IntArray intArray = new IntArray(integerList.size());
        for (int index = 0; index < integerList.size(); index++) {
            intArray.add(integerList.get(index).intValue());
        }
        return intArray;
    }

    private static List<Integer> convertIntArrayToIntegerList(IntArray intArray) {
        if (intArray == null) {
            return new ArrayList();
        }
        ArrayList<Integer> integerArrayList = new ArrayList<>(intArray.size());
        for (int index = 0; index < intArray.size(); index++) {
            integerArrayList.add(Integer.valueOf(intArray.get(index)));
        }
        return integerArrayList;
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
        private static final IntArray DEFAULT_UNFADEABLE_PLAYER_TYPES =
                IntArray.wrap(new int[] {13, 3});
        private static final IntArray DEFAULT_UNFADEABLE_CONTENT_TYPES =
                IntArray.wrap(new int[] {1});
        private static final IntArray DEFAULT_FADEABLE_USAGES = IntArray.wrap(new int[] {14, 1});

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

        public Builder(long fadeOutDurationMillis, long fadeInDurationMills) {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new SparseArray<>();
            this.mAttrToFadeWrapperMap = new ArrayMap<>();
            this.mFadeableUsages = new IntArray();
            this.mUnfadeableContentTypes = new IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new IntArray();
            this.mUnfadeableAudioAttributes = new ArrayList();
            this.mFadeOutDurationMillis = fadeOutDurationMillis;
            this.mFadeInDurationMillis = fadeInDurationMills;
        }

        public Builder(FadeManagerConfiguration fmc) {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new SparseArray<>();
            this.mAttrToFadeWrapperMap = new ArrayMap<>();
            this.mFadeableUsages = new IntArray();
            this.mUnfadeableContentTypes = new IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new IntArray();
            this.mUnfadeableAudioAttributes = new ArrayList();
            this.mFadeState = fmc.mFadeState;
            copyUsageToFadeWrapperMapInternal(fmc.mUsageToFadeWrapperMap);
            this.mAttrToFadeWrapperMap = new ArrayMap<>(fmc.mAttrToFadeWrapperMap);
            this.mFadeableUsages = fmc.mFadeableUsages.m5208clone();
            setFlag(2L);
            this.mUnfadeableContentTypes = fmc.mUnfadeableContentTypes.m5208clone();
            setFlag(4L);
            this.mUnfadeablePlayerTypes = fmc.mUnfadeablePlayerTypes.m5208clone();
            this.mUnfadeableUids = fmc.mUnfadeableUids.m5208clone();
            this.mUnfadeableAudioAttributes = new ArrayList(fmc.mUnfadeableAudioAttributes);
            this.mFadeOutDurationMillis = fmc.mFadeOutDurationMillis;
            this.mFadeInDurationMillis = fmc.mFadeInDurationMillis;
        }

        public Builder setFadeState(int state) {
            validateFadeState(state);
            this.mFadeState = state;
            return this;
        }

        public Builder setFadeOutVolumeShaperConfigForUsage(
                int usage, VolumeShaper.Configuration fadeOutVShaperConfig) {
            FadeManagerConfiguration.validateUsage(usage);
            getFadeVolShaperConfigWrapperForUsage(usage)
                    .setFadeOutVolShaperConfig(fadeOutVShaperConfig);
            cleanupInactiveWrapperEntries(usage);
            return this;
        }

        public Builder setFadeInVolumeShaperConfigForUsage(
                int usage, VolumeShaper.Configuration fadeInVShaperConfig) {
            FadeManagerConfiguration.validateUsage(usage);
            getFadeVolShaperConfigWrapperForUsage(usage)
                    .setFadeInVolShaperConfig(fadeInVShaperConfig);
            cleanupInactiveWrapperEntries(usage);
            return this;
        }

        public Builder setFadeOutDurationForUsage(int usage, long fadeOutDurationMillis) {
            FadeManagerConfiguration.validateUsage(usage);
            VolumeShaper.Configuration fadeOutVShaperConfig =
                    createVolShaperConfigForDuration(fadeOutDurationMillis, false);
            setFadeOutVolumeShaperConfigForUsage(usage, fadeOutVShaperConfig);
            return this;
        }

        public Builder setFadeInDurationForUsage(int usage, long fadeInDurationMillis) {
            FadeManagerConfiguration.validateUsage(usage);
            VolumeShaper.Configuration fadeInVShaperConfig =
                    createVolShaperConfigForDuration(fadeInDurationMillis, true);
            setFadeInVolumeShaperConfigForUsage(usage, fadeInVShaperConfig);
            return this;
        }

        public Builder setFadeOutVolumeShaperConfigForAudioAttributes(
                AudioAttributes audioAttributes, VolumeShaper.Configuration fadeOutVShaperConfig) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            getFadeVolShaperConfigWrapperForAttr(audioAttributes)
                    .setFadeOutVolShaperConfig(fadeOutVShaperConfig);
            cleanupInactiveWrapperEntries(audioAttributes);
            return this;
        }

        public Builder setFadeInVolumeShaperConfigForAudioAttributes(
                AudioAttributes audioAttributes, VolumeShaper.Configuration fadeInVShaperConfig) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            getFadeVolShaperConfigWrapperForAttr(audioAttributes)
                    .setFadeInVolShaperConfig(fadeInVShaperConfig);
            cleanupInactiveWrapperEntries(audioAttributes);
            return this;
        }

        public Builder setFadeOutDurationForAudioAttributes(
                AudioAttributes audioAttributes, long fadeOutDurationMillis) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            VolumeShaper.Configuration fadeOutVShaperConfig =
                    createVolShaperConfigForDuration(fadeOutDurationMillis, false);
            setFadeOutVolumeShaperConfigForAudioAttributes(audioAttributes, fadeOutVShaperConfig);
            return this;
        }

        public Builder setFadeInDurationForAudioAttributes(
                AudioAttributes audioAttributes, long fadeInDurationMillis) {
            Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            VolumeShaper.Configuration fadeInVShaperConfig =
                    createVolShaperConfigForDuration(fadeInDurationMillis, true);
            setFadeInVolumeShaperConfigForAudioAttributes(audioAttributes, fadeInVShaperConfig);
            return this;
        }

        public Builder setFadeableUsages(List<Integer> usages) {
            Objects.requireNonNull(usages, "List of usages cannot be null");
            validateUsages(usages);
            setFlag(2L);
            this.mFadeableUsages.clear();
            this.mFadeableUsages.addAll(
                    FadeManagerConfiguration.convertIntegerListToIntArray(usages));
            return this;
        }

        public Builder addFadeableUsage(int usage) {
            FadeManagerConfiguration.validateUsage(usage);
            setFlag(2L);
            if (!this.mFadeableUsages.contains(usage)) {
                this.mFadeableUsages.add(usage);
            }
            return this;
        }

        public Builder clearFadeableUsages() {
            setFlag(2L);
            this.mFadeableUsages.clear();
            return this;
        }

        public Builder setUnfadeableContentTypes(List<Integer> contentTypes) {
            Objects.requireNonNull(contentTypes, "List of content types cannot be null");
            validateContentTypes(contentTypes);
            setFlag(4L);
            this.mUnfadeableContentTypes.clear();
            this.mUnfadeableContentTypes.addAll(
                    FadeManagerConfiguration.convertIntegerListToIntArray(contentTypes));
            return this;
        }

        public Builder addUnfadeableContentType(int contentType) {
            validateContentType(contentType);
            setFlag(4L);
            if (!this.mUnfadeableContentTypes.contains(contentType)) {
                this.mUnfadeableContentTypes.add(contentType);
            }
            return this;
        }

        public Builder clearUnfadeableContentTypes() {
            setFlag(4L);
            this.mUnfadeableContentTypes.clear();
            return this;
        }

        public Builder setUnfadeableUids(List<Integer> uids) {
            Objects.requireNonNull(uids, "List of uids cannot be null");
            this.mUnfadeableUids.clear();
            this.mUnfadeableUids.addAll(
                    FadeManagerConfiguration.convertIntegerListToIntArray(uids));
            return this;
        }

        public Builder addUnfadeableUid(int uid) {
            if (!this.mUnfadeableUids.contains(uid)) {
                this.mUnfadeableUids.add(uid);
            }
            return this;
        }

        public Builder clearUnfadeableUids() {
            this.mUnfadeableUids.clear();
            return this;
        }

        public Builder setUnfadeableAudioAttributes(List<AudioAttributes> attrs) {
            Objects.requireNonNull(attrs, "List of audio attributes cannot be null");
            this.mUnfadeableAudioAttributes.clear();
            this.mUnfadeableAudioAttributes.addAll(attrs);
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

        public Builder setFadeInDelayForOffenders(long delayMillis) {
            Preconditions.checkArgument(delayMillis >= 0, "Delay cannot be negative");
            this.mFadeInDelayForOffendersMillis = delayMillis;
            return this;
        }

        public FadeManagerConfiguration build() {
            if (!checkNotSet(1L)) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
            setFlag(1L);
            if (checkNotSet(2L)) {
                this.mFadeableUsages = DEFAULT_FADEABLE_USAGES;
                setVolShaperConfigsForUsages(this.mFadeableUsages);
            }
            if (checkNotSet(4L)) {
                this.mUnfadeableContentTypes = DEFAULT_UNFADEABLE_CONTENT_TYPES;
            }
            validateFadeConfigurations();
            return new FadeManagerConfiguration(
                    this.mFadeState,
                    this.mFadeOutDurationMillis,
                    this.mFadeInDurationMillis,
                    this.mFadeInDelayForOffendersMillis,
                    this.mUsageToFadeWrapperMap,
                    this.mAttrToFadeWrapperMap,
                    this.mFadeableUsages,
                    this.mUnfadeableContentTypes,
                    this.mUnfadeablePlayerTypes,
                    this.mUnfadeableUids,
                    this.mUnfadeableAudioAttributes);
        }

        private void setFlag(long flag) {
            this.mBuilderFieldsSet |= flag;
        }

        private boolean checkNotSet(long flag) {
            return (this.mBuilderFieldsSet & flag) == 0;
        }

        private FadeVolumeShaperConfigsWrapper getFadeVolShaperConfigWrapperForUsage(int usage) {
            if (!this.mUsageToFadeWrapperMap.contains(usage)) {
                this.mUsageToFadeWrapperMap.put(usage, new FadeVolumeShaperConfigsWrapper());
            }
            return this.mUsageToFadeWrapperMap.get(usage);
        }

        private FadeVolumeShaperConfigsWrapper getFadeVolShaperConfigWrapperForAttr(
                AudioAttributes attr) {
            if (!this.mAttrToFadeWrapperMap.containsKey(attr)) {
                this.mAttrToFadeWrapperMap.put(attr, new FadeVolumeShaperConfigsWrapper());
            }
            return this.mAttrToFadeWrapperMap.get(attr);
        }

        private VolumeShaper.Configuration createVolShaperConfigForDuration(
                long duration, boolean isFadeIn) {
            if (duration == 0) {
                return null;
            }
            VolumeShaper.Configuration.Builder builder =
                    new VolumeShaper.Configuration.Builder()
                            .setId(2)
                            .setOptionFlags(2)
                            .setDuration(duration);
            if (isFadeIn) {
                builder.setCurve(new float[] {0.0f, 0.5f, 1.0f}, new float[] {0.0f, 0.3f, 1.0f});
            } else {
                builder.setCurve(new float[] {0.0f, 0.25f, 1.0f}, new float[] {1.0f, 0.65f, 0.0f});
            }
            return builder.build();
        }

        private void cleanupInactiveWrapperEntries(int usage) {
            FadeVolumeShaperConfigsWrapper fmcw = this.mUsageToFadeWrapperMap.get(usage);
            if (fmcw != null && fmcw.isInactive()) {
                this.mUsageToFadeWrapperMap.remove(usage);
            }
        }

        private void cleanupInactiveWrapperEntries(AudioAttributes attr) {
            FadeVolumeShaperConfigsWrapper fmcw = this.mAttrToFadeWrapperMap.get(attr);
            if (fmcw != null && fmcw.isInactive()) {
                this.mAttrToFadeWrapperMap.remove(attr);
            }
        }

        private void setVolShaperConfigsForUsages(IntArray usages) {
            for (int index = 0; index < usages.size(); index++) {
                setMissingVolShaperConfigsForWrapper(
                        getFadeVolShaperConfigWrapperForUsage(usages.get(index)));
            }
        }

        private void setMissingVolShaperConfigsForWrapper(FadeVolumeShaperConfigsWrapper wrapper) {
            if (!wrapper.isFadeOutConfigActive()) {
                wrapper.setFadeOutVolShaperConfig(
                        createVolShaperConfigForDuration(this.mFadeOutDurationMillis, false));
            }
            if (!wrapper.isFadeInConfigActive()) {
                wrapper.setFadeInVolShaperConfig(
                        createVolShaperConfigForDuration(this.mFadeInDurationMillis, true));
            }
        }

        private void copyUsageToFadeWrapperMapInternal(
                SparseArray<FadeVolumeShaperConfigsWrapper> usageToFadeWrapperMap) {
            for (int index = 0; index < usageToFadeWrapperMap.size(); index++) {
                this.mUsageToFadeWrapperMap.put(
                        usageToFadeWrapperMap.keyAt(index),
                        new FadeVolumeShaperConfigsWrapper(usageToFadeWrapperMap.valueAt(index)));
            }
        }

        private void validateFadeState(int state) {
            switch (state) {
                case 0:
                case 1:
                    return;
                default:
                    throw new IllegalArgumentException("Unknown fade state: " + state);
            }
        }

        private void validateUsages(List<Integer> usages) {
            for (int index = 0; index < usages.size(); index++) {
                FadeManagerConfiguration.validateUsage(usages.get(index).intValue());
            }
        }

        private void validateContentTypes(List<Integer> contentTypes) {
            for (int index = 0; index < contentTypes.size(); index++) {
                validateContentType(contentTypes.get(index).intValue());
            }
        }

        private void validateContentType(int contentType) {
            Preconditions.checkArgument(
                    AudioAttributes.isSdkContentType(contentType),
                    "Invalid content type: ",
                    Integer.valueOf(contentType));
        }

        private void validateFadeConfigurations() {
            validateFadeableUsages();
            validateFadeVolumeShaperConfigsWrappers();
            validateUnfadeableAudioAttributes();
        }

        private void validateFadeableUsages() {
            Preconditions.checkArgumentPositive(
                    this.mFadeableUsages.size(),
                    "Fadeable usage list cannot be empty when state set to enabled");
            for (int index = 0; index < this.mFadeableUsages.size(); index++) {
                setMissingVolShaperConfigsForWrapper(
                        getFadeVolShaperConfigWrapperForUsage(this.mFadeableUsages.get(index)));
            }
        }

        private void validateFadeVolumeShaperConfigsWrappers() {
            for (int index = 0; index < this.mUsageToFadeWrapperMap.size(); index++) {
                setMissingVolShaperConfigsForWrapper(
                        getFadeVolShaperConfigWrapperForUsage(
                                this.mUsageToFadeWrapperMap.keyAt(index)));
            }
            for (int index2 = 0; index2 < this.mAttrToFadeWrapperMap.size(); index2++) {
                setMissingVolShaperConfigsForWrapper(
                        getFadeVolShaperConfigWrapperForAttr(
                                this.mAttrToFadeWrapperMap.keyAt(index2)));
            }
        }

        private void validateUnfadeableAudioAttributes() {
            for (int index = 0; index < this.mUnfadeableAudioAttributes.size(); index++) {
                AudioAttributes targetAttr = this.mUnfadeableAudioAttributes.get(index);
                int usage = targetAttr.getSystemUsage();
                boolean isFadeableUsage = this.mFadeableUsages.contains(usage);
                Preconditions.checkArgument(
                        !isFadeableUsage || (isFadeableUsage && !isGeneric(targetAttr)),
                        "Unfadeable audio attributes cannot be generic of the fadeable usage");
            }
        }

        private static boolean isGeneric(AudioAttributes attr) {
            return attr.getContentType() == 0
                    && attr.getFlags() == 0
                    && attr.getBundle() == null
                    && attr.getTags().isEmpty();
        }
    }

    private static final class FadeVolumeShaperConfigsWrapper implements Parcelable {
        public static final Parcelable.Creator<FadeVolumeShaperConfigsWrapper> CREATOR =
                new Parcelable.Creator<FadeVolumeShaperConfigsWrapper>() { // from class:
                    // android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FadeVolumeShaperConfigsWrapper createFromParcel(Parcel in) {
                        return new FadeVolumeShaperConfigsWrapper(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FadeVolumeShaperConfigsWrapper[] newArray(int size) {
                        return new FadeVolumeShaperConfigsWrapper[size];
                    }
                };
        private VolumeShaper.Configuration mFadeInVolShaperConfig;
        private VolumeShaper.Configuration mFadeOutVolShaperConfig;

        FadeVolumeShaperConfigsWrapper() {}

        FadeVolumeShaperConfigsWrapper(FadeVolumeShaperConfigsWrapper wrapper) {
            Objects.requireNonNull(wrapper, "Fade volume shaper configs wrapper cannot be null");
            this.mFadeOutVolShaperConfig = wrapper.mFadeOutVolShaperConfig;
            this.mFadeInVolShaperConfig = wrapper.mFadeInVolShaperConfig;
        }

        public void setFadeOutVolShaperConfig(VolumeShaper.Configuration fadeOutConfig) {
            this.mFadeOutVolShaperConfig = fadeOutConfig;
        }

        public void setFadeInVolShaperConfig(VolumeShaper.Configuration fadeInConfig) {
            this.mFadeInVolShaperConfig = fadeInConfig;
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

        public boolean equals(Object o) {
            boolean isEqual;
            if (this == o) {
                return true;
            }
            if (!(o instanceof FadeVolumeShaperConfigsWrapper)) {
                return false;
            }
            FadeVolumeShaperConfigsWrapper rhs = (FadeVolumeShaperConfigsWrapper) o;
            if (this.mFadeInVolShaperConfig == null
                    && rhs.mFadeInVolShaperConfig == null
                    && this.mFadeOutVolShaperConfig == null
                    && rhs.mFadeOutVolShaperConfig == null) {
                return true;
            }
            if (this.mFadeOutVolShaperConfig != null) {
                isEqual = this.mFadeOutVolShaperConfig.equals(rhs.mFadeOutVolShaperConfig);
            } else {
                if (rhs.mFadeOutVolShaperConfig != null) {
                    return false;
                }
                isEqual = true;
            }
            if (this.mFadeInVolShaperConfig != null) {
                boolean isEqual2 =
                        isEqual && this.mFadeInVolShaperConfig.equals(rhs.mFadeInVolShaperConfig);
                return isEqual2;
            }
            if (rhs.mFadeInVolShaperConfig != null) {
                return false;
            }
            return isEqual;
        }

        public int hashCode() {
            return Objects.hash(this.mFadeOutVolShaperConfig, this.mFadeInVolShaperConfig);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            this.mFadeOutVolShaperConfig.writeToParcel(dest, flags);
            this.mFadeInVolShaperConfig.writeToParcel(dest, flags);
        }

        FadeVolumeShaperConfigsWrapper(Parcel in) {
            this.mFadeOutVolShaperConfig = VolumeShaper.Configuration.CREATOR.createFromParcel(in);
            this.mFadeInVolShaperConfig = VolumeShaper.Configuration.CREATOR.createFromParcel(in);
        }
    }
}
