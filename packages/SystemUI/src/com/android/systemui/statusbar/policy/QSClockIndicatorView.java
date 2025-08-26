package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewParent;
import android.view.WindowInsets;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class QSClockIndicatorView extends QSClock implements DarkIconDispatcher.DarkReceiver {
    public final int ICON_DARK_COLOR_TINT;
    public final int ICON_LIGHT_COLOR_TINT;
    public String callers;
    public boolean clockVisibleByUser;
    public QSClockBellSound lastQSClockBellSound;
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
        if (slimIndicatorViewMediator == null) {
            return false;
        }
        slimIndicatorViewMediator.getClass();
        return ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).showDateClock();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b1 A[PHI: r0 r2
      0x00b1: PHI (r0v9 java.lang.String) = 
      (r0v3 java.lang.String)
      (r0v3 java.lang.String)
      (r0v1 java.lang.String)
      (r0v1 java.lang.String)
      (r0v1 java.lang.String)
     binds: [B:35:0x00a1, B:37:0x00ac, B:11:0x0021, B:13:0x0027, B:15:0x002d] A[DONT_GENERATE, DONT_INLINE]
      0x00b1: PHI (r2v6 java.lang.String) = 
      (r2v1 java.lang.String)
      (r2v1 java.lang.String)
      (r2v0 java.lang.String)
      (r2v0 java.lang.String)
      (r2v0 java.lang.String)
     binds: [B:35:0x00a1, B:37:0x00ac, B:11:0x0021, B:13:0x0027, B:15:0x002d] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.systemui.statusbar.policy.QSClock, com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        String strM;
        int dimensionPixelSize;
        CharSequence charSequence;
        int i;
        SettingsHelper settingsHelper;
        this.lastQSClockBellSound = qSClockBellSound;
        String strM2 = qSClockBellSound.TimeText;
        SlimIndicatorViewMediator slimIndicatorViewMediator = this.slimIndicatorViewMediator;
        String str = qSClockBellSound.TimeContentDescription;
        if (slimIndicatorViewMediator != null) {
            slimIndicatorViewMediator.getClass();
            if (((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).showAmPmClock()) {
                strM2 = str;
            }
        }
        if (isTurnOnShowingDateByQuickStar() || ((settingsHelper = this.settingsHelper) != null && settingsHelper.isShowDate() && DeviceType.isTablet())) {
            String str2 = isTurnOnShowingDateByQuickStar() ? "  " : "   ";
            if (getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
                String strM3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(getDateText(qSClockBellSound), str2);
                strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM3, strM2);
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM3, str);
            } else {
                String strM4 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, getDateText(qSClockBellSound));
                strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM2, strM4);
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, strM4);
            }
            str = strM;
            dimensionPixelSize = isTurnOnShowingDateByQuickStar() ? getResources().getDimensionPixelSize(R.dimen.quickstar_status_bar_date_clock_right_padding) : getResources().getDimensionPixelSize(R.dimen.status_bar_date_clock_right_padding);
            SlimIndicatorViewMediator slimIndicatorViewMediator2 = this.slimIndicatorViewMediator;
            if (slimIndicatorViewMediator2 != null) {
                slimIndicatorViewMediator2.getClass();
                if (((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator2).isLeftClockPosition()) {
                    charSequence = str;
                    i = dimensionPixelSize;
                    dimensionPixelSize = 0;
                } else {
                    SlimIndicatorViewMediator slimIndicatorViewMediator3 = this.slimIndicatorViewMediator;
                    if (slimIndicatorViewMediator3 != null) {
                        slimIndicatorViewMediator3.getClass();
                        if (((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator3).isRightClockPosition()) {
                            charSequence = str;
                            i = 0;
                        }
                    }
                }
            }
        } else {
            charSequence = str;
            dimensionPixelSize = 0;
            i = 0;
        }
        setText(strM2);
        setContentDescription(charSequence);
        setPaddingRelative(dimensionPixelSize, getPaddingTop(), i, 0);
        boolean z = this.mClockVisibleByPolicy;
        boolean z2 = this.clockVisibleByUser;
        boolean z3 = getVisibility() == 0;
        ViewParent parent = getParent();
        String str3 = this.callers;
        StringBuilder sb = new StringBuilder("StatusBar clock=");
        sb.append(strM2);
        sb.append(" notifyTimeChanged(");
        sb.append(qSClockBellSound);
        sb.append(") clockVisibleByPolicy=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z, ", clockVisibleByUser=", z2, ", visible=");
        sb.append(z3);
        sb.append(", parent=");
        sb.append(parent);
        sb.append(" caller=");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str3, " ", "QSClock");
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        post(new Runnable() { // from class: com.android.systemui.statusbar.policy.QSClockIndicatorView.onApplyWindowInsets.1
            @Override // java.lang.Runnable
            public final void run() {
                QSClockIndicatorView qSClockIndicatorView = QSClockIndicatorView.this;
                QSClockBellSound qSClockBellSound = qSClockIndicatorView.lastQSClockBellSound;
                if (qSClockBellSound != null) {
                    qSClockIndicatorView.notifyTimeChanged(qSClockBellSound);
                }
            }
        });
        return super.onApplyWindowInsets(windowInsets);
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
