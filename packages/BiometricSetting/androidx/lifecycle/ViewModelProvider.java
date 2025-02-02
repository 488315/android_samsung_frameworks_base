package androidx.lifecycle;

import android.app.Application;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewModelProvider.kt */
/* loaded from: classes.dex */
public final class ViewModelProvider {
    private final CreationExtras defaultCreationExtras;
    private final Factory factory;
    private final ViewModelStore store;

    /* compiled from: ViewModelProvider.kt */
    public static class AndroidViewModelFactory extends NewInstanceFactory {
        public static final CreationExtras.Key<Application> APPLICATION_KEY = C0147x44c92b45.INSTANCE;
    }

    /* compiled from: ViewModelProvider.kt */
    public interface Factory {
        default <T extends ViewModel> T create(Class<T> cls) {
            throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
        }

        default ViewModel create(Class cls, MutableCreationExtras mutableCreationExtras) {
            return create(cls);
        }
    }

    /* compiled from: ViewModelProvider.kt */
    public static class NewInstanceFactory implements Factory {
        public static final CreationExtras.Key<String> VIEW_MODEL_KEY = ViewModelProvider$NewInstanceFactory$Companion$ViewModelKeyImpl.INSTANCE;
    }

    /* compiled from: ViewModelProvider.kt */
    public static class OnRequeryFactory {
    }

    public ViewModelProvider(ViewModelStore store, Factory factory, CreationExtras defaultCreationExtras) {
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(defaultCreationExtras, "defaultCreationExtras");
        this.store = store;
        this.factory = factory;
        this.defaultCreationExtras = defaultCreationExtras;
    }

    public final <T extends ViewModel> T get(Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return (T) get(cls, "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName));
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ViewModel get(Class cls, String key) {
        ViewModel create;
        Intrinsics.checkNotNullParameter(key, "key");
        ViewModelStore viewModelStore = this.store;
        ViewModel viewModel = viewModelStore.get(key);
        boolean isInstance = cls.isInstance(viewModel);
        Factory factory = this.factory;
        if (isInstance) {
            if ((factory instanceof OnRequeryFactory ? (OnRequeryFactory) factory : null) != null) {
                Intrinsics.checkNotNullExpressionValue(viewModel, "viewModel");
            }
            Intrinsics.checkNotNull(viewModel, "null cannot be cast to non-null type T of androidx.lifecycle.ViewModelProvider.get");
            return viewModel;
        }
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(this.defaultCreationExtras);
        CreationExtras.Key<String> key2 = NewInstanceFactory.VIEW_MODEL_KEY;
        mutableCreationExtras.getMap$lifecycle_viewmodel_release().put(ViewModelProvider$NewInstanceFactory$Companion$ViewModelKeyImpl.INSTANCE, key);
        try {
            create = factory.create(cls, mutableCreationExtras);
        } catch (AbstractMethodError unused) {
            create = factory.create(cls);
        }
        viewModelStore.put(key, create);
        return create;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStore store, Factory factory) {
        this(store, factory, CreationExtras.Empty.INSTANCE);
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
    }
}
