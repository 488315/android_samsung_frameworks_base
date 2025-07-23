package android.graphics.text;

import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.Preconditions;
import com.android.text.flags.Flags;
import dalvik.annotation.optimization.CriticalNative;
import java.util.ArrayList;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class PositionedGlyphs {
    public static final float NO_OVERRIDE = Float.MIN_VALUE;
    private final ArrayList<Font> mFonts;
    private final long mLayoutPtr;
    private final float mXOffset;
    private final float mYOffset;

    @CriticalNative
    private static native float nGetAscent(long j);

    @CriticalNative
    private static native float nGetDescent(long j);

    @CriticalNative
    private static native boolean nGetFakeBold(long j, int i);

    @CriticalNative
    private static native boolean nGetFakeItalic(long j, int i);

    @CriticalNative
    private static native long nGetFont(long j, int i);

    @CriticalNative
    private static native int nGetFontCount(long j);

    @CriticalNative
    private static native int nGetFontId(long j, int i);

    @CriticalNative
    private static native long nGetFontRef(long j, int i);

    @CriticalNative
    private static native int nGetGlyphCount(long j);

    @CriticalNative
    private static native int nGetGlyphId(long j, int i);

    @CriticalNative
    private static native float nGetItalicOverride(long j, int i);

    @CriticalNative
    private static native float nGetTotalAdvance(long j);

    @CriticalNative
    private static native float nGetWeightOverride(long j, int i);

    @CriticalNative
    private static native float nGetX(long j, int i);

    @CriticalNative
    private static native float nGetY(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nReleaseFunc();

    private static class NoImagePreloadHolder {
        private static final NativeAllocationRegistry REGISTRY = NativeAllocationRegistry.createMalloced(Typeface.class.getClassLoader(), PositionedGlyphs.nReleaseFunc());

        private NoImagePreloadHolder() {
        }
    }

    public float getAdvance() {
        return nGetTotalAdvance(this.mLayoutPtr);
    }

    public float getAscent() {
        return nGetAscent(this.mLayoutPtr);
    }

    public float getDescent() {
        return nGetDescent(this.mLayoutPtr);
    }

    public float getOffsetX() {
        return this.mXOffset;
    }

    public float getOffsetY() {
        return this.mYOffset;
    }

    public int glyphCount() {
        return nGetGlyphCount(this.mLayoutPtr);
    }

    public Font getFont(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        if (Flags.typefaceRedesignReadonly()) {
            return this.mFonts.get(nGetFontId(this.mLayoutPtr, i));
        }
        return this.mFonts.get(i);
    }

    public int getGlyphId(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        return nGetGlyphId(this.mLayoutPtr, i);
    }

    public float getGlyphX(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        return nGetX(this.mLayoutPtr, i) + this.mXOffset;
    }

    public float getGlyphY(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        return nGetY(this.mLayoutPtr, i) + this.mYOffset;
    }

    public boolean getFakeBold(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        return nGetFakeBold(this.mLayoutPtr, i);
    }

    public boolean getFakeItalic(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        return nGetFakeItalic(this.mLayoutPtr, i);
    }

    public float getWeightOverride(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        float nGetWeightOverride = nGetWeightOverride(this.mLayoutPtr, i);
        if (nGetWeightOverride == -1.0f) {
            return Float.MIN_VALUE;
        }
        return nGetWeightOverride;
    }

    public float getItalicOverride(int i) {
        Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, "index");
        float nGetItalicOverride = nGetItalicOverride(this.mLayoutPtr, i);
        if (nGetItalicOverride == -1.0f) {
            return Float.MIN_VALUE;
        }
        return nGetItalicOverride;
    }

    public PositionedGlyphs(long j, float f, float f2) {
        this.mLayoutPtr = j;
        this.mXOffset = f;
        this.mYOffset = f2;
        int i = 0;
        if (Flags.typefaceRedesignReadonly()) {
            int nGetFontCount = nGetFontCount(j);
            this.mFonts = new ArrayList<>(nGetFontCount);
            while (i < nGetFontCount) {
                this.mFonts.add(new Font(nGetFontRef(j, i)));
                i++;
            }
        } else {
            int nGetGlyphCount = nGetGlyphCount(j);
            this.mFonts = new ArrayList<>(nGetGlyphCount);
            long j2 = 0;
            Font font = null;
            while (i < nGetGlyphCount) {
                long nGetFont = nGetFont(j, i);
                if (j2 != nGetFont) {
                    font = new Font(nGetFont);
                    j2 = nGetFont;
                }
                this.mFonts.add(font);
                i++;
            }
        }
        NoImagePreloadHolder.REGISTRY.registerNativeAllocation(this, j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PositionedGlyphs)) {
            return false;
        }
        PositionedGlyphs positionedGlyphs = (PositionedGlyphs) obj;
        if (this.mXOffset != positionedGlyphs.mXOffset || this.mYOffset != positionedGlyphs.mYOffset || glyphCount() != positionedGlyphs.glyphCount()) {
            return false;
        }
        for (int i = 0; i < glyphCount(); i++) {
            if (getGlyphId(i) != positionedGlyphs.getGlyphId(i) || getGlyphX(i) != positionedGlyphs.getGlyphX(i) || getGlyphY(i) != positionedGlyphs.getGlyphY(i) || !getFont(i).equals(positionedGlyphs.getFont(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = Objects.hash(Float.valueOf(this.mXOffset), Float.valueOf(this.mYOffset));
        for (int i = 0; i < glyphCount(); i++) {
            hash = Objects.hash(Integer.valueOf(hash), Integer.valueOf(getGlyphId(i)), Float.valueOf(getGlyphX(i)), Float.valueOf(getGlyphY(i)), getFont(i));
        }
        return hash;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        for (int i = 0; i < glyphCount(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append("[ ID = " + getGlyphId(i) + ", pos = (" + getGlyphX(i) + "," + getGlyphY(i) + ") font = " + getFont(i) + " ]");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return "PositionedGlyphs{glyphs = " + sb.toString() + ", mXOffset=" + this.mXOffset + ", mYOffset=" + this.mYOffset + '}';
    }
}
