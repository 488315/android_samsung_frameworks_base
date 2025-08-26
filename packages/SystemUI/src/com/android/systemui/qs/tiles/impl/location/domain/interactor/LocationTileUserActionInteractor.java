package com.android.systemui.qs.tiles.impl.location.domain.interactor;

import android.content.Intent;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.location.domain.model.LocationTileModel;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes2.dex */
public final class LocationTileUserActionInteractor implements QSTileUserActionInteractor {
    public final ActivityStarter activityStarter;
    public final CoroutineScope applicationScope;
    public final CoroutineContext coroutineContext;
    public final KeyguardStateController keyguardController;
    public final LocationController locationController;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public LocationTileUserActionInteractor(CoroutineContext coroutineContext, CoroutineScope coroutineScope, LocationController locationController, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, ActivityStarter activityStarter, KeyguardStateController keyguardStateController) {
        this.coroutineContext = coroutineContext;
        this.applicationScope = coroutineScope;
        this.locationController = locationController;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.activityStarter = activityStarter;
        this.keyguardController = keyguardStateController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) throws Throwable {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            final boolean z = ((LocationTileModel) qSTileInput.data).isEnabled;
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardController;
            if (keyguardStateControllerImpl.mSecure && keyguardStateControllerImpl.mShowing) {
                this.activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.impl.location.domain.interactor.LocationTileUserActionInteractor$handleInput$2$1

                    /* renamed from: com.android.systemui.qs.tiles.impl.location.domain.interactor.LocationTileUserActionInteractor$handleInput$2$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ boolean $wasEnabled;
                        int label;
                        final /* synthetic */ LocationTileUserActionInteractor this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(LocationTileUserActionInteractor locationTileUserActionInteractor, boolean z, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = locationTileUserActionInteractor;
                            this.$wasEnabled = z;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.this$0, this.$wasEnabled, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            ((LocationControllerImpl) this.this$0.locationController).setLocationEnabled(!this.$wasEnabled);
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        CoroutineContext coroutineContext = this.this$0.applicationScope.getCoroutineContext();
                        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
                        CoroutineTracingKt.launchTraced$default(CoroutineScopeKt.CoroutineScope(coroutineContext.plus(EmptyCoroutineContext.INSTANCE)), null, null, new AnonymousClass1(this.this$0, z, null), 7);
                    }
                });
            } else {
                Object objWithContext = BuildersKt.withContext(this.coroutineContext, new LocationTileUserActionInteractor$handleInput$2$2(this, z, null), continuation);
                if (objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return objWithContext;
                }
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
