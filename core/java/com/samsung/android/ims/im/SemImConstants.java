package com.samsung.android.ims.im;

public interface SemImConstants {

    public interface ChatState {
        public static final int ACTIVE = 1;
        public static final int CLOSED_BY_USER = 2;
        public static final int CLOSED_INVOLUNTARILY = 3;
        public static final int CLOSED_IN_UNAVAILABLE = 5;
        public static final int CLOSED_VOLUNTARILY = 4;
        public static final int INACTIVE = 0;
        public static final int NONE = -1;
    }
}
