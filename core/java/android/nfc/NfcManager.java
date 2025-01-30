package android.nfc;

import android.content.Context;

/* loaded from: classes3.dex */
public final class NfcManager {
    private NfcAdapter mAdapter;
    private boolean mIsBinded;

    public NfcManager(Context context) {
        NfcAdapter adapter;
        this.mIsBinded = false;
        Context context2 = context.getApplicationContext();
        if (context2 == null) {
            throw new IllegalArgumentException("context not associated with any application (using a mock context?)");
        }
        try {
            adapter = NfcAdapter.getNfcAdapter(context2);
            this.mIsBinded = true;
        } catch (UnsupportedOperationException e) {
            this.mIsBinded = false;
            adapter = null;
        }
        this.mAdapter = adapter;
    }

    public void bindNfcService(Context context) {
        NfcAdapter adapter;
        if (this.mIsBinded) {
            return;
        }
        Context context2 = context.getApplicationContext();
        if (context2 == null) {
            throw new IllegalArgumentException("context not associated with any application (using a mock context?)");
        }
        try {
            adapter = NfcAdapter.getNfcAdapter(context2);
            this.mIsBinded = true;
        } catch (UnsupportedOperationException e) {
            this.mIsBinded = false;
            adapter = null;
        }
        this.mAdapter = adapter;
    }

    public NfcAdapter getDefaultAdapter() {
        return this.mAdapter;
    }
}
