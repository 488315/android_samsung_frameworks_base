package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;

import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlSerializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

final class SerializedVibrationEffect implements XmlSerializedVibration<VibrationEffect> {
    private final SerializedSegment[] mSegments;

    interface SerializedSegment {
        void deserializeIntoComposition(VibrationEffect.Composition composition);

        void write(TypedXmlSerializer typedXmlSerializer) throws IOException;
    }

    SerializedVibrationEffect(SerializedSegment segment) {
        Objects.requireNonNull(segment);
        this.mSegments = new SerializedSegment[] {segment};
    }

    SerializedVibrationEffect(SerializedSegment[] segments) {
        Objects.requireNonNull(segments);
        Preconditions.checkArgument(segments.length > 0, "Unsupported empty vibration");
        this.mSegments = segments;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public VibrationEffect deserialize() {
        VibrationEffect.Composition composition = VibrationEffect.startComposition();
        for (SerializedSegment segment : this.mSegments) {
            segment.deserializeIntoComposition(composition);
        }
        return composition.compose();
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void write(TypedXmlSerializer serializer) throws IOException {
        serializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VIBRATION_EFFECT);
        writeContent(serializer);
        serializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VIBRATION_EFFECT);
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void writeContent(TypedXmlSerializer serializer) throws IOException {
        for (SerializedSegment segment : this.mSegments) {
            segment.write(serializer);
        }
    }

    public String toString() {
        return "SerializedVibrationEffect{segments=" + Arrays.toString(this.mSegments) + '}';
    }
}
