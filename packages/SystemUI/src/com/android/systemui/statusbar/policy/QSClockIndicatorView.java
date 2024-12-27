package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Debug;
import android.util.AttributeSet;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class QSClockIndicatorView extends QSClock implements DarkIconDispatcher.DarkReceiver {
    public String callers;
    public boolean clockVisibleByUser;
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
        if (slimIndicatorViewMediator != null) {
            Intrinsics.checkNotNull(slimIndicatorViewMediator);
            if (((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).showDateClock()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x003a  */
    @Override // com.android.systemui.statusbar.policy.QSClock, com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyTimeChanged(com.android.systemui.statusbar.policy.QSClockBellSound r8) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.QSClockIndicatorView.notifyTimeChanged(com.android.systemui.statusbar.policy.QSClockBellSound):void");
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (Typeface.semIsDefaultFontStyle()) {
            setTypeface(Typeface.create(Typeface.create("sec", 0), KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, false));
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        setTextColor(DarkIconDispatcher.getTint(arrayList, this, i));
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
        this.callers = "";
        this.clockVisibleByUser = true;
    }
}
