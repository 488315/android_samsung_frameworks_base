package com.android.systemui.qs.tiles.dialog;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class InternetDialogDelegate$$ExternalSyntheticLambda27 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Drawable f$1;

    public /* synthetic */ InternetDialogDelegate$$ExternalSyntheticLambda27(Object obj, Drawable drawable, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = drawable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                InternetDialogDelegate internetDialogDelegate = (InternetDialogDelegate) this.f$0;
                internetDialogDelegate.mSignalIcon.setImageDrawable(this.f$1);
                break;
            default:
                ((ImageView) this.f$0).setImageDrawable(this.f$1);
                break;
        }
    }
}
