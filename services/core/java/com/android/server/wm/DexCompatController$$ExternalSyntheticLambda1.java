package com.android.server.wm;

import java.util.function.IntSupplier;

public final /* synthetic */ class DexCompatController$$ExternalSyntheticLambda1
        implements IntSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Task f$0;

    public /* synthetic */ DexCompatController$$ExternalSyntheticLambda1(int i, Task task) {
        this.$r8$classId = i;
        this.f$0 = task;
    }

    @Override // java.util.function.IntSupplier
    public final int getAsInt() {
        int i = this.$r8$classId;
        Task task = this.f$0;
        switch (i) {
            case 0:
                return task.mDexCompatUiMode != 3 ? 1 : 5;
            default:
                return task.getWindowingMode();
        }
    }
}
