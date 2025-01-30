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
import android.util.Log;
import android.util.Slog;
import com.android.internal.graphics.fonts.IFontManager;
import com.android.internal.security.VerityUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.graphics.fonts.UpdatableFontDir;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.DirectByteBuffer;
import java.nio.NioUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class FontManagerService extends IFontManager.Stub {
    public final Context mContext;
    public String mDebugCertFilePath;
    public final boolean mIsSafeMode;
    public SharedMemory mSerializedFontMap;
    public final Object mSerializedFontMapLock;
    public UpdatableFontDir mUpdatableFontDir;
    public final Object mUpdatableFontDirLock;

    public FontConfig getFontConfig() {
        super.getFontConfig_enforcePermission();
        return getSystemFontConfig();
    }

    public int updateFontFamily(List list, int i) {
        try {
            Preconditions.checkArgumentNonnegative(i);
            Objects.requireNonNull(list);
            getContext().enforceCallingPermission("android.permission.UPDATE_FONTS", "UPDATE_FONTS permission required.");
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

    class SystemFontException extends AndroidException {
        private final int mErrorCode;

        public SystemFontException(int i, String str, Throwable th) {
            super(str, th);
            this.mErrorCode = i;
        }

        public SystemFontException(int i, String str) {
            super(str);
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public final class Lifecycle extends SystemService {
        public final FontManagerService mService;

        public Lifecycle(Context context, boolean z) {
            super(context);
            this.mService = new FontManagerService(context, z);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            LocalServices.addService(FontManagerInternal.class, new FontManagerInternal() { // from class: com.android.server.graphics.fonts.FontManagerService.Lifecycle.1
                @Override // com.android.server.graphics.fonts.FontManagerInternal
                public SharedMemory getSerializedSystemFontMap() {
                    return Lifecycle.this.mService.getCurrentFontMap();
                }
            });
            publishBinderService("font", this.mService);
        }
    }

    public class FsverityUtilImpl implements UpdatableFontDir.FsverityUtil {
        public final String[] mDerCertPaths;

        public FsverityUtilImpl(String[] strArr) {
            this.mDerCertPaths = strArr;
        }

        @Override // com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil
        public boolean isFromTrustedProvider(String str, byte[] bArr) {
            FileInputStream fileInputStream;
            byte[] fsverityDigest = VerityUtils.getFsverityDigest(str);
            if (fsverityDigest == null) {
                Log.w("FontManagerService", "Failed to get fs-verity digest for " + str);
                return false;
            }
            for (String str2 : this.mDerCertPaths) {
                try {
                    fileInputStream = new FileInputStream(str2);
                    try {
                    } finally {
                    }
                } catch (IOException unused) {
                    Log.w("FontManagerService", "Failed to read certificate file: " + str2);
                }
                if (VerityUtils.verifyPkcs7DetachedSignature(bArr, fsverityDigest, fileInputStream)) {
                    fileInputStream.close();
                    return true;
                }
                fileInputStream.close();
            }
            return false;
        }

        @Override // com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil
        public void setUpFsverity(String str) {
            VerityUtils.setUpFsverity(str);
        }

        @Override // com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil
        public boolean rename(File file, File file2) {
            return file.renameTo(file2);
        }
    }

    public FontManagerService(Context context, boolean z) {
        this.mUpdatableFontDirLock = new Object();
        this.mDebugCertFilePath = null;
        this.mSerializedFontMapLock = new Object();
        this.mSerializedFontMap = null;
        if (z) {
            Slog.i("FontManagerService", "Entering safe mode. Deleting all font updates.");
            UpdatableFontDir.deleteAllFiles(new File("/data/fonts/files"), new File("/data/fonts/config/config.xml"));
        }
        this.mContext = context;
        this.mIsSafeMode = z;
        initialize();
    }

    public final UpdatableFontDir createUpdatableFontDir() {
        if (this.mIsSafeMode || !VerityUtils.isFsVeritySupported()) {
            return null;
        }
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.no_ems_support_sim_operators);
        if (this.mDebugCertFilePath != null && Build.IS_DEBUGGABLE) {
            String[] strArr = new String[stringArray.length + 1];
            System.arraycopy(stringArray, 0, strArr, 0, stringArray.length);
            strArr[stringArray.length] = this.mDebugCertFilePath;
            stringArray = strArr;
        }
        return new UpdatableFontDir(new File("/data/fonts/files"), new OtfFontFileParser(), new FsverityUtilImpl(stringArray), new File("/data/fonts/config/config.xml"));
    }

    public void addDebugCertificate(String str) {
        this.mDebugCertFilePath = str;
    }

    public final void initialize() {
        synchronized (this.mUpdatableFontDirLock) {
            UpdatableFontDir createUpdatableFontDir = createUpdatableFontDir();
            this.mUpdatableFontDir = createUpdatableFontDir;
            if (createUpdatableFontDir == null) {
                setSerializedFontMap(serializeSystemServerFontMap());
            } else {
                createUpdatableFontDir.loadFontFileMap();
                updateSerializedFontMap();
            }
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public SharedMemory getCurrentFontMap() {
        SharedMemory sharedMemory;
        synchronized (this.mSerializedFontMapLock) {
            sharedMemory = this.mSerializedFontMap;
        }
        return sharedMemory;
    }

    public void update(int i, List list) {
        synchronized (this.mUpdatableFontDirLock) {
            UpdatableFontDir updatableFontDir = this.mUpdatableFontDir;
            if (updatableFontDir == null) {
                throw new SystemFontException(-7, "The font updater is disabled.");
            }
            if (i != -1 && updatableFontDir.getConfigVersion() != i) {
                throw new SystemFontException(-8, "The base config version is older than current.");
            }
            this.mUpdatableFontDir.update(list);
            updateSerializedFontMap();
        }
    }

    public void clearUpdates() {
        UpdatableFontDir.deleteAllFiles(new File("/data/fonts/files"), new File("/data/fonts/config/config.xml"));
        initialize();
    }

    public void restart() {
        initialize();
    }

    public Map getFontFileMap() {
        synchronized (this.mUpdatableFontDirLock) {
            UpdatableFontDir updatableFontDir = this.mUpdatableFontDir;
            if (updatableFontDir == null) {
                return Collections.emptyMap();
            }
            return updatableFontDir.getPostScriptMap();
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "FontManagerService", printWriter)) {
            new FontManagerShellCommand(this).dumpAll(new IndentingPrintWriter(printWriter, "  "));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new FontManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public FontConfig getSystemFontConfig() {
        synchronized (this.mUpdatableFontDirLock) {
            UpdatableFontDir updatableFontDir = this.mUpdatableFontDir;
            if (updatableFontDir == null) {
                return SystemFonts.getSystemPreinstalledFontConfig();
            }
            return updatableFontDir.getSystemFontConfig();
        }
    }

    public final void updateSerializedFontMap() {
        SharedMemory serializeFontMap = serializeFontMap(getSystemFontConfig());
        if (serializeFontMap == null) {
            serializeFontMap = serializeSystemServerFontMap();
        }
        setSerializedFontMap(serializeFontMap);
    }

    public static SharedMemory serializeFontMap(FontConfig fontConfig) {
        ArrayMap arrayMap = new ArrayMap();
        try {
            try {
                SharedMemory serializeFontMap = Typeface.serializeFontMap(SystemFonts.buildSystemTypefaces(fontConfig, SystemFonts.buildSystemFallback(fontConfig, arrayMap)));
                for (ByteBuffer byteBuffer : arrayMap.values()) {
                    if (byteBuffer instanceof DirectByteBuffer) {
                        NioUtils.freeDirectBuffer(byteBuffer);
                    }
                }
                return serializeFontMap;
            } catch (ErrnoException | IOException e) {
                Slog.w("FontManagerService", "Failed to serialize updatable font map. Retrying with system image fonts.", e);
                for (ByteBuffer byteBuffer2 : arrayMap.values()) {
                    if (byteBuffer2 instanceof DirectByteBuffer) {
                        NioUtils.freeDirectBuffer(byteBuffer2);
                    }
                }
                return null;
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

    public static SharedMemory serializeSystemServerFontMap() {
        try {
            return Typeface.serializeFontMap(Typeface.getSystemFontMap());
        } catch (ErrnoException | IOException e) {
            Slog.e("FontManagerService", "Failed to serialize SystemServer system font map", e);
            return null;
        }
    }

    public final void setSerializedFontMap(SharedMemory sharedMemory) {
        SharedMemory sharedMemory2;
        synchronized (this.mSerializedFontMapLock) {
            sharedMemory2 = this.mSerializedFontMap;
            this.mSerializedFontMap = sharedMemory;
        }
        if (sharedMemory2 != null) {
            sharedMemory2.close();
        }
    }
}
