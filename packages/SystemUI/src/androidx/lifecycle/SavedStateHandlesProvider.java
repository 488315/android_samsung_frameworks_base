package androidx.lifecycle;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SavedStateHandlesProvider implements SavedStateRegistry.SavedStateProvider {
    public boolean restored;
    public Bundle restoredState;
    public final SavedStateRegistry savedStateRegistry;
    public final Lazy viewModel$delegate;

    public SavedStateHandlesProvider(SavedStateRegistry savedStateRegistry, final ViewModelStoreOwner viewModelStoreOwner) {
        this.savedStateRegistry = savedStateRegistry;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.lifecycle.SavedStateHandlesProvider$viewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewModelStoreOwner viewModelStoreOwner2 = ViewModelStoreOwner.this;
                SavedStateHandleSupport$SAVED_STATE_REGISTRY_OWNER_KEY$1 savedStateHandleSupport$SAVED_STATE_REGISTRY_OWNER_KEY$1 = SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY;
                ViewModelProvider viewModelProvider = new ViewModelProvider(viewModelStoreOwner2, new SavedStateHandleSupport$savedStateHandlesVM$1());
                return (SavedStateHandlesVM) viewModelProvider.impl.getViewModel$lifecycle_viewmodel_release(Reflection.getOrCreateKotlinClass(SavedStateHandlesVM.class), "androidx.lifecycle.internal.SavedStateHandlesVM");
            }
        });
    }

    public final void performRestore() {
        if (this.restored) {
            return;
        }
        Bundle consumeRestoredStateForKey = this.savedStateRegistry.consumeRestoredStateForKey("androidx.lifecycle.internal.SavedStateHandlesProvider");
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.restoredState;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        if (consumeRestoredStateForKey != null) {
            bundle.putAll(consumeRestoredStateForKey);
        }
        this.restoredState = bundle;
        this.restored = true;
    }

    @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
    public final Bundle saveState() {
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.restoredState;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        for (Map.Entry entry : ((LinkedHashMap) ((SavedStateHandlesVM) this.viewModel$delegate.getValue()).handles).entrySet()) {
            String str = (String) entry.getKey();
            Bundle saveState = ((SavedStateHandle) entry.getValue()).savedStateProvider.saveState();
            if (!Intrinsics.areEqual(saveState, Bundle.EMPTY)) {
                bundle.putBundle(str, saveState);
            }
        }
        this.restored = false;
        return bundle;
    }
}
