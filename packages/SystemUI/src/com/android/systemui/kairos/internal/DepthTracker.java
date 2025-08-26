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
import kotlin.jvm.internal.Intrinsics;

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
            SetBuilder setBuilderBuild = setBuilder4.build();
            SetBuilder setBuilder5 = new SetBuilder();
            setBuilder5.addAll(this.indirectAdditions);
            if (!this.snapshotIsIndirectRoot && this.dirty_isIndirectRoot) {
                setBuilder5.add((MuxDeferredNode) muxNode);
            }
            downstreamSet.adjustIndirectUpstream(schedulerImpl, i7, i8, setBuilderBuild, setBuilder5.build());
        }
        reset();
    }

    public final boolean isDirty() {
        return this.snapshotIsDirect ? (this.dirty_depthIsDirect && this.snapshotDirectDepth == this.dirty_directDepth) ? false : true : this.snapshotIsIndirectRoot ? this.dirty_depthIsDirect || !this.dirty_isIndirectRoot : (!this.dirty_depthIsDirect && !this.dirty_isIndirectRoot && this.snapshotIndirectDepth == this.dirty_indirectDepth && this.indirectAdditions.isEmpty() && this.indirectRemovals.isEmpty()) ? false : true;
    }

    public final boolean recalcDepth() {
        Map.Entry entryLastEntry = this.dirty_directUpstreamDepths.lastEntry();
        int iIntValue = entryLastEntry != null ? ((Integer) entryLastEntry.getKey()).intValue() + 1 : 0;
        boolean z = !this.dirty_directUpstreamDepths.isEmpty();
        boolean z2 = this.dirty_depthIsDirect != z;
        this.dirty_depthIsDirect = z;
        boolean z3 = iIntValue != this.dirty_directDepth;
        this.dirty_directDepth = iIntValue;
        return z3 | z2;
    }

    public final boolean recalcIndirDepth() {
        Map.Entry entryLastEntry = this.dirty_indirectUpstreamDepths.lastEntry();
        boolean z = false;
        int iIntValue = entryLastEntry != null ? ((Integer) entryLastEntry.getKey()).intValue() + 1 : 0;
        if (!this.dirty_depthIsDirect && !this.dirty_isIndirectRoot && iIntValue != this.dirty_indirectDepth) {
            z = true;
        }
        this.dirty_indirectDepth = iIntValue;
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
            if (((Boolean) schedulerImpl.enqueue.mo781invoke(muxNode)).booleanValue()) {
                schedulerImpl.scheduledQ.add(new Pair(Integer.valueOf(i), muxNode));
                return;
            }
            return;
        }
        int i2 = this.dirty_indirectDepth - 2147483648;
        if (((Boolean) schedulerImpl.enqueue.mo781invoke(muxNode)).booleanValue()) {
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
        StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("DepthTracker(sIsDirect=", i, ", sDirectDepth=", z, ", sIndirectDepth=");
        sbM.append(i2);
        sbM.append(", sIndirectRoots=");
        sbM.append(set);
        sbM.append(", dIsIndirectRoot=");
        sbM.append(z2);
        sbM.append(", dDirectDepths=");
        sbM.append(treeMap);
        sbM.append(", dIndirectDepths=");
        sbM.append(treeMap2);
        sbM.append(", dIndirectRoots=");
        sbM.append(bag);
        sbM.append(")");
        return sbM.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateIndirectRoots(Set set, Set set2, MuxDeferredNode muxDeferredNode) {
        boolean z;
        boolean z2;
        if (set != null) {
            Bag bag = this.dirty_indirectUpstreamRoots;
            bag.getClass();
            HashSet hashSet = new HashSet();
            for (Object obj : set) {
                if (!Intrinsics.areEqual(obj, muxDeferredNode) && bag.add(obj)) {
                    hashSet.add(obj);
                }
            }
            if (hashSet.isEmpty()) {
                hashSet = null;
            }
            if (hashSet != null) {
                HashSet hashSet2 = this.indirectAdditions;
                HashSet hashSet3 = this.indirectRemovals;
                HashSet hashSet4 = new HashSet();
                for (Object obj2 : hashSet) {
                    if (!hashSet3.add(obj2)) {
                        hashSet4.add(obj2);
                    }
                }
                hashSet2.addAll(hashSet4);
                z = true;
            } else {
                z = false;
            }
        }
        if (set2 != null) {
            Bag bag2 = this.dirty_indirectUpstreamRoots;
            bag2.getClass();
            HashSet hashSet5 = new HashSet();
            for (Object obj3 : set2) {
                if (bag2.remove(obj3)) {
                    hashSet5.add(obj3);
                }
            }
            HashSet hashSet6 = hashSet5.isEmpty() ? null : hashSet5;
            if (hashSet6 != null) {
                HashSet hashSet7 = this.indirectRemovals;
                HashSet hashSet8 = this.indirectAdditions;
                HashSet hashSet9 = new HashSet();
                for (Object obj4 : hashSet6) {
                    if (!hashSet8.add(obj4)) {
                        hashSet9.add(obj4);
                    }
                }
                hashSet7.addAll(hashSet9);
                z2 = true;
            } else {
                z2 = false;
            }
        }
        return !this.dirty_depthIsDirect && (z || z2);
    }
}
