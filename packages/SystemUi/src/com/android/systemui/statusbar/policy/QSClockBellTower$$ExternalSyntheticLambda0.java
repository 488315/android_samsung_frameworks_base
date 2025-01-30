package com.android.systemui.statusbar.policy;

import com.android.systemui.QpRune;
import com.android.systemui.statusbar.policy.QSClockBellTower;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSClockBellTower$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSClockBellTower$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((QSClockBellTower) this.f$0).ringBellOfTower();
                break;
            case 1:
                ((QSClockBellTower) this.f$0).ringBellOfTower();
                break;
            case 2:
                ((QSClockBellTower) this.f$0).ringBellOfTower(false);
                break;
            default:
                QSClockBellTower.TimeBroadcastReceiver timeBroadcastReceiver = (QSClockBellTower.TimeBroadcastReceiver) this.f$0;
                Locale locale = timeBroadcastReceiver.this$0.mContext.getResources().getConfiguration().getLocales().get(0);
                if (!locale.equals(timeBroadcastReceiver.this$0.mLocale)) {
                    QSClockBellTower qSClockBellTower = timeBroadcastReceiver.this$0;
                    qSClockBellTower.mLocale = locale;
                    qSClockBellTower.mClockFormatString = "";
                    qSClockBellTower.mDateStringFormat = null;
                    qSClockBellTower.mShortenDateStringFormat = null;
                    qSClockBellTower.mQuickStarDateStringFormat = null;
                    if (QpRune.QUICK_STYLE_ALTERNATE_CALENDAR) {
                        qSClockBellTower.mAlternateCalendarUtil.updateAlternateCalendar("android.intent.action.LOCALE_CHANGED");
                        break;
                    }
                }
                break;
        }
    }
}
