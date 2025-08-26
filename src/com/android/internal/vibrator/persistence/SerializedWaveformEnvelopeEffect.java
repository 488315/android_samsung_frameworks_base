package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
final class SerializedWaveformEnvelopeEffect implements SerializedComposedEffect.SerializedSegment {
    private final WaveformControlPoint[] mControlPoints;
    private final float mInitialFrequency;

    SerializedWaveformEnvelopeEffect(WaveformControlPoint[] waveformControlPointArr, float f) {
        this.mControlPoints = waveformControlPointArr;
        this.mInitialFrequency = f;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENVELOPE_EFFECT);
        if (!Float.isNaN(this.mInitialFrequency)) {
            typedXmlSerializer.attributeFloat(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_INITIAL_FREQUENCY_HZ, this.mInitialFrequency);
        }
        for (WaveformControlPoint waveformControlPoint : this.mControlPoints) {
            typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_CONTROL_POINT);
            typedXmlSerializer.attributeFloat(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE, waveformControlPoint.mAmplitude);
            typedXmlSerializer.attributeFloat(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_FREQUENCY_HZ, waveformControlPoint.mFrequency);
            typedXmlSerializer.attributeLong(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DURATION_MS, waveformControlPoint.mDurationMs);
            typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_CONTROL_POINT);
        }
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENVELOPE_EFFECT);
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        VibrationEffect.WaveformEnvelopeBuilder waveformEnvelopeBuilder = new VibrationEffect.WaveformEnvelopeBuilder();
        if (!Float.isNaN(this.mInitialFrequency)) {
            waveformEnvelopeBuilder.setInitialFrequencyHz(this.mInitialFrequency);
        }
        for (WaveformControlPoint waveformControlPoint : this.mControlPoints) {
            waveformEnvelopeBuilder.addControlPoint(waveformControlPoint.mAmplitude, waveformControlPoint.mFrequency, waveformControlPoint.mDurationMs);
        }
        composition.addEffect(waveformEnvelopeBuilder.build());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SerializedWaveformEnvelopeEffect{InitialFrequency=");
        sb.append(Float.isNaN(this.mInitialFrequency) ? "" : Float.valueOf(this.mInitialFrequency));
        sb.append(", controlPoints=");
        sb.append(Arrays.toString(this.mControlPoints));
        sb.append('}');
        return sb.toString();
    }

    static final class Builder {
        private float mInitialFrequencyHz = Float.NaN;
        private final List<WaveformControlPoint> mControlPoints = new ArrayList();

        Builder() {
        }

        void setInitialFrequencyHz(float f) {
            this.mInitialFrequencyHz = f;
        }

        void addControlPoint(float f, float f2, long j) {
            this.mControlPoints.add(new WaveformControlPoint(f, f2, j));
        }

        SerializedWaveformEnvelopeEffect build() {
            return new SerializedWaveformEnvelopeEffect((WaveformControlPoint[]) this.mControlPoints.toArray(new WaveformControlPoint[0]), this.mInitialFrequencyHz);
        }
    }

    static final class Parser {
        Parser() {
        }

        static SerializedWaveformEnvelopeEffect parseNext(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_WAVEFORM_ENVELOPE_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, XmlConstants.ATTRIBUTE_INITIAL_FREQUENCY_HZ);
            Builder builder = new Builder();
            builder.setInitialFrequencyHz(XmlReader.readAttributePositiveFloat(typedXmlPullParser, XmlConstants.ATTRIBUTE_INITIAL_FREQUENCY_HZ, Float.NaN));
            int depth = typedXmlPullParser.getDepth();
            while (XmlReader.readNextTagWithin(typedXmlPullParser, depth)) {
                parseControlPoint(typedXmlPullParser, builder);
                XmlReader.readEndTag(typedXmlPullParser);
            }
            XmlValidator.checkParserCondition(!builder.mControlPoints.isEmpty(), "Expected tag %s to have at least one control point", XmlConstants.TAG_WAVEFORM_ENVELOPE_EFFECT);
            return builder.build();
        }

        private static void parseControlPoint(TypedXmlPullParser typedXmlPullParser, Builder builder) throws XmlParserException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_CONTROL_POINT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, XmlConstants.ATTRIBUTE_DURATION_MS, XmlConstants.ATTRIBUTE_AMPLITUDE, XmlConstants.ATTRIBUTE_FREQUENCY_HZ);
            builder.addControlPoint(XmlReader.readAttributeFloatInRange(typedXmlPullParser, XmlConstants.ATTRIBUTE_AMPLITUDE, 0.0f, 1.0f), XmlReader.readAttributePositiveFloat(typedXmlPullParser, XmlConstants.ATTRIBUTE_FREQUENCY_HZ), XmlReader.readAttributePositiveLong(typedXmlPullParser, XmlConstants.ATTRIBUTE_DURATION_MS));
        }
    }

    private static final class WaveformControlPoint {
        private final float mAmplitude;
        private final long mDurationMs;
        private final float mFrequency;

        WaveformControlPoint(float f, float f2, long j) {
            this.mAmplitude = f;
            this.mFrequency = f2;
            this.mDurationMs = j;
        }

        public String toString() {
            return String.format(Locale.ROOT, "(%.2f, %.2f, %dms)", Float.valueOf(this.mAmplitude), Float.valueOf(this.mFrequency), Long.valueOf(this.mDurationMs));
        }
    }
}
