package androidx.compose.ui.platform;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class AndroidComposeView$focusOwner$1 extends FunctionReferenceImpl implements Function1 {
    public AndroidComposeView$focusOwner$1(Object obj) {
        super(1, obj, AndroidComposeView.class, "registerOnEndApplyChangesListener", "registerOnEndApplyChangesListener(Lkotlin/jvm/functions/Function0;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((AndroidComposeView) this.receiver).registerOnEndApplyChangesListener((Function0) obj);
        return Unit.INSTANCE;
    }
}
