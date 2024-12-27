package com.android.server.locksettings.recoverablekeystore.storage;

import android.os.SystemClock;
import android.util.SparseArray;

import com.android.security.SecureBox;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteLockscreenValidationSessionStorage {
    final SparseArray mSessionsByUserId = new SparseArray(0);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockscreenVerificationSession {
        public final KeyPair mKeyPair;

        public LockscreenVerificationSession(KeyPair keyPair) {
            this.mKeyPair = keyPair;
        }
    }

    public final void finishSession(int i) {
        synchronized (this.mSessionsByUserId) {
            this.mSessionsByUserId.delete(i);
        }
    }

    public final LockscreenVerificationSession get(int i) {
        LockscreenVerificationSession lockscreenVerificationSession;
        synchronized (this.mSessionsByUserId) {
            lockscreenVerificationSession =
                    (LockscreenVerificationSession) this.mSessionsByUserId.get(i);
        }
        return lockscreenVerificationSession;
    }

    public final LockscreenVerificationSession startSession(int i) {
        LockscreenVerificationSession lockscreenVerificationSession;
        synchronized (this.mSessionsByUserId) {
            if (this.mSessionsByUserId.get(i) != null) {
                this.mSessionsByUserId.delete(i);
            }
            try {
                KeyPair genKeyPair = SecureBox.genKeyPair();
                SystemClock.elapsedRealtime();
                lockscreenVerificationSession = new LockscreenVerificationSession(genKeyPair);
                this.mSessionsByUserId.put(i, lockscreenVerificationSession);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return lockscreenVerificationSession;
    }
}
