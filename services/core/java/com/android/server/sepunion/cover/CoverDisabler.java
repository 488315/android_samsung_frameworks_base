package com.android.server.sepunion.cover;

import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;

import com.samsung.android.sepunion.Log;

import java.util.ArrayList;

public final class CoverDisabler {
    public final PowerManager.WakeLock mDisableCoverManagerWakeLock;
    public final CoverDisablerHandler mHandler;
    public final Object mLock = new Object();
    public final ArrayList mDisableRecords = new ArrayList();
    public boolean mCoverManagerDisabled = false;
    public boolean mRealCoverSwitchState = true;

    public final class CoverDisablerHandler extends Handler {
        public CoverDisablerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            CoverDisabler coverDisabler = CoverDisabler.this;
            if (coverDisabler.mDisableCoverManagerWakeLock.isHeld()) {
                coverDisabler.mDisableCoverManagerWakeLock.release();
            }
        }
    }

    public final class DisableRecord implements IBinder.DeathRecipient {
        public boolean disable;
        public String pkg;
        public IBinder token;

        public DisableRecord() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.i("CoverManager_CoverDisabler", "binder died : pkg=" + this.pkg);
            synchronized (CoverDisabler.this.mLock) {
                CoverDisabler.this.disableCoverManagerLocked(false, this.token, this.pkg);
            }
            this.token.unlinkToDeath(this, 0);
        }
    }

    public CoverDisabler(Looper looper, Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mHandler = new CoverDisablerHandler(looper);
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "disable covermanager");
        this.mDisableCoverManagerWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
    }

    public final boolean disableCoverManagerLocked(boolean z, IBinder iBinder, String str) {
        boolean z2;
        if (iBinder == null || str == null) {
            return false;
        }
        Log.d(
                "CoverManager_CoverDisabler",
                "disableCoverManagerLocked : disable=" + z + " pkg=" + str + " token=" + iBinder);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            manageDisableListLocked(z, iBinder, str);
            int size = this.mDisableRecords.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    z2 = false;
                    break;
                }
                if (((DisableRecord) this.mDisableRecords.get(i)).disable) {
                    z2 = true;
                    break;
                }
                i++;
            }
            if (z2 == this.mCoverManagerDisabled) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            this.mCoverManagerDisabled = z2;
            if (!this.mDisableCoverManagerWakeLock.isHeld()) {
                this.mDisableCoverManagerWakeLock.acquire();
            }
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.arg1 = z ? 1 : 0;
            this.mHandler.sendMessage(obtain);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void manageDisableListLocked(boolean z, IBinder iBinder, String str) {
        DisableRecord disableRecord;
        int size = this.mDisableRecords.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                disableRecord = null;
                break;
            }
            disableRecord = (DisableRecord) this.mDisableRecords.get(i);
            if (disableRecord.token == iBinder) {
                break;
            } else {
                i++;
            }
        }
        if (!z || !iBinder.isBinderAlive()) {
            if (disableRecord != null) {
                this.mDisableRecords.remove(i);
                disableRecord.token.unlinkToDeath(disableRecord, 0);
                return;
            }
            return;
        }
        if (disableRecord == null) {
            disableRecord = new DisableRecord();
            try {
                iBinder.linkToDeath(disableRecord, 0);
                this.mDisableRecords.add(disableRecord);
            } catch (RemoteException unused) {
                return;
            }
        }
        disableRecord.disable = true;
        disableRecord.token = iBinder;
        disableRecord.pkg = str;
    }
}
