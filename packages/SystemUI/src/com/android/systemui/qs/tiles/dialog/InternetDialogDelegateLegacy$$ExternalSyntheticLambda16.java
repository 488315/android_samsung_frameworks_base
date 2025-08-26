package com.android.systemui.qs.tiles.dialog;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialogDelegateLegacy$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Drawable f$1;

    public /* synthetic */ InternetDialogDelegateLegacy$$ExternalSyntheticLambda16(Object obj, Drawable drawable, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = drawable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                InternetDialogDelegateLegacy internetDialogDelegateLegacy = (InternetDialogDelegateLegacy) this.f$0;
                internetDialogDelegateLegacy.mSignalIcon.setImageDrawable(this.f$1);
                break;
            default:
                ImageView imageView = (ImageView) this.f$0;
                Drawable drawable = this.f$1;
                boolean z = InternetDialogDelegateLegacy.DEBUG;
                imageView.setImageDrawable(drawable);
                break;
        }
    }
}
