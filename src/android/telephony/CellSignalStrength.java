package android.telephony;

import android.annotation.SystemApi;
import android.os.PersistableBundle;

/* loaded from: classes4.dex */
public abstract class CellSignalStrength {
    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;
    protected static final int NUM_SIGNAL_STRENGTH_THRESHOLDS = 4;
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    public static final int SIGNAL_STRENGTH_POOR = 1;

    protected static final int getAsuFromRscpDbm(int i) {
        if (i == Integer.MAX_VALUE) {
            return 255;
        }
        return i + 120;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static int getNumSignalStrengthLevels() {
        return 5;
    }

    public static final int getRscpDbmFromAsu(int i) {
        if (i > 96 || i < 0) {
            return Integer.MAX_VALUE;
        }
        return i - 120;
    }

    public static final int getRssiDbmFromAsu(int i) {
        if (i > 31 || i < 0) {
            return Integer.MAX_VALUE;
        }
        return (i * 2) - 113;
    }

    protected static final int inRangeOrUnavailable(int i, int i2, int i3) {
        if (i < i2 || i > i3) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    protected static final int inRangeOrUnavailable(int i, int i2, int i3, int i4) {
        if ((i < i2 || i > i3) && i != i4) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    public abstract CellSignalStrength copy();

    public abstract boolean equals(Object obj);

    public abstract int getAsuLevel();

    public abstract int getDbm();

    public abstract int getLevel();

    public abstract int hashCode();

    public abstract boolean isValid();

    public abstract void setDefaultValues();

    public abstract void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState);

    protected CellSignalStrength() {
    }

    protected static final int getAsuFromRssiDbm(int i) {
        if (i == Integer.MAX_VALUE) {
            return 99;
        }
        return (i + 113) / 2;
    }

    public static final int getEcNoDbFromAsu(int i) {
        if (i > 49 || i < 0) {
            return Integer.MAX_VALUE;
        }
        return (i / 2) - 24;
    }
}
