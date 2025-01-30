package com.android.systemui.statusbar.events;

import android.content.Context;
import android.provider.DeviceConfig;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.privacy.PrivacyChipBuilder;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemEventCoordinator {
    public final Context context;
    public final FeatureFlags featureFlags;
    public final PrivacyItemController privacyController;
    public final SystemEventCoordinator$privacyStateListener$1 privacyStateListener;
    public SystemStatusAnimationScheduler scheduler;
    public final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.events.SystemEventCoordinator$privacyStateListener$1] */
    public SystemEventCoordinator(SystemClock systemClock, BatteryController batteryController, PrivacyItemController privacyItemController, Context context, FeatureFlags featureFlags) {
        this.systemClock = systemClock;
        this.privacyController = privacyItemController;
        this.context = context;
        this.featureFlags = featureFlags;
        new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.events.SystemEventCoordinator$batteryStateListener$1
            public boolean plugged;
            public boolean stateKnown;

            public final void notifyListeners(int i) {
                if (this.plugged) {
                    SystemEventCoordinator systemEventCoordinator = SystemEventCoordinator.this;
                    systemEventCoordinator.getClass();
                    Flags.INSTANCE.getClass();
                    if (((FeatureFlagsRelease) systemEventCoordinator.featureFlags).isEnabled(Flags.PLUG_IN_STATUS_BAR_CHIP)) {
                        SystemStatusAnimationScheduler systemStatusAnimationScheduler = systemEventCoordinator.scheduler;
                        if (systemStatusAnimationScheduler == null) {
                            systemStatusAnimationScheduler = null;
                        }
                        ((SystemStatusAnimationSchedulerImpl) systemStatusAnimationScheduler).onStatusEvent(new BatteryEvent(i));
                    }
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                if (!this.stateKnown) {
                    this.stateKnown = true;
                    this.plugged = z;
                    notifyListeners(i);
                } else if (this.plugged != z) {
                    this.plugged = z;
                    notifyListeners(i);
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
                ((SystemClockImpl) SystemEventCoordinator.this.systemClock).getClass();
                this.timeLastEmpty = android.os.SystemClock.elapsedRealtime();
            }

            public static boolean uniqueItemsMatch(List list, List list2) {
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    PrivacyItem privacyItem = (PrivacyItem) it.next();
                    arrayList.add(new Pair(Integer.valueOf(privacyItem.application.uid), privacyItem.privacyType.getPermGroupName()));
                }
                Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    PrivacyItem privacyItem2 = (PrivacyItem) it2.next();
                    arrayList2.add(new Pair(Integer.valueOf(privacyItem2.application.uid), privacyItem2.privacyType.getPermGroupName()));
                }
                return Intrinsics.areEqual(set, CollectionsKt___CollectionsKt.toSet(arrayList2));
            }

            /* JADX WARN: Code restructure failed: missing block: B:23:0x0070, code lost:
            
                if ((android.os.SystemClock.elapsedRealtime() - r7.timeLastEmpty) >= 3000) goto L25;
             */
            /* JADX WARN: Code restructure failed: missing block: B:24:0x0072, code lost:
            
                if (r0 != false) goto L28;
             */
            @Override // com.android.systemui.privacy.PrivacyItemController.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onPrivacyItemsChanged(List list) {
                if (uniqueItemsMatch(list, this.currentPrivacyItems)) {
                    return;
                }
                boolean isEmpty = list.isEmpty();
                SystemEventCoordinator systemEventCoordinator = SystemEventCoordinator.this;
                if (isEmpty) {
                    this.previousPrivacyItems = this.currentPrivacyItems;
                    ((SystemClockImpl) systemEventCoordinator.systemClock).getClass();
                    this.timeLastEmpty = android.os.SystemClock.elapsedRealtime();
                }
                boolean z = true;
                boolean z2 = this.currentPrivacyItems.size() < list.size();
                this.currentPrivacyItems = list;
                if (list.isEmpty()) {
                    SystemStatusAnimationScheduler systemStatusAnimationScheduler = systemEventCoordinator.scheduler;
                    ((SystemStatusAnimationSchedulerImpl) (systemStatusAnimationScheduler != null ? systemStatusAnimationScheduler : null)).removePersistentDot(true);
                    return;
                }
                if (DeviceConfig.getBoolean("privacy", "privacy_chip_animation_enabled", true)) {
                    if (uniqueItemsMatch(this.currentPrivacyItems, this.previousPrivacyItems)) {
                        ((SystemClockImpl) systemEventCoordinator.systemClock).getClass();
                    }
                }
                z = false;
                systemEventCoordinator.getClass();
                PrivacyEvent privacyEvent = new PrivacyEvent(z);
                List list2 = systemEventCoordinator.privacyStateListener.currentPrivacyItems;
                privacyEvent.privacyItems = list2;
                Context context2 = systemEventCoordinator.context;
                privacyEvent.contentDescription = context2.getString(R.string.ongoing_privacy_chip_content_multiple_apps, new PrivacyChipBuilder(context2, list2).joinTypes());
                SystemStatusAnimationScheduler systemStatusAnimationScheduler2 = systemEventCoordinator.scheduler;
                ((SystemStatusAnimationSchedulerImpl) (systemStatusAnimationScheduler2 != null ? systemStatusAnimationScheduler2 : null)).onStatusEvent(privacyEvent);
            }
        };
    }
}
