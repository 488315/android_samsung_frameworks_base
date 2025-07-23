package androidx.compose.ui.layout;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.ViewGroup;
import androidx.collection.IntSetKt;
import androidx.collection.MutableIntSet;
import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.runtime.ComposeNodeLifecycleCallback;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.CompositionImpl;
import androidx.compose.runtime.CompositionKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.ReusableComposition;
import androidx.compose.runtime.SlotReader;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.changelist.ChangeList;
import androidx.compose.runtime.changelist.ComposerChangeListWriter;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.LayoutNodeSubcompositionsState;
import androidx.compose.ui.layout.SubcomposeLayoutState;
import androidx.compose.ui.layout.SubcomposeSlotReusePolicy;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.LayoutNodeKt;
import androidx.compose.ui.node.LayoutNodeLayoutDelegate;
import androidx.compose.ui.node.LookaheadDelegate;
import androidx.compose.ui.node.LookaheadPassDelegate;
import androidx.compose.ui.node.MeasurePassDelegate;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.node.UiApplier;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.Wrapper_androidKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutNodeSubcompositionsState implements ComposeNodeLifecycleCallback {
    public CompositionContext compositionContext;
    public int currentApproachIndex;
    public int currentIndex;
    public int precomposedCount;
    public int reusableCount;
    public final LayoutNode root;
    public SubcomposeSlotReusePolicy slotReusePolicy;
    public final MutableScatterMap nodeToNodeState = ScatterMapKt.mutableScatterMapOf();
    public final MutableScatterMap slotIdToNode = ScatterMapKt.mutableScatterMapOf();
    public final Scope scope = new Scope();
    public final ApproachMeasureScopeImpl approachMeasureScope = new ApproachMeasureScopeImpl();
    public final MutableScatterMap precomposeMap = ScatterMapKt.mutableScatterMapOf();
    public final SubcomposeSlotReusePolicy.SlotIdsSet reusableSlotIdsSet = new SubcomposeSlotReusePolicy.SlotIdsSet(null, 1, null);
    public final MutableScatterMap approachPrecomposeSlotHandleMap = ScatterMapKt.mutableScatterMapOf();
    public final MutableVector approachComposedSlotIds = new MutableVector(new Object[16], 0);
    public final String NoIntrinsicsMessage = "Asking for intrinsic measurements of SubcomposeLayout layouts is not supported. This includes components that are built on top of SubcomposeLayout, such as lazy lists, BoxWithConstraints, TabRow, etc. To mitigate this:\n- if intrinsic measurements are used to achieve 'match parent' sizing, consider replacing the parent of the component with a custom layout which controls the order in which children are measured, making intrinsic measurement not needed\n- adding a size modifier to the component, in order to fast return the queried intrinsic measurement.";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class ApproachMeasureScopeImpl implements SubcomposeMeasureScope, MeasureScope {
        public final /* synthetic */ Scope $$delegate_0;

        public ApproachMeasureScopeImpl() {
            this.$$delegate_0 = LayoutNodeSubcompositionsState.this.scope;
        }

        @Override // androidx.compose.ui.unit.Density
        public final float getDensity() {
            return this.$$delegate_0.density;
        }

        @Override // androidx.compose.ui.unit.FontScaling
        public final float getFontScale() {
            return this.$$delegate_0.fontScale;
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
        public final LayoutDirection getLayoutDirection() {
            return this.$$delegate_0.layoutDirection;
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
        public final boolean isLookingAhead() {
            return this.$$delegate_0.isLookingAhead();
        }

        @Override // androidx.compose.ui.layout.MeasureScope
        public final MeasureResult layout(int i, int i2, Map map, Function1 function1) {
            return this.$$delegate_0.layout(i, i2, map, function1);
        }

        @Override // androidx.compose.ui.layout.MeasureScope
        public final MeasureResult layout$1(int i, int i2, Map map, Function1 function1) {
            return this.$$delegate_0.layout(i, i2, map, function1);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: roundToPx-0680j_4 */
        public final int mo51roundToPx0680j_4(float f) {
            return this.$$delegate_0.mo51roundToPx0680j_4(f);
        }

        @Override // androidx.compose.ui.layout.SubcomposeMeasureScope
        public final List subcompose(Object obj, Function2 function2) {
            LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = LayoutNodeSubcompositionsState.this;
            LayoutNode layoutNode = (LayoutNode) layoutNodeSubcompositionsState.slotIdToNode.get(obj);
            LayoutNode layoutNode2 = layoutNodeSubcompositionsState.root;
            if (layoutNode != null && layoutNode2.getFoldedChildren$ui_release().indexOf(layoutNode) < layoutNodeSubcompositionsState.currentIndex) {
                return layoutNode.getChildMeasurables$ui_release();
            }
            MutableVector mutableVector = layoutNodeSubcompositionsState.approachComposedSlotIds;
            if (mutableVector.size < layoutNodeSubcompositionsState.currentApproachIndex) {
                InlineClassHelperKt.throwIllegalArgumentException("Error: currentApproachIndex cannot be greater than the size of theapproachComposedSlotIds list.");
            }
            int i = mutableVector.size;
            int i2 = layoutNodeSubcompositionsState.currentApproachIndex;
            if (i == i2) {
                mutableVector.add(obj);
            } else {
                Object[] objArr = mutableVector.content;
                Object obj2 = objArr[i2];
                objArr[i2] = obj;
            }
            layoutNodeSubcompositionsState.currentApproachIndex++;
            MutableScatterMap mutableScatterMap = layoutNodeSubcompositionsState.precomposeMap;
            if (mutableScatterMap.contains(obj)) {
                LayoutNode layoutNode3 = (LayoutNode) mutableScatterMap.get(obj);
                NodeState nodeState = layoutNode3 != null ? (NodeState) layoutNodeSubcompositionsState.nodeToNodeState.get(layoutNode3) : null;
                if (nodeState != null && nodeState.forceRecompose) {
                    layoutNodeSubcompositionsState.subcompose(layoutNode3, obj, function2);
                }
            } else {
                layoutNodeSubcompositionsState.approachPrecomposeSlotHandleMap.set(obj, layoutNodeSubcompositionsState.precompose(obj, function2));
                if (layoutNode2.layoutDelegate.layoutState == LayoutNode.LayoutState.LayingOut) {
                    layoutNode2.requestLookaheadRelayout$ui_release(true);
                } else {
                    LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode2, true, 6);
                }
            }
            LayoutNode layoutNode4 = (LayoutNode) mutableScatterMap.get(obj);
            if (layoutNode4 == null) {
                return EmptyList.INSTANCE;
            }
            List childDelegates$ui_release = layoutNode4.layoutDelegate.measurePassDelegate.getChildDelegates$ui_release();
            int size = childDelegates$ui_release.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((MeasurePassDelegate) childDelegates$ui_release.get(i3)).layoutNodeLayoutDelegate.detachedFromParentLookaheadPass = true;
            }
            return childDelegates$ui_release;
        }

        @Override // androidx.compose.ui.unit.FontScaling
        /* renamed from: toDp-GaN1DYA */
        public final float mo52toDpGaN1DYA(long j) {
            return this.$$delegate_0.mo52toDpGaN1DYA(j);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toDp-u2uoSUM */
        public final float mo53toDpu2uoSUM(float f) {
            return this.$$delegate_0.mo53toDpu2uoSUM(f);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toDpSize-k-rfVVM */
        public final long mo55toDpSizekrfVVM(long j) {
            return this.$$delegate_0.mo55toDpSizekrfVVM(j);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toPx--R2X_6o */
        public final float mo56toPxR2X_6o(long j) {
            return this.$$delegate_0.mo56toPxR2X_6o(j);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toPx-0680j_4 */
        public final float mo57toPx0680j_4(float f) {
            return this.$$delegate_0.getDensity() * f;
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toSize-XkaWNTQ */
        public final long mo58toSizeXkaWNTQ(long j) {
            return this.$$delegate_0.mo58toSizeXkaWNTQ(j);
        }

        @Override // androidx.compose.ui.unit.FontScaling
        /* renamed from: toSp-0xMU5do */
        public final long mo59toSp0xMU5do(float f) {
            return this.$$delegate_0.mo59toSp0xMU5do(f);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toSp-kPz2Gy4 */
        public final long mo60toSpkPz2Gy4(float f) {
            return this.$$delegate_0.mo60toSpkPz2Gy4(f);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toDp-u2uoSUM */
        public final float mo54toDpu2uoSUM(int i) {
            return this.$$delegate_0.mo54toDpu2uoSUM(i);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Scope implements SubcomposeMeasureScope {
        public float density;
        public float fontScale;
        public LayoutDirection layoutDirection = LayoutDirection.Rtl;

        public Scope() {
        }

        @Override // androidx.compose.ui.unit.Density
        public final float getDensity() {
            return this.density;
        }

        @Override // androidx.compose.ui.unit.FontScaling
        public final float getFontScale() {
            return this.fontScale;
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
        public final LayoutDirection getLayoutDirection() {
            return this.layoutDirection;
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
        public final boolean isLookingAhead() {
            LayoutNode.LayoutState layoutState = LayoutNodeSubcompositionsState.this.root.layoutDelegate.layoutState;
            return layoutState == LayoutNode.LayoutState.LookaheadLayingOut || layoutState == LayoutNode.LayoutState.LookaheadMeasuring;
        }

        @Override // androidx.compose.ui.layout.MeasureScope
        public final MeasureResult layout(final int i, final int i2, final Map map, final Function1 function1) {
            if ((i & (-16777216)) != 0 || ((-16777216) & i2) != 0) {
                InlineClassHelperKt.throwIllegalStateException("Size(" + i + " x " + i2 + ") is out of range. Each dimension must be between 0 and 16777215.");
            }
            final LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = LayoutNodeSubcompositionsState.this;
            final Function1 function12 = null;
            return new MeasureResult() { // from class: androidx.compose.ui.layout.LayoutNodeSubcompositionsState$Scope$layout$1
                @Override // androidx.compose.ui.layout.MeasureResult
                public final Map getAlignmentLines() {
                    return map;
                }

                @Override // androidx.compose.ui.layout.MeasureResult
                public final int getHeight() {
                    return i2;
                }

                @Override // androidx.compose.ui.layout.MeasureResult
                public final Function1 getRulers() {
                    return function12;
                }

                @Override // androidx.compose.ui.layout.MeasureResult
                public final int getWidth() {
                    return i;
                }

                @Override // androidx.compose.ui.layout.MeasureResult
                public final void placeChildren() {
                    LookaheadDelegate lookaheadDelegate;
                    boolean isLookingAhead = this.isLookingAhead();
                    Function1 function13 = function1;
                    LayoutNodeSubcompositionsState layoutNodeSubcompositionsState2 = layoutNodeSubcompositionsState;
                    if (!isLookingAhead || (lookaheadDelegate = layoutNodeSubcompositionsState2.root.nodes.innerCoordinator.lookaheadDelegate) == null) {
                        function13.mo779invoke(layoutNodeSubcompositionsState2.root.nodes.innerCoordinator.placementScope);
                    } else {
                        function13.mo779invoke(lookaheadDelegate.placementScope);
                    }
                }
            };
        }

        @Override // androidx.compose.ui.layout.SubcomposeMeasureScope
        public final List subcompose(Object obj, Function2 function2) {
            LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = LayoutNodeSubcompositionsState.this;
            layoutNodeSubcompositionsState.makeSureStateIsConsistent();
            LayoutNode layoutNode = layoutNodeSubcompositionsState.root;
            LayoutNode.LayoutState layoutState = layoutNode.layoutDelegate.layoutState;
            LayoutNode.LayoutState layoutState2 = LayoutNode.LayoutState.Measuring;
            if (layoutState != layoutState2 && layoutState != LayoutNode.LayoutState.LayingOut && layoutState != LayoutNode.LayoutState.LookaheadMeasuring && layoutState != LayoutNode.LayoutState.LookaheadLayingOut) {
                InlineClassHelperKt.throwIllegalStateException("subcompose can only be used inside the measure or layout blocks");
            }
            MutableScatterMap mutableScatterMap = layoutNodeSubcompositionsState.slotIdToNode;
            Object obj2 = mutableScatterMap.get(obj);
            if (obj2 == null) {
                obj2 = (LayoutNode) layoutNodeSubcompositionsState.precomposeMap.remove(obj);
                if (obj2 != null) {
                    if (layoutNodeSubcompositionsState.precomposedCount <= 0) {
                        InlineClassHelperKt.throwIllegalStateException("Check failed.");
                    }
                    layoutNodeSubcompositionsState.precomposedCount--;
                } else {
                    obj2 = layoutNodeSubcompositionsState.takeNodeFromReusables(obj);
                    if (obj2 == null) {
                        int i = layoutNodeSubcompositionsState.currentIndex;
                        LayoutNode layoutNode2 = new LayoutNode(true, 0, 2, null);
                        layoutNode.ignoreRemeasureRequests = true;
                        layoutNode.insertAt$ui_release(i, layoutNode2);
                        layoutNode.ignoreRemeasureRequests = false;
                        obj2 = layoutNode2;
                    }
                }
                mutableScatterMap.set(obj, obj2);
            }
            LayoutNode layoutNode3 = (LayoutNode) obj2;
            if (CollectionsKt___CollectionsKt.getOrNull(layoutNodeSubcompositionsState.currentIndex, layoutNode.getFoldedChildren$ui_release()) != layoutNode3) {
                int indexOf = layoutNode.getFoldedChildren$ui_release().indexOf(layoutNode3);
                if (indexOf < layoutNodeSubcompositionsState.currentIndex) {
                    InlineClassHelperKt.throwIllegalArgumentException("Key \"" + obj + "\" was already used. If you are using LazyColumn/Row please make sure you provide a unique key for each item.");
                }
                int i2 = layoutNodeSubcompositionsState.currentIndex;
                if (i2 != indexOf) {
                    layoutNode.ignoreRemeasureRequests = true;
                    layoutNode.move$ui_release(indexOf, i2, 1);
                    layoutNode.ignoreRemeasureRequests = false;
                }
            }
            layoutNodeSubcompositionsState.currentIndex++;
            layoutNodeSubcompositionsState.subcompose(layoutNode3, obj, function2);
            return (layoutState == layoutState2 || layoutState == LayoutNode.LayoutState.LayingOut) ? layoutNode3.getChildMeasurables$ui_release() : layoutNode3.getChildLookaheadMeasurables$ui_release();
        }
    }

    public LayoutNodeSubcompositionsState(LayoutNode layoutNode, SubcomposeSlotReusePolicy subcomposeSlotReusePolicy) {
        this.root = layoutNode;
        this.slotReusePolicy = subcomposeSlotReusePolicy;
    }

    public static ReusableComposition subcomposeInto(ReusableComposition reusableComposition, LayoutNode layoutNode, boolean z, CompositionContext compositionContext, ComposableLambdaImpl composableLambdaImpl) {
        if (reusableComposition == null || ((CompositionImpl) reusableComposition).disposed) {
            ViewGroup.LayoutParams layoutParams = Wrapper_androidKt.DefaultLayoutParams;
            UiApplier uiApplier = new UiApplier(layoutNode);
            Object obj = CompositionKt.PendingApplyNoModifications;
            reusableComposition = new CompositionImpl(compositionContext, uiApplier, null, 4, null);
        }
        if (!z) {
            ((CompositionImpl) reusableComposition).composeInitial(composableLambdaImpl);
            return reusableComposition;
        }
        CompositionImpl compositionImpl = (CompositionImpl) reusableComposition;
        ComposerImpl composerImpl = compositionImpl.composer;
        composerImpl.reusingGroup = 100;
        composerImpl.reusing = true;
        compositionImpl.composeInitial(composableLambdaImpl);
        if (composerImpl.isComposing || composerImpl.reusingGroup != 100) {
            PreconditionsKt.throwIllegalArgumentException("Cannot disable reuse from root if it was caused by other groups");
        }
        composerImpl.reusingGroup = -1;
        composerImpl.reusing = false;
        return reusableComposition;
    }

    public final void disposeOrReuseStartingFromIndex(int i) {
        boolean z = false;
        this.reusableCount = 0;
        LayoutNode layoutNode = this.root;
        List foldedChildren$ui_release = layoutNode.getFoldedChildren$ui_release();
        int size = (foldedChildren$ui_release.size() - this.precomposedCount) - 1;
        if (i <= size) {
            SubcomposeSlotReusePolicy.SlotIdsSet slotIdsSet = this.reusableSlotIdsSet;
            slotIdsSet.clear();
            MutableScatterMap mutableScatterMap = this.nodeToNodeState;
            if (i <= size) {
                int i2 = i;
                while (true) {
                    Object obj = mutableScatterMap.get((LayoutNode) foldedChildren$ui_release.get(i2));
                    obj.getClass();
                    slotIdsSet.set.add(((NodeState) obj).slotId);
                    if (i2 == size) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            this.slotReusePolicy.getSlotsToRetain(slotIdsSet);
            Snapshot.Companion.getClass();
            Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
            Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
            Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
            boolean z2 = false;
            while (size >= i) {
                try {
                    LayoutNode layoutNode2 = (LayoutNode) foldedChildren$ui_release.get(size);
                    Object obj2 = mutableScatterMap.get(layoutNode2);
                    obj2.getClass();
                    NodeState nodeState = (NodeState) obj2;
                    Object obj3 = nodeState.slotId;
                    if (slotIdsSet.set.contains(obj3)) {
                        this.reusableCount++;
                        if (((Boolean) ((SnapshotMutableStateImpl) nodeState.activeState).getValue()).booleanValue()) {
                            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode2.layoutDelegate;
                            MeasurePassDelegate measurePassDelegate = layoutNodeLayoutDelegate.measurePassDelegate;
                            LayoutNode.UsageByParent usageByParent = LayoutNode.UsageByParent.NotUsed;
                            measurePassDelegate.measuredByParent = usageByParent;
                            LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
                            if (lookaheadPassDelegate != null) {
                                lookaheadPassDelegate.measuredByParent = usageByParent;
                            }
                            ((SnapshotMutableStateImpl) nodeState.activeState).setValue(Boolean.FALSE);
                            z2 = true;
                        }
                    } else {
                        layoutNode.ignoreRemeasureRequests = true;
                        mutableScatterMap.remove(layoutNode2);
                        ReusableComposition reusableComposition = nodeState.composition;
                        if (reusableComposition != null) {
                            ((CompositionImpl) reusableComposition).dispose();
                        }
                        layoutNode.removeAt$ui_release(size, 1);
                        layoutNode.ignoreRemeasureRequests = false;
                    }
                    this.slotIdToNode.remove(obj3);
                    size--;
                } catch (Throwable th) {
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                    throw th;
                }
            }
            Unit unit = Unit.INSTANCE;
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
            z = z2;
        }
        if (z) {
            Snapshot.Companion.getClass();
            Snapshot.Companion.sendApplyNotifications();
        }
        makeSureStateIsConsistent();
    }

    public final void makeSureStateIsConsistent() {
        int size = this.root.getFoldedChildren$ui_release().size();
        MutableScatterMap mutableScatterMap = this.nodeToNodeState;
        if (!(mutableScatterMap._size == size)) {
            InlineClassHelperKt.throwIllegalArgumentException("Inconsistency between the count of nodes tracked by the state (" + mutableScatterMap._size + ") and the children count on the SubcomposeLayout (" + size + "). Are you trying to use the state of the disposed SubcomposeLayout?");
        }
        if (!((size - this.reusableCount) - this.precomposedCount >= 0)) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "Incorrect state. Total children ", ". Reusable children ");
            m.append(this.reusableCount);
            m.append(". Precomposed children ");
            m.append(this.precomposedCount);
            InlineClassHelperKt.throwIllegalArgumentException(m.toString());
        }
        MutableScatterMap mutableScatterMap2 = this.precomposeMap;
        if (mutableScatterMap2._size == this.precomposedCount) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Incorrect state. Precomposed children " + this.precomposedCount + ". Map size " + mutableScatterMap2._size);
    }

    public final void markActiveNodesAsReused(boolean z) {
        this.precomposedCount = 0;
        this.precomposeMap.clear();
        List foldedChildren$ui_release = this.root.getFoldedChildren$ui_release();
        int size = foldedChildren$ui_release.size();
        if (this.reusableCount != size) {
            this.reusableCount = size;
            Snapshot.Companion.getClass();
            Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
            Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
            Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
            for (int i = 0; i < size; i++) {
                try {
                    LayoutNode layoutNode = (LayoutNode) foldedChildren$ui_release.get(i);
                    NodeState nodeState = (NodeState) this.nodeToNodeState.get(layoutNode);
                    if (nodeState != null && ((Boolean) ((SnapshotMutableStateImpl) nodeState.activeState).getValue()).booleanValue()) {
                        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
                        MeasurePassDelegate measurePassDelegate = layoutNodeLayoutDelegate.measurePassDelegate;
                        LayoutNode.UsageByParent usageByParent = LayoutNode.UsageByParent.NotUsed;
                        measurePassDelegate.measuredByParent = usageByParent;
                        LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
                        if (lookaheadPassDelegate != null) {
                            lookaheadPassDelegate.measuredByParent = usageByParent;
                        }
                        if (z) {
                            ReusableComposition reusableComposition = nodeState.composition;
                            if (reusableComposition != null) {
                                ((CompositionImpl) reusableComposition).deactivate();
                            }
                            nodeState.activeState = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                        } else {
                            ((SnapshotMutableStateImpl) nodeState.activeState).setValue(Boolean.FALSE);
                        }
                        nodeState.slotId = SubcomposeLayoutKt.ReusedSlotId;
                    }
                } catch (Throwable th) {
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                    throw th;
                }
            }
            Unit unit = Unit.INSTANCE;
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
            this.slotIdToNode.clear();
        }
        makeSureStateIsConsistent();
    }

    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onDeactivate() {
        markActiveNodesAsReused(true);
    }

    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onRelease() {
        ReusableComposition reusableComposition;
        LayoutNode layoutNode = this.root;
        layoutNode.ignoreRemeasureRequests = true;
        MutableScatterMap mutableScatterMap = this.nodeToNodeState;
        Object[] objArr = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128 && (reusableComposition = ((NodeState) objArr[(i << 3) + i3]).composition) != null) {
                            ((CompositionImpl) reusableComposition).dispose();
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                } else {
                    i++;
                }
            }
        }
        layoutNode.removeAll$ui_release();
        layoutNode.ignoreRemeasureRequests = false;
        mutableScatterMap.clear();
        this.slotIdToNode.clear();
        this.precomposedCount = 0;
        this.reusableCount = 0;
        this.precomposeMap.clear();
        makeSureStateIsConsistent();
    }

    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onReuse() {
        markActiveNodesAsReused(false);
    }

    public final SubcomposeLayoutState.PrecomposedSlotHandle precompose(final Object obj, Function2 function2) {
        LayoutNode layoutNode = this.root;
        if (!layoutNode.isAttached()) {
            return new SubcomposeLayoutState.PrecomposedSlotHandle() { // from class: androidx.compose.ui.layout.LayoutNodeSubcompositionsState$precompose$1
                @Override // androidx.compose.ui.layout.SubcomposeLayoutState.PrecomposedSlotHandle
                public final void dispose() {
                }
            };
        }
        makeSureStateIsConsistent();
        if (!this.slotIdToNode.containsKey(obj)) {
            this.approachPrecomposeSlotHandleMap.remove(obj);
            MutableScatterMap mutableScatterMap = this.precomposeMap;
            Object obj2 = mutableScatterMap.get(obj);
            if (obj2 == null) {
                obj2 = takeNodeFromReusables(obj);
                if (obj2 != null) {
                    int indexOf = layoutNode.getFoldedChildren$ui_release().indexOf(obj2);
                    int size = layoutNode.getFoldedChildren$ui_release().size();
                    layoutNode.ignoreRemeasureRequests = true;
                    layoutNode.move$ui_release(indexOf, size, 1);
                    layoutNode.ignoreRemeasureRequests = false;
                    this.precomposedCount++;
                } else {
                    int size2 = layoutNode.getFoldedChildren$ui_release().size();
                    LayoutNode layoutNode2 = new LayoutNode(true, 0, 2, null);
                    layoutNode.ignoreRemeasureRequests = true;
                    layoutNode.insertAt$ui_release(size2, layoutNode2);
                    layoutNode.ignoreRemeasureRequests = false;
                    this.precomposedCount++;
                    obj2 = layoutNode2;
                }
                mutableScatterMap.set(obj, obj2);
            }
            subcompose((LayoutNode) obj2, obj, function2);
        }
        return new SubcomposeLayoutState.PrecomposedSlotHandle() { // from class: androidx.compose.ui.layout.LayoutNodeSubcompositionsState$precompose$2
            public final MutableIntSet hasPremeasured;

            {
                int[] iArr = IntSetKt.EmptyIntArray;
                this.hasPremeasured = new MutableIntSet(0, 1, null);
            }

            @Override // androidx.compose.ui.layout.SubcomposeLayoutState.PrecomposedSlotHandle
            public final void dispose() {
                LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = LayoutNodeSubcompositionsState.this;
                layoutNodeSubcompositionsState.makeSureStateIsConsistent();
                LayoutNode layoutNode3 = (LayoutNode) layoutNodeSubcompositionsState.precomposeMap.remove(obj);
                if (layoutNode3 != null) {
                    if (layoutNodeSubcompositionsState.precomposedCount <= 0) {
                        InlineClassHelperKt.throwIllegalStateException("No pre-composed items to dispose");
                    }
                    LayoutNode layoutNode4 = layoutNodeSubcompositionsState.root;
                    int indexOf2 = layoutNode4.getFoldedChildren$ui_release().indexOf(layoutNode3);
                    if (indexOf2 < layoutNode4.getFoldedChildren$ui_release().size() - layoutNodeSubcompositionsState.precomposedCount) {
                        InlineClassHelperKt.throwIllegalStateException("Item is not in pre-composed item range");
                    }
                    layoutNodeSubcompositionsState.reusableCount++;
                    layoutNodeSubcompositionsState.precomposedCount--;
                    int size3 = (layoutNode4.getFoldedChildren$ui_release().size() - layoutNodeSubcompositionsState.precomposedCount) - layoutNodeSubcompositionsState.reusableCount;
                    layoutNode4.ignoreRemeasureRequests = true;
                    layoutNode4.move$ui_release(indexOf2, size3, 1);
                    layoutNode4.ignoreRemeasureRequests = false;
                    layoutNodeSubcompositionsState.disposeOrReuseStartingFromIndex(size3);
                }
            }

            @Override // androidx.compose.ui.layout.SubcomposeLayoutState.PrecomposedSlotHandle
            public final int getPlaceablesCount() {
                LayoutNode layoutNode3 = (LayoutNode) LayoutNodeSubcompositionsState.this.precomposeMap.get(obj);
                if (layoutNode3 != null) {
                    return layoutNode3.getChildren$ui_release().size();
                }
                return 0;
            }

            @Override // androidx.compose.ui.layout.SubcomposeLayoutState.PrecomposedSlotHandle
            /* renamed from: getSize-YEO4UFw, reason: not valid java name */
            public final long mo620getSizeYEO4UFw(int i) {
                LayoutNode layoutNode3 = (LayoutNode) LayoutNodeSubcompositionsState.this.precomposeMap.get(obj);
                if (layoutNode3 != null && layoutNode3.isAttached()) {
                    int size3 = layoutNode3.getChildren$ui_release().size();
                    if (i < 0 || i >= size3) {
                        InlineClassHelperKt.throwIndexOutOfBoundsException("Index (" + i + ") is out of bound of [0, " + size3 + ')');
                    }
                    if (this.hasPremeasured.contains(i)) {
                        int i2 = ((LayoutNode) layoutNode3.getChildren$ui_release().get(i)).layoutDelegate.measurePassDelegate.width;
                        long j = (((LayoutNode) layoutNode3.getChildren$ui_release().get(i)).layoutDelegate.measurePassDelegate.height & 4294967295L) | (i2 << 32);
                        IntSize.Companion companion = IntSize.Companion;
                        return j;
                    }
                }
                IntSize.Companion.getClass();
                return 0L;
            }

            @Override // androidx.compose.ui.layout.SubcomposeLayoutState.PrecomposedSlotHandle
            /* renamed from: premeasure-0kLqBqw, reason: not valid java name */
            public final void mo621premeasure0kLqBqw(int i, long j) {
                LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = LayoutNodeSubcompositionsState.this;
                LayoutNode layoutNode3 = (LayoutNode) layoutNodeSubcompositionsState.precomposeMap.get(obj);
                if (layoutNode3 == null || !layoutNode3.isAttached()) {
                    return;
                }
                int size3 = layoutNode3.getChildren$ui_release().size();
                if (i < 0 || i >= size3) {
                    InlineClassHelperKt.throwIndexOutOfBoundsException("Index (" + i + ") is out of bound of [0, " + size3 + ')');
                }
                if (layoutNode3.isPlaced()) {
                    InlineClassHelperKt.throwIllegalArgumentException("Pre-measure called on node that is not placed");
                }
                LayoutNode layoutNode4 = layoutNodeSubcompositionsState.root;
                layoutNode4.ignoreRemeasureRequests = true;
                ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode3)).m694measureAndLayout0kLqBqw((LayoutNode) layoutNode3.getChildren$ui_release().get(i), j);
                layoutNode4.ignoreRemeasureRequests = false;
                this.hasPremeasured.add(i);
            }

            @Override // androidx.compose.ui.layout.SubcomposeLayoutState.PrecomposedSlotHandle
            public final void traverseDescendants(Function1 function1) {
                NodeChain nodeChain;
                Modifier.Node node;
                LayoutNode layoutNode3 = (LayoutNode) LayoutNodeSubcompositionsState.this.precomposeMap.get(obj);
                if (layoutNode3 == null || (nodeChain = layoutNode3.nodes) == null || (node = nodeChain.head) == null) {
                    return;
                }
                TraversableNodeKt.traverseDescendants(node, "androidx.compose.foundation.lazy.layout.TraversablePrefetchStateNode", function1);
            }
        };
    }

    public final void subcompose(LayoutNode layoutNode, Object obj, Function2 function2) {
        boolean z;
        MutableScatterMap mutableScatterMap = this.nodeToNodeState;
        Object obj2 = mutableScatterMap.get(layoutNode);
        if (obj2 == null) {
            ComposableSingletons$SubcomposeLayoutKt.INSTANCE.getClass();
            NodeState nodeState = new NodeState(obj, ComposableSingletons$SubcomposeLayoutKt.f19lambda1, null, 4, null);
            mutableScatterMap.set(layoutNode, nodeState);
            obj2 = nodeState;
        }
        final NodeState nodeState2 = (NodeState) obj2;
        ReusableComposition reusableComposition = nodeState2.composition;
        if (reusableComposition != null) {
            CompositionImpl compositionImpl = (CompositionImpl) reusableComposition;
            synchronized (compositionImpl.lock) {
                z = compositionImpl.invalidations._size > 0;
            }
        } else {
            z = true;
        }
        if (nodeState2.content != function2 || z || nodeState2.forceRecompose) {
            nodeState2.content = function2;
            Snapshot.Companion.getClass();
            Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
            Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
            Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
            try {
                LayoutNode layoutNode2 = this.root;
                layoutNode2.ignoreRemeasureRequests = true;
                final Function2 function22 = nodeState2.content;
                ReusableComposition reusableComposition2 = nodeState2.composition;
                CompositionContext compositionContext = this.compositionContext;
                if (compositionContext == null) {
                    InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("parent composition reference not set");
                    throw new KotlinNothingValueException();
                }
                nodeState2.composition = subcomposeInto(reusableComposition2, layoutNode, nodeState2.forceReuse, compositionContext, new ComposableLambdaImpl(-1750409193, true, new Function2() { // from class: androidx.compose.ui.layout.LayoutNodeSubcompositionsState$subcompose$3$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer = (Composer) obj3;
                        int intValue = ((Number) obj4).intValue();
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        if (composerImpl.shouldExecute(intValue & 1, (intValue & 3) != 2)) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.ui.layout.LayoutNodeSubcompositionsState.subcompose.<anonymous>.<anonymous>.<anonymous> (SubcomposeLayout.kt:532)");
                            }
                            Boolean bool = (Boolean) ((SnapshotMutableStateImpl) LayoutNodeSubcompositionsState.NodeState.this.activeState).getValue();
                            boolean booleanValue = bool.booleanValue();
                            Function2 function23 = function22;
                            composerImpl.startReusableGroup(bool);
                            boolean changed = composerImpl.changed(booleanValue);
                            if (booleanValue) {
                                function23.invoke(composerImpl, 0);
                            } else {
                                if (composerImpl.groupNodeCount != 0) {
                                    ComposerKt.composeImmediateRuntimeError("No nodes can be emitted before calling dactivateToEndGroup");
                                }
                                if (!composerImpl.inserting) {
                                    if (changed) {
                                        SlotReader slotReader = composerImpl.reader;
                                        int i = slotReader.currentGroup;
                                        int i2 = slotReader.currentEnd;
                                        ComposerChangeListWriter composerChangeListWriter = composerImpl.changeListWriter;
                                        composerChangeListWriter.getClass();
                                        composerChangeListWriter.realizeOperationLocation(false);
                                        ChangeList changeList = composerChangeListWriter.changeList;
                                        changeList.getClass();
                                        changeList.operations.pushOp(Operation.DeactivateCurrentGroup.INSTANCE);
                                        ComposerKt.access$removeRange(composerImpl.invalidations, i, i2);
                                        composerImpl.reader.skipToGroupEnd();
                                    } else {
                                        composerImpl.skipReaderToGroupEnd();
                                    }
                                }
                            }
                            if (composerImpl.reusing && composerImpl.reader.parent == composerImpl.reusingGroup) {
                                composerImpl.reusingGroup = -1;
                                composerImpl.reusing = false;
                            }
                            composerImpl.end(false);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else {
                            composerImpl.skipToGroupEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }));
                nodeState2.forceReuse = false;
                layoutNode2.ignoreRemeasureRequests = false;
                Unit unit = Unit.INSTANCE;
                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                nodeState2.forceRecompose = false;
            } catch (Throwable th) {
                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                throw th;
            }
        }
    }

    public final LayoutNode takeNodeFromReusables(Object obj) {
        MutableScatterMap mutableScatterMap;
        int i;
        if (this.reusableCount == 0) {
            return null;
        }
        LayoutNode layoutNode = this.root;
        List foldedChildren$ui_release = layoutNode.getFoldedChildren$ui_release();
        int size = foldedChildren$ui_release.size() - this.precomposedCount;
        int i2 = size - this.reusableCount;
        int i3 = size - 1;
        int i4 = i3;
        while (true) {
            mutableScatterMap = this.nodeToNodeState;
            if (i4 < i2) {
                i = -1;
                break;
            }
            Object obj2 = mutableScatterMap.get((LayoutNode) foldedChildren$ui_release.get(i4));
            obj2.getClass();
            if (Intrinsics.areEqual(((NodeState) obj2).slotId, obj)) {
                i = i4;
                break;
            }
            i4--;
        }
        if (i == -1) {
            while (i3 >= i2) {
                Object obj3 = mutableScatterMap.get((LayoutNode) foldedChildren$ui_release.get(i3));
                obj3.getClass();
                NodeState nodeState = (NodeState) obj3;
                Object obj4 = nodeState.slotId;
                if (obj4 == SubcomposeLayoutKt.ReusedSlotId || this.slotReusePolicy.areCompatible(obj, obj4)) {
                    nodeState.slotId = obj;
                    i4 = i3;
                    i = i4;
                    break;
                }
                i3--;
            }
            i4 = i3;
        }
        if (i == -1) {
            return null;
        }
        if (i4 != i2) {
            layoutNode.ignoreRemeasureRequests = true;
            layoutNode.move$ui_release(i4, i2, 1);
            layoutNode.ignoreRemeasureRequests = false;
        }
        this.reusableCount--;
        LayoutNode layoutNode2 = (LayoutNode) foldedChildren$ui_release.get(i2);
        Object obj5 = mutableScatterMap.get(layoutNode2);
        obj5.getClass();
        NodeState nodeState2 = (NodeState) obj5;
        nodeState2.activeState = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
        nodeState2.forceReuse = true;
        nodeState2.forceRecompose = true;
        return layoutNode2;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class NodeState {
        public MutableState activeState;
        public ReusableComposition composition;
        public Function2 content;
        public boolean forceRecompose;
        public boolean forceReuse;
        public Object slotId;

        public NodeState(Object obj, Function2 function2, ReusableComposition reusableComposition) {
            this.slotId = obj;
            this.content = function2;
            this.composition = reusableComposition;
            this.activeState = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
        }

        public /* synthetic */ NodeState(Object obj, Function2 function2, ReusableComposition reusableComposition, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(obj, function2, (i & 4) != 0 ? null : reusableComposition);
        }
    }
}
