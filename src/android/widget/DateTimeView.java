package android.widget;

import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.PluralsMessageFormatter;
import android.view.RemotableViewMethod;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.flags.Flags;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.samsung.android.knox.analytics.database.Contract;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class DateTimeView extends TextView {
    public static final int DISAMBIGUATION_TEXT_FUTURE = 2;
    public static final int DISAMBIGUATION_TEXT_PAST = 1;
    private static final int SHOW_MONTH_DAY_YEAR = 1;
    private static final int SHOW_TIME = 0;
    private static final String TAG = "DateTimeView";
    public static final int UNIT_DISPLAY_LENGTH_MEDIUM = 1;
    public static final int UNIT_DISPLAY_LENGTH_SHORTEST = 0;
    private static final ThreadLocal<ReceiverInfo> sReceiverInfo = new ThreadLocal<>();
    private static DateFormat sTimeFormat;
    private final boolean mCanUseRelativeTimeDisplayConfigs;
    int mLastDisplay;
    DateFormat mLastFormat;
    private LocalDateTime mLocalTime;
    private String mNowText;
    private int mRelativeTimeDisambiguationTextMask;
    private int mRelativeTimeUnitDisplayLength;
    private boolean mShowRelativeTime;
    private long mTimeMillis;
    private long mUpdateTimeMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisambiguationTextMask {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UnitDisplayLength {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<DateTimeView> {
        private boolean mPropertiesMapped = false;
        private int mShowReleativeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mShowReleativeId = propertyMapper.mapBoolean("showReleative", 0);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(DateTimeView dateTimeView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mShowReleativeId, dateTimeView.isShowRelativeTime());
        }
    }

    public DateTimeView(Context context) {
        this(context, null);
    }

    public DateTimeView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        boolean zDateTimeViewRelativeTimeDisplayConfigs = Flags.dateTimeViewRelativeTimeDisplayConfigs();
        this.mCanUseRelativeTimeDisplayConfigs = zDateTimeViewRelativeTimeDisplayConfigs;
        this.mLastDisplay = -1;
        this.mRelativeTimeUnitDisplayLength = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DateTimeView, 0, 0);
        setShowRelativeTime(typedArrayObtainStyledAttributes.getBoolean(2, false));
        if (zDateTimeViewRelativeTimeDisplayConfigs) {
            setRelativeTimeDisambiguationTextMask(typedArrayObtainStyledAttributes.getInt(0, 2));
            setRelativeTimeUnitDisplayLength(typedArrayObtainStyledAttributes.getInt(1, 0));
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() throws Resources.NotFoundException {
        super.onAttachedToWindow();
        ThreadLocal<ReceiverInfo> threadLocal = sReceiverInfo;
        ReceiverInfo receiverInfo = threadLocal.get();
        if (receiverInfo == null) {
            receiverInfo = new ReceiverInfo();
            threadLocal.set(receiverInfo);
        }
        receiverInfo.addView(this);
        if (this.mShowRelativeTime) {
            update();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ReceiverInfo receiverInfo = sReceiverInfo.get();
        if (receiverInfo != null) {
            receiverInfo.removeView(this);
        }
    }

    @RemotableViewMethod
    public void setTime(long j) throws Resources.NotFoundException {
        this.mTimeMillis = j;
        this.mLocalTime = toLocalDateTime(j, ZoneId.systemDefault()).withSecond(0);
        update();
    }

    @RemotableViewMethod
    public void setShowRelativeTime(boolean z) throws Resources.NotFoundException {
        this.mShowRelativeTime = z;
        updateNowText();
        update();
    }

    @RemotableViewMethod
    public void setRelativeTimeDisambiguationTextMask(int i) throws Resources.NotFoundException {
        if (this.mCanUseRelativeTimeDisplayConfigs) {
            this.mRelativeTimeDisambiguationTextMask = i;
            updateNowText();
            update();
        }
    }

    @RemotableViewMethod
    public void setRelativeTimeUnitDisplayLength(int i) throws Resources.NotFoundException {
        if (this.mCanUseRelativeTimeDisplayConfigs) {
            this.mRelativeTimeUnitDisplayLength = i;
            updateNowText();
            update();
        }
    }

    public boolean isShowRelativeTime() {
        return this.mShowRelativeTime;
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setVisibility(int i) throws Resources.NotFoundException {
        boolean z = i != 8 && getVisibility() == 8;
        super.setVisibility(i);
        if (z) {
            update();
        }
    }

    void update() throws Resources.NotFoundException {
        DateFormat timeFormat;
        if (this.mLocalTime == null || getVisibility() == 8) {
            return;
        }
        if (this.mShowRelativeTime) {
            updateRelativeTime();
            return;
        }
        ZoneId zoneIdSystemDefault = ZoneId.systemDefault();
        LocalDateTime localDateTime = this.mLocalTime;
        LocalDateTime localDateTimeOf = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIDNIGHT);
        LocalDateTime localDateTimePlusDays = localDateTimeOf.plusDays(1L);
        int i = 0;
        LocalDateTime localDateTimeWithSecond = LocalDateTime.now(zoneIdSystemDefault).withSecond(0);
        long epochMillis = toEpochMillis(localDateTime.minusHours(12L), zoneIdSystemDefault);
        long epochMillis2 = toEpochMillis(localDateTime.plusHours(12L), zoneIdSystemDefault);
        long epochMillis3 = toEpochMillis(localDateTimeOf, zoneIdSystemDefault);
        long epochMillis4 = toEpochMillis(localDateTimePlusDays, zoneIdSystemDefault);
        long epochMillis5 = toEpochMillis(localDateTime, zoneIdSystemDefault);
        long epochMillis6 = toEpochMillis(localDateTimeWithSecond, zoneIdSystemDefault);
        if ((epochMillis6 < epochMillis3 || epochMillis6 >= epochMillis4) && (epochMillis6 < epochMillis || epochMillis6 >= epochMillis2)) {
            i = 1;
        }
        if (i != this.mLastDisplay || (timeFormat = this.mLastFormat) == null) {
            if (i == 0) {
                timeFormat = sTimeFormat;
                if (timeFormat == null) {
                    timeFormat = getTimeFormat();
                }
            } else if (i == 1) {
                timeFormat = DateFormat.getDateInstance(3);
            } else {
                throw new RuntimeException("unknown display value: " + i);
            }
            this.mLastFormat = timeFormat;
        }
        maybeSetText(timeFormat.format(new Date(epochMillis5)));
        if (i == 0) {
            if (epochMillis2 <= epochMillis4) {
                epochMillis2 = epochMillis4;
            }
            this.mUpdateTimeMillis = epochMillis2;
        } else {
            if (this.mTimeMillis < epochMillis6) {
                this.mUpdateTimeMillis = 0L;
                return;
            }
            if (epochMillis >= epochMillis3) {
                epochMillis = epochMillis3;
            }
            this.mUpdateTimeMillis = epochMillis;
        }
    }

    private void updateRelativeTime() throws Resources.NotFoundException {
        int i;
        String string;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jAbs = Math.abs(jCurrentTimeMillis - this.mTimeMillis);
        boolean z = jCurrentTimeMillis >= this.mTimeMillis;
        long j = 60000;
        if (jAbs < 60000) {
            maybeSetText(this.mNowText);
            this.mUpdateTimeMillis = this.mTimeMillis + 60001;
            return;
        }
        long j2 = 3600000;
        if (jAbs < 3600000) {
            i = (int) (jAbs / 60000);
            string = getContext().getResources().getString(getMinutesStringId(z), Integer.valueOf(i));
        } else {
            j = 86400000;
            if (jAbs < 86400000) {
                i = (int) (jAbs / 3600000);
                string = getContext().getResources().getString(getHoursStringId(z), Integer.valueOf(i));
            } else {
                j2 = 31449600000L;
                if (jAbs < 31449600000L) {
                    LocalDateTime localDateTime = this.mLocalTime;
                    ZoneId zoneIdSystemDefault = ZoneId.systemDefault();
                    LocalDateTime localDateTime2 = toLocalDateTime(jCurrentTimeMillis, zoneIdSystemDefault);
                    int iMax = Math.max(Math.abs(dayDistance(localDateTime, localDateTime2)), 1);
                    String string2 = getContext().getResources().getString(getDaysStringId(z), Integer.valueOf(iMax));
                    if (z || iMax != 1) {
                        this.mUpdateTimeMillis = computeNextMidnight(localDateTime2, zoneIdSystemDefault);
                        j = -1;
                    }
                    i = iMax;
                    string = string2;
                } else {
                    i = (int) (jAbs / 31449600000L);
                    string = getContext().getResources().getString(getYearsStringId(z), Integer.valueOf(i));
                }
            }
            j = j2;
        }
        if (j != -1) {
            if (z) {
                this.mUpdateTimeMillis = this.mTimeMillis + (j * (i + 1)) + 1;
            } else {
                this.mUpdateTimeMillis = (this.mTimeMillis - (j * i)) + 1;
            }
        }
        maybeSetText(string);
    }

    private int getMinutesStringId(boolean z) {
        return !this.mCanUseRelativeTimeDisplayConfigs ? z ? R.string.duration_minutes_shortest : R.string.duration_minutes_shortest_future : this.mRelativeTimeUnitDisplayLength == 0 ? (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_minutes_shortest : R.string.duration_minutes_shortest_future : R.string.duration_minutes_shortest_past : (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_minutes_medium : R.string.duration_minutes_medium_future : R.string.duration_minutes_medium_past;
    }

    private int getHoursStringId(boolean z) {
        return !this.mCanUseRelativeTimeDisplayConfigs ? z ? R.string.duration_hours_shortest : R.string.duration_hours_shortest_future : this.mRelativeTimeUnitDisplayLength == 0 ? (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_hours_shortest : R.string.duration_hours_shortest_future : R.string.duration_hours_shortest_past : (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_hours_medium : R.string.duration_hours_medium_future : R.string.duration_hours_medium_past;
    }

    private int getDaysStringId(boolean z) {
        return !this.mCanUseRelativeTimeDisplayConfigs ? z ? R.string.duration_days_shortest : R.string.duration_days_shortest_future : this.mRelativeTimeUnitDisplayLength == 0 ? (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_days_shortest : R.string.duration_days_shortest_future : R.string.duration_days_shortest_past : (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_days_medium : R.string.duration_days_medium_future : R.string.duration_days_medium_past;
    }

    private int getYearsStringId(boolean z) {
        return !this.mCanUseRelativeTimeDisplayConfigs ? z ? R.string.duration_years_shortest : R.string.duration_years_shortest_future : this.mRelativeTimeUnitDisplayLength == 0 ? (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_years_shortest : R.string.duration_years_shortest_future : R.string.duration_years_shortest_past : (!z || (this.mRelativeTimeDisambiguationTextMask & 1) == 0) ? (z || (this.mRelativeTimeDisambiguationTextMask & 2) == 0) ? R.string.duration_years_medium : R.string.duration_years_medium_future : R.string.duration_years_medium_past;
    }

    private void maybeSetText(String str) {
        if (TextUtils.equals(getText(), str)) {
            return;
        }
        lambda$setTextAsync$0(str);
    }

    private static long computeNextMidnight(LocalDateTime localDateTime, ZoneId zoneId) {
        return toEpochMillis(LocalDateTime.of(localDateTime.toLocalDate().plusDays(1L), LocalTime.MIDNIGHT), zoneId);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        updateNowText();
        update();
    }

    private void updateNowText() {
        if (this.mShowRelativeTime) {
            this.mNowText = getContext().getResources().getString(R.string.now_string_shortest);
        }
    }

    private static int dayDistance(LocalDateTime localDateTime, LocalDateTime localDateTime2) {
        return (int) (localDateTime2.getLong(JulianFields.JULIAN_DAY) - localDateTime.getLong(JulianFields.JULIAN_DAY));
    }

    private DateFormat getTimeFormat() {
        Log.i(TAG, "getTimeFormat");
        return android.text.format.DateFormat.getTimeFormat(getContext());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clearFormatAndUpdate() throws Resources.NotFoundException {
        this.mLastFormat = null;
        update();
    }

    @Override // android.widget.TextView, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) throws Resources.NotFoundException {
        String str;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mShowRelativeTime) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jAbs = Math.abs(jCurrentTimeMillis - this.mTimeMillis);
            boolean z = jCurrentTimeMillis >= this.mTimeMillis;
            HashMap map = new HashMap();
            if (jAbs < 60000) {
                str = this.mNowText;
            } else if (jAbs < 3600000) {
                map.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf((int) (jAbs / 60000)));
                str = PluralsMessageFormatter.format(getContext().getResources(), map, z ? R.string.duration_minutes_relative : R.string.duration_minutes_relative_future);
            } else if (jAbs < 86400000) {
                map.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf((int) (jAbs / 3600000)));
                str = PluralsMessageFormatter.format(getContext().getResources(), map, z ? R.string.duration_hours_relative : R.string.duration_hours_relative_future);
            } else if (jAbs < 31449600000L) {
                map.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf(Math.max(Math.abs(dayDistance(this.mLocalTime, toLocalDateTime(jCurrentTimeMillis, ZoneId.systemDefault()))), 1)));
                str = PluralsMessageFormatter.format(getContext().getResources(), map, z ? R.string.duration_days_relative : R.string.duration_days_relative_future);
            } else {
                map.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf((int) (jAbs / 31449600000L)));
                str = PluralsMessageFormatter.format(getContext().getResources(), map, z ? R.string.duration_years_relative : R.string.duration_years_relative_future);
            }
            accessibilityNodeInfo.setText(str);
        }
    }

    public static void setReceiverHandler(Handler handler) {
        ThreadLocal<ReceiverInfo> threadLocal = sReceiverInfo;
        ReceiverInfo receiverInfo = threadLocal.get();
        if (receiverInfo == null) {
            receiverInfo = new ReceiverInfo();
            threadLocal.set(receiverInfo);
        }
        receiverInfo.setHandler(handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ReceiverInfo {
        private final ArrayList<DateTimeView> mAttachedViews;
        private Handler mHandler;
        private final ContentObserver mObserver;
        private final BroadcastReceiver mReceiver;

        private ReceiverInfo() {
            this.mAttachedViews = new ArrayList<>();
            this.mReceiver = new BroadcastReceiver() { // from class: android.widget.DateTimeView.ReceiverInfo.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (!Intent.ACTION_TIME_TICK.equals(intent.getAction()) || System.currentTimeMillis() >= ReceiverInfo.this.getSoonestUpdateTime()) {
                        ReceiverInfo.this.updateAll();
                    }
                }
            };
            this.mObserver = new ContentObserver(new Handler()) { // from class: android.widget.DateTimeView.ReceiverInfo.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    ReceiverInfo.this.updateAll();
                }
            };
            this.mHandler = new Handler();
        }

        public void addView(DateTimeView dateTimeView) {
            synchronized (this.mAttachedViews) {
                boolean zIsEmpty = this.mAttachedViews.isEmpty();
                this.mAttachedViews.add(dateTimeView);
                if (zIsEmpty) {
                    register(getApplicationContextIfAvailable(dateTimeView.getContext()));
                }
            }
        }

        public void removeView(DateTimeView dateTimeView) {
            synchronized (this.mAttachedViews) {
                if (this.mAttachedViews.remove(dateTimeView) && this.mAttachedViews.isEmpty()) {
                    unregister(getApplicationContextIfAvailable(dateTimeView.getContext()));
                }
            }
        }

        void updateAll() {
            synchronized (this.mAttachedViews) {
                int size = this.mAttachedViews.size();
                if (size > 0) {
                    Log.i(DateTimeView.TAG, "updateAll - getTimeFormat");
                    DateTimeView.sTimeFormat = android.text.format.DateFormat.getTimeFormat(this.mAttachedViews.get(0).getContext());
                }
                for (int i = 0; i < size; i++) {
                    final DateTimeView dateTimeView = this.mAttachedViews.get(i);
                    dateTimeView.post(new Runnable() { // from class: android.widget.DateTimeView$ReceiverInfo$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            dateTimeView.clearFormatAndUpdate();
                        }
                    });
                }
            }
        }

        long getSoonestUpdateTime() {
            long j;
            synchronized (this.mAttachedViews) {
                int size = this.mAttachedViews.size();
                j = Long.MAX_VALUE;
                for (int i = 0; i < size; i++) {
                    long j2 = this.mAttachedViews.get(i).mUpdateTimeMillis;
                    if (j2 < j) {
                        j = j2;
                    }
                }
            }
            return j;
        }

        static final Context getApplicationContextIfAvailable(Context context) {
            Context applicationContext = context.getApplicationContext();
            return applicationContext != null ? applicationContext : ActivityThread.currentApplication().getApplicationContext();
        }

        void register(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_TICK);
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
            intentFilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            context.registerReceiver(this.mReceiver, intentFilter, null, this.mHandler);
        }

        void unregister(Context context) {
            context.unregisterReceiver(this.mReceiver);
        }

        public void setHandler(Handler handler) {
            this.mHandler = handler;
            synchronized (this.mAttachedViews) {
                if (!this.mAttachedViews.isEmpty()) {
                    unregister(this.mAttachedViews.get(0).getContext());
                    register(this.mAttachedViews.get(0).getContext());
                }
            }
        }
    }

    private static LocalDateTime toLocalDateTime(long j, ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(j), zoneId);
    }

    private static long toEpochMillis(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.toInstant(zoneId.getRules().getOffset(localDateTime)).toEpochMilli();
    }
}
