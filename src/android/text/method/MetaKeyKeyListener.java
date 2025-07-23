package android.text.method;

import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;

/* loaded from: classes4.dex */
public abstract class MetaKeyKeyListener {
    private static final int LOCKED = 67108881;
    private static final int LOCKED_RETURN_VALUE = 2;
    public static final int META_ALT_LOCKED = 512;
    private static final long META_ALT_MASK = 565157566611970L;
    public static final int META_ALT_ON = 2;
    private static final long META_ALT_PRESSED = 2199023255552L;
    private static final long META_ALT_RELEASED = 562949953421312L;
    private static final long META_ALT_USED = 8589934592L;
    public static final int META_CAP_LOCKED = 256;
    private static final long META_CAP_PRESSED = 1099511627776L;
    private static final long META_CAP_RELEASED = 281474976710656L;
    private static final long META_CAP_USED = 4294967296L;
    public static final int META_SELECTING = 2048;
    private static final long META_SHIFT_MASK = 282578783305985L;
    public static final int META_SHIFT_ON = 1;
    public static final int META_SYM_LOCKED = 1024;
    private static final long META_SYM_MASK = 1130315133223940L;
    public static final int META_SYM_ON = 4;
    private static final long META_SYM_PRESSED = 4398046511104L;
    private static final long META_SYM_RELEASED = 1125899906842624L;
    private static final long META_SYM_USED = 17179869184L;
    private static final int PRESSED = 16777233;
    private static final int PRESSED_RETURN_VALUE = 1;
    private static final int RELEASED = 33554449;
    private static final int USED = 50331665;
    private static final Object CAP = new NoCopySpan.Concrete();
    private static final Object ALT = new NoCopySpan.Concrete();
    private static final Object SYM = new NoCopySpan.Concrete();
    private static final Object SELECTING = new NoCopySpan.Concrete();

    public static long adjustMetaAfterKeypress(long j) {
        if ((1099511627776L & j) != 0) {
            j = (j & (-282578783305986L)) | 4294967297L;
        } else if ((281474976710656L & j) != 0) {
            j &= -282578783305986L;
        }
        if ((2199023255552L & j) != 0) {
            j = (j & (-565157566611971L)) | 8589934594L;
        } else if ((562949953421312L & j) != 0) {
            j &= -565157566611971L;
        }
        return (4398046511104L & j) != 0 ? (j & (-1130315133223941L)) | 17179869188L : (1125899906842624L & j) != 0 ? j & (-1130315133223941L) : j;
    }

    public static final int getMetaState(long j) {
        int i = (256 & j) != 0 ? 256 : (1 & j) != 0 ? 1 : 0;
        if ((512 & j) != 0) {
            i |= 512;
        } else if ((2 & j) != 0) {
            i |= 2;
        }
        return (1024 & j) != 0 ? i | 1024 : (j & 4) != 0 ? i | 4 : i;
    }

    public static final int getMetaState(long j, int i) {
        if (i == 1) {
            if ((256 & j) != 0) {
                return 2;
            }
            return (j & 1) != 0 ? 1 : 0;
        }
        if (i == 2) {
            if ((512 & j) != 0) {
                return 2;
            }
            return (j & 2) != 0 ? 1 : 0;
        }
        if (i != 4) {
            return 0;
        }
        if ((1024 & j) != 0) {
            return 2;
        }
        return (j & 4) != 0 ? 1 : 0;
    }

    private static long press(long j, int i, long j2, long j3, long j4, long j5, long j6) {
        if ((j & j4) == 0) {
            if ((j & j5) != 0) {
                return (j & (~j2)) | i | j3;
            }
            if ((j & j6) == 0) {
                return (j3 & j) != 0 ? j & (~j2) : j | i | j4;
            }
        }
        return j;
    }

    public static long resetLockedMeta(long j) {
        if ((256 & j) != 0) {
            j &= -282578783305986L;
        }
        if ((512 & j) != 0) {
            j &= -565157566611971L;
        }
        return (1024 & j) != 0 ? j & (-1130315133223941L) : j;
    }

    public long clearMetaKeyState(long j, int i) {
        if ((i & 1) != 0 && (256 & j) != 0) {
            j &= -282578783305986L;
        }
        if ((i & 2) != 0 && (512 & j) != 0) {
            j &= -565157566611971L;
        }
        return ((i & 4) == 0 || (1024 & j) == 0) ? j : j & (-1130315133223941L);
    }

    public static void resetMetaState(Spannable spannable) {
        spannable.removeSpan(CAP);
        spannable.removeSpan(ALT);
        spannable.removeSpan(SYM);
        spannable.removeSpan(SELECTING);
    }

    public static final int getMetaState(CharSequence charSequence) {
        return getActive(charSequence, SELECTING, 2048, 2048) | getActive(charSequence, CAP, 1, 256) | getActive(charSequence, ALT, 2, 512) | getActive(charSequence, SYM, 4, 1024);
    }

    public static final int getMetaState(CharSequence charSequence, KeyEvent keyEvent) {
        int metaState = keyEvent.getMetaState();
        return keyEvent.getKeyCharacterMap().getModifierBehavior() == 1 ? getMetaState(charSequence) | metaState : metaState;
    }

    public static final int getMetaState(CharSequence charSequence, int i) {
        if (i == 1) {
            return getActive(charSequence, CAP, 1, 2);
        }
        if (i == 2) {
            return getActive(charSequence, ALT, 1, 2);
        }
        if (i == 4) {
            return getActive(charSequence, SYM, 1, 2);
        }
        if (i != 2048) {
            return 0;
        }
        return getActive(charSequence, SELECTING, 1, 2);
    }

    public static final int getMetaState(CharSequence charSequence, int i, KeyEvent keyEvent) {
        int metaState = keyEvent.getMetaState();
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() == 1) {
            metaState |= getMetaState(charSequence);
        }
        if (2048 == i) {
            return (2048 & metaState) != 0 ? 1 : 0;
        }
        return getMetaState(metaState, i);
    }

    private static int getActive(CharSequence charSequence, Object obj, int i, int i2) {
        if (!(charSequence instanceof Spanned)) {
            return 0;
        }
        int spanFlags = ((Spanned) charSequence).getSpanFlags(obj);
        if (spanFlags == LOCKED) {
            return i2;
        }
        if (spanFlags != 0) {
            return i;
        }
        return 0;
    }

    public static void adjustMetaAfterKeypress(Spannable spannable) {
        adjust(spannable, CAP);
        adjust(spannable, ALT);
        adjust(spannable, SYM);
    }

    public static boolean isMetaTracker(CharSequence charSequence, Object obj) {
        return obj == CAP || obj == ALT || obj == SYM || obj == SELECTING;
    }

    public static boolean isSelectingMetaTracker(CharSequence charSequence, Object obj) {
        return obj == SELECTING;
    }

    private static void adjust(Spannable spannable, Object obj) {
        int spanFlags = spannable.getSpanFlags(obj);
        if (spanFlags == PRESSED) {
            spannable.setSpan(obj, 0, 0, USED);
        } else if (spanFlags == RELEASED) {
            spannable.removeSpan(obj);
        }
    }

    protected static void resetLockedMeta(Spannable spannable) {
        resetLock(spannable, CAP);
        resetLock(spannable, ALT);
        resetLock(spannable, SYM);
        resetLock(spannable, SELECTING);
    }

    private static void resetLock(Spannable spannable, Object obj) {
        if (spannable.getSpanFlags(obj) == LOCKED) {
            spannable.removeSpan(obj);
        }
    }

    public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
        if (i == 59 || i == 60) {
            press(editable, CAP);
            return true;
        }
        if (i == 57 || i == 58 || i == 78) {
            press(editable, ALT);
            return true;
        }
        if (i != 63) {
            return false;
        }
        press(editable, SYM);
        return true;
    }

    private void press(Editable editable, Object obj) {
        int spanFlags = editable.getSpanFlags(obj);
        if (spanFlags == PRESSED) {
            return;
        }
        if (spanFlags == RELEASED) {
            editable.setSpan(obj, 0, 0, LOCKED);
        } else {
            if (spanFlags == USED) {
                return;
            }
            if (spanFlags == LOCKED) {
                editable.removeSpan(obj);
            } else {
                editable.setSpan(obj, 0, 0, PRESSED);
            }
        }
    }

    public static void startSelecting(View view, Spannable spannable) {
        spannable.setSpan(SELECTING, 0, 0, PRESSED);
    }

    public static void stopSelecting(View view, Spannable spannable) {
        spannable.removeSpan(SELECTING);
    }

    public boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
        if (i == 59 || i == 60) {
            release(editable, CAP, keyEvent);
            return true;
        }
        if (i == 57 || i == 58 || i == 78) {
            release(editable, ALT, keyEvent);
            return true;
        }
        if (i != 63) {
            return false;
        }
        release(editable, SYM, keyEvent);
        return true;
    }

    private void release(Editable editable, Object obj, KeyEvent keyEvent) {
        int spanFlags = editable.getSpanFlags(obj);
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() != 1) {
            editable.removeSpan(obj);
        } else if (spanFlags == USED) {
            editable.removeSpan(obj);
        } else if (spanFlags == PRESSED) {
            editable.setSpan(obj, 0, 0, RELEASED);
        }
    }

    public void clearMetaKeyState(View view, Editable editable, int i) {
        clearMetaKeyState(editable, i);
    }

    public static void clearMetaKeyState(Editable editable, int i) {
        if ((i & 1) != 0) {
            editable.removeSpan(CAP);
        }
        if ((i & 2) != 0) {
            editable.removeSpan(ALT);
        }
        if ((i & 4) != 0) {
            editable.removeSpan(SYM);
        }
        if ((i & 2048) != 0) {
            editable.removeSpan(SELECTING);
        }
    }

    public static long handleKeyDown(long j, int i, KeyEvent keyEvent) {
        if (i == 59 || i == 60) {
            return press(j, 1, META_SHIFT_MASK, 256L, 1099511627776L, 281474976710656L, 4294967296L);
        }
        if (i == 57 || i == 58 || i == 78) {
            return press(j, 2, META_ALT_MASK, 512L, 2199023255552L, 562949953421312L, 8589934592L);
        }
        return i == 63 ? press(j, 4, META_SYM_MASK, 1024L, 4398046511104L, 1125899906842624L, 17179869184L) : j;
    }

    public static long handleKeyUp(long j, int i, KeyEvent keyEvent) {
        if (i == 59 || i == 60) {
            return release(j, 1, META_SHIFT_MASK, 1099511627776L, 281474976710656L, 4294967296L, keyEvent);
        }
        if (i == 57 || i == 58 || i == 78) {
            return release(j, 2, META_ALT_MASK, 2199023255552L, 562949953421312L, 8589934592L, keyEvent);
        }
        return i == 63 ? release(j, 4, META_SYM_MASK, 4398046511104L, 1125899906842624L, 17179869184L, keyEvent) : j;
    }

    private static long release(long j, int i, long j2, long j3, long j4, long j5, KeyEvent keyEvent) {
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() == 1 && (j5 & j) == 0) {
            return (j & j3) != 0 ? j | i | j4 : j;
        }
        return j & (~j2);
    }
}
