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
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

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
            String strUnicodeWrap = BidiFormatter.getInstance().unicodeWrap(packageManager.getApplicationInfo(this.mCallingPkg, 0).loadSafeLabel(packageManager, 1000.0f, 5).toString());
            String strUnicodeWrap2 = BidiFormatter.getInstance().unicodeWrap(packageManager.getApplicationInfo(this.mProviderPkg, 0).loadSafeLabel(packageManager, 1000.0f, 5).toString());
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(getString(R.string.slice_permission_title, new Object[]{strUnicodeWrap, strUnicodeWrap2})).setView(R.layout.slice_permission_request).setNegativeButton(R.string.slice_permission_deny, this).setPositiveButton(R.string.slice_permission_allow, this).setOnDismissListener(this).create();
            alertDialogCreate.getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
            alertDialogCreate.show();
            ((TextView) alertDialogCreate.getWindow().getDecorView().findViewById(R.id.text1)).setText(getString(R.string.slice_permission_text_1, new Object[]{strUnicodeWrap2}));
            ((TextView) alertDialogCreate.getWindow().getDecorView().findViewById(R.id.text2)).setText(getString(R.string.slice_permission_text_2, new Object[]{strUnicodeWrap2}));
            CheckBox checkBox = (CheckBox) alertDialogCreate.getWindow().getDecorView().findViewById(R.id.slice_permission_checkbox);
            this.mAllCheckbox = checkBox;
            checkBox.setText(getString(R.string.slice_permission_checkbox, new Object[]{strUnicodeWrap}));
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
