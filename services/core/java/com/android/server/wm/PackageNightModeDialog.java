package com.android.server.wm;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.view.Window;

/* loaded from: classes3.dex */
public class PackageNightModeDialog extends AppWarnings.BaseDialog {
  public PackageNightModeDialog(
      AppWarnings appWarnings, final Context context, ApplicationInfo applicationInfo) {
    super(appWarnings, applicationInfo.packageName);
    AlertDialog create =
        new AlertDialog.Builder(
                context,
                context.getResources().getConfiguration().isNightModeActive()
                    ? R.style.Theme.DeviceDefault.Dialog.Alert
                    : R.style.Theme.DeviceDefault.Light.Dialog.Alert)
            .setNegativeButton(R.string.roamingText3, (DialogInterface.OnClickListener) null)
            .setPositiveButton(
                R.string.roamingText5,
                new DialogInterface
                    .OnClickListener() { // from class:
                                         // com.android.server.wm.PackageNightModeDialog$$ExternalSyntheticLambda0
                  @Override // android.content.DialogInterface.OnClickListener
                  public final void onClick(DialogInterface dialogInterface, int i) {
                    PackageNightModeDialog.lambda$new$0(context, dialogInterface, i);
                  }
                })
            .setMessage(
                String.format(
                    context.getString(R.string.roamingText4),
                    applicationInfo.loadSafeLabel(context.getPackageManager(), 1000.0f, 5)))
            .setCancelable(false)
            .create();
    this.mDialog = create;
    create.setCanceledOnTouchOutside(false);
    this.mDialog.create();
    Window window = this.mDialog.getWindow();
    window.setType(2002);
    window.getAttributes().setTitle("PackageNightModeDialog");
  }

  public static /* synthetic */ void lambda$new$0(
      Context context, DialogInterface dialogInterface, int i) {
    Intent intent = new Intent();
    intent.setClassName(
        "com.android.settings", "com.android.settings.Settings$DarkModeAppsSettingsActivity");
    intent.addFlags(268468224);
    context.startActivity(intent);
  }
}
