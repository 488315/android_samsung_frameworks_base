package com.android.systemui.usb;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

public class UsbAccessoryUriActivity extends AlertActivity implements DialogInterface.OnClickListener {
    public UsbAccessory mAccessory;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public Uri mUri;

    public UsbAccessoryUriActivity(DeviceProvisionedController deviceProvisionedController) {
        this.mDeviceProvisionedController = deviceProvisionedController;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Intent intent = new Intent("android.intent.action.VIEW", this.mUri);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            try {
                startActivityAsUser(intent, UserHandle.CURRENT);
            } catch (ActivityNotFoundException unused) {
                Log.e("UsbAccessoryUriActivity", "startActivity failed for " + this.mUri);
            }
        }
        finish();
    }

    public final void onCreate(Bundle bundle) {
        getWindow().addSystemFlags(524288);
        super.onCreate(bundle);
        if (!((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get()) {
            Log.e("UsbAccessoryUriActivity", "device not provisioned");
            finish();
            return;
        }
        Intent intent = getIntent();
        this.mAccessory = (UsbAccessory) intent.getParcelableExtra("accessory");
        String stringExtra = intent.getStringExtra("uri");
        Uri parse = stringExtra == null ? null : Uri.parse(stringExtra);
        this.mUri = parse;
        if (parse == null) {
            Log.e("UsbAccessoryUriActivity", "could not parse Uri " + stringExtra);
            finish();
            return;
        }
        String scheme = parse.getScheme();
        if (!"http".equals(scheme) && !"https".equals(scheme)) {
            Log.e("UsbAccessoryUriActivity", "Uri not http or https: " + this.mUri);
            finish();
            return;
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        String description = this.mAccessory.getDescription();
        alertParams.mTitle = description;
        if (description == null || description.length() == 0) {
            alertParams.mTitle = getString(R.string.title_usb_accessory);
        }
        alertParams.mMessage = getString(R.string.usb_accessory_uri_prompt, new Object[]{this.mUri});
        alertParams.mPositiveButtonText = getString(R.string.label_view);
        alertParams.mNegativeButtonText = getString(android.R.string.cancel);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonListener = this;
        setupAlert();
    }
}
