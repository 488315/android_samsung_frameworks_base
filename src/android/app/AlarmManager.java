package android.app;

import android.annotation.SystemApi;
import android.app.IAlarmListener;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.i18n.timezone.ZoneInfoDb;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class AlarmManager {
    public static final String ACTION_ALARM_CLOCK_CHANGED = "com.samsung.android.action.ALARM_CLOCK_CHANGED";
    public static final String ACTION_NEXT_ALARM_CLOCK_CHANGED = "android.app.action.NEXT_ALARM_CLOCK_CHANGED";
    public static final String ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED = "android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED";
    public static final int ELAPSED_REALTIME = 3;
    public static final int ELAPSED_REALTIME_WAKEUP = 2;
    public static final long ENABLE_USE_EXACT_ALARM = 218533173;
    public static final long ENFORCE_MINIMUM_WINDOW_ON_INEXACT_ALARMS = 185199076;
    public static final long EXACT_LISTENER_ALARMS_DROPPED_ON_CACHED = 265195908;
    public static final int FLAG_ALLOW_WHILE_IDLE = 4;
    public static final int FLAG_ALLOW_WHILE_IDLE_COMPAT = 32;
    public static final int FLAG_ALLOW_WHILE_IDLE_UNRESTRICTED = 8;
    public static final int FLAG_IDLE_UNTIL = 16;
    public static final int FLAG_PRIORITIZE = 64;
    public static final int FLAG_STANDALONE = 1;
    public static final int FLAG_WAKE_FROM_IDLE = 2;
    private static final String GENERATED_TAG_PREFIX = "$android.alarm.generated";
    public static final long INTERVAL_DAY = 86400000;
    public static final long INTERVAL_FIFTEEN_MINUTES = 900000;
    public static final long INTERVAL_HALF_DAY = 43200000;
    public static final long INTERVAL_HALF_HOUR = 1800000;
    public static final long INTERVAL_HOUR = 3600000;
    public static final long REQUIRE_EXACT_ALARM_PERMISSION = 171306433;
    public static final int RTC = 1;
    public static final int RTC_WAKEUP = 0;
    public static final long SCHEDULE_EXACT_ALARM_DENIED_BY_DEFAULT = 226439802;
    public static final long SCHEDULE_EXACT_ALARM_DOES_NOT_ELEVATE_BUCKET = 262645982;
    private static final String TAG = "AlarmManager";
    public static final long WINDOW_EXACT = 0;
    public static final long WINDOW_HEURISTIC = -1;
    private static WeakHashMap<OnAlarmListener, WeakReference<ListenerWrapper>> sWrappers;
    private final boolean mAlwaysExact;
    private final Context mContext;
    private final Handler mMainThreadHandler;
    private final String mPackageName;
    private final IAlarmManager mService;
    private final int mTargetSdkVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlarmType {
    }

    public interface OnAlarmListener {
        void onAlarm();
    }

    final class ListenerWrapper extends IAlarmListener.Stub implements Runnable {
        IAlarmCompleteListener mCompletion;
        Executor mExecutor;
        final OnAlarmListener mListener;

        public ListenerWrapper(OnAlarmListener onAlarmListener) {
            this.mListener = onAlarmListener;
        }

        void setExecutor(Executor executor) {
            this.mExecutor = executor;
        }

        public void cancel() {
            try {
                AlarmManager.this.mService.remove(null, this);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.app.IAlarmListener
        public void doAlarm(IAlarmCompleteListener iAlarmCompleteListener) {
            this.mCompletion = iAlarmCompleteListener;
            this.mExecutor.execute(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mListener.onAlarm();
            } finally {
                try {
                    this.mCompletion.alarmComplete(this);
                } catch (Exception e) {
                    Log.e(AlarmManager.TAG, "Unable to report completion to Alarm Manager!", e);
                }
            }
        }
    }

    AlarmManager(IAlarmManager iAlarmManager, Context context) {
        this.mService = iAlarmManager;
        this.mContext = context;
        this.mPackageName = context.getPackageName();
        int i = context.getApplicationInfo().targetSdkVersion;
        this.mTargetSdkVersion = i;
        this.mAlwaysExact = i < 19;
        this.mMainThreadHandler = new Handler(context.getMainLooper());
    }

    private long legacyExactLength() {
        return this.mAlwaysExact ? 0L : -1L;
    }

    public void semSetAutoPowerUp(String str) {
        try {
            this.mService.setAutoPowerUp(str);
        } catch (RemoteException unused) {
        }
    }

    public void set(int i, long j, PendingIntent pendingIntent) {
        setImpl(i, j, legacyExactLength(), 0L, 0, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void set(int i, long j, String str, OnAlarmListener onAlarmListener, Handler handler) {
        setImpl(i, j, legacyExactLength(), 0L, 0, (PendingIntent) null, onAlarmListener, str, handler, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setRepeating(int i, long j, long j2, PendingIntent pendingIntent) {
        setImpl(i, j, legacyExactLength(), j2, 0, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setWindow(int i, long j, long j2, PendingIntent pendingIntent) {
        setImpl(i, j, j2, 0L, 0, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setWindow(int i, long j, long j2, String str, OnAlarmListener onAlarmListener, Handler handler) {
        setImpl(i, j, j2, 0L, 0, (PendingIntent) null, onAlarmListener, str, handler, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setWindow(int i, long j, long j2, String str, Executor executor, OnAlarmListener onAlarmListener) {
        setImpl(i, j, j2, 0L, 0, (PendingIntent) null, onAlarmListener, str, executor, (WorkSource) null, (AlarmClockInfo) null);
    }

    @SystemApi
    public void setWindow(int i, long j, long j2, String str, Executor executor, WorkSource workSource, OnAlarmListener onAlarmListener) {
        setImpl(i, j, j2, 0L, 0, (PendingIntent) null, onAlarmListener, str, executor, workSource, (AlarmClockInfo) null);
    }

    @SystemApi
    public void setPrioritized(int i, long j, long j2, String str, Executor executor, OnAlarmListener onAlarmListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onAlarmListener);
        setImpl(i, j, j2, 0L, 64, (PendingIntent) null, onAlarmListener, str, executor, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setExact(int i, long j, PendingIntent pendingIntent) {
        setImpl(i, j, 0L, 0L, 0, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setExact(int i, long j, String str, OnAlarmListener onAlarmListener, Handler handler) {
        setImpl(i, j, 0L, 0L, 0, (PendingIntent) null, onAlarmListener, str, handler, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setIdleUntil(int i, long j, String str, OnAlarmListener onAlarmListener, Handler handler) {
        setImpl(i, j, 0L, 0L, 16, (PendingIntent) null, onAlarmListener, str, handler, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setAlarmClock(AlarmClockInfo alarmClockInfo, PendingIntent pendingIntent) {
        setImpl(0, alarmClockInfo.getTriggerTime(), 0L, 0L, 0, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, alarmClockInfo);
    }

    @SystemApi
    public void set(int i, long j, long j2, long j3, PendingIntent pendingIntent, WorkSource workSource) {
        setImpl(i, j, j2, j3, 0, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, workSource, (AlarmClockInfo) null);
    }

    public void set(int i, long j, long j2, long j3, String str, OnAlarmListener onAlarmListener, Handler handler, WorkSource workSource) {
        setImpl(i, j, j2, j3, 0, (PendingIntent) null, onAlarmListener, str, handler, workSource, (AlarmClockInfo) null);
    }

    private static String makeTag(long j, WorkSource workSource) {
        StringBuilder sb = new StringBuilder("$android.alarm.generated:");
        sb.append(UserHandle.formatUid((workSource == null || workSource.isEmpty()) ? Process.myUid() : workSource.getAttributionUid()));
        sb.append(":");
        sb.append(j);
        return sb.toString();
    }

    @SystemApi
    @Deprecated
    public void set(int i, long j, long j2, long j3, OnAlarmListener onAlarmListener, Handler handler, WorkSource workSource) {
        setImpl(i, j, j2, j3, 0, (PendingIntent) null, onAlarmListener, makeTag(j, workSource), handler, workSource, (AlarmClockInfo) null);
    }

    @SystemApi
    public void setExact(int i, long j, String str, Executor executor, WorkSource workSource, OnAlarmListener onAlarmListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(workSource);
        Objects.requireNonNull(onAlarmListener);
        setImpl(i, j, 0L, 0L, 0, (PendingIntent) null, onAlarmListener, str, executor, workSource, (AlarmClockInfo) null);
    }

    private void setImpl(int i, long j, long j2, long j3, int i2, PendingIntent pendingIntent, OnAlarmListener onAlarmListener, String str, Handler handler, WorkSource workSource, AlarmClockInfo alarmClockInfo) {
        setImpl(i, j, j2, j3, i2, pendingIntent, onAlarmListener, str, new HandlerExecutor(handler != null ? handler : this.mMainThreadHandler), workSource, alarmClockInfo);
    }

    private void setImpl(int i, long j, long j2, long j3, int i2, PendingIntent pendingIntent, OnAlarmListener onAlarmListener, String str, Executor executor, WorkSource workSource, AlarmClockInfo alarmClockInfo) {
        long j4 = j < 0 ? 0L : j;
        if (onAlarmListener != null) {
            synchronized (AlarmManager.class) {
                if (sWrappers == null) {
                    sWrappers = new WeakHashMap<>();
                }
                WeakReference<ListenerWrapper> weakReference = sWrappers.get(onAlarmListener);
                listenerWrapper = weakReference != null ? weakReference.get() : null;
                if (listenerWrapper == null) {
                    listenerWrapper = new ListenerWrapper(onAlarmListener);
                    sWrappers.put(onAlarmListener, new WeakReference<>(listenerWrapper));
                }
            }
            listenerWrapper.setExecutor(executor);
        }
        try {
            this.mService.set(this.mPackageName, i, j4, j2, j3, i2, pendingIntent, listenerWrapper, str, workSource, alarmClockInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInexactRepeating(int i, long j, long j2, PendingIntent pendingIntent) {
        setImpl(i, j, -1L, j2, 0, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setAndAllowWhileIdle(int i, long j, PendingIntent pendingIntent) {
        setImpl(i, j, -1L, 0L, 4, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, (AlarmClockInfo) null);
    }

    public void setExactAndAllowWhileIdle(int i, long j, PendingIntent pendingIntent) {
        setImpl(i, j, 0L, 0L, 4, pendingIntent, (OnAlarmListener) null, (String) null, (Handler) null, (WorkSource) null, (AlarmClockInfo) null);
    }

    @SystemApi
    public void setExactAndAllowWhileIdle(int i, long j, String str, Executor executor, WorkSource workSource, OnAlarmListener onAlarmListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onAlarmListener);
        setImpl(i, j, 0L, 0L, 4, (PendingIntent) null, onAlarmListener, str, executor, workSource, (AlarmClockInfo) null);
    }

    public void cancel(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            if (this.mTargetSdkVersion >= 24) {
                throw new NullPointerException("cancel() called with a null PendingIntent");
            }
            Log.e(TAG, "cancel() called with a null PendingIntent");
        } else {
            try {
                this.mService.remove(pendingIntent, null);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancel(OnAlarmListener onAlarmListener) {
        ListenerWrapper listenerWrapper;
        WeakReference<ListenerWrapper> weakReference;
        if (onAlarmListener == null) {
            throw new NullPointerException("cancel() called with a null OnAlarmListener");
        }
        synchronized (AlarmManager.class) {
            WeakHashMap<OnAlarmListener, WeakReference<ListenerWrapper>> weakHashMap = sWrappers;
            listenerWrapper = (weakHashMap == null || (weakReference = weakHashMap.get(onAlarmListener)) == null) ? null : weakReference.get();
        }
        if (listenerWrapper == null) {
            Log.w(TAG, "Unrecognized alarm listener " + onAlarmListener);
            return;
        }
        listenerWrapper.cancel();
    }

    public void cancelAll() {
        try {
            this.mService.removeAll(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTime(long j) {
        try {
            this.mService.setTime(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (this.mTargetSdkVersion >= 23 && !ZoneInfoDb.getInstance().hasTimeZone(str)) {
            throw new IllegalArgumentException("Timezone: " + str + " is not an Olson ID");
        }
        try {
            this.mService.setTimeZone(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getNextWakeFromIdleTime() {
        try {
            return this.mService.getNextWakeFromIdleTime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canScheduleExactAlarms() {
        try {
            return this.mService.canScheduleExactAlarms(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasScheduleExactAlarm(String str, int i) {
        try {
            return this.mService.hasScheduleExactAlarm(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AlarmClockInfo getNextAlarmClock() {
        return getNextAlarmClock(this.mContext.getUserId());
    }

    public List<AlarmClockInfo> getNextAlarmClocks(int i) {
        try {
            return this.mService.getNextAlarmClocks(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AlarmClockInfo getNextAlarmClock(int i) {
        try {
            return this.mService.getNextAlarmClock(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class AlarmClockInfo implements Parcelable {
        public static final Parcelable.Creator<AlarmClockInfo> CREATOR = new Parcelable.Creator<AlarmClockInfo>() { // from class: android.app.AlarmManager.AlarmClockInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AlarmClockInfo createFromParcel(Parcel parcel) {
                return new AlarmClockInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AlarmClockInfo[] newArray(int i) {
                return new AlarmClockInfo[i];
            }
        };
        private final PendingIntent mShowIntent;
        private final long mTriggerTime;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AlarmClockInfo(long j, PendingIntent pendingIntent) {
            this.mTriggerTime = j;
            this.mShowIntent = pendingIntent;
        }

        AlarmClockInfo(Parcel parcel) {
            this.mTriggerTime = parcel.readLong();
            this.mShowIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
        }

        public long getTriggerTime() {
            return this.mTriggerTime;
        }

        public PendingIntent getShowIntent() {
            return this.mShowIntent;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.mTriggerTime);
            parcel.writeParcelable(this.mShowIntent, i);
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long jStart = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, this.mTriggerTime);
            PendingIntent pendingIntent = this.mShowIntent;
            if (pendingIntent != null) {
                pendingIntent.dumpDebug(protoOutputStream, 1146756268034L);
            }
            protoOutputStream.end(jStart);
        }
    }
}
