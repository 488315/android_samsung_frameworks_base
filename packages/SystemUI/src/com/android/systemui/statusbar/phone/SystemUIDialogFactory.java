package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;

public final class SystemUIDialogFactory {
    public final Context applicationContext;
    public final BroadcastDispatcher broadcastDispatcher;
    public final SystemUIDialogManager dialogManager;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final SysUiState sysUiState;

    public SystemUIDialogFactory(Context context, SystemUIDialogManager systemUIDialogManager, SysUiState sysUiState, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator) {
        this.applicationContext = context;
        this.dialogManager = systemUIDialogManager;
        this.sysUiState = sysUiState;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
    }
}
