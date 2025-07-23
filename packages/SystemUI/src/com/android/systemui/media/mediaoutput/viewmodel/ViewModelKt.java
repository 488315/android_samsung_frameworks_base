package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ViewModelKt {
    public static final ViewModelProvider.Factory createDaggerViewModelFactory(ViewModelStoreOwner viewModelStoreOwner) {
        if (viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory) {
            return ((HasDefaultViewModelProviderFactory) viewModelStoreOwner).getDefaultViewModelProviderFactory();
        }
        return null;
    }

    public static final ViewModel get(ViewModelStoreOwner viewModelStoreOwner, Class cls, ViewModelProvider.Factory factory, CreationExtras creationExtras) {
        ViewModelProvider create$default;
        if (factory != null) {
            ViewModelProvider.Companion companion = ViewModelProvider.Companion;
            ViewModelStore viewModelStore = viewModelStoreOwner.getViewModelStore();
            companion.getClass();
            create$default = new ViewModelProvider(viewModelStore, factory, creationExtras);
        } else {
            create$default = ViewModelProvider.Companion.create$default(ViewModelProvider.Companion, viewModelStoreOwner);
        }
        return create$default.get(cls);
    }
}
