package com.android.server.wm;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import com.android.server.am.BaseErrorDialog;

/* loaded from: classes3.dex */
public final class FactoryErrorDialog extends BaseErrorDialog {
    public final Handler mHandler;

    @Override // com.android.server.am.BaseErrorDialog
    public void closeDialog() {
    }

    public FactoryErrorDialog(Context context, CharSequence charSequence) {
        super(context);
        Handler handler = new Handler() { // from class: com.android.server.wm.FactoryErrorDialog.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                throw new RuntimeException("Rebooting from failed factory test");
            }
        };
        this.mHandler = handler;
        setCancelable(false);
        setTitle(context.getText(R.string.kg_login_submit_button));
        setMessage(charSequence);
        setButton(-1, context.getText(R.string.kg_password_instructions), handler.obtainMessage(0));
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Factory Error");
        getWindow().setAttributes(attributes);
    }
}
