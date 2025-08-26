package com.android.systemui.qs.tiles.impl.screenrecord.domain.interactor;

import android.util.Log;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
public final class ScreenRecordTileUserActionInteractor implements QSTileUserActionInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineContext backgroundContext;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final KeyguardInteractor keyguardInteractor;
    public final CoroutineContext mainContext;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public final PanelInteractor panelInteractor;
    public final RecordingController recordingController;
    public final ScreenRecordRepository screenRecordRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ScreenRecordTileUserActionInteractor(CoroutineContext coroutineContext, CoroutineContext coroutineContext2, ScreenRecordRepository screenRecordRepository, RecordingController recordingController, KeyguardInteractor keyguardInteractor, KeyguardDismissUtil keyguardDismissUtil, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, MediaProjectionMetricsLogger mediaProjectionMetricsLogger) {
        this.mainContext = coroutineContext;
        this.backgroundContext = coroutineContext2;
        this.screenRecordRepository = screenRecordRepository;
        this.recordingController = recordingController;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardDismissUtil = keyguardDismissUtil;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.panelInteractor = panelInteractor;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) throws Throwable {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            ScreenRecordModel screenRecordModel = (ScreenRecordModel) qSTileInput.data;
            if (screenRecordModel instanceof ScreenRecordModel.Starting) {
                Log.d("ScreenRecordTileUserActionInteractor", "Cancelling countdown");
                Object objWithContext = BuildersKt.withContext(this.backgroundContext, new ScreenRecordTileUserActionInteractor$handleInput$2$1(this, null), continuation);
                if (objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return objWithContext;
                }
            } else if (screenRecordModel instanceof ScreenRecordModel.Recording) {
                Object objStopRecording = ((ScreenRecordRepositoryImpl) this.screenRecordRepository).stopRecording(5, (SuspendLambda) continuation);
                if (objStopRecording == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return objStopRecording;
                }
            } else {
                if (!(screenRecordModel instanceof ScreenRecordModel.DoingNothing)) {
                    throw new NoWhenBranchMatchedException();
                }
                Object objWithContext2 = BuildersKt.withContext(this.mainContext, new ScreenRecordTileUserActionInteractor$handleInput$2$2(this, qSTileInput, null), continuation);
                if (objWithContext2 == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return objWithContext2;
                }
            }
        } else if (!(qSTileUserAction instanceof QSTileUserAction.LongClick) && !(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
