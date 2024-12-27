package com.android.keyguard;

import android.R;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
class KeyguardEsimArea extends Button implements View.OnClickListener {
    public final EuiccManager mEuiccManager;
    public final AnonymousClass1 mReceiver;
    public int mSubscriptionId;

    public KeyguardEsimArea(Context context) {
        this(context, null);
    }

    public static boolean isEsimLocked(int i, Context context) {
        SubscriptionInfo activeSubscriptionInfo;
        EuiccManager euiccManager = (EuiccManager) context.getSystemService("euicc");
        return euiccManager != null && euiccManager.isEnabled() && (activeSubscriptionInfo = SubscriptionManager.from(context).getActiveSubscriptionInfo(i)) != null && activeSubscriptionInfo.isEmbedded();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((Button) this).mContext.registerReceiver(this.mReceiver, new IntentFilter("com.android.keyguard.disable_esim"), "com.android.systemui.permission.SELF", null, 2);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (this.mEuiccManager == null) {
            Log.e("KeyguardEsimArea", "EuiccManager not present");
            return;
        }
        SubscriptionInfo activeSubscriptionInfo = SubscriptionManager.from(((Button) this).mContext).getActiveSubscriptionInfo(this.mSubscriptionId);
        if (activeSubscriptionInfo == null) {
            Log.e("KeyguardEsimArea", "No active subscription with subscriptionId: " + this.mSubscriptionId);
        } else {
            Intent intent = new Intent("com.android.keyguard.disable_esim");
            intent.setPackage(((Button) this).mContext.getPackageName());
            this.mEuiccManager.switchToSubscription(-1, activeSubscriptionInfo.getPortIndex(), PendingIntent.getBroadcastAsUser(((Button) this).mContext, 0, intent, 167772160, UserHandle.SYSTEM));
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        ((Button) this).mContext.unregisterReceiver(this.mReceiver);
        super.onDetachedFromWindow();
    }

    public KeyguardEsimArea(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardEsimArea(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Widget.Material.Button.Borderless);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.KeyguardEsimArea$1] */
    public KeyguardEsimArea(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardEsimArea.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int resultCode;
                if (!"com.android.keyguard.disable_esim".equals(intent.getAction()) || (resultCode = getResultCode()) == 0) {
                    return;
                }
                Log.e("KeyguardEsimArea", "Error disabling esim, result code = " + resultCode);
                AlertDialog create = new AlertDialog.Builder(((Button) KeyguardEsimArea.this).mContext).setMessage(com.android.systemui.R.string.error_disable_esim_msg).setTitle(com.android.systemui.R.string.error_disable_esim_title).setCancelable(false).setPositiveButton(com.android.systemui.R.string.ok, (DialogInterface.OnClickListener) null).create();
                create.getWindow().setType(2009);
                create.show();
            }
        };
        this.mEuiccManager = (EuiccManager) context.getSystemService("euicc");
        setOnClickListener(this);
    }
}
