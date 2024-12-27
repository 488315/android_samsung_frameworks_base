package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.external.CustomTileInterface;
import com.android.systemui.qs.external.TileServiceManager;
import com.android.systemui.qs.external.TileServices;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandlerImpl;
import com.android.systemui.qs.tiles.base.logging.QSTileLogger;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class CustomTileServiceInteractor {
    public final ActivityStarter activityStarter;
    public UserHandle currentUser;
    public final CustomTileInteractor customTileInteractor;
    public Job destructionJob;
    public final QSTileLogger qsTileLogger;
    public final ReceivingInterface tileReceivingInterface = new ReceivingInterface();
    public final CoroutineScope tileScope;
    public TileServiceManager tileServiceManager;
    public final TileServices tileServices;
    public final TileSpec.CustomTileSpec tileSpec;
    public final Lazy userActionInteractor;

    public final class ReceivingInterface implements CustomTileInterface {
        public final ComponentName component;
        public final StateFlowImpl mutableCallingAppIds = StateFlowKt.MutableStateFlow(-1);
        public final SharedFlowImpl mutableRefreshEvents = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);

        public ReceivingInterface() {
            this.component = CustomTileServiceInteractor.this.tileSpec.componentName;
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final ComponentName getComponent() {
            return this.component;
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final Tile getQsTile() {
            CustomTileServiceInteractor customTileServiceInteractor = CustomTileServiceInteractor.this;
            CustomTileInteractor customTileInteractor = customTileServiceInteractor.customTileInteractor;
            UserHandle userHandle = customTileServiceInteractor.currentUser;
            CustomTileRepositoryImpl.TileWithUser tileWithUser = (CustomTileRepositoryImpl.TileWithUser) CollectionsKt___CollectionsKt.lastOrNull(((CustomTileRepositoryImpl) customTileInteractor.customTileRepository).tileWithUserState.getReplayCache());
            if (tileWithUser == null) {
                throw new IllegalStateException("Tile is not set");
            }
            Tile tile = Intrinsics.areEqual(tileWithUser.user, userHandle) ? tileWithUser.tile : null;
            if (tile != null) {
                return tile;
            }
            throw new IllegalStateException("Attempt to get a tile for a wrong user");
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final String getTileSpec() {
            return CustomTileServiceInteractor.this.tileSpec.spec;
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final int getUser() {
            return CustomTileServiceInteractor.this.currentUser.getIdentifier();
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final void onDialogHidden() {
            CustomTileUserActionInteractor customTileUserActionInteractor = (CustomTileUserActionInteractor) CustomTileServiceInteractor.this.userActionInteractor.get();
            synchronized (customTileUserActionInteractor.token) {
                customTileUserActionInteractor.isShowingDialog = false;
                Unit unit = Unit.INSTANCE;
            }
            customTileUserActionInteractor.revokeToken(true);
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final void onDialogShown() {
            CustomTileUserActionInteractor customTileUserActionInteractor = (CustomTileUserActionInteractor) CustomTileServiceInteractor.this.userActionInteractor.get();
            synchronized (customTileUserActionInteractor.token) {
                customTileUserActionInteractor.isShowingDialog = true;
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final void refreshState() {
            BuildersKt.launch$default(CustomTileServiceInteractor.this.tileScope, null, null, new CustomTileServiceInteractor$ReceivingInterface$refreshState$1(this, null), 3);
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final void startActivityAndCollapse(PendingIntent pendingIntent) {
            CustomTileUserActionInteractor customTileUserActionInteractor = (CustomTileUserActionInteractor) CustomTileServiceInteractor.this.userActionInteractor.get();
            if (customTileUserActionInteractor.isTokenGranted) {
                ((QSTileIntentUserInputHandlerImpl) customTileUserActionInteractor.qsTileIntentUserInputHandler).handle((Expandable) customTileUserActionInteractor.lastClickedExpandable.getAndSet(null), pendingIntent, false);
            }
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final void startUnlockAndRun() {
            final CustomTileServiceInteractor customTileServiceInteractor = CustomTileServiceInteractor.this;
            customTileServiceInteractor.activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$ReceivingInterface$startUnlockAndRun$1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomTileServiceInteractor.this.getTileServiceManager().mStateManager.onUnlockComplete();
                }
            });
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final void updateTileState(Tile tile, int i) {
            CustomTileServiceInteractor.this.customTileInteractor.tileUpdates.tryEmit(tile);
            this.mutableCallingAppIds.updateState(null, Integer.valueOf(i));
        }
    }

    public CustomTileServiceInteractor(TileSpec.CustomTileSpec customTileSpec, ActivityStarter activityStarter, Lazy lazy, CustomTileInteractor customTileInteractor, UserRepository userRepository, QSTileLogger qSTileLogger, TileServices tileServices, CoroutineScope coroutineScope) {
        this.tileSpec = customTileSpec;
        this.activityStarter = activityStarter;
        this.userActionInteractor = lazy;
        this.customTileInteractor = customTileInteractor;
        this.qsTileLogger = qSTileLogger;
        this.tileServices = tileServices;
        this.tileScope = coroutineScope;
        this.currentUser = ((UserRepositoryImpl) userRepository).getSelectedUserInfo().getUserHandle();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005f A[Catch: RemoteException -> 0x002f, TRY_LEAVE, TryCatch #1 {RemoteException -> 0x002f, blocks: (B:11:0x002b, B:12:0x0057, B:14:0x005f), top: B:10:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object bindOnClick(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnClick$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnClick$1 r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnClick$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnClick$1 r0 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnClick$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r5 = r0.L$1
            com.android.systemui.qs.external.TileServiceManager r5 = (com.android.systemui.qs.external.TileServiceManager) r5
            java.lang.Object r0 = r0.L$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor) r0
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: android.os.RemoteException -> L2f
            goto L57
        L2f:
            r5 = move-exception
            goto L71
        L31:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L39:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.qs.external.TileServiceManager r6 = r5.getTileServiceManager()     // Catch: android.os.RemoteException -> L6f
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor r2 = r5.customTileInteractor     // Catch: android.os.RemoteException -> L6f
            r0.L$0 = r5     // Catch: android.os.RemoteException -> L6f
            r0.L$1 = r6     // Catch: android.os.RemoteException -> L6f
            r0.label = r3     // Catch: android.os.RemoteException -> L6f
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository r2 = r2.customTileRepository     // Catch: android.os.RemoteException -> L6f
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r2 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl) r2     // Catch: android.os.RemoteException -> L6f
            java.lang.Object r0 = r2.isTileActive(r0)     // Catch: android.os.RemoteException -> L6f
            if (r0 != r1) goto L53
            return r1
        L53:
            r4 = r0
            r0 = r5
            r5 = r6
            r6 = r4
        L57:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: android.os.RemoteException -> L2f
            boolean r6 = r6.booleanValue()     // Catch: android.os.RemoteException -> L2f
            if (r6 == 0) goto L7a
            r5.setBindRequested(r3)     // Catch: android.os.RemoteException -> L2f
            com.android.systemui.qs.external.TileServiceManager r5 = r0.getTileServiceManager()     // Catch: android.os.RemoteException -> L2f
            com.android.systemui.qs.external.TileLifecycleManager r5 = r5.mStateManager     // Catch: android.os.RemoteException -> L2f
            r5.onStartListening()     // Catch: android.os.RemoteException -> L2f
            goto L7a
        L6c:
            r0 = r5
            r5 = r6
            goto L71
        L6f:
            r6 = move-exception
            goto L6c
        L71:
            com.android.systemui.qs.tiles.base.logging.QSTileLogger r6 = r0.qsTileLogger
            com.android.systemui.qs.pipeline.shared.TileSpec$CustomTileSpec r0 = r0.tileSpec
            java.lang.String r1 = "Binding to the service on click failed"
            r6.logError(r0, r1, r5)
        L7a:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor.bindOnClick(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005f A[Catch: RemoteException -> 0x002f, TryCatch #1 {RemoteException -> 0x002f, blocks: (B:11:0x002b, B:12:0x0057, B:14:0x005f, B:18:0x0063), top: B:10:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0063 A[Catch: RemoteException -> 0x002f, TRY_LEAVE, TryCatch #1 {RemoteException -> 0x002f, blocks: (B:11:0x002b, B:12:0x0057, B:14:0x005f, B:18:0x0063), top: B:10:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object bindOnStart(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnStart$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnStart$1 r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnStart$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnStart$1 r0 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnStart$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r5 = r0.L$1
            com.android.systemui.qs.external.TileServiceManager r5 = (com.android.systemui.qs.external.TileServiceManager) r5
            java.lang.Object r0 = r0.L$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor) r0
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: android.os.RemoteException -> L2f
            goto L57
        L2f:
            r5 = move-exception
            goto L75
        L31:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L39:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.qs.external.TileServiceManager r6 = r5.getTileServiceManager()     // Catch: android.os.RemoteException -> L73
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor r2 = r5.customTileInteractor     // Catch: android.os.RemoteException -> L73
            r0.L$0 = r5     // Catch: android.os.RemoteException -> L73
            r0.L$1 = r6     // Catch: android.os.RemoteException -> L73
            r0.label = r3     // Catch: android.os.RemoteException -> L73
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository r2 = r2.customTileRepository     // Catch: android.os.RemoteException -> L73
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r2 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl) r2     // Catch: android.os.RemoteException -> L73
            java.lang.Object r0 = r2.isTileActive(r0)     // Catch: android.os.RemoteException -> L73
            if (r0 != r1) goto L53
            return r1
        L53:
            r4 = r0
            r0 = r5
            r5 = r6
            r6 = r4
        L57:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: android.os.RemoteException -> L2f
            boolean r6 = r6.booleanValue()     // Catch: android.os.RemoteException -> L2f
            if (r6 == 0) goto L63
            r6 = 0
            r5.mPendingBind = r6     // Catch: android.os.RemoteException -> L2f
            goto L7e
        L63:
            r5.setBindRequested(r3)     // Catch: android.os.RemoteException -> L2f
            com.android.systemui.qs.external.TileServiceManager r5 = r0.getTileServiceManager()     // Catch: android.os.RemoteException -> L2f
            com.android.systemui.qs.external.TileLifecycleManager r5 = r5.mStateManager     // Catch: android.os.RemoteException -> L2f
            r5.onStartListening()     // Catch: android.os.RemoteException -> L2f
            goto L7e
        L70:
            r0 = r5
            r5 = r6
            goto L75
        L73:
            r6 = move-exception
            goto L70
        L75:
            com.android.systemui.qs.tiles.base.logging.QSTileLogger r6 = r0.qsTileLogger
            com.android.systemui.qs.pipeline.shared.TileSpec$CustomTileSpec r0 = r0.tileSpec
            java.lang.String r1 = "Binding to the service failed"
            r6.logError(r0, r1, r5)
        L7e:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor.bindOnStart(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final TileServiceManager getTileServiceManager() {
        TileServiceManager tileServiceManager;
        synchronized (this.tileServices) {
            tileServiceManager = this.tileServiceManager;
            if (tileServiceManager == null) {
                tileServiceManager = this.tileServices.getTileWrapper(this.tileReceivingInterface);
                this.destructionJob = BuildersKt.launch$default(this.tileScope, null, null, new CustomTileServiceInteractor$createDestructionJob$1(this, null), 3);
                this.tileServiceManager = tileServiceManager;
            }
        }
        return tileServiceManager;
    }
}
