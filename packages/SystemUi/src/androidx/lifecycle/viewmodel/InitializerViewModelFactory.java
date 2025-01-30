package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InitializerViewModelFactory implements ViewModelProvider.Factory {
    public final ViewModelInitializer[] initializers;

    public InitializerViewModelFactory(ViewModelInitializer... viewModelInitializerArr) {
        this.initializers = viewModelInitializerArr;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls, MutableCreationExtras mutableCreationExtras) {
        ViewModel viewModel = null;
        for (ViewModelInitializer viewModelInitializer : this.initializers) {
            if (Intrinsics.areEqual(viewModelInitializer.clazz, cls)) {
                Object invoke = viewModelInitializer.initializer.invoke(mutableCreationExtras);
                viewModel = invoke instanceof ViewModel ? (ViewModel) invoke : null;
            }
        }
        if (viewModel != null) {
            return viewModel;
        }
        throw new IllegalArgumentException("No initializer set for given class ".concat(cls.getName()));
    }
}
