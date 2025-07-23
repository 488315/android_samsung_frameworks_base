package android.os.incremental;

import android.content.pm.DataLoaderParams;
import android.content.pm.IPackageLoadingProgressCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.incremental.IStorageLoadingProgressListener;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.util.Slog;
import android.util.SparseArray;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class IncrementalManager {
    private static final String ALLOWED_PROPERTY = "incremental.allowed";
    public static final int CREATE_MODE_CREATE = 4;
    public static final int CREATE_MODE_OPEN_EXISTING = 8;
    public static final int CREATE_MODE_PERMANENT_BIND = 2;
    public static final int CREATE_MODE_TEMPORARY_BIND = 1;
    public static final int MIN_VERSION_TO_SUPPORT_FSVERITY = 2;
    private static final String TAG = "IncrementalManager";
    private final LoadingProgressCallbacks mLoadingProgressCallbacks = new LoadingProgressCallbacks();
    private final IIncrementalService mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CreateMode {
    }

    private static native boolean nativeIsEnabled();

    private static native boolean nativeIsIncrementalFd(int i);

    private static native boolean nativeIsIncrementalPath(String str);

    private static native boolean nativeIsV2Available();

    private static native byte[] nativeUnsafeGetFileSignature(String str);

    public IncrementalManager(IIncrementalService iIncrementalService) {
        this.mService = iIncrementalService;
    }

    public IncrementalStorage createStorage(String str, DataLoaderParams dataLoaderParams, int i) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(dataLoaderParams);
        try {
            int createStorage = this.mService.createStorage(str, dataLoaderParams.getData(), i);
            if (createStorage < 0) {
                return null;
            }
            return new IncrementalStorage(this.mService, createStorage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IncrementalStorage openStorage(String str) {
        try {
            int openStorage = this.mService.openStorage(str);
            if (openStorage < 0) {
                return null;
            }
            return new IncrementalStorage(this.mService, openStorage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IncrementalStorage createStorage(String str, IncrementalStorage incrementalStorage, int i) {
        int i2 = -1;
        try {
            StructStat stat = Os.stat(str);
            i2 = this.mService.createLinkedStorage(str, incrementalStorage.getId(), i);
            if (i2 < 0) {
                return null;
            }
            Os.chmod(str, stat.st_mode & 4095);
            return new IncrementalStorage(this.mService, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ErrnoException e2) {
            if (i2 >= 0) {
                try {
                    this.mService.deleteStorage(i2);
                } catch (RemoteException e3) {
                    throw e3.rethrowFromSystemServer();
                }
            }
            throw new RuntimeException(e2);
        }
    }

    public void linkCodePath(File file, File file2) throws IllegalArgumentException, IOException {
        File absoluteFile = file.getAbsoluteFile();
        IncrementalStorage openStorage = openStorage(absoluteFile.toString());
        if (openStorage == null) {
            throw new IllegalArgumentException("Not an Incremental path: " + absoluteFile);
        }
        String parent = file2.getAbsoluteFile().getParent();
        IncrementalStorage createStorage = createStorage(parent, openStorage, 6);
        if (createStorage == null) {
            throw new IOException("Failed to create linked storage at dir: " + parent);
        }
        try {
            linkFiles(openStorage, absoluteFile, "", createStorage, file2.getName());
        } catch (Exception e) {
            createStorage.unBind(parent);
            throw e;
        }
    }

    private void linkFiles(final IncrementalStorage incrementalStorage, File file, String str, final IncrementalStorage incrementalStorage2, String str2) throws IOException {
        final Path resolve = file.toPath().resolve(str);
        final Path path = Paths.get(str2, new String[0]);
        Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>(this) { // from class: android.os.incremental.IncrementalManager.1
            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            public FileVisitResult preVisitDirectory(Path path2, BasicFileAttributes basicFileAttributes) throws IOException {
                incrementalStorage2.makeDirectory(path.resolve(resolve.relativize(path2)).toString());
                return FileVisitResult.CONTINUE;
            }

            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            public FileVisitResult visitFile(Path path2, BasicFileAttributes basicFileAttributes) throws IOException {
                incrementalStorage.makeLink(path2.toAbsolutePath().toString(), incrementalStorage2, path.resolve(resolve.relativize(path2)).toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static boolean isFeatureEnabled() {
        return nativeIsEnabled();
    }

    public static int getVersion() {
        if (nativeIsEnabled()) {
            return nativeIsV2Available() ? 2 : 1;
        }
        return 0;
    }

    public static boolean isAllowed() {
        return isFeatureEnabled() && SystemProperties.getBoolean(ALLOWED_PROPERTY, true);
    }

    public static boolean isIncrementalPath(String str) {
        return nativeIsIncrementalPath(str);
    }

    public static boolean isIncrementalFileFd(FileDescriptor fileDescriptor) {
        return nativeIsIncrementalFd(fileDescriptor.getInt$());
    }

    public static byte[] unsafeGetFileSignature(String str) {
        return nativeUnsafeGetFileSignature(str);
    }

    public void rmPackageDir(File file) {
        try {
            String absolutePath = file.getAbsolutePath();
            IncrementalStorage openStorage = openStorage(absolutePath);
            if (openStorage == null) {
                return;
            }
            this.mLoadingProgressCallbacks.cleanUpCallbacks(openStorage);
            openStorage.unBind(absolutePath);
        } catch (IOException e) {
            Slog.w(TAG, "Failed to remove code path", e);
        }
    }

    public boolean registerLoadingProgressCallback(String str, IPackageLoadingProgressCallback iPackageLoadingProgressCallback) {
        IncrementalStorage openStorage = openStorage(str);
        if (openStorage == null) {
            return false;
        }
        return this.mLoadingProgressCallbacks.registerCallback(openStorage, iPackageLoadingProgressCallback);
    }

    public void unregisterLoadingProgressCallbacks(String str) {
        IncrementalStorage openStorage = openStorage(str);
        if (openStorage == null) {
            return;
        }
        this.mLoadingProgressCallbacks.cleanUpCallbacks(openStorage);
    }

    private static class LoadingProgressCallbacks extends IStorageLoadingProgressListener.Stub {
        private final SparseArray<RemoteCallbackList<IPackageLoadingProgressCallback>> mCallbacks;

        private LoadingProgressCallbacks() {
            this.mCallbacks = new SparseArray<>();
        }

        public void cleanUpCallbacks(IncrementalStorage incrementalStorage) {
            RemoteCallbackList<IPackageLoadingProgressCallback> removeReturnOld;
            int id = incrementalStorage.getId();
            synchronized (this.mCallbacks) {
                removeReturnOld = this.mCallbacks.removeReturnOld(id);
            }
            if (removeReturnOld == null) {
                return;
            }
            removeReturnOld.kill();
            incrementalStorage.unregisterLoadingProgressListener();
        }

        public boolean registerCallback(IncrementalStorage incrementalStorage, IPackageLoadingProgressCallback iPackageLoadingProgressCallback) {
            int id = incrementalStorage.getId();
            synchronized (this.mCallbacks) {
                RemoteCallbackList<IPackageLoadingProgressCallback> remoteCallbackList = this.mCallbacks.get(id);
                if (remoteCallbackList == null) {
                    remoteCallbackList = new RemoteCallbackList<>();
                    this.mCallbacks.put(id, remoteCallbackList);
                }
                remoteCallbackList.register(iPackageLoadingProgressCallback);
                if (remoteCallbackList.getRegisteredCallbackCount() > 1) {
                    return true;
                }
                return incrementalStorage.registerLoadingProgressListener(this);
            }
        }

        @Override // android.os.incremental.IStorageLoadingProgressListener
        public void onStorageLoadingProgressChanged(int i, float f) {
            RemoteCallbackList<IPackageLoadingProgressCallback> remoteCallbackList;
            synchronized (this.mCallbacks) {
                remoteCallbackList = this.mCallbacks.get(i);
            }
            if (remoteCallbackList == null) {
                return;
            }
            int beginBroadcast = remoteCallbackList.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    remoteCallbackList.getBroadcastItem(i2).onPackageLoadingProgressChanged(f);
                } catch (RemoteException unused) {
                }
            }
            remoteCallbackList.finishBroadcast();
        }
    }

    public IncrementalMetrics getMetrics(String str) {
        IncrementalStorage openStorage = openStorage(str);
        if (openStorage == null) {
            return null;
        }
        return new IncrementalMetrics(openStorage.getMetrics());
    }
}
