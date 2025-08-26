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

/* loaded from: classes.dex */
public abstract class Operation {
    public final int ints;
    public final int objects;

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

    public final class AppendValue extends Operation {
        public static final AppendValue INSTANCE = new AppendValue();

        private AppendValue() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor = (Anchor) opIterator.m338getObject31yXWZQ(0);
            Object objM338getObject31yXWZQ = opIterator.m338getObject31yXWZQ(1);
            if (objM338getObject31yXWZQ instanceof RememberObserverHolder) {
                rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) objM338getObject31yXWZQ);
            }
            if (slotWriter.insertCount != 0) {
                ComposerKt.composeImmediateRuntimeError("Can only append a slot if not current inserting");
            }
            int i = slotWriter.currentSlot;
            int i2 = slotWriter.currentSlotEnd;
            int iAnchorIndex = slotWriter.anchorIndex(anchor);
            int iDataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(iAnchorIndex + 1), slotWriter.groups);
            slotWriter.currentSlot = iDataIndex;
            slotWriter.currentSlotEnd = iDataIndex;
            slotWriter.insertSlots(1, iAnchorIndex);
            if (i >= iDataIndex) {
                i++;
                i2++;
            }
            slotWriter.slots[iDataIndex] = objM338getObject31yXWZQ;
            slotWriter.currentSlot = i;
            slotWriter.currentSlotEnd = i2;
        }
    }

    public final class ApplyChangeList extends Operation {
        public static final ApplyChangeList INSTANCE = new ApplyChangeList();

        private ApplyChangeList() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            IntRef intRef = (IntRef) opIterator.m338getObject31yXWZQ(1);
            int i = intRef != null ? intRef.element : 0;
            ChangeList changeList = (ChangeList) opIterator.m338getObject31yXWZQ(0);
            if (i > 0) {
                applier = new OffsetApplier(applier, i);
            }
            changeList.executeAndFlushAllPendingChanges(applier, slotWriter, rememberEventDispatcher, operationErrorContext != null ? new OperationKt$withCurrentStackTrace$1(operationErrorContext, slotWriter) : null);
        }
    }

    public final class CopyNodesToNewAnchorLocation extends Operation {
        public static final CopyNodesToNewAnchorLocation INSTANCE = new CopyNodesToNewAnchorLocation();

        private CopyNodesToNewAnchorLocation() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int i = ((IntRef) opIterator.m338getObject31yXWZQ(0)).element;
            List list = (List) opIterator.m338getObject31yXWZQ(1);
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object obj = list.get(i2);
                int i3 = i + i2;
                applier.insertBottomUp(i3, obj);
                applier.insertTopDown(i3, obj);
            }
        }
    }

    public final class CopySlotTableToAnchorLocation extends Operation {
        public static final CopySlotTableToAnchorLocation INSTANCE = new CopySlotTableToAnchorLocation();

        private CopySlotTableToAnchorLocation() {
            super(0, 4, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            MovableContentStateReference movableContentStateReference = (MovableContentStateReference) opIterator.m338getObject31yXWZQ(2);
            MovableContentStateReference movableContentStateReference2 = (MovableContentStateReference) opIterator.m338getObject31yXWZQ(3);
            CompositionContext compositionContext = (CompositionContext) opIterator.m338getObject31yXWZQ(1);
            MovableContentState movableContentStateMovableContentStateResolve$runtime_release = (MovableContentState) opIterator.m338getObject31yXWZQ(0);
            if (movableContentStateMovableContentStateResolve$runtime_release == null && (movableContentStateMovableContentStateResolve$runtime_release = compositionContext.movableContentStateResolve$runtime_release(movableContentStateReference)) == null) {
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
            SlotWriter slotWriterOpenWriter = movableContentStateMovableContentStateResolve$runtime_release.slotTable.openWriter();
            try {
                SlotWriter.Companion.getClass();
                List listMoveGroup = SlotWriter.Companion.moveGroup(slotWriterOpenWriter, 2, slotWriter, false, true, true);
                slotWriterOpenWriter.close(true);
                slotWriter.endInsert();
                slotWriter.endGroup();
                slotWriter.currentGroup = i;
                slotWriter.currentSlot = i2;
                slotWriter.currentSlotEnd = i3;
                RecomposeScopeImpl.Companion companion = RecomposeScopeImpl.Companion;
                RecomposeScopeOwner recomposeScopeOwner = (RecomposeScopeOwner) movableContentStateReference2.composition;
                companion.getClass();
                RecomposeScopeImpl.Companion.adoptAnchoredScopes$runtime_release(slotWriter, listMoveGroup, recomposeScopeOwner);
            } catch (Throwable th) {
                slotWriterOpenWriter.close(false);
                throw th;
            }
        }
    }

    public final class DeactivateCurrentGroup extends Operation {
        public static final DeactivateCurrentGroup INSTANCE = new DeactivateCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        private DeactivateCurrentGroup() {
            int i = 0;
            super(i, i, 3, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ComposerKt.deactivateCurrentGroup(slotWriter, rememberEventDispatcher);
        }
    }

    public final class DetermineMovableContentNodeIndex extends Operation {
        public static final DetermineMovableContentNodeIndex INSTANCE = new DetermineMovableContentNodeIndex();

        private DetermineMovableContentNodeIndex() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int i;
            IntRef intRef = (IntRef) opIterator.m338getObject31yXWZQ(0);
            int iAnchorIndex = slotWriter.anchorIndex((Anchor) opIterator.m338getObject31yXWZQ(1));
            if (!(slotWriter.currentGroup < iAnchorIndex)) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            OperationKt.positionToParentOf(slotWriter, applier, iAnchorIndex);
            int i2 = slotWriter.currentGroup;
            int iParent = slotWriter.parent;
            while (iParent >= 0 && !slotWriter.isNode(iParent)) {
                iParent = slotWriter.parent(iParent, slotWriter.groups);
            }
            int iGroupSize = iParent + 1;
            int iSkipGroup = 0;
            while (iGroupSize < i2) {
                if (slotWriter.indexInGroup(i2, iGroupSize)) {
                    if (slotWriter.isNode(iGroupSize)) {
                        iSkipGroup = 0;
                    }
                    iGroupSize++;
                } else {
                    iSkipGroup += slotWriter.isNode(iGroupSize) ? 1 : slotWriter.nodeCount(iGroupSize);
                    iGroupSize += slotWriter.groupSize(iGroupSize);
                }
            }
            while (true) {
                i = slotWriter.currentGroup;
                if (i >= iAnchorIndex) {
                    break;
                }
                if (slotWriter.indexInGroup(iAnchorIndex, i)) {
                    int i3 = slotWriter.currentGroup;
                    if (i3 < slotWriter.currentGroupEnd && (slotWriter.groups[(slotWriter.groupIndexToAddress(i3) * 5) + 1] & 1073741824) != 0) {
                        applier.down(slotWriter.node(slotWriter.currentGroup));
                        iSkipGroup = 0;
                    }
                    slotWriter.startGroup();
                } else {
                    iSkipGroup += slotWriter.skipGroup();
                }
            }
            if (i != iAnchorIndex) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            intRef.element = iSkipGroup;
        }
    }

    public final class Downs extends Operation {
        public static final Downs INSTANCE = new Downs();

        /* JADX WARN: Illegal instructions before constructor call */
        private Downs() {
            int i = 1;
            super(0, i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            for (Object obj : (Object[]) opIterator.m338getObject31yXWZQ(0)) {
                applier.down(obj);
            }
        }
    }

    public final class EndCompositionScope extends Operation {
        public static final EndCompositionScope INSTANCE = new EndCompositionScope();

        private EndCompositionScope() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ((Function1) opIterator.m338getObject31yXWZQ(0)).mo781invoke((Composition) opIterator.m338getObject31yXWZQ(1));
        }
    }

    public final class EndCurrentGroup extends Operation {
        public static final EndCurrentGroup INSTANCE = new EndCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        private EndCurrentGroup() {
            int i = 0;
            super(i, i, 3, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.endGroup();
        }
    }

    public final class EndMovableContentPlacement extends Operation {
        public static final EndMovableContentPlacement INSTANCE = new EndMovableContentPlacement();

        /* JADX WARN: Illegal instructions before constructor call */
        private EndMovableContentPlacement() {
            int i = 0;
            super(i, i, 3, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            OperationKt.positionToParentOf(slotWriter, applier, 0);
            slotWriter.endGroup();
        }
    }

    public final class EndResumingScope extends Operation {
        public static final EndResumingScope INSTANCE = new EndResumingScope();

        /* JADX WARN: Illegal instructions before constructor call */
        private EndResumingScope() {
            int i = 1;
            super(0, i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            MutableVector mutableVector;
            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) opIterator.m338getObject31yXWZQ(0);
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

    public final class EnsureGroupStarted extends Operation {
        public static final EnsureGroupStarted INSTANCE = new EnsureGroupStarted();

        /* JADX WARN: Illegal instructions before constructor call */
        private EnsureGroupStarted() {
            int i = 1;
            super(0, i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor = (Anchor) opIterator.m338getObject31yXWZQ(0);
            anchor.getClass();
            slotWriter.ensureStarted(slotWriter.anchorIndex(anchor));
        }
    }

    public final class EnsureRootGroupStarted extends Operation {
        public static final EnsureRootGroupStarted INSTANCE = new EnsureRootGroupStarted();

        /* JADX WARN: Illegal instructions before constructor call */
        private EnsureRootGroupStarted() {
            int i = 0;
            super(i, i, 3, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.ensureStarted(0);
        }
    }

    public final class InsertNodeFixup extends Operation {
        public static final InsertNodeFixup INSTANCE = new InsertNodeFixup();

        private InsertNodeFixup() {
            super(1, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Object objInvoke = ((Function0) opIterator.m338getObject31yXWZQ(0)).invoke();
            Anchor anchor = (Anchor) opIterator.m338getObject31yXWZQ(1);
            int i = opIterator.getInt(0);
            anchor.getClass();
            slotWriter.updateNodeOfGroup(slotWriter.anchorIndex(anchor), objInvoke);
            applier.insertTopDown(i, objInvoke);
            applier.down(objInvoke);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final Anchor getGroupAnchor(Operations.OpIterator opIterator) {
            return (Anchor) opIterator.m338getObject31yXWZQ(1);
        }
    }

    public final class InsertSlots extends Operation {
        public static final InsertSlots INSTANCE = new InsertSlots();

        private InsertSlots() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            SlotTable slotTable = (SlotTable) opIterator.m338getObject31yXWZQ(1);
            Anchor anchor = (Anchor) opIterator.m338getObject31yXWZQ(0);
            slotWriter.beginInsert();
            anchor.getClass();
            slotWriter.moveFrom(slotTable, slotTable.anchorIndex(anchor));
            slotWriter.endInsert();
        }
    }

    public final class InsertSlotsWithFixups extends Operation {
        public static final InsertSlotsWithFixups INSTANCE = new InsertSlotsWithFixups();

        private InsertSlotsWithFixups() {
            super(0, 3, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            OperationKt$withCurrentStackTrace$1 operationKt$withCurrentStackTrace$1;
            SlotTable slotTable = (SlotTable) opIterator.m338getObject31yXWZQ(1);
            Anchor anchor = (Anchor) opIterator.m338getObject31yXWZQ(0);
            FixupList fixupList = (FixupList) opIterator.m338getObject31yXWZQ(2);
            SlotWriter slotWriterOpenWriter = slotTable.openWriter();
            if (operationErrorContext != null) {
                try {
                    operationKt$withCurrentStackTrace$1 = new OperationKt$withCurrentStackTrace$1(operationErrorContext, slotWriter);
                } catch (Throwable th) {
                    slotWriterOpenWriter.close(false);
                    throw th;
                }
            } else {
                operationKt$withCurrentStackTrace$1 = null;
            }
            if (!fixupList.pendingOperations.isEmpty()) {
                ComposerKt.composeImmediateRuntimeError("FixupList has pending fixup operations that were not realized. Were there mismatched insertNode() and endNodeInsert() calls?");
            }
            fixupList.operations.executeAndFlushAllPendingOperations(applier, slotWriterOpenWriter, rememberEventDispatcher, operationKt$withCurrentStackTrace$1);
            Unit unit = Unit.INSTANCE;
            slotWriterOpenWriter.close(true);
            slotWriter.beginInsert();
            anchor.getClass();
            slotWriter.moveFrom(slotTable, slotTable.anchorIndex(anchor));
            slotWriter.endInsert();
        }
    }

    public final class MoveCurrentGroup extends Operation {
        public static final MoveCurrentGroup INSTANCE = new MoveCurrentGroup();

        private MoveCurrentGroup() {
            super(1, 0, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor;
            int iAnchorIndex;
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
            int iDataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.currentGroup), slotWriter.groups);
            int iDataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i5), slotWriter.groups);
            int i7 = i5 + i6;
            int iDataIndex3 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i7), slotWriter.groups);
            int i8 = iDataIndex3 - iDataIndex2;
            slotWriter.insertSlots(i8, Math.max(slotWriter.currentGroup - 1, 0));
            slotWriter.insertGroups(i6);
            int[] iArr = slotWriter.groups;
            int iGroupIndexToAddress = slotWriter.groupIndexToAddress(i7) * 5;
            ArraysKt___ArraysJvmKt.copyInto(slotWriter.groupIndexToAddress(i2) * 5, iGroupIndexToAddress, (i6 * 5) + iGroupIndexToAddress, iArr, iArr);
            if (i8 > 0) {
                Object[] objArr = slotWriter.slots;
                int iDataIndexToDataAddress = slotWriter.dataIndexToDataAddress(iDataIndex2 + i8);
                System.arraycopy(objArr, iDataIndexToDataAddress, objArr, iDataIndex, slotWriter.dataIndexToDataAddress(iDataIndex3 + i8) - iDataIndexToDataAddress);
            }
            int i9 = iDataIndex2 + i8;
            int i10 = i9 - iDataIndex;
            int i11 = slotWriter.slotsGapStart;
            int i12 = slotWriter.slotsGapLen;
            int length = slotWriter.slots.length;
            int i13 = slotWriter.slotsGapOwner;
            int i14 = i2 + i6;
            int i15 = i2;
            while (i15 < i14) {
                boolean z2 = z;
                int iGroupIndexToAddress2 = slotWriter.groupIndexToAddress(i15);
                int i16 = i15;
                iArr[(iGroupIndexToAddress2 * 5) + 4] = SlotWriter.dataIndexToDataAnchor(SlotWriter.dataIndexToDataAnchor(slotWriter.dataIndex(iGroupIndexToAddress2, iArr) - i10, i13 < iGroupIndexToAddress2 ? 0 : i11, i12, length), slotWriter.slotsGapStart, slotWriter.slotsGapLen, slotWriter.slots.length);
                i15 = i16 + 1;
                z = z2;
                i10 = i10;
                i11 = i11;
            }
            int i17 = i7 + i6;
            int size$runtime_release = slotWriter.getSize$runtime_release();
            int iAccess$locationOf = SlotTableKt.access$locationOf(slotWriter.anchors, i7, size$runtime_release);
            ArrayList arrayList = new ArrayList();
            if (iAccess$locationOf >= 0) {
                while (iAccess$locationOf < slotWriter.anchors.size() && (iAnchorIndex = slotWriter.anchorIndex((anchor = (Anchor) slotWriter.anchors.get(iAccess$locationOf)))) >= i7 && iAnchorIndex < i17) {
                    arrayList.add(anchor);
                    slotWriter.anchors.remove(iAccess$locationOf);
                }
            }
            int i18 = i2 - i7;
            int size = arrayList.size();
            for (int i19 = 0; i19 < size; i19++) {
                Anchor anchor2 = (Anchor) arrayList.get(i19);
                int iAnchorIndex2 = slotWriter.anchorIndex(anchor2) + i18;
                if (iAnchorIndex2 >= slotWriter.groupGapStart) {
                    anchor2.location = -(size$runtime_release - iAnchorIndex2);
                } else {
                    anchor2.location = iAnchorIndex2;
                }
                slotWriter.anchors.add(SlotTableKt.access$locationOf(slotWriter.anchors, iAnchorIndex2, size$runtime_release), anchor2);
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

    public final class PostInsertNodeFixup extends Operation {
        public static final PostInsertNodeFixup INSTANCE = new PostInsertNodeFixup();

        /* JADX WARN: Illegal instructions before constructor call */
        private PostInsertNodeFixup() {
            int i = 1;
            super(i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Anchor anchor = (Anchor) opIterator.m338getObject31yXWZQ(0);
            int i = opIterator.getInt(0);
            applier.up();
            anchor.getClass();
            applier.insertBottomUp(i, slotWriter.node(slotWriter.anchorIndex(anchor)));
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final Anchor getGroupAnchor(Operations.OpIterator opIterator) {
            return (Anchor) opIterator.m338getObject31yXWZQ(0);
        }
    }

    public final class ReleaseMovableGroupAtCurrent extends Operation {
        public static final ReleaseMovableGroupAtCurrent INSTANCE = new ReleaseMovableGroupAtCurrent();

        private ReleaseMovableGroupAtCurrent() {
            super(0, 3, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ControlledComposition controlledComposition = (ControlledComposition) opIterator.m338getObject31yXWZQ(0);
            MovableContentStateReference movableContentStateReference = (MovableContentStateReference) opIterator.m338getObject31yXWZQ(2);
            ((CompositionContext) opIterator.m338getObject31yXWZQ(1)).movableContentStateReleased$runtime_release(movableContentStateReference, ComposerKt.extractMovableContentAtCurrent(controlledComposition, movableContentStateReference, slotWriter, null), applier);
        }
    }

    public final class Remember extends Operation {
        public static final Remember INSTANCE = new Remember();

        /* JADX WARN: Illegal instructions before constructor call */
        private Remember() {
            int i = 1;
            super(0, i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) opIterator.m338getObject31yXWZQ(0));
        }
    }

    public final class RemoveCurrentGroup extends Operation {
        public static final RemoveCurrentGroup INSTANCE = new RemoveCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        private RemoveCurrentGroup() {
            int i = 0;
            super(i, i, 3, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ComposerKt.removeCurrentGroup(slotWriter, rememberEventDispatcher);
        }
    }

    public final class RemoveNode extends Operation {
        public static final RemoveNode INSTANCE = new RemoveNode();

        /* JADX WARN: Illegal instructions before constructor call */
        private RemoveNode() {
            int i = 2;
            super(i, 0, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            applier.remove(opIterator.getInt(0), opIterator.getInt(1));
        }
    }

    public final class ResetSlots extends Operation {
        public static final ResetSlots INSTANCE = new ResetSlots();

        /* JADX WARN: Illegal instructions before constructor call */
        private ResetSlots() {
            int i = 0;
            super(i, i, 3, null);
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

    public final class SideEffect extends Operation {
        public static final SideEffect INSTANCE = new SideEffect();

        /* JADX WARN: Illegal instructions before constructor call */
        private SideEffect() {
            int i = 1;
            super(0, i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            rememberEventDispatcher.sideEffects.add((Function0) opIterator.m338getObject31yXWZQ(0));
        }
    }

    public final class SkipToEndOfCurrentGroup extends Operation {
        public static final SkipToEndOfCurrentGroup INSTANCE = new SkipToEndOfCurrentGroup();

        /* JADX WARN: Illegal instructions before constructor call */
        private SkipToEndOfCurrentGroup() {
            int i = 0;
            super(i, i, 3, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.skipToGroupEnd();
        }
    }

    public final class StartResumingScope extends Operation {
        public static final StartResumingScope INSTANCE = new StartResumingScope();

        /* JADX WARN: Illegal instructions before constructor call */
        private StartResumingScope() {
            int i = 1;
            super(0, i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) opIterator.m338getObject31yXWZQ(0);
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

    public final class TrimParentValues extends Operation {
        public static final TrimParentValues INSTANCE = new TrimParentValues();

        private TrimParentValues() {
            super(1, 0, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int iAnchorIndex;
            int slotsSize;
            int i = opIterator.getInt(0);
            int slotsSize2 = slotWriter.getSlotsSize();
            int i2 = slotWriter.parent;
            int iSlotIndex = slotWriter.slotIndex(slotWriter.groupIndexToAddress(i2), slotWriter.groups);
            int iDataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i2 + 1), slotWriter.groups);
            for (int iMax = Math.max(iSlotIndex, iDataIndex - i); iMax < iDataIndex; iMax++) {
                Object obj = slotWriter.slots[slotWriter.dataIndexToDataAddress(iMax)];
                if (obj instanceof RememberObserverHolder) {
                    int i3 = slotsSize2 - iMax;
                    RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
                    Anchor anchor = rememberObserverHolder.after;
                    if (anchor == null || !anchor.getValid()) {
                        iAnchorIndex = -1;
                        slotsSize = -1;
                    } else {
                        iAnchorIndex = slotWriter.anchorIndex(anchor);
                        slotsSize = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(iAnchorIndex);
                    }
                    rememberEventDispatcher.recordLeaving(i3, iAnchorIndex, slotsSize, rememberObserverHolder);
                } else if (obj instanceof RecomposeScopeImpl) {
                    ((RecomposeScopeImpl) obj).release();
                }
            }
            if (!(i > 0)) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            int i4 = slotWriter.parent;
            int iSlotIndex2 = slotWriter.slotIndex(slotWriter.groupIndexToAddress(i4), slotWriter.groups);
            int iDataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i4 + 1), slotWriter.groups) - i;
            if (iDataIndex2 < iSlotIndex2) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            slotWriter.removeSlots(iDataIndex2, i, i4);
            int i5 = slotWriter.currentSlot;
            if (i5 >= iSlotIndex2) {
                slotWriter.currentSlot = i5 - i;
            }
        }
    }

    public final class UpdateAnchoredValue extends Operation {
        public static final UpdateAnchoredValue INSTANCE = new UpdateAnchoredValue();

        private UpdateAnchoredValue() {
            super(1, 2, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            int iAnchorIndex;
            int slotsSize;
            Object objM338getObject31yXWZQ = opIterator.m338getObject31yXWZQ(0);
            Anchor anchor = (Anchor) opIterator.m338getObject31yXWZQ(1);
            int i = opIterator.getInt(0);
            if (objM338getObject31yXWZQ instanceof RememberObserverHolder) {
                rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) objM338getObject31yXWZQ);
            }
            int iAnchorIndex2 = slotWriter.anchorIndex(anchor);
            int iDataIndexToDataAddress = slotWriter.dataIndexToDataAddress(slotWriter.slotIndexOfGroupSlotIndex(iAnchorIndex2, i));
            Object[] objArr = slotWriter.slots;
            Object obj = objArr[iDataIndexToDataAddress];
            objArr[iDataIndexToDataAddress] = objM338getObject31yXWZQ;
            if (!(obj instanceof RememberObserverHolder)) {
                if (obj instanceof RecomposeScopeImpl) {
                    ((RecomposeScopeImpl) obj).release();
                    return;
                }
                return;
            }
            int slotsSize2 = slotWriter.getSlotsSize() - slotWriter.slotIndexOfGroupSlotIndex(iAnchorIndex2, i);
            RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
            Anchor anchor2 = rememberObserverHolder.after;
            if (anchor2 == null || !anchor2.getValid()) {
                iAnchorIndex = -1;
                slotsSize = -1;
            } else {
                iAnchorIndex = slotWriter.anchorIndex(anchor2);
                slotsSize = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(iAnchorIndex);
            }
            rememberEventDispatcher.recordLeaving(slotsSize2, iAnchorIndex, slotsSize, rememberObserverHolder);
        }
    }

    public final class UpdateAuxData extends Operation {
        public static final UpdateAuxData INSTANCE = new UpdateAuxData();

        /* JADX WARN: Illegal instructions before constructor call */
        private UpdateAuxData() {
            int i = 1;
            super(0, i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            slotWriter.updateAux(opIterator.m338getObject31yXWZQ(0));
        }
    }

    public final class UpdateNode extends Operation {
        public static final UpdateNode INSTANCE = new UpdateNode();

        private UpdateNode() {
            super(0, 2, 1, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            ((Function2) opIterator.m338getObject31yXWZQ(1)).invoke(applier.getCurrent(), opIterator.m338getObject31yXWZQ(0));
        }
    }

    public final class UpdateValue extends Operation {
        public static final UpdateValue INSTANCE = new UpdateValue();

        /* JADX WARN: Illegal instructions before constructor call */
        private UpdateValue() {
            int i = 1;
            super(i, i, null);
        }

        @Override // androidx.compose.runtime.changelist.Operation
        public final void execute(Operations.OpIterator opIterator, Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
            Object objM338getObject31yXWZQ = opIterator.m338getObject31yXWZQ(0);
            int i = opIterator.getInt(0);
            if (objM338getObject31yXWZQ instanceof RememberObserverHolder) {
                rememberEventDispatcher.currentRememberingList.add((RememberObserverHolder) objM338getObject31yXWZQ);
            }
            int iDataIndexToDataAddress = slotWriter.dataIndexToDataAddress(slotWriter.slotIndexOfGroupSlotIndex(slotWriter.currentGroup, i));
            Object[] objArr = slotWriter.slots;
            Object obj = objArr[iDataIndexToDataAddress];
            objArr[iDataIndexToDataAddress] = objM338getObject31yXWZQ;
            if (obj instanceof RememberObserverHolder) {
                rememberEventDispatcher.recordLeaving(slotWriter.getSlotsSize() - slotWriter.slotIndexOfGroupSlotIndex(slotWriter.currentGroup, i), -1, -1, (RememberObserverHolder) obj);
            } else if (obj instanceof RecomposeScopeImpl) {
                ((RecomposeScopeImpl) obj).release();
            }
        }
    }

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

    public final class UseCurrentNode extends Operation {
        public static final UseCurrentNode INSTANCE = new UseCurrentNode();

        /* JADX WARN: Illegal instructions before constructor call */
        private UseCurrentNode() {
            int i = 0;
            super(i, i, 3, null);
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
