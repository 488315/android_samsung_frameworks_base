package com.android.systemui.screenrecord;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate;
import com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder;
import com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder$setStartButtonOnClickListener$1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenRecordPermissionDialogDelegate extends BaseMediaProjectionPermissionDialogDelegate implements SystemUIDialog.Delegate {
    public final ActivityStarter activityStarter;
    public final Context context;
    public final RecordingController controller;
    public final DisplayManager displayManager;
    public final int hostUid;
    public final UserHandle hostUserHandle;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public final Runnable onStartRecordingClicked;
    public final SystemUIDialog.Factory systemUIDialogFactory;
    public final int theme;
    public final UserContextProvider userContextProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ScreenRecordPermissionDialogDelegate create(RecordingController recordingController, UserHandle userHandle, int i, Runnable runnable);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenRecordPermissionDialogDelegate(UserHandle userHandle, int i, RecordingController recordingController, ActivityStarter activityStarter, UserContextProvider userContextProvider, Runnable runnable, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, SystemUIDialog.Factory factory, int i2, int i3, Context context, DisplayManager displayManager) {
        super(ScreenRecordPermissionViewBinder.Companion.createOptionList(displayManager), null, i, mediaProjectionMetricsLogger, Integer.valueOf(R.drawable.ic_screenrecord), Integer.valueOf(R.color.screenrecord_icon_color), i2);
        ScreenRecordPermissionViewBinder.Companion.getClass();
        this.hostUserHandle = userHandle;
        this.hostUid = i;
        this.controller = recordingController;
        this.activityStarter = activityStarter;
        this.userContextProvider = userContextProvider;
        this.onStartRecordingClicked = runnable;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.systemUIDialogFactory = factory;
        this.theme = i3;
        this.context = context;
        this.displayManager = displayManager;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        return this.systemUIDialogFactory.create(this, this.context, this.theme, true);
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate
    public final BaseMediaProjectionPermissionViewBinder createViewBinder() {
        return new ScreenRecordPermissionViewBinder(this.hostUserHandle, this.hostUid, this.mediaProjectionMetricsLogger, this.defaultSelectedMode, this.displayManager, this.controller, this.activityStarter, this.userContextProvider, this.onStartRecordingClicked);
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate, com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(final SystemUIDialog systemUIDialog, Bundle bundle) {
        super.onCreate((AlertDialog) systemUIDialog, bundle);
        setDialogTitle(R.string.screenrecord_permission_dialog_title);
        systemUIDialog.setTitle(R.string.screenrecord_title);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionDialogDelegate$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseMediaProjectionPermissionViewBinder baseMediaProjectionPermissionViewBinder = ScreenRecordPermissionDialogDelegate.this.viewBinder;
                if (baseMediaProjectionPermissionViewBinder == null) {
                    baseMediaProjectionPermissionViewBinder = null;
                }
                ScreenRecordPermissionViewBinder screenRecordPermissionViewBinder = (ScreenRecordPermissionViewBinder) baseMediaProjectionPermissionViewBinder;
                if (screenRecordPermissionViewBinder != null) {
                    screenRecordPermissionViewBinder.startButtonOnClicked();
                }
                systemUIDialog.dismiss();
            }
        };
        BaseMediaProjectionPermissionViewBinder baseMediaProjectionPermissionViewBinder = this.viewBinder;
        if (baseMediaProjectionPermissionViewBinder == null) {
            baseMediaProjectionPermissionViewBinder = null;
        }
        TextView textView = baseMediaProjectionPermissionViewBinder.startButton;
        if (textView == null) {
            textView = null;
        }
        textView.setOnClickListener(new BaseMediaProjectionPermissionViewBinder$setStartButtonOnClickListener$1(baseMediaProjectionPermissionViewBinder, onClickListener));
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionDialogDelegate$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog.this.dismiss();
            }
        };
        TextView textView2 = this.cancelButton;
        (textView2 != null ? textView2 : null).setOnClickListener(onClickListener2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScreenRecordPermissionDialogDelegate(UserHandle userHandle, int i, RecordingController recordingController, ActivityStarter activityStarter, UserContextProvider userContextProvider, Runnable runnable, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, SystemUIDialog.Factory factory, Context context, DisplayManager displayManager) {
        this(userHandle, i, recordingController, activityStarter, userContextProvider, runnable, mediaProjectionMetricsLogger, factory, 0, R.style.Theme_SystemUI_Dialog, context, displayManager);
        int i2 = SystemUIDialog.$r8$clinit;
    }
}
