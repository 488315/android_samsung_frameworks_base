package com.android.server.locksettings.recoverablekeystore.storage;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.security.auth.Destroyable;

public final class RecoverySessionStorage implements Destroyable {
    public final SparseArray mSessionsByUid = new SparseArray();

    public final class Entry implements Destroyable {
        public final byte[] mKeyClaimant;
        public final byte[] mLskfHash;
        public final String mSessionId;
        public final byte[] mVaultParams;

        public Entry(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) {
            this.mLskfHash = bArr;
            this.mSessionId = str;
            this.mKeyClaimant = bArr2;
            this.mVaultParams = bArr3;
        }

        @Override // javax.security.auth.Destroyable
        public final void destroy() {
            Arrays.fill(this.mLskfHash, (byte) 0);
            Arrays.fill(this.mKeyClaimant, (byte) 0);
        }
    }

    @Override // javax.security.auth.Destroyable
    public final void destroy() {
        int size = this.mSessionsByUid.size();
        for (int i = 0; i < size; i++) {
            Iterator it = ((ArrayList) this.mSessionsByUid.valueAt(i)).iterator();
            while (it.hasNext()) {
                ((Entry) it.next()).destroy();
            }
        }
    }

    public final void remove(int i) {
        ArrayList arrayList = (ArrayList) this.mSessionsByUid.get(i);
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Entry) it.next()).destroy();
        }
        this.mSessionsByUid.remove(i);
    }
}
