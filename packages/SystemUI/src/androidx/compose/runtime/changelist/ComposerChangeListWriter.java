package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.IntStack;
import androidx.compose.runtime.MovableContentState;
import androidx.compose.runtime.MovableContentStateReference;
import androidx.compose.runtime.SlotReader;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.changelist.Operations;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposerChangeListWriter {
    public ChangeList changeList;
    public final ComposerImpl composer;
    public int moveCount;
    public int pendingUps;
    public boolean startedGroup;
    public int writersReaderDelta;
    public final IntStack startedGroups = new IntStack();
    public boolean implicitRootStart = true;
    public final ArrayList pendingDownNodes = new ArrayList();
    public int removeFrom = -1;
    public int moveFrom = -1;
    public int moveTo = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ComposerChangeListWriter(ComposerImpl composerImpl, ChangeList changeList) {
        this.composer = composerImpl;
        this.changeList = changeList;
    }

    public final void copySlotTableToAnchorLocation(MovableContentState movableContentState, CompositionContext compositionContext, MovableContentStateReference movableContentStateReference, MovableContentStateReference movableContentStateReference2) {
        ChangeList changeList = this.changeList;
        changeList.getClass();
        Operation.CopySlotTableToAnchorLocation copySlotTableToAnchorLocation = Operation.CopySlotTableToAnchorLocation.INSTANCE;
        Operations operations = changeList.operations;
        operations.pushOp(copySlotTableToAnchorLocation);
        int i = operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].objects;
        Object[] objArr = operations.objectArgs;
        objArr[i] = movableContentState;
        objArr[i + 1] = compositionContext;
        objArr[i + 3] = movableContentStateReference2;
        objArr[i + 2] = movableContentStateReference;
    }

    public final void moveUp() {
        realizeNodeMovementOperations();
        if (this.pendingDownNodes.isEmpty()) {
            this.pendingUps++;
        } else {
            this.pendingDownNodes.remove(r1.size() - 1);
        }
    }

    public final void pushPendingUpsAndDowns() {
        int i = this.pendingUps;
        if (i > 0) {
            ChangeList changeList = this.changeList;
            changeList.getClass();
            Operation.Ups ups = Operation.Ups.INSTANCE;
            Operations operations = changeList.operations;
            operations.pushOp(ups);
            operations.intArgs[operations.intArgsSize - operations.opCodes[operations.opCodesSize - 1].ints] = i;
            this.pendingUps = 0;
        }
        if (this.pendingDownNodes.isEmpty()) {
            return;
        }
        ChangeList changeList2 = this.changeList;
        ArrayList arrayList = this.pendingDownNodes;
        int size = arrayList.size();
        Object[] objArr = new Object[size];
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i2] = arrayList.get(i2);
        }
        changeList2.getClass();
        if (size != 0) {
            Operation.Downs downs = Operation.Downs.INSTANCE;
            Operations operations2 = changeList2.operations;
            operations2.pushOp(downs);
            Operations.WriteScope.m338setObjectDKhxnng(operations2, 0, objArr);
        }
        this.pendingDownNodes.clear();
    }

    public final void realizeNodeMovementOperations() {
        int i = this.moveCount;
        if (i > 0) {
            int i2 = this.removeFrom;
            if (i2 >= 0) {
                pushPendingUpsAndDowns();
                ChangeList changeList = this.changeList;
                changeList.getClass();
                Operation.RemoveNode removeNode = Operation.RemoveNode.INSTANCE;
                Operations operations = changeList.operations;
                operations.pushOp(removeNode);
                int i3 = operations.intArgsSize - operations.opCodes[operations.opCodesSize - 1].ints;
                int[] iArr = operations.intArgs;
                iArr[i3] = i2;
                iArr[i3 + 1] = i;
                this.removeFrom = -1;
            } else {
                int i4 = this.moveTo;
                int i5 = this.moveFrom;
                pushPendingUpsAndDowns();
                ChangeList changeList2 = this.changeList;
                changeList2.getClass();
                Operation.MoveNode moveNode = Operation.MoveNode.INSTANCE;
                Operations operations2 = changeList2.operations;
                operations2.pushOp(moveNode);
                int i6 = operations2.intArgsSize - operations2.opCodes[operations2.opCodesSize - 1].ints;
                int[] iArr2 = operations2.intArgs;
                iArr2[i6 + 1] = i4;
                iArr2[i6] = i5;
                iArr2[i6 + 2] = i;
                this.moveFrom = -1;
                this.moveTo = -1;
            }
            this.moveCount = 0;
        }
    }

    public final void realizeOperationLocation(boolean z) {
        ComposerImpl composerImpl = this.composer;
        int i = z ? composerImpl.reader.parent : composerImpl.reader.currentGroup;
        int i2 = i - this.writersReaderDelta;
        if (i2 < 0) {
            ComposerKt.composeImmediateRuntimeError("Tried to seek backward");
        }
        if (i2 > 0) {
            ChangeList changeList = this.changeList;
            changeList.getClass();
            Operation.AdvanceSlotsBy advanceSlotsBy = Operation.AdvanceSlotsBy.INSTANCE;
            Operations operations = changeList.operations;
            operations.pushOp(advanceSlotsBy);
            operations.intArgs[operations.intArgsSize - operations.opCodes[operations.opCodesSize - 1].ints] = i2;
            this.writersReaderDelta = i;
        }
    }

    public final void recordSlotEditing() {
        SlotReader slotReader = this.composer.reader;
        if (slotReader.groupsSize > 0) {
            int i = slotReader.parent;
            IntStack intStack = this.startedGroups;
            if (intStack.peekOr(-2) != i) {
                if (!this.startedGroup && this.implicitRootStart) {
                    realizeOperationLocation(false);
                    ChangeList changeList = this.changeList;
                    changeList.getClass();
                    changeList.operations.pushOp(Operation.EnsureRootGroupStarted.INSTANCE);
                    this.startedGroup = true;
                }
                if (i > 0) {
                    Anchor anchor = slotReader.anchor(i);
                    intStack.push(i);
                    realizeOperationLocation(false);
                    ChangeList changeList2 = this.changeList;
                    changeList2.getClass();
                    Operation.EnsureGroupStarted ensureGroupStarted = Operation.EnsureGroupStarted.INSTANCE;
                    Operations operations = changeList2.operations;
                    operations.pushOp(ensureGroupStarted);
                    Operations.WriteScope.m338setObjectDKhxnng(operations, 0, anchor);
                    this.startedGroup = true;
                }
            }
        }
    }

    public final void removeNode(int i, int i2) {
        if (i2 > 0) {
            if (!(i >= 0)) {
                ComposerKt.composeImmediateRuntimeError("Invalid remove index " + i);
            }
            if (this.removeFrom == i) {
                this.moveCount += i2;
                return;
            }
            realizeNodeMovementOperations();
            this.removeFrom = i;
            this.moveCount = i2;
        }
    }
}
