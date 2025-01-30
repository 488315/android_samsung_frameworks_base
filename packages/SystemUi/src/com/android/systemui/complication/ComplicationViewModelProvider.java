package com.android.systemui.complication;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.complication.dagger.DaggerViewModelProviderFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
