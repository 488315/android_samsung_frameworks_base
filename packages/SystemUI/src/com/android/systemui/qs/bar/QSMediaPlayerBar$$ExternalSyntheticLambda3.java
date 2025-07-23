package com.android.systemui.qs.bar;

import com.android.systemui.plugins.qs.DetailAdapter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSMediaPlayerBar$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSMediaPlayerBar f$0;

    public /* synthetic */ QSMediaPlayerBar$$ExternalSyntheticLambda3(QSMediaPlayerBar qSMediaPlayerBar, int i) {
        this.$r8$classId = i;
        this.f$0 = qSMediaPlayerBar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        QSMediaPlayerBar qSMediaPlayerBar = this.f$0;
        switch (i) {
            case 0:
                qSMediaPlayerBar.mSecQSDetailController.showTargetDetail((DetailAdapter) qSMediaPlayerBar.mSoundCraftQpDetailAdapterLazy.get());
                break;
            default:
                qSMediaPlayerBar.mSecQSDetailController.closeTargetDetail((DetailAdapter) qSMediaPlayerBar.mSoundCraftQpDetailAdapterLazy.get());
                break;
        }
    }
}
