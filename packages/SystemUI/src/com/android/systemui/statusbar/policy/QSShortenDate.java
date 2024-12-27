package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class QSShortenDate extends QSDate {
    public final Context mContext;

    public QSShortenDate(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override // com.android.systemui.statusbar.policy.QSDate, com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public final void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        String str = (locale == null || !locale.getLanguage().equals("ko")) ? qSClockBellSound.ShortDateText : qSClockBellSound.DateText;
        if (str.equals(this.mLastText)) {
            return;
        }
        setText(str);
        this.mLastText = str;
    }
}
