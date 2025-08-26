package com.samsung.android.game;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.function.IntPredicate;

/* loaded from: classes6.dex */
public class SemGameNotification {
    public static final int GAME_FOCUSED_IN = 2;
    public static final int GAME_FOCUSED_OUT = 3;
    public static final int GAME_PACKAGE_ADDED = 0;
    public static final int GAME_PACKAGE_REMOVED = 1;
    public static final int UNDEFINED = -1;
    private static final int[] VALID_GAME_EVENTS = {0, 1, 2, 3};

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GameEvent {
    }

    public static class NotificationDataKey {
        public static final String GAME_EVENT = "game_event";
        public static final String PACKAGE_NAME = "package_name";
        public static final String USER_ID = "user_id";
    }

    public static boolean isValidGameEvent(final Integer num) {
        return Arrays.stream(VALID_GAME_EVENTS).anyMatch(new IntPredicate() { // from class: com.samsung.android.game.SemGameNotification$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return SemGameNotification.lambda$isValidGameEvent$0(num, i);
            }
        });
    }

    static /* synthetic */ boolean lambda$isValidGameEvent$0(Integer num, int i) {
        return i == num.intValue();
    }
}
