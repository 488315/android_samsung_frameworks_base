package com.samsung.accessory.manager.authentication.cover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.util.Log;

/* loaded from: classes.dex */
public class RestrictionPolicyOberver {
    public static NfcRestrictionPolicyListener sNfcRestrictionPolicyListener;
    public final Context mContext;
    public static final String TAG = "SAccessoryManager_" + RestrictionPolicyOberver.class.getSimpleName();
    public static boolean sIsFelicaAllowed = true;
    public static boolean sPendingFelicaNotification = false;
    public boolean mFelicaReceiverInitialized = false;
    public boolean mBootCompleted = false;
    public BroadcastReceiver mFelicaReceiver = new BroadcastReceiver() { // from class: com.samsung.accessory.manager.authentication.cover.RestrictionPolicyOberver.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            NfcAdapter nfcAdapter = RestrictionPolicyOberver.this.getNfcAdapter();
            if ("android.nfc.action.ADAPTER_STATE_CHANGED".equals(action)) {
                if (nfcAdapter != null) {
                    int adapterState = nfcAdapter.getAdapterState();
                    if (adapterState == 4 || adapterState == 1) {
                        RestrictionPolicyOberver.sIsFelicaAllowed = false;
                        return;
                    }
                    return;
                }
                return;
            }
            if (!"com.sec.android.nfc.AUTH_READY".equals(action) || nfcAdapter == null) {
                return;
            }
            int adapterState2 = nfcAdapter.getAdapterState();
            if (RestrictionPolicyOberver.sIsFelicaAllowed) {
                return;
            }
            if (adapterState2 == 3 || adapterState2 == 5) {
                RestrictionPolicyOberver.sIsFelicaAllowed = true;
                if (RestrictionPolicyOberver.sNfcRestrictionPolicyListener != null) {
                    RestrictionPolicyOberver.sNfcRestrictionPolicyListener.onNfcRestrictionPolicyChanged(RestrictionPolicyOberver.sIsFelicaAllowed);
                }
            }
        }
    };

    public interface NfcRestrictionPolicyListener {
        void onNfcRestrictionPolicyChanged(boolean z);
    }

    public void setNfcRestrictionPolicyListener(NfcRestrictionPolicyListener nfcRestrictionPolicyListener) {
        sNfcRestrictionPolicyListener = nfcRestrictionPolicyListener;
    }

    public static int getBlockedType() {
        return !sIsFelicaAllowed ? 2 : 0;
    }

    public static boolean isAuthenticatonAllowed() {
        return sIsFelicaAllowed;
    }

    public RestrictionPolicyOberver(Context context) {
        this.mContext = context;
    }

    public void onBootCompleted() {
        this.mBootCompleted = true;
    }

    public void showFelicaNotification() {
        if (this.mBootCompleted) {
            return;
        }
        sPendingFelicaNotification = true;
    }

    public final NfcAdapter getNfcAdapter() {
        NfcAdapter nfcAdapter = null;
        try {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
            if (nfcAdapter == null) {
                String str = TAG;
                Log.e(str, "NfcAdapter.getDefaultAdapter returns null");
                nfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
                if (nfcAdapter == null) {
                    Log.e(str, "retry, NfcAdapter.getDefaultAdapter returns null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nfcAdapter;
    }
}
