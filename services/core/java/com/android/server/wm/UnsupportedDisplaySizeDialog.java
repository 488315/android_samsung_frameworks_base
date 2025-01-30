package com.android.server.wm;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/* loaded from: classes3.dex */
public class UnsupportedDisplaySizeDialog extends AppWarnings.BaseDialog {
  public UnsupportedDisplaySizeDialog(
      final AppWarnings appWarnings, Context context, ApplicationInfo applicationInfo) {
    super(appWarnings, applicationInfo.packageName);
    AlertDialog create =
        new AlertDialog.Builder(context)
            .setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null)
            .setMessage(
                context.getString(
                    17043093,
                    applicationInfo.loadSafeLabel(context.getPackageManager(), 1000.0f, 5)))
            .setView(17367507)
            .create();
    this.mDialog = create;
    create.create();
    Window window = this.mDialog.getWindow();
    window.setType(2002);
    window.getAttributes().setTitle("UnsupportedDisplaySizeDialog");
    CheckBox checkBox = (CheckBox) this.mDialog.findViewById(R.id.autofill_dataset_list);
    checkBox.setChecked(true);
    checkBox.setOnCheckedChangeListener(
        new CompoundButton
            .OnCheckedChangeListener() { // from class:
                                         // com.android.server.wm.UnsupportedDisplaySizeDialog$$ExternalSyntheticLambda0
          @Override // android.widget.CompoundButton.OnCheckedChangeListener
          public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            UnsupportedDisplaySizeDialog.this.lambda$new$0(appWarnings, compoundButton, z);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(
      AppWarnings appWarnings, CompoundButton compoundButton, boolean z) {
    appWarnings.setPackageFlag(this.mPackageName, 1, !z);
  }
}
