package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.internal.RememberEventDispatcher;
import java.util.Arrays;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Operations extends OperationsDebugStringFormattable {
    public int intArgsSize;
    public int objectArgsSize;
    public int opCodesSize;
    public Operation[] opCodes = new Operation[16];
    public int[] intArgs = new int[16];
    public Object[] objectArgs = new Object[16];

    public final class OpIterator {
        public int intIdx;
        public int objIdx;
        public int opIdx;

        public OpIterator() {
        }

        public final int getInt(int i) {
            return Operations.this.intArgs[this.intIdx + i];
        }

        /* renamed from: getObject-31yXWZQ, reason: not valid java name */
        public final Object m338getObject31yXWZQ(int i) {
            return Operations.this.objectArgs[this.objIdx + i];
        }
    }

    public final class WriteScope {
        public final Operations stack;

        /* renamed from: setObject-DKhxnng, reason: not valid java name */
        public static final void m339setObjectDKhxnng(Operations operations, int i, Object obj) {
            operations.objectArgs[(operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].objects) + i] = obj;
        }

        /* renamed from: setObjects-4uCC6AY, reason: not valid java name */
        public static final void m340setObjects4uCC6AY(Operations operations, int i, Object obj, int i2, Object obj2) {
            int i3 = operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].objects;
            Object[] objArr = operations.objectArgs;
            objArr[i + i3] = obj;
            objArr[i3 + i2] = obj2;
        }

        /* renamed from: setObjects-t7hvbck, reason: not valid java name */
        public static final void m341setObjectst7hvbck(Operations operations, Object obj, Object obj2, Object obj3) {
            int i = operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].objects;
            Object[] objArr = operations.objectArgs;
            objArr[i] = obj;
            objArr[i + 1] = obj2;
            objArr[i + 2] = obj3;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof WriteScope) {
                return Intrinsics.areEqual(this.stack, ((WriteScope) obj).stack);
            }
            return false;
        }

        public final int hashCode() {
            return this.stack.hashCode();
        }

        public final String toString() {
            return "WriteScope(stack=" + this.stack + ')';
        }
    }

    public final void clear() {
        this.opCodesSize = 0;
        this.intArgsSize = 0;
        Arrays.fill(this.objectArgs, 0, this.objectArgsSize, (Object) null);
        this.objectArgsSize = 0;
    }

    public final void executeAndFlushAllPendingOperations(Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
        if (isNotEmpty()) {
            OpIterator opIterator = new OpIterator();
            while (true) {
                Operations operations = Operations.this;
                Operation operation = operations.opCodes[opIterator.opIdx];
                final Anchor groupAnchor = operation.getGroupAnchor(opIterator);
                Applier applier2 = applier;
                final SlotWriter slotWriter2 = slotWriter;
                RememberEventDispatcher rememberEventDispatcher2 = rememberEventDispatcher;
                final OperationErrorContext operationErrorContext2 = operationErrorContext;
                try {
                    operation.execute(opIterator, applier2, slotWriter2, rememberEventDispatcher2, operationErrorContext2);
                    int i = opIterator.opIdx;
                    int i2 = operations.opCodesSize;
                    if (i < i2) {
                        Operation operation2 = operations.opCodes[i];
                        opIterator.intIdx += operation2.ints;
                        opIterator.objIdx += operation2.objects;
                        int i3 = i + 1;
                        opIterator.opIdx = i3;
                        if (i3 >= i2) {
                            break;
                        }
                        applier = applier2;
                        slotWriter = slotWriter2;
                        rememberEventDispatcher = rememberEventDispatcher2;
                        operationErrorContext = operationErrorContext2;
                    } else {
                        break;
                    }
                } finally {
                }
            }
        }
        clear();
    }

    public final boolean isEmpty() {
        return this.opCodesSize == 0;
    }

    public final boolean isNotEmpty() {
        return this.opCodesSize != 0;
    }

    public final void pushOp(Operation operation) {
        int i = this.opCodesSize;
        Operation[] operationArr = this.opCodes;
        if (i == operationArr.length) {
            Operation[] operationArr2 = new Operation[(i > 1024 ? 1024 : i) + i];
            System.arraycopy(operationArr, 0, operationArr2, 0, i);
            this.opCodes = operationArr2;
        }
        int i2 = this.intArgsSize + operation.ints;
        int[] iArr = this.intArgs;
        int length = iArr.length;
        if (i2 > length) {
            int i3 = (length > 1024 ? 1024 : length) + length;
            if (i3 >= i2) {
                i2 = i3;
            }
            int[] iArr2 = new int[i2];
            ArraysKt___ArraysJvmKt.copyInto(0, 0, length, iArr, iArr2);
            this.intArgs = iArr2;
        }
        int i4 = this.objectArgsSize;
        int i5 = operation.objects;
        int i6 = i4 + i5;
        Object[] objArr = this.objectArgs;
        int length2 = objArr.length;
        if (i6 > length2) {
            int i7 = (length2 <= 1024 ? length2 : 1024) + length2;
            if (i7 >= i6) {
                i6 = i7;
            }
            Object[] objArr2 = new Object[i6];
            System.arraycopy(objArr, 0, objArr2, 0, length2);
            this.objectArgs = objArr2;
        }
        Operation[] operationArr3 = this.opCodes;
        int i8 = this.opCodesSize;
        this.opCodesSize = i8 + 1;
        operationArr3[i8] = operation;
        this.intArgsSize += operation.ints;
        this.objectArgsSize += i5;
    }
}
