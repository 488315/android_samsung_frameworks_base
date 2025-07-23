package com.android.systemui.communal.widgets;

import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManager$widgets$1$callback$1 extends IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener.Stub {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public GlanceableHubWidgetManager$widgets$1$callback$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener
    public final void onWidgetsUpdated(List list) {
        SendChannel sendChannel = this.$$this$conflatedCallbackFlow;
        if (list == null) {
            list = EmptyList.INSTANCE;
        }
        ((ChannelCoroutine) sendChannel).mo3456trySendJP2dKIU(list);
    }
}
