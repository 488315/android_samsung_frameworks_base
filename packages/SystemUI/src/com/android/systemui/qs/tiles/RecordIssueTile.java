package com.android.systemui.qs.tiles;

import android.app.AlertDialog;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.recordissue.IssueRecordingService;
import com.android.systemui.recordissue.IssueRecordingServiceConnection;
import com.android.systemui.recordissue.IssueRecordingState;
import com.android.systemui.recordissue.RecordIssueDialogDelegate;
import com.android.systemui.recordissue.TagsHandler;
import com.android.systemui.recordissue.TraceurConnection;
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class RecordIssueTile extends QSTileImpl {
    public final Executor bgExecutor;
    public final RecordIssueDialogDelegate.Factory delegateFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final IssueRecordingServiceConnection irsConnection;
    public final IssueRecordingState issueRecordingState;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final KeyguardStateController keyguardStateController;
    public final RecordIssueTile$onRecordingChangeListener$1 onRecordingChangeListener;
    public final PanelInteractor panelInteractor;
    public final RecordingController recordingController;
    public final TraceurConnection traceurConnection;
    public final UserContextProvider userContextProvider;

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.qs.tiles.RecordIssueTile$onRecordingChangeListener$1] */
    public RecordIssueTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, UserContextProvider userContextProvider, IssueRecordingServiceConnection.Provider provider, TraceurConnection.Provider provider2, Executor executor, IssueRecordingState issueRecordingState, RecordIssueDialogDelegate.Factory factory, RecordingController recordingController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.keyguardDismissUtil = keyguardDismissUtil;
        this.keyguardStateController = keyguardStateController;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.panelInteractor = panelInteractor;
        this.userContextProvider = userContextProvider;
        this.bgExecutor = executor;
        this.issueRecordingState = issueRecordingState;
        this.delegateFactory = factory;
        this.recordingController = recordingController;
        this.onRecordingChangeListener = new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$onRecordingChangeListener$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.refreshState(null);
            }
        };
        provider.getClass();
        this.irsConnection = new IssueRecordingServiceConnection(provider.userContextProvider);
        provider2.getClass();
        final TraceurConnection traceurConnection = new TraceurConnection(provider2.userContextProvider, provider2.bgLooper, null);
        ((CopyOnWriteArrayList) traceurConnection.onBound).add(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$traceurConnection$1$1
            @Override // java.lang.Runnable
            public final void run() throws RemoteException {
                TraceurConnection traceurConnection2 = traceurConnection;
                IssueRecordingState issueRecordingState2 = this.issueRecordingState;
                traceurConnection2.getClass();
                TraceurConnection.sendMessage$default(traceurConnection2, 3, null, new Messenger(new TagsHandler(traceurConnection2.bgLooper, issueRecordingState2)), 2);
                traceurConnection.doUnBind();
            }
        });
        this.traceurConnection = traceurConnection;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.qs_record_issue_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(final Expandable expandable) throws PendingIntent.CanceledException {
        if (!this.issueRecordingState.isRecording) {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile.handleClick.1
                @Override // java.lang.Runnable
                public final void run() {
                    final RecordIssueTile recordIssueTile = RecordIssueTile.this;
                    final Expandable expandable2 = expandable;
                    recordIssueTile.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$showPrompt$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (!recordIssueTile.traceurConnection.onBound.isEmpty()) {
                                recordIssueTile.traceurConnection.doBind();
                            }
                            recordIssueTile.irsConnection.doBind();
                        }
                    });
                    final SystemUIDialog systemUIDialogCreateDialog = recordIssueTile.delegateFactory.create(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$showPrompt$dialog$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RecordIssueTile recordIssueTile2 = recordIssueTile;
                            recordIssueTile2.getClass();
                            IssueRecordingService.Companion companion = IssueRecordingService.Companion;
                            UserContextProvider userContextProvider = recordIssueTile2.userContextProvider;
                            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userContextProvider;
                            Context userContext = userTrackerImpl.getUserContext();
                            LinkedHashMap linkedHashMap = IssueRecordingState.ALL_ISSUE_TYPES;
                            IssueRecordingState issueRecordingState = recordIssueTile2.issueRecordingState;
                            TraceConfig traceConfig = (TraceConfig) linkedHashMap.get(Integer.valueOf(issueRecordingState.getIssueTypeRes()));
                            if (traceConfig == null) {
                                traceConfig = issueRecordingState.customTraceState.getTraceConfig();
                            }
                            boolean z = issueRecordingState.getPrefs().getBoolean("key_recordScreen", false);
                            boolean z2 = issueRecordingState.getPrefs().getBoolean("key_takeBugReport", false);
                            companion.getClass();
                            PendingIntent service = PendingIntent.getService(((UserTrackerImpl) userContextProvider).getUserContext(), 2, new Intent(userContext, (Class<?>) IssueRecordingService.class).setAction("com.android.systemui.screenrecord.START").putExtra("com.android.traceur.trace_type", traceConfig).putExtra("extra_screenRecord", z).putExtra("extra_bugReport", z2), 201326592);
                            PendingIntent service2 = PendingIntent.getService(((UserTrackerImpl) userContextProvider).getUserContext(), 2, IssueRecordingService.Companion.getStopIntent(userTrackerImpl.getUserContext()), 201326592);
                            RecordingController recordingController = recordIssueTile2.recordingController;
                            recordingController.mIsStarting = true;
                            recordingController.mStopIntent = service2;
                            RecordingController.AnonymousClass3 anonymousClass3 = recordingController.new AnonymousClass3(0L, 1000L, service);
                            recordingController.mCountDownTimer = anonymousClass3;
                            anonymousClass3.start();
                            recordIssueTile.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                            ((PanelInteractorImpl) recordIssueTile.panelInteractor).collapsePanels();
                        }
                    }).createDialog();
                    recordIssueTile.keyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$showPrompt$dismissAction$1
                        /* JADX WARN: Removed duplicated region for block: B:10:0x002c  */
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final boolean onDismiss() {
                            Expandable expandable3 = expandable2;
                            if (expandable3 != null) {
                                RecordIssueTile recordIssueTile2 = recordIssueTile;
                                if (((KeyguardStateControllerImpl) recordIssueTile2.keyguardStateController).mShowing) {
                                    systemUIDialogCreateDialog.show();
                                } else {
                                    DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable3.dialogTransitionController(new DialogCuj(58, "record_issue"));
                                    AlertDialog alertDialog = systemUIDialogCreateDialog;
                                    if (controllerDialogTransitionController != null) {
                                        TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                                        recordIssueTile2.dialogTransitionAnimator.show(alertDialog, controllerDialogTransitionController, false);
                                    } else {
                                        alertDialog.show();
                                    }
                                }
                            }
                            return false;
                        }
                    }, false, true);
                }
            });
            return;
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile.handleDestroy.1
            @Override // java.lang.Runnable
            public final void run() {
                RecordIssueTile.this.irsConnection.doUnBind();
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(final boolean z) {
        super.handleSetListening(z);
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile.handleSetListening.1
            @Override // java.lang.Runnable
            public final void run() {
                if (z) {
                    RecordIssueTile recordIssueTile = this;
                    recordIssueTile.issueRecordingState.addListener(recordIssueTile.onRecordingChangeListener);
                    return;
                }
                RecordIssueTile recordIssueTile2 = this;
                IssueRecordingState issueRecordingState = recordIssueTile2.issueRecordingState;
                issueRecordingState.listeners.remove(recordIssueTile2.onRecordingChangeListener);
                if (issueRecordingState.listeners.isEmpty()) {
                    issueRecordingState.resolver.unregisterContentObserver(issueRecordingState.onRecordingChangeListener);
                }
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return Build.IS_DEBUGGABLE;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = getTileLabel();
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        CharSequence charSequence;
        if (this.issueRecordingState.isRecording) {
            booleanState.value = true;
            booleanState.state = 2;
            booleanState.forceExpandIcon = false;
            booleanState.secondaryLabel = this.mContext.getString(R.string.qs_record_issue_stop);
            int i = QsInCompose.$r8$clinit;
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.qs_record_issue_icon_on);
        } else {
            booleanState.value = false;
            booleanState.state = 1;
            booleanState.forceExpandIcon = true;
            booleanState.secondaryLabel = this.mContext.getString(R.string.qs_record_issue_start);
            int i2 = QsInCompose.$r8$clinit;
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.qs_record_issue_icon_off);
        }
        booleanState.label = getTileLabel();
        if (TextUtils.isEmpty(booleanState.secondaryLabel)) {
            charSequence = booleanState.label;
        } else {
            charSequence = ((Object) booleanState.label) + ", " + ((Object) booleanState.secondaryLabel);
        }
        booleanState.contentDescription = charSequence;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }
}
