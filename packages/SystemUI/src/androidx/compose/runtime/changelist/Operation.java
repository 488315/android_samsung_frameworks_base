package androidx.compose.runtime.changelist;

import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.Composition;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.ControlledComposition;
import androidx.compose.runtime.MovableContentState;
import androidx.compose.runtime.MovableContentStateReference;
import androidx.compose.runtime.OffsetApplier;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeOwner;
import androidx.compose.runtime.RememberObserverHolder;
import androidx.compose.runtime.SlotTable;
import androidx.compose.runtime.SlotTableKt;
import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.changelist.Operations;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.IntRef;
import androidx.compose.runtime.internal.PausedCompositionRemembers;
import androidx.compose.runtime.internal.RememberEventDispatcher;
import java.util.ArrayList;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Operation {
    public final int ints;
    public final int objects;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AdvanceSlotsBy extends Operation {
        public static final AdvanceSlotsBy INSTANCE = new AdvanceSlotsBy();

        private AdvanceSlotsBy() {
            super(1, 0, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.advanceBy(opIterator.getInt(0));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AppendValue extends Operation {
        public static final AppendValue INSTANCE = new AppendValue();

        private AppendValue() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor = (Anchor) opIterator.m337getObject31yXWZQ(0);
            Object m337getObject31yXWZQ = opIterator.m337getObject31yXWZQ(1);
            if (m337getObject31yXWZQ instanceof RememberObserverHolder) {
                rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) m337getObject31yXWZQ);
            }
            if (slotWriter.insertCount != 0) {
                ComposerKt.composeImmediateRuntimeError("Can only append a slot if not current inserting");
            }
            int i = slotWriter.currentSlot;
            int i2 = slotWriter.currentSlotEnd;
            int anchorIndex = slotWriter.anchorIndex(anchor);
            int dataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(anchorIndex + 1), slotWriter.groups);
            slotWriter.currentSlot = dataIndex;
            slotWriter.currentSlotEnd = dataIndex;
            slotWriter.insertSlots(1, anchorIndex);
            if (i >= dataIndex) {
                i++;
                i2++;
            }
            slotWriter.slots[dataIndex] = m337getObject31yXWZQ;
            slotWriter.currentSlot = i;
            slotWriter.currentSlotEnd = i2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ApplyChangeList extends Operation {
        public static final ApplyChangeList INSTANCE = new ApplyChangeList();

        private ApplyChangeList() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            IntRef intRef = (IntRef) opIterator.m337getObject31yXWZQ(1);
            int i = intRef != null ? intRef.element : 0;
            ChangeList changeList = (ChangeList) opIterator.m337getObject31yXWZQ(0);
            if (i > 0) {
                applier = new OffsetApplier(applier, i);
            }
            changeList.executeAndFlushAllPendingChanges(applier, slotWriter, rememberEventDispatcher, operationErrorContext != null ? new OperationKt$withCurrentStackTrace$1(operationErrorContext, slotWriter) : null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CopyNodesToNewAnchorLocation extends Operation {
        public static final CopyNodesToNewAnchorLocation INSTANCE = new CopyNodesToNewAnchorLocation();

        private CopyNodesToNewAnchorLocation() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int i = ((IntRef) opIterator.m337getObject31yXWZQ(0)).element;
            List list = (List) opIterator.m337getObject31yXWZQ(1);
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object obj = list.get(i2);
                int i3 = i + i2;
                applier.insertBottomUp(i3, obj);
                applier.insertTopDown(i3, obj);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CopySlotTableToAnchorLocation extends Operation {
        public static final CopySlotTableToAnchorLocation INSTANCE = new CopySlotTableToAnchorLocation();

        private CopySlotTableToAnchorLocation() {
            super(0, 4, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            MovableContentStateReference movableContentStateReference = (MovableContentStateReference) opIterator.m337getObject31yXWZQ(2);
            MovableContentStateReference movableContentStateReference2 = (MovableContentStateReference) opIterator.m337getObject31yXWZQ(3);
            CompositionContext compositionContext = (CompositionContext) opIterator.m337getObject31yXWZQ(1);
            MovableContentState movableContentState = (MovableContentState) opIterator.m337getObject31yXWZQ(0);
            if (movableContentState == null && (movableContentState = compositionContext.movableContentStateResolve$runtime_release(movableContentStateReference)) == null) {
                ComposerKt.composeRuntimeError("Could not resolve state for movable content");
                throw new KotlinNothingValueException();
            }
            if (slotWriter.insertCount > 0 || slotWriter.groupSize(slotWriter.currentGroup + 1) != 1) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            int i = slotWriter.currentGroup;
            int i2 = slotWriter.currentSlot;
            int i3 = slotWriter.currentSlotEnd;
            slotWriter.advanceBy(1);
            slotWriter.startGroup();
            slotWriter.beginInsert();
            SlotWriter openWriter = movableContentState.slotTable.openWriter();
            try {
                SlotWriter.Companion.getClass();
                List moveGroup = SlotWriter.Companion.moveGroup(openWriter, 2, slotWriter, false, true, true);
                openWriter.close(true);
                slotWriter.endInsert();
                slotWriter.endGroup();
                slotWriter.currentGroup = i;
                slotWriter.currentSlot = i2;
                slotWriter.currentSlotEnd = i3;
                RecomposeScopeImpl.Companion companion = RecomposeScopeImpl.Companion;
                RecomposeScopeOwner recomposeScopeOwner = (RecomposeScopeOwner) movableContentStateReference2.composition;
                companion.getClass();
                RecomposeScopeImpl.Companion.adoptAnchoredScopes$runtime_release(slotWriter, moveGroup, recomposeScopeOwner);
            } catch (Throwable th) {
                openWriter.close(false);
                throw th;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeactivateCurrentGroup extends Operation {
        public static final DeactivateCurrentGroup INSTANCE = new DeactivateCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private DeactivateCurrentGroup() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.DeactivateCurrentGroup.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ComposerKt.deactivateCurrentGroup(slotWriter, rememberEventDispatcher);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DetermineMovableContentNodeIndex extends Operation {
        public static final DetermineMovableContentNodeIndex INSTANCE = new DetermineMovableContentNodeIndex();

        private DetermineMovableContentNodeIndex() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int i;
            IntRef intRef = (IntRef) opIterator.m337getObject31yXWZQ(0);
            int anchorIndex = slotWriter.anchorIndex((Anchor) opIterator.m337getObject31yXWZQ(1));
            if (!(slotWriter.currentGroup < anchorIndex)) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            OperationKt.positionToParentOf(slotWriter, applier, anchorIndex);
            int i2 = slotWriter.currentGroup;
            int i3 = slotWriter.parent;
            while (i3 >= 0 && !slotWriter.isNode(i3)) {
                i3 = slotWriter.parent(i3, slotWriter.groups);
            }
            int i4 = i3 + 1;
            int i5 = 0;
            while (i4 < i2) {
                if (slotWriter.indexInGroup(i2, i4)) {
                    if (slotWriter.isNode(i4)) {
                        i5 = 0;
                    }
                    i4++;
                } else {
                    i5 += slotWriter.isNode(i4) ? 1 : slotWriter.nodeCount(i4);
                    i4 += slotWriter.groupSize(i4);
                }
            }
            while (true) {
                i = slotWriter.currentGroup;
                if (i >= anchorIndex) {
                    break;
                }
                if (slotWriter.indexInGroup(anchorIndex, i)) {
                    int i6 = slotWriter.currentGroup;
                    if (i6 < slotWriter.currentGroupEnd && (slotWriter.groups[(slotWriter.groupIndexToAddress(i6) * 5) + 1] & 1073741824) != 0) {
                        applier.down(slotWriter.node(slotWriter.currentGroup));
                        i5 = 0;
                    }
                    slotWriter.startGroup();
                } else {
                    i5 += slotWriter.skipGroup();
                }
            }
            if (i != anchorIndex) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            intRef.element = i5;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Downs extends Operation {
        public static final Downs INSTANCE = new Downs();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Downs() {
            /*
                r3 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                r3.<init>(r2, r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.Downs.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            for (Object obj : (Object[]) opIterator.m337getObject31yXWZQ(0)) {
                applier.down(obj);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EndCompositionScope extends Operation {
        public static final EndCompositionScope INSTANCE = new EndCompositionScope();

        private EndCompositionScope() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ((Function1) opIterator.m337getObject31yXWZQ(0)).mo779invoke((Composition) opIterator.m337getObject31yXWZQ(1));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EndCurrentGroup extends Operation {
        public static final EndCurrentGroup INSTANCE = new EndCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private EndCurrentGroup() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.EndCurrentGroup.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.endGroup();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EndMovableContentPlacement extends Operation {
        public static final EndMovableContentPlacement INSTANCE = new EndMovableContentPlacement();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private EndMovableContentPlacement() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.EndMovableContentPlacement.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            OperationKt.positionToParentOf(slotWriter, applier, 0);
            slotWriter.endGroup();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EndResumingScope extends Operation {
        public static final EndResumingScope INSTANCE = new EndResumingScope();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private EndResumingScope() {
            /*
                r3 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                r3.<init>(r2, r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.EndResumingScope.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            MutableVector mutableVector;
            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) opIterator.m337getObject31yXWZQ(0);
            MutableScatterMap mutableScatterMap = rememberEventDispatcher.pausedPlaceholders;
            if (mutableScatterMap == null || ((PausedCompositionRemembers) mutableScatterMap.get(recomposeScopeImpl)) == null) {
                return;
            }
            ArrayList arrayList = rememberEventDispatcher.nestedRemembersLists;
            if (arrayList != null && (mutableVector = (MutableVector) arrayList.remove(arrayList.size() - 1)) != null) {
                rememberEventDispatcher.currentRememberingList = mutableVector;
            }
            mutableScatterMap.remove(recomposeScopeImpl);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EnsureGroupStarted extends Operation {
        public static final EnsureGroupStarted INSTANCE = new EnsureGroupStarted();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private EnsureGroupStarted() {
            /*
                r3 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                r3.<init>(r2, r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.EnsureGroupStarted.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor = (Anchor) opIterator.m337getObject31yXWZQ(0);
            anchor.getClass();
            slotWriter.ensureStarted(slotWriter.anchorIndex(anchor));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EnsureRootGroupStarted extends Operation {
        public static final EnsureRootGroupStarted INSTANCE = new EnsureRootGroupStarted();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private EnsureRootGroupStarted() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.EnsureRootGroupStarted.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.ensureStarted(0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InsertNodeFixup extends Operation {
        public static final InsertNodeFixup INSTANCE = new InsertNodeFixup();

        private InsertNodeFixup() {
            super(1, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Object invoke = ((Function0) opIterator.m337getObject31yXWZQ(0)).invoke();
            Anchor anchor = (Anchor) opIterator.m337getObject31yXWZQ(1);
            int i = opIterator.getInt(0);
            anchor.getClass();
            slotWriter.updateNodeOfGroup(slotWriter.anchorIndex(anchor), invoke);
            applier.insertTopDown(i, invoke);
            applier.down(invoke);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final Anchor getGroupAnchor(Operations.OpIterator opIterator) {
            return (Anchor) opIterator.m337getObject31yXWZQ(1);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InsertSlots extends Operation {
        public static final InsertSlots INSTANCE = new InsertSlots();

        private InsertSlots() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            SlotTable slotTable = (SlotTable) opIterator.m337getObject31yXWZQ(1);
            Anchor anchor = (Anchor) opIterator.m337getObject31yXWZQ(0);
            slotWriter.beginInsert();
            anchor.getClass();
            slotWriter.moveFrom(slotTable, slotTable.anchorIndex(anchor));
            slotWriter.endInsert();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InsertSlotsWithFixups extends Operation {
        public static final InsertSlotsWithFixups INSTANCE = new InsertSlotsWithFixups();

        private InsertSlotsWithFixups() {
            super(0, 3, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            OperationKt$withCurrentStackTrace$1 operationKt$withCurrentStackTrace$1;
            SlotTable slotTable = (SlotTable) opIterator.m337getObject31yXWZQ(1);
            Anchor anchor = (Anchor) opIterator.m337getObject31yXWZQ(0);
            FixupList fixupList = (FixupList) opIterator.m337getObject31yXWZQ(2);
            SlotWriter openWriter = slotTable.openWriter();
            if (operationErrorContext != null) {
                try {
                    operationKt$withCurrentStackTrace$1 = new OperationKt$withCurrentStackTrace$1(operationErrorContext, slotWriter);
                } catch (Throwable th) {
                    openWriter.close(false);
                    throw th;
                }
            } else {
                operationKt$withCurrentStackTrace$1 = null;
            }
            if (!fixupList.pendingOperations.isEmpty()) {
                ComposerKt.composeImmediateRuntimeError("FixupList has pending fixup operations that were not realized. Were there mismatched insertNode() and endNodeInsert() calls?");
            }
            fixupList.operations.executeAndFlushAllPendingOperations(applier, openWriter, rememberEventDispatcher, operationKt$withCurrentStackTrace$1);
            Unit unit = Unit.INSTANCE;
            openWriter.close(true);
            slotWriter.beginInsert();
            anchor.getClass();
            slotWriter.moveFrom(slotTable, slotTable.anchorIndex(anchor));
            slotWriter.endInsert();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MoveCurrentGroup extends Operation {
        public static final MoveCurrentGroup INSTANCE = new MoveCurrentGroup();

        private MoveCurrentGroup() {
            super(1, 0, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor;
            int anchorIndex;
            int i = opIterator.getInt(0);
            if (slotWriter.insertCount != 0) {
                ComposerKt.composeImmediateRuntimeError("Cannot move a group while inserting");
            }
            boolean z = true;
            if (!(i >= 0)) {
                ComposerKt.composeImmediateRuntimeError("Parameter offset is out of bounds");
            }
            if (i == 0) {
                return;
            }
            int i2 = slotWriter.currentGroup;
            int i3 = slotWriter.parent;
            int i4 = slotWriter.currentGroupEnd;
            int i5 = i2;
            while (i > 0) {
                i5 += slotWriter.groups[(slotWriter.groupIndexToAddress(i5) * 5) + 3];
                if (i5 > i4) {
                    ComposerKt.composeImmediateRuntimeError("Parameter offset is out of bounds");
                }
                i--;
            }
            int i6 = slotWriter.groups[(slotWriter.groupIndexToAddress(i5) * 5) + 3];
            int dataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.currentGroup), slotWriter.groups);
            int dataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i5), slotWriter.groups);
            int i7 = i5 + i6;
            int dataIndex3 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i7), slotWriter.groups);
            int i8 = dataIndex3 - dataIndex2;
            slotWriter.insertSlots(i8, Math.max(slotWriter.currentGroup - 1, 0));
            slotWriter.insertGroups(i6);
            int[] iArr = slotWriter.groups;
            int groupIndexToAddress = slotWriter.groupIndexToAddress(i7) * 5;
            ArraysKt___ArraysJvmKt.copyInto(slotWriter.groupIndexToAddress(i2) * 5, groupIndexToAddress, (i6 * 5) + groupIndexToAddress, iArr, iArr);
            if (i8 > 0) {
                Object[] objArr = slotWriter.slots;
                int dataIndexToDataAddress = slotWriter.dataIndexToDataAddress(dataIndex2 + i8);
                System.arraycopy(objArr, dataIndexToDataAddress, objArr, dataIndex, slotWriter.dataIndexToDataAddress(dataIndex3 + i8) - dataIndexToDataAddress);
            }
            int i9 = dataIndex2 + i8;
            int i10 = i9 - dataIndex;
            int i11 = slotWriter.slotsGapStart;
            int i12 = slotWriter.slotsGapLen;
            int length = slotWriter.slots.length;
            int i13 = slotWriter.slotsGapOwner;
            int i14 = i2 + i6;
            int i15 = i2;
            while (i15 < i14) {
                boolean z2 = z;
                int groupIndexToAddress2 = slotWriter.groupIndexToAddress(i15);
                int i16 = i15;
                iArr[(groupIndexToAddress2 * 5) + 4] = SlotWriter.dataIndexToDataAnchor(SlotWriter.dataIndexToDataAnchor(slotWriter.dataIndex(groupIndexToAddress2, iArr) - i10, i13 < groupIndexToAddress2 ? 0 : i11, i12, length), slotWriter.slotsGapStart, slotWriter.slotsGapLen, slotWriter.slots.length);
                i15 = i16 + 1;
                z = z2;
                i10 = i10;
                i11 = i11;
            }
            int i17 = i7 + i6;
            int size$runtime_release = slotWriter.getSize$runtime_release();
            int access$locationOf = SlotTableKt.access$locationOf(slotWriter.anchors, i7, size$runtime_release);
            ArrayList arrayList = new ArrayList();
            if (access$locationOf >= 0) {
                while (access$locationOf < slotWriter.anchors.size() && (anchorIndex = slotWriter.anchorIndex((anchor = (Anchor) slotWriter.anchors.get(access$locationOf)))) >= i7 && anchorIndex < i17) {
                    arrayList.add(anchor);
                    slotWriter.anchors.remove(access$locationOf);
                }
            }
            int i18 = i2 - i7;
            int size = arrayList.size();
            for (int i19 = 0; i19 < size; i19++) {
                Anchor anchor2 = (Anchor) arrayList.get(i19);
                int anchorIndex2 = slotWriter.anchorIndex(anchor2) + i18;
                if (anchorIndex2 >= slotWriter.groupGapStart) {
                    anchor2.location = -(size$runtime_release - anchorIndex2);
                } else {
                    anchor2.location = anchorIndex2;
                }
                slotWriter.anchors.add(SlotTableKt.access$locationOf(slotWriter.anchors, anchorIndex2, size$runtime_release), anchor2);
            }
            if (slotWriter.removeGroups(i7, i6)) {
                ComposerKt.composeImmediateRuntimeError("Unexpectedly removed anchors");
            }
            slotWriter.fixParentAnchorsFor(i3, slotWriter.currentGroupEnd, i2);
            if (i8 > 0) {
                slotWriter.removeSlots(i9, i8, i7 - 1);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MoveNode extends Operation {
        public static final MoveNode INSTANCE = new MoveNode();

        private MoveNode() {
            super(3, 0, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            applier.move(opIterator.getInt(0), opIterator.getInt(1), opIterator.getInt(2));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PostInsertNodeFixup extends Operation {
        public static final PostInsertNodeFixup INSTANCE = new PostInsertNodeFixup();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private PostInsertNodeFixup() {
            /*
                r2 = this;
                r0 = 1
                r1 = 0
                r2.<init>(r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.PostInsertNodeFixup.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor = (Anchor) opIterator.m337getObject31yXWZQ(0);
            int i = opIterator.getInt(0);
            applier.up();
            anchor.getClass();
            applier.insertBottomUp(i, slotWriter.node(slotWriter.anchorIndex(anchor)));
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final Anchor getGroupAnchor(Operations.OpIterator opIterator) {
            return (Anchor) opIterator.m337getObject31yXWZQ(0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReleaseMovableGroupAtCurrent extends Operation {
        public static final ReleaseMovableGroupAtCurrent INSTANCE = new ReleaseMovableGroupAtCurrent();

        private ReleaseMovableGroupAtCurrent() {
            super(0, 3, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ControlledComposition controlledComposition = (ControlledComposition) opIterator.m337getObject31yXWZQ(0);
            MovableContentStateReference movableContentStateReference = (MovableContentStateReference) opIterator.m337getObject31yXWZQ(2);
            ((CompositionContext) opIterator.m337getObject31yXWZQ(1)).movableContentStateReleased$runtime_release(movableContentStateReference, ComposerKt.extractMovableContentAtCurrent(controlledComposition, movableContentStateReference, slotWriter, null), applier);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Remember extends Operation {
        public static final Remember INSTANCE = new Remember();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Remember() {
            /*
                r3 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                r3.<init>(r2, r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.Remember.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) opIterator.m337getObject31yXWZQ(0));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RemoveCurrentGroup extends Operation {
        public static final RemoveCurrentGroup INSTANCE = new RemoveCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private RemoveCurrentGroup() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.RemoveCurrentGroup.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ComposerKt.removeCurrentGroup(slotWriter, rememberEventDispatcher);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RemoveNode extends Operation {
        public static final RemoveNode INSTANCE = new RemoveNode();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private RemoveNode() {
            /*
                r3 = this;
                r0 = 0
                r1 = 0
                r2 = 2
                r3.<init>(r2, r0, r2, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.RemoveNode.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            applier.remove(opIterator.getInt(0), opIterator.getInt(1));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ResetSlots extends Operation {
        public static final ResetSlots INSTANCE = new ResetSlots();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private ResetSlots() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.ResetSlots.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            if (slotWriter.insertCount != 0) {
                ComposerKt.composeImmediateRuntimeError("Cannot reset when inserting");
            }
            slotWriter.recalculateMarks();
            slotWriter.currentGroup = 0;
            slotWriter.currentGroupEnd = slotWriter.getCapacity() - slotWriter.groupGapLen;
            slotWriter.currentSlot = 0;
            slotWriter.currentSlotEnd = 0;
            slotWriter.nodeCount = 0;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SideEffect extends Operation {
        public static final SideEffect INSTANCE = new SideEffect();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private SideEffect() {
            /*
                r3 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                r3.<init>(r2, r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.SideEffect.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            rememberEventDispatcher.sideEffects.add((Function0) opIterator.m337getObject31yXWZQ(0));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SkipToEndOfCurrentGroup extends Operation {
        public static final SkipToEndOfCurrentGroup INSTANCE = new SkipToEndOfCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private SkipToEndOfCurrentGroup() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.SkipToEndOfCurrentGroup.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.skipToGroupEnd();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StartResumingScope extends Operation {
        public static final StartResumingScope INSTANCE = new StartResumingScope();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private StartResumingScope() {
            /*
                r3 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                r3.<init>(r2, r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.StartResumingScope.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) opIterator.m337getObject31yXWZQ(0);
            MutableScatterMap mutableScatterMap = rememberEventDispatcher.pausedPlaceholders;
            PausedCompositionRemembers pausedCompositionRemembers = mutableScatterMap != null ? (PausedCompositionRemembers) mutableScatterMap.get(recomposeScopeImpl) : null;
            if (pausedCompositionRemembers != null) {
                ArrayList arrayList = rememberEventDispatcher.nestedRemembersLists;
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    rememberEventDispatcher.nestedRemembersLists = arrayList;
                }
                arrayList.add(rememberEventDispatcher.currentRememberingList);
                rememberEventDispatcher.currentRememberingList = pausedCompositionRemembers.pausedRemembers;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TrimParentValues extends Operation {
        public static final TrimParentValues INSTANCE = new TrimParentValues();

        private TrimParentValues() {
            super(1, 0, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int i;
            int i2;
            int i3 = opIterator.getInt(0);
            int slotsSize = slotWriter.getSlotsSize();
            int i4 = slotWriter.parent;
            int slotIndex = slotWriter.slotIndex(slotWriter.groupIndexToAddress(i4), slotWriter.groups);
            int dataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i4 + 1), slotWriter.groups);
            for (int max = Math.max(slotIndex, dataIndex - i3); max < dataIndex; max++) {
                Object obj = slotWriter.slots[slotWriter.dataIndexToDataAddress(max)];
                if (obj instanceof RememberObserverHolder) {
                    int i5 = slotsSize - max;
                    RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
                    Anchor anchor = rememberObserverHolder.after;
                    if (anchor == null || !anchor.getValid()) {
                        i = -1;
                        i2 = -1;
                    } else {
                        i = slotWriter.anchorIndex(anchor);
                        i2 = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(i);
                    }
                    rememberEventDispatcher.recordLeaving(i5, i, i2, rememberObserverHolder);
                } else if (obj instanceof RecomposeScopeImpl) {
                    ((RecomposeScopeImpl) obj).release();
                }
            }
            if (!(i3 > 0)) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            int i6 = slotWriter.parent;
            int slotIndex2 = slotWriter.slotIndex(slotWriter.groupIndexToAddress(i6), slotWriter.groups);
            int dataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i6 + 1), slotWriter.groups) - i3;
            if (dataIndex2 < slotIndex2) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            slotWriter.removeSlots(dataIndex2, i3, i6);
            int i7 = slotWriter.currentSlot;
            if (i7 >= slotIndex2) {
                slotWriter.currentSlot = i7 - i3;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UpdateAnchoredValue extends Operation {
        public static final UpdateAnchoredValue INSTANCE = new UpdateAnchoredValue();

        private UpdateAnchoredValue() {
            super(1, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int i;
            int i2;
            Object m337getObject31yXWZQ = opIterator.m337getObject31yXWZQ(0);
            Anchor anchor = (Anchor) opIterator.m337getObject31yXWZQ(1);
            int i3 = opIterator.getInt(0);
            if (m337getObject31yXWZQ instanceof RememberObserverHolder) {
                rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) m337getObject31yXWZQ);
            }
            int anchorIndex = slotWriter.anchorIndex(anchor);
            int dataIndexToDataAddress = slotWriter.dataIndexToDataAddress(slotWriter.slotIndexOfGroupSlotIndex(anchorIndex, i3));
            Object[] objArr = slotWriter.slots;
            Object obj = objArr[dataIndexToDataAddress];
            objArr[dataIndexToDataAddress] = m337getObject31yXWZQ;
            if (!(obj instanceof RememberObserverHolder)) {
                if (obj instanceof RecomposeScopeImpl) {
                    ((RecomposeScopeImpl) obj).release();
                    return;
                }
                return;
            }
            int slotsSize = slotWriter.getSlotsSize() - slotWriter.slotIndexOfGroupSlotIndex(anchorIndex, i3);
            RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
            Anchor anchor2 = rememberObserverHolder.after;
            if (anchor2 == null || !anchor2.getValid()) {
                i = -1;
                i2 = -1;
            } else {
                i = slotWriter.anchorIndex(anchor2);
                i2 = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(i);
            }
            rememberEventDispatcher.recordLeaving(slotsSize, i, i2, rememberObserverHolder);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UpdateAuxData extends Operation {
        public static final UpdateAuxData INSTANCE = new UpdateAuxData();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private UpdateAuxData() {
            /*
                r3 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                r3.<init>(r2, r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.UpdateAuxData.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.updateAux(opIterator.m337getObject31yXWZQ(0));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UpdateNode extends Operation {
        public static final UpdateNode INSTANCE = new UpdateNode();

        private UpdateNode() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ((Function2) opIterator.m337getObject31yXWZQ(1)).invoke(applier.getCurrent(), opIterator.m337getObject31yXWZQ(0));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UpdateValue extends Operation {
        public static final UpdateValue INSTANCE = new UpdateValue();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private UpdateValue() {
            /*
                r2 = this;
                r0 = 1
                r1 = 0
                r2.<init>(r0, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.UpdateValue.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Object m337getObject31yXWZQ = opIterator.m337getObject31yXWZQ(0);
            int i = opIterator.getInt(0);
            if (m337getObject31yXWZQ instanceof RememberObserverHolder) {
                rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) m337getObject31yXWZQ);
            }
            int dataIndexToDataAddress = slotWriter.dataIndexToDataAddress(slotWriter.slotIndexOfGroupSlotIndex(slotWriter.currentGroup, i));
            Object[] objArr = slotWriter.slots;
            Object obj = objArr[dataIndexToDataAddress];
            objArr[dataIndexToDataAddress] = m337getObject31yXWZQ;
            if (obj instanceof RememberObserverHolder) {
                rememberEventDispatcher.recordLeaving(slotWriter.getSlotsSize() - slotWriter.slotIndexOfGroupSlotIndex(slotWriter.currentGroup, i), -1, -1, (RememberObserverHolder) obj);
            } else if (obj instanceof RecomposeScopeImpl) {
                ((RecomposeScopeImpl) obj).release();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Ups extends Operation {
        public static final Ups INSTANCE = new Ups();

        private Ups() {
            super(1, 0, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int i = opIterator.getInt(0);
            for (int i2 = 0; i2 < i; i2++) {
                applier.up();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UseCurrentNode extends Operation {
        public static final UseCurrentNode INSTANCE = new UseCurrentNode();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private UseCurrentNode() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.changelist.Operation.UseCurrentNode.<init>():void");
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            applier.reuse();
        }
    }

    public /* synthetic */ Operation(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2);
    }

    public abstract void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext);

    public Anchor getGroupAnchor(Operations.OpIterator opIterator) {
        return null;
    }

    public final String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        return simpleName == null ? "" : simpleName;
    }

    private Operation(int i, int i2) {
        this.ints = i;
        this.objects = i2;
    }

    public /* synthetic */ Operation(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, null);
    }
}
