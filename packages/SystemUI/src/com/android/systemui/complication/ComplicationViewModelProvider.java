package com.android.systemui.complication;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.complication.dagger.DaggerViewModelProviderFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ComplicationViewModelProvider extends ViewModelProvider {
    public static final /* synthetic */ int $r8$clinit = 0;

    public ComplicationViewModelProvider(ViewModelStore viewModelStore, final ComplicationViewModel complicationViewModel) {
        super(viewModelStore, new DaggerViewModelProviderFactory(new DaggerViewModelProviderFactory.ViewModelCreator() { // from class: com.android.systemui.complication.ComplicationViewModelProvider$$ExternalSyntheticLambda0
            @Override // com.android.systemui.complication.dagger.DaggerViewModelProviderFactory.ViewModelCreator
            public final ViewModel create() {
                int i = ComplicationViewModelProvider.$r8$clinit;
                return ComplicationViewModel.this;
            }
        }));
    }
}
