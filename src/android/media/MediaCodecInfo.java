package android.media;

import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodecInfo;
import android.media.codec.Flags;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.os.Process;
import android.os.SystemProperties;
import android.sysprop.MediaProperties;
import android.text.Spanned;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.Protocol;
import com.samsung.android.media.SemExtendedFormat;
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
        Set<String> setKeySet = this.mCaps.keySet();
        String[] strArr = (String[]) setKeySet.toArray(new String[setKeySet.size()]);
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
        Range<Integer> rangeCreate;
        if (SIZE_RANGE == null) {
            if (Process.is64Bit()) {
                rangeCreate = Range.create(1, 32768);
            } else {
                rangeCreate = Range.create(1, MediaProperties.resolution_limit_32bit().orElse(4096));
            }
            SIZE_RANGE = rangeCreate;
        }
        return SIZE_RANGE;
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
                        return Boolean.valueOf(Flags.dynamicColorAspects());
                    }
                })) {
                    arrayList.add(new Feature(CodecCapabilities.FEATURE_DynamicColorAspects, 256, true));
                }
                if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodecInfo$CodecCapabilities$FeatureList$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return Boolean.valueOf(Flags.nullOutputSurface());
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
                        return Boolean.valueOf(Flags.hlgEditing());
                    }
                })) {
                    arrayList.add(new Feature(CodecCapabilities.FEATURE_HlgEditing, 64, true));
                }
                if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodecInfo$CodecCapabilities$FeatureList$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return Boolean.valueOf(Flags.regionOfInterest());
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
                CodecCapabilities codecCapabilitiesCreateFromProfileLevel = createFromProfileLevel(this.mMime, num2.intValue(), i);
                HashMap map2 = new HashMap(map);
                if (isVideo()) {
                    set = VideoCapabilities.VIDEO_LEVEL_CRITICAL_FORMAT_KEYS;
                } else {
                    set = isAudio() ? AudioCapabilities.AUDIO_LEVEL_CRITICAL_FORMAT_KEYS : null;
                }
                if (set != null && set.size() > 1 && codecCapabilitiesCreateFromProfileLevel != null) {
                    map2.keySet().retainAll(set);
                    if (!codecCapabilitiesCreateFromProfileLevel.isFormatSupported(new MediaFormat(map2))) {
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
            Integer numValueOf = (Integer) map.get(MediaFormat.KEY_MAX_BIT_RATE);
            Integer num = (Integer) map.get(MediaFormat.KEY_BIT_RATE);
            if (num != null) {
                numValueOf = numValueOf != null ? Integer.valueOf(Math.max(num.intValue(), numValueOf.intValue())) : num;
            }
            if (numValueOf == null || numValueOf.intValue() <= 0) {
                return true;
            }
            return range.contains((Range<Integer>) numValueOf);
        }

        /* JADX WARN: Removed duplicated region for block: B:48:0x009a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private boolean supportsProfileLevel(int i, Integer num) {
            for (CodecProfileLevel codecProfileLevel : this.profileLevels) {
                if (codecProfileLevel.profile == i) {
                    if (num == null || this.mMime.equalsIgnoreCase("audio/mp4a-latm") || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS) || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_HD) || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_UHD)) {
                        return true;
                    }
                    if ((!this.mMime.equalsIgnoreCase("video/3gpp") || codecProfileLevel.level == num.intValue() || codecProfileLevel.level != 16 || num.intValue() <= 1) && (!this.mMime.equalsIgnoreCase("video/mp4v-es") || codecProfileLevel.level == num.intValue() || codecProfileLevel.level != 4 || num.intValue() <= 1)) {
                        if (this.mMime.equalsIgnoreCase("video/hevc")) {
                            boolean z = (codecProfileLevel.level & 44739242) != 0;
                            if ((44739242 & num.intValue()) == 0 || z) {
                            }
                        } else if (codecProfileLevel.level >= num.intValue()) {
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
                AudioCapabilities audioCapabilitiesCreate = AudioCapabilities.create(mediaFormat2, this);
                this.mAudioCaps = audioCapabilitiesCreate;
                audioCapabilitiesCreate.getDefaultFormat(this.mDefaultFormat);
            } else if (this.mMime.toLowerCase().startsWith(BnRConstants.VIDEO_DIR_PATH) || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_IMAGE_ANDROID_HEIC)) {
                this.mVideoCaps = VideoCapabilities.create(mediaFormat2, this);
            }
            if (z) {
                EncoderCapabilities encoderCapabilitiesCreate = EncoderCapabilities.create(mediaFormat2, this);
                this.mEncoderCaps = encoderCapabilitiesCreate;
                encoderCapabilitiesCreate.getDefaultFormat(this.mDefaultFormat);
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
                int iIntValue = ((Integer) this.mInputChannelRanges[length].getUpper()).intValue();
                if (iIntValue > i) {
                    i = iIntValue;
                }
            }
            return i;
        }

        public int getMinInputChannelCount() {
            int i = 30;
            for (int length = this.mInputChannelRanges.length - 1; length >= 0; length--) {
                int iIntValue = ((Integer) this.mInputChannelRanges[length].getLower()).intValue();
                if (iIntValue < i) {
                    i = iIntValue;
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
                this.mSampleRates[i] = ((Integer) rangeArr[i].getLower()).intValue();
                i++;
            }
        }

        private void limitSampleRates(Range<Integer>[] rangeArr) {
            Utils.sortDistinctRanges(rangeArr);
            Range<Integer>[] rangeArrIntersectSortedDistinctRanges = Utils.intersectSortedDistinctRanges(this.mSampleRateRanges, rangeArr);
            this.mSampleRateRanges = rangeArrIntersectSortedDistinctRanges;
            for (Range<Integer> range : rangeArrIntersectSortedDistinctRanges) {
                if (!((Integer) range.getLower()).equals(range.getUpper())) {
                    this.mSampleRates = null;
                    return;
                }
            }
            createDiscreteSampleRates();
        }

        /* JADX WARN: Removed duplicated region for block: B:61:0x0292  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void applyLevelLimits() {
            int i;
            int[] iArr;
            Range<Integer> rangeCreate;
            int[] iArr2;
            Range<Integer> rangeCreate2;
            int i2;
            int i3;
            int[] iArr3;
            Range<Integer> rangeCreate3;
            int[] iArr4;
            CodecProfileLevel[] codecProfileLevelArr = this.mParent.profileLevels;
            String mimeType = this.mParent.getMimeType();
            char c = 5;
            int i4 = 44100;
            Range<Integer> rangeCreate4 = null;
            if (mimeType.equalsIgnoreCase("audio/mpeg")) {
                iArr = new int[]{8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000};
                rangeCreate = Range.create(8000, 320000);
                i = 2;
            } else {
                if (mimeType.equalsIgnoreCase("audio/3gpp")) {
                    iArr = new int[]{8000};
                    rangeCreate = Range.create(4750, 12200);
                } else if (mimeType.equalsIgnoreCase("audio/amr-wb")) {
                    iArr = new int[]{16000};
                    rangeCreate = Range.create(6600, 23850);
                } else if (mimeType.equalsIgnoreCase("audio/mp4a-latm")) {
                    iArr = new int[]{7350, 8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000, 64000, 88200, 96000};
                    rangeCreate = Range.create(8000, 510000);
                    i = 48;
                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_VORBIS)) {
                    i = 255;
                    rangeCreate = Range.create(32000, Integer.valueOf(Build.VERSION_CODES_FULL.ECLAIR));
                    iArr = null;
                    rangeCreate4 = Range.create(8000, 192000);
                } else {
                    if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_OPUS)) {
                        i = 255;
                        iArr4 = new int[]{8000, 12000, 16000, 24000, 48000};
                        rangeCreate = Range.create(6000, 510000);
                    } else if (!mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_RAW)) {
                        if (!mimeType.equalsIgnoreCase("audio/flac")) {
                            i = 30;
                            if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_ALAW) || mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_MLAW)) {
                                iArr = new int[]{8000};
                                rangeCreate = Range.create(64000, 64000);
                            } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_MSGSM)) {
                                iArr = new int[]{8000};
                                rangeCreate = Range.create(Integer.valueOf(EncodeConstants.BitRate.MM_AVG_FHD_DATARATE), Integer.valueOf(EncodeConstants.BitRate.MM_AVG_FHD_DATARATE));
                            } else {
                                if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AC3)) {
                                    i = 6;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_EAC3)) {
                                    i = 16;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_EAC3_JOC)) {
                                    iArr = new int[]{48000};
                                    rangeCreate = Range.create(32000, 6144000);
                                    i = 16;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AC4)) {
                                    iArr = new int[]{44100, 48000, 96000, 192000};
                                    rangeCreate = Range.create(16000, 2688000);
                                    i = 24;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS)) {
                                    iArr = new int[]{44100, 48000};
                                    rangeCreate = Range.create(96000, 1524000);
                                    i = 6;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_HD)) {
                                    int length = codecProfileLevelArr.length;
                                    int i5 = 0;
                                    int[] iArr5 = null;
                                    Range<Integer> range = null;
                                    while (i5 < length) {
                                        CodecProfileLevel codecProfileLevel = codecProfileLevelArr[i5];
                                        char c2 = c;
                                        int i6 = codecProfileLevel.profile;
                                        if (i6 == 1) {
                                            i3 = i4;
                                            iArr3 = new int[6];
                                            iArr3[0] = i3;
                                            iArr3[1] = 48000;
                                            iArr3[2] = 88200;
                                            iArr3[3] = 96000;
                                            iArr3[4] = 176400;
                                            iArr3[c2] = 192000;
                                            rangeCreate3 = Range.create(96000, 24500000);
                                        } else if (i6 == 2) {
                                            i3 = i4;
                                            iArr3 = new int[]{22050, 24000, i3, 48000};
                                            rangeCreate3 = Range.create(32000, 768000);
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
                                            rangeCreate3 = Range.create(96000, 24500000);
                                        }
                                        iArr5 = iArr3;
                                        range = rangeCreate3;
                                        i5++;
                                        c = c2;
                                        i4 = i3;
                                    }
                                    i = 8;
                                    iArr = iArr5;
                                    rangeCreate = range;
                                } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_DTS_UHD)) {
                                    int[] iArr6 = null;
                                    Range<Integer> range2 = null;
                                    for (CodecProfileLevel codecProfileLevel2 : codecProfileLevelArr) {
                                        int i7 = codecProfileLevel2.profile;
                                        if (i7 == 1) {
                                            iArr2 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                            rangeCreate2 = Range.create(96000, 24500000);
                                        } else if (i7 == 2) {
                                            iArr2 = new int[]{48000};
                                            rangeCreate2 = Range.create(96000, 768000);
                                            i2 = 10;
                                            Range<Integer> range3 = rangeCreate2;
                                            iArr6 = iArr2;
                                            i = i2;
                                            range2 = range3;
                                        } else {
                                            Log.w(TAG, "Unrecognized profile " + codecProfileLevel2.profile + " for " + mimeType);
                                            CodecCapabilities codecCapabilities2 = this.mParent;
                                            codecCapabilities2.mError = codecCapabilities2.mError | 1;
                                            iArr2 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                            rangeCreate2 = Range.create(96000, 24500000);
                                        }
                                        i2 = 32;
                                        Range<Integer> range32 = rangeCreate2;
                                        iArr6 = iArr2;
                                        i = i2;
                                        range2 = range32;
                                    }
                                    iArr = iArr6;
                                    rangeCreate = range2;
                                } else {
                                    Log.w(TAG, "Unsupported mime " + mimeType);
                                    CodecCapabilities codecCapabilities3 = this.mParent;
                                    codecCapabilities3.mError = codecCapabilities3.mError | 2;
                                }
                                iArr = null;
                                rangeCreate = null;
                            }
                        } else {
                            i = 255;
                            rangeCreate = null;
                            rangeCreate4 = Range.create(1, 655350);
                            iArr = null;
                        }
                    } else {
                        Range<Integer> rangeCreate5 = Range.create(1, 192000);
                        rangeCreate = Range.create(1, 10000000);
                        i = AudioSystem.OUT_CHANNEL_COUNT_MAX;
                        iArr4 = null;
                        rangeCreate4 = rangeCreate5;
                    }
                    iArr = iArr4;
                }
                i = 1;
            }
            if (iArr != null) {
                limitSampleRates(iArr);
            } else if (rangeCreate4 != null) {
                limitSampleRates(new Range[]{rangeCreate4});
            }
            applyLimits(new Range[]{Range.create(1, Integer.valueOf(i))}, rangeCreate);
        }

        private void applyLimits(Range<Integer>[] rangeArr, Range<Integer> range) {
            Range[] rangeArr2 = new Range[rangeArr.length];
            for (int i = 0; i < rangeArr.length; i++) {
                Integer num = (Integer) rangeArr[i].clamp(1);
                num.intValue();
                Integer num2 = (Integer) rangeArr[i].clamp(30);
                num2.intValue();
                rangeArr2[i] = Range.create(num, num2);
            }
            Utils.sortDistinctRanges(rangeArr2);
            this.mInputChannelRanges = Utils.intersectSortedDistinctRanges(rangeArr2, this.mInputChannelRanges);
            if (range != null) {
                this.mBitrateRange = this.mBitrateRange.intersect(range);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void parseFromInfo(MediaFormat mediaFormat) {
            Range[] rangeArr = {Range.create(1, 30)};
            Range rangeIntersect = MediaCodecInfo.POSITIVE_INTEGERS;
            if (mediaFormat.containsKey("sample-rate-ranges")) {
                String[] strArrSplit = mediaFormat.getString("sample-rate-ranges").split(",");
                Range[] rangeArr2 = new Range[strArrSplit.length];
                for (int i = 0; i < strArrSplit.length; i++) {
                    rangeArr2[i] = Utils.parseIntRange(strArrSplit[i], null);
                }
                limitSampleRates((Range<Integer>[]) rangeArr2);
            }
            if (mediaFormat.containsKey("channel-ranges")) {
                String[] strArrSplit2 = mediaFormat.getString("channel-ranges").split(",");
                rangeArr = new Range[strArrSplit2.length];
                for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                    rangeArr[i2] = Utils.parseIntRange(strArrSplit2[i2], null);
                }
            } else if (mediaFormat.containsKey("channel-range")) {
                rangeArr = new Range[]{Utils.parseIntRange(mediaFormat.getString("channel-range"), null)};
            } else if (mediaFormat.containsKey("max-channel-count")) {
                int intSafely = Utils.parseIntSafely(mediaFormat.getString("max-channel-count"), 30);
                if (intSafely == 0) {
                    rangeArr = new Range[]{Range.create(0, 0)};
                } else {
                    rangeArr = new Range[]{Range.create(1, Integer.valueOf(intSafely))};
                }
            } else if ((this.mParent.mError & 2) != 0) {
                rangeArr = new Range[]{Range.create(0, 0)};
            }
            if (mediaFormat.containsKey("bitrate-range")) {
                rangeIntersect = rangeIntersect.intersect(Utils.parseIntRange(mediaFormat.getString("bitrate-range"), rangeIntersect));
            }
            applyLimits(rangeArr, rangeIntersect);
        }

        public void getDefaultFormat(MediaFormat mediaFormat) {
            if (((Integer) this.mBitrateRange.getLower()).equals(this.mBitrateRange.getUpper())) {
                mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, ((Integer) this.mBitrateRange.getLower()).intValue());
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
                int iDivUp = Utils.divUp(i, this.mBlockHeight);
                double d = iDivUp;
                Range rangeIntersect = range.intersect(Integer.valueOf(((Math.max(Utils.divUp(((Integer) this.mBlockCountRange.getLower()).intValue(), iDivUp), (int) Math.ceil(((Rational) this.mBlockAspectRatioRange.getLower()).doubleValue() * d)) - 1) * this.mBlockWidth) + this.mWidthAlignment), Integer.valueOf(Math.min(((Integer) this.mBlockCountRange.getUpper()).intValue() / iDivUp, (int) (((Rational) this.mBlockAspectRatioRange.getUpper()).doubleValue() * d)) * this.mBlockWidth));
                if (i > this.mSmallerDimensionUpperLimit) {
                    rangeIntersect = rangeIntersect.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                double d2 = i;
                return rangeIntersect.intersect(Integer.valueOf((int) Math.ceil(((Rational) this.mAspectRatioRange.getLower()).doubleValue() * d2)), Integer.valueOf((int) (((Rational) this.mAspectRatioRange.getUpper()).doubleValue() * d2)));
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
                int iDivUp = Utils.divUp(i, this.mBlockWidth);
                double d = iDivUp;
                Range rangeIntersect = range.intersect(Integer.valueOf(((Math.max(Utils.divUp(((Integer) this.mBlockCountRange.getLower()).intValue(), iDivUp), (int) Math.ceil(d / ((Rational) this.mBlockAspectRatioRange.getUpper()).doubleValue())) - 1) * this.mBlockHeight) + this.mHeightAlignment), Integer.valueOf(Math.min(((Integer) this.mBlockCountRange.getUpper()).intValue() / iDivUp, (int) (d / ((Rational) this.mBlockAspectRatioRange.getLower()).doubleValue())) * this.mBlockHeight));
                if (i > this.mSmallerDimensionUpperLimit) {
                    rangeIntersect = rangeIntersect.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                double d2 = i;
                return rangeIntersect.intersect(Integer.valueOf((int) Math.ceil(d2 / ((Rational) this.mAspectRatioRange.getUpper()).doubleValue())), Integer.valueOf((int) (d2 / ((Rational) this.mAspectRatioRange.getLower()).doubleValue())));
            } catch (IllegalArgumentException unused) {
                Log.v(TAG, "could not get supported heights for " + i);
                throw new IllegalArgumentException("unsupported width");
            }
        }

        public Range<Double> getSupportedFrameRatesFor(int i, int i2) {
            if (!supports(Integer.valueOf(i), Integer.valueOf(i2), null)) {
                throw new IllegalArgumentException("unsupported size");
            }
            double dDivUp = Utils.divUp(i, this.mBlockWidth) * Utils.divUp(i2, this.mBlockHeight);
            return Range.create(Double.valueOf(Math.max(((Long) this.mBlocksPerSecondRange.getLower()).longValue() / dDivUp, ((Integer) this.mFrameRateRange.getLower()).intValue())), Double.valueOf(Math.min(((Long) this.mBlocksPerSecondRange.getUpper()).longValue() / dDivUp, ((Integer) this.mFrameRateRange.getUpper()).intValue())));
        }

        private int getBlockCount(int i, int i2) {
            return Utils.divUp(i, this.mBlockWidth) * Utils.divUp(i2, this.mBlockHeight);
        }

        private Size findClosestSize(int i, int i2) {
            int blockCount = getBlockCount(i, i2);
            Size size = null;
            int i3 = Integer.MAX_VALUE;
            for (Size size2 : this.mMeasuredFrameRates.keySet()) {
                int iAbs = Math.abs(blockCount - getBlockCount(size2.getWidth(), size2.getHeight()));
                if (iAbs < i3) {
                    size = size2;
                    i3 = iAbs;
                }
            }
            return size;
        }

        private Range<Double> estimateFrameRatesFor(int i, int i2) {
            Range<Long> range = this.mMeasuredFrameRates.get(findClosestSize(i, i2));
            double blockCount = getBlockCount(r0.getWidth(), r0.getHeight()) / Math.max(getBlockCount(i, i2), 1);
            Double dValueOf = Double.valueOf(blockCount);
            double dLongValue = ((Long) range.getLower()).longValue();
            dValueOf.getClass();
            Double dValueOf2 = Double.valueOf(dLongValue * blockCount);
            double dLongValue2 = ((Long) range.getUpper()).longValue();
            dValueOf.getClass();
            return Range.create(dValueOf2, Double.valueOf(dLongValue2 * blockCount));
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
                int iDivUp = (int) Utils.divUp(this.mMaxMacroBlockRate, getMaxMacroBlocks());
                String str = (this.mWidth * 16) + "x" + (this.mHeight * 16) + "@" + iDivUp;
                if (iDivUp < this.mMaxFrameRate) {
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
            boolean zContains = num == null || (this.mWidthRange.contains((Range<Integer>) num) && num.intValue() % this.mWidthAlignment == 0);
            if (zContains && num2 != null) {
                zContains = this.mHeightRange.contains((Range<Integer>) num2) && num2.intValue() % this.mHeightAlignment == 0;
            }
            if (zContains && number != null) {
                zContains = this.mFrameRateRange.contains(Utils.intRangeFor(number.doubleValue()));
            }
            if (!zContains || num2 == null || num == null) {
                return zContains;
            }
            boolean z2 = Math.min(num2.intValue(), num.intValue()) <= this.mSmallerDimensionUpperLimit;
            int iDivUp = Utils.divUp(num.intValue(), this.mBlockWidth);
            int iDivUp2 = Utils.divUp(num2.intValue(), this.mBlockHeight);
            int i = iDivUp * iDivUp2;
            if (z2 && this.mBlockCountRange.contains((Range<Integer>) Integer.valueOf(i)) && this.mBlockAspectRatioRange.contains((Range<Rational>) new Rational(iDivUp, iDivUp2)) && this.mAspectRatioRange.contains((Range<Rational>) new Rational(num.intValue(), num2.intValue()))) {
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

        public static VideoCapabilities create(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) throws NumberFormatException {
            VideoCapabilities videoCapabilities = new VideoCapabilities();
            videoCapabilities.init(mediaFormat, codecCapabilities);
            return videoCapabilities;
        }

        private void init(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) throws NumberFormatException {
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
            Size size;
            Range<Long> longRange;
            Vector vector = new Vector();
            for (String str : map.keySet()) {
                if (str.startsWith("performance-point-")) {
                    if (str.substring(18).equals("none") && vector.size() == 0) {
                        return Collections.unmodifiableList(vector);
                    }
                    String[] strArrSplit = str.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (strArrSplit.length == 4 && (size = Utils.parseSize(strArrSplit[2], null)) != null && size.getWidth() * size.getHeight() > 0 && (longRange = Utils.parseLongRange(map.get(str), null)) != null && ((Long) longRange.getLower()).longValue() >= 0 && ((Long) longRange.getUpper()).longValue() >= 0) {
                        PerformancePoint performancePoint = new PerformancePoint(size.getWidth(), size.getHeight(), ((Long) longRange.getLower()).intValue(), ((Long) longRange.getUpper()).intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                        PerformancePoint performancePoint2 = new PerformancePoint(size.getHeight(), size.getWidth(), ((Long) longRange.getLower()).intValue(), ((Long) longRange.getUpper()).intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
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

        /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        static /* synthetic */ int lambda$getPerformancePoints$0(PerformancePoint performancePoint, PerformancePoint performancePoint2) {
            int i = -1;
            if (performancePoint.getMaxMacroBlocks() != performancePoint2.getMaxMacroBlocks()) {
                if (performancePoint.getMaxMacroBlocks() >= performancePoint2.getMaxMacroBlocks()) {
                    i = 1;
                }
            } else if (performancePoint.getMaxMacroBlockRate() != performancePoint2.getMaxMacroBlockRate()) {
                if (performancePoint.getMaxMacroBlockRate() >= performancePoint2.getMaxMacroBlockRate()) {
                }
            } else if (performancePoint.getMaxFrameRate() == performancePoint2.getMaxFrameRate()) {
                i = 0;
            } else if (performancePoint.getMaxFrameRate() >= performancePoint2.getMaxFrameRate()) {
            }
            return -i;
        }

        private Map<Size, Range<Long>> getMeasuredFrameRates(Map<String, Object> map) {
            Size size;
            Range<Long> longRange;
            HashMap map2 = new HashMap();
            for (String str : map.keySet()) {
                if (str.startsWith("measured-frame-rate-")) {
                    str.substring(20);
                    String[] strArrSplit = str.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (strArrSplit.length == 5 && (size = Utils.parseSize(strArrSplit[3], null)) != null && size.getWidth() * size.getHeight() > 0 && (longRange = Utils.parseLongRange(map.get(str), null)) != null && ((Long) longRange.getLower()).longValue() >= 0 && ((Long) longRange.getUpper()).longValue() >= 0) {
                        map2.put(size, longRange);
                    }
                }
            }
            return map2;
        }

        private static Pair<Range<Integer>, Range<Integer>> parseWidthHeightRanges(Object obj) {
            Pair<Size, Size> sizeRange = Utils.parseSizeRange(obj);
            if (sizeRange == null) {
                return null;
            }
            try {
                return Pair.create(Range.create(Integer.valueOf(sizeRange.first.getWidth()), Integer.valueOf(sizeRange.second.getWidth())), Range.create(Integer.valueOf(sizeRange.first.getHeight()), Integer.valueOf(sizeRange.second.getHeight())));
            } catch (IllegalArgumentException unused) {
                Log.w(TAG, "could not parse size range '" + obj + "'");
                return null;
            }
        }

        public static int equivalentVP9Level(MediaFormat mediaFormat) throws NumberFormatException {
            Map<String, Object> map = mediaFormat.getMap();
            Size size = Utils.parseSize(map.get("block-size"), new Size(8, 8));
            int width = size.getWidth() * size.getHeight();
            Range<Integer> intRange = Utils.parseIntRange(map.get("block-count-range"), null);
            int iIntValue = intRange == null ? 0 : ((Integer) intRange.getUpper()).intValue() * width;
            Range<Long> longRange = Utils.parseLongRange(map.get("blocks-per-second-range"), null);
            long jLongValue = longRange == null ? 0L : width * ((Long) longRange.getUpper()).longValue();
            Pair<Range<Integer>, Range<Integer>> widthHeightRanges = parseWidthHeightRanges(map.get("size-range"));
            int iMax = widthHeightRanges == null ? 0 : Math.max(((Integer) widthHeightRanges.first.getUpper()).intValue(), ((Integer) widthHeightRanges.second.getUpper()).intValue());
            Range<Integer> intRange2 = Utils.parseIntRange(map.get("bitrate-range"), null);
            int iDivUp = intRange2 != null ? Utils.divUp(((Integer) intRange2.getUpper()).intValue(), 1000) : 0;
            if (jLongValue <= 829440 && iIntValue <= 36864 && iDivUp <= 200 && iMax <= 512) {
                return 1;
            }
            if (jLongValue <= 2764800 && iIntValue <= 73728 && iDivUp <= 800 && iMax <= 768) {
                return 2;
            }
            if (jLongValue <= 4608000 && iIntValue <= 122880 && iDivUp <= 1800 && iMax <= 960) {
                return 4;
            }
            if (jLongValue <= 9216000 && iIntValue <= 245760 && iDivUp <= 3600 && iMax <= 1344) {
                return 8;
            }
            if (jLongValue <= 20736000 && iIntValue <= 552960 && iDivUp <= 7200 && iMax <= 2048) {
                return 16;
            }
            if (jLongValue <= 36864000 && iIntValue <= 983040 && iDivUp <= 12000 && iMax <= 2752) {
                return 32;
            }
            if (jLongValue <= 83558400 && iIntValue <= 2228224 && iDivUp <= 18000 && iMax <= 4160) {
                return 64;
            }
            if (jLongValue <= 160432128 && iIntValue <= 2228224 && iDivUp <= 30000 && iMax <= 4160) {
                return 128;
            }
            if (jLongValue <= 311951360 && iIntValue <= 8912896 && iDivUp <= 60000 && iMax <= 8384) {
                return 256;
            }
            if (jLongValue <= 588251136 && iIntValue <= 8912896 && iDivUp <= 120000 && iMax <= 8384) {
                return 512;
            }
            if (jLongValue <= 1176502272 && iIntValue <= 8912896 && iDivUp <= 180000 && iMax <= 8384) {
                return 1024;
            }
            if (jLongValue > 1176502272 || iIntValue > 35651584 || iDivUp > 180000 || iMax > 16832) {
                return (jLongValue > 2353004544L || iIntValue > 35651584 || iDivUp > 240000 || iMax > 16832) ? 8192 : 4096;
            }
            return 2048;
        }

        private void parseFromInfo(MediaFormat mediaFormat) throws NumberFormatException {
            Range<Integer> range;
            Range<Integer> range2;
            Range<Integer> rangeExtend;
            Range<Integer> range3;
            Map<String, Object> map = mediaFormat.getMap();
            Size size = new Size(this.mBlockWidth, this.mBlockHeight);
            Size size2 = new Size(this.mWidthAlignment, this.mHeightAlignment);
            Size size3 = Utils.parseSize(map.get("block-size"), size);
            Size size4 = Utils.parseSize(map.get("alignment"), size2);
            Range rangeIntersect = null;
            Range<Integer> intRange = Utils.parseIntRange(map.get("block-count-range"), null);
            Range<Long> longRange = Utils.parseLongRange(map.get("blocks-per-second-range"), null);
            this.mMeasuredFrameRates = getMeasuredFrameRates(map);
            this.mPerformancePoints = getPerformancePoints(map);
            Pair<Range<Integer>, Range<Integer>> widthHeightRanges = parseWidthHeightRanges(map.get("size-range"));
            if (widthHeightRanges != null) {
                range2 = widthHeightRanges.first;
                range = widthHeightRanges.second;
            } else {
                range = null;
                range2 = null;
            }
            if (!map.containsKey("feature-can-swap-width-height")) {
                rangeExtend = range;
                range3 = range2;
            } else if (range2 != null) {
                this.mSmallerDimensionUpperLimit = Math.min(((Integer) range2.getUpper()).intValue(), ((Integer) range.getUpper()).intValue());
                rangeExtend = range2.extend(range);
                range3 = rangeExtend;
            } else {
                Log.w(TAG, "feature can-swap-width-height is best used with size-range");
                this.mSmallerDimensionUpperLimit = Math.min(((Integer) this.mWidthRange.getUpper()).intValue(), ((Integer) this.mHeightRange.getUpper()).intValue());
                Range rangeExtend2 = this.mWidthRange.extend(this.mHeightRange);
                this.mHeightRange = rangeExtend2;
                this.mWidthRange = rangeExtend2;
                rangeExtend = range;
                range3 = range2;
            }
            Range<Rational> rationalRange = Utils.parseRationalRange(map.get("block-aspect-ratio-range"), null);
            Range<Rational> rationalRange2 = Utils.parseRationalRange(map.get("pixel-aspect-ratio-range"), null);
            Range<Integer> intRange2 = Utils.parseIntRange(map.get("frame-rate-range"), null);
            if (intRange2 != null) {
                try {
                    intRange2 = intRange2.intersect(MediaCodecInfo.FRAME_RATE_RANGE);
                } catch (IllegalArgumentException unused) {
                    Log.w(TAG, "frame rate range (" + intRange2 + ") is out of limits: " + MediaCodecInfo.FRAME_RATE_RANGE);
                    intRange2 = null;
                }
            }
            Range<Integer> intRange3 = Utils.parseIntRange(map.get("bitrate-range"), null);
            if (intRange3 != null) {
                try {
                    rangeIntersect = intRange3.intersect(MediaCodecInfo.BITRATE_RANGE);
                } catch (IllegalArgumentException unused2) {
                    Log.w(TAG, "bitrate range (" + intRange3 + ") is out of limits: " + MediaCodecInfo.BITRATE_RANGE);
                }
            } else {
                rangeIntersect = intRange3;
            }
            MediaCodecInfo.checkPowerOfTwo(size3.getWidth(), "block-size width must be power of two");
            MediaCodecInfo.checkPowerOfTwo(size3.getHeight(), "block-size height must be power of two");
            MediaCodecInfo.checkPowerOfTwo(size4.getWidth(), "alignment width must be power of two");
            MediaCodecInfo.checkPowerOfTwo(size4.getHeight(), "alignment height must be power of two");
            Range range4 = rangeIntersect;
            Range<Integer> range5 = intRange2;
            applyMacroBlockLimits(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, size3.getWidth(), size3.getHeight(), size4.getWidth(), size4.getHeight());
            if ((this.mParent.mError & 2) != 0 || this.mAllowMbOverride) {
                if (range3 != null) {
                    this.mWidthRange = MediaCodecInfo.getSizeRange().intersect(range3);
                }
                if (rangeExtend != null) {
                    this.mHeightRange = MediaCodecInfo.getSizeRange().intersect(rangeExtend);
                }
                if (intRange != null) {
                    this.mBlockCountRange = MediaCodecInfo.POSITIVE_INTEGERS.intersect(Utils.factorRange(intRange, ((this.mBlockWidth * this.mBlockHeight) / size3.getWidth()) / size3.getHeight()));
                }
                if (longRange != null) {
                    this.mBlocksPerSecondRange = MediaCodecInfo.POSITIVE_LONGS.intersect(Utils.factorRange(longRange, ((this.mBlockWidth * this.mBlockHeight) / size3.getWidth()) / size3.getHeight()));
                }
                if (rationalRange2 != null) {
                    this.mBlockAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS.intersect(Utils.scaleRange(rationalRange2, this.mBlockHeight / size3.getHeight(), this.mBlockWidth / size3.getWidth()));
                }
                if (rationalRange != null) {
                    this.mAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS.intersect(rationalRange);
                }
                if (range5 != null) {
                    this.mFrameRateRange = MediaCodecInfo.FRAME_RATE_RANGE.intersect(range5);
                }
                if (range4 != null) {
                    if ((this.mParent.mError & 2) != 0) {
                        this.mBitrateRange = MediaCodecInfo.BITRATE_RANGE.intersect(range4);
                    } else {
                        this.mBitrateRange = this.mBitrateRange.intersect(range4);
                    }
                }
            } else {
                if (range3 != null) {
                    this.mWidthRange = this.mWidthRange.intersect(range3);
                }
                if (rangeExtend != null) {
                    this.mHeightRange = this.mHeightRange.intersect(rangeExtend);
                }
                if (intRange != null) {
                    this.mBlockCountRange = this.mBlockCountRange.intersect(Utils.factorRange(intRange, ((this.mBlockWidth * this.mBlockHeight) / size3.getWidth()) / size3.getHeight()));
                }
                if (longRange != null) {
                    this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(Utils.factorRange(longRange, ((this.mBlockWidth * this.mBlockHeight) / size3.getWidth()) / size3.getHeight()));
                }
                if (rationalRange2 != null) {
                    this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(Utils.scaleRange(rationalRange2, this.mBlockHeight / size3.getHeight(), this.mBlockWidth / size3.getWidth()));
                }
                if (rationalRange != null) {
                    this.mAspectRatioRange = this.mAspectRatioRange.intersect(rationalRange);
                }
                if (range5 != null) {
                    this.mFrameRateRange = this.mFrameRateRange.intersect(range5);
                }
                if (range4 != null) {
                    this.mBitrateRange = this.mBitrateRange.intersect(range4);
                }
            }
            updateLimits();
        }

        private void applyBlockLimits(int i, int i2, Range<Integer> range, Range<Long> range2, Range<Rational> range3) {
            MediaCodecInfo.checkPowerOfTwo(i, "blockWidth must be a power of two");
            MediaCodecInfo.checkPowerOfTwo(i2, "blockHeight must be a power of two");
            int iMax = Math.max(i, this.mBlockWidth);
            int iMax2 = Math.max(i2, this.mBlockHeight);
            int i3 = iMax * iMax2;
            int i4 = (i3 / this.mBlockWidth) / this.mBlockHeight;
            if (i4 != 1) {
                this.mBlockCountRange = Utils.factorRange(this.mBlockCountRange, i4);
                this.mBlocksPerSecondRange = Utils.factorRange(this.mBlocksPerSecondRange, i4);
                this.mBlockAspectRatioRange = Utils.scaleRange(this.mBlockAspectRatioRange, iMax2 / this.mBlockHeight, iMax / this.mBlockWidth);
                this.mHorizontalBlockRange = Utils.factorRange(this.mHorizontalBlockRange, iMax / this.mBlockWidth);
                this.mVerticalBlockRange = Utils.factorRange(this.mVerticalBlockRange, iMax2 / this.mBlockHeight);
            }
            int i5 = (i3 / i) / i2;
            if (i5 != 1) {
                range = Utils.factorRange(range, i5);
                range2 = Utils.factorRange(range2, i5);
                range3 = Utils.scaleRange(range3, iMax2 / i2, iMax / i);
            }
            this.mBlockCountRange = this.mBlockCountRange.intersect(range);
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(range2);
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(range3);
            this.mBlockWidth = iMax;
            this.mBlockHeight = iMax2;
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
            Range rangeIntersect = this.mHorizontalBlockRange.intersect(Utils.factorRange(this.mWidthRange, this.mBlockWidth));
            this.mHorizontalBlockRange = rangeIntersect;
            this.mHorizontalBlockRange = rangeIntersect.intersect(Range.create(Integer.valueOf(((Integer) this.mBlockCountRange.getLower()).intValue() / ((Integer) this.mVerticalBlockRange.getUpper()).intValue()), Integer.valueOf(((Integer) this.mBlockCountRange.getUpper()).intValue() / ((Integer) this.mVerticalBlockRange.getLower()).intValue())));
            Range rangeIntersect2 = this.mVerticalBlockRange.intersect(Utils.factorRange(this.mHeightRange, this.mBlockHeight));
            this.mVerticalBlockRange = rangeIntersect2;
            this.mVerticalBlockRange = rangeIntersect2.intersect(Range.create(Integer.valueOf(((Integer) this.mBlockCountRange.getLower()).intValue() / ((Integer) this.mHorizontalBlockRange.getUpper()).intValue()), Integer.valueOf(((Integer) this.mBlockCountRange.getUpper()).intValue() / ((Integer) this.mHorizontalBlockRange.getLower()).intValue())));
            this.mBlockCountRange = this.mBlockCountRange.intersect(Range.create(Integer.valueOf(((Integer) this.mHorizontalBlockRange.getLower()).intValue() * ((Integer) this.mVerticalBlockRange.getLower()).intValue()), Integer.valueOf(((Integer) this.mHorizontalBlockRange.getUpper()).intValue() * ((Integer) this.mVerticalBlockRange.getUpper()).intValue())));
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(new Rational(((Integer) this.mHorizontalBlockRange.getLower()).intValue(), ((Integer) this.mVerticalBlockRange.getUpper()).intValue()), new Rational(((Integer) this.mHorizontalBlockRange.getUpper()).intValue(), ((Integer) this.mVerticalBlockRange.getLower()).intValue()));
            this.mWidthRange = this.mWidthRange.intersect(Integer.valueOf(((((Integer) this.mHorizontalBlockRange.getLower()).intValue() - 1) * this.mBlockWidth) + this.mWidthAlignment), Integer.valueOf(((Integer) this.mHorizontalBlockRange.getUpper()).intValue() * this.mBlockWidth));
            this.mHeightRange = this.mHeightRange.intersect(Integer.valueOf(((((Integer) this.mVerticalBlockRange.getLower()).intValue() - 1) * this.mBlockHeight) + this.mHeightAlignment), Integer.valueOf(((Integer) this.mVerticalBlockRange.getUpper()).intValue() * this.mBlockHeight));
            this.mAspectRatioRange = this.mAspectRatioRange.intersect(new Rational(((Integer) this.mWidthRange.getLower()).intValue(), ((Integer) this.mHeightRange.getUpper()).intValue()), new Rational(((Integer) this.mWidthRange.getUpper()).intValue(), ((Integer) this.mHeightRange.getLower()).intValue()));
            this.mSmallerDimensionUpperLimit = Math.min(this.mSmallerDimensionUpperLimit, Math.min(((Integer) this.mWidthRange.getUpper()).intValue(), ((Integer) this.mHeightRange.getUpper()).intValue()));
            Range rangeIntersect3 = this.mBlocksPerSecondRange.intersect(Long.valueOf(((Integer) this.mBlockCountRange.getLower()).intValue() * ((Integer) this.mFrameRateRange.getLower()).intValue()), Long.valueOf(((Integer) this.mBlockCountRange.getUpper()).intValue() * ((Integer) this.mFrameRateRange.getUpper()).intValue()));
            this.mBlocksPerSecondRange = rangeIntersect3;
            this.mFrameRateRange = this.mFrameRateRange.intersect(Integer.valueOf((int) (((Long) rangeIntersect3.getLower()).longValue() / ((Integer) this.mBlockCountRange.getUpper()).intValue())), Integer.valueOf((int) (((Long) this.mBlocksPerSecondRange.getUpper()).longValue() / ((Integer) this.mBlockCountRange.getLower()).intValue())));
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

        /* JADX WARN: Failed to find 'out' block for switch in B:125:0x03c4. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:114:0x0335  */
        /* JADX WARN: Removed duplicated region for block: B:406:0x0cb9  */
        /* JADX WARN: Removed duplicated region for block: B:497:0x01a0 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:500:0x0337 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:58:0x015e  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0193  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x019c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void applyLevelLimits() {
            VideoCapabilities videoCapabilities;
            Integer num;
            int iMax;
            int i;
            int i2;
            long j;
            long j2;
            int i3;
            int i4;
            int i5;
            CodecProfileLevel[] codecProfileLevelArr;
            int i6;
            int i7;
            int i8;
            long j3;
            int i9;
            int i10;
            int i11;
            int i12;
            long j4;
            int i13;
            long j5;
            int i14;
            double d;
            int i15;
            int i16;
            int i17;
            int i18;
            int i19;
            int iMax2;
            long j6;
            int i20;
            int i21;
            int i22;
            int i23;
            int i24;
            int i25;
            int i26;
            CodecProfileLevel[] codecProfileLevelArr2;
            Integer num2;
            int i27;
            int i28;
            int i29;
            int i30;
            String str;
            String str2;
            CodecProfileLevel[] codecProfileLevelArr3;
            String str3;
            int i31;
            int i32;
            int i33;
            int i34;
            int i35;
            int i36;
            int i37;
            int i38;
            int i39;
            int i40;
            boolean z;
            String str4;
            int i41;
            int i42;
            int i43;
            int i44;
            int i45;
            int i46;
            String str5;
            String str6;
            int i47;
            String str7;
            int i48;
            String str8;
            int i49;
            int i50;
            int i51;
            int i52;
            int i53;
            boolean z2;
            boolean z3;
            int i54;
            int i55;
            int i56;
            int i57;
            int i58;
            int i59;
            int i60;
            int iMax3;
            int iMax4;
            int i61;
            int i62;
            int i63;
            String str9;
            String str10;
            Integer num3;
            int i64;
            int i65;
            int i66;
            int i67;
            boolean z4;
            int i68;
            int i69;
            int i70;
            int i71;
            int i72;
            int i73;
            int i74;
            int i75;
            int i76;
            int i77;
            boolean z5;
            int i78;
            int i79;
            int i80;
            int i81;
            int i82;
            int i83;
            CodecProfileLevel[] codecProfileLevelArr4 = this.mParent.profileLevels;
            String mimeType = this.mParent.getMimeType();
            boolean zEqualsIgnoreCase = mimeType.equalsIgnoreCase("video/avc");
            String str11 = "Unrecognized profile ";
            int i84 = 2;
            String str12 = " for ";
            String str13 = TAG;
            int i85 = 1;
            Integer num4 = 1;
            if (zEqualsIgnoreCase) {
                int length = codecProfileLevelArr4.length;
                long jMax = 1485;
                iMax = 64000;
                int i86 = 0;
                i = 4;
                int iMax5 = 99;
                int iMax6 = 396;
                while (i86 < length) {
                    CodecProfileLevel codecProfileLevel = codecProfileLevelArr4[i86];
                    int i87 = codecProfileLevel.level;
                    if (i87 == i85) {
                        i71 = 1485;
                        i72 = 99;
                        i73 = 64;
                    } else if (i87 != i84) {
                        switch (i87) {
                            case 4:
                                i71 = 3000;
                                i79 = 192;
                                i80 = 900;
                                i73 = i79;
                                i74 = i80;
                                i72 = 396;
                                break;
                            case 8:
                                i71 = 6000;
                                i79 = 384;
                                i80 = 2376;
                                i73 = i79;
                                i74 = i80;
                                i72 = 396;
                                break;
                            case 16:
                                i71 = 11880;
                                i79 = 768;
                                i80 = 2376;
                                i73 = i79;
                                i74 = i80;
                                i72 = 396;
                                break;
                            case 32:
                                i71 = 11880;
                                i79 = 2000;
                                i80 = 2376;
                                i73 = i79;
                                i74 = i80;
                                i72 = 396;
                                break;
                            case 64:
                                i71 = 19800;
                                i72 = 792;
                                i81 = 4000;
                                i82 = 4752;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 128:
                                i71 = 20250;
                                i72 = 1620;
                                i81 = 4000;
                                i82 = 8100;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 256:
                                i71 = 40500;
                                i72 = 1620;
                                i81 = 10000;
                                i82 = 8100;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 512:
                                i71 = 108000;
                                i72 = 3600;
                                i81 = 14000;
                                i82 = EncodeConstants.BitRate.MM_AVG_QHD_DATARATE;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 1024:
                                i71 = 216000;
                                i72 = 5120;
                                i81 = 20000;
                                i82 = 20480;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 2048:
                                i71 = 245760;
                                i83 = 20000;
                                i73 = i83;
                                i74 = 32768;
                                i72 = 8192;
                                break;
                            case 4096:
                                i71 = 245760;
                                i83 = 50000;
                                i73 = i83;
                                i74 = 32768;
                                i72 = 8192;
                                break;
                            case 8192:
                                i71 = 522240;
                                i72 = 8704;
                                i81 = 50000;
                                i82 = GLES20.GL_STENCIL_BACK_FUNC;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 16384:
                                i71 = 589824;
                                i72 = 22080;
                                i81 = 135000;
                                i82 = 110400;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 32768:
                                i71 = 983040;
                                i72 = 36864;
                                i81 = 240000;
                                i82 = 184320;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 65536:
                                i71 = 2073600;
                                i72 = 36864;
                                i81 = 240000;
                                i82 = 184320;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 131072:
                                i71 = 4177920;
                                i72 = Protocol.BASE_WIFI_P2P_MANAGER;
                                i81 = 240000;
                                i82 = 696320;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 262144:
                                i71 = 8355840;
                                i72 = Protocol.BASE_WIFI_P2P_MANAGER;
                                i81 = 480000;
                                i82 = 696320;
                                i73 = i81;
                                i74 = i82;
                                break;
                            case 524288:
                                i71 = Spanned.SPAN_PRIORITY;
                                i72 = Protocol.BASE_WIFI_P2P_MANAGER;
                                i81 = Build.VERSION_CODES_FULL.FROYO;
                                i82 = 696320;
                                i73 = i81;
                                i74 = i82;
                                break;
                            default:
                                Log.w(TAG, "Unrecognized level " + codecProfileLevel.level + " for " + mimeType);
                                i |= 1;
                                i71 = 0;
                                i72 = 0;
                                i73 = 0;
                                i74 = 0;
                                break;
                        }
                        i75 = codecProfileLevel.profile;
                        if (i75 != i85 || i75 == 2) {
                            i76 = length;
                            i77 = i73;
                        } else {
                            if (i75 != 4) {
                                if (i75 == 8) {
                                    i78 = i73 * 1250;
                                    i76 = length;
                                    z5 = true;
                                } else {
                                    if (i75 == 16) {
                                        i78 = i73 * 3000;
                                    } else if (i75 != 32 && i75 != 64) {
                                        if (i75 == 65536) {
                                            i77 = i73;
                                            i76 = length;
                                        } else if (i75 != 524288) {
                                            Log.w(TAG, "Unrecognized profile " + codecProfileLevel.profile + " for " + mimeType);
                                            i |= 1;
                                            i78 = i73 * 1000;
                                        }
                                    }
                                    i76 = length;
                                    z5 = true;
                                }
                                if (!z5) {
                                    i &= -5;
                                }
                                jMax = Math.max(i71, jMax);
                                iMax5 = Math.max(i72, iMax5);
                                iMax = Math.max(i78, iMax);
                                iMax6 = Math.max(iMax6, i74);
                                i86++;
                                length = i76;
                                i84 = 2;
                                i85 = 1;
                            }
                            i77 = i73;
                            i76 = length;
                            Log.w(TAG, "Unsupported profile " + codecProfileLevel.profile + " for " + mimeType);
                            i |= 2;
                            z5 = false;
                            i78 = i77 * 1000;
                            if (!z5) {
                            }
                            jMax = Math.max(i71, jMax);
                            iMax5 = Math.max(i72, iMax5);
                            iMax = Math.max(i78, iMax);
                            iMax6 = Math.max(iMax6, i74);
                            i86++;
                            length = i76;
                            i84 = 2;
                            i85 = 1;
                        }
                        z5 = true;
                        i78 = i77 * 1000;
                        if (!z5) {
                        }
                        jMax = Math.max(i71, jMax);
                        iMax5 = Math.max(i72, iMax5);
                        iMax = Math.max(i78, iMax);
                        iMax6 = Math.max(iMax6, i74);
                        i86++;
                        length = i76;
                        i84 = 2;
                        i85 = 1;
                    } else {
                        i71 = 1485;
                        i72 = 99;
                        i73 = 128;
                    }
                    i74 = 396;
                    i75 = codecProfileLevel.profile;
                    if (i75 != i85) {
                        i76 = length;
                        i77 = i73;
                        z5 = true;
                        i78 = i77 * 1000;
                    }
                    if (!z5) {
                    }
                    jMax = Math.max(i71, jMax);
                    iMax5 = Math.max(i72, iMax5);
                    iMax = Math.max(i78, iMax);
                    iMax6 = Math.max(iMax6, i74);
                    i86++;
                    length = i76;
                    i84 = 2;
                    i85 = 1;
                }
                int iSqrt = (int) Math.sqrt(r3 * 8);
                videoCapabilities = this;
                videoCapabilities.applyMacroBlockLimits(iSqrt, iSqrt, iMax5, jMax, 16, 16, 1, 1);
                num = num4;
            } else if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_MPEG2)) {
                int length2 = codecProfileLevelArr4.length;
                int iMax7 = 64000;
                int iMax8 = 15;
                long jMax2 = 1485;
                int iMax9 = 99;
                int i88 = 0;
                int iMax10 = 9;
                int iMax11 = 11;
                i = 4;
                while (i88 < length2) {
                    CodecProfileLevel codecProfileLevel2 = codecProfileLevelArr4[i88];
                    int i89 = length2;
                    int i90 = codecProfileLevel2.profile;
                    if (i90 != 0) {
                        i61 = i88;
                        if (i90 == 1) {
                            int i91 = codecProfileLevel2.level;
                            if (i91 == 0) {
                                str9 = str12;
                                str10 = str13;
                                num3 = num4;
                                i64 = 11880;
                                i62 = 22;
                                i66 = 4000;
                                i67 = 30;
                                i63 = 18;
                                i65 = 396;
                            } else if (i91 != 1) {
                                if (i91 != 2) {
                                    if (i91 == 3) {
                                        i67 = 60;
                                        i62 = 120;
                                        i63 = 68;
                                        i68 = 244800;
                                    } else if (i91 != 4) {
                                        Log.w(str13, "Unrecognized profile/level " + codecProfileLevel2.profile + "/" + codecProfileLevel2.level + str12 + mimeType);
                                    } else {
                                        i67 = 60;
                                        i62 = 120;
                                        i63 = 68;
                                        i68 = 489600;
                                    }
                                    i69 = 8160;
                                    i70 = 80000;
                                } else {
                                    i67 = 60;
                                    i62 = 90;
                                    i63 = 68;
                                    i68 = 183600;
                                    i69 = 6120;
                                    i70 = 60000;
                                }
                                str9 = str12;
                                str10 = str13;
                                num3 = num4;
                                i64 = i68;
                                i65 = i69;
                                i66 = i70;
                            } else {
                                i62 = 45;
                                i63 = 36;
                                str9 = str12;
                                str10 = str13;
                                num3 = num4;
                                i64 = 40500;
                                i65 = 1620;
                                i66 = 15000;
                                i67 = 30;
                            }
                        } else if (i90 == 2 || i90 == 3 || i90 == 4 || i90 == 5) {
                            Log.i(str13, "Unsupported profile " + codecProfileLevel2.profile + str12 + mimeType);
                            i |= 2;
                            str9 = str12;
                            str10 = str13;
                            num3 = num4;
                            i67 = 0;
                            i62 = 0;
                            i63 = 0;
                            i66 = 0;
                            i65 = 0;
                            i64 = 0;
                            z4 = false;
                            if (!z4) {
                                i &= -5;
                            }
                            jMax2 = Math.max(i64, jMax2);
                            iMax9 = Math.max(i65, iMax9);
                            iMax7 = Math.max(i66 * 1000, iMax7);
                            iMax11 = Math.max(i62, iMax11);
                            iMax10 = Math.max(i63, iMax10);
                            iMax8 = Math.max(i67, iMax8);
                            i88 = i61 + 1;
                            length2 = i89;
                            num4 = num3;
                            str13 = str10;
                            str12 = str9;
                            codecProfileLevelArr4 = codecProfileLevelArr4;
                            mimeType = mimeType;
                        } else {
                            Log.w(str13, "Unrecognized profile " + codecProfileLevel2.profile + str12 + mimeType);
                        }
                        i |= 1;
                        str9 = str12;
                        str10 = str13;
                        num3 = num4;
                        i67 = 0;
                        i62 = 0;
                        i63 = 0;
                        i66 = 0;
                        i65 = 0;
                        i64 = 0;
                    } else {
                        i61 = i88;
                        if (codecProfileLevel2.level != 1) {
                            Log.w(str13, "Unrecognized profile/level " + codecProfileLevel2.profile + "/" + codecProfileLevel2.level + str12 + mimeType);
                            i |= 1;
                            str9 = str12;
                            str10 = str13;
                            num3 = num4;
                            i67 = 0;
                            i62 = 0;
                            i63 = 0;
                            i66 = 0;
                            i65 = 0;
                            i64 = 0;
                        }
                        i62 = 45;
                        i63 = 36;
                        str9 = str12;
                        str10 = str13;
                        num3 = num4;
                        i64 = 40500;
                        i65 = 1620;
                        i66 = 15000;
                        i67 = 30;
                    }
                    z4 = true;
                    if (!z4) {
                    }
                    jMax2 = Math.max(i64, jMax2);
                    iMax9 = Math.max(i65, iMax9);
                    iMax7 = Math.max(i66 * 1000, iMax7);
                    iMax11 = Math.max(i62, iMax11);
                    iMax10 = Math.max(i63, iMax10);
                    iMax8 = Math.max(i67, iMax8);
                    i88 = i61 + 1;
                    length2 = i89;
                    num4 = num3;
                    str13 = str10;
                    str12 = str9;
                    codecProfileLevelArr4 = codecProfileLevelArr4;
                    mimeType = mimeType;
                }
                num = num4;
                videoCapabilities = this;
                videoCapabilities.applyMacroBlockLimits(iMax11, iMax10, iMax9, jMax2, 16, 16, 1, 1);
                videoCapabilities.mFrameRateRange = videoCapabilities.mFrameRateRange.intersect(12, Integer.valueOf(iMax8));
                iMax = iMax7;
            } else {
                videoCapabilities = this;
                String str14 = " for ";
                String str15 = TAG;
                num = num4;
                if (mimeType.equalsIgnoreCase("video/mp4v-es")) {
                    int length3 = codecProfileLevelArr4.length;
                    long jMax3 = 1485;
                    CodecProfileLevel[] codecProfileLevelArr5 = codecProfileLevelArr4;
                    iMax2 = 64000;
                    int iMax12 = 11;
                    int i92 = 9;
                    int iMax13 = 99;
                    i = 4;
                    int i93 = 15;
                    int i94 = 0;
                    while (i94 < length3) {
                        CodecProfileLevel codecProfileLevel3 = codecProfileLevelArr5[i94];
                        int i95 = codecProfileLevel3.profile;
                        if (i95 == 1) {
                            i46 = length3;
                            str5 = str15;
                            str6 = str14;
                            int i96 = codecProfileLevel3.level;
                            if (i96 == 1) {
                                i47 = 15;
                                str7 = str11;
                                i48 = i94;
                                str8 = str5;
                                i49 = 1485;
                                i50 = 11;
                                i51 = 64;
                            } else if (i96 == 2) {
                                i47 = 15;
                                str7 = str11;
                                i48 = i94;
                                str8 = str5;
                                i49 = 1485;
                                i50 = 11;
                                i51 = 128;
                            } else if (i96 == 4) {
                                str7 = str11;
                                i48 = i94;
                                str8 = str5;
                                i49 = 1485;
                                i47 = 30;
                                i50 = 11;
                                i51 = 64;
                                i52 = 99;
                                i53 = 9;
                                z2 = true;
                                z3 = false;
                            } else if (i96 == 8) {
                                str7 = str11;
                                i48 = i94;
                                str8 = str5;
                                i49 = 5940;
                                i50 = 22;
                                i47 = 30;
                                i51 = 128;
                                i52 = 396;
                                i53 = 18;
                                z2 = true;
                                z3 = false;
                            } else if (i96 == 16) {
                                i54 = 11880;
                                i60 = 384;
                                str7 = str11;
                                i48 = i94;
                                str8 = str5;
                                i49 = i54;
                                i50 = 22;
                                i52 = 396;
                                i53 = 18;
                                z2 = true;
                                z3 = false;
                                i51 = i60;
                                i47 = 30;
                            } else if (i96 == 64) {
                                i55 = 40;
                                str7 = str11;
                                str8 = str5;
                                i52 = 1200;
                                z2 = true;
                                z3 = false;
                                i51 = 4000;
                                i48 = i94;
                                i49 = 36000;
                                i53 = 30;
                                i50 = i55;
                                i47 = 30;
                            } else if (i96 == 128) {
                                i55 = 45;
                                i56 = 36;
                                i57 = 40500;
                                i58 = 1620;
                                i59 = 8000;
                                int i97 = i59;
                                str7 = str11;
                                i51 = i97;
                                str8 = str5;
                                i52 = i58;
                                z2 = true;
                                z3 = false;
                                i48 = i94;
                                i49 = i57;
                                i53 = i56;
                                i50 = i55;
                                i47 = 30;
                            } else if (i96 != 256) {
                                Log.w(str5, "Unrecognized profile/level " + codecProfileLevel3.profile + "/" + codecProfileLevel3.level + str6 + mimeType);
                                i |= 1;
                                str7 = str11;
                                i48 = i94;
                                str8 = str5;
                                i47 = 0;
                                i50 = 0;
                                i51 = 0;
                                i49 = 0;
                                i52 = 0;
                                i53 = 0;
                                z2 = true;
                                z3 = false;
                            } else {
                                i55 = 80;
                                i56 = 45;
                                i57 = 108000;
                                i58 = 3600;
                                i59 = 12000;
                                int i972 = i59;
                                str7 = str11;
                                i51 = i972;
                                str8 = str5;
                                i52 = i58;
                                z2 = true;
                                z3 = false;
                                i48 = i94;
                                i49 = i57;
                                i53 = i56;
                                i50 = i55;
                                i47 = 30;
                            }
                            i52 = 99;
                            i53 = 9;
                            z2 = true;
                            z3 = true;
                        } else {
                            if (i95 != 2) {
                                switch (i95) {
                                    case 4:
                                    case 8:
                                    case 16:
                                    case 32:
                                    case 64:
                                    case 128:
                                    case 256:
                                    case 512:
                                    case 1024:
                                    case 2048:
                                    case 4096:
                                    case 8192:
                                    case 16384:
                                        break;
                                    case 32768:
                                        str5 = str15;
                                        str6 = str14;
                                        int i98 = codecProfileLevel3.level;
                                        i46 = length3;
                                        if (i98 == 1 || i98 == 4) {
                                            str7 = str11;
                                            i48 = i94;
                                            str8 = str5;
                                            i49 = 2970;
                                            i47 = 30;
                                            i50 = 11;
                                            i51 = 128;
                                            i52 = 99;
                                            i53 = 9;
                                            z2 = true;
                                            z3 = false;
                                            break;
                                        } else if (i98 != 8) {
                                            if (i98 == 16) {
                                                i54 = 11880;
                                                i60 = 768;
                                            } else if (i98 == 24) {
                                                i54 = 11880;
                                                i60 = 1500;
                                            } else if (i98 == 32) {
                                                i55 = 44;
                                                i56 = 36;
                                                i57 = 23760;
                                                i58 = 792;
                                                i59 = 3000;
                                                int i9722 = i59;
                                                str7 = str11;
                                                i51 = i9722;
                                                str8 = str5;
                                                i52 = i58;
                                                z2 = true;
                                                z3 = false;
                                                i48 = i94;
                                                i49 = i57;
                                                i53 = i56;
                                                i50 = i55;
                                                i47 = 30;
                                            } else if (i98 == 128) {
                                                i55 = 45;
                                                i56 = 36;
                                                i57 = 48600;
                                                i58 = 1620;
                                                i59 = 8000;
                                                int i97222 = i59;
                                                str7 = str11;
                                                i51 = i97222;
                                                str8 = str5;
                                                i52 = i58;
                                                z2 = true;
                                                z3 = false;
                                                i48 = i94;
                                                i49 = i57;
                                                i53 = i56;
                                                i50 = i55;
                                                i47 = 30;
                                                break;
                                            } else {
                                                Log.w(str5, "Unrecognized profile/level " + codecProfileLevel3.profile + "/" + codecProfileLevel3.level + str6 + mimeType);
                                                i |= 1;
                                                str7 = str11;
                                                i48 = i94;
                                                str8 = str5;
                                                i47 = 0;
                                                i50 = 0;
                                                i51 = 0;
                                                i49 = 0;
                                                i52 = 0;
                                                i53 = 0;
                                                z2 = true;
                                                z3 = false;
                                            }
                                            str7 = str11;
                                            i48 = i94;
                                            str8 = str5;
                                            i49 = i54;
                                            i50 = 22;
                                            i52 = 396;
                                            i53 = 18;
                                            z2 = true;
                                            z3 = false;
                                            i51 = i60;
                                            i47 = 30;
                                        } else {
                                            i54 = 5940;
                                            i60 = 384;
                                            str7 = str11;
                                            i48 = i94;
                                            str8 = str5;
                                            i49 = i54;
                                            i50 = 22;
                                            i52 = 396;
                                            i53 = 18;
                                            z2 = true;
                                            z3 = false;
                                            i51 = i60;
                                            i47 = 30;
                                        }
                                        break;
                                    default:
                                        StringBuilder sb = new StringBuilder(str11);
                                        sb.append(codecProfileLevel3.profile);
                                        str6 = str14;
                                        sb.append(str6);
                                        sb.append(mimeType);
                                        String str16 = str15;
                                        Log.w(str16, sb.toString());
                                        i |= 1;
                                        i46 = length3;
                                        str7 = str11;
                                        i48 = i94;
                                        str8 = str16;
                                        i47 = 0;
                                        i50 = 0;
                                        i51 = 0;
                                        i49 = 0;
                                        i52 = 0;
                                        i53 = 0;
                                        z2 = true;
                                        z3 = false;
                                        break;
                                }
                            }
                            i46 = length3;
                            String str17 = str15;
                            str6 = str14;
                            Log.i(str17, "Unsupported profile " + codecProfileLevel3.profile + str6 + mimeType);
                            i |= 2;
                            str7 = str11;
                            i48 = i94;
                            str8 = str17;
                            i47 = 0;
                            i50 = 0;
                            i51 = 0;
                            i49 = 0;
                            i52 = 0;
                            i53 = 0;
                            z2 = false;
                            z3 = false;
                        }
                        if (z2) {
                            i &= -5;
                        }
                        CodecProfileLevel[] codecProfileLevelArr6 = codecProfileLevelArr5;
                        int i99 = i92;
                        jMax3 = Math.max(i49, jMax3);
                        iMax13 = Math.max(i52, iMax13);
                        iMax2 = Math.max(i51 * 1000, iMax2);
                        if (z3) {
                            iMax12 = Math.max(i50, iMax12);
                            iMax3 = Math.max(i53, i99);
                            iMax4 = Math.max(i47, i93);
                        } else {
                            int iSqrt2 = (int) Math.sqrt(i52 * 2);
                            iMax12 = Math.max(iSqrt2, iMax12);
                            iMax3 = Math.max(iSqrt2, i99);
                            iMax4 = Math.max(Math.max(i47, 60), i93);
                        }
                        i93 = iMax4;
                        i92 = iMax3;
                        i94 = i48 + 1;
                        length3 = i46;
                        str11 = str7;
                        codecProfileLevelArr5 = codecProfileLevelArr6;
                        str15 = str8;
                        str14 = str6;
                    }
                    videoCapabilities = this;
                    videoCapabilities.applyMacroBlockLimits(iMax12, i92, iMax13, jMax3, 16, 16, 1, 1);
                    videoCapabilities.mFrameRateRange = videoCapabilities.mFrameRateRange.intersect(12, Integer.valueOf(i93));
                } else {
                    String str18 = str15;
                    String str19 = "Unrecognized profile ";
                    if (mimeType.equalsIgnoreCase("video/3gpp")) {
                        int length4 = codecProfileLevelArr4.length;
                        long jMax4 = 1485;
                        int iMax14 = 15;
                        int i100 = 16;
                        int iMax15 = 64000;
                        int iMin = 9;
                        int iMax16 = 99;
                        int i101 = 0;
                        int iMax17 = 11;
                        int iMax18 = 9;
                        i = 4;
                        CodecProfileLevel[] codecProfileLevelArr7 = codecProfileLevelArr4;
                        int iMin2 = 11;
                        while (i101 < length4) {
                            int i102 = length4;
                            CodecProfileLevel codecProfileLevel4 = codecProfileLevelArr7[i101];
                            int i103 = i100;
                            int i104 = codecProfileLevel4.level;
                            int i105 = i101;
                            if (i104 == 1) {
                                codecProfileLevelArr3 = codecProfileLevelArr7;
                                str3 = str18;
                                i31 = iMin2;
                                i32 = i31;
                                i33 = iMin;
                                i34 = i33;
                                i35 = 15;
                                i36 = i;
                                i37 = 1485;
                                i38 = 9;
                                i39 = 11;
                                i40 = 1;
                                z = true;
                            } else if (i104 != 2) {
                                if (i104 == 4) {
                                    codecProfileLevelArr3 = codecProfileLevelArr7;
                                    str3 = str18;
                                    i43 = 6;
                                } else if (i104 == 8) {
                                    codecProfileLevelArr3 = codecProfileLevelArr7;
                                    str3 = str18;
                                    i43 = 32;
                                } else if (i104 != 16) {
                                    if (i104 == 32) {
                                        str3 = str18;
                                        i32 = iMin2;
                                        i34 = iMin;
                                        codecProfileLevelArr3 = codecProfileLevelArr7;
                                        i35 = 60;
                                        i36 = i;
                                        i39 = 22;
                                        i37 = 19800;
                                        i38 = 18;
                                        i103 = 4;
                                        i40 = 64;
                                    } else if (i104 == 64) {
                                        str3 = str18;
                                        i32 = iMin2;
                                        i34 = iMin;
                                        codecProfileLevelArr3 = codecProfileLevelArr7;
                                        i35 = 60;
                                        i39 = 45;
                                        i36 = i;
                                        i37 = 40500;
                                        i38 = 18;
                                        i103 = 4;
                                        i40 = 128;
                                    } else if (i104 != 128) {
                                        str3 = str18;
                                        Log.w(str3, "Unrecognized profile/level " + codecProfileLevel4.profile + "/" + codecProfileLevel4.level + str14 + mimeType);
                                        i31 = iMin2;
                                        i32 = i31;
                                        i33 = iMin;
                                        i34 = i33;
                                        codecProfileLevelArr3 = codecProfileLevelArr7;
                                        i36 = i | 1;
                                        i37 = 0;
                                        i38 = 0;
                                        i39 = 0;
                                        i40 = 0;
                                        z = false;
                                        i35 = 0;
                                    } else {
                                        str3 = str18;
                                        i32 = iMin2;
                                        i34 = iMin;
                                        i35 = 60;
                                        i38 = 36;
                                        i37 = 81000;
                                        z = false;
                                        i31 = 1;
                                        i33 = 1;
                                        i36 = i;
                                        i40 = 256;
                                        codecProfileLevelArr3 = codecProfileLevelArr7;
                                        i39 = 45;
                                        i103 = 4;
                                    }
                                    z = false;
                                    i31 = 1;
                                    i33 = 1;
                                } else {
                                    str3 = str18;
                                    codecProfileLevelArr3 = codecProfileLevelArr7;
                                    boolean z6 = codecProfileLevel4.profile == 1 || codecProfileLevel4.profile == 4;
                                    if (z6) {
                                        i44 = iMin2;
                                        i45 = iMin;
                                    } else {
                                        i44 = 1;
                                        i103 = 4;
                                        i45 = 1;
                                    }
                                    i32 = iMin2;
                                    i34 = iMin;
                                    i33 = i45;
                                    i35 = 15;
                                    i37 = 1485;
                                    i38 = 9;
                                    z = z6;
                                    i31 = i44;
                                    i36 = i;
                                    i39 = 11;
                                    i40 = 2;
                                }
                                i31 = iMin2;
                                i32 = i31;
                                i33 = iMin;
                                i34 = i33;
                                i36 = i;
                                i37 = 11880;
                                i38 = 18;
                                z = true;
                                i35 = 30;
                                i40 = i43;
                                i39 = 22;
                            } else {
                                codecProfileLevelArr3 = codecProfileLevelArr7;
                                str3 = str18;
                                i31 = iMin2;
                                i32 = i31;
                                i33 = iMin;
                                i34 = i33;
                                i36 = i;
                                i39 = 22;
                                i37 = 5940;
                                i38 = 18;
                                i40 = 2;
                                z = true;
                                i35 = 30;
                            }
                            int i106 = codecProfileLevel4.profile;
                            int i107 = iMax14;
                            if (i106 == 1 || i106 == 2 || i106 == 4 || i106 == 8 || i106 == 16 || i106 == 32 || i106 == 64 || i106 == 128 || i106 == 256) {
                                str4 = str19;
                            } else {
                                str4 = str19;
                                Log.w(str3, str4 + codecProfileLevel4.profile + str14 + mimeType);
                                i36 |= 1;
                            }
                            if (z) {
                                i41 = 11;
                                i42 = 9;
                            } else {
                                videoCapabilities.mAllowMbOverride = true;
                                i41 = i31;
                                i42 = i33;
                            }
                            str18 = str3;
                            str19 = str4;
                            jMax4 = Math.max(i37, jMax4);
                            iMax16 = Math.max(i39 * i38, iMax16);
                            iMax15 = Math.max(64000 * i40, iMax15);
                            iMax17 = Math.max(i39, iMax17);
                            iMax18 = Math.max(i38, iMax18);
                            iMax14 = Math.max(i35, i107);
                            iMin2 = Math.min(i41, i32);
                            iMin = Math.min(i42, i34);
                            i101 = i105 + 1;
                            length4 = i102;
                            i100 = i103;
                            i = i36 & (-5);
                            codecProfileLevelArr7 = codecProfileLevelArr3;
                        }
                        int i108 = iMin;
                        int i109 = i100;
                        if (!videoCapabilities.mAllowMbOverride) {
                            videoCapabilities.mBlockAspectRatioRange = Range.create(new Rational(11, 9), new Rational(11, 9));
                        }
                        videoCapabilities.applyMacroBlockLimits(iMin2, i108, iMax17, iMax18, iMax16, jMax4, 16, 16, i109, i109);
                        videoCapabilities.mFrameRateRange = Range.create(num, Integer.valueOf(iMax14));
                        iMax = iMax15;
                    } else {
                        Integer num5 = num;
                        if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_VP8)) {
                            int length5 = codecProfileLevelArr4.length;
                            int i110 = 0;
                            i = 4;
                            while (i110 < length5) {
                                CodecProfileLevel codecProfileLevel5 = codecProfileLevelArr4[i110];
                                int i111 = codecProfileLevel5.level;
                                if (i111 == 1 || i111 == 2 || i111 == 4 || i111 == 8) {
                                    str = str18;
                                } else {
                                    str = str18;
                                    Log.w(str, "Unrecognized level " + codecProfileLevel5.level + str14 + mimeType);
                                    i |= 1;
                                }
                                if (codecProfileLevel5.profile != 1) {
                                    str2 = str19;
                                    Log.w(str, str2 + codecProfileLevel5.profile + str14 + mimeType);
                                    i |= 1;
                                } else {
                                    str2 = str19;
                                }
                                i &= -5;
                                i110++;
                                str19 = str2;
                                str18 = str;
                            }
                            videoCapabilities.applyMacroBlockLimits(32767, 32767, Integer.MAX_VALUE, 2147483647L, 16, 16, 1, 1);
                            num = num5;
                            iMax = 100000000;
                            videoCapabilities = this;
                        } else {
                            CodecProfileLevel[] codecProfileLevelArr8 = codecProfileLevelArr4;
                            if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_VP9)) {
                                int length6 = codecProfileLevelArr8.length;
                                long jMax5 = 829440;
                                int iMax19 = 36864;
                                iMax2 = 200000;
                                int iMax20 = 512;
                                int i112 = 0;
                                i = 4;
                                while (i112 < length6) {
                                    CodecProfileLevel codecProfileLevel6 = codecProfileLevelArr8[i112];
                                    int i113 = codecProfileLevel6.level;
                                    if (i113 == 1) {
                                        j6 = 829440;
                                        i20 = 36864;
                                        i21 = 200;
                                        i22 = 512;
                                    } else if (i113 != 2) {
                                        switch (i113) {
                                            case 4:
                                                j6 = 4608000;
                                                i20 = 122880;
                                                i21 = 1800;
                                                i22 = EncodeConstants.Resolution.MM_360_EXPORT_HEIGHT_960;
                                                break;
                                            case 8:
                                                j6 = 9216000;
                                                i20 = 245760;
                                                i21 = 3600;
                                                i22 = MetricsProto.MetricsEvent.NOTIFICATION_ZEN_MODE_DURATION_PROMPT;
                                                break;
                                            case 16:
                                                j6 = 20736000;
                                                i20 = 552960;
                                                i21 = 7200;
                                                i22 = 2048;
                                                break;
                                            case 32:
                                                j6 = 36864000;
                                                i20 = 983040;
                                                i21 = 12000;
                                                i22 = SemExtendedFormat.DataType.DUAL_SHOT_ZOOMINOUT_INFO;
                                                break;
                                            case 64:
                                                j6 = 83558400;
                                                i20 = 2228224;
                                                i21 = EncodeConstants.BitRate.MM_AVG_QHD_DATARATE;
                                                i22 = 4160;
                                                break;
                                            case 128:
                                                j6 = 160432128;
                                                i20 = 2228224;
                                                i21 = 30000;
                                                i22 = 4160;
                                                break;
                                            case 256:
                                                j6 = 311951360;
                                                i29 = 60000;
                                                codecProfileLevelArr2 = codecProfileLevelArr8;
                                                num2 = num5;
                                                i27 = 8384;
                                                i25 = i112;
                                                i26 = i29;
                                                i23 = length6;
                                                i24 = 8912896;
                                                break;
                                            case 512:
                                                j6 = 588251136;
                                                i29 = 120000;
                                                codecProfileLevelArr2 = codecProfileLevelArr8;
                                                num2 = num5;
                                                i27 = 8384;
                                                i25 = i112;
                                                i26 = i29;
                                                i23 = length6;
                                                i24 = 8912896;
                                                break;
                                            case 1024:
                                                j6 = 1176502272;
                                                i29 = 180000;
                                                codecProfileLevelArr2 = codecProfileLevelArr8;
                                                num2 = num5;
                                                i27 = 8384;
                                                i25 = i112;
                                                i26 = i29;
                                                i23 = length6;
                                                i24 = 8912896;
                                                break;
                                            case 2048:
                                                j6 = 1176502272;
                                                i30 = 180000;
                                                codecProfileLevelArr2 = codecProfileLevelArr8;
                                                num2 = num5;
                                                i27 = 16832;
                                                i25 = i112;
                                                i26 = i30;
                                                i23 = length6;
                                                i24 = 35651584;
                                                break;
                                            case 4096:
                                                j6 = 2353004544L;
                                                i30 = 240000;
                                                codecProfileLevelArr2 = codecProfileLevelArr8;
                                                num2 = num5;
                                                i27 = 16832;
                                                i25 = i112;
                                                i26 = i30;
                                                i23 = length6;
                                                i24 = 35651584;
                                                break;
                                            case 8192:
                                                j6 = 4706009088L;
                                                i30 = 480000;
                                                codecProfileLevelArr2 = codecProfileLevelArr8;
                                                num2 = num5;
                                                i27 = 16832;
                                                i25 = i112;
                                                i26 = i30;
                                                i23 = length6;
                                                i24 = 35651584;
                                                break;
                                            default:
                                                Log.w(str18, "Unrecognized level " + codecProfileLevel6.level + str14 + mimeType);
                                                i |= 1;
                                                j6 = 0;
                                                i23 = length6;
                                                codecProfileLevelArr2 = codecProfileLevelArr8;
                                                num2 = num5;
                                                i25 = i112;
                                                i24 = 0;
                                                i27 = 0;
                                                i26 = 0;
                                                break;
                                        }
                                        i28 = codecProfileLevel6.profile;
                                        int i114 = iMax20;
                                        if (i28 == 1 && i28 != 2 && i28 != 4 && i28 != 8 && i28 != 4096 && i28 != 8192 && i28 != 16384 && i28 != 32768) {
                                            Log.w(str18, str19 + codecProfileLevel6.profile + str14 + mimeType);
                                            i |= 1;
                                        }
                                        i &= -5;
                                        jMax5 = Math.max(j6, jMax5);
                                        iMax19 = Math.max(i24, iMax19);
                                        iMax2 = Math.max(i26 * 1000, iMax2);
                                        iMax20 = Math.max(i27, i114);
                                        i112 = i25 + 1;
                                        length6 = i23;
                                        num5 = num2;
                                        codecProfileLevelArr8 = codecProfileLevelArr2;
                                    } else {
                                        j6 = 2764800;
                                        i20 = 73728;
                                        i21 = 800;
                                        i22 = 768;
                                    }
                                    int i115 = i20;
                                    i23 = length6;
                                    i24 = i115;
                                    int i116 = i21;
                                    i25 = i112;
                                    i26 = i116;
                                    codecProfileLevelArr2 = codecProfileLevelArr8;
                                    num2 = num5;
                                    i27 = i22;
                                    i28 = codecProfileLevel6.profile;
                                    int i1142 = iMax20;
                                    if (i28 == 1) {
                                    }
                                    i &= -5;
                                    jMax5 = Math.max(j6, jMax5);
                                    iMax19 = Math.max(i24, iMax19);
                                    iMax2 = Math.max(i26 * 1000, iMax2);
                                    iMax20 = Math.max(i27, i1142);
                                    i112 = i25 + 1;
                                    length6 = i23;
                                    num5 = num2;
                                    codecProfileLevelArr8 = codecProfileLevelArr2;
                                }
                                num = num5;
                                int iDivUp = Utils.divUp(iMax20, 8);
                                videoCapabilities = this;
                                videoCapabilities.applyMacroBlockLimits(iDivUp, iDivUp, Utils.divUp(iMax19, 64), Utils.divUp(jMax5, 64L), 8, 8, 1, 1);
                            } else {
                                num = num5;
                                if (mimeType.equalsIgnoreCase("video/hevc")) {
                                    int length7 = codecProfileLevelArr8.length;
                                    iMax = 128000;
                                    i = 4;
                                    int iMax21 = 576;
                                    long jMax6 = 8640;
                                    int i117 = 0;
                                    while (i117 < length7) {
                                        CodecProfileLevel codecProfileLevel7 = codecProfileLevelArr8[i117];
                                        int i118 = codecProfileLevel7.level;
                                        if (i118 != 1 && i118 != 2) {
                                            switch (i118) {
                                                case 4:
                                                case 8:
                                                    d = 30.0d;
                                                    i18 = 122880;
                                                    i19 = 1500;
                                                    int i119 = i19;
                                                    i16 = i18;
                                                    i17 = i119;
                                                    i15 = i117;
                                                    break;
                                                case 16:
                                                case 32:
                                                    d = 30.0d;
                                                    i18 = 245760;
                                                    i19 = 3000;
                                                    int i1192 = i19;
                                                    i16 = i18;
                                                    i17 = i1192;
                                                    i15 = i117;
                                                    break;
                                                case 64:
                                                case 128:
                                                    d = 30.0d;
                                                    i18 = 552960;
                                                    i19 = 6000;
                                                    int i11922 = i19;
                                                    i16 = i18;
                                                    i17 = i11922;
                                                    i15 = i117;
                                                    break;
                                                case 256:
                                                case 512:
                                                    d = 33.75d;
                                                    i18 = 983040;
                                                    i19 = 10000;
                                                    int i119222 = i19;
                                                    i16 = i18;
                                                    i17 = i119222;
                                                    i15 = i117;
                                                    break;
                                                case 1024:
                                                    d = 30.0d;
                                                    i18 = 2228224;
                                                    i19 = 12000;
                                                    int i1192222 = i19;
                                                    i16 = i18;
                                                    i17 = i1192222;
                                                    i15 = i117;
                                                    break;
                                                case 2048:
                                                    d = 30.0d;
                                                    i18 = 2228224;
                                                    i19 = 30000;
                                                    int i11922222 = i19;
                                                    i16 = i18;
                                                    i17 = i11922222;
                                                    i15 = i117;
                                                    break;
                                                case 4096:
                                                    d = 60.0d;
                                                    i18 = 2228224;
                                                    i19 = 20000;
                                                    int i119222222 = i19;
                                                    i16 = i18;
                                                    i17 = i119222222;
                                                    i15 = i117;
                                                    break;
                                                case 8192:
                                                    d = 60.0d;
                                                    i18 = 2228224;
                                                    i19 = 50000;
                                                    int i1192222222 = i19;
                                                    i16 = i18;
                                                    i17 = i1192222222;
                                                    i15 = i117;
                                                    break;
                                                case 16384:
                                                    d = 30.0d;
                                                    i17 = 25000;
                                                    i15 = i117;
                                                    i16 = 8912896;
                                                    break;
                                                case 32768:
                                                    d = 30.0d;
                                                    i17 = 100000;
                                                    i15 = i117;
                                                    i16 = 8912896;
                                                    break;
                                                case 65536:
                                                    d = 60.0d;
                                                    i17 = 40000;
                                                    i15 = i117;
                                                    i16 = 8912896;
                                                    break;
                                                case 131072:
                                                    d = 60.0d;
                                                    i17 = 160000;
                                                    i15 = i117;
                                                    i16 = 8912896;
                                                    break;
                                                case 262144:
                                                    d = 120.0d;
                                                    i17 = 60000;
                                                    i15 = i117;
                                                    i16 = 8912896;
                                                    break;
                                                case 524288:
                                                    d = 120.0d;
                                                    i17 = 240000;
                                                    i15 = i117;
                                                    i16 = 8912896;
                                                    break;
                                                case 1048576:
                                                    d = 30.0d;
                                                    i17 = 60000;
                                                    i15 = i117;
                                                    i16 = 35651584;
                                                    break;
                                                case 2097152:
                                                    d = 30.0d;
                                                    i17 = 240000;
                                                    i15 = i117;
                                                    i16 = 35651584;
                                                    break;
                                                case 4194304:
                                                    d = 60.0d;
                                                    i17 = 120000;
                                                    i15 = i117;
                                                    i16 = 35651584;
                                                    break;
                                                case 8388608:
                                                    d = 60.0d;
                                                    i17 = 480000;
                                                    i15 = i117;
                                                    i16 = 35651584;
                                                    break;
                                                case 16777216:
                                                    d = 120.0d;
                                                    i17 = 240000;
                                                    i15 = i117;
                                                    i16 = 35651584;
                                                    break;
                                                case 33554432:
                                                    d = 120.0d;
                                                    i17 = Build.VERSION_CODES_FULL.FROYO;
                                                    i15 = i117;
                                                    i16 = 35651584;
                                                    break;
                                                default:
                                                    Log.w(str18, "Unrecognized level " + codecProfileLevel7.level + str14 + mimeType);
                                                    i |= 1;
                                                    d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                                                    i15 = i117;
                                                    i17 = 0;
                                                    i16 = 0;
                                                    break;
                                            }
                                        } else {
                                            d = 15.0d;
                                            i15 = i117;
                                            i16 = 36864;
                                            i17 = 128;
                                        }
                                        int i120 = codecProfileLevel7.profile;
                                        int i121 = length7;
                                        if (i120 != 1 && i120 != 2 && i120 != 4 && i120 != 4096 && i120 != 8192) {
                                            Log.w(str18, str19 + codecProfileLevel7.profile + str14 + mimeType);
                                            i |= 1;
                                        }
                                        i &= -5;
                                        jMax6 = Math.max((int) (r1 * d), jMax6);
                                        iMax21 = Math.max(i16 >> 6, iMax21);
                                        iMax = Math.max(i17 * 1000, iMax);
                                        i117 = i15 + 1;
                                        length7 = i121;
                                    }
                                    int iSqrt3 = (int) Math.sqrt(iMax21 * 8);
                                    int i122 = iMax21;
                                    videoCapabilities = this;
                                    videoCapabilities.applyMacroBlockLimits(iSqrt3, iSqrt3, i122, jMax6, 8, 8, 1, 1);
                                } else {
                                    CodecProfileLevel[] codecProfileLevelArr9 = codecProfileLevelArr8;
                                    if (mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_AV1)) {
                                        int length8 = codecProfileLevelArr9.length;
                                        long jMax7 = 829440;
                                        int iMax22 = 36864;
                                        int iMax23 = 512;
                                        iMax = 200000;
                                        int i123 = 0;
                                        i2 = 4;
                                        while (i123 < length8) {
                                            CodecProfileLevel codecProfileLevel8 = codecProfileLevelArr9[i123];
                                            int i124 = codecProfileLevel8.level;
                                            if (i124 != 1) {
                                                if (i124 != 2) {
                                                    switch (i124) {
                                                        case 4:
                                                        case 8:
                                                            j2 = 10454400;
                                                            i3 = 278784;
                                                            i4 = 3000;
                                                            i5 = 2816;
                                                            break;
                                                        case 16:
                                                            j2 = 24969600;
                                                            i3 = 665856;
                                                            i4 = 6000;
                                                            i5 = 4352;
                                                            break;
                                                        case 32:
                                                        case 64:
                                                        case 128:
                                                            j2 = 39938400;
                                                            i3 = 1065024;
                                                            i4 = 10000;
                                                            i5 = 5504;
                                                            break;
                                                        case 256:
                                                            j2 = 77856768;
                                                            i3 = 2359296;
                                                            i4 = 12000;
                                                            i5 = GLES30.GL_COLOR;
                                                            break;
                                                        case 512:
                                                        case 1024:
                                                        case 2048:
                                                            j2 = 155713536;
                                                            i3 = 2359296;
                                                            i4 = 20000;
                                                            i5 = GLES30.GL_COLOR;
                                                            break;
                                                        case 4096:
                                                            j4 = 273715200;
                                                            i13 = 30000;
                                                            i7 = length8;
                                                            i8 = i123;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            j3 = j4;
                                                            i10 = 8912896;
                                                            i9 = i13;
                                                            i11 = 8192;
                                                            break;
                                                        case 8192:
                                                            j4 = 547430400;
                                                            i13 = 40000;
                                                            i7 = length8;
                                                            i8 = i123;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            j3 = j4;
                                                            i10 = 8912896;
                                                            i9 = i13;
                                                            i11 = 8192;
                                                            break;
                                                        case 16384:
                                                            j4 = 1094860800;
                                                            i13 = 60000;
                                                            i7 = length8;
                                                            i8 = i123;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            j3 = j4;
                                                            i10 = 8912896;
                                                            i9 = i13;
                                                            i11 = 8192;
                                                            break;
                                                        case 32768:
                                                            j4 = 1176502272;
                                                            i13 = 60000;
                                                            i7 = length8;
                                                            i8 = i123;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            j3 = j4;
                                                            i10 = 8912896;
                                                            i9 = i13;
                                                            i11 = 8192;
                                                            break;
                                                        case 65536:
                                                            j5 = 1176502272;
                                                            i14 = 60000;
                                                            i7 = length8;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            i6 = 16384;
                                                            i8 = i123;
                                                            j3 = j5;
                                                            i10 = 35651584;
                                                            i9 = i14;
                                                            i11 = i6;
                                                            break;
                                                        case 131072:
                                                            j5 = 2189721600L;
                                                            i14 = 100000;
                                                            i7 = length8;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            i6 = 16384;
                                                            i8 = i123;
                                                            j3 = j5;
                                                            i10 = 35651584;
                                                            i9 = i14;
                                                            i11 = i6;
                                                            break;
                                                        case 262144:
                                                            j5 = 4379443200L;
                                                            i14 = 160000;
                                                            i7 = length8;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            i6 = 16384;
                                                            i8 = i123;
                                                            j3 = j5;
                                                            i10 = 35651584;
                                                            i9 = i14;
                                                            i11 = i6;
                                                            break;
                                                        case 524288:
                                                            j5 = 4706009088L;
                                                            i14 = 160000;
                                                            i7 = length8;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            i6 = 16384;
                                                            i8 = i123;
                                                            j3 = j5;
                                                            i10 = 35651584;
                                                            i9 = i14;
                                                            i11 = i6;
                                                            break;
                                                        default:
                                                            Log.w(str18, "Unrecognized level " + codecProfileLevel8.level + str14 + mimeType);
                                                            i2 |= 1;
                                                            i7 = length8;
                                                            i8 = i123;
                                                            codecProfileLevelArr = codecProfileLevelArr9;
                                                            j3 = 0;
                                                            i11 = 0;
                                                            i10 = 0;
                                                            i9 = 0;
                                                            break;
                                                    }
                                                }
                                                i12 = codecProfileLevel8.profile;
                                                int i125 = iMax23;
                                                if (i12 == 1 && i12 != 2 && i12 != 4096 && i12 != 8192) {
                                                    Log.w(str18, str19 + codecProfileLevel8.profile + str14 + mimeType);
                                                    i2 |= 1;
                                                }
                                                i2 &= -5;
                                                jMax7 = Math.max(j3, jMax7);
                                                iMax22 = Math.max(i10, iMax22);
                                                iMax = Math.max(i9 * 1000, iMax);
                                                iMax23 = Math.max(i11, i125);
                                                i123 = i8 + 1;
                                                length8 = i7;
                                                codecProfileLevelArr9 = codecProfileLevelArr;
                                            } else {
                                                j2 = 5529600;
                                                i3 = Protocol.BASE_WIFI_MONITOR;
                                                i4 = 1500;
                                                i5 = 2048;
                                            }
                                            codecProfileLevelArr = codecProfileLevelArr9;
                                            i6 = i5;
                                            i7 = length8;
                                            int i126 = i4;
                                            i8 = i123;
                                            j3 = j2;
                                            i9 = i126;
                                            i10 = i3;
                                            i11 = i6;
                                            i12 = codecProfileLevel8.profile;
                                            int i1252 = iMax23;
                                            if (i12 == 1) {
                                            }
                                            i2 &= -5;
                                            jMax7 = Math.max(j3, jMax7);
                                            iMax22 = Math.max(i10, iMax22);
                                            iMax = Math.max(i9 * 1000, iMax);
                                            iMax23 = Math.max(i11, i1252);
                                            i123 = i8 + 1;
                                            length8 = i7;
                                            codecProfileLevelArr9 = codecProfileLevelArr;
                                        }
                                        int iDivUp2 = Utils.divUp(iMax23, 8);
                                        videoCapabilities = this;
                                        videoCapabilities.applyMacroBlockLimits(iDivUp2, iDivUp2, Utils.divUp(iMax22, 64), Utils.divUp(jMax7, 64L), 8, 8, 1, 1);
                                    } else if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodecInfo$VideoCapabilities$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return Boolean.valueOf(Flags.apvSupport());
                                        }
                                    }) && mimeType.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_APV)) {
                                        int length9 = codecProfileLevelArr9.length;
                                        long jMax8 = 11880;
                                        iMax = 7000000;
                                        int i127 = 0;
                                        i2 = 4;
                                        while (true) {
                                            int i128 = Integer.MAX_VALUE;
                                            if (i127 < length9) {
                                                CodecProfileLevel codecProfileLevel9 = codecProfileLevelArr9[i127];
                                                switch (codecProfileLevel9.level) {
                                                    case 257:
                                                        j = 3041280;
                                                        i128 = 7000000;
                                                        break;
                                                    case 258:
                                                        j = 3041280;
                                                        i128 = 11000000;
                                                        break;
                                                    case 260:
                                                        j = 3041280;
                                                        i128 = 14000000;
                                                        break;
                                                    case 264:
                                                        j = 3041280;
                                                        i128 = 21000000;
                                                        break;
                                                    case 513:
                                                        j = 6082560;
                                                        i128 = 14000000;
                                                        break;
                                                    case 514:
                                                        j = 6082560;
                                                        i128 = 21000000;
                                                        break;
                                                    case 516:
                                                        j = 6082560;
                                                        i128 = 28000000;
                                                        break;
                                                    case 520:
                                                        j = 6082560;
                                                        i128 = 42000000;
                                                        break;
                                                    case 1025:
                                                        j = 15667200;
                                                        i128 = 36000000;
                                                        break;
                                                    case 1026:
                                                        j = 15667200;
                                                        i128 = 53000000;
                                                        break;
                                                    case 1028:
                                                        j = 15667200;
                                                        i128 = 71000000;
                                                        break;
                                                    case 1032:
                                                        j = 15667200;
                                                        i128 = 106000000;
                                                        break;
                                                    case 2049:
                                                        j = 31334400;
                                                        i128 = 71000000;
                                                        break;
                                                    case 2050:
                                                        j = 31334400;
                                                        i128 = 106000000;
                                                        break;
                                                    case 2052:
                                                        j = 31334400;
                                                        i128 = 141000000;
                                                        break;
                                                    case 2056:
                                                        j = 31334400;
                                                        i128 = 212000000;
                                                        break;
                                                    case 4097:
                                                        j = 66846720;
                                                        i128 = 101000000;
                                                        break;
                                                    case 4098:
                                                        j = 66846720;
                                                        i128 = 151000000;
                                                        break;
                                                    case 4100:
                                                        j = 66846720;
                                                        i128 = 201000000;
                                                        break;
                                                    case 4104:
                                                        j = 66846720;
                                                        i128 = 301000000;
                                                        break;
                                                    case 8193:
                                                        j = 133693440;
                                                        i128 = 201000000;
                                                        break;
                                                    case 8194:
                                                        j = 133693440;
                                                        i128 = 301000000;
                                                        break;
                                                    case 8196:
                                                        j = 133693440;
                                                        i128 = 401000000;
                                                        break;
                                                    case 8200:
                                                        j = 133693440;
                                                        i128 = 602000000;
                                                        break;
                                                    case 16385:
                                                        j = 265420800;
                                                        i128 = 401000000;
                                                        break;
                                                    case 16386:
                                                        j = 265420800;
                                                        i128 = 602000000;
                                                        break;
                                                    case 16388:
                                                        j = 265420800;
                                                        i128 = 780000000;
                                                        break;
                                                    case 16392:
                                                        j = 265420800;
                                                        i128 = 1170000000;
                                                        break;
                                                    case 32769:
                                                        j = 530841600;
                                                        i128 = 780000000;
                                                        break;
                                                    case 32770:
                                                        j = 530841600;
                                                        i128 = 1170000000;
                                                        break;
                                                    case 32772:
                                                        j = 530841600;
                                                        i128 = 1560000000;
                                                        break;
                                                    case 32776:
                                                        j = 530841600;
                                                        break;
                                                    case 65537:
                                                        j = 1061683200;
                                                        i128 = 1560000000;
                                                        break;
                                                    case 65538:
                                                    case 65540:
                                                    case 65544:
                                                        j = 1061683200;
                                                        break;
                                                    case CodecProfileLevel.APVLevel51Band0 /* 131073 */:
                                                    case CodecProfileLevel.APVLevel51Band1 /* 131074 */:
                                                    case 131076:
                                                    case CodecProfileLevel.APVLevel51Band3 /* 131080 */:
                                                        j = 2123366400;
                                                        break;
                                                    case 262145:
                                                    case CodecProfileLevel.APVLevel6Band1 /* 262146 */:
                                                    case 262148:
                                                    case CodecProfileLevel.APVLevel6Band3 /* 262152 */:
                                                        j = 4777574400L;
                                                        break;
                                                    case CodecProfileLevel.APVLevel61Band0 /* 524289 */:
                                                    case 524290:
                                                    case 524292:
                                                    case CodecProfileLevel.APVLevel61Band3 /* 524296 */:
                                                        j = 8493465600L;
                                                        break;
                                                    case CodecProfileLevel.APVLevel7Band0 /* 1048577 */:
                                                    case CodecProfileLevel.APVLevel7Band1 /* 1048578 */:
                                                    case CodecProfileLevel.APVLevel7Band2 /* 1048580 */:
                                                    case 1048584:
                                                        j = 16986931200L;
                                                        break;
                                                    case CodecProfileLevel.APVLevel71Band0 /* 2097153 */:
                                                    case CodecProfileLevel.APVLevel71Band1 /* 2097154 */:
                                                    case CodecProfileLevel.APVLevel71Band2 /* 2097156 */:
                                                    case CodecProfileLevel.APVLevel71Band3 /* 2097160 */:
                                                        j = 33973862400L;
                                                        break;
                                                    default:
                                                        Log.w(str18, "Unrecognized level " + codecProfileLevel9.level + str14 + mimeType);
                                                        i2 |= 1;
                                                        j = 0;
                                                        i128 = 0;
                                                        break;
                                                }
                                                int i129 = codecProfileLevel9.profile;
                                                if (i129 != 1 && i129 != 4096) {
                                                    if (i129 != 8192) {
                                                        Log.w(str18, str19 + codecProfileLevel9.profile + str14 + mimeType);
                                                        i2 |= 1;
                                                    }
                                                }
                                                i2 &= -5;
                                                jMax8 = Math.max(j, jMax8);
                                                iMax = Math.max(i128, iMax);
                                                i127++;
                                            } else {
                                                long jDivUp = Utils.divUp(jMax8, 256L);
                                                int iMin3 = (int) Math.min(Integer.MAX_VALUE, jDivUp);
                                                int iMin4 = Math.min(Utils.divUp((int) Math.pow(2.0d, 24.0d), 16), iMin3);
                                                videoCapabilities = this;
                                                videoCapabilities.applyMacroBlockLimits(iMin4, iMin4, iMin3, jDivUp, 16, 16, 2, 1);
                                            }
                                        }
                                    } else {
                                        videoCapabilities = this;
                                        Log.w(str18, "Unsupported mime " + mimeType);
                                        iMax = 64000;
                                        i = 6;
                                    }
                                    i = i2;
                                }
                            }
                        }
                    }
                }
                iMax = iMax2;
            }
            videoCapabilities.mBitrateRange = Range.create(num, Integer.valueOf(iMax));
            videoCapabilities.mParent.mError |= i;
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
            boolean zContains = num != null ? this.mComplexityRange.contains((Range<Integer>) num) : true;
            if (zContains && num2 != null) {
                zContains = this.mQualityRange.contains((Range<Integer>) num2);
            }
            if (!zContains || num3 == null) {
                return zContains;
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
            if (!((Integer) this.mQualityRange.getUpper()).equals(this.mQualityRange.getLower()) && (num2 = this.mDefaultQuality) != null) {
                mediaFormat.setInteger("quality", num2.intValue());
            }
            if (!((Integer) this.mComplexityRange.getUpper()).equals(this.mComplexityRange.getLower()) && (num = this.mDefaultComplexity) != null) {
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
