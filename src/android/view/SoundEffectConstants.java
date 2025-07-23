package android.view;

import android.media.AudioManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

/* loaded from: classes4.dex */
public class SoundEffectConstants {
    public static final int CLICK = 0;
    public static final int NAVIGATION_DOWN = 4;
    public static final int NAVIGATION_LEFT = 1;
    public static final int NAVIGATION_REPEAT_DOWN = 8;
    public static final int NAVIGATION_REPEAT_LEFT = 5;
    public static final int NAVIGATION_REPEAT_RIGHT = 7;
    public static final int NAVIGATION_REPEAT_UP = 6;
    public static final int NAVIGATION_RIGHT = 3;
    public static final int NAVIGATION_UP = 2;
    private static final Random NAVIGATION_REPEAT_RANDOMIZER = new Random();
    private static int sLastNavigationRepeatSoundEffectId = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationSoundEffect {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SoundEffect {
    }

    public static boolean isNavigationRepeat(int i) {
        return i == 8 || i == 5 || i == 7 || i == 6;
    }

    private SoundEffectConstants() {
    }

    public static int getContantForFocusDirection(int i) {
        if (i != 1) {
            if (i == 2) {
                return 4;
            }
            if (i == 17) {
                return 1;
            }
            if (i != 33) {
                if (i == 66) {
                    return 3;
                }
                if (i == 130) {
                    return 4;
                }
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
            }
        }
        return 2;
    }

    public static int getConstantForFocusDirection(int i, boolean z) {
        if (!z) {
            return getContantForFocusDirection(i);
        }
        if (i == 1) {
            return 6;
        }
        if (i == 2) {
            return 8;
        }
        if (i == 17) {
            return 5;
        }
        if (i == 33) {
            return 6;
        }
        if (i == 66) {
            return 7;
        }
        if (i == 130) {
            return 8;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
    }

    public static int nextNavigationRepeatSoundEffectId() {
        int nextInt = NAVIGATION_REPEAT_RANDOMIZER.nextInt(3);
        if (nextInt >= sLastNavigationRepeatSoundEffectId) {
            nextInt++;
        }
        sLastNavigationRepeatSoundEffectId = nextInt;
        return AudioManager.getNthNavigationRepeatSoundEffect(nextInt);
    }
}
