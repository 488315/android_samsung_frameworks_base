package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.internal.vibrator.persistence.XmlConstants;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;

/* loaded from: classes4.dex */
final class SerializedPredefinedEffect implements SerializedComposedEffect.SerializedSegment {
    private final XmlConstants.PredefinedEffectName mEffectName;
    private final boolean mShouldFallback;

    SerializedPredefinedEffect(XmlConstants.PredefinedEffectName predefinedEffectName, boolean z) {
        this.mEffectName = predefinedEffectName;
        this.mShouldFallback = z;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        composition.addEffect(VibrationEffect.get(this.mEffectName.getEffectId(), this.mShouldFallback));
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PREDEFINED_EFFECT);
        typedXmlSerializer.attribute(XmlConstants.NAMESPACE, "name", this.mEffectName.toString());
        if (!this.mShouldFallback) {
            typedXmlSerializer.attributeBoolean(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_FALLBACK, this.mShouldFallback);
        }
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PREDEFINED_EFFECT);
    }

    public String toString() {
        return "SerializedPredefinedEffect{name=" + this.mEffectName + ", fallback=" + this.mShouldFallback + '}';
    }

    static final class Parser {
        Parser() {
        }

        static SerializedPredefinedEffect parseNext(TypedXmlPullParser typedXmlPullParser, int i) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_PREDEFINED_EFFECT);
            boolean z = (i & 1) != 0;
            if (z) {
                XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, "name", XmlConstants.ATTRIBUTE_FALLBACK);
            } else {
                XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, "name");
            }
            String attributeValue = typedXmlPullParser.getAttributeValue(XmlConstants.NAMESPACE, "name");
            if (attributeValue == null) {
                throw new XmlParserException("Missing predefined effect name");
            }
            XmlConstants.PredefinedEffectName findByName = XmlConstants.PredefinedEffectName.findByName(attributeValue, i);
            if (findByName != null) {
                boolean attributeBoolean = z ? typedXmlPullParser.getAttributeBoolean(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_FALLBACK, true) : true;
                XmlReader.readEndTag(typedXmlPullParser);
                return new SerializedPredefinedEffect(findByName, attributeBoolean);
            }
            throw new XmlParserException("Unexpected predefined effect name " + attributeValue);
        }
    }
}
