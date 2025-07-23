package androidx.compose.ui.viewinterop;

import android.content.Context;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.saveable.SaveableStateRegistryImpl$registerProvider$3;
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.node.Owner;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewFactoryHolder<T extends View> extends AndroidViewHolder {
    public Function1 releaseBlock;
    public Function1 resetBlock;
    public SaveableStateRegistry.Entry savableRegistryEntry;
    public final View typedView;
    public Function1 updateBlock;

    public /* synthetic */ ViewFactoryHolder(Context context, CompositionContext compositionContext, View view, NestedScrollDispatcher nestedScrollDispatcher, SaveableStateRegistry saveableStateRegistry, int i, Owner owner, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : compositionContext, view, (i2 & 8) != 0 ? new NestedScrollDispatcher() : nestedScrollDispatcher, saveableStateRegistry, i, owner);
    }

    private ViewFactoryHolder(Context context, CompositionContext compositionContext, T t, NestedScrollDispatcher nestedScrollDispatcher, SaveableStateRegistry saveableStateRegistry, int i, Owner owner) {
        super(context, compositionContext, i, nestedScrollDispatcher, t, owner);
        this.typedView = t;
        setClipChildren(false);
        String valueOf = String.valueOf(i);
        Object consumeRestored = saveableStateRegistry != null ? saveableStateRegistry.consumeRestored(valueOf) : null;
        SparseArray<Parcelable> sparseArray = consumeRestored instanceof SparseArray ? (SparseArray) consumeRestored : null;
        if (sparseArray != null) {
            t.restoreHierarchyState(sparseArray);
        }
        if (saveableStateRegistry != null) {
            SaveableStateRegistry.Entry registerProvider = saveableStateRegistry.registerProvider(valueOf, new Function0(this) { // from class: androidx.compose.ui.viewinterop.ViewFactoryHolder$registerSaveStateProvider$1
                final /* synthetic */ ViewFactoryHolder<View> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SparseArray<Parcelable> sparseArray2 = new SparseArray<>();
                    this.this$0.typedView.saveHierarchyState(sparseArray2);
                    return sparseArray2;
                }
            });
            SaveableStateRegistry.Entry entry = this.savableRegistryEntry;
            if (entry != null) {
                ((SaveableStateRegistryImpl$registerProvider$3) entry).unregister();
            }
            this.savableRegistryEntry = registerProvider;
        }
        Function1 function1 = AndroidView_androidKt.NoOpUpdate;
        this.updateBlock = function1;
        this.resetBlock = function1;
        this.releaseBlock = function1;
    }

    public /* synthetic */ ViewFactoryHolder(Context context, Function1 function1, CompositionContext compositionContext, SaveableStateRegistry saveableStateRegistry, int i, Owner owner, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, function1, (i2 & 4) != 0 ? null : compositionContext, saveableStateRegistry, i, owner);
    }

    public ViewFactoryHolder(Context context, Function1 function1, CompositionContext compositionContext, SaveableStateRegistry saveableStateRegistry, int i, Owner owner) {
        this(context, compositionContext, (View) function1.mo779invoke(context), null, saveableStateRegistry, i, owner, 8, null);
    }
}
