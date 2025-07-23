package com.android.systemui.blur.di;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SecPanelBlurBinding {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BlurType {
        public static final /* synthetic */ BlurType[] $VALUES;
        public static final BlurType ALT_VIEW;
        public static final BlurType BOUNCER;
        public static final BlurType FULL_SCREEN;
        public static final BlurType NONE;
        public static final BlurType QUICK_PANEL;

        static {
            BlurType blurType = new BlurType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = blurType;
            BlurType blurType2 = new BlurType("BOUNCER", 1);
            BOUNCER = blurType2;
            BlurType blurType3 = new BlurType("QUICK_PANEL", 2);
            QUICK_PANEL = blurType3;
            BlurType blurType4 = new BlurType("FULL_SCREEN", 3);
            FULL_SCREEN = blurType4;
            BlurType blurType5 = new BlurType("ALT_VIEW", 4);
            ALT_VIEW = blurType5;
            BlurType[] blurTypeArr = {blurType, blurType2, blurType3, blurType4, blurType5};
            $VALUES = blurTypeArr;
            EnumEntriesKt.enumEntries(blurTypeArr);
        }

        private BlurType(String str, int i) {
        }

        public static BlurType valueOf(String str) {
            return (BlurType) Enum.valueOf(BlurType.class, str);
        }

        public static BlurType[] values() {
            return (BlurType[]) $VALUES.clone();
        }
    }

    void doBlur(BlurType blurType);

    float getInterpolation(float f);

    void setFraction(float f);
}
