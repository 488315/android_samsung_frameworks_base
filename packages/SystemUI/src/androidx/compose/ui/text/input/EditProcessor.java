package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.AnnotatedStringKt;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EditProcessor {
    public EditingBuffer mBuffer;
    public TextFieldValue mBufferState;

    public EditProcessor() {
        AnnotatedString annotatedString = AnnotatedStringKt.EmptyAnnotatedString;
        TextRange.Companion.getClass();
        this.mBufferState = new TextFieldValue(annotatedString, TextRange.Zero, (TextRange) null, (DefaultConstructorMarker) null);
        TextFieldValue textFieldValue = this.mBufferState;
        this.mBuffer = new EditingBuffer(textFieldValue.annotatedString, textFieldValue.selection, (DefaultConstructorMarker) null);
    }

    public final TextFieldValue apply(List list) {
        final EditCommand editCommand = null;
        try {
            int size = list.size();
            int i = 0;
            EditCommand editCommand2 = null;
            while (i < size) {
                try {
                    EditCommand editCommand3 = (EditCommand) list.get(i);
                    try {
                        editCommand3.applyTo(this.mBuffer);
                        i++;
                        editCommand2 = editCommand3;
                    } catch (Exception e) {
                        e = e;
                        editCommand = editCommand3;
                        StringBuilder sb = new StringBuilder();
                        StringBuilder sb2 = new StringBuilder("Error while applying EditCommand batch to buffer (length=");
                        sb2.append(this.mBuffer.gapBuffer.getLength());
                        sb2.append(", composition=");
                        sb2.append(this.mBuffer.m770getCompositionMzsxiRA$ui_text_release());
                        sb2.append(", selection=");
                        EditingBuffer editingBuffer = this.mBuffer;
                        sb2.append((Object) TextRange.m752toStringimpl(TextRangeKt.TextRange(editingBuffer.selectionStart, editingBuffer.selectionEnd)));
                        sb2.append("):");
                        sb.append(sb2.toString());
                        sb.append('\n');
                        CollectionsKt___CollectionsKt.joinTo$default(list, sb, "\n", new Function1() { // from class: androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj) {
                                String concat;
                                EditCommand editCommand4 = (EditCommand) obj;
                                StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(EditCommand.this == editCommand4 ? " > " : "   ");
                                this.getClass();
                                if (editCommand4 instanceof CommitTextCommand) {
                                    StringBuilder sb3 = new StringBuilder("CommitTextCommand(text.length=");
                                    CommitTextCommand commitTextCommand = (CommitTextCommand) editCommand4;
                                    sb3.append(commitTextCommand.annotatedString.text.length());
                                    sb3.append(", newCursorPosition=");
                                    concat = BackEventCompat$$ExternalSyntheticOutline0.m(sb3, commitTextCommand.newCursorPosition, ')');
                                } else if (editCommand4 instanceof SetComposingTextCommand) {
                                    StringBuilder sb4 = new StringBuilder("SetComposingTextCommand(text.length=");
                                    SetComposingTextCommand setComposingTextCommand = (SetComposingTextCommand) editCommand4;
                                    sb4.append(setComposingTextCommand.annotatedString.text.length());
                                    sb4.append(", newCursorPosition=");
                                    concat = BackEventCompat$$ExternalSyntheticOutline0.m(sb4, setComposingTextCommand.newCursorPosition, ')');
                                } else if (editCommand4 instanceof SetComposingRegionCommand) {
                                    concat = editCommand4.toString();
                                } else if (editCommand4 instanceof DeleteSurroundingTextCommand) {
                                    concat = editCommand4.toString();
                                } else if (editCommand4 instanceof DeleteSurroundingTextInCodePointsCommand) {
                                    concat = editCommand4.toString();
                                } else if (editCommand4 instanceof SetSelectionCommand) {
                                    concat = editCommand4.toString();
                                } else if (editCommand4 instanceof FinishComposingTextCommand) {
                                    ((FinishComposingTextCommand) editCommand4).getClass();
                                    concat = "FinishComposingTextCommand()";
                                } else if (editCommand4 instanceof BackspaceCommand) {
                                    ((BackspaceCommand) editCommand4).getClass();
                                    concat = "BackspaceCommand()";
                                } else if (editCommand4 instanceof MoveCursorCommand) {
                                    concat = editCommand4.toString();
                                } else if (editCommand4 instanceof DeleteAllCommand) {
                                    ((DeleteAllCommand) editCommand4).getClass();
                                    concat = "DeleteAllCommand()";
                                } else {
                                    String simpleName = Reflection.getOrCreateKotlinClass(editCommand4.getClass()).getSimpleName();
                                    if (simpleName == null) {
                                        simpleName = "{anonymous EditCommand}";
                                    }
                                    concat = "Unknown EditCommand: ".concat(simpleName);
                                }
                                m.append(concat);
                                return m.toString();
                            }
                        }, 60);
                        throw new RuntimeException(sb.toString(), e);
                    }
                } catch (Exception e2) {
                    e = e2;
                    editCommand = editCommand2;
                }
            }
            EditingBuffer editingBuffer2 = this.mBuffer;
            editingBuffer2.getClass();
            AnnotatedString annotatedString = new AnnotatedString(editingBuffer2.gapBuffer.toString(), null, 2, null);
            EditingBuffer editingBuffer3 = this.mBuffer;
            long TextRange = TextRangeKt.TextRange(editingBuffer3.selectionStart, editingBuffer3.selectionEnd);
            TextRange m745boximpl = TextRange.m751getReversedimpl(this.mBufferState.selection) ? null : TextRange.m745boximpl(TextRange);
            TextFieldValue textFieldValue = new TextFieldValue(annotatedString, m745boximpl != null ? m745boximpl.packedValue : TextRangeKt.TextRange(TextRange.m749getMaximpl(TextRange), TextRange.m750getMinimpl(TextRange)), this.mBuffer.m770getCompositionMzsxiRA$ui_text_release(), (DefaultConstructorMarker) null);
            this.mBufferState = textFieldValue;
            return textFieldValue;
        } catch (Exception e3) {
            e = e3;
        }
    }
}
