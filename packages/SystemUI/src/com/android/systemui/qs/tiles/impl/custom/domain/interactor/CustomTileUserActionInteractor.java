package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.IWindowManager;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.logging.QSTileLogger;
import com.android.systemui.settings.DisplayTracker;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CustomTileUserActionInteractor implements QSTileUserActionInteractor {
    public final CoroutineContext backgroundContext;
    public final Context context;
    public final DisplayTracker displayTracker;
    public boolean isShowingDialog;
    public boolean isTokenGranted;
    public final QSTileIntentUserInputHandler qsTileIntentUserInputHandler;
    public final QSTileLogger qsTileLogger;
    public final CustomTileServiceInteractor serviceInteractor;
    public final TileSpec tileSpec;
    public final IWindowManager windowManager;
    public final IBinder token = new Binder();
    public final AtomicReference lastClickedExpandable = new AtomicReference();

    public CustomTileUserActionInteractor(Context context, TileSpec tileSpec, QSTileLogger qSTileLogger, IWindowManager iWindowManager, DisplayTracker displayTracker, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, CoroutineContext coroutineContext, CustomTileServiceInteractor customTileServiceInteractor) {
        this.context = context;
        this.tileSpec = tileSpec;
        this.qsTileLogger = qSTileLogger;
        this.windowManager = iWindowManager;
        this.displayTracker = displayTracker;
        this.qsTileIntentUserInputHandler = qSTileIntentUserInputHandler;
        this.backgroundContext = coroutineContext;
        this.serviceInteractor = customTileServiceInteractor;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:3|(7:5|6|7|(1:(2:10|11)(2:18|19))(4:20|45|27|(1:29))|(1:13)(1:17)|14|15))|41|6|7|(0)(0)|(0)(0)|14|15) */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0036, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0099, code lost:
    
        r8.qsTileLogger.logError(r8.tileSpec, "Failed to deliver click", r9);
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x007e A[Catch: RemoteException -> 0x0036, TryCatch #2 {RemoteException -> 0x0036, blocks: (B:11:0x0032, B:13:0x007e, B:17:0x0091, B:27:0x006b), top: B:7:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0091 A[Catch: RemoteException -> 0x0036, TRY_LEAVE, TryCatch #2 {RemoteException -> 0x0036, blocks: (B:11:0x0032, B:13:0x007e, B:17:0x0091, B:27:0x006b), top: B:7:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object click(com.android.systemui.animation.Expandable r9, android.app.PendingIntent r10, kotlin.coroutines.Continuation r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$click$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$click$1 r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$click$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$click$1 r0 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$click$1
            r0.<init>(r8, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 != r4) goto L38
            java.lang.Object r8 = r0.L$2
            r10 = r8
            android.app.PendingIntent r10 = (android.app.PendingIntent) r10
            java.lang.Object r8 = r0.L$1
            r9 = r8
            com.android.systemui.animation.Expandable r9 = (com.android.systemui.animation.Expandable) r9
            java.lang.Object r8 = r0.L$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor r8 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor) r8
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: android.os.RemoteException -> L36
            goto L7c
        L36:
            r9 = move-exception
            goto L99
        L38:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L40:
            kotlin.ResultKt.throwOnFailure(r11)
            android.os.IBinder r11 = r8.token
            monitor-enter(r11)
            boolean r2 = r8.isTokenGranted     // Catch: java.lang.Throwable -> L5a
            if (r2 != 0) goto L68
            android.view.IWindowManager r2 = r8.windowManager     // Catch: java.lang.Throwable -> L5a android.os.RemoteException -> L5c
            android.os.IBinder r5 = r8.token     // Catch: java.lang.Throwable -> L5a android.os.RemoteException -> L5c
            com.android.systemui.settings.DisplayTracker r6 = r8.displayTracker     // Catch: java.lang.Throwable -> L5a android.os.RemoteException -> L5c
            r6.getClass()     // Catch: java.lang.Throwable -> L5a android.os.RemoteException -> L5c
            r6 = 2035(0x7f3, float:2.852E-42)
            r7 = 0
            r2.addWindowToken(r5, r6, r3, r7)     // Catch: java.lang.Throwable -> L5a android.os.RemoteException -> L5c
            goto L66
        L5a:
            r8 = move-exception
            goto La5
        L5c:
            r2 = move-exception
            com.android.systemui.qs.tiles.base.logging.QSTileLogger r5 = r8.qsTileLogger     // Catch: java.lang.Throwable -> L5a
            com.android.systemui.qs.pipeline.shared.TileSpec r6 = r8.tileSpec     // Catch: java.lang.Throwable -> L5a
            java.lang.String r7 = "Failed to grant a window token"
            r5.logError(r6, r7, r2)     // Catch: java.lang.Throwable -> L5a
        L66:
            r8.isTokenGranted = r4     // Catch: java.lang.Throwable -> L5a
        L68:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L5a
            monitor-exit(r11)
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor r11 = r8.serviceInteractor     // Catch: android.os.RemoteException -> L36
            r0.L$0 = r8     // Catch: android.os.RemoteException -> L36
            r0.L$1 = r9     // Catch: android.os.RemoteException -> L36
            r0.L$2 = r10     // Catch: android.os.RemoteException -> L36
            r0.label = r4     // Catch: android.os.RemoteException -> L36
            java.lang.Object r11 = r11.bindOnClick(r0)     // Catch: android.os.RemoteException -> L36
            if (r11 != r1) goto L7c
            return r1
        L7c:
            if (r10 != 0) goto L91
            java.util.concurrent.atomic.AtomicReference r10 = r8.lastClickedExpandable     // Catch: android.os.RemoteException -> L36
            r10.set(r9)     // Catch: android.os.RemoteException -> L36
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor r9 = r8.serviceInteractor     // Catch: android.os.RemoteException -> L36
            android.os.IBinder r10 = r8.token     // Catch: android.os.RemoteException -> L36
            com.android.systemui.qs.external.TileServiceManager r9 = r9.getTileServiceManager()     // Catch: android.os.RemoteException -> L36
            com.android.systemui.qs.external.TileLifecycleManager r9 = r9.mStateManager     // Catch: android.os.RemoteException -> L36
            r9.onClick(r10)     // Catch: android.os.RemoteException -> L36
            goto La2
        L91:
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler r11 = r8.qsTileIntentUserInputHandler     // Catch: android.os.RemoteException -> L36
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandlerImpl r11 = (com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandlerImpl) r11     // Catch: android.os.RemoteException -> L36
            r11.handle(r9, r10, r3)     // Catch: android.os.RemoteException -> L36
            goto La2
        L99:
            com.android.systemui.qs.tiles.base.logging.QSTileLogger r10 = r8.qsTileLogger
            com.android.systemui.qs.pipeline.shared.TileSpec r8 = r8.tileSpec
            java.lang.String r11 = "Failed to deliver click"
            r10.logError(r8, r11, r9)
        La2:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        La5:
            monitor-exit(r11)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor.click(com.android.systemui.animation.Expandable, android.app.PendingIntent, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object handleInput(com.android.systemui.qs.tiles.base.interactor.QSTileInput r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$handleInput$1
            if (r0 == 0) goto L14
            r0 = r9
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$handleInput$1 r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$handleInput$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r6 = r0
            goto L1a
        L14:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$handleInput$1 r0 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$handleInput$1
            r0.<init>(r7, r9)
            goto L12
        L1a:
            java.lang.Object r9 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L39
            if (r1 == r3) goto L31
            if (r1 != r2) goto L29
            goto L31
        L29:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L31:
            java.lang.Object r7 = r6.L$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor r7 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L7d
        L39:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.systemui.qs.tiles.viewmodel.QSTileUserAction r9 = r8.action
            boolean r1 = r9 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.Click
            java.lang.Object r4 = r8.data
            if (r1 == 0) goto L5b
            com.android.systemui.qs.tiles.viewmodel.QSTileUserAction$Click r9 = (com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.Click) r9
            com.android.systemui.animation.Expandable r8 = r9.expandable
            com.android.systemui.qs.tiles.impl.custom.domain.entity.CustomTileDataModel r4 = (com.android.systemui.qs.tiles.impl.custom.domain.entity.CustomTileDataModel) r4
            android.service.quicksettings.Tile r9 = r4.tile
            android.app.PendingIntent r9 = r9.getActivityLaunchForClick()
            r6.L$0 = r7
            r6.label = r3
            java.lang.Object r8 = r7.click(r8, r9, r6)
            if (r8 != r0) goto L7d
            return r0
        L5b:
            boolean r1 = r9 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.LongClick
            if (r1 == 0) goto L7d
            android.os.UserHandle r8 = r8.user
            com.android.systemui.qs.tiles.viewmodel.QSTileUserAction$LongClick r9 = (com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.LongClick) r9
            com.android.systemui.animation.Expandable r3 = r9.expandable
            com.android.systemui.qs.tiles.impl.custom.domain.entity.CustomTileDataModel r4 = (com.android.systemui.qs.tiles.impl.custom.domain.entity.CustomTileDataModel) r4
            android.content.ComponentName r9 = r4.componentName
            android.service.quicksettings.Tile r1 = r4.tile
            int r5 = r1.getState()
            r6.L$0 = r7
            r6.label = r2
            r1 = r7
            r2 = r8
            r4 = r9
            java.lang.Object r8 = r1.longClick(r2, r3, r4, r5, r6)
            if (r8 != r0) goto L7d
            return r0
        L7d:
            com.android.systemui.qs.tiles.base.logging.QSTileLogger r8 = r7.qsTileLogger
            com.android.systemui.qs.pipeline.shared.TileSpec r7 = r7.tileSpec
            r8.logCustomTileUserActionDelivered(r7)
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor.handleInput(com.android.systemui.qs.tiles.base.interactor.QSTileInput, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object longClick(android.os.UserHandle r6, com.android.systemui.animation.Expandable r7, android.content.ComponentName r8, int r9, kotlin.coroutines.Continuation r10) {
        /*
            r5 = this;
            boolean r0 = r10 instanceof com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$longClick$1
            if (r0 == 0) goto L13
            r0 = r10
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$longClick$1 r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$longClick$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$longClick$1 r0 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$longClick$1
            r0.<init>(r5, r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L40
            if (r2 != r3) goto L38
            int r9 = r0.I$0
            java.lang.Object r5 = r0.L$2
            r8 = r5
            android.content.ComponentName r8 = (android.content.ComponentName) r8
            java.lang.Object r5 = r0.L$1
            r7 = r5
            com.android.systemui.animation.Expandable r7 = (com.android.systemui.animation.Expandable) r7
            java.lang.Object r5 = r0.L$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor r5 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor) r5
            kotlin.ResultKt.throwOnFailure(r10)
            goto L69
        L38:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L40:
            kotlin.ResultKt.throwOnFailure(r10)
            android.content.Intent r10 = new android.content.Intent
            java.lang.String r2 = "android.service.quicksettings.action.QS_TILE_PREFERENCES"
            r10.<init>(r2)
            java.lang.String r2 = r8.getPackageName()
            r10.setPackage(r2)
            r0.L$0 = r5
            r0.L$1 = r7
            r0.L$2 = r8
            r0.I$0 = r9
            r0.label = r3
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$resolveIntent$2 r2 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$resolveIntent$2
            r2.<init>(r5, r10, r6, r4)
            kotlin.coroutines.CoroutineContext r6 = r5.backgroundContext
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r6, r2, r0)
            if (r10 != r1) goto L69
            return r1
        L69:
            android.content.Intent r10 = (android.content.Intent) r10
            if (r10 == 0) goto L79
            java.lang.String r6 = "android.intent.extra.COMPONENT_NAME"
            r10.putExtra(r6, r8)
            java.lang.String r6 = "state"
            r10.putExtra(r6, r9)
            goto L7a
        L79:
            r10 = r4
        L7a:
            if (r10 != 0) goto L97
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler r5 = r5.qsTileIntentUserInputHandler
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r9 = "android.settings.APPLICATION_DETAILS_SETTINGS"
            r6.<init>(r9)
            java.lang.String r9 = "package"
            java.lang.String r8 = r8.getPackageName()
            android.net.Uri r8 = android.net.Uri.fromParts(r9, r8, r4)
            android.content.Intent r6 = r6.setData(r8)
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler.handle$default(r5, r7, r6)
            goto L9c
        L97:
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler r5 = r5.qsTileIntentUserInputHandler
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler.handle$default(r5, r7, r10)
        L9c:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor.longClick(android.os.UserHandle, com.android.systemui.animation.Expandable, android.content.ComponentName, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void revokeToken(boolean z) {
        synchronized (this.token) {
            if (this.isTokenGranted && (z || !this.isShowingDialog)) {
                try {
                    IWindowManager iWindowManager = this.windowManager;
                    IBinder iBinder = this.token;
                    this.displayTracker.getClass();
                    iWindowManager.removeWindowToken(iBinder, 0);
                } catch (RemoteException e) {
                    this.qsTileLogger.logError(this.tileSpec, "Failed to remove a window token", e);
                }
                this.isTokenGranted = false;
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
