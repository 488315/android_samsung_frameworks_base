package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class QSClockIndicatorView extends QSClock implements DarkIconDispatcher.DarkReceiver {
    public final int ICON_DARK_COLOR_TINT;
    public final int ICON_LIGHT_COLOR_TINT;
    public String callers;
    public boolean clockVisibleByUser;
    public int recentColor;
    private SettingsHelper settingsHelper;
    public SlimIndicatorViewMediator slimIndicatorViewMediator;

    public QSClockIndicatorView(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // com.android.systemui.statusbar.policy.QSClock
    public final boolean calculateVisibility() {
        return this.mClockVisibleByPolicy && this.clockVisibleByUser;
    }

    public final String getDateText(QSClockBellSound qSClockBellSound) {
        return isTurnOnShowingDateByQuickStar() ? qSClockBellSound.QuickStarDateText : !Intrinsics.areEqual(getContext().getResources().getConfiguration().getLocales().get(0).getLanguage(), "ko") || isTurnOnShowingDateByQuickStar() ? qSClockBellSound.ShortDateText : qSClockBellSound.DateText;
    }

    public final boolean isTurnOnShowingDateByQuickStar() {
        SlimIndicatorViewMediator slimIndicatorViewMediator = this.slimIndicatorViewMediator;
        return slimIndicatorViewMediator != null && ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).showDateClock();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00c7  */
    @Override // com.android.systemui.statusbar.policy.QSClock, com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyTimeChanged(com.android.systemui.statusbar.policy.QSClockBellSound r8) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.QSClockIndicatorView.notifyTimeChanged(com.android.systemui.statusbar.policy.QSClockBellSound):void");
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        setTextColor(tint);
        if ((tint == this.ICON_LIGHT_COLOR_TINT || tint == this.ICON_DARK_COLOR_TINT) && this.recentColor != tint) {
            Log.d("QSClock", "onDarkChanged tint=".concat(tint == this.ICON_DARK_COLOR_TINT ? "DARK" : "WHITE"));
            this.recentColor = tint;
        }
    }

    public final void setDependencies(SlimIndicatorViewMediator slimIndicatorViewMediator, SettingsHelper settingsHelper) {
        this.slimIndicatorViewMediator = slimIndicatorViewMediator;
        this.settingsHelper = settingsHelper;
    }

    @Override // com.android.systemui.statusbar.policy.QSClock, android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        this.callers = Debug.getCallers(5);
    }

    public QSClockIndicatorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ QSClockIndicatorView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public QSClockIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ICON_LIGHT_COLOR_TINT = -1;
        this.ICON_DARK_COLOR_TINT = -1728053248;
        this.recentColor = -1;
        this.callers = "";
        this.clockVisibleByUser = true;
    }
}
