package android.media.audiofx;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audiofx.AudioEffect;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public final class DynamicsProcessing extends AudioEffect {
    private static final int CHANNEL_COUNT_MAX = 32;
    private static final float CHANNEL_DEFAULT_INPUT_GAIN = 0.0f;
    private static final int CONFIG_DEFAULT_MBC_BANDS = 6;
    private static final int CONFIG_DEFAULT_POSTEQ_BANDS = 6;
    private static final int CONFIG_DEFAULT_PREEQ_BANDS = 6;
    private static final boolean CONFIG_DEFAULT_USE_LIMITER = true;
    private static final boolean CONFIG_DEFAULT_USE_MBC = true;
    private static final boolean CONFIG_DEFAULT_USE_POSTEQ = true;
    private static final boolean CONFIG_DEFAULT_USE_PREEQ = true;
    private static final int CONFIG_DEFAULT_VARIANT = 0;
    private static final float CONFIG_PREFERRED_FRAME_DURATION_MS = 10.0f;
    private static final float DEFAULT_MAX_FREQUENCY = 20000.0f;
    private static final float DEFAULT_MIN_FREQUENCY = 220.0f;
    private static final float EQ_DEFAULT_GAIN = 0.0f;
    private static final float LIMITER_DEFAULT_ATTACK_TIME = 1.0f;
    private static final boolean LIMITER_DEFAULT_ENABLED = true;
    private static final int LIMITER_DEFAULT_LINK_GROUP = 0;
    private static final float LIMITER_DEFAULT_POST_GAIN = 0.0f;
    private static final float LIMITER_DEFAULT_RATIO = 10.0f;
    private static final float LIMITER_DEFAULT_RELEASE_TIME = 60.0f;
    private static final float LIMITER_DEFAULT_THRESHOLD = -2.0f;
    private static final float MBC_DEFAULT_ATTACK_TIME = 3.0f;
    private static final boolean MBC_DEFAULT_ENABLED = true;
    private static final float MBC_DEFAULT_EXPANDER_RATIO = 1.0f;
    private static final float MBC_DEFAULT_KNEE_WIDTH = 0.0f;
    private static final float MBC_DEFAULT_NOISE_GATE_THRESHOLD = -90.0f;
    private static final float MBC_DEFAULT_POST_GAIN = 0.0f;
    private static final float MBC_DEFAULT_PRE_GAIN = 0.0f;
    private static final float MBC_DEFAULT_RATIO = 1.0f;
    private static final float MBC_DEFAULT_RELEASE_TIME = 80.0f;
    private static final float MBC_DEFAULT_THRESHOLD = -45.0f;
    private static final int PARAM_ENGINE_ARCHITECTURE = 48;
    private static final int PARAM_GET_CHANNEL_COUNT = 16;
    private static final int PARAM_INPUT_GAIN = 32;
    private static final int PARAM_LIMITER = 112;
    private static final int PARAM_MBC = 80;
    private static final int PARAM_MBC_BAND = 85;
    private static final int PARAM_POST_EQ = 96;
    private static final int PARAM_POST_EQ_BAND = 101;
    private static final int PARAM_PRE_EQ = 64;
    private static final int PARAM_PRE_EQ_BAND = 69;
    private static final boolean POSTEQ_DEFAULT_ENABLED = true;
    private static final boolean PREEQ_DEFAULT_ENABLED = true;
    private static final String TAG = "DynamicsProcessing";
    public static final int VARIANT_FAVOR_FREQUENCY_RESOLUTION = 0;
    public static final int VARIANT_FAVOR_TIME_RESOLUTION = 1;
    private BaseParameterListener mBaseParamListener;
    private int mChannelCount;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;
    private static final float mMinFreqLog = (float) Math.log10(220.0d);
    private static final float mMaxFreqLog = (float) Math.log10(20000.0d);

    public interface OnParameterChangeListener {
        void onParameterChange(DynamicsProcessing dynamicsProcessing, int i, int i2);
    }

    public DynamicsProcessing(int i) {
        this(0, i);
    }

    public DynamicsProcessing(int i, int i2) {
        this(i, i2, null);
    }

    public DynamicsProcessing(int i, int i2, Config config) {
        super(EFFECT_TYPE_DYNAMICS_PROCESSING, EFFECT_TYPE_NULL, i, i2);
        Config config2;
        this.mChannelCount = 0;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        if (i2 == 0) {
            Log.w(TAG, "WARNING: attaching a DynamicsProcessing to global output mix isdeprecated!");
        }
        this.mChannelCount = getChannelCount();
        if (config == null) {
            config2 = new Config.Builder(0, this.mChannelCount, true, 6, true, 6, true, 6, true).build();
        } else {
            config2 = new Config(this.mChannelCount, config);
        }
        setEngineArchitecture(config2.getVariant(), config2.getPreferredFrameDuration(), config2.isPreEqInUse(), config2.getPreEqBandCount(), config2.isMbcInUse(), config2.getMbcBandCount(), config2.isPostEqInUse(), config2.getPostEqBandCount(), config2.isLimiterInUse());
        for (int i3 = 0; i3 < this.mChannelCount; i3++) {
            updateEngineChannelByChannelIndex(i3, config2.getChannelByChannelIndex(i3));
        }
    }

    public Config getConfig() {
        Number[] numberArr = {0, Float.valueOf(0.0f), 0, 0, 0, 0, 0, 0, 0};
        byte[] numberArrayToByteArray = numberArrayToByteArray(new Number[]{48});
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr);
        Config build = new Config.Builder(numberArr[0].intValue(), this.mChannelCount, numberArr[2].intValue() > 0, numberArr[3].intValue(), numberArr[4].intValue() > 0, numberArr[5].intValue(), numberArr[6].intValue() > 0, numberArr[7].intValue(), numberArr[8].intValue() > 0).setPreferredFrameDuration(numberArr[1].floatValue()).build();
        for (int i = 0; i < this.mChannelCount; i++) {
            build.setChannelTo(i, queryEngineByChannelIndex(i));
        }
        return build;
    }

    public static class Stage {
        private boolean mEnabled;
        private boolean mInUse;

        public Stage(boolean z, boolean z2) {
            this.mInUse = z;
            this.mEnabled = z2;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        public boolean isInUse() {
            return this.mInUse;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(" Stage InUse: %b\n", Boolean.valueOf(isInUse())));
            if (isInUse()) {
                sb.append(String.format(" Stage Enabled: %b\n", Boolean.valueOf(this.mEnabled)));
            }
            return sb.toString();
        }
    }

    public static class BandStage extends Stage {
        private int mBandCount;

        public BandStage(boolean z, boolean z2, int i) {
            super(z, z2);
            this.mBandCount = isInUse() ? i : 0;
        }

        public int getBandCount() {
            return this.mBandCount;
        }

        @Override // android.media.audiofx.DynamicsProcessing.Stage
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append(String.format(" Band Count: %d\n", Integer.valueOf(this.mBandCount)));
            }
            return sb.toString();
        }
    }

    public static class BandBase {
        private float mCutoffFrequency;
        private boolean mEnabled;

        public BandBase(boolean z, float f) {
            this.mEnabled = z;
            this.mCutoffFrequency = f;
        }

        public String toString() {
            return String.format(" Enabled: %b\n", Boolean.valueOf(this.mEnabled)) + String.format(" CutoffFrequency: %f\n", Float.valueOf(this.mCutoffFrequency));
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        public float getCutoffFrequency() {
            return this.mCutoffFrequency;
        }

        public void setCutoffFrequency(float f) {
            this.mCutoffFrequency = f;
        }
    }

    public static final class EqBand extends BandBase {
        private float mGain;

        public EqBand(boolean z, float f, float f2) {
            super(z, f);
            this.mGain = f2;
        }

        public EqBand(EqBand eqBand) {
            super(eqBand.isEnabled(), eqBand.getCutoffFrequency());
            this.mGain = eqBand.mGain;
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandBase
        public String toString() {
            return super.toString() + String.format(" Gain: %f\n", Float.valueOf(this.mGain));
        }

        public float getGain() {
            return this.mGain;
        }

        public void setGain(float f) {
            this.mGain = f;
        }
    }

    public static final class MbcBand extends BandBase {
        private float mAttackTime;
        private float mExpanderRatio;
        private float mKneeWidth;
        private float mNoiseGateThreshold;
        private float mPostGain;
        private float mPreGain;
        private float mRatio;
        private float mReleaseTime;
        private float mThreshold;

        public MbcBand(boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            super(z, f);
            this.mAttackTime = f2;
            this.mReleaseTime = f3;
            this.mRatio = f4;
            this.mThreshold = f5;
            this.mKneeWidth = f6;
            this.mNoiseGateThreshold = f7;
            this.mExpanderRatio = f8;
            this.mPreGain = f9;
            this.mPostGain = f10;
        }

        public MbcBand(MbcBand mbcBand) {
            super(mbcBand.isEnabled(), mbcBand.getCutoffFrequency());
            this.mAttackTime = mbcBand.mAttackTime;
            this.mReleaseTime = mbcBand.mReleaseTime;
            this.mRatio = mbcBand.mRatio;
            this.mThreshold = mbcBand.mThreshold;
            this.mKneeWidth = mbcBand.mKneeWidth;
            this.mNoiseGateThreshold = mbcBand.mNoiseGateThreshold;
            this.mExpanderRatio = mbcBand.mExpanderRatio;
            this.mPreGain = mbcBand.mPreGain;
            this.mPostGain = mbcBand.mPostGain;
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandBase
        public String toString() {
            return super.toString() + String.format(" AttackTime: %f (ms)\n", Float.valueOf(this.mAttackTime)) + String.format(" ReleaseTime: %f (ms)\n", Float.valueOf(this.mReleaseTime)) + String.format(" Ratio: 1:%f\n", Float.valueOf(this.mRatio)) + String.format(" Threshold: %f (dB)\n", Float.valueOf(this.mThreshold)) + String.format(" NoiseGateThreshold: %f(dB)\n", Float.valueOf(this.mNoiseGateThreshold)) + String.format(" ExpanderRatio: %f:1\n", Float.valueOf(this.mExpanderRatio)) + String.format(" PreGain: %f (dB)\n", Float.valueOf(this.mPreGain)) + String.format(" PostGain: %f (dB)\n", Float.valueOf(this.mPostGain));
        }

        public float getAttackTime() {
            return this.mAttackTime;
        }

        public void setAttackTime(float f) {
            this.mAttackTime = f;
        }

        public float getReleaseTime() {
            return this.mReleaseTime;
        }

        public void setReleaseTime(float f) {
            this.mReleaseTime = f;
        }

        public float getRatio() {
            return this.mRatio;
        }

        public void setRatio(float f) {
            this.mRatio = f;
        }

        public float getThreshold() {
            return this.mThreshold;
        }

        public void setThreshold(float f) {
            this.mThreshold = f;
        }

        public float getKneeWidth() {
            return this.mKneeWidth;
        }

        public void setKneeWidth(float f) {
            this.mKneeWidth = f;
        }

        public float getNoiseGateThreshold() {
            return this.mNoiseGateThreshold;
        }

        public void setNoiseGateThreshold(float f) {
            this.mNoiseGateThreshold = f;
        }

        public float getExpanderRatio() {
            return this.mExpanderRatio;
        }

        public void setExpanderRatio(float f) {
            this.mExpanderRatio = f;
        }

        public float getPreGain() {
            return this.mPreGain;
        }

        public void setPreGain(float f) {
            this.mPreGain = f;
        }

        public float getPostGain() {
            return this.mPostGain;
        }

        public void setPostGain(float f) {
            this.mPostGain = f;
        }
    }

    public static final class Eq extends BandStage {
        private final EqBand[] mBands;

        public Eq(boolean z, boolean z2, int i) {
            super(z, z2, i);
            if (isInUse()) {
                this.mBands = new EqBand[i];
                for (int i2 = 0; i2 < i; i2++) {
                    this.mBands[i2] = new EqBand(true, i > 1 ? (float) Math.pow(10.0d, DynamicsProcessing.mMinFreqLog + ((i2 * (DynamicsProcessing.mMaxFreqLog - DynamicsProcessing.mMinFreqLog)) / (i - 1))) : 20000.0f, 0.0f);
                }
                return;
            }
            this.mBands = null;
        }

        public Eq(Eq eq) {
            super(eq.isInUse(), eq.isEnabled(), eq.getBandCount());
            if (isInUse()) {
                this.mBands = new EqBand[eq.mBands.length];
                int i = 0;
                while (true) {
                    EqBand[] eqBandArr = this.mBands;
                    if (i >= eqBandArr.length) {
                        return;
                    }
                    eqBandArr[i] = new EqBand(eq.mBands[i]);
                    i++;
                }
            } else {
                this.mBands = null;
            }
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandStage, android.media.audiofx.DynamicsProcessing.Stage
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append("--->EqBands: " + this.mBands.length + ShaderAssembler.NEWLINE);
                for (int i = 0; i < this.mBands.length; i++) {
                    sb.append(String.format("  Band %d\n", Integer.valueOf(i)));
                    sb.append(this.mBands[i].toString());
                }
            }
            return sb.toString();
        }

        private void checkBand(int i) {
            EqBand[] eqBandArr = this.mBands;
            if (eqBandArr == null || i < 0 || i >= eqBandArr.length) {
                throw new IllegalArgumentException("band index " + i + " out of bounds");
            }
        }

        public void setBand(int i, EqBand eqBand) {
            checkBand(i);
            this.mBands[i] = new EqBand(eqBand);
        }

        public EqBand getBand(int i) {
            checkBand(i);
            return this.mBands[i];
        }
    }

    public static final class Mbc extends BandStage {
        private final MbcBand[] mBands;

        public Mbc(boolean z, boolean z2, int i) {
            super(z, z2, i);
            if (isInUse()) {
                this.mBands = new MbcBand[i];
                for (int i2 = 0; i2 < i; i2++) {
                    this.mBands[i2] = new MbcBand(true, i > 1 ? (float) Math.pow(10.0d, DynamicsProcessing.mMinFreqLog + ((i2 * (DynamicsProcessing.mMaxFreqLog - DynamicsProcessing.mMinFreqLog)) / (i - 1))) : 20000.0f, 3.0f, DynamicsProcessing.MBC_DEFAULT_RELEASE_TIME, 1.0f, DynamicsProcessing.MBC_DEFAULT_THRESHOLD, 0.0f, DynamicsProcessing.MBC_DEFAULT_NOISE_GATE_THRESHOLD, 1.0f, 0.0f, 0.0f);
                }
                return;
            }
            this.mBands = null;
        }

        public Mbc(Mbc mbc) {
            super(mbc.isInUse(), mbc.isEnabled(), mbc.getBandCount());
            if (isInUse()) {
                this.mBands = new MbcBand[mbc.mBands.length];
                int i = 0;
                while (true) {
                    MbcBand[] mbcBandArr = this.mBands;
                    if (i >= mbcBandArr.length) {
                        return;
                    }
                    mbcBandArr[i] = new MbcBand(mbc.mBands[i]);
                    i++;
                }
            } else {
                this.mBands = null;
            }
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandStage, android.media.audiofx.DynamicsProcessing.Stage
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append("--->MbcBands: " + this.mBands.length + ShaderAssembler.NEWLINE);
                for (int i = 0; i < this.mBands.length; i++) {
                    sb.append(String.format("  Band %d\n", Integer.valueOf(i)));
                    sb.append(this.mBands[i].toString());
                }
            }
            return sb.toString();
        }

        private void checkBand(int i) {
            MbcBand[] mbcBandArr = this.mBands;
            if (mbcBandArr == null || i < 0 || i >= mbcBandArr.length) {
                throw new IllegalArgumentException("band index " + i + " out of bounds");
            }
        }

        public void setBand(int i, MbcBand mbcBand) {
            checkBand(i);
            this.mBands[i] = new MbcBand(mbcBand);
        }

        public MbcBand getBand(int i) {
            checkBand(i);
            return this.mBands[i];
        }
    }

    public static final class Limiter extends Stage {
        private float mAttackTime;
        private int mLinkGroup;
        private float mPostGain;
        private float mRatio;
        private float mReleaseTime;
        private float mThreshold;

        public Limiter(boolean z, boolean z2, int i, float f, float f2, float f3, float f4, float f5) {
            super(z, z2);
            this.mLinkGroup = i;
            this.mAttackTime = f;
            this.mReleaseTime = f2;
            this.mRatio = f3;
            this.mThreshold = f4;
            this.mPostGain = f5;
        }

        public Limiter(Limiter limiter) {
            super(limiter.isInUse(), limiter.isEnabled());
            this.mLinkGroup = limiter.mLinkGroup;
            this.mAttackTime = limiter.mAttackTime;
            this.mReleaseTime = limiter.mReleaseTime;
            this.mRatio = limiter.mRatio;
            this.mThreshold = limiter.mThreshold;
            this.mPostGain = limiter.mPostGain;
        }

        @Override // android.media.audiofx.DynamicsProcessing.Stage
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append(String.format(" LinkGroup: %d (group)\n", Integer.valueOf(this.mLinkGroup)));
                sb.append(String.format(" AttackTime: %f (ms)\n", Float.valueOf(this.mAttackTime)));
                sb.append(String.format(" ReleaseTime: %f (ms)\n", Float.valueOf(this.mReleaseTime)));
                sb.append(String.format(" Ratio: 1:%f\n", Float.valueOf(this.mRatio)));
                sb.append(String.format(" Threshold: %f (dB)\n", Float.valueOf(this.mThreshold)));
                sb.append(String.format(" PostGain: %f (dB)\n", Float.valueOf(this.mPostGain)));
            }
            return sb.toString();
        }

        public int getLinkGroup() {
            return this.mLinkGroup;
        }

        public void setLinkGroup(int i) {
            this.mLinkGroup = i;
        }

        public float getAttackTime() {
            return this.mAttackTime;
        }

        public void setAttackTime(float f) {
            this.mAttackTime = f;
        }

        public float getReleaseTime() {
            return this.mReleaseTime;
        }

        public void setReleaseTime(float f) {
            this.mReleaseTime = f;
        }

        public float getRatio() {
            return this.mRatio;
        }

        public void setRatio(float f) {
            this.mRatio = f;
        }

        public float getThreshold() {
            return this.mThreshold;
        }

        public void setThreshold(float f) {
            this.mThreshold = f;
        }

        public float getPostGain() {
            return this.mPostGain;
        }

        public void setPostGain(float f) {
            this.mPostGain = f;
        }
    }

    public static final class Channel {
        private float mInputGain;
        private Limiter mLimiter;
        private Mbc mMbc;
        private Eq mPostEq;
        private Eq mPreEq;

        public Channel(float f, boolean z, int i, boolean z2, int i2, boolean z3, int i3, boolean z4) {
            this.mInputGain = f;
            this.mPreEq = new Eq(z, true, i);
            this.mMbc = new Mbc(z2, true, i2);
            this.mPostEq = new Eq(z3, true, i3);
            this.mLimiter = new Limiter(z4, true, 0, 1.0f, 60.0f, 10.0f, -2.0f, 0.0f);
        }

        public Channel(Channel channel) {
            this.mInputGain = channel.mInputGain;
            this.mPreEq = new Eq(channel.mPreEq);
            this.mMbc = new Mbc(channel.mMbc);
            this.mPostEq = new Eq(channel.mPostEq);
            this.mLimiter = new Limiter(channel.mLimiter);
        }

        public String toString() {
            return String.format(" InputGain: %f\n", Float.valueOf(this.mInputGain)) + "-->PreEq\n" + this.mPreEq.toString() + "-->MBC\n" + this.mMbc.toString() + "-->PostEq\n" + this.mPostEq.toString() + "-->Limiter\n" + this.mLimiter.toString();
        }

        public float getInputGain() {
            return this.mInputGain;
        }

        public void setInputGain(float f) {
            this.mInputGain = f;
        }

        public Eq getPreEq() {
            return this.mPreEq;
        }

        public void setPreEq(Eq eq) {
            if (eq.getBandCount() != this.mPreEq.getBandCount()) {
                throw new IllegalArgumentException("PreEqBandCount changed from " + this.mPreEq.getBandCount() + " to " + eq.getBandCount());
            }
            this.mPreEq = new Eq(eq);
        }

        public EqBand getPreEqBand(int i) {
            return this.mPreEq.getBand(i);
        }

        public void setPreEqBand(int i, EqBand eqBand) {
            this.mPreEq.setBand(i, eqBand);
        }

        public Mbc getMbc() {
            return this.mMbc;
        }

        public void setMbc(Mbc mbc) {
            if (mbc.getBandCount() != this.mMbc.getBandCount()) {
                throw new IllegalArgumentException("MbcBandCount changed from " + this.mMbc.getBandCount() + " to " + mbc.getBandCount());
            }
            this.mMbc = new Mbc(mbc);
        }

        public MbcBand getMbcBand(int i) {
            return this.mMbc.getBand(i);
        }

        public void setMbcBand(int i, MbcBand mbcBand) {
            this.mMbc.setBand(i, mbcBand);
        }

        public Eq getPostEq() {
            return this.mPostEq;
        }

        public void setPostEq(Eq eq) {
            if (eq.getBandCount() != this.mPostEq.getBandCount()) {
                throw new IllegalArgumentException("PostEqBandCount changed from " + this.mPostEq.getBandCount() + " to " + eq.getBandCount());
            }
            this.mPostEq = new Eq(eq);
        }

        public EqBand getPostEqBand(int i) {
            return this.mPostEq.getBand(i);
        }

        public void setPostEqBand(int i, EqBand eqBand) {
            this.mPostEq.setBand(i, eqBand);
        }

        public Limiter getLimiter() {
            return this.mLimiter;
        }

        public void setLimiter(Limiter limiter) {
            this.mLimiter = new Limiter(limiter);
        }
    }

    public static final class Config {
        private final Channel[] mChannel;
        private final int mChannelCount;
        private final boolean mLimiterInUse;
        private final int mMbcBandCount;
        private final boolean mMbcInUse;
        private final int mPostEqBandCount;
        private final boolean mPostEqInUse;
        private final int mPreEqBandCount;
        private final boolean mPreEqInUse;
        private final float mPreferredFrameDuration;
        private final int mVariant;

        public Config(int i, float f, int i2, boolean z, int i3, boolean z2, int i4, boolean z3, int i5, boolean z4, Channel[] channelArr) {
            this.mVariant = i;
            this.mPreferredFrameDuration = f;
            this.mChannelCount = i2;
            this.mPreEqInUse = z;
            this.mPreEqBandCount = i3;
            this.mMbcInUse = z2;
            this.mMbcBandCount = i4;
            this.mPostEqInUse = z3;
            this.mPostEqBandCount = i5;
            this.mLimiterInUse = z4;
            this.mChannel = new Channel[i2];
            for (int i6 = 0; i6 < this.mChannelCount; i6++) {
                if (i6 < channelArr.length) {
                    this.mChannel[i6] = new Channel(channelArr[i6]);
                }
            }
        }

        public Config(int i, Config config) {
            this.mVariant = config.mVariant;
            this.mPreferredFrameDuration = config.mPreferredFrameDuration;
            int i2 = config.mChannelCount;
            this.mChannelCount = i2;
            this.mPreEqInUse = config.mPreEqInUse;
            this.mPreEqBandCount = config.mPreEqBandCount;
            this.mMbcInUse = config.mMbcInUse;
            this.mMbcBandCount = config.mMbcBandCount;
            this.mPostEqInUse = config.mPostEqInUse;
            this.mPostEqBandCount = config.mPostEqBandCount;
            this.mLimiterInUse = config.mLimiterInUse;
            if (i2 != config.mChannel.length) {
                throw new IllegalArgumentException("configuration channel counts differ " + i2 + " !=" + config.mChannel.length);
            }
            if (i < 1) {
                throw new IllegalArgumentException("channel resizing less than 1 not allowed");
            }
            this.mChannel = new Channel[i];
            for (int i3 = 0; i3 < i; i3++) {
                if (i3 < this.mChannelCount) {
                    this.mChannel[i3] = new Channel(config.mChannel[i3]);
                } else {
                    this.mChannel[i3] = new Channel(config.mChannel[this.mChannelCount - 1]);
                }
            }
        }

        public Config(Config config) {
            this(config.mChannelCount, config);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Variant: %d\n", Integer.valueOf(this.mVariant)));
            sb.append(String.format("PreferredFrameDuration: %f\n", Float.valueOf(this.mPreferredFrameDuration)));
            sb.append(String.format("ChannelCount: %d\n", Integer.valueOf(this.mChannelCount)));
            sb.append(String.format("PreEq inUse: %b, bandCount:%d\n", Boolean.valueOf(this.mPreEqInUse), Integer.valueOf(this.mPreEqBandCount)));
            sb.append(String.format("Mbc inUse: %b, bandCount: %d\n", Boolean.valueOf(this.mMbcInUse), Integer.valueOf(this.mMbcBandCount)));
            sb.append(String.format("PostEq inUse: %b, bandCount: %d\n", Boolean.valueOf(this.mPostEqInUse), Integer.valueOf(this.mPostEqBandCount)));
            sb.append(String.format("Limiter inUse: %b\n", Boolean.valueOf(this.mLimiterInUse)));
            for (int i = 0; i < this.mChannel.length; i++) {
                sb.append(String.format("==Channel %d\n", Integer.valueOf(i)));
                sb.append(this.mChannel[i].toString());
            }
            return sb.toString();
        }

        private void checkChannel(int i) {
            if (i < 0 || i >= this.mChannel.length) {
                throw new IllegalArgumentException("ChannelIndex out of bounds");
            }
        }

        public int getVariant() {
            return this.mVariant;
        }

        public float getPreferredFrameDuration() {
            return this.mPreferredFrameDuration;
        }

        public boolean isPreEqInUse() {
            return this.mPreEqInUse;
        }

        public int getPreEqBandCount() {
            return this.mPreEqBandCount;
        }

        public boolean isMbcInUse() {
            return this.mMbcInUse;
        }

        public int getMbcBandCount() {
            return this.mMbcBandCount;
        }

        public boolean isPostEqInUse() {
            return this.mPostEqInUse;
        }

        public int getPostEqBandCount() {
            return this.mPostEqBandCount;
        }

        public boolean isLimiterInUse() {
            return this.mLimiterInUse;
        }

        public Channel getChannelByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i];
        }

        public void setChannelTo(int i, Channel channel) {
            checkChannel(i);
            if (this.mMbcBandCount != channel.getMbc().getBandCount()) {
                throw new IllegalArgumentException("MbcBandCount changed from " + this.mMbcBandCount + " to " + channel.getPreEq().getBandCount());
            }
            if (this.mPreEqBandCount != channel.getPreEq().getBandCount()) {
                throw new IllegalArgumentException("PreEqBandCount changed from " + this.mPreEqBandCount + " to " + channel.getPreEq().getBandCount());
            }
            if (this.mPostEqBandCount != channel.getPostEq().getBandCount()) {
                throw new IllegalArgumentException("PostEqBandCount changed from " + this.mPostEqBandCount + " to " + channel.getPostEq().getBandCount());
            }
            this.mChannel[i] = new Channel(channel);
        }

        public void setAllChannelsTo(Channel channel) {
            for (int i = 0; i < this.mChannel.length; i++) {
                setChannelTo(i, channel);
            }
        }

        public float getInputGainByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getInputGain();
        }

        public void setInputGainByChannelIndex(int i, float f) {
            checkChannel(i);
            this.mChannel[i].setInputGain(f);
        }

        public void setInputGainAllChannelsTo(float f) {
            int i = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i >= channelArr.length) {
                    return;
                }
                channelArr[i].setInputGain(f);
                i++;
            }
        }

        public Eq getPreEqByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getPreEq();
        }

        public void setPreEqByChannelIndex(int i, Eq eq) {
            checkChannel(i);
            this.mChannel[i].setPreEq(eq);
        }

        public void setPreEqAllChannelsTo(Eq eq) {
            int i = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i >= channelArr.length) {
                    return;
                }
                channelArr[i].setPreEq(eq);
                i++;
            }
        }

        public EqBand getPreEqBandByChannelIndex(int i, int i2) {
            checkChannel(i);
            return this.mChannel[i].getPreEqBand(i2);
        }

        public void setPreEqBandByChannelIndex(int i, int i2, EqBand eqBand) {
            checkChannel(i);
            this.mChannel[i].setPreEqBand(i2, eqBand);
        }

        public void setPreEqBandAllChannelsTo(int i, EqBand eqBand) {
            int i2 = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i2 >= channelArr.length) {
                    return;
                }
                channelArr[i2].setPreEqBand(i, eqBand);
                i2++;
            }
        }

        public Mbc getMbcByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getMbc();
        }

        public void setMbcByChannelIndex(int i, Mbc mbc) {
            checkChannel(i);
            this.mChannel[i].setMbc(mbc);
        }

        public void setMbcAllChannelsTo(Mbc mbc) {
            int i = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i >= channelArr.length) {
                    return;
                }
                channelArr[i].setMbc(mbc);
                i++;
            }
        }

        public MbcBand getMbcBandByChannelIndex(int i, int i2) {
            checkChannel(i);
            return this.mChannel[i].getMbcBand(i2);
        }

        public void setMbcBandByChannelIndex(int i, int i2, MbcBand mbcBand) {
            checkChannel(i);
            this.mChannel[i].setMbcBand(i2, mbcBand);
        }

        public void setMbcBandAllChannelsTo(int i, MbcBand mbcBand) {
            int i2 = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i2 >= channelArr.length) {
                    return;
                }
                channelArr[i2].setMbcBand(i, mbcBand);
                i2++;
            }
        }

        public Eq getPostEqByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getPostEq();
        }

        public void setPostEqByChannelIndex(int i, Eq eq) {
            checkChannel(i);
            this.mChannel[i].setPostEq(eq);
        }

        public void setPostEqAllChannelsTo(Eq eq) {
            int i = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i >= channelArr.length) {
                    return;
                }
                channelArr[i].setPostEq(eq);
                i++;
            }
        }

        public EqBand getPostEqBandByChannelIndex(int i, int i2) {
            checkChannel(i);
            return this.mChannel[i].getPostEqBand(i2);
        }

        public void setPostEqBandByChannelIndex(int i, int i2, EqBand eqBand) {
            checkChannel(i);
            this.mChannel[i].setPostEqBand(i2, eqBand);
        }

        public void setPostEqBandAllChannelsTo(int i, EqBand eqBand) {
            int i2 = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i2 >= channelArr.length) {
                    return;
                }
                channelArr[i2].setPostEqBand(i, eqBand);
                i2++;
            }
        }

        public Limiter getLimiterByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getLimiter();
        }

        public void setLimiterByChannelIndex(int i, Limiter limiter) {
            checkChannel(i);
            this.mChannel[i].setLimiter(limiter);
        }

        public void setLimiterAllChannelsTo(Limiter limiter) {
            int i = 0;
            while (true) {
                Channel[] channelArr = this.mChannel;
                if (i >= channelArr.length) {
                    return;
                }
                channelArr[i].setLimiter(limiter);
                i++;
            }
        }

        public static final class Builder {
            private Channel[] mChannel;
            private int mChannelCount;
            private boolean mLimiterInUse;
            private int mMbcBandCount;
            private boolean mMbcInUse;
            private int mPostEqBandCount;
            private boolean mPostEqInUse;
            private int mPreEqBandCount;
            private boolean mPreEqInUse;
            private float mPreferredFrameDuration = 10.0f;
            private int mVariant;

            public Builder(int i, int i2, boolean z, int i3, boolean z2, int i4, boolean z3, int i5, boolean z4) {
                this.mVariant = i;
                this.mChannelCount = i2;
                this.mPreEqInUse = z;
                this.mPreEqBandCount = i3;
                this.mMbcInUse = z2;
                this.mMbcBandCount = i4;
                this.mPostEqInUse = z3;
                this.mPostEqBandCount = i5;
                this.mLimiterInUse = z4;
                this.mChannel = new Channel[i2];
                for (int i6 = 0; i6 < this.mChannelCount; i6++) {
                    this.mChannel[i6] = new Channel(0.0f, this.mPreEqInUse, this.mPreEqBandCount, this.mMbcInUse, this.mMbcBandCount, this.mPostEqInUse, this.mPostEqBandCount, this.mLimiterInUse);
                }
            }

            private void checkChannel(int i) {
                if (i < 0 || i >= this.mChannel.length) {
                    throw new IllegalArgumentException("ChannelIndex out of bounds");
                }
            }

            public Builder setPreferredFrameDuration(float f) {
                if (f < 0.0f) {
                    throw new IllegalArgumentException("Expected positive frameDuration");
                }
                this.mPreferredFrameDuration = f;
                return this;
            }

            public Builder setInputGainByChannelIndex(int i, float f) {
                checkChannel(i);
                this.mChannel[i].setInputGain(f);
                return this;
            }

            public Builder setInputGainAllChannelsTo(float f) {
                int i = 0;
                while (true) {
                    Channel[] channelArr = this.mChannel;
                    if (i >= channelArr.length) {
                        return this;
                    }
                    channelArr[i].setInputGain(f);
                    i++;
                }
            }

            public Builder setChannelTo(int i, Channel channel) {
                checkChannel(i);
                if (this.mMbcBandCount != channel.getMbc().getBandCount()) {
                    throw new IllegalArgumentException("MbcBandCount changed from " + this.mMbcBandCount + " to " + channel.getPreEq().getBandCount());
                }
                if (this.mPreEqBandCount != channel.getPreEq().getBandCount()) {
                    throw new IllegalArgumentException("PreEqBandCount changed from " + this.mPreEqBandCount + " to " + channel.getPreEq().getBandCount());
                }
                if (this.mPostEqBandCount != channel.getPostEq().getBandCount()) {
                    throw new IllegalArgumentException("PostEqBandCount changed from " + this.mPostEqBandCount + " to " + channel.getPostEq().getBandCount());
                }
                this.mChannel[i] = new Channel(channel);
                return this;
            }

            public Builder setAllChannelsTo(Channel channel) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setChannelTo(i, channel);
                }
                return this;
            }

            public Builder setPreEqByChannelIndex(int i, Eq eq) {
                checkChannel(i);
                this.mChannel[i].setPreEq(eq);
                return this;
            }

            public Builder setPreEqAllChannelsTo(Eq eq) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setPreEqByChannelIndex(i, eq);
                }
                return this;
            }

            public Builder setMbcByChannelIndex(int i, Mbc mbc) {
                checkChannel(i);
                this.mChannel[i].setMbc(mbc);
                return this;
            }

            public Builder setMbcAllChannelsTo(Mbc mbc) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setMbcByChannelIndex(i, mbc);
                }
                return this;
            }

            public Builder setPostEqByChannelIndex(int i, Eq eq) {
                checkChannel(i);
                this.mChannel[i].setPostEq(eq);
                return this;
            }

            public Builder setPostEqAllChannelsTo(Eq eq) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setPostEqByChannelIndex(i, eq);
                }
                return this;
            }

            public Builder setLimiterByChannelIndex(int i, Limiter limiter) {
                checkChannel(i);
                this.mChannel[i].setLimiter(limiter);
                return this;
            }

            public Builder setLimiterAllChannelsTo(Limiter limiter) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setLimiterByChannelIndex(i, limiter);
                }
                return this;
            }

            public Config build() {
                return new Config(this.mVariant, this.mPreferredFrameDuration, this.mChannelCount, this.mPreEqInUse, this.mPreEqBandCount, this.mMbcInUse, this.mMbcBandCount, this.mPostEqInUse, this.mPostEqBandCount, this.mLimiterInUse, this.mChannel);
            }
        }
    }

    public Channel getChannelByChannelIndex(int i) {
        return queryEngineByChannelIndex(i);
    }

    public void setChannelTo(int i, Channel channel) {
        updateEngineChannelByChannelIndex(i, channel);
    }

    public void setAllChannelsTo(Channel channel) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setChannelTo(i, channel);
        }
    }

    public float getInputGainByChannelIndex(int i) {
        return getTwoFloat(32, i);
    }

    public void setInputGainbyChannel(int i, float f) {
        setTwoFloat(32, i, f);
    }

    public void setInputGainAllChannelsTo(float f) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setInputGainbyChannel(i, f);
        }
    }

    public Eq getPreEqByChannelIndex(int i) {
        return queryEngineEqByChannelIndex(64, i);
    }

    public void setPreEqByChannelIndex(int i, Eq eq) {
        updateEngineEqByChannelIndex(64, i, eq);
    }

    public void setPreEqAllChannelsTo(Eq eq) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setPreEqByChannelIndex(i, eq);
        }
    }

    public EqBand getPreEqBandByChannelIndex(int i, int i2) {
        return queryEngineEqBandByChannelIndex(69, i, i2);
    }

    public void setPreEqBandByChannelIndex(int i, int i2, EqBand eqBand) {
        updateEngineEqBandByChannelIndex(69, i, i2, eqBand);
    }

    public void setPreEqBandAllChannelsTo(int i, EqBand eqBand) {
        for (int i2 = 0; i2 < this.mChannelCount; i2++) {
            setPreEqBandByChannelIndex(i2, i, eqBand);
        }
    }

    public Mbc getMbcByChannelIndex(int i) {
        return queryEngineMbcByChannelIndex(i);
    }

    public void setMbcByChannelIndex(int i, Mbc mbc) {
        updateEngineMbcByChannelIndex(i, mbc);
    }

    public void setMbcAllChannelsTo(Mbc mbc) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setMbcByChannelIndex(i, mbc);
        }
    }

    public MbcBand getMbcBandByChannelIndex(int i, int i2) {
        return queryEngineMbcBandByChannelIndex(i, i2);
    }

    public void setMbcBandByChannelIndex(int i, int i2, MbcBand mbcBand) {
        updateEngineMbcBandByChannelIndex(i, i2, mbcBand);
    }

    public void setMbcBandAllChannelsTo(int i, MbcBand mbcBand) {
        for (int i2 = 0; i2 < this.mChannelCount; i2++) {
            setMbcBandByChannelIndex(i2, i, mbcBand);
        }
    }

    public Eq getPostEqByChannelIndex(int i) {
        return queryEngineEqByChannelIndex(96, i);
    }

    public void setPostEqByChannelIndex(int i, Eq eq) {
        updateEngineEqByChannelIndex(96, i, eq);
    }

    public void setPostEqAllChannelsTo(Eq eq) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setPostEqByChannelIndex(i, eq);
        }
    }

    public EqBand getPostEqBandByChannelIndex(int i, int i2) {
        return queryEngineEqBandByChannelIndex(101, i, i2);
    }

    public void setPostEqBandByChannelIndex(int i, int i2, EqBand eqBand) {
        updateEngineEqBandByChannelIndex(101, i, i2, eqBand);
    }

    public void setPostEqBandAllChannelsTo(int i, EqBand eqBand) {
        for (int i2 = 0; i2 < this.mChannelCount; i2++) {
            setPostEqBandByChannelIndex(i2, i, eqBand);
        }
    }

    public Limiter getLimiterByChannelIndex(int i) {
        return queryEngineLimiterByChannelIndex(i);
    }

    public void setLimiterByChannelIndex(int i, Limiter limiter) {
        updateEngineLimiterByChannelIndex(i, limiter);
    }

    public void setLimiterAllChannelsTo(Limiter limiter) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setLimiterByChannelIndex(i, limiter);
        }
    }

    public int getChannelCount() {
        return getOneInt(16);
    }

    private void setEngineArchitecture(int i, float f, boolean z, int i2, boolean z2, int i3, boolean z3, int i4, boolean z4) {
        setNumberArray(new Number[]{48}, new Number[]{Integer.valueOf(i), Float.valueOf(f), Integer.valueOf(z ? 1 : 0), Integer.valueOf(i2), Integer.valueOf(z2 ? 1 : 0), Integer.valueOf(i3), Integer.valueOf(z3 ? 1 : 0), Integer.valueOf(i4), Integer.valueOf(z4 ? 1 : 0)});
    }

    private void updateEngineEqBandByChannelIndex(int i, int i2, int i3, EqBand eqBand) {
        setNumberArray(new Number[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)}, new Number[]{Integer.valueOf(eqBand.isEnabled() ? 1 : 0), Float.valueOf(eqBand.getCutoffFrequency()), Float.valueOf(eqBand.getGain())});
    }

    private Eq queryEngineEqByChannelIndex(int i, int i2) {
        Number[] numberArr = new Number[2];
        numberArr[0] = Integer.valueOf(i == 64 ? 64 : 96);
        numberArr[1] = Integer.valueOf(i2);
        Number[] numberArr2 = {0, 0, 0};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        int intValue = numberArr2[2].intValue();
        Eq eq = new Eq(numberArr2[0].intValue() > 0, numberArr2[1].intValue() > 0, intValue);
        for (int i3 = 0; i3 < intValue; i3++) {
            eq.setBand(i3, queryEngineEqBandByChannelIndex(i == 64 ? 69 : 101, i2, i3));
        }
        return eq;
    }

    private EqBand queryEngineEqBandByChannelIndex(int i, int i2, int i3) {
        Number[] numberArr = {Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)};
        Float valueOf = Float.valueOf(0.0f);
        Number[] numberArr2 = {0, valueOf, valueOf};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        return new EqBand(numberArr2[0].intValue() > 0, numberArr2[1].floatValue(), numberArr2[2].floatValue());
    }

    private void updateEngineEqByChannelIndex(int i, int i2, Eq eq) {
        int bandCount = eq.getBandCount();
        setNumberArray(new Number[]{Integer.valueOf(i), Integer.valueOf(i2)}, new Number[]{Integer.valueOf(eq.isInUse() ? 1 : 0), Integer.valueOf(eq.isEnabled() ? 1 : 0), Integer.valueOf(bandCount)});
        for (int i3 = 0; i3 < bandCount; i3++) {
            updateEngineEqBandByChannelIndex(i == 64 ? 69 : 101, i2, i3, eq.getBand(i3));
        }
    }

    private Mbc queryEngineMbcByChannelIndex(int i) {
        Number[] numberArr = {0, 0, 0};
        byte[] numberArrayToByteArray = numberArrayToByteArray(new Number[]{80, Integer.valueOf(i)});
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr);
        int intValue = numberArr[2].intValue();
        Mbc mbc = new Mbc(numberArr[0].intValue() > 0, numberArr[1].intValue() > 0, intValue);
        for (int i2 = 0; i2 < intValue; i2++) {
            mbc.setBand(i2, queryEngineMbcBandByChannelIndex(i, i2));
        }
        return mbc;
    }

    private MbcBand queryEngineMbcBandByChannelIndex(int i, int i2) {
        Number[] numberArr = {85, Integer.valueOf(i), Integer.valueOf(i2)};
        Float valueOf = Float.valueOf(0.0f);
        Number[] numberArr2 = {0, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        return new MbcBand(numberArr2[0].intValue() > 0, numberArr2[1].floatValue(), numberArr2[2].floatValue(), numberArr2[3].floatValue(), numberArr2[4].floatValue(), numberArr2[5].floatValue(), numberArr2[6].floatValue(), numberArr2[7].floatValue(), numberArr2[8].floatValue(), numberArr2[9].floatValue(), numberArr2[10].floatValue());
    }

    private void updateEngineMbcBandByChannelIndex(int i, int i2, MbcBand mbcBand) {
        setNumberArray(new Number[]{85, Integer.valueOf(i), Integer.valueOf(i2)}, new Number[]{Integer.valueOf(mbcBand.isEnabled() ? 1 : 0), Float.valueOf(mbcBand.getCutoffFrequency()), Float.valueOf(mbcBand.getAttackTime()), Float.valueOf(mbcBand.getReleaseTime()), Float.valueOf(mbcBand.getRatio()), Float.valueOf(mbcBand.getThreshold()), Float.valueOf(mbcBand.getKneeWidth()), Float.valueOf(mbcBand.getNoiseGateThreshold()), Float.valueOf(mbcBand.getExpanderRatio()), Float.valueOf(mbcBand.getPreGain()), Float.valueOf(mbcBand.getPostGain())});
    }

    private void updateEngineMbcByChannelIndex(int i, Mbc mbc) {
        int bandCount = mbc.getBandCount();
        setNumberArray(new Number[]{80, Integer.valueOf(i)}, new Number[]{Integer.valueOf(mbc.isInUse() ? 1 : 0), Integer.valueOf(mbc.isEnabled() ? 1 : 0), Integer.valueOf(bandCount)});
        for (int i2 = 0; i2 < bandCount; i2++) {
            updateEngineMbcBandByChannelIndex(i, i2, mbc.getBand(i2));
        }
    }

    private void updateEngineLimiterByChannelIndex(int i, Limiter limiter) {
        setNumberArray(new Number[]{112, Integer.valueOf(i)}, new Number[]{Integer.valueOf(limiter.isInUse() ? 1 : 0), Integer.valueOf(limiter.isEnabled() ? 1 : 0), Integer.valueOf(limiter.getLinkGroup()), Float.valueOf(limiter.getAttackTime()), Float.valueOf(limiter.getReleaseTime()), Float.valueOf(limiter.getRatio()), Float.valueOf(limiter.getThreshold()), Float.valueOf(limiter.getPostGain())});
    }

    private Limiter queryEngineLimiterByChannelIndex(int i) {
        Number[] numberArr = {112, Integer.valueOf(i)};
        Float valueOf = Float.valueOf(0.0f);
        Number[] numberArr2 = {0, 0, 0, valueOf, valueOf, valueOf, valueOf, valueOf};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        return new Limiter(numberArr2[0].intValue() > 0, numberArr2[1].intValue() > 0, numberArr2[2].intValue(), numberArr2[3].floatValue(), numberArr2[4].floatValue(), numberArr2[5].floatValue(), numberArr2[6].floatValue(), numberArr2[7].floatValue());
    }

    private Channel queryEngineByChannelIndex(int i) {
        float twoFloat = getTwoFloat(32, i);
        Eq queryEngineEqByChannelIndex = queryEngineEqByChannelIndex(64, i);
        Mbc queryEngineMbcByChannelIndex = queryEngineMbcByChannelIndex(i);
        Eq queryEngineEqByChannelIndex2 = queryEngineEqByChannelIndex(96, i);
        Limiter queryEngineLimiterByChannelIndex = queryEngineLimiterByChannelIndex(i);
        Channel channel = new Channel(twoFloat, queryEngineEqByChannelIndex.isInUse(), queryEngineEqByChannelIndex.getBandCount(), queryEngineMbcByChannelIndex.isInUse(), queryEngineMbcByChannelIndex.getBandCount(), queryEngineEqByChannelIndex2.isInUse(), queryEngineEqByChannelIndex2.getBandCount(), queryEngineLimiterByChannelIndex.isInUse());
        channel.setInputGain(twoFloat);
        channel.setPreEq(queryEngineEqByChannelIndex);
        channel.setMbc(queryEngineMbcByChannelIndex);
        channel.setPostEq(queryEngineEqByChannelIndex2);
        channel.setLimiter(queryEngineLimiterByChannelIndex);
        return channel;
    }

    private void updateEngineChannelByChannelIndex(int i, Channel channel) {
        setTwoFloat(32, i, channel.getInputGain());
        updateEngineEqByChannelIndex(64, i, channel.getPreEq());
        updateEngineMbcByChannelIndex(i, channel.getMbc());
        updateEngineEqByChannelIndex(96, i, channel.getPostEq());
        updateEngineLimiterByChannelIndex(i, channel.getLimiter());
    }

    private int getOneInt(int i) {
        int[] iArr = new int[1];
        checkStatus(getParameter(new int[]{i}, iArr));
        return iArr[0];
    }

    private void setTwoFloat(int i, int i2, float f) {
        checkStatus(setParameter(new int[]{i, i2}, floatToByteArray(f)));
    }

    private byte[] numberArrayToByteArray(Number[] numberArr) {
        int i = 0;
        for (int i2 = 0; i2 < numberArr.length; i2++) {
            Number number = numberArr[i2];
            if (!(number instanceof Integer) && !(number instanceof Float)) {
                throw new IllegalArgumentException("unknown value type " + numberArr[i2].getClass());
            }
            i += 4;
        }
        ByteBuffer allocate = ByteBuffer.allocate(i);
        allocate.order(ByteOrder.nativeOrder());
        for (Number number2 : numberArr) {
            if (number2 instanceof Integer) {
                allocate.putInt(number2.intValue());
            } else if (number2 instanceof Float) {
                allocate.putFloat(number2.floatValue());
            }
        }
        return allocate.array();
    }

    private void byteArrayToNumberArray(byte[] bArr, Number[] numberArr) {
        int i;
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length && i3 < numberArr.length) {
            Number number = numberArr[i3];
            if (number instanceof Integer) {
                i = i3 + 1;
                numberArr[i3] = Integer.valueOf(byteArrayToInt(bArr, i2));
            } else if (number instanceof Float) {
                i = i3 + 1;
                numberArr[i3] = Float.valueOf(byteArrayToFloat(bArr, i2));
            } else {
                throw new IllegalArgumentException("can't convert " + numberArr[i3].getClass());
            }
            i2 += 4;
            i3 = i;
        }
        if (i3 == numberArr.length) {
            return;
        }
        throw new IllegalArgumentException("only converted " + i3 + " values out of " + numberArr.length + " expected");
    }

    private void setNumberArray(Number[] numberArr, Number[] numberArr2) {
        checkStatus(setParameter(numberArrayToByteArray(numberArr), numberArrayToByteArray(numberArr2)));
    }

    private float getTwoFloat(int i, int i2) {
        int[] iArr = {i, i2};
        byte[] bArr = new byte[4];
        checkStatus(getParameter(iArr, bArr));
        return byteArrayToFloat(bArr);
    }

    private void updateEffectArchitecture() {
        this.mChannelCount = getChannelCount();
    }

    private class BaseParameterListener implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            OnParameterChangeListener onParameterChangeListener;
            if (i != 0) {
                return;
            }
            synchronized (DynamicsProcessing.this.mParamListenerLock) {
                onParameterChangeListener = DynamicsProcessing.this.mParamListener != null ? DynamicsProcessing.this.mParamListener : null;
            }
            if (onParameterChangeListener != null) {
                int byteArrayToInt = bArr.length == 4 ? AudioEffect.byteArrayToInt(bArr, 0) : -1;
                int byteArrayToInt2 = bArr2.length == 4 ? AudioEffect.byteArrayToInt(bArr2, 0) : Integer.MIN_VALUE;
                if (byteArrayToInt == -1 || byteArrayToInt2 == Integer.MIN_VALUE) {
                    return;
                }
                onParameterChangeListener.onParameterChange(DynamicsProcessing.this, byteArrayToInt, byteArrayToInt2);
            }
        }
    }

    public void setParameterListener(OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                BaseParameterListener baseParameterListener = new BaseParameterListener();
                this.mBaseParamListener = baseParameterListener;
                super.setParameterListener(baseParameterListener);
            }
            this.mParamListener = onParameterChangeListener;
        }
    }

    public static class Settings {
        public int channelCount;
        public float[] inputGain;

        public Settings() {
        }

        public Settings(String str) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, "=;");
            if (stringTokenizer.countTokens() != 3) {
                throw new IllegalArgumentException("settings: " + str);
            }
            String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(DynamicsProcessing.TAG)) {
                throw new IllegalArgumentException("invalid settings for DynamicsProcessing: " + nextToken);
            }
            try {
                String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("channelCount")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken2);
                }
                short parseShort = Short.parseShort(stringTokenizer.nextToken());
                this.channelCount = parseShort;
                if (parseShort > 32) {
                    throw new IllegalArgumentException("too many channels Settings:" + str);
                }
                int countTokens = stringTokenizer.countTokens();
                int i = this.channelCount;
                if (countTokens != i) {
                    throw new IllegalArgumentException("settings: " + str);
                }
                this.inputGain = new float[i];
                for (int i2 = 0; i2 < this.channelCount; i2++) {
                    String nextToken3 = stringTokenizer.nextToken();
                    if (!nextToken3.equals(i2 + "_inputGain")) {
                        throw new IllegalArgumentException("invalid key name: " + nextToken3);
                    }
                    this.inputGain[i2] = Float.parseFloat(stringTokenizer.nextToken());
                }
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public String toString() {
            String str = new String("DynamicsProcessing;channelCount=" + Integer.toString(this.channelCount));
            for (int i = 0; i < this.channelCount; i++) {
                str = str.concat(NavigationBarInflaterView.GRAVITY_SEPARATOR + i + "_inputGain=" + Float.toString(this.inputGain[i]));
            }
            return str;
        }
    }

    public Settings getProperties() {
        Settings settings = new Settings();
        settings.channelCount = getChannelCount();
        if (settings.channelCount > 32) {
            throw new IllegalArgumentException("too many channels Settings:" + settings);
        }
        settings.inputGain = new float[settings.channelCount];
        for (int i = 0; i < settings.channelCount; i++) {
        }
        return settings;
    }

    public void setProperties(Settings settings) {
        if (settings.channelCount != settings.inputGain.length || settings.channelCount != this.mChannelCount) {
            throw new IllegalArgumentException("settings invalid channel count: " + settings.channelCount);
        }
        for (int i = 0; i < this.mChannelCount; i++) {
        }
    }
}
