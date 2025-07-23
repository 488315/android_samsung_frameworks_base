package androidx.compose.runtime;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.collection.MutableIntIntMap;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.GroupKind;
import androidx.compose.runtime.changelist.ChangeList;
import androidx.compose.runtime.changelist.ComposerChangeListWriter;
import androidx.compose.runtime.changelist.FixupList;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.changelist.Operations;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.collection.ScopeMap;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.PersistentCompositionLocalHashMap;
import androidx.compose.runtime.internal.PersistentCompositionLocalMapKt;
import androidx.compose.runtime.internal.Trace;
import androidx.compose.runtime.snapshots.SnapshotKt;
import androidx.compose.runtime.tooling.ComposeStackTraceBuilderKt;
import androidx.compose.runtime.tooling.CompositionErrorContextImpl;
import androidx.compose.runtime.tooling.CompositionErrorContextKt;
import androidx.compose.runtime.tooling.InspectionTablesKt;
import androidx.compose.runtime.tooling.ObjectLocation;
import com.android.systemui.compose.EnableCommand$enableCompositionTracing$1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposerImpl implements Composer {
    public CompositionDataImpl _compositionData;
    public final Set abandonSet;
    public final Applier applier;
    public final CoroutineContext applyCoroutineContext;
    public final ComposerChangeListWriter changeListWriter;
    public final ChangeList changes;
    public int childrenComposing;
    public final ControlledComposition composition;
    public int compositionToken;
    public int compoundKeyHash;
    public ChangeList deferredChanges;
    public final ComposerImpl$derivedStateObserver$1 derivedStateObserver;
    public final CompositionErrorContextImpl errorContext;
    public boolean forceRecomposeScopes;
    public int groupNodeCount;
    public Anchor insertAnchor;
    public FixupList insertFixups;
    public SlotTable insertTable;
    public boolean inserting;
    public final ArrayList invalidateStack;
    public boolean isComposing;
    public final ChangeList lateChanges;
    public int[] nodeCountOverrides;
    public MutableIntIntMap nodeCountVirtualOverrides;
    public boolean nodeExpected;
    public int nodeIndex;
    public final CompositionContext parentContext;
    public Pending pending;
    public PersistentCompositionLocalMap providerCache;
    public MutableIntObjectMap providerUpdates;
    public boolean providersInvalid;
    public int rGroupIndex;
    public SlotReader reader;
    public boolean reusing;
    public final SlotTable slotTable;
    public boolean sourceMarkersEnabled;
    public SlotWriter writer;
    public boolean writerHasAProvider;
    public final ArrayList pendingStack = new ArrayList();
    public final IntStack parentStateStack = new IntStack();
    public final List invalidations = new ArrayList();
    public final IntStack entersStack = new IntStack();
    public PersistentCompositionLocalMap rootProvider = PersistentCompositionLocalMapKt.persistentCompositionLocalHashMapOf();
    public final IntStack providersInvalidStack = new IntStack();
    public int reusingGroup = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CompositionContextImpl extends CompositionContext {
        public final boolean collectingParameterInformation;
        public final boolean collectingSourceInformation;
        public final Set composers = new LinkedHashSet();
        public final MutableState compositionLocalScope$delegate = new ParcelableSnapshotMutableState(PersistentCompositionLocalMapKt.persistentCompositionLocalHashMapOf(), ReferentialEqualityPolicy.INSTANCE);
        public final int compoundHashKey;
        public Set inspectionTables;
        public final CompositionObserverHolder observerHolder;

        public CompositionContextImpl(int i, boolean z, boolean z2, CompositionObserverHolder compositionObserverHolder) {
            this.compoundHashKey = i;
            this.collectingParameterInformation = z;
            this.collectingSourceInformation = z2;
            this.observerHolder = compositionObserverHolder;
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void composeInitial$runtime_release(CompositionImpl compositionImpl, ComposableLambdaImpl composableLambdaImpl) {
            ComposerImpl.this.parentContext.composeInitial$runtime_release(compositionImpl, composableLambdaImpl);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void deletedMovableContent$runtime_release(MovableContentStateReference movableContentStateReference) {
            ComposerImpl.this.parentContext.deletedMovableContent$runtime_release(movableContentStateReference);
        }

        public final void dispose() {
            if (this.composers.isEmpty()) {
                return;
            }
            Set set = this.inspectionTables;
            if (set != null) {
                for (ComposerImpl composerImpl : this.composers) {
                    Iterator it = ((HashSet) set).iterator();
                    while (it.hasNext()) {
                        ((Set) it.next()).remove(composerImpl.slotTable);
                    }
                }
            }
            this.composers.clear();
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void doneComposing$runtime_release() {
            ComposerImpl composerImpl = ComposerImpl.this;
            composerImpl.childrenComposing--;
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final boolean getCollectingCallByInformation$runtime_release() {
            return ComposerImpl.this.parentContext.getCollectingCallByInformation$runtime_release();
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final boolean getCollectingParameterInformation$runtime_release() {
            return this.collectingParameterInformation;
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final boolean getCollectingSourceInformation$runtime_release() {
            return this.collectingSourceInformation;
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final Composition getComposition$runtime_release() {
            return ComposerImpl.this.composition;
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final PersistentCompositionLocalMap getCompositionLocalScope$runtime_release() {
            return (PersistentCompositionLocalMap) ((SnapshotMutableStateImpl) this.compositionLocalScope$delegate).getValue();
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final int getCompoundHashKey$runtime_release() {
            return this.compoundHashKey;
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final CoroutineContext getEffectCoroutineContext() {
            return ComposerImpl.this.parentContext.getEffectCoroutineContext();
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void insertMovableContent$runtime_release(MovableContentStateReference movableContentStateReference) {
            ComposerImpl.this.parentContext.insertMovableContent$runtime_release(movableContentStateReference);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void invalidate$runtime_release(ControlledComposition controlledComposition) {
            ComposerImpl composerImpl = ComposerImpl.this;
            composerImpl.parentContext.invalidate$runtime_release(composerImpl.composition);
            composerImpl.parentContext.invalidate$runtime_release(controlledComposition);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void movableContentStateReleased$runtime_release(MovableContentStateReference movableContentStateReference, MovableContentState movableContentState, Applier applier) {
            ComposerImpl.this.parentContext.movableContentStateReleased$runtime_release(movableContentStateReference, movableContentState, applier);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final MovableContentState movableContentStateResolve$runtime_release(MovableContentStateReference movableContentStateReference) {
            return ComposerImpl.this.parentContext.movableContentStateResolve$runtime_release(movableContentStateReference);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void recordInspectionTable$runtime_release(Set set) {
            Set set2 = this.inspectionTables;
            if (set2 == null) {
                set2 = new HashSet();
                this.inspectionTables = set2;
            }
            set2.add(set);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void registerComposer$runtime_release(ComposerImpl composerImpl) {
            this.composers.add(composerImpl);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void reportRemovedComposition$runtime_release(ControlledComposition controlledComposition) {
            ComposerImpl.this.parentContext.reportRemovedComposition$runtime_release(controlledComposition);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void startComposing$runtime_release() {
            ComposerImpl.this.childrenComposing++;
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void unregisterComposer$runtime_release(ComposerImpl composerImpl) {
            Set set = this.inspectionTables;
            if (set != null) {
                Iterator it = ((HashSet) set).iterator();
                while (it.hasNext()) {
                    ((Set) it.next()).remove(composerImpl.slotTable);
                }
            }
            TypeIntrinsics.asMutableCollection(this.composers).remove(composerImpl);
        }

        @Override // androidx.compose.runtime.CompositionContext
        public final void unregisterComposition$runtime_release(CompositionImpl compositionImpl) {
            ComposerImpl.this.parentContext.unregisterComposition$runtime_release(compositionImpl);
        }
    }

    /* JADX WARN: Type inference failed for: r1v11, types: [androidx.compose.runtime.ComposerImpl$derivedStateObserver$1] */
    public ComposerImpl(Applier<?> applier, CompositionContext compositionContext, SlotTable slotTable, Set<RememberObserver> set, ChangeList changeList, ChangeList changeList2, ControlledComposition controlledComposition) {
        this.applier = applier;
        this.parentContext = compositionContext;
        this.slotTable = slotTable;
        this.abandonSet = set;
        this.changes = changeList;
        this.lateChanges = changeList2;
        this.composition = controlledComposition;
        int i = 0;
        int i2 = 1;
        this.sourceMarkersEnabled = compositionContext.getCollectingSourceInformation$runtime_release() || compositionContext.getCollectingCallByInformation$runtime_release();
        this.derivedStateObserver = new DerivedStateObserver() { // from class: androidx.compose.runtime.ComposerImpl$derivedStateObserver$1
            @Override // androidx.compose.runtime.DerivedStateObserver
            public final void done() {
                ComposerImpl composerImpl = ComposerImpl.this;
                composerImpl.childrenComposing--;
            }

            @Override // androidx.compose.runtime.DerivedStateObserver
            public final void start() {
                ComposerImpl.this.childrenComposing++;
            }
        };
        this.invalidateStack = new ArrayList();
        SlotReader openReader = slotTable.openReader();
        openReader.close();
        this.reader = openReader;
        SlotTable slotTable2 = new SlotTable();
        if (compositionContext.getCollectingSourceInformation$runtime_release()) {
            slotTable2.collectSourceInformation();
        }
        if (compositionContext.getCollectingCallByInformation$runtime_release()) {
            slotTable2.calledByMap = new MutableIntObjectMap(i, i2, null);
        }
        this.insertTable = slotTable2;
        SlotWriter openWriter = slotTable2.openWriter();
        openWriter.close(true);
        this.writer = openWriter;
        this.changeListWriter = new ComposerChangeListWriter(this, changeList);
        SlotReader openReader2 = this.insertTable.openReader();
        try {
            Anchor anchor = openReader2.anchor(0);
            openReader2.close();
            this.insertAnchor = anchor;
            this.insertFixups = new FixupList();
            this.errorContext = new CompositionErrorContextImpl(this);
            CoroutineContext effectCoroutineContext = compositionContext.getEffectCoroutineContext();
            CoroutineContext errorContext$runtime_release = getErrorContext$runtime_release();
            this.applyCoroutineContext = effectCoroutineContext.plus(errorContext$runtime_release == null ? EmptyCoroutineContext.INSTANCE : errorContext$runtime_release);
        } catch (Throwable th) {
            openReader2.close();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008e A[LOOP:1: B:21:0x008c->B:22:0x008e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007f A[EDGE_INSN: B:27:0x007f->B:20:0x007f BREAK  A[LOOP:0: B:14:0x0066->B:18:0x0079], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.runtime.MovableContentStateReference reportFreeMovableContent$movableContentReferenceFor(androidx.compose.runtime.ComposerImpl r13, int r14) {
        /*
            androidx.compose.runtime.SlotReader r0 = r13.reader
            int r0 = r0.groupKey(r14)
            androidx.compose.runtime.SlotReader r1 = r13.reader
            int[] r2 = r1.groups
            java.lang.Object r1 = r1.objectKey(r14, r2)
            r2 = 126665345(0x78cc281, float:2.1179178E-34)
            r3 = 0
            if (r0 != r2) goto Lb1
            boolean r0 = r1 instanceof androidx.compose.runtime.MovableContent
            if (r0 == 0) goto Lb1
            androidx.compose.runtime.SlotReader r0 = r13.reader
            boolean r0 = r0.containsMark(r14)
            if (r0 == 0) goto L30
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            reportFreeMovableContent$movableContentReferenceFor$traverseGroups(r13, r0, r14)
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L30
            r12 = r0
            goto L31
        L30:
            r12 = r3
        L31:
            androidx.compose.runtime.SlotReader r0 = r13.reader
            int[] r1 = r0.groups
            java.lang.Object r0 = r0.objectKey(r14, r1)
            r5 = r0
            androidx.compose.runtime.MovableContent r5 = (androidx.compose.runtime.MovableContent) r5
            androidx.compose.runtime.SlotReader r0 = r13.reader
            r1 = 0
            java.lang.Object r6 = r0.groupGet(r14, r1)
            androidx.compose.runtime.SlotReader r0 = r13.reader
            androidx.compose.runtime.Anchor r9 = r0.anchor(r14)
            androidx.compose.runtime.SlotReader r0 = r13.reader
            int[] r0 = r0.groups
            int r2 = r14 * 5
            int r2 = r2 + 3
            r0 = r0[r2]
            int r0 = r0 + r14
            java.util.List r2 = r13.invalidations
            com.android.systemui.compose.EnableCommand$enableCompositionTracing$1 r3 = androidx.compose.runtime.ComposerKt.compositionTracer
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            int r4 = androidx.compose.runtime.ComposerKt.findLocation(r14, r2)
            if (r4 >= 0) goto L66
            int r4 = r4 + 1
            int r4 = -r4
        L66:
            r7 = r2
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            int r8 = r7.size()
            if (r4 >= r8) goto L7f
            java.lang.Object r7 = r7.get(r4)
            androidx.compose.runtime.Invalidation r7 = (androidx.compose.runtime.Invalidation) r7
            int r8 = r7.location
            if (r8 >= r0) goto L7f
            r3.add(r7)
            int r4 = r4 + 1
            goto L66
        L7f:
            java.util.ArrayList r10 = new java.util.ArrayList
            int r0 = r3.size()
            r10.<init>(r0)
            int r0 = r3.size()
        L8c:
            if (r1 >= r0) goto La3
            java.lang.Object r2 = r3.get(r1)
            androidx.compose.runtime.Invalidation r2 = (androidx.compose.runtime.Invalidation) r2
            androidx.compose.runtime.RecomposeScopeImpl r4 = r2.scope
            java.lang.Object r2 = r2.instances
            kotlin.Pair r7 = new kotlin.Pair
            r7.<init>(r4, r2)
            r10.add(r7)
            int r1 = r1 + 1
            goto L8c
        La3:
            androidx.compose.runtime.MovableContentStateReference r4 = new androidx.compose.runtime.MovableContentStateReference
            androidx.compose.runtime.PersistentCompositionLocalMap r11 = r13.currentCompositionLocalScope(r14)
            androidx.compose.runtime.ControlledComposition r7 = r13.composition
            androidx.compose.runtime.SlotTable r8 = r13.slotTable
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12)
            return r4
        Lb1:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.reportFreeMovableContent$movableContentReferenceFor(androidx.compose.runtime.ComposerImpl, int):androidx.compose.runtime.MovableContentStateReference");
    }

    public static final void reportFreeMovableContent$movableContentReferenceFor$traverseGroups(ComposerImpl composerImpl, List list, int i) {
        int i2 = composerImpl.reader.groups[(i * 5) + 3] + i;
        int i3 = i + 1;
        while (i3 < i2) {
            if (composerImpl.reader.hasMark(i3)) {
                MovableContentStateReference reportFreeMovableContent$movableContentReferenceFor = reportFreeMovableContent$movableContentReferenceFor(composerImpl, i3);
                if (reportFreeMovableContent$movableContentReferenceFor != null) {
                    ((ArrayList) list).add(reportFreeMovableContent$movableContentReferenceFor);
                }
            } else if (composerImpl.reader.containsMark(i3)) {
                reportFreeMovableContent$movableContentReferenceFor$traverseGroups(composerImpl, list, i3);
            }
            i3 += composerImpl.reader.groups[(i3 * 5) + 3];
        }
    }

    public static final int reportFreeMovableContent$reportGroup(ComposerImpl composerImpl, int i, int i2, boolean z, int i3) {
        SlotReader slotReader = composerImpl.reader;
        boolean hasMark = slotReader.hasMark(i2);
        ComposerChangeListWriter composerChangeListWriter = composerImpl.changeListWriter;
        int[] iArr = slotReader.groups;
        if (hasMark) {
            int groupKey = slotReader.groupKey(i2);
            Object objectKey = slotReader.objectKey(i2, iArr);
            CompositionContext compositionContext = composerImpl.parentContext;
            if (groupKey == 126665345 && (objectKey instanceof MovableContent)) {
                MovableContentStateReference reportFreeMovableContent$movableContentReferenceFor = reportFreeMovableContent$movableContentReferenceFor(composerImpl, i2);
                if (reportFreeMovableContent$movableContentReferenceFor != null) {
                    compositionContext.deletedMovableContent$runtime_release(reportFreeMovableContent$movableContentReferenceFor);
                    composerChangeListWriter.recordSlotEditing();
                    ChangeList changeList = composerChangeListWriter.changeList;
                    changeList.getClass();
                    Operation.ReleaseMovableGroupAtCurrent releaseMovableGroupAtCurrent = Operation.ReleaseMovableGroupAtCurrent.INSTANCE;
                    Operations operations = changeList.operations;
                    operations.pushOp(releaseMovableGroupAtCurrent);
                    Operations.WriteScope.m340setObjectst7hvbck(operations, composerImpl.composition, composerImpl.parentContext, reportFreeMovableContent$movableContentReferenceFor);
                }
                if (!z || i2 == i) {
                    return slotReader.nodeCount(i2);
                }
                composerChangeListWriter.realizeNodeMovementOperations();
                composerChangeListWriter.pushPendingUpsAndDowns();
                ComposerImpl composerImpl2 = composerChangeListWriter.composer;
                int nodeCount = composerImpl2.reader.isNode(i2) ? 1 : composerImpl2.reader.nodeCount(i2);
                if (nodeCount > 0) {
                    composerChangeListWriter.removeNode(i3, nodeCount);
                }
                return 0;
            }
            if (groupKey == 206 && Intrinsics.areEqual(objectKey, ComposerKt.reference)) {
                Object groupGet = slotReader.groupGet(i2, 0);
                CompositionContextHolder compositionContextHolder = groupGet instanceof CompositionContextHolder ? (CompositionContextHolder) groupGet : null;
                if (compositionContextHolder != null) {
                    for (ComposerImpl composerImpl3 : compositionContextHolder.ref.composers) {
                        SlotTable slotTable = composerImpl3.slotTable;
                        if (slotTable.groupsSize > 0 && (slotTable.groups[1] & 67108864) != 0) {
                            CompositionImpl compositionImpl = (CompositionImpl) composerImpl3.composition;
                            synchronized (compositionImpl.lock) {
                                compositionImpl.drainPendingModificationsOutOfBandLocked();
                                MutableScatterMap mutableScatterMap = compositionImpl.invalidations;
                                compositionImpl.invalidations = ScatterMapKt.mutableScatterMapOf();
                                try {
                                    compositionImpl.composer.m332updateComposerInvalidationsRY85e9Y(mutableScatterMap);
                                    Unit unit = Unit.INSTANCE;
                                } finally {
                                }
                            }
                            ChangeList changeList2 = new ChangeList();
                            composerImpl3.deferredChanges = changeList2;
                            SlotReader openReader = composerImpl3.slotTable.openReader();
                            try {
                                composerImpl3.reader = openReader;
                                ComposerChangeListWriter composerChangeListWriter2 = composerImpl3.changeListWriter;
                                ChangeList changeList3 = composerChangeListWriter2.changeList;
                                try {
                                    composerChangeListWriter2.changeList = changeList2;
                                    composerImpl3.reportFreeMovableContent(0);
                                    ComposerChangeListWriter composerChangeListWriter3 = composerImpl3.changeListWriter;
                                    composerChangeListWriter3.pushPendingUpsAndDowns();
                                    if (composerChangeListWriter3.startedGroup) {
                                        ChangeList changeList4 = composerChangeListWriter3.changeList;
                                        changeList4.getClass();
                                        changeList4.operations.pushOp(Operation.SkipToEndOfCurrentGroup.INSTANCE);
                                        if (composerChangeListWriter3.startedGroup) {
                                            composerChangeListWriter3.realizeOperationLocation(false);
                                            composerChangeListWriter3.realizeOperationLocation(false);
                                            ChangeList changeList5 = composerChangeListWriter3.changeList;
                                            changeList5.getClass();
                                            changeList5.operations.pushOp(Operation.EndCurrentGroup.INSTANCE);
                                            composerChangeListWriter3.startedGroup = false;
                                        }
                                    }
                                } finally {
                                }
                            } finally {
                                openReader.close();
                            }
                        }
                        compositionContext.reportRemovedComposition$runtime_release(composerImpl3.composition);
                    }
                }
                return slotReader.nodeCount(i2);
            }
            if (!slotReader.isNode(i2)) {
                return slotReader.nodeCount(i2);
            }
        } else if (slotReader.containsMark(i2)) {
            int i4 = iArr[(i2 * 5) + 3] + i2;
            int i5 = 0;
            for (int i6 = i2 + 1; i6 < i4; i6 += iArr[(i6 * 5) + 3]) {
                boolean isNode = slotReader.isNode(i6);
                if (isNode) {
                    composerChangeListWriter.realizeNodeMovementOperations();
                    Object node = slotReader.node(i6);
                    composerChangeListWriter.realizeNodeMovementOperations();
                    composerChangeListWriter.pendingDownNodes.add(node);
                }
                i5 += reportFreeMovableContent$reportGroup(composerImpl, i, i6, isNode || z, isNode ? 0 : i3 + i5);
                if (isNode) {
                    composerChangeListWriter.realizeNodeMovementOperations();
                    composerChangeListWriter.moveUp();
                }
            }
            if (!slotReader.isNode(i2)) {
                return i5;
            }
        } else if (!slotReader.isNode(i2)) {
            return slotReader.nodeCount(i2);
        }
        return 1;
    }

    public final void abortRoot() {
        cleanUpCompose();
        this.pendingStack.clear();
        this.parentStateStack.tos = 0;
        this.entersStack.tos = 0;
        this.providersInvalidStack.tos = 0;
        this.providerUpdates = null;
        FixupList fixupList = this.insertFixups;
        fixupList.pendingOperations.clear();
        fixupList.operations.clear();
        this.compoundKeyHash = 0;
        this.childrenComposing = 0;
        this.nodeExpected = false;
        this.inserting = false;
        this.reusing = false;
        this.isComposing = false;
        this.reusingGroup = -1;
        SlotReader slotReader = this.reader;
        if (!slotReader.closed) {
            slotReader.close();
        }
        if (this.writer.closed) {
            return;
        }
        forceFreshInsertTable();
    }

    public final void apply(Object obj, Function2 function2) {
        if (this.inserting) {
            FixupList fixupList = this.insertFixups;
            fixupList.getClass();
            Operation.UpdateNode updateNode = Operation.UpdateNode.INSTANCE;
            Operations operations = fixupList.operations;
            operations.pushOp(updateNode);
            Operations.WriteScope.m338setObjectDKhxnng(operations, 0, obj);
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
            Operations.WriteScope.m338setObjectDKhxnng(operations, 1, function2);
            return;
        }
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        composerChangeListWriter.pushPendingUpsAndDowns();
        ChangeList changeList = composerChangeListWriter.changeList;
        changeList.getClass();
        Operation.UpdateNode updateNode2 = Operation.UpdateNode.INSTANCE;
        Operations operations2 = changeList.operations;
        operations2.pushOp(updateNode2);
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
        Operations.WriteScope.m339setObjects4uCC6AY(operations2, 0, obj, 1, function2);
    }

    public final boolean changed(Object obj) {
        if (Intrinsics.areEqual(nextSlot(), obj)) {
            return false;
        }
        updateValue(obj);
        return true;
    }

    public final boolean changedInstance(Object obj) {
        if (nextSlot() == obj) {
            return false;
        }
        updateValue(obj);
        return true;
    }

    public final void cleanUpCompose() {
        this.pending = null;
        this.nodeIndex = 0;
        this.groupNodeCount = 0;
        this.compoundKeyHash = 0;
        this.nodeExpected = false;
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        composerChangeListWriter.startedGroup = false;
        composerChangeListWriter.startedGroups.tos = 0;
        composerChangeListWriter.writersReaderDelta = 0;
        this.invalidateStack.clear();
        this.nodeCountOverrides = null;
        this.nodeCountVirtualOverrides = null;
    }

    public final Object consume(CompositionLocal compositionLocal) {
        return CompositionLocalMapKt.read(currentCompositionLocalScope(), compositionLocal);
    }

    public final void createNode(Function0 function0) {
        if (!this.nodeExpected) {
            ComposerKt.composeImmediateRuntimeError("A call to createNode(), emitNode() or useNode() expected was not expected");
        }
        this.nodeExpected = false;
        if (!this.inserting) {
            ComposerKt.composeImmediateRuntimeError("createNode() can only be called when inserting");
        }
        IntStack intStack = this.parentStateStack;
        int i = intStack.slots[intStack.tos - 1];
        SlotWriter slotWriter = this.writer;
        Anchor anchor = slotWriter.anchor(slotWriter.parent);
        this.groupNodeCount++;
        FixupList fixupList = this.insertFixups;
        fixupList.getClass();
        Operation.InsertNodeFixup insertNodeFixup = Operation.InsertNodeFixup.INSTANCE;
        Operations operations = fixupList.operations;
        operations.pushOp(insertNodeFixup);
        Operations.WriteScope.m338setObjectDKhxnng(operations, 0, function0);
        operations.intArgs[operations.intArgsSize - operations.opCodes[operations.opCodesSize - 1].ints] = i;
        Operations.WriteScope.m338setObjectDKhxnng(operations, 1, anchor);
        Operation.PostInsertNodeFixup postInsertNodeFixup = Operation.PostInsertNodeFixup.INSTANCE;
        Operations operations2 = fixupList.pendingOperations;
        operations2.pushOp(postInsertNodeFixup);
        operations2.intArgs[operations2.intArgsSize - operations2.opCodes[operations2.opCodesSize - 1].ints] = i;
        Operations.WriteScope.m338setObjectDKhxnng(operations2, 0, anchor);
    }

    public final PersistentCompositionLocalMap currentCompositionLocalScope(int i) {
        PersistentCompositionLocalMap persistentCompositionLocalMap;
        if (this.inserting && this.writerHasAProvider) {
            int i2 = this.writer.parent;
            while (i2 > 0) {
                SlotWriter slotWriter = this.writer;
                if (slotWriter.groups[slotWriter.groupIndexToAddress(i2) * 5] == 202 && Intrinsics.areEqual(this.writer.groupObjectKey(i2), ComposerKt.compositionLocalMap)) {
                    PersistentCompositionLocalMap persistentCompositionLocalMap2 = (PersistentCompositionLocalMap) this.writer.groupAux(i2);
                    this.providerCache = persistentCompositionLocalMap2;
                    return persistentCompositionLocalMap2;
                }
                SlotWriter slotWriter2 = this.writer;
                i2 = slotWriter2.parent(i2, slotWriter2.groups);
            }
        }
        if (this.reader.groupsSize > 0) {
            while (i > 0) {
                if (this.reader.groupKey(i) == 202) {
                    SlotReader slotReader = this.reader;
                    if (Intrinsics.areEqual(slotReader.objectKey(i, slotReader.groups), ComposerKt.compositionLocalMap)) {
                        MutableIntObjectMap mutableIntObjectMap = this.providerUpdates;
                        if (mutableIntObjectMap == null || (persistentCompositionLocalMap = (PersistentCompositionLocalMap) mutableIntObjectMap.get(i)) == null) {
                            SlotReader slotReader2 = this.reader;
                            persistentCompositionLocalMap = (PersistentCompositionLocalMap) slotReader2.aux(i, slotReader2.groups);
                        }
                        this.providerCache = persistentCompositionLocalMap;
                        return persistentCompositionLocalMap;
                    }
                }
                i = this.reader.parent(i);
            }
        }
        PersistentCompositionLocalMap persistentCompositionLocalMap3 = this.rootProvider;
        this.providerCache = persistentCompositionLocalMap3;
        return persistentCompositionLocalMap3;
    }

    public final void dispose$runtime_release() {
        Trace.INSTANCE.getClass();
        android.os.Trace.beginSection("Compose:Composer.dispose");
        try {
            this.parentContext.unregisterComposer$runtime_release(this);
            this.invalidateStack.clear();
            ((ArrayList) this.invalidations).clear();
            this.changes.operations.clear();
            this.providerUpdates = null;
            this.applier.clear();
            Unit unit = Unit.INSTANCE;
            android.os.Trace.endSection();
        } catch (Throwable th) {
            Trace.INSTANCE.getClass();
            android.os.Trace.endSection();
            throw th;
        }
    }

    /* renamed from: doCompose-aFTiNEg, reason: not valid java name */
    public final void m330doComposeaFTiNEg(MutableScatterMap mutableScatterMap, ComposableLambdaImpl composableLambdaImpl) {
        if (this.isComposing) {
            ComposerKt.composeImmediateRuntimeError("Reentrant composition is not supported");
        }
        Trace.INSTANCE.getClass();
        android.os.Trace.beginSection("Compose:recompose");
        try {
            this.compositionToken = Long.hashCode(SnapshotKt.currentSnapshot().getSnapshotId());
            this.providerUpdates = null;
            m332updateComposerInvalidationsRY85e9Y(mutableScatterMap);
            this.nodeIndex = 0;
            this.isComposing = true;
            try {
                startRoot();
                Object nextSlot = nextSlot();
                if (nextSlot != composableLambdaImpl && composableLambdaImpl != null) {
                    updateValue(composableLambdaImpl);
                }
                ComposerImpl$derivedStateObserver$1 composerImpl$derivedStateObserver$1 = this.derivedStateObserver;
                MutableVector derivedStateObservers = SnapshotStateKt.derivedStateObservers();
                try {
                    derivedStateObservers.add(composerImpl$derivedStateObserver$1);
                    if (composableLambdaImpl != null) {
                        startGroup(200, ComposerKt.invocation);
                        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, composableLambdaImpl);
                        composableLambdaImpl.invoke((Object) this, (Object) 1);
                        end(false);
                    } else {
                        if (this.providersInvalid && nextSlot != null) {
                            Composer.Companion.getClass();
                            if (!nextSlot.equals(Composer.Companion.Empty)) {
                                startGroup(200, ComposerKt.invocation);
                                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, nextSlot);
                                Function2 function2 = (Function2) nextSlot;
                                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
                                function2.invoke(this, 1);
                                end(false);
                            }
                        }
                        skipCurrentGroup();
                    }
                    derivedStateObservers.removeAt(derivedStateObservers.size - 1);
                    endRoot();
                    this.isComposing = false;
                    ((ArrayList) this.invalidations).clear();
                    if (!this.writer.closed) {
                        ComposerKt.composeImmediateRuntimeError("Check failed");
                    }
                    forceFreshInsertTable();
                    Unit unit = Unit.INSTANCE;
                    android.os.Trace.endSection();
                } catch (Throwable th) {
                    derivedStateObservers.removeAt(derivedStateObservers.size - 1);
                    throw th;
                }
            } finally {
            }
        } catch (Throwable th2) {
            Trace.INSTANCE.getClass();
            android.os.Trace.endSection();
            throw th2;
        }
    }

    public final void doRecordDownsFor(int i, int i2) {
        if (i <= 0 || i == i2) {
            return;
        }
        doRecordDownsFor(this.reader.parent(i), i2);
        if (this.reader.isNode(i)) {
            Object node = this.reader.node(i);
            ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
            composerChangeListWriter.realizeNodeMovementOperations();
            composerChangeListWriter.pendingDownNodes.add(node);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [boolean, int] */
    public final void end(boolean z) {
        int hashCode;
        IntStack intStack;
        int i;
        int i2;
        ?? r6;
        int i3;
        Pending pending;
        int i4;
        List list;
        ArrayList arrayList;
        HashSet hashSet;
        LinkedHashSet linkedHashSet;
        int i5;
        int i6;
        long j;
        long[] jArr;
        long j2;
        long[] jArr2;
        int hashCode2;
        IntStack intStack2 = this.parentStateStack;
        int i7 = 1;
        int i8 = intStack2.slots[intStack2.tos - 2] - 1;
        boolean z2 = this.inserting;
        Composer.Companion companion = Composer.Companion;
        if (z2) {
            SlotWriter slotWriter = this.writer;
            int i9 = slotWriter.parent;
            int i10 = slotWriter.groups[slotWriter.groupIndexToAddress(i9) * 5];
            Object groupObjectKey = this.writer.groupObjectKey(i9);
            Object groupAux = this.writer.groupAux(i9);
            if (groupObjectKey == null) {
                if (groupAux != null && i10 == 207) {
                    companion.getClass();
                    if (!groupAux.equals(Composer.Companion.Empty)) {
                        this.compoundKeyHash = Integer.rotateRight(Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(groupAux.hashCode()), 3);
                    }
                }
                hashCode2 = Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(i10);
            } else {
                hashCode2 = Integer.hashCode(groupObjectKey instanceof Enum ? ((Enum) groupObjectKey).ordinal() : groupObjectKey.hashCode()) ^ Integer.rotateRight(this.compoundKeyHash, 3);
            }
            this.compoundKeyHash = Integer.rotateRight(hashCode2, 3);
        } else {
            SlotReader slotReader = this.reader;
            int i11 = slotReader.parent;
            int groupKey = slotReader.groupKey(i11);
            SlotReader slotReader2 = this.reader;
            Object objectKey = slotReader2.objectKey(i11, slotReader2.groups);
            SlotReader slotReader3 = this.reader;
            Object aux = slotReader3.aux(i11, slotReader3.groups);
            if (objectKey == null) {
                if (aux != null && groupKey == 207) {
                    companion.getClass();
                    if (!aux.equals(Composer.Companion.Empty)) {
                        this.compoundKeyHash = Integer.rotateRight(Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(aux.hashCode()), 3);
                    }
                }
                hashCode = Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(groupKey);
            } else {
                hashCode = Integer.hashCode(objectKey instanceof Enum ? ((Enum) objectKey).ordinal() : objectKey.hashCode()) ^ Integer.rotateRight(this.compoundKeyHash, 3);
            }
            this.compoundKeyHash = Integer.rotateRight(hashCode, 3);
        }
        int i12 = this.groupNodeCount;
        Pending pending2 = this.pending;
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        if (pending2 == null || pending2.keyInfos.size() <= 0) {
            intStack = intStack2;
            i = 1;
            i2 = -1;
        } else {
            List list2 = pending2.keyInfos;
            ArrayList arrayList2 = (ArrayList) pending2.usedKeys;
            HashSet hashSet2 = new HashSet(arrayList2.size());
            int size = arrayList2.size();
            for (int i13 = 0; i13 < size; i13++) {
                hashSet2.add(arrayList2.get(i13));
            }
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            int size2 = arrayList2.size();
            int size3 = list2.size();
            int i14 = 0;
            int i15 = 0;
            int i16 = 0;
            i2 = -1;
            while (i16 < size3) {
                int i17 = i7;
                KeyInfo keyInfo = (KeyInfo) list2.get(i16);
                boolean contains = hashSet2.contains(keyInfo);
                MutableIntObjectMap mutableIntObjectMap = pending2.groupInfos;
                IntStack intStack3 = intStack2;
                int i18 = pending2.startIndex;
                if (!contains) {
                    GroupInfo groupInfo = (GroupInfo) mutableIntObjectMap.get(keyInfo.location);
                    composerChangeListWriter.removeNode((groupInfo != null ? groupInfo.nodeIndex : -1) + i18, keyInfo.nodes);
                    int i19 = keyInfo.location;
                    pending2.updateNodeCount(i19, 0);
                    composerChangeListWriter.writersReaderDelta = (i19 - composerChangeListWriter.composer.reader.currentGroup) + composerChangeListWriter.writersReaderDelta;
                    this.reader.reposition(i19);
                    recordDelete();
                    this.reader.skipGroup();
                    ComposerKt.access$removeRange(this.invalidations, i19, this.reader.groups[(i19 * 5) + 3] + i19);
                } else if (!linkedHashSet2.contains(keyInfo)) {
                    if (i14 < size2) {
                        KeyInfo keyInfo2 = (KeyInfo) arrayList2.get(i14);
                        if (keyInfo2 != keyInfo) {
                            GroupInfo groupInfo2 = (GroupInfo) mutableIntObjectMap.get(keyInfo2.location);
                            int i20 = groupInfo2 != null ? groupInfo2.nodeIndex : -1;
                            linkedHashSet2.add(keyInfo2);
                            pending = pending2;
                            if (i20 != i15) {
                                GroupInfo groupInfo3 = (GroupInfo) mutableIntObjectMap.get(keyInfo2.location);
                                int i21 = groupInfo3 != null ? groupInfo3.nodeCount : keyInfo2.nodes;
                                i4 = i14;
                                int i22 = i20 + i18;
                                list = list2;
                                int i23 = i15 + i18;
                                if (i21 > 0) {
                                    arrayList = arrayList2;
                                    int i24 = composerChangeListWriter.moveCount;
                                    if (i24 > 0) {
                                        hashSet = hashSet2;
                                        if (composerChangeListWriter.moveFrom == i22 - i24 && composerChangeListWriter.moveTo == i23 - i24) {
                                            composerChangeListWriter.moveCount = i24 + i21;
                                        }
                                    } else {
                                        hashSet = hashSet2;
                                    }
                                    composerChangeListWriter.realizeNodeMovementOperations();
                                    composerChangeListWriter.moveFrom = i22;
                                    composerChangeListWriter.moveTo = i23;
                                    composerChangeListWriter.moveCount = i21;
                                } else {
                                    arrayList = arrayList2;
                                    hashSet = hashSet2;
                                    composerChangeListWriter.getClass();
                                }
                                if (i20 > i15) {
                                    Object[] objArr = mutableIntObjectMap.values;
                                    long[] jArr3 = mutableIntObjectMap.metadata;
                                    int length = jArr3.length - 2;
                                    if (length >= 0) {
                                        int i25 = i21;
                                        int i26 = 0;
                                        while (true) {
                                            long j3 = jArr3[i26];
                                            linkedHashSet = linkedHashSet2;
                                            i5 = size2;
                                            if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                int i27 = 8 - ((~(i26 - length)) >>> 31);
                                                int i28 = 0;
                                                while (i28 < i27) {
                                                    if ((j3 & 255) < 128) {
                                                        j2 = j3;
                                                        GroupInfo groupInfo4 = (GroupInfo) objArr[(i26 << 3) + i28];
                                                        int i29 = groupInfo4.nodeIndex;
                                                        jArr2 = jArr3;
                                                        if (i20 <= i29 && i29 < i20 + i25) {
                                                            groupInfo4.nodeIndex = (i29 - i20) + i15;
                                                        } else if (i15 <= i29 && i29 < i20) {
                                                            groupInfo4.nodeIndex = i29 + i25;
                                                        }
                                                    } else {
                                                        j2 = j3;
                                                        jArr2 = jArr3;
                                                    }
                                                    j3 = j2 >> 8;
                                                    i28++;
                                                    jArr3 = jArr2;
                                                }
                                                jArr = jArr3;
                                                if (i27 != 8) {
                                                    break;
                                                }
                                            } else {
                                                jArr = jArr3;
                                            }
                                            if (i26 == length) {
                                                break;
                                            }
                                            i26++;
                                            linkedHashSet2 = linkedHashSet;
                                            size2 = i5;
                                            jArr3 = jArr;
                                        }
                                    }
                                } else {
                                    int i30 = i21;
                                    linkedHashSet = linkedHashSet2;
                                    i5 = size2;
                                    if (i15 > i20) {
                                        Object[] objArr2 = mutableIntObjectMap.values;
                                        long[] jArr4 = mutableIntObjectMap.metadata;
                                        int length2 = jArr4.length - 2;
                                        if (length2 >= 0) {
                                            int i31 = 0;
                                            while (true) {
                                                long j4 = jArr4[i31];
                                                Object[] objArr3 = objArr2;
                                                long[] jArr5 = jArr4;
                                                if ((((~j4) << 7) & j4 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                    int i32 = 8 - ((~(i31 - length2)) >>> 31);
                                                    for (int i33 = 0; i33 < i32; i33 = i6 + 1) {
                                                        if ((j4 & 255) < 128) {
                                                            i6 = i33;
                                                            GroupInfo groupInfo5 = (GroupInfo) objArr3[(i31 << 3) + i33];
                                                            j = j4;
                                                            int i34 = groupInfo5.nodeIndex;
                                                            if (i20 <= i34 && i34 < i20 + i30) {
                                                                groupInfo5.nodeIndex = (i34 - i20) + i15;
                                                            } else if (i20 + 1 <= i34 && i34 < i15) {
                                                                groupInfo5.nodeIndex = i34 - i30;
                                                            }
                                                        } else {
                                                            i6 = i33;
                                                            j = j4;
                                                        }
                                                        j4 = j >> 8;
                                                    }
                                                    if (i32 != 8) {
                                                        break;
                                                    }
                                                }
                                                if (i31 == length2) {
                                                    break;
                                                }
                                                i31++;
                                                objArr2 = objArr3;
                                                jArr4 = jArr5;
                                            }
                                        }
                                    }
                                }
                            } else {
                                i4 = i14;
                                list = list2;
                                arrayList = arrayList2;
                                hashSet = hashSet2;
                            }
                            linkedHashSet = linkedHashSet2;
                            i5 = size2;
                        } else {
                            pending = pending2;
                            i4 = i14;
                            list = list2;
                            arrayList = arrayList2;
                            hashSet = hashSet2;
                            linkedHashSet = linkedHashSet2;
                            i5 = size2;
                            i16++;
                        }
                        i14 = i4 + 1;
                        GroupInfo groupInfo6 = (GroupInfo) mutableIntObjectMap.get(keyInfo2.location);
                        i15 += groupInfo6 != null ? groupInfo6.nodeCount : keyInfo2.nodes;
                        arrayList2 = arrayList;
                        i7 = i17;
                        intStack2 = intStack3;
                        pending2 = pending;
                        list2 = list;
                        hashSet2 = hashSet;
                        linkedHashSet2 = linkedHashSet;
                        size2 = i5;
                    } else {
                        i7 = i17;
                        intStack2 = intStack3;
                    }
                }
                i16++;
                i7 = i17;
                intStack2 = intStack3;
            }
            intStack = intStack2;
            i = i7;
            composerChangeListWriter.realizeNodeMovementOperations();
            if (list2.size() > 0) {
                SlotReader slotReader4 = this.reader;
                composerChangeListWriter.writersReaderDelta = (slotReader4.currentEnd - composerChangeListWriter.composer.reader.currentGroup) + composerChangeListWriter.writersReaderDelta;
                slotReader4.skipToGroupEnd();
            }
        }
        boolean z3 = this.inserting;
        if (!z3) {
            SlotReader slotReader5 = this.reader;
            int i35 = slotReader5.currentSlotEnd - slotReader5.currentSlot;
            if (i35 > 0) {
                if (i35 > 0) {
                    composerChangeListWriter.realizeOperationLocation(false);
                    composerChangeListWriter.recordSlotEditing();
                    ChangeList changeList = composerChangeListWriter.changeList;
                    changeList.getClass();
                    Operation.TrimParentValues trimParentValues = Operation.TrimParentValues.INSTANCE;
                    Operations operations = changeList.operations;
                    operations.pushOp(trimParentValues);
                    operations.intArgs[operations.intArgsSize - operations.opCodes[operations.opCodesSize - 1].ints] = i35;
                } else {
                    composerChangeListWriter.getClass();
                }
            }
        }
        int i36 = this.nodeIndex;
        while (true) {
            SlotReader slotReader6 = this.reader;
            if (slotReader6.emptyCount <= 0 && (i3 = slotReader6.currentGroup) != slotReader6.currentEnd) {
                recordDelete();
                composerChangeListWriter.removeNode(i36, this.reader.skipGroup());
                ComposerKt.access$removeRange(this.invalidations, i3, this.reader.currentGroup);
                i2 = i2;
            }
        }
        if (z3) {
            if (z) {
                FixupList fixupList = this.insertFixups;
                Operations operations2 = fixupList.pendingOperations;
                if (!operations2.isNotEmpty()) {
                    ComposerKt.composeImmediateRuntimeError("Cannot end node insertion, there are no pending operations that can be realized.");
                }
                Operation[] operationArr = operations2.opCodes;
                int i37 = operations2.opCodesSize - 1;
                operations2.opCodesSize = i37;
                Operation operation = operationArr[i37];
                operationArr[i37] = null;
                Operations operations3 = fixupList.operations;
                operations3.pushOp(operation);
                Object[] objArr4 = operations2.objectArgs;
                Object[] objArr5 = operations3.objectArgs;
                int i38 = operations3.objectArgsSize;
                int i39 = operation.objects;
                int i40 = operations2.objectArgsSize;
                int i41 = i40 - i39;
                System.arraycopy(objArr4, i41, objArr5, i38 - i39, i40 - i41);
                Object[] objArr6 = operations2.objectArgs;
                int i42 = operations2.objectArgsSize;
                Arrays.fill(objArr6, i42 - i39, i42, (Object) null);
                int[] iArr = operations2.intArgs;
                int[] iArr2 = operations3.intArgs;
                int i43 = operations3.intArgsSize;
                int i44 = operation.ints;
                int i45 = operations2.intArgsSize;
                ArraysKt___ArraysJvmKt.copyInto(i43 - i44, i45 - i44, i45, iArr, iArr2);
                operations2.objectArgsSize -= i39;
                operations2.intArgsSize -= i44;
                i12 = i;
            }
            if (this.reader.emptyCount <= 0) {
                PreconditionsKt.throwIllegalArgumentException("Unbalanced begin/end empty");
            }
            r3.emptyCount--;
            SlotWriter slotWriter2 = this.writer;
            int i46 = slotWriter2.parent;
            slotWriter2.endGroup();
            if (this.reader.emptyCount <= 0) {
                int i47 = (-2) - i46;
                this.writer.endInsert();
                this.writer.close(i);
                Anchor anchor = this.insertAnchor;
                if (this.insertFixups.operations.isEmpty()) {
                    SlotTable slotTable = this.insertTable;
                    composerChangeListWriter.pushPendingUpsAndDowns();
                    composerChangeListWriter.realizeOperationLocation(false);
                    composerChangeListWriter.recordSlotEditing();
                    composerChangeListWriter.realizeNodeMovementOperations();
                    ChangeList changeList2 = composerChangeListWriter.changeList;
                    changeList2.getClass();
                    Operation.InsertSlots insertSlots = Operation.InsertSlots.INSTANCE;
                    Operations operations4 = changeList2.operations;
                    operations4.pushOp(insertSlots);
                    Operations.WriteScope.m339setObjects4uCC6AY(operations4, 0, anchor, 1, slotTable);
                    r6 = 0;
                } else {
                    SlotTable slotTable2 = this.insertTable;
                    FixupList fixupList2 = this.insertFixups;
                    composerChangeListWriter.pushPendingUpsAndDowns();
                    composerChangeListWriter.realizeOperationLocation(false);
                    composerChangeListWriter.recordSlotEditing();
                    composerChangeListWriter.realizeNodeMovementOperations();
                    ChangeList changeList3 = composerChangeListWriter.changeList;
                    changeList3.getClass();
                    Operation.InsertSlotsWithFixups insertSlotsWithFixups = Operation.InsertSlotsWithFixups.INSTANCE;
                    Operations operations5 = changeList3.operations;
                    operations5.pushOp(insertSlotsWithFixups);
                    Operations.WriteScope.m340setObjectst7hvbck(operations5, anchor, slotTable2, fixupList2);
                    this.insertFixups = new FixupList();
                    r6 = 0;
                }
                this.inserting = r6;
                if (this.slotTable.groupsSize != 0) {
                    updateNodeCount(i47, r6);
                    updateNodeCountOverrides(i47, i12);
                }
            }
        } else {
            if (z) {
                composerChangeListWriter.moveUp();
            }
            int i48 = composerChangeListWriter.composer.reader.parent;
            IntStack intStack4 = composerChangeListWriter.startedGroups;
            int i49 = i2;
            if (intStack4.peekOr(i49) > i48) {
                ComposerKt.composeImmediateRuntimeError("Missed recording an endGroup");
            }
            if (intStack4.peekOr(i49) == i48) {
                composerChangeListWriter.realizeOperationLocation(false);
                intStack4.pop();
                ChangeList changeList4 = composerChangeListWriter.changeList;
                changeList4.getClass();
                changeList4.operations.pushOp(Operation.EndCurrentGroup.INSTANCE);
            }
            int i50 = this.reader.parent;
            if (i12 != updatedNodeCount(i50)) {
                updateNodeCountOverrides(i50, i12);
            }
            if (z) {
                i12 = 1;
            }
            this.reader.endGroup();
            composerChangeListWriter.realizeNodeMovementOperations();
        }
        Pending pending3 = (Pending) this.pendingStack.remove(r3.size() - 1);
        if (pending3 != null && !z3) {
            pending3.groupIndex++;
        }
        this.pending = pending3;
        this.nodeIndex = intStack.pop() + i12;
        this.rGroupIndex = intStack.pop();
        this.groupNodeCount = intStack.pop() + i12;
    }

    public final void endDefaults() {
        end(false);
        RecomposeScopeImpl currentRecomposeScope$runtime_release = getCurrentRecomposeScope$runtime_release();
        if (currentRecomposeScope$runtime_release != null) {
            int i = currentRecomposeScope$runtime_release.flags;
            if ((i & 1) != 0) {
                currentRecomposeScope$runtime_release.flags = i | 2;
            }
        }
    }

    public final RecomposeScopeImpl endRestartGroup() {
        final RecomposeScopeImpl recomposeScopeImpl;
        RecomposeScopeImpl recomposeScopeImpl2;
        Anchor anchor;
        Function1 function1;
        if (this.invalidateStack.isEmpty()) {
            recomposeScopeImpl = null;
        } else {
            ArrayList arrayList = this.invalidateStack;
            recomposeScopeImpl = (RecomposeScopeImpl) arrayList.remove(arrayList.size() - 1);
        }
        if (recomposeScopeImpl != null) {
            int i = recomposeScopeImpl.flags;
            recomposeScopeImpl.flags = i & (-9);
            final int i2 = this.compositionToken;
            final MutableObjectIntMap mutableObjectIntMap = recomposeScopeImpl.trackedInstances;
            if (mutableObjectIntMap != null && (i & 16) == 0) {
                Object[] objArr = mutableObjectIntMap.keys;
                int[] iArr = mutableObjectIntMap.values;
                long[] jArr = mutableObjectIntMap.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i3 = 0;
                    loop0: while (true) {
                        long j = jArr[i3];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i4 = 8 - ((~(i3 - length)) >>> 31);
                            for (int i5 = 0; i5 < i4; i5++) {
                                if ((j & 255) < 128) {
                                    int i6 = (i3 << 3) + i5;
                                    Object obj = objArr[i6];
                                    if (iArr[i6] != i2) {
                                        function1 = new Function1() { // from class: androidx.compose.runtime.RecomposeScopeImpl$end$1$2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj2) {
                                                Composition composition;
                                                Composition composition2;
                                                int i7;
                                                Composition composition3 = (Composition) obj2;
                                                RecomposeScopeImpl recomposeScopeImpl3 = RecomposeScopeImpl.this;
                                                if (recomposeScopeImpl3.currentToken == i2 && Intrinsics.areEqual(mutableObjectIntMap, recomposeScopeImpl3.trackedInstances) && (composition3 instanceof CompositionImpl)) {
                                                    MutableObjectIntMap mutableObjectIntMap2 = mutableObjectIntMap;
                                                    int i8 = i2;
                                                    RecomposeScopeImpl recomposeScopeImpl4 = RecomposeScopeImpl.this;
                                                    long[] jArr2 = mutableObjectIntMap2.metadata;
                                                    int length2 = jArr2.length - 2;
                                                    if (length2 >= 0) {
                                                        int i9 = 0;
                                                        while (true) {
                                                            long j2 = jArr2[i9];
                                                            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                int i10 = 8;
                                                                int i11 = 8 - ((~(i9 - length2)) >>> 31);
                                                                int i12 = 0;
                                                                while (i12 < i11) {
                                                                    if ((255 & j2) < 128) {
                                                                        int i13 = (i9 << 3) + i12;
                                                                        Object obj3 = mutableObjectIntMap2.keys[i13];
                                                                        boolean z = mutableObjectIntMap2.values[i13] != i8;
                                                                        if (z) {
                                                                            CompositionImpl compositionImpl = (CompositionImpl) composition3;
                                                                            i7 = i10;
                                                                            ScopeMap.m348removeimpl(compositionImpl.observations, obj3, recomposeScopeImpl4);
                                                                            if (obj3 instanceof DerivedState) {
                                                                                DerivedState derivedState = (DerivedState) obj3;
                                                                                composition2 = composition3;
                                                                                if (!compositionImpl.observations.containsKey(derivedState)) {
                                                                                    ScopeMap.m349removeScopeimpl(compositionImpl.derivedStates, derivedState);
                                                                                }
                                                                                MutableScatterMap mutableScatterMap = recomposeScopeImpl4.trackedDependencies;
                                                                                if (mutableScatterMap != null) {
                                                                                    mutableScatterMap.remove(obj3);
                                                                                }
                                                                            } else {
                                                                                composition2 = composition3;
                                                                            }
                                                                        } else {
                                                                            composition2 = composition3;
                                                                            i7 = i10;
                                                                        }
                                                                        if (z) {
                                                                            mutableObjectIntMap2.removeValueAt(i13);
                                                                        }
                                                                    } else {
                                                                        composition2 = composition3;
                                                                        i7 = i10;
                                                                    }
                                                                    j2 >>= i7;
                                                                    i12++;
                                                                    i10 = i7;
                                                                    composition3 = composition2;
                                                                }
                                                                composition = composition3;
                                                                if (i11 != i10) {
                                                                    break;
                                                                }
                                                            } else {
                                                                composition = composition3;
                                                            }
                                                            if (i9 == length2) {
                                                                break;
                                                            }
                                                            i9++;
                                                            composition3 = composition;
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        break loop0;
                                    }
                                }
                                j >>= 8;
                            }
                            if (i4 != 8) {
                                break;
                            }
                        }
                        if (i3 == length) {
                            break;
                        }
                        i3++;
                    }
                }
            }
            function1 = null;
            ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
            if (function1 != null) {
                ChangeList changeList = composerChangeListWriter.changeList;
                changeList.getClass();
                Operation.EndCompositionScope endCompositionScope = Operation.EndCompositionScope.INSTANCE;
                Operations operations = changeList.operations;
                operations.pushOp(endCompositionScope);
                Operations.WriteScope.m339setObjects4uCC6AY(operations, 0, function1, 1, this.composition);
            }
            int i7 = recomposeScopeImpl.flags;
            if ((i7 & 512) != 0) {
                recomposeScopeImpl.flags = i7 & (-513);
                ChangeList changeList2 = composerChangeListWriter.changeList;
                changeList2.getClass();
                Operation.EndResumingScope endResumingScope = Operation.EndResumingScope.INSTANCE;
                Operations operations2 = changeList2.operations;
                operations2.pushOp(endResumingScope);
                Operations.WriteScope.m338setObjectDKhxnng(operations2, 0, recomposeScopeImpl);
            }
        }
        if (recomposeScopeImpl != null) {
            int i8 = recomposeScopeImpl.flags;
            if ((i8 & 16) == 0 && ((i8 & 1) != 0 || this.forceRecomposeScopes)) {
                if (recomposeScopeImpl.anchor == null) {
                    if (this.inserting) {
                        SlotWriter slotWriter = this.writer;
                        anchor = slotWriter.anchor(slotWriter.parent);
                    } else {
                        SlotReader slotReader = this.reader;
                        anchor = slotReader.anchor(slotReader.parent);
                    }
                    recomposeScopeImpl.anchor = anchor;
                }
                recomposeScopeImpl.flags &= -5;
                recomposeScopeImpl2 = recomposeScopeImpl;
                end(false);
                return recomposeScopeImpl2;
            }
        }
        recomposeScopeImpl2 = null;
        end(false);
        return recomposeScopeImpl2;
    }

    public final void endRoot() {
        end(false);
        this.parentContext.doneComposing$runtime_release();
        end(false);
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        if (composerChangeListWriter.startedGroup) {
            composerChangeListWriter.realizeOperationLocation(false);
            composerChangeListWriter.realizeOperationLocation(false);
            ChangeList changeList = composerChangeListWriter.changeList;
            changeList.getClass();
            changeList.operations.pushOp(Operation.EndCurrentGroup.INSTANCE);
            composerChangeListWriter.startedGroup = false;
        }
        composerChangeListWriter.pushPendingUpsAndDowns();
        if (composerChangeListWriter.startedGroups.tos != 0) {
            ComposerKt.composeImmediateRuntimeError("Missed recording an endGroup()");
        }
        if (!this.pendingStack.isEmpty()) {
            ComposerKt.composeImmediateRuntimeError("Start/end imbalance");
        }
        cleanUpCompose();
        this.reader.close();
        int pop = this.providersInvalidStack.pop();
        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
        this.providersInvalid = pop != 0;
    }

    public final void enterGroup(boolean z, Pending pending) {
        this.pendingStack.add(this.pending);
        this.pending = pending;
        int i = this.groupNodeCount;
        IntStack intStack = this.parentStateStack;
        intStack.push(i);
        intStack.push(this.rGroupIndex);
        intStack.push(this.nodeIndex);
        if (z) {
            this.nodeIndex = 0;
        }
        this.groupNodeCount = 0;
        this.rGroupIndex = 0;
    }

    public final void forceFreshInsertTable() {
        SlotTable slotTable = new SlotTable();
        if (this.sourceMarkersEnabled) {
            slotTable.collectSourceInformation();
        }
        int i = 1;
        if (this.parentContext.getCollectingCallByInformation$runtime_release()) {
            slotTable.calledByMap = new MutableIntObjectMap(0, i, null);
        }
        this.insertTable = slotTable;
        SlotWriter openWriter = slotTable.openWriter();
        openWriter.close(true);
        this.writer = openWriter;
    }

    public final RecomposeScopeImpl getCurrentRecomposeScope$runtime_release() {
        ArrayList arrayList = this.invalidateStack;
        if (this.childrenComposing != 0 || arrayList.isEmpty()) {
            return null;
        }
        return (RecomposeScopeImpl) AlertController$$ExternalSyntheticOutline0.m(arrayList, 1);
    }

    public final boolean getDefaultsInvalid() {
        if (!getSkipping() || this.providersInvalid) {
            return true;
        }
        RecomposeScopeImpl currentRecomposeScope$runtime_release = getCurrentRecomposeScope$runtime_release();
        return (currentRecomposeScope$runtime_release == null || (currentRecomposeScope$runtime_release.flags & 4) == 0) ? false : true;
    }

    public final CompositionErrorContextImpl getErrorContext$runtime_release() {
        if (this.sourceMarkersEnabled) {
            return this.errorContext;
        }
        return null;
    }

    public final boolean getSkipping() {
        RecomposeScopeImpl currentRecomposeScope$runtime_release;
        return (this.inserting || this.reusing || this.providersInvalid || (currentRecomposeScope$runtime_release = getCurrentRecomposeScope$runtime_release()) == null || (currentRecomposeScope$runtime_release.flags & 8) != 0) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0103, code lost:
    
        if (r14 == null) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void insertMovableContentGuarded(java.util.List r24) {
        /*
            Method dump skipped, instructions count: 557
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.insertMovableContentGuarded(java.util.List):void");
    }

    public final void invokeMovableContentLambda(final MovableContent movableContent, PersistentCompositionLocalMap persistentCompositionLocalMap, final Object obj, boolean z) {
        startMovableGroup(126665345, movableContent);
        nextSlot();
        updateValue(obj);
        int i = this.compoundKeyHash;
        try {
            this.compoundKeyHash = 126665345;
            if (this.inserting) {
                SlotWriter.markGroup$default(this.writer);
            }
            boolean z2 = (this.inserting || Intrinsics.areEqual(this.reader.getGroupAux(), persistentCompositionLocalMap)) ? false : true;
            if (z2) {
                recordProviderUpdate(persistentCompositionLocalMap);
            }
            OpaqueKey opaqueKey = ComposerKt.compositionLocalMap;
            GroupKind.Companion.getClass();
            m331startBaiHCIY(202, 0, opaqueKey, persistentCompositionLocalMap);
            this.providerCache = null;
            if (!this.inserting || z) {
                boolean z3 = this.providersInvalid;
                this.providersInvalid = z2;
                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(316014703, true, new Function2() { // from class: androidx.compose.runtime.ComposerImpl$invokeMovableContentLambda$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        Composer composer = (Composer) obj2;
                        int intValue = ((Number) obj3).intValue();
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        if (composerImpl.shouldExecute(intValue & 1, (intValue & 3) != 2)) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.runtime.ComposerImpl.invokeMovableContentLambda.<anonymous> (Composer.kt:3427)");
                            }
                            movableContent.content.invoke(obj, composerImpl, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else {
                            composerImpl.skipToGroupEnd();
                        }
                        return Unit.INSTANCE;
                    }
                });
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, composableLambdaImpl);
                composableLambdaImpl.invoke((Object) this, (Object) 1);
                this.providersInvalid = z3;
            } else {
                this.writerHasAProvider = true;
                SlotWriter slotWriter = this.writer;
                this.parentContext.insertMovableContent$runtime_release(new MovableContentStateReference(movableContent, obj, this.composition, this.insertTable, slotWriter.anchor(slotWriter.parent(slotWriter.parent, slotWriter.groups)), EmptyList.INSTANCE, currentCompositionLocalScope(), null));
            }
            end(false);
            this.providerCache = null;
            this.compoundKeyHash = i;
            end(false);
        } catch (Throwable th) {
            end(false);
            this.providerCache = null;
            this.compoundKeyHash = i;
            end(false);
            throw th;
        }
    }

    public final Object nextSlot() {
        boolean z = this.inserting;
        Composer.Companion companion = Composer.Companion;
        if (z) {
            if (this.nodeExpected) {
                ComposerKt.composeImmediateRuntimeError("A call to createNode(), emitNode() or useNode() expected");
            }
            companion.getClass();
            return Composer.Companion.Empty;
        }
        Object next = this.reader.next();
        if (!this.reusing || (next instanceof ReusableRememberObserver)) {
            return next;
        }
        companion.getClass();
        return Composer.Companion.Empty;
    }

    public final List parentStackTrace() {
        CompositionContext compositionContext = this.parentContext;
        Composition composition$runtime_release = compositionContext.getComposition$runtime_release();
        CompositionImpl compositionImpl = composition$runtime_release instanceof CompositionImpl ? (CompositionImpl) composition$runtime_release : null;
        if (compositionImpl == null) {
            return EmptyList.INSTANCE;
        }
        SlotTable slotTable = compositionImpl.slotTable;
        SlotReader openReader = slotTable.openReader();
        try {
            Integer findSubcompositionContextGroup$lambda$4$scanGroup = ComposeStackTraceBuilderKt.findSubcompositionContextGroup$lambda$4$scanGroup(openReader, compositionContext, 0, openReader.groupsSize);
            if (findSubcompositionContextGroup$lambda$4$scanGroup == null) {
                return EmptyList.INSTANCE;
            }
            try {
                return ComposeStackTraceBuilderKt.traceForGroup(slotTable.openReader(), findSubcompositionContextGroup$lambda$4$scanGroup.intValue(), 0);
            } finally {
            }
        } finally {
        }
    }

    public final int rGroupIndexOf(int i) {
        int parent = this.reader.parent(i) + 1;
        int i2 = 0;
        while (parent < i) {
            if (!this.reader.hasObjectKey(parent)) {
                i2++;
            }
            parent += SlotTableKt.access$groupSize(parent, this.reader.groups);
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0062, code lost:
    
        if (r10 == null) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object recomposeMovableContent(androidx.compose.runtime.ControlledComposition r9, androidx.compose.runtime.ControlledComposition r10, java.lang.Integer r11, java.util.List r12, kotlin.jvm.functions.Function0 r13) {
        /*
            r8 = this;
            boolean r0 = r8.isComposing
            int r1 = r8.nodeIndex
            r2 = 1
            r8.isComposing = r2     // Catch: java.lang.Throwable -> L2b
            r2 = 0
            r8.nodeIndex = r2     // Catch: java.lang.Throwable -> L2b
            r3 = r12
            java.util.Collection r3 = (java.util.Collection) r3     // Catch: java.lang.Throwable -> L2b
            int r3 = r3.size()     // Catch: java.lang.Throwable -> L2b
            r4 = r2
        L12:
            r5 = 0
            if (r4 >= r3) goto L33
            java.lang.Object r6 = r12.get(r4)     // Catch: java.lang.Throwable -> L2b
            kotlin.Pair r6 = (kotlin.Pair) r6     // Catch: java.lang.Throwable -> L2b
            java.lang.Object r7 = r6.component1()     // Catch: java.lang.Throwable -> L2b
            androidx.compose.runtime.RecomposeScopeImpl r7 = (androidx.compose.runtime.RecomposeScopeImpl) r7     // Catch: java.lang.Throwable -> L2b
            java.lang.Object r6 = r6.component2()     // Catch: java.lang.Throwable -> L2b
            if (r6 == 0) goto L2d
            r8.tryImminentInvalidation$runtime_release(r7, r6)     // Catch: java.lang.Throwable -> L2b
            goto L30
        L2b:
            r9 = move-exception
            goto L6d
        L2d:
            r8.tryImminentInvalidation$runtime_release(r7, r5)     // Catch: java.lang.Throwable -> L2b
        L30:
            int r4 = r4 + 1
            goto L12
        L33:
            if (r9 == 0) goto L64
            if (r11 == 0) goto L3c
            int r11 = r11.intValue()     // Catch: java.lang.Throwable -> L2b
            goto L3d
        L3c:
            r11 = -1
        L3d:
            androidx.compose.runtime.CompositionImpl r9 = (androidx.compose.runtime.CompositionImpl) r9     // Catch: java.lang.Throwable -> L2b
            if (r10 == 0) goto L5e
            boolean r12 = r10.equals(r9)     // Catch: java.lang.Throwable -> L2b
            if (r12 != 0) goto L5e
            if (r11 < 0) goto L5e
            androidx.compose.runtime.CompositionImpl r10 = (androidx.compose.runtime.CompositionImpl) r10     // Catch: java.lang.Throwable -> L2b
            r9.invalidationDelegate = r10     // Catch: java.lang.Throwable -> L2b
            r9.invalidationDelegateGroup = r11     // Catch: java.lang.Throwable -> L2b
            java.lang.Object r10 = r13.invoke()     // Catch: java.lang.Throwable -> L58
            r9.invalidationDelegate = r5     // Catch: java.lang.Throwable -> L2b
            r9.invalidationDelegateGroup = r2     // Catch: java.lang.Throwable -> L2b
            goto L62
        L58:
            r10 = move-exception
            r9.invalidationDelegate = r5     // Catch: java.lang.Throwable -> L2b
            r9.invalidationDelegateGroup = r2     // Catch: java.lang.Throwable -> L2b
            throw r10     // Catch: java.lang.Throwable -> L2b
        L5e:
            java.lang.Object r10 = r13.invoke()     // Catch: java.lang.Throwable -> L2b
        L62:
            if (r10 != 0) goto L68
        L64:
            java.lang.Object r10 = r13.invoke()     // Catch: java.lang.Throwable -> L2b
        L68:
            r8.isComposing = r0
            r8.nodeIndex = r1
            return r10
        L6d:
            r8.isComposing = r0
            r8.nodeIndex = r1
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.recomposeMovableContent(androidx.compose.runtime.ControlledComposition, androidx.compose.runtime.ControlledComposition, java.lang.Integer, java.util.List, kotlin.jvm.functions.Function0):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0039, code lost:
    
        if (r3.location < r5) goto L11;
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02bc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0359  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void recomposeToGroupEnd() {
        /*
            Method dump skipped, instructions count: 929
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.recomposeToGroupEnd():void");
    }

    public final void recordDelete() {
        reportFreeMovableContent(this.reader.currentGroup);
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        composerChangeListWriter.realizeOperationLocation(false);
        composerChangeListWriter.recordSlotEditing();
        ChangeList changeList = composerChangeListWriter.changeList;
        changeList.getClass();
        changeList.operations.pushOp(Operation.RemoveCurrentGroup.INSTANCE);
        int i = composerChangeListWriter.writersReaderDelta;
        SlotReader slotReader = composerChangeListWriter.composer.reader;
        composerChangeListWriter.writersReaderDelta = slotReader.groups[(slotReader.currentGroup * 5) + 3] + i;
    }

    public final void recordProviderUpdate(PersistentCompositionLocalMap persistentCompositionLocalMap) {
        MutableIntObjectMap mutableIntObjectMap = this.providerUpdates;
        if (mutableIntObjectMap == null) {
            mutableIntObjectMap = new MutableIntObjectMap(0, 1, null);
            this.providerUpdates = mutableIntObjectMap;
        }
        mutableIntObjectMap.set(this.reader.currentGroup, persistentCompositionLocalMap);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void recordUpsAndDowns(int r7, int r8, int r9) {
        /*
            r6 = this;
            androidx.compose.runtime.SlotReader r0 = r6.reader
            com.android.systemui.compose.EnableCommand$enableCompositionTracing$1 r1 = androidx.compose.runtime.ComposerKt.compositionTracer
            if (r7 != r8) goto L7
            goto L1c
        L7:
            if (r7 == r9) goto L6d
            if (r8 != r9) goto Ld
            goto L6d
        Ld:
            int r1 = r0.parent(r7)
            if (r1 != r8) goto L16
            r9 = r8
            goto L6d
        L16:
            int r1 = r0.parent(r8)
            if (r1 != r7) goto L1e
        L1c:
            r9 = r7
            goto L6d
        L1e:
            int r1 = r0.parent(r7)
            int r2 = r0.parent(r8)
            if (r1 != r2) goto L2d
            int r9 = r0.parent(r7)
            goto L6d
        L2d:
            r1 = 0
            r2 = r7
            r3 = r1
        L30:
            if (r2 <= 0) goto L3b
            if (r2 == r9) goto L3b
            int r2 = r0.parent(r2)
            int r3 = r3 + 1
            goto L30
        L3b:
            r2 = r8
            r4 = r1
        L3d:
            if (r2 <= 0) goto L48
            if (r2 == r9) goto L48
            int r2 = r0.parent(r2)
            int r4 = r4 + 1
            goto L3d
        L48:
            int r9 = r3 - r4
            r5 = r7
            r2 = r1
        L4c:
            if (r2 >= r9) goto L55
            int r5 = r0.parent(r5)
            int r2 = r2 + 1
            goto L4c
        L55:
            int r4 = r4 - r3
            r9 = r8
        L57:
            if (r1 >= r4) goto L60
            int r9 = r0.parent(r9)
            int r1 = r1 + 1
            goto L57
        L60:
            r1 = r9
            r9 = r5
        L62:
            if (r9 == r1) goto L6d
            int r9 = r0.parent(r9)
            int r1 = r0.parent(r1)
            goto L62
        L6d:
            if (r7 <= 0) goto L81
            if (r7 == r9) goto L81
            boolean r1 = r0.isNode(r7)
            if (r1 == 0) goto L7c
            androidx.compose.runtime.changelist.ComposerChangeListWriter r1 = r6.changeListWriter
            r1.moveUp()
        L7c:
            int r7 = r0.parent(r7)
            goto L6d
        L81:
            r6.doRecordDownsFor(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.recordUpsAndDowns(int, int, int):void");
    }

    public final Object rememberedValue() {
        boolean z = this.inserting;
        Composer.Companion companion = Composer.Companion;
        if (z) {
            if (this.nodeExpected) {
                ComposerKt.composeImmediateRuntimeError("A call to createNode(), emitNode() or useNode() expected");
            }
            companion.getClass();
            return Composer.Companion.Empty;
        }
        Object next = this.reader.next();
        if (!this.reusing || (next instanceof ReusableRememberObserver)) {
            return next instanceof RememberObserverHolder ? ((RememberObserverHolder) next).wrapped : next;
        }
        companion.getClass();
        return Composer.Companion.Empty;
    }

    public final void reportFreeMovableContent(int i) {
        boolean isNode = this.reader.isNode(i);
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        if (isNode) {
            composerChangeListWriter.realizeNodeMovementOperations();
            Object node = this.reader.node(i);
            composerChangeListWriter.realizeNodeMovementOperations();
            composerChangeListWriter.pendingDownNodes.add(node);
        }
        reportFreeMovableContent$reportGroup(this, i, i, isNode, 0);
        composerChangeListWriter.realizeNodeMovementOperations();
        if (isNode) {
            composerChangeListWriter.moveUp();
        }
    }

    public final boolean shouldExecute(int i, boolean z) {
        return ((i & 1) == 0 && (this.inserting || this.reusing)) || z || !getSkipping();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void skipCurrentGroup() {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.skipCurrentGroup():void");
    }

    public final void skipReaderToGroupEnd() {
        int i;
        SlotReader slotReader = this.reader;
        int i2 = slotReader.parent;
        if (i2 >= 0) {
            i = slotReader.groups[(i2 * 5) + 1] & 67108863;
        } else {
            i = 0;
        }
        this.groupNodeCount = i;
        slotReader.skipToGroupEnd();
    }

    public final void skipToGroupEnd() {
        if (this.groupNodeCount != 0) {
            ComposerKt.composeImmediateRuntimeError("No nodes can be emitted before calling skipAndEndGroup");
        }
        if (this.inserting) {
            return;
        }
        RecomposeScopeImpl currentRecomposeScope$runtime_release = getCurrentRecomposeScope$runtime_release();
        if (currentRecomposeScope$runtime_release != null) {
            int i = currentRecomposeScope$runtime_release.flags;
            if ((i & 128) == 0) {
                currentRecomposeScope$runtime_release.flags = i | 16;
            }
        }
        if (((ArrayList) this.invalidations).isEmpty()) {
            skipReaderToGroupEnd();
        } else {
            recomposeToGroupEnd();
        }
    }

    public final List stackTraceForValue$runtime_release(final Object obj) {
        ObjectLocation objectLocation;
        List list;
        if (!this.sourceMarkersEnabled) {
            return EmptyList.INSTANCE;
        }
        Function1 function1 = new Function1() { // from class: androidx.compose.runtime.ComposerImpl$stackTraceForValue$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                boolean z;
                Object obj3 = obj;
                if (obj2 != obj3) {
                    RememberObserverHolder rememberObserverHolder = obj2 instanceof RememberObserverHolder ? (RememberObserverHolder) obj2 : null;
                    if ((rememberObserverHolder != null ? rememberObserverHolder.wrapped : null) != obj3) {
                        z = false;
                        return Boolean.valueOf(z);
                    }
                }
                z = true;
                return Boolean.valueOf(z);
            }
        };
        SlotTable slotTable = this.slotTable;
        SlotReader openReader = slotTable.openReader();
        try {
            Ref$IntRef ref$IntRef = new Ref$IntRef();
            loop0: while (true) {
                int i = ref$IntRef.element;
                objectLocation = null;
                if (i >= slotTable.groupsSize) {
                    Unit unit = Unit.INSTANCE;
                    break;
                }
                if (openReader.isNode(i) && ((Boolean) function1.mo779invoke(openReader.node(ref$IntRef.element))).booleanValue()) {
                    ObjectLocation objectLocation2 = new ObjectLocation(ref$IntRef.element, null);
                    openReader.close();
                    objectLocation = objectLocation2;
                    break;
                }
                int i2 = ref$IntRef.element;
                int[] iArr = openReader.groups;
                int access$slotAnchor = SlotTableKt.access$slotAnchor(i2, iArr);
                int i3 = i2 + 1;
                int i4 = (i3 < openReader.groupsSize ? iArr[(i3 * 5) + 4] : openReader.slotsSize) - access$slotAnchor;
                for (int i5 = 0; i5 < i4; i5++) {
                    if (((Boolean) function1.mo779invoke(openReader.groupGet(ref$IntRef.element, i5))).booleanValue()) {
                        objectLocation = new ObjectLocation(ref$IntRef.element, Integer.valueOf(i5));
                        break loop0;
                    }
                }
                ref$IntRef.element++;
            }
            if (objectLocation == null) {
                return EmptyList.INSTANCE;
            }
            int i6 = objectLocation.group;
            Integer num = objectLocation.dataOffset;
            if (this.sourceMarkersEnabled) {
                openReader = slotTable.openReader();
                try {
                    List traceForGroup = ComposeStackTraceBuilderKt.traceForGroup(openReader, i6, num);
                    openReader.close();
                    list = traceForGroup;
                } finally {
                }
            } else {
                list = EmptyList.INSTANCE;
            }
            return CollectionsKt___CollectionsKt.plus((Iterable) parentStackTrace(), (Collection) list);
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0172  */
    /* renamed from: start-BaiHCIY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m331startBaiHCIY(int r28, int r29, java.lang.Object r30, java.lang.Object r31) {
        /*
            Method dump skipped, instructions count: 926
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.m331startBaiHCIY(int, int, java.lang.Object, java.lang.Object):void");
    }

    public final void startDefaults() {
        GroupKind.Companion.getClass();
        m331startBaiHCIY(-127, 0, null, null);
    }

    public final void startGroup(int i, OpaqueKey opaqueKey) {
        GroupKind.Companion.getClass();
        m331startBaiHCIY(i, 0, opaqueKey, null);
    }

    public final void startMovableGroup(int i, Object obj) {
        GroupKind.Companion.getClass();
        m331startBaiHCIY(i, 0, obj, null);
    }

    public final void startNode() {
        GroupKind.Companion.getClass();
        m331startBaiHCIY(125, GroupKind.Node, null, null);
        this.nodeExpected = true;
    }

    public final void startReaderGroup(Object obj, boolean z) {
        if (z) {
            SlotReader slotReader = this.reader;
            if (slotReader.emptyCount <= 0) {
                if ((slotReader.groups[(slotReader.currentGroup * 5) + 1] & 1073741824) == 0) {
                    PreconditionsKt.throwIllegalArgumentException("Expected a node group");
                }
                slotReader.startGroup();
                return;
            }
            return;
        }
        if (obj != null && this.reader.getGroupAux() != obj) {
            ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
            composerChangeListWriter.getClass();
            composerChangeListWriter.realizeOperationLocation(false);
            ChangeList changeList = composerChangeListWriter.changeList;
            changeList.getClass();
            Operation.UpdateAuxData updateAuxData = Operation.UpdateAuxData.INSTANCE;
            Operations operations = changeList.operations;
            operations.pushOp(updateAuxData);
            Operations.WriteScope.m338setObjectDKhxnng(operations, 0, obj);
        }
        this.reader.startGroup();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0060, code lost:
    
        if ((r0.groups[(r4 * 5) + 1] & com.samsung.systemui.splugins.volume.VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startReplaceGroup(int r10) {
        /*
            r9 = this;
            androidx.compose.runtime.Pending r0 = r9.pending
            r1 = 0
            r2 = 0
            if (r0 == 0) goto Lf
            androidx.compose.runtime.GroupKind$Companion r0 = androidx.compose.runtime.GroupKind.Companion
            r0.getClass()
            r9.m331startBaiHCIY(r10, r1, r2, r2)
            return
        Lf:
            boolean r0 = r9.nodeExpected
            if (r0 == 0) goto L18
            java.lang.String r0 = "A call to createNode(), emitNode() or useNode() expected"
            androidx.compose.runtime.ComposerKt.composeImmediateRuntimeError(r0)
        L18:
            int r0 = r9.rGroupIndex
            int r3 = r9.compoundKeyHash
            r4 = 3
            int r3 = java.lang.Integer.rotateLeft(r3, r4)
            r3 = r3 ^ r10
            int r3 = java.lang.Integer.rotateLeft(r3, r4)
            r0 = r0 ^ r3
            r9.compoundKeyHash = r0
            int r0 = r9.rGroupIndex
            r3 = 1
            int r0 = r0 + r3
            r9.rGroupIndex = r0
            androidx.compose.runtime.SlotReader r0 = r9.reader
            boolean r4 = r9.inserting
            androidx.compose.runtime.Composer$Companion r5 = androidx.compose.runtime.Composer.Companion
            if (r4 == 0) goto L4a
            int r4 = r0.emptyCount
            int r4 = r4 + r3
            r0.emptyCount = r4
            androidx.compose.runtime.SlotWriter r0 = r9.writer
            r5.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            r0.startGroup(r10, r3)
            r9.enterGroup(r1, r2)
            return
        L4a:
            int r4 = r0.getGroupKey()
            if (r4 != r10) goto L6a
            int r4 = r0.currentGroup
            int r6 = r0.currentEnd
            if (r4 >= r6) goto L63
            int r4 = r4 * 5
            int r4 = r4 + r3
            int[] r6 = r0.groups
            r4 = r6[r4]
            r6 = 536870912(0x20000000, float:1.0842022E-19)
            r4 = r4 & r6
            if (r4 == 0) goto L63
            goto L6a
        L63:
            r0.startGroup()
            r9.enterGroup(r1, r2)
            return
        L6a:
            int r4 = r0.emptyCount
            if (r4 <= 0) goto L6f
            goto L8b
        L6f:
            int r4 = r0.currentGroup
            int r6 = r0.currentEnd
            if (r4 != r6) goto L76
            goto L8b
        L76:
            int r6 = r9.nodeIndex
            r9.recordDelete()
            int r7 = r0.skipGroup()
            androidx.compose.runtime.changelist.ComposerChangeListWriter r8 = r9.changeListWriter
            r8.removeNode(r6, r7)
            java.util.List r6 = r9.invalidations
            int r7 = r0.currentGroup
            androidx.compose.runtime.ComposerKt.access$removeRange(r6, r4, r7)
        L8b:
            int r4 = r0.emptyCount
            int r4 = r4 + r3
            r0.emptyCount = r4
            r9.inserting = r3
            r9.providerCache = r2
            androidx.compose.runtime.SlotWriter r0 = r9.writer
            boolean r0 = r0.closed
            if (r0 == 0) goto La9
            androidx.compose.runtime.SlotTable r0 = r9.insertTable
            androidx.compose.runtime.SlotWriter r0 = r0.openWriter()
            r9.writer = r0
            r0.skipToGroupEnd()
            r9.writerHasAProvider = r1
            r9.providerCache = r2
        La9:
            androidx.compose.runtime.SlotWriter r0 = r9.writer
            r0.beginInsert()
            int r3 = r0.currentGroup
            r5.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
            r0.startGroup(r10, r4)
            androidx.compose.runtime.Anchor r10 = r0.anchor(r3)
            r9.insertAnchor = r10
            r9.enterGroup(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.startReplaceGroup(int):void");
    }

    public final void startReplaceableGroup(int i) {
        GroupKind.Companion.getClass();
        m331startBaiHCIY(i, 0, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.runtime.ComposerImpl startRestartGroup(int r4) {
        /*
            r3 = this;
            r3.startReplaceGroup(r4)
            boolean r4 = r3.inserting
            androidx.compose.runtime.ControlledComposition r0 = r3.composition
            if (r4 == 0) goto L23
            androidx.compose.runtime.RecomposeScopeImpl r4 = new androidx.compose.runtime.RecomposeScopeImpl
            androidx.compose.runtime.CompositionImpl r0 = (androidx.compose.runtime.CompositionImpl) r0
            r4.<init>(r0)
            java.util.ArrayList r0 = r3.invalidateStack
            r0.add(r4)
            r3.updateValue(r4)
            int r0 = r3.compositionToken
            r4.currentToken = r0
            int r0 = r4.flags
            r0 = r0 & (-17)
            r4.flags = r0
            return r3
        L23:
            java.util.List r4 = r3.invalidations
            androidx.compose.runtime.SlotReader r1 = r3.reader
            int r1 = r1.parent
            int r1 = androidx.compose.runtime.ComposerKt.findLocation(r1, r4)
            if (r1 < 0) goto L38
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            java.lang.Object r4 = r4.remove(r1)
            androidx.compose.runtime.Invalidation r4 = (androidx.compose.runtime.Invalidation) r4
            goto L39
        L38:
            r4 = 0
        L39:
            androidx.compose.runtime.SlotReader r1 = r3.reader
            java.lang.Object r1 = r1.next()
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r2 == 0) goto L57
            androidx.compose.runtime.RecomposeScopeImpl r1 = new androidx.compose.runtime.RecomposeScopeImpl
            androidx.compose.runtime.CompositionImpl r0 = (androidx.compose.runtime.CompositionImpl) r0
            r1.<init>(r0)
            r3.updateValue(r1)
            goto L59
        L57:
            androidx.compose.runtime.RecomposeScopeImpl r1 = (androidx.compose.runtime.RecomposeScopeImpl) r1
        L59:
            r0 = 0
            if (r4 != 0) goto L75
            int r4 = r1.flags
            r2 = r4 & 64
            if (r2 == 0) goto L64
            r2 = 1
            goto L65
        L64:
            r2 = r0
        L65:
            if (r2 == 0) goto L6b
            r4 = r4 & (-65)
            r1.flags = r4
        L6b:
            if (r2 == 0) goto L6e
            goto L75
        L6e:
            int r4 = r1.flags
            r4 = r4 & (-9)
            r1.flags = r4
            goto L7b
        L75:
            int r4 = r1.flags
            r4 = r4 | 8
            r1.flags = r4
        L7b:
            java.util.ArrayList r4 = r3.invalidateStack
            r4.add(r1)
            int r4 = r3.compositionToken
            r1.currentToken = r4
            int r4 = r1.flags
            r2 = r4 & (-17)
            r1.flags = r2
            r2 = r4 & 256(0x100, float:3.59E-43)
            if (r2 == 0) goto La5
            r4 = r4 & (-273(0xfffffffffffffeef, float:NaN))
            r4 = r4 | 512(0x200, float:7.17E-43)
            r1.flags = r4
            androidx.compose.runtime.changelist.ComposerChangeListWriter r4 = r3.changeListWriter
            androidx.compose.runtime.changelist.ChangeList r4 = r4.changeList
            r4.getClass()
            androidx.compose.runtime.changelist.Operation$StartResumingScope r2 = androidx.compose.runtime.changelist.Operation.StartResumingScope.INSTANCE
            androidx.compose.runtime.changelist.Operations r4 = r4.operations
            r4.pushOp(r2)
            androidx.compose.runtime.changelist.Operations.WriteScope.m338setObjectDKhxnng(r4, r0, r1)
        La5:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ComposerImpl.startRestartGroup(int):androidx.compose.runtime.ComposerImpl");
    }

    public final void startReusableGroup(Object obj) {
        if (!this.inserting && this.reader.getGroupKey() == 207 && !Intrinsics.areEqual(this.reader.getGroupAux(), obj) && this.reusingGroup < 0) {
            this.reusingGroup = this.reader.currentGroup;
            this.reusing = true;
        }
        GroupKind.Companion.getClass();
        m331startBaiHCIY(207, 0, null, obj);
    }

    public final void startReusableNode() {
        GroupKind.Companion.getClass();
        m331startBaiHCIY(125, GroupKind.ReusableNode, null, null);
        this.nodeExpected = true;
    }

    public final void startRoot() {
        this.rGroupIndex = 0;
        this.reader = this.slotTable.openReader();
        GroupKind.Companion companion = GroupKind.Companion;
        companion.getClass();
        m331startBaiHCIY(100, 0, null, null);
        CompositionContext compositionContext = this.parentContext;
        compositionContext.startComposing$runtime_release();
        PersistentCompositionLocalMap compositionLocalScope$runtime_release = compositionContext.getCompositionLocalScope$runtime_release();
        boolean z = this.providersInvalid;
        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
        this.providersInvalidStack.push(z ? 1 : 0);
        this.providersInvalid = changed(compositionLocalScope$runtime_release);
        this.providerCache = null;
        if (!this.forceRecomposeScopes) {
            this.forceRecomposeScopes = compositionContext.getCollectingParameterInformation$runtime_release();
        }
        if (!this.sourceMarkersEnabled) {
            this.sourceMarkersEnabled = compositionContext.getCollectingSourceInformation$runtime_release();
        }
        if (this.sourceMarkersEnabled) {
            compositionLocalScope$runtime_release = ((PersistentCompositionLocalHashMap) compositionLocalScope$runtime_release).putValue(CompositionErrorContextKt.LocalCompositionErrorContext, new StaticValueHolder(getErrorContext$runtime_release()));
        }
        this.rootProvider = compositionLocalScope$runtime_release;
        Set set = (Set) CompositionLocalMapKt.read(compositionLocalScope$runtime_release, InspectionTablesKt.LocalInspectionTables);
        if (set != null) {
            CompositionDataImpl compositionDataImpl = this._compositionData;
            if (compositionDataImpl == null) {
                compositionDataImpl = new CompositionDataImpl(this.composition);
                this._compositionData = compositionDataImpl;
            }
            set.add(compositionDataImpl);
            compositionContext.recordInspectionTable$runtime_release(set);
        }
        int compoundHashKey$runtime_release = compositionContext.getCompoundHashKey$runtime_release();
        companion.getClass();
        m331startBaiHCIY(compoundHashKey$runtime_release, 0, null, null);
    }

    public final boolean tryImminentInvalidation$runtime_release(RecomposeScopeImpl recomposeScopeImpl, Object obj) {
        Anchor anchor = recomposeScopeImpl.anchor;
        if (anchor == null) {
            return false;
        }
        int anchorIndex = this.reader.table.anchorIndex(anchor);
        if (!this.isComposing || anchorIndex < this.reader.currentGroup) {
            return false;
        }
        List list = this.invalidations;
        int findLocation = ComposerKt.findLocation(anchorIndex, list);
        if (findLocation < 0) {
            int i = -(findLocation + 1);
            if (!(obj instanceof DerivedState)) {
                obj = null;
            }
            ((ArrayList) list).add(i, new Invalidation(recomposeScopeImpl, anchorIndex, obj));
            return true;
        }
        Invalidation invalidation = (Invalidation) ((ArrayList) list).get(findLocation);
        if (!(obj instanceof DerivedState)) {
            invalidation.instances = null;
            return true;
        }
        Object obj2 = invalidation.instances;
        if (obj2 == null) {
            invalidation.instances = obj;
            return true;
        }
        if (obj2 instanceof MutableScatterSet) {
            ((MutableScatterSet) obj2).add(obj);
            return true;
        }
        int i2 = ScatterSetKt.$r8$clinit;
        MutableScatterSet mutableScatterSet = new MutableScatterSet(2);
        mutableScatterSet.plusAssign(obj2);
        mutableScatterSet.plusAssign(obj);
        invalidation.instances = mutableScatterSet;
        return true;
    }

    /* renamed from: updateComposerInvalidations-RY85e9Y, reason: not valid java name */
    public final void m332updateComposerInvalidationsRY85e9Y(MutableScatterMap mutableScatterMap) {
        Object[] objArr = mutableScatterMap.keys;
        Object[] objArr2 = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj = objArr[i4];
                            Object obj2 = objArr2[i4];
                            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) obj;
                            Anchor anchor = recomposeScopeImpl.anchor;
                            if (anchor != null) {
                                int i5 = anchor.location;
                                List list = this.invalidations;
                                if (obj2 == ScopeInvalidated.INSTANCE) {
                                    obj2 = null;
                                }
                                ((ArrayList) list).add(new Invalidation(recomposeScopeImpl, i5, obj2));
                            }
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
        CollectionsKt__MutableCollectionsJVMKt.sortWith(this.invalidations, ComposerKt.InvalidationLocationAscending);
    }

    public final void updateNodeCount(int i, int i2) {
        if (updatedNodeCount(i) != i2) {
            if (i >= 0) {
                int[] iArr = this.nodeCountOverrides;
                if (iArr == null) {
                    iArr = new int[this.reader.groupsSize];
                    Arrays.fill(iArr, 0, iArr.length, -1);
                    this.nodeCountOverrides = iArr;
                }
                iArr[i] = i2;
                return;
            }
            MutableIntIntMap mutableIntIntMap = this.nodeCountVirtualOverrides;
            if (mutableIntIntMap == null) {
                mutableIntIntMap = new MutableIntIntMap(0, 1, null);
                this.nodeCountVirtualOverrides = mutableIntIntMap;
            }
            mutableIntIntMap.set(i, i2);
        }
    }

    public final void updateNodeCountOverrides(int i, int i2) {
        int updatedNodeCount = updatedNodeCount(i);
        if (updatedNodeCount != i2) {
            int i3 = i2 - updatedNodeCount;
            int size = this.pendingStack.size() - 1;
            while (i != -1) {
                int updatedNodeCount2 = updatedNodeCount(i) + i3;
                updateNodeCount(i, updatedNodeCount2);
                int i4 = size;
                while (true) {
                    if (-1 < i4) {
                        Pending pending = (Pending) this.pendingStack.get(i4);
                        if (pending != null && pending.updateNodeCount(i, updatedNodeCount2)) {
                            size = i4 - 1;
                            break;
                        }
                        i4--;
                    } else {
                        break;
                    }
                }
                if (i < 0) {
                    i = this.reader.parent;
                } else if (this.reader.isNode(i)) {
                    return;
                } else {
                    i = this.reader.parent(i);
                }
            }
        }
    }

    public final PersistentCompositionLocalHashMap updateProviderMapGroup(PersistentCompositionLocalMap persistentCompositionLocalMap, PersistentCompositionLocalHashMap persistentCompositionLocalHashMap) {
        PersistentCompositionLocalHashMap persistentCompositionLocalHashMap2 = (PersistentCompositionLocalHashMap) persistentCompositionLocalMap;
        persistentCompositionLocalHashMap2.getClass();
        PersistentCompositionLocalHashMap.Builder builder = new PersistentCompositionLocalHashMap.Builder(persistentCompositionLocalHashMap2);
        builder.putAll(persistentCompositionLocalHashMap);
        PersistentCompositionLocalHashMap build = builder.build();
        startGroup(204, ComposerKt.providerMaps);
        nextSlot();
        updateValue(build);
        nextSlot();
        updateValue(persistentCompositionLocalHashMap);
        end(false);
        return build;
    }

    public final void updateRememberedValue(Object obj) {
        int i;
        SlotReader slotReader;
        int i2;
        SlotWriter slotWriter;
        if (obj instanceof RememberObserver) {
            RememberObserver rememberObserver = (RememberObserver) obj;
            Anchor anchor = null;
            if (this.inserting) {
                SlotWriter slotWriter2 = this.writer;
                EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
                int i3 = slotWriter2.currentGroup;
                if (i3 > slotWriter2.parent + 1) {
                    int i4 = i3 - 1;
                    int parent = slotWriter2.parent(i4, slotWriter2.groups);
                    while (true) {
                        i2 = i4;
                        i4 = parent;
                        slotWriter = this.writer;
                        if (i4 == slotWriter.parent || i4 < 0) {
                            break;
                        } else {
                            parent = slotWriter.parent(i4, slotWriter.groups);
                        }
                    }
                    anchor = slotWriter.anchor(i2);
                }
            } else {
                SlotReader slotReader2 = this.reader;
                EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$12 = ComposerKt.compositionTracer;
                int i5 = slotReader2.currentGroup;
                if (i5 > slotReader2.parent + 1) {
                    int i6 = i5 - 1;
                    int parent2 = slotReader2.parent(i6);
                    while (true) {
                        i = i6;
                        i6 = parent2;
                        slotReader = this.reader;
                        if (i6 == slotReader.parent || i6 < 0) {
                            break;
                        } else {
                            parent2 = slotReader.parent(i6);
                        }
                    }
                    anchor = slotReader.anchor(i);
                }
            }
            RememberObserverHolder rememberObserverHolder = new RememberObserverHolder(rememberObserver, anchor);
            if (this.inserting) {
                ChangeList changeList = this.changeListWriter.changeList;
                changeList.getClass();
                Operation.Remember remember = Operation.Remember.INSTANCE;
                Operations operations = changeList.operations;
                operations.pushOp(remember);
                Operations.WriteScope.m338setObjectDKhxnng(operations, 0, rememberObserverHolder);
            }
            this.abandonSet.add(obj);
            obj = rememberObserverHolder;
        }
        updateValue(obj);
    }

    public final void updateValue(Object obj) {
        if (this.inserting) {
            this.writer.update(obj);
            return;
        }
        SlotReader slotReader = this.reader;
        boolean z = slotReader.hadNext;
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        if (!z) {
            Anchor anchor = slotReader.anchor(slotReader.parent);
            ChangeList changeList = composerChangeListWriter.changeList;
            changeList.getClass();
            Operation.AppendValue appendValue = Operation.AppendValue.INSTANCE;
            Operations operations = changeList.operations;
            operations.pushOp(appendValue);
            Operations.WriteScope.m339setObjects4uCC6AY(operations, 0, anchor, 1, obj);
            return;
        }
        int access$slotAnchor = (slotReader.currentSlot - SlotTableKt.access$slotAnchor(slotReader.parent, slotReader.groups)) - 1;
        if (composerChangeListWriter.composer.reader.parent - composerChangeListWriter.writersReaderDelta >= 0) {
            composerChangeListWriter.realizeOperationLocation(true);
            ChangeList changeList2 = composerChangeListWriter.changeList;
            changeList2.getClass();
            Operation.UpdateValue updateValue = Operation.UpdateValue.INSTANCE;
            Operations operations2 = changeList2.operations;
            operations2.pushOp(updateValue);
            Operations.WriteScope.m338setObjectDKhxnng(operations2, 0, obj);
            operations2.intArgs[operations2.intArgsSize - operations2.opCodes[operations2.opCodesSize - 1].ints] = access$slotAnchor;
            return;
        }
        SlotReader slotReader2 = this.reader;
        Anchor anchor2 = slotReader2.anchor(slotReader2.parent);
        ChangeList changeList3 = composerChangeListWriter.changeList;
        changeList3.getClass();
        Operation.UpdateAnchoredValue updateAnchoredValue = Operation.UpdateAnchoredValue.INSTANCE;
        Operations operations3 = changeList3.operations;
        operations3.pushOp(updateAnchoredValue);
        Operations.WriteScope.m339setObjects4uCC6AY(operations3, 0, obj, 1, anchor2);
        operations3.intArgs[operations3.intArgsSize - operations3.opCodes[operations3.opCodesSize - 1].ints] = access$slotAnchor;
    }

    public final int updatedNodeCount(int i) {
        int i2;
        if (i >= 0) {
            int[] iArr = this.nodeCountOverrides;
            return (iArr == null || (i2 = iArr[i]) < 0) ? this.reader.nodeCount(i) : i2;
        }
        MutableIntIntMap mutableIntIntMap = this.nodeCountVirtualOverrides;
        if (mutableIntIntMap == null || mutableIntIntMap.findKeyIndex(i) < 0) {
            return 0;
        }
        return mutableIntIntMap.get(i);
    }

    public final void useNode() {
        if (!this.nodeExpected) {
            ComposerKt.composeImmediateRuntimeError("A call to createNode(), emitNode() or useNode() expected was not expected");
        }
        this.nodeExpected = false;
        if (this.inserting) {
            ComposerKt.composeImmediateRuntimeError("useNode() called while inserting");
        }
        SlotReader slotReader = this.reader;
        Object node = slotReader.node(slotReader.parent);
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        composerChangeListWriter.realizeNodeMovementOperations();
        composerChangeListWriter.pendingDownNodes.add(node);
        if (this.reusing && (node instanceof ComposeNodeLifecycleCallback)) {
            composerChangeListWriter.pushPendingUpsAndDowns();
            ChangeList changeList = composerChangeListWriter.changeList;
            changeList.getClass();
            if (node instanceof ComposeNodeLifecycleCallback) {
                changeList.operations.pushOp(Operation.UseCurrentNode.INSTANCE);
            }
        }
    }

    public final boolean changed(boolean z) {
        Object nextSlot = nextSlot();
        if ((nextSlot instanceof Boolean) && z == ((Boolean) nextSlot).booleanValue()) {
            return false;
        }
        updateValue(Boolean.valueOf(z));
        return true;
    }

    public final boolean changed(float f) {
        Object nextSlot = nextSlot();
        if ((nextSlot instanceof Float) && f == ((Number) nextSlot).floatValue()) {
            return false;
        }
        updateValue(Float.valueOf(f));
        return true;
    }

    public final boolean changed(long j) {
        Object nextSlot = nextSlot();
        if ((nextSlot instanceof Long) && j == ((Number) nextSlot).longValue()) {
            return false;
        }
        updateValue(Long.valueOf(j));
        return true;
    }

    public final boolean changed(int i) {
        Object nextSlot = nextSlot();
        if ((nextSlot instanceof Integer) && i == ((Number) nextSlot).intValue()) {
            return false;
        }
        updateValue(Integer.valueOf(i));
        return true;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CompositionContextHolder implements ReusableRememberObserver {
        public final CompositionContextImpl ref;

        public CompositionContextHolder(CompositionContextImpl compositionContextImpl) {
            this.ref = compositionContextImpl;
        }

        @Override // androidx.compose.runtime.RememberObserver
        public final void onAbandoned() {
            this.ref.dispose();
        }

        @Override // androidx.compose.runtime.RememberObserver
        public final void onForgotten() {
            this.ref.dispose();
        }

        @Override // androidx.compose.runtime.RememberObserver
        public final void onRemembered() {
        }
    }

    public final PersistentCompositionLocalMap currentCompositionLocalScope() {
        PersistentCompositionLocalMap persistentCompositionLocalMap = this.providerCache;
        return persistentCompositionLocalMap != null ? persistentCompositionLocalMap : currentCompositionLocalScope(this.reader.parent);
    }
}
