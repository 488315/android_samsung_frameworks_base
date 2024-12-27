package com.android.systemui.complication;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.complication.dagger.DaggerViewModelProviderFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComplicationViewModelProvider extends ViewModelProvider {
    public ComplicationViewModelProvider(ViewModelStore viewModelStore, final ComplicationViewModel complicationViewModel) {
        super(viewModelStore, new DaggerViewModelProviderFactory(new DaggerViewModelProviderFactory.ViewModelCreator() { // from class: com.android.systemui.complication.ComplicationViewModelProvider$$ExternalSyntheticLambda0
            @Override // com.android.systemui.complication.dagger.DaggerViewModelProviderFactory.ViewModelCreator
            public final ViewModel create() {
                return ComplicationViewModel.this;
            }
        }));
    }
}
