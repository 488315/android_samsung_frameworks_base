package com.android.server.graphics.fonts;

import android.R;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.FontUpdateRequest;
import android.graphics.fonts.SystemFonts;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SharedMemory;
import android.os.ShellCallback;
import android.system.ErrnoException;
import android.text.FontConfig;
import android.util.AndroidException;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.internal.graphics.fonts.IFontManager;
import com.android.internal.security.VerityUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.SystemServerInitThreadPool;
import com.android.server.SystemService;
import com.android.text.flags.Flags;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.DirectByteBuffer;
import java.nio.NioUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FontManagerService extends IFontManager.Stub {
    public final Context mContext;
    public final boolean mIsSafeMode;
    public SharedMemory mSerializedFontMap;
    public final Object mSerializedFontMapLock;
    public UpdatableFontDir mUpdatableFontDir;
    public final Object mUpdatableFontDirLock = new Object();
    public String mDebugCertFilePath = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FsverityUtilImpl {
        public final String[] mDerCertPaths;

        public FsverityUtilImpl(String[] strArr) {
            this.mDerCertPaths = strArr;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final FontManagerService mService;
        public final CompletableFuture mServiceStarted;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.graphics.fonts.FontManagerService$Lifecycle$1, reason: invalid class name */
        public final class AnonymousClass1 {
            public AnonymousClass1() {}
        }

        public Lifecycle(Context context, boolean z) {
            super(context);
            CompletableFuture completableFuture = new CompletableFuture();
            this.mServiceStarted = completableFuture;
            this.mService = new FontManagerService(context, z, completableFuture);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i
                    == (Flags.completeFontLoadInSystemServicesReady()
                            ? SystemService.PHASE_LOCK_SETTINGS_READY
                            : SystemService.PHASE_ACTIVITY_MANAGER_READY)) {
                this.mServiceStarted.join();
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            LocalServices.addService(AnonymousClass1.class, new AnonymousClass1());
            publishBinderService("font", this.mService);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class SystemFontException extends AndroidException {
        private final int mErrorCode;

        public SystemFontException(int i, String str) {
            super(str);
            this.mErrorCode = i;
        }

        public SystemFontException(int i, String str, Throwable th) {
            super(str, th);
            this.mErrorCode = i;
        }

        public final int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public FontManagerService(
            Context context, boolean z, final CompletableFuture completableFuture) {
        SharedMemory sharedMemory;
        Object obj = new Object();
        this.mSerializedFontMapLock = obj;
        this.mSerializedFontMap = null;
        if (z) {
            Slog.i("FontManagerService", "Entering safe mode. Deleting all font updates.");
            UpdatableFontDir.deleteAllFiles(
                    new File("/data/fonts/files"), new File("/data/fonts/config/config.xml"));
        }
        this.mContext = context;
        this.mIsSafeMode = z;
        if (Flags.useOptimizedBoottimeFontLoading()) {
            Slog.i("FontManagerService", "Using optimized boot-time font loading.");
            SystemServerInitThreadPool.submit(
                    "FontManagerService_create",
                    new Runnable() { // from class:
                                     // com.android.server.graphics.fonts.FontManagerService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedMemory sharedMemory2;
                            FontManagerService fontManagerService = FontManagerService.this;
                            CompletableFuture completableFuture2 = completableFuture;
                            fontManagerService.initialize();
                            synchronized (fontManagerService.mUpdatableFontDirLock) {
                                if (fontManagerService.mUpdatableFontDir != null) {
                                    try {
                                        synchronized (fontManagerService.mSerializedFontMapLock) {
                                            sharedMemory2 = fontManagerService.mSerializedFontMap;
                                        }
                                        Typeface.setSystemFontMap(sharedMemory2);
                                    } catch (ErrnoException | IOException unused) {
                                        Slog.w(
                                                "FontManagerService",
                                                "Failed to set system font map of system_server");
                                    }
                                }
                            }
                            completableFuture2.complete(null);
                        }
                    });
            return;
        }
        Slog.i("FontManagerService", "Not using optimized boot-time font loading.");
        initialize();
        try {
            synchronized (obj) {
                sharedMemory = this.mSerializedFontMap;
            }
            Typeface.setSystemFontMap(sharedMemory);
        } catch (ErrnoException | IOException unused) {
            Slog.w("FontManagerService", "Failed to set system font map of system_server");
        }
        completableFuture.complete(null);
    }

    public static void closeFileDescriptors(List list) {
        ParcelFileDescriptor fd;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            FontUpdateRequest fontUpdateRequest = (FontUpdateRequest) it.next();
            if (fontUpdateRequest != null && (fd = fontUpdateRequest.getFd()) != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                    Slog.w("FontManagerService", "Failed to close fd", e);
                }
            }
        }
    }

    public final UpdatableFontDir createUpdatableFontDir() {
        if (this.mIsSafeMode || !VerityUtils.isFsVeritySupported()) {
            return null;
        }
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.radioAttributes);
        if (this.mDebugCertFilePath != null && Build.IS_DEBUGGABLE) {
            String[] strArr = new String[stringArray.length + 1];
            System.arraycopy(stringArray, 0, strArr, 0, stringArray.length);
            strArr[stringArray.length] = this.mDebugCertFilePath;
            stringArray = strArr;
        }
        return new UpdatableFontDir(
                new File("/data/fonts/files"),
                new OtfFontFileParser(),
                new FsverityUtilImpl(stringArray),
                new File("/data/fonts/config/config.xml"));
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "FontManagerService", printWriter)) {
            FontManagerShellCommand.dumpFontConfig(
                    new IndentingPrintWriter(printWriter, "  "),
                    new FontManagerShellCommand(this).mService.getSystemFontConfig());
        }
    }

    public final FontConfig getFontConfig() {
        getFontConfig_enforcePermission();
        return getSystemFontConfig();
    }

    public final FontConfig getSystemFontConfig() {
        synchronized (this.mUpdatableFontDirLock) {
            try {
                UpdatableFontDir updatableFontDir = this.mUpdatableFontDir;
                if (updatableFontDir == null) {
                    return SystemFonts.getSystemPreinstalledFontConfig();
                }
                return updatableFontDir.getSystemFontConfig();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void initialize() {
        SharedMemory sharedMemory;
        SharedMemory sharedMemory2;
        synchronized (this.mUpdatableFontDirLock) {
            UpdatableFontDir createUpdatableFontDir = createUpdatableFontDir();
            this.mUpdatableFontDir = createUpdatableFontDir;
            if (createUpdatableFontDir != null) {
                createUpdatableFontDir.loadFontFileMap();
                updateSerializedFontMap();
                return;
            }
            if (Flags.useOptimizedBoottimeFontLoading()) {
                Typeface.loadPreinstalledSystemFontMap();
            }
            try {
                sharedMemory = Typeface.serializeFontMap(Typeface.getSystemFontMap());
            } catch (ErrnoException | IOException e) {
                Slog.e("FontManagerService", "Failed to serialize SystemServer system font map", e);
                sharedMemory = null;
            }
            synchronized (this.mSerializedFontMapLock) {
                sharedMemory2 = this.mSerializedFontMap;
                this.mSerializedFontMap = sharedMemory;
            }
            if (sharedMemory2 != null) {
                sharedMemory2.close();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new FontManagerShellCommand(this)
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }

    public final void update(int i, List list) {
        synchronized (this.mUpdatableFontDirLock) {
            try {
                UpdatableFontDir updatableFontDir = this.mUpdatableFontDir;
                if (updatableFontDir == null) {
                    throw new SystemFontException(-7, "The font updater is disabled.");
                }
                if (i != -1 && updatableFontDir.mConfigVersion != i) {
                    throw new SystemFontException(
                            -8, "The base config version is older than current.");
                }
                updatableFontDir.update(list);
                updateSerializedFontMap();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int updateFontFamily(List list, int i) {
        try {
            Preconditions.checkArgumentNonnegative(i);
            Objects.requireNonNull(list);
            this.mContext.enforceCallingPermission(
                    "android.permission.UPDATE_FONTS", "UPDATE_FONTS permission required.");
            try {
                update(i, list);
                closeFileDescriptors(list);
                return 0;
            } catch (SystemFontException e) {
                Slog.e("FontManagerService", "Failed to update font family", e);
                int errorCode = e.getErrorCode();
                closeFileDescriptors(list);
                return errorCode;
            }
        } catch (Throwable th) {
            closeFileDescriptors(list);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.util.Iterator] */
    public final void updateSerializedFontMap() {
        SharedMemory sharedMemory;
        SharedMemory sharedMemory2;
        FontConfig systemFontConfig = getSystemFontConfig();
        ArrayMap arrayMap = new ArrayMap();
        SharedMemory sharedMemory3 = null;
        try {
            try {
                sharedMemory =
                        Typeface.serializeFontMap(
                                SystemFonts.buildSystemTypefaces(
                                        systemFontConfig,
                                        SystemFonts.buildSystemFallback(
                                                systemFontConfig, arrayMap)));
                arrayMap = arrayMap.values().iterator();
                while (arrayMap.hasNext()) {
                    ByteBuffer byteBuffer = (ByteBuffer) arrayMap.next();
                    if (byteBuffer instanceof DirectByteBuffer) {
                        NioUtils.freeDirectBuffer(byteBuffer);
                    }
                }
            } catch (ErrnoException | IOException e) {
                Slog.w(
                        "FontManagerService",
                        "Failed to serialize updatable font map. Retrying with system image fonts.",
                        e);
                Iterator it = arrayMap.values().iterator();
                while (true) {
                    arrayMap = it.hasNext();
                    if (arrayMap == 0) {
                        break;
                    }
                    ByteBuffer byteBuffer2 = (ByteBuffer) it.next();
                    if (byteBuffer2 instanceof DirectByteBuffer) {
                        NioUtils.freeDirectBuffer(byteBuffer2);
                    }
                }
                sharedMemory = null;
            }
            if (sharedMemory == null) {
                try {
                    sharedMemory3 = Typeface.serializeFontMap(Typeface.getSystemFontMap());
                } catch (ErrnoException | IOException e2) {
                    Slog.e(
                            "FontManagerService",
                            "Failed to serialize SystemServer system font map",
                            e2);
                }
                sharedMemory = sharedMemory3;
            }
            synchronized (this.mSerializedFontMapLock) {
                sharedMemory2 = this.mSerializedFontMap;
                this.mSerializedFontMap = sharedMemory;
            }
            if (sharedMemory2 != null) {
                sharedMemory2.close();
            }
        } catch (Throwable th) {
            for (ByteBuffer byteBuffer3 : arrayMap.values()) {
                if (byteBuffer3 instanceof DirectByteBuffer) {
                    NioUtils.freeDirectBuffer(byteBuffer3);
                }
            }
            throw th;
        }
    }
}
