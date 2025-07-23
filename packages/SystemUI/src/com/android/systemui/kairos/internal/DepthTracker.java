package com.android.systemui.kairos.internal;

import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.util.Bag;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.SetBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DepthTracker {
    public volatile int dirty_directDepth;
    public volatile int dirty_indirectDepth;
    public volatile boolean dirty_isIndirectRoot;
    public volatile int snapshotDirectDepth;
    public volatile int snapshotIndirectDepth;
    public volatile boolean snapshotIsIndirectRoot;
    public volatile boolean snapshotIsDirect = true;
    public final HashSet _snapshotIndirectRoots = new HashSet();
    public final HashSet indirectAdditions = new HashSet();
    public final HashSet indirectRemovals = new HashSet();
    public final TreeMap dirty_directUpstreamDepths = new TreeMap();
    public final TreeMap dirty_indirectUpstreamDepths = new TreeMap();
    public final Bag dirty_indirectUpstreamRoots = new Bag();
    public volatile boolean dirty_depthIsDirect = true;

    public static /* synthetic */ boolean updateIndirectRoots$default(DepthTracker depthTracker, Set set, Set set2, MuxDeferredNode muxDeferredNode, int i) {
        if ((i & 1) != 0) {
            set = null;
        }
        if ((i & 2) != 0) {
            set2 = null;
        }
        if ((i & 4) != 0) {
            muxDeferredNode = null;
        }
        return depthTracker.updateIndirectRoots(set, set2, muxDeferredNode);
    }

    public final boolean addDirectUpstream(int i, Integer num) {
        if (num != null) {
            this.dirty_directUpstreamDepths.compute(num, new GraphKt$sam$java_util_function_BiFunction$0(new DepthTracker$$ExternalSyntheticLambda0(4)));
        }
        this.dirty_directUpstreamDepths.compute(Integer.valueOf(i), new GraphKt$sam$java_util_function_BiFunction$0(new DepthTracker$$ExternalSyntheticLambda0(5)));
        return recalcDepth();
    }

    public final boolean addIndirectUpstream(int i, Integer num) {
        if (num != null && num.intValue() == i) {
            return false;
        }
        if (num != null) {
            this.dirty_indirectUpstreamDepths.compute(num, new GraphKt$sam$java_util_function_BiFunction$0(new DepthTracker$$ExternalSyntheticLambda0(0)));
        }
        this.dirty_indirectUpstreamDepths.compute(Integer.valueOf(i), new GraphKt$sam$java_util_function_BiFunction$0(new DepthTracker$$ExternalSyntheticLambda0(1)));
        return recalcIndirDepth();
    }

    public final void applyChanges(SchedulerImpl schedulerImpl, DownstreamSet downstreamSet, MuxNode muxNode) {
        if (this.dirty_depthIsDirect) {
            if (this.snapshotIsDirect) {
                int i = this.snapshotDirectDepth;
                int i2 = this.dirty_directDepth;
                Iterator it = downstreamSet.nodes.iterator();
                while (it.hasNext()) {
                    ((SchedulableNode) it.next()).adjustDirectUpstream(schedulerImpl, i, i2);
                }
            } else {
                int i3 = this.snapshotIndirectDepth;
                SetBuilder setBuilder = new SetBuilder();
                setBuilder.addAll(CollectionsKt___CollectionsKt.toSet(this._snapshotIndirectRoots));
                if (this.snapshotIsIndirectRoot) {
                    setBuilder.add((MuxDeferredNode) muxNode);
                }
                Unit unit = Unit.INSTANCE;
                downstreamSet.moveIndirectUpstreamToDirect(i3, this.dirty_directDepth, schedulerImpl, setBuilder.build());
            }
        } else if (this.dirty_indirectUpstreamRoots.$$delegate_0.isEmpty() && !this.dirty_isIndirectRoot) {
            muxNode.lifecycle.lifecycleState = MuxLifecycleState.Dead.INSTANCE;
            if (this.snapshotIsDirect) {
                downstreamSet.removeDirectUpstream(schedulerImpl, this.snapshotDirectDepth);
            } else {
                int i4 = this.snapshotIndirectDepth;
                SetBuilder setBuilder2 = new SetBuilder();
                setBuilder2.addAll(CollectionsKt___CollectionsKt.toSet(this._snapshotIndirectRoots));
                if (this.snapshotIsIndirectRoot) {
                    setBuilder2.add((MuxDeferredNode) muxNode);
                }
                Unit unit2 = Unit.INSTANCE;
                downstreamSet.removeIndirectUpstream(schedulerImpl, i4, setBuilder2.build());
            }
        } else if (this.snapshotIsDirect) {
            int i5 = this.snapshotDirectDepth;
            int i6 = this.dirty_indirectDepth;
            SetBuilder setBuilder3 = new SetBuilder();
            setBuilder3.addAll(this.dirty_indirectUpstreamRoots);
            if (this.dirty_isIndirectRoot) {
                setBuilder3.add((MuxDeferredNode) muxNode);
            }
            Unit unit3 = Unit.INSTANCE;
            downstreamSet.moveDirectUpstreamToIndirect(i5, i6, schedulerImpl, setBuilder3.build());
        } else {
            int i7 = this.snapshotIndirectDepth;
            int i8 = this.dirty_indirectDepth;
            SetBuilder setBuilder4 = new SetBuilder();
            setBuilder4.addAll(this.indirectRemovals);
            if (this.snapshotIsIndirectRoot && !this.dirty_isIndirectRoot) {
                setBuilder4.add((MuxDeferredNode) muxNode);
            }
            Unit unit4 = Unit.INSTANCE;
            SetBuilder build = setBuilder4.build();
            SetBuilder setBuilder5 = new SetBuilder();
            setBuilder5.addAll(this.indirectAdditions);
            if (!this.snapshotIsIndirectRoot && this.dirty_isIndirectRoot) {
                setBuilder5.add((MuxDeferredNode) muxNode);
            }
            downstreamSet.adjustIndirectUpstream(schedulerImpl, i7, i8, build, setBuilder5.build());
        }
        reset();
    }

    public final boolean isDirty() {
        return this.snapshotIsDirect ? (this.dirty_depthIsDirect && this.snapshotDirectDepth == this.dirty_directDepth) ? false : true : this.snapshotIsIndirectRoot ? this.dirty_depthIsDirect || !this.dirty_isIndirectRoot : (!this.dirty_depthIsDirect && !this.dirty_isIndirectRoot && this.snapshotIndirectDepth == this.dirty_indirectDepth && this.indirectAdditions.isEmpty() && this.indirectRemovals.isEmpty()) ? false : true;
    }

    public final boolean recalcDepth() {
        Map.Entry lastEntry = this.dirty_directUpstreamDepths.lastEntry();
        int intValue = lastEntry != null ? ((Integer) lastEntry.getKey()).intValue() + 1 : 0;
        boolean z = !this.dirty_directUpstreamDepths.isEmpty();
        boolean z2 = this.dirty_depthIsDirect != z;
        this.dirty_depthIsDirect = z;
        boolean z3 = intValue != this.dirty_directDepth;
        this.dirty_directDepth = intValue;
        return z3 | z2;
    }

    public final boolean recalcIndirDepth() {
        Map.Entry lastEntry = this.dirty_indirectUpstreamDepths.lastEntry();
        boolean z = false;
        int intValue = lastEntry != null ? ((Integer) lastEntry.getKey()).intValue() + 1 : 0;
        if (!this.dirty_depthIsDirect && !this.dirty_isIndirectRoot && intValue != this.dirty_indirectDepth) {
            z = true;
        }
        this.dirty_indirectDepth = intValue;
        return z;
    }

    public final boolean removeDirectUpstream(int i) {
        this.dirty_directUpstreamDepths.compute(Integer.valueOf(i), new GraphKt$sam$java_util_function_BiFunction$0(new DepthTracker$$ExternalSyntheticLambda0(2)));
        return recalcDepth();
    }

    public final boolean removeIndirectUpstream(int i) {
        this.dirty_indirectUpstreamDepths.compute(Integer.valueOf(i), new GraphKt$sam$java_util_function_BiFunction$0(new DepthTracker$$ExternalSyntheticLambda0(3)));
        return recalcIndirDepth();
    }

    public final void reset() {
        this.snapshotIsDirect = !this.dirty_directUpstreamDepths.isEmpty();
        this.snapshotDirectDepth = this.dirty_directDepth;
        this.snapshotIndirectDepth = this.dirty_indirectDepth;
        this.snapshotIsIndirectRoot = this.dirty_isIndirectRoot;
        if (!this.indirectAdditions.isEmpty() || !this.indirectRemovals.isEmpty()) {
            this._snapshotIndirectRoots.clear();
            this._snapshotIndirectRoots.addAll(this.dirty_indirectUpstreamRoots);
        }
        this.indirectAdditions.clear();
        this.indirectRemovals.clear();
    }

    public final void schedule(SchedulerImpl schedulerImpl, MuxNode muxNode) {
        if (this.dirty_depthIsDirect) {
            int i = this.dirty_directDepth;
            if (((Boolean) schedulerImpl.enqueue.mo779invoke(muxNode)).booleanValue()) {
                schedulerImpl.scheduledQ.add(new Pair(Integer.valueOf(i), muxNode));
                return;
            }
            return;
        }
        int i2 = this.dirty_indirectDepth - 2147483648;
        if (((Boolean) schedulerImpl.enqueue.mo779invoke(muxNode)).booleanValue()) {
            schedulerImpl.scheduledQ.add(new Pair(Integer.valueOf(i2), muxNode));
        }
    }

    public final boolean setIsIndirectRoot(boolean z) {
        if (z == this.dirty_isIndirectRoot) {
            return false;
        }
        this.dirty_isIndirectRoot = z;
        return !this.dirty_depthIsDirect;
    }

    public final String toString() {
        boolean z = this.snapshotIsDirect;
        int i = this.snapshotDirectDepth;
        int i2 = this.snapshotIndirectDepth;
        Set set = CollectionsKt___CollectionsKt.toSet(this._snapshotIndirectRoots);
        boolean z2 = this.dirty_isIndirectRoot;
        TreeMap treeMap = this.dirty_directUpstreamDepths;
        TreeMap treeMap2 = this.dirty_indirectUpstreamDepths;
        Bag bag = this.dirty_indirectUpstreamRoots;
        StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("DepthTracker(sIsDirect=", i, ", sDirectDepth=", z, ", sIndirectDepth=");
        m.append(i2);
        m.append(", sIndirectRoots=");
        m.append(set);
        m.append(", dIsIndirectRoot=");
        m.append(z2);
        m.append(", dDirectDepths=");
        m.append(treeMap);
        m.append(", dIndirectDepths=");
        m.append(treeMap2);
        m.append(", dIndirectRoots=");
        m.append(bag);
        m.append(")");
        return m.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ba A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateIndirectRoots(java.util.Set r8, java.util.Set r9, com.android.systemui.kairos.internal.MuxDeferredNode r10) {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            r2 = 1
            if (r8 == 0) goto L5e
            com.android.systemui.kairos.internal.util.Bag r3 = r7.dirty_indirectUpstreamRoots
            java.lang.Iterable r8 = (java.lang.Iterable) r8
            r3.getClass()
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            java.util.Iterator r8 = r8.iterator()
        L15:
            boolean r5 = r8.hasNext()
            if (r5 == 0) goto L2f
            java.lang.Object r5 = r8.next()
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r10)
            if (r6 != 0) goto L15
            boolean r6 = r3.add(r5)
            if (r6 == 0) goto L15
            r4.add(r5)
            goto L15
        L2f:
            boolean r8 = r4.isEmpty()
            if (r8 == 0) goto L36
            r4 = r0
        L36:
            if (r4 == 0) goto L5e
            java.util.HashSet r8 = r7.indirectAdditions
            java.util.HashSet r10 = r7.indirectRemovals
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.Iterator r4 = r4.iterator()
        L45:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L59
            java.lang.Object r5 = r4.next()
            boolean r6 = r10.add(r5)
            if (r6 != 0) goto L45
            r3.add(r5)
            goto L45
        L59:
            r8.addAll(r3)
            r8 = r2
            goto L5f
        L5e:
            r8 = r1
        L5f:
            if (r9 == 0) goto Lb5
            com.android.systemui.kairos.internal.util.Bag r10 = r7.dirty_indirectUpstreamRoots
            java.util.Collection r9 = (java.util.Collection) r9
            r10.getClass()
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.Iterator r9 = r9.iterator()
        L71:
            boolean r4 = r9.hasNext()
            if (r4 == 0) goto L85
            java.lang.Object r4 = r9.next()
            boolean r5 = r10.remove(r4)
            if (r5 == 0) goto L71
            r3.add(r4)
            goto L71
        L85:
            boolean r9 = r3.isEmpty()
            if (r9 == 0) goto L8c
            goto L8d
        L8c:
            r0 = r3
        L8d:
            if (r0 == 0) goto Lb5
            java.util.HashSet r9 = r7.indirectRemovals
            java.util.HashSet r10 = r7.indirectAdditions
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.Iterator r0 = r0.iterator()
        L9c:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto Lb0
            java.lang.Object r4 = r0.next()
            boolean r5 = r10.add(r4)
            if (r5 != 0) goto L9c
            r3.add(r4)
            goto L9c
        Lb0:
            r9.addAll(r3)
            r9 = r2
            goto Lb6
        Lb5:
            r9 = r1
        Lb6:
            boolean r7 = r7.dirty_depthIsDirect
            if (r7 != 0) goto Lbf
            if (r8 != 0) goto Lbe
            if (r9 == 0) goto Lbf
        Lbe:
            return r2
        Lbf:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.DepthTracker.updateIndirectRoots(java.util.Set, java.util.Set, com.android.systemui.kairos.internal.MuxDeferredNode):boolean");
    }
}
