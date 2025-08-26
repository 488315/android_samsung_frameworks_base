package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final class RenderStageManager implements PipelineDumpable {
    public ShadeViewManager$viewRenderer$1 viewRenderer;
    public final List onAfterRenderListListeners = new ArrayList();
    public final List onAfterRenderGroupListeners = new ArrayList();
    public final List onAfterRenderEntryListeners = new ArrayList();

    public final void dispatchOnAfterRenderEntries(ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1, List list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderStageManager.dispatchOnAfterRenderEntries");
        }
        try {
            if (((ArrayList) this.onAfterRenderEntryListeners).isEmpty()) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                PipelineEntry pipelineEntry = (PipelineEntry) it.next();
                int i = 0;
                if (pipelineEntry instanceof NotificationEntry) {
                    NotificationEntry notificationEntry = (NotificationEntry) pipelineEntry;
                    NotifViewController rowController = shadeViewManager$viewRenderer$1.getRowController(notificationEntry);
                    ArrayList arrayList = (ArrayList) this.onAfterRenderEntryListeners;
                    int size = arrayList.size();
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((OnAfterRenderEntryListener) obj).onAfterRenderEntry(notificationEntry, rowController);
                    }
                } else {
                    if (!(pipelineEntry instanceof GroupEntry)) {
                        throw new IllegalStateException(("Unhandled entry: " + pipelineEntry).toString());
                    }
                    GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                    NotificationEntry notificationEntry2 = groupEntry.mSummary;
                    if (notificationEntry2 == null) {
                        throw new IllegalStateException(("No Summary: " + groupEntry).toString());
                    }
                    NotifViewController rowController2 = shadeViewManager$viewRenderer$1.getRowController(notificationEntry2);
                    ArrayList arrayList2 = (ArrayList) this.onAfterRenderEntryListeners;
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        ((OnAfterRenderEntryListener) obj2).onAfterRenderEntry(notificationEntry2, rowController2);
                    }
                    for (NotificationEntry notificationEntry3 : ((GroupEntry) pipelineEntry).mUnmodifiableChildren) {
                        NotifViewController rowController3 = shadeViewManager$viewRenderer$1.getRowController(notificationEntry3);
                        ArrayList arrayList3 = (ArrayList) this.onAfterRenderEntryListeners;
                        int size3 = arrayList3.size();
                        int i3 = 0;
                        while (i3 < size3) {
                            Object obj3 = arrayList3.get(i3);
                            i3++;
                            ((OnAfterRenderEntryListener) obj3).onAfterRenderEntry(notificationEntry3, rowController3);
                        }
                    }
                }
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

    public final void dispatchOnAfterRenderGroups(ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1, List list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderStageManager.dispatchOnAfterRenderGroups");
        }
        try {
            if (((ArrayList) this.onAfterRenderGroupListeners).isEmpty()) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.RenderStageManager$dispatchOnAfterRenderGroups$lambda$6$$inlined$filterIsInstance$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof GroupEntry);
                }
            }).new AnonymousClass1();
            while (anonymousClass1.hasNext()) {
                GroupEntry groupEntry = (GroupEntry) anonymousClass1.next();
                NotifViewController groupController = shadeViewManager$viewRenderer$1.getGroupController(groupEntry);
                ArrayList arrayList = (ArrayList) this.onAfterRenderGroupListeners;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((OnAfterRenderGroupListener) obj).onAfterRenderGroup(groupEntry, groupController);
                }
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

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.viewRenderer, "viewRenderer");
        pipelineDumper.dump(this.onAfterRenderListListeners, "onAfterRenderListListeners");
        pipelineDumper.dump(this.onAfterRenderGroupListeners, "onAfterRenderGroupListeners");
        pipelineDumper.dump(this.onAfterRenderEntryListeners, "onAfterRenderEntryListeners");
    }
}
