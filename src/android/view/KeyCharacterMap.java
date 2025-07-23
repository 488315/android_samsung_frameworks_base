package android.view;

import android.hardware.input.InputManagerGlobal;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AndroidRuntimeException;
import android.util.SparseIntArray;
import com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.Flags;
import com.android.internal.logging.nano.MetricsProto;
import java.text.Normalizer;

/* loaded from: classes4.dex */
public class KeyCharacterMap implements Parcelable {
    private static final int ACCENT_ACUTE = 180;
    private static final int ACCENT_APOSTROPHE = 39;
    private static final int ACCENT_BREVE = 728;
    private static final int ACCENT_CARON = 711;
    private static final int ACCENT_CEDILLA = 184;
    private static final int ACCENT_CIRCUMFLEX = 710;
    private static final int ACCENT_CIRCUMFLEX_LEGACY = 94;
    private static final int ACCENT_COMMA_ABOVE = 8125;
    private static final int ACCENT_COMMA_ABOVE_RIGHT = 700;
    private static final int ACCENT_DOT_ABOVE = 729;
    private static final int ACCENT_DOT_BELOW = 46;
    private static final int ACCENT_DOUBLE_ACUTE = 733;
    private static final int ACCENT_GRAVE = 715;
    private static final int ACCENT_GRAVE_LEGACY = 96;
    private static final int ACCENT_HOOK_ABOVE = 704;
    private static final int ACCENT_HORN = 39;
    private static final int ACCENT_MACRON = 175;
    private static final int ACCENT_MACRON_BELOW = 717;
    private static final int ACCENT_OGONEK = 731;
    private static final int ACCENT_QUOTATION_MARK = 34;
    private static final int ACCENT_REVERSED_COMMA_ABOVE = 701;
    private static final int ACCENT_RING_ABOVE = 730;
    private static final int ACCENT_STROKE = 45;
    private static final int ACCENT_TILDE = 732;
    private static final int ACCENT_TILDE_LEGACY = 126;
    private static final int ACCENT_TURNED_COMMA_ABOVE = 699;
    private static final int ACCENT_UMLAUT = 168;
    private static final int ACCENT_VERTICAL_LINE_ABOVE = 712;
    private static final int ACCENT_VERTICAL_LINE_BELOW = 716;
    public static final int ALPHA = 3;

    @Deprecated
    public static final int BUILT_IN_KEYBOARD = 0;
    private static final int CHAR_SPACE = 32;
    public static final int COMBINING_ACCENT = Integer.MIN_VALUE;
    public static final int COMBINING_ACCENT_MASK = Integer.MAX_VALUE;
    public static final Parcelable.Creator<KeyCharacterMap> CREATOR;
    public static final int FULL = 4;
    public static final char HEX_INPUT = 61184;
    public static final int MODIFIER_BEHAVIOR_CHORDED = 0;
    public static final int MODIFIER_BEHAVIOR_CHORDED_OR_TOGGLED = 1;
    public static final int NUMERIC = 1;
    public static final char PICKER_DIALOG_INPUT = 61185;
    public static final int PREDICTIVE = 2;
    public static final int SPECIAL_FUNCTION = 5;
    public static final int VIRTUAL_KEYBOARD = -1;
    private static final SparseIntArray sAccentToCombining;
    private static final SparseIntArray sCombiningToAccent;
    private static final StringBuilder sDeadKeyBuilder;
    private static final SparseIntArray sDeadKeyCache;
    private long mPtr;

    @Deprecated
    public static class KeyData {
        public static final int META_LENGTH = 4;
        public char displayLabel;
        public char[] meta = new char[4];
        public char number;
    }

    private static native void nativeApplyOverlay(long j, String str, String str2);

    private static native void nativeDispose(long j);

    private static native boolean nativeEquals(long j, long j2);

    private static native char nativeGetCharacter(long j, int i, int i2);

    private static native char nativeGetDisplayLabel(long j, int i);

    private static native KeyEvent[] nativeGetEvents(long j, char[] cArr);

    private static native boolean nativeGetFallbackAction(long j, int i, int i2, FallbackAction fallbackAction);

    private static native int nativeGetKeyboardType(long j);

    private static native int nativeGetMappedKey(long j, int i);

    private static native char nativeGetMatch(long j, int i, char[] cArr, int i2);

    private static native char nativeGetNumber(long j, int i);

    private static native KeyCharacterMap nativeObtainEmptyKeyCharacterMap(int i);

    private static native long nativeReadFromParcel(Parcel parcel);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sCombiningToAccent = sparseIntArray;
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        sAccentToCombining = sparseIntArray2;
        addCombining(768, 715);
        addCombining(769, 180);
        addCombining(770, 710);
        addCombining(771, 732);
        addCombining(772, 175);
        addCombining(774, 728);
        addCombining(775, 729);
        addCombining(776, 168);
        addCombining(777, 704);
        addCombining(778, 730);
        addCombining(779, 733);
        addCombining(780, 711);
        addCombining(786, 699);
        addCombining(787, ACCENT_COMMA_ABOVE);
        addCombining(788, 701);
        addCombining(789, 700);
        addCombining(MetricsProto.MetricsEvent.NOTIFICATION_SINCE_UPDATE_MILLIS, 39);
        addCombining(803, 46);
        addCombining(807, 184);
        addCombining(808, 731);
        addCombining(MetricsProto.MetricsEvent.PROVISIONING_TERMS_ACTIVITY_TIME_MS, 716);
        addCombining(817, 717);
        addCombining(821, 45);
        sparseIntArray.append(832, 715);
        sparseIntArray.append(833, 180);
        sparseIntArray.append(835, ACCENT_COMMA_ABOVE);
        sparseIntArray.append(781, 39);
        sparseIntArray.append(782, 34);
        sparseIntArray2.append(96, 768);
        sparseIntArray2.append(94, 770);
        sparseIntArray2.append(126, 771);
        sparseIntArray2.append(39, 769);
        sparseIntArray2.append(34, 776);
        sDeadKeyCache = new SparseIntArray();
        sDeadKeyBuilder = new StringBuilder();
        addDeadKey(45, 68, 272);
        addDeadKey(45, 71, 484);
        addDeadKey(45, 72, 294);
        addDeadKey(45, 73, 407);
        addDeadKey(45, 76, 321);
        addDeadKey(45, 79, 216);
        addDeadKey(45, 84, 358);
        addDeadKey(45, 100, 273);
        addDeadKey(45, 103, 485);
        addDeadKey(45, 104, 295);
        addDeadKey(45, 105, MetricsProto.MetricsEvent.PROVISIONING_ENTRY_POINT_QR_CODE);
        addDeadKey(45, 108, 322);
        addDeadKey(45, 111, 248);
        addDeadKey(45, 116, 359);
        CREATOR = new Parcelable.Creator<KeyCharacterMap>() { // from class: android.view.KeyCharacterMap.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyCharacterMap createFromParcel(Parcel parcel) {
                return new KeyCharacterMap(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyCharacterMap[] newArray(int i) {
                return new KeyCharacterMap[i];
            }
        };
    }

    private static void addCombining(int i, int i2) {
        sCombiningToAccent.append(i, i2);
        sAccentToCombining.append(i2, i);
    }

    private static void addDeadKey(int i, int i2, int i3) {
        int i4 = sAccentToCombining.get(i);
        if (i4 == 0) {
            throw new IllegalStateException("Invalid dead key declaration.");
        }
        sDeadKeyCache.put((i4 << 16) | i2, i3);
    }

    private KeyCharacterMap(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel must not be null");
        }
        long nativeReadFromParcel = nativeReadFromParcel(parcel);
        this.mPtr = nativeReadFromParcel;
        if (nativeReadFromParcel == 0) {
            throw new RuntimeException("Could not read KeyCharacterMap from parcel.");
        }
    }

    private KeyCharacterMap(long j) {
        this.mPtr = j;
    }

    protected void finalize() throws Throwable {
        long j = this.mPtr;
        if (j != 0) {
            nativeDispose(j);
            this.mPtr = 0L;
        }
    }

    public static KeyCharacterMap obtainEmptyMap(int i) {
        return nativeObtainEmptyKeyCharacterMap(i);
    }

    public static KeyCharacterMap load(int i) {
        InputManagerGlobal inputManagerGlobal = InputManagerGlobal.getInstance();
        InputDevice inputDevice = inputManagerGlobal.getInputDevice(i);
        if (inputDevice == null && (inputDevice = inputManagerGlobal.getInputDevice(-1)) == null) {
            throw new UnavailableException("Could not load key character map for device " + i);
        }
        return inputDevice.getKeyCharacterMap();
    }

    public static KeyCharacterMap load(String str, String str2) {
        KeyCharacterMap load = load(-1);
        load.applyOverlay(str, str2);
        return load;
    }

    private void applyOverlay(String str, String str2) {
        nativeApplyOverlay(this.mPtr, str, str2);
    }

    public int getMappedKeyOrDefault(int i, int i2) {
        int nativeGetMappedKey = nativeGetMappedKey(this.mPtr, i);
        return nativeGetMappedKey == 0 ? i2 : nativeGetMappedKey;
    }

    public int get(int i, int i2) {
        char nativeGetCharacter = nativeGetCharacter(this.mPtr, i, KeyEvent.normalizeMetaState(i2));
        int i3 = sCombiningToAccent.get(nativeGetCharacter);
        return i3 != 0 ? Integer.MIN_VALUE | i3 : nativeGetCharacter;
    }

    public FallbackAction getFallbackAction(int i, int i2) {
        FallbackAction obtain = FallbackAction.obtain();
        if (nativeGetFallbackAction(this.mPtr, i, KeyEvent.normalizeMetaState(i2), obtain)) {
            if (Flags.removeFallbackModifiers()) {
                obtain.metaState = 0;
                return obtain;
            }
            obtain.metaState = KeyEvent.normalizeMetaState(obtain.metaState);
            return obtain;
        }
        obtain.recycle();
        return null;
    }

    public char getNumber(int i) {
        return nativeGetNumber(this.mPtr, i);
    }

    public char getMatch(int i, char[] cArr) {
        return getMatch(i, cArr, 0);
    }

    public char getMatch(int i, char[] cArr, int i2) {
        if (cArr == null) {
            throw new IllegalArgumentException("chars must not be null.");
        }
        return nativeGetMatch(this.mPtr, i, cArr, KeyEvent.normalizeMetaState(i2));
    }

    public char getDisplayLabel(int i) {
        return nativeGetDisplayLabel(this.mPtr, i);
    }

    public static int getDeadChar(int i, int i2) {
        int i3;
        if (i2 == i || 32 == i2) {
            return i;
        }
        int i4 = sAccentToCombining.get(i);
        if (i4 == 0) {
            return 0;
        }
        int i5 = (i4 << 16) | i2;
        SparseIntArray sparseIntArray = sDeadKeyCache;
        synchronized (sparseIntArray) {
            i3 = sparseIntArray.get(i5, -1);
            if (i3 == -1) {
                StringBuilder sb = sDeadKeyBuilder;
                sb.setLength(0);
                sb.append((char) i2);
                sb.append((char) i4);
                String normalize = Normalizer.normalize(sb, Normalizer.Form.NFC);
                int codePointAt = normalize.codePointCount(0, normalize.length()) == 1 ? normalize.codePointAt(0) : 0;
                sparseIntArray.put(i5, codePointAt);
                i3 = codePointAt;
            }
        }
        return i3;
    }

    public static int getCombiningChar(int i) {
        return sAccentToCombining.get(i);
    }

    @Deprecated
    public boolean getKeyData(int i, KeyData keyData) {
        if (keyData.meta.length < 4) {
            throw new IndexOutOfBoundsException("results.meta.length must be >= 4");
        }
        char nativeGetDisplayLabel = nativeGetDisplayLabel(this.mPtr, i);
        if (nativeGetDisplayLabel == 0) {
            return false;
        }
        keyData.displayLabel = nativeGetDisplayLabel;
        keyData.number = nativeGetNumber(this.mPtr, i);
        keyData.meta[0] = nativeGetCharacter(this.mPtr, i, 0);
        keyData.meta[1] = nativeGetCharacter(this.mPtr, i, 1);
        keyData.meta[2] = nativeGetCharacter(this.mPtr, i, 2);
        keyData.meta[3] = nativeGetCharacter(this.mPtr, i, 3);
        return true;
    }

    public KeyEvent[] getEvents(char[] cArr) {
        if (cArr == null) {
            throw new IllegalArgumentException("chars must not be null.");
        }
        return nativeGetEvents(this.mPtr, cArr);
    }

    public boolean isPrintingKey(int i) {
        switch (Character.getType(nativeGetDisplayLabel(this.mPtr, i))) {
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return false;
            default:
                return true;
        }
    }

    public int getKeyboardType() {
        return nativeGetKeyboardType(this.mPtr);
    }

    public int getModifierBehavior() {
        int keyboardType = getKeyboardType();
        return (keyboardType == 4 || keyboardType == 5) ? 0 : 1;
    }

    public static boolean deviceHasKey(int i) {
        return InputManagerGlobal.getInstance().deviceHasKeys(new int[]{i})[0];
    }

    public static boolean[] deviceHasKeys(int[] iArr) {
        return InputManagerGlobal.getInstance().deviceHasKeys(iArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel must not be null");
        }
        nativeWriteToParcel(this.mPtr, parcel);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof KeyCharacterMap)) {
            KeyCharacterMap keyCharacterMap = (KeyCharacterMap) obj;
            long j = this.mPtr;
            if (j != 0) {
                long j2 = keyCharacterMap.mPtr;
                if (j2 != 0) {
                    return nativeEquals(j, j2);
                }
            }
            if (j == keyCharacterMap.mPtr) {
                return true;
            }
        }
        return false;
    }

    public static class UnavailableException extends AndroidRuntimeException {
        public UnavailableException(String str) {
            super(str);
        }
    }

    public static final class FallbackAction {
        private static final int MAX_RECYCLED = 10;
        private static FallbackAction sRecycleBin;
        private static final Object sRecycleLock = new Object();
        private static int sRecycledCount;
        public int keyCode;
        public int metaState;
        private FallbackAction next;

        private FallbackAction() {
        }

        public static FallbackAction obtain() {
            FallbackAction fallbackAction;
            synchronized (sRecycleLock) {
                fallbackAction = sRecycleBin;
                if (fallbackAction == null) {
                    fallbackAction = new FallbackAction();
                } else {
                    sRecycleBin = fallbackAction.next;
                    sRecycledCount--;
                    fallbackAction.next = null;
                }
            }
            return fallbackAction;
        }

        public void recycle() {
            synchronized (sRecycleLock) {
                int i = sRecycledCount;
                if (i < 10) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount = i + 1;
                } else {
                    this.next = null;
                }
            }
        }
    }
}
