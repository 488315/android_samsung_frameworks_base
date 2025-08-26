package com.android.systemui.qs.tiles.impl.night.domain.interactor;

import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.accessibility.data.repository.NightDisplayRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class NightDisplayTileUserActionInteractor implements QSTileUserActionInteractor {
    public static final TileSpec spec;
    public final NightDisplayRepository nightDisplayRepository;
    public final QSTileLogger qsLogger;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor$handleInput$1, reason: invalid class name */
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
            return NightDisplayTileUserActionInteractor.this.handleInput(null, this);
        }
    }

    static {
        new Companion(null);
        TileSpec.Companion.getClass();
        spec = TileSpec.Companion.create("night");
    }

    public NightDisplayTileUserActionInteractor(NightDisplayRepository nightDisplayRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, QSTileLogger qSTileLogger) {
        this.nightDisplayRepository = nightDisplayRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.qsLogger = qSTileLogger;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0061, code lost:
    
        if (r6.nightDisplayRepository.setNightDisplayAutoMode(r8, r0) == r1) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0085, code lost:
    
        if (r6.nightDisplayRepository.setNightDisplayActivated(r7, r0, r8) == r1) goto L26;
     */
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            QSTileUserAction qSTileUserAction = qSTileInput.action;
            if (qSTileUserAction instanceof QSTileUserAction.Click) {
                if (((NightDisplayTileModel) qSTileInput.data).isEnrolledInForcedNightDisplayAutoMode()) {
                    UserHandle userHandle = qSTileInput.user;
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = qSTileInput;
                    anonymousClass1.label = 1;
                }
                return coroutineSingletons;
            }
            if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
                QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.NIGHT_DISPLAY_SETTINGS"));
            } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
                throw new NoWhenBranchMatchedException();
            }
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        qSTileInput = (QSTileInput) anonymousClass1.L$1;
        this = (NightDisplayTileUserActionInteractor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        this.qsLogger.logInfo("Enrolled in forced night display auto mode", spec);
        boolean z = !((NightDisplayTileModel) qSTileInput.data).isActivated();
        UserHandle userHandle2 = qSTileInput.user;
        anonymousClass1.L$0 = null;
        anonymousClass1.L$1 = null;
        anonymousClass1.label = 2;
    }
}
