package com.android.server.display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
