package com.android.systemui.audio.soundcraft.di.vm.component;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftViewModelExt {
    public static final SoundCraftViewModelExt INSTANCE = new SoundCraftViewModelExt();

    private SoundCraftViewModelExt() {
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
