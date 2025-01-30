package com.android.server.sepunion.cover;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class CoverDisabler {
    public static final String TAG = "CoverManager_" + CoverDisabler.class.getSimpleName();
    public PowerManager.WakeLock mDisableCoverManagerWakeLock;
    public final CoverDisablerHandler mHandler;
    public final PowerManager mPowerManager;
    public final Object mLock = new Object();
    public final ArrayList mDisableRecords = new ArrayList();
    public boolean mCoverManagerDisabled = false;
    public boolean mRealCoverSwitchState = true;

    public CoverDisabler(Looper looper, Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mHandler = new CoverDisablerHandler(looper);
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "disable covermanager");
        this.mDisableCoverManagerWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
    }

    public final class CoverDisablerHandler extends Handler {
        public CoverDisablerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            CoverDisabler.this.handleDisableCoverManagerLocked(message.arg1 == 1);
        }
    }

    public class DisableRecord implements IBinder.DeathRecipient {
        public boolean disable;
        public String pkg;
        public IBinder token;

        public DisableRecord() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.i(CoverDisabler.TAG, "binder died : pkg=" + this.pkg);
            synchronized (CoverDisabler.this.mLock) {
                CoverDisabler.this.disableCoverManagerLocked(false, this.token, this.pkg);
            }
            this.token.unlinkToDeath(this, 0);
        }
    }

    public void setRealCoverSwitchState(boolean z) {
        this.mRealCoverSwitchState = z;
    }

    public boolean getRealCoverSwitchState() {
        return this.mRealCoverSwitchState;
    }

    public boolean isCoverManagerDisabled() {
        return this.mCoverManagerDisabled;
    }

    public void sendCoverSwitchIntent(Context context, boolean z) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.sepunion.cover.SEND_COVER_SWITCH");
        intent.putExtra("switchState", z);
        Log.d(TAG, "sendCoverSwitchIntent");
        context.sendBroadcast(intent);
    }

    public boolean disableCoverManager(boolean z, IBinder iBinder, String str) {
        boolean disableCoverManagerLocked;
        synchronized (this.mLock) {
            disableCoverManagerLocked = disableCoverManagerLocked(z, iBinder, str);
        }
        return disableCoverManagerLocked;
    }

    public final boolean disableCoverManagerLocked(boolean z, IBinder iBinder, String str) {
        if (iBinder == null || str == null) {
            return false;
        }
        Log.d(TAG, "disableCoverManagerLocked : disable=" + z + " pkg=" + str + " token=" + iBinder);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            manageDisableListLocked(z, iBinder, str);
            boolean gatherDisableLocked = gatherDisableLocked();
            if (gatherDisableLocked == this.mCoverManagerDisabled) {
                return false;
            }
            this.mCoverManagerDisabled = gatherDisableLocked;
            if (!this.mDisableCoverManagerWakeLock.isHeld()) {
                this.mDisableCoverManagerWakeLock.acquire();
            }
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.arg1 = z ? 1 : 0;
            this.mHandler.sendMessage(obtain);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void handleDisableCoverManagerLocked(boolean z) {
        if (this.mDisableCoverManagerWakeLock.isHeld()) {
            this.mDisableCoverManagerWakeLock.release();
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

    public final boolean gatherDisableLocked() {
        int size = this.mDisableRecords.size();
        for (int i = 0; i < size; i++) {
            if (((DisableRecord) this.mDisableRecords.get(i)).disable) {
                return true;
            }
        }
        return false;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current CoverDisabler state:");
        synchronized (this.mLock) {
            printWriter.println("  mCoverManagerDisabled=" + this.mCoverManagerDisabled);
            if (this.mCoverManagerDisabled) {
                printWriter.println("  Real Cover Switch State=" + this.mRealCoverSwitchState);
            }
            int size = this.mDisableRecords.size();
            printWriter.println("  mDisableRecords.size=" + size);
            for (int i = 0; i < size; i++) {
                DisableRecord disableRecord = (DisableRecord) this.mDisableRecords.get(i);
                printWriter.println("    [" + i + "] disable=" + disableRecord.disable + " pkg=" + disableRecord.pkg + " token=" + disableRecord.token);
            }
            printWriter.println("  ");
        }
    }
}
