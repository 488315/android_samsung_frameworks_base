package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import com.android.internal.vibrator.persistence.SerializedBasicEnvelopeEffect;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.internal.vibrator.persistence.SerializedCompositionPrimitive;
import com.android.internal.vibrator.persistence.SerializedPredefinedEffect;
import com.android.internal.vibrator.persistence.SerializedWaveformEffectEntries;
import com.android.internal.vibrator.persistence.SerializedWaveformEnvelopeEffect;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SerializedRepeatingEffect implements SerializedComposedEffect.SerializedSegment {
    private final SerializedComposedEffect mSerializedPreamble;
    private final SerializedComposedEffect mSerializedRepeating;

    SerializedRepeatingEffect(SerializedComposedEffect serializedComposedEffect, SerializedComposedEffect serializedComposedEffect2) {
        this.mSerializedPreamble = serializedComposedEffect;
        this.mSerializedRepeating = serializedComposedEffect2;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING_EFFECT);
        if (this.mSerializedPreamble != null) {
            typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PREAMBLE);
            this.mSerializedPreamble.writeContent(typedXmlSerializer);
            typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_PREAMBLE);
        }
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING);
        this.mSerializedRepeating.writeContent(typedXmlSerializer);
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING);
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING_EFFECT);
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        SerializedComposedEffect serializedComposedEffect = this.mSerializedPreamble;
        if (serializedComposedEffect != null) {
            composition.addEffect(VibrationEffect.createRepeatingEffect(serializedComposedEffect.deserialize(), this.mSerializedRepeating.deserialize()));
        } else {
            composition.addEffect(VibrationEffect.createRepeatingEffect(this.mSerializedRepeating.deserialize()));
        }
    }

    public String toString() {
        return "SerializedRepeatingEffect{preamble=" + this.mSerializedPreamble + ", repeating=" + this.mSerializedRepeating + '}';
    }

    static final class Builder {
        private SerializedComposedEffect mPreamble;
        private SerializedComposedEffect mRepeating;

        Builder() {
        }

        void setPreamble(SerializedComposedEffect serializedComposedEffect) {
            this.mPreamble = serializedComposedEffect;
        }

        void setRepeating(SerializedComposedEffect serializedComposedEffect) {
            this.mRepeating = serializedComposedEffect;
        }

        boolean hasRepeatingSegment() {
            return this.mRepeating != null;
        }

        SerializedRepeatingEffect build() {
            return new SerializedRepeatingEffect(this.mPreamble, this.mRepeating);
        }
    }

    static final class Parser {
        Parser() {
        }

        static SerializedRepeatingEffect parseNext(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_REPEATING_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
            Builder builder = new Builder();
            int depth = typedXmlPullParser.getDepth();
            boolean nextTagWithin = XmlReader.readNextTagWithin(typedXmlPullParser, depth);
            if (nextTagWithin && XmlConstants.TAG_PREAMBLE.equals(typedXmlPullParser.getName())) {
                builder.setPreamble(parseEffect(typedXmlPullParser, XmlConstants.TAG_PREAMBLE, i));
                nextTagWithin = XmlReader.readNextTagWithin(typedXmlPullParser, depth);
            }
            XmlValidator.checkParserCondition(nextTagWithin, "Missing %s tag in %s", XmlConstants.TAG_REPEATING, XmlConstants.TAG_REPEATING_EFFECT);
            builder.setRepeating(parseEffect(typedXmlPullParser, XmlConstants.TAG_REPEATING, i));
            XmlValidator.checkParserCondition(builder.hasRepeatingSegment(), "Unexpected %s tag with no repeating segment", XmlConstants.TAG_REPEATING_EFFECT);
            XmlReader.readEndTag(typedXmlPullParser, XmlConstants.TAG_REPEATING_EFFECT, depth);
            return builder.build();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:4:0x0029  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static SerializedComposedEffect parseEffect(TypedXmlPullParser typedXmlPullParser, String str, int i) throws IOException, XmlParserException {
            SerializedComposedEffect serializedComposedEffect;
            SerializedComposedEffect primitiveEffects;
            XmlValidator.checkStartTag(typedXmlPullParser, str);
            char c = 0;
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
            int depth = typedXmlPullParser.getDepth();
            XmlValidator.checkParserCondition(XmlReader.readNextTagWithin(typedXmlPullParser, depth), "Unsupported empty %s tag", str);
            String name = typedXmlPullParser.getName();
            name.hashCode();
            switch (name.hashCode()) {
                case -2119736689:
                    if (!name.equals(XmlConstants.TAG_BASIC_ENVELOPE_EFFECT)) {
                        c = 65535;
                        break;
                    }
                    break;
                case -1327271112:
                    if (name.equals(XmlConstants.TAG_PREDEFINED_EFFECT)) {
                        c = 1;
                        break;
                    }
                    break;
                case 149296439:
                    if (name.equals(XmlConstants.TAG_PRIMITIVE_EFFECT)) {
                        c = 2;
                        break;
                    }
                    break;
                case 1239288734:
                    if (name.equals(XmlConstants.TAG_WAVEFORM_ENVELOPE_EFFECT)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1638168034:
                    if (name.equals(XmlConstants.TAG_WAVEFORM_ENTRY)) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    serializedComposedEffect = new SerializedComposedEffect(SerializedBasicEnvelopeEffect.Parser.parseNext(typedXmlPullParser, i));
                    primitiveEffects = serializedComposedEffect;
                    XmlReader.readEndTag(typedXmlPullParser, str, depth);
                    return primitiveEffects;
                case 1:
                    serializedComposedEffect = new SerializedComposedEffect(SerializedPredefinedEffect.Parser.parseNext(typedXmlPullParser, i));
                    primitiveEffects = serializedComposedEffect;
                    XmlReader.readEndTag(typedXmlPullParser, str, depth);
                    return primitiveEffects;
                case 2:
                    primitiveEffects = parsePrimitiveEffects(typedXmlPullParser, depth);
                    XmlReader.readEndTag(typedXmlPullParser, str, depth);
                    return primitiveEffects;
                case 3:
                    serializedComposedEffect = new SerializedComposedEffect(SerializedWaveformEnvelopeEffect.Parser.parseNext(typedXmlPullParser, i));
                    primitiveEffects = serializedComposedEffect;
                    XmlReader.readEndTag(typedXmlPullParser, str, depth);
                    return primitiveEffects;
                case 4:
                    primitiveEffects = parseWaveformEntries(typedXmlPullParser, depth);
                    XmlReader.readEndTag(typedXmlPullParser, str, depth);
                    return primitiveEffects;
                default:
                    throw new XmlParserException("Unexpected tag " + typedXmlPullParser.getName() + " in vibration tag " + str);
            }
        }

        private static SerializedComposedEffect parsePrimitiveEffects(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
            ArrayList arrayList = new ArrayList();
            do {
                arrayList.add(SerializedCompositionPrimitive.Parser.parseNext(typedXmlPullParser));
            } while (XmlReader.readNextTagWithin(typedXmlPullParser, i));
            return new SerializedComposedEffect((SerializedComposedEffect.SerializedSegment[]) arrayList.toArray(new SerializedComposedEffect.SerializedSegment[arrayList.size()]));
        }

        private static SerializedComposedEffect parseWaveformEntries(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
            SerializedWaveformEffectEntries.Builder builder = new SerializedWaveformEffectEntries.Builder();
            do {
                SerializedWaveformEffectEntries.Parser.parseWaveformEntry(typedXmlPullParser, builder);
            } while (XmlReader.readNextTagWithin(typedXmlPullParser, i));
            XmlValidator.checkParserCondition(builder.hasNonZeroDuration(), "Unexpected %s tag with total duration zero", XmlConstants.TAG_WAVEFORM_ENTRY);
            return new SerializedComposedEffect(builder.build());
        }
    }
}
