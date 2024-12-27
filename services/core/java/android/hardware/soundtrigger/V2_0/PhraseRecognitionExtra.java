package android.hardware.soundtrigger.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PhraseRecognitionExtra {
    public int id = 0;
    public int recognitionModes = 0;
    public int confidenceLevel = 0;
    public final ArrayList levels = new ArrayList();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PhraseRecognitionExtra.class) {
            return false;
        }
        PhraseRecognitionExtra phraseRecognitionExtra = (PhraseRecognitionExtra) obj;
        return this.id == phraseRecognitionExtra.id
                && this.recognitionModes == phraseRecognitionExtra.recognitionModes
                && this.confidenceLevel == phraseRecognitionExtra.confidenceLevel
                && HidlSupport.deepEquals(this.levels, phraseRecognitionExtra.levels);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.id),
                AudioConfig$$ExternalSyntheticOutline0.m(this.recognitionModes),
                AudioConfig$$ExternalSyntheticOutline0.m(this.confidenceLevel),
                Integer.valueOf(HidlSupport.deepHashCode(this.levels)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.id = hwBlob.getInt32(j);
        this.recognitionModes = hwBlob.getInt32(j + 4);
        this.confidenceLevel = hwBlob.getInt32(j + 8);
        int int32 = hwBlob.getInt32(j + 24);
        HwBlob readEmbeddedBuffer =
                hwParcel.readEmbeddedBuffer(int32 * 8, hwBlob.handle(), j + 16, true);
        this.levels.clear();
        for (int i = 0; i < int32; i++) {
            ConfidenceLevel confidenceLevel = new ConfidenceLevel();
            long j2 = i * 8;
            confidenceLevel.userId = readEmbeddedBuffer.getInt32(j2);
            confidenceLevel.levelPercent = readEmbeddedBuffer.getInt32(j2 + 4);
            this.levels.add(confidenceLevel);
        }
    }

    public final String toString() {
        return "{.id = "
                + this.id
                + ", .recognitionModes = "
                + this.recognitionModes
                + ", .confidenceLevel = "
                + this.confidenceLevel
                + ", .levels = "
                + this.levels
                + "}";
    }
}
