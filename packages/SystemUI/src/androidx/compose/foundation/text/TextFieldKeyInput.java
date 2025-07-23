package androidx.compose.foundation.text;

import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.foundation.text.selection.TextPreparedSelectionState;
import androidx.compose.ui.text.input.EditProcessor;
import androidx.compose.ui.text.input.FinishComposingTextCommand;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextFieldKeyInput {
    public final boolean editable;
    public final int imeAction;
    public final DeadKeyCombiner keyCombiner;
    public final KeyMapping keyMapping;
    public final OffsetMapping offsetMapping;
    public final Function1 onValueChange;
    public final TextPreparedSelectionState preparedSelectionState;
    public final TextFieldSelectionManager selectionManager;
    public final boolean singleLine;
    public final LegacyTextFieldState state;
    public final UndoManager undoManager;
    public final TextFieldValue value;

    public /* synthetic */ TextFieldKeyInput(LegacyTextFieldState legacyTextFieldState, TextFieldSelectionManager textFieldSelectionManager, TextFieldValue textFieldValue, boolean z, boolean z2, TextPreparedSelectionState textPreparedSelectionState, OffsetMapping offsetMapping, UndoManager undoManager, DeadKeyCombiner deadKeyCombiner, KeyMapping keyMapping, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(legacyTextFieldState, textFieldSelectionManager, textFieldValue, z, z2, textPreparedSelectionState, offsetMapping, undoManager, deadKeyCombiner, keyMapping, function1, i);
    }

    public final void apply(List list) {
        EditProcessor editProcessor = this.state.processor;
        ArrayList arrayList = new ArrayList(list);
        arrayList.add(0, new FinishComposingTextCommand());
        this.onValueChange.mo779invoke(editProcessor.apply(arrayList));
    }

    private TextFieldKeyInput(LegacyTextFieldState legacyTextFieldState, TextFieldSelectionManager textFieldSelectionManager, TextFieldValue textFieldValue, boolean z, boolean z2, TextPreparedSelectionState textPreparedSelectionState, OffsetMapping offsetMapping, UndoManager undoManager, DeadKeyCombiner deadKeyCombiner, KeyMapping keyMapping, Function1 function1, int i) {
        this.state = legacyTextFieldState;
        this.selectionManager = textFieldSelectionManager;
        this.value = textFieldValue;
        this.editable = z;
        this.singleLine = z2;
        this.preparedSelectionState = textPreparedSelectionState;
        this.offsetMapping = offsetMapping;
        this.undoManager = undoManager;
        this.keyCombiner = deadKeyCombiner;
        this.keyMapping = keyMapping;
        this.onValueChange = function1;
        this.imeAction = i;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextFieldKeyInput(androidx.compose.foundation.text.LegacyTextFieldState r18, androidx.compose.foundation.text.selection.TextFieldSelectionManager r19, androidx.compose.ui.text.input.TextFieldValue r20, boolean r21, boolean r22, androidx.compose.foundation.text.selection.TextPreparedSelectionState r23, androidx.compose.ui.text.input.OffsetMapping r24, androidx.compose.foundation.text.UndoManager r25, androidx.compose.foundation.text.DeadKeyCombiner r26, androidx.compose.foundation.text.KeyMapping r27, kotlin.jvm.functions.Function1 r28, int r29, int r30, kotlin.jvm.internal.DefaultConstructorMarker r31) {
        /*
            r17 = this;
            r0 = r30
            r1 = r0 & 4
            if (r1 == 0) goto L13
            androidx.compose.ui.text.input.TextFieldValue r2 = new androidx.compose.ui.text.input.TextFieldValue
            r4 = 0
            r6 = 0
            r3 = 0
            r7 = 7
            r8 = 0
            r2.<init>(r3, r4, r6, r7, r8)
            r6 = r2
            goto L15
        L13:
            r6 = r20
        L15:
            r1 = r0 & 8
            if (r1 == 0) goto L1c
            r1 = 1
            r7 = r1
            goto L1e
        L1c:
            r7 = r21
        L1e:
            r1 = r0 & 16
            if (r1 == 0) goto L25
            r1 = 0
            r8 = r1
            goto L27
        L25:
            r8 = r22
        L27:
            r1 = r0 & 64
            if (r1 == 0) goto L34
            androidx.compose.ui.text.input.OffsetMapping$Companion r1 = androidx.compose.ui.text.input.OffsetMapping.Companion
            r1.getClass()
            androidx.compose.ui.text.input.OffsetMapping$Companion$Identity$1 r1 = androidx.compose.ui.text.input.OffsetMapping.Companion.Identity
            r10 = r1
            goto L36
        L34:
            r10 = r24
        L36:
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L3d
            r1 = 0
            r11 = r1
            goto L3f
        L3d:
            r11 = r25
        L3f:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L47
            androidx.compose.foundation.text.KeyMapping_androidKt$platformDefaultKeyMapping$1 r1 = androidx.compose.foundation.text.KeyMapping_androidKt.platformDefaultKeyMapping
            r13 = r1
            goto L49
        L47:
            r13 = r27
        L49:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L51
            androidx.compose.foundation.text.TextFieldKeyInput$1 r0 = new kotlin.jvm.functions.Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput.1
                static {
                    /*
                        androidx.compose.foundation.text.TextFieldKeyInput$1 r0 = new androidx.compose.foundation.text.TextFieldKeyInput$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.foundation.text.TextFieldKeyInput$1) androidx.compose.foundation.text.TextFieldKeyInput.1.INSTANCE androidx.compose.foundation.text.TextFieldKeyInput$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextFieldKeyInput.AnonymousClass1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextFieldKeyInput.AnonymousClass1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ java.lang.Object mo779invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        androidx.compose.ui.text.input.TextFieldValue r1 = (androidx.compose.ui.text.input.TextFieldValue) r1
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextFieldKeyInput.AnonymousClass1.mo779invoke(java.lang.Object):java.lang.Object");
                }
            }
            r14 = r0
            goto L53
        L51:
            r14 = r28
        L53:
            r16 = 0
            r3 = r17
            r4 = r18
            r5 = r19
            r9 = r23
            r12 = r26
            r15 = r29
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextFieldKeyInput.<init>(androidx.compose.foundation.text.LegacyTextFieldState, androidx.compose.foundation.text.selection.TextFieldSelectionManager, androidx.compose.ui.text.input.TextFieldValue, boolean, boolean, androidx.compose.foundation.text.selection.TextPreparedSelectionState, androidx.compose.ui.text.input.OffsetMapping, androidx.compose.foundation.text.UndoManager, androidx.compose.foundation.text.DeadKeyCombiner, androidx.compose.foundation.text.KeyMapping, kotlin.jvm.functions.Function1, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
