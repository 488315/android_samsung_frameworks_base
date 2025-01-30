package com.android.systemui.controls.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.settings.SecureSettings;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsSettingsDialogManagerImpl implements ControlsSettingsDialogManager {
    public final ActivityStarter activityStarter;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public AlertDialog customDialog;
    public AlertDialog dialog;
    public final Function2 dialogProvider;
    public final SecureSettings secureSettings;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DialogListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
        public final int attempts;
        public final Function0 onComplete;
        public final SharedPreferences prefs;

        public DialogListener(SharedPreferences sharedPreferences, int i, Function0 function0) {
            this.prefs = sharedPreferences;
            this.attempts = i;
            this.onComplete = function0;
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            if (dialogInterface == null) {
                return;
            }
            if (this.attempts < 2) {
                this.prefs.edit().putInt("show_settings_attempts", this.attempts + 1).apply();
            }
            this.onComplete.invoke();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            if (dialogInterface == null) {
                return;
            }
            if (i == -1) {
                final List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf("lockscreen_allow_trivial_controls");
                if (!ControlsSettingsDialogManagerImpl.this.getShowDeviceControlsInLockscreen()) {
                    mutableListOf.add("lockscreen_show_controls");
                }
                final ControlsSettingsDialogManagerImpl controlsSettingsDialogManagerImpl = ControlsSettingsDialogManagerImpl.this;
                final Function0 function0 = this.onComplete;
                controlsSettingsDialogManagerImpl.getClass();
                controlsSettingsDialogManagerImpl.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$turnOnSettingSecurely$action$1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        for (String str : mutableListOf) {
                            ControlsSettingsDialogManagerImpl controlsSettingsDialogManagerImpl2 = controlsSettingsDialogManagerImpl;
                            controlsSettingsDialogManagerImpl2.secureSettings.putIntForUser(1, ((UserTrackerImpl) controlsSettingsDialogManagerImpl2.userTracker).getUserId(), str);
                        }
                        function0.invoke();
                        return true;
                    }
                }, new Runnable() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$sam$java_lang_Runnable$0
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        Function0.this.invoke();
                    }
                }, true);
            } else {
                this.onComplete.invoke();
            }
            if (this.attempts != 2) {
                this.prefs.edit().putInt("show_settings_attempts", 2).apply();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsDialog extends AlertDialog {
        public SettingsDialog(Context context, int i) {
            super(context, i);
        }
    }

    public ControlsSettingsDialogManagerImpl(SecureSettings secureSettings, UserFileManager userFileManager, ControlsSettingsRepository controlsSettingsRepository, UserTracker userTracker, ActivityStarter activityStarter, Function2 function2) {
        this.secureSettings = secureSettings;
        this.userFileManager = userFileManager;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.dialogProvider = function2;
    }

    public final void closeDialog() {
        AlertDialog alertDialog;
        AlertDialog alertDialog2;
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        if (!z && (alertDialog2 = this.dialog) != null) {
            alertDialog2.dismiss();
        }
        if (!z || (alertDialog = this.customDialog) == null) {
            return;
        }
        alertDialog.dismiss();
    }

    public final boolean getShowDeviceControlsInLockscreen() {
        return ((Boolean) ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).canShowControlsInLockscreen.getValue()).booleanValue();
    }

    public final void maybeShowDialog(Context context, Function0 function0) {
        closeDialog();
        SharedPreferences sharedPreferences = ((UserFileManagerImpl) this.userFileManager).getSharedPreferences(((UserTrackerImpl) this.userTracker).getUserId(), "controls_prefs");
        int i = sharedPreferences.getInt("show_settings_attempts", 0);
        if (i >= 2 || (getShowDeviceControlsInLockscreen() && ((Boolean) ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.getValue()).booleanValue())) {
            function0.invoke();
            return;
        }
        DialogListener dialogListener = new DialogListener(sharedPreferences, i, function0);
        AlertDialog alertDialog = (AlertDialog) this.dialogProvider.invoke(context, 2132018527);
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setOnCancelListener(dialogListener);
        alertDialog.setButton(-3, alertDialog.getContext().getText(R.string.controls_settings_dialog_neutral_button), dialogListener);
        alertDialog.setButton(-1, alertDialog.getContext().getText(R.string.controls_settings_dialog_positive_button), dialogListener);
        if (getShowDeviceControlsInLockscreen()) {
            alertDialog.setTitle(R.string.controls_settings_trivial_controls_dialog_title);
            alertDialog.setMessage(alertDialog.getContext().getText(R.string.controls_settings_trivial_controls_dialog_message));
        } else {
            alertDialog.setTitle(R.string.controls_settings_show_controls_dialog_title);
            alertDialog.setMessage(alertDialog.getContext().getText(R.string.controls_settings_show_controls_dialog_message));
        }
        SystemUIDialog.registerDismissListener(alertDialog, new Runnable() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$maybeShowDialog$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsSettingsDialogManagerImpl.this.dialog = null;
            }
        });
        SystemUIDialog.setDialogSize(alertDialog);
        SystemUIDialog.setShowForAllUsers(alertDialog);
        this.dialog = alertDialog;
        alertDialog.show();
    }

    public ControlsSettingsDialogManagerImpl(SecureSettings secureSettings, UserFileManager userFileManager, ControlsSettingsRepository controlsSettingsRepository, UserTracker userTracker, ActivityStarter activityStarter) {
        this(secureSettings, userFileManager, controlsSettingsRepository, userTracker, activityStarter, new Function2() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new SettingsDialog((Context) obj, ((Number) obj2).intValue());
            }
        });
    }
}
