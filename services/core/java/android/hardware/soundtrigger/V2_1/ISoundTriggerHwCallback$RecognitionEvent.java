package android.hardware.soundtrigger.V2_1;

import android.os.HidlMemory;
import android.os.HwBlob;
import android.os.HwParcel;

import java.io.IOException;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ISoundTriggerHwCallback$RecognitionEvent {
    public final /* synthetic */ int $r8$classId;
    public Object data;
    public Object header;

    public ISoundTriggerHwCallback$RecognitionEvent(int i) {
        this.$r8$classId = i;
        switch (i) {
            case 1:
                this.header = new ISoundTriggerHwCallback$RecognitionEvent(0);
                this.data = new ArrayList();
                break;
            default:
                this.header =
                        new android.hardware.soundtrigger.V2_0
                                .ISoundTriggerHwCallback$RecognitionEvent();
                this.data = null;
                break;
        }
    }

    public void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob) {
        ((android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback$RecognitionEvent) this.header)
                .readEmbeddedFromParcel(hwParcel, hwBlob);
        try {
            this.data =
                    hwParcel.readEmbeddedHidlMemory(
                                    hwBlob.getFieldHandle(120L), hwBlob.handle(), 120L)
                            .dup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final String toString() {
        switch (this.$r8$classId) {
            case 0:
                return "{.header = "
                        + ((android.hardware.soundtrigger.V2_0
                                        .ISoundTriggerHwCallback$RecognitionEvent)
                                this.header)
                        + ", .data = "
                        + ((HidlMemory) this.data)
                        + "}";
            default:
                return "{.common = "
                        + ((ISoundTriggerHwCallback$RecognitionEvent) this.header)
                        + ", .phraseExtras = "
                        + ((ArrayList) this.data)
                        + "}";
        }
    }
}
