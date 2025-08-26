package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
final class SerializedComposedEffect implements XmlSerializedVibration<VibrationEffect.Composed> {
    private final SerializedSegment[] mSegments;

    interface SerializedSegment {
        void deserializeIntoComposition(VibrationEffect.Composition composition);

        void write(TypedXmlSerializer typedXmlSerializer) throws IOException;
    }

    SerializedComposedEffect(SerializedSegment serializedSegment) {
        Objects.requireNonNull(serializedSegment);
        this.mSegments = new SerializedSegment[]{serializedSegment};
    }

    SerializedComposedEffect(SerializedSegment[] serializedSegmentArr) {
        Objects.requireNonNull(serializedSegmentArr);
        Preconditions.checkArgument(serializedSegmentArr.length > 0, "Unsupported empty vibration");
        this.mSegments = serializedSegmentArr;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public VibrationEffect.Composed deserialize() {
        VibrationEffect.Composition compositionStartComposition = VibrationEffect.startComposition();
        for (SerializedSegment serializedSegment : this.mSegments) {
            serializedSegment.deserializeIntoComposition(compositionStartComposition);
        }
        return (VibrationEffect.Composed) compositionStartComposition.compose();
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VIBRATION_EFFECT);
        writeContent(typedXmlSerializer);
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VIBRATION_EFFECT);
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void writeContent(TypedXmlSerializer typedXmlSerializer) throws IOException {
        for (SerializedSegment serializedSegment : this.mSegments) {
            serializedSegment.write(typedXmlSerializer);
        }
    }

    public String toString() {
        return "SerializedComposedEffect{segments=" + Arrays.toString(this.mSegments) + '}';
    }
}
