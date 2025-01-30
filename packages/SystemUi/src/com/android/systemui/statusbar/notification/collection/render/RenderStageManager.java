package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator$attach$2;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RenderStageManager implements PipelineDumpable {
    public ShadeViewManager$viewRenderer$1 viewRenderer;
    public final List onAfterRenderListListeners = new ArrayList();
    public final List onAfterRenderGroupListeners = new ArrayList();
    public final List onAfterRenderEntryListeners = new ArrayList();

    public final void dispatchOnAfterRenderEntries(ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1, List list) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        List list2 = this.onAfterRenderEntryListeners;
        if (!isTagEnabled) {
            ArrayList arrayList = (ArrayList) list2;
            if (arrayList.isEmpty()) {
                return;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ListEntry listEntry = (ListEntry) it.next();
                if (listEntry instanceof NotificationEntry) {
                    NotificationEntry notificationEntry = (NotificationEntry) listEntry;
                    NotifViewController rowController = shadeViewManager$viewRenderer$1.getRowController(notificationEntry);
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        ((RowAppearanceCoordinator$attach$2) it2.next()).onAfterRenderEntry(notificationEntry, rowController);
                    }
                } else {
                    if (!(listEntry instanceof GroupEntry)) {
                        throw new IllegalStateException(("Unhandled entry: " + listEntry).toString());
                    }
                    GroupEntry groupEntry = (GroupEntry) listEntry;
                    NotificationEntry notificationEntry2 = groupEntry.mSummary;
                    if (notificationEntry2 == null) {
                        throw new IllegalStateException(("No Summary: " + groupEntry).toString());
                    }
                    NotifViewController rowController2 = shadeViewManager$viewRenderer$1.getRowController(notificationEntry2);
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        ((RowAppearanceCoordinator$attach$2) it3.next()).onAfterRenderEntry(notificationEntry2, rowController2);
                    }
                    for (NotificationEntry notificationEntry3 : groupEntry.mUnmodifiableChildren) {
                        NotifViewController rowController3 = shadeViewManager$viewRenderer$1.getRowController(notificationEntry3);
                        Iterator it4 = arrayList.iterator();
                        while (it4.hasNext()) {
                            ((RowAppearanceCoordinator$attach$2) it4.next()).onAfterRenderEntry(notificationEntry3, rowController3);
                        }
                    }
                }
            }
            return;
        }
        Trace.traceBegin(4096L, "RenderStageManager.dispatchOnAfterRenderEntries");
        try {
            if (((ArrayList) list2).isEmpty()) {
                return;
            }
            Iterator it5 = list.iterator();
            while (it5.hasNext()) {
                ListEntry listEntry2 = (ListEntry) it5.next();
                if (listEntry2 instanceof NotificationEntry) {
                    NotificationEntry notificationEntry4 = (NotificationEntry) listEntry2;
                    NotifViewController rowController4 = shadeViewManager$viewRenderer$1.getRowController(notificationEntry4);
                    Iterator it6 = ((ArrayList) list2).iterator();
                    while (it6.hasNext()) {
                        ((RowAppearanceCoordinator$attach$2) it6.next()).onAfterRenderEntry(notificationEntry4, rowController4);
                    }
                } else {
                    if (!(listEntry2 instanceof GroupEntry)) {
                        throw new IllegalStateException(("Unhandled entry: " + listEntry2).toString());
                    }
                    GroupEntry groupEntry2 = (GroupEntry) listEntry2;
                    NotificationEntry notificationEntry5 = groupEntry2.mSummary;
                    if (notificationEntry5 == null) {
                        throw new IllegalStateException(("No Summary: " + groupEntry2).toString());
                    }
                    NotifViewController rowController5 = shadeViewManager$viewRenderer$1.getRowController(notificationEntry5);
                    ArrayList arrayList2 = (ArrayList) list2;
                    Iterator it7 = arrayList2.iterator();
                    while (it7.hasNext()) {
                        ((RowAppearanceCoordinator$attach$2) it7.next()).onAfterRenderEntry(notificationEntry5, rowController5);
                    }
                    for (NotificationEntry notificationEntry6 : ((GroupEntry) listEntry2).mUnmodifiableChildren) {
                        NotifViewController rowController6 = shadeViewManager$viewRenderer$1.getRowController(notificationEntry6);
                        Iterator it8 = arrayList2.iterator();
                        while (it8.hasNext()) {
                            ((RowAppearanceCoordinator$attach$2) it8.next()).onAfterRenderEntry(notificationEntry6, rowController6);
                        }
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final void dispatchOnAfterRenderGroups(ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1, List list) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        List list2 = this.onAfterRenderGroupListeners;
        if (!isTagEnabled) {
            ArrayList arrayList = (ArrayList) list2;
            if (arrayList.isEmpty()) {
                return;
            }
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.RenderStageManager$dispatchOnAfterRenderGroups$lambda$6$$inlined$filterIsInstance$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof GroupEntry);
                }
            }));
            while (filteringSequence$iterator$1.hasNext()) {
                GroupEntry groupEntry = (GroupEntry) filteringSequence$iterator$1.next();
                NotifViewController groupController = shadeViewManager$viewRenderer$1.getGroupController(groupEntry);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((OnAfterRenderGroupListener) it.next()).onAfterRenderGroup(groupEntry, groupController);
                }
            }
            return;
        }
        Trace.traceBegin(4096L, "RenderStageManager.dispatchOnAfterRenderGroups");
        try {
            if (((ArrayList) list2).isEmpty()) {
                return;
            }
            FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.RenderStageManager$dispatchOnAfterRenderGroups$lambda$6$$inlined$filterIsInstance$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof GroupEntry);
                }
            }));
            while (filteringSequence$iterator$12.hasNext()) {
                GroupEntry groupEntry2 = (GroupEntry) filteringSequence$iterator$12.next();
                NotifViewController groupController2 = shadeViewManager$viewRenderer$1.getGroupController(groupEntry2);
                Iterator it2 = ((ArrayList) list2).iterator();
                while (it2.hasNext()) {
                    ((OnAfterRenderGroupListener) it2.next()).onAfterRenderGroup(groupEntry2, groupController2);
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final void dispatchOnAfterRenderList(ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1, List list) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        List list2 = this.onAfterRenderListListeners;
        ShadeViewManager shadeViewManager = shadeViewManager$viewRenderer$1.this$0;
        if (!isTagEnabled) {
            NotifStackController notifStackController = shadeViewManager.stackController;
            Iterator it = ((ArrayList) list2).iterator();
            while (it.hasNext()) {
                ((OnAfterRenderListListener) it.next()).onAfterRenderList(list, notifStackController);
            }
            return;
        }
        Trace.traceBegin(4096L, "RenderStageManager.dispatchOnAfterRenderList");
        try {
            NotifStackController notifStackController2 = shadeViewManager.stackController;
            Iterator it2 = ((ArrayList) list2).iterator();
            while (it2.hasNext()) {
                ((OnAfterRenderListListener) it2.next()).onAfterRenderList(list, notifStackController2);
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.viewRenderer, "viewRenderer");
        pipelineDumper.dump(this.onAfterRenderListListeners, "onAfterRenderListListeners");
        pipelineDumper.dump(this.onAfterRenderGroupListeners, "onAfterRenderGroupListeners");
        pipelineDumper.dump(this.onAfterRenderEntryListeners, "onAfterRenderEntryListeners");
    }
}
