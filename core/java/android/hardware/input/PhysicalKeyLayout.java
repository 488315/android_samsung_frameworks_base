package android.hardware.input;

import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.KeyCharacterMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* loaded from: classes2.dex */
final class PhysicalKeyLayout {
    private static final SparseIntArray DEFAULT_KEYCODE_FOR_SCANCODE = new SparseIntArray();
    private static final int SCANCODE_0 = 11;
    private static final int SCANCODE_1 = 2;
    private static final int SCANCODE_2 = 3;
    private static final int SCANCODE_3 = 4;
    private static final int SCANCODE_4 = 5;
    private static final int SCANCODE_5 = 6;
    private static final int SCANCODE_6 = 7;
    private static final int SCANCODE_7 = 8;
    private static final int SCANCODE_8 = 9;
    private static final int SCANCODE_9 = 10;
    private static final int SCANCODE_A = 30;
    private static final int SCANCODE_APOSTROPHE = 40;
    private static final int SCANCODE_B = 48;
    private static final int SCANCODE_BACKSLASH1 = 43;
    private static final int SCANCODE_BACKSLASH2 = 86;
    private static final int SCANCODE_C = 46;
    private static final int SCANCODE_COMMA = 51;
    private static final int SCANCODE_D = 32;
    private static final int SCANCODE_E = 18;
    private static final int SCANCODE_EQUALS = 13;
    private static final int SCANCODE_F = 33;
    private static final int SCANCODE_G = 34;
    private static final int SCANCODE_GRAVE = 41;
    private static final int SCANCODE_H = 35;
    private static final int SCANCODE_I = 23;
    private static final int SCANCODE_J = 36;
    private static final int SCANCODE_K = 37;
    private static final int SCANCODE_L = 38;
    private static final int SCANCODE_LEFT_BRACKET = 26;
    private static final int SCANCODE_M = 50;
    private static final int SCANCODE_MINUS = 12;
    private static final int SCANCODE_N = 49;
    private static final int SCANCODE_O = 24;
    private static final int SCANCODE_P = 25;
    private static final int SCANCODE_PERIOD = 52;
    private static final int SCANCODE_Q = 16;
    private static final int SCANCODE_R = 19;
    private static final int SCANCODE_RIGHT_BRACKET = 27;
    private static final int SCANCODE_RO = 89;
    private static final int SCANCODE_S = 31;
    private static final int SCANCODE_SEMICOLON = 39;
    private static final int SCANCODE_SLASH = 53;
    private static final int SCANCODE_T = 20;
    private static final int SCANCODE_U = 22;
    private static final int SCANCODE_V = 47;
    private static final int SCANCODE_W = 17;
    private static final int SCANCODE_X = 45;
    private static final int SCANCODE_Y = 21;
    private static final int SCANCODE_YEN = 124;
    private static final int SCANCODE_Z = 44;
    private static final String TAG = "KeyboardLayoutPreview";
    private LayoutKey[][] mKeys = null;
    private EnterKey mEnterKey = null;

    static {
        DEFAULT_KEYCODE_FOR_SCANCODE.put(2, 8);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(3, 9);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(4, 10);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(5, 11);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(6, 12);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(7, 13);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(8, 14);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(9, 15);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(10, 16);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(11, 7);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(12, 69);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(13, 70);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(16, 45);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(17, 51);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(18, 33);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(19, 46);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(20, 48);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(21, 53);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(22, 49);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(23, 37);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(24, 43);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(25, 44);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(26, 71);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(27, 72);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(30, 29);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(31, 47);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(32, 32);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(33, 34);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(34, 35);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(35, 36);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(36, 38);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(37, 39);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(38, 40);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(39, 74);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(40, 75);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(41, 68);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(43, 73);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(44, 54);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(45, 52);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(46, 31);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(47, 50);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(48, 30);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(49, 42);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(50, 41);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(51, 55);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(52, 56);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(53, 76);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(86, 73);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(89, 217);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(124, 216);
    }

    public PhysicalKeyLayout(KeyCharacterMap kcm, KeyboardLayout layout) {
        initLayoutKeys(kcm, layout);
    }

    private void initLayoutKeys(KeyCharacterMap kcm, KeyboardLayout layout) {
        if (layout == null) {
            createIsoLayout(kcm);
            return;
        }
        if (layout.isAnsiLayout()) {
            createAnsiLayout(kcm);
        } else if (layout.isJisLayout()) {
            createJisLayout(kcm);
        } else {
            createIsoLayout(kcm);
        }
    }

    public LayoutKey[][] getKeys() {
        return this.mKeys;
    }

    public EnterKey getEnterKey() {
        return this.mEnterKey;
    }

    private void createAnsiLayout(KeyCharacterMap kcm) {
        this.mKeys = new LayoutKey[][]{new LayoutKey[]{getKey(kcm, 41), getKey(kcm, 2), getKey(kcm, 3), getKey(kcm, 4), getKey(kcm, 5), getKey(kcm, 6), getKey(kcm, 7), getKey(kcm, 8), getKey(kcm, 9), getKey(kcm, 10), getKey(kcm, 11), getKey(kcm, 12), getKey(kcm, 13), getKey(67, 1.5f)}, new LayoutKey[]{getKey(61, 1.5f), getKey(kcm, 16), getKey(kcm, 17), getKey(kcm, 18), getKey(kcm, 19), getKey(kcm, 20), getKey(kcm, 21), getKey(kcm, 22), getKey(kcm, 23), getKey(kcm, 24), getKey(kcm, 25), getKey(kcm, 26), getKey(kcm, 27), getKey(kcm, 43)}, new LayoutKey[]{getKey(115, 1.75f), getKey(kcm, 30), getKey(kcm, 31), getKey(kcm, 32), getKey(kcm, 33), getKey(kcm, 34), getKey(kcm, 35), getKey(kcm, 36), getKey(kcm, 37), getKey(kcm, 38), getKey(kcm, 39), getKey(kcm, 40), getKey(66, 1.75f)}, new LayoutKey[]{getKey(59, 2.5f), getKey(kcm, 44), getKey(kcm, 45), getKey(kcm, 46), getKey(kcm, 47), getKey(kcm, 48), getKey(kcm, 49), getKey(kcm, 50), getKey(kcm, 51), getKey(kcm, 52), getKey(kcm, 53), getKey(60, 2.5f)}, new LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(62, 6.5f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
    }

    private void createIsoLayout(KeyCharacterMap kcm) {
        this.mKeys = new LayoutKey[][]{new LayoutKey[]{getKey(kcm, 41), getKey(kcm, 2), getKey(kcm, 3), getKey(kcm, 4), getKey(kcm, 5), getKey(kcm, 6), getKey(kcm, 7), getKey(kcm, 8), getKey(kcm, 9), getKey(kcm, 10), getKey(kcm, 11), getKey(kcm, 12), getKey(kcm, 13), getKey(67, 1.5f)}, new LayoutKey[]{getKey(61, 1.15f), getKey(kcm, 16), getKey(kcm, 17), getKey(kcm, 18), getKey(kcm, 19), getKey(kcm, 20), getKey(kcm, 21), getKey(kcm, 22), getKey(kcm, 23), getKey(kcm, 24), getKey(kcm, 25), getKey(kcm, 26), getKey(kcm, 27), getKey(66, 1.35f)}, new LayoutKey[]{getKey(61, 1.5f), getKey(kcm, 30), getKey(kcm, 31), getKey(kcm, 32), getKey(kcm, 33), getKey(kcm, 34), getKey(kcm, 35), getKey(kcm, 36), getKey(kcm, 37), getKey(kcm, 38), getKey(kcm, 39), getKey(kcm, 40), getKey(kcm, 43), getKey(66, 1.0f)}, new LayoutKey[]{getKey(59, 1.15f), getKey(kcm, 86), getKey(kcm, 44), getKey(kcm, 45), getKey(kcm, 46), getKey(kcm, 47), getKey(kcm, 48), getKey(kcm, 49), getKey(kcm, 50), getKey(kcm, 51), getKey(kcm, 52), getKey(kcm, 53), getKey(60, 2.35f)}, new LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(62, 6.5f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
        this.mEnterKey = new EnterKey(1, 13, 1.35f, 1.0f);
    }

    private void createJisLayout(KeyCharacterMap kcm) {
        this.mKeys = new LayoutKey[][]{new LayoutKey[]{getKey(kcm, 41), getKey(kcm, 2), getKey(kcm, 3), getKey(kcm, 4), getKey(kcm, 5), getKey(kcm, 6), getKey(kcm, 7), getKey(kcm, 8), getKey(kcm, 9), getKey(kcm, 10), getKey(kcm, 11), getKey(kcm, 12, 0.8f), getKey(kcm, 13, 0.8f), getKey(kcm, 124, 0.8f), getKey(67, 1.1f)}, new LayoutKey[]{getKey(61, 1.15f), getKey(kcm, 16), getKey(kcm, 17), getKey(kcm, 18), getKey(kcm, 19), getKey(kcm, 20), getKey(kcm, 21), getKey(kcm, 22), getKey(kcm, 23), getKey(kcm, 24), getKey(kcm, 25), getKey(kcm, 26), getKey(kcm, 27), getKey(66, 1.35f)}, new LayoutKey[]{getKey(61, 1.5f), getKey(kcm, 30), getKey(kcm, 31), getKey(kcm, 32), getKey(kcm, 33), getKey(kcm, 34), getKey(kcm, 35), getKey(kcm, 36), getKey(kcm, 37), getKey(kcm, 38), getKey(kcm, 39), getKey(kcm, 40), getKey(kcm, 86), getKey(66, 1.0f)}, new LayoutKey[]{getKey(59, 1.15f), getKey(kcm, 44), getKey(kcm, 45), getKey(kcm, 46), getKey(kcm, 47), getKey(kcm, 48), getKey(kcm, 49), getKey(kcm, 50), getKey(kcm, 51), getKey(kcm, 52), getKey(kcm, 53), getKey(kcm, 89), getKey(60, 2.35f)}, new LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(0, 1.0f), getKey(62, 3.5f), getKey(0, 1.0f), getKey(0, 1.0f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
        this.mEnterKey = new EnterKey(1, 13, 1.35f, 1.0f);
    }

    private static LayoutKey getKey(KeyCharacterMap kcm, int scanCode, float keyWeight) {
        int keyCode = kcm.getMappedKeyOrDefault(scanCode, DEFAULT_KEYCODE_FOR_SCANCODE.get(scanCode, 0));
        return new LayoutKey(keyCode, scanCode, keyWeight, new KeyGlyph(kcm, keyCode));
    }

    private static LayoutKey getKey(KeyCharacterMap kcm, int scanCode) {
        return getKey(kcm, scanCode, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getKeyText(KeyCharacterMap kcm, int keyCode, int modifierState) {
        int utf8Char;
        if (isSpecialKey(keyCode) || (utf8Char = kcm.get(keyCode, modifierState) & Integer.MAX_VALUE) == 0) {
            return "";
        }
        if (Character.isValidCodePoint(utf8Char)) {
            return String.valueOf(Character.toChars(utf8Char));
        }
        return "□";
    }

    private static LayoutKey getKey(int keyCode, float keyWeight) {
        return new LayoutKey(keyCode, keyCode, keyWeight, null);
    }

    private static boolean isSpecialKey(int keyCode) {
        switch (keyCode) {
            case 0:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 66:
            case 67:
            case 82:
            case 113:
            case 114:
            case 115:
            case 117:
            case 118:
            case 119:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSpecialKey(LayoutKey key) {
        return isSpecialKey(key.keyCode);
    }

    public static boolean isKeyPositionUnsure(LayoutKey key) {
        switch (key.scanCode) {
            case 41:
            case 43:
            case 86:
                return true;
            default:
                return false;
        }
    }

    public static final class LayoutKey extends Record {
        private final KeyGlyph glyph;
        private final int keyCode;
        private final float keyWeight;
        private final int scanCode;

        public LayoutKey(int keyCode, int scanCode, float keyWeight, KeyGlyph glyph) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.keyWeight = keyWeight;
            this.glyph = glyph;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LayoutKey.class, Object.class), LayoutKey.class, "keyCode;scanCode;keyWeight;glyph", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->scanCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->glyph:Landroid/hardware/input/PhysicalKeyLayout$KeyGlyph;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public KeyGlyph glyph() {
            return this.glyph;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LayoutKey.class), LayoutKey.class, "keyCode;scanCode;keyWeight;glyph", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->scanCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->glyph:Landroid/hardware/input/PhysicalKeyLayout$KeyGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public int keyCode() {
            return this.keyCode;
        }

        public float keyWeight() {
            return this.keyWeight;
        }

        public int scanCode() {
            return this.scanCode;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LayoutKey.class), LayoutKey.class, "keyCode;scanCode;keyWeight;glyph", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->scanCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->glyph:Landroid/hardware/input/PhysicalKeyLayout$KeyGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }
    }

    public static final class EnterKey extends Record {
        private final float bottomKeyWeight;
        private final int column;
        private final int row;
        private final float topKeyWeight;

        public EnterKey(int row, int column, float topKeyWeight, float bottomKeyWeight) {
            this.row = row;
            this.column = column;
            this.topKeyWeight = topKeyWeight;
            this.bottomKeyWeight = bottomKeyWeight;
        }

        public float bottomKeyWeight() {
            return this.bottomKeyWeight;
        }

        public int column() {
            return this.column;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EnterKey.class, Object.class), EnterKey.class, "row;column;topKeyWeight;bottomKeyWeight", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->row:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->column:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->topKeyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->bottomKeyWeight:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EnterKey.class), EnterKey.class, "row;column;topKeyWeight;bottomKeyWeight", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->row:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->column:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->topKeyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->bottomKeyWeight:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public int row() {
            return this.row;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EnterKey.class), EnterKey.class, "row;column;topKeyWeight;bottomKeyWeight", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->row:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->column:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->topKeyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->bottomKeyWeight:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public float topKeyWeight() {
            return this.topKeyWeight;
        }
    }

    public static class KeyGlyph {
        private final String mAltGrShiftText;
        private final String mAltGrText;
        private final String mBaseText;
        private final String mShiftText;

        public KeyGlyph(KeyCharacterMap kcm, int keyCode) {
            this.mBaseText = PhysicalKeyLayout.getKeyText(kcm, keyCode, 1048576);
            this.mShiftText = PhysicalKeyLayout.getKeyText(kcm, keyCode, 65);
            this.mAltGrText = PhysicalKeyLayout.getKeyText(kcm, keyCode, 1048610);
            this.mAltGrShiftText = PhysicalKeyLayout.getKeyText(kcm, keyCode, 99);
        }

        public String getBaseText() {
            return this.mBaseText;
        }

        public String getShiftText() {
            return this.mShiftText;
        }

        public String getAltGrText() {
            return this.mAltGrText;
        }

        public String getAltGrShiftText() {
            return this.mAltGrShiftText;
        }

        public boolean hasBaseText() {
            return !TextUtils.isEmpty(this.mBaseText);
        }

        public boolean hasValidShiftText() {
            return (TextUtils.isEmpty(this.mShiftText) || TextUtils.equals(this.mBaseText, this.mShiftText)) ? false : true;
        }

        public boolean hasValidAltGrText() {
            return (TextUtils.isEmpty(this.mAltGrText) || TextUtils.equals(this.mBaseText, this.mAltGrText) || TextUtils.equals(this.mShiftText, this.mAltGrText)) ? false : true;
        }

        public boolean hasValidAltGrShiftText() {
            return (TextUtils.isEmpty(this.mAltGrShiftText) || TextUtils.equals(this.mBaseText, this.mAltGrShiftText) || TextUtils.equals(this.mAltGrText, this.mAltGrShiftText) || TextUtils.equals(this.mShiftText, this.mAltGrShiftText)) ? false : true;
        }
    }
}
