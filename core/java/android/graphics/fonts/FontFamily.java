package android.graphics.fonts;

import android.util.SparseIntArray;

import com.android.internal.util.Preconditions;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;

import libcore.util.NativeAllocationRegistry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Set;

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
        private final ArrayList<Font> mFonts = new ArrayList<>();
        private final SparseIntArray mStyles = new SparseIntArray(4);

        @Retention(RetentionPolicy.SOURCE)
        public @interface VariableFontFamilyType {}

        @CriticalNative
        private static native void nAddFont(long j, long j2);

        private static native long nBuild(long j, String str, int i, boolean z, boolean z2, int i2);

        /* JADX INFO: Access modifiers changed from: private */
        @CriticalNative
        public static native long nGetReleaseNativeFamily();

        private static native long nInitBuilder();

        private static class NoImagePreloadHolder {
            private static final NativeAllocationRegistry sFamilyRegistry =
                    NativeAllocationRegistry.createMalloced(
                            FontFamily.class.getClassLoader(), Builder.nGetReleaseNativeFamily());

            private NoImagePreloadHolder() {}
        }

        public Builder(Font font) {
            Preconditions.checkNotNull(font, "font can not be null");
            this.mStyles.append(makeStyleIdentifier(font), 0);
            this.mFonts.add(font);
        }

        public Builder addFont(Font font) {
            Preconditions.checkNotNull(font, "font can not be null");
            int key = makeStyleIdentifier(font);
            if (this.mStyles.indexOfKey(key) >= 0) {
                throw new IllegalArgumentException(font + " has already been added");
            }
            this.mStyles.append(key, 0);
            this.mFonts.add(font);
            return this;
        }

        public FontFamily buildVariableFamily() {
            int variableFamilyType = analyzeAndResolveVariableType(this.mFonts);
            if (variableFamilyType == -1) {
                return null;
            }
            return build("", 0, true, false, variableFamilyType);
        }

        public FontFamily build() {
            return build("", 0, true, false, 0);
        }

        public FontFamily build(
                String langTags,
                int variant,
                boolean isCustomFallback,
                boolean isDefaultFallback,
                int variableFamilyType) {
            long builderPtr = nInitBuilder();
            for (int i = 0; i < this.mFonts.size(); i++) {
                nAddFont(builderPtr, this.mFonts.get(i).getNativePtr());
            }
            long ptr =
                    nBuild(
                            builderPtr,
                            langTags,
                            variant,
                            isCustomFallback,
                            isDefaultFallback,
                            variableFamilyType);
            FontFamily family = new FontFamily(ptr);
            NoImagePreloadHolder.sFamilyRegistry.registerNativeAllocation(family, ptr);
            return family;
        }

        private static int makeStyleIdentifier(Font font) {
            return font.getStyle().getWeight() | (font.getStyle().getSlant() << 16);
        }

        public static int analyzeAndResolveVariableType(ArrayList<Font> fonts) {
            if (fonts.size() > 2) {
                return -1;
            }
            if (fonts.size() == 1) {
                Font font = fonts.get(0);
                Set<Integer> supportedAxes =
                        FontFileUtil.getSupportedAxes(font.getBuffer(), font.getTtcIndex());
                if (supportedAxes.contains(Integer.valueOf(TAG_wght))) {
                    return supportedAxes.contains(Integer.valueOf(TAG_ital)) ? 2 : 1;
                }
                return -1;
            }
            for (int i = 0; i < fonts.size(); i++) {
                Font font2 = fonts.get(i);
                if (!FontFileUtil.getSupportedAxes(font2.getBuffer(), font2.getTtcIndex())
                        .contains(Integer.valueOf(TAG_wght))) {
                    return -1;
                }
            }
            boolean italic1 = fonts.get(0).getStyle().getSlant() == 1;
            boolean italic2 = fonts.get(1).getStyle().getSlant() == 1;
            if (italic1 == italic2) {
                return -1;
            }
            if (italic1) {
                Font firstFont = fonts.get(0);
                fonts.set(0, fonts.get(1));
                fonts.set(1, firstFont);
                return 3;
            }
            return 3;
        }
    }

    public FontFamily(long ptr) {
        this.mNativePtr = ptr;
    }

    public String getLangTags() {
        return nGetLangTags(this.mNativePtr);
    }

    public int getVariant() {
        return nGetVariant(this.mNativePtr);
    }

    public Font getFont(int index) {
        if (index < 0 || getSize() <= index) {
            throw new IndexOutOfBoundsException();
        }
        return new Font(nGetFont(this.mNativePtr, index));
    }

    public int getSize() {
        return nGetFontSize(this.mNativePtr);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }
}
