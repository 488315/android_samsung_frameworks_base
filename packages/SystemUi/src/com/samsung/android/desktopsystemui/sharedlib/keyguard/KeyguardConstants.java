package com.samsung.android.desktopsystemui.sharedlib.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardConstants {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class SecurityMode {
        public static final int INVALID = 0;
        public static final int NONE = 1;
        public static final int OTHERS = 5;
        public static final int PASSWORD = 3;
        public static final int PATTERN = 2;
        public static final int PIN = 4;

        private SecurityMode() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class UpdateType {
        public static final int BOUNCER_TEXT = 2;
        public static final int SCREEN_STATE = 3;
        public static final int TRUST_STATE = 4;
        public static final int UNLOCK_FAIL = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class BouncerTextKey {
            public static final String MSG = "msg";
            public static final String POPUP_MSG = "popupMsg";
            public static final String SUB_MSG = "subMsg";

            private BouncerTextKey() {
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class ScreenStateKey {
            public static final String IS_SCREEN_ON = "isScreenOn";

            private ScreenStateKey() {
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class TrustStateKey {
            public static final String TRUST = "trust";

            private TrustStateKey() {
            }
        }

        private UpdateType() {
        }
    }

    private KeyguardConstants() {
    }
}
