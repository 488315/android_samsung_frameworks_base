package com.android.systemui.popup.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.systemui.basic.util.LogWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DataConnectionDataLimitDialog implements PopupUIAlertDialog {
    private static final String TAG = "DataConnectionDataLimitDialog";
    private Context mContext;
    private LogWrapper mLogWrapper;

    public DataConnectionDataLimitDialog(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public boolean isShowing() {
        return false;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public void show() {
        String subscriberId = ((TelephonyManager) this.mContext.getSystemService("phone")).getSubscriberId();
        if (TextUtils.isEmpty(subscriberId)) {
            this.mLogWrapper.d(TAG, "showDataConnectionNotifications() : Failed TelephonyManager.getSubscriberId");
            return;
        }
        NetworkPolicy[] networkPolicies = NetworkPolicyManager.from(this.mContext).getNetworkPolicies();
        boolean z = false;
        NetworkTemplate networkTemplate = null;
        if (networkPolicies != null) {
            int length = networkPolicies.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                networkTemplate = networkPolicies[i].template;
                if (networkTemplate.getSubscriberIds().contains(subscriberId)) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        if (!z) {
            this.mLogWrapper.d(TAG, "showDataConnectionNotifications() : hasPolicy is false");
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.net.NetworkOverLimitActivity"));
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
        try {
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public void dismiss() {
    }
}
