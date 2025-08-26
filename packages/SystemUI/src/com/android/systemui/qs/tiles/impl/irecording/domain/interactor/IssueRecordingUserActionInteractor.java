package com.android.systemui.qs.tiles.impl.irecording.domain.interactor;

import android.app.AlertDialog;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.irecording.data.model.IssueRecordingModel;
import com.android.systemui.recordissue.IssueRecordingService;
import com.android.systemui.recordissue.IssueRecordingState;
import com.android.systemui.recordissue.RecordIssueDialogDelegate;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingController.AnonymousClass3;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.traceur.TraceConfig;
import java.util.LinkedHashMap;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingUserActionInteractor$handleInput$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ QSTileInput $input;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(QSTileInput qSTileInput, Continuation continuation) {
            super(2, continuation);
            this.$input = qSTileInput;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IssueRecordingUserActionInteractor.this.new AnonymousClass2(this.$input, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final IssueRecordingUserActionInteractor issueRecordingUserActionInteractor = IssueRecordingUserActionInteractor.this;
            final Expandable expandable = ((QSTileUserAction.Click) this.$input.action).expandable;
            int i = IssueRecordingUserActionInteractor.$r8$clinit;
            issueRecordingUserActionInteractor.getClass();
            final SystemUIDialog systemUIDialogCreateDialog = issueRecordingUserActionInteractor.delegateFactory.create(new Runnable() { // from class: com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingUserActionInteractor$showPrompt$dialog$1
                @Override // java.lang.Runnable
                public final void run() {
                    IssueRecordingUserActionInteractor issueRecordingUserActionInteractor2 = issueRecordingUserActionInteractor;
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
                    issueRecordingUserActionInteractor.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                    ((PanelInteractorImpl) issueRecordingUserActionInteractor.panelInteractor).collapsePanels();
                }
            }).createDialog();
            issueRecordingUserActionInteractor.keyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingUserActionInteractor$showPrompt$dismissAction$1
                /* JADX WARN: Removed duplicated region for block: B:10:0x002c  */
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final boolean onDismiss() {
                    Expandable expandable2 = expandable;
                    if (expandable2 != null) {
                        IssueRecordingUserActionInteractor issueRecordingUserActionInteractor2 = issueRecordingUserActionInteractor;
                        if (((KeyguardStateControllerImpl) issueRecordingUserActionInteractor2.keyguardStateController).mShowing) {
                            systemUIDialogCreateDialog.show();
                        } else {
                            DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "record_issue"));
                            AlertDialog alertDialog = systemUIDialogCreateDialog;
                            if (controllerDialogTransitionController != null) {
                                TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                                issueRecordingUserActionInteractor2.dialogTransitionAnimator.show(alertDialog, controllerDialogTransitionController, false);
                            } else {
                                alertDialog.show();
                            }
                        }
                    }
                    return false;
                }
            }, false, true);
            return Unit.INSTANCE;
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
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) throws Throwable {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (!(qSTileUserAction instanceof QSTileUserAction.Click)) {
            Objects.toString(qSTileUserAction);
            Boxing.boxInt(0);
        } else {
            if (!((IssueRecordingModel) qSTileInput.data).isRecording) {
                Object objWithContext = BuildersKt.withContext(this.mainCoroutineContext, new AnonymousClass2(qSTileInput, null), continuation);
                return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
            }
            IssueRecordingService.Companion companion = IssueRecordingService.Companion;
            UserContextProvider userContextProvider = this.userContextProvider;
            Context userContext = ((UserTrackerImpl) userContextProvider).getUserContext();
            companion.getClass();
            PendingIntent service = PendingIntent.getService(((UserTrackerImpl) userContextProvider).getUserContext(), 2, IssueRecordingService.Companion.getStopIntent(userContext), 201326592);
            BroadcastOptions broadcastOptionsMakeBasic = BroadcastOptions.makeBasic();
            broadcastOptionsMakeBasic.setInteractive(true);
            service.send(broadcastOptionsMakeBasic.toBundle());
        }
        return Unit.INSTANCE;
    }
}
