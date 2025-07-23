package androidx.compose.ui.platform;

import androidx.compose.runtime.Composition;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WrappedComposition implements Composition, LifecycleEventObserver {
    public Lifecycle addedToLifecycle;
    public boolean disposed;
    public Function2 lastContent;
    public final Composition original;
    public final AndroidComposeView owner;

    public WrappedComposition(AndroidComposeView androidComposeView, Composition composition) {
        this.owner = androidComposeView;
        this.original = composition;
        ComposableSingletons$Wrapper_androidKt.INSTANCE.getClass();
        this.lastContent = ComposableSingletons$Wrapper_androidKt.f20lambda1;
    }

    @Override // androidx.compose.runtime.Composition
    public final void dispose() {
        if (!this.disposed) {
            this.disposed = true;
            AndroidComposeView androidComposeView = this.owner;
            androidComposeView.getClass();
            androidComposeView.setTag(R.id.wrapped_composition_tag, null);
            Lifecycle lifecycle = this.addedToLifecycle;
            if (lifecycle != null) {
                lifecycle.removeObserver(this);
            }
        }
        this.original.dispose();
    }

    @Override // androidx.compose.runtime.Composition
    public final boolean isDisposed() {
        return this.original.isDisposed();
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            dispose();
        } else {
            if (event != Lifecycle.Event.ON_CREATE || this.disposed) {
                return;
            }
            setContent(this.lastContent);
        }
    }

    @Override // androidx.compose.runtime.Composition
    public final void setContent(Function2 function2) {
        WrappedComposition$setContent$1 wrappedComposition$setContent$1 = new WrappedComposition$setContent$1(this, function2);
        AndroidComposeView androidComposeView = this.owner;
        AndroidComposeView.ViewTreeOwners viewTreeOwners = androidComposeView.getViewTreeOwners();
        if (viewTreeOwners != null) {
            wrappedComposition$setContent$1.mo779invoke(viewTreeOwners);
        }
        if (androidComposeView.isAttachedToWindow()) {
            return;
        }
        androidComposeView.onViewTreeOwnersAvailable = wrappedComposition$setContent$1;
    }
}
