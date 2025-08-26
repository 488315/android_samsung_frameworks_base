package android.graphics.fonts;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.BatteryStats;
import android.os.LocaleList;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.TypedValue;
import android.util.proto.ProtoStream;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.Set;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class Font {
    private static final int NOT_SPECIFIED = -1;
    private static final int STYLE_ITALIC = 1;
    private static final int STYLE_NORMAL = 0;
    private static final String TAG = "Font";
    private final long mNativePtr;
    private final Object mLock = new Object();
    private ByteBuffer mBuffer = null;
    private boolean mIsFileInitialized = false;
    private File mFile = null;
    private FontStyle mFontStyle = null;
    private FontVariationAxis[] mAxes = null;
    private LocaleList mLocaleList = null;

    @CriticalNative
    private static native long nCloneFont(long j);

    @FastNative
    private static native long[] nGetAvailableFontSet();

    @CriticalNative
    private static native int nGetAxisCount(long j);

    @CriticalNative
    private static native long nGetAxisInfo(long j, int i);

    @CriticalNative
    private static native long nGetBufferAddress(long j);

    @FastNative
    private static native float nGetFontMetrics(long j, long j2, Paint.FontMetrics fontMetrics);

    @FastNative
    private static native String nGetFontPath(long j);

    @FastNative
    private static native float nGetGlyphBounds(long j, int i, long j2, RectF rectF);

    @CriticalNative
    private static native int nGetIndex(long j);

    @FastNative
    private static native String nGetLocaleList(long j);

    @CriticalNative
    private static native long nGetMinikinFontPtr(long j);

    @CriticalNative
    private static native int nGetPackedStyle(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nGetReleaseNativeFont();

    @CriticalNative
    private static native int nGetSourceId(long j);

    @FastNative
    private static native ByteBuffer nNewByteBuffer(long j);

    private static class NoImagePreloadHolder {
        private static final NativeAllocationRegistry BUFFER_REGISTRY = NativeAllocationRegistry.createMalloced(ByteBuffer.class.getClassLoader(), Font.nGetReleaseNativeFont());
        private static final NativeAllocationRegistry FONT_REGISTRY = NativeAllocationRegistry.createMalloced(Font.class.getClassLoader(), Font.nGetReleaseNativeFont());

        private NoImagePreloadHolder() {
        }
    }

    public static final class Builder {
        private FontVariationAxis[] mAxes;
        private ByteBuffer mBuffer;
        private IOException mException;
        private File mFile;
        private Font mFont;
        private int mItalic;
        private String mLocaleList;
        private int mTtcIndex;
        private int mWeight;

        @CriticalNative
        private static native void nAddAxis(long j, int i, float f);

        private static native long nBuild(long j, ByteBuffer byteBuffer, String str, String str2, int i, boolean z, int i2);

        @FastNative
        private static native long nClone(long j, long j2, int i, boolean z, int i2);

        private static native long nInitBuilder();

        public Builder(ByteBuffer byteBuffer) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            Preconditions.checkNotNull(byteBuffer, "buffer can not be null");
            if (!byteBuffer.isDirect()) {
                throw new IllegalArgumentException("Only direct buffer can be used as the source of font data.");
            }
            this.mBuffer = byteBuffer;
        }

        public Builder(ByteBuffer byteBuffer, File file, String str) {
            this(byteBuffer);
            this.mFile = file;
            this.mLocaleList = str;
        }

        public Builder(File file, String str) {
            this(file);
            this.mLocaleList = str;
        }

        public Builder(File file) throws IOException {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            Preconditions.checkNotNull(file, "path can not be null");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    FileChannel channel = fileInputStream.getChannel();
                    this.mBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                    fileInputStream.close();
                } finally {
                }
            } catch (IOException e) {
                this.mException = e;
            }
            this.mFile = file;
        }

        public Builder(ParcelFileDescriptor parcelFileDescriptor) {
            this(parcelFileDescriptor, 0L, -1L);
        }

        public Builder(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws IOException {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    FileChannel channel = fileInputStream.getChannel();
                    this.mBuffer = channel.map(FileChannel.MapMode.READ_ONLY, j, j2 == -1 ? channel.size() - j : j2);
                    fileInputStream.close();
                } finally {
                }
            } catch (IOException e) {
                this.mException = e;
            }
        }

        public Builder(AssetManager assetManager, String str) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            try {
                this.mBuffer = createBuffer(assetManager, str, true, -1);
            } catch (IOException e) {
                this.mException = e;
            }
        }

        public Builder(AssetManager assetManager, String str, boolean z, int i) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            try {
                this.mBuffer = createBuffer(assetManager, str, z, i);
            } catch (IOException e) {
                this.mException = e;
            }
        }

        public Builder(Resources resources, int i) throws Resources.NotFoundException {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            TypedValue typedValue = new TypedValue();
            resources.getValue(i, typedValue, true);
            if (typedValue.string == null) {
                this.mException = new FileNotFoundException(i + " not found");
                return;
            }
            String string = typedValue.string.toString();
            if (string.toLowerCase().endsWith(".xml")) {
                this.mException = new FileNotFoundException(i + " must be font file.");
                return;
            }
            try {
                this.mBuffer = createBuffer(resources.getAssets(), string, false, typedValue.assetCookie);
            } catch (IOException e) {
                this.mException = e;
            }
        }

        public Builder(Font font) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            this.mFont = font;
            this.mBuffer = font.getBuffer();
            this.mWeight = font.getStyle().getWeight();
            this.mItalic = font.getStyle().getSlant();
            this.mAxes = font.getAxes();
            this.mFile = font.getFile();
            this.mTtcIndex = font.getTtcIndex();
        }

        public static ByteBuffer createBuffer(AssetManager assetManager, String str, boolean z, int i) throws IOException {
            InputStream inputStreamOpenNonAsset;
            AssetFileDescriptor assetFileDescriptorOpenNonAssetFd;
            Preconditions.checkNotNull(assetManager, "assetManager can not be null");
            Preconditions.checkNotNull(str, "path can not be null");
            try {
                if (z) {
                    assetFileDescriptorOpenNonAssetFd = assetManager.openFd(str);
                } else if (i > 0) {
                    assetFileDescriptorOpenNonAssetFd = assetManager.openNonAssetFd(i, str);
                } else {
                    assetFileDescriptorOpenNonAssetFd = assetManager.openNonAssetFd(str);
                }
                FileInputStream fileInputStreamCreateInputStream = assetFileDescriptorOpenNonAssetFd.createInputStream();
                try {
                    MappedByteBuffer map = fileInputStreamCreateInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, assetFileDescriptorOpenNonAssetFd.getStartOffset(), assetFileDescriptorOpenNonAssetFd.getDeclaredLength());
                    if (fileInputStreamCreateInputStream != null) {
                        fileInputStreamCreateInputStream.close();
                    }
                    return map;
                } finally {
                }
            } catch (IOException unused) {
                if (z) {
                    inputStreamOpenNonAsset = assetManager.open(str, 3);
                } else {
                    inputStreamOpenNonAsset = assetManager.openNonAsset(i, str, 3);
                }
                try {
                    ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(inputStreamOpenNonAsset.available());
                    byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
                    if (byteBufferAllocateDirect.hasArray()) {
                        inputStreamOpenNonAsset.read(byteBufferAllocateDirect.array(), byteBufferAllocateDirect.arrayOffset(), inputStreamOpenNonAsset.available());
                    } else {
                        Channels.newChannel(inputStreamOpenNonAsset).read(byteBufferAllocateDirect.duplicate());
                    }
                    if (inputStreamOpenNonAsset.read() == -1) {
                        if (inputStreamOpenNonAsset != null) {
                            inputStreamOpenNonAsset.close();
                        }
                        return byteBufferAllocateDirect;
                    }
                    throw new IOException("Unable to access full contents of " + str);
                } finally {
                }
            }
        }

        public Builder setWeight(int i) {
            Preconditions.checkArgument(1 <= i && i <= 1000);
            this.mWeight = i;
            return this;
        }

        public Builder setSlant(int i) {
            this.mItalic = i == 0 ? 0 : 1;
            return this;
        }

        public Builder setTtcIndex(int i) {
            this.mTtcIndex = i;
            return this;
        }

        public Builder setFontVariationSettings(String str) {
            this.mAxes = FontVariationAxis.fromFontVariationSettings(str);
            return this;
        }

        public Builder setFontVariationSettings(FontVariationAxis[] fontVariationAxisArr) {
            this.mAxes = fontVariationAxisArr == null ? null : (FontVariationAxis[]) fontVariationAxisArr.clone();
            return this;
        }

        public Font build() throws IOException {
            if (this.mException != null) {
                throw new IOException("Failed to read font contents", this.mException);
            }
            if (this.mWeight == -1 || this.mItalic == -1) {
                int iAnalyzeStyle = FontFileUtil.analyzeStyle(this.mBuffer, this.mTtcIndex, this.mAxes);
                if (FontFileUtil.isSuccess(iAnalyzeStyle)) {
                    if (this.mWeight == -1) {
                        this.mWeight = FontFileUtil.unpackWeight(iAnalyzeStyle);
                    }
                    if (this.mItalic == -1) {
                        this.mItalic = FontFileUtil.unpackItalic(iAnalyzeStyle) ? 1 : 0;
                    }
                } else {
                    this.mWeight = 400;
                    this.mItalic = 0;
                }
            }
            this.mWeight = Math.max(1, Math.min(1000, this.mWeight));
            boolean z = this.mItalic == 1;
            long jNInitBuilder = nInitBuilder();
            FontVariationAxis[] fontVariationAxisArr = this.mAxes;
            if (fontVariationAxisArr != null) {
                for (FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                    nAddAxis(jNInitBuilder, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
                }
            }
            ByteBuffer byteBufferAsReadOnlyBuffer = this.mBuffer.asReadOnlyBuffer();
            File file = this.mFile;
            String absolutePath = file == null ? "" : file.getAbsolutePath();
            Font font = this.mFont;
            if (font == null) {
                return new Font(nBuild(jNInitBuilder, byteBufferAsReadOnlyBuffer, absolutePath, this.mLocaleList, this.mWeight, z, this.mTtcIndex));
            }
            return new Font(nClone(font.getNativePtr(), jNInitBuilder, this.mWeight, z, this.mTtcIndex));
        }
    }

    public Font(long j) {
        this.mNativePtr = j;
        NoImagePreloadHolder.FONT_REGISTRY.registerNativeAllocation(this, j);
    }

    public ByteBuffer getBuffer() {
        ByteBuffer byteBuffer;
        synchronized (this.mLock) {
            if (this.mBuffer == null) {
                long jNCloneFont = nCloneFont(this.mNativePtr);
                ByteBuffer byteBufferNNewByteBuffer = nNewByteBuffer(this.mNativePtr);
                NoImagePreloadHolder.BUFFER_REGISTRY.registerNativeAllocation(byteBufferNNewByteBuffer, jNCloneFont);
                this.mBuffer = byteBufferNNewByteBuffer.asReadOnlyBuffer();
            }
            byteBuffer = this.mBuffer;
        }
        return byteBuffer;
    }

    public File getFile() {
        File file;
        synchronized (this.mLock) {
            if (!this.mIsFileInitialized) {
                String strNGetFontPath = nGetFontPath(this.mNativePtr);
                if (!TextUtils.isEmpty(strNGetFontPath)) {
                    this.mFile = new File(strNGetFontPath);
                }
                this.mIsFileInitialized = true;
            }
            file = this.mFile;
        }
        return file;
    }

    public FontStyle getStyle() {
        FontStyle fontStyle;
        synchronized (this.mLock) {
            if (this.mFontStyle == null) {
                int iNGetPackedStyle = nGetPackedStyle(this.mNativePtr);
                this.mFontStyle = new FontStyle(FontFileUtil.unpackWeight(iNGetPackedStyle), FontFileUtil.unpackItalic(iNGetPackedStyle) ? 1 : 0);
            }
            fontStyle = this.mFontStyle;
        }
        return fontStyle;
    }

    public int getTtcIndex() {
        return nGetIndex(this.mNativePtr);
    }

    public FontVariationAxis[] getAxes() {
        synchronized (this.mLock) {
            if (this.mAxes == null) {
                int iNGetAxisCount = nGetAxisCount(this.mNativePtr);
                this.mAxes = new FontVariationAxis[iNGetAxisCount];
                for (int i = 0; i < iNGetAxisCount; i++) {
                    this.mAxes[i] = new FontVariationAxis(new String(new char[]{(char) ((BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK & r4) >>> 56), (char) ((BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK & r4) >>> 48), (char) ((BatteryStats.STEP_LEVEL_LEVEL_MASK & r4) >>> 40), (char) ((r4 & ProtoStream.FIELD_TYPE_MASK) >>> 32)}), Float.intBitsToFloat((int) (4294967295L & nGetAxisInfo(this.mNativePtr, i))));
                }
            }
        }
        return this.mAxes;
    }

    public LocaleList getLocaleList() {
        LocaleList localeList;
        synchronized (this.mLock) {
            if (this.mLocaleList == null) {
                String strNGetLocaleList = nGetLocaleList(this.mNativePtr);
                if (TextUtils.isEmpty(strNGetLocaleList)) {
                    this.mLocaleList = LocaleList.getEmptyLocaleList();
                } else {
                    this.mLocaleList = LocaleList.forLanguageTags(strNGetLocaleList);
                }
            }
            localeList = this.mLocaleList;
        }
        return localeList;
    }

    public float getGlyphBounds(int i, Paint paint, RectF rectF) {
        return nGetGlyphBounds(this.mNativePtr, i, paint.getNativeInstance(), rectF);
    }

    public void getMetrics(Paint paint, Paint.FontMetrics fontMetrics) {
        nGetFontMetrics(this.mNativePtr, paint.getNativeInstance(), fontMetrics);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }

    public int getSourceIdentifier() {
        return nGetSourceId(this.mNativePtr);
    }

    private boolean isSameSource(Font font) {
        Objects.requireNonNull(font);
        ByteBuffer buffer = getBuffer();
        ByteBuffer buffer2 = font.getBuffer();
        if (buffer == buffer2) {
            return true;
        }
        if (buffer.capacity() != buffer2.capacity()) {
            return false;
        }
        if (getSourceIdentifier() == font.getSourceIdentifier() && buffer.position() == buffer2.position()) {
            return true;
        }
        return buffer.equals(buffer2);
    }

    public boolean paramEquals(Font font) {
        return font.getStyle().equals(getStyle()) && font.getTtcIndex() == getTtcIndex() && Arrays.equals(font.getAxes(), getAxes()) && Objects.equals(font.getLocaleList(), getLocaleList()) && Objects.equals(getFile(), font.getFile());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Font)) {
            return false;
        }
        Font font = (Font) obj;
        if (nGetMinikinFontPtr(this.mNativePtr) == nGetMinikinFontPtr(font.mNativePtr)) {
            return true;
        }
        if (paramEquals(font)) {
            return isSameSource(font);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getStyle(), Integer.valueOf(getTtcIndex()), Integer.valueOf(Arrays.hashCode(getAxes())), getLocaleList());
    }

    public String toString() {
        return "Font {path=" + getFile() + ", style=" + getStyle() + ", ttcIndex=" + getTtcIndex() + ", axes=" + FontVariationAxis.toFontVariationSettings(getAxes()) + ", localeList=" + getLocaleList() + ", buffer=" + getBuffer() + "}";
    }

    public static Set<Font> getAvailableFonts() {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (long j : nGetAvailableFontSet()) {
            Font font = new Font(j);
            identityHashMap.put(font, font);
        }
        return Collections.unmodifiableSet(identityHashMap.keySet());
    }
}
