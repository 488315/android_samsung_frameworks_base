package com.android.systemui.qs.tiles;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.NextAlarmController;
import java.util.Locale;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AlarmTile extends QSTileImpl {
    public final AlarmTile$callback$1 callback;
    public final Intent defaultIntent;
    public final QSTile.Icon icon;
    public AlarmManager.AlarmClockInfo lastAlarmInfo;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.qs.tiles.AlarmTile$callback$1, java.lang.Object] */
    public AlarmTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker, NextAlarmController nextAlarmController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.userTracker = userTracker;
        this.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_alarm);
        this.defaultIntent = new Intent("android.intent.action.SHOW_ALARMS");
        ?? r1 = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.qs.tiles.AlarmTile$callback$1
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                AlarmTile alarmTile = AlarmTile.this;
                alarmTile.lastAlarmInfo = alarmClockInfo;
                alarmTile.refreshState(null);
            }
        };
        this.callback = r1;
        nextAlarmController.observe(this.mLifecycle, r1);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.status_bar_alarm);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController;
        if (view != null) {
            ActivityLaunchAnimator.Controller.Companion.getClass();
            ghostedViewLaunchAnimatorController = ActivityLaunchAnimator.Controller.Companion.fromView(view, 32);
        } else {
            ghostedViewLaunchAnimatorController = null;
        }
        AlarmManager.AlarmClockInfo alarmClockInfo = this.lastAlarmInfo;
        PendingIntent showIntent = alarmClockInfo != null ? alarmClockInfo.getShowIntent() : null;
        ActivityStarter activityStarter = this.mActivityStarter;
        if (showIntent != null) {
            activityStarter.postStartActivityDismissingKeyguard(showIntent, ghostedViewLaunchAnimatorController);
        } else {
            activityStarter.postStartActivityDismissingKeyguard(this.defaultIntent, 0, ghostedViewLaunchAnimatorController);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        Unit unit;
        state.icon = this.icon;
        state.label = getTileLabel();
        AlarmManager.AlarmClockInfo alarmClockInfo = this.lastAlarmInfo;
        Context context = this.mContext;
        if (alarmClockInfo != null) {
            state.secondaryLabel = DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), DateFormat.is24HourFormat(context, ((UserTrackerImpl) this.userTracker).getUserId()) ? "EHm" : "Ehma"), alarmClockInfo.getTriggerTime()).toString();
            state.state = 2;
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            state.secondaryLabel = context.getString(R.string.qs_alarm_tile_no_alarm);
            state.state = 1;
        }
        state.contentDescription = TextUtils.concat(state.label, ", ", state.secondaryLabel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.handlesLongClick = false;
        return state;
    }

    /* renamed from: getDefaultIntent$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m178x39713f4d() {
    }
}
