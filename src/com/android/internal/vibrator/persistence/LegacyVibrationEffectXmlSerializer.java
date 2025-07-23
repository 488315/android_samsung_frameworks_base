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
import com.android.internal.vibrator.persistence.SerializedWaveformEnvelopeEffect;
import com.android.internal.vibrator.persistence.XmlConstants;
import java.util.List;

/* loaded from: classes4.dex */
public final class LegacyVibrationEffectXmlSerializer {
    public static XmlSerializedVibration<? extends VibrationEffect> serialize(VibrationEffect vibrationEffect, int i) throws XmlSerializerException {
        if (Flags.vendorVibrationEffects() && (vibrationEffect instanceof VibrationEffect.VendorEffect)) {
            return serializeVendorEffect((VibrationEffect.VendorEffect) vibrationEffect);
        }
        XmlValidator.checkSerializerCondition(vibrationEffect instanceof VibrationEffect.Composed, "Unsupported VibrationEffect type %s", vibrationEffect);
        VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
        XmlValidator.checkSerializerCondition(!composed.getSegments().isEmpty(), "Unsupported empty VibrationEffect %s", vibrationEffect);
        VibrationEffectSegment vibrationEffectSegment = composed.getSegments().get(0);
        if (vibrationEffectSegment instanceof PrebakedSegment) {
            return serializePredefinedEffect(composed, i);
        }
        if (vibrationEffectSegment instanceof PrimitiveSegment) {
            return serializePrimitiveEffect(composed);
        }
        if (Flags.normalizedPwleEffects() && (vibrationEffectSegment instanceof PwleSegment)) {
            return serializeWaveformEnvelopeEffect(composed);
        }
        if (Flags.normalizedPwleEffects() && (vibrationEffectSegment instanceof BasicPwleSegment)) {
            return serializeBasicEnvelopeEffect(composed);
        }
        return serializeWaveformEffect(composed);
    }

    private static SerializedComposedEffect serializePredefinedEffect(VibrationEffect.Composed composed, int i) throws XmlSerializerException {
        List<VibrationEffectSegment> segments = composed.getSegments();
        XmlValidator.checkSerializerCondition(composed.getRepeatIndex() == -1, "Unsupported repeating predefined effect %s", composed);
        XmlValidator.checkSerializerCondition(segments.size() == 1, "Unsupported multiple segments in predefined effect %s", composed);
        return new SerializedComposedEffect(serializePrebakedSegment(segments.get(0), i));
    }

    private static SerializedVendorEffect serializeVendorEffect(VibrationEffect.VendorEffect vendorEffect) {
        return new SerializedVendorEffect(vendorEffect.getVendorData());
    }

    private static SerializedComposedEffect serializePrimitiveEffect(VibrationEffect.Composed composed) throws XmlSerializerException {
        List<VibrationEffectSegment> segments = composed.getSegments();
        XmlValidator.checkSerializerCondition(composed.getRepeatIndex() == -1, "Unsupported repeating primitive composition %s", composed);
        SerializedComposedEffect.SerializedSegment[] serializedSegmentArr = new SerializedComposedEffect.SerializedSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            serializedSegmentArr[i] = serializePrimitiveSegment(segments.get(i));
        }
        return new SerializedComposedEffect(serializedSegmentArr);
    }

    private static SerializedComposedEffect serializeWaveformEnvelopeEffect(VibrationEffect.Composed composed) throws XmlSerializerException {
        SerializedWaveformEnvelopeEffect.Builder builder = new SerializedWaveformEnvelopeEffect.Builder();
        List<VibrationEffectSegment> segments = composed.getSegments();
        XmlValidator.checkSerializerCondition(composed.getRepeatIndex() == -1, "Unsupported repeating waveform envelope effect %s", composed);
        for (int i = 0; i < segments.size(); i++) {
            XmlValidator.checkSerializerCondition(segments.get(i) instanceof PwleSegment, "Unsupported segment for waveform envelope effect %s", segments.get(i));
            PwleSegment pwleSegment = (PwleSegment) segments.get(i);
            if (i == 0 && pwleSegment.getStartFrequencyHz() != pwleSegment.getEndFrequencyHz()) {
                builder.setInitialFrequencyHz(pwleSegment.getStartFrequencyHz());
            }
            builder.addControlPoint(pwleSegment.getEndAmplitude(), pwleSegment.getEndFrequencyHz(), pwleSegment.getDuration());
        }
        return new SerializedComposedEffect(builder.build());
    }

    private static SerializedComposedEffect serializeBasicEnvelopeEffect(VibrationEffect.Composed composed) throws XmlSerializerException {
        SerializedBasicEnvelopeEffect.Builder builder = new SerializedBasicEnvelopeEffect.Builder();
        List<VibrationEffectSegment> segments = composed.getSegments();
        XmlValidator.checkSerializerCondition(composed.getRepeatIndex() == -1, "Unsupported repeating basic envelope effect %s", composed);
        for (int i = 0; i < segments.size(); i++) {
            XmlValidator.checkSerializerCondition(segments.get(i) instanceof BasicPwleSegment, "Unsupported segment for basic envelope effect %s", segments.get(i));
            BasicPwleSegment basicPwleSegment = (BasicPwleSegment) segments.get(i);
            if (i == 0 && basicPwleSegment.getStartSharpness() != basicPwleSegment.getEndSharpness()) {
                builder.setInitialSharpness(basicPwleSegment.getStartSharpness());
            }
            builder.addControlPoint(basicPwleSegment.getEndIntensity(), basicPwleSegment.getEndSharpness(), basicPwleSegment.getDuration());
        }
        return new SerializedComposedEffect(builder.build());
    }

    private static SerializedComposedEffect serializeWaveformEffect(VibrationEffect.Composed composed) throws XmlSerializerException {
        SerializedAmplitudeStepWaveform.Builder builder = new SerializedAmplitudeStepWaveform.Builder();
        List<VibrationEffectSegment> segments = composed.getSegments();
        for (int i = 0; i < segments.size(); i++) {
            XmlValidator.checkSerializerCondition(segments.get(i) instanceof StepSegment, "Unsupported segment for waveform effect %s", segments.get(i));
            StepSegment stepSegment = (StepSegment) segments.get(i);
            if (composed.getRepeatIndex() == i) {
                builder.setRepeatIndexToCurrentEntry();
            }
            XmlValidator.checkSerializerCondition(Float.compare(stepSegment.getFrequencyHz(), 0.0f) == 0, "Unsupported segment with non-default frequency %f", Float.valueOf(stepSegment.getFrequencyHz()));
            builder.addDurationAndAmplitude(stepSegment.getDuration(), toAmplitudeInt(stepSegment.getAmplitude()));
        }
        return new SerializedComposedEffect(builder.build());
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
