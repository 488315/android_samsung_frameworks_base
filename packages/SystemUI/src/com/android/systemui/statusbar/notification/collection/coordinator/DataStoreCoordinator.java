package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public final class DataStoreCoordinator implements CoreCoordinator {
    public static final int $stable = 8;
    private final NotifLiveDataStoreImpl notifLiveDataStoreImpl;

    public DataStoreCoordinator(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl;
    }

    private final List<NotificationEntry> flattenedEntryList(List<? extends ListEntry> list) {
        ArrayList arrayList = new ArrayList();
        for (ListEntry listEntry : list) {
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
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderList(List<? extends ListEntry> list) {
        List<NotificationEntry> flattenedEntryList = flattenedEntryList(list);
        NotifLiveDataStoreImpl notifLiveDataStoreImpl = this.notifLiveDataStoreImpl;
        notifLiveDataStoreImpl.getClass();
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotifLiveDataStore.setActiveNotifList");
        }
        try {
            Assert.isMainThread();
            List unmodifiableList = Collections.unmodifiableList(CollectionsKt___CollectionsKt.toList(flattenedEntryList));
            NotifLiveDataImpl notifLiveDataImpl = notifLiveDataStoreImpl.activeNotifListPrivate;
            Intrinsics.checkNotNull(unmodifiableList);
            Iterator it = CollectionsKt__CollectionsKt.listOf(notifLiveDataImpl.setValueAndProvideDispatcher(unmodifiableList), notifLiveDataStoreImpl.activeNotifCountPrivate.setValueAndProvideDispatcher(Integer.valueOf(unmodifiableList.size())), notifLiveDataStoreImpl.hasActiveNotifsPrivate.setValueAndProvideDispatcher(Boolean.valueOf(!unmodifiableList.isEmpty()))).iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).invoke();
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<ListEntry> list, NotifStackController notifStackController) {
                DataStoreCoordinator.this.onAfterRenderList(list);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.CoreCoordinator, com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.notifLiveDataStoreImpl, "notifLiveDataStoreImpl");
    }
}
