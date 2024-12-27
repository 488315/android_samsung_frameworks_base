package com.android.systemui.qs.tiles;

import android.app.AlertDialog;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Flags;
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
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.recordissue.IssueRecordingService;
import com.android.systemui.recordissue.IssueRecordingState;
import com.android.systemui.recordissue.RecordIssueDialogDelegate;
import com.android.systemui.recordissue.TraceurMessageSender;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RecordIssueTile extends QSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor bgExecutor;
    public final RecordIssueDialogDelegate.Factory delegateFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final IssueRecordingState issueRecordingState;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final KeyguardStateController keyguardStateController;
    public final RecordIssueTile$onRecordingChangeListener$1 onRecordingChangeListener;
    public final PanelInteractor panelInteractor;
    public final TraceurMessageSender traceurMessageSender;
    public final UserContextProvider userContextProvider;

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

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.tiles.RecordIssueTile$onRecordingChangeListener$1] */
    public RecordIssueTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, UserContextProvider userContextProvider, TraceurMessageSender traceurMessageSender, Executor executor, IssueRecordingState issueRecordingState, RecordIssueDialogDelegate.Factory factory) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.keyguardDismissUtil = keyguardDismissUtil;
        this.keyguardStateController = keyguardStateController;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.panelInteractor = panelInteractor;
        this.userContextProvider = userContextProvider;
        this.traceurMessageSender = traceurMessageSender;
        this.bgExecutor = executor;
        this.issueRecordingState = issueRecordingState;
        this.delegateFactory = factory;
        this.onRecordingChangeListener = new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$onRecordingChangeListener$1
            @Override // java.lang.Runnable
            public final void run() {
                RecordIssueTile.this.refreshState(null);
            }
        };
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
    public void handleClick(final Expandable expandable) {
        if (!this.issueRecordingState.isRecording) {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$handleClick$1
                @Override // java.lang.Runnable
                public final void run() {
                    final RecordIssueTile recordIssueTile = RecordIssueTile.this;
                    final Expandable expandable2 = expandable;
                    int i = RecordIssueTile.$r8$clinit;
                    recordIssueTile.getClass();
                    final SystemUIDialog createDialog = recordIssueTile.delegateFactory.create(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$showPrompt$dialog$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) RecordIssueTile.this.userContextProvider;
                            Context userContext = userTrackerImpl.getUserContext();
                            IssueRecordingService.Companion companion = IssueRecordingService.Companion;
                            Context userContext2 = userTrackerImpl.getUserContext();
                            companion.getClass();
                            PendingIntent foregroundService = PendingIntent.getForegroundService(userContext, 2, new Intent(userContext2, (Class<?>) IssueRecordingService.class).setAction("com.android.systemui.screenrecord.START"), 201326592);
                            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                            makeBasic.setInteractive(true);
                            foregroundService.send(makeBasic.toBundle());
                            RecordIssueTile.this.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                            RecordIssueTile.this.panelInteractor.collapsePanels();
                        }
                    }).createDialog();
                    recordIssueTile.keyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$showPrompt$dismissAction$1
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean onDismiss() {
                            Unit unit;
                            Expandable expandable3 = Expandable.this;
                            if (expandable3 != null) {
                                RecordIssueTile recordIssueTile2 = recordIssueTile;
                                if (!((KeyguardStateControllerImpl) recordIssueTile2.keyguardStateController).mShowing) {
                                    DialogTransitionAnimator.Controller dialogTransitionController = expandable3.dialogTransitionController(new DialogCuj(58, "record_issue"));
                                    if (dialogTransitionController != null) {
                                        AlertDialog alertDialog = createDialog;
                                        TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                                        recordIssueTile2.dialogTransitionAnimator.show(alertDialog, dialogTransitionController, false);
                                        unit = Unit.INSTANCE;
                                    } else {
                                        unit = null;
                                    }
                                    if (unit == null) {
                                        createDialog.show();
                                    }
                                    return false;
                                }
                            }
                            createDialog.show();
                            return false;
                        }
                    }, false, true);
                }
            });
            return;
        }
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.userContextProvider;
        Context userContext = userTrackerImpl.getUserContext();
        IssueRecordingService.Companion companion = IssueRecordingService.Companion;
        Context userContext2 = userTrackerImpl.getUserContext();
        companion.getClass();
        PendingIntent service = PendingIntent.getService(userContext, 2, new Intent(userContext2, (Class<?>) IssueRecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", userContext2.getUserId()), 201326592);
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setInteractive(true);
        service.send(makeBasic.toBundle());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.RecordIssueTile$handleDestroy$1
            @Override // java.lang.Runnable
            public final void run() {
                RecordIssueTile recordIssueTile = RecordIssueTile.this;
                TraceurMessageSender traceurMessageSender = recordIssueTile.traceurMessageSender;
                Context context = recordIssueTile.mContext;
                if (traceurMessageSender.isBound) {
                    context.unbindService(traceurMessageSender.traceurConnection);
                }
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        RecordIssueTile$onRecordingChangeListener$1 recordIssueTile$onRecordingChangeListener$1 = this.onRecordingChangeListener;
        IssueRecordingState issueRecordingState = this.issueRecordingState;
        if (z) {
            issueRecordingState.listeners.add(recordIssueTile$onRecordingChangeListener$1);
        } else {
            issueRecordingState.listeners.remove(recordIssueTile$onRecordingChangeListener$1);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (!Build.IS_DEBUGGABLE) {
            return false;
        }
        Flags.FEATURE_FLAGS.getClass();
        return false;
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
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.qs_record_issue_icon_on);
        } else {
            booleanState.value = false;
            booleanState.state = 1;
            booleanState.forceExpandIcon = true;
            booleanState.secondaryLabel = this.mContext.getString(R.string.qs_record_issue_start);
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
