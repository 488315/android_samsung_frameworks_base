package com.android.systemui.statusbar.notification.row;

import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationContentExtractor;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationContentInflater_Factory implements Provider {
    public final Provider conversationProcessorProvider;
    public final Provider headsUpStyleProvider;
    public final Provider inflationExecutorProvider;
    public final Provider loggerProvider;
    public final Provider mediaFeatureFlagProvider;
    public final Provider notifLayoutInflaterFactoryProvider;
    public final Provider promotedNotificationContentExtractorProvider;
    public final Provider remoteInputManagerProvider;
    public final Provider remoteViewCacheProvider;
    public final Provider smartRepliesInflaterProvider;

    public NotificationContentInflater_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.remoteViewCacheProvider = provider;
        this.remoteInputManagerProvider = provider2;
        this.conversationProcessorProvider = provider3;
        this.mediaFeatureFlagProvider = provider4;
        this.inflationExecutorProvider = provider5;
        this.smartRepliesInflaterProvider = provider6;
        this.notifLayoutInflaterFactoryProvider = provider7;
        this.headsUpStyleProvider = provider8;
        this.promotedNotificationContentExtractorProvider = provider9;
        this.loggerProvider = provider10;
    }

    public static NotificationContentInflater newInstance(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, MediaFeatureFlag mediaFeatureFlag, Executor executor, SmartReplyStateInflaterImpl smartReplyStateInflaterImpl, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, PromotedNotificationContentExtractor promotedNotificationContentExtractor, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        return new NotificationContentInflater(notifRemoteViewCache, notificationRemoteInputManager, conversationNotificationProcessor, mediaFeatureFlag, executor, smartReplyStateInflaterImpl, provider, headsUpStyleProvider, promotedNotificationContentExtractor, notificationRowContentBinderLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotificationContentInflater((NotifRemoteViewCache) this.remoteViewCacheProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (ConversationNotificationProcessor) this.conversationProcessorProvider.get(), (MediaFeatureFlag) this.mediaFeatureFlagProvider.get(), (Executor) this.inflationExecutorProvider.get(), (SmartReplyStateInflater) this.smartRepliesInflaterProvider.get(), (NotifLayoutInflaterFactory.Provider) this.notifLayoutInflaterFactoryProvider.get(), (HeadsUpStyleProvider) this.headsUpStyleProvider.get(), (PromotedNotificationContentExtractor) this.promotedNotificationContentExtractorProvider.get(), (NotificationRowContentBinderLogger) this.loggerProvider.get());
    }
}
