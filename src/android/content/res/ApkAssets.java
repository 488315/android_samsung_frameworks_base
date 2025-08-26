package android.content.res;

import android.content.om.OverlayableInfo;
import android.content.res.loader.AssetsProvider;
import android.text.TextUtils;
import dalvik.annotation.optimization.CriticalNative;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ApkAssets {
    private static final boolean DEBUG = false;
    private static final int FORMAT_APK = 0;
    private static final int FORMAT_ARSC = 2;
    private static final int FORMAT_DIR = 3;
    private static final int FORMAT_IDMAP = 1;
    public static final int PROPERTY_DISABLE_INCREMENTAL_HARDENING = 16;
    public static final int PROPERTY_DYNAMIC = 2;
    public static final int PROPERTY_LOADER = 4;
    public static final int PROPERTY_ONLY_OVERLAYABLES = 32;
    private static final int PROPERTY_OVERLAY = 8;
    public static final int PROPERTY_SYSTEM = 1;
    private static final int UPTODATE_ALWAYS_TRUE = 2;
    private static final int UPTODATE_FALSE = 0;
    private static final int UPTODATE_TRUE = 1;
    private final AssetsProvider mAssets;
    private final int mFlags;
    private final boolean mIsOverlay;
    private String mName;
    private long mNativePtr;
    private int mPreviousUpToDateResult;
    private StringBlock mStringBlock;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FormatType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyFlags {
    }

    private static double intervalMs(long j, long j2) {
        return (j2 - j) / 1000000.0d;
    }

    private static native boolean nativeDefinesOverlayable(long j) throws IOException;

    private static native void nativeDestroy(long j);

    private static native String nativeGetAssetPath(long j);

    private static native String nativeGetDebugName(long j);

    private static native OverlayableInfo nativeGetOverlayableInfo(long j, String str) throws IOException;

    private static native long nativeGetStringBlock(long j);

    @CriticalNative
    private static native int nativeIsUpToDate(long j);

    private static native long nativeLoad(int i, String str, int i2, AssetsProvider assetsProvider) throws IOException;

    private static native long nativeLoadEmpty(int i, AssetsProvider assetsProvider);

    private static native long nativeLoadFd(int i, FileDescriptor fileDescriptor, String str, int i2, AssetsProvider assetsProvider) throws IOException;

    private static native long nativeLoadFdOffsets(int i, FileDescriptor fileDescriptor, String str, long j, long j2, int i2, AssetsProvider assetsProvider) throws IOException;

    private static native long nativeOpenXml(long j, String str) throws IOException;

    public static ApkAssets loadFromPath(String str) throws IOException {
        return loadFromPath(str, 0);
    }

    public static ApkAssets loadFromPath(String str, int i) throws IOException {
        return new ApkAssets(0, str, i, (AssetsProvider) null);
    }

    public static ApkAssets loadFromPath(String str, int i, AssetsProvider assetsProvider) throws IOException {
        return new ApkAssets(0, str, i, assetsProvider);
    }

    public static ApkAssets loadFromFd(FileDescriptor fileDescriptor, String str, int i, AssetsProvider assetsProvider) throws IOException {
        return new ApkAssets(0, fileDescriptor, str, i, assetsProvider);
    }

    public static ApkAssets loadFromFd(FileDescriptor fileDescriptor, String str, long j, long j2, int i, AssetsProvider assetsProvider) throws IOException {
        return new ApkAssets(0, fileDescriptor, str, j, j2, i, assetsProvider);
    }

    public static ApkAssets loadOverlayFromPath(String str, int i) throws IOException {
        return new ApkAssets(1, str, i, (AssetsProvider) null);
    }

    public static ApkAssets loadTableFromFd(FileDescriptor fileDescriptor, String str, int i, AssetsProvider assetsProvider) throws IOException {
        return new ApkAssets(2, fileDescriptor, str, i, assetsProvider);
    }

    public static ApkAssets loadTableFromFd(FileDescriptor fileDescriptor, String str, long j, long j2, int i, AssetsProvider assetsProvider) throws IOException {
        return new ApkAssets(2, fileDescriptor, str, j, j2, i, assetsProvider);
    }

    public static ApkAssets loadFromDir(String str, int i, AssetsProvider assetsProvider) throws IOException {
        return new ApkAssets(3, str, i, assetsProvider);
    }

    public static ApkAssets loadEmptyForLoader(int i, AssetsProvider assetsProvider) {
        return new ApkAssets(i, assetsProvider);
    }

    private ApkAssets(int i, String str, int i2, AssetsProvider assetsProvider) throws IOException {
        this(i, i2, assetsProvider, str);
        Objects.requireNonNull(str, "path");
        this.mNativePtr = nativeLoad(i, str, i2, assetsProvider);
        this.mStringBlock = new StringBlock(nativeGetStringBlock(this.mNativePtr), true);
    }

    private ApkAssets(int i, FileDescriptor fileDescriptor, String str, int i2, AssetsProvider assetsProvider) throws IOException {
        this(i, i2, assetsProvider, str);
        Objects.requireNonNull(fileDescriptor, "fd");
        Objects.requireNonNull(str, "friendlyName");
        this.mNativePtr = nativeLoadFd(i, fileDescriptor, str, i2, assetsProvider);
        this.mStringBlock = new StringBlock(nativeGetStringBlock(this.mNativePtr), true);
    }

    private ApkAssets(int i, FileDescriptor fileDescriptor, String str, long j, long j2, int i2, AssetsProvider assetsProvider) throws IOException {
        this(i, i2, assetsProvider, str);
        Objects.requireNonNull(fileDescriptor, "fd");
        Objects.requireNonNull(str, "friendlyName");
        this.mNativePtr = nativeLoadFdOffsets(i, fileDescriptor, str, j, j2, i2, assetsProvider);
        this.mStringBlock = new StringBlock(nativeGetStringBlock(this.mNativePtr), true);
    }

    private ApkAssets(int i, AssetsProvider assetsProvider) {
        this(0, i, assetsProvider, "empty");
        this.mNativePtr = nativeLoadEmpty(i, assetsProvider);
        this.mStringBlock = null;
    }

    private ApkAssets(int i, int i2, AssetsProvider assetsProvider, String str) {
        this.mPreviousUpToDateResult = 1;
        this.mFlags = i2;
        this.mAssets = assetsProvider;
        this.mIsOverlay = i == 1;
    }

    public String getAssetPath() {
        String strEmptyIfNull;
        synchronized (this) {
            strEmptyIfNull = TextUtils.emptyIfNull(nativeGetAssetPath(this.mNativePtr));
        }
        return strEmptyIfNull;
    }

    public String getDebugName() {
        String strNativeGetDebugName;
        synchronized (this) {
            long j = this.mNativePtr;
            strNativeGetDebugName = j == 0 ? "<destroyed>" : nativeGetDebugName(j);
        }
        return strNativeGetDebugName;
    }

    CharSequence getStringFromPool(int i) {
        CharSequence sequence;
        if (this.mStringBlock == null) {
            return null;
        }
        synchronized (this) {
            sequence = this.mStringBlock.getSequence(i);
        }
        return sequence;
    }

    public boolean isForLoader() {
        return (this.mFlags & 4) != 0;
    }

    public AssetsProvider getAssetsProvider() {
        return this.mAssets;
    }

    public XmlResourceParser openXml(String str) throws IOException {
        XmlResourceParser xmlResourceParserNewParser;
        Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            XmlBlock xmlBlock = new XmlBlock((AssetManager) null, nativeOpenXml(this.mNativePtr, str), true);
            try {
                xmlResourceParserNewParser = xmlBlock.newParser();
                if (xmlResourceParserNewParser == null) {
                    throw new AssertionError("block.newParser() returned a null parser");
                }
                xmlBlock.close();
            } finally {
            }
        }
        return xmlResourceParserNewParser;
    }

    public OverlayableInfo getOverlayableInfo(String str) throws IOException {
        OverlayableInfo overlayableInfoNativeGetOverlayableInfo;
        synchronized (this) {
            overlayableInfoNativeGetOverlayableInfo = nativeGetOverlayableInfo(this.mNativePtr, str);
        }
        return overlayableInfoNativeGetOverlayableInfo;
    }

    public boolean definesOverlayable() throws IOException {
        boolean zNativeDefinesOverlayable;
        synchronized (this) {
            zNativeDefinesOverlayable = nativeDefinesOverlayable(this.mNativePtr);
        }
        return zNativeDefinesOverlayable;
    }

    public boolean isUpToDate() {
        int iNativeIsUpToDate;
        int i = this.mPreviousUpToDateResult;
        if (i != 1) {
            return i == 2;
        }
        synchronized (this) {
            iNativeIsUpToDate = nativeIsUpToDate(this.mNativePtr);
        }
        this.mPreviousUpToDateResult = iNativeIsUpToDate;
        return iNativeIsUpToDate != 0;
    }

    public boolean isSystem() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isSharedLib() {
        return (this.mFlags & 2) != 0;
    }

    public boolean isOverlay() {
        return this.mIsOverlay;
    }

    public String toString() {
        return "ApkAssets{path=" + getDebugName() + "}";
    }

    protected void finalize() throws Throwable {
        close();
    }

    public void close() {
        synchronized (this) {
            if (this.mNativePtr != 0) {
                StringBlock stringBlock = this.mStringBlock;
                if (stringBlock != null) {
                    stringBlock.close();
                }
                nativeDestroy(this.mNativePtr);
                this.mNativePtr = 0L;
            }
        }
    }

    void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "class=" + getClass());
        printWriter.println(str + "debugName=" + getDebugName());
        printWriter.println(str + "assetPath=" + getAssetPath());
    }
}
