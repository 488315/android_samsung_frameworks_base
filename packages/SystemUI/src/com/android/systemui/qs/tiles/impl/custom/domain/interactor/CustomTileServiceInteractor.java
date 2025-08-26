package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.external.CustomTileInterface;
import com.android.systemui.qs.external.TileServiceManager;
import com.android.systemui.qs.external.TileServices;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandlerImpl;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class CustomTileServiceInteractor {
    public final ActivityStarter activityStarter;
    public UserHandle currentUser;
    public final CustomTileInteractor customTileInteractor;
    public StandaloneCoroutine destructionJob;
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
            CustomTileRepositoryImpl.TileWithUser currentTileWithUser = ((CustomTileRepositoryImpl) customTileInteractor.customTileRepository).getCurrentTileWithUser();
            if (currentTileWithUser == null) {
                throw new IllegalStateException("Tile is not set");
            }
            Tile tile = Intrinsics.areEqual(currentTileWithUser.user, userHandle) ? currentTileWithUser.tile : null;
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
            CoroutineTracingKt.launchTraced$default(CustomTileServiceInteractor.this.tileScope, null, null, new CustomTileServiceInteractor$ReceivingInterface$refreshState$1(this, null), 7);
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
                    customTileServiceInteractor.getTileServiceManager().mStateManager.onUnlockComplete();
                }
            });
        }

        @Override // com.android.systemui.qs.external.CustomTileInterface
        public final void updateTileState(Tile tile, int i) {
            CustomTileServiceInteractor.this.customTileInteractor.tileUpdates.tryEmit(tile);
            this.mutableCallingAppIds.updateState(null, Integer.valueOf(i));
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnClick$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CustomTileServiceInteractor.this.bindOnClick(this);
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$bindOnStart$1, reason: invalid class name and case insensitive filesystem */
    final class C10171 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C10171(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CustomTileServiceInteractor.this.bindOnStart(this);
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object bindOnClick(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        CustomTileServiceInteractor customTileServiceInteractor;
        RemoteException e;
        TileServiceManager tileServiceManager;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            try {
                TileServiceManager tileServiceManager2 = getTileServiceManager();
                CustomTileInteractor customTileInteractor = this.customTileInteractor;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = tileServiceManager2;
                anonymousClass1.label = 1;
                Object objIsTileActive = ((CustomTileRepositoryImpl) customTileInteractor.customTileRepository).isTileActive(anonymousClass1);
                if (objIsTileActive == coroutineSingletons) {
                    return coroutineSingletons;
                }
                customTileServiceInteractor = this;
                tileServiceManager = tileServiceManager2;
                obj = objIsTileActive;
            } catch (RemoteException e2) {
                customTileServiceInteractor = this;
                e = e2;
                customTileServiceInteractor.qsTileLogger.logError(customTileServiceInteractor.tileSpec, "Binding to the service on click failed", e);
                return Unit.INSTANCE;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            tileServiceManager = (TileServiceManager) anonymousClass1.L$1;
            customTileServiceInteractor = (CustomTileServiceInteractor) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (RemoteException e3) {
                e = e3;
                customTileServiceInteractor.qsTileLogger.logError(customTileServiceInteractor.tileSpec, "Binding to the service on click failed", e);
                return Unit.INSTANCE;
            }
        }
        if (((Boolean) obj).booleanValue()) {
            tileServiceManager.setBindRequested(true);
            customTileServiceInteractor.getTileServiceManager().mStateManager.onStartListening();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005f A[Catch: RemoteException -> 0x002f, TryCatch #1 {RemoteException -> 0x002f, blocks: (B:12:0x002b, B:23:0x0057, B:25:0x005f, B:26:0x0063), top: B:36:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0063 A[Catch: RemoteException -> 0x002f, TRY_LEAVE, TryCatch #1 {RemoteException -> 0x002f, blocks: (B:12:0x002b, B:23:0x0057, B:25:0x005f, B:26:0x0063), top: B:36:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object bindOnStart(ContinuationImpl continuationImpl) {
        C10171 c10171;
        CustomTileServiceInteractor customTileServiceInteractor;
        RemoteException e;
        TileServiceManager tileServiceManager;
        if (continuationImpl instanceof C10171) {
            c10171 = (C10171) continuationImpl;
            int i = c10171.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c10171.label = i - Integer.MIN_VALUE;
            } else {
                c10171 = new C10171(continuationImpl);
            }
        }
        Object obj = c10171.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c10171.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            try {
                TileServiceManager tileServiceManager2 = getTileServiceManager();
                CustomTileInteractor customTileInteractor = this.customTileInteractor;
                c10171.L$0 = this;
                c10171.L$1 = tileServiceManager2;
                c10171.label = 1;
                Object objIsTileActive = ((CustomTileRepositoryImpl) customTileInteractor.customTileRepository).isTileActive(c10171);
                if (objIsTileActive == coroutineSingletons) {
                    return coroutineSingletons;
                }
                customTileServiceInteractor = this;
                tileServiceManager = tileServiceManager2;
                obj = objIsTileActive;
                if (((Boolean) obj).booleanValue()) {
                }
            } catch (RemoteException e2) {
                customTileServiceInteractor = this;
                e = e2;
                customTileServiceInteractor.qsTileLogger.logError(customTileServiceInteractor.tileSpec, "Binding to the service failed", e);
                return Unit.INSTANCE;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            tileServiceManager = (TileServiceManager) c10171.L$1;
            customTileServiceInteractor = (CustomTileServiceInteractor) c10171.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                if (((Boolean) obj).booleanValue()) {
                    tileServiceManager.setBindRequested(true);
                    customTileServiceInteractor.getTileServiceManager().mStateManager.onStartListening();
                } else {
                    tileServiceManager.mPendingBind = false;
                }
            } catch (RemoteException e3) {
                e = e3;
                customTileServiceInteractor.qsTileLogger.logError(customTileServiceInteractor.tileSpec, "Binding to the service failed", e);
                return Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }

    public final TileServiceManager getTileServiceManager() {
        TileServiceManager tileWrapper;
        synchronized (this.tileServices) {
            this.qsTileLogger.logInfo("getTileServiceManager called", this.tileSpec);
            tileWrapper = this.tileServiceManager;
            if (tileWrapper == null) {
                tileWrapper = this.tileServices.getTileWrapper(this.tileReceivingInterface);
                this.destructionJob = CoroutineTracingKt.launchTraced$default(this.tileScope, null, null, new CustomTileServiceInteractor$createDestructionJob$1(this, null), 7);
                this.tileServiceManager = tileWrapper;
            }
        }
        return tileWrapper;
    }
}
