package androidx.compose.ui.viewinterop;

import android.view.View;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.saveable.SaveableStateRegistryImpl$registerProvider$3;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ViewFactoryHolder$releaseBlock$1 extends Lambda implements Function0 {
    final /* synthetic */ ViewFactoryHolder<View> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewFactoryHolder$releaseBlock$1(ViewFactoryHolder<View> viewFactoryHolder) {
        super(0);
        this.this$0 = viewFactoryHolder;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ViewFactoryHolder<View> viewFactoryHolder = this.this$0;
        viewFactoryHolder.releaseBlock.mo779invoke(viewFactoryHolder.typedView);
        ViewFactoryHolder<View> viewFactoryHolder2 = this.this$0;
        SaveableStateRegistry.Entry entry = viewFactoryHolder2.savableRegistryEntry;
        if (entry != null) {
            ((SaveableStateRegistryImpl$registerProvider$3) entry).unregister();
        }
        viewFactoryHolder2.savableRegistryEntry = null;
        return Unit.INSTANCE;
    }
}
