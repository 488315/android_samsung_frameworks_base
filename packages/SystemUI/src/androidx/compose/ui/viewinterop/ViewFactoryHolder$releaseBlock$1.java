package androidx.compose.ui.viewinterop;

import android.view.View;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.saveable.SaveableStateRegistryImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

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
        viewFactoryHolder.releaseBlock.mo781invoke(viewFactoryHolder.typedView);
        ViewFactoryHolder<View> viewFactoryHolder2 = this.this$0;
        SaveableStateRegistry.Entry entry = viewFactoryHolder2.savableRegistryEntry;
        if (entry != null) {
            ((SaveableStateRegistryImpl.AnonymousClass3) entry).unregister();
        }
        viewFactoryHolder2.savableRegistryEntry = null;
        return Unit.INSTANCE;
    }
}
