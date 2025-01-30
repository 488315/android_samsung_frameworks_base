package com.samsung.android.server.pm.install;

import android.util.Slog;
import com.android.server.pm.PackageManagerServiceUtils;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public abstract class PdpUtils {
    public static boolean waitUntilInstalldConnected(final Supplier supplier) {
        long currentTimeMillis = System.currentTimeMillis();
        Future submit = Executors.newSingleThreadExecutor().submit(new Callable() { // from class: com.samsung.android.server.pm.install.PdpUtils$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                Boolean lambda$waitUntilInstalldConnected$0;
                lambda$waitUntilInstalldConnected$0 = PdpUtils.lambda$waitUntilInstalldConnected$0(supplier);
                return lambda$waitUntilInstalldConnected$0;
            }
        });
        try {
            submit.get(210L, TimeUnit.SECONDS);
            Slog.w("PackageManager", "Installd connected. Took " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
            return false;
        } catch (TimeoutException unused) {
            PackageManagerServiceUtils.logCriticalInfo(5, "Timeout. Installd connection failed.");
            submit.cancel(true);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static /* synthetic */ Boolean lambda$waitUntilInstalldConnected$0(Supplier supplier) {
        while (!((Boolean) supplier.get()).booleanValue()) {
            Slog.w("PackageManager", "installd not connected. Trying again");
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Boolean.TRUE;
    }
}
