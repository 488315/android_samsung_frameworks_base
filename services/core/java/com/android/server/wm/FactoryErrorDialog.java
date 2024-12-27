package com.android.server.wm;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.android.server.am.BaseErrorDialog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FactoryErrorDialog extends BaseErrorDialog {
    public final AnonymousClass1 mHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.FactoryErrorDialog$1, reason: invalid class name */
    public final class AnonymousClass1 extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            throw new RuntimeException("Rebooting from failed factory test");
        }
    }

    public FactoryErrorDialog(Context context, CharSequence charSequence) {
        super(context);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        setCancelable(false);
        setTitle(context.getText(R.string.lockscreen_emergency_call));
        setMessage(charSequence);
        setButton(
                -1,
                context.getText(R.string.lockscreen_failed_attempts_now_wiping),
                anonymousClass1.obtainMessage(0));
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Factory Error");
        getWindow().setAttributes(attributes);
    }

    @Override // com.android.server.am.BaseErrorDialog
    public final void closeDialog() {}
}
