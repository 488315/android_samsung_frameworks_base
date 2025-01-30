package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DataStoreCoordinator implements Coordinator, PipelineDumpable {
    public final NotifLiveDataStoreImpl notifLiveDataStoreImpl;

    public DataStoreCoordinator(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderListListeners).add(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                DataStoreCoordinator dataStoreCoordinator = DataStoreCoordinator.this;
                dataStoreCoordinator.getClass();
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListEntry listEntry = (ListEntry) it.next();
                    if (listEntry instanceof NotificationEntry) {
                        arrayList.add(listEntry);
                    } else {
                        if (!(listEntry instanceof GroupEntry)) {
                            throw new IllegalStateException(("Unexpected entry " + listEntry).toString());
                        }
                        GroupEntry groupEntry = (GroupEntry) listEntry;
                        NotificationEntry notificationEntry = groupEntry.mSummary;
                        if (notificationEntry == null) {
                            throw new IllegalStateException(("No Summary: " + groupEntry).toString());
                        }
                        arrayList.add(notificationEntry);
                        arrayList.addAll(groupEntry.mUnmodifiableChildren);
                    }
                }
                NotifLiveDataStoreImpl notifLiveDataStoreImpl = dataStoreCoordinator.notifLiveDataStoreImpl;
                notifLiveDataStoreImpl.getClass();
                boolean isTagEnabled = Trace.isTagEnabled(4096L);
                NotifLiveDataImpl notifLiveDataImpl = notifLiveDataStoreImpl.hasActiveNotifsPrivate;
                NotifLiveDataImpl notifLiveDataImpl2 = notifLiveDataStoreImpl.activeNotifCountPrivate;
                NotifLiveDataImpl notifLiveDataImpl3 = notifLiveDataStoreImpl.activeNotifListPrivate;
                if (!isTagEnabled) {
                    Assert.isMainThread();
                    List unmodifiableList = Collections.unmodifiableList(CollectionsKt___CollectionsKt.toList(arrayList));
                    Iterator it2 = CollectionsKt__CollectionsKt.listOf(notifLiveDataImpl3.setValueAndProvideDispatcher(unmodifiableList), notifLiveDataImpl2.setValueAndProvideDispatcher(Integer.valueOf(unmodifiableList.size())), notifLiveDataImpl.setValueAndProvideDispatcher(Boolean.valueOf(!unmodifiableList.isEmpty()))).iterator();
                    while (it2.hasNext()) {
                        ((Function0) it2.next()).invoke();
                    }
                    return;
                }
                Trace.traceBegin(4096L, "NotifLiveDataStore.setActiveNotifList");
                try {
                    Assert.isMainThread();
                    List unmodifiableList2 = Collections.unmodifiableList(CollectionsKt___CollectionsKt.toList(arrayList));
                    Iterator it3 = CollectionsKt__CollectionsKt.listOf(notifLiveDataImpl3.setValueAndProvideDispatcher(unmodifiableList2), notifLiveDataImpl2.setValueAndProvideDispatcher(Integer.valueOf(unmodifiableList2.size())), notifLiveDataImpl.setValueAndProvideDispatcher(Boolean.valueOf(!unmodifiableList2.isEmpty()))).iterator();
                    while (it3.hasNext()) {
                        ((Function0) it3.next()).invoke();
                    }
                    Unit unit = Unit.INSTANCE;
                } finally {
                    Trace.traceEnd(4096L);
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.notifLiveDataStoreImpl, "notifLiveDataStoreImpl");
    }
}
