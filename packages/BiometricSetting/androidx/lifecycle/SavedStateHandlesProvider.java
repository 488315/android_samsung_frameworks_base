package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactory;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;
import androidx.savedstate.SavedStateRegistry;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: SavedStateHandleSupport.kt */
/* loaded from: classes.dex */
public final class SavedStateHandlesProvider implements SavedStateRegistry.SavedStateProvider {
    private boolean restored;
    private Bundle restoredState;
    private final SavedStateRegistry savedStateRegistry;
    private final Lazy viewModel$delegate;

    public SavedStateHandlesProvider(SavedStateRegistry savedStateRegistry, final ViewModelStoreOwner viewModelStoreOwner) {
        Intrinsics.checkNotNullParameter(savedStateRegistry, "savedStateRegistry");
        Intrinsics.checkNotNullParameter(viewModelStoreOwner, "viewModelStoreOwner");
        this.savedStateRegistry = savedStateRegistry;
        this.viewModel$delegate = LazyKt.lazy(new Function0<SavedStateHandlesVM>() { // from class: androidx.lifecycle.SavedStateHandlesProvider$viewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SavedStateHandlesVM invoke() {
                CreationExtras creationExtras;
                ViewModelStoreOwner viewModelStoreOwner2 = ViewModelStoreOwner.this;
                Intrinsics.checkNotNullParameter(viewModelStoreOwner2, "<this>");
                InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder = new InitializerViewModelFactoryBuilder();
                initializerViewModelFactoryBuilder.addInitializer(Reflection.getOrCreateKotlinClass(), new Function1<CreationExtras, SavedStateHandlesVM>() { // from class: androidx.lifecycle.SavedStateHandleSupport$savedStateHandlesVM$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final SavedStateHandlesVM invoke(CreationExtras creationExtras2) {
                        CreationExtras initializer = creationExtras2;
                        Intrinsics.checkNotNullParameter(initializer, "$this$initializer");
                        return new SavedStateHandlesVM();
                    }
                });
                InitializerViewModelFactory build = initializerViewModelFactoryBuilder.build();
                ViewModelStore viewModelStore = viewModelStoreOwner2.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "owner.viewModelStore");
                if (viewModelStoreOwner2 instanceof HasDefaultViewModelProviderFactory) {
                    creationExtras = ((HasDefaultViewModelProviderFactory) viewModelStoreOwner2).getDefaultViewModelCreationExtras();
                    Intrinsics.checkNotNullExpressionValue(creationExtras, "{\n        owner.defaultV…ModelCreationExtras\n    }");
                } else {
                    creationExtras = CreationExtras.Empty.INSTANCE;
                }
                return (SavedStateHandlesVM) new ViewModelProvider(viewModelStore, build, creationExtras).get(SavedStateHandlesVM.class, "androidx.lifecycle.internal.SavedStateHandlesVM");
            }
        });
    }

    private final SavedStateHandlesVM getViewModel() {
        return (SavedStateHandlesVM) this.viewModel$delegate.getValue();
    }

    public final void performRestore() {
        if (this.restored) {
            return;
        }
        this.restoredState = this.savedStateRegistry.consumeRestoredStateForKey("androidx.lifecycle.internal.SavedStateHandlesProvider");
        this.restored = true;
        getViewModel();
    }

    @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
    public final Bundle saveState() {
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.restoredState;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        for (Map.Entry entry : ((LinkedHashMap) getViewModel().getHandles()).entrySet()) {
            String str = (String) entry.getKey();
            Bundle saveState = ((SavedStateHandle) entry.getValue()).savedStateProvider().saveState();
            if (!Intrinsics.areEqual(saveState, Bundle.EMPTY)) {
                bundle.putBundle(str, saveState);
            }
        }
        this.restored = false;
        return bundle;
    }
}
