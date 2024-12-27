package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.asynclayoutinflater.view.AsyncLayoutFactory;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl$$ExternalSyntheticLambda0;
import com.android.systemui.util.time.SystemClock;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RowInflaterTask implements InflationTask, AsyncLayoutInflater.OnInflateFinishedListener {
    public boolean mCancelled;
    public NotificationEntry mEntry;
    public Throwable mInflateOrigin;
    public long mInflateStartTimeMs;
    public RowInflationFinishedListener mListener;
    public final RowInflaterTaskLogger mLogger;
    public final SystemClock mSystemClock;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class RowAsyncLayoutInflater implements AsyncLayoutFactory {
        public final NotificationEntry mEntry;
        public final RowInflaterTaskLogger mLogger;
        public final SystemClock mSystemClock;

        public RowAsyncLayoutInflater(NotificationEntry notificationEntry, SystemClock systemClock, RowInflaterTaskLogger rowInflaterTaskLogger) {
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
            long elapsedRealtime = this.mSystemClock.elapsedRealtime();
            ExpandableNotificationRow expandableNotificationRow = new ExpandableNotificationRow(context, attributeSet, this.mEntry);
            long elapsedRealtime2 = this.mSystemClock.elapsedRealtime() - elapsedRealtime;
            RowInflaterTaskLogger rowInflaterTaskLogger = this.mLogger;
            NotificationEntry notificationEntry = this.mEntry;
            rowInflaterTaskLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            RowInflaterTaskLogger$logCreatedRow$2 rowInflaterTaskLogger$logCreatedRow$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$logCreatedRow$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return "created row in " + logMessage.getLong1() + " ms for " + logMessage.getStr1();
                }
            };
            LogBuffer logBuffer = rowInflaterTaskLogger.buffer;
            LogMessage obtain = logBuffer.obtain("RowInflaterTask", logLevel, rowInflaterTaskLogger$logCreatedRow$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.long1 = elapsedRealtime2;
            logBuffer.commit(obtain);
            return expandableNotificationRow;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface RowInflationFinishedListener {
    }

    public RowInflaterTask(SystemClock systemClock, RowInflaterTaskLogger rowInflaterTaskLogger) {
        this.mSystemClock = systemClock;
        this.mLogger = rowInflaterTaskLogger;
    }

    @Override // com.android.systemui.statusbar.InflationTask
    public final void abort() {
        this.mCancelled = true;
    }

    public void inflate(Context context, ViewGroup viewGroup, NotificationEntry notificationEntry, Executor executor, RowInflationFinishedListener rowInflationFinishedListener) {
        this.mInflateOrigin = new Throwable("inflate requested here");
        this.mListener = rowInflationFinishedListener;
        SystemClock systemClock = this.mSystemClock;
        RowInflaterTaskLogger rowInflaterTaskLogger = this.mLogger;
        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context, new RowAsyncLayoutInflater(notificationEntry, systemClock, rowInflaterTaskLogger));
        this.mEntry = notificationEntry;
        notificationEntry.abortTask();
        notificationEntry.mRunningTask = this;
        rowInflaterTaskLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        RowInflaterTaskLogger$logInflateStart$2 rowInflaterTaskLogger$logInflateStart$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$logInflateStart$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("started row inflation for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = rowInflaterTaskLogger.buffer;
        LogMessage obtain = logBuffer.obtain("RowInflaterTask", logLevel, rowInflaterTaskLogger$logInflateStart$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
        this.mInflateStartTimeMs = systemClock.elapsedRealtime();
        try {
            asyncLayoutInflater.inflateInternal(R.layout.status_bar_notification_row, viewGroup, this, asyncLayoutInflater.mInflater, executor);
        } catch (Throwable th) {
            Log.e("RowInflaterTask", "Error in AsyncLayoutInflation: " + th);
            throw th;
        }
    }

    @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
    public final void onInflateFinished(View view, ViewGroup viewGroup) {
        long elapsedRealtime = this.mSystemClock.elapsedRealtime() - this.mInflateStartTimeMs;
        NotificationEntry notificationEntry = this.mEntry;
        boolean z = this.mCancelled;
        RowInflaterTaskLogger rowInflaterTaskLogger = this.mLogger;
        rowInflaterTaskLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        RowInflaterTaskLogger$logInflateFinish$2 rowInflaterTaskLogger$logInflateFinish$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$logInflateFinish$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str = logMessage.getBool1() ? "cancelled " : "";
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                StringBuilder sb = new StringBuilder("finished ");
                sb.append(str);
                sb.append("row inflation in ");
                sb.append(long1);
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " ms for ", str1);
            }
        };
        LogBuffer logBuffer = rowInflaterTaskLogger.buffer;
        LogMessage obtain = logBuffer.obtain("RowInflaterTask", logLevel, rowInflaterTaskLogger$logInflateFinish$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.long1 = elapsedRealtime;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
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
