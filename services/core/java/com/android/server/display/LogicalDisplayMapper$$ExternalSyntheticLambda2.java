package com.android.server.display;

public final /* synthetic */ class LogicalDisplayMapper$$ExternalSyntheticLambda2 {
    public final /* synthetic */ int $r8$classId;

    public final int getId(boolean z) {
        switch (this.$r8$classId) {
            case 0:
                if (z) {
                    return 0;
                }
                int i = LogicalDisplayMapper.sNextNonDefaultDisplayId;
                LogicalDisplayMapper.sNextNonDefaultDisplayId = i + 1;
                return i;
            default:
                if (z) {
                    return 0;
                }
                int i2 = LogicalDisplayMapper.sNextNonDefaultDisplayId;
                LogicalDisplayMapper.sNextNonDefaultDisplayId = i2 + 1;
                return i2;
        }
    }
}
