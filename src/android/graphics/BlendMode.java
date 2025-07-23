package android.graphics;

import android.graphics.PorterDuff;

/* loaded from: classes.dex */
public enum BlendMode {
    CLEAR(0),
    SRC(1),
    DST(2),
    SRC_OVER(3),
    DST_OVER(4),
    SRC_IN(5),
    DST_IN(6),
    SRC_OUT(7),
    DST_OUT(8),
    SRC_ATOP(9),
    DST_ATOP(10),
    XOR(11),
    PLUS(12),
    MODULATE(13),
    SCREEN(14),
    OVERLAY(15),
    DARKEN(16),
    LIGHTEN(17),
    COLOR_DODGE(18),
    COLOR_BURN(19),
    HARD_LIGHT(20),
    SOFT_LIGHT(21),
    DIFFERENCE(22),
    EXCLUSION(23),
    MULTIPLY(24),
    HUE(25),
    SATURATION(26),
    COLOR(27),
    LUMINOSITY(28);

    private static final BlendMode[] BLEND_MODES = values();
    private final PorterDuffXfermode mXfermode;

    public static BlendMode fromValue(int i) {
        for (BlendMode blendMode : BLEND_MODES) {
            if (blendMode.mXfermode.porterDuffMode == i) {
                return blendMode;
            }
        }
        return null;
    }

    public static int toValue(BlendMode blendMode) {
        return blendMode.getXfermode().porterDuffMode;
    }

    public static PorterDuff.Mode blendModeToPorterDuffMode(BlendMode blendMode) {
        if (blendMode == null) {
            return null;
        }
        switch (blendMode) {
            case CLEAR:
                return PorterDuff.Mode.CLEAR;
            case SRC:
                return PorterDuff.Mode.SRC;
            case DST:
                return PorterDuff.Mode.DST;
            case SRC_OVER:
                return PorterDuff.Mode.SRC_OVER;
            case DST_OVER:
                return PorterDuff.Mode.DST_OVER;
            case SRC_IN:
                return PorterDuff.Mode.SRC_IN;
            case DST_IN:
                return PorterDuff.Mode.DST_IN;
            case SRC_OUT:
                return PorterDuff.Mode.SRC_OUT;
            case DST_OUT:
                return PorterDuff.Mode.DST_OUT;
            case SRC_ATOP:
                return PorterDuff.Mode.SRC_ATOP;
            case DST_ATOP:
                return PorterDuff.Mode.DST_ATOP;
            case XOR:
                return PorterDuff.Mode.XOR;
            case PLUS:
                return PorterDuff.Mode.ADD;
            case MODULATE:
                return PorterDuff.Mode.MULTIPLY;
            case SCREEN:
                return PorterDuff.Mode.SCREEN;
            case OVERLAY:
                return PorterDuff.Mode.OVERLAY;
            case DARKEN:
                return PorterDuff.Mode.DARKEN;
            case LIGHTEN:
                return PorterDuff.Mode.LIGHTEN;
            default:
                return null;
        }
    }

    BlendMode(int i) {
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode();
        this.mXfermode = porterDuffXfermode;
        porterDuffXfermode.porterDuffMode = i;
    }

    public PorterDuffXfermode getXfermode() {
        return this.mXfermode;
    }
}
