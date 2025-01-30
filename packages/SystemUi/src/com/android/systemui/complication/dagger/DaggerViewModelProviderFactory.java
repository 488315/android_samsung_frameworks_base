package com.android.systemui.complication.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DaggerViewModelProviderFactory implements ViewModelProvider.Factory {
    public final ViewModelCreator mCreator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
