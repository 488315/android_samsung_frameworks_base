package com.android.internal.widget.remotecompose.core.operations.utilities;

import com.android.internal.telephony.gsm.SmsCbConstants;
import com.android.internal.widget.remotecompose.core.operations.Utils;

public class NanMap {
    public static final int CLOSE = 4101;
    public static final int CONIC = 4099;
    public static final int CUBIC = 4100;
    public static final int DONE = 4102;
    public static final int LINE = 4097;
    public static final int MOVE = 4096;
    public static final int QUADRATIC = 4098;
    public static final float MOVE_NAN = Utils.asNan(4096);
    public static final float LINE_NAN = Utils.asNan(4097);
    public static final float QUADRATIC_NAN = Utils.asNan(4098);
    public static final float CONIC_NAN = Utils.asNan(4099);
    public static final float CUBIC_NAN = Utils.asNan(4100);
    public static final float CLOSE_NAN = Utils.asNan(4101);
    public static final float DONE_NAN = Utils.asNan(4102);
    public static final float ADD = asNan(4352);
    public static final float SUB = asNan(4353);
    public static final float MUL = asNan(4354);
    public static final float DIV = asNan(SmsCbConstants.MESSAGE_ID_ETWS_TEST_MESSAGE);
    public static final float MOD = asNan(SmsCbConstants.MESSAGE_ID_ETWS_OTHER_EMERGENCY_TYPE);
    public static final float MIN = asNan(4357);
    public static final float MAX = asNan(4358);
    public static final float POW = asNan(4359);

    public static int fromNaN(float v) {
        int b = Float.floatToRawIntBits(v);
        return 1048575 & b;
    }

    public static float asNan(int v) {
        return Float.intBitsToFloat((-8388608) | v);
    }
}
