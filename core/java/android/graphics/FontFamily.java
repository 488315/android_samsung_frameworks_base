package android.graphics;

import android.content.res.AssetManager;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.text.TextUtils;

import dalvik.annotation.optimization.CriticalNative;

import libcore.util.NativeAllocationRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Deprecated
public class FontFamily {
    private static String TAG = "FontFamily";
    private long mBuilderPtr;
    private Runnable mNativeBuilderCleaner;
    public long mNativePtr;

    @CriticalNative
    private static native void nAddAxisValue(long j, int i, float f);

    private static native boolean nAddFont(long j, ByteBuffer byteBuffer, int i, int i2, int i3);

    private static native boolean nAddFontWeightStyle(
            long j, ByteBuffer byteBuffer, int i, int i2, int i3);

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
        private static final NativeAllocationRegistry sBuilderRegistry =
                NativeAllocationRegistry.createMalloced(
                        FontFamily.class.getClassLoader(), FontFamily.nGetBuilderReleaseFunc());
        private static final NativeAllocationRegistry sFamilyRegistry =
                NativeAllocationRegistry.createMalloced(
                        FontFamily.class.getClassLoader(), FontFamily.nGetFamilyReleaseFunc());

        private NoImagePreloadHolder() {}
    }

    public FontFamily() {
        this.mBuilderPtr = nInitBuilder(null, 0);
        this.mNativeBuilderCleaner =
                NoImagePreloadHolder.sBuilderRegistry.registerNativeAllocation(
                        this, this.mBuilderPtr);
    }

    public FontFamily(String[] langs, int variant) {
        String langsString;
        if (langs == null || langs.length == 0) {
            langsString = null;
        } else if (langs.length == 1) {
            langsString = langs[0];
        } else {
            langsString = TextUtils.join(",", langs);
        }
        this.mBuilderPtr = nInitBuilder(langsString, variant);
        this.mNativeBuilderCleaner =
                NoImagePreloadHolder.sBuilderRegistry.registerNativeAllocation(
                        this, this.mBuilderPtr);
    }

    public boolean freeze() {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("This FontFamily is already frozen");
        }
        this.mNativePtr = nCreateFamily(this.mBuilderPtr);
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

    public boolean addFont(
            String path, int ttcIndex, FontVariationAxis[] axes, int weight, int italic) {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("Unable to call addFont after freezing.");
        }
        try {
            FileInputStream file = new FileInputStream(path);
            try {
                FileChannel fileChannel = file.getChannel();
                long fontSize = fileChannel.size();
                ByteBuffer fontBuffer =
                        fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fontSize);
                if (axes != null) {
                    for (FontVariationAxis axis : axes) {
                        nAddAxisValue(
                                this.mBuilderPtr, axis.getOpenTypeTagValue(), axis.getStyleValue());
                    }
                }
                boolean nAddFont = nAddFont(this.mBuilderPtr, fontBuffer, ttcIndex, weight, italic);
                file.close();
                return nAddFont;
            } finally {
            }
        } catch (IOException e) {
            return false;
        }
    }

    public boolean addFontFromBuffer(
            ByteBuffer font, int ttcIndex, FontVariationAxis[] axes, int weight, int italic) {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("Unable to call addFontWeightStyle after freezing.");
        }
        if (axes != null) {
            for (FontVariationAxis axis : axes) {
                nAddAxisValue(this.mBuilderPtr, axis.getOpenTypeTagValue(), axis.getStyleValue());
            }
        }
        return nAddFontWeightStyle(this.mBuilderPtr, font, ttcIndex, weight, italic);
    }

    public boolean addFontFromAssetManager(
            AssetManager mgr,
            String path,
            int cookie,
            boolean isAsset,
            int ttcIndex,
            int weight,
            int isItalic,
            FontVariationAxis[] axes) {
        if (this.mBuilderPtr == 0) {
            throw new IllegalStateException("Unable to call addFontFromAsset after freezing.");
        }
        try {
            ByteBuffer buffer = Font.Builder.createBuffer(mgr, path, isAsset, cookie);
            return addFontFromBuffer(buffer, ttcIndex, axes, weight, isItalic);
        } catch (IOException e) {
            return false;
        }
    }
}
