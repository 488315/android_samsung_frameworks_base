package com.android.systemui.recordissue;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.SessionCreationSource;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDisabledDialogDelegate;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import dagger.Lazy;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;

/* loaded from: classes2.dex */
public final class RecordIssueDialogDelegate implements SystemUIDialog.Delegate {
    public final Executor bgExecutor;
    public final Lazy devicePolicyResolver;
    public final SystemUIDialog.Factory factory;
    public Button issueTypeButton;
    public final Executor mainExecutor;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public final Runnable onStarted;
    public final ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate;
    public Switch screenRecordSwitch;
    public final IssueRecordingState state;
    public final UserTracker userTracker;

    public interface Factory {
        RecordIssueDialogDelegate create(Runnable runnable);
    }

    public RecordIssueDialogDelegate(SystemUIDialog.Factory factory, UserTracker userTracker, FeatureFlagsClassic featureFlagsClassic, Executor executor, Executor executor2, Lazy lazy, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate, IssueRecordingState issueRecordingState, Runnable runnable) {
        this.factory = factory;
        this.userTracker = userTracker;
        this.bgExecutor = executor;
        this.mainExecutor = executor2;
        this.devicePolicyResolver = lazy;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.screenCaptureDisabledDialogDelegate = screenCaptureDisabledDialogDelegate;
        this.state = issueRecordingState;
        this.onStarted = runnable;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
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
                this.this$0.onStarted.run();
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
        Switch r6 = (Switch) systemUIDialog.requireViewById(R.id.screenrecord_switch);
        IssueRecordingState issueRecordingState = this.state;
        r6.setChecked(issueRecordingState.getPrefs().getBoolean("key_recordScreen", false));
        r6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$2$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.this$0.state.getPrefs().edit().putBoolean("key_recordScreen", z).apply();
                if (z) {
                    final RecordIssueDialogDelegate recordIssueDialogDelegate = this.this$0;
                    recordIssueDialogDelegate.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$2$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final RecordIssueDialogDelegate recordIssueDialogDelegate2 = recordIssueDialogDelegate;
                            ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver = (ScreenCaptureDevicePolicyResolver) recordIssueDialogDelegate2.devicePolicyResolver.get();
                            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) recordIssueDialogDelegate2.userTracker;
                            if (screenCaptureDevicePolicyResolver.isScreenCaptureCompletelyDisabled(UserHandle.of(userTrackerImpl.getUserId()))) {
                                recordIssueDialogDelegate2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onScreenRecordSwitchClicked$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate = recordIssueDialogDelegate2.screenCaptureDisabledDialogDelegate;
                                        screenCaptureDisabledDialogDelegate.getClass();
                                        SystemUIDialog systemUIDialog2 = new SystemUIDialog(screenCaptureDisabledDialogDelegate.context);
                                        screenCaptureDisabledDialogDelegate.initDialog(systemUIDialog2);
                                        systemUIDialog2.show();
                                        Switch r3 = recordIssueDialogDelegate2.screenRecordSwitch;
                                        if (r3 == null) {
                                            r3 = null;
                                        }
                                        r3.setChecked(false);
                                    }
                                });
                                return;
                            }
                            recordIssueDialogDelegate2.mediaProjectionMetricsLogger.notifyProjectionInitiated(userTrackerImpl.getUserId(), SessionCreationSource.SYSTEM_UI_SCREEN_RECORDER);
                            if (recordIssueDialogDelegate2.state.getPrefs().getBoolean("HasApprovedScreenRecord", false)) {
                                return;
                            }
                            recordIssueDialogDelegate2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onScreenRecordSwitchClicked$2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RecordIssueDialogDelegate recordIssueDialogDelegate3 = recordIssueDialogDelegate2;
                                    SystemUIDialog systemUIDialogCreateDialog = new ScreenCapturePermissionDialogDelegate(recordIssueDialogDelegate3.factory, recordIssueDialogDelegate3.state).createDialog();
                                    final RecordIssueDialogDelegate recordIssueDialogDelegate4 = recordIssueDialogDelegate2;
                                    systemUIDialogCreateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onScreenRecordSwitchClicked$2$1$1
                                        @Override // android.content.DialogInterface.OnCancelListener
                                        public final void onCancel(DialogInterface dialogInterface) {
                                            Switch r0 = recordIssueDialogDelegate4.screenRecordSwitch;
                                            if (r0 == null) {
                                                r0 = null;
                                            }
                                            r0.setChecked(false);
                                        }
                                    });
                                    systemUIDialogCreateDialog.show();
                                }
                            });
                        }
                    });
                }
            }
        });
        this.screenRecordSwitch = r6;
        Switch r62 = (Switch) systemUIDialog.requireViewById(R.id.bugreport_switch);
        r62.setChecked(issueRecordingState.getPrefs().getBoolean("key_takeBugReport", false));
        r62.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$3$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.this$0.state.getPrefs().edit().putBoolean("key_takeBugReport", z).apply();
            }
        });
        final Button button = (Button) systemUIDialog.requireViewById(R.id.issue_type_button);
        final Button button2 = systemUIDialog.getButton(-1);
        if (issueRecordingState.getIssueTypeRes() != -1) {
            button.setText(issueRecordingState.getIssueTypeRes());
        } else {
            button2.setEnabled(false);
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onCreate$1$4$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final RecordIssueDialogDelegate recordIssueDialogDelegate = this.this$0;
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
                final PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onIssueTypeClicked$onMenuItemClickListener$1
                    @Override // android.widget.PopupMenu.OnMenuItemClickListener
                    public final boolean onMenuItemClick(MenuItem menuItem) {
                        Button button5 = recordIssueDialogDelegate.issueTypeButton;
                        if (button5 == null) {
                            button5 = null;
                        }
                        button5.setText(menuItem.getTitle());
                        IssueRecordingState issueRecordingState2 = recordIssueDialogDelegate.state;
                        Intent intent = menuItem.getIntent();
                        int intExtra = intent != null ? intent.getIntExtra("extra_issueTypeRes", -1) : -1;
                        issueRecordingState2.getClass();
                        issueRecordingState2.getPrefs().edit().putInt("key_issueTypeIndex", ArraysKt___ArraysKt.indexOf(intExtra, CollectionsKt___CollectionsKt.toIntArray(IssueRecordingState.ALL_ISSUE_TYPES.keySet()))).apply();
                        runnable.run();
                        return true;
                    }
                };
                IssueRecordingState.Companion.getClass();
                for (Integer num : IssueRecordingState.ALL_ISSUE_TYPES.keySet()) {
                    Menu menu = popupMenu.getMenu();
                    num.getClass();
                    MenuItem menuItemAdd = menu.add(num.intValue());
                    menuItemAdd.setIcon(R.drawable.arrow_pointing_down);
                    if (num.intValue() != recordIssueDialogDelegate.state.getIssueTypeRes()) {
                        menuItemAdd.setIconTintList(ColorStateList.valueOf(0));
                    } else {
                        menuItemAdd.setContentDescription(context.getString(17042938) + " " + context.getString(num.intValue()));
                    }
                    menuItemAdd.setIntent(new Intent().putExtra("extra_issueTypeRes", num.intValue()));
                    if (num.intValue() == R.string.custom) {
                        menuItemAdd.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onIssueTypeClicked$1$1$1
                            @Override // android.view.MenuItem.OnMenuItemClickListener
                            public final boolean onMenuItemClick(final MenuItem menuItem) {
                                RecordIssueDialogDelegate recordIssueDialogDelegate2 = recordIssueDialogDelegate;
                                SystemUIDialog.Factory factory = recordIssueDialogDelegate2.factory;
                                IssueRecordingState issueRecordingState2 = recordIssueDialogDelegate2.state;
                                CustomTraceState customTraceState = issueRecordingState2.customTraceState;
                                SharedPreferences prefs = issueRecordingState2.getPrefs();
                                Set<String> set = EmptySet.INSTANCE;
                                Set<String> stringSet = prefs.getStringSet("key_tagTitles", set);
                                if (stringSet != null) {
                                    set = stringSet;
                                }
                                final PopupMenu.OnMenuItemClickListener onMenuItemClickListener2 = onMenuItemClickListener;
                                new CustomTraceSettingsDialogDelegate(factory, customTraceState, set, new Runnable() { // from class: com.android.systemui.recordissue.RecordIssueDialogDelegate$onIssueTypeClicked$1$1$1.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        onMenuItemClickListener2.onMenuItemClick(menuItem);
                                    }
                                }).createDialog().show();
                                return true;
                            }
                        });
                    }
                }
                popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
                popupMenu.setForceShowIcon(true);
                popupMenu.show();
            }
        });
        this.issueTypeButton = button;
    }
}
