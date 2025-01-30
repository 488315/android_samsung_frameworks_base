package com.android.systemui.statusbar.notification.collection;

import java.util.concurrent.Executor;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
