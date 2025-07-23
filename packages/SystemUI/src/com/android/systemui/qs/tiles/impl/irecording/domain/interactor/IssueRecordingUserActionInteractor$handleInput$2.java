package com.android.systemui.qs.tiles.impl.irecording.domain.interactor;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.recordissue.IssueRecordingService;
import com.android.systemui.recordissue.IssueRecordingState;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingController.AnonymousClass3;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.traceur.TraceConfig;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class IssueRecordingUserActionInteractor$handleInput$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileInput $input;
    int label;
    final /* synthetic */ IssueRecordingUserActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IssueRecordingUserActionInteractor$handleInput$2(IssueRecordingUserActionInteractor issueRecordingUserActionInteractor, QSTileInput qSTileInput, Continuation continuation) {
        super(2, continuation);
        this.this$0 = issueRecordingUserActionInteractor;
        this.$input = qSTileInput;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IssueRecordingUserActionInteractor$handleInput$2(this.this$0, this.$input, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IssueRecordingUserActionInteractor$handleInput$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final IssueRecordingUserActionInteractor issueRecordingUserActionInteractor = this.this$0;
        final Expandable expandable = ((QSTileUserAction.Click) this.$input.action).expandable;
        int i = IssueRecordingUserActionInteractor.$r8$clinit;
        issueRecordingUserActionInteractor.getClass();
        final SystemUIDialog createDialog = issueRecordingUserActionInteractor.delegateFactory.create(new Runnable() { // from class: com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingUserActionInteractor$showPrompt$dialog$1
            @Override // java.lang.Runnable
            public final void run() {
                IssueRecordingUserActionInteractor issueRecordingUserActionInteractor2 = IssueRecordingUserActionInteractor.this;
                int i2 = IssueRecordingUserActionInteractor.$r8$clinit;
                issueRecordingUserActionInteractor2.getClass();
                IssueRecordingService.Companion companion = IssueRecordingService.Companion;
                UserContextProvider userContextProvider = issueRecordingUserActionInteractor2.userContextProvider;
                UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userContextProvider;
                Context userContext = userTrackerImpl.getUserContext();
                LinkedHashMap linkedHashMap = IssueRecordingState.ALL_ISSUE_TYPES;
                IssueRecordingState issueRecordingState = issueRecordingUserActionInteractor2.state;
                TraceConfig traceConfig = (TraceConfig) linkedHashMap.get(Integer.valueOf(issueRecordingState.getIssueTypeRes()));
                if (traceConfig == null) {
                    traceConfig = issueRecordingState.customTraceState.getTraceConfig();
                }
                boolean z = issueRecordingState.getPrefs().getBoolean("key_recordScreen", false);
                boolean z2 = issueRecordingState.getPrefs().getBoolean("key_takeBugReport", false);
                companion.getClass();
                PendingIntent service = PendingIntent.getService(((UserTrackerImpl) userContextProvider).getUserContext(), 2, new Intent(userContext, (Class<?>) IssueRecordingService.class).setAction("com.android.systemui.screenrecord.START").putExtra("com.android.traceur.trace_type", traceConfig).putExtra("extra_screenRecord", z).putExtra("extra_bugReport", z2), 201326592);
                PendingIntent service2 = PendingIntent.getService(((UserTrackerImpl) userContextProvider).getUserContext(), 2, IssueRecordingService.Companion.getStopIntent(userTrackerImpl.getUserContext()), 201326592);
                RecordingController recordingController = issueRecordingUserActionInteractor2.recordingController;
                recordingController.mIsStarting = true;
                recordingController.mStopIntent = service2;
                RecordingController.AnonymousClass3 anonymousClass3 = recordingController.new AnonymousClass3(0L, 1000L, service);
                recordingController.mCountDownTimer = anonymousClass3;
                anonymousClass3.start();
                IssueRecordingUserActionInteractor.this.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                ((PanelInteractorImpl) IssueRecordingUserActionInteractor.this.panelInteractor).collapsePanels();
            }
        }).createDialog();
        issueRecordingUserActionInteractor.keyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingUserActionInteractor$showPrompt$dismissAction$1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                Expandable expandable2 = Expandable.this;
                if (expandable2 != null) {
                    IssueRecordingUserActionInteractor issueRecordingUserActionInteractor2 = issueRecordingUserActionInteractor;
                    if (!((KeyguardStateControllerImpl) issueRecordingUserActionInteractor2.keyguardStateController).mShowing) {
                        DialogTransitionAnimator.Controller dialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "record_issue"));
                        AlertDialog alertDialog = createDialog;
                        if (dialogTransitionController != null) {
                            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                            issueRecordingUserActionInteractor2.dialogTransitionAnimator.show(alertDialog, dialogTransitionController, false);
                        } else {
                            alertDialog.show();
                        }
                        return false;
                    }
                }
                createDialog.show();
                return false;
            }
        }, false, true);
        return Unit.INSTANCE;
    }
}
