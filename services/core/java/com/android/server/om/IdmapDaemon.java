package com.android.server.om;

import android.os.IBinder;
import android.os.IIdmap2;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemService;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.FgThread;

import java.io.File;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IdmapDaemon {
    public static IdmapDaemon sInstance;
    public volatile IIdmap2 mService;
    public final AtomicInteger mOpenedCount = new AtomicInteger();
    public final Object mIdmapToken = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Connection implements AutoCloseable {
        public final IIdmap2 mIdmap2;
        public boolean mOpened = true;

        public Connection(IIdmap2 iIdmap2) {
            synchronized (IdmapDaemon.this.mIdmapToken) {
                IdmapDaemon.this.mOpenedCount.incrementAndGet();
                this.mIdmap2 = iIdmap2;
            }
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            synchronized (IdmapDaemon.this.mIdmapToken) {
                try {
                    if (this.mOpened) {
                        this.mOpened = false;
                        if (IdmapDaemon.this.mOpenedCount.decrementAndGet() != 0) {
                            return;
                        }
                        FgThread.getHandler()
                                .postDelayed(
                                        new Runnable() { // from class:
                                            // com.android.server.om.IdmapDaemon$Connection$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                IdmapDaemon.Connection connection =
                                                        IdmapDaemon.Connection.this;
                                                synchronized (IdmapDaemon.this.mIdmapToken) {
                                                    try {
                                                        if (IdmapDaemon.this.mService == null
                                                                || IdmapDaemon.this.mOpenedCount
                                                                                .get()
                                                                        != 0) {
                                                            return;
                                                        }
                                                        try {
                                                            SystemService.stop("idmap2d");
                                                        } catch (RuntimeException e) {
                                                            Slog.w(
                                                                    "OverlayManager",
                                                                    "Failed to disable idmap2"
                                                                        + " daemon",
                                                                    e);
                                                        }
                                                        IdmapDaemon.this.mService = null;
                                                    } finally {
                                                    }
                                                }
                                            }
                                        },
                                        IdmapDaemon.this.mIdmapToken,
                                        10000L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static IBinder getIdmapService() {
        long uptimeMillis;
        try {
            SystemService.start("idmap2d");
        } catch (RuntimeException e) {
            Slog.wtf("OverlayManager", "Failed to enable idmap2 daemon", e);
            if (e.getMessage().contains("failed to set system property")) {
                return null;
            }
        }
        long uptimeMillis2 = SystemClock.uptimeMillis() + 5000;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = elapsedRealtime + 30000;
        do {
            IBinder service = ServiceManager.getService("idmap");
            if (service == null) {
                SystemClock.sleep(5L);
                uptimeMillis = SystemClock.uptimeMillis();
                if (uptimeMillis > uptimeMillis2) {
                    break;
                }
                elapsedRealtime = SystemClock.elapsedRealtime();
            } else {
                service.linkToDeath(new IdmapDaemon$$ExternalSyntheticLambda0(), 0);
                return service;
            }
        } while (elapsedRealtime <= j);
        throw new TimeoutException(
                TextUtils.formatSimple(
                        "Failed to connect to '%s' in %d/%d ms (spent %d/%d ms)",
                        new Object[] {
                            "idmap",
                            5000,
                            30000,
                            Long.valueOf((uptimeMillis - uptimeMillis2) + 5000),
                            Long.valueOf((elapsedRealtime - j) + 30000)
                        }));
    }

    public final Connection connect() {
        synchronized (this.mIdmapToken) {
            try {
                FgThread.getHandler().removeCallbacksAndMessages(this.mIdmapToken);
                if (this.mService != null) {
                    return new Connection(this.mService);
                }
                IBinder idmapService = getIdmapService();
                if (idmapService == null) {
                    return new Connection(null);
                }
                this.mService = IIdmap2.Stub.asInterface(idmapService);
                return new Connection(this.mService);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String createIdmap(
            String str, int i, String str2, String str3, int i2, boolean z) {
        Connection connect = connect();
        try {
            IIdmap2 iIdmap2 = connect.mIdmap2;
            if (iIdmap2 != null) {
                String createIdmap =
                        iIdmap2.createIdmap(str, str2, TextUtils.emptyIfNull(str3), i, z, i2);
                connect.close();
                return createIdmap;
            }
            Slog.w(
                    "OverlayManager",
                    "idmap2d service is not ready for createIdmap(\""
                            + str
                            + "\", \""
                            + str2
                            + "\", \""
                            + str3
                            + "\", "
                            + i
                            + ", "
                            + z
                            + ", "
                            + i2
                            + ")");
            connect.close();
            return null;
        } catch (Throwable th) {
            try {
                connect.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    public final String getTargetPath(String str) {
        Connection connect = connect();
        try {
            IIdmap2 iIdmap2 = connect.mIdmap2;
            if (iIdmap2 != null) {
                String targetPath = iIdmap2.getTargetPath(str);
                connect.close();
                return targetPath;
            }
            Slog.w(
                    "OverlayManager",
                    "idmap2d service is not ready for getTargetPath(\"" + str + ")");
            connect.close();
            return null;
        } catch (Throwable th) {
            try {
                connect.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final boolean idmapExists(int i, String str) {
        try {
            Connection connect = connect();
            try {
                IIdmap2 iIdmap2 = connect.mIdmap2;
                if (iIdmap2 != null) {
                    boolean isFile = new File(iIdmap2.getIdmapPath(str, i)).isFile();
                    connect.close();
                    return isFile;
                }
                Slog.w(
                        "OverlayManager",
                        "idmap2d service is not ready for idmapExists(\"" + str + "\", " + i + ")");
                connect.close();
                return false;
            } finally {
            }
        } catch (Exception e) {
            Slog.wtf("OverlayManager", "failed to check if idmap exists for " + str, e);
            return false;
        }
    }

    public final boolean removeIdmap(int i, String str) {
        Connection connect = connect();
        try {
            IIdmap2 iIdmap2 = connect.mIdmap2;
            if (iIdmap2 != null) {
                boolean removeIdmap = iIdmap2.removeIdmap(str, i);
                connect.close();
                return removeIdmap;
            }
            Slog.w(
                    "OverlayManager",
                    "idmap2d service is not ready for removeIdmap(\"" + str + "\", " + i + ")");
            connect.close();
            return false;
        } catch (Throwable th) {
            try {
                connect.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final boolean verifyIdmap(
            String str, int i, String str2, String str3, int i2, boolean z) {
        Connection connect = connect();
        try {
            IIdmap2 iIdmap2 = connect.mIdmap2;
            if (iIdmap2 != null) {
                boolean verifyIdmap =
                        iIdmap2.verifyIdmap(str, str2, TextUtils.emptyIfNull(str3), i, z, i2);
                connect.close();
                return verifyIdmap;
            }
            Slog.w(
                    "OverlayManager",
                    "idmap2d service is not ready for verifyIdmap(\""
                            + str
                            + "\", \""
                            + str2
                            + "\", \""
                            + str3
                            + "\", "
                            + i
                            + ", "
                            + z
                            + ", "
                            + i2
                            + ")");
            connect.close();
            return false;
        } catch (Throwable th) {
            try {
                connect.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }
}
