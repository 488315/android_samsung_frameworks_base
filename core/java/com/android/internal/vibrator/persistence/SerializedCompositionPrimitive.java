package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import com.android.internal.vibrator.persistence.SerializedVibrationEffect;
import com.android.internal.vibrator.persistence.XmlConstants;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;

/* loaded from: classes5.dex */
final class SerializedCompositionPrimitive implements SerializedVibrationEffect.SerializedSegment {
    private final int mPrimitiveDelayMs;
    private final XmlConstants.PrimitiveEffectName mPrimitiveName;
    private final float mPrimitiveScale;

    SerializedCompositionPrimitive(XmlConstants.PrimitiveEffectName primitiveName, float scale, int delayMs) {
        this.mPrimitiveName = primitiveName;
        this.mPrimitiveScale = scale;
        this.mPrimitiveDelayMs = delayMs;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        composition.addPrimitive(this.mPrimitiveName.getPrimitiveId(), this.mPrimitiveScale, this.mPrimitiveDelayMs);
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void write(TypedXmlSerializer serializer) throws IOException {
        serializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PRIMITIVE_EFFECT);
        serializer.attribute(XmlConstants.NAMESPACE, "name", this.mPrimitiveName.toString());
        if (Float.compare(this.mPrimitiveScale, 1.0f) != 0) {
            serializer.attributeFloat(XmlConstants.NAMESPACE, "scale", this.mPrimitiveScale);
        }
        if (this.mPrimitiveDelayMs != 0) {
            serializer.attributeInt(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DELAY_MS, this.mPrimitiveDelayMs);
        }
        serializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PRIMITIVE_EFFECT);
    }

    public String toString() {
        return "SerializedCompositionPrimitive{name=" + this.mPrimitiveName + ", scale=" + this.mPrimitiveScale + ", delayMs=" + this.mPrimitiveDelayMs + '}';
    }

    static final class Parser {
        Parser() {
        }

        static SerializedCompositionPrimitive parseNext(TypedXmlPullParser parser) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(parser, XmlConstants.TAG_PRIMITIVE_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(parser, "name", XmlConstants.ATTRIBUTE_DELAY_MS, "scale");
            XmlConstants.PrimitiveEffectName primitiveName = parsePrimitiveName(parser.getAttributeValue(XmlConstants.NAMESPACE, "name"));
            float scale = XmlReader.readAttributeFloatInRange(parser, "scale", 0.0f, 1.0f, 1.0f);
            int delayMs = XmlReader.readAttributeIntNonNegative(parser, XmlConstants.ATTRIBUTE_DELAY_MS, 0);
            XmlReader.readEndTag(parser);
            return new SerializedCompositionPrimitive(primitiveName, scale, delayMs);
        }

        private static XmlConstants.PrimitiveEffectName parsePrimitiveName(String name) throws XmlParserException {
            if (name == null) {
                throw new XmlParserException("Missing primitive effect name");
            }
            XmlConstants.PrimitiveEffectName effectName = XmlConstants.PrimitiveEffectName.findByName(name);
            if (effectName == null) {
                throw new XmlParserException("Unexpected primitive effect name " + name);
            }
            return effectName;
        }
    }
}
