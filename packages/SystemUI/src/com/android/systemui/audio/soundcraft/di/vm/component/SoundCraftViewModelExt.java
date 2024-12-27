package com.android.systemui.audio.soundcraft.di.vm.component;

import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;

public final class SoundCraftViewModelExt {
    public static final SoundCraftViewModelExt INSTANCE = new SoundCraftViewModelExt();

    private SoundCraftViewModelExt() {
    }

    public static ViewModelProvider.Factory createDaggerViewModelFactory(SoundCraftViewComponent soundCraftViewComponent) {
        if (soundCraftViewComponent instanceof HasDefaultViewModelProviderFactory) {
            return soundCraftViewComponent.getDefaultViewModelProviderFactory();
        }
        return null;
    }

    public static ViewModel get(SoundCraftViewComponent soundCraftViewComponent, Class cls, ViewModelProvider.Factory factory) {
        ViewModelProvider viewModelProvider;
        if (factory != null) {
            viewModelProvider = new ViewModelProvider(soundCraftViewComponent.viewModelStore, factory, null, 4, null);
        } else {
            viewModelProvider = new ViewModelProvider(soundCraftViewComponent);
        }
        return viewModelProvider.get(cls);
    }
}
