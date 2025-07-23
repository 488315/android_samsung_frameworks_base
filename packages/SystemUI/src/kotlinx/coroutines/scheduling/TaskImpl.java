package kotlinx.coroutines.scheduling;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import kotlinx.coroutines.DebugStringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Task[", simpleName, "@", hexAddress, ", ");
        m.append(j);
        m.append(", ");
        m.append(str2);
        m.append("]");
        return m.toString();
    }
}
