package android.graphics.fonts;

import android.util.SparseIntArray;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Set;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class FontFamily {
    private static final String TAG = "FontFamily";
    private final long mNativePtr;

    @CriticalNative
    private static native long nGetFont(long j, int i);

    @CriticalNative
    private static native int nGetFontSize(long j);

    @FastNative
    private static native String nGetLangTags(long j);

    @CriticalNative
    private static native int nGetVariant(long j);

    public static final class Builder {
        private static final int TAG_ital = 1769234796;
        private static final int TAG_wght = 2003265652;
        public static final int VARIABLE_FONT_FAMILY_TYPE_NONE = 0;
        public static final int VARIABLE_FONT_FAMILY_TYPE_SINGLE_FONT_WGHT_ITAL = 2;
        public static final int VARIABLE_FONT_FAMILY_TYPE_SINGLE_FONT_WGHT_ONLY = 1;
        public static final int VARIABLE_FONT_FAMILY_TYPE_TWO_FONTS_WGHT = 3;
        public static final int VARIABLE_FONT_FAMILY_TYPE_UNKNOWN = -1;
        private final ArrayList<Font> mFonts;
        private final SparseIntArray mStyles;

        @Retention(RetentionPolicy.SOURCE)
        public @interface VariableFontFamilyType {
        }

        @CriticalNative
        private static native void nAddFont(long j, long j2);

        private static native long nBuild(long j, String str, int i, boolean z, boolean z2, int i2);

        /* JADX INFO: Access modifiers changed from: private */
        @CriticalNative
        public static native long nGetReleaseNativeFamily();

        private static native long nInitBuilder();

        private static class NoImagePreloadHolder {
            private static final NativeAllocationRegistry sFamilyRegistry = NativeAllocationRegistry.createMalloced(FontFamily.class.getClassLoader(), Builder.nGetReleaseNativeFamily());

            private NoImagePreloadHolder() {
            }
        }

        public Builder(Font font) {
            ArrayList<Font> arrayList = new ArrayList<>();
            this.mFonts = arrayList;
            SparseIntArray sparseIntArray = new SparseIntArray(4);
            this.mStyles = sparseIntArray;
            Preconditions.checkNotNull(font, "font can not be null");
            sparseIntArray.append(makeStyleIdentifier(font), 0);
            arrayList.add(font);
        }

        public Builder addFont(Font font) {
            Preconditions.checkNotNull(font, "font can not be null");
            int iMakeStyleIdentifier = makeStyleIdentifier(font);
            if (this.mStyles.indexOfKey(iMakeStyleIdentifier) >= 0) {
                throw new IllegalArgumentException(font + " has already been added");
            }
            this.mStyles.append(iMakeStyleIdentifier, 0);
            this.mFonts.add(font);
            return this;
        }

        public FontFamily buildVariableFamily() {
            int iAnalyzeAndResolveVariableType = analyzeAndResolveVariableType(this.mFonts);
            if (iAnalyzeAndResolveVariableType == -1) {
                return null;
            }
            return build("", 0, true, false, iAnalyzeAndResolveVariableType);
        }

        public FontFamily build() {
            return build("", 0, true, false, 0);
        }

        public FontFamily build(String str, int i, boolean z, boolean z2, int i2) {
            long jNInitBuilder = nInitBuilder();
            for (int i3 = 0; i3 < this.mFonts.size(); i3++) {
                nAddFont(jNInitBuilder, this.mFonts.get(i3).getNativePtr());
            }
            long jNBuild = nBuild(jNInitBuilder, str, i, z, z2, i2);
            FontFamily fontFamily = new FontFamily(jNBuild);
            NoImagePreloadHolder.sFamilyRegistry.registerNativeAllocation(fontFamily, jNBuild);
            return fontFamily;
        }

        private static int makeStyleIdentifier(Font font) {
            return (font.getStyle().getSlant() << 16) | font.getStyle().getWeight();
        }

        public static int analyzeAndResolveVariableType(ArrayList<Font> arrayList) {
            if (arrayList.size() > 2) {
                return -1;
            }
            if (arrayList.size() == 1) {
                Font font = arrayList.get(0);
                Set<Integer> supportedAxes = FontFileUtil.getSupportedAxes(font.getBuffer(), font.getTtcIndex());
                if (supportedAxes.contains(Integer.valueOf(TAG_wght))) {
                    return supportedAxes.contains(Integer.valueOf(TAG_ital)) ? 2 : 1;
                }
                return -1;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                Font font2 = arrayList.get(i);
                if (!FontFileUtil.getSupportedAxes(font2.getBuffer(), font2.getTtcIndex()).contains(Integer.valueOf(TAG_wght))) {
                    return -1;
                }
            }
            boolean z = arrayList.get(0).getStyle().getSlant() == 1;
            if (z == (arrayList.get(1).getStyle().getSlant() == 1)) {
                return -1;
            }
            if (!z) {
                return 3;
            }
            Font font3 = arrayList.get(0);
            arrayList.set(0, arrayList.get(1));
            arrayList.set(1, font3);
            return 3;
        }
    }

    public FontFamily(long j) {
        this.mNativePtr = j;
    }

    public String getLangTags() {
        return nGetLangTags(this.mNativePtr);
    }

    public int getVariant() {
        return nGetVariant(this.mNativePtr);
    }

    public Font getFont(int i) {
        if (i < 0 || getSize() <= i) {
            throw new IndexOutOfBoundsException();
        }
        return new Font(nGetFont(this.mNativePtr, i));
    }

    public int getSize() {
        return nGetFontSize(this.mNativePtr);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }
}
