package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.icon.ConversationIconManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class ConversationCoordinator_Factory implements Provider {
    private final Provider conversationIconManagerProvider;
    private final Provider highPriorityProvider;
    private final Provider peopleHeaderControllerProvider;
    private final Provider peopleNotificationIdentifierProvider;

    public ConversationCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.peopleNotificationIdentifierProvider = provider;
        this.conversationIconManagerProvider = provider2;
        this.highPriorityProvider = provider3;
        this.peopleHeaderControllerProvider = provider4;
    }

    public static ConversationCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new ConversationCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static ConversationCoordinator newInstance(PeopleNotificationIdentifier peopleNotificationIdentifier, ConversationIconManager conversationIconManager, HighPriorityProvider highPriorityProvider, NodeController nodeController) {
        return new ConversationCoordinator(peopleNotificationIdentifier, conversationIconManager, highPriorityProvider, nodeController);
    }

    public static ConversationCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new ConversationCoordinator_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public ConversationCoordinator get() {
        return newInstance((PeopleNotificationIdentifier) this.peopleNotificationIdentifierProvider.get(), (ConversationIconManager) this.conversationIconManagerProvider.get(), (HighPriorityProvider) this.highPriorityProvider.get(), (NodeController) this.peopleHeaderControllerProvider.get());
    }
}
