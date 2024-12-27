package com.android.systemui.complication.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class DaggerViewModelProviderFactory implements ViewModelProvider.Factory {
    public final ViewModelCreator mCreator;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ViewModelCreator {
        ViewModel create();
    }

    public DaggerViewModelProviderFactory(ViewModelCreator viewModelCreator) {
        this.mCreator = viewModelCreator;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        return this.mCreator.create();
    }
}
