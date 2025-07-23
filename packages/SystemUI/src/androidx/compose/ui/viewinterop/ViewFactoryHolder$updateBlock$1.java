package androidx.compose.ui.viewinterop;

import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ViewFactoryHolder$updateBlock$1 extends Lambda implements Function0 {
    final /* synthetic */ ViewFactoryHolder<View> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewFactoryHolder$updateBlock$1(ViewFactoryHolder<View> viewFactoryHolder) {
        super(0);
        this.this$0 = viewFactoryHolder;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ViewFactoryHolder<View> viewFactoryHolder = this.this$0;
        viewFactoryHolder.updateBlock.mo779invoke(viewFactoryHolder.typedView);
        return Unit.INSTANCE;
    }
}
