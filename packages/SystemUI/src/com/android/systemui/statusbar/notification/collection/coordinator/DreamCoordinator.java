package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public final class DreamCoordinator implements Coordinator {
    public static final int $stable = 8;
    private boolean isLockscreenHostedDream;
    private boolean isOnKeyguard;
    private final KeyguardRepository keyguardRepository;
    private final CoroutineScope scope;
    private final SysuiStatusBarStateController statusBarStateController;
    private final DreamCoordinator$filter$1 filter = new DreamCoordinator$filter$1(this);
    private final DreamCoordinator$statusBarStateListener$1 statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$statusBarStateListener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onStateChanged(int i) {
            DreamCoordinator.this.recordStatusBarState(i);
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$statusBarStateListener$1] */
    public DreamCoordinator(SysuiStatusBarStateController sysuiStatusBarStateController, CoroutineScope coroutineScope, KeyguardRepository keyguardRepository) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.scope = coroutineScope;
        this.keyguardRepository = keyguardRepository;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object attachFilterOnDreamingStateChange(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$attachFilterOnDreamingStateChange$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$attachFilterOnDreamingStateChange$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$attachFilterOnDreamingStateChange$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$attachFilterOnDreamingStateChange$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$attachFilterOnDreamingStateChange$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L48
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.keyguard.data.repository.KeyguardRepository r5 = r4.keyguardRepository
            com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r5 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl) r5
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.isActiveDreamLockscreenHosted
            com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$attachFilterOnDreamingStateChange$2 r2 = new com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator$attachFilterOnDreamingStateChange$2
            r2.<init>()
            r0.label = r3
            kotlinx.coroutines.flow.StateFlow r4 = r5.$$delegate_0
            java.lang.Object r4 = r4.collect(r2, r0)
            if (r4 != r1) goto L48
            return r1
        L48:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.DreamCoordinator.attachFilterOnDreamingStateChange(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recordDreamingState(boolean z) {
        this.isLockscreenHostedDream = z;
        DreamCoordinator$filter$1 dreamCoordinator$filter$1 = this.filter;
        boolean isFiltering = dreamCoordinator$filter$1.isFiltering();
        dreamCoordinator$filter$1.setFiltering(dreamCoordinator$filter$1.this$0.isLockscreenHostedDream && dreamCoordinator$filter$1.this$0.isOnKeyguard);
        if (isFiltering != dreamCoordinator$filter$1.isFiltering()) {
            dreamCoordinator$filter$1.invalidateList("recordLockscreenHostedDreamState: " + z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recordStatusBarState(int i) {
        boolean z = false;
        this.isOnKeyguard = i == 1;
        DreamCoordinator$filter$1 dreamCoordinator$filter$1 = this.filter;
        boolean isFiltering = dreamCoordinator$filter$1.isFiltering();
        if (dreamCoordinator$filter$1.this$0.isLockscreenHostedDream && dreamCoordinator$filter$1.this$0.isOnKeyguard) {
            z = true;
        }
        dreamCoordinator$filter$1.setFiltering(z);
        if (isFiltering != dreamCoordinator$filter$1.isFiltering()) {
            dreamCoordinator$filter$1.invalidateList("recordStatusBarState: " + StatusBarState.toString(i));
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.filter);
        ((StatusBarStateControllerImpl) this.statusBarStateController).addCallback((StatusBarStateController.StateListener) this.statusBarStateListener);
        BuildersKt.launch$default(this.scope, null, null, new DreamCoordinator$attach$1(this, null), 3);
        recordStatusBarState(((StatusBarStateControllerImpl) this.statusBarStateController).mState);
    }
}
