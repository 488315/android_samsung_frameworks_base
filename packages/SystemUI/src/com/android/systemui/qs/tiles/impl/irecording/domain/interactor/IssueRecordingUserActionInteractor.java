package com.android.systemui.qs.tiles.impl.irecording.domain.interactor;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.irecording.data.model.IssueRecordingModel;
import com.android.systemui.recordissue.IssueRecordingService;
import com.android.systemui.recordissue.IssueRecordingState;
import com.android.systemui.recordissue.RecordIssueDialogDelegate;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IssueRecordingUserActionInteractor implements QSTileUserActionInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final RecordIssueDialogDelegate.Factory delegateFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final KeyguardStateController keyguardStateController;
    public final CoroutineContext mainCoroutineContext;
    public final PanelInteractor panelInteractor;
    public final RecordingController recordingController;
    public final IssueRecordingState state;
    public final UserContextProvider userContextProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public IssueRecordingUserActionInteractor(CoroutineContext coroutineContext, IssueRecordingState issueRecordingState, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, UserContextProvider userContextProvider, RecordIssueDialogDelegate.Factory factory, RecordingController recordingController) {
        this.mainCoroutineContext = coroutineContext;
        this.state = issueRecordingState;
        this.keyguardDismissUtil = keyguardDismissUtil;
        this.keyguardStateController = keyguardStateController;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.panelInteractor = panelInteractor;
        this.userContextProvider = userContextProvider;
        this.delegateFactory = factory;
        this.recordingController = recordingController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (!(qSTileUserAction instanceof QSTileUserAction.Click)) {
            Objects.toString(qSTileUserAction);
            Boxing.boxInt(0);
        } else {
            if (!((IssueRecordingModel) qSTileInput.data).isRecording) {
                Object withContext = BuildersKt.withContext(this.mainCoroutineContext, new IssueRecordingUserActionInteractor$handleInput$2(this, qSTileInput, null), continuation);
                return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
            }
            IssueRecordingService.Companion companion = IssueRecordingService.Companion;
            UserContextProvider userContextProvider = this.userContextProvider;
            Context userContext = ((UserTrackerImpl) userContextProvider).getUserContext();
            companion.getClass();
            PendingIntent service = PendingIntent.getService(((UserTrackerImpl) userContextProvider).getUserContext(), 2, IssueRecordingService.Companion.getStopIntent(userContext), 201326592);
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setInteractive(true);
            service.send(makeBasic.toBundle());
        }
        return Unit.INSTANCE;
    }
}
