package com.android.systemui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.slice.SliceManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.BidiFormatter;
import android.util.EventLog;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class SlicePermissionActivity extends Activity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public CheckBox mAllCheckbox;
    public String mCallingPkg;
    public String mProviderPkg;
    public Uri mUri;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            ((SliceManager) getSystemService(SliceManager.class)).grantPermissionFromUser(this.mUri, this.mCallingPkg, this.mAllCheckbox.isChecked());
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            this.mUri = (Uri) getIntent().getParcelableExtra("slice_uri");
        } catch (Exception e) {
            Log.w("SlicePermissionActivity", "Failed to getParcelableExtra", e);
        }
        this.mCallingPkg = getIntent().getStringExtra("pkg");
        if (this.mUri == null || !"vnd.android.slice".equals(getContentResolver().getType(this.mUri)) || !"com.android.intent.action.REQUEST_SLICE_PERMISSION".equals(getIntent().getAction())) {
            Log.e("SlicePermissionActivity", "Intent is not valid");
            finish();
            return;
        }
        try {
            PackageManager packageManager = getPackageManager();
            this.mProviderPkg = packageManager.resolveContentProvider(this.mUri.getAuthority(), 128).applicationInfo.packageName;
            String stringExtra = getIntent().getStringExtra("provider_pkg");
            if (stringExtra != null && !this.mProviderPkg.equals(stringExtra)) {
                Uri referrer = getReferrer();
                String host = referrer == null ? null : referrer.getHost();
                int i = -1;
                if (host != null) {
                    try {
                        i = getPackageManager().getApplicationInfo(host, 0).uid;
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
                EventLog.writeEvent(1397638484, "159145361", Integer.valueOf(i));
            }
            String unicodeWrap = BidiFormatter.getInstance().unicodeWrap(packageManager.getApplicationInfo(this.mCallingPkg, 0).loadSafeLabel(packageManager, 1000.0f, 5).toString());
            String unicodeWrap2 = BidiFormatter.getInstance().unicodeWrap(packageManager.getApplicationInfo(this.mProviderPkg, 0).loadSafeLabel(packageManager, 1000.0f, 5).toString());
            AlertDialog create = new AlertDialog.Builder(this).setTitle(getString(R.string.slice_permission_title, new Object[]{unicodeWrap, unicodeWrap2})).setView(R.layout.slice_permission_request).setNegativeButton(R.string.slice_permission_deny, this).setPositiveButton(R.string.slice_permission_allow, this).setOnDismissListener(this).create();
            create.getWindow().addSystemFlags(524288);
            create.show();
            ((TextView) create.getWindow().getDecorView().findViewById(R.id.text1)).setText(getString(R.string.slice_permission_text_1, new Object[]{unicodeWrap2}));
            ((TextView) create.getWindow().getDecorView().findViewById(R.id.text2)).setText(getString(R.string.slice_permission_text_2, new Object[]{unicodeWrap2}));
            CheckBox checkBox = (CheckBox) create.getWindow().getDecorView().findViewById(R.id.slice_permission_checkbox);
            this.mAllCheckbox = checkBox;
            checkBox.setText(getString(R.string.slice_permission_checkbox, new Object[]{unicodeWrap}));
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("SlicePermissionActivity", "Couldn't find package", e2);
            finish();
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }
}
