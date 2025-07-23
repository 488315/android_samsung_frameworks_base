package android.content.res.loader;

import android.content.Context;
import android.content.om.OverlayInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.ApkAssets;
import android.content.res.Flags;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.internal.content.om.OverlayManagerImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;

/* loaded from: classes.dex */
public class ResourcesProvider implements AutoCloseable, Closeable {
    private static final String TAG = "ResourcesProvider";
    private final ApkAssets mApkAssets;
    private final Object mLock = new Object();
    private boolean mOpen = true;
    private int mOpenCount = 0;

    public static ResourcesProvider empty(AssetsProvider assetsProvider) {
        return new ResourcesProvider(ApkAssets.loadEmptyForLoader(4, assetsProvider));
    }

    public static ResourcesProvider loadOverlay(OverlayInfo overlayInfo) throws IOException {
        Objects.requireNonNull(overlayInfo);
        Preconditions.checkArgument(overlayInfo.isFabricated(), "Not accepted overlay");
        if (!Flags.selfTargetingAndroidResourceFrro()) {
            Preconditions.checkStringNotEmpty(overlayInfo.getTargetOverlayableName(), "Without overlayable name");
        }
        String checkOverlayNameValid = OverlayManagerImpl.checkOverlayNameValid(overlayInfo.getOverlayName());
        Path of = Path.of((String) Preconditions.checkStringNotEmpty(overlayInfo.getBaseCodePath(), "Invalid base path"), new String[0]);
        if (!Files.isRegularFile(of, new LinkOption[0])) {
            throw new FileNotFoundException("The frro file not found");
        }
        Path resolve = of.getParent().resolve(checkOverlayNameValid + ".idmap");
        if (!Files.isRegularFile(resolve, new LinkOption[0])) {
            throw new FileNotFoundException("The idmap file not found");
        }
        return new ResourcesProvider(ApkAssets.loadOverlayFromPath(resolve.toString(), 0));
    }

    public static ResourcesProvider loadFromApk(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        return loadFromApk(parcelFileDescriptor, null);
    }

    public static ResourcesProvider loadFromApk(ParcelFileDescriptor parcelFileDescriptor, AssetsProvider assetsProvider) throws IOException {
        return new ResourcesProvider(ApkAssets.loadFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), 4, assetsProvider));
    }

    public static ResourcesProvider loadFromApk(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, AssetsProvider assetsProvider) throws IOException {
        return new ResourcesProvider(ApkAssets.loadFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), j, j2, 4, assetsProvider));
    }

    public static ResourcesProvider loadFromTable(ParcelFileDescriptor parcelFileDescriptor, AssetsProvider assetsProvider) throws IOException {
        return new ResourcesProvider(ApkAssets.loadTableFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), 4, assetsProvider));
    }

    public static ResourcesProvider loadFromTable(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, AssetsProvider assetsProvider) throws IOException {
        return new ResourcesProvider(ApkAssets.loadTableFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), j, j2, 4, assetsProvider));
    }

    public static ResourcesProvider loadFromSplit(Context context, String str) throws IOException {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int indexOf = ArrayUtils.indexOf(applicationInfo.splitNames, str);
        if (indexOf < 0) {
            throw new IllegalArgumentException("Split " + str + " not found");
        }
        return new ResourcesProvider(ApkAssets.loadFromPath(applicationInfo.getSplitCodePaths()[indexOf], 4, null));
    }

    public static ResourcesProvider loadFromDirectory(String str, AssetsProvider assetsProvider) throws IOException {
        return new ResourcesProvider(ApkAssets.loadFromDir(str, 4, assetsProvider));
    }

    private ResourcesProvider(ApkAssets apkAssets) {
        this.mApkAssets = apkAssets;
    }

    public ApkAssets getApkAssets() {
        return this.mApkAssets;
    }

    final void incrementRefCount() {
        synchronized (this.mLock) {
            if (!this.mOpen) {
                throw new IllegalStateException("Operation failed: resources provider is closed");
            }
            this.mOpenCount++;
        }
    }

    final void decrementRefCount() {
        synchronized (this.mLock) {
            this.mOpenCount--;
        }
    }

    @Override // java.lang.AutoCloseable, java.io.Closeable
    public void close() {
        synchronized (this.mLock) {
            if (this.mOpen) {
                if (this.mOpenCount != 0) {
                    throw new IllegalStateException("Failed to close provider used by " + this.mOpenCount + " ResourcesLoader instances");
                }
                this.mOpen = false;
                try {
                    this.mApkAssets.close();
                } catch (Throwable unused) {
                }
            }
        }
    }

    protected void finalize() throws Throwable {
        synchronized (this.mLock) {
            if (this.mOpenCount != 0) {
                Log.w(TAG, "ResourcesProvider " + this + " finalized with non-zero refs: " + this.mOpenCount);
            }
        }
    }
}
