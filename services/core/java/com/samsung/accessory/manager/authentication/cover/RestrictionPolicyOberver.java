package com.samsung.accessory.manager.authentication.cover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.util.Log;
import com.samsung.android.cover.CoverState;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RestrictionPolicyOberver {
    public static boolean sIsFelicaAllowed = true;
    public static NfcRestrictionPolicyListener sNfcRestrictionPolicyListener;
    public final Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface NfcRestrictionPolicyListener {
    }

    public RestrictionPolicyOberver(Context context) {
        new BroadcastReceiver() { // from class: com.samsung.accessory.manager.authentication.cover.RestrictionPolicyOberver.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                RestrictionPolicyOberver restrictionPolicyOberver = RestrictionPolicyOberver.this;
                restrictionPolicyOberver.getClass();
                NfcAdapter nfcAdapter = null;
                try {
                    nfcAdapter = NfcAdapter.getDefaultAdapter(restrictionPolicyOberver.mContext);
                    if (nfcAdapter == null) {
                        Log.e("SAccessoryManager_RestrictionPolicyOberver", "NfcAdapter.getDefaultAdapter returns null");
                        nfcAdapter = NfcAdapter.getDefaultAdapter(restrictionPolicyOberver.mContext);
                        if (nfcAdapter == null) {
                            Log.e("SAccessoryManager_RestrictionPolicyOberver", "retry, NfcAdapter.getDefaultAdapter returns null");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                if (!"com.samsung.android.nfc.INIT_READY".equals(action) || nfcAdapter == null) {
                    return;
                }
                int adapterState2 = nfcAdapter.getAdapterState();
                if (RestrictionPolicyOberver.sIsFelicaAllowed || adapterState2 != 3) {
                    return;
                }
                RestrictionPolicyOberver.sIsFelicaAllowed = true;
                NfcRestrictionPolicyListener nfcRestrictionPolicyListener = RestrictionPolicyOberver.sNfcRestrictionPolicyListener;
                if (nfcRestrictionPolicyListener != null) {
                    final CoverAuthenticator coverAuthenticator = (CoverAuthenticator) nfcRestrictionPolicyListener;
                    Log.d("SAccessoryManager_CoverAuthenticator", "onNfcRestrictionPolicyChanged = true");
                    coverAuthenticator.mCoverAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.10
                        public final /* synthetic */ boolean val$allowed = true;

                        public AnonymousClass10() {
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            CoverState coverState;
                            char c = !RestrictionPolicyOberver.sIsFelicaAllowed ? (char) 2 : (char) 0;
                            if (!this.val$allowed || CoverAuthenticator.this.getCoverSwitchState() < 1) {
                                return;
                            }
                            if (c == 0 && (coverState = CoverAuthenticator.this.mLastCoverState) != null && coverState.getAttachState()) {
                                CoverAuthenticator coverAuthenticator2 = CoverAuthenticator.this;
                                coverAuthenticator2.mAuthType = 4;
                                if (coverAuthenticator2.currentHall == 0) {
                                    coverAuthenticator2.setCoverVerified(false, null, null);
                                } else {
                                    coverAuthenticator2.setFriendsVerified(false, null, null);
                                }
                            }
                            CoverAuthenticator.this.startAuthentication(0L, true);
                        }
                    });
                }
            }
        };
        this.mContext = context;
    }
}
