package android.os;

import java.util.Arrays;
import java.util.function.Function;

public final class DdmSyncState {
    private static int sCurrentStageIndex = 0;

    public enum Stage {
        Boot("BOOT"),
        Attach("ATCH"),
        Bind("BIND"),
        Named("NAMD"),
        Debugger("DEBG"),
        Running("A_GO");

        final String mLabel;

        Stage(String label) {
            if (label.length() != 4) {
                throw new IllegalStateException(
                        "Bad stage id '" + label + "'. Must be four letters");
            }
            this.mLabel = label;
        }

        public int toInt() {
            int result = 0;
            for (int i = 0; i < 4; i++) {
                result = (result << 8) | (this.mLabel.charAt(i) & 255);
            }
            return result;
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

    public static synchronized void next(Stage nextStage) {
        synchronized (DdmSyncState.class) {
            Stage[] stages = Stage.values();
            int rover = sCurrentStageIndex;
            while (rover < stages.length && stages[rover] != nextStage) {
                rover++;
            }
            if (rover == stages.length || stages[rover] != nextStage) {
                throw new IllegalStateException(
                        "Cannot go to " + nextStage + " from:" + getInternalState());
            }
            sCurrentStageIndex = rover;
        }
    }

    private static String getInternalState() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("level = ").append(sCurrentStageIndex);
        sb.append("\n");
        sb.append("stages = ");
        sb.append(
                Arrays.toString(
                        Arrays.stream(Stage.values())
                                .map(
                                        new Function() { // from class:
                                                         // android.os.DdmSyncState$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return ((DdmSyncState.Stage) obj).name();
                                            }
                                        })
                                .toArray()));
        sb.append("\n");
        return sb.toString();
    }
}
