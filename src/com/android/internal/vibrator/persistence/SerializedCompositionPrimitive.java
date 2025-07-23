package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.os.vibrator.Flags;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.internal.vibrator.persistence.XmlConstants;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;

/* loaded from: classes4.dex */
final class SerializedCompositionPrimitive implements SerializedComposedEffect.SerializedSegment {
    private final XmlConstants.PrimitiveDelayType mDelayType;
    private final int mPrimitiveDelayMs;
    private final XmlConstants.PrimitiveEffectName mPrimitiveName;
    private final float mPrimitiveScale;

    SerializedCompositionPrimitive(XmlConstants.PrimitiveEffectName primitiveEffectName, float f, int i, XmlConstants.PrimitiveDelayType primitiveDelayType) {
        this.mPrimitiveName = primitiveEffectName;
        this.mPrimitiveScale = f;
        this.mPrimitiveDelayMs = i;
        this.mDelayType = primitiveDelayType;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        if (Flags.primitiveCompositionAbsoluteDelay() && this.mDelayType != null) {
            composition.addPrimitive(this.mPrimitiveName.getPrimitiveId(), this.mPrimitiveScale, this.mPrimitiveDelayMs, this.mDelayType.getDelayType());
        } else {
            composition.addPrimitive(this.mPrimitiveName.getPrimitiveId(), this.mPrimitiveScale, this.mPrimitiveDelayMs);
        }
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        XmlConstants.PrimitiveDelayType primitiveDelayType;
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PRIMITIVE_EFFECT);
        typedXmlSerializer.attribute(XmlConstants.NAMESPACE, "name", this.mPrimitiveName.toString());
        if (Float.compare(this.mPrimitiveScale, 1.0f) != 0) {
            typedXmlSerializer.attributeFloat(XmlConstants.NAMESPACE, "scale", this.mPrimitiveScale);
        }
        if (this.mPrimitiveDelayMs != 0) {
            typedXmlSerializer.attributeInt(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DELAY_MS, this.mPrimitiveDelayMs);
        }
        if (Flags.primitiveCompositionAbsoluteDelay() && (primitiveDelayType = this.mDelayType) != null && primitiveDelayType.getDelayType() != 0) {
            typedXmlSerializer.attribute(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DELAY_TYPE, this.mDelayType.toString());
        }
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PRIMITIVE_EFFECT);
    }

    public String toString() {
        return "SerializedCompositionPrimitive{name=" + this.mPrimitiveName + ", scale=" + this.mPrimitiveScale + ", delayMs=" + this.mPrimitiveDelayMs + ", delayType=" + this.mDelayType + '}';
    }

    static final class Parser {
        Parser() {
        }

        static SerializedCompositionPrimitive parseNext(TypedXmlPullParser typedXmlPullParser) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_PRIMITIVE_EFFECT);
            if (Flags.primitiveCompositionAbsoluteDelay()) {
                XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, "name", XmlConstants.ATTRIBUTE_DELAY_MS, "scale", XmlConstants.ATTRIBUTE_DELAY_TYPE);
            } else {
                XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, "name", XmlConstants.ATTRIBUTE_DELAY_MS, "scale");
            }
            XmlConstants.PrimitiveEffectName parsePrimitiveName = parsePrimitiveName(typedXmlPullParser.getAttributeValue(XmlConstants.NAMESPACE, "name"));
            float readAttributeFloatInRange = XmlReader.readAttributeFloatInRange(typedXmlPullParser, "scale", 0.0f, 1.0f, 1.0f);
            int readAttributeIntNonNegative = XmlReader.readAttributeIntNonNegative(typedXmlPullParser, XmlConstants.ATTRIBUTE_DELAY_MS, 0);
            XmlConstants.PrimitiveDelayType parseDelayType = parseDelayType(typedXmlPullParser.getAttributeValue(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DELAY_TYPE));
            XmlReader.readEndTag(typedXmlPullParser);
            return new SerializedCompositionPrimitive(parsePrimitiveName, readAttributeFloatInRange, readAttributeIntNonNegative, parseDelayType);
        }

        private static XmlConstants.PrimitiveEffectName parsePrimitiveName(String str) throws XmlParserException {
            if (str == null) {
                throw new XmlParserException("Missing primitive effect name");
            }
            XmlConstants.PrimitiveEffectName findByName = XmlConstants.PrimitiveEffectName.findByName(str);
            if (findByName != null) {
                return findByName;
            }
            throw new XmlParserException("Unexpected primitive effect name " + str);
        }

        private static XmlConstants.PrimitiveDelayType parseDelayType(String str) throws XmlParserException {
            if (str == null) {
                return null;
            }
            if (!Flags.primitiveCompositionAbsoluteDelay()) {
                throw new XmlParserException("Unexpected primitive delay type " + str);
            }
            XmlConstants.PrimitiveDelayType findByName = XmlConstants.PrimitiveDelayType.findByName(str);
            if (findByName != null) {
                return findByName;
            }
            throw new XmlParserException("Unexpected primitive delay type " + str);
        }
    }
}
