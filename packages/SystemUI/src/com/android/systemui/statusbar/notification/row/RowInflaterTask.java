package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutFactory;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.row.AsyncRowInflater;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.util.time.SystemClock;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class RowInflaterTask implements InflationTask, AsyncLayoutInflater.OnInflateFinishedListener, AsyncRowInflater.OnInflateFinishedListener {
    public final AsyncRowInflater mAsyncRowInflater;
    public boolean mCancelled;
    public NotificationEntry mEntry;
    public Throwable mInflateOrigin;
    public long mInflateStartTimeMs;
    public RowInflationFinishedListener mListener;
    public final RowInflaterTaskLogger mLogger;
    public final SystemClock mSystemClock;
    public UserTracker mUserTracker;

    public class RowAsyncLayoutInflater implements AsyncLayoutFactory {
        public final NotificationEntry mEntry;
        public final RowInflaterTaskLogger mLogger;
        public final SystemClock mSystemClock;

        public RowAsyncLayoutInflater(NotificationEntry notificationEntry, SystemClock systemClock, RowInflaterTaskLogger rowInflaterTaskLogger, UserHandle userHandle) {
            this.mEntry = notificationEntry;
            this.mSystemClock = systemClock;
            this.mLogger = rowInflaterTaskLogger;
        }

        @Override // android.view.LayoutInflater.Factory
        public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
            return null;
        }

        @Override // android.view.LayoutInflater.Factory2
        public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            if (!str.equals(ExpandableNotificationRow.class.getName())) {
                return null;
            }
            long jElapsedRealtime = this.mSystemClock.elapsedRealtime();
            int i = NotificationBundleUi.$r8$clinit;
            ExpandableNotificationRow expandableNotificationRow = new ExpandableNotificationRow(context, attributeSet, this.mEntry);
            long jElapsedRealtime2 = this.mSystemClock.elapsedRealtime() - jElapsedRealtime;
            RowInflaterTaskLogger rowInflaterTaskLogger = this.mLogger;
            NotificationEntry notificationEntry = this.mEntry;
            rowInflaterTaskLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            RowInflaterTaskLogger$$ExternalSyntheticLambda0 rowInflaterTaskLogger$$ExternalSyntheticLambda0 = new RowInflaterTaskLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = rowInflaterTaskLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("RowInflaterTask", logLevel, rowInflaterTaskLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.long1 = jElapsedRealtime2;
            logBuffer.commit(logMessageObtain);
            return expandableNotificationRow;
        }
    }

    public interface RowInflationFinishedListener {
    }

    public RowInflaterTask(SystemClock systemClock, RowInflaterTaskLogger rowInflaterTaskLogger, UserTracker userTracker, AsyncRowInflater asyncRowInflater) {
        this.mSystemClock = systemClock;
        this.mLogger = rowInflaterTaskLogger;
        this.mUserTracker = userTracker;
        this.mAsyncRowInflater = asyncRowInflater;
    }

    @Override // com.android.systemui.statusbar.InflationTask
    public final void abort() {
        this.mCancelled = true;
    }

    public void inflate(Context context, ViewGroup viewGroup, NotificationEntry notificationEntry, Executor executor, RowInflationFinishedListener rowInflationFinishedListener) {
        this.mInflateOrigin = new Throwable("inflate requested here");
        this.mListener = rowInflationFinishedListener;
        UserHandle userHandle = ((UserTrackerImpl) this.mUserTracker).getUserHandle();
        SystemClock systemClock = this.mSystemClock;
        RowInflaterTaskLogger rowInflaterTaskLogger = this.mLogger;
        RowAsyncLayoutInflater rowAsyncLayoutInflater = new RowAsyncLayoutInflater(notificationEntry, systemClock, rowInflaterTaskLogger, userHandle);
        this.mEntry = notificationEntry;
        notificationEntry.abortTask();
        notificationEntry.mRunningTask = this;
        rowInflaterTaskLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        RowInflaterTaskLogger$$ExternalSyntheticLambda0 rowInflaterTaskLogger$$ExternalSyntheticLambda0 = new RowInflaterTaskLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = rowInflaterTaskLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("RowInflaterTask", logLevel, rowInflaterTaskLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = NotificationUtils.logKey(notificationEntry);
        logBuffer.commit(logMessageObtain);
        this.mInflateStartTimeMs = systemClock.elapsedRealtime();
        try {
            AsyncRowInflater asyncRowInflater = this.mAsyncRowInflater;
            asyncRowInflater.getClass();
            BasicRowInflater basicRowInflater = new BasicRowInflater(context);
            basicRowInflater.setFactory2(rowAsyncLayoutInflater);
            CoroutineTracingKt.launchTraced$default(asyncRowInflater.applicationScope, asyncRowInflater.inflationCoroutineDispatcher, null, new AsyncRowInflater$inflate$1(basicRowInflater, R.layout.status_bar_notification_row, viewGroup, asyncRowInflater, this, null), 4);
        } catch (Throwable th) {
            Log.e("RowInflaterTask", "Error in AsyncLayoutInflation: " + th);
            throw th;
        }
    }

    public ExpandableNotificationRow inflateSynchronously(Context context, ViewGroup viewGroup, NotificationEntry notificationEntry) {
        BasicRowInflater basicRowInflater = new BasicRowInflater(context);
        basicRowInflater.setFactory2(new RowAsyncLayoutInflater(notificationEntry, this.mSystemClock, this.mLogger, ((UserTrackerImpl) this.mUserTracker).getUserHandle()));
        return (ExpandableNotificationRow) basicRowInflater.inflate(R.layout.status_bar_notification_row, viewGroup, false);
    }

    @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
    public final void onInflateFinished(int i, View view, ViewGroup viewGroup) {
        long jElapsedRealtime = this.mSystemClock.elapsedRealtime() - this.mInflateStartTimeMs;
        NotificationEntry notificationEntry = this.mEntry;
        boolean z = this.mCancelled;
        RowInflaterTaskLogger rowInflaterTaskLogger = this.mLogger;
        rowInflaterTaskLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        RowInflaterTaskLogger$$ExternalSyntheticLambda0 rowInflaterTaskLogger$$ExternalSyntheticLambda0 = new RowInflaterTaskLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = rowInflaterTaskLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("RowInflaterTask", logLevel, rowInflaterTaskLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.long1 = jElapsedRealtime;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        if (this.mCancelled) {
            return;
        }
        try {
            this.mEntry.mRunningTask = null;
            ((NotificationRowBinderImpl$$ExternalSyntheticLambda0) this.mListener).onInflationFinished((ExpandableNotificationRow) view);
        } catch (Throwable th) {
            if (this.mInflateOrigin != null) {
                Log.e("RowInflaterTask", RowInflaterTask$$ExternalSyntheticOutline0.m("Error in inflation finished listener: ", th), this.mInflateOrigin);
                th.addSuppressed(this.mInflateOrigin);
            }
            throw th;
        }
    }
}
