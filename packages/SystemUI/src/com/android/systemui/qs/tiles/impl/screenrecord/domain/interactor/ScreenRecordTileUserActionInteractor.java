package com.android.systemui.qs.tiles.impl.screenrecord.domain.interactor;

import android.content.Context;
import android.util.Log;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenRecordTileUserActionInteractor implements QSTileUserActionInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final CoroutineContext backgroundContext;
    public final Context context;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final FeatureFlagsClassic featureFlags;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final KeyguardInteractor keyguardInteractor;
    public final CoroutineContext mainContext;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public final PanelInteractor panelInteractor;
    public final RecordingController recordingController;
    public final ScreenRecordRepository screenRecordRepository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ScreenRecordTileUserActionInteractor(Context context, CoroutineContext coroutineContext, CoroutineContext coroutineContext2, ScreenRecordRepository screenRecordRepository, RecordingController recordingController, KeyguardInteractor keyguardInteractor, KeyguardDismissUtil keyguardDismissUtil, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, FeatureFlagsClassic featureFlagsClassic, ActivityStarter activityStarter) {
        this.mainContext = coroutineContext;
        this.backgroundContext = coroutineContext2;
        this.screenRecordRepository = screenRecordRepository;
        this.recordingController = recordingController;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardDismissUtil = keyguardDismissUtil;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.panelInteractor = panelInteractor;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.featureFlags = featureFlagsClassic;
        this.activityStarter = activityStarter;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            ScreenRecordModel screenRecordModel = (ScreenRecordModel) qSTileInput.data;
            if (screenRecordModel instanceof ScreenRecordModel.Starting) {
                Log.d("ScreenRecordTileUserActionInteractor", "Cancelling countdown");
                Object withContext = BuildersKt.withContext(this.backgroundContext, new ScreenRecordTileUserActionInteractor$handleInput$2$1(this, null), continuation);
                if (withContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return withContext;
                }
            } else if (screenRecordModel instanceof ScreenRecordModel.Recording) {
                Object stopRecording = ((ScreenRecordRepositoryImpl) this.screenRecordRepository).stopRecording(continuation);
                if (stopRecording == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return stopRecording;
                }
            } else if (screenRecordModel instanceof ScreenRecordModel.DoingNothing) {
                Object withContext2 = BuildersKt.withContext(this.mainContext, new ScreenRecordTileUserActionInteractor$handleInput$2$2(this, qSTileInput, null), continuation);
                if (withContext2 == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return withContext2;
                }
            }
        } else {
            boolean z = qSTileUserAction instanceof QSTileUserAction.LongClick;
        }
        return Unit.INSTANCE;
    }
}
