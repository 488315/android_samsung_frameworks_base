package androidx.compose.foundation.text;

import androidx.compose.foundation.text.TextFieldDelegate;
import androidx.compose.ui.text.input.EditProcessor;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextInputSession;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextFieldDelegate$Companion$restartInput$1 extends Lambda implements Function1 {
    final /* synthetic */ EditProcessor $editProcessor;
    final /* synthetic */ Function1 $onValueChange;
    final /* synthetic */ Ref$ObjectRef<TextInputSession> $session;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextFieldDelegate$Companion$restartInput$1(EditProcessor editProcessor, Function1 function1, Ref$ObjectRef<TextInputSession> ref$ObjectRef) {
        super(1);
        this.$editProcessor = editProcessor;
        this.$onValueChange = function1;
        this.$session = ref$ObjectRef;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        TextFieldDelegate.Companion companion = TextFieldDelegate.Companion;
        EditProcessor editProcessor = this.$editProcessor;
        Function1 function1 = this.$onValueChange;
        TextInputSession textInputSession = this.$session.element;
        companion.getClass();
        TextFieldValue apply = editProcessor.apply((List) obj);
        if (textInputSession != null && Intrinsics.areEqual((TextInputSession) textInputSession.textInputService._currentInputSession.get(), textInputSession)) {
            textInputSession.platformTextInputService.updateState(null, apply);
        }
        function1.mo779invoke(apply);
        return Unit.INSTANCE;
    }
}
