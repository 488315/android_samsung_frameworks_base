package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.QSClockBellTower;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class QSClockBellTower$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSClockBellTower$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((QSClockBellTower) obj).ringBellOfTower();
                break;
            case 1:
                ((QSClockBellTower) obj).ringBellOfTower(false);
                break;
            default:
                QSClockBellTower.TimeBroadcastReceiver timeBroadcastReceiver = (QSClockBellTower.TimeBroadcastReceiver) obj;
                Locale locale = QSClockBellTower.this.mContext.getResources().getConfiguration().getLocales().get(0);
                if (!locale.equals(QSClockBellTower.this.mLocale)) {
                    QSClockBellTower qSClockBellTower = QSClockBellTower.this;
                    qSClockBellTower.mLocale = locale;
                    qSClockBellTower.mClockFormatString = "";
                    qSClockBellTower.mDateStringFormat = null;
                    qSClockBellTower.mShortenDateStringFormat = null;
                    qSClockBellTower.mQSClockQuickStarHelper.mQuickStarDateStringFormat = null;
                    break;
                }
                break;
        }
    }
}
