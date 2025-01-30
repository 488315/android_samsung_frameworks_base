package com.android.systemui.qs.bar;

import android.util.Log;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BarController$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((BarItemImpl) obj).destroy();
                break;
            case 1:
                ((BarItemImpl) obj).onKnoxPolicyChanged();
                break;
            case 2:
                ((BarItemImpl) obj).onUiModeChanged();
                break;
            case 3:
                ((BarItemImpl) obj).updateHeightMargins();
                break;
            case 4:
                ((BarItemImpl) obj).setUnderneathQqs(false);
                break;
            case 5:
                ((BarItemImpl) obj).setUnderneathQqs(true);
                break;
            case 6:
                Log.d("BarController", (String) obj);
                break;
            default:
                Log.d("BarController", (String) obj);
                break;
        }
    }
}
