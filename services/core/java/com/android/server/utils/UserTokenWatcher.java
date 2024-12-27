package com.android.server.utils;

import android.os.Handler;
import android.os.TokenWatcher;
import android.util.SparseArray;

import com.android.internal.util.IndentingPrintWriter;
import com.android.server.wm.KeyguardDisableHandler;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserTokenWatcher {
    public final KeyguardDisableHandler.AnonymousClass1 mCallback;
    public final Handler mHandler;
    public final SparseArray mWatchers = new SparseArray(1);
    public final String mTag = "WindowManager";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InnerTokenWatcher extends TokenWatcher {
        public final int mUserId;

        public InnerTokenWatcher(int i, Handler handler, String str) {
            super(handler, str);
            this.mUserId = i;
        }

        @Override // android.os.TokenWatcher
        public final void acquired() {
            KeyguardDisableHandler.AnonymousClass1 anonymousClass1 =
                    UserTokenWatcher.this.mCallback;
            KeyguardDisableHandler.this.updateKeyguardEnabled(this.mUserId);
        }

        @Override // android.os.TokenWatcher
        public final void released() {
            KeyguardDisableHandler.AnonymousClass1 anonymousClass1 =
                    UserTokenWatcher.this.mCallback;
            KeyguardDisableHandler.this.updateKeyguardEnabled(this.mUserId);
            synchronized (UserTokenWatcher.this.mWatchers) {
                try {
                    TokenWatcher tokenWatcher =
                            (TokenWatcher) UserTokenWatcher.this.mWatchers.get(this.mUserId);
                    if (tokenWatcher != null && !tokenWatcher.isAcquired()) {
                        UserTokenWatcher.this.mWatchers.remove(this.mUserId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public UserTokenWatcher(
            KeyguardDisableHandler.AnonymousClass1 anonymousClass1, Handler handler) {
        this.mCallback = anonymousClass1;
        this.mHandler = handler;
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mWatchers) {
            for (int i = 0; i < this.mWatchers.size(); i++) {
                try {
                    int keyAt = this.mWatchers.keyAt(i);
                    TokenWatcher tokenWatcher = (TokenWatcher) this.mWatchers.valueAt(i);
                    if (tokenWatcher.isAcquired()) {
                        printWriter.print("User ");
                        printWriter.print(keyAt);
                        printWriter.println(":");
                        tokenWatcher.dump(new IndentingPrintWriter(printWriter, " "));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final boolean isAcquired(int i) {
        boolean z;
        synchronized (this.mWatchers) {
            try {
                TokenWatcher tokenWatcher = (TokenWatcher) this.mWatchers.get(i);
                z = tokenWatcher != null && tokenWatcher.isAcquired();
            } finally {
            }
        }
        return z;
    }
}
