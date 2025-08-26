package androidx.compose.foundation.text;

import androidx.compose.ui.text.input.TextFieldValue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class UndoManager {
    public boolean forceNextSnapshot;
    public Long lastSnapshot;
    public final int maxStoredCharacters;
    public Entry redoStack;
    public int storedCharacters;
    public Entry undoStack;

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

    /* JADX WARN: Removed duplicated region for block: B:34:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void makeSnapshot(TextFieldValue textFieldValue) {
        Entry entry;
        TextFieldValue textFieldValue2;
        this.forceNextSnapshot = false;
        Entry entry2 = this.undoStack;
        if (Intrinsics.areEqual(textFieldValue, entry2 != null ? entry2.value : null)) {
            return;
        }
        String str = textFieldValue.annotatedString.text;
        Entry entry3 = this.undoStack;
        if (Intrinsics.areEqual(str, (entry3 == null || (textFieldValue2 = entry3.value) == null) ? null : textFieldValue2.annotatedString.text)) {
            Entry entry4 = this.undoStack;
            if (entry4 == null) {
                return;
            }
            entry4.value = textFieldValue;
            return;
        }
        this.undoStack = new Entry(this.undoStack, textFieldValue);
        this.redoStack = null;
        int length = textFieldValue.annotatedString.text.length() + this.storedCharacters;
        this.storedCharacters = length;
        if (length > this.maxStoredCharacters) {
            Entry entry5 = this.undoStack;
            if ((entry5 != null ? entry5.next : null) == null) {
                return;
            }
            while (true) {
                if (entry5 == null) {
                    entry = null;
                } else {
                    Entry entry6 = entry5.next;
                    if (entry6 != null) {
                        entry = entry6.next;
                    }
                }
                if (entry == null) {
                    break;
                } else {
                    entry5 = entry5.next;
                }
            }
            if (entry5 == null) {
                return;
            }
            entry5.next = null;
        }
    }

    public UndoManager(int i) {
        this.maxStoredCharacters = i;
    }

    public /* synthetic */ UndoManager(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 100000 : i);
    }
}
