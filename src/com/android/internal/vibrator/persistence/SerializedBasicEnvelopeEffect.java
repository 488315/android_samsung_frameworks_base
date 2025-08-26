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
final class SerializedBasicEnvelopeEffect implements SerializedComposedEffect.SerializedSegment {
    private final BasicControlPoint[] mControlPoints;
    private final float mInitialSharpness;

    SerializedBasicEnvelopeEffect(BasicControlPoint[] basicControlPointArr, float f) {
        this.mControlPoints = basicControlPointArr;
        this.mInitialSharpness = f;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_BASIC_ENVELOPE_EFFECT);
        if (!Float.isNaN(this.mInitialSharpness)) {
            typedXmlSerializer.attributeFloat(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_INITIAL_SHARPNESS, this.mInitialSharpness);
        }
        for (BasicControlPoint basicControlPoint : this.mControlPoints) {
            typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_CONTROL_POINT);
            typedXmlSerializer.attributeFloat(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_INTENSITY, basicControlPoint.mIntensity);
            typedXmlSerializer.attributeFloat(XmlConstants.NAMESPACE, "sharpness", basicControlPoint.mSharpness);
            typedXmlSerializer.attributeLong(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DURATION_MS, basicControlPoint.mDurationMs);
            typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_CONTROL_POINT);
        }
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_BASIC_ENVELOPE_EFFECT);
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        VibrationEffect.BasicEnvelopeBuilder basicEnvelopeBuilder = new VibrationEffect.BasicEnvelopeBuilder();
        if (!Float.isNaN(this.mInitialSharpness)) {
            basicEnvelopeBuilder.setInitialSharpness(this.mInitialSharpness);
        }
        for (BasicControlPoint basicControlPoint : this.mControlPoints) {
            basicEnvelopeBuilder.addControlPoint(basicControlPoint.mIntensity, basicControlPoint.mSharpness, basicControlPoint.mDurationMs);
        }
        composition.addEffect(basicEnvelopeBuilder.build());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SerializedBasicEnvelopeEffect{initialSharpness=");
        sb.append(Float.isNaN(this.mInitialSharpness) ? "" : Float.valueOf(this.mInitialSharpness));
        sb.append(", controlPoints=");
        sb.append(Arrays.toString(this.mControlPoints));
        sb.append('}');
        return sb.toString();
    }

    static final class Builder {
        private float mInitialSharpness = Float.NaN;
        private final List<BasicControlPoint> mControlPoints = new ArrayList();

        Builder() {
        }

        void setInitialSharpness(float f) {
            this.mInitialSharpness = f;
        }

        void addControlPoint(float f, float f2, long j) {
            this.mControlPoints.add(new BasicControlPoint(f, f2, j));
        }

        SerializedBasicEnvelopeEffect build() {
            return new SerializedBasicEnvelopeEffect((BasicControlPoint[]) this.mControlPoints.toArray(new BasicControlPoint[0]), this.mInitialSharpness);
        }
    }

    static final class Parser {
        Parser() {
        }

        static SerializedBasicEnvelopeEffect parseNext(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_BASIC_ENVELOPE_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, XmlConstants.ATTRIBUTE_INITIAL_SHARPNESS);
            Builder builder = new Builder();
            builder.setInitialSharpness(XmlReader.readAttributeFloatInRange(typedXmlPullParser, XmlConstants.ATTRIBUTE_INITIAL_SHARPNESS, 0.0f, 1.0f, Float.NaN));
            int depth = typedXmlPullParser.getDepth();
            while (XmlReader.readNextTagWithin(typedXmlPullParser, depth)) {
                parseControlPoint(typedXmlPullParser, builder);
                XmlReader.readEndTag(typedXmlPullParser);
            }
            XmlValidator.checkParserCondition(!builder.mControlPoints.isEmpty(), "Expected tag %s to have at least one control point", XmlConstants.TAG_BASIC_ENVELOPE_EFFECT);
            XmlValidator.checkParserCondition(((BasicControlPoint) builder.mControlPoints.getLast()).mIntensity == 0.0f, "Basic envelope effects must end at a zero intensity control point", new Object[0]);
            return builder.build();
        }

        private static void parseControlPoint(TypedXmlPullParser typedXmlPullParser, Builder builder) throws XmlParserException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_CONTROL_POINT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, XmlConstants.ATTRIBUTE_DURATION_MS, XmlConstants.ATTRIBUTE_INTENSITY, "sharpness");
            builder.addControlPoint(XmlReader.readAttributeFloatInRange(typedXmlPullParser, XmlConstants.ATTRIBUTE_INTENSITY, 0.0f, 1.0f), XmlReader.readAttributeFloatInRange(typedXmlPullParser, "sharpness", 0.0f, 1.0f), XmlReader.readAttributePositiveLong(typedXmlPullParser, XmlConstants.ATTRIBUTE_DURATION_MS));
        }
    }

    private static final class BasicControlPoint {
        private final long mDurationMs;
        private final float mIntensity;
        private final float mSharpness;

        BasicControlPoint(float f, float f2, long j) {
            this.mIntensity = f;
            this.mSharpness = f2;
            this.mDurationMs = j;
        }

        public String toString() {
            return String.format(Locale.ROOT, "(%.2f, %.2f, %dms)", Float.valueOf(this.mIntensity), Float.valueOf(this.mSharpness), Long.valueOf(this.mDurationMs));
        }
    }
}
