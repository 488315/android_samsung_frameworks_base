package android.telephony;

import android.content.Context;
import android.hardware.gnss.GnssSignalType;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.android.internal.R;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes4.dex */
public class DropBoxManagerLoggerBackend implements PersistentLoggerBackend {
    private static final int BUFFER_SIZE_BYTES = 512000;
    private static final String DROPBOX_TAG = "DropBoxManagerLoggerBackend";
    private static final int MIN_BUFFER_BYTES_FOR_FLUSH = 5120;
    private static final String TAG = "DropBoxManagerLoggerBackend";
    private static DropBoxManagerLoggerBackend sInstance;
    private final DropBoxManager mDropBoxManager;
    private final boolean mDropBoxManagerLoggingEnabled;
    private final Handler mHandler;
    private static final DateTimeFormatter LOG_TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
    private static final ZoneId LOCAL_ZONE_ID = ZoneId.systemDefault();
    private final Object mBufferLock = new Object();
    private final StringBuilder mLogBuffer = new StringBuilder();
    private long mBufferStartTime = -1;
    private final HandlerThread mHandlerThread = new HandlerThread("DropBoxManagerLoggerBackend");
    private boolean mIsLoggingEnabled = false;

    public static synchronized DropBoxManagerLoggerBackend getInstance(Context context) {
        DropBoxManagerLoggerBackend dropBoxManagerLoggerBackend;
        synchronized (DropBoxManagerLoggerBackend.class) {
            if (sInstance == null) {
                sInstance = new DropBoxManagerLoggerBackend(context);
            }
            dropBoxManagerLoggerBackend = sInstance;
        }
        return dropBoxManagerLoggerBackend;
    }

    private DropBoxManagerLoggerBackend(Context context) {
        this.mDropBoxManager = (DropBoxManager) context.getSystemService(DropBoxManager.class);
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        this.mDropBoxManagerLoggingEnabled = persistentLoggingEnabled(context);
    }

    private boolean persistentLoggingEnabled(Context context) {
        try {
            return context.getResources()
                    .getBoolean(R.bool.config_dropboxmanager_persistent_logging_enabled);
        } catch (RuntimeException e) {
            Log.w("DropBoxManagerLoggerBackend", "Persistent logging config not found");
            return false;
        }
    }

    public void setLoggingEnabled(boolean isLoggingEnabled) {
        Log.i("DropBoxManagerLoggerBackend", "toggle logging: " + isLoggingEnabled);
        this.mIsLoggingEnabled = isLoggingEnabled;
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void debug(String tag, String msg) {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        bufferLog(GnssSignalType.CODE_TYPE_D, tag, msg, Optional.empty());
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void info(String tag, String msg) {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        bufferLog(GnssSignalType.CODE_TYPE_I, tag, msg, Optional.empty());
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void warn(String tag, String msg) {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        bufferLog(GnssSignalType.CODE_TYPE_W, tag, msg, Optional.empty());
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void warn(String tag, String msg, Throwable t) {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        bufferLog(GnssSignalType.CODE_TYPE_W, tag, msg, Optional.of(t));
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void error(String tag, String msg) {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        bufferLog("E", tag, msg, Optional.empty());
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void error(String tag, String msg, Throwable t) {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        bufferLog("E", tag, msg, Optional.of(t));
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:27:0x003b
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    private synchronized void bufferLog(
            java.lang.String r5,
            java.lang.String r6,
            java.lang.String r7,
            java.util.Optional<java.lang.Throwable> r8) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.mIsLoggingEnabled     // Catch: java.lang.Throwable -> L3d
            if (r0 != 0) goto L7
            monitor-exit(r4)
            return
        L7:
            long r0 = r4.mBufferStartTime     // Catch: java.lang.Throwable -> L3d
            r2 = -1
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L15
            long r0 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L3d
            r4.mBufferStartTime = r0     // Catch: java.lang.Throwable -> L3d
        L15:
            java.lang.Object r0 = r4.mBufferLock     // Catch: java.lang.Throwable -> L3d
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L3d
            java.lang.StringBuilder r1 = r4.mLogBuffer     // Catch: java.lang.Throwable -> L38
            java.lang.String r2 = r4.formatLog(r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L38
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L38
            java.lang.String r2 = "\n"
            r1.append(r2)     // Catch: java.lang.Throwable -> L38
            java.lang.StringBuilder r1 = r4.mLogBuffer     // Catch: java.lang.Throwable -> L38
            int r1 = r1.length()     // Catch: java.lang.Throwable -> L38
            r2 = 512000(0x7d000, float:7.17465E-40)
            if (r1 < r2) goto L35
            r4.flushAsync()     // Catch: java.lang.Throwable -> L3b
        L35:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L38
            monitor-exit(r4)
            return
        L38:
            r1 = move-exception
        L39:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3b
            throw r1     // Catch: java.lang.Throwable -> L3d
        L3b:
            r1 = move-exception
            goto L39
        L3d:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.telephony.DropBoxManagerLoggerBackend.bufferLog(java.lang.String,"
                    + " java.lang.String, java.lang.String, java.util.Optional):void");
    }

    private String formatLog(String level, String tag, final String msg, Optional<Throwable> t) {
        return formatTimestamp(System.currentTimeMillis())
                + " "
                + level
                + " "
                + tag
                + ": "
                + ((String)
                        t.map(
                                        new Function() { // from class:
                                                         // android.telephony.DropBoxManagerLoggerBackend$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return DropBoxManagerLoggerBackend
                                                        .lambda$formatLog$0(msg, (Throwable) obj);
                                            }
                                        })
                                .orElse(msg));
    }

    static /* synthetic */ String lambda$formatLog$0(String msg, Throwable throwable) {
        return msg + ": " + Log.getStackTraceString(throwable);
    }

    private String formatTimestamp(long currentTimeMillis) {
        return Instant.ofEpochMilli(currentTimeMillis)
                .atZone(LOCAL_ZONE_ID)
                .format(LOG_TIMESTAMP_FORMATTER);
    }

    public void flushAsync() {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.telephony.DropBoxManagerLoggerBackend$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DropBoxManagerLoggerBackend.this.flush();
                    }
                });
    }

    public void flush() {
        if (!this.mDropBoxManagerLoggingEnabled) {
            return;
        }
        synchronized (this.mBufferLock) {
            if (this.mLogBuffer.length() < 5120) {
                return;
            }
            Log.d(
                    "DropBoxManagerLoggerBackend",
                    "Flushing logs from "
                            + formatTimestamp(this.mBufferStartTime)
                            + " to "
                            + formatTimestamp(System.currentTimeMillis()));
            try {
                this.mDropBoxManager.addText("Telephony", this.mLogBuffer.toString());
            } catch (Exception e) {
                Log.w(
                        "DropBoxManagerLoggerBackend",
                        "Failed to flush logs of length "
                                + this.mLogBuffer.length()
                                + " to DropBoxManager",
                        e);
            }
            this.mLogBuffer.setLength(0);
            this.mBufferStartTime = -1L;
        }
    }
}
