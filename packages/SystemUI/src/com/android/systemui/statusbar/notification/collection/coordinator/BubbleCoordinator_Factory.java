package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.internal.Provider;
import java.util.Optional;

public final class BubbleCoordinator_Factory implements Provider {
    private final javax.inject.Provider bubblesManagerOptionalProvider;
    private final javax.inject.Provider bubblesOptionalProvider;
    private final javax.inject.Provider notifCollectionProvider;

    public BubbleCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.bubblesManagerOptionalProvider = provider;
        this.bubblesOptionalProvider = provider2;
        this.notifCollectionProvider = provider3;
    }

    public static BubbleCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new BubbleCoordinator_Factory(provider, provider2, provider3);
    }

    public static BubbleCoordinator newInstance(Optional<BubblesManager> optional, Optional<Bubbles> optional2, NotifCollection notifCollection) {
        return new BubbleCoordinator(optional, optional2, notifCollection);
    }

    @Override // javax.inject.Provider
    public BubbleCoordinator get() {
        return newInstance((Optional) this.bubblesManagerOptionalProvider.get(), (Optional) this.bubblesOptionalProvider.get(), (NotifCollection) this.notifCollectionProvider.get());
    }
}
