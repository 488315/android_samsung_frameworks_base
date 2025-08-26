package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.vibrator.Flags;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

/* loaded from: classes3.dex */
public abstract class CombinedVibration implements Parcelable {
    public static final Parcelable.Creator<CombinedVibration> CREATOR = new Parcelable.Creator<CombinedVibration>() { // from class: android.os.CombinedVibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CombinedVibration createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == 1) {
                return new Mono(parcel);
            }
            if (i == 2) {
                return new Stereo(parcel);
            }
            if (i == 3) {
                return new Sequential(parcel);
            }
            throw new IllegalStateException("Unexpected combined vibration event type token in parcel.");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CombinedVibration[] newArray(int i) {
            return new CombinedVibration[i];
        }
    };
    private static final int PARCEL_TOKEN_MONO = 1;
    private static final int PARCEL_TOKEN_SEQUENTIAL = 3;
    private static final int PARCEL_TOKEN_STEREO = 2;

    public interface VibratorAdapter {
        VibrationEffect adaptToVibrator(int i, VibrationEffect vibrationEffect);

        int[] getAvailableVibratorIds();
    }

    public abstract CombinedVibration adapt(VibratorAdapter vibratorAdapter);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract long getDuration();

    public abstract long getDuration(SparseArray<VibratorInfo> sparseArray);

    public abstract boolean hasVendorEffects();

    public abstract boolean hasVibrator(int i);

    public boolean isHapticFeedbackCandidate() {
        return false;
    }

    public abstract String toDebugString();

    public abstract <ParamT> CombinedVibration transform(VibrationEffect.Transformation<ParamT> transformation, ParamT paramt);

    public abstract void validate();

    CombinedVibration() {
    }

    public static CombinedVibration createParallel(VibrationEffect vibrationEffect) {
        Mono mono = new Mono(vibrationEffect);
        mono.validate();
        return mono;
    }

    public static ParallelCombination startParallel() {
        return new ParallelCombination();
    }

    public static SequentialCombination startSequential() {
        return new SequentialCombination();
    }

    public static final class ParallelCombination {
        private final SparseArray<VibrationEffect> mEffects = new SparseArray<>();

        ParallelCombination() {
        }

        public ParallelCombination addVibrator(int i, VibrationEffect vibrationEffect) {
            this.mEffects.put(i, vibrationEffect);
            return this;
        }

        public CombinedVibration combine() {
            if (this.mEffects.size() == 0) {
                throw new IllegalStateException("Combination must have at least one element to combine.");
            }
            Stereo stereo = new Stereo(this.mEffects);
            stereo.validate();
            return stereo;
        }
    }

    public static final class SequentialCombination {
        private final ArrayList<CombinedVibration> mEffects = new ArrayList<>();
        private final ArrayList<Integer> mDelays = new ArrayList<>();

        SequentialCombination() {
        }

        public SequentialCombination addNext(int i, VibrationEffect vibrationEffect) {
            return addNext(i, vibrationEffect, 0);
        }

        public SequentialCombination addNext(int i, VibrationEffect vibrationEffect, int i2) {
            return addNext(CombinedVibration.startParallel().addVibrator(i, vibrationEffect).combine(), i2);
        }

        public SequentialCombination addNext(CombinedVibration combinedVibration) {
            return addNext(combinedVibration, 0);
        }

        public SequentialCombination addNext(CombinedVibration combinedVibration, int i) {
            if (combinedVibration instanceof Sequential) {
                Sequential sequential = (Sequential) combinedVibration;
                int size = this.mDelays.size();
                this.mEffects.addAll(sequential.getEffects());
                this.mDelays.addAll(sequential.getDelays());
                ArrayList<Integer> arrayList = this.mDelays;
                arrayList.set(size, Integer.valueOf(i + arrayList.get(size).intValue()));
                return this;
            }
            this.mEffects.add(combinedVibration);
            this.mDelays.add(Integer.valueOf(i));
            return this;
        }

        public CombinedVibration combine() {
            if (this.mEffects.size() == 0) {
                throw new IllegalStateException("Combination must have at least one element to combine.");
            }
            Sequential sequential = new Sequential(this.mEffects, this.mDelays);
            sequential.validate();
            return sequential;
        }
    }

    public static final class Mono extends CombinedVibration {
        public static final Parcelable.Creator<Mono> CREATOR = new Parcelable.Creator<Mono>() { // from class: android.os.CombinedVibration.Mono.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Mono createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new Mono(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Mono[] newArray(int i) {
                return new Mono[i];
            }
        };
        private final VibrationEffect mEffect;

        @Override // android.os.CombinedVibration, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.CombinedVibration
        public boolean hasVibrator(int i) {
            return true;
        }

        Mono(Parcel parcel) {
            this.mEffect = VibrationEffect.CREATOR.createFromParcel(parcel);
        }

        Mono(VibrationEffect vibrationEffect) {
            this.mEffect = vibrationEffect;
        }

        public VibrationEffect getEffect() {
            return this.mEffect;
        }

        @Override // android.os.CombinedVibration
        public long getDuration() {
            return this.mEffect.getDuration();
        }

        @Override // android.os.CombinedVibration
        public long getDuration(SparseArray<VibratorInfo> sparseArray) {
            if (sparseArray == null) {
                return getDuration();
            }
            long jMax = 0;
            for (int i = 0; i < sparseArray.size(); i++) {
                long duration = this.mEffect.getDuration(sparseArray.valueAt(i));
                if (duration == Long.MAX_VALUE || duration < 0) {
                    return duration;
                }
                jMax = Math.max(jMax, duration);
            }
            return jMax;
        }

        @Override // android.os.CombinedVibration
        public boolean isHapticFeedbackCandidate() {
            return this.mEffect.isHapticFeedbackCandidate();
        }

        @Override // android.os.CombinedVibration
        public void validate() {
            this.mEffect.validate();
        }

        @Override // android.os.CombinedVibration
        public <ParamT> CombinedVibration transform(VibrationEffect.Transformation<ParamT> transformation, ParamT paramt) {
            VibrationEffect vibrationEffectTransform = transformation.transform(this.mEffect, paramt);
            return this.mEffect.equals(vibrationEffectTransform) ? this : CombinedVibration.createParallel(vibrationEffectTransform);
        }

        @Override // android.os.CombinedVibration
        public CombinedVibration adapt(VibratorAdapter vibratorAdapter) {
            ParallelCombination parallelCombinationStartParallel = CombinedVibration.startParallel();
            boolean zEquals = true;
            for (int i : vibratorAdapter.getAvailableVibratorIds()) {
                VibrationEffect vibrationEffectAdaptToVibrator = vibratorAdapter.adaptToVibrator(i, this.mEffect);
                if (vibrationEffectAdaptToVibrator == null) {
                    return null;
                }
                parallelCombinationStartParallel.addVibrator(i, vibrationEffectAdaptToVibrator);
                zEquals &= this.mEffect.equals(vibrationEffectAdaptToVibrator);
            }
            return zEquals ? this : parallelCombinationStartParallel.combine();
        }

        @Override // android.os.CombinedVibration
        public boolean hasVendorEffects() {
            if (Flags.vendorVibrationEffects()) {
                return this.mEffect instanceof VibrationEffect.VendorEffect;
            }
            return false;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Mono) {
                return this.mEffect.equals(((Mono) obj).mEffect);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mEffect);
        }

        public String toString() {
            return "Mono{mEffect=" + this.mEffect + '}';
        }

        @Override // android.os.CombinedVibration
        public String toDebugString() {
            return this.mEffect.toDebugString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(1);
            this.mEffect.writeToParcel(parcel, i);
        }
    }

    public static final class Stereo extends CombinedVibration {
        public static final Parcelable.Creator<Stereo> CREATOR = new Parcelable.Creator<Stereo>() { // from class: android.os.CombinedVibration.Stereo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Stereo createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new Stereo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Stereo[] newArray(int i) {
                return new Stereo[i];
            }
        };
        private final SparseArray<VibrationEffect> mEffects;

        @Override // android.os.CombinedVibration, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        Stereo(Parcel parcel) {
            int i = parcel.readInt();
            this.mEffects = new SparseArray<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.mEffects.put(parcel.readInt(), VibrationEffect.CREATOR.createFromParcel(parcel));
            }
        }

        Stereo(SparseArray<VibrationEffect> sparseArray) {
            this.mEffects = new SparseArray<>(sparseArray.size());
            for (int i = 0; i < sparseArray.size(); i++) {
                this.mEffects.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
            }
        }

        public SparseArray<VibrationEffect> getEffects() {
            return this.mEffects;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Long lambda$getDuration$0(Integer num) {
            return Long.valueOf(this.mEffects.valueAt(num.intValue()).getDuration());
        }

        @Override // android.os.CombinedVibration
        public long getDuration() {
            return getDuration(new Function() { // from class: android.os.CombinedVibration$Stereo$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.lambda$getDuration$0((Integer) obj);
                }
            });
        }

        @Override // android.os.CombinedVibration
        public long getDuration(final SparseArray<VibratorInfo> sparseArray) {
            if (sparseArray == null) {
                return getDuration();
            }
            return getDuration(new Function() { // from class: android.os.CombinedVibration$Stereo$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.lambda$getDuration$1(sparseArray, (Integer) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Long lambda$getDuration$1(SparseArray sparseArray, Integer num) {
            return Long.valueOf(this.mEffects.valueAt(num.intValue()).getDuration((VibratorInfo) sparseArray.get(this.mEffects.keyAt(num.intValue()))));
        }

        private long getDuration(Function<Integer, Long> function) {
            long jMax = Long.MIN_VALUE;
            boolean z = false;
            for (int i = 0; i < this.mEffects.size(); i++) {
                long jLongValue = function.apply(Integer.valueOf(i)).longValue();
                if (jLongValue == Long.MAX_VALUE) {
                    return jLongValue;
                }
                jMax = Math.max(jMax, jLongValue);
                z |= jLongValue < 0;
            }
            if (z) {
                return -1L;
            }
            return jMax;
        }

        @Override // android.os.CombinedVibration
        public boolean isHapticFeedbackCandidate() {
            for (int i = 0; i < this.mEffects.size(); i++) {
                if (!this.mEffects.valueAt(i).isHapticFeedbackCandidate()) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.os.CombinedVibration
        public void validate() {
            Preconditions.checkArgument(this.mEffects.size() > 0, "There should be at least one effect set for a combined effect");
            for (int i = 0; i < this.mEffects.size(); i++) {
                this.mEffects.valueAt(i).validate();
            }
        }

        @Override // android.os.CombinedVibration
        public <ParamT> CombinedVibration transform(VibrationEffect.Transformation<ParamT> transformation, ParamT paramt) {
            ParallelCombination parallelCombinationStartParallel = CombinedVibration.startParallel();
            boolean zEquals = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                int iKeyAt = this.mEffects.keyAt(i);
                VibrationEffect vibrationEffectValueAt = this.mEffects.valueAt(i);
                VibrationEffect vibrationEffectTransform = transformation.transform(vibrationEffectValueAt, paramt);
                parallelCombinationStartParallel.addVibrator(iKeyAt, vibrationEffectTransform);
                zEquals &= vibrationEffectValueAt.equals(vibrationEffectTransform);
            }
            return zEquals ? this : parallelCombinationStartParallel.combine();
        }

        @Override // android.os.CombinedVibration
        public CombinedVibration adapt(VibratorAdapter vibratorAdapter) {
            ParallelCombination parallelCombinationStartParallel = CombinedVibration.startParallel();
            boolean zEquals = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                int iKeyAt = this.mEffects.keyAt(i);
                VibrationEffect vibrationEffectValueAt = this.mEffects.valueAt(i);
                VibrationEffect vibrationEffectAdaptToVibrator = vibratorAdapter.adaptToVibrator(iKeyAt, vibrationEffectValueAt);
                if (vibrationEffectAdaptToVibrator == null) {
                    return null;
                }
                parallelCombinationStartParallel.addVibrator(iKeyAt, vibrationEffectAdaptToVibrator);
                zEquals &= vibrationEffectValueAt.equals(vibrationEffectAdaptToVibrator);
            }
            return zEquals ? this : parallelCombinationStartParallel.combine();
        }

        @Override // android.os.CombinedVibration
        public boolean hasVibrator(int i) {
            return this.mEffects.indexOfKey(i) >= 0;
        }

        @Override // android.os.CombinedVibration
        public boolean hasVendorEffects() {
            if (!Flags.vendorVibrationEffects()) {
                return false;
            }
            for (int i = 0; i < this.mEffects.size(); i++) {
                if (this.mEffects.get(i) instanceof VibrationEffect.VendorEffect) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Stereo)) {
                return false;
            }
            Stereo stereo = (Stereo) obj;
            if (this.mEffects.size() != stereo.mEffects.size()) {
                return false;
            }
            for (int i = 0; i < this.mEffects.size(); i++) {
                if (!this.mEffects.valueAt(i).equals(stereo.mEffects.get(this.mEffects.keyAt(i)))) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return this.mEffects.contentHashCode();
        }

        public String toString() {
            return "Stereo{mEffects=" + this.mEffects + '}';
        }

        @Override // android.os.CombinedVibration
        public String toDebugString() {
            StringJoiner stringJoiner = new StringJoiner(",", "Stereo{", "}");
            for (int i = 0; i < this.mEffects.size(); i++) {
                stringJoiner.add(String.format(Locale.ROOT, "vibrator(id=%d): %s", Integer.valueOf(this.mEffects.keyAt(i)), this.mEffects.valueAt(i).toDebugString()));
            }
            return stringJoiner.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(2);
            parcel.writeInt(this.mEffects.size());
            for (int i2 = 0; i2 < this.mEffects.size(); i2++) {
                parcel.writeInt(this.mEffects.keyAt(i2));
                this.mEffects.valueAt(i2).writeToParcel(parcel, i);
            }
        }
    }

    public static final class Sequential extends CombinedVibration {
        public static final Parcelable.Creator<Sequential> CREATOR = new Parcelable.Creator<Sequential>() { // from class: android.os.CombinedVibration.Sequential.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Sequential createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new Sequential(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Sequential[] newArray(int i) {
                return new Sequential[i];
            }
        };
        private static final long MAX_HAPTIC_FEEDBACK_SEQUENCE_SIZE = 3;
        private final List<Integer> mDelays;
        private final List<CombinedVibration> mEffects;

        @Override // android.os.CombinedVibration, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        Sequential(Parcel parcel) {
            int i = parcel.readInt();
            this.mEffects = new ArrayList(i);
            this.mDelays = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.mDelays.add(Integer.valueOf(parcel.readInt()));
                this.mEffects.add(CombinedVibration.CREATOR.createFromParcel(parcel));
            }
        }

        Sequential(List<CombinedVibration> list, List<Integer> list2) {
            this.mEffects = new ArrayList(list);
            this.mDelays = new ArrayList(list2);
        }

        public List<CombinedVibration> getEffects() {
            return this.mEffects;
        }

        public List<Integer> getDelays() {
            return this.mDelays;
        }

        @Override // android.os.CombinedVibration
        public long getDuration() {
            return getDuration(new Function() { // from class: android.os.CombinedVibration$Sequential$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(((CombinedVibration) obj).getDuration());
                }
            });
        }

        @Override // android.os.CombinedVibration
        public long getDuration(final SparseArray<VibratorInfo> sparseArray) {
            return getDuration(new Function() { // from class: android.os.CombinedVibration$Sequential$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(((CombinedVibration) obj).getDuration(sparseArray));
                }
            });
        }

        private long getDuration(Function<CombinedVibration, Long> function) {
            int size = this.mEffects.size();
            long jIntValue = 0;
            boolean z = false;
            long j = 0;
            for (int i = 0; i < size; i++) {
                long jLongValue = function.apply(this.mEffects.get(i)).longValue();
                if (jLongValue == Long.MAX_VALUE) {
                    return jLongValue;
                }
                j += jLongValue;
                z |= jLongValue < 0;
            }
            if (z) {
                return -1L;
            }
            for (int i2 = 0; i2 < size; i2++) {
                jIntValue += this.mDelays.get(i2).intValue();
            }
            return j + jIntValue;
        }

        @Override // android.os.CombinedVibration
        public boolean isHapticFeedbackCandidate() {
            int size = this.mEffects.size();
            if (size > 3) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!this.mEffects.get(i).isHapticFeedbackCandidate()) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.os.CombinedVibration
        public void validate() {
            Preconditions.checkArgument(this.mEffects.size() > 0, "There should be at least one effect set for a combined effect");
            Preconditions.checkArgument(this.mEffects.size() == this.mDelays.size(), "Effect and delays should have equal length");
            int size = this.mEffects.size();
            for (int i = 0; i < size; i++) {
                if (this.mDelays.get(i).intValue() < 0) {
                    throw new IllegalArgumentException("Delays must all be >= 0 (delays=" + this.mDelays + NavigationBarInflaterView.KEY_CODE_END);
                }
            }
            for (int i2 = 0; i2 < size; i2++) {
                CombinedVibration combinedVibration = this.mEffects.get(i2);
                if (combinedVibration instanceof Sequential) {
                    throw new IllegalArgumentException("There should be no nested sequential effects in a combined effect");
                }
                combinedVibration.validate();
            }
        }

        @Override // android.os.CombinedVibration
        public <ParamT> CombinedVibration transform(VibrationEffect.Transformation<ParamT> transformation, ParamT paramt) {
            SequentialCombination sequentialCombinationStartSequential = CombinedVibration.startSequential();
            boolean zEquals = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                CombinedVibration combinedVibration = this.mEffects.get(i);
                CombinedVibration combinedVibrationTransform = combinedVibration.transform(transformation, paramt);
                sequentialCombinationStartSequential.addNext(combinedVibrationTransform, this.mDelays.get(i).intValue());
                zEquals &= combinedVibration.equals(combinedVibrationTransform);
            }
            return zEquals ? this : sequentialCombinationStartSequential.combine();
        }

        @Override // android.os.CombinedVibration
        public CombinedVibration adapt(VibratorAdapter vibratorAdapter) {
            SequentialCombination sequentialCombinationStartSequential = CombinedVibration.startSequential();
            boolean zEquals = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                CombinedVibration combinedVibration = this.mEffects.get(i);
                CombinedVibration combinedVibrationAdapt = combinedVibration.adapt(vibratorAdapter);
                sequentialCombinationStartSequential.addNext(combinedVibrationAdapt, this.mDelays.get(i).intValue());
                zEquals &= combinedVibration.equals(combinedVibrationAdapt);
            }
            return zEquals ? this : sequentialCombinationStartSequential.combine();
        }

        @Override // android.os.CombinedVibration
        public boolean hasVibrator(int i) {
            int size = this.mEffects.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (this.mEffects.get(i2).hasVibrator(i)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.os.CombinedVibration
        public boolean hasVendorEffects() {
            for (int i = 0; i < this.mEffects.size(); i++) {
                if (this.mEffects.get(i).hasVendorEffects()) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Sequential)) {
                return false;
            }
            Sequential sequential = (Sequential) obj;
            return this.mDelays.equals(sequential.mDelays) && this.mEffects.equals(sequential.mEffects);
        }

        public int hashCode() {
            return Objects.hash(this.mEffects, this.mDelays);
        }

        public String toString() {
            return "Sequential{mEffects=" + this.mEffects + ", mDelays=" + this.mDelays + '}';
        }

        @Override // android.os.CombinedVibration
        public String toDebugString() {
            StringJoiner stringJoiner = new StringJoiner(",", "Sequential{", "}");
            for (int i = 0; i < this.mEffects.size(); i++) {
                stringJoiner.add(String.format(Locale.ROOT, "delayMs=%d, effect=%s", this.mDelays.get(i), this.mEffects.get(i).toDebugString()));
            }
            return stringJoiner.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(3);
            parcel.writeInt(this.mEffects.size());
            for (int i2 = 0; i2 < this.mEffects.size(); i2++) {
                parcel.writeInt(this.mDelays.get(i2).intValue());
                this.mEffects.get(i2).writeToParcel(parcel, i);
            }
        }
    }
}
