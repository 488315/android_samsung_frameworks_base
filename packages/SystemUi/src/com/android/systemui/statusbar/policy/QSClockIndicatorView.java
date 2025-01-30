package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.os.Debug;
import android.util.AttributeSet;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSClockIndicatorView extends QSClock implements DarkIconDispatcher.DarkReceiver {
    public String callers;
    public boolean clockVisibleByUser;
    public SlimIndicatorViewMediator slimIndicatorViewMediator;

    public QSClockIndicatorView(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // com.android.systemui.statusbar.policy.QSClock
    public final boolean calculateVisibility() {
        return this.mClockVisibleByPolicy && this.clockVisibleByUser;
    }

    public final String getDateText(QSClockBellSound qSClockBellSound) {
        return isTurnOnShowingDateByQuickStar() ? qSClockBellSound.QuickStarDateText : Intrinsics.areEqual(getContext().getResources().getConfiguration().getLocales().get(0).getLanguage(), "ko") ^ true ? qSClockBellSound.ShortDateText : qSClockBellSound.DateText;
    }

    public final boolean isTurnOnShowingDateByQuickStar() {
        SlimIndicatorViewMediator slimIndicatorViewMediator = this.slimIndicatorViewMediator;
        if (slimIndicatorViewMediator == null) {
            return false;
        }
        Intrinsics.checkNotNull(slimIndicatorViewMediator);
        SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator;
        String iconBlacklist = slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist();
        return slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_SHOW_DATE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0051, code lost:
    
        if ((r4 && ((r4 = r5.mItemLists.get("status_bar_show_date").getIntValue()) == -1 || r4 == 1)) == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006b, code lost:
    
        if (getContext().getResources().getConfiguration().getLayoutDirection() != 1) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x006d, code lost:
    
        r4 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(getDateText(r8), "   ");
        r0 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(r4, r0);
        r1 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(r4, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x008e, code lost:
    
        r4 = r7.slimIndicatorViewMediator;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0093, code lost:
    
        if (r4 == null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x009b, code lost:
    
        if (((com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl) r4).isLeftClockPosition() == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x009d, code lost:
    
        r5 = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_date_clock_right_padding);
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00d6, code lost:
    
        setText(r0);
        setContentDescription(r1);
        setPaddingRelative(r4, getPaddingTop(), r5, 0);
        r1 = r7.mClockVisibleByPolicy;
        r4 = r7.clockVisibleByUser;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00eb, code lost:
    
        if (getVisibility() != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ee, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00ef, code lost:
    
        r7 = getParent();
        r3 = new java.lang.StringBuilder("StatusBar clock=");
        r3.append(r0);
        r3.append(" notifyTimeChanged(");
        r3.append(r8);
        r3.append(") clockVisibleByPolicy=");
        com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(r3, r1, ", clockVisibleByUser=", r4, ", visible=");
        r3.append(r2);
        r3.append(", parent=");
        r3.append(r7);
        android.util.Log.d("QSClock", r3.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0125, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a8, code lost:
    
        r4 = r7.slimIndicatorViewMediator;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00aa, code lost:
    
        if (r4 == null) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ac, code lost:
    
        r4 = (com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl) r4;
        r6 = r4.mSettingsHelper.getIconBlacklist();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b8, code lost:
    
        if (r4.mPluginMediator.mIsSPluginConnected == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ba, code lost:
    
        if (r6 == null) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c3, code lost:
    
        if (r6.contains(com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel.DB_KEY_RIGHT_CLOCK_POSITION) == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00c5, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c8, code lost:
    
        if (r4 == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ca, code lost:
    
        r4 = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_date_clock_right_padding);
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00c7, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d4, code lost:
    
        r4 = 0;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x007e, code lost:
    
        r4 = androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0.m21m("   ", getDateText(r8));
        r0 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(r0, r4);
        r1 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(r1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0057, code lost:
    
        if (isTurnOnShowingDateByQuickStar() == false) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002e  */
    @Override // com.android.systemui.statusbar.policy.QSClock, com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        String str;
        boolean z;
        SlimIndicatorViewMediator slimIndicatorViewMediator = this.slimIndicatorViewMediator;
        String str2 = qSClockBellSound.TimeContentDescription;
        boolean z2 = true;
        if (slimIndicatorViewMediator != null) {
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator;
            String iconBlacklist = slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist();
            if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_SHOW_AMPM)) {
                str = str2;
                z = BasicRune.STATUS_LAYOUT_SHOW_DATE;
                if (z) {
                    SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
                    settingsHelper.getClass();
                }
            }
        }
        str = qSClockBellSound.TimeText;
        z = BasicRune.STATUS_LAYOUT_SHOW_DATE;
        if (z) {
        }
    }

    @Override // com.android.systemui.statusbar.policy.QSClock, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.shadowDisabled = true;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        setTextColor(DarkIconDispatcher.getTint(arrayList, this, i));
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
