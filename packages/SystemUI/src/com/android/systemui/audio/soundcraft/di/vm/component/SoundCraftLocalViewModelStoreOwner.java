package com.android.systemui.audio.soundcraft.di.vm.component;

import androidx.lifecycle.ViewModelStoreOwner;

public final class SoundCraftLocalViewModelStoreOwner {
    public static final SoundCraftLocalViewModelStoreOwner INSTANCE = new SoundCraftLocalViewModelStoreOwner();
    public static ViewModelStoreOwner current;

    private SoundCraftLocalViewModelStoreOwner() {
    }
}
