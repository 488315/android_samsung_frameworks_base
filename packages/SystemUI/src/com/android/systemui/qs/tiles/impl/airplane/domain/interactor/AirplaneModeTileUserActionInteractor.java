package com.android.systemui.qs.tiles.impl.airplane.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.airplane.domain.model.AirplaneModeTileModel;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
public final class AirplaneModeTileUserActionInteractor implements QSTileUserActionInteractor {
    public final AirplaneModeInteractor airplaneModeInteractor;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AirplaneModeInteractor.SetResult.values().length];
            try {
                iArr[AirplaneModeInteractor.SetResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AirplaneModeInteractor.SetResult.BLOCKED_BY_ECM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.airplane.domain.interactor.AirplaneModeTileUserActionInteractor$handleInput$1, reason: invalid class name */
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
            return AirplaneModeTileUserActionInteractor.this.handleInput(null, this);
        }
    }

    public AirplaneModeTileUserActionInteractor(AirplaneModeInteractor airplaneModeInteractor, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.airplaneModeInteractor = airplaneModeInteractor;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object isAirplaneMode = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(isAirplaneMode);
            QSTileUserAction qSTileUserAction = qSTileInput.action;
            if (!(qSTileUserAction instanceof QSTileUserAction.Click)) {
                if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
                    QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.AIRPLANE_MODE_SETTINGS"));
                } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
                    throw new NoWhenBranchMatchedException();
                }
                return Unit.INSTANCE;
            }
            boolean z = !((AirplaneModeTileModel) qSTileInput.data).isEnabled;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = qSTileInput;
            anonymousClass1.label = 1;
            isAirplaneMode = this.airplaneModeInteractor.setIsAirplaneMode(z, anonymousClass1);
            if (isAirplaneMode == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            qSTileInput = (QSTileInput) anonymousClass1.L$1;
            this = (AirplaneModeTileUserActionInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(isAirplaneMode);
        }
        int i3 = WhenMappings.$EnumSwitchMapping$0[((AirplaneModeInteractor.SetResult) isAirplaneMode).ordinal()];
        if (i3 != 1) {
            if (i3 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.Click) qSTileInput.action).expandable, new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS"));
        }
        return Unit.INSTANCE;
    }
}
