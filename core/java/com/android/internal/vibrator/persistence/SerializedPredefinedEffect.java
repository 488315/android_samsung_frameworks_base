package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import java.io.IOException;

/* loaded from: classes5.dex */
final class SerializedPredefinedEffect implements SerializedVibrationEffect.SerializedSegment {
    private final XmlConstants.PredefinedEffectName mEffectName;
    private final boolean mShouldFallback;

    SerializedPredefinedEffect(
            XmlConstants.PredefinedEffectName effectName, boolean shouldFallback) {
        this.mEffectName = effectName;
        this.mShouldFallback = shouldFallback;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        composition.addEffect(
                VibrationEffect.get(this.mEffectName.getEffectId(), this.mShouldFallback));
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void write(TypedXmlSerializer serializer) throws IOException {
        serializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PREDEFINED_EFFECT);
        serializer.attribute(XmlConstants.NAMESPACE, "name", this.mEffectName.toString());
        if (!this.mShouldFallback) {
            serializer.attributeBoolean(
                    XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_FALLBACK, this.mShouldFallback);
        }
        serializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PREDEFINED_EFFECT);
    }

    public String toString() {
        return "SerializedPredefinedEffect{name="
                + this.mEffectName
                + ", fallback="
                + this.mShouldFallback
                + '}';
    }

    static final class Parser {
        Parser() {}

        static SerializedPredefinedEffect parseNext(TypedXmlPullParser parser, int flags)
                throws XmlParserException, IOException {
            boolean fallback;
            XmlValidator.checkStartTag(parser, XmlConstants.TAG_PREDEFINED_EFFECT);
            boolean allowHidden = (flags & 1) != 0;
            if (allowHidden) {
                XmlValidator.checkTagHasNoUnexpectedAttributes(
                        parser, "name", XmlConstants.ATTRIBUTE_FALLBACK);
            } else {
                XmlValidator.checkTagHasNoUnexpectedAttributes(parser, "name");
            }
            String nameAttr = parser.getAttributeValue(XmlConstants.NAMESPACE, "name");
            if (nameAttr == null) {
                throw new XmlParserException("Missing predefined effect name");
            }
            XmlConstants.PredefinedEffectName effectName =
                    XmlConstants.PredefinedEffectName.findByName(nameAttr, flags);
            if (effectName == null) {
                throw new XmlParserException("Unexpected predefined effect name " + nameAttr);
            }
            if (allowHidden) {
                fallback =
                        parser.getAttributeBoolean(
                                XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_FALLBACK, true);
            } else {
                fallback = true;
            }
            XmlReader.readEndTag(parser);
            return new SerializedPredefinedEffect(effectName, fallback);
        }
    }
}
