package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.QSClockPanelView;
import com.android.systemui.statusbar.policy.QSDate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSDateClock extends LinearLayout {
    public QSClockPanelView mClock;
    public final Context mContext;
    public QSDate mDate;
    public int mOldOrientation;

    public QSDateClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mOldOrientation != configuration.orientation) {
            updateVisibility();
            this.mOldOrientation = configuration.orientation;
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mClock = (QSClockPanelView) findViewById(R.id.panel_clock);
        this.mDate = (QSDate) findViewById(R.id.panel_date);
        updateVisibility();
        int color = this.mContext.getColor(R.color.sec_qs_header_tint_color);
        this.mClock.setTextColor(color);
        this.mDate.setTextColor(color);
    }

    public final void updateVisibility() {
        boolean z = getResources().getConfiguration().orientation == 2;
        if (QpRune.QUICK_TABLET || !z) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
    }
}
