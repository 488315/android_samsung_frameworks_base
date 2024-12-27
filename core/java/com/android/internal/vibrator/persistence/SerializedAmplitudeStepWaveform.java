package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.util.IntArray;
import android.util.LongArray;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import java.io.IOException;
import java.util.Arrays;

final class SerializedAmplitudeStepWaveform implements SerializedVibrationEffect.SerializedSegment {
    private final int[] mAmplitudes;
    private final int mRepeatIndex;
    private final long[] mTimings;

    private SerializedAmplitudeStepWaveform(long[] timings, int[] amplitudes, int repeatIndex) {
        this.mTimings = timings;
        this.mAmplitudes = amplitudes;
        this.mRepeatIndex = repeatIndex;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        composition.addEffect(
                VibrationEffect.createWaveform(this.mTimings, this.mAmplitudes, this.mRepeatIndex));
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void write(TypedXmlSerializer serializer) throws IOException {
        serializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_EFFECT);
        for (int i = 0; i < this.mTimings.length; i++) {
            if (i == this.mRepeatIndex) {
                serializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING);
            }
            writeWaveformEntry(serializer, i);
        }
        int i2 = this.mRepeatIndex;
        if (i2 >= 0) {
            serializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_REPEATING);
        }
        serializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_EFFECT);
    }

    private void writeWaveformEntry(TypedXmlSerializer serializer, int index) throws IOException {
        serializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENTRY);
        if (this.mAmplitudes[index] == -1) {
            serializer.attribute(
                    XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE, "default");
        } else {
            serializer.attributeInt(
                    XmlConstants.NAMESPACE,
                    XmlConstants.ATTRIBUTE_AMPLITUDE,
                    this.mAmplitudes[index]);
        }
        serializer.attributeLong(
                XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DURATION_MS, this.mTimings[index]);
        serializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENTRY);
    }

    public String toString() {
        return "SerializedAmplitudeStepWaveform{timings="
                + Arrays.toString(this.mTimings)
                + ", amplitudes="
                + Arrays.toString(this.mAmplitudes)
                + ", repeatIndex="
                + this.mRepeatIndex
                + '}';
    }

    static final class Builder {
        private final LongArray mTimings = new LongArray();
        private final IntArray mAmplitudes = new IntArray();
        private int mRepeatIndex = -1;

        Builder() {}

        void addDurationAndAmplitude(long durationMs, int amplitude) {
            this.mTimings.add(durationMs);
            this.mAmplitudes.add(amplitude);
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
            return new SerializedAmplitudeStepWaveform(
                    this.mTimings.toArray(), this.mAmplitudes.toArray(), this.mRepeatIndex);
        }
    }

    static final class Parser {
        Parser() {}

        static SerializedAmplitudeStepWaveform parseNext(TypedXmlPullParser parser)
                throws XmlParserException, IOException {
            XmlValidator.checkStartTag(parser, XmlConstants.TAG_WAVEFORM_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(parser, new String[0]);
            Builder waveformBuilder = new Builder();
            int outerDepth = parser.getDepth();
            while (XmlReader.readNextTagWithin(parser, outerDepth)
                    && !XmlConstants.TAG_REPEATING.equals(parser.getName())) {
                parseWaveformEntry(parser, waveformBuilder);
            }
            if (XmlConstants.TAG_REPEATING.equals(parser.getName())) {
                parseRepeating(parser, waveformBuilder);
            }
            XmlValidator.checkParserCondition(
                    waveformBuilder.hasNonZeroDuration(),
                    "Unexpected %s tag with total duration zero",
                    XmlConstants.TAG_WAVEFORM_EFFECT);
            XmlReader.readEndTag(parser, XmlConstants.TAG_WAVEFORM_EFFECT, outerDepth);
            return waveformBuilder.build();
        }

        private static void parseRepeating(TypedXmlPullParser parser, Builder waveformBuilder)
                throws XmlParserException, IOException {
            XmlValidator.checkStartTag(parser, XmlConstants.TAG_REPEATING);
            XmlValidator.checkTagHasNoUnexpectedAttributes(parser, new String[0]);
            waveformBuilder.setRepeatIndexToCurrentEntry();
            boolean hasEntry = false;
            int outerDepth = parser.getDepth();
            while (XmlReader.readNextTagWithin(parser, outerDepth)) {
                parseWaveformEntry(parser, waveformBuilder);
                hasEntry = true;
            }
            XmlValidator.checkParserCondition(
                    hasEntry, "Unexpected empty %s tag", XmlConstants.TAG_REPEATING);
            XmlReader.readEndTag(parser, XmlConstants.TAG_REPEATING, outerDepth);
        }

        private static void parseWaveformEntry(TypedXmlPullParser parser, Builder waveformBuilder)
                throws XmlParserException, IOException {
            int amplitude;
            XmlValidator.checkStartTag(parser, XmlConstants.TAG_WAVEFORM_ENTRY);
            XmlValidator.checkTagHasNoUnexpectedAttributes(
                    parser, XmlConstants.ATTRIBUTE_DURATION_MS, XmlConstants.ATTRIBUTE_AMPLITUDE);
            String rawAmplitude =
                    parser.getAttributeValue(
                            XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE);
            if ("default".equals(rawAmplitude)) {
                amplitude = -1;
            } else {
                amplitude =
                        XmlReader.readAttributeIntInRange(
                                parser, XmlConstants.ATTRIBUTE_AMPLITUDE, 0, 255);
            }
            int durationMs =
                    XmlReader.readAttributeIntNonNegative(
                            parser, XmlConstants.ATTRIBUTE_DURATION_MS);
            waveformBuilder.addDurationAndAmplitude(durationMs, amplitude);
            XmlReader.readEndTag(parser);
        }
    }
}
