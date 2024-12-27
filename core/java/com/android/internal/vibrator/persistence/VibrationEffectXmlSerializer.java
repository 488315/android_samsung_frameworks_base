package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform;
import com.android.internal.vibrator.persistence.SerializedVibrationEffect;
import com.android.internal.vibrator.persistence.XmlConstants;
import java.util.List;

/* loaded from: classes5.dex */
public final class VibrationEffectXmlSerializer {
    public static XmlSerializedVibration<VibrationEffect> serialize(VibrationEffect vibration, int flags) throws XmlSerializerException {
        XmlValidator.checkSerializerCondition(vibration instanceof VibrationEffect.Composed, "Unsupported VibrationEffect type %s", vibration);
        VibrationEffect.Composed composed = (VibrationEffect.Composed) vibration;
        XmlValidator.checkSerializerCondition(!composed.getSegments().isEmpty(), "Unsupported empty VibrationEffect %s", vibration);
        VibrationEffectSegment firstSegment = composed.getSegments().get(0);
        if (firstSegment instanceof PrebakedSegment) {
            return serializePredefinedEffect(composed, flags);
        }
        if (firstSegment instanceof PrimitiveSegment) {
            return serializePrimitiveEffect(composed);
        }
        return serializeWaveformEffect(composed);
    }

    private static SerializedVibrationEffect serializePredefinedEffect(VibrationEffect.Composed effect, int flags) throws XmlSerializerException {
        List<VibrationEffectSegment> segments = effect.getSegments();
        XmlValidator.checkSerializerCondition(effect.getRepeatIndex() == -1, "Unsupported repeating predefined effect %s", effect);
        XmlValidator.checkSerializerCondition(segments.size() == 1, "Unsupported multiple segments in predefined effect %s", effect);
        return new SerializedVibrationEffect(serializePrebakedSegment(segments.get(0), flags));
    }

    private static SerializedVibrationEffect serializePrimitiveEffect(VibrationEffect.Composed effect) throws XmlSerializerException {
        List<VibrationEffectSegment> segments = effect.getSegments();
        XmlValidator.checkSerializerCondition(effect.getRepeatIndex() == -1, "Unsupported repeating primitive composition %s", effect);
        SerializedVibrationEffect.SerializedSegment[] primitives = new SerializedVibrationEffect.SerializedSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            primitives[i] = serializePrimitiveSegment(segments.get(i));
        }
        return new SerializedVibrationEffect(primitives);
    }

    private static SerializedVibrationEffect serializeWaveformEffect(VibrationEffect.Composed effect) throws XmlSerializerException {
        SerializedAmplitudeStepWaveform.Builder serializedWaveformBuilder = new SerializedAmplitudeStepWaveform.Builder();
        List<VibrationEffectSegment> segments = effect.getSegments();
        for (int i = 0; i < segments.size(); i++) {
            XmlValidator.checkSerializerCondition(segments.get(i) instanceof StepSegment, "Unsupported segment for waveform effect %s", segments.get(i));
            StepSegment segment = (StepSegment) segments.get(i);
            if (effect.getRepeatIndex() == i) {
                serializedWaveformBuilder.setRepeatIndexToCurrentEntry();
            }
            XmlValidator.checkSerializerCondition(Float.compare(segment.getFrequencyHz(), 0.0f) == 0, "Unsupported segment with non-default frequency %f", Float.valueOf(segment.getFrequencyHz()));
            serializedWaveformBuilder.addDurationAndAmplitude(segment.getDuration(), toAmplitudeInt(segment.getAmplitude()));
        }
        return new SerializedVibrationEffect(serializedWaveformBuilder.build());
    }

    private static SerializedPredefinedEffect serializePrebakedSegment(VibrationEffectSegment segment, int flags) throws XmlSerializerException {
        XmlValidator.checkSerializerCondition(segment instanceof PrebakedSegment, "Unsupported segment for predefined effect %s", segment);
        PrebakedSegment prebaked = (PrebakedSegment) segment;
        XmlConstants.PredefinedEffectName effectName = XmlConstants.PredefinedEffectName.findById(prebaked.getEffectId(), flags);
        XmlValidator.checkSerializerCondition(effectName != null, "Unsupported predefined effect id %s", Integer.valueOf(prebaked.getEffectId()));
        if ((flags & 1) == 0) {
            XmlValidator.checkSerializerCondition(prebaked.shouldFallback(), "Unsupported predefined effect with should fallback %s", Boolean.valueOf(prebaked.shouldFallback()));
        }
        return new SerializedPredefinedEffect(effectName, prebaked.shouldFallback());
    }

    private static SerializedCompositionPrimitive serializePrimitiveSegment(VibrationEffectSegment segment) throws XmlSerializerException {
        XmlValidator.checkSerializerCondition(segment instanceof PrimitiveSegment, "Unsupported segment for primitive composition %s", segment);
        PrimitiveSegment primitive = (PrimitiveSegment) segment;
        XmlConstants.PrimitiveEffectName primitiveName = XmlConstants.PrimitiveEffectName.findById(primitive.getPrimitiveId());
        XmlValidator.checkSerializerCondition(primitiveName != null, "Unsupported primitive effect id %s", Integer.valueOf(primitive.getPrimitiveId()));
        return new SerializedCompositionPrimitive(primitiveName, primitive.getScale(), primitive.getDelay());
    }

    private static int toAmplitudeInt(float amplitude) {
        if (Float.compare(amplitude, -1.0f) == 0) {
            return -1;
        }
        return Math.round(255.0f * amplitude);
    }
}
