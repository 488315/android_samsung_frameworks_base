package com.android.systemui.qs;

import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileLayout$$ExternalSyntheticLambda0 implements IntConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TileLayout f$0;

    public /* synthetic */ TileLayout$$ExternalSyntheticLambda0(TileLayout tileLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = tileLayout;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        switch (this.$r8$classId) {
            case 4:
                this.f$0.mCellWidth = i;
                break;
            case 5:
                this.f$0.mMaxAllowedRows = i;
                break;
            case 6:
                this.f$0.mMaxCellHeight = i;
                break;
            default:
                this.f$0.mColumns = i;
                break;
        }
    }
}
