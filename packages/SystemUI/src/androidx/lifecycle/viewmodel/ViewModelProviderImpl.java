package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.internal.ViewModelProviders;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.ClassReference;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewModelProviderImpl {
    public final CreationExtras extras;
    public final ViewModelProvider.Factory factory;
    public final ViewModelStore store;

    public ViewModelProviderImpl(ViewModelStore viewModelStore, ViewModelProvider.Factory factory, CreationExtras creationExtras) {
        this.store = viewModelStore;
        this.factory = factory;
        this.extras = creationExtras;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ViewModel getViewModel$lifecycle_viewmodel_release(KClass kClass, String str) {
        ViewModel create;
        ViewModelStore viewModelStore = this.store;
        ViewModel viewModel = (ViewModel) ((LinkedHashMap) viewModelStore.map).get(str);
        ClassReference classReference = (ClassReference) kClass;
        boolean isInstance = classReference.isInstance(viewModel);
        ViewModelProvider.Factory factory = this.factory;
        if (isInstance) {
            if (factory instanceof ViewModelProvider.OnRequeryFactory) {
                viewModel.getClass();
                ((ViewModelProvider.OnRequeryFactory) factory).onRequery(viewModel);
            }
            return viewModel;
        }
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(this.extras);
        mutableCreationExtras.set(ViewModelProviders.ViewModelKey.INSTANCE, str);
        try {
            create = factory.create(classReference, mutableCreationExtras);
        } catch (Error unused) {
            create = factory.create(classReference, CreationExtras.Empty.INSTANCE);
        }
        ViewModel viewModel2 = (ViewModel) viewModelStore.map.put(str, create);
        if (viewModel2 != null) {
            viewModel2.clear$lifecycle_viewmodel_release();
        }
        return create;
    }

    public ViewModelProviderImpl(ViewModelStoreOwner viewModelStoreOwner, ViewModelProvider.Factory factory, CreationExtras creationExtras) {
        this(viewModelStoreOwner.getViewModelStore(), factory, creationExtras);
    }
}
