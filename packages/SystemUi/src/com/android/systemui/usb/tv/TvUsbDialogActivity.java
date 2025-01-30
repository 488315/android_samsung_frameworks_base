package com.android.systemui.usb.tv;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.tv.TvBottomSheetActivity;
import com.android.systemui.usb.UsbDialogHelper;
import com.android.systemui.usb.UsbDisconnectedReceiver;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class TvUsbDialogActivity extends TvBottomSheetActivity implements View.OnClickListener {
    public UsbDialogHelper mDialogHelper;

    public final void initUI(CharSequence charSequence, CharSequence charSequence2) {
        TextView textView = (TextView) findViewById(R.id.bottom_sheet_title);
        TextView textView2 = (TextView) findViewById(R.id.bottom_sheet_body);
        ImageView imageView = (ImageView) findViewById(R.id.bottom_sheet_icon);
        ImageView imageView2 = (ImageView) findViewById(R.id.bottom_sheet_second_icon);
        Button button = (Button) findViewById(R.id.bottom_sheet_positive_button);
        Button button2 = (Button) findViewById(R.id.bottom_sheet_negative_button);
        textView.setText(charSequence);
        textView2.setText(charSequence2);
        imageView.setImageResource(android.R.drawable.ic_sysbar_quicksettings);
        imageView2.setVisibility(8);
        button.setText(android.R.string.ok);
        button.setOnClickListener(this);
        button2.setText(android.R.string.cancel);
        button2.setOnClickListener(this);
        button2.requestFocus();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.bottom_sheet_positive_button) {
            onConfirm();
        } else {
            finish();
        }
    }

    public abstract void onConfirm();

    @Override // com.android.systemui.tv.TvBottomSheetActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(524288);
        try {
            this.mDialogHelper = new UsbDialogHelper(getApplicationContext(), getIntent());
        } catch (IllegalStateException e) {
            Log.e("TvUsbDialogActivity", "unable to initialize", e);
            finish();
        }
    }

    @Override // android.app.Activity
    public void onPause() {
        UsbDisconnectedReceiver usbDisconnectedReceiver;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (usbDialogHelper != null && (usbDisconnectedReceiver = usbDialogHelper.mDisconnectedReceiver) != null) {
            try {
                unregisterReceiver(usbDisconnectedReceiver);
            } catch (Exception unused) {
            }
            usbDialogHelper.mDisconnectedReceiver = null;
        }
        super.onPause();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (usbDialogHelper.mIsUsbDevice) {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver(this, usbDialogHelper.mDevice);
        } else {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver(this, usbDialogHelper.mAccessory);
        }
    }
}
