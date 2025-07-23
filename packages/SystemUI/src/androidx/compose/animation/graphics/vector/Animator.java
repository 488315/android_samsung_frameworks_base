package androidx.compose.animation.graphics.vector;

import androidx.collection.MutableScatterMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Animator {
    public /* synthetic */ Animator(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x008b, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L48;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Configure(final androidx.compose.animation.core.Transition r22, final androidx.compose.animation.graphics.vector.StateVectorConfig r23, final int r24, androidx.compose.runtime.Composer r25, final int r26) {
        /*
            Method dump skipped, instructions count: 610
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.graphics.vector.Animator.Configure(androidx.compose.animation.core.Transition, androidx.compose.animation.graphics.vector.StateVectorConfig, int, androidx.compose.runtime.Composer, int):void");
    }

    public abstract void collectPropertyValues(MutableScatterMap mutableScatterMap, int i, int i2);

    public abstract int getTotalDuration();

    private Animator() {
    }
}
