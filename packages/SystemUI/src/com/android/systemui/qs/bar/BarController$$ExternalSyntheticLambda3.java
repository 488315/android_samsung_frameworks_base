package com.android.systemui.qs.bar;

import android.util.Log;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BarController$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((BarItemImpl) obj).getClass();
                break;
            case 1:
                Log.d("BarController", (String) obj);
                break;
            case 2:
                ((BarItemImpl) obj).setUnderneathQqs(false);
                break;
            case 3:
                ((BarItemImpl) obj).setUnderneathQqs(true);
                break;
            case 4:
                Log.d("BarController", (String) obj);
                break;
            case 5:
                ((BarItemImpl) obj).destroy();
                break;
            case 6:
                ((BarItemImpl) obj).onKnoxPolicyChanged();
                break;
            case 7:
                ((BarItemImpl) obj).onUiModeChanged();
                break;
            default:
                ((BarItemImpl) obj).updateHeightMargins();
                break;
        }
    }
}
