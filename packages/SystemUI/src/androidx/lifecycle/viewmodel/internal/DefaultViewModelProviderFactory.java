package androidx.lifecycle.viewmodel.internal;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DefaultViewModelProviderFactory implements ViewModelProvider.Factory {
    public static final DefaultViewModelProviderFactory INSTANCE = new DefaultViewModelProviderFactory();

    private DefaultViewModelProviderFactory() {
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(KClass kClass, CreationExtras creationExtras) {
        JvmViewModelProviders jvmViewModelProviders = JvmViewModelProviders.INSTANCE;
        Class jClass = ((ClassBasedDeclarationContainer) kClass).getJClass();
        jvmViewModelProviders.getClass();
        return JvmViewModelProviders.createViewModel(jClass);
    }
}
