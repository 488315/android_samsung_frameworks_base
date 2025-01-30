package com.android.server.wm;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.view.Window;

/* loaded from: classes3.dex */
public class DeprecatedAbiDialog extends AppWarnings.BaseDialog {
  public DeprecatedAbiDialog(
      final AppWarnings appWarnings, Context context, ApplicationInfo applicationInfo) {
    super(appWarnings, applicationInfo.packageName);
    AlertDialog create =
        new AlertDialog.Builder(context)
            .setPositiveButton(
                R.string.ok,
                new DialogInterface
                    .OnClickListener() { // from class:
                                         // com.android.server.wm.DeprecatedAbiDialog$$ExternalSyntheticLambda0
                  @Override // android.content.DialogInterface.OnClickListener
                  public final void onClick(DialogInterface dialogInterface, int i) {
                    DeprecatedAbiDialog.this.lambda$new$0(appWarnings, dialogInterface, i);
                  }
                })
            .setMessage(context.getString(R.string.font_family_display_2_material))
            .setTitle(applicationInfo.loadSafeLabel(context.getPackageManager(), 1000.0f, 5))
            .create();
    this.mDialog = create;
    create.create();
    Window window = this.mDialog.getWindow();
    window.setType(2002);
    window.getAttributes().setTitle("DeprecatedAbiDialog");
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(
      AppWarnings appWarnings, DialogInterface dialogInterface, int i) {
    appWarnings.setPackageFlag(this.mPackageName, 8, true);
  }
}
