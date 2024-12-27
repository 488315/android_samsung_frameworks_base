package com.android.server.incident;

import android.os.Handler;
import android.os.IBinder;

import java.util.ArrayList;

public final class RequestQueue {
    public final Handler mHandler;
    public boolean mStarted;
    public final ArrayList mPending = new ArrayList();
    public final AnonymousClass1 mWorker =
            new Runnable() { // from class: com.android.server.incident.RequestQueue.1
                @Override // java.lang.Runnable
                public final void run() {
                    ArrayList arrayList;
                    synchronized (RequestQueue.this.mPending) {
                        try {
                            if (RequestQueue.this.mPending.size() > 0) {
                                arrayList = new ArrayList(RequestQueue.this.mPending);
                                RequestQueue.this.mPending.clear();
                            } else {
                                arrayList = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (arrayList != null) {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            ((Rec) arrayList.get(i)).runnable.run();
                        }
                    }
                }
            };

    public final class Rec {
        public final IBinder key;
        public final Runnable runnable;
        public final boolean value;

        public Rec(IBinder iBinder, boolean z, Runnable runnable) {
            this.key = iBinder;
            this.value = z;
            this.runnable = runnable;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.incident.RequestQueue$1] */
    public RequestQueue(Handler handler) {
        this.mHandler = handler;
    }

    public final void enqueue(IBinder iBinder, boolean z, Runnable runnable) {
        synchronized (this.mPending) {
            if (!z) {
                try {
                    for (int size = this.mPending.size() - 1; size >= 0; size--) {
                        Rec rec = (Rec) this.mPending.get(size);
                        if (rec.key == iBinder && rec.value) {
                            this.mPending.remove(size);
                            break;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mPending.add(new Rec(iBinder, z, runnable));
            if (this.mStarted) {
                this.mHandler.post(this.mWorker);
            }
        }
    }
}
