package com.android.internal.widget.remotecompose.core.operations.utilities;

import android.media.audio.common.AudioChannelLayout;
import com.android.internal.widget.remotecompose.core.operations.Utils;

/* loaded from: classes6.dex */
public class NanMap {
    public static final int ID_REGION_ARRAY = 2097152;
    public static final int ID_REGION_MASK = 7340032;
    public static final int MOVE = 3145728;
    public static final int START_ARRAY = 2097194;
    public static final int START_VAR = 1048618;
    public static final int TYPE_ARRAY = 2;
    public static final int TYPE_OPERATION = 3;
    public static final int TYPE_SYSTEM = 0;
    public static final int TYPE_VARIABLE = 1;
    public static final float MOVE_NAN = Utils.asNan(3145728);
    public static final int LINE = 3145729;
    public static final float LINE_NAN = Utils.asNan(LINE);
    public static final int QUADRATIC = 3145730;
    public static final float QUADRATIC_NAN = Utils.asNan(QUADRATIC);
    public static final int CONIC = 3145731;
    public static final float CONIC_NAN = Utils.asNan(CONIC);
    public static final int CUBIC = 3145732;
    public static final float CUBIC_NAN = Utils.asNan(CUBIC);
    public static final int CLOSE = 3145733;
    public static final float CLOSE_NAN = Utils.asNan(CLOSE);
    public static final int DONE = 3145734;
    public static final float DONE_NAN = Utils.asNan(DONE);

    public static boolean isSystemVariable(float f) {
        return (fromNaN(f) >> 20) == 0;
    }

    public static boolean isNormalVariable(float f) {
        return (fromNaN(f) >> 20) == 1;
    }

    public static boolean isDataVariable(float f) {
        return (fromNaN(f) >> 20) == 2;
    }

    public static boolean isOperationVariable(float f) {
        return (fromNaN(f) >> 20) == 3;
    }

    public static int fromNaN(float f) {
        return Float.floatToRawIntBits(f) & AudioChannelLayout.INDEX_MASK_23;
    }

    public static float asNan(int i) {
        return Float.intBitsToFloat(i | (-8388608));
    }
}
