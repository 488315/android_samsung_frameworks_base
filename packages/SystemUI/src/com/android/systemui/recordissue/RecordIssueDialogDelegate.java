package com.android.systemui.recordissue;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.SessionCreationSource;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDisabledDialogDelegate;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RecordIssueDialogDelegate implements SystemUIDialog.Delegate {
    public final Executor bgExecutor;
    public final Lazy devicePolicyResolver;
    public final SystemUIDialog.Factory factory;
    public final FeatureFlagsClassic flags;
    public Button issueTypeButton;
    public final Executor mainExecutor;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public final Runnable onStarted;
    public final ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate;
    public Switch screenRecordSwitch;
    public final IssueRecordingState state;
    public final TraceurMessageSender traceurMessageSender;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        RecordIssueDialogDelegate create(Runnable runnable);
    }

    public RecordIssueDialogDelegate(SystemUIDialog.Factory factory, UserTracker userTracker, FeatureFlagsClassic featureFlagsClassic, Executor executor, Executor executor2, Lazy lazy, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate, IssueRecordingState issueRecordingState, TraceurMessageSender traceurMessageSender, Runnable runnable) {
        this.factory = factory;
        this.userTracker = userTracker;
        this.flags = featureFlagsClassic;
        this.bgExecutor = executor;
        this.mainExecutor = executor2;
        this.devicePolicyResolver = lazy;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.screenCaptureDisabledDialogDelegate = screenCaptureDisabledDialogDelegate;
        this.state = issueRecordingState;
        this.traceurMessageSender = traceurMessageSender;
        this.onStarted = runnable;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        final SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        systemUIDialog.setView(LayoutInflater.from(systemUIDialog.getContext()).inflate(R.layout.record_issue_dialog, (ViewGroup) null));
        systemUIDialog.setTitle(systemUIDialog.getContext().getString(R.string.qs_record_issue_label));
        systemUIDialog.setIcon(R.drawable.qs_record_issue_icon_off);
        systemUIDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$beforeCreate$1$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        systemUIDialog.setPositiveButton(R.string.qs_record_issue_start, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$beforeCreate$1$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                RecordIssueDialogDelegate.this.onStarted.run();
            }
        });
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$beforeCreate$2
            @Override // java.lang.Runnable
            public final void run() {
                TraceurMessageSender traceurMessageSender = RecordIssueDialogDelegate.this.traceurMessageSender;
                Context context = systemUIDialog.getContext();
                if (traceurMessageSender.isBound) {
                    return;
                }
                try {
                    context.bindService(new Intent().setClassName(context.getPackageManager().getPackageInfo("com.android.traceur", QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING).packageName, "com.android.traceur.BindableTraceService"), traceurMessageSender.traceurConnection, 33554465);
                } catch (Exception e) {
                    Log.e("TraceurMessageSender", "failed to bind to Traceur's service", e);
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.factory;
        return factory.create(this, factory.mContext);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        Window window = systemUIDialog.getWindow();
        if (window != null) {
            window.addPrivateFlags(16);
            window.setGravity(17);
        }
        Switch r7 = (Switch) systemUIDialog.requireViewById(R.id.screenrecord_switch);
        IssueRecordingState issueRecordingState = this.state;
        r7.setChecked(issueRecordingState.prefs.getBoolean("key_recordScreen", false));
        r7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$2$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                RecordIssueDialogDelegate.this.state.prefs.edit().putBoolean("key_recordScreen", z).apply();
                if (z) {
                    final RecordIssueDialogDelegate recordIssueDialogDelegate = RecordIssueDialogDelegate.this;
                    recordIssueDialogDelegate.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$2$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final RecordIssueDialogDelegate recordIssueDialogDelegate2 = RecordIssueDialogDelegate.this;
                            recordIssueDialogDelegate2.getClass();
                            ReleasedFlag releasedFlag = Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING_ENTERPRISE_POLICIES;
                            FeatureFlagsClassicRelease featureFlagsClassicRelease = (FeatureFlagsClassicRelease) recordIssueDialogDelegate2.flags;
                            boolean isEnabled = featureFlagsClassicRelease.isEnabled(releasedFlag);
                            UserTracker userTracker = recordIssueDialogDelegate2.userTracker;
                            if (isEnabled && ((ScreenCaptureDevicePolicyResolver) recordIssueDialogDelegate2.devicePolicyResolver.get()).isScreenCaptureCompletelyDisabled(UserHandle.of(((UserTrackerImpl) userTracker).getUserId()))) {
                                recordIssueDialogDelegate2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onScreenRecordSwitchClicked$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate = RecordIssueDialogDelegate.this.screenCaptureDisabledDialogDelegate;
                                        screenCaptureDisabledDialogDelegate.getClass();
                                        SystemUIDialog systemUIDialog2 = new SystemUIDialog(screenCaptureDisabledDialogDelegate.context);
                                        screenCaptureDisabledDialogDelegate.initDialog(systemUIDialog2);
                                        systemUIDialog2.show();
                                        Switch r3 = RecordIssueDialogDelegate.this.screenRecordSwitch;
                                        if (r3 == null) {
                                            r3 = null;
                                        }
                                        r3.setChecked(false);
                                    }
                                });
                                return;
                            }
                            recordIssueDialogDelegate2.mediaProjectionMetricsLogger.notifyProjectionInitiated(((UserTrackerImpl) userTracker).getUserId(), SessionCreationSource.SYSTEM_UI_SCREEN_RECORDER);
                            if (!featureFlagsClassicRelease.isEnabled(Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING) || recordIssueDialogDelegate2.state.prefs.getBoolean("HasApprovedScreenRecord", false)) {
                                return;
                            }
                            recordIssueDialogDelegate2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onScreenRecordSwitchClicked$2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RecordIssueDialogDelegate recordIssueDialogDelegate3 = RecordIssueDialogDelegate.this;
                                    SystemUIDialog createDialog = new ScreenCapturePermissionDialogDelegate(recordIssueDialogDelegate3.factory, recordIssueDialogDelegate3.state).createDialog();
                                    final RecordIssueDialogDelegate recordIssueDialogDelegate4 = RecordIssueDialogDelegate.this;
                                    createDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onScreenRecordSwitchClicked$2$1$1
                                        @Override // android.content.DialogInterface.OnCancelListener
                                        public final void onCancel(DialogInterface dialogInterface) {
                                            Switch r0 = RecordIssueDialogDelegate.this.screenRecordSwitch;
                                            if (r0 == null) {
                                                r0 = null;
                                            }
                                            r0.setChecked(false);
                                        }
                                    });
                                    createDialog.show();
                                }
                            });
                        }
                    });
                }
            }
        });
        this.screenRecordSwitch = r7;
        Switch r72 = (Switch) systemUIDialog.requireViewById(R.id.bugreport_switch);
        r72.setChecked(issueRecordingState.prefs.getBoolean("key_takeBugReport", false));
        r72.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$3$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                RecordIssueDialogDelegate.this.state.prefs.edit().putBoolean("key_takeBugReport", z).apply();
            }
        });
        final Button button = (Button) systemUIDialog.requireViewById(R.id.issue_type_button);
        final Button button2 = systemUIDialog.getButton(-1);
        if (issueRecordingState.prefs.getInt("key_issueTypeRes", -1) != -1) {
            button.setText(issueRecordingState.prefs.getInt("key_issueTypeRes", -1));
        } else {
            button2.setEnabled(false);
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$4$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final RecordIssueDialogDelegate recordIssueDialogDelegate = RecordIssueDialogDelegate.this;
                Context context = button.getContext();
                final Button button3 = button2;
                final Runnable runnable = new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$4$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        button3.setEnabled(true);
                    }
                };
                recordIssueDialogDelegate.getClass();
                Button button4 = recordIssueDialogDelegate.issueTypeButton;
                if (button4 == null) {
                    button4 = null;
                }
                PopupMenu popupMenu = new PopupMenu(context, button4);
                IssueRecordingState.Companion.getClass();
                Iterator it = ((HashMap) IssueRecordingState.ALL_ISSUE_TYPES).keySet().iterator();
                while (it.hasNext()) {
                    int intValue = ((Number) it.next()).intValue();
                    MenuItem add = popupMenu.getMenu().add(intValue);
                    add.setIcon(R.drawable.arrow_pointing_down);
                    if (intValue != recordIssueDialogDelegate.state.prefs.getInt("key_issueTypeRes", -1)) {
                        add.setIconTintList(ColorStateList.valueOf(0));
                    }
                    add.setIntent(new Intent().putExtra("key_issueTypeRes", intValue));
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onIssueTypeClicked$2$1
                    @Override // android.widget.PopupMenu.OnMenuItemClickListener
                    public final boolean onMenuItemClick(MenuItem menuItem) {
                        Button button5 = RecordIssueDialogDelegate.this.issueTypeButton;
                        if (button5 == null) {
                            button5 = null;
                        }
                        button5.setText(menuItem.getTitle());
                        IssueRecordingState issueRecordingState2 = RecordIssueDialogDelegate.this.state;
                        Intent intent = menuItem.getIntent();
                        issueRecordingState2.prefs.edit().putInt("key_issueTypeRes", intent != null ? intent.getIntExtra("key_issueTypeRes", -1) : -1).apply();
                        runnable.run();
                        return true;
                    }
                });
                popupMenu.setForceShowIcon(true);
                popupMenu.show();
            }
        });
        this.issueTypeButton = button;
    }
}
