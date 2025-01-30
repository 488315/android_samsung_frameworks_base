package kotlinx.coroutines.scheduling;

import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import kotlinx.coroutines.DebugStringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TaskImpl extends Task {
    public final Runnable block;

    public TaskImpl(Runnable runnable, long j, TaskContext taskContext) {
        super(j, taskContext);
        this.block = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.block.run();
        } finally {
            this.taskContext.getClass();
        }
    }

    public final String toString() {
        String classSimpleName = DebugStringsKt.getClassSimpleName(this.block);
        String hexAddress = DebugStringsKt.getHexAddress(this.block);
        long j = this.submissionTime;
        TaskContext taskContext = this.taskContext;
        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Task[", classSimpleName, "@", hexAddress, ", ");
        m87m.append(j);
        m87m.append(", ");
        m87m.append(taskContext);
        m87m.append("]");
        return m87m.toString();
    }
}
