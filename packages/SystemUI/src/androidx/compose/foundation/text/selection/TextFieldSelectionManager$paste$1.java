package androidx.compose.foundation.text.selection;

import android.content.ClipData;
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
final class TextFieldSelectionManager$paste$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ TextFieldSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextFieldSelectionManager$paste$1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = textFieldSelectionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TextFieldSelectionManager$paste$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TextFieldSelectionManager$paste$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:
    
        if (r7 == r0) goto L22;
     */
    /* JADX WARN: Type inference failed for: r0v7, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Clipboard clipboard = this.this$0.clipboard;
            if (clipboard != null) {
                this.label = 1;
                ClipData primaryClip = ((AndroidClipboard) clipboard).androidClipboardManager.clipboardManager.getPrimaryClip();
                obj = primaryClip != null ? new ClipEntry(primaryClip) : null;
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AnnotatedString annotatedString = (AnnotatedString) obj;
            if (annotatedString != null) {
                AnnotatedString.Builder builder = new AnnotatedString.Builder(TextFieldValueKt.getTextBeforeSelection(this.this$0.getValue$foundation_release(), this.this$0.getValue$foundation_release().annotatedString.text.length()));
                builder.append(annotatedString);
                AnnotatedString annotatedString2 = builder.toAnnotatedString();
                AnnotatedString textAfterSelection = TextFieldValueKt.getTextAfterSelection(this.this$0.getValue$foundation_release(), this.this$0.getValue$foundation_release().annotatedString.text.length());
                AnnotatedString.Builder builder2 = new AnnotatedString.Builder(annotatedString2);
                builder2.append(textAfterSelection);
                AnnotatedString annotatedString3 = builder2.toAnnotatedString();
                int length = annotatedString.text.length() + TextRange.m752getMinimpl(this.this$0.getValue$foundation_release().selection);
                TextFieldSelectionManager textFieldSelectionManager = this.this$0;
                long jTextRange = TextRangeKt.TextRange(length, length);
                textFieldSelectionManager.getClass();
                this.this$0.onValueChange.mo781invoke(TextFieldSelectionManager.m238createTextFieldValueFDrldGo(annotatedString3, jTextRange));
                this.this$0.setHandleState(HandleState.None);
                UndoManager undoManager = this.this$0.undoManager;
                if (undoManager != null) {
                    undoManager.forceNextSnapshot = true;
                }
                return Unit.INSTANCE;
            }
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        ClipEntry clipEntry = (ClipEntry) obj;
        if (clipEntry != null) {
            this.label = 2;
            obj = ClipboardUtils_androidKt.readAnnotatedString(clipEntry);
        }
        return Unit.INSTANCE;
    }
}
