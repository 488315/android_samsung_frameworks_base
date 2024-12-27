package com.android.server.wm;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

public abstract class InputConfigAdapter {
    public static final FlagMapping[] INPUT_FEATURE_TO_CONFIG_MAP;
    public static final int INPUT_FEATURE_TO_CONFIG_MASK;
    public static final FlagMapping[] LAYOUT_PARAM_FLAG_TO_CONFIG_MAP;
    public static final int LAYOUT_PARAM_FLAG_TO_CONFIG_MASK;

    public final class FlagMapping {
        public final int mFlag;
        public final int mInputConfig;
        public final boolean mInverted;

        public FlagMapping(int i, int i2, boolean z) {
            this.mFlag = i;
            this.mInputConfig = i2;
            this.mInverted = z;
        }
    }

    static {
        FlagMapping[] flagMappingArr = {
            new FlagMapping(1, 1, false),
            new FlagMapping(2, 2048, false),
            new FlagMapping(
                    4, EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION, false),
            new FlagMapping(8, 262144, false)
        };
        INPUT_FEATURE_TO_CONFIG_MAP = flagMappingArr;
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i |= flagMappingArr[i2].mInputConfig;
        }
        INPUT_FEATURE_TO_CONFIG_MASK = i;
        FlagMapping[] flagMappingArr2 = {
            new FlagMapping(16, 8, false),
            new FlagMapping(8388608, 16, true),
            new FlagMapping(262144, 512, false),
            new FlagMapping(536870912, 1024, false)
        };
        LAYOUT_PARAM_FLAG_TO_CONFIG_MAP = flagMappingArr2;
        int i3 = 0;
        for (int i4 = 0; i4 < 4; i4++) {
            i3 |= flagMappingArr2[i4].mInputConfig;
        }
        LAYOUT_PARAM_FLAG_TO_CONFIG_MASK = i3;
    }

    public static int applyMapping(int i, FlagMapping[] flagMappingArr) {
        int i2 = 0;
        for (FlagMapping flagMapping : flagMappingArr) {
            if (((flagMapping.mFlag & i) != 0) != flagMapping.mInverted) {
                i2 |= flagMapping.mInputConfig;
            }
        }
        return i2;
    }

    public static int getInputConfigFromWindowParams(int i, int i2, int i3) {
        return (i == 2013 ? 64 : 0)
                | applyMapping(i2, LAYOUT_PARAM_FLAG_TO_CONFIG_MAP)
                | applyMapping(i3, INPUT_FEATURE_TO_CONFIG_MAP);
    }
}
