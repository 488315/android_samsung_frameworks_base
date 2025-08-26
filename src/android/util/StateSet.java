package android.util;

import com.android.internal.R;

/* loaded from: classes4.dex */
public class StateSet {
    public static final int[] NOTHING;
    public static final int VIEW_STATE_ACCELERATED = 64;
    public static final int VIEW_STATE_ACTIVATED = 32;
    public static final int VIEW_STATE_DRAG_CAN_ACCEPT = 256;
    public static final int VIEW_STATE_DRAG_HOVERED = 512;
    public static final int VIEW_STATE_ENABLED = 8;
    public static final int VIEW_STATE_FOCUSED = 4;
    public static final int VIEW_STATE_HOVERED = 128;
    static final int[] VIEW_STATE_IDS;
    public static final int VIEW_STATE_PRESSED = 16;
    public static final int VIEW_STATE_SELECTED = 2;
    private static final int[][] VIEW_STATE_SETS;
    public static final int VIEW_STATE_SPEN_HOVERED = 1024;
    public static final int VIEW_STATE_WINDOW_FOCUSED = 1;
    public static final int[] WILD_CARD;

    static {
        int[] iArr = {16842909, 1, 16842913, 2, 16842908, 4, 16842910, 8, 16842919, 16, 16843518, 32, 16843547, 64, 16843623, 128, 16843624, 256, 16843625, 512, R.attr.zzz_state_spen_hovered, 1024};
        VIEW_STATE_IDS = iArr;
        if (iArr.length / 2 != R.styleable.ViewDrawableStates.length) {
            throw new IllegalStateException("VIEW_STATE_IDs array length does not match ViewDrawableStates style array");
        }
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i = 0; i < R.styleable.ViewDrawableStates.length; i++) {
            int i2 = R.styleable.ViewDrawableStates[i];
            int i3 = 0;
            while (true) {
                int[] iArr3 = VIEW_STATE_IDS;
                if (i3 < iArr3.length) {
                    if (iArr3[i3] == i2) {
                        int i4 = i * 2;
                        iArr2[i4] = i2;
                        iArr2[i4 + 1] = iArr3[i3 + 1];
                    }
                    i3 += 2;
                }
            }
        }
        VIEW_STATE_SETS = new int[1 << (VIEW_STATE_IDS.length / 2)][];
        for (int i5 = 0; i5 < VIEW_STATE_SETS.length; i5++) {
            int[] iArr4 = new int[Integer.bitCount(i5)];
            int i6 = 0;
            for (int i7 = 0; i7 < length; i7 += 2) {
                if ((iArr2[i7 + 1] & i5) != 0) {
                    iArr4[i6] = iArr2[i7];
                    i6++;
                }
            }
            VIEW_STATE_SETS[i5] = iArr4;
        }
        WILD_CARD = new int[0];
        NOTHING = new int[]{0};
    }

    public static int[] get(int i) {
        int[][] iArr = VIEW_STATE_SETS;
        if (i >= iArr.length) {
            throw new IllegalArgumentException("Invalid state set mask");
        }
        return iArr[i];
    }

    public static boolean isWildCard(int[] iArr) {
        return iArr.length == 0 || iArr[0] == 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0033, code lost:
    
        r5 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean stateSetMatches(int[] iArr, int[] iArr2) {
        boolean z;
        boolean z2;
        if (iArr2 == null) {
            return iArr == null || isWildCard(iArr);
        }
        int length = iArr2.length;
        for (int i : iArr) {
            if (i == 0) {
                return true;
            }
            if (i > 0) {
                z = true;
            } else {
                i = -i;
                z = false;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                int i3 = iArr2[i2];
                if (i3 == 0) {
                    if (z) {
                        return false;
                    }
                } else if (i3 != i) {
                    i2++;
                } else {
                    if (!z) {
                        return false;
                    }
                    z2 = true;
                }
            }
            if (z && !z2) {
                return false;
            }
        }
        return true;
    }

    public static boolean stateSetMatches(int[] iArr, int i) {
        int i2;
        int length = iArr.length;
        for (int i3 = 0; i3 < length && (i2 = iArr[i3]) != 0; i3++) {
            if (i2 > 0) {
                if (i != i2) {
                    return false;
                }
            } else if (i == (-i2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsAttribute(int[][] iArr, int i) {
        if (iArr != null) {
            for (int[] iArr2 : iArr) {
                if (iArr2 == null) {
                    break;
                }
                for (int i2 : iArr2) {
                    if (i2 == i || (-i2) == i) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int[] trimStateSet(int[] iArr, int i) {
        if (iArr.length == i) {
            return iArr;
        }
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    public static String dump(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i : iArr) {
            switch (i) {
                case 16842908:
                    sb.append("F ");
                    break;
                case 16842909:
                    sb.append("W ");
                    break;
                case 16842910:
                    sb.append("E ");
                    break;
                case 16842912:
                    sb.append("C ");
                    break;
                case 16842913:
                    sb.append("S ");
                    break;
                case 16842919:
                    sb.append("P ");
                    break;
                case 16843518:
                    sb.append("A ");
                    break;
                case 16843623:
                    sb.append("H ");
                    break;
            }
        }
        return sb.toString();
    }
}
