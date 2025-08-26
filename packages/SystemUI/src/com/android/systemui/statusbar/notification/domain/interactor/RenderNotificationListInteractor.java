package com.android.systemui.statusbar.notification.domain.interactor;

import android.content.Context;
import android.os.Trace;
import android.util.ArrayMap;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationGroupModel;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class RenderNotificationListInteractor {
    public final Context context;
    public final ActiveNotificationListRepository repository;
    public final SectionStyleProvider sectionStyleProvider;

    public RenderNotificationListInteractor(ActiveNotificationListRepository activeNotificationListRepository, SectionStyleProvider sectionStyleProvider, Context context) {
        this.repository = activeNotificationListRepository;
        this.sectionStyleProvider = sectionStyleProvider;
        this.context = context;
    }

    public final void setRenderedList(final List list) {
        Object value;
        ActiveNotificationsStore.Builder builder;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderNotificationListInteractor.setRenderedList");
        }
        try {
            StateFlowImpl stateFlowImpl = this.repository.activeNotifications;
            do {
                value = stateFlowImpl.getValue();
                SectionStyleProvider sectionStyleProvider = this.sectionStyleProvider;
                Context context = this.context;
                Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor$$ExternalSyntheticLambda0
                    /* JADX WARN: Removed duplicated region for block: B:25:0x007f  */
                    /* JADX WARN: Removed duplicated region for block: B:29:0x00ae A[LOOP:2: B:27:0x00a8->B:29:0x00ae, LOOP_END] */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object mo781invoke(Object obj) {
                        List<PipelineEntry> list2 = list;
                        ActiveNotificationsStoreBuilder activeNotificationsStoreBuilder = (ActiveNotificationsStoreBuilder) obj;
                        for (PipelineEntry pipelineEntry : list2) {
                            activeNotificationsStoreBuilder.getClass();
                            boolean z = pipelineEntry instanceof GroupEntry;
                            ActiveNotificationsStore.Builder builder2 = activeNotificationsStoreBuilder.builder;
                            if (z) {
                                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                                NotificationEntry notificationEntry = groupEntry.mSummary;
                                if (notificationEntry != null) {
                                    ActiveNotificationModel model = activeNotificationsStoreBuilder.toModel(notificationEntry);
                                    List<NotificationEntry> list3 = groupEntry.mUnmodifiableChildren;
                                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                                    for (NotificationEntry notificationEntry2 : list3) {
                                        notificationEntry2.getClass();
                                        arrayList.add(activeNotificationsStoreBuilder.toModel(notificationEntry2));
                                    }
                                    Map map = activeNotificationsStoreBuilder.existingModels.groups;
                                    String str = groupEntry.mKey;
                                    ActiveNotificationGroupModel activeNotificationGroupModel = (ActiveNotificationGroupModel) map.get(str);
                                    if (activeNotificationGroupModel == null) {
                                        activeNotificationGroupModel = new ActiveNotificationGroupModel(str, model, arrayList);
                                        List list4 = builder2.renderList;
                                        String str2 = activeNotificationGroupModel.key;
                                        ((ArrayList) list4).add(new ActiveNotificationsStore.Key.Group(str2));
                                        builder2.groups.put(str2, activeNotificationGroupModel);
                                        Map map2 = builder2.individuals;
                                        ActiveNotificationModel activeNotificationModel = activeNotificationGroupModel.summary;
                                        map2.put(activeNotificationModel.key, activeNotificationModel);
                                        for (ActiveNotificationModel activeNotificationModel2 : activeNotificationGroupModel.children) {
                                            builder2.individuals.put(activeNotificationModel2.key, activeNotificationModel2);
                                        }
                                    } else {
                                        if (!Intrinsics.areEqual(str, activeNotificationGroupModel.key) || !model.equals(activeNotificationGroupModel.summary) || !arrayList.equals(activeNotificationGroupModel.children)) {
                                            activeNotificationGroupModel = null;
                                        }
                                        if (activeNotificationGroupModel == null) {
                                        }
                                        List list42 = builder2.renderList;
                                        String str22 = activeNotificationGroupModel.key;
                                        ((ArrayList) list42).add(new ActiveNotificationsStore.Key.Group(str22));
                                        builder2.groups.put(str22, activeNotificationGroupModel);
                                        Map map22 = builder2.individuals;
                                        ActiveNotificationModel activeNotificationModel3 = activeNotificationGroupModel.summary;
                                        map22.put(activeNotificationModel3.key, activeNotificationModel3);
                                        while (r1.hasNext()) {
                                        }
                                    }
                                }
                            } else {
                                NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
                                if (representativeEntry != null) {
                                    ActiveNotificationModel model2 = activeNotificationsStoreBuilder.toModel(representativeEntry);
                                    List list5 = builder2.renderList;
                                    String str3 = model2.key;
                                    ((ArrayList) list5).add(new ActiveNotificationsStore.Key.Individual(str3));
                                    builder2.individuals.put(str3, model2);
                                }
                            }
                        }
                        activeNotificationsStoreBuilder.getClass();
                        ArrayMap arrayMap = new ArrayMap();
                        for (PipelineEntry pipelineEntry2 : list2) {
                            if (pipelineEntry2 instanceof NotificationEntry) {
                                NotificationEntry notificationEntry3 = (NotificationEntry) pipelineEntry2;
                                notificationEntry3.getClass();
                                arrayMap.put(notificationEntry3.mKey, Integer.valueOf(notificationEntry3.mRanking.getRank()));
                            } else if (pipelineEntry2 instanceof GroupEntry) {
                                GroupEntry groupEntry2 = (GroupEntry) pipelineEntry2;
                                NotificationEntry notificationEntry4 = groupEntry2.mSummary;
                                if (notificationEntry4 != null) {
                                    arrayMap.put(notificationEntry4.mKey, Integer.valueOf(notificationEntry4.mRanking.getRank()));
                                }
                                for (NotificationEntry notificationEntry5 : groupEntry2.mUnmodifiableChildren) {
                                    arrayMap.put(notificationEntry5.mKey, Integer.valueOf(notificationEntry5.mRanking.getRank()));
                                }
                            }
                        }
                        ActiveNotificationsStore.Builder builder3 = activeNotificationsStoreBuilder.builder;
                        builder3.getClass();
                        builder3.rankingsMap = MapsKt__MapsKt.toMap(arrayMap);
                        return Unit.INSTANCE;
                    }
                };
                ActiveNotificationsStoreBuilder activeNotificationsStoreBuilder = new ActiveNotificationsStoreBuilder((ActiveNotificationsStore) value, sectionStyleProvider, context);
                function1.mo781invoke(activeNotificationsStoreBuilder);
                builder = activeNotificationsStoreBuilder.builder;
            } while (!stateFlowImpl.compareAndSet(value, new ActiveNotificationsStore(builder.groups, builder.individuals, builder.renderList, builder.rankingsMap)));
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
