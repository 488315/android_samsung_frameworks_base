package androidx.compose.foundation.text;

import androidx.compose.ui.text.input.TextFieldValue;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UndoManager {
    public boolean forceNextSnapshot;
    public Long lastSnapshot;
    public final int maxStoredCharacters;
    public Entry redoStack;
    public int storedCharacters;
    public Entry undoStack;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Entry {
        public Entry next;
        public TextFieldValue value;

        public Entry(Entry entry, TextFieldValue textFieldValue) {
            this.next = entry;
            this.value = textFieldValue;
        }

        public /* synthetic */ Entry(Entry entry, TextFieldValue textFieldValue, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : entry, textFieldValue);
        }
    }

    public UndoManager() {
        this(0, 1, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0066 A[LOOP:0: B:25:0x005a->B:30:0x0066, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0069 A[EDGE_INSN: B:31:0x0069->B:32:0x0069 BREAK  A[LOOP:0: B:25:0x005a->B:30:0x0066], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void makeSnapshot(androidx.compose.ui.text.input.TextFieldValue r4) {
        /*
            r3 = this;
            r0 = 0
            r3.forceNextSnapshot = r0
            androidx.compose.foundation.text.UndoManager$Entry r0 = r3.undoStack
            r1 = 0
            if (r0 == 0) goto Lb
            androidx.compose.ui.text.input.TextFieldValue r0 = r0.value
            goto Lc
        Lb:
            r0 = r1
        Lc:
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r0)
            if (r0 == 0) goto L13
            goto L6e
        L13:
            androidx.compose.ui.text.AnnotatedString r0 = r4.annotatedString
            java.lang.String r0 = r0.text
            androidx.compose.foundation.text.UndoManager$Entry r2 = r3.undoStack
            if (r2 == 0) goto L24
            androidx.compose.ui.text.input.TextFieldValue r2 = r2.value
            if (r2 == 0) goto L24
            androidx.compose.ui.text.AnnotatedString r2 = r2.annotatedString
            java.lang.String r2 = r2.text
            goto L25
        L24:
            r2 = r1
        L25:
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r0 == 0) goto L33
            androidx.compose.foundation.text.UndoManager$Entry r3 = r3.undoStack
            if (r3 != 0) goto L30
            goto L6e
        L30:
            r3.value = r4
            return
        L33:
            androidx.compose.foundation.text.UndoManager$Entry r0 = r3.undoStack
            androidx.compose.foundation.text.UndoManager$Entry r2 = new androidx.compose.foundation.text.UndoManager$Entry
            r2.<init>(r0, r4)
            r3.undoStack = r2
            r3.redoStack = r1
            int r0 = r3.storedCharacters
            androidx.compose.ui.text.AnnotatedString r4 = r4.annotatedString
            java.lang.String r4 = r4.text
            int r4 = r4.length()
            int r4 = r4 + r0
            r3.storedCharacters = r4
            int r0 = r3.maxStoredCharacters
            if (r4 <= r0) goto L6e
            androidx.compose.foundation.text.UndoManager$Entry r3 = r3.undoStack
            if (r3 == 0) goto L56
            androidx.compose.foundation.text.UndoManager$Entry r4 = r3.next
            goto L57
        L56:
            r4 = r1
        L57:
            if (r4 != 0) goto L5a
            goto L6e
        L5a:
            if (r3 == 0) goto L63
            androidx.compose.foundation.text.UndoManager$Entry r4 = r3.next
            if (r4 == 0) goto L63
            androidx.compose.foundation.text.UndoManager$Entry r4 = r4.next
            goto L64
        L63:
            r4 = r1
        L64:
            if (r4 == 0) goto L69
            androidx.compose.foundation.text.UndoManager$Entry r3 = r3.next
            goto L5a
        L69:
            if (r3 != 0) goto L6c
            goto L6e
        L6c:
            r3.next = r1
        L6e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.UndoManager.makeSnapshot(androidx.compose.ui.text.input.TextFieldValue):void");
    }

    public UndoManager(int i) {
        this.maxStoredCharacters = i;
    }

    public /* synthetic */ UndoManager(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 100000 : i);
    }
}
