package android.os;

import android.os.DdmSyncState;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Arrays;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class DdmSyncState {
    private static int sCurrentStageIndex;

    public enum Stage {
        Boot("BOOT"),
        Attach("ATCH"),
        Bind("BIND"),
        Named("NAMD"),
        Debugger("DEBG"),
        Running("A_GO");

        final String mLabel;

        Stage(String str) {
            if (str.length() != 4) {
                throw new IllegalStateException("Bad stage id '" + str + "'. Must be four letters");
            }
            this.mLabel = str;
        }

        public int toInt() {
            int i = 0;
            for (int i2 = 0; i2 < 4; i2++) {
                i = (i << 8) | (this.mLabel.charAt(i2) & 255);
            }
            return i;
        }
    }

    public static synchronized Stage getStage() {
        Stage stage;
        synchronized (DdmSyncState.class) {
            stage = Stage.values()[sCurrentStageIndex];
        }
        return stage;
    }

    public static void reset() {
        sCurrentStageIndex = 0;
    }

    public static synchronized void next(Stage stage) {
        synchronized (DdmSyncState.class) {
            Stage[] values = Stage.values();
            int i = sCurrentStageIndex;
            while (i < values.length && values[i] != stage) {
                i++;
            }
            if (i == values.length || values[i] != stage) {
                throw new IllegalStateException("Cannot go to " + stage + " from:" + getInternalState());
            }
            sCurrentStageIndex = i;
        }
    }

    private static String getInternalState() {
        return "\nlevel = " + sCurrentStageIndex + "\nstages = " + Arrays.toString(Arrays.stream(Stage.values()).map(new Function() { // from class: android.os.DdmSyncState$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DdmSyncState.Stage) obj).name();
            }
        }).toArray()) + ShaderAssembler.NEWLINE;
    }
}
