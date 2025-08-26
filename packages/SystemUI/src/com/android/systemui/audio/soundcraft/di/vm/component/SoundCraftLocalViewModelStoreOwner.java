package com.android.systemui.audio.soundcraft.di.vm.component;

import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;

/* loaded from: classes.dex */
public final class SoundCraftLocalViewModelStoreOwner {
    public static final SoundCraftLocalViewModelStoreOwner INSTANCE = new SoundCraftLocalViewModelStoreOwner();
    public static SoundCraftViewComponent current;

    private SoundCraftLocalViewModelStoreOwner() {
    }
}
