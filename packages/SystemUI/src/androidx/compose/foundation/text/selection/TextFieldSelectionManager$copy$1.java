package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.internal.ClipboardUtils_androidKt;
import androidx.compose.foundation.text.HandleState;
import androidx.compose.ui.platform.AndroidClipboard;
import androidx.compose.ui.platform.ClipEntry;
import androidx.compose.ui.platform.Clipboard;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextFieldSelectionManager$copy$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $cancelSelection;
    int label;
    final /* synthetic */ TextFieldSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextFieldSelectionManager$copy$1(TextFieldSelectionManager textFieldSelectionManager, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = textFieldSelectionManager;
        this.$cancelSelection = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TextFieldSelectionManager$copy$1(this.this$0, this.$cancelSelection, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TextFieldSelectionManager$copy$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (TextRange.m747getCollapsedimpl(this.this$0.getValue$foundation_release().selection)) {
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
        if (!this.$cancelSelection) {
            return Unit.INSTANCE;
        }
        int m749getMaximpl = TextRange.m749getMaximpl(this.this$0.getValue$foundation_release().selection);
        this.this$0.onValueChange.mo779invoke(TextFieldSelectionManager.m237createTextFieldValueFDrldGo(this.this$0.getValue$foundation_release().annotatedString, TextRangeKt.TextRange(m749getMaximpl, m749getMaximpl)));
        this.this$0.setHandleState(HandleState.None);
        return Unit.INSTANCE;
    }
}
