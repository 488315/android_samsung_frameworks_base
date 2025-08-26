package android.graphics;

import android.content.res.AssetManager;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.text.TextUtils;
import dalvik.annotation.optimization.CriticalNative;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import libcore.util.NativeAllocationRegistry;

@Deprecated
/* loaded from: classes.dex */
public class FontFamily {
    private static String TAG = "FontFamily";
    private long mBuilderPtr;
    private Runnable mNativeBuilderCleaner;
    public long mNativePtr;

    @CriticalNative
    private static native void nAddAxisValue(long j, int i, float f);

    private static native boolean nAddFont(long j, ByteBuffer byteBuffer, int i, int i2, int i3);

    private static native boolean nAddFontWeightStyle(long j, ByteBuffer byteBuffer, int i, int i2, int i3);

    @CriticalNative
    private static native long nCreateFamily(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nGetBuilderReleaseFunc();

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nGetFamilyReleaseFunc();

    private static native long nInitBuilder(String str, int i);

    private static class NoImagePreloadHolder {
        private static final NativeAllocationRegistry sBuilderRegistry = NativeAllocationRegistry.createMalloced(FontFamily.class.getClassLoader(), FontFamily.nGetBuilderReleaseFunc());
        private static final NativeAllocationRegistry sFamilyRegistry = NativeAllocationRegistry.createMalloced(FontFamily.class.getClassLoader(), FontFamily.nGetFamilyReleaseFunc());

        private NoImagePreloadHolder() {
        }
    }

    public FontFamily() {
        this.mBuilderPtr = nInitBuilder(null, 0);
        this.mNativeBuilderCleaner = NoImagePreloadHolder.sBuilderRegistry.registerNativeAllocation(this, this.mBuilderPtr);
    }

    public FontFamily(String[] strArr, int i) {
        String strJoin;
        if (strArr == null || strArr.length == 0) {
            strJoin = null;
        } else if (strArr.length == 1) {
            strJoin = strArr[0];
        } else {
            strJoin = TextUtils.join(",", strArr);
        }
        this.mBuilderPtr = nInitBuilder(strJoin, i);
        this.mNativeBuilderCleaner = NoImagePreloadHolder.sBuilderRegistry.registerNativeAllocation(this, this.mBuilderPtr);
    }

    public boolean freeze() {
        long j = this.mBuilderPtr;
        if (j == 0) {
            throw new IllegalStateException("This FontFamily is already frozen");
        }
        this.mNativePtr = nCreateFamily(j);
        this.mNativeBuilderCleaner.run();
        this.mBuilderPtr = 0L;
        if (this.mNativePtr != 0) {
            NoImagePreloadHolder.sFamilyRegistry.registerNativeAllocation(this, this.mNativePtr);
        }
        return this.mNativePtr != 0;
    }

    public void abortCreation() {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("This FontFamily is already frozen or abandoned");
        }
        this.mNativeBuilderCleaner.run();
        this.mBuilderPtr = 0L;
    }

    public boolean addFont(String str, int i, FontVariationAxis[] fontVariationAxisArr, int i2, int i3) throws IOException {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("Unable to call addFont after freezing.");
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                FileChannel channel = fileInputStream.getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                if (fontVariationAxisArr != null) {
                    for (FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                        nAddAxisValue(this.mBuilderPtr, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
                    }
                }
                boolean zNAddFont = nAddFont(this.mBuilderPtr, map, i, i2, i3);
                fileInputStream.close();
                return zNAddFont;
            } finally {
            }
        } catch (IOException unused) {
            return false;
        }
    }

    public boolean addFontFromBuffer(ByteBuffer byteBuffer, int i, FontVariationAxis[] fontVariationAxisArr, int i2, int i3) {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("Unable to call addFontWeightStyle after freezing.");
        }
        if (fontVariationAxisArr != null) {
            for (FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                nAddAxisValue(this.mBuilderPtr, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
            }
        }
        return nAddFontWeightStyle(this.mBuilderPtr, byteBuffer, i, i2, i3);
    }

    public boolean addFontFromAssetManager(AssetManager assetManager, String str, int i, boolean z, int i2, int i3, int i4, FontVariationAxis[] fontVariationAxisArr) {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("Unable to call addFontFromAsset after freezing.");
        }
        try {
            return addFontFromBuffer(Font.Builder.createBuffer(assetManager, str, z, i), i2, fontVariationAxisArr, i3, i4);
        } catch (IOException unused) {
            return false;
        }
    }
}
