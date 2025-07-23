package android.telephony;

import android.content.Context;
import android.hardware.gnss.GnssSignalType;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.android.internal.R;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
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
    private final HandlerThread mHandlerThread;
    private boolean mIsLoggingEnabled;
    private static final DateTimeFormatter LOG_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
    private static final ZoneId LOCAL_ZONE_ID = ZoneId.systemDefault();
    private final Object mBufferLock = new Object();
    private final StringBuilder mLogBuffer = new StringBuilder();
    private long mBufferStartTime = -1;

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
        HandlerThread handlerThread = new HandlerThread("DropBoxManagerLoggerBackend");
        this.mHandlerThread = handlerThread;
        this.mIsLoggingEnabled = false;
        this.mDropBoxManager = (DropBoxManager) context.getSystemService(DropBoxManager.class);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mDropBoxManagerLoggingEnabled = persistentLoggingEnabled(context);
    }

    private boolean persistentLoggingEnabled(Context context) {
        try {
            return context.getResources().getBoolean(R.bool.config_dropboxmanager_persistent_logging_enabled);
        } catch (RuntimeException unused) {
            Log.w("DropBoxManagerLoggerBackend", "Persistent logging config not found");
            return false;
        }
    }

    public void setLoggingEnabled(boolean z) {
        Log.i("DropBoxManagerLoggerBackend", "toggle logging: " + z);
        this.mIsLoggingEnabled = z;
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void debug(String str, String str2) {
        if (this.mDropBoxManagerLoggingEnabled) {
            bufferLog(GnssSignalType.CODE_TYPE_D, str, str2, Optional.empty());
        }
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void info(String str, String str2) {
        if (this.mDropBoxManagerLoggingEnabled) {
            bufferLog(GnssSignalType.CODE_TYPE_I, str, str2, Optional.empty());
        }
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void warn(String str, String str2) {
        if (this.mDropBoxManagerLoggingEnabled) {
            bufferLog(GnssSignalType.CODE_TYPE_W, str, str2, Optional.empty());
        }
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void warn(String str, String str2, Throwable th) {
        if (this.mDropBoxManagerLoggingEnabled) {
            bufferLog(GnssSignalType.CODE_TYPE_W, str, str2, Optional.of(th));
        }
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void error(String str, String str2) {
        if (this.mDropBoxManagerLoggingEnabled) {
            bufferLog("E", str, str2, Optional.empty());
        }
    }

    @Override // android.telephony.PersistentLoggerBackend
    public void error(String str, String str2, Throwable th) {
        if (this.mDropBoxManagerLoggingEnabled) {
            bufferLog("E", str, str2, Optional.of(th));
        }
    }

    private synchronized void bufferLog(String str, String str2, String str3, Optional<Throwable> optional) {
        if (this.mIsLoggingEnabled) {
            if (this.mBufferStartTime == -1) {
                this.mBufferStartTime = System.currentTimeMillis();
            }
            synchronized (this.mBufferLock) {
                StringBuilder sb = this.mLogBuffer;
                sb.append(formatLog(str, str2, str3, optional));
                sb.append(ShaderAssembler.NEWLINE);
                if (this.mLogBuffer.length() >= BUFFER_SIZE_BYTES) {
                    flushAsync();
                }
            }
        }
    }

    private String formatLog(String str, String str2, final String str3, Optional<Throwable> optional) {
        return formatTimestamp(System.currentTimeMillis()) + " " + str + " " + str2 + ": " + ((String) optional.map(new Function() { // from class: android.telephony.DropBoxManagerLoggerBackend$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DropBoxManagerLoggerBackend.lambda$formatLog$0(str3, (Throwable) obj);
            }
        }).orElse(str3));
    }

    static /* synthetic */ String lambda$formatLog$0(String str, Throwable th) {
        return str + ": " + Log.getStackTraceString(th);
    }

    private String formatTimestamp(long j) {
        return Instant.ofEpochMilli(j).atZone(LOCAL_ZONE_ID).format(LOG_TIMESTAMP_FORMATTER);
    }

    public void flushAsync() {
        if (this.mDropBoxManagerLoggingEnabled) {
            this.mHandler.post(new Runnable() { // from class: android.telephony.DropBoxManagerLoggerBackend$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DropBoxManagerLoggerBackend.this.flush();
                }
            });
        }
    }

    public void flush() {
        if (this.mDropBoxManagerLoggingEnabled) {
            synchronized (this.mBufferLock) {
                if (this.mLogBuffer.length() < 5120) {
                    return;
                }
                Log.d("DropBoxManagerLoggerBackend", "Flushing logs from " + formatTimestamp(this.mBufferStartTime) + " to " + formatTimestamp(System.currentTimeMillis()));
                try {
                    this.mDropBoxManager.addText("Telephony", this.mLogBuffer.toString());
                } catch (Exception e) {
                    Log.w("DropBoxManagerLoggerBackend", "Failed to flush logs of length " + this.mLogBuffer.length() + " to DropBoxManager", e);
                }
                this.mLogBuffer.setLength(0);
                this.mBufferStartTime = -1L;
            }
        }
    }
}
