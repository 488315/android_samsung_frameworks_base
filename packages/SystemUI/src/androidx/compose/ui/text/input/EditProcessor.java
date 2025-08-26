package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.AnnotatedStringKt;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import java.io.IOException;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

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

    public final TextFieldValue apply(List list) throws IOException {
        EditCommand editCommand;
        final EditCommand editCommand2 = null;
        try {
            int size = list.size();
            int i = 0;
            EditCommand editCommand3 = null;
            while (i < size) {
                try {
                    editCommand = (EditCommand) list.get(i);
                } catch (Exception e) {
                    e = e;
                    editCommand2 = editCommand3;
                }
                try {
                    editCommand.applyTo(this.mBuffer);
                    i++;
                    editCommand3 = editCommand;
                } catch (Exception e2) {
                    e = e2;
                    editCommand2 = editCommand;
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder("Error while applying EditCommand batch to buffer (length=");
                    sb2.append(this.mBuffer.gapBuffer.getLength());
                    sb2.append(", composition=");
                    sb2.append(this.mBuffer.m772getCompositionMzsxiRA$ui_text_release());
                    sb2.append(", selection=");
                    EditingBuffer editingBuffer = this.mBuffer;
                    sb2.append((Object) TextRange.m754toStringimpl(TextRangeKt.TextRange(editingBuffer.selectionStart, editingBuffer.selectionEnd)));
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
                        public final Object mo781invoke(Object obj) {
                            String strConcat;
                            EditCommand editCommand4 = (EditCommand) obj;
                            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(editCommand2 == editCommand4 ? " > " : "   ");
                            this.getClass();
                            if (editCommand4 instanceof CommitTextCommand) {
                                StringBuilder sb3 = new StringBuilder("CommitTextCommand(text.length=");
                                CommitTextCommand commitTextCommand = (CommitTextCommand) editCommand4;
                                sb3.append(commitTextCommand.annotatedString.text.length());
                                sb3.append(", newCursorPosition=");
                                strConcat = BackEventCompat$$ExternalSyntheticOutline0.m(sb3, commitTextCommand.newCursorPosition, ')');
                            } else if (editCommand4 instanceof SetComposingTextCommand) {
                                StringBuilder sb4 = new StringBuilder("SetComposingTextCommand(text.length=");
                                SetComposingTextCommand setComposingTextCommand = (SetComposingTextCommand) editCommand4;
                                sb4.append(setComposingTextCommand.annotatedString.text.length());
                                sb4.append(", newCursorPosition=");
                                strConcat = BackEventCompat$$ExternalSyntheticOutline0.m(sb4, setComposingTextCommand.newCursorPosition, ')');
                            } else if ((editCommand4 instanceof SetComposingRegionCommand) || (editCommand4 instanceof DeleteSurroundingTextCommand) || (editCommand4 instanceof DeleteSurroundingTextInCodePointsCommand) || (editCommand4 instanceof SetSelectionCommand)) {
                                strConcat = editCommand4.toString();
                            } else if (editCommand4 instanceof FinishComposingTextCommand) {
                                ((FinishComposingTextCommand) editCommand4).getClass();
                                strConcat = "FinishComposingTextCommand()";
                            } else if (editCommand4 instanceof BackspaceCommand) {
                                ((BackspaceCommand) editCommand4).getClass();
                                strConcat = "BackspaceCommand()";
                            } else if (editCommand4 instanceof MoveCursorCommand) {
                                strConcat = editCommand4.toString();
                            } else if (editCommand4 instanceof DeleteAllCommand) {
                                ((DeleteAllCommand) editCommand4).getClass();
                                strConcat = "DeleteAllCommand()";
                            } else {
                                String simpleName = Reflection.getOrCreateKotlinClass(editCommand4.getClass()).getSimpleName();
                                if (simpleName == null) {
                                    simpleName = "{anonymous EditCommand}";
                                }
                                strConcat = "Unknown EditCommand: ".concat(simpleName);
                            }
                            sbM.append(strConcat);
                            return sbM.toString();
                        }
                    }, 60);
                    throw new RuntimeException(sb.toString(), e);
                }
            }
            EditingBuffer editingBuffer2 = this.mBuffer;
            editingBuffer2.getClass();
            AnnotatedString annotatedString = new AnnotatedString(editingBuffer2.gapBuffer.toString(), null, 2, null);
            EditingBuffer editingBuffer3 = this.mBuffer;
            long jTextRange = TextRangeKt.TextRange(editingBuffer3.selectionStart, editingBuffer3.selectionEnd);
            TextRange textRangeM747boximpl = TextRange.m753getReversedimpl(this.mBufferState.selection) ? null : TextRange.m747boximpl(jTextRange);
            TextFieldValue textFieldValue = new TextFieldValue(annotatedString, textRangeM747boximpl != null ? textRangeM747boximpl.packedValue : TextRangeKt.TextRange(TextRange.m751getMaximpl(jTextRange), TextRange.m752getMinimpl(jTextRange)), this.mBuffer.m772getCompositionMzsxiRA$ui_text_release(), (DefaultConstructorMarker) null);
            this.mBufferState = textFieldValue;
            return textFieldValue;
        } catch (Exception e3) {
            e = e3;
        }
    }
}
