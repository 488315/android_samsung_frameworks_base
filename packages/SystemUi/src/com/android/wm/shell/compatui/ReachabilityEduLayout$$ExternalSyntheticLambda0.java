package com.android.wm.shell.compatui;

import android.widget.FrameLayout;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ReachabilityEduLayout$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = ReachabilityEduLayout.$r8$clinit;
                return Integer.valueOf(((FrameLayout.LayoutParams) obj).topMargin);
            case 1:
                int i2 = ReachabilityEduLayout.$r8$clinit;
                return Integer.valueOf(((FrameLayout.LayoutParams) obj).bottomMargin);
            case 2:
                int i3 = ReachabilityEduLayout.$r8$clinit;
                return Integer.valueOf(((FrameLayout.LayoutParams) obj).leftMargin);
            default:
                int i4 = ReachabilityEduLayout.$r8$clinit;
                return Integer.valueOf(((FrameLayout.LayoutParams) obj).rightMargin);
        }
    }
}
