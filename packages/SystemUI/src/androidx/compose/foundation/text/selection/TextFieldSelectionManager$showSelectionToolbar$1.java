package androidx.compose.foundation.text.selection;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextFieldSelectionManager$showSelectionToolbar$1 extends SuspendLambda implements Function2 {
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ TextFieldSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextFieldSelectionManager$showSelectionToolbar$1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = textFieldSelectionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TextFieldSelectionManager$showSelectionToolbar$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TextFieldSelectionManager$showSelectionToolbar$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00f9  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 660
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
