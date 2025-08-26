package com.android.systemui.statusbar.notification.ui.viewbinder;

import java.util.function.Consumer;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: classes3.dex */
public final class HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1 implements Consumer {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Boolean bool = (Boolean) obj;
        SendChannel sendChannel = this.$$this$conflatedCallbackFlow;
        bool.getClass();
        ((ChannelCoroutine) sendChannel).mo3476trySendJP2dKIU(bool);
    }
}
