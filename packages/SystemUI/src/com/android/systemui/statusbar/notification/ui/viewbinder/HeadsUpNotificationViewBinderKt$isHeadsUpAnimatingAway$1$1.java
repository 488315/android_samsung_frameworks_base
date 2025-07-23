package com.android.systemui.statusbar.notification.ui.viewbinder;

import java.util.function.Consumer;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ((ChannelCoroutine) sendChannel).mo3456trySendJP2dKIU(bool);
    }
}
