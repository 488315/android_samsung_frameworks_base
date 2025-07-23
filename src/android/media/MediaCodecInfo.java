package android.media;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodecInfo;
import android.media.codec.Flags;
import android.os.Build;
import android.os.Process;
import android.os.SystemProperties;
import android.sysprop.MediaProperties;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.media.SemMediaPostProcessor;
import com.samsung.android.transcode.constants.EncodeConstants;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public final class MediaCodecInfo {
    private static final int DEFAULT_MAX_SUPPORTED_INSTANCES = 32;
    private static final int ERROR_NONE_SUPPORTED = 4;
    private static final int ERROR_UNRECOGNIZED = 1;
    private static final int ERROR_UNSUPPORTED = 2;
    private static final int FLAG_IS_ENCODER = 1;
    private static final int FLAG_IS_HARDWARE_ACCELERATED = 8;
    private static final int FLAG_IS_SOFTWARE_ONLY = 4;
    private static final int FLAG_IS_VENDOR = 2;
    private static final int MAX_SUPPORTED_INSTANCES_LIMIT = 256;
    public static final int SECURITY_MODEL_MEMORY_SAFE = 1;
    public static final int SECURITY_MODEL_SANDBOXED = 0;
    public static final int SECURITY_MODEL_TRUSTED_CONTENT_ONLY = 2;
    private static Range<Integer> SIZE_RANGE = null;
    private static final String TAG = "MediaCodecInfo";
    private String mCanonicalName;
    private Map<String, CodecCapabilities> mCaps = new HashMap();
    private int mFlags;
    private String mName;
    private static final Range<Integer> POSITIVE_INTEGERS = Range.create(1, Integer.MAX_VALUE);
    private static final Range<Long> POSITIVE_LONGS = Range.create(1L, Long.MAX_VALUE);
    private static final Range<Rational> POSITIVE_RATIONALS = Range.create(new Rational(1, Integer.MAX_VALUE), new Rational(Integer.MAX_VALUE, 1));
    private static final Range<Integer> FRAME_RATE_RANGE = Range.create(0, Integer.valueOf(EncodeConstants.Resolution.MM_360_EXPORT_HEIGHT_960));
    private static final Range<Integer> BITRATE_RANGE = Range.create(0, 500000000);

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityModel {
    }

    public int getSecurityModel() {
        return 0;
    }

    MediaCodecInfo(String str, String str2, int i, CodecCapabilities[] codecCapabilitiesArr) {
        this.mName = str;
        this.mCanonicalName = str2;
        this.mFlags = i;
        for (CodecCapabilities codecCapabilities : codecCapabilitiesArr) {
            this.mCaps.put(codecCapabilities.getMimeType(), codecCapabilities);
        }
    }

    public final String getName() {
        return this.mName;
    }

    public final String getCanonicalName() {
        return this.mCanonicalName;
    }

    public final boolean isAlias() {
        return !this.mName.equals(this.mCanonicalName);
    }

    public final boolean isEncoder() {
        return (this.mFlags & 1) != 0;
    }

    public final boolean isVendor() {
        return (this.mFlags & 2) != 0;
    }

    public final boolean isSoftwareOnly() {
        return (this.mFlags & 4) != 0;
    }

    public final boolean isHardwareAccelerated() {
        return (this.mFlags & 8) != 0;
    }

    public final String[] getSupportedTypes() {
        Set<String> keySet = this.mCaps.keySet();
        String[] strArr = (String[]) keySet.toArray(new String[keySet.size()]);
        Arrays.sort(strArr);
        return strArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPowerOfTwo(int i, String str) {
        if (((i - 1) & i) == 0) {
            return i;
        }
        throw new IllegalArgumentException(str);
    }

    private static class Feature {
        public boolean mDefault;
        public boolean mInternal;
        public String mName;
        public int mValue;

        public Feature(String str, int i, boolean z) {
            this(str, i, z, false);
        }

        public Feature(String str, int i, boolean z, boolean z2) {
            this.mName = str;
            this.mValue = i;
            this.mDefault = z;
            this.mInternal = z2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized Range<Integer> getSizeRange() {
        Range<Integer> range;
        Range<Integer> create;
        synchronized (MediaCodecInfo.class) {
            if (SIZE_RANGE == null) {
                if (Process.is64Bit()) {
                    create = Range.create(1, 32768);
                } else {
                    create = Range.create(1, MediaProperties.resolution_limit_32bit().orElse(4096));
                }
                SIZE_RANGE = create;
            }
            range = SIZE_RANGE;
        }
        return range;
    }

    public static final class CodecCapabilities {
        public static final int COLOR_Format12bitRGB444 = 3;
        public static final int COLOR_Format16bitARGB1555 = 5;
        public static final int COLOR_Format16bitARGB4444 = 4;
        public static final int COLOR_Format16bitBGR565 = 7;
        public static final int COLOR_Format16bitRGB565 = 6;
        public static final int COLOR_Format18BitBGR666 = 41;
        public static final int COLOR_Format18bitARGB1665 = 9;
        public static final int COLOR_Format18bitRGB666 = 8;
        public static final int COLOR_Format19bitARGB1666 = 10;
        public static final int COLOR_Format24BitABGR6666 = 43;
        public static final int COLOR_Format24BitARGB6666 = 42;
        public static final int COLOR_Format24bitARGB1887 = 13;
        public static final int COLOR_Format24bitBGR888 = 12;
        public static final int COLOR_Format24bitRGB888 = 11;
        public static final int COLOR_Format25bitARGB1888 = 14;
        public static final int COLOR_Format32bitABGR2101010 = 2130750114;
        public static final int COLOR_Format32bitABGR8888 = 2130747392;
        public static final int COLOR_Format32bitARGB8888 = 16;
        public static final int COLOR_Format32bitBGRA8888 = 15;
        public static final int COLOR_Format64bitABGRFloat = 2130710294;
        public static final int COLOR_Format8bitRGB332 = 2;
        public static final int COLOR_FormatCbYCrY = 27;
        public static final int COLOR_FormatCrYCbY = 28;
        public static final int COLOR_FormatL16 = 36;
        public static final int COLOR_FormatL2 = 33;
        public static final int COLOR_FormatL24 = 37;
        public static final int COLOR_FormatL32 = 38;
        public static final int COLOR_FormatL4 = 34;
        public static final int COLOR_FormatL8 = 35;
        public static final int COLOR_FormatMonochrome = 1;
        public static final int COLOR_FormatRGBAFlexible = 2134288520;
        public static final int COLOR_FormatRGBFlexible = 2134292616;
        public static final int COLOR_FormatRawBayer10bit = 31;
        public static final int COLOR_FormatRawBayer8bit = 30;
        public static final int COLOR_FormatRawBayer8bitcompressed = 32;
        public static final int COLOR_FormatSurface = 2130708361;
        public static final int COLOR_FormatYCbYCr = 25;
        public static final int COLOR_FormatYCrYCb = 26;
        public static final int COLOR_FormatYUV411PackedPlanar = 18;
        public static final int COLOR_FormatYUV411Planar = 17;
        public static final int COLOR_FormatYUV420Flexible = 2135033992;
        public static final int COLOR_FormatYUV420PackedPlanar = 20;
        public static final int COLOR_FormatYUV420PackedSemiPlanar = 39;
        public static final int COLOR_FormatYUV420Planar = 19;
        public static final int COLOR_FormatYUV420SemiPlanar = 21;
        public static final int COLOR_FormatYUV422Flexible = 2135042184;
        public static final int COLOR_FormatYUV422PackedPlanar = 23;
        public static final int COLOR_FormatYUV422PackedSemiPlanar = 40;
        public static final int COLOR_FormatYUV422Planar = 22;
        public static final int COLOR_FormatYUV422SemiPlanar = 24;
        public static final int COLOR_FormatYUV444Flexible = 2135181448;
        public static final int COLOR_FormatYUV444Interleaved = 29;
        public static final int COLOR_FormatYUVP010 = 54;
        public static final int COLOR_FormatYUVP210 = 60;
        public static final int COLOR_QCOM_FormatYUV420SemiPlanar = 2141391872;
        public static final int COLOR_TI_FormatYUV420PackedSemiPlanar = 2130706688;
        public static final String FEATURE_AdaptivePlayback = "adaptive-playback";
        public static final String FEATURE_DetachedSurface = "detached-surface";
        public static final String FEATURE_DynamicColorAspects = "dynamic-color-aspects";
        public static final String FEATURE_DynamicTimestamp = "dynamic-timestamp";
        public static final String FEATURE_EncodingStatistics = "encoding-statistics";
        public static final String FEATURE_FrameParsing = "frame-parsing";
        public static final String FEATURE_HdrEditing = "hdr-editing";
        public static final String FEATURE_HlgEditing = "hlg-editing";
        public static final String FEATURE_IntraRefresh = "intra-refresh";
        public static final String FEATURE_LowLatency = "low-latency";
        public static final String FEATURE_MultipleFrames = "multiple-frames";
        public static final String FEATURE_PartialFrame = "partial-frame";
        public static final String FEATURE_QpBounds = "qp-bounds";
        public static final String FEATURE_Roi = "region-of-interest";
        public static final String FEATURE_SecurePlayback = "secure-playback";
        private static final String FEATURE_SpecialCodec = "special-codec";
        public static final String FEATURE_TunneledPlayback = "tunneled-playback";
        private static final String TAG = "CodecCapabilities";
        public int[] colorFormats;
        private AudioCapabilities mAudioCaps;
        private MediaFormat mCapabilitiesInfo;
        private MediaFormat mDefaultFormat;
        private EncoderCapabilities mEncoderCaps;
        int mError;
        private int mFlagsRequired;
        private int mFlagsSupported;
        private int mFlagsVerified;
        private int mMaxSupportedInstances;
        private String mMime;
        private VideoCapabilities mVideoCaps;
        public CodecProfileLevel[] profileLevels;

        public CodecCapabilities() {
        }

        public final boolean isFeatureSupported(String str) {
            return checkFeature(str, this.mFlagsSupported);
        }

        public final boolean isFeatureRequired(String str) {
            return checkFeature(str, this.mFlagsRequired);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class FeatureList {
            private static Feature[] decoderFeatures = getDecoderFeatures();
            private static Feature[] encoderFeatures = getEncoderFeatures();

            private FeatureList() {
            }

            private static Feature[] getDecoderFeatures() {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Feature(CodecCapabilities.FEATURE_AdaptivePlayback, 1, true));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_SecurePlayback, 2, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_TunneledPlayback, 4, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_PartialFrame, 8, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_FrameParsing, 16, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_MultipleFrames, 32, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_DynamicTimestamp, 64, false));
                arrayList.add(new Feature("low-latency", 128, true));
                if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodecInfo$CodecCapabilities$FeatureList$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(Flags.dynamicColorAspects());
                        return valueOf;
                    }
                })) {
                    arrayList.add(new Feature(CodecCapabilities.FEATURE_DynamicColorAspects, 256, true));
                }
                if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodecInfo$CodecCapabilities$FeatureList$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(Flags.nullOutputSurface());
                        return valueOf;
                    }
                })) {
                    arrayList.add(new Feature(CodecCapabilities.FEATURE_DetachedSurface, 512, true));
                }
                arrayList.add(new Feature(CodecCapabilities.FEATURE_SpecialCodec, 1073741824, false, true));
                return (Feature[]) arrayList.toArray(new Feature[0]);
            }

            private static Feature[] getEncoderFeatures() {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Feature(CodecCapabilities.FEATURE_IntraRefresh, 1, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_MultipleFrames, 2, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_DynamicTimestamp, 4, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_QpBounds, 8, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_EncodingStatistics, 16, false));
                arrayList.add(new Feature(CodecCapabilities.FEATURE_HdrEditing, 32, false));
                if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodecInfo$CodecCapabilities$FeatureList$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(Flags.hlgEditing());
                        return valueOf;
                    }
                })) {
                    arrayList.add(new Feature(CodecCapabilities.FEATURE_HlgEditing, 64, true));
                }
                if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodecInfo$CodecCapabilities$FeatureList$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(Flags.regionOfInterest());
                        return valueOf;
                    }
                })) {
                    arrayList.add(new Feature(CodecCapabilities.FEATURE_Roi, 128, true));
                }
                arrayList.add(new Feature(CodecCapabilities.FEATURE_SpecialCodec, 1073741824, false, true));
                return (Feature[]) arrayList.toArray(new Feature[0]);
            }

            public static Feature[] getFeatures(boolean z) {
                if (z) {
                    return encoderFeatures;
                }
                return decoderFeatures;
            }
        }

        public String[] validFeatures() {
            Feature[] validFeatures = getValidFeatures();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < validFeatures.length; i++) {
                if (!validFeatures[i].mInternal) {
                    arrayList.add(validFeatures[i].mName);
                }
            }
            return (String[]) arrayList.toArray(new String[0]);
        }

        private Feature[] getValidFeatures() {
            return FeatureList.getFeatures(isEncoder());
        }

        private boolean checkFeature(String str, int i) {
            for (Feature feature : getValidFeatures()) {
                if (feature.mName.equals(str)) {
                    return (feature.mValue & i) != 0;
                }
            }
            return false;
        }

        public boolean isRegular() {
            for (Feature feature : getValidFeatures()) {
                if (!feature.mDefault && isFeatureRequired(feature.mName)) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isFormatSupported(MediaFormat mediaFormat) {
            Set<String> set;
            Map<String, Object> map = mediaFormat.getMap();
            String str = (String) map.get("mime");
            if (str != null && !this.mMime.equalsIgnoreCase(str)) {
                return false;
            }
            for (Feature feature : getValidFeatures()) {
                if (!feature.mInternal) {
                    Integer num = (Integer) map.get(MediaFormat.KEY_FEATURE_ + feature.mName);
                    if (num != null && ((num.intValue() == 1 && !isFeatureSupported(feature.mName)) || (num.intValue() == 0 && isFeatureRequired(feature.mName)))) {
                        return false;
                    }
                }
            }
            Integer num2 = (Integer) map.get("profile");
            Integer num3 = (Integer) map.get("level");
            if (num2 != null) {
                if (!supportsProfileLevel(num2.intValue(), num3)) {
                    return false;
                }
                int i = 0;
                for (CodecProfileLevel codecProfileLevel : this.profileLevels) {
                    if (codecProfileLevel.profile == num2.intValue() && codecProfileLevel.level > i && (!this.mMime.equalsIgnoreCase("video/3gpp") || codecProfileLevel.level != 16 || i == 1)) {
                        i = codecProfileLevel.level;
                    }
                }
                CodecCapabilities createFromProfileLevel = createFromProfileLevel(this.mMime, num2.intValue(), i);
                HashMap hashMap = new HashMap(map);
                if (isVideo()) {
                    set = VideoCapabilities.VIDEO_LEVEL_CRITICAL_FORMAT_KEYS;
                } else {
                    set = isAudio() ? AudioCapabilities.AUDIO_LEVEL_CRITICAL_FORMAT_KEYS : null;
                }
                if (set != null && set.size() > 1 && createFromProfileLevel != null) {
                    hashMap.keySet().retainAll(set);
                    if (!createFromProfileLevel.isFormatSupported(new MediaFormat(hashMap))) {
                        return false;
                    }
                }
            }
            AudioCapabilities audioCapabilities = this.mAudioCaps;
            if (audioCapabilities != null && !audioCapabilities.supportsFormat(mediaFormat)) {
                return false;
            }
            VideoCapabilities videoCapabilities = this.mVideoCaps;
            if (videoCapabilities != null && !videoCapabilities.supportsFormat(mediaFormat)) {
                return false;
            }
            EncoderCapabilities encoderCapabilities = this.mEncoderCaps;
            return encoderCapabilities == null || encoderCapabilities.supportsFormat(mediaFormat);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean supportsBitrate(Range<Integer> range, MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            Integer num = (Integer) map.get(MediaFormat.KEY_MAX_BIT_RATE);
            Integer num2 = (Integer) map.get(MediaFormat.KEY_BIT_RATE);
            if (num2 != null) {
                num = num != null ? Integer.valueOf(Math.max(num2.intValue(), num.intValue())) : num2;
            }
            if (num == null || num.intValue() <= 0) {
                return true;
            }
            return range.contains((Range<Integer>) num);
        }

        private boolean supportsProfileLevel(int i, Integer num) {
            for (CodecProfileLevel codecProfileLevel : this.profileLevels) {
                if (codecProfileLevel.profile == i) {
                    if (num == null || this.mMime.equalsIgnoreCase("audio/mp4a-latm") || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS) || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_HD) || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_UHD)) {
                        return true;
                    }
                    if ((!this.mMime.equalsIgnoreCase("video/3gpp") || codecProfileLevel.level == num.intValue() || codecProfileLevel.level != 16 || num.intValue() <= 1) && (!this.mMime.equalsIgnoreCase("video/mp4v-es") || codecProfileLevel.level == num.intValue() || codecProfileLevel.level != 4 || num.intValue() <= 1)) {
                        if (this.mMime.equalsIgnoreCase("video/hevc")) {
                            boolean z = (codecProfileLevel.level & 44739242) != 0;
                            if ((44739242 & num.intValue()) != 0 && !z) {
                            }
                        }
                        if (codecProfileLevel.level >= num.intValue()) {
                            return createFromProfileLevel(this.mMime, i, codecProfileLevel.level) == null || createFromProfileLevel(this.mMime, i, num.intValue()) != null;
                        }
                    }
                }
            }
            return false;
        }

        public MediaFormat getDefaultFormat() {
            return this.mDefaultFormat;
        }

        public String getMimeType() {
            return this.mMime;
        }

        public int getMaxSupportedInstances() {
            return this.mMaxSupportedInstances;
        }

        private boolean isAudio() {
            return this.mAudioCaps != null;
        }

        public AudioCapabilities getAudioCapabilities() {
            return this.mAudioCaps;
        }

        private boolean isEncoder() {
            return this.mEncoderCaps != null;
        }

        public EncoderCapabilities getEncoderCapabilities() {
            return this.mEncoderCaps;
        }

        private boolean isVideo() {
            return this.mVideoCaps != null;
        }

        public VideoCapabilities getVideoCapabilities() {
            return this.mVideoCaps;
        }

        public CodecCapabilities dup() {
            CodecCapabilities codecCapabilities = new CodecCapabilities();
            CodecProfileLevel[] codecProfileLevelArr = this.profileLevels;
            codecCapabilities.profileLevels = (CodecProfileLevel[]) Arrays.copyOf(codecProfileLevelArr, codecProfileLevelArr.length);
            int[] iArr = this.colorFormats;
            codecCapabilities.colorFormats = Arrays.copyOf(iArr, iArr.length);
            codecCapabilities.mMime = this.mMime;
            codecCapabilities.mMaxSupportedInstances = this.mMaxSupportedInstances;
            codecCapabilities.mFlagsRequired = this.mFlagsRequired;
            codecCapabilities.mFlagsSupported = this.mFlagsSupported;
            codecCapabilities.mFlagsVerified = this.mFlagsVerified;
            codecCapabilities.mAudioCaps = this.mAudioCaps;
            codecCapabilities.mVideoCaps = this.mVideoCaps;
            codecCapabilities.mEncoderCaps = this.mEncoderCaps;
            codecCapabilities.mDefaultFormat = this.mDefaultFormat;
            codecCapabilities.mCapabilitiesInfo = this.mCapabilitiesInfo;
            return codecCapabilities;
        }

        public static CodecCapabilities createFromProfileLevel(String str, int i, int i2) {
            CodecProfileLevel codecProfileLevel = new CodecProfileLevel();
            codecProfileLevel.profile = i;
            codecProfileLevel.level = i2;
            MediaFormat mediaFormat = new MediaFormat();
            mediaFormat.setString("mime", str);
            CodecCapabilities codecCapabilities = new CodecCapabilities(new CodecProfileLevel[]{codecProfileLevel}, new int[0], true, mediaFormat, new MediaFormat());
            if (codecCapabilities.mError != 0) {
                return null;
            }
            return codecCapabilities;
        }

        CodecCapabilities(CodecProfileLevel[] codecProfileLevelArr, int[] iArr, boolean z, Map<String, Object> map, Map<String, Object> map2) {
            this(codecProfileLevelArr, iArr, z, new MediaFormat(map), new MediaFormat(map2));
        }

        CodecCapabilities(CodecProfileLevel[] codecProfileLevelArr, int[] iArr, boolean z, MediaFormat mediaFormat, MediaFormat mediaFormat2) {
            Map<String, Object> map = mediaFormat2.getMap();
            this.colorFormats = iArr;
            this.mFlagsVerified = 0;
            this.mDefaultFormat = mediaFormat;
            this.mCapabilitiesInfo = mediaFormat2;
            String string = mediaFormat.getString("mime");
            this.mMime = string;
            if (codecProfileLevelArr.length == 0 && string.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_VP9)) {
                CodecProfileLevel codecProfileLevel = new CodecProfileLevel();
                codecProfileLevel.profile = 1;
                codecProfileLevel.level = VideoCapabilities.equivalentVP9Level(mediaFormat2);
                codecProfileLevelArr = new CodecProfileLevel[]{codecProfileLevel};
            }
            this.profileLevels = codecProfileLevelArr;
            if (this.mMime.toLowerCase().startsWith("audio/")) {
                AudioCapabilities create = AudioCapabilities.create(mediaFormat2, this);
                this.mAudioCaps = create;
                create.getDefaultFormat(this.mDefaultFormat);
            } else if (this.mMime.toLowerCase().startsWith(BnRConstants.VIDEO_DIR_PATH) || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_IMAGE_ANDROID_HEIC)) {
                this.mVideoCaps = VideoCapabilities.create(mediaFormat2, this);
            }
            if (z) {
                EncoderCapabilities create2 = EncoderCapabilities.create(mediaFormat2, this);
                this.mEncoderCaps = create2;
                create2.getDefaultFormat(this.mDefaultFormat);
            }
            this.mMaxSupportedInstances = Utils.parseIntSafely(MediaCodecList.getGlobalSettings().get("max-concurrent-instances"), 32);
            this.mMaxSupportedInstances = ((Integer) Range.create(1, 256).clamp(Integer.valueOf(Utils.parseIntSafely(map.get("max-concurrent-instances"), this.mMaxSupportedInstances)))).intValue();
            for (Feature feature : getValidFeatures()) {
                String str = MediaFormat.KEY_FEATURE_ + feature.mName;
                Integer num = (Integer) map.get(str);
                if (num != null) {
                    if (num.intValue() > 0) {
                        this.mFlagsRequired |= feature.mValue;
                    }
                    this.mFlagsSupported |= feature.mValue;
                    if (!feature.mInternal) {
                        this.mDefaultFormat.setInteger(str, 1);
                    }
                }
            }
        }
    }

    public static final class AudioCapabilities {
        static final Set<String> AUDIO_LEVEL_CRITICAL_FORMAT_KEYS = Set.of("mime");
        private static final int MAX_INPUT_CHANNEL_COUNT = 30;
        private static final String TAG = "AudioCapabilities";
        private Range<Integer> mBitrateRange;
        private Range<Integer>[] mInputChannelRanges;
        private CodecCapabilities mParent;
        private Range<Integer>[] mSampleRateRanges;
        private int[] mSampleRates;

        public Range<Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public int[] getSupportedSampleRates() {
            int[] iArr = this.mSampleRates;
            if (iArr != null) {
                return Arrays.copyOf(iArr, iArr.length);
            }
            return null;
        }

        public Range<Integer>[] getSupportedSampleRateRanges() {
            Range<Integer>[] rangeArr = this.mSampleRateRanges;
            return (Range[]) Arrays.copyOf(rangeArr, rangeArr.length);
        }

        public int getMaxInputChannelCount() {
            int i = 0;
            for (int length = this.mInputChannelRanges.length - 1; length >= 0; length--) {
                int intValue = this.mInputChannelRanges[length].getUpper().intValue();
                if (intValue > i) {
                    i = intValue;
                }
            }
            return i;
        }

        public int getMinInputChannelCount() {
            int i = 30;
            for (int length = this.mInputChannelRanges.length - 1; length >= 0; length--) {
                int intValue = this.mInputChannelRanges[length].getLower().intValue();
                if (intValue < i) {
                    i = intValue;
                }
            }
            return i;
        }

        public Range<Integer>[] getInputChannelCountRanges() {
            Range<Integer>[] rangeArr = this.mInputChannelRanges;
            return (Range[]) Arrays.copyOf(rangeArr, rangeArr.length);
        }

        private AudioCapabilities() {
        }

        public static AudioCapabilities create(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            AudioCapabilities audioCapabilities = new AudioCapabilities();
            audioCapabilities.init(mediaFormat, codecCapabilities);
            return audioCapabilities;
        }

        private void init(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            initWithPlatformLimits();
            applyLevelLimits();
            parseFromInfo(mediaFormat);
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = Range.create(0, Integer.MAX_VALUE);
            this.mInputChannelRanges = new Range[]{Range.create(1, 30)};
            this.mSampleRateRanges = new Range[]{Range.create(Integer.valueOf(SystemProperties.getInt("ro.mediacodec.min_sample_rate", 7350)), Integer.valueOf(SystemProperties.getInt("ro.mediacodec.max_sample_rate", 192000)))};
            this.mSampleRates = null;
        }

        private boolean supports(Integer num, Integer num2) {
            if (num2 == null || Utils.binarySearchDistinctRanges(this.mInputChannelRanges, num2) >= 0) {
                return num == null || Utils.binarySearchDistinctRanges(this.mSampleRateRanges, num) >= 0;
            }
            return false;
        }

        public boolean isSampleRateSupported(int i) {
            return supports(Integer.valueOf(i), null);
        }

        private void limitSampleRates(int[] iArr) {
            Arrays.sort(iArr);
            ArrayList arrayList = new ArrayList();
            for (int i : iArr) {
                if (supports(Integer.valueOf(i), null)) {
                    arrayList.add(Range.create(Integer.valueOf(i), Integer.valueOf(i)));
                }
            }
            this.mSampleRateRanges = (Range[]) arrayList.toArray(new Range[arrayList.size()]);
            createDiscreteSampleRates();
        }

        private void createDiscreteSampleRates() {
            this.mSampleRates = new int[this.mSampleRateRanges.length];
            int i = 0;
            while (true) {
                Range<Integer>[] rangeArr = this.mSampleRateRanges;
                if (i >= rangeArr.length) {
                    return;
                }
                this.mSampleRates[i] = rangeArr[i].getLower().intValue();
                i++;
            }
        }

        private void limitSampleRates(Range<Integer>[] rangeArr) {
            Utils.sortDistinctRanges(rangeArr);
            Range<Integer>[] intersectSortedDistinctRanges = Utils.intersectSortedDistinctRanges(this.mSampleRateRanges, rangeArr);
            this.mSampleRateRanges = intersectSortedDistinctRanges;
            for (Range<Integer> range : intersectSortedDistinctRanges) {
                if (!range.getLower().equals(range.getUpper())) {
                    this.mSampleRates = null;
                    return;
                }
            }
            createDiscreteSampleRates();
        }

        private void applyLevelLimits() {
            int i;
            int[] iArr;
            Range<Integer> create;
            int[] iArr2;
            Range<Integer> create2;
            int i2;
            int i3;
            int[] iArr3;
            Range<Integer> create3;
            int[] iArr4;
            CodecProfileLevel[] codecProfileLevelArr = this.mParent.profileLevels;
            String mimeType = this.mParent.getMimeType();
            char c = 5;
            int i4 = 44100;
            Range<Integer> range = null;
            if (mimeType.equalsIgnoreCase("audio/mpeg")) {
                iArr = new int[]{8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000};
                create = Range.create(8000, 320000);
                i = 2;
            } else {
                if (mimeType.equalsIgnoreCase("audio/3gpp")) {
                    iArr = new int[]{8000};
                    create = Range.create(4750, 12200);
                } else if (mimeType.equalsIgnoreCase("audio/amr-wb")) {
                    iArr = new int[]{16000};
                    create = Range.create(6600, 23850);
                } else if (mimeType.equalsIgnoreCase("audio/mp4a-latm")) {
                    iArr = new int[]{7350, 8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000, 64000, 88200, 96000};
                    create = Range.create(8000, 510000);
                    i = 48;
                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_VORBIS)) {
                    i = 255;
                    create = Range.create(32000, Integer.valueOf(Build.VERSION_CODES_FULL.ECLAIR));
                    iArr = null;
                    range = Range.create(8000, 192000);
                } else {
                    if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_OPUS)) {
                        i = 255;
                        iArr4 = new int[]{8000, 12000, 16000, 24000, 48000};
                        create = Range.create(6000, 510000);
                    } else if (!mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_RAW)) {
                        if (!mimeType.equalsIgnoreCase("audio/flac")) {
                            i = 30;
                            if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_ALAW) || mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_MLAW)) {
                                iArr = new int[]{8000};
                                create = Range.create(64000, 64000);
                            } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_MSGSM)) {
                                iArr = new int[]{8000};
                                create = Range.create(Integer.valueOf(EncodeConstants.BitRate.MM_AVG_FHD_DATARATE), Integer.valueOf(EncodeConstants.BitRate.MM_AVG_FHD_DATARATE));
                            } else {
                                if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AC3)) {
                                    i = 6;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_EAC3)) {
                                    i = 16;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_EAC3_JOC)) {
                                    iArr = new int[]{48000};
                                    create = Range.create(32000, 6144000);
                                    i = 16;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AC4)) {
                                    iArr = new int[]{44100, 48000, 96000, 192000};
                                    create = Range.create(16000, 2688000);
                                    i = 24;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS)) {
                                    iArr = new int[]{44100, 48000};
                                    create = Range.create(96000, 1524000);
                                    i = 6;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_HD)) {
                                    int length = codecProfileLevelArr.length;
                                    int i5 = 0;
                                    int[] iArr5 = null;
                                    Range<Integer> range2 = null;
                                    while (i5 < length) {
                                        CodecProfileLevel codecProfileLevel = codecProfileLevelArr[i5];
                                        char c2 = c;
                                        int i6 = codecProfileLevel.profile;
                                        if (i6 != 1) {
                                            if (i6 == 2) {
                                                i3 = i4;
                                                iArr3 = new int[]{22050, 24000, i3, 48000};
                                                create3 = Range.create(32000, 768000);
                                            } else if (i6 != 4) {
                                                i3 = i4;
                                                Log.w(TAG, "Unrecognized profile " + codecProfileLevel.profile + " for " + mimeType);
                                                CodecCapabilities codecCapabilities = this.mParent;
                                                codecCapabilities.mError = codecCapabilities.mError | 1;
                                                iArr3 = new int[6];
                                                iArr3[0] = i3;
                                                iArr3[1] = 48000;
                                                iArr3[2] = 88200;
                                                iArr3[3] = 96000;
                                                iArr3[4] = 176400;
                                                iArr3[c2] = 192000;
                                                create3 = Range.create(96000, 24500000);
                                            }
                                            iArr5 = iArr3;
                                            range2 = create3;
                                            i5++;
                                            c = c2;
                                            i4 = i3;
                                        }
                                        i3 = i4;
                                        iArr3 = new int[6];
                                        iArr3[0] = i3;
                                        iArr3[1] = 48000;
                                        iArr3[2] = 88200;
                                        iArr3[3] = 96000;
                                        iArr3[4] = 176400;
                                        iArr3[c2] = 192000;
                                        create3 = Range.create(96000, 24500000);
                                        iArr5 = iArr3;
                                        range2 = create3;
                                        i5++;
                                        c = c2;
                                        i4 = i3;
                                    }
                                    i = 8;
                                    iArr = iArr5;
                                    create = range2;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_UHD)) {
                                    int[] iArr6 = null;
                                    Range<Integer> range3 = null;
                                    for (CodecProfileLevel codecProfileLevel2 : codecProfileLevelArr) {
                                        int i7 = codecProfileLevel2.profile;
                                        if (i7 == 1) {
                                            iArr2 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                            create2 = Range.create(96000, 24500000);
                                        } else if (i7 == 2) {
                                            iArr2 = new int[]{48000};
                                            create2 = Range.create(96000, 768000);
                                            i2 = 10;
                                            Range<Integer> range4 = create2;
                                            iArr6 = iArr2;
                                            i = i2;
                                            range3 = range4;
                                        } else {
                                            Log.w(TAG, "Unrecognized profile " + codecProfileLevel2.profile + " for " + mimeType);
                                            CodecCapabilities codecCapabilities2 = this.mParent;
                                            codecCapabilities2.mError = codecCapabilities2.mError | 1;
                                            iArr2 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                            create2 = Range.create(96000, 24500000);
                                        }
                                        i2 = 32;
                                        Range<Integer> range42 = create2;
                                        iArr6 = iArr2;
                                        i = i2;
                                        range3 = range42;
                                    }
                                    iArr = iArr6;
                                    create = range3;
                                } else {
                                    Log.w(TAG, "Unsupported mime " + mimeType);
                                    CodecCapabilities codecCapabilities3 = this.mParent;
                                    codecCapabilities3.mError = codecCapabilities3.mError | 2;
                                }
                                iArr = null;
                                create = null;
                            }
                        } else {
                            i = 255;
                            create = null;
                            range = Range.create(1, 655350);
                            iArr = null;
                        }
                    } else {
                        Range<Integer> create4 = Range.create(1, 192000);
                        create = Range.create(1, 10000000);
                        i = AudioSystem.OUT_CHANNEL_COUNT_MAX;
                        iArr4 = null;
                        range = create4;
                    }
                    iArr = iArr4;
                }
                i = 1;
            }
            if (iArr != null) {
                limitSampleRates(iArr);
            } else if (range != null) {
                limitSampleRates(new Range[]{range});
            }
            applyLimits(new Range[]{Range.create(1, Integer.valueOf(i))}, create);
        }

        private void applyLimits(Range<Integer>[] rangeArr, Range<Integer> range) {
            Range[] rangeArr2 = new Range[rangeArr.length];
            for (int i = 0; i < rangeArr.length; i++) {
                Integer clamp = rangeArr[i].clamp(1);
                clamp.intValue();
                Integer clamp2 = rangeArr[i].clamp(30);
                clamp2.intValue();
                rangeArr2[i] = Range.create(clamp, clamp2);
            }
            Utils.sortDistinctRanges(rangeArr2);
            this.mInputChannelRanges = Utils.intersectSortedDistinctRanges(rangeArr2, this.mInputChannelRanges);
            if (range != null) {
                this.mBitrateRange = this.mBitrateRange.intersect(range);
            }
        }

        private void parseFromInfo(MediaFormat mediaFormat) {
            Range<Integer>[] rangeArr = {Range.create(1, 30)};
            Range<Integer> range = MediaCodecInfo.POSITIVE_INTEGERS;
            if (mediaFormat.containsKey("sample-rate-ranges")) {
                String[] split = mediaFormat.getString("sample-rate-ranges").split(",");
                Range<Integer>[] rangeArr2 = new Range[split.length];
                for (int i = 0; i < split.length; i++) {
                    rangeArr2[i] = Utils.parseIntRange(split[i], null);
                }
                limitSampleRates(rangeArr2);
            }
            if (mediaFormat.containsKey("channel-ranges")) {
                String[] split2 = mediaFormat.getString("channel-ranges").split(",");
                rangeArr = new Range[split2.length];
                for (int i2 = 0; i2 < split2.length; i2++) {
                    rangeArr[i2] = Utils.parseIntRange(split2[i2], null);
                }
            } else if (mediaFormat.containsKey("channel-range")) {
                rangeArr = new Range[]{Utils.parseIntRange(mediaFormat.getString("channel-range"), null)};
            } else if (mediaFormat.containsKey("max-channel-count")) {
                int parseIntSafely = Utils.parseIntSafely(mediaFormat.getString("max-channel-count"), 30);
                if (parseIntSafely == 0) {
                    rangeArr = new Range[]{Range.create(0, 0)};
                } else {
                    rangeArr = new Range[]{Range.create(1, Integer.valueOf(parseIntSafely))};
                }
            } else if ((this.mParent.mError & 2) != 0) {
                rangeArr = new Range[]{Range.create(0, 0)};
            }
            if (mediaFormat.containsKey("bitrate-range")) {
                range = range.intersect(Utils.parseIntRange(mediaFormat.getString("bitrate-range"), range));
            }
            applyLimits(rangeArr, range);
        }

        public void getDefaultFormat(MediaFormat mediaFormat) {
            if (this.mBitrateRange.getLower().equals(this.mBitrateRange.getUpper())) {
                mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, this.mBitrateRange.getLower().intValue());
            }
            if (getMaxInputChannelCount() == 1) {
                mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
            }
            int[] iArr = this.mSampleRates;
            if (iArr == null || iArr.length != 1) {
                return;
            }
            mediaFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE, iArr[0]);
        }

        public boolean supportsFormat(MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            return supports((Integer) map.get(MediaFormat.KEY_SAMPLE_RATE), (Integer) map.get(MediaFormat.KEY_CHANNEL_COUNT)) && CodecCapabilities.supportsBitrate(this.mBitrateRange, mediaFormat);
        }
    }

    public static final class VideoCapabilities {
        private static final String TAG = "VideoCapabilities";
        static final Set<String> VIDEO_LEVEL_CRITICAL_FORMAT_KEYS = Set.of("width", "height", MediaFormat.KEY_FRAME_RATE, MediaFormat.KEY_BIT_RATE, "mime");
        private boolean mAllowMbOverride;
        private Range<Rational> mAspectRatioRange;
        private Range<Integer> mBitrateRange;
        private Range<Rational> mBlockAspectRatioRange;
        private Range<Integer> mBlockCountRange;
        private int mBlockHeight;
        private int mBlockWidth;
        private Range<Long> mBlocksPerSecondRange;
        private Range<Integer> mFrameRateRange;
        private int mHeightAlignment;
        private Range<Integer> mHeightRange;
        private Range<Integer> mHorizontalBlockRange;
        private Map<Size, Range<Long>> mMeasuredFrameRates;
        private CodecCapabilities mParent;
        private List<PerformancePoint> mPerformancePoints;
        private int mSmallerDimensionUpperLimit;
        private Range<Integer> mVerticalBlockRange;
        private int mWidthAlignment;
        private Range<Integer> mWidthRange;

        public Range<Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public Range<Integer> getSupportedWidths() {
            return this.mWidthRange;
        }

        public Range<Integer> getSupportedHeights() {
            return this.mHeightRange;
        }

        public int getWidthAlignment() {
            return this.mWidthAlignment;
        }

        public int getHeightAlignment() {
            return this.mHeightAlignment;
        }

        public int getSmallerDimensionUpperLimit() {
            return this.mSmallerDimensionUpperLimit;
        }

        public Range<Integer> getSupportedFrameRates() {
            return this.mFrameRateRange;
        }

        public Range<Integer> getSupportedWidthsFor(int i) {
            try {
                Range<Integer> range = this.mWidthRange;
                if (!this.mHeightRange.contains((Range<Integer>) Integer.valueOf(i)) || i % this.mHeightAlignment != 0) {
                    throw new IllegalArgumentException("unsupported height");
                }
                int divUp = Utils.divUp(i, this.mBlockHeight);
                double d = divUp;
                Range<Integer> intersect = range.intersect(Integer.valueOf(((Math.max(Utils.divUp(this.mBlockCountRange.getLower().intValue(), divUp), (int) Math.ceil(this.mBlockAspectRatioRange.getLower().doubleValue() * d)) - 1) * this.mBlockWidth) + this.mWidthAlignment), Integer.valueOf(Math.min(this.mBlockCountRange.getUpper().intValue() / divUp, (int) (this.mBlockAspectRatioRange.getUpper().doubleValue() * d)) * this.mBlockWidth));
                if (i > this.mSmallerDimensionUpperLimit) {
                    intersect = intersect.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                double d2 = i;
                return intersect.intersect(Integer.valueOf((int) Math.ceil(this.mAspectRatioRange.getLower().doubleValue() * d2)), Integer.valueOf((int) (this.mAspectRatioRange.getUpper().doubleValue() * d2)));
            } catch (IllegalArgumentException unused) {
                Log.v(TAG, "could not get supported widths for " + i);
                throw new IllegalArgumentException("unsupported height");
            }
        }

        public Range<Integer> getSupportedHeightsFor(int i) {
            try {
                Range<Integer> range = this.mHeightRange;
                if (!this.mWidthRange.contains((Range<Integer>) Integer.valueOf(i)) || i % this.mWidthAlignment != 0) {
                    throw new IllegalArgumentException("unsupported width");
                }
                int divUp = Utils.divUp(i, this.mBlockWidth);
                double d = divUp;
                Range<Integer> intersect = range.intersect(Integer.valueOf(((Math.max(Utils.divUp(this.mBlockCountRange.getLower().intValue(), divUp), (int) Math.ceil(d / this.mBlockAspectRatioRange.getUpper().doubleValue())) - 1) * this.mBlockHeight) + this.mHeightAlignment), Integer.valueOf(Math.min(this.mBlockCountRange.getUpper().intValue() / divUp, (int) (d / this.mBlockAspectRatioRange.getLower().doubleValue())) * this.mBlockHeight));
                if (i > this.mSmallerDimensionUpperLimit) {
                    intersect = intersect.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                double d2 = i;
                return intersect.intersect(Integer.valueOf((int) Math.ceil(d2 / this.mAspectRatioRange.getUpper().doubleValue())), Integer.valueOf((int) (d2 / this.mAspectRatioRange.getLower().doubleValue())));
            } catch (IllegalArgumentException unused) {
                Log.v(TAG, "could not get supported heights for " + i);
                throw new IllegalArgumentException("unsupported width");
            }
        }

        public Range<Double> getSupportedFrameRatesFor(int i, int i2) {
            if (!supports(Integer.valueOf(i), Integer.valueOf(i2), null)) {
                throw new IllegalArgumentException("unsupported size");
            }
            double divUp = Utils.divUp(i, this.mBlockWidth) * Utils.divUp(i2, this.mBlockHeight);
            return Range.create(Double.valueOf(Math.max(this.mBlocksPerSecondRange.getLower().longValue() / divUp, this.mFrameRateRange.getLower().intValue())), Double.valueOf(Math.min(this.mBlocksPerSecondRange.getUpper().longValue() / divUp, this.mFrameRateRange.getUpper().intValue())));
        }

        private int getBlockCount(int i, int i2) {
            return Utils.divUp(i, this.mBlockWidth) * Utils.divUp(i2, this.mBlockHeight);
        }

        private Size findClosestSize(int i, int i2) {
            int blockCount = getBlockCount(i, i2);
            Size size = null;
            int i3 = Integer.MAX_VALUE;
            for (Size size2 : this.mMeasuredFrameRates.keySet()) {
                int abs = Math.abs(blockCount - getBlockCount(size2.getWidth(), size2.getHeight()));
                if (abs < i3) {
                    size = size2;
                    i3 = abs;
                }
            }
            return size;
        }

        private Range<Double> estimateFrameRatesFor(int i, int i2) {
            Range<Long> range = this.mMeasuredFrameRates.get(findClosestSize(i, i2));
            double blockCount = getBlockCount(r0.getWidth(), r0.getHeight()) / Math.max(getBlockCount(i, i2), 1);
            Double valueOf = Double.valueOf(blockCount);
            double longValue = range.getLower().longValue();
            valueOf.getClass();
            Double valueOf2 = Double.valueOf(longValue * blockCount);
            double longValue2 = range.getUpper().longValue();
            valueOf.getClass();
            return Range.create(valueOf2, Double.valueOf(longValue2 * blockCount));
        }

        public Range<Double> getAchievableFrameRatesFor(int i, int i2) {
            if (!supports(Integer.valueOf(i), Integer.valueOf(i2), null)) {
                throw new IllegalArgumentException("unsupported size");
            }
            Map<Size, Range<Long>> map = this.mMeasuredFrameRates;
            if (map == null || map.size() <= 0) {
                Log.w(TAG, "Codec did not publish any measurement data.");
                return null;
            }
            return estimateFrameRatesFor(i, i2);
        }

        public static final class PerformancePoint {
            private Size mBlockSize;
            private int mHeight;
            private int mMaxFrameRate;
            private long mMaxMacroBlockRate;
            private int mWidth;
            public static final PerformancePoint SD_24 = new PerformancePoint(720, 480, 24);
            public static final PerformancePoint SD_25 = new PerformancePoint(720, 576, 25);
            public static final PerformancePoint SD_30 = new PerformancePoint(720, 480, 30);
            public static final PerformancePoint SD_48 = new PerformancePoint(720, 480, 48);
            public static final PerformancePoint SD_50 = new PerformancePoint(720, 576, 50);
            public static final PerformancePoint SD_60 = new PerformancePoint(720, 480, 60);
            public static final PerformancePoint HD_24 = new PerformancePoint(1280, 720, 24);
            public static final PerformancePoint HD_25 = new PerformancePoint(1280, 720, 25);
            public static final PerformancePoint HD_30 = new PerformancePoint(1280, 720, 30);
            public static final PerformancePoint HD_50 = new PerformancePoint(1280, 720, 50);
            public static final PerformancePoint HD_60 = new PerformancePoint(1280, 720, 60);
            public static final PerformancePoint HD_100 = new PerformancePoint(1280, 720, 100);
            public static final PerformancePoint HD_120 = new PerformancePoint(1280, 720, 120);
            public static final PerformancePoint HD_200 = new PerformancePoint(1280, 720, 200);
            public static final PerformancePoint HD_240 = new PerformancePoint(1280, 720, 240);
            public static final PerformancePoint FHD_24 = new PerformancePoint(1920, 1080, 24);
            public static final PerformancePoint FHD_25 = new PerformancePoint(1920, 1080, 25);
            public static final PerformancePoint FHD_30 = new PerformancePoint(1920, 1080, 30);
            public static final PerformancePoint FHD_50 = new PerformancePoint(1920, 1080, 50);
            public static final PerformancePoint FHD_60 = new PerformancePoint(1920, 1080, 60);
            public static final PerformancePoint FHD_100 = new PerformancePoint(1920, 1080, 100);
            public static final PerformancePoint FHD_120 = new PerformancePoint(1920, 1080, 120);
            public static final PerformancePoint FHD_200 = new PerformancePoint(1920, 1080, 200);
            public static final PerformancePoint FHD_240 = new PerformancePoint(1920, 1080, 240);
            public static final PerformancePoint UHD_24 = new PerformancePoint(3840, 2160, 24);
            public static final PerformancePoint UHD_25 = new PerformancePoint(3840, 2160, 25);
            public static final PerformancePoint UHD_30 = new PerformancePoint(3840, 2160, 30);
            public static final PerformancePoint UHD_50 = new PerformancePoint(3840, 2160, 50);
            public static final PerformancePoint UHD_60 = new PerformancePoint(3840, 2160, 60);
            public static final PerformancePoint UHD_100 = new PerformancePoint(3840, 2160, 100);
            public static final PerformancePoint UHD_120 = new PerformancePoint(3840, 2160, 120);
            public static final PerformancePoint UHD_200 = new PerformancePoint(3840, 2160, 200);
            public static final PerformancePoint UHD_240 = new PerformancePoint(3840, 2160, 240);

            private int saturateLongToInt(long j) {
                if (j < -2147483648L) {
                    return Integer.MIN_VALUE;
                }
                if (j > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) j;
            }

            public int getMaxMacroBlocks() {
                return saturateLongToInt(this.mWidth * this.mHeight);
            }

            public int getMaxFrameRate() {
                return this.mMaxFrameRate;
            }

            public long getMaxMacroBlockRate() {
                return this.mMaxMacroBlockRate;
            }

            public String toString() {
                int width = this.mBlockSize.getWidth() * 16;
                int height = this.mBlockSize.getHeight() * 16;
                int divUp = (int) Utils.divUp(this.mMaxMacroBlockRate, getMaxMacroBlocks());
                String str = (this.mWidth * 16) + "x" + (this.mHeight * 16) + "@" + divUp;
                if (divUp < this.mMaxFrameRate) {
                    str = str + ", max " + this.mMaxFrameRate + SemMediaPostProcessor.ProcessingFormat.Key.FPS;
                }
                if (width > 16 || height > 16) {
                    str = str + ", " + width + "x" + height + " blocks";
                }
                return "PerformancePoint(" + str + NavigationBarInflaterView.KEY_CODE_END;
            }

            public int hashCode() {
                return this.mMaxFrameRate;
            }

            public PerformancePoint(int i, int i2, int i3, int i4, Size size) {
                MediaCodecInfo.checkPowerOfTwo(size.getWidth(), "block width");
                MediaCodecInfo.checkPowerOfTwo(size.getHeight(), "block height");
                this.mBlockSize = new Size(Utils.divUp(size.getWidth(), 16), Utils.divUp(size.getHeight(), 16));
                this.mWidth = (int) (Utils.divUp(Math.max(1L, i), Math.max(size.getWidth(), 16)) * this.mBlockSize.getWidth());
                this.mHeight = (int) (Utils.divUp(Math.max(1L, i2), Math.max(size.getHeight(), 16)) * this.mBlockSize.getHeight());
                this.mMaxFrameRate = Math.max(1, Math.max(i3, i4));
                this.mMaxMacroBlockRate = Math.max(1, i3) * getMaxMacroBlocks();
            }

            public PerformancePoint(PerformancePoint performancePoint, Size size) {
                this(performancePoint.mWidth * 16, performancePoint.mHeight * 16, (int) Utils.divUp(performancePoint.mMaxMacroBlockRate, performancePoint.getMaxMacroBlocks()), performancePoint.mMaxFrameRate, new Size(Math.max(size.getWidth(), performancePoint.mBlockSize.getWidth() * 16), Math.max(size.getHeight(), performancePoint.mBlockSize.getHeight() * 16)));
            }

            public PerformancePoint(int i, int i2, int i3) {
                this(i, i2, i3, i3, new Size(16, 16));
            }

            private int align(int i, int i2) {
                return Utils.divUp(i, i2) * i2;
            }

            private void checkPowerOfTwo2(int i, String str) {
                if (i == 0 || ((i - 1) & i) != 0) {
                    throw new IllegalArgumentException(str + " (" + i + ") must be a power of 2");
                }
            }

            public boolean covers(MediaFormat mediaFormat) {
                return covers(new PerformancePoint(mediaFormat.getInteger("width", 0), mediaFormat.getInteger("height", 0), Math.round((float) Math.ceil(mediaFormat.getNumber(MediaFormat.KEY_FRAME_RATE, 0).doubleValue()))));
            }

            public boolean covers(PerformancePoint performancePoint) {
                Size commonBlockSize = getCommonBlockSize(performancePoint);
                PerformancePoint performancePoint2 = new PerformancePoint(this, commonBlockSize);
                PerformancePoint performancePoint3 = new PerformancePoint(performancePoint, commonBlockSize);
                return performancePoint2.getMaxMacroBlocks() >= performancePoint3.getMaxMacroBlocks() && performancePoint2.mMaxFrameRate >= performancePoint3.mMaxFrameRate && performancePoint2.mMaxMacroBlockRate >= performancePoint3.mMaxMacroBlockRate;
            }

            private Size getCommonBlockSize(PerformancePoint performancePoint) {
                return new Size(Math.max(this.mBlockSize.getWidth(), performancePoint.mBlockSize.getWidth()) * 16, Math.max(this.mBlockSize.getHeight(), performancePoint.mBlockSize.getHeight()) * 16);
            }

            public boolean equals(Object obj) {
                if (obj instanceof PerformancePoint) {
                    PerformancePoint performancePoint = (PerformancePoint) obj;
                    Size commonBlockSize = getCommonBlockSize(performancePoint);
                    PerformancePoint performancePoint2 = new PerformancePoint(this, commonBlockSize);
                    PerformancePoint performancePoint3 = new PerformancePoint(performancePoint, commonBlockSize);
                    if (performancePoint2.getMaxMacroBlocks() == performancePoint3.getMaxMacroBlocks() && performancePoint2.mMaxFrameRate == performancePoint3.mMaxFrameRate && performancePoint2.mMaxMacroBlockRate == performancePoint3.mMaxMacroBlockRate) {
                        return true;
                    }
                }
                return false;
            }
        }

        public List<PerformancePoint> getSupportedPerformancePoints() {
            return this.mPerformancePoints;
        }

        public boolean areSizeAndRateSupported(int i, int i2, double d) {
            return supports(Integer.valueOf(i), Integer.valueOf(i2), Double.valueOf(d));
        }

        public boolean isSizeSupported(int i, int i2) {
            return supports(Integer.valueOf(i), Integer.valueOf(i2), null);
        }

        private boolean supports(Integer num, Integer num2, Number number) {
            boolean z = false;
            boolean z2 = num == null || (this.mWidthRange.contains((Range<Integer>) num) && num.intValue() % this.mWidthAlignment == 0);
            if (z2 && num2 != null) {
                z2 = this.mHeightRange.contains((Range<Integer>) num2) && num2.intValue() % this.mHeightAlignment == 0;
            }
            if (z2 && number != null) {
                z2 = this.mFrameRateRange.contains(Utils.intRangeFor(number.doubleValue()));
            }
            if (!z2 || num2 == null || num == null) {
                return z2;
            }
            boolean z3 = Math.min(num2.intValue(), num.intValue()) <= this.mSmallerDimensionUpperLimit;
            int divUp = Utils.divUp(num.intValue(), this.mBlockWidth);
            int divUp2 = Utils.divUp(num2.intValue(), this.mBlockHeight);
            int i = divUp * divUp2;
            if (z3 && this.mBlockCountRange.contains((Range<Integer>) Integer.valueOf(i)) && this.mBlockAspectRatioRange.contains((Range<Rational>) new Rational(divUp, divUp2)) && this.mAspectRatioRange.contains((Range<Rational>) new Rational(num.intValue(), num2.intValue()))) {
                z = true;
            }
            if (!z || number == null) {
                return z;
            }
            return this.mBlocksPerSecondRange.contains(Utils.longRangeFor(i * number.doubleValue()));
        }

        public boolean supportsFormat(MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            return supports((Integer) map.get("width"), (Integer) map.get("height"), (Number) map.get(MediaFormat.KEY_FRAME_RATE)) && CodecCapabilities.supportsBitrate(this.mBitrateRange, mediaFormat);
        }

        private VideoCapabilities() {
        }

        public static VideoCapabilities create(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            VideoCapabilities videoCapabilities = new VideoCapabilities();
            videoCapabilities.init(mediaFormat, codecCapabilities);
            return videoCapabilities;
        }

        private void init(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            initWithPlatformLimits();
            applyLevelLimits();
            parseFromInfo(mediaFormat);
            updateLimits();
        }

        public Size getBlockSize() {
            return new Size(this.mBlockWidth, this.mBlockHeight);
        }

        public Range<Integer> getBlockCountRange() {
            return this.mBlockCountRange;
        }

        public Range<Long> getBlocksPerSecondRange() {
            return this.mBlocksPerSecondRange;
        }

        public Range<Rational> getAspectRatioRange(boolean z) {
            return z ? this.mBlockAspectRatioRange : this.mAspectRatioRange;
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = MediaCodecInfo.BITRATE_RANGE;
            this.mWidthRange = MediaCodecInfo.getSizeRange();
            this.mHeightRange = MediaCodecInfo.getSizeRange();
            this.mFrameRateRange = MediaCodecInfo.FRAME_RATE_RANGE;
            this.mHorizontalBlockRange = MediaCodecInfo.getSizeRange();
            this.mVerticalBlockRange = MediaCodecInfo.getSizeRange();
            this.mBlockCountRange = MediaCodecInfo.POSITIVE_INTEGERS;
            this.mBlocksPerSecondRange = MediaCodecInfo.POSITIVE_LONGS;
            this.mBlockAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS;
            this.mAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS;
            this.mWidthAlignment = 1;
            this.mHeightAlignment = 1;
            this.mBlockWidth = 1;
            this.mBlockHeight = 1;
            this.mSmallerDimensionUpperLimit = ((Integer) MediaCodecInfo.getSizeRange().getUpper()).intValue();
        }

        private List<PerformancePoint> getPerformancePoints(Map<String, Object> map) {
            Size parseSize;
            Range<Long> parseLongRange;
            Vector vector = new Vector();
            for (String str : map.keySet()) {
                if (str.startsWith("performance-point-")) {
                    if (str.substring(18).equals("none") && vector.size() == 0) {
                        return Collections.unmodifiableList(vector);
                    }
                    String[] split = str.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (split.length == 4 && (parseSize = Utils.parseSize(split[2], null)) != null && parseSize.getWidth() * parseSize.getHeight() > 0 && (parseLongRange = Utils.parseLongRange(map.get(str), null)) != null && parseLongRange.getLower().longValue() >= 0 && parseLongRange.getUpper().longValue() >= 0) {
                        PerformancePoint performancePoint = new PerformancePoint(parseSize.getWidth(), parseSize.getHeight(), parseLongRange.getLower().intValue(), parseLongRange.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                        PerformancePoint performancePoint2 = new PerformancePoint(parseSize.getHeight(), parseSize.getWidth(), parseLongRange.getLower().intValue(), parseLongRange.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                        vector.add(performancePoint);
                        if (!performancePoint.covers(performancePoint2)) {
                            vector.add(performancePoint2);
                        }
                    }
                }
            }
            if (vector.size() == 0) {
                return null;
            }
            vector.sort(new Comparator() { // from class: android.media.MediaCodecInfo$VideoCapabilities$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return MediaCodecInfo.VideoCapabilities.lambda$getPerformancePoints$0((MediaCodecInfo.VideoCapabilities.PerformancePoint) obj, (MediaCodecInfo.VideoCapabilities.PerformancePoint) obj2);
                }
            });
            return Collections.unmodifiableList(vector);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
        
            if (r6.getMaxMacroBlockRate() < r7.getMaxMacroBlockRate()) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0044, code lost:
        
            if (r6.getMaxFrameRate() < r7.getMaxFrameRate()) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:4:0x0014, code lost:
        
            if (r6.getMaxMacroBlocks() < r7.getMaxMacroBlocks()) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:5:0x0017, code lost:
        
            r2 = 1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        static /* synthetic */ int lambda$getPerformancePoints$0(android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint r6, android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint r7) {
            /*
                int r0 = r6.getMaxMacroBlocks()
                int r1 = r7.getMaxMacroBlocks()
                r2 = -1
                r3 = 1
                if (r0 == r1) goto L19
                int r6 = r6.getMaxMacroBlocks()
                int r7 = r7.getMaxMacroBlocks()
                if (r6 >= r7) goto L17
                goto L48
            L17:
                r2 = r3
                goto L48
            L19:
                long r0 = r6.getMaxMacroBlockRate()
                long r4 = r7.getMaxMacroBlockRate()
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L32
                long r0 = r6.getMaxMacroBlockRate()
                long r6 = r7.getMaxMacroBlockRate()
                int r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r6 >= 0) goto L17
                goto L48
            L32:
                int r0 = r6.getMaxFrameRate()
                int r1 = r7.getMaxFrameRate()
                if (r0 == r1) goto L47
                int r6 = r6.getMaxFrameRate()
                int r7 = r7.getMaxFrameRate()
                if (r6 >= r7) goto L17
                goto L48
            L47:
                r2 = 0
            L48:
                int r6 = -r2
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaCodecInfo.VideoCapabilities.lambda$getPerformancePoints$0(android.media.MediaCodecInfo$VideoCapabilities$PerformancePoint, android.media.MediaCodecInfo$VideoCapabilities$PerformancePoint):int");
        }

        private Map<Size, Range<Long>> getMeasuredFrameRates(Map<String, Object> map) {
            Size parseSize;
            Range<Long> parseLongRange;
            HashMap hashMap = new HashMap();
            for (String str : map.keySet()) {
                if (str.startsWith("measured-frame-rate-")) {
                    str.substring(20);
                    String[] split = str.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (split.length == 5 && (parseSize = Utils.parseSize(split[3], null)) != null && parseSize.getWidth() * parseSize.getHeight() > 0 && (parseLongRange = Utils.parseLongRange(map.get(str), null)) != null && parseLongRange.getLower().longValue() >= 0 && parseLongRange.getUpper().longValue() >= 0) {
                        hashMap.put(parseSize, parseLongRange);
                    }
                }
            }
            return hashMap;
        }

        private static Pair<Range<Integer>, Range<Integer>> parseWidthHeightRanges(Object obj) {
            Pair<Size, Size> parseSizeRange = Utils.parseSizeRange(obj);
            if (parseSizeRange == null) {
                return null;
            }
            try {
                return Pair.create(Range.create(Integer.valueOf(parseSizeRange.first.getWidth()), Integer.valueOf(parseSizeRange.second.getWidth())), Range.create(Integer.valueOf(parseSizeRange.first.getHeight()), Integer.valueOf(parseSizeRange.second.getHeight())));
            } catch (IllegalArgumentException unused) {
                Log.w(TAG, "could not parse size range '" + obj + "'");
                return null;
            }
        }

        public static int equivalentVP9Level(MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            Size parseSize = Utils.parseSize(map.get("block-size"), new Size(8, 8));
            int width = parseSize.getWidth() * parseSize.getHeight();
            Range<Integer> parseIntRange = Utils.parseIntRange(map.get("block-count-range"), null);
            int intValue = parseIntRange == null ? 0 : parseIntRange.getUpper().intValue() * width;
            Range<Long> parseLongRange = Utils.parseLongRange(map.get("blocks-per-second-range"), null);
            long longValue = parseLongRange == null ? 0L : width * parseLongRange.getUpper().longValue();
            Pair<Range<Integer>, Range<Integer>> parseWidthHeightRanges = parseWidthHeightRanges(map.get("size-range"));
            int max = parseWidthHeightRanges == null ? 0 : Math.max(parseWidthHeightRanges.first.getUpper().intValue(), parseWidthHeightRanges.second.getUpper().intValue());
            Range<Integer> parseIntRange2 = Utils.parseIntRange(map.get("bitrate-range"), null);
            int divUp = parseIntRange2 != null ? Utils.divUp(parseIntRange2.getUpper().intValue(), 1000) : 0;
            if (longValue <= 829440 && intValue <= 36864 && divUp <= 200 && max <= 512) {
                return 1;
            }
            if (longValue <= 2764800 && intValue <= 73728 && divUp <= 800 && max <= 768) {
                return 2;
            }
            if (longValue <= 4608000 && intValue <= 122880 && divUp <= 1800 && max <= 960) {
                return 4;
            }
            if (longValue <= 9216000 && intValue <= 245760 && divUp <= 3600 && max <= 1344) {
                return 8;
            }
            if (longValue <= 20736000 && intValue <= 552960 && divUp <= 7200 && max <= 2048) {
                return 16;
            }
            if (longValue <= 36864000 && intValue <= 983040 && divUp <= 12000 && max <= 2752) {
                return 32;
            }
            if (longValue <= 83558400 && intValue <= 2228224 && divUp <= 18000 && max <= 4160) {
                return 64;
            }
            if (longValue <= 160432128 && intValue <= 2228224 && divUp <= 30000 && max <= 4160) {
                return 128;
            }
            if (longValue <= 311951360 && intValue <= 8912896 && divUp <= 60000 && max <= 8384) {
                return 256;
            }
            if (longValue <= 588251136 && intValue <= 8912896 && divUp <= 120000 && max <= 8384) {
                return 512;
            }
            if (longValue <= 1176502272 && intValue <= 8912896 && divUp <= 180000 && max <= 8384) {
                return 1024;
            }
            if (longValue > 1176502272 || intValue > 35651584 || divUp > 180000 || max > 16832) {
                return (longValue > 2353004544L || intValue > 35651584 || divUp > 240000 || max > 16832) ? 8192 : 4096;
            }
            return 2048;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x013a  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0231  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x023d  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0249  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0268  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0288  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x02a6  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x02b2  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x02be  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0115 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:62:0x00e4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void parseFromInfo(android.media.MediaFormat r22) {
            /*
                Method dump skipped, instructions count: 733
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaCodecInfo.VideoCapabilities.parseFromInfo(android.media.MediaFormat):void");
        }

        private void applyBlockLimits(int i, int i2, Range<Integer> range, Range<Long> range2, Range<Rational> range3) {
            MediaCodecInfo.checkPowerOfTwo(i, "blockWidth must be a power of two");
            MediaCodecInfo.checkPowerOfTwo(i2, "blockHeight must be a power of two");
            int max = Math.max(i, this.mBlockWidth);
            int max2 = Math.max(i2, this.mBlockHeight);
            int i3 = max * max2;
            int i4 = (i3 / this.mBlockWidth) / this.mBlockHeight;
            if (i4 != 1) {
                this.mBlockCountRange = Utils.factorRange(this.mBlockCountRange, i4);
                this.mBlocksPerSecondRange = Utils.factorRange(this.mBlocksPerSecondRange, i4);
                this.mBlockAspectRatioRange = Utils.scaleRange(this.mBlockAspectRatioRange, max2 / this.mBlockHeight, max / this.mBlockWidth);
                this.mHorizontalBlockRange = Utils.factorRange(this.mHorizontalBlockRange, max / this.mBlockWidth);
                this.mVerticalBlockRange = Utils.factorRange(this.mVerticalBlockRange, max2 / this.mBlockHeight);
            }
            int i5 = (i3 / i) / i2;
            if (i5 != 1) {
                range = Utils.factorRange(range, i5);
                range2 = Utils.factorRange(range2, i5);
                range3 = Utils.scaleRange(range3, max2 / i2, max / i);
            }
            this.mBlockCountRange = this.mBlockCountRange.intersect(range);
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(range2);
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(range3);
            this.mBlockWidth = max;
            this.mBlockHeight = max2;
        }

        private void applyAlignment(int i, int i2) {
            VideoCapabilities videoCapabilities;
            MediaCodecInfo.checkPowerOfTwo(i, "widthAlignment must be a power of two");
            MediaCodecInfo.checkPowerOfTwo(i2, "heightAlignment must be a power of two");
            int i3 = this.mBlockWidth;
            if (i > i3 || i2 > this.mBlockHeight) {
                videoCapabilities = this;
                videoCapabilities.applyBlockLimits(Math.max(i, i3), Math.max(i2, this.mBlockHeight), MediaCodecInfo.POSITIVE_INTEGERS, MediaCodecInfo.POSITIVE_LONGS, MediaCodecInfo.POSITIVE_RATIONALS);
            } else {
                videoCapabilities = this;
            }
            videoCapabilities.mWidthAlignment = Math.max(i, videoCapabilities.mWidthAlignment);
            videoCapabilities.mHeightAlignment = Math.max(i2, videoCapabilities.mHeightAlignment);
            videoCapabilities.mWidthRange = Utils.alignRange(videoCapabilities.mWidthRange, videoCapabilities.mWidthAlignment);
            videoCapabilities.mHeightRange = Utils.alignRange(videoCapabilities.mHeightRange, videoCapabilities.mHeightAlignment);
        }

        private void updateLimits() {
            Range<Integer> intersect = this.mHorizontalBlockRange.intersect(Utils.factorRange(this.mWidthRange, this.mBlockWidth));
            this.mHorizontalBlockRange = intersect;
            this.mHorizontalBlockRange = intersect.intersect(Range.create(Integer.valueOf(this.mBlockCountRange.getLower().intValue() / this.mVerticalBlockRange.getUpper().intValue()), Integer.valueOf(this.mBlockCountRange.getUpper().intValue() / this.mVerticalBlockRange.getLower().intValue())));
            Range<Integer> intersect2 = this.mVerticalBlockRange.intersect(Utils.factorRange(this.mHeightRange, this.mBlockHeight));
            this.mVerticalBlockRange = intersect2;
            this.mVerticalBlockRange = intersect2.intersect(Range.create(Integer.valueOf(this.mBlockCountRange.getLower().intValue() / this.mHorizontalBlockRange.getUpper().intValue()), Integer.valueOf(this.mBlockCountRange.getUpper().intValue() / this.mHorizontalBlockRange.getLower().intValue())));
            this.mBlockCountRange = this.mBlockCountRange.intersect(Range.create(Integer.valueOf(this.mHorizontalBlockRange.getLower().intValue() * this.mVerticalBlockRange.getLower().intValue()), Integer.valueOf(this.mHorizontalBlockRange.getUpper().intValue() * this.mVerticalBlockRange.getUpper().intValue())));
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(new Rational(this.mHorizontalBlockRange.getLower().intValue(), this.mVerticalBlockRange.getUpper().intValue()), new Rational(this.mHorizontalBlockRange.getUpper().intValue(), this.mVerticalBlockRange.getLower().intValue()));
            this.mWidthRange = this.mWidthRange.intersect(Integer.valueOf(((this.mHorizontalBlockRange.getLower().intValue() - 1) * this.mBlockWidth) + this.mWidthAlignment), Integer.valueOf(this.mHorizontalBlockRange.getUpper().intValue() * this.mBlockWidth));
            this.mHeightRange = this.mHeightRange.intersect(Integer.valueOf(((this.mVerticalBlockRange.getLower().intValue() - 1) * this.mBlockHeight) + this.mHeightAlignment), Integer.valueOf(this.mVerticalBlockRange.getUpper().intValue() * this.mBlockHeight));
            this.mAspectRatioRange = this.mAspectRatioRange.intersect(new Rational(this.mWidthRange.getLower().intValue(), this.mHeightRange.getUpper().intValue()), new Rational(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getLower().intValue()));
            this.mSmallerDimensionUpperLimit = Math.min(this.mSmallerDimensionUpperLimit, Math.min(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getUpper().intValue()));
            Range<Long> intersect3 = this.mBlocksPerSecondRange.intersect(Long.valueOf(this.mBlockCountRange.getLower().intValue() * this.mFrameRateRange.getLower().intValue()), Long.valueOf(this.mBlockCountRange.getUpper().intValue() * this.mFrameRateRange.getUpper().intValue()));
            this.mBlocksPerSecondRange = intersect3;
            this.mFrameRateRange = this.mFrameRateRange.intersect(Integer.valueOf((int) (intersect3.getLower().longValue() / this.mBlockCountRange.getUpper().intValue())), Integer.valueOf((int) (this.mBlocksPerSecondRange.getUpper().longValue() / this.mBlockCountRange.getLower().intValue())));
        }

        private void applyMacroBlockLimits(int i, int i2, int i3, long j, int i4, int i5, int i6, int i7) {
            applyMacroBlockLimits(1, 1, i, i2, i3, j, i4, i5, i6, i7);
        }

        private void applyMacroBlockLimits(int i, int i2, int i3, int i4, int i5, long j, int i6, int i7, int i8, int i9) {
            applyAlignment(i8, i9);
            applyBlockLimits(i6, i7, Range.create(1, Integer.valueOf(i5)), Range.create(1L, Long.valueOf(j)), Range.create(new Rational(1, i4), new Rational(i3, 1)));
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Integer.valueOf(Utils.divUp(i, this.mBlockWidth / i6)), Integer.valueOf(i3 / (this.mBlockWidth / i6)));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Integer.valueOf(Utils.divUp(i2, this.mBlockHeight / i7)), Integer.valueOf(i4 / (this.mBlockHeight / i7)));
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:133:0x03c4. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:100:0x0337 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:31:0x019c  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x01a0 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0335  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void applyLevelLimits() {
            /*
                Method dump skipped, instructions count: 4448
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaCodecInfo.VideoCapabilities.applyLevelLimits():void");
        }
    }

    public static final class EncoderCapabilities {
        public static final int BITRATE_MODE_CBR = 2;
        public static final int BITRATE_MODE_CBR_FD = 3;
        public static final int BITRATE_MODE_CQ = 0;
        public static final int BITRATE_MODE_VBR = 1;
        private static final Feature[] bitrates = {new Feature("VBR", 1, true), new Feature("CBR", 2, false), new Feature("CQ", 0, false), new Feature("CBR-FD", 3, false)};
        private int mBitControl;
        private Range<Integer> mComplexityRange;
        private Integer mDefaultComplexity;
        private Integer mDefaultQuality;
        private CodecCapabilities mParent;
        private Range<Integer> mQualityRange;
        private String mQualityScale;

        public Range<Integer> getQualityRange() {
            return this.mQualityRange;
        }

        public Range<Integer> getComplexityRange() {
            return this.mComplexityRange;
        }

        private static int parseBitrateMode(String str) {
            for (Feature feature : bitrates) {
                if (feature.mName.equalsIgnoreCase(str)) {
                    return feature.mValue;
                }
            }
            return 0;
        }

        public boolean isBitrateModeSupported(int i) {
            for (Feature feature : bitrates) {
                if (i == feature.mValue) {
                    return (this.mBitControl & (1 << i)) != 0;
                }
            }
            return false;
        }

        private EncoderCapabilities() {
        }

        public static EncoderCapabilities create(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            EncoderCapabilities encoderCapabilities = new EncoderCapabilities();
            encoderCapabilities.init(mediaFormat, codecCapabilities);
            return encoderCapabilities;
        }

        private void init(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            this.mComplexityRange = Range.create(0, 0);
            this.mQualityRange = Range.create(0, 0);
            this.mBitControl = 2;
            applyLevelLimits();
            parseFromInfo(mediaFormat);
        }

        private void applyLevelLimits() {
            String mimeType = this.mParent.getMimeType();
            if (mimeType.equalsIgnoreCase("audio/flac")) {
                this.mComplexityRange = Range.create(0, 8);
                this.mBitControl = 1;
            } else if (mimeType.equalsIgnoreCase("audio/3gpp") || mimeType.equalsIgnoreCase("audio/amr-wb") || mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_ALAW) || mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_MLAW) || mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_MSGSM)) {
                this.mBitControl = 4;
            }
        }

        private void parseFromInfo(MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            if (mediaFormat.containsKey("complexity-range")) {
                this.mComplexityRange = Utils.parseIntRange(mediaFormat.getString("complexity-range"), this.mComplexityRange);
            }
            if (mediaFormat.containsKey("quality-range")) {
                this.mQualityRange = Utils.parseIntRange(mediaFormat.getString("quality-range"), this.mQualityRange);
            }
            if (mediaFormat.containsKey("feature-bitrate-modes")) {
                this.mBitControl = 0;
                for (String str : mediaFormat.getString("feature-bitrate-modes").split(",")) {
                    this.mBitControl = (1 << parseBitrateMode(str)) | this.mBitControl;
                }
            }
            try {
                this.mDefaultComplexity = Integer.valueOf(Integer.parseInt((String) map.get("complexity-default")));
            } catch (NumberFormatException unused) {
            }
            try {
                this.mDefaultQuality = Integer.valueOf(Integer.parseInt((String) map.get("quality-default")));
            } catch (NumberFormatException unused2) {
            }
            this.mQualityScale = (String) map.get("quality-scale");
        }

        private boolean supports(Integer num, Integer num2, Integer num3) {
            boolean contains = num != null ? this.mComplexityRange.contains((Range<Integer>) num) : true;
            if (contains && num2 != null) {
                contains = this.mQualityRange.contains((Range<Integer>) num2);
            }
            if (!contains || num3 == null) {
                return contains;
            }
            CodecProfileLevel[] codecProfileLevelArr = this.mParent.profileLevels;
            int length = codecProfileLevelArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (codecProfileLevelArr[i].profile == num3.intValue()) {
                    num3 = null;
                    break;
                }
                i++;
            }
            return num3 == null;
        }

        public void getDefaultFormat(MediaFormat mediaFormat) {
            Integer num;
            Integer num2;
            if (!this.mQualityRange.getUpper().equals(this.mQualityRange.getLower()) && (num2 = this.mDefaultQuality) != null) {
                mediaFormat.setInteger("quality", num2.intValue());
            }
            if (!this.mComplexityRange.getUpper().equals(this.mComplexityRange.getLower()) && (num = this.mDefaultComplexity) != null) {
                mediaFormat.setInteger(MediaFormat.KEY_COMPLEXITY, num.intValue());
            }
            for (Feature feature : bitrates) {
                if ((this.mBitControl & (1 << feature.mValue)) != 0) {
                    mediaFormat.setInteger(MediaFormat.KEY_BITRATE_MODE, feature.mValue);
                    return;
                }
            }
        }

        public boolean supportsFormat(MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            String mimeType = this.mParent.getMimeType();
            Integer num = (Integer) map.get(MediaFormat.KEY_BITRATE_MODE);
            if (num != null && !isBitrateModeSupported(num.intValue())) {
                return false;
            }
            Integer num2 = (Integer) map.get(MediaFormat.KEY_COMPLEXITY);
            if ("audio/flac".equalsIgnoreCase(mimeType)) {
                Integer num3 = (Integer) map.get(MediaFormat.KEY_FLAC_COMPRESSION_LEVEL);
                if (num2 == null) {
                    num2 = num3;
                } else if (num3 != null && !num2.equals(num3)) {
                    throw new IllegalArgumentException("conflicting values for complexity and flac-compression-level");
                }
            }
            Integer num4 = (Integer) map.get("profile");
            if ("audio/mp4a-latm".equalsIgnoreCase(mimeType)) {
                Integer num5 = (Integer) map.get(MediaFormat.KEY_AAC_PROFILE);
                if (num4 == null) {
                    num4 = num5;
                } else if (num5 != null && !num5.equals(num4)) {
                    throw new IllegalArgumentException("conflicting values for profile and aac-profile");
                }
            }
            return supports(num2, (Integer) map.get("quality"), num4);
        }
    }

    public static final class CodecProfileLevel {
        public static final int AACObjectELD = 39;
        public static final int AACObjectERLC = 17;
        public static final int AACObjectERScalable = 20;
        public static final int AACObjectHE = 5;
        public static final int AACObjectHE_PS = 29;
        public static final int AACObjectLC = 2;
        public static final int AACObjectLD = 23;
        public static final int AACObjectLTP = 4;
        public static final int AACObjectMain = 1;
        public static final int AACObjectSSR = 3;
        public static final int AACObjectScalable = 6;
        public static final int AACObjectXHE = 42;
        private static final int AC4BitstreamVersion0 = 1;
        private static final int AC4BitstreamVersion1 = 2;
        private static final int AC4BitstreamVersion2 = 4;
        public static final int AC4Level0 = 1;
        public static final int AC4Level1 = 2;
        public static final int AC4Level2 = 4;
        public static final int AC4Level3 = 8;
        public static final int AC4Level4 = 16;
        private static final int AC4PresentationVersion0 = 1;
        private static final int AC4PresentationVersion1 = 2;
        private static final int AC4PresentationVersion2 = 4;
        public static final int AC4Profile00 = 257;
        public static final int AC4Profile10 = 513;
        public static final int AC4Profile11 = 514;
        public static final int AC4Profile21 = 1026;
        public static final int AC4Profile22 = 1028;
        public static final int APVLevel11Band0 = 513;
        public static final int APVLevel11Band1 = 514;
        public static final int APVLevel11Band2 = 516;
        public static final int APVLevel11Band3 = 520;
        public static final int APVLevel1Band0 = 257;
        public static final int APVLevel1Band1 = 258;
        public static final int APVLevel1Band2 = 260;
        public static final int APVLevel1Band3 = 264;
        public static final int APVLevel21Band0 = 2049;
        public static final int APVLevel21Band1 = 2050;
        public static final int APVLevel21Band2 = 2052;
        public static final int APVLevel21Band3 = 2056;
        public static final int APVLevel2Band0 = 1025;
        public static final int APVLevel2Band1 = 1026;
        public static final int APVLevel2Band2 = 1028;
        public static final int APVLevel2Band3 = 1032;
        public static final int APVLevel31Band0 = 8193;
        public static final int APVLevel31Band1 = 8194;
        public static final int APVLevel31Band2 = 8196;
        public static final int APVLevel31Band3 = 8200;
        public static final int APVLevel3Band0 = 4097;
        public static final int APVLevel3Band1 = 4098;
        public static final int APVLevel3Band2 = 4100;
        public static final int APVLevel3Band3 = 4104;
        public static final int APVLevel41Band0 = 32769;
        public static final int APVLevel41Band1 = 32770;
        public static final int APVLevel41Band2 = 32772;
        public static final int APVLevel41Band3 = 32776;
        public static final int APVLevel4Band0 = 16385;
        public static final int APVLevel4Band1 = 16386;
        public static final int APVLevel4Band2 = 16388;
        public static final int APVLevel4Band3 = 16392;
        public static final int APVLevel51Band0 = 131073;
        public static final int APVLevel51Band1 = 131074;
        public static final int APVLevel51Band2 = 131076;
        public static final int APVLevel51Band3 = 131080;
        public static final int APVLevel5Band0 = 65537;
        public static final int APVLevel5Band1 = 65538;
        public static final int APVLevel5Band2 = 65540;
        public static final int APVLevel5Band3 = 65544;
        public static final int APVLevel61Band0 = 524289;
        public static final int APVLevel61Band1 = 524290;
        public static final int APVLevel61Band2 = 524292;
        public static final int APVLevel61Band3 = 524296;
        public static final int APVLevel6Band0 = 262145;
        public static final int APVLevel6Band1 = 262146;
        public static final int APVLevel6Band2 = 262148;
        public static final int APVLevel6Band3 = 262152;
        public static final int APVLevel71Band0 = 2097153;
        public static final int APVLevel71Band1 = 2097154;
        public static final int APVLevel71Band2 = 2097156;
        public static final int APVLevel71Band3 = 2097160;
        public static final int APVLevel7Band0 = 1048577;
        public static final int APVLevel7Band1 = 1048578;
        public static final int APVLevel7Band2 = 1048580;
        public static final int APVLevel7Band3 = 1048584;
        public static final int APVProfile422_10 = 1;
        public static final int APVProfile422_10HDR10 = 4096;
        public static final int APVProfile422_10HDR10Plus = 8192;
        public static final int AV1Level2 = 1;
        public static final int AV1Level21 = 2;
        public static final int AV1Level22 = 4;
        public static final int AV1Level23 = 8;
        public static final int AV1Level3 = 16;
        public static final int AV1Level31 = 32;
        public static final int AV1Level32 = 64;
        public static final int AV1Level33 = 128;
        public static final int AV1Level4 = 256;
        public static final int AV1Level41 = 512;
        public static final int AV1Level42 = 1024;
        public static final int AV1Level43 = 2048;
        public static final int AV1Level5 = 4096;
        public static final int AV1Level51 = 8192;
        public static final int AV1Level52 = 16384;
        public static final int AV1Level53 = 32768;
        public static final int AV1Level6 = 65536;
        public static final int AV1Level61 = 131072;
        public static final int AV1Level62 = 262144;
        public static final int AV1Level63 = 524288;
        public static final int AV1Level7 = 1048576;
        public static final int AV1Level71 = 2097152;
        public static final int AV1Level72 = 4194304;
        public static final int AV1Level73 = 8388608;
        public static final int AV1ProfileMain10 = 2;
        public static final int AV1ProfileMain10HDR10 = 4096;
        public static final int AV1ProfileMain10HDR10Plus = 8192;
        public static final int AV1ProfileMain8 = 1;
        public static final int AVCLevel1 = 1;
        public static final int AVCLevel11 = 4;
        public static final int AVCLevel12 = 8;
        public static final int AVCLevel13 = 16;
        public static final int AVCLevel1b = 2;
        public static final int AVCLevel2 = 32;
        public static final int AVCLevel21 = 64;
        public static final int AVCLevel22 = 128;
        public static final int AVCLevel3 = 256;
        public static final int AVCLevel31 = 512;
        public static final int AVCLevel32 = 1024;
        public static final int AVCLevel4 = 2048;
        public static final int AVCLevel41 = 4096;
        public static final int AVCLevel42 = 8192;
        public static final int AVCLevel5 = 16384;
        public static final int AVCLevel51 = 32768;
        public static final int AVCLevel52 = 65536;
        public static final int AVCLevel6 = 131072;
        public static final int AVCLevel61 = 262144;
        public static final int AVCLevel62 = 524288;
        public static final int AVCProfileBaseline = 1;
        public static final int AVCProfileConstrainedBaseline = 65536;
        public static final int AVCProfileConstrainedHigh = 524288;
        public static final int AVCProfileExtended = 4;
        public static final int AVCProfileHigh = 8;
        public static final int AVCProfileHigh10 = 16;
        public static final int AVCProfileHigh422 = 32;
        public static final int AVCProfileHigh444 = 64;
        public static final int AVCProfileMain = 2;
        public static final int DTS_HDProfileHRA = 1;
        public static final int DTS_HDProfileLBR = 2;
        public static final int DTS_HDProfileMA = 4;
        public static final int DTS_UHDProfileP1 = 1;
        public static final int DTS_UHDProfileP2 = 2;
        public static final int DolbyVisionLevel8k30 = 1024;
        public static final int DolbyVisionLevel8k60 = 2048;
        public static final int DolbyVisionLevelFhd24 = 4;
        public static final int DolbyVisionLevelFhd30 = 8;
        public static final int DolbyVisionLevelFhd60 = 16;
        public static final int DolbyVisionLevelHd24 = 1;
        public static final int DolbyVisionLevelHd30 = 2;
        public static final int DolbyVisionLevelUhd120 = 512;
        public static final int DolbyVisionLevelUhd24 = 32;
        public static final int DolbyVisionLevelUhd30 = 64;
        public static final int DolbyVisionLevelUhd48 = 128;
        public static final int DolbyVisionLevelUhd60 = 256;
        public static final int DolbyVisionProfileDvav110 = 1024;
        public static final int DolbyVisionProfileDvavPen = 2;
        public static final int DolbyVisionProfileDvavPer = 1;
        public static final int DolbyVisionProfileDvavSe = 512;
        public static final int DolbyVisionProfileDvheDen = 8;
        public static final int DolbyVisionProfileDvheDer = 4;
        public static final int DolbyVisionProfileDvheDtb = 128;
        public static final int DolbyVisionProfileDvheDth = 64;
        public static final int DolbyVisionProfileDvheDtr = 16;
        public static final int DolbyVisionProfileDvheSt = 256;
        public static final int DolbyVisionProfileDvheStn = 32;
        public static final int H263Level10 = 1;
        public static final int H263Level20 = 2;
        public static final int H263Level30 = 4;
        public static final int H263Level40 = 8;
        public static final int H263Level45 = 16;
        public static final int H263Level50 = 32;
        public static final int H263Level60 = 64;
        public static final int H263Level70 = 128;
        public static final int H263ProfileBackwardCompatible = 4;
        public static final int H263ProfileBaseline = 1;
        public static final int H263ProfileH320Coding = 2;
        public static final int H263ProfileHighCompression = 32;
        public static final int H263ProfileHighLatency = 256;
        public static final int H263ProfileISWV2 = 8;
        public static final int H263ProfileISWV3 = 16;
        public static final int H263ProfileInterlace = 128;
        public static final int H263ProfileInternet = 64;
        public static final int HEVCHighTierLevel1 = 2;
        public static final int HEVCHighTierLevel2 = 8;
        public static final int HEVCHighTierLevel21 = 32;
        public static final int HEVCHighTierLevel3 = 128;
        public static final int HEVCHighTierLevel31 = 512;
        public static final int HEVCHighTierLevel4 = 2048;
        public static final int HEVCHighTierLevel41 = 8192;
        public static final int HEVCHighTierLevel5 = 32768;
        public static final int HEVCHighTierLevel51 = 131072;
        public static final int HEVCHighTierLevel52 = 524288;
        public static final int HEVCHighTierLevel6 = 2097152;
        public static final int HEVCHighTierLevel61 = 8388608;
        public static final int HEVCHighTierLevel62 = 33554432;
        private static final int HEVCHighTierLevels = 44739242;
        public static final int HEVCMainTierLevel1 = 1;
        public static final int HEVCMainTierLevel2 = 4;
        public static final int HEVCMainTierLevel21 = 16;
        public static final int HEVCMainTierLevel3 = 64;
        public static final int HEVCMainTierLevel31 = 256;
        public static final int HEVCMainTierLevel4 = 1024;
        public static final int HEVCMainTierLevel41 = 4096;
        public static final int HEVCMainTierLevel5 = 16384;
        public static final int HEVCMainTierLevel51 = 65536;
        public static final int HEVCMainTierLevel52 = 262144;
        public static final int HEVCMainTierLevel6 = 1048576;
        public static final int HEVCMainTierLevel61 = 4194304;
        public static final int HEVCMainTierLevel62 = 16777216;
        public static final int HEVCProfileMain = 1;
        public static final int HEVCProfileMain10 = 2;
        public static final int HEVCProfileMain10HDR10 = 4096;
        public static final int HEVCProfileMain10HDR10Plus = 8192;
        public static final int HEVCProfileMainStill = 4;
        public static final int IAMFProfileBaseAac = 16908290;
        public static final int IAMFProfileBaseEnhancedAac = 17039362;
        public static final int IAMFProfileBaseEnhancedFlac = 17039364;
        public static final int IAMFProfileBaseEnhancedOpus = 17039361;
        public static final int IAMFProfileBaseEnhancedPcm = 17039368;
        public static final int IAMFProfileBaseFlac = 16908292;
        public static final int IAMFProfileBaseOpus = 16908289;
        public static final int IAMFProfileBasePcm = 16908296;
        public static final int IAMFProfileSimpleAac = 16842754;
        public static final int IAMFProfileSimpleFlac = 16842756;
        public static final int IAMFProfileSimpleOpus = 16842753;
        public static final int IAMFProfileSimplePcm = 16842760;
        private static final int IAMF_CODEC_AAC = 2;
        private static final int IAMF_CODEC_FLAC = 4;
        private static final int IAMF_CODEC_OPUS = 1;
        private static final int IAMF_CODEC_PCM = 8;
        private static final int IAMF_PROFILE_BASE = 131072;
        private static final int IAMF_PROFILE_BASE_ENHANCED = 262144;
        private static final int IAMF_PROFILE_SIMPLE = 65536;
        private static final int IAMF_v1 = 16777216;
        public static final int MPEG2LevelH14 = 2;
        public static final int MPEG2LevelHL = 3;
        public static final int MPEG2LevelHP = 4;
        public static final int MPEG2LevelLL = 0;
        public static final int MPEG2LevelML = 1;
        public static final int MPEG2Profile422 = 2;
        public static final int MPEG2ProfileHigh = 5;
        public static final int MPEG2ProfileMain = 1;
        public static final int MPEG2ProfileSNR = 3;
        public static final int MPEG2ProfileSimple = 0;
        public static final int MPEG2ProfileSpatial = 4;
        public static final int MPEG4Level0 = 1;
        public static final int MPEG4Level0b = 2;
        public static final int MPEG4Level1 = 4;
        public static final int MPEG4Level2 = 8;
        public static final int MPEG4Level3 = 16;
        public static final int MPEG4Level3b = 24;
        public static final int MPEG4Level4 = 32;
        public static final int MPEG4Level4a = 64;
        public static final int MPEG4Level5 = 128;
        public static final int MPEG4Level6 = 256;
        public static final int MPEG4ProfileAdvancedCoding = 4096;
        public static final int MPEG4ProfileAdvancedCore = 8192;
        public static final int MPEG4ProfileAdvancedRealTime = 1024;
        public static final int MPEG4ProfileAdvancedScalable = 16384;
        public static final int MPEG4ProfileAdvancedSimple = 32768;
        public static final int MPEG4ProfileBasicAnimated = 256;
        public static final int MPEG4ProfileCore = 4;
        public static final int MPEG4ProfileCoreScalable = 2048;
        public static final int MPEG4ProfileHybrid = 512;
        public static final int MPEG4ProfileMain = 8;
        public static final int MPEG4ProfileNbit = 16;
        public static final int MPEG4ProfileScalableTexture = 32;
        public static final int MPEG4ProfileSimple = 1;
        public static final int MPEG4ProfileSimpleFBA = 128;
        public static final int MPEG4ProfileSimpleFace = 64;
        public static final int MPEG4ProfileSimpleScalable = 2;
        public static final int VP8Level_Version0 = 1;
        public static final int VP8Level_Version1 = 2;
        public static final int VP8Level_Version2 = 4;
        public static final int VP8Level_Version3 = 8;
        public static final int VP8ProfileMain = 1;
        public static final int VP9Level1 = 1;
        public static final int VP9Level11 = 2;
        public static final int VP9Level2 = 4;
        public static final int VP9Level21 = 8;
        public static final int VP9Level3 = 16;
        public static final int VP9Level31 = 32;
        public static final int VP9Level4 = 64;
        public static final int VP9Level41 = 128;
        public static final int VP9Level5 = 256;
        public static final int VP9Level51 = 512;
        public static final int VP9Level52 = 1024;
        public static final int VP9Level6 = 2048;
        public static final int VP9Level61 = 4096;
        public static final int VP9Level62 = 8192;
        public static final int VP9Profile0 = 1;
        public static final int VP9Profile1 = 2;
        public static final int VP9Profile2 = 4;
        public static final int VP9Profile2HDR = 4096;
        public static final int VP9Profile2HDR10Plus = 16384;
        public static final int VP9Profile3 = 8;
        public static final int VP9Profile3HDR = 8192;
        public static final int VP9Profile3HDR10Plus = 32768;
        public int level;
        public int profile;

        public boolean equals(Object obj) {
            if (obj != null && (obj instanceof CodecProfileLevel)) {
                CodecProfileLevel codecProfileLevel = (CodecProfileLevel) obj;
                if (codecProfileLevel.profile == this.profile && codecProfileLevel.level == this.level) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Long.hashCode((this.profile << 32) | this.level);
        }
    }

    public final CodecCapabilities getCapabilitiesForType(String str) {
        CodecCapabilities codecCapabilities = this.mCaps.get(str);
        if (codecCapabilities == null) {
            throw new IllegalArgumentException("codec does not support type");
        }
        return codecCapabilities.dup();
    }

    public MediaCodecInfo makeRegular() {
        ArrayList arrayList = new ArrayList();
        for (CodecCapabilities codecCapabilities : this.mCaps.values()) {
            if (codecCapabilities.isRegular()) {
                arrayList.add(codecCapabilities);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList.size() == this.mCaps.size() ? this : new MediaCodecInfo(this.mName, this.mCanonicalName, this.mFlags, (CodecCapabilities[]) arrayList.toArray(new CodecCapabilities[arrayList.size()]));
    }
}
