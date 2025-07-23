package com.android.systemui.statusbar.events;

import android.content.Context;
import android.provider.DeviceConfig;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.privacy.PrivacyChipBuilder;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SystemEventCoordinator {
    public final Context context;
    public final PrivacyItemController privacyController;
    public final SystemEventCoordinator$privacyStateListener$1 privacyStateListener;
    public SystemStatusAnimationSchedulerImpl scheduler;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.events.SystemEventCoordinator$privacyStateListener$1] */
    public SystemEventCoordinator(SystemClock systemClock, BatteryController batteryController, PrivacyItemController privacyItemController, ShadeExpansionStateManager shadeExpansionStateManager, Context context, CoroutineScope coroutineScope, ConnectedDisplayInteractor connectedDisplayInteractor) {
        this.systemClock = systemClock;
        this.privacyController = privacyItemController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.context = context;
        connectedDisplayInteractor.getClass();
        new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.events.SystemEventCoordinator$batteryStateListener$1
            public boolean plugged;
            public boolean stateKnown;

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                boolean z3 = this.stateKnown;
                SystemEventCoordinator systemEventCoordinator = SystemEventCoordinator.this;
                if (!z3) {
                    this.stateKnown = true;
                    this.plugged = z;
                    if (z && systemEventCoordinator.shadeExpansionStateManager.isClosed()) {
                        SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = systemEventCoordinator.scheduler;
                        (systemStatusAnimationSchedulerImpl != null ? systemStatusAnimationSchedulerImpl : null).onStatusEvent(new BatteryEvent(i, 0));
                        return;
                    }
                    return;
                }
                if (this.plugged != z) {
                    this.plugged = z;
                    if (z && systemEventCoordinator.shadeExpansionStateManager.isClosed()) {
                        SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = systemEventCoordinator.scheduler;
                        (systemStatusAnimationSchedulerImpl2 != null ? systemStatusAnimationSchedulerImpl2 : null).onStatusEvent(new BatteryEvent(i, 0));
                    }
                }
            }
        };
        this.privacyStateListener = new PrivacyItemController.Callback() { // from class: com.android.systemui.statusbar.events.SystemEventCoordinator$privacyStateListener$1
            public List currentPrivacyItems;
            public List previousPrivacyItems;
            public long timeLastEmpty;

            {
                EmptyList emptyList = EmptyList.INSTANCE;
                this.currentPrivacyItems = emptyList;
                this.previousPrivacyItems = emptyList;
                this.timeLastEmpty = SystemEventCoordinator.this.systemClock.elapsedRealtime();
            }

            public static boolean uniqueItemsMatch(List list, List list2) {
                List<PrivacyItem> list3 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                for (PrivacyItem privacyItem : list3) {
                    arrayList.add(new Pair(Integer.valueOf(privacyItem.application.uid), privacyItem.privacyType.getPermGroupName()));
                }
                Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                List<PrivacyItem> list4 = list2;
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                for (PrivacyItem privacyItem2 : list4) {
                    arrayList2.add(new Pair(Integer.valueOf(privacyItem2.application.uid), privacyItem2.privacyType.getPermGroupName()));
                }
                return Intrinsics.areEqual(set, CollectionsKt___CollectionsKt.toSet(arrayList2));
            }

            @Override // com.android.systemui.privacy.PrivacyItemController.Callback
            public final void onPrivacyItemsChanged(List list) {
                if (BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION) {
                    List list2 = list;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        Iterator it = list2.iterator();
                        while (it.hasNext()) {
                            if (((PrivacyItem) it.next()).privacyType == PrivacyType.TYPE_LOCATION) {
                                ArrayList arrayList = new ArrayList();
                                for (Object obj : list2) {
                                    if (((PrivacyItem) obj).privacyType != PrivacyType.TYPE_LOCATION) {
                                        arrayList.add(obj);
                                    }
                                }
                                onPrivacyItemsChanged(arrayList);
                                return;
                            }
                        }
                    }
                }
                if (uniqueItemsMatch(list, this.currentPrivacyItems)) {
                    return;
                }
                boolean isEmpty = list.isEmpty();
                SystemEventCoordinator systemEventCoordinator = SystemEventCoordinator.this;
                if (isEmpty) {
                    this.previousPrivacyItems = this.currentPrivacyItems;
                    this.timeLastEmpty = systemEventCoordinator.systemClock.elapsedRealtime();
                }
                boolean z = false;
                boolean z2 = this.currentPrivacyItems.size() < list.size();
                this.currentPrivacyItems = list;
                if (list.isEmpty()) {
                    SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = systemEventCoordinator.scheduler;
                    (systemStatusAnimationSchedulerImpl != null ? systemStatusAnimationSchedulerImpl : null).removePersistentDot(true);
                    return;
                }
                if (DeviceConfig.getBoolean("privacy", "privacy_chip_animation_enabled", systemEventCoordinator.context.getResources().getBoolean(R.bool.config_enablePrivacyChipAnimation)) && ((!uniqueItemsMatch(this.currentPrivacyItems, this.previousPrivacyItems) || systemEventCoordinator.systemClock.elapsedRealtime() - this.timeLastEmpty >= 3000) && z2)) {
                    z = true;
                }
                PrivacyEvent privacyEvent = new PrivacyEvent(z);
                List list3 = systemEventCoordinator.privacyStateListener.currentPrivacyItems;
                privacyEvent.privacyItems = list3;
                privacyEvent.contentDescription = systemEventCoordinator.context.getString(R.string.ongoing_privacy_chip_content_multiple_apps, new PrivacyChipBuilder(systemEventCoordinator.context, list3).joinTypes());
                SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = systemEventCoordinator.scheduler;
                (systemStatusAnimationSchedulerImpl2 != null ? systemStatusAnimationSchedulerImpl2 : null).onStatusEvent(privacyEvent);
            }
        };
    }
}
