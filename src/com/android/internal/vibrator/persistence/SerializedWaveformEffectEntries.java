package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.util.IntArray;
import android.util.LongArray;
import com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
final class SerializedWaveformEffectEntries implements SerializedComposedEffect.SerializedSegment {
    private final int[] mAmplitudes;
    private final long[] mTimings;

    private SerializedWaveformEffectEntries(long[] jArr, int[] iArr) {
        this.mTimings = jArr;
        this.mAmplitudes = iArr;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void deserializeIntoComposition(VibrationEffect.Composition composition) {
        composition.addEffect(VibrationEffect.createWaveform(this.mTimings, this.mAmplitudes, -1));
    }

    @Override // com.android.internal.vibrator.persistence.SerializedComposedEffect.SerializedSegment
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        for (int i = 0; i < this.mTimings.length; i++) {
            typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENTRY);
            if (this.mAmplitudes[i] == -1) {
                typedXmlSerializer.attribute(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE, "default");
            } else {
                typedXmlSerializer.attributeInt(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_AMPLITUDE, this.mAmplitudes[i]);
            }
            typedXmlSerializer.attributeLong(XmlConstants.NAMESPACE, XmlConstants.ATTRIBUTE_DURATION_MS, this.mTimings[i]);
            typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_WAVEFORM_ENTRY);
        }
    }

    public String toString() {
        return "SerializedWaveformEffectEntries{timings=" + Arrays.toString(this.mTimings) + ", amplitudes=" + Arrays.toString(this.mAmplitudes) + '}';
    }

    static final class Builder {
        private final LongArray mTimings = new LongArray();
        private final IntArray mAmplitudes = new IntArray();

        Builder() {
        }

        void addDurationAndAmplitude(long j, int i) {
            this.mTimings.add(j);
            this.mAmplitudes.add(i);
        }

        boolean hasNonZeroDuration() {
            for (int i = 0; i < this.mTimings.size(); i++) {
                if (this.mTimings.get(i) > 0) {
                    return true;
                }
            }
            return false;
        }

        SerializedWaveformEffectEntries build() {
            return new SerializedWaveformEffectEntries(this.mTimings.toArray(), this.mAmplitudes.toArray());
        }
    }

    static final class Parser {
        Parser() {
        }

        public static void parseWaveformEntry(TypedXmlPullParser typedXmlPullParser, final Builder builder) throws IOException, XmlParserException {
            Objects.requireNonNull(builder);
            SerializedAmplitudeStepWaveform.Parser.parseWaveformEntry(typedXmlPullParser, new BiConsumer() { // from class: com.android.internal.vibrator.persistence.SerializedWaveformEffectEntries$Parser$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    builder.addDurationAndAmplitude(((Integer) obj).intValue(), ((Integer) obj2).intValue());
                }
            });
        }
    }
}
