package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.internal.ClipboardUtils_androidKt;
import androidx.compose.foundation.text.HandleState;
import androidx.compose.foundation.text.UndoManager;
import androidx.compose.ui.platform.AndroidClipboard;
import androidx.compose.ui.platform.ClipEntry;
import androidx.compose.ui.platform.Clipboard;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.input.TextFieldValueKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class TextFieldSelectionManager$cut$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ TextFieldSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextFieldSelectionManager$cut$1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = textFieldSelectionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TextFieldSelectionManager$cut$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TextFieldSelectionManager$cut$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (TextRange.m749getCollapsedimpl(this.this$0.getValue$foundation_release().selection)) {
                return Unit.INSTANCE;
            }
            TextFieldSelectionManager textFieldSelectionManager = this.this$0;
            Clipboard clipboard = textFieldSelectionManager.clipboard;
            if (clipboard != null) {
                ClipEntry clipEntry = ClipboardUtils_androidKt.toClipEntry(TextFieldValueKt.getSelectedText(textFieldSelectionManager.getValue$foundation_release()));
                this.label = 1;
                if (((AndroidClipboard) clipboard).setClipEntry(clipEntry) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        AnnotatedString textBeforeSelection = TextFieldValueKt.getTextBeforeSelection(this.this$0.getValue$foundation_release(), this.this$0.getValue$foundation_release().annotatedString.text.length());
        AnnotatedString textAfterSelection = TextFieldValueKt.getTextAfterSelection(this.this$0.getValue$foundation_release(), this.this$0.getValue$foundation_release().annotatedString.text.length());
        AnnotatedString.Builder builder = new AnnotatedString.Builder(textBeforeSelection);
        builder.append(textAfterSelection);
        AnnotatedString annotatedString = builder.toAnnotatedString();
        int iM752getMinimpl = TextRange.m752getMinimpl(this.this$0.getValue$foundation_release().selection);
        TextFieldSelectionManager textFieldSelectionManager2 = this.this$0;
        long jTextRange = TextRangeKt.TextRange(iM752getMinimpl, iM752getMinimpl);
        textFieldSelectionManager2.getClass();
        this.this$0.onValueChange.mo781invoke(TextFieldSelectionManager.m238createTextFieldValueFDrldGo(annotatedString, jTextRange));
        this.this$0.setHandleState(HandleState.None);
        UndoManager undoManager = this.this$0.undoManager;
        if (undoManager != null) {
            undoManager.forceNextSnapshot = true;
        }
        return Unit.INSTANCE;
    }
}
