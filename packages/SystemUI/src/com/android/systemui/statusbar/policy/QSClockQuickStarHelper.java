package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.util.SettingsHelper;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSClockQuickStarHelper implements SlimIndicatorViewSubscriber {
    public final Context mContext;
    public int mPanelState;
    public String mQuickStarDateStringFormat;
    public String mQuickStarDateStringPattern;
    public final Runnable mRingBellOfTowerRunnable;
    public Handler mSecondsHandler;
    private SettingsHelper mSettingsHelper;
    public boolean mShouldShowSecondsClockByQuickStar;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
    public final AnonymousClass1 mSecondTick = new Runnable() { // from class: com.android.systemui.statusbar.policy.QSClockQuickStarHelper.1
        @Override // java.lang.Runnable
        public final void run() {
            QSClockQuickStarHelper.this.mRingBellOfTowerRunnable.run();
            QSClockQuickStarHelper qSClockQuickStarHelper = QSClockQuickStarHelper.this;
            Handler handler = qSClockQuickStarHelper.mSecondsHandler;
            if (handler != null) {
                qSClockQuickStarHelper.getClass();
                handler.postAtTime(this, (SystemClock.uptimeMillis() + 1000) - (System.currentTimeMillis() % 1000));
            }
        }
    };
    private SettingsHelper.OnChangedCallback mDateFormatChangedListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.policy.QSClockQuickStarHelper.2
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            QSClockQuickStarHelper qSClockQuickStarHelper = QSClockQuickStarHelper.this;
            qSClockQuickStarHelper.mQuickStarDateStringFormat = null;
            qSClockQuickStarHelper.mRingBellOfTowerRunnable.run();
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.policy.QSClockQuickStarHelper$1] */
    public QSClockQuickStarHelper(SlimIndicatorViewMediator slimIndicatorViewMediator, Runnable runnable, Context context, SettingsHelper settingsHelper) {
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mRingBellOfTowerRunnable = runnable;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
    }

    public final String getQuickStarDateViewText() {
        if (TextUtils.isEmpty(this.mQuickStarDateStringFormat)) {
            int quickStarDateFormat = this.mSettingsHelper.getQuickStarDateFormat();
            if (quickStarDateFormat == 1) {
                this.mQuickStarDateStringPattern = this.mContext.getString(R.string.quickstar_short_date_pattern_short_month_day);
            } else if (quickStarDateFormat == 2) {
                this.mQuickStarDateStringPattern = this.mContext.getString(R.string.quickstar_short_date_pattern_month_day);
            } else if (quickStarDateFormat == 3) {
                this.mQuickStarDateStringPattern = this.mContext.getString(R.string.quickstar_short_date_pattern_day_weekday);
            } else if (quickStarDateFormat != 4) {
                this.mQuickStarDateStringPattern = this.mContext.getString(R.string.quick_panel_shorten_date_pattern);
            } else {
                this.mQuickStarDateStringPattern = this.mContext.getString(R.string.quickstar_short_date_pattern_weekday);
            }
            try {
                this.mQuickStarDateStringFormat = DateFormat.getBestDateTimePattern(Locale.getDefault(), this.mQuickStarDateStringPattern).trim();
            } catch (ExceptionInInitializerError unused) {
                this.mQuickStarDateStringFormat = null;
                return "";
            }
        }
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        return DateFormat.format(this.mQuickStarDateStringFormat, date).toString();
    }

    public final void init() {
        ((SlimIndicatorViewMediatorImpl) this.mSlimIndicatorViewMediator).registerSubscriber("QSClockBellTower", this);
        this.mSettingsHelper.registerCallback(this.mDateFormatChangedListener, Settings.Global.getUriFor(SettingsHelper.INDEX_QUICKSTAR_DATE_FORMAT));
        SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecPanelExpansionStateInteractor.class);
        ((CopyOnWriteArrayList) secPanelExpansionStateInteractor.expansionStateListeners$delegate.getValue()).add(new SecPanelExpansionStateListener() { // from class: com.android.systemui.statusbar.policy.QSClockQuickStarHelper$$ExternalSyntheticLambda0
            @Override // com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener
            public final void onPanelExpansionStateChanged(SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent) {
                QSClockQuickStarHelper qSClockQuickStarHelper = QSClockQuickStarHelper.this;
                qSClockQuickStarHelper.getClass();
                qSClockQuickStarHelper.mPanelState = secPanelExpansionStateChangeEvent.panelExpansionState;
                qSClockQuickStarHelper.updateSecondsClockHandler();
            }
        });
        new SecPanelExpansionStateChangeEvent(((Number) secPanelExpansionStateInteractor.getRepository().panelState.$$delegate_0.getValue()).intValue());
    }

    public final boolean shouldShowSecondsClock() {
        int i;
        return this.mShouldShowSecondsClockByQuickStar && ((i = this.mPanelState) == 2 || i == 1);
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
    public final void updateQuickStarStyle() {
        boolean shouldShowSecondsClock = ((SlimIndicatorViewMediatorImpl) this.mSlimIndicatorViewMediator).shouldShowSecondsClock();
        Log.d("QSClockBellTower", "updateQuickStarStyle() shouldShowSecondsClock(" + this.mShouldShowSecondsClockByQuickStar + " >> " + shouldShowSecondsClock + ")");
        if (this.mShouldShowSecondsClockByQuickStar != shouldShowSecondsClock) {
            this.mShouldShowSecondsClockByQuickStar = shouldShowSecondsClock;
            updateSecondsClockHandler();
        }
        this.mRingBellOfTowerRunnable.run();
    }

    public final void updateSecondsClockHandler() {
        boolean shouldShowSecondsClock = shouldShowSecondsClock();
        AnonymousClass1 anonymousClass1 = this.mSecondTick;
        if (shouldShowSecondsClock) {
            if (this.mSecondsHandler == null) {
                Handler handler = new Handler();
                this.mSecondsHandler = handler;
                handler.post(anonymousClass1);
                return;
            }
            return;
        }
        Handler handler2 = this.mSecondsHandler;
        if (handler2 != null) {
            handler2.removeCallbacks(anonymousClass1);
            this.mSecondsHandler = null;
        }
    }
}
