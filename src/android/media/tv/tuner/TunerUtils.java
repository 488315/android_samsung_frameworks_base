package android.media.tv.tuner;

/* loaded from: classes3.dex */
public final class TunerUtils {
    public static int getFilterSubtype(int i, int i2) {
        if (i == 1) {
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 4;
                case 4:
                    return 5;
                case 6:
                    return 7;
                case 7:
                    return 3;
                case 8:
                    return 6;
                case 9:
                    return 8;
            }
        }
        if (i == 2) {
            if (i2 == 10) {
                return 3;
            }
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 4;
                case 4:
                    return 5;
                case 5:
                    return 7;
                case 6:
                    return 6;
            }
        }
        if (i == 4) {
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 1) {
                return 1;
            }
            switch (i2) {
                case 11:
                    return 2;
                case 12:
                    return 3;
                case 13:
                    return 4;
                case 14:
                    return 5;
            }
        }
        if (i == 8) {
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 1) {
                return 1;
            }
            if (i2 == 14) {
                return 3;
            }
            if (i2 == 15) {
                return 2;
            }
        } else if (i == 16) {
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 1) {
                return 1;
            }
            if (i2 == 14) {
                return 3;
            }
            if (i2 == 16) {
                return 2;
            }
        }
        throw new IllegalArgumentException("Invalid filter types. Main type=" + i + ", subtype=" + i2);
    }

    public static void throwExceptionForResult(int i, String str) {
        if (str == null) {
            str = "";
        }
        switch (i) {
            case 0:
                return;
            case 1:
                throw new IllegalStateException("Invalid state: resource unavailable. " + str);
            case 2:
                throw new IllegalStateException("Invalid state: not initialized. " + str);
            case 3:
                throw new IllegalStateException(str);
            case 4:
                throw new IllegalArgumentException(str);
            case 5:
                throw new OutOfMemoryError(str);
            case 6:
                throw new RuntimeException("Unknown error" + str);
            default:
                throw new RuntimeException("Unexpected result " + i + ".  " + str);
        }
    }

    public static void checkResourceState(String str, boolean z) {
        if (z) {
            throw new IllegalStateException(str + " has been closed");
        }
    }

    public static void checkResourceAccessible(String str, boolean z) {
        if (z) {
            return;
        }
        throw new IllegalStateException(str + " is inaccessible");
    }

    private TunerUtils() {
    }
}
