package com.android.server.pm;

import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.util.Slog;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ShortcutBitmapSaver {
    public final Executor mExecutor =
            new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    public final Deque mPendingItems = new LinkedBlockingDeque();
    public final ShortcutBitmapSaver$$ExternalSyntheticLambda0 mRunnable =
            new ShortcutBitmapSaver$$ExternalSyntheticLambda0(0, this);
    public final ShortcutService mService;

    public final class PendingItem {
        public final byte[] bytes;
        public final long mInstantiatedUptimeMillis = SystemClock.uptimeMillis();
        public final ShortcutInfo shortcut;

        public PendingItem(ShortcutInfo shortcutInfo, byte[] bArr) {
            this.shortcut = shortcutInfo;
            this.bytes = bArr;
        }

        public final String toString() {
            return "PendingItem{size="
                    + this.bytes.length
                    + " age="
                    + (SystemClock.uptimeMillis() - this.mInstantiatedUptimeMillis)
                    + "ms shortcut="
                    + this.shortcut.toInsecureString()
                    + "}";
        }
    }

    public ShortcutBitmapSaver(ShortcutService shortcutService) {
        this.mService = shortcutService;
    }

    public static void removeIcon(ShortcutInfo shortcutInfo) {
        shortcutInfo.setIconResourceId(0);
        shortcutInfo.setIconResName(null);
        shortcutInfo.setBitmapPath(null);
        shortcutInfo.setIconUri(null);
        shortcutInfo.clearFlags(35340);
    }

    public final void dumpLocked(PrintWriter printWriter) {
        synchronized (this.mPendingItems) {
            try {
                int size = ((LinkedBlockingDeque) this.mPendingItems).size();
                printWriter.print("  ");
                printWriter.println("Pending saves: Num=" + size + " Executor=" + this.mExecutor);
                Iterator it = ((LinkedBlockingDeque) this.mPendingItems).iterator();
                while (it.hasNext()) {
                    PendingItem pendingItem = (PendingItem) it.next();
                    printWriter.print("  ");
                    printWriter.print("  ");
                    printWriter.println(pendingItem);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void saveBitmapLocked(
            ShortcutInfo shortcutInfo, int i, Bitmap.CompressFormat compressFormat, int i2) {
        Icon icon = shortcutInfo.getIcon();
        Objects.requireNonNull(icon);
        Bitmap bitmap = icon.getBitmap();
        if (bitmap == null) {
            Log.e("ShortcutService", "Missing icon: " + shortcutInfo);
            return;
        }
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            try {
                StrictMode.setThreadPolicy(
                        new StrictMode.ThreadPolicy.Builder(threadPolicy)
                                .permitCustomSlowCalls()
                                .build());
                Bitmap shrinkBitmap = ShortcutService.shrinkBitmap(i, bitmap);
                try {
                    ByteArrayOutputStream byteArrayOutputStream =
                            new ByteArrayOutputStream(
                                    EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
                    try {
                        if (!shrinkBitmap.compress(compressFormat, i2, byteArrayOutputStream)) {
                            Slog.wtf("ShortcutService", "Unable to compress bitmap");
                        }
                        byteArrayOutputStream.flush();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        byteArrayOutputStream.close();
                        StrictMode.setThreadPolicy(threadPolicy);
                        shortcutInfo.addFlags(2056);
                        if (icon.getType() == 5) {
                            shortcutInfo.addFlags(512);
                        }
                        PendingItem pendingItem = new PendingItem(shortcutInfo, byteArray);
                        synchronized (this.mPendingItems) {
                            ((LinkedBlockingDeque) this.mPendingItems).add(pendingItem);
                        }
                        ((ThreadPoolExecutor) this.mExecutor).execute(this.mRunnable);
                    } finally {
                    }
                } finally {
                    if (shrinkBitmap != bitmap) {
                        shrinkBitmap.recycle();
                    }
                }
            } catch (IOException | OutOfMemoryError | RuntimeException e) {
                Slog.wtf("ShortcutService", "Unable to write bitmap to file", e);
                StrictMode.setThreadPolicy(threadPolicy);
            }
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicy);
            throw th;
        }
    }

    public final boolean waitForAllSavesLocked() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ((ThreadPoolExecutor) this.mExecutor)
                .execute(new ShortcutBitmapSaver$$ExternalSyntheticLambda0(1, countDownLatch));
        try {
            if (countDownLatch.await(5000L, TimeUnit.MILLISECONDS)) {
                return true;
            }
            this.mService.wtf("Timed out waiting on saving bitmaps.", null);
            return false;
        } catch (InterruptedException unused) {
            Slog.w("ShortcutService", "interrupted");
            return false;
        }
    }
}
