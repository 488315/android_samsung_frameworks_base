package androidx.lifecycle;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                return SavedStateHandleSupport.getSavedStateHandlesVM(ViewModelStoreOwner.this);
            }
        });
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
