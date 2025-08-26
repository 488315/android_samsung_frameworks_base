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
            int iCharAt = 0;
            for (int i = 0; i < 4; i++) {
                iCharAt = (iCharAt << 8) | (this.mLabel.charAt(i) & 255);
            }
            return iCharAt;
        }
    }

    public static synchronized Stage getStage() {
        return Stage.values()[sCurrentStageIndex];
    }

    public static void reset() {
        sCurrentStageIndex = 0;
    }

    public static synchronized void next(Stage stage) {
        Stage[] stageArrValues = Stage.values();
        int i = sCurrentStageIndex;
        while (i < stageArrValues.length && stageArrValues[i] != stage) {
            i++;
        }
        if (i == stageArrValues.length || stageArrValues[i] != stage) {
            throw new IllegalStateException("Cannot go to " + stage + " from:" + getInternalState());
        }
        sCurrentStageIndex = i;
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
