package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.util.IntArray;
import android.util.LongArray;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
final class SerializedAmplitudeStepWaveform implements SerializedComposedEffect.SerializedSegment {
    private final int[] mAmplitudes;
    private final int mRepeatIndex;
    private final long[] mTimings;

    private SerializedAmplitudeStepWaveform(long[] jArr, int[] iArr, int i) {
        this.mTimings = jArr;
        this.mAmplitudes = iArr;
        this.mRepeatIndex = i;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        composition.addEffect(VibrationEffect.createWaveform(this.mTimings, this.mAmplitudes, this.mRepeatIndex));
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_EFFECT);
        for (int i = 0; i < this.mTimings.length; i++) {
            if (i == this.mRepeatIndex) {
                typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING);
            }
            writeWaveformEntry(typedXmlSerializer, i);
        }
        if (this.mRepeatIndex >= 0) {
            typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING);
        }
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_EFFECT);
    }

    private void writeWaveformEntry(TypedXmlSerializer typedXmlSerializer, int i) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENTRY);
        if (this.mAmplitudes[i] == -1) {
            typedXmlSerializer.attribute(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE, "default");
        } else {
            typedXmlSerializer.attributeInt(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE, this.mAmplitudes[i]);
        }
        typedXmlSerializer.attributeLong(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DURATION_MS, this.mTimings[i]);
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENTRY);
    }

    public String toString() {
        return "SerializedAmplitudeStepWaveform{timings=" + Arrays.toString(this.mTimings) + ", amplitudes=" + Arrays.toString(this.mAmplitudes) + ", repeatIndex=" + this.mRepeatIndex + '}';
    }

    static final class Builder {
        private final LongArray mTimings = new LongArray();
        private final IntArray mAmplitudes = new IntArray();
        private int mRepeatIndex = -1;

        Builder() {
        }

        void addDurationAndAmplitude(long j, int i) {
            this.mTimings.add(j);
            this.mAmplitudes.add(i);
        }

        void setRepeatIndexToCurrentEntry() {
            this.mRepeatIndex = this.mTimings.size();
        }

        boolean hasNonZeroDuration() {
            for (int i = 0; i < this.mTimings.size(); i++) {
                if (this.mTimings.get(i) > 0) {
                    return true;
                }
            }
            return false;
        }

        SerializedAmplitudeStepWaveform build() {
            return new SerializedAmplitudeStepWaveform(this.mTimings.toArray(), this.mAmplitudes.toArray(), this.mRepeatIndex);
        }
    }

    static final class Parser {
        Parser() {
        }

        static SerializedAmplitudeStepWaveform parseNext(TypedXmlPullParser typedXmlPullParser) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_WAVEFORM_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
            Builder builder = new Builder();
            int depth = typedXmlPullParser.getDepth();
            while (XmlReader.readNextTagWithin(typedXmlPullParser, depth) && !XmlConstants.TAG_REPEATING.equals(typedXmlPullParser.getName())) {
                parseWaveformEntry(typedXmlPullParser, new SerializedAmplitudeStepWaveform$Parser$$ExternalSyntheticLambda0(builder));
            }
            if (XmlConstants.TAG_REPEATING.equals(typedXmlPullParser.getName())) {
                parseRepeating(typedXmlPullParser, builder);
            }
            XmlValidator.checkParserCondition(builder.hasNonZeroDuration(), "Unexpected %s tag with total duration zero", XmlConstants.TAG_WAVEFORM_EFFECT);
            XmlReader.readEndTag(typedXmlPullParser, XmlConstants.TAG_WAVEFORM_EFFECT, depth);
            return builder.build();
        }

        static void parseWaveformEntry(TypedXmlPullParser typedXmlPullParser, BiConsumer<Integer, Integer> biConsumer) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_WAVEFORM_ENTRY);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, XmlConstants.ATTRIBUTE_DURATION_MS, XmlConstants.ATTRIBUTE_AMPLITUDE);
            biConsumer.accept(Integer.valueOf(XmlReader.readAttributeIntNonNegative(typedXmlPullParser, XmlConstants.ATTRIBUTE_DURATION_MS)), Integer.valueOf("default".equals(typedXmlPullParser.getAttributeValue(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE)) ? -1 : XmlReader.readAttributeIntInRange(typedXmlPullParser, XmlConstants.ATTRIBUTE_AMPLITUDE, 0, 255)));
            XmlReader.readEndTag(typedXmlPullParser);
        }

        private static void parseRepeating(TypedXmlPullParser typedXmlPullParser, Builder builder) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_REPEATING);
            boolean z = false;
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
            builder.setRepeatIndexToCurrentEntry();
            int depth = typedXmlPullParser.getDepth();
            while (XmlReader.readNextTagWithin(typedXmlPullParser, depth)) {
                Objects.requireNonNull(builder);
                parseWaveformEntry(typedXmlPullParser, new SerializedAmplitudeStepWaveform$Parser$$ExternalSyntheticLambda0(builder));
                z = true;
            }
            XmlValidator.checkParserCondition(z, "Unexpected empty %s tag", XmlConstants.TAG_REPEATING);
            XmlReader.readEndTag(typedXmlPullParser, XmlConstants.TAG_REPEATING, depth);
        }
    }
}
