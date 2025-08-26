package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.UserSwitcherController;

/* loaded from: classes.dex */
public final class GuestResetOrExitSessionReceiver extends BroadcastReceiver {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public SystemUIDialog mExitSessionDialog;
    public final ExitSessionDialogFactory mExitSessionDialogFactory;
    public SystemUIDialog mResetSessionDialog;
    public final ResetSessionDialogFactory mResetSessionDialogFactory;
    public final UserTracker mUserTracker;

    public class ExitSessionDialogClickListener implements DialogInterface.OnClickListener {
        public final DialogInterface mDialog;
        public final boolean mIsEphemeral;
        public final int mUserId;
        public final UserSwitcherController mUserSwitcherController;

        public interface Factory {
            ExitSessionDialogClickListener create(boolean z, int i, DialogInterface dialogInterface);
        }

        public ExitSessionDialogClickListener(UserSwitcherController userSwitcherController, boolean z, int i, DialogInterface dialogInterface) {
            this.mUserSwitcherController = userSwitcherController;
            this.mIsEphemeral = z;
            this.mUserId = i;
            this.mDialog = dialogInterface;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            if (this.mIsEphemeral) {
                if (i == -1) {
                    UserSwitcherController userSwitcherController = this.mUserSwitcherController;
                    userSwitcherController.getMUserSwitcherInteractor().exitGuestUser(this.mUserId, -10000, false);
                    return;
                } else {
                    if (i == -3) {
                        this.mDialog.cancel();
                        return;
                    }
                    return;
                }
            }
            if (i == -1) {
                UserSwitcherController userSwitcherController2 = this.mUserSwitcherController;
                userSwitcherController2.getMUserSwitcherInteractor().exitGuestUser(this.mUserId, -10000, false);
            } else if (i == -2) {
                UserSwitcherController userSwitcherController3 = this.mUserSwitcherController;
                userSwitcherController3.getMUserSwitcherInteractor().exitGuestUser(this.mUserId, -10000, true);
            } else if (i == -3) {
                this.mDialog.cancel();
            }
        }
    }

    public final class ExitSessionDialogFactory {
        public final ExitSessionDialogClickListener.Factory mClickListenerFactory;
        public final SystemUIDialog.Factory mDialogFactory;
        public final Resources mResources;

        public ExitSessionDialogFactory(SystemUIDialog.Factory factory, ExitSessionDialogClickListener.Factory factory2, Resources resources) {
            this.mDialogFactory = factory;
            this.mClickListenerFactory = factory2;
            this.mResources = resources;
        }
    }

    public class ResetSessionDialogClickListener implements DialogInterface.OnClickListener {
        public final DialogInterface mDialog;
        public final UiEventLogger mUiEventLogger;
        public final int mUserId;
        public final UserSwitcherController mUserSwitcherController;

        public interface Factory {
            ResetSessionDialogClickListener create(int i, DialogInterface dialogInterface);
        }

        public ResetSessionDialogClickListener(UserSwitcherController userSwitcherController, UiEventLogger uiEventLogger, int i, DialogInterface dialogInterface) {
            this.mUserSwitcherController = userSwitcherController;
            this.mUiEventLogger = uiEventLogger;
            this.mUserId = i;
            this.mDialog = dialogInterface;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            if (i != -1) {
                if (i == -3) {
                    this.mDialog.cancel();
                }
            } else {
                this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
                UserSwitcherController userSwitcherController = this.mUserSwitcherController;
                userSwitcherController.getMUserSwitcherInteractor().removeGuestUser(this.mUserId);
            }
        }
    }

    public final class ResetSessionDialogFactory {
        public final ResetSessionDialogClickListener.Factory mClickListenerFactory;
        public final SystemUIDialog.Factory mDialogFactory;
        public final Resources mResources;

        public ResetSessionDialogFactory(SystemUIDialog.Factory factory, Resources resources, ResetSessionDialogClickListener.Factory factory2) {
            this.mDialogFactory = factory;
            this.mResources = resources;
            this.mClickListenerFactory = factory2;
        }

        public final SystemUIDialog create(int i) {
            SystemUIDialog systemUIDialogCreate = this.mDialogFactory.create();
            ResetSessionDialogClickListener resetSessionDialogClickListenerCreate = this.mClickListenerFactory.create(i, systemUIDialogCreate);
            systemUIDialogCreate.setTitle(R.string.guest_reset_and_restart_dialog_title);
            systemUIDialogCreate.setMessage(this.mResources.getString(R.string.guest_reset_and_restart_dialog_message));
            systemUIDialogCreate.setButton(-3, this.mResources.getString(android.R.string.cancel), resetSessionDialogClickListenerCreate);
            systemUIDialogCreate.setButton(-1, this.mResources.getString(R.string.guest_reset_guest_confirm_button), resetSessionDialogClickListenerCreate);
            systemUIDialogCreate.setCanceledOnTouchOutside(false);
            return systemUIDialogCreate;
        }
    }

    public GuestResetOrExitSessionReceiver(UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, ResetSessionDialogFactory resetSessionDialogFactory, ExitSessionDialogFactory exitSessionDialogFactory) {
        this.mUserTracker = userTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mResetSessionDialogFactory = resetSessionDialogFactory;
        this.mExitSessionDialogFactory = exitSessionDialogFactory;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        SystemUIDialog systemUIDialog = this.mResetSessionDialog;
        if (systemUIDialog != null && systemUIDialog.isShowing()) {
            this.mResetSessionDialog.cancel();
            this.mResetSessionDialog = null;
        }
        SystemUIDialog systemUIDialog2 = this.mExitSessionDialog;
        if (systemUIDialog2 != null && systemUIDialog2.isShowing()) {
            this.mExitSessionDialog.cancel();
            this.mExitSessionDialog = null;
        }
        UserInfo userInfo = ((UserTrackerImpl) this.mUserTracker).getUserInfo();
        if (userInfo.isGuest()) {
            if ("android.intent.action.GUEST_RESET".equals(action)) {
                SystemUIDialog systemUIDialogCreate = this.mResetSessionDialogFactory.create(userInfo.id);
                this.mResetSessionDialog = systemUIDialogCreate;
                systemUIDialogCreate.show();
                return;
            }
            if ("android.intent.action.GUEST_EXIT".equals(action)) {
                ExitSessionDialogFactory exitSessionDialogFactory = this.mExitSessionDialogFactory;
                boolean zIsEphemeral = userInfo.isEphemeral();
                int i = userInfo.id;
                SystemUIDialog systemUIDialogCreate2 = exitSessionDialogFactory.mDialogFactory.create();
                ExitSessionDialogClickListener exitSessionDialogClickListenerCreate = exitSessionDialogFactory.mClickListenerFactory.create(zIsEphemeral, i, systemUIDialogCreate2);
                if (zIsEphemeral) {
                    systemUIDialogCreate2.setTitle(exitSessionDialogFactory.mResources.getString(R.string.guest_exit_dialog_title));
                    systemUIDialogCreate2.setMessage(exitSessionDialogFactory.mResources.getString(R.string.guest_exit_dialog_message));
                    systemUIDialogCreate2.setButton(-3, exitSessionDialogFactory.mResources.getString(android.R.string.cancel), exitSessionDialogClickListenerCreate);
                    systemUIDialogCreate2.setButton(-1, exitSessionDialogFactory.mResources.getString(R.string.guest_exit_dialog_button), exitSessionDialogClickListenerCreate);
                } else {
                    systemUIDialogCreate2.setTitle(exitSessionDialogFactory.mResources.getString(R.string.guest_exit_dialog_title_non_ephemeral));
                    systemUIDialogCreate2.setMessage(exitSessionDialogFactory.mResources.getString(R.string.guest_exit_dialog_message_non_ephemeral));
                    systemUIDialogCreate2.setButton(-3, exitSessionDialogFactory.mResources.getString(android.R.string.cancel), exitSessionDialogClickListenerCreate);
                    systemUIDialogCreate2.setButton(-2, exitSessionDialogFactory.mResources.getString(R.string.guest_exit_clear_data_button), exitSessionDialogClickListenerCreate);
                    systemUIDialogCreate2.setButton(-1, exitSessionDialogFactory.mResources.getString(R.string.guest_exit_save_data_button), exitSessionDialogClickListenerCreate);
                }
                systemUIDialogCreate2.setCanceledOnTouchOutside(false);
                this.mExitSessionDialog = systemUIDialogCreate2;
                systemUIDialogCreate2.show();
            }
        }
    }
}
