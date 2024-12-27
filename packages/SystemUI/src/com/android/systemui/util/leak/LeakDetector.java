package com.android.systemui.util.leak;

import android.os.Build;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class LeakDetector implements Dumpable {
    public static final boolean ENABLED = Build.IS_DEBUGGABLE;
    private final TrackedCollections mTrackedCollections;
    private final TrackedGarbage mTrackedGarbage;
    private final TrackedObjects mTrackedObjects;

    public LeakDetector(TrackedCollections trackedCollections, TrackedGarbage trackedGarbage, TrackedObjects trackedObjects, DumpManager dumpManager) {
        this.mTrackedCollections = trackedCollections;
        this.mTrackedGarbage = trackedGarbage;
        this.mTrackedObjects = trackedObjects;
        dumpManager.registerDumpable(getClass().getSimpleName(), this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dump$0(Collection collection) {
        return !TrackedObjects.isTrackedObject(collection);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("SYSUI LEAK DETECTOR");
        indentingPrintWriter.increaseIndent();
        if (this.mTrackedCollections == null || this.mTrackedGarbage == null) {
            indentingPrintWriter.println("disabled");
        } else {
            indentingPrintWriter.println("TrackedCollections:");
            indentingPrintWriter.increaseIndent();
            final int i = 0;
            this.mTrackedCollections.dump(indentingPrintWriter, new Predicate() { // from class: com.android.systemui.util.leak.LeakDetector$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$dump$0;
                    Collection collection = (Collection) obj;
                    switch (i) {
                        case 0:
                            lambda$dump$0 = LeakDetector.lambda$dump$0(collection);
                            return lambda$dump$0;
                        default:
                            return TrackedObjects.isTrackedObject(collection);
                    }
                }
            });
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("TrackedObjects:");
            indentingPrintWriter.increaseIndent();
            final int i2 = 1;
            this.mTrackedCollections.dump(indentingPrintWriter, new Predicate() { // from class: com.android.systemui.util.leak.LeakDetector$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$dump$0;
                    Collection collection = (Collection) obj;
                    switch (i2) {
                        case 0:
                            lambda$dump$0 = LeakDetector.lambda$dump$0(collection);
                            return lambda$dump$0;
                        default:
                            return TrackedObjects.isTrackedObject(collection);
                    }
                }
            });
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.print("TrackedGarbage:");
            indentingPrintWriter.increaseIndent();
            this.mTrackedGarbage.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public TrackedGarbage getTrackedGarbage() {
        return this.mTrackedGarbage;
    }

    public <T> void trackCollection(Collection<T> collection, String str) {
        TrackedCollections trackedCollections = this.mTrackedCollections;
        if (trackedCollections != null) {
            trackedCollections.track(collection, str);
        }
    }

    public void trackGarbage(Object obj) {
        TrackedGarbage trackedGarbage = this.mTrackedGarbage;
        if (trackedGarbage != null) {
            trackedGarbage.track(obj);
        }
    }

    public <T> void trackInstance(T t) {
        TrackedObjects trackedObjects = this.mTrackedObjects;
        if (trackedObjects != null) {
            trackedObjects.track(t);
        }
    }
}
