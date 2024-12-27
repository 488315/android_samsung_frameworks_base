package com.android.systemui.statusbar.notification.collection;

import java.util.concurrent.Executor;
import kotlin.collections.EmptyList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotifLiveDataStoreImpl implements NotifLiveDataStore, PipelineDumpable {
    public final NotifLiveDataImpl activeNotifCount;
    public final NotifLiveDataImpl activeNotifCountPrivate;
    public final NotifLiveDataImpl activeNotifList;
    public final NotifLiveDataImpl activeNotifListPrivate;
    public final NotifLiveDataImpl hasActiveNotifs;
    public final NotifLiveDataImpl hasActiveNotifsPrivate;
    public final Executor mainExecutor;

    public NotifLiveDataStoreImpl(Executor executor) {
        this.mainExecutor = executor;
        NotifLiveDataImpl notifLiveDataImpl = new NotifLiveDataImpl("hasActiveNotifs", Boolean.FALSE, executor);
        this.hasActiveNotifsPrivate = notifLiveDataImpl;
        NotifLiveDataImpl notifLiveDataImpl2 = new NotifLiveDataImpl("activeNotifCount", 0, executor);
        this.activeNotifCountPrivate = notifLiveDataImpl2;
        NotifLiveDataImpl notifLiveDataImpl3 = new NotifLiveDataImpl("activeNotifList", EmptyList.INSTANCE, executor);
        this.activeNotifListPrivate = notifLiveDataImpl3;
        this.hasActiveNotifs = notifLiveDataImpl;
        this.activeNotifCount = notifLiveDataImpl2;
        this.activeNotifList = notifLiveDataImpl3;
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.activeNotifListPrivate, "activeNotifListPrivate");
        pipelineDumper.dump(this.activeNotifCountPrivate, "activeNotifCountPrivate");
        pipelineDumper.dump(this.hasActiveNotifsPrivate, "hasActiveNotifsPrivate");
    }
}
