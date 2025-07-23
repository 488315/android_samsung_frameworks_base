package android.hardware.input;

import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.KeyCharacterMap;
import java.util.Objects;

/* loaded from: classes2.dex */
final class PhysicalKeyLayout {
    private static final SparseIntArray DEFAULT_KEYCODE_FOR_SCANCODE;
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

    private static boolean isSpecialKey(int i) {
        if (i == 0 || i == 82 || i == 66 || i == 67) {
            return true;
        }
        switch (i) {
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                return true;
            default:
                switch (i) {
                    case 113:
                    case 114:
                    case 115:
                        return true;
                    default:
                        switch (i) {
                            case 117:
                            case 118:
                            case 119:
                                return true;
                            default:
                                return false;
                        }
                }
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        DEFAULT_KEYCODE_FOR_SCANCODE = sparseIntArray;
        sparseIntArray.put(2, 8);
        sparseIntArray.put(3, 9);
        sparseIntArray.put(4, 10);
        sparseIntArray.put(5, 11);
        sparseIntArray.put(6, 12);
        sparseIntArray.put(7, 13);
        sparseIntArray.put(8, 14);
        sparseIntArray.put(9, 15);
        sparseIntArray.put(10, 16);
        sparseIntArray.put(11, 7);
        sparseIntArray.put(12, 69);
        sparseIntArray.put(13, 70);
        sparseIntArray.put(16, 45);
        sparseIntArray.put(17, 51);
        sparseIntArray.put(18, 33);
        sparseIntArray.put(19, 46);
        sparseIntArray.put(20, 48);
        sparseIntArray.put(21, 53);
        sparseIntArray.put(22, 49);
        sparseIntArray.put(23, 37);
        sparseIntArray.put(24, 43);
        sparseIntArray.put(25, 44);
        sparseIntArray.put(26, 71);
        sparseIntArray.put(27, 72);
        sparseIntArray.put(30, 29);
        sparseIntArray.put(31, 47);
        sparseIntArray.put(32, 32);
        sparseIntArray.put(33, 34);
        sparseIntArray.put(34, 35);
        sparseIntArray.put(35, 36);
        sparseIntArray.put(36, 38);
        sparseIntArray.put(37, 39);
        sparseIntArray.put(38, 40);
        sparseIntArray.put(39, 74);
        sparseIntArray.put(40, 75);
        sparseIntArray.put(41, 68);
        sparseIntArray.put(43, 73);
        sparseIntArray.put(44, 54);
        sparseIntArray.put(45, 52);
        sparseIntArray.put(46, 31);
        sparseIntArray.put(47, 50);
        sparseIntArray.put(48, 30);
        sparseIntArray.put(49, 42);
        sparseIntArray.put(50, 41);
        sparseIntArray.put(51, 55);
        sparseIntArray.put(52, 56);
        sparseIntArray.put(53, 76);
        sparseIntArray.put(86, 73);
        sparseIntArray.put(89, 217);
        sparseIntArray.put(124, 216);
    }

    public PhysicalKeyLayout(KeyCharacterMap keyCharacterMap, KeyboardLayout keyboardLayout) {
        initLayoutKeys(keyCharacterMap, keyboardLayout);
    }

    private void initLayoutKeys(KeyCharacterMap keyCharacterMap, KeyboardLayout keyboardLayout) {
        if (keyboardLayout == null) {
            createIsoLayout(keyCharacterMap);
            return;
        }
        if (keyboardLayout.isAnsiLayout()) {
            createAnsiLayout(keyCharacterMap);
        } else if (keyboardLayout.isJisLayout()) {
            createJisLayout(keyCharacterMap);
        } else {
            createIsoLayout(keyCharacterMap);
        }
    }

    public LayoutKey[][] getKeys() {
        return this.mKeys;
    }

    public EnterKey getEnterKey() {
        return this.mEnterKey;
    }

    private void createAnsiLayout(KeyCharacterMap keyCharacterMap) {
        this.mKeys = new LayoutKey[][]{new LayoutKey[]{getKey(keyCharacterMap, 41), getKey(keyCharacterMap, 2), getKey(keyCharacterMap, 3), getKey(keyCharacterMap, 4), getKey(keyCharacterMap, 5), getKey(keyCharacterMap, 6), getKey(keyCharacterMap, 7), getKey(keyCharacterMap, 8), getKey(keyCharacterMap, 9), getKey(keyCharacterMap, 10), getKey(keyCharacterMap, 11), getKey(keyCharacterMap, 12), getKey(keyCharacterMap, 13), getKey(67, 1.5f)}, new LayoutKey[]{getKey(61, 1.5f), getKey(keyCharacterMap, 16), getKey(keyCharacterMap, 17), getKey(keyCharacterMap, 18), getKey(keyCharacterMap, 19), getKey(keyCharacterMap, 20), getKey(keyCharacterMap, 21), getKey(keyCharacterMap, 22), getKey(keyCharacterMap, 23), getKey(keyCharacterMap, 24), getKey(keyCharacterMap, 25), getKey(keyCharacterMap, 26), getKey(keyCharacterMap, 27), getKey(keyCharacterMap, 43)}, new LayoutKey[]{getKey(115, 1.75f), getKey(keyCharacterMap, 30), getKey(keyCharacterMap, 31), getKey(keyCharacterMap, 32), getKey(keyCharacterMap, 33), getKey(keyCharacterMap, 34), getKey(keyCharacterMap, 35), getKey(keyCharacterMap, 36), getKey(keyCharacterMap, 37), getKey(keyCharacterMap, 38), getKey(keyCharacterMap, 39), getKey(keyCharacterMap, 40), getKey(66, 1.75f)}, new LayoutKey[]{getKey(59, 2.5f), getKey(keyCharacterMap, 44), getKey(keyCharacterMap, 45), getKey(keyCharacterMap, 46), getKey(keyCharacterMap, 47), getKey(keyCharacterMap, 48), getKey(keyCharacterMap, 49), getKey(keyCharacterMap, 50), getKey(keyCharacterMap, 51), getKey(keyCharacterMap, 52), getKey(keyCharacterMap, 53), getKey(60, 2.5f)}, new LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(62, 6.5f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
    }

    private void createIsoLayout(KeyCharacterMap keyCharacterMap) {
        this.mKeys = new LayoutKey[][]{new LayoutKey[]{getKey(keyCharacterMap, 41), getKey(keyCharacterMap, 2), getKey(keyCharacterMap, 3), getKey(keyCharacterMap, 4), getKey(keyCharacterMap, 5), getKey(keyCharacterMap, 6), getKey(keyCharacterMap, 7), getKey(keyCharacterMap, 8), getKey(keyCharacterMap, 9), getKey(keyCharacterMap, 10), getKey(keyCharacterMap, 11), getKey(keyCharacterMap, 12), getKey(keyCharacterMap, 13), getKey(67, 1.5f)}, new LayoutKey[]{getKey(61, 1.15f), getKey(keyCharacterMap, 16), getKey(keyCharacterMap, 17), getKey(keyCharacterMap, 18), getKey(keyCharacterMap, 19), getKey(keyCharacterMap, 20), getKey(keyCharacterMap, 21), getKey(keyCharacterMap, 22), getKey(keyCharacterMap, 23), getKey(keyCharacterMap, 24), getKey(keyCharacterMap, 25), getKey(keyCharacterMap, 26), getKey(keyCharacterMap, 27), getKey(66, 1.35f)}, new LayoutKey[]{getKey(61, 1.5f), getKey(keyCharacterMap, 30), getKey(keyCharacterMap, 31), getKey(keyCharacterMap, 32), getKey(keyCharacterMap, 33), getKey(keyCharacterMap, 34), getKey(keyCharacterMap, 35), getKey(keyCharacterMap, 36), getKey(keyCharacterMap, 37), getKey(keyCharacterMap, 38), getKey(keyCharacterMap, 39), getKey(keyCharacterMap, 40), getKey(keyCharacterMap, 43), getKey(66, 1.0f)}, new LayoutKey[]{getKey(59, 1.15f), getKey(keyCharacterMap, 86), getKey(keyCharacterMap, 44), getKey(keyCharacterMap, 45), getKey(keyCharacterMap, 46), getKey(keyCharacterMap, 47), getKey(keyCharacterMap, 48), getKey(keyCharacterMap, 49), getKey(keyCharacterMap, 50), getKey(keyCharacterMap, 51), getKey(keyCharacterMap, 52), getKey(keyCharacterMap, 53), getKey(60, 2.35f)}, new LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(62, 6.5f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
        this.mEnterKey = new EnterKey(1, 13, 1.35f, 1.0f);
    }

    private void createJisLayout(KeyCharacterMap keyCharacterMap) {
        this.mKeys = new LayoutKey[][]{new LayoutKey[]{getKey(keyCharacterMap, 41), getKey(keyCharacterMap, 2), getKey(keyCharacterMap, 3), getKey(keyCharacterMap, 4), getKey(keyCharacterMap, 5), getKey(keyCharacterMap, 6), getKey(keyCharacterMap, 7), getKey(keyCharacterMap, 8), getKey(keyCharacterMap, 9), getKey(keyCharacterMap, 10), getKey(keyCharacterMap, 11), getKey(keyCharacterMap, 12, 0.8f), getKey(keyCharacterMap, 13, 0.8f), getKey(keyCharacterMap, 124, 0.8f), getKey(67, 1.1f)}, new LayoutKey[]{getKey(61, 1.15f), getKey(keyCharacterMap, 16), getKey(keyCharacterMap, 17), getKey(keyCharacterMap, 18), getKey(keyCharacterMap, 19), getKey(keyCharacterMap, 20), getKey(keyCharacterMap, 21), getKey(keyCharacterMap, 22), getKey(keyCharacterMap, 23), getKey(keyCharacterMap, 24), getKey(keyCharacterMap, 25), getKey(keyCharacterMap, 26), getKey(keyCharacterMap, 27), getKey(66, 1.35f)}, new LayoutKey[]{getKey(61, 1.5f), getKey(keyCharacterMap, 30), getKey(keyCharacterMap, 31), getKey(keyCharacterMap, 32), getKey(keyCharacterMap, 33), getKey(keyCharacterMap, 34), getKey(keyCharacterMap, 35), getKey(keyCharacterMap, 36), getKey(keyCharacterMap, 37), getKey(keyCharacterMap, 38), getKey(keyCharacterMap, 39), getKey(keyCharacterMap, 40), getKey(keyCharacterMap, 86), getKey(66, 1.0f)}, new LayoutKey[]{getKey(59, 1.15f), getKey(keyCharacterMap, 44), getKey(keyCharacterMap, 45), getKey(keyCharacterMap, 46), getKey(keyCharacterMap, 47), getKey(keyCharacterMap, 48), getKey(keyCharacterMap, 49), getKey(keyCharacterMap, 50), getKey(keyCharacterMap, 51), getKey(keyCharacterMap, 52), getKey(keyCharacterMap, 53), getKey(keyCharacterMap, 89), getKey(60, 2.35f)}, new LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(0, 1.0f), getKey(62, 3.5f), getKey(0, 1.0f), getKey(0, 1.0f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
        this.mEnterKey = new EnterKey(1, 13, 1.35f, 1.0f);
    }

    private static LayoutKey getKey(KeyCharacterMap keyCharacterMap, int i, float f) {
        int mappedKeyOrDefault = keyCharacterMap.getMappedKeyOrDefault(i, DEFAULT_KEYCODE_FOR_SCANCODE.get(i, 0));
        return new LayoutKey(mappedKeyOrDefault, i, f, new KeyGlyph(keyCharacterMap, mappedKeyOrDefault));
    }

    private static LayoutKey getKey(KeyCharacterMap keyCharacterMap, int i) {
        return getKey(keyCharacterMap, i, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getKeyText(KeyCharacterMap keyCharacterMap, int i, int i2) {
        int i3;
        if (isSpecialKey(i) || (i3 = keyCharacterMap.get(i, i2) & Integer.MAX_VALUE) == 0) {
            return "";
        }
        if (Character.isValidCodePoint(i3)) {
            return String.valueOf(Character.toChars(i3));
        }
        return "□";
    }

    private static LayoutKey getKey(int i, float f) {
        return new LayoutKey(i, i, f, null);
    }

    public static boolean isSpecialKey(LayoutKey layoutKey) {
        return isSpecialKey(layoutKey.keyCode);
    }

    public static boolean isKeyPositionUnsure(LayoutKey layoutKey) {
        int i = layoutKey.scanCode;
        return i == 41 || i == 43 || i == 86;
    }

    public static final class LayoutKey extends Record {
        private final KeyGlyph glyph;
        private final int keyCode;
        private final float keyWeight;
        private final int scanCode;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof LayoutKey)) {
                return false;
            }
            LayoutKey layoutKey = (LayoutKey) obj;
            return this.keyCode == layoutKey.keyCode && this.scanCode == layoutKey.scanCode && this.keyWeight == layoutKey.keyWeight && Objects.equals(this.glyph, layoutKey.glyph);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.keyCode), Integer.valueOf(this.scanCode), Float.valueOf(this.keyWeight), this.glyph};
        }

        public LayoutKey(int keyCode, int scanCode, float keyWeight, KeyGlyph glyph) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.keyWeight = keyWeight;
            this.glyph = glyph;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public KeyGlyph glyph() {
            return this.glyph;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.keyCode, this.scanCode, this.keyWeight, this.glyph);
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
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), LayoutKey.class, "keyCode;scanCode;keyWeight;glyph");
        }
    }

    public static final class EnterKey extends Record {
        private final float bottomKeyWeight;
        private final int column;
        private final int row;
        private final float topKeyWeight;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof EnterKey)) {
                return false;
            }
            EnterKey enterKey = (EnterKey) obj;
            return this.row == enterKey.row && this.column == enterKey.column && this.topKeyWeight == enterKey.topKeyWeight && this.bottomKeyWeight == enterKey.bottomKeyWeight;
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.row), Integer.valueOf(this.column), Float.valueOf(this.topKeyWeight), Float.valueOf(this.bottomKeyWeight)};
        }

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
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.row, this.column, this.topKeyWeight, this.bottomKeyWeight);
        }

        public int row() {
            return this.row;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), EnterKey.class, "row;column;topKeyWeight;bottomKeyWeight");
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

        public KeyGlyph(KeyCharacterMap keyCharacterMap, int i) {
            this.mBaseText = PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 1048576);
            this.mShiftText = PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 65);
            this.mAltGrText = PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 1048610);
            this.mAltGrShiftText = PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 99);
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
