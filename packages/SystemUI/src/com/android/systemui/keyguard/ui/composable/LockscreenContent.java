package com.android.systemui.keyguard.ui.composable;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.ui.composable.blueprint.ComposableLockscreenSceneBlueprint;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel;
import java.util.LinkedHashMap;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenContent {
    public final Lazy blueprintByBlueprintId$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.ui.composable.LockscreenContent$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Set set = LockscreenContent.this.blueprints;
            int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
            if (mapCapacity < 16) {
                mapCapacity = 16;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
            for (Object obj : set) {
                linkedHashMap.put(((ComposableLockscreenSceneBlueprint) obj).getId(), obj);
            }
            return linkedHashMap;
        }
    });
    public final Set blueprints;
    public final KeyguardClockInteractor clockInteractor;
    public final InteractionJankMonitor interactionJankMonitor;
    public final NotificationLockscreenScrimViewModel.Factory notificationScrimViewModelFactory;
    public final LockscreenContentViewModel.Factory viewModelFactory;

    public LockscreenContent(LockscreenContentViewModel.Factory factory, NotificationLockscreenScrimViewModel.Factory factory2, Set<ComposableLockscreenSceneBlueprint> set, KeyguardClockInteractor keyguardClockInteractor, InteractionJankMonitor interactionJankMonitor) {
        this.viewModelFactory = factory;
        this.notificationScrimViewModelFactory = factory2;
        this.blueprints = set;
        this.clockInteractor = keyguardClockInteractor;
        this.interactionJankMonitor = interactionJankMonitor;
    }
}
