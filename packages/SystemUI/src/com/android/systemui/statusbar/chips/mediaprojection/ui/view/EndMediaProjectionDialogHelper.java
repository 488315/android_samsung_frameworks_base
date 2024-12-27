package com.android.systemui.statusbar.chips.mediaprojection.ui.view;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.Html;
import android.text.TextUtils;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EndMediaProjectionDialogHelper {
    public final Context context;
    public final SystemUIDialog.Factory dialogFactory;
    public final PackageManager packageManager;

    public EndMediaProjectionDialogHelper(SystemUIDialog.Factory factory, PackageManager packageManager, Context context) {
        this.dialogFactory = factory;
        this.packageManager = packageManager;
        this.context = context;
    }

    public final CharSequence getDialogMessage(ActivityManager.RunningTaskInfo runningTaskInfo, int i, int i2) {
        String packageName;
        if (runningTaskInfo == null) {
            return this.context.getString(i);
        }
        ComponentName component = runningTaskInfo.baseIntent.getComponent();
        if (component == null || (packageName = component.getPackageName()) == null) {
            return this.context.getString(i);
        }
        try {
            return Html.fromHtml(this.context.getString(i2, TextUtils.htmlEncode(this.packageManager.getApplicationInfo(packageName, 0).loadLabel(this.packageManager).toString())), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            String string = this.context.getString(i);
            Intrinsics.checkNotNull(string);
            return string;
        }
    }
}
