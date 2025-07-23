package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.internal.vibrator.persistence.SerializedCompositionPrimitive;
import com.android.internal.vibrator.persistence.SerializedWaveformEffectEntries;
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

        static SerializedRepeatingEffect parseNext(TypedXmlPullParser typedXmlPullParser, int i) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_REPEATING_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
            Builder builder = new Builder();
            int depth = typedXmlPullParser.getDepth();
            boolean readNextTagWithin = XmlReader.readNextTagWithin(typedXmlPullParser, depth);
            if (readNextTagWithin && XmlConstants.TAG_PREAMBLE.equals(typedXmlPullParser.getName())) {
                builder.setPreamble(parseEffect(typedXmlPullParser, XmlConstants.TAG_PREAMBLE, i));
                readNextTagWithin = XmlReader.readNextTagWithin(typedXmlPullParser, depth);
            }
            XmlValidator.checkParserCondition(readNextTagWithin, "Missing %s tag in %s", XmlConstants.TAG_REPEATING, XmlConstants.TAG_REPEATING_EFFECT);
            builder.setRepeating(parseEffect(typedXmlPullParser, XmlConstants.TAG_REPEATING, i));
            XmlValidator.checkParserCondition(builder.hasRepeatingSegment(), "Unexpected %s tag with no repeating segment", XmlConstants.TAG_REPEATING_EFFECT);
            XmlReader.readEndTag(typedXmlPullParser, XmlConstants.TAG_REPEATING_EFFECT, depth);
            return builder.build();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0061, code lost:
        
            if (r2.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_BASIC_ENVELOPE_EFFECT) == false) goto L4;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static com.android.internal.vibrator.persistence.SerializedComposedEffect parseEffect(com.android.modules.utils.TypedXmlPullParser r5, java.lang.String r6, int r7) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
            /*
                com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(r5, r6)
                r0 = 0
                java.lang.String[] r1 = new java.lang.String[r0]
                com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(r5, r1)
                int r1 = r5.getDepth()
                boolean r2 = com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(r5, r1)
                java.lang.String r3 = "Unsupported empty %s tag"
                java.lang.Object[] r4 = new java.lang.Object[]{r6}
                com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(r2, r3, r4)
                java.lang.String r2 = r5.getName()
                r2.hashCode()
                int r3 = r2.hashCode()
                r4 = -1
                switch(r3) {
                    case -2119736689: goto L5b;
                    case -1327271112: goto L4f;
                    case 149296439: goto L43;
                    case 1239288734: goto L37;
                    case 1638168034: goto L2b;
                    default: goto L29;
                }
            L29:
                r0 = r4
                goto L64
            L2b:
                java.lang.String r0 = "waveform-entry"
                boolean r0 = r2.equals(r0)
                if (r0 != 0) goto L35
                goto L29
            L35:
                r0 = 4
                goto L64
            L37:
                java.lang.String r0 = "waveform-envelope-effect"
                boolean r0 = r2.equals(r0)
                if (r0 != 0) goto L41
                goto L29
            L41:
                r0 = 3
                goto L64
            L43:
                java.lang.String r0 = "primitive-effect"
                boolean r0 = r2.equals(r0)
                if (r0 != 0) goto L4d
                goto L29
            L4d:
                r0 = 2
                goto L64
            L4f:
                java.lang.String r0 = "predefined-effect"
                boolean r0 = r2.equals(r0)
                if (r0 != 0) goto L59
                goto L29
            L59:
                r0 = 1
                goto L64
            L5b:
                java.lang.String r3 = "basic-envelope-effect"
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L64
                goto L29
            L64:
                switch(r0) {
                    case 0: goto La6;
                    case 1: goto L9b;
                    case 2: goto L96;
                    case 3: goto L8c;
                    case 4: goto L87;
                    default: goto L67;
                }
            L67:
                com.android.internal.vibrator.persistence.XmlParserException r7 = new com.android.internal.vibrator.persistence.XmlParserException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Unexpected tag "
                r0.<init>(r1)
                java.lang.String r5 = r5.getName()
                r0.append(r5)
                java.lang.String r5 = " in vibration tag "
                r0.append(r5)
                r0.append(r6)
                java.lang.String r5 = r0.toString()
                r7.<init>(r5)
                throw r7
            L87:
                com.android.internal.vibrator.persistence.SerializedComposedEffect r7 = parseWaveformEntries(r5, r1)
                goto Lb0
            L8c:
                com.android.internal.vibrator.persistence.SerializedComposedEffect r0 = new com.android.internal.vibrator.persistence.SerializedComposedEffect
                com.android.internal.vibrator.persistence.SerializedWaveformEnvelopeEffect r7 = com.android.internal.vibrator.persistence.SerializedWaveformEnvelopeEffect.Parser.parseNext(r5, r7)
                r0.<init>(r7)
                goto La4
            L96:
                com.android.internal.vibrator.persistence.SerializedComposedEffect r7 = parsePrimitiveEffects(r5, r1)
                goto Lb0
            L9b:
                com.android.internal.vibrator.persistence.SerializedComposedEffect r0 = new com.android.internal.vibrator.persistence.SerializedComposedEffect
                com.android.internal.vibrator.persistence.SerializedPredefinedEffect r7 = com.android.internal.vibrator.persistence.SerializedPredefinedEffect.Parser.parseNext(r5, r7)
                r0.<init>(r7)
            La4:
                r7 = r0
                goto Lb0
            La6:
                com.android.internal.vibrator.persistence.SerializedComposedEffect r0 = new com.android.internal.vibrator.persistence.SerializedComposedEffect
                com.android.internal.vibrator.persistence.SerializedBasicEnvelopeEffect r7 = com.android.internal.vibrator.persistence.SerializedBasicEnvelopeEffect.Parser.parseNext(r5, r7)
                r0.<init>(r7)
                goto La4
            Lb0:
                com.android.internal.vibrator.persistence.XmlReader.readEndTag(r5, r6, r1)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.vibrator.persistence.SerializedRepeatingEffect.Parser.parseEffect(com.android.modules.utils.TypedXmlPullParser, java.lang.String, int):com.android.internal.vibrator.persistence.SerializedComposedEffect");
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
