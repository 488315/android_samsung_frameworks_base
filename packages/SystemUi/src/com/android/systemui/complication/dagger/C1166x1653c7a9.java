package com.android.systemui.complication.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.complication.ComplicationCollectionViewModel;
import com.android.systemui.complication.dagger.DaggerViewModelProviderFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.complication.dagger.ComplicationModule_ProvidesComplicationCollectionViewModelFactory */
/* loaded from: classes.dex */
public final class C1166x1653c7a9 implements Provider {
    public final Provider storeProvider;
    public final Provider viewModelProvider;

    public C1166x1653c7a9(Provider provider, Provider provider2) {
        this.storeProvider = provider;
        this.viewModelProvider = provider2;
    }

    public static ComplicationCollectionViewModel providesComplicationCollectionViewModel(ViewModelStore viewModelStore, final ComplicationCollectionViewModel complicationCollectionViewModel) {
        ComplicationCollectionViewModel complicationCollectionViewModel2 = (ComplicationCollectionViewModel) new ViewModelProvider(viewModelStore, new DaggerViewModelProviderFactory(new DaggerViewModelProviderFactory.ViewModelCreator() { // from class: com.android.systemui.complication.dagger.ComplicationModule$$ExternalSyntheticLambda0
            @Override // com.android.systemui.complication.dagger.DaggerViewModelProviderFactory.ViewModelCreator
            public final ViewModel create() {
                return ComplicationCollectionViewModel.this;
            }
        })).get(ComplicationCollectionViewModel.class);
        Preconditions.checkNotNullFromProvides(complicationCollectionViewModel2);
        return complicationCollectionViewModel2;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesComplicationCollectionViewModel((ViewModelStore) this.storeProvider.get(), (ComplicationCollectionViewModel) this.viewModelProvider.get());
    }
}
