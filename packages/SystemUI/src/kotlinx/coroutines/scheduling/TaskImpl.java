package kotlinx.coroutines.scheduling;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import kotlinx.coroutines.DebugStringsKt;

/* loaded from: classes4.dex */
public final class TaskImpl extends Task {
    public final Runnable block;

    public TaskImpl(Runnable runnable, long j, boolean z) {
        super(j, z);
        this.block = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.block.run();
    }

    public final String toString() {
        String simpleName = this.block.getClass().getSimpleName();
        String hexAddress = DebugStringsKt.getHexAddress(this.block);
        long j = this.submissionTime;
        boolean z = this.taskContext;
        String str = TasksKt.DEFAULT_SCHEDULER_NAME;
        String str2 = z ? "Blocking" : "Non-blocking";
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Task[", simpleName, "@", hexAddress, ", ");
        sbM.append(j);
        sbM.append(", ");
        sbM.append(str2);
        sbM.append("]");
        return sbM.toString();
    }
}
