package androidx.compose.runtime;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.collection.MutableIntIntMap;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.GroupKind;
import androidx.compose.runtime.changelist.ChangeList;
import androidx.compose.runtime.changelist.ComposerChangeListWriter;
import androidx.compose.runtime.changelist.FixupList;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.changelist.Operations;
import androidx.compose.runtime.collection.MultiValueMap;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.collection.ScopeMap;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.IntRef;
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
import kotlin.Pair;
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
            Set hashSet = this.inspectionTables;
            if (hashSet == null) {
                hashSet = new HashSet();
                this.inspectionTables = hashSet;
            }
            hashSet.add(set);
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
                ComposerImpl composerImpl = this.this$0;
                composerImpl.childrenComposing--;
            }

            @Override // androidx.compose.runtime.DerivedStateObserver
            public final void start() {
                this.this$0.childrenComposing++;
            }
        };
        this.invalidateStack = new ArrayList();
        SlotReader slotReaderOpenReader = slotTable.openReader();
        slotReaderOpenReader.close();
        this.reader = slotReaderOpenReader;
        SlotTable slotTable2 = new SlotTable();
        if (compositionContext.getCollectingSourceInformation$runtime_release()) {
            slotTable2.collectSourceInformation();
        }
        if (compositionContext.getCollectingCallByInformation$runtime_release()) {
            slotTable2.calledByMap = new MutableIntObjectMap(i, i2, null);
        }
        this.insertTable = slotTable2;
        SlotWriter slotWriterOpenWriter = slotTable2.openWriter();
        slotWriterOpenWriter.close(true);
        this.writer = slotWriterOpenWriter;
        this.changeListWriter = new ComposerChangeListWriter(this, changeList);
        SlotReader slotReaderOpenReader2 = this.insertTable.openReader();
        try {
            Anchor anchor = slotReaderOpenReader2.anchor(0);
            slotReaderOpenReader2.close();
            this.insertAnchor = anchor;
            this.insertFixups = new FixupList();
            this.errorContext = new CompositionErrorContextImpl(this);
            CoroutineContext effectCoroutineContext = compositionContext.getEffectCoroutineContext();
            CoroutineContext errorContext$runtime_release = getErrorContext$runtime_release();
            this.applyCoroutineContext = effectCoroutineContext.plus(errorContext$runtime_release == null ? EmptyCoroutineContext.INSTANCE : errorContext$runtime_release);
        } catch (Throwable th) {
            slotReaderOpenReader2.close();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final MovableContentStateReference reportFreeMovableContent$movableContentReferenceFor(ComposerImpl composerImpl, int i) {
        ArrayList arrayList;
        int iGroupKey = composerImpl.reader.groupKey(i);
        SlotReader slotReader = composerImpl.reader;
        Object objObjectKey = slotReader.objectKey(i, slotReader.groups);
        if (iGroupKey != 126665345 || !(objObjectKey instanceof MovableContent)) {
            return null;
        }
        if (composerImpl.reader.containsMark(i)) {
            ArrayList arrayList2 = new ArrayList();
            reportFreeMovableContent$movableContentReferenceFor$traverseGroups(composerImpl, arrayList2, i);
            arrayList = !arrayList2.isEmpty() ? arrayList2 : null;
        }
        SlotReader slotReader2 = composerImpl.reader;
        MovableContent movableContent = (MovableContent) slotReader2.objectKey(i, slotReader2.groups);
        Object objGroupGet = composerImpl.reader.groupGet(i, 0);
        Anchor anchor = composerImpl.reader.anchor(i);
        int i2 = composerImpl.reader.groups[(i * 5) + 3] + i;
        List list = composerImpl.invalidations;
        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
        ArrayList arrayList3 = new ArrayList();
        int iFindLocation = ComposerKt.findLocation(i, list);
        if (iFindLocation < 0) {
            iFindLocation = -(iFindLocation + 1);
        }
        while (true) {
            ArrayList arrayList4 = (ArrayList) list;
            if (iFindLocation >= arrayList4.size()) {
                break;
            }
            Invalidation invalidation = (Invalidation) arrayList4.get(iFindLocation);
            if (invalidation.location >= i2) {
                break;
            }
            arrayList3.add(invalidation);
            iFindLocation++;
        }
        ArrayList arrayList5 = new ArrayList(arrayList3.size());
        int size = arrayList3.size();
        for (int i3 = 0; i3 < size; i3++) {
            Invalidation invalidation2 = (Invalidation) arrayList3.get(i3);
            arrayList5.add(new Pair(invalidation2.scope, invalidation2.instances));
        }
        return new MovableContentStateReference(movableContent, objGroupGet, composerImpl.composition, composerImpl.slotTable, anchor, arrayList5, composerImpl.currentCompositionLocalScope(i), arrayList);
    }

    public static final void reportFreeMovableContent$movableContentReferenceFor$traverseGroups(ComposerImpl composerImpl, List list, int i) {
        int i2 = composerImpl.reader.groups[(i * 5) + 3] + i;
        int i3 = i + 1;
        while (i3 < i2) {
            if (composerImpl.reader.hasMark(i3)) {
                MovableContentStateReference movableContentStateReferenceReportFreeMovableContent$movableContentReferenceFor = reportFreeMovableContent$movableContentReferenceFor(composerImpl, i3);
                if (movableContentStateReferenceReportFreeMovableContent$movableContentReferenceFor != null) {
                    ((ArrayList) list).add(movableContentStateReferenceReportFreeMovableContent$movableContentReferenceFor);
                }
            } else if (composerImpl.reader.containsMark(i3)) {
                reportFreeMovableContent$movableContentReferenceFor$traverseGroups(composerImpl, list, i3);
            }
            i3 += composerImpl.reader.groups[(i3 * 5) + 3];
        }
    }

    public static final int reportFreeMovableContent$reportGroup(ComposerImpl composerImpl, int i, int i2, boolean z, int i3) {
        SlotReader slotReader = composerImpl.reader;
        boolean zHasMark = slotReader.hasMark(i2);
        ComposerChangeListWriter composerChangeListWriter = composerImpl.changeListWriter;
        int[] iArr = slotReader.groups;
        if (zHasMark) {
            int iGroupKey = slotReader.groupKey(i2);
            Object objObjectKey = slotReader.objectKey(i2, iArr);
            CompositionContext compositionContext = composerImpl.parentContext;
            if (iGroupKey == 126665345 && (objObjectKey instanceof MovableContent)) {
                MovableContentStateReference movableContentStateReferenceReportFreeMovableContent$movableContentReferenceFor = reportFreeMovableContent$movableContentReferenceFor(composerImpl, i2);
                if (movableContentStateReferenceReportFreeMovableContent$movableContentReferenceFor != null) {
                    compositionContext.deletedMovableContent$runtime_release(movableContentStateReferenceReportFreeMovableContent$movableContentReferenceFor);
                    composerChangeListWriter.recordSlotEditing();
                    ChangeList changeList = composerChangeListWriter.changeList;
                    changeList.getClass();
                    Operation.ReleaseMovableGroupAtCurrent releaseMovableGroupAtCurrent = Operation.ReleaseMovableGroupAtCurrent.INSTANCE;
                    Operations operations = changeList.operations;
                    operations.pushOp(releaseMovableGroupAtCurrent);
                    Operations.WriteScope.m341setObjectst7hvbck(operations, composerImpl.composition, composerImpl.parentContext, movableContentStateReferenceReportFreeMovableContent$movableContentReferenceFor);
                }
                if (!z || i2 == i) {
                    return slotReader.nodeCount(i2);
                }
                composerChangeListWriter.realizeNodeMovementOperations();
                composerChangeListWriter.pushPendingUpsAndDowns();
                ComposerImpl composerImpl2 = composerChangeListWriter.composer;
                int iNodeCount = composerImpl2.reader.isNode(i2) ? 1 : composerImpl2.reader.nodeCount(i2);
                if (iNodeCount > 0) {
                    composerChangeListWriter.removeNode(i3, iNodeCount);
                }
                return 0;
            }
            if (iGroupKey == 206 && Intrinsics.areEqual(objObjectKey, ComposerKt.reference)) {
                Object objGroupGet = slotReader.groupGet(i2, 0);
                CompositionContextHolder compositionContextHolder = objGroupGet instanceof CompositionContextHolder ? (CompositionContextHolder) objGroupGet : null;
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
                                    compositionImpl.composer.m333updateComposerInvalidationsRY85e9Y(mutableScatterMap);
                                    Unit unit = Unit.INSTANCE;
                                } finally {
                                }
                            }
                            ChangeList changeList2 = new ChangeList();
                            composerImpl3.deferredChanges = changeList2;
                            SlotReader slotReaderOpenReader = composerImpl3.slotTable.openReader();
                            try {
                                composerImpl3.reader = slotReaderOpenReader;
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
                                slotReaderOpenReader.close();
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
            int iReportFreeMovableContent$reportGroup = 0;
            for (int i5 = i2 + 1; i5 < i4; i5 += iArr[(i5 * 5) + 3]) {
                boolean zIsNode = slotReader.isNode(i5);
                if (zIsNode) {
                    composerChangeListWriter.realizeNodeMovementOperations();
                    Object objNode = slotReader.node(i5);
                    composerChangeListWriter.realizeNodeMovementOperations();
                    composerChangeListWriter.pendingDownNodes.add(objNode);
                }
                iReportFreeMovableContent$reportGroup += reportFreeMovableContent$reportGroup(composerImpl, i, i5, zIsNode || z, zIsNode ? 0 : i3 + iReportFreeMovableContent$reportGroup);
                if (zIsNode) {
                    composerChangeListWriter.realizeNodeMovementOperations();
                    composerChangeListWriter.moveUp();
                }
            }
            if (!slotReader.isNode(i2)) {
                return iReportFreeMovableContent$reportGroup;
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
            Operations.WriteScope.m339setObjectDKhxnng(operations, 0, obj);
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
            Operations.WriteScope.m339setObjectDKhxnng(operations, 1, function2);
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
        Operations.WriteScope.m340setObjects4uCC6AY(operations2, 0, obj, 1, function2);
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
        Operations.WriteScope.m339setObjectDKhxnng(operations, 0, function0);
        operations.intArgs[operations.intArgsSize - operations.opCodes[operations.opCodesSize - 1].ints] = i;
        Operations.WriteScope.m339setObjectDKhxnng(operations, 1, anchor);
        Operation.PostInsertNodeFixup postInsertNodeFixup = Operation.PostInsertNodeFixup.INSTANCE;
        Operations operations2 = fixupList.pendingOperations;
        operations2.pushOp(postInsertNodeFixup);
        operations2.intArgs[operations2.intArgsSize - operations2.opCodes[operations2.opCodesSize - 1].ints] = i;
        Operations.WriteScope.m339setObjectDKhxnng(operations2, 0, anchor);
    }

    public final PersistentCompositionLocalMap currentCompositionLocalScope(int i) {
        PersistentCompositionLocalMap persistentCompositionLocalMap;
        if (this.inserting && this.writerHasAProvider) {
            int iParent = this.writer.parent;
            while (iParent > 0) {
                SlotWriter slotWriter = this.writer;
                if (slotWriter.groups[slotWriter.groupIndexToAddress(iParent) * 5] == 202 && Intrinsics.areEqual(this.writer.groupObjectKey(iParent), ComposerKt.compositionLocalMap)) {
                    PersistentCompositionLocalMap persistentCompositionLocalMap2 = (PersistentCompositionLocalMap) this.writer.groupAux(iParent);
                    this.providerCache = persistentCompositionLocalMap2;
                    return persistentCompositionLocalMap2;
                }
                SlotWriter slotWriter2 = this.writer;
                iParent = slotWriter2.parent(iParent, slotWriter2.groups);
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x008f A[Catch: all -> 0x008d, TRY_LEAVE, TryCatch #2 {all -> 0x008d, blocks: (B:14:0x0047, B:16:0x004f, B:17:0x0062, B:20:0x0068, B:22:0x0075, B:25:0x008f), top: B:49:0x0047, outer: #3 }] */
    /* renamed from: doCompose-aFTiNEg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m331doComposeaFTiNEg(MutableScatterMap mutableScatterMap, ComposableLambdaImpl composableLambdaImpl) {
        if (this.isComposing) {
            ComposerKt.composeImmediateRuntimeError("Reentrant composition is not supported");
        }
        Trace.INSTANCE.getClass();
        android.os.Trace.beginSection("Compose:recompose");
        try {
            this.compositionToken = Long.hashCode(SnapshotKt.currentSnapshot().getSnapshotId());
            this.providerUpdates = null;
            m333updateComposerInvalidationsRY85e9Y(mutableScatterMap);
            this.nodeIndex = 0;
            this.isComposing = true;
            try {
                startRoot();
                Object objNextSlot = nextSlot();
                if (objNextSlot != composableLambdaImpl && composableLambdaImpl != null) {
                    updateValue(composableLambdaImpl);
                }
                ComposerImpl$derivedStateObserver$1 composerImpl$derivedStateObserver$1 = this.derivedStateObserver;
                MutableVector mutableVectorDerivedStateObservers = SnapshotStateKt.derivedStateObservers();
                try {
                    mutableVectorDerivedStateObservers.add(composerImpl$derivedStateObserver$1);
                    if (composableLambdaImpl != null) {
                        startGroup(200, ComposerKt.invocation);
                        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, composableLambdaImpl);
                        composableLambdaImpl.invoke((Object) this, (Object) 1);
                        end(false);
                    } else if (!this.providersInvalid || objNextSlot == null) {
                        skipCurrentGroup();
                    } else {
                        Composer.Companion.getClass();
                        if (!objNextSlot.equals(Composer.Companion.Empty)) {
                            startGroup(200, ComposerKt.invocation);
                            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, objNextSlot);
                            Function2 function2 = (Function2) objNextSlot;
                            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
                            function2.invoke(this, 1);
                            end(false);
                        }
                    }
                    mutableVectorDerivedStateObservers.removeAt(mutableVectorDerivedStateObservers.size - 1);
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
                    mutableVectorDerivedStateObservers.removeAt(mutableVectorDerivedStateObservers.size - 1);
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
            Object objNode = this.reader.node(i);
            ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
            composerChangeListWriter.realizeNodeMovementOperations();
            composerChangeListWriter.pendingDownNodes.add(objNode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [boolean, int] */
    public final void end(boolean z) {
        int iHashCode;
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
        int iHashCode2;
        IntStack intStack2 = this.parentStateStack;
        int i7 = 1;
        int i8 = intStack2.slots[intStack2.tos - 2] - 1;
        boolean z2 = this.inserting;
        Composer.Companion companion = Composer.Companion;
        if (z2) {
            SlotWriter slotWriter = this.writer;
            int i9 = slotWriter.parent;
            int i10 = slotWriter.groups[slotWriter.groupIndexToAddress(i9) * 5];
            Object objGroupObjectKey = this.writer.groupObjectKey(i9);
            Object objGroupAux = this.writer.groupAux(i9);
            if (objGroupObjectKey == null) {
                if (objGroupAux != null && i10 == 207) {
                    companion.getClass();
                    if (!objGroupAux.equals(Composer.Companion.Empty)) {
                        this.compoundKeyHash = Integer.rotateRight(Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(objGroupAux.hashCode()), 3);
                    }
                }
                iHashCode2 = Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(i10);
            } else {
                iHashCode2 = Integer.hashCode(objGroupObjectKey instanceof Enum ? ((Enum) objGroupObjectKey).ordinal() : objGroupObjectKey.hashCode()) ^ Integer.rotateRight(this.compoundKeyHash, 3);
            }
            this.compoundKeyHash = Integer.rotateRight(iHashCode2, 3);
        } else {
            SlotReader slotReader = this.reader;
            int i11 = slotReader.parent;
            int iGroupKey = slotReader.groupKey(i11);
            SlotReader slotReader2 = this.reader;
            Object objObjectKey = slotReader2.objectKey(i11, slotReader2.groups);
            SlotReader slotReader3 = this.reader;
            Object objAux = slotReader3.aux(i11, slotReader3.groups);
            if (objObjectKey == null) {
                if (objAux != null && iGroupKey == 207) {
                    companion.getClass();
                    if (!objAux.equals(Composer.Companion.Empty)) {
                        this.compoundKeyHash = Integer.rotateRight(Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(objAux.hashCode()), 3);
                    }
                }
                iHashCode = Integer.rotateRight(i8 ^ this.compoundKeyHash, 3) ^ Integer.hashCode(iGroupKey);
            } else {
                iHashCode = Integer.hashCode(objObjectKey instanceof Enum ? ((Enum) objObjectKey).ordinal() : objObjectKey.hashCode()) ^ Integer.rotateRight(this.compoundKeyHash, 3);
            }
            this.compoundKeyHash = Integer.rotateRight(iHashCode, 3);
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
                boolean zContains = hashSet2.contains(keyInfo);
                MutableIntObjectMap mutableIntObjectMap = pending2.groupInfos;
                IntStack intStack3 = intStack2;
                int i18 = pending2.startIndex;
                if (!zContains) {
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
            if (slotReader6.emptyCount > 0 || (i3 = slotReader6.currentGroup) == slotReader6.currentEnd) {
                break;
            }
            recordDelete();
            composerChangeListWriter.removeNode(i36, this.reader.skipGroup());
            ComposerKt.access$removeRange(this.invalidations, i3, this.reader.currentGroup);
            i2 = i2;
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
                    Operations.WriteScope.m340setObjects4uCC6AY(operations4, 0, anchor, 1, slotTable);
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
                    Operations.WriteScope.m341setObjectst7hvbck(operations5, anchor, slotTable2, fixupList2);
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0080 A[EDGE_INSN: B:57:0x0080->B:29:0x0080 BREAK  A[LOOP:0: B:16:0x003a->B:28:0x007c], EDGE_INSN: B:58:0x0080->B:29:0x0080 BREAK  A[LOOP:0: B:16:0x003a->B:28:0x007c]] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final RecomposeScopeImpl endRestartGroup() {
        final RecomposeScopeImpl recomposeScopeImpl;
        RecomposeScopeImpl recomposeScopeImpl2;
        Anchor anchor;
        Function1 function1;
        int i;
        if (this.invalidateStack.isEmpty()) {
            recomposeScopeImpl = null;
        } else {
            ArrayList arrayList = this.invalidateStack;
            recomposeScopeImpl = (RecomposeScopeImpl) arrayList.remove(arrayList.size() - 1);
        }
        if (recomposeScopeImpl != null) {
            int i2 = recomposeScopeImpl.flags;
            recomposeScopeImpl.flags = i2 & (-9);
            final int i3 = this.compositionToken;
            final MutableObjectIntMap mutableObjectIntMap = recomposeScopeImpl.trackedInstances;
            if (mutableObjectIntMap == null || (i2 & 16) != 0) {
                function1 = null;
                ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
                if (function1 != null) {
                    ChangeList changeList = composerChangeListWriter.changeList;
                    changeList.getClass();
                    Operation.EndCompositionScope endCompositionScope = Operation.EndCompositionScope.INSTANCE;
                    Operations operations = changeList.operations;
                    operations.pushOp(endCompositionScope);
                    Operations.WriteScope.m340setObjects4uCC6AY(operations, 0, function1, 1, this.composition);
                }
                i = recomposeScopeImpl.flags;
                if ((i & 512) != 0) {
                    recomposeScopeImpl.flags = i & (-513);
                    ChangeList changeList2 = composerChangeListWriter.changeList;
                    changeList2.getClass();
                    Operation.EndResumingScope endResumingScope = Operation.EndResumingScope.INSTANCE;
                    Operations operations2 = changeList2.operations;
                    operations2.pushOp(endResumingScope);
                    Operations.WriteScope.m339setObjectDKhxnng(operations2, 0, recomposeScopeImpl);
                }
            } else {
                Object[] objArr = mutableObjectIntMap.keys;
                int[] iArr = mutableObjectIntMap.values;
                long[] jArr = mutableObjectIntMap.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i4 = 0;
                    loop0: while (true) {
                        long j = jArr[i4];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i5 = 8 - ((~(i4 - length)) >>> 31);
                            for (int i6 = 0; i6 < i5; i6++) {
                                if ((j & 255) < 128) {
                                    int i7 = (i4 << 3) + i6;
                                    Object obj = objArr[i7];
                                    if (iArr[i7] != i3) {
                                        function1 = new Function1() { // from class: androidx.compose.runtime.RecomposeScopeImpl$end$1$2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj2) {
                                                Composition composition;
                                                Composition composition2;
                                                int i8;
                                                Composition composition3 = (Composition) obj2;
                                                RecomposeScopeImpl recomposeScopeImpl3 = recomposeScopeImpl;
                                                if (recomposeScopeImpl3.currentToken == i3 && Intrinsics.areEqual(mutableObjectIntMap, recomposeScopeImpl3.trackedInstances) && (composition3 instanceof CompositionImpl)) {
                                                    MutableObjectIntMap mutableObjectIntMap2 = mutableObjectIntMap;
                                                    int i9 = i3;
                                                    RecomposeScopeImpl recomposeScopeImpl4 = recomposeScopeImpl;
                                                    long[] jArr2 = mutableObjectIntMap2.metadata;
                                                    int length2 = jArr2.length - 2;
                                                    if (length2 >= 0) {
                                                        int i10 = 0;
                                                        while (true) {
                                                            long j2 = jArr2[i10];
                                                            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                int i11 = 8;
                                                                int i12 = 8 - ((~(i10 - length2)) >>> 31);
                                                                int i13 = 0;
                                                                while (i13 < i12) {
                                                                    if ((255 & j2) < 128) {
                                                                        int i14 = (i10 << 3) + i13;
                                                                        Object obj3 = mutableObjectIntMap2.keys[i14];
                                                                        boolean z = mutableObjectIntMap2.values[i14] != i9;
                                                                        if (z) {
                                                                            CompositionImpl compositionImpl = (CompositionImpl) composition3;
                                                                            i8 = i11;
                                                                            ScopeMap.m349removeimpl(compositionImpl.observations, obj3, recomposeScopeImpl4);
                                                                            if (obj3 instanceof DerivedState) {
                                                                                DerivedState derivedState = (DerivedState) obj3;
                                                                                composition2 = composition3;
                                                                                if (!compositionImpl.observations.containsKey(derivedState)) {
                                                                                    ScopeMap.m350removeScopeimpl(compositionImpl.derivedStates, derivedState);
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
                                                                            i8 = i11;
                                                                        }
                                                                        if (z) {
                                                                            mutableObjectIntMap2.removeValueAt(i14);
                                                                        }
                                                                    } else {
                                                                        composition2 = composition3;
                                                                        i8 = i11;
                                                                    }
                                                                    j2 >>= i8;
                                                                    i13++;
                                                                    i11 = i8;
                                                                    composition3 = composition2;
                                                                }
                                                                composition = composition3;
                                                                if (i12 != i11) {
                                                                    break;
                                                                }
                                                            } else {
                                                                composition = composition3;
                                                            }
                                                            if (i10 == length2) {
                                                                break;
                                                            }
                                                            i10++;
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
                            if (i5 != 8) {
                                break;
                            }
                            if (i4 == length) {
                                break;
                            }
                            i4++;
                        }
                    }
                    function1 = null;
                    ComposerChangeListWriter composerChangeListWriter2 = this.changeListWriter;
                    if (function1 != null) {
                    }
                    i = recomposeScopeImpl.flags;
                    if ((i & 512) != 0) {
                    }
                }
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
            } else {
                recomposeScopeImpl2 = null;
            }
        }
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
        int iPop = this.providersInvalidStack.pop();
        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
        this.providersInvalid = iPop != 0;
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
        SlotWriter slotWriterOpenWriter = slotTable.openWriter();
        slotWriterOpenWriter.close(true);
        this.writer = slotWriterOpenWriter;
    }

    public final RecomposeScopeImpl getCurrentRecomposeScope$runtime_release() {
        ArrayList arrayList = this.invalidateStack;
        if (this.childrenComposing != 0 || arrayList.isEmpty()) {
            return null;
        }
        return (RecomposeScopeImpl) AlertController$$ExternalSyntheticOutline0.m(1, arrayList);
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

    /* JADX WARN: Removed duplicated region for block: B:56:0x0127 A[Catch: all -> 0x0208, TryCatch #5 {all -> 0x0208, blocks: (B:7:0x0023, B:9:0x0039, B:29:0x00bb, B:52:0x010a, B:54:0x011e, B:56:0x0127, B:58:0x0132, B:51:0x0108, B:33:0x00c5), top: B:122:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01bc A[Catch: all -> 0x01fb, TRY_LEAVE, TryCatch #7 {all -> 0x01fb, blocks: (B:70:0x0173, B:79:0x01af, B:81:0x01bc, B:95:0x01f8, B:96:0x01fa, B:71:0x0185, B:78:0x01ad, B:92:0x01f4, B:93:0x01f6), top: B:125:0x0173, outer: #1, inners: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void insertMovableContentGuarded(List list) throws Throwable {
        ChangeList changeList;
        ChangeList changeList2;
        SlotTable slotTable;
        int i;
        Anchor anchor;
        ArrayList arrayList;
        final SlotReader slotReaderOpenReader;
        SlotReader slotReader;
        int[] iArr;
        MutableIntObjectMap mutableIntObjectMap;
        ChangeList changeList3;
        boolean z;
        boolean z2;
        SlotTable slotTable2;
        SlotTable slotTable3;
        int i2 = 1;
        SlotTable slotTable4 = this.slotTable;
        CompositionContext compositionContext = this.parentContext;
        ChangeList changeList4 = this.lateChanges;
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        ChangeList changeList5 = composerChangeListWriter.changeList;
        try {
            composerChangeListWriter.changeList = changeList4;
            changeList4.getClass();
            changeList4.operations.pushOp(Operation.ResetSlots.INSTANCE);
            int size = list.size();
            int i3 = 0;
            int i4 = 0;
            while (i4 < size) {
                try {
                    Pair pair = (Pair) list.get(i4);
                    final MovableContentStateReference movableContentStateReference = (MovableContentStateReference) pair.component1();
                    MovableContentStateReference movableContentStateReference2 = (MovableContentStateReference) pair.component2();
                    Anchor anchor2 = movableContentStateReference.anchor;
                    SlotTable slotTable5 = movableContentStateReference.slotTable;
                    int iAnchorIndex = slotTable5.anchorIndex(anchor2);
                    int i5 = size;
                    IntRef intRef = new IntRef(i3, i2, null);
                    composerChangeListWriter.pushPendingUpsAndDowns();
                    ChangeList changeList6 = composerChangeListWriter.changeList;
                    changeList6.getClass();
                    Operation.DetermineMovableContentNodeIndex determineMovableContentNodeIndex = Operation.DetermineMovableContentNodeIndex.INSTANCE;
                    Operations operations = changeList6.operations;
                    operations.pushOp(determineMovableContentNodeIndex);
                    Operations.WriteScope.m340setObjects4uCC6AY(operations, i3, intRef, 1, anchor2);
                    if (movableContentStateReference2 == null) {
                        if (slotTable5.equals(this.insertTable)) {
                            if (!this.writer.closed) {
                                ComposerKt.composeImmediateRuntimeError("Check failed");
                            }
                            forceFreshInsertTable();
                        }
                        slotReaderOpenReader = slotTable5.openReader();
                        try {
                            slotReaderOpenReader.reposition(iAnchorIndex);
                            composerChangeListWriter.writersReaderDelta = iAnchorIndex;
                            final ChangeList changeList7 = new ChangeList();
                            recomposeMovableContent(null, null, null, EmptyList.INSTANCE, new Function0() { // from class: androidx.compose.runtime.ComposerImpl$insertMovableContentGuarded$1$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    ComposerImpl composerImpl = this.this$0;
                                    ComposerChangeListWriter composerChangeListWriter2 = composerImpl.changeListWriter;
                                    ChangeList changeList8 = changeList7;
                                    SlotReader slotReader2 = slotReaderOpenReader;
                                    MovableContentStateReference movableContentStateReference3 = movableContentStateReference;
                                    ChangeList changeList9 = composerChangeListWriter2.changeList;
                                    try {
                                        composerChangeListWriter2.changeList = changeList8;
                                        SlotReader slotReader3 = composerImpl.reader;
                                        int[] iArr2 = composerImpl.nodeCountOverrides;
                                        MutableIntObjectMap mutableIntObjectMap2 = composerImpl.providerUpdates;
                                        composerImpl.nodeCountOverrides = null;
                                        composerImpl.providerUpdates = null;
                                        try {
                                            composerImpl.reader = slotReader2;
                                            boolean z3 = composerChangeListWriter2.implicitRootStart;
                                            try {
                                                composerChangeListWriter2.implicitRootStart = false;
                                                composerImpl.invokeMovableContentLambda(movableContentStateReference3.content, movableContentStateReference3.locals, movableContentStateReference3.parameter, true);
                                                composerChangeListWriter2.implicitRootStart = z3;
                                                Unit unit = Unit.INSTANCE;
                                                composerChangeListWriter2.changeList = changeList9;
                                                return Unit.INSTANCE;
                                            } catch (Throwable th) {
                                                composerChangeListWriter2.implicitRootStart = z3;
                                                throw th;
                                            }
                                        } finally {
                                            composerImpl.reader = slotReader3;
                                            composerImpl.nodeCountOverrides = iArr2;
                                            composerImpl.providerUpdates = mutableIntObjectMap2;
                                        }
                                    } catch (Throwable th2) {
                                        composerChangeListWriter2.changeList = changeList9;
                                        throw th2;
                                    }
                                }
                            });
                            ChangeList changeList8 = composerChangeListWriter.changeList;
                            changeList8.getClass();
                            if (changeList7.operations.isNotEmpty()) {
                                Operation.ApplyChangeList applyChangeList = Operation.ApplyChangeList.INSTANCE;
                                Operations operations2 = changeList8.operations;
                                operations2.pushOp(applyChangeList);
                                Operations.WriteScope.m340setObjects4uCC6AY(operations2, i3, changeList7, 1, intRef);
                            }
                            Unit unit = Unit.INSTANCE;
                            slotReaderOpenReader.close();
                            slotTable2 = slotTable4;
                            changeList2 = changeList5;
                            i = i4;
                        } finally {
                        }
                    } else {
                        MovableContentState movableContentStateMovableContentStateResolve$runtime_release = compositionContext.movableContentStateResolve$runtime_release(movableContentStateReference2);
                        if (movableContentStateMovableContentStateResolve$runtime_release == null || (slotTable = movableContentStateMovableContentStateResolve$runtime_release.slotTable) == null) {
                            slotTable = movableContentStateReference2.slotTable;
                        }
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    if (movableContentStateMovableContentStateResolve$runtime_release == null || (slotTable3 = movableContentStateMovableContentStateResolve$runtime_release.slotTable) == null) {
                                                        i = i4;
                                                    } else {
                                                        if (slotTable3.writer) {
                                                            ComposerKt.composeImmediateRuntimeError("use active SlotWriter to create an anchor location instead");
                                                        }
                                                        if (slotTable3.groupsSize <= 0) {
                                                            PreconditionsKt.throwIllegalArgumentException("Parameter index is out of range");
                                                        }
                                                        ArrayList arrayList2 = slotTable3.anchors;
                                                        i = i4;
                                                        int iSearch = SlotTableKt.search(arrayList2, 0, slotTable3.groupsSize);
                                                        if (iSearch < 0) {
                                                            anchor = new Anchor(0);
                                                            arrayList2.add(-(iSearch + 1), anchor);
                                                        } else {
                                                            anchor = (Anchor) arrayList2.get(iSearch);
                                                        }
                                                        if (anchor == null) {
                                                        }
                                                        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
                                                        arrayList = new ArrayList();
                                                        slotReaderOpenReader = slotTable.openReader();
                                                        ComposerKt.collectNodesFrom$lambda$10$collectFromGroup(slotReaderOpenReader, arrayList, slotTable.anchorIndex(anchor));
                                                        Unit unit2 = Unit.INSTANCE;
                                                        slotReaderOpenReader.close();
                                                        if (arrayList.isEmpty()) {
                                                            ChangeList changeList9 = composerChangeListWriter.changeList;
                                                            changeList9.getClass();
                                                            if (arrayList.isEmpty()) {
                                                                changeList2 = changeList5;
                                                            } else {
                                                                Operation.CopyNodesToNewAnchorLocation copyNodesToNewAnchorLocation = Operation.CopyNodesToNewAnchorLocation.INSTANCE;
                                                                Operations operations3 = changeList9.operations;
                                                                operations3.pushOp(copyNodesToNewAnchorLocation);
                                                                changeList2 = changeList5;
                                                                try {
                                                                    Operations.WriteScope.m340setObjects4uCC6AY(operations3, 1, arrayList, 0, intRef);
                                                                } catch (Throwable th) {
                                                                    th = th;
                                                                    changeList = changeList2;
                                                                    composerChangeListWriter.changeList = changeList;
                                                                    throw th;
                                                                }
                                                            }
                                                            if (slotTable5.equals(slotTable4)) {
                                                                int iAnchorIndex2 = slotTable4.anchorIndex(anchor2);
                                                                updateNodeCount(iAnchorIndex2, updatedNodeCount(iAnchorIndex2) + arrayList.size());
                                                            }
                                                        } else {
                                                            changeList2 = changeList5;
                                                        }
                                                        composerChangeListWriter.copySlotTableToAnchorLocation(movableContentStateMovableContentStateResolve$runtime_release, compositionContext, movableContentStateReference2, movableContentStateReference);
                                                        slotReaderOpenReader = slotTable.openReader();
                                                        slotReader = this.reader;
                                                        iArr = this.nodeCountOverrides;
                                                        mutableIntObjectMap = this.providerUpdates;
                                                        this.nodeCountOverrides = null;
                                                        this.providerUpdates = null;
                                                        this.reader = slotReaderOpenReader;
                                                        int iAnchorIndex3 = slotTable.anchorIndex(anchor);
                                                        slotReaderOpenReader.reposition(iAnchorIndex3);
                                                        composerChangeListWriter.writersReaderDelta = iAnchorIndex3;
                                                        changeList3 = new ChangeList();
                                                        ChangeList changeList10 = composerChangeListWriter.changeList;
                                                        composerChangeListWriter.changeList = changeList3;
                                                        z = composerChangeListWriter.implicitRootStart;
                                                        composerChangeListWriter.implicitRootStart = false;
                                                        slotTable2 = slotTable4;
                                                        z2 = z;
                                                        recomposeMovableContent(movableContentStateReference2.composition, movableContentStateReference.composition, Integer.valueOf(slotReaderOpenReader.currentGroup), movableContentStateReference2.invalidations, new Function0() { // from class: androidx.compose.runtime.ComposerImpl$insertMovableContentGuarded$1$1$2$1$1$1$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                ComposerImpl composerImpl = this.this$0;
                                                                MovableContentStateReference movableContentStateReference3 = movableContentStateReference;
                                                                composerImpl.invokeMovableContentLambda(movableContentStateReference3.content, movableContentStateReference3.locals, movableContentStateReference3.parameter, true);
                                                                return Unit.INSTANCE;
                                                            }
                                                        });
                                                        composerChangeListWriter.implicitRootStart = z2;
                                                        composerChangeListWriter.changeList = changeList10;
                                                        changeList10.getClass();
                                                        if (changeList3.operations.isNotEmpty()) {
                                                            Operation.ApplyChangeList applyChangeList2 = Operation.ApplyChangeList.INSTANCE;
                                                            Operations operations4 = changeList10.operations;
                                                            operations4.pushOp(applyChangeList2);
                                                            Operations.WriteScope.m340setObjects4uCC6AY(operations4, 0, changeList3, 1, intRef);
                                                        }
                                                    }
                                                    recomposeMovableContent(movableContentStateReference2.composition, movableContentStateReference.composition, Integer.valueOf(slotReaderOpenReader.currentGroup), movableContentStateReference2.invalidations, new Function0() { // from class: androidx.compose.runtime.ComposerImpl$insertMovableContentGuarded$1$1$2$1$1$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            ComposerImpl composerImpl = this.this$0;
                                                            MovableContentStateReference movableContentStateReference3 = movableContentStateReference;
                                                            composerImpl.invokeMovableContentLambda(movableContentStateReference3.content, movableContentStateReference3.locals, movableContentStateReference3.parameter, true);
                                                            return Unit.INSTANCE;
                                                        }
                                                    });
                                                    composerChangeListWriter.implicitRootStart = z2;
                                                    composerChangeListWriter.changeList = changeList10;
                                                    changeList10.getClass();
                                                    if (changeList3.operations.isNotEmpty()) {
                                                    }
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    composerChangeListWriter.implicitRootStart = z2;
                                                    throw th;
                                                }
                                                slotTable2 = slotTable4;
                                                z2 = z;
                                            } catch (Throwable th3) {
                                                th = th3;
                                                z2 = z;
                                            }
                                            composerChangeListWriter.implicitRootStart = false;
                                        } catch (Throwable th4) {
                                            th = th4;
                                            z2 = z;
                                        }
                                        composerChangeListWriter.changeList = changeList3;
                                        z = composerChangeListWriter.implicitRootStart;
                                    } finally {
                                    }
                                    this.reader = slotReaderOpenReader;
                                    int iAnchorIndex32 = slotTable.anchorIndex(anchor);
                                    slotReaderOpenReader.reposition(iAnchorIndex32);
                                    composerChangeListWriter.writersReaderDelta = iAnchorIndex32;
                                    changeList3 = new ChangeList();
                                    ChangeList changeList102 = composerChangeListWriter.changeList;
                                } finally {
                                    this.reader = slotReader;
                                    this.nodeCountOverrides = iArr;
                                    this.providerUpdates = mutableIntObjectMap;
                                }
                                slotReader = this.reader;
                                iArr = this.nodeCountOverrides;
                                mutableIntObjectMap = this.providerUpdates;
                                this.nodeCountOverrides = null;
                                this.providerUpdates = null;
                            } finally {
                            }
                            ComposerKt.collectNodesFrom$lambda$10$collectFromGroup(slotReaderOpenReader, arrayList, slotTable.anchorIndex(anchor));
                            Unit unit22 = Unit.INSTANCE;
                            slotReaderOpenReader.close();
                            if (arrayList.isEmpty()) {
                            }
                            composerChangeListWriter.copySlotTableToAnchorLocation(movableContentStateMovableContentStateResolve$runtime_release, compositionContext, movableContentStateReference2, movableContentStateReference);
                            slotReaderOpenReader = slotTable.openReader();
                        } catch (Throwable th5) {
                            throw th5;
                        }
                        anchor = movableContentStateReference2.anchor;
                        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$12 = ComposerKt.compositionTracer;
                        arrayList = new ArrayList();
                        slotReaderOpenReader = slotTable.openReader();
                    }
                    ChangeList changeList11 = composerChangeListWriter.changeList;
                    changeList11.getClass();
                    changeList11.operations.pushOp(Operation.SkipToEndOfCurrentGroup.INSTANCE);
                    i4 = i + 1;
                    size = i5;
                    i2 = 1;
                    changeList5 = changeList2;
                    slotTable4 = slotTable2;
                    i3 = 0;
                } catch (Throwable th6) {
                    th = th6;
                    changeList2 = changeList5;
                }
            }
            ChangeList changeList12 = changeList5;
            ChangeList changeList13 = composerChangeListWriter.changeList;
            changeList13.getClass();
            changeList13.operations.pushOp(Operation.EndMovableContentPlacement.INSTANCE);
            composerChangeListWriter.writersReaderDelta = 0;
            composerChangeListWriter.changeList = changeList12;
        } catch (Throwable th7) {
            th = th7;
            changeList = changeList5;
        }
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
            m332startBaiHCIY(202, 0, opaqueKey, persistentCompositionLocalMap);
            this.providerCache = null;
            if (!this.inserting || z) {
                boolean z3 = this.providersInvalid;
                this.providersInvalid = z2;
                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(316014703, true, new Function2() { // from class: androidx.compose.runtime.ComposerImpl.invokeMovableContentLambda.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        Composer composer = (Composer) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        if (composerImpl.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
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
        SlotReader slotReaderOpenReader = slotTable.openReader();
        try {
            Integer numFindSubcompositionContextGroup$lambda$4$scanGroup = ComposeStackTraceBuilderKt.findSubcompositionContextGroup$lambda$4$scanGroup(slotReaderOpenReader, compositionContext, 0, slotReaderOpenReader.groupsSize);
            if (numFindSubcompositionContextGroup$lambda$4$scanGroup == null) {
                return EmptyList.INSTANCE;
            }
            try {
                return ComposeStackTraceBuilderKt.traceForGroup(slotTable.openReader(), numFindSubcompositionContextGroup$lambda$4$scanGroup.intValue(), 0);
            } finally {
            }
        } finally {
        }
    }

    public final int rGroupIndexOf(int i) {
        int iParent = this.reader.parent(i) + 1;
        int i2 = 0;
        while (iParent < i) {
            if (!this.reader.hasObjectKey(iParent)) {
                i2++;
            }
            iParent += SlotTableKt.access$groupSize(iParent, this.reader.groups);
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0064 A[Catch: all -> 0x002b, TRY_LEAVE, TryCatch #1 {all -> 0x002b, blocks: (B:3:0x0005, B:6:0x0015, B:8:0x0027, B:12:0x0030, B:11:0x002d, B:15:0x0037, B:17:0x003d, B:19:0x0041, B:22:0x0049, B:24:0x0053, B:26:0x0059, B:27:0x005d, B:28:0x005e, B:30:0x0064, B:23:0x004f), top: B:37:0x0005, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object recomposeMovableContent(ControlledComposition controlledComposition, ControlledComposition controlledComposition2, Integer num, List list, Function0 function0) {
        Object objInvoke;
        boolean z = this.isComposing;
        int i = this.nodeIndex;
        try {
            this.isComposing = true;
            this.nodeIndex = 0;
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Pair pair = (Pair) list.get(i2);
                RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) pair.component1();
                Object objComponent2 = pair.component2();
                if (objComponent2 != null) {
                    tryImminentInvalidation$runtime_release(recomposeScopeImpl, objComponent2);
                } else {
                    tryImminentInvalidation$runtime_release(recomposeScopeImpl, null);
                }
            }
            if (controlledComposition == null) {
                objInvoke = function0.invoke();
            } else {
                int iIntValue = num != null ? num.intValue() : -1;
                CompositionImpl compositionImpl = (CompositionImpl) controlledComposition;
                if (controlledComposition2 == null || controlledComposition2.equals(compositionImpl) || iIntValue < 0) {
                    objInvoke = function0.invoke();
                } else {
                    compositionImpl.invalidationDelegate = (CompositionImpl) controlledComposition2;
                    compositionImpl.invalidationDelegateGroup = iIntValue;
                    try {
                        objInvoke = function0.invoke();
                        compositionImpl.invalidationDelegate = null;
                        compositionImpl.invalidationDelegateGroup = 0;
                    } catch (Throwable th) {
                        compositionImpl.invalidationDelegate = null;
                        compositionImpl.invalidationDelegateGroup = 0;
                        throw th;
                    }
                }
                if (objInvoke == null) {
                }
            }
            this.isComposing = z;
            this.nodeIndex = i;
            return objInvoke;
        } catch (Throwable th2) {
            this.isComposing = z;
            this.nodeIndex = i;
            throw th2;
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x02bc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x014d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void recomposeToGroupEnd() {
        Invalidation invalidation;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        int i6;
        long j;
        boolean z2;
        MutableObjectIntMap mutableObjectIntMap;
        int iFindLocation;
        ArrayList arrayList;
        boolean z3;
        Function2 function2;
        Unit unit;
        int iRotateLeft;
        int iHashCode;
        Object objAux;
        boolean z4 = this.isComposing;
        boolean z5 = true;
        this.isComposing = true;
        SlotReader slotReader = this.reader;
        int i7 = slotReader.parent;
        int i8 = 3;
        int i9 = (i7 * 5) + 3;
        int i10 = slotReader.groups[i9] + i7;
        int i11 = this.nodeIndex;
        int i12 = this.compoundKeyHash;
        int i13 = this.groupNodeCount;
        int i14 = this.rGroupIndex;
        List list = this.invalidations;
        int iFindLocation2 = ComposerKt.findLocation(slotReader.currentGroup, list);
        if (iFindLocation2 < 0) {
            iFindLocation2 = -(iFindLocation2 + 1);
        }
        ArrayList arrayList2 = (ArrayList) list;
        if (iFindLocation2 < arrayList2.size()) {
            invalidation = (Invalidation) arrayList2.get(iFindLocation2);
            if (invalidation.location >= i10) {
                invalidation = null;
            }
        }
        int i15 = i7;
        boolean z6 = false;
        while (invalidation != null) {
            int i16 = i8;
            List list2 = this.invalidations;
            int i17 = invalidation.location;
            boolean z7 = z5;
            int iFindLocation3 = ComposerKt.findLocation(i17, list2);
            if (iFindLocation3 >= 0) {
            }
            Object obj = invalidation.instances;
            RecomposeScopeImpl recomposeScopeImpl = invalidation.scope;
            if (obj == null) {
                recomposeScopeImpl.getClass();
                i2 = i10;
                i = i9;
            } else {
                MutableScatterMap mutableScatterMap = recomposeScopeImpl.trackedDependencies;
                if (mutableScatterMap == null) {
                    i2 = i10;
                    i = i9;
                } else {
                    int i18 = 8;
                    if (obj instanceof DerivedState) {
                        DerivedState derivedState = (DerivedState) obj;
                        DerivedSnapshotState derivedSnapshotState = (DerivedSnapshotState) derivedState;
                        i = i9;
                        SnapshotMutationPolicy snapshotMutationPolicy = derivedSnapshotState.policy;
                        if (snapshotMutationPolicy == null) {
                            snapshotMutationPolicy = StructuralEqualityPolicy.INSTANCE;
                        }
                        z = !snapshotMutationPolicy.equivalent(derivedSnapshotState.getCurrentRecord().result, mutableScatterMap.get(derivedState));
                        i2 = i10;
                        i3 = i11;
                        i4 = i13;
                        i5 = i14;
                    } else {
                        i = i9;
                        if (obj instanceof ScatterSet) {
                            ScatterSet scatterSet = (ScatterSet) obj;
                            if (scatterSet.isNotEmpty()) {
                                Object[] objArr = scatterSet.elements;
                                long[] jArr = scatterSet.metadata;
                                int length = jArr.length - 2;
                                if (length >= 0) {
                                    i4 = i13;
                                    i5 = i14;
                                    int i19 = 0;
                                    while (true) {
                                        long j2 = jArr[i19];
                                        i2 = i10;
                                        Object[] objArr2 = objArr;
                                        if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                                            int i20 = 8 - ((~(i19 - length)) >>> 31);
                                            int i21 = 0;
                                            while (i21 < i20) {
                                                if ((j2 & 255) < 128) {
                                                    i6 = i21;
                                                    Object obj2 = objArr2[(i19 << 3) + i21];
                                                    i3 = i11;
                                                    if (!(obj2 instanceof DerivedState)) {
                                                        break;
                                                    }
                                                    DerivedState derivedState2 = (DerivedState) obj2;
                                                    DerivedSnapshotState derivedSnapshotState2 = (DerivedSnapshotState) derivedState2;
                                                    j = j2;
                                                    SnapshotMutationPolicy snapshotMutationPolicy2 = derivedSnapshotState2.policy;
                                                    if (snapshotMutationPolicy2 == null) {
                                                        snapshotMutationPolicy2 = StructuralEqualityPolicy.INSTANCE;
                                                    }
                                                    if (!snapshotMutationPolicy2.equivalent(derivedSnapshotState2.getCurrentRecord().result, mutableScatterMap.get(derivedState2))) {
                                                        break;
                                                    }
                                                } else {
                                                    i6 = i21;
                                                    i3 = i11;
                                                    j = j2;
                                                }
                                                j2 = j >> i18;
                                                i21 = i6 + 1;
                                                i11 = i3;
                                            }
                                            i3 = i11;
                                            if (i20 != i18) {
                                                break;
                                            }
                                        } else {
                                            i3 = i11;
                                        }
                                        if (i19 == length) {
                                            break;
                                        }
                                        i19++;
                                        i10 = i2;
                                        objArr = objArr2;
                                        i11 = i3;
                                        i18 = 8;
                                    }
                                } else {
                                    i2 = i10;
                                    i3 = i11;
                                    i4 = i13;
                                    i5 = i14;
                                }
                                z = false;
                            }
                        } else {
                            i2 = i10;
                        }
                    }
                    if (z) {
                        this.invalidateStack.add(recomposeScopeImpl);
                        RecomposeScopeOwner recomposeScopeOwner = recomposeScopeImpl.owner;
                        if (recomposeScopeOwner != null && (mutableObjectIntMap = recomposeScopeImpl.trackedInstances) != null) {
                            recomposeScopeImpl.setRereading(true);
                            try {
                                Object[] objArr3 = mutableObjectIntMap.keys;
                                int[] iArr = mutableObjectIntMap.values;
                                long[] jArr2 = mutableObjectIntMap.metadata;
                                int length2 = jArr2.length - 2;
                                if (length2 >= 0) {
                                    int i22 = 0;
                                    while (true) {
                                        long j3 = jArr2[i22];
                                        Object[] objArr4 = objArr3;
                                        int[] iArr2 = iArr;
                                        if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                                            int i23 = 8 - ((~(i22 - length2)) >>> 31);
                                            long j4 = j3;
                                            for (int i24 = 0; i24 < i23; i24++) {
                                                if ((j4 & 255) < 128) {
                                                    int i25 = (i22 << 3) + i24;
                                                    Object obj3 = objArr4[i25];
                                                    int i26 = iArr2[i25];
                                                    recomposeScopeOwner.recordReadOf(obj3);
                                                }
                                                j4 >>= 8;
                                            }
                                            if (i23 != 8) {
                                                break;
                                            }
                                        }
                                        if (i22 == length2) {
                                            break;
                                        }
                                        i22++;
                                        objArr3 = objArr4;
                                        iArr = iArr2;
                                    }
                                }
                                recomposeScopeImpl.setRereading(false);
                            } catch (Throwable th) {
                                recomposeScopeImpl.setRereading(false);
                                throw th;
                            }
                        }
                        z2 = true;
                        this.invalidateStack.remove(r2.size() - 1);
                    } else {
                        this.reader.reposition(i17);
                        int i27 = this.reader.currentGroup;
                        recordUpsAndDowns(i15, i27, i7);
                        int iParent = this.reader.parent(i27);
                        while (iParent != i7 && !this.reader.isNode(iParent)) {
                            iParent = this.reader.parent(iParent);
                        }
                        int iUpdatedNodeCount = this.reader.isNode(iParent) ? 0 : i3;
                        if (iParent != i27) {
                            int iUpdatedNodeCount2 = (updatedNodeCount(iParent) - this.reader.nodeCount(i27)) + iUpdatedNodeCount;
                            while (iUpdatedNodeCount < iUpdatedNodeCount2 && iParent != i17) {
                                iParent++;
                                while (iParent < i17) {
                                    SlotReader slotReader2 = this.reader;
                                    int i28 = slotReader2.groups[(iParent * 5) + 3] + iParent;
                                    if (i17 >= i28) {
                                        iUpdatedNodeCount += slotReader2.isNode(iParent) ? z7 ? 1 : 0 : updatedNodeCount(iParent);
                                        iParent = i28;
                                    }
                                }
                                break;
                            }
                        }
                        this.nodeIndex = iUpdatedNodeCount;
                        this.rGroupIndex = rGroupIndexOf(i27);
                        int iParent2 = this.reader.parent(i27);
                        int i29 = i16;
                        int iRotateLeft2 = 0;
                        int i30 = 0;
                        while (iParent2 >= 0) {
                            if (iParent2 == i7) {
                                iRotateLeft = Integer.rotateLeft(i12, i30);
                            } else {
                                SlotReader slotReader3 = this.reader;
                                boolean zHasObjectKey = slotReader3.hasObjectKey(iParent2);
                                int[] iArr3 = slotReader3.groups;
                                if (zHasObjectKey) {
                                    Object objObjectKey = slotReader3.objectKey(iParent2, iArr3);
                                    iHashCode = objObjectKey != null ? objObjectKey instanceof Enum ? ((Enum) objObjectKey).ordinal() : objObjectKey instanceof MovableContent ? 126665345 : objObjectKey.hashCode() : 0;
                                } else {
                                    int iGroupKey = slotReader3.groupKey(iParent2);
                                    if (iGroupKey != 207 || (objAux = slotReader3.aux(iParent2, iArr3)) == null) {
                                        iHashCode = iGroupKey;
                                    } else {
                                        Composer.Companion.getClass();
                                        if (!objAux.equals(Composer.Companion.Empty)) {
                                            iHashCode = objAux.hashCode();
                                        }
                                    }
                                }
                                if (iHashCode == 126665345) {
                                    iRotateLeft = Integer.rotateLeft(iHashCode, i30);
                                } else {
                                    iRotateLeft2 = (iRotateLeft2 ^ Integer.rotateLeft(iHashCode, i29)) ^ Integer.rotateLeft(this.reader.hasObjectKey(iParent2) ? 0 : rGroupIndexOf(iParent2), i30);
                                    i29 = (i29 + 6) % 32;
                                    i30 = (i30 + 6) % 32;
                                    iParent2 = this.reader.parent(iParent2);
                                }
                            }
                            iRotateLeft2 ^= iRotateLeft;
                            break;
                        }
                        this.compoundKeyHash = iRotateLeft2;
                        this.providerCache = null;
                        if (this.reusing) {
                            z3 = false;
                            if (z3) {
                                this.reusing = z7;
                            }
                            function2 = recomposeScopeImpl.block;
                            if (function2 == null) {
                                function2.invoke(this, Integer.valueOf(z7 ? 1 : 0));
                                unit = Unit.INSTANCE;
                            } else {
                                unit = null;
                            }
                            if (unit != null) {
                                throw new IllegalStateException("Invalid restart scope");
                            }
                            if (z3) {
                                this.reusing = false;
                            }
                            this.providerCache = null;
                            SlotReader slotReader4 = this.reader;
                            int i31 = slotReader4.groups[i] + i7;
                            int i32 = slotReader4.currentGroup;
                            if (!(i32 >= i7 && i32 <= i31)) {
                                ComposerKt.composeImmediateRuntimeError("Index " + i7 + " is not a parent of " + i32);
                            }
                            slotReader4.parent = i7;
                            slotReader4.currentEnd = i31;
                            slotReader4.currentSlot = 0;
                            slotReader4.currentSlotEnd = 0;
                            i15 = i27;
                            z6 = true;
                            z2 = true;
                        } else {
                            if ((recomposeScopeImpl.flags & 128) != 0 ? z7 ? 1 : 0 : false) {
                                z3 = z7 ? 1 : 0;
                            }
                            if (z3) {
                            }
                            function2 = recomposeScopeImpl.block;
                            if (function2 == null) {
                            }
                            if (unit != null) {
                            }
                        }
                    }
                    List list3 = this.invalidations;
                    iFindLocation = ComposerKt.findLocation(this.reader.currentGroup, list3);
                    if (iFindLocation < 0) {
                        iFindLocation = -(iFindLocation + 1);
                    }
                    arrayList = (ArrayList) list3;
                    if (iFindLocation >= arrayList.size()) {
                        Invalidation invalidation2 = (Invalidation) arrayList.get(iFindLocation);
                        i10 = i2;
                        invalidation = invalidation2.location < i10 ? invalidation2 : null;
                        i8 = i16;
                        z5 = z2;
                        i9 = i;
                        i13 = i4;
                        i14 = i5;
                        i11 = i3;
                    } else {
                        i10 = i2;
                    }
                    i8 = i16;
                    z5 = z2;
                    i9 = i;
                    i13 = i4;
                    i14 = i5;
                    i11 = i3;
                }
            }
            i3 = i11;
            i4 = i13;
            i5 = i14;
            z = z7 ? 1 : 0;
            if (z) {
            }
            List list32 = this.invalidations;
            iFindLocation = ComposerKt.findLocation(this.reader.currentGroup, list32);
            if (iFindLocation < 0) {
            }
            arrayList = (ArrayList) list32;
            if (iFindLocation >= arrayList.size()) {
            }
            i8 = i16;
            z5 = z2;
            i9 = i;
            i13 = i4;
            i14 = i5;
            i11 = i3;
        }
        int i33 = i11;
        int i34 = i13;
        int i35 = i14;
        if (z6) {
            recordUpsAndDowns(i15, i7, i7);
            this.reader.skipToGroupEnd();
            int iUpdatedNodeCount3 = updatedNodeCount(i7);
            this.nodeIndex = i33 + iUpdatedNodeCount3;
            this.groupNodeCount = i34 + iUpdatedNodeCount3;
            this.rGroupIndex = i35;
        } else {
            skipReaderToGroupEnd();
        }
        this.compoundKeyHash = i12;
        this.isComposing = z4;
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void recordUpsAndDowns(int i, int i2, int i3) {
        SlotReader slotReader = this.reader;
        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
        if (i != i2) {
            if (i != i3 && i2 != i3) {
                if (slotReader.parent(i) == i2) {
                    i3 = i2;
                } else if (slotReader.parent(i2) == i) {
                    i3 = i;
                } else if (slotReader.parent(i) == slotReader.parent(i2)) {
                    i3 = slotReader.parent(i);
                } else {
                    int iParent = i;
                    int i4 = 0;
                    while (iParent > 0 && iParent != i3) {
                        iParent = slotReader.parent(iParent);
                        i4++;
                    }
                    int iParent2 = i2;
                    int i5 = 0;
                    while (iParent2 > 0 && iParent2 != i3) {
                        iParent2 = slotReader.parent(iParent2);
                        i5++;
                    }
                    int i6 = i4 - i5;
                    int iParent3 = i;
                    for (int i7 = 0; i7 < i6; i7++) {
                        iParent3 = slotReader.parent(iParent3);
                    }
                    int i8 = i5 - i4;
                    int iParent4 = i2;
                    for (int i9 = 0; i9 < i8; i9++) {
                        iParent4 = slotReader.parent(iParent4);
                    }
                    i3 = iParent3;
                    for (int iParent5 = iParent4; i3 != iParent5; iParent5 = slotReader.parent(iParent5)) {
                        i3 = slotReader.parent(i3);
                    }
                }
            }
        }
        while (i > 0 && i != i3) {
            if (slotReader.isNode(i)) {
                this.changeListWriter.moveUp();
            }
            i = slotReader.parent(i);
        }
        doRecordDownsFor(i2, i3);
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
        boolean zIsNode = this.reader.isNode(i);
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        if (zIsNode) {
            composerChangeListWriter.realizeNodeMovementOperations();
            Object objNode = this.reader.node(i);
            composerChangeListWriter.realizeNodeMovementOperations();
            composerChangeListWriter.pendingDownNodes.add(objNode);
        }
        reportFreeMovableContent$reportGroup(this, i, i, zIsNode, 0);
        composerChangeListWriter.realizeNodeMovementOperations();
        if (zIsNode) {
            composerChangeListWriter.moveUp();
        }
    }

    public final boolean shouldExecute(int i, boolean z) {
        return ((i & 1) == 0 && (this.inserting || this.reusing)) || z || !getSkipping();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void skipCurrentGroup() {
        int iRotateLeft;
        if (((ArrayList) this.invalidations).isEmpty()) {
            this.groupNodeCount = this.reader.skipGroup() + this.groupNodeCount;
            return;
        }
        SlotReader slotReader = this.reader;
        int groupKey = slotReader.getGroupKey();
        int i = slotReader.currentGroup;
        int i2 = slotReader.currentEnd;
        int[] iArr = slotReader.groups;
        Object objObjectKey = i < i2 ? slotReader.objectKey(i, iArr) : null;
        Object groupAux = slotReader.getGroupAux();
        int i3 = this.rGroupIndex;
        Composer.Companion companion = Composer.Companion;
        if (objObjectKey == null) {
            if (groupAux != null && groupKey == 207) {
                companion.getClass();
                if (!groupAux.equals(Composer.Companion.Empty)) {
                    this.compoundKeyHash = Integer.rotateLeft(groupAux.hashCode() ^ Integer.rotateLeft(this.compoundKeyHash, 3), 3) ^ i3;
                    startReaderGroup(null, (iArr[(slotReader.currentGroup * 5) + 1] & 1073741824) != 0);
                    recomposeToGroupEnd();
                    slotReader.endGroup();
                    if (objObjectKey == null) {
                        if (objObjectKey instanceof Enum) {
                            this.compoundKeyHash = Integer.rotateRight(Integer.hashCode(((Enum) objObjectKey).ordinal()) ^ Integer.rotateRight(this.compoundKeyHash, 3), 3);
                            return;
                        } else {
                            this.compoundKeyHash = Integer.rotateRight(Integer.hashCode(objObjectKey.hashCode()) ^ Integer.rotateRight(this.compoundKeyHash, 3), 3);
                            return;
                        }
                    }
                    if (groupAux != null && groupKey == 207) {
                        companion.getClass();
                        if (!groupAux.equals(Composer.Companion.Empty)) {
                            this.compoundKeyHash = Integer.rotateRight(Integer.hashCode(groupAux.hashCode()) ^ Integer.rotateRight(this.compoundKeyHash ^ i3, 3), 3);
                            return;
                        }
                    }
                    this.compoundKeyHash = Integer.rotateRight(Integer.rotateRight(this.compoundKeyHash ^ i3, 3) ^ Integer.hashCode(groupKey), 3);
                    return;
                }
            }
            iRotateLeft = Integer.rotateLeft(Integer.rotateLeft(this.compoundKeyHash, 3) ^ groupKey, 3) ^ i3;
        } else {
            iRotateLeft = Integer.rotateLeft((objObjectKey instanceof Enum ? ((Enum) objObjectKey).ordinal() : objObjectKey.hashCode()) ^ Integer.rotateLeft(this.compoundKeyHash, 3), 3);
        }
        this.compoundKeyHash = iRotateLeft;
        startReaderGroup(null, (iArr[(slotReader.currentGroup * 5) + 1] & 1073741824) != 0);
        recomposeToGroupEnd();
        slotReader.endGroup();
        if (objObjectKey == null) {
        }
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

            /* JADX WARN: Removed duplicated region for block: B:13:0x0016  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj2) {
                boolean z;
                Object obj3 = obj;
                if (obj2 != obj3) {
                    RememberObserverHolder rememberObserverHolder = obj2 instanceof RememberObserverHolder ? (RememberObserverHolder) obj2 : null;
                    z = (rememberObserverHolder != null ? rememberObserverHolder.wrapped : null) == obj3;
                }
                return Boolean.valueOf(z);
            }
        };
        SlotTable slotTable = this.slotTable;
        SlotReader slotReaderOpenReader = slotTable.openReader();
        try {
            Ref$IntRef ref$IntRef = new Ref$IntRef();
            loop0: while (true) {
                int i = ref$IntRef.element;
                objectLocation = null;
                if (i >= slotTable.groupsSize) {
                    Unit unit = Unit.INSTANCE;
                    break;
                }
                if (slotReaderOpenReader.isNode(i) && ((Boolean) function1.mo781invoke(slotReaderOpenReader.node(ref$IntRef.element))).booleanValue()) {
                    ObjectLocation objectLocation2 = new ObjectLocation(ref$IntRef.element, null);
                    slotReaderOpenReader.close();
                    objectLocation = objectLocation2;
                    break;
                }
                int i2 = ref$IntRef.element;
                int[] iArr = slotReaderOpenReader.groups;
                int iAccess$slotAnchor = SlotTableKt.access$slotAnchor(i2, iArr);
                int i3 = i2 + 1;
                int i4 = (i3 < slotReaderOpenReader.groupsSize ? iArr[(i3 * 5) + 4] : slotReaderOpenReader.slotsSize) - iAccess$slotAnchor;
                for (int i5 = 0; i5 < i4; i5++) {
                    if (((Boolean) function1.mo781invoke(slotReaderOpenReader.groupGet(ref$IntRef.element, i5))).booleanValue()) {
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
                slotReaderOpenReader = slotTable.openReader();
                try {
                    List listTraceForGroup = ComposeStackTraceBuilderKt.traceForGroup(slotReaderOpenReader, i6, num);
                    slotReaderOpenReader.close();
                    list = listTraceForGroup;
                } finally {
                }
            } else {
                list = EmptyList.INSTANCE;
            }
            return CollectionsKt___CollectionsKt.plus((Iterable) parentStackTrace(), (Collection) list);
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00df  */
    /* renamed from: start-BaiHCIY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m332startBaiHCIY(int i, int i2, Object obj, Object obj2) {
        int iRotateLeft;
        int i3;
        Pending pending;
        Pending pending2;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        Object obj3 = obj;
        if (this.nodeExpected) {
            ComposerKt.composeImmediateRuntimeError("A call to createNode(), emitNode() or useNode() expected");
        }
        int i9 = this.rGroupIndex;
        Composer.Companion companion = Composer.Companion;
        if (obj3 == null) {
            if (obj2 != null && i == 207) {
                companion.getClass();
                if (!obj2.equals(Composer.Companion.Empty)) {
                    this.compoundKeyHash = i9 ^ Integer.rotateLeft(obj2.hashCode() ^ Integer.rotateLeft(this.compoundKeyHash, 3), 3);
                    boolean z = true;
                    if (obj3 == null) {
                        this.rGroupIndex++;
                    }
                    GroupKind.Companion.getClass();
                    int i10 = 0;
                    boolean z2 = i2 == 0;
                    int i11 = -1;
                    if (!this.inserting) {
                        this.reader.emptyCount++;
                        SlotWriter slotWriter = this.writer;
                        int i12 = slotWriter.currentGroup;
                        if (z2) {
                            companion.getClass();
                            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                            companion.getClass();
                            slotWriter.startGroup(i, composer$Companion$Empty$1, true, composer$Companion$Empty$1);
                        } else if (obj2 != null) {
                            if (obj3 == null) {
                                companion.getClass();
                                obj3 = Composer.Companion.Empty;
                            }
                            slotWriter.startGroup(i, obj3, false, obj2);
                        } else {
                            if (obj3 == null) {
                                companion.getClass();
                                obj3 = Composer.Companion.Empty;
                            }
                            slotWriter.startGroup(i, obj3);
                        }
                        Pending pending3 = this.pending;
                        if (pending3 != null) {
                            KeyInfo keyInfo = new KeyInfo(i, -1, (-2) - i12, -1, 0);
                            pending3.groupInfos.set(keyInfo.location, new GroupInfo(-1, this.nodeIndex - pending3.startIndex, 0));
                            ((ArrayList) pending3.usedKeys).add(keyInfo);
                        }
                        enterGroup(z2, null);
                        return;
                    }
                    boolean z3 = i2 == GroupKind.Node && this.reusing;
                    if (this.pending == null) {
                        int groupKey = this.reader.getGroupKey();
                        if (!z3 && groupKey == i) {
                            SlotReader slotReader = this.reader;
                            int i13 = slotReader.currentGroup;
                            if (Intrinsics.areEqual(obj3, i13 < slotReader.currentEnd ? slotReader.objectKey(i13, slotReader.groups) : null)) {
                                startReaderGroup(obj2, z2);
                                i3 = -1;
                            }
                        }
                        SlotReader slotReader2 = this.reader;
                        slotReader2.getClass();
                        ArrayList arrayList = new ArrayList();
                        if (slotReader2.emptyCount <= 0) {
                            int i14 = slotReader2.currentGroup;
                            int i15 = 0;
                            while (i14 < slotReader2.currentEnd) {
                                int i16 = i14 * 5;
                                int i17 = i11;
                                int[] iArr = slotReader2.groups;
                                int i18 = iArr[i16];
                                Object objObjectKey = slotReader2.objectKey(i14, iArr);
                                int i19 = iArr[i16 + 1];
                                int i20 = (i19 & 1073741824) != 0 ? 1 : i19 & 67108863;
                                int i21 = i14;
                                arrayList.add(new KeyInfo(i18, objObjectKey, i21, i20, i15));
                                i14 = iArr[i16 + 3] + i21;
                                i11 = i17;
                                i15++;
                            }
                        }
                        i3 = i11;
                        this.pending = new Pending(arrayList, this.nodeIndex);
                    } else {
                        i3 = -1;
                    }
                    Pending pending4 = this.pending;
                    if (pending4 != null) {
                        Object joinedKey = obj3 != null ? new JoinedKey(Integer.valueOf(i), obj3) : Integer.valueOf(i);
                        MutableScatterMap mutableScatterMap = ((MultiValueMap) pending4.keyMap$delegate.getValue()).map;
                        Object obj4 = mutableScatterMap.get(joinedKey);
                        if (obj4 == null) {
                            obj4 = null;
                        } else if (obj4 instanceof MutableObjectList) {
                            MutableObjectList mutableObjectList = (MutableObjectList) obj4;
                            Object objRemoveAt = mutableObjectList.removeAt(0);
                            if (mutableObjectList.isEmpty()) {
                                mutableScatterMap.remove(joinedKey);
                            }
                            if (mutableObjectList._size == 1) {
                                mutableScatterMap.set(joinedKey, mutableObjectList.first());
                            }
                            obj4 = objRemoveAt;
                        } else {
                            mutableScatterMap.remove(joinedKey);
                        }
                        KeyInfo keyInfo2 = (KeyInfo) obj4;
                        MutableIntObjectMap mutableIntObjectMap = pending4.groupInfos;
                        int i22 = pending4.startIndex;
                        if (z3 || keyInfo2 == null) {
                            this.reader.emptyCount++;
                            this.inserting = true;
                            this.providerCache = null;
                            if (this.writer.closed) {
                                SlotWriter slotWriterOpenWriter = this.insertTable.openWriter();
                                this.writer = slotWriterOpenWriter;
                                slotWriterOpenWriter.skipToGroupEnd();
                                this.writerHasAProvider = false;
                                this.providerCache = null;
                            }
                            this.writer.beginInsert();
                            SlotWriter slotWriter2 = this.writer;
                            int i23 = slotWriter2.currentGroup;
                            if (z2) {
                                companion.getClass();
                                Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
                                companion.getClass();
                                slotWriter2.startGroup(i, composer$Companion$Empty$12, true, composer$Companion$Empty$12);
                            } else if (obj2 != null) {
                                if (obj3 == null) {
                                    companion.getClass();
                                    obj3 = Composer.Companion.Empty;
                                }
                                slotWriter2.startGroup(i, obj3, false, obj2);
                            } else {
                                if (obj3 == null) {
                                    companion.getClass();
                                    obj3 = Composer.Companion.Empty;
                                }
                                slotWriter2.startGroup(i, obj3);
                            }
                            this.insertAnchor = this.writer.anchor(i23);
                            KeyInfo keyInfo3 = new KeyInfo(i, Integer.valueOf(i3), (-2) - i23, -1, 0);
                            mutableIntObjectMap.set(keyInfo3.location, new GroupInfo(i3, this.nodeIndex - i22, 0));
                            ((ArrayList) pending4.usedKeys).add(keyInfo3);
                            pending2 = new Pending(new ArrayList(), z2 ? 0 : this.nodeIndex);
                            enterGroup(z2, pending2);
                            return;
                        }
                        ((ArrayList) pending4.usedKeys).add(keyInfo2);
                        int i24 = keyInfo2.location;
                        GroupInfo groupInfo = (GroupInfo) mutableIntObjectMap.get(i24);
                        this.nodeIndex = (groupInfo != null ? groupInfo.nodeIndex : i3) + i22;
                        GroupInfo groupInfo2 = (GroupInfo) mutableIntObjectMap.get(i24);
                        int i25 = groupInfo2 != null ? groupInfo2.slotIndex : i3;
                        int i26 = pending4.groupIndex;
                        int i27 = i25 - i26;
                        int i28 = 8;
                        if (i25 > i26) {
                            Object[] objArr = mutableIntObjectMap.values;
                            long[] jArr = mutableIntObjectMap.metadata;
                            int length = jArr.length - 2;
                            if (length >= 0) {
                                int i29 = 0;
                                while (true) {
                                    long j = jArr[i29];
                                    boolean z4 = z;
                                    i4 = i27;
                                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i30 = 8 - ((~(i29 - length)) >>> 31);
                                        for (int i31 = i10; i31 < i30; i31++) {
                                            if ((j & 255) < 128) {
                                                GroupInfo groupInfo3 = (GroupInfo) objArr[(i29 << 3) + i31];
                                                int i32 = groupInfo3.slotIndex;
                                                if (i32 == i25) {
                                                    groupInfo3.slotIndex = i26;
                                                } else if (i26 <= i32 && i32 < i25) {
                                                    groupInfo3.slotIndex = i32 + 1;
                                                }
                                            }
                                            j >>= 8;
                                        }
                                        if (i30 != 8) {
                                            break;
                                        }
                                        if (i29 == length) {
                                            break;
                                        }
                                        i29++;
                                        z = z4;
                                        i27 = i4;
                                        i10 = 0;
                                    }
                                    enterGroup(z2, pending2);
                                    return;
                                }
                            }
                            i4 = i27;
                        } else {
                            i4 = i27;
                            if (i26 > i25) {
                                Object[] objArr2 = mutableIntObjectMap.values;
                                long[] jArr2 = mutableIntObjectMap.metadata;
                                int length2 = jArr2.length - 2;
                                if (length2 >= 0) {
                                    int i33 = 0;
                                    while (true) {
                                        long j2 = jArr2[i33];
                                        if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                                            int i34 = 8 - ((~(i33 - length2)) >>> 31);
                                            int i35 = 0;
                                            while (i35 < i34) {
                                                if ((j2 & 255) < 128) {
                                                    GroupInfo groupInfo4 = (GroupInfo) objArr2[(i33 << 3) + i35];
                                                    i8 = i28;
                                                    int i36 = groupInfo4.slotIndex;
                                                    if (i36 == i25) {
                                                        groupInfo4.slotIndex = i26;
                                                        i7 = i25;
                                                    } else {
                                                        i7 = i25;
                                                        if (i7 + 1 <= i36 && i36 < i26) {
                                                            groupInfo4.slotIndex = i36 - 1;
                                                        }
                                                    }
                                                } else {
                                                    i7 = i25;
                                                    i8 = i28;
                                                }
                                                j2 >>= i8;
                                                i35++;
                                                i28 = i8;
                                                i25 = i7;
                                            }
                                            i5 = i25;
                                            i6 = i28;
                                            if (i34 != i6) {
                                                break;
                                            }
                                        } else {
                                            i5 = i25;
                                            i6 = i28;
                                        }
                                        if (i33 == length2) {
                                            break;
                                        }
                                        i33++;
                                        i28 = i6;
                                        i25 = i5;
                                    }
                                }
                            }
                        }
                        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
                        composerChangeListWriter.writersReaderDelta = (i24 - composerChangeListWriter.composer.reader.currentGroup) + composerChangeListWriter.writersReaderDelta;
                        this.reader.reposition(i24);
                        if (i4 > 0) {
                            composerChangeListWriter.realizeOperationLocation(false);
                            composerChangeListWriter.recordSlotEditing();
                            ChangeList changeList = composerChangeListWriter.changeList;
                            changeList.getClass();
                            Operation.MoveCurrentGroup moveCurrentGroup = Operation.MoveCurrentGroup.INSTANCE;
                            Operations operations = changeList.operations;
                            operations.pushOp(moveCurrentGroup);
                            operations.intArgs[operations.intArgsSize - operations.opCodes[operations.opCodesSize - 1].ints] = i4;
                        }
                        startReaderGroup(obj2, z2);
                        pending = null;
                    } else {
                        pending = null;
                    }
                    pending2 = pending;
                    enterGroup(z2, pending2);
                    return;
                }
            }
            iRotateLeft = i9 ^ Integer.rotateLeft(Integer.rotateLeft(this.compoundKeyHash, 3) ^ i, 3);
        } else {
            iRotateLeft = Integer.rotateLeft((obj3 instanceof Enum ? ((Enum) obj3).ordinal() : obj3.hashCode()) ^ Integer.rotateLeft(this.compoundKeyHash, 3), 3);
        }
        this.compoundKeyHash = iRotateLeft;
        boolean z5 = true;
        if (obj3 == null) {
        }
        GroupKind.Companion.getClass();
        int i102 = 0;
        if (i2 == 0) {
        }
        int i112 = -1;
        if (!this.inserting) {
        }
    }

    public final void startDefaults() {
        GroupKind.Companion.getClass();
        m332startBaiHCIY(-127, 0, null, null);
    }

    public final void startGroup(int i, OpaqueKey opaqueKey) {
        GroupKind.Companion.getClass();
        m332startBaiHCIY(i, 0, opaqueKey, null);
    }

    public final void startMovableGroup(int i, Object obj) {
        GroupKind.Companion.getClass();
        m332startBaiHCIY(i, 0, obj, null);
    }

    public final void startNode() {
        GroupKind.Companion.getClass();
        m332startBaiHCIY(125, GroupKind.Node, null, null);
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
            Operations.WriteScope.m339setObjectDKhxnng(operations, 0, obj);
        }
        this.reader.startGroup();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0060, code lost:
    
        if ((r0.groups[(r4 * 5) + 1] & com.samsung.systemui.splugins.volume.VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startReplaceGroup(int i) {
        int i2;
        if (this.pending != null) {
            GroupKind.Companion.getClass();
            m332startBaiHCIY(i, 0, null, null);
            return;
        }
        if (this.nodeExpected) {
            ComposerKt.composeImmediateRuntimeError("A call to createNode(), emitNode() or useNode() expected");
        }
        this.compoundKeyHash = this.rGroupIndex ^ Integer.rotateLeft(Integer.rotateLeft(this.compoundKeyHash, 3) ^ i, 3);
        this.rGroupIndex++;
        SlotReader slotReader = this.reader;
        boolean z = this.inserting;
        Composer.Companion companion = Composer.Companion;
        if (z) {
            slotReader.emptyCount++;
            SlotWriter slotWriter = this.writer;
            companion.getClass();
            slotWriter.startGroup(i, Composer.Companion.Empty);
            enterGroup(false, null);
            return;
        }
        if (slotReader.getGroupKey() == i) {
            int i3 = slotReader.currentGroup;
            if (i3 < slotReader.currentEnd) {
            }
            slotReader.startGroup();
            enterGroup(false, null);
            return;
        }
        if (slotReader.emptyCount <= 0 && (i2 = slotReader.currentGroup) != slotReader.currentEnd) {
            int i4 = this.nodeIndex;
            recordDelete();
            this.changeListWriter.removeNode(i4, slotReader.skipGroup());
            ComposerKt.access$removeRange(this.invalidations, i2, slotReader.currentGroup);
        }
        slotReader.emptyCount++;
        this.inserting = true;
        this.providerCache = null;
        if (this.writer.closed) {
            SlotWriter slotWriterOpenWriter = this.insertTable.openWriter();
            this.writer = slotWriterOpenWriter;
            slotWriterOpenWriter.skipToGroupEnd();
            this.writerHasAProvider = false;
            this.providerCache = null;
        }
        SlotWriter slotWriter2 = this.writer;
        slotWriter2.beginInsert();
        int i5 = slotWriter2.currentGroup;
        companion.getClass();
        slotWriter2.startGroup(i, Composer.Companion.Empty);
        this.insertAnchor = slotWriter2.anchor(i5);
        enterGroup(false, null);
    }

    public final void startReplaceableGroup(int i) {
        GroupKind.Companion.getClass();
        m332startBaiHCIY(i, 0, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ComposerImpl startRestartGroup(int i) {
        RecomposeScopeImpl recomposeScopeImpl;
        startReplaceGroup(i);
        boolean z = this.inserting;
        ControlledComposition controlledComposition = this.composition;
        if (z) {
            RecomposeScopeImpl recomposeScopeImpl2 = new RecomposeScopeImpl((CompositionImpl) controlledComposition);
            this.invalidateStack.add(recomposeScopeImpl2);
            updateValue(recomposeScopeImpl2);
            recomposeScopeImpl2.currentToken = this.compositionToken;
            recomposeScopeImpl2.flags &= -17;
            return this;
        }
        List list = this.invalidations;
        int iFindLocation = ComposerKt.findLocation(this.reader.parent, list);
        Invalidation invalidation = iFindLocation >= 0 ? (Invalidation) ((ArrayList) list).remove(iFindLocation) : null;
        Object next = this.reader.next();
        Composer.Companion.getClass();
        if (Intrinsics.areEqual(next, Composer.Companion.Empty)) {
            recomposeScopeImpl = new RecomposeScopeImpl((CompositionImpl) controlledComposition);
            updateValue(recomposeScopeImpl);
        } else {
            recomposeScopeImpl = (RecomposeScopeImpl) next;
        }
        if (invalidation == null) {
            int i2 = recomposeScopeImpl.flags;
            boolean z2 = (i2 & 64) != 0;
            if (z2) {
                recomposeScopeImpl.flags = i2 & (-65);
            }
            if (z2) {
                recomposeScopeImpl.flags |= 8;
            } else {
                recomposeScopeImpl.flags &= -9;
            }
        }
        this.invalidateStack.add(recomposeScopeImpl);
        recomposeScopeImpl.currentToken = this.compositionToken;
        int i3 = recomposeScopeImpl.flags;
        recomposeScopeImpl.flags = i3 & (-17);
        if ((i3 & 256) != 0) {
            recomposeScopeImpl.flags = (i3 & (-273)) | 512;
            ChangeList changeList = this.changeListWriter.changeList;
            changeList.getClass();
            Operation.StartResumingScope startResumingScope = Operation.StartResumingScope.INSTANCE;
            Operations operations = changeList.operations;
            operations.pushOp(startResumingScope);
            Operations.WriteScope.m339setObjectDKhxnng(operations, 0, recomposeScopeImpl);
        }
        return this;
    }

    public final void startReusableGroup(Object obj) {
        if (!this.inserting && this.reader.getGroupKey() == 207 && !Intrinsics.areEqual(this.reader.getGroupAux(), obj) && this.reusingGroup < 0) {
            this.reusingGroup = this.reader.currentGroup;
            this.reusing = true;
        }
        GroupKind.Companion.getClass();
        m332startBaiHCIY(207, 0, null, obj);
    }

    public final void startReusableNode() {
        GroupKind.Companion.getClass();
        m332startBaiHCIY(125, GroupKind.ReusableNode, null, null);
        this.nodeExpected = true;
    }

    public final void startRoot() {
        this.rGroupIndex = 0;
        this.reader = this.slotTable.openReader();
        GroupKind.Companion companion = GroupKind.Companion;
        companion.getClass();
        m332startBaiHCIY(100, 0, null, null);
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
        m332startBaiHCIY(compoundHashKey$runtime_release, 0, null, null);
    }

    public final boolean tryImminentInvalidation$runtime_release(RecomposeScopeImpl recomposeScopeImpl, Object obj) {
        Anchor anchor = recomposeScopeImpl.anchor;
        if (anchor == null) {
            return false;
        }
        int iAnchorIndex = this.reader.table.anchorIndex(anchor);
        if (!this.isComposing || iAnchorIndex < this.reader.currentGroup) {
            return false;
        }
        List list = this.invalidations;
        int iFindLocation = ComposerKt.findLocation(iAnchorIndex, list);
        if (iFindLocation < 0) {
            int i = -(iFindLocation + 1);
            if (!(obj instanceof DerivedState)) {
                obj = null;
            }
            ((ArrayList) list).add(i, new Invalidation(recomposeScopeImpl, iAnchorIndex, obj));
            return true;
        }
        Invalidation invalidation = (Invalidation) ((ArrayList) list).get(iFindLocation);
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x005b  */
    /* renamed from: updateComposerInvalidations-RY85e9Y, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m333updateComposerInvalidationsRY85e9Y(MutableScatterMap mutableScatterMap) {
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
                    } else if (i == length) {
                        break;
                    } else {
                        i++;
                    }
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
        int iUpdatedNodeCount = updatedNodeCount(i);
        if (iUpdatedNodeCount != i2) {
            int i3 = i2 - iUpdatedNodeCount;
            int size = this.pendingStack.size() - 1;
            while (i != -1) {
                int iUpdatedNodeCount2 = updatedNodeCount(i) + i3;
                updateNodeCount(i, iUpdatedNodeCount2);
                int i4 = size;
                while (true) {
                    if (-1 < i4) {
                        Pending pending = (Pending) this.pendingStack.get(i4);
                        if (pending != null && pending.updateNodeCount(i, iUpdatedNodeCount2)) {
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
        PersistentCompositionLocalHashMap persistentCompositionLocalHashMapBuild = builder.build();
        startGroup(204, ComposerKt.providerMaps);
        nextSlot();
        updateValue(persistentCompositionLocalHashMapBuild);
        nextSlot();
        updateValue(persistentCompositionLocalHashMap);
        end(false);
        return persistentCompositionLocalHashMapBuild;
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
                    int iParent = slotWriter2.parent(i4, slotWriter2.groups);
                    while (true) {
                        i2 = i4;
                        i4 = iParent;
                        slotWriter = this.writer;
                        if (i4 == slotWriter.parent || i4 < 0) {
                            break;
                        } else {
                            iParent = slotWriter.parent(i4, slotWriter.groups);
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
                    int iParent2 = slotReader2.parent(i6);
                    while (true) {
                        i = i6;
                        i6 = iParent2;
                        slotReader = this.reader;
                        if (i6 == slotReader.parent || i6 < 0) {
                            break;
                        } else {
                            iParent2 = slotReader.parent(i6);
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
                Operations.WriteScope.m339setObjectDKhxnng(operations, 0, rememberObserverHolder);
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
            Operations.WriteScope.m340setObjects4uCC6AY(operations, 0, anchor, 1, obj);
            return;
        }
        int iAccess$slotAnchor = (slotReader.currentSlot - SlotTableKt.access$slotAnchor(slotReader.parent, slotReader.groups)) - 1;
        if (composerChangeListWriter.composer.reader.parent - composerChangeListWriter.writersReaderDelta >= 0) {
            composerChangeListWriter.realizeOperationLocation(true);
            ChangeList changeList2 = composerChangeListWriter.changeList;
            changeList2.getClass();
            Operation.UpdateValue updateValue = Operation.UpdateValue.INSTANCE;
            Operations operations2 = changeList2.operations;
            operations2.pushOp(updateValue);
            Operations.WriteScope.m339setObjectDKhxnng(operations2, 0, obj);
            operations2.intArgs[operations2.intArgsSize - operations2.opCodes[operations2.opCodesSize - 1].ints] = iAccess$slotAnchor;
            return;
        }
        SlotReader slotReader2 = this.reader;
        Anchor anchor2 = slotReader2.anchor(slotReader2.parent);
        ChangeList changeList3 = composerChangeListWriter.changeList;
        changeList3.getClass();
        Operation.UpdateAnchoredValue updateAnchoredValue = Operation.UpdateAnchoredValue.INSTANCE;
        Operations operations3 = changeList3.operations;
        operations3.pushOp(updateAnchoredValue);
        Operations.WriteScope.m340setObjects4uCC6AY(operations3, 0, obj, 1, anchor2);
        operations3.intArgs[operations3.intArgsSize - operations3.opCodes[operations3.opCodesSize - 1].ints] = iAccess$slotAnchor;
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
        Object objNode = slotReader.node(slotReader.parent);
        ComposerChangeListWriter composerChangeListWriter = this.changeListWriter;
        composerChangeListWriter.realizeNodeMovementOperations();
        composerChangeListWriter.pendingDownNodes.add(objNode);
        if (this.reusing && (objNode instanceof ComposeNodeLifecycleCallback)) {
            composerChangeListWriter.pushPendingUpsAndDowns();
            ChangeList changeList = composerChangeListWriter.changeList;
            changeList.getClass();
            if (objNode instanceof ComposeNodeLifecycleCallback) {
                changeList.operations.pushOp(Operation.UseCurrentNode.INSTANCE);
            }
        }
    }

    public final boolean changed(boolean z) {
        Object objNextSlot = nextSlot();
        if ((objNextSlot instanceof Boolean) && z == ((Boolean) objNextSlot).booleanValue()) {
            return false;
        }
        updateValue(Boolean.valueOf(z));
        return true;
    }

    public final boolean changed(float f) {
        Object objNextSlot = nextSlot();
        if ((objNextSlot instanceof Float) && f == ((Number) objNextSlot).floatValue()) {
            return false;
        }
        updateValue(Float.valueOf(f));
        return true;
    }

    public final boolean changed(long j) {
        Object objNextSlot = nextSlot();
        if ((objNextSlot instanceof Long) && j == ((Number) objNextSlot).longValue()) {
            return false;
        }
        updateValue(Long.valueOf(j));
        return true;
    }

    public final boolean changed(int i) {
        Object objNextSlot = nextSlot();
        if ((objNextSlot instanceof Integer) && i == ((Number) objNextSlot).intValue()) {
            return false;
        }
        updateValue(Integer.valueOf(i));
        return true;
    }

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
