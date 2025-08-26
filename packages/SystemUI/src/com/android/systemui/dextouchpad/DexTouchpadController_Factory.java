package com.android.systemui.dextouchpad;

import android.content.Context;
import com.android.systemui.dextouchpad.manager.NavBarIconManager;
import com.android.systemui.statusbar.CommandQueue;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class DexTouchpadController_Factory implements Provider {
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider mNavBarIconManagerProvider;

    public DexTouchpadController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
        this.mNavBarIconManagerProvider = provider3;
    }

    public static DexTouchpadController newInstance(Context context, CommandQueue commandQueue) {
        return new DexTouchpadController(context, commandQueue);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DexTouchpadController dexTouchpadController = new DexTouchpadController((Context) this.contextProvider.get(), (CommandQueue) this.commandQueueProvider.get());
        dexTouchpadController.mNavBarIconManager = (NavBarIconManager) this.mNavBarIconManagerProvider.get();
        return dexTouchpadController;
    }
}
