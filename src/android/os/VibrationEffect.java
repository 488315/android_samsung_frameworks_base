package android.os;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Parcelable;
import android.os.vibrator.BasicPwleSegment;
import android.os.vibrator.Flags;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.PwleSegment;
import android.os.vibrator.RampSegment;
import android.os.vibrator.SemHapticSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.provider.TimeZoneRulesDataContract;
import android.util.MathUtils;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;

/* loaded from: classes3.dex */
public abstract class VibrationEffect implements Parcelable {
    public static final int DEFAULT_AMPLITUDE = -1;
    public static final int EFFECT_CLICK = 0;
    public static final int EFFECT_DOUBLE_CLICK = 1;
    public static final int EFFECT_HEAVY_CLICK = 5;
    public static final int EFFECT_POP = 4;
    public static final int EFFECT_STRENGTH_LIGHT = 0;
    public static final int EFFECT_STRENGTH_MEDIUM = 1;
    public static final int EFFECT_STRENGTH_STRONG = 2;
    public static final int EFFECT_TEXTURE_TICK = 21;
    public static final int EFFECT_THUD = 3;
    public static final int EFFECT_TICK = 2;
    public static final int MAX_AMPLITUDE = 255;
    private static final long MAX_HAPTIC_FEEDBACK_COMPOSITION_SIZE = 3;
    private static final long MAX_HAPTIC_FEEDBACK_DURATION = 1000;
    private static final int PARCEL_TOKEN_COMPOSED = 1;
    private static final int PARCEL_TOKEN_VENDOR_EFFECT = 2;
    private static final float SCALE_GAMMA = 0.65f;
    protected int mMagnitude = -1;
    protected SemMagnitudeType mMagnitudeType = SemMagnitudeType.TYPE_EXTRA;
    public static final int[] RINGTONES = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    public static final Parcelable.Creator<VibrationEffect> CREATOR = new Parcelable.Creator<VibrationEffect>() { // from class: android.os.VibrationEffect.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationEffect createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == 1) {
                return new Composed(parcel);
            }
            if (i == 2 && Flags.vendorVibrationEffects()) {
                return new VendorEffect(parcel);
            }
            throw new IllegalStateException("Unexpected vibration effect type token in parcel.");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationEffect[] newArray(int i) {
            return new VibrationEffect[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface EffectType {
    }

    public enum SemMagnitudeType {
        TYPE_TOUCH,
        TYPE_NOTIFICATION,
        TYPE_CALL,
        TYPE_MAX,
        TYPE_MIN,
        TYPE_EXTRA,
        TYPE_FORCE
    }

    public interface Transformation<ParamT> {
        VibrationEffect transform(VibrationEffect vibrationEffect, ParamT paramt);
    }

    public abstract VibrationEffect applyAdaptiveScale(float f);

    public abstract VibrationEffect applyEffectStrength(int i);

    public abstract VibrationEffect applyRepeatingIndefinitely(boolean z, int i);

    public abstract boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo);

    public abstract long[] computeCreateWaveformOffOnTimingsOrNull();

    public abstract VibrationEffect cropToLengthOrNull(int i);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract long getDuration();

    public boolean isHapticFeedbackCandidate() {
        return false;
    }

    public abstract VibrationEffect resolve(int i);

    public abstract VibrationEffect scale(float f);

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends VibrationEffect> T semApplyEffectStrength(int i) {
        return this;
    }

    public abstract String toDebugString();

    public abstract void validate();

    public static VibrationEffect createOneShot(long j, int i) {
        if (i != 0) {
            return createWaveform(new long[]{j}, new int[]{i}, -1);
        }
        throw new IllegalArgumentException("amplitude must either be DEFAULT_AMPLITUDE, or between 1 and 255 inclusive (amplitude=" + i + NavigationBarInflaterView.KEY_CODE_END);
    }

    public static VibrationEffect createWaveform(long[] jArr, int i) {
        int[] iArr = new int[jArr.length];
        for (int i2 = 0; i2 < jArr.length / 2; i2++) {
            iArr[(i2 * 2) + 1] = -1;
        }
        return createWaveform(jArr, iArr, i);
    }

    public static VibrationEffect createWaveform(long[] jArr, int[] iArr, int i) {
        if (jArr.length != iArr.length) {
            throw new IllegalArgumentException("timing and amplitude arrays must be of equal length (timings.length=" + jArr.length + ", amplitudes.length=" + iArr.length + NavigationBarInflaterView.KEY_CODE_END);
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jArr.length; i2++) {
            int i3 = iArr[i2];
            arrayList.add(new StepSegment(i3 == -1 ? -1.0f : i3 / 255.0f, 0.0f, (int) jArr[i2]));
        }
        Composed composed = new Composed(arrayList, i);
        composed.validate();
        return composed;
    }

    public static VibrationEffect createPredefined(int i) {
        return get(i, true);
    }

    @SystemApi
    public static VibrationEffect createVendorEffect(PersistableBundle persistableBundle) {
        VendorEffect vendorEffect = new VendorEffect(persistableBundle, 1, 1.0f, 1.0f);
        vendorEffect.validate();
        return vendorEffect;
    }

    public static VibrationEffect get(int i) {
        return get(i, true);
    }

    public static VibrationEffect get(int i, boolean z) {
        Composed composed = new Composed(new PrebakedSegment(i, z, 1));
        composed.validate();
        return composed;
    }

    public static VibrationEffect get(Uri uri, Context context) throws Resources.NotFoundException {
        Uri uriUncanonicalize;
        String[] stringArray = context.getResources().getStringArray(R.array.config_ringtoneEffectUris);
        if (stringArray.length == 0) {
            return null;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriUncanonicalize2 = contentResolver.uncanonicalize(uri);
        if (uriUncanonicalize2 != null) {
            uri = uriUncanonicalize2;
        }
        for (int i = 0; i < stringArray.length; i++) {
            int[] iArr = RINGTONES;
            if (i >= iArr.length) {
                break;
            }
            String str = stringArray[i];
            if (str != null && (uriUncanonicalize = contentResolver.uncanonicalize(Uri.parse(str))) != null && uriUncanonicalize.equals(uri)) {
                return get(iArr[i]);
            }
        }
        return null;
    }

    public static Composition startComposition() {
        return new Composition();
    }

    public static WaveformBuilder startWaveform() {
        return new WaveformBuilder();
    }

    public static WaveformBuilder startWaveform(VibrationParameter vibrationParameter) {
        WaveformBuilder waveformBuilderStartWaveform = startWaveform();
        waveformBuilderStartWaveform.addTransition(Duration.ZERO, vibrationParameter);
        return waveformBuilderStartWaveform;
    }

    public static WaveformBuilder startWaveform(VibrationParameter vibrationParameter, VibrationParameter vibrationParameter2) {
        WaveformBuilder waveformBuilderStartWaveform = startWaveform();
        waveformBuilderStartWaveform.addTransition(Duration.ZERO, vibrationParameter, vibrationParameter2);
        return waveformBuilderStartWaveform;
    }

    public long getDuration(VibratorInfo vibratorInfo) {
        return getDuration();
    }

    public static float scale(float f, float f2) {
        if (Flags.hapticsScaleV2Enabled()) {
            return (Float.compare(f2, 1.0f) <= 0 || Float.compare(f, 0.0f) == 0) ? f2 * f : (f2 * f) / ((((f2 - 1.0f) * f) * f) + 1.0f);
        }
        float fPow = MathUtils.pow(f2, 1.5384616f);
        if (f2 <= 1.0f) {
            return f * fPow;
        }
        float fPow2 = MathUtils.pow(f2, 4.0f - f2);
        float fExp = MathUtils.exp(f * fPow * fPow2);
        float fExp2 = MathUtils.exp(fPow * fPow2);
        return MathUtils.constrain(((fExp2 + 1.0f) / (fExp2 - 1.0f)) * ((fExp - 1.0f) / (fExp + 1.0f)), 0.0f, 1.0f);
    }

    public static float scaleLinearly(float f, float f2) {
        return MathUtils.constrain(f * f2, 0.0f, 1.0f);
    }

    public static String effectIdToString(int i) {
        if (i == 0) {
            return "CLICK";
        }
        if (i == 1) {
            return "DOUBLE_CLICK";
        }
        if (i == 2) {
            return "TICK";
        }
        if (i == 3) {
            return "THUD";
        }
        if (i == 4) {
            return "POP";
        }
        if (i == 5) {
            return "HEAVY_CLICK";
        }
        if (i == 21) {
            return "TEXTURE_TICK";
        }
        return Integer.toString(i);
    }

    public static String effectStrengthToString(int i) {
        if (i == 0) {
            return "LIGHT";
        }
        if (i == 1) {
            return "MEDIUM";
        }
        if (i == 2) {
            return "STRONG";
        }
        return Integer.toString(i);
    }

    public static final class Composed extends VibrationEffect {
        public static final Parcelable.Creator<Composed> CREATOR = new Parcelable.Creator<Composed>() { // from class: android.os.VibrationEffect.Composed.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Composed createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new Composed(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Composed[] newArray(int i) {
                return new Composed[i];
            }
        };
        private final int mRepeatIndex;
        private final ArrayList<VibrationEffectSegment> mSegments;

        @Override // android.os.VibrationEffect, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        Composed(Parcel parcel) {
            this((List) Objects.requireNonNull(parcel.readArrayList(VibrationEffectSegment.class.getClassLoader(), VibrationEffectSegment.class)), parcel.readInt());
            this.mMagnitude = parcel.readInt();
            this.mMagnitudeType = SemMagnitudeType.values()[parcel.readInt()];
        }

        Composed(VibrationEffectSegment vibrationEffectSegment) {
            this(Arrays.asList(vibrationEffectSegment), -1);
        }

        public Composed(List<? extends VibrationEffectSegment> list, int i) {
            this.mSegments = new ArrayList<>(list);
            this.mRepeatIndex = i;
        }

        public List<VibrationEffectSegment> getSegments() {
            return this.mSegments;
        }

        public int getRepeatIndex() {
            return this.mRepeatIndex;
        }

        @Override // android.os.VibrationEffect
        public long[] computeCreateWaveformOffOnTimingsOrNull() {
            if (getRepeatIndex() >= 0) {
                return null;
            }
            List<VibrationEffectSegment> segments = getSegments();
            long[] jArr = new long[segments.size() + 1];
            int i = 0;
            for (int i2 = 0; i2 < segments.size(); i2++) {
                StepSegment stepSegmentCastToValidStepSegmentForOffOnTimingsOrNull = castToValidStepSegmentForOffOnTimingsOrNull(segments.get(i2));
                if (stepSegmentCastToValidStepSegmentForOffOnTimingsOrNull == null) {
                    return null;
                }
                if ((stepSegmentCastToValidStepSegmentForOffOnTimingsOrNull.getAmplitude() == 0.0f) != (i % 2 == 0)) {
                    i++;
                }
                jArr[i] = jArr[i] + stepSegmentCastToValidStepSegmentForOffOnTimingsOrNull.getDuration();
            }
            return Arrays.copyOf(jArr, i + 1);
        }

        @Override // android.os.VibrationEffect
        public void validate() {
            int size = this.mSegments.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                VibrationEffectSegment vibrationEffectSegment = this.mSegments.get(i);
                vibrationEffectSegment.validate();
                z |= vibrationEffectSegment.getDuration() != 0;
            }
            if (!z) {
                throw new IllegalArgumentException("at least one timing must be non-zero (segments=" + this.mSegments + NavigationBarInflaterView.KEY_CODE_END);
            }
            int i2 = this.mRepeatIndex;
            if (i2 != -1) {
                Preconditions.checkArgumentInRange(i2, 0, size - 1, "repeat index must be within the bounds of the segments (segments.length=" + size + ", index=" + this.mRepeatIndex + NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        @Override // android.os.VibrationEffect
        public VibrationEffect cropToLengthOrNull(int i) {
            if (this.mRepeatIndex >= 0) {
                return null;
            }
            if (this.mSegments.size() <= i) {
                return this;
            }
            Composed composed = new Composed(new ArrayList(this.mSegments.subList(0, i)), this.mRepeatIndex);
            try {
                composed.validate();
                return composed;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        @Override // android.os.VibrationEffect
        public long getDuration() {
            return getDuration(new Function() { // from class: android.os.VibrationEffect$Composed$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(((VibrationEffectSegment) obj).getDuration());
                }
            });
        }

        @Override // android.os.VibrationEffect
        public long getDuration(final VibratorInfo vibratorInfo) {
            return getDuration(new Function() { // from class: android.os.VibrationEffect$Composed$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(((VibrationEffectSegment) obj).getDuration(vibratorInfo));
                }
            });
        }

        private long getDuration(Function<VibrationEffectSegment, Long> function) {
            if (this.mRepeatIndex >= 0) {
                return Long.MAX_VALUE;
            }
            int size = this.mSegments.size();
            long j = 0;
            for (int i = 0; i < size; i++) {
                long jLongValue = function.apply(this.mSegments.get(i)).longValue();
                if (jLongValue < 0) {
                    return jLongValue;
                }
                j += jLongValue;
            }
            return j;
        }

        @Override // android.os.VibrationEffect
        public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
            Iterator<VibrationEffectSegment> it = this.mSegments.iterator();
            while (it.hasNext()) {
                if (!it.next().areVibrationFeaturesSupported(vibratorInfo)) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.os.VibrationEffect
        public boolean isHapticFeedbackCandidate() {
            if (getDuration() > 1000) {
                return false;
            }
            int size = this.mSegments.size();
            if (size > 3) {
                return false;
            }
            long j = 0;
            for (int i = 0; i < size; i++) {
                if (!this.mSegments.get(i).isHapticFeedbackCandidate()) {
                    return false;
                }
                long duration = this.mSegments.get(i).getDuration();
                if (duration > 0) {
                    j += duration;
                }
            }
            return j <= 1000;
        }

        @Override // android.os.VibrationEffect
        public Composed resolve(int i) {
            return applyToSegments(new BiFunction() { // from class: android.os.VibrationEffect$Composed$$ExternalSyntheticLambda4
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((VibrationEffectSegment) obj).resolve(((Integer) obj2).intValue());
                }
            }, Integer.valueOf(i));
        }

        @Override // android.os.VibrationEffect
        public VibrationEffect applyEffectStrength(int i) {
            return applyToSegments(new BiFunction() { // from class: android.os.VibrationEffect$Composed$$ExternalSyntheticLambda5
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((VibrationEffectSegment) obj).applyEffectStrength(((Integer) obj2).intValue());
                }
            }, Integer.valueOf(i));
        }

        @Override // android.os.VibrationEffect
        public Composed scale(float f) {
            return applyToSegments(new BiFunction() { // from class: android.os.VibrationEffect$Composed$$ExternalSyntheticLambda3
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((VibrationEffectSegment) obj).scale(((Float) obj2).floatValue());
                }
            }, Float.valueOf(f));
        }

        @Override // android.os.VibrationEffect
        public Composed applyAdaptiveScale(float f) {
            return applyToSegments(new BiFunction() { // from class: android.os.VibrationEffect$Composed$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((VibrationEffectSegment) obj).scaleLinearly(((Float) obj2).floatValue());
                }
            }, Float.valueOf(f));
        }

        @Override // android.os.VibrationEffect
        public Composed applyRepeatingIndefinitely(boolean z, int i) {
            if ((this.mRepeatIndex >= 0) == z) {
                return this;
            }
            if (!z) {
                return new Composed(this.mSegments, -1);
            }
            if (i <= 0) {
                return new Composed(this.mSegments, 0);
            }
            ArrayList arrayList = new ArrayList(this.mSegments.size() + 1);
            arrayList.addAll(this.mSegments);
            arrayList.add(new StepSegment(0.0f, 0.0f, i));
            return new Composed(arrayList, 0);
        }

        @Override // android.os.VibrationEffect
        public Composed semApplyEffectStrength(int i) {
            int size = this.mSegments.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(this.mSegments.get(i2).applyEffectStrength(i));
            }
            if (arrayList.equals(this.mSegments)) {
                return this;
            }
            Composed composed = new Composed(arrayList, this.mRepeatIndex);
            composed.validate();
            return composed;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Composed) {
                Composed composed = (Composed) obj;
                if (this.mSegments.equals(composed.mSegments) && this.mRepeatIndex == composed.mRepeatIndex) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mSegments, Integer.valueOf(this.mRepeatIndex));
        }

        public String toString() {
            return "Composed{segments=" + this.mSegments + ", repeat=" + this.mRepeatIndex + ", mMagnitudeType=" + this.mMagnitudeType.toString() + "}";
        }

        @Override // android.os.VibrationEffect
        public String toDebugString() {
            if (this.mSegments.size() == 1 && this.mRepeatIndex < 0) {
                return this.mSegments.get(0).toDebugString();
            }
            StringJoiner stringJoiner = new StringJoiner(",", NavigationBarInflaterView.SIZE_MOD_START, NavigationBarInflaterView.SIZE_MOD_END);
            for (int i = 0; i < this.mSegments.size(); i++) {
                stringJoiner.add(this.mSegments.get(i).toDebugString());
            }
            if (this.mRepeatIndex >= 0) {
                return String.format(Locale.ROOT, "%s, repeat=%d", stringJoiner, Integer.valueOf(this.mRepeatIndex));
            }
            return stringJoiner.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(1);
            parcel.writeList(this.mSegments);
            parcel.writeInt(this.mRepeatIndex);
            parcel.writeInt(this.mMagnitude);
            parcel.writeInt(this.mMagnitudeType.ordinal());
        }

        private static StepSegment castToValidStepSegmentForOffOnTimingsOrNull(VibrationEffectSegment vibrationEffectSegment) {
            if (!(vibrationEffectSegment instanceof StepSegment)) {
                return null;
            }
            StepSegment stepSegment = (StepSegment) vibrationEffectSegment;
            if (stepSegment.getFrequencyHz() != 0.0f) {
                return null;
            }
            float amplitude = stepSegment.getAmplitude();
            if (amplitude == 0.0f || amplitude == -1.0f) {
                return stepSegment;
            }
            return null;
        }

        private <T> Composed applyToSegments(BiFunction<VibrationEffectSegment, T, VibrationEffectSegment> biFunction, T t) {
            int size = this.mSegments.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(biFunction.apply(this.mSegments.get(i), t));
            }
            if (this.mSegments.equals(arrayList)) {
                return this;
            }
            Composed composed = new Composed(arrayList, this.mRepeatIndex);
            composed.validate();
            return composed;
        }
    }

    public static final class VendorEffect extends VibrationEffect {
        public static final Parcelable.Creator<VendorEffect> CREATOR = new Parcelable.Creator<VendorEffect>() { // from class: android.os.VibrationEffect.VendorEffect.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VendorEffect createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new VendorEffect(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VendorEffect[] newArray(int i) {
                return new VendorEffect[i];
            }
        };
        public static final float DEFAULT_SCALE = 1.0f;
        public static final int DEFAULT_STRENGTH = 1;
        private final float mAdaptiveScale;
        private final int mEffectStrength;
        private final float mScale;
        private final PersistableBundle mVendorData;

        @Override // android.os.VibrationEffect
        public VendorEffect applyRepeatingIndefinitely(boolean z, int i) {
            return this;
        }

        @Override // android.os.VibrationEffect
        public long[] computeCreateWaveformOffOnTimingsOrNull() {
            return null;
        }

        @Override // android.os.VibrationEffect
        public VibrationEffect cropToLengthOrNull(int i) {
            return null;
        }

        @Override // android.os.VibrationEffect, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.VibrationEffect
        public long getDuration() {
            return -1L;
        }

        @Override // android.os.VibrationEffect
        public boolean isHapticFeedbackCandidate() {
            return false;
        }

        @Override // android.os.VibrationEffect
        public VendorEffect resolve(int i) {
            return this;
        }

        VendorEffect(Parcel parcel) {
            this((PersistableBundle) Objects.requireNonNull(parcel.readPersistableBundle(VibrationEffect.class.getClassLoader())), parcel.readInt(), parcel.readFloat(), parcel.readFloat());
        }

        public VendorEffect(PersistableBundle persistableBundle, int i, float f, float f2) {
            this.mVendorData = persistableBundle;
            this.mEffectStrength = i;
            this.mScale = f;
            this.mAdaptiveScale = f2;
        }

        public PersistableBundle getVendorData() {
            return this.mVendorData;
        }

        public int getEffectStrength() {
            return this.mEffectStrength;
        }

        public float getScale() {
            return this.mScale;
        }

        public float getAdaptiveScale() {
            return this.mAdaptiveScale;
        }

        @Override // android.os.VibrationEffect
        public void validate() {
            Preconditions.checkArgument(!this.mVendorData.isEmpty(), "Vendor effect bundle must be non-empty");
        }

        @Override // android.os.VibrationEffect
        public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
            return vibratorInfo.hasCapability(2048L);
        }

        @Override // android.os.VibrationEffect
        public VibrationEffect applyEffectStrength(int i) {
            if (this.mEffectStrength == i) {
                return this;
            }
            VendorEffect vendorEffect = new VendorEffect(this.mVendorData, i, this.mScale, this.mAdaptiveScale);
            vendorEffect.validate();
            return vendorEffect;
        }

        @Override // android.os.VibrationEffect
        public VendorEffect scale(float f) {
            if (Float.compare(this.mScale, f) == 0) {
                return this;
            }
            VendorEffect vendorEffect = new VendorEffect(this.mVendorData, this.mEffectStrength, f, this.mAdaptiveScale);
            vendorEffect.validate();
            return vendorEffect;
        }

        @Override // android.os.VibrationEffect
        public VibrationEffect applyAdaptiveScale(float f) {
            if (Float.compare(this.mAdaptiveScale, f) == 0) {
                return this;
            }
            VendorEffect vendorEffect = new VendorEffect(this.mVendorData, this.mEffectStrength, this.mScale, f);
            vendorEffect.validate();
            return vendorEffect;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof VendorEffect) {
                VendorEffect vendorEffect = (VendorEffect) obj;
                if (this.mEffectStrength == vendorEffect.mEffectStrength && Float.compare(this.mScale, vendorEffect.mScale) == 0 && Float.compare(this.mAdaptiveScale, vendorEffect.mAdaptiveScale) == 0 && isPersistableBundleEquals(this.mVendorData, vendorEffect.mVendorData)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mVendorData.size()), Integer.valueOf(this.mEffectStrength), Float.valueOf(this.mScale), Float.valueOf(this.mAdaptiveScale));
        }

        public String toString() {
            return String.format(Locale.ROOT, "VendorEffect{vendorData=%s, strength=%s, scale=%.2f, adaptiveScale=%.2f}", this.mVendorData, effectStrengthToString(this.mEffectStrength), Float.valueOf(this.mScale), Float.valueOf(this.mAdaptiveScale));
        }

        @Override // android.os.VibrationEffect
        public String toDebugString() {
            return String.format(Locale.ROOT, "vendorEffect=%s, strength=%s, scale=%.2f, adaptiveScale=%.2f", this.mVendorData.toShortString(), effectStrengthToString(this.mEffectStrength), Float.valueOf(this.mScale), Float.valueOf(this.mAdaptiveScale));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(2);
            parcel.writePersistableBundle(this.mVendorData);
            parcel.writeInt(this.mEffectStrength);
            parcel.writeFloat(this.mScale);
            parcel.writeFloat(this.mAdaptiveScale);
        }

        private static boolean isPersistableBundleEquals(PersistableBundle persistableBundle, PersistableBundle persistableBundle2) {
            if (persistableBundle == persistableBundle2) {
                return true;
            }
            if (persistableBundle == null || persistableBundle2 == null || persistableBundle.size() != persistableBundle2.size()) {
                return false;
            }
            for (String str : persistableBundle.keySet()) {
                if (!isPersistableBundleSupportedValueEquals(persistableBundle.get(str), persistableBundle2.get(str))) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isPersistableBundleSupportedValueEquals(Object obj, Object obj2) {
            if (obj == obj2) {
                return true;
            }
            if (obj == null || obj2 == null || !obj.getClass().equals(obj2.getClass())) {
                return false;
            }
            if (obj instanceof PersistableBundle) {
                return isPersistableBundleEquals((PersistableBundle) obj, (PersistableBundle) obj2);
            }
            if (obj instanceof int[]) {
                return Arrays.equals((int[]) obj, (int[]) obj2);
            }
            if (obj instanceof long[]) {
                return Arrays.equals((long[]) obj, (long[]) obj2);
            }
            if (obj instanceof double[]) {
                return Arrays.equals((double[]) obj, (double[]) obj2);
            }
            if (obj instanceof boolean[]) {
                return Arrays.equals((boolean[]) obj, (boolean[]) obj2);
            }
            if (obj instanceof String[]) {
                return Arrays.equals((String[]) obj, (String[]) obj2);
            }
            return Objects.equals(obj, obj2);
        }
    }

    public static VibrationEffect createRepeatingEffect(VibrationEffect vibrationEffect) {
        return startComposition().repeatEffectIndefinitely(vibrationEffect).compose();
    }

    public static VibrationEffect createRepeatingEffect(VibrationEffect vibrationEffect, VibrationEffect vibrationEffect2) {
        Preconditions.checkArgument(vibrationEffect.getDuration() < Long.MAX_VALUE, "Can't repeat an indefinitely repeating effect.");
        return startComposition().addEffect(vibrationEffect).repeatEffectIndefinitely(vibrationEffect2).compose();
    }

    public static final class Composition {
        public static final int DELAY_TYPE_PAUSE = 0;
        public static final int DELAY_TYPE_RELATIVE_START_OFFSET = 1;
        public static final int PRIMITIVE_CLICK = 1;
        public static final int PRIMITIVE_LOW_TICK = 8;
        public static final int PRIMITIVE_NOOP = 0;
        public static final int PRIMITIVE_QUICK_FALL = 6;
        public static final int PRIMITIVE_QUICK_RISE = 4;
        public static final int PRIMITIVE_SLOW_RISE = 5;
        public static final int PRIMITIVE_SPIN = 3;
        public static final int PRIMITIVE_THUD = 2;
        public static final int PRIMITIVE_TICK = 7;
        private final ArrayList<VibrationEffectSegment> mSegments = new ArrayList<>();
        private int mRepeatIndex = -1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DelayType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface PrimitiveType {
        }

        public static final class UnreachableAfterRepeatingIndefinitelyException extends IllegalStateException {
            UnreachableAfterRepeatingIndefinitelyException() {
                super("Compositions ending in an indefinitely repeating effect can't be extended");
            }
        }

        Composition() {
        }

        public Composition addOffDuration(Duration duration) {
            int millis = (int) duration.toMillis();
            Preconditions.checkArgumentNonnegative(millis, "Off period must be non-negative");
            if (millis > 0) {
                addSegment(new StepSegment(0.0f, 0.0f, (int) duration.toMillis()));
            }
            return this;
        }

        public Composition addEffect(VibrationEffect vibrationEffect) {
            return addSegments(vibrationEffect);
        }

        public Composition repeatEffectIndefinitely(VibrationEffect vibrationEffect) {
            Preconditions.checkArgument(vibrationEffect.getDuration() < Long.MAX_VALUE, "Can't repeat an indefinitely repeating effect. Consider addEffect instead.");
            int size = this.mSegments.size();
            addSegments(vibrationEffect);
            this.mRepeatIndex = size;
            return this;
        }

        public Composition addPrimitive(int i) {
            return addPrimitive(i, 1.0f);
        }

        public Composition addPrimitive(int i, float f) {
            return addPrimitive(i, f, 0);
        }

        public Composition addPrimitive(int i, float f, int i2) {
            return addPrimitive(i, f, i2, 0);
        }

        public Composition addPrimitive(int i, float f, int i2, int i3) {
            PrimitiveSegment primitiveSegment = new PrimitiveSegment(i, f, i2, i3);
            primitiveSegment.validate();
            return addSegment(primitiveSegment);
        }

        private Composition addSegment(VibrationEffectSegment vibrationEffectSegment) {
            if (this.mRepeatIndex >= 0) {
                throw new UnreachableAfterRepeatingIndefinitelyException();
            }
            this.mSegments.add(vibrationEffectSegment);
            return this;
        }

        private Composition addSegments(VibrationEffect vibrationEffect) {
            if (this.mRepeatIndex >= 0) {
                throw new UnreachableAfterRepeatingIndefinitelyException();
            }
            if (!(vibrationEffect instanceof Composed)) {
                throw new IllegalArgumentException("Can't add vendor effects to composition.");
            }
            Composed composed = (Composed) vibrationEffect;
            if (composed.getRepeatIndex() >= 0) {
                this.mRepeatIndex = this.mSegments.size() + composed.getRepeatIndex();
            }
            this.mSegments.addAll(composed.getSegments());
            return this;
        }

        public VibrationEffect compose() {
            if (this.mSegments.isEmpty()) {
                throw new IllegalStateException("Composition must have at least one element to compose.");
            }
            Composed composed = new Composed(this.mSegments, this.mRepeatIndex);
            composed.validate();
            return composed;
        }

        public static String primitiveToString(int i) {
            switch (i) {
                case 0:
                    return TimeZoneRulesDataContract.Operation.TYPE_NO_OP;
                case 1:
                    return "CLICK";
                case 2:
                    return "THUD";
                case 3:
                    return "SPIN";
                case 4:
                    return "QUICK_RISE";
                case 5:
                    return "SLOW_RISE";
                case 6:
                    return "QUICK_FALL";
                case 7:
                    return "TICK";
                case 8:
                    return "LOW_TICK";
                default:
                    return Integer.toString(i);
            }
        }

        public static String delayTypeToString(int i) {
            if (i == 0) {
                return "PAUSE";
            }
            if (i == 1) {
                return "START_OFFSET";
            }
            return Integer.toString(i);
        }
    }

    public static final class WaveformEnvelopeBuilder {
        private ArrayList<PwleSegment> mSegments = new ArrayList<>();
        private float mLastAmplitude = 0.0f;
        private float mLastFrequencyHz = Float.NaN;

        public WaveformEnvelopeBuilder setInitialFrequencyHz(float f) {
            if (this.mSegments.isEmpty()) {
                this.mLastFrequencyHz = f;
                return this;
            }
            PwleSegment pwleSegment = (PwleSegment) this.mSegments.getFirst();
            this.mSegments.set(0, new PwleSegment(pwleSegment.getStartAmplitude(), pwleSegment.getEndAmplitude(), f, pwleSegment.getEndFrequencyHz(), pwleSegment.getDuration()));
            return this;
        }

        public WaveformEnvelopeBuilder addControlPoint(float f, float f2, long j) {
            if (Float.isNaN(this.mLastFrequencyHz)) {
                this.mLastFrequencyHz = f2;
            }
            this.mSegments.add(new PwleSegment(this.mLastAmplitude, f, this.mLastFrequencyHz, f2, j));
            this.mLastAmplitude = f;
            this.mLastFrequencyHz = f2;
            return this;
        }

        public VibrationEffect build() {
            if (this.mSegments.isEmpty()) {
                throw new IllegalStateException("WaveformEnvelopeBuilder must have at least one control point to build.");
            }
            Composed composed = new Composed(this.mSegments, -1);
            composed.validate();
            return composed;
        }
    }

    public static final class BasicEnvelopeBuilder {
        private ArrayList<BasicPwleSegment> mSegments = new ArrayList<>();
        private float mLastIntensity = 0.0f;
        private float mLastSharpness = Float.NaN;

        public BasicEnvelopeBuilder setInitialSharpness(float f) {
            if (this.mSegments.isEmpty()) {
                this.mLastSharpness = f;
                return this;
            }
            BasicPwleSegment basicPwleSegment = (BasicPwleSegment) this.mSegments.getFirst();
            this.mSegments.set(0, new BasicPwleSegment(basicPwleSegment.getStartIntensity(), basicPwleSegment.getEndIntensity(), f, basicPwleSegment.getEndSharpness(), basicPwleSegment.getDuration()));
            return this;
        }

        public BasicEnvelopeBuilder addControlPoint(float f, float f2, long j) {
            if (Float.isNaN(this.mLastSharpness)) {
                this.mLastSharpness = f2;
            }
            this.mSegments.add(new BasicPwleSegment(this.mLastIntensity, f, this.mLastSharpness, f2, j));
            this.mLastIntensity = f;
            this.mLastSharpness = f2;
            return this;
        }

        public VibrationEffect build() {
            if (this.mSegments.isEmpty()) {
                throw new IllegalStateException("BasicEnvelopeBuilder must have at least one control point to build.");
            }
            if (((BasicPwleSegment) this.mSegments.getLast()).getEndIntensity() != 0.0f) {
                throw new IllegalStateException("Basic envelope effects must end at a zero intensity control point.");
            }
            Composed composed = new Composed(this.mSegments, -1);
            composed.validate();
            return composed;
        }
    }

    public static final class WaveformBuilder {
        private static final float EPSILON = 1.0E-5f;
        private ArrayList<VibrationEffectSegment> mSegments = new ArrayList<>();
        private float mLastAmplitude = 0.0f;
        private float mLastFrequencyHz = 0.0f;

        WaveformBuilder() {
        }

        public WaveformBuilder addTransition(Duration duration, VibrationParameter vibrationParameter) {
            Preconditions.checkNotNull(duration, "Duration is null");
            checkVibrationParameter(vibrationParameter, "targetParameter");
            addTransitionSegment(duration, extractTargetAmplitude(vibrationParameter, null), extractTargetFrequency(vibrationParameter, null));
            return this;
        }

        public WaveformBuilder addTransition(Duration duration, VibrationParameter vibrationParameter, VibrationParameter vibrationParameter2) {
            Preconditions.checkNotNull(duration, "Duration is null");
            checkVibrationParameter(vibrationParameter, "targetParameter1");
            checkVibrationParameter(vibrationParameter2, "targetParameter2");
            Preconditions.checkArgument(!Objects.equals(vibrationParameter.getClass(), vibrationParameter2.getClass()), "Parameter arguments must specify different parameter types");
            addTransitionSegment(duration, extractTargetAmplitude(vibrationParameter, vibrationParameter2), extractTargetFrequency(vibrationParameter, vibrationParameter2));
            return this;
        }

        public WaveformBuilder addSustain(Duration duration) {
            int millis = (int) duration.toMillis();
            Preconditions.checkArgument(millis >= 1, "Sustain duration must be >= 1ms");
            this.mSegments.add(new StepSegment(this.mLastAmplitude, this.mLastFrequencyHz, millis));
            return this;
        }

        public VibrationEffect build() {
            if (this.mSegments.isEmpty()) {
                throw new IllegalStateException("WaveformBuilder must have at least one transition to build.");
            }
            Composed composed = new Composed(this.mSegments, -1);
            composed.validate();
            return composed;
        }

        private void checkVibrationParameter(VibrationParameter vibrationParameter, String str) {
            Preconditions.checkNotNull(vibrationParameter, "%s is null", str);
            Preconditions.checkArgument((vibrationParameter instanceof AmplitudeVibrationParameter) || (vibrationParameter instanceof FrequencyVibrationParameter), "%s is a unknown parameter", str);
        }

        private float extractTargetAmplitude(VibrationParameter vibrationParameter, VibrationParameter vibrationParameter2) {
            if (vibrationParameter2 instanceof AmplitudeVibrationParameter) {
                return ((AmplitudeVibrationParameter) vibrationParameter2).amplitude;
            }
            if (vibrationParameter instanceof AmplitudeVibrationParameter) {
                return ((AmplitudeVibrationParameter) vibrationParameter).amplitude;
            }
            return this.mLastAmplitude;
        }

        private float extractTargetFrequency(VibrationParameter vibrationParameter, VibrationParameter vibrationParameter2) {
            if (vibrationParameter2 instanceof FrequencyVibrationParameter) {
                return ((FrequencyVibrationParameter) vibrationParameter2).frequencyHz;
            }
            if (vibrationParameter instanceof FrequencyVibrationParameter) {
                return ((FrequencyVibrationParameter) vibrationParameter).frequencyHz;
            }
            return this.mLastFrequencyHz;
        }

        private void addTransitionSegment(Duration duration, float f, float f2) {
            float f3;
            float f4;
            Preconditions.checkNotNull(duration, "Duration is null");
            Preconditions.checkArgument(!duration.isNegative(), "Transition duration must be non-negative");
            int millis = (int) duration.toMillis();
            if (millis <= 0) {
                f3 = f;
                f4 = f2;
            } else if (Math.abs(this.mLastAmplitude - f) < 1.0E-5f && Math.abs(this.mLastFrequencyHz - f2) < 1.0E-5f) {
                this.mSegments.add(new StepSegment(f, f2, millis));
                f3 = f;
                f4 = f2;
            } else {
                f3 = f;
                f4 = f2;
                this.mSegments.add(new RampSegment(this.mLastAmplitude, f3, this.mLastFrequencyHz, f4, millis));
            }
            this.mLastAmplitude = f3;
            this.mLastFrequencyHz = f4;
        }
    }

    public static class VibrationParameter {
        VibrationParameter() {
        }

        public static VibrationParameter targetAmplitude(float f) {
            return new AmplitudeVibrationParameter(f);
        }

        public static VibrationParameter targetFrequency(float f) {
            return new FrequencyVibrationParameter(f);
        }
    }

    private static final class AmplitudeVibrationParameter extends VibrationParameter {
        public final float amplitude;

        AmplitudeVibrationParameter(float f) {
            Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Amplitude must be within [0,1]");
            this.amplitude = f;
        }
    }

    private static final class FrequencyVibrationParameter extends VibrationParameter {
        public final float frequencyHz;

        FrequencyVibrationParameter(float f) {
            Preconditions.checkArgument(f >= 1.0f, "Frequency must be >= 1");
            Preconditions.checkArgument(Float.isFinite(f), "Frequency must be finite");
            this.frequencyHz = f;
        }
    }

    public static VibrationEffect semCreateWaveform(int i, int i2, SemMagnitudeType semMagnitudeType) {
        return semCreateHaptic(i, i2, semMagnitudeType);
    }

    public static VibrationEffect semCreateWaveform(int i, int i2) {
        return semCreateHaptic(i, i2);
    }

    public static VibrationEffect semCreateHaptic(int i, int i2, SemMagnitudeType semMagnitudeType) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SemHapticSegment(i));
        Composed composed = new Composed(arrayList, i2);
        composed.semSetMagnitudeType(semMagnitudeType);
        try {
            composed.validate();
            return composed;
        } catch (Exception unused) {
            arrayList.clear();
            arrayList.add(new SemHapticSegment(SemHapticFeedbackConstants.EFFECT_SILENT));
            return new Composed(arrayList, i2);
        }
    }

    public static VibrationEffect semCreateHaptic(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SemHapticSegment(i));
        Composed composed = new Composed(arrayList, i2);
        try {
            composed.validate();
            return composed;
        } catch (Exception unused) {
            arrayList.clear();
            arrayList.add(new SemHapticSegment(SemHapticFeedbackConstants.EFFECT_SILENT));
            return new Composed(arrayList, i2);
        }
    }

    public void semSetMagnitudeType(SemMagnitudeType semMagnitudeType) {
        this.mMagnitudeType = semMagnitudeType;
    }

    public SemMagnitudeType semGetMagnitudeType() {
        return this.mMagnitudeType;
    }

    public void semSetMagnitude(int i) {
        this.mMagnitude = i;
    }

    public int semGetMagnitude() {
        return this.mMagnitude;
    }

    public static VibrationEffect createWaveform(int[] iArr, float[] fArr, float[] fArr2, int i) {
        if (iArr.length != fArr.length || iArr.length != fArr2.length) {
            throw new IllegalArgumentException("timing and amplitude arrays must be of equal length (timings.length=" + iArr.length + ", amplitudes.length=" + fArr.length + ", frequencies.length=" + fArr2.length + NavigationBarInflaterView.KEY_CODE_END);
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            arrayList.add(new StepSegment(fArr[i2], fArr2[i2], iArr[i2]));
        }
        Composed composed = new Composed(arrayList, i);
        composed.validate();
        return composed;
    }
}
