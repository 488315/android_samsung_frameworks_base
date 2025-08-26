package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class DataStoreCoordinator implements CoreCoordinator {
    public static final int $stable = 8;
    private final NotifLiveDataStoreImpl notifLiveDataStoreImpl;

    public DataStoreCoordinator(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl;
    }

    private final List<NotificationEntry> flattenedEntryList(List<? extends PipelineEntry> list) {
        ArrayList arrayList = new ArrayList();
        for (PipelineEntry pipelineEntry : list) {
            if (pipelineEntry instanceof NotificationEntry) {
                arrayList.add(pipelineEntry);
            } else {
                if (!(pipelineEntry instanceof GroupEntry)) {
                    throw new IllegalStateException(("Unexpected entry " + pipelineEntry).toString());
                }
                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
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
    public final void onAfterRenderList(List<? extends PipelineEntry> list) {
        List<NotificationEntry> listFlattenedEntryList = flattenedEntryList(list);
        NotifLiveDataStoreImpl notifLiveDataStoreImpl = this.notifLiveDataStoreImpl;
        notifLiveDataStoreImpl.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NotifLiveDataStore.setActiveNotifList");
        }
        try {
            Assert.isMainThread();
            List listUnmodifiableList = Collections.unmodifiableList(CollectionsKt___CollectionsKt.toList(listFlattenedEntryList));
            NotifLiveDataImpl notifLiveDataImpl = notifLiveDataStoreImpl.activeNotifListPrivate;
            listUnmodifiableList.getClass();
            Iterator it = Arrays.asList(notifLiveDataImpl.setValueAndProvideDispatcher(listUnmodifiableList), notifLiveDataStoreImpl.activeNotifCountPrivate.setValueAndProvideDispatcher(Integer.valueOf(listUnmodifiableList.size())), notifLiveDataStoreImpl.hasActiveNotifsPrivate.setValueAndProvideDispatcher(Boolean.valueOf(!listUnmodifiableList.isEmpty()))).iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).invoke();
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<PipelineEntry> list) {
                DataStoreCoordinator.this.onAfterRenderList(list);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.CoreCoordinator, com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.notifLiveDataStoreImpl, "notifLiveDataStoreImpl");
    }
}
