package com.android.systemui.mediaprojection.permission;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.statusbar.phone.DialogDelegate;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class BaseMediaProjectionPermissionDialogDelegate implements DialogDelegate {
    public final String appName;
    public TextView cancelButton;
    public final int defaultSelectedMode;
    public AlertDialog dialog;
    public final Integer dialogIconDrawable;
    public final Integer dialogIconTint;
    public TextView dialogTitle;
    public final int hostUid;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public final List screenShareOptions;
    public BaseMediaProjectionPermissionViewBinder viewBinder;

    public BaseMediaProjectionPermissionDialogDelegate(List<ScreenShareOption> list, String str, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, Integer num, Integer num2, int i2) {
        this.screenShareOptions = list;
        this.appName = str;
        this.hostUid = i;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.dialogIconDrawable = num;
        this.dialogIconTint = num2;
        this.defaultSelectedMode = i2;
    }

    public BaseMediaProjectionPermissionViewBinder createViewBinder() {
        return new BaseMediaProjectionPermissionViewBinder(this.screenShareOptions, this.appName, this.hostUid, this.mediaProjectionMetricsLogger, this.defaultSelectedMode);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStop(Dialog dialog) {
        BaseMediaProjectionPermissionViewBinder baseMediaProjectionPermissionViewBinder = this.viewBinder;
        if (baseMediaProjectionPermissionViewBinder == null) {
            baseMediaProjectionPermissionViewBinder = null;
        }
        if (baseMediaProjectionPermissionViewBinder.shouldLogCancel) {
            MediaProjectionMetricsLogger mediaProjectionMetricsLogger = baseMediaProjectionPermissionViewBinder.mediaProjectionMetricsLogger;
            int i = baseMediaProjectionPermissionViewBinder.hostUid;
            mediaProjectionMetricsLogger.getClass();
            try {
                mediaProjectionMetricsLogger.service.notifyPermissionRequestCancelled(i);
            } catch (RemoteException e) {
                Log.e("MediaProjectionMetricsLogger", "Error notifying server of projection cancelled", e);
            }
            baseMediaProjectionPermissionViewBinder.shouldLogCancel = false;
        }
    }

    public final void setDialogTitle(int i) {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog == null) {
            alertDialog = null;
        }
        String string = alertDialog.getContext().getString(i, this.appName);
        TextView textView = this.dialogTitle;
        (textView != null ? textView : null).setText(string);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public void onCreate(AlertDialog alertDialog, Bundle bundle) {
        this.dialog = alertDialog;
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.addPrivateFlags(16);
        }
        Window window2 = alertDialog.getWindow();
        if (window2 != null) {
            window2.setGravity(17);
        }
        alertDialog.setContentView(R.layout.screen_share_dialog);
        this.dialogTitle = (TextView) alertDialog.requireViewById(R.id.screen_share_dialog_title);
        this.cancelButton = (TextView) alertDialog.requireViewById(android.R.id.button2);
        AlertDialog alertDialog2 = this.dialog;
        if (alertDialog2 == null) {
            alertDialog2 = null;
        }
        ImageView imageView = (ImageView) alertDialog2.requireViewById(R.id.screen_share_dialog_icon);
        Integer num = this.dialogIconTint;
        if (num != null) {
            AlertDialog alertDialog3 = this.dialog;
            if (alertDialog3 == null) {
                alertDialog3 = null;
            }
            imageView.setColorFilter(alertDialog3.getContext().getColor(num.intValue()));
        }
        Integer num2 = this.dialogIconDrawable;
        if (num2 != null) {
            AlertDialog alertDialog4 = this.dialog;
            if (alertDialog4 == null) {
                alertDialog4 = null;
            }
            imageView.setImageDrawable(alertDialog4.getContext().getDrawable(num2.intValue()));
        }
        if (this.viewBinder == null) {
            this.viewBinder = createViewBinder();
        }
        BaseMediaProjectionPermissionViewBinder baseMediaProjectionPermissionViewBinder = this.viewBinder;
        (baseMediaProjectionPermissionViewBinder != null ? baseMediaProjectionPermissionViewBinder : null).bind(alertDialog.requireViewById(R.id.screen_share_permission_dialog));
    }

    public BaseMediaProjectionPermissionDialogDelegate(List list, String str, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, Integer num, Integer num2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, str, i, mediaProjectionMetricsLogger, (i3 & 16) != 0 ? null : num, (i3 & 32) != 0 ? null : num2, (i3 & 64) != 0 ? ((ScreenShareOption) CollectionsKt___CollectionsKt.first(list)).mode : i2);
    }
}
