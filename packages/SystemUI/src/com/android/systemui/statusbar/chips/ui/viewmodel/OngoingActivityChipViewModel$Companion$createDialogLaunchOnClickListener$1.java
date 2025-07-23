package com.android.systemui.statusbar.chips.ui.viewmodel;

import android.view.View;
import com.android.internal.logging.InstanceId;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.animation.ViewDialogTransitionAnimatorController;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.chips.ui.view.ChipBackgroundContainer;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipUiEvent;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.ongoingcall.StatusBarChipsModernization;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ DialogCuj $cuj;
    public final /* synthetic */ SystemUIDialog.Delegate $dialogDelegate;
    public final /* synthetic */ DialogTransitionAnimator $dialogTransitionAnimator;
    public final /* synthetic */ InstanceId $instanceId;
    public final /* synthetic */ LogBuffer $logger;
    public final /* synthetic */ String $tag;
    public final /* synthetic */ StatusBarChipsUiEventLogger $uiEventLogger;

    public OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1(LogBuffer logBuffer, String str, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger, InstanceId instanceId, SystemUIDialog.Delegate delegate, DialogTransitionAnimator dialogTransitionAnimator, DialogCuj dialogCuj) {
        this.$logger = logBuffer;
        this.$tag = str;
        this.$uiEventLogger = statusBarChipsUiEventLogger;
        this.$instanceId = instanceId;
        this.$dialogDelegate = delegate;
        this.$dialogTransitionAnimator = dialogTransitionAnimator;
        this.$cuj = dialogCuj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        LogBuffer logBuffer = this.$logger;
        logBuffer.commit(logBuffer.obtain(this.$tag, LogLevel.INFO, new CallChipViewModel$$ExternalSyntheticLambda0(1), null));
        StatusBarChipsUiEventLogger statusBarChipsUiEventLogger = this.$uiEventLogger;
        statusBarChipsUiEventLogger.logger.log(StatusBarChipUiEvent.STATUS_BAR_CHIP_TAP_TO_SHOW, this.$instanceId);
        SystemUIDialog createDialog = this.$dialogDelegate.createDialog();
        ChipBackgroundContainer chipBackgroundContainer = (ChipBackgroundContainer) view.requireViewById(R.id.ongoing_activity_chip_background);
        DialogTransitionAnimator dialogTransitionAnimator = this.$dialogTransitionAnimator;
        DialogCuj dialogCuj = this.$cuj;
        TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
        dialogTransitionAnimator.getClass();
        DialogTransitionAnimator.Controller.Companion.getClass();
        ViewDialogTransitionAnimatorController fromView = DialogTransitionAnimator.Controller.Companion.fromView(chipBackgroundContainer, dialogCuj);
        if (fromView == null) {
            createDialog.show();
        } else {
            dialogTransitionAnimator.show(createDialog, fromView, false);
        }
    }
}
