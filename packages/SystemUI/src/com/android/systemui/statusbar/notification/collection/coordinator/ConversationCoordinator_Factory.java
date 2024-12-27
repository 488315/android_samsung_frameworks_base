package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.icon.ConversationIconManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import dagger.internal.Provider;

public final class ConversationCoordinator_Factory implements Provider {
    private final javax.inject.Provider conversationIconManagerProvider;
    private final javax.inject.Provider highPriorityProvider;
    private final javax.inject.Provider peopleHeaderControllerProvider;
    private final javax.inject.Provider peopleNotificationIdentifierProvider;

    public ConversationCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.peopleNotificationIdentifierProvider = provider;
        this.conversationIconManagerProvider = provider2;
        this.highPriorityProvider = provider3;
        this.peopleHeaderControllerProvider = provider4;
    }

    public static ConversationCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new ConversationCoordinator_Factory(provider, provider2, provider3, provider4);
    }

    public static ConversationCoordinator newInstance(PeopleNotificationIdentifier peopleNotificationIdentifier, ConversationIconManager conversationIconManager, HighPriorityProvider highPriorityProvider, NodeController nodeController) {
        return new ConversationCoordinator(peopleNotificationIdentifier, conversationIconManager, highPriorityProvider, nodeController);
    }

    @Override // javax.inject.Provider
    public ConversationCoordinator get() {
        return newInstance((PeopleNotificationIdentifier) this.peopleNotificationIdentifierProvider.get(), (ConversationIconManager) this.conversationIconManagerProvider.get(), (HighPriorityProvider) this.highPriorityProvider.get(), (NodeController) this.peopleHeaderControllerProvider.get());
    }
}
