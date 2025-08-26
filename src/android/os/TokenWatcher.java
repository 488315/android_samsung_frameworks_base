package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.util.Log;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public abstract class TokenWatcher {
    private Handler mHandler;
    private String mTag;
    private Runnable mNotificationTask = new Runnable() { // from class: android.os.TokenWatcher.1
        @Override // java.lang.Runnable
        public void run() {
            int i;
            synchronized (TokenWatcher.this.mTokens) {
                i = TokenWatcher.this.mNotificationQueue;
                TokenWatcher.this.mNotificationQueue = -1;
            }
            if (i == 1) {
                TokenWatcher.this.acquired();
            } else if (i == 0) {
                TokenWatcher.this.released();
            }
        }
    };
    private WeakHashMap<IBinder, Death> mTokens = new WeakHashMap<>();
    private int mNotificationQueue = -1;
    private volatile boolean mAcquired = false;

    public abstract void acquired();

    public abstract void released();

    public TokenWatcher(Handler handler, String str) {
        this.mHandler = handler;
        this.mTag = str == null ? "TokenWatcher" : str;
    }

    public void acquire(IBinder iBinder, String str) {
        synchronized (this.mTokens) {
            if (this.mTokens.containsKey(iBinder)) {
                return;
            }
            int size = this.mTokens.size();
            Death death = new Death(iBinder, str);
            try {
                iBinder.linkToDeath(death, 0);
                this.mTokens.put(iBinder, death);
                if (size == 0 && !this.mAcquired) {
                    sendNotificationLocked(true);
                    this.mAcquired = true;
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public void cleanup(IBinder iBinder, boolean z) {
        synchronized (this.mTokens) {
            Death deathRemove = this.mTokens.remove(iBinder);
            if (z && deathRemove != null) {
                deathRemove.token.unlinkToDeath(deathRemove, 0);
                deathRemove.token = null;
            }
            if (this.mTokens.size() == 0 && this.mAcquired) {
                sendNotificationLocked(false);
                this.mAcquired = false;
            }
        }
    }

    public void release(IBinder iBinder) {
        cleanup(iBinder, true);
    }

    public boolean isAcquired() {
        boolean z;
        synchronized (this.mTokens) {
            z = this.mAcquired;
        }
        return z;
    }

    public void dump() {
        Iterator<String> it = dumpInternal().iterator();
        while (it.hasNext()) {
            Log.i(this.mTag, it.next());
        }
    }

    public void dump(PrintWriter printWriter) {
        Iterator<String> it = dumpInternal().iterator();
        while (it.hasNext()) {
            printWriter.println(it.next());
        }
    }

    private ArrayList<String> dumpInternal() {
        ArrayList<String> arrayList = new ArrayList<>();
        synchronized (this.mTokens) {
            Set<IBinder> setKeySet = this.mTokens.keySet();
            arrayList.add("Token count: " + this.mTokens.size());
            int i = 0;
            for (IBinder iBinder : setKeySet) {
                arrayList.add(NavigationBarInflaterView.SIZE_MOD_START + i + "] " + this.mTokens.get(iBinder).tag + " - " + iBinder);
                i++;
            }
        }
        return arrayList;
    }

    private void sendNotificationLocked(boolean z) {
        int i = this.mNotificationQueue;
        if (i == -1) {
            this.mNotificationQueue = z ? 1 : 0;
            this.mHandler.post(this.mNotificationTask);
        } else if (i != z) {
            this.mNotificationQueue = -1;
            this.mHandler.removeCallbacks(this.mNotificationTask);
        }
    }

    private class Death implements IBinder.DeathRecipient {
        String tag;
        IBinder token;

        Death(IBinder iBinder, String str) {
            this.token = iBinder;
            this.tag = str;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            TokenWatcher.this.cleanup(this.token, false);
        }

        protected void finalize() throws Throwable {
            try {
                if (this.token != null) {
                    Log.w(TokenWatcher.this.mTag, "cleaning up leaked reference: " + this.tag);
                    TokenWatcher.this.release(this.token);
                }
            } finally {
                super.finalize();
            }
        }
    }
}
