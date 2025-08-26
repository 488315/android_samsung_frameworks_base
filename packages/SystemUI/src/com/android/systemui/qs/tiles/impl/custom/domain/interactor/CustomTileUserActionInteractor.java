package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.view.IWindowManager;
import com.android.systemui.animation.Expandable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandlerImpl;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.custom.domain.model.CustomTileDataModel;
import com.android.systemui.settings.DisplayTracker;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;

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

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$click$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CustomTileUserActionInteractor.this.click(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$handleInput$1, reason: invalid class name and case insensitive filesystem */
    final class C10181 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10181(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CustomTileUserActionInteractor.this.handleInput(null, this);
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileUserActionInteractor$longClick$1, reason: invalid class name and case insensitive filesystem */
    final class C10191 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C10191(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CustomTileUserActionInteractor.this.longClick(null, null, null, 0, this);
        }
    }

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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object click(Expandable expandable, PendingIntent pendingIntent, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
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
        try {
        } catch (RemoteException e) {
            this.qsTileLogger.logError(this.tileSpec, "Failed to deliver click", e);
        }
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            synchronized (this.token) {
                if (this.isTokenGranted) {
                    Unit unit = Unit.INSTANCE;
                } else {
                    try {
                        IWindowManager iWindowManager = this.windowManager;
                        IBinder iBinder = this.token;
                        this.displayTracker.getClass();
                        iWindowManager.addWindowToken(iBinder, 2035, 0, (Bundle) null);
                    } catch (RemoteException e2) {
                        this.qsTileLogger.logError(this.tileSpec, "Failed to grant a window token", e2);
                    }
                    this.isTokenGranted = true;
                    Unit unit2 = Unit.INSTANCE;
                }
                return Unit.INSTANCE;
            }
            CustomTileServiceInteractor customTileServiceInteractor = this.serviceInteractor;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = expandable;
            anonymousClass1.L$2 = pendingIntent;
            anonymousClass1.label = 1;
            if (customTileServiceInteractor.bindOnClick(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            pendingIntent = (PendingIntent) anonymousClass1.L$2;
            expandable = (Expandable) anonymousClass1.L$1;
            this = (CustomTileUserActionInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (pendingIntent == null) {
            this.lastClickedExpandable.set(expandable);
            CustomTileServiceInteractor customTileServiceInteractor2 = this.serviceInteractor;
            customTileServiceInteractor2.getTileServiceManager().mStateManager.onClick(this.token);
        } else {
            ((QSTileIntentUserInputHandlerImpl) this.qsTileIntentUserInputHandler).handle(expandable, pendingIntent, false);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (click(r8, r9, r6) == r0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007a, code lost:
    
        if (r1.longClick(r8, r3, r4, r5, r6) == r0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x007c, code lost:
    
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        C10181 c10181;
        CustomTileUserActionInteractor customTileUserActionInteractor;
        if (continuation instanceof C10181) {
            c10181 = (C10181) continuation;
            int i = c10181.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c10181.label = i - Integer.MIN_VALUE;
            } else {
                c10181 = new C10181(continuation);
            }
        }
        C10181 c101812 = c10181;
        Object obj = c101812.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c101812.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            QSTileUserAction qSTileUserAction = qSTileInput.action;
            boolean z = qSTileUserAction instanceof QSTileUserAction.Click;
            Object obj2 = qSTileInput.data;
            if (z) {
                Expandable expandable = ((QSTileUserAction.Click) qSTileUserAction).expandable;
                PendingIntent activityLaunchForClick = ((CustomTileDataModel) obj2).tile.getActivityLaunchForClick();
                c101812.L$0 = this;
                c101812.label = 1;
            } else {
                if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
                    UserHandle userHandle = qSTileInput.user;
                    Expandable expandable2 = ((QSTileUserAction.LongClick) qSTileUserAction).expandable;
                    CustomTileDataModel customTileDataModel = (CustomTileDataModel) obj2;
                    ComponentName componentName = customTileDataModel.componentName;
                    int state = customTileDataModel.tile.getState();
                    c101812.L$0 = this;
                    c101812.label = 2;
                    customTileUserActionInteractor = this;
                } else {
                    customTileUserActionInteractor = this;
                    if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                this = customTileUserActionInteractor;
            }
        } else {
            if (i2 != 1 && i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (CustomTileUserActionInteractor) c101812.L$0;
            ResultKt.throwOnFailure(obj);
        }
        QSTileLogger qSTileLogger = this.qsTileLogger;
        TileSpec tileSpec = this.tileSpec;
        LogBuffer logBuffer = qSTileLogger.getLogBuffer(tileSpec);
        logBuffer.commit(logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda0(4), null));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object longClick(UserHandle userHandle, Expandable expandable, ComponentName componentName, int i, ContinuationImpl continuationImpl) throws Throwable {
        C10191 c10191;
        if (continuationImpl instanceof C10191) {
            c10191 = (C10191) continuationImpl;
            int i2 = c10191.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c10191.label = i2 - Integer.MIN_VALUE;
            } else {
                c10191 = new C10191(continuationImpl);
            }
        }
        Object objWithContext = c10191.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c10191.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            Intent intent = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
            intent.setPackage(componentName.getPackageName());
            c10191.L$0 = this;
            c10191.L$1 = expandable;
            c10191.L$2 = componentName;
            c10191.I$0 = i;
            c10191.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundContext, new CustomTileUserActionInteractor$resolveIntent$2(this, intent, userHandle, null), c10191);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = c10191.I$0;
            componentName = (ComponentName) c10191.L$2;
            expandable = (Expandable) c10191.L$1;
            this = (CustomTileUserActionInteractor) c10191.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        Intent intent2 = (Intent) objWithContext;
        if (intent2 != null) {
            intent2.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
            intent2.putExtra("state", i);
        } else {
            intent2 = null;
        }
        if (intent2 == null) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserInputHandler, expandable, new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", componentName.getPackageName(), null)));
        } else {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserInputHandler, expandable, intent2);
        }
        return Unit.INSTANCE;
    }

    public final void revokeToken(boolean z) {
        synchronized (this.token) {
            if (!this.isTokenGranted || (!z && this.isShowingDialog)) {
                Unit unit = Unit.INSTANCE;
            } else {
                try {
                    IWindowManager iWindowManager = this.windowManager;
                    IBinder iBinder = this.token;
                    this.displayTracker.getClass();
                    iWindowManager.removeWindowToken(iBinder, 0);
                } catch (RemoteException e) {
                    this.qsTileLogger.logError(this.tileSpec, "Failed to remove a window token", e);
                }
                this.isTokenGranted = false;
                Unit unit2 = Unit.INSTANCE;
            }
        }
    }
}
