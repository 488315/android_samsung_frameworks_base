package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.os.vibrator.BasicPwleSegment;
import android.os.vibrator.Flags;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.PwleSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform;
import com.android.internal.vibrator.persistence.SerializedBasicEnvelopeEffect;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.internal.vibrator.persistence.SerializedRepeatingEffect;
import com.android.internal.vibrator.persistence.SerializedWaveformEffectEntries;
import com.android.internal.vibrator.persistence.SerializedWaveformEnvelopeEffect;
import com.android.internal.vibrator.persistence.XmlConstants;
import java.util.List;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public class VibrationEffectSerializer {
    private static final String TAG = "VibrationEffectSerializer";

    public static XmlSerializedVibration<? extends VibrationEffect> serialize(VibrationEffect vibrationEffect, int i) throws XmlSerializerException {
        if (Flags.vendorVibrationEffects() && (vibrationEffect instanceof VibrationEffect.VendorEffect)) {
            return serializeVendorEffect((VibrationEffect.VendorEffect) vibrationEffect);
        }
        XmlValidator.checkSerializerCondition(vibrationEffect instanceof VibrationEffect.Composed, "Unsupported VibrationEffect type %s", vibrationEffect);
        VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
        XmlValidator.checkSerializerCondition(!composed.getSegments().isEmpty(), "Unsupported empty VibrationEffect %s", vibrationEffect);
        List<VibrationEffectSegment> segments = composed.getSegments();
        int repeatIndex = composed.getRepeatIndex();
        if (repeatIndex >= 0) {
            SerializedComposedEffect trySerializeRepeatingAmplitudeWaveformEffect = trySerializeRepeatingAmplitudeWaveformEffect(segments, repeatIndex);
            return trySerializeRepeatingAmplitudeWaveformEffect == null ? serializeRepeatingEffect(segments, repeatIndex, i) : trySerializeRepeatingAmplitudeWaveformEffect;
        }
        return serializeNonRepeatingEffect(segments, i);
    }

    private static SerializedComposedEffect serializeRepeatingEffect(List<VibrationEffectSegment> list, int i, int i2) throws XmlSerializerException {
        SerializedRepeatingEffect.Builder builder = new SerializedRepeatingEffect.Builder();
        if (i > 0) {
            builder.setPreamble(serializeEffectEntries(list.subList(0, i), i2));
            list = list.subList(i, list.size());
        }
        builder.setRepeating(serializeEffectEntries(list, i2));
        return new SerializedComposedEffect(builder.build());
    }

    private static SerializedComposedEffect serializeNonRepeatingEffect(List<VibrationEffectSegment> list, int i) throws XmlSerializerException {
        SerializedComposedEffect trySerializeNonWaveformEffect = trySerializeNonWaveformEffect(list, i);
        return trySerializeNonWaveformEffect == null ? serializeWaveformEffect(list) : trySerializeNonWaveformEffect;
    }

    private static SerializedComposedEffect serializeEffectEntries(List<VibrationEffectSegment> list, int i) throws XmlSerializerException {
        SerializedComposedEffect trySerializeNonWaveformEffect = trySerializeNonWaveformEffect(list, i);
        return trySerializeNonWaveformEffect == null ? serializeWaveformEffectEntries(list) : trySerializeNonWaveformEffect;
    }

    private static SerializedComposedEffect trySerializeNonWaveformEffect(List<VibrationEffectSegment> list, int i) throws XmlSerializerException {
        VibrationEffectSegment vibrationEffectSegment = (VibrationEffectSegment) list.getFirst();
        if (vibrationEffectSegment instanceof PrebakedSegment) {
            return serializePredefinedEffect(list, i);
        }
        if (vibrationEffectSegment instanceof PrimitiveSegment) {
            return serializePrimitiveEffect(list);
        }
        if (vibrationEffectSegment instanceof PwleSegment) {
            return serializeWaveformEnvelopeEffect(list);
        }
        if (vibrationEffectSegment instanceof BasicPwleSegment) {
            return serializeBasicEnvelopeEffect(list);
        }
        return null;
    }

    private static SerializedComposedEffect serializePredefinedEffect(List<VibrationEffectSegment> list, int i) throws XmlSerializerException {
        XmlValidator.checkSerializerCondition(list.size() == 1, "Unsupported multiple segments in predefined effect: %s", list);
        return new SerializedComposedEffect(serializePrebakedSegment((VibrationEffectSegment) list.getFirst(), i));
    }

    private static SerializedVendorEffect serializeVendorEffect(VibrationEffect.VendorEffect vendorEffect) {
        return new SerializedVendorEffect(vendorEffect.getVendorData());
    }

    private static SerializedComposedEffect serializePrimitiveEffect(List<VibrationEffectSegment> list) throws XmlSerializerException {
        SerializedComposedEffect.SerializedSegment[] serializedSegmentArr = new SerializedComposedEffect.SerializedSegment[list.size()];
        for (int i = 0; i < list.size(); i++) {
            serializedSegmentArr[i] = serializePrimitiveSegment(list.get(i));
        }
        return new SerializedComposedEffect(serializedSegmentArr);
    }

    private static SerializedComposedEffect serializeWaveformEnvelopeEffect(List<VibrationEffectSegment> list) throws XmlSerializerException {
        SerializedWaveformEnvelopeEffect.Builder builder = new SerializedWaveformEnvelopeEffect.Builder();
        for (int i = 0; i < list.size(); i++) {
            XmlValidator.checkSerializerCondition(list.get(i) instanceof PwleSegment, "Unsupported segment for waveform envelope effect %s", list.get(i));
            PwleSegment pwleSegment = (PwleSegment) list.get(i);
            if (i == 0 && pwleSegment.getStartFrequencyHz() != pwleSegment.getEndFrequencyHz()) {
                builder.setInitialFrequencyHz(pwleSegment.getStartFrequencyHz());
            }
            builder.addControlPoint(pwleSegment.getEndAmplitude(), pwleSegment.getEndFrequencyHz(), pwleSegment.getDuration());
        }
        return new SerializedComposedEffect(builder.build());
    }

    private static SerializedComposedEffect serializeBasicEnvelopeEffect(List<VibrationEffectSegment> list) throws XmlSerializerException {
        SerializedBasicEnvelopeEffect.Builder builder = new SerializedBasicEnvelopeEffect.Builder();
        for (int i = 0; i < list.size(); i++) {
            XmlValidator.checkSerializerCondition(list.get(i) instanceof BasicPwleSegment, "Unsupported segment for basic envelope effect %s", list.get(i));
            BasicPwleSegment basicPwleSegment = (BasicPwleSegment) list.get(i);
            if (i == 0 && basicPwleSegment.getStartSharpness() != basicPwleSegment.getEndSharpness()) {
                builder.setInitialSharpness(basicPwleSegment.getStartSharpness());
            }
            builder.addControlPoint(basicPwleSegment.getEndIntensity(), basicPwleSegment.getEndSharpness(), basicPwleSegment.getDuration());
        }
        return new SerializedComposedEffect(builder.build());
    }

    private static SerializedComposedEffect trySerializeRepeatingAmplitudeWaveformEffect(List<VibrationEffectSegment> list, int i) {
        SerializedAmplitudeStepWaveform.Builder builder = new SerializedAmplitudeStepWaveform.Builder();
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (i == i2) {
                builder.setRepeatIndexToCurrentEntry();
            }
            try {
                serializeStepSegment(list.get(i2), new VibrationEffectSerializer$$ExternalSyntheticLambda0(builder));
            } catch (XmlSerializerException unused) {
                return null;
            }
        }
        return new SerializedComposedEffect(builder.build());
    }

    private static SerializedComposedEffect serializeWaveformEffect(List<VibrationEffectSegment> list) throws XmlSerializerException {
        SerializedAmplitudeStepWaveform.Builder builder = new SerializedAmplitudeStepWaveform.Builder();
        for (int i = 0; i < list.size(); i++) {
            serializeStepSegment(list.get(i), new VibrationEffectSerializer$$ExternalSyntheticLambda0(builder));
        }
        return new SerializedComposedEffect(builder.build());
    }

    private static SerializedComposedEffect serializeWaveformEffectEntries(List<VibrationEffectSegment> list) throws XmlSerializerException {
        final SerializedWaveformEffectEntries.Builder builder = new SerializedWaveformEffectEntries.Builder();
        for (int i = 0; i < list.size(); i++) {
            serializeStepSegment(list.get(i), new BiConsumer() { // from class: com.android.internal.vibrator.persistence.VibrationEffectSerializer$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SerializedWaveformEffectEntries.Builder.this.addDurationAndAmplitude(((Long) obj).longValue(), ((Integer) obj2).intValue());
                }
            });
        }
        return new SerializedComposedEffect(builder.build());
    }

    private static void serializeStepSegment(VibrationEffectSegment vibrationEffectSegment, BiConsumer<Long, Integer> biConsumer) throws XmlSerializerException {
        XmlValidator.checkSerializerCondition(vibrationEffectSegment instanceof StepSegment, "Unsupported segment for waveform effect %s", vibrationEffectSegment);
        StepSegment stepSegment = (StepSegment) vibrationEffectSegment;
        XmlValidator.checkSerializerCondition(Float.compare(stepSegment.getFrequencyHz(), 0.0f) == 0, "Unsupported segment with non-default frequency %f", Float.valueOf(stepSegment.getFrequencyHz()));
        biConsumer.accept(Long.valueOf(vibrationEffectSegment.getDuration()), Integer.valueOf(toAmplitudeInt(stepSegment.getAmplitude())));
    }

    private static SerializedPredefinedEffect serializePrebakedSegment(VibrationEffectSegment vibrationEffectSegment, int i) throws XmlSerializerException {
        XmlValidator.checkSerializerCondition(vibrationEffectSegment instanceof PrebakedSegment, "Unsupported segment for predefined effect %s", vibrationEffectSegment);
        PrebakedSegment prebakedSegment = (PrebakedSegment) vibrationEffectSegment;
        XmlConstants.PredefinedEffectName findById = XmlConstants.PredefinedEffectName.findById(prebakedSegment.getEffectId(), i);
        XmlValidator.checkSerializerCondition(findById != null, "Unsupported predefined effect id %s", Integer.valueOf(prebakedSegment.getEffectId()));
        if ((i & 1) == 0) {
            XmlValidator.checkSerializerCondition(prebakedSegment.shouldFallback(), "Unsupported predefined effect with should fallback %s", Boolean.valueOf(prebakedSegment.shouldFallback()));
        }
        return new SerializedPredefinedEffect(findById, prebakedSegment.shouldFallback());
    }

    private static SerializedCompositionPrimitive serializePrimitiveSegment(VibrationEffectSegment vibrationEffectSegment) throws XmlSerializerException {
        XmlConstants.PrimitiveDelayType primitiveDelayType;
        XmlValidator.checkSerializerCondition(vibrationEffectSegment instanceof PrimitiveSegment, "Unsupported segment for primitive composition %s", vibrationEffectSegment);
        PrimitiveSegment primitiveSegment = (PrimitiveSegment) vibrationEffectSegment;
        XmlConstants.PrimitiveEffectName findById = XmlConstants.PrimitiveEffectName.findById(primitiveSegment.getPrimitiveId());
        XmlValidator.checkSerializerCondition(findById != null, "Unsupported primitive effect id %s", Integer.valueOf(primitiveSegment.getPrimitiveId()));
        if (Flags.primitiveCompositionAbsoluteDelay()) {
            primitiveDelayType = XmlConstants.PrimitiveDelayType.findByType(primitiveSegment.getDelayType());
            XmlValidator.checkSerializerCondition(primitiveDelayType != null, "Unsupported primitive delay type %s", Integer.valueOf(primitiveSegment.getDelayType()));
        } else {
            XmlValidator.checkSerializerCondition(primitiveSegment.getDelayType() == 0, "Unsupported primitive delay type %s", Integer.valueOf(primitiveSegment.getDelayType()));
            primitiveDelayType = null;
        }
        return new SerializedCompositionPrimitive(findById, primitiveSegment.getScale(), primitiveSegment.getDelay(), primitiveDelayType);
    }

    private static int toAmplitudeInt(float f) {
        if (Float.compare(f, -1.0f) == 0) {
            return -1;
        }
        return Math.round(f * 255.0f);
    }
}
