package android.widget;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.icu.text.DateTimePatternGenerator;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;

import com.android.internal.R;
import com.android.internal.util.Preconditions;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RemoteViews.RemoteView
public class TextClock extends TextView {

    @Deprecated public static final CharSequence DEFAULT_FORMAT_12_HOUR = "h:mm a";

    @Deprecated public static final CharSequence DEFAULT_FORMAT_24_HOUR = "H:mm";
    private ClockEventDelegate mClockEventDelegate;
    private CharSequence mDescFormat;
    private CharSequence mDescFormat12;
    private CharSequence mDescFormat24;

    @ViewDebug.ExportedProperty private CharSequence mFormat;
    private CharSequence mFormat12;
    private CharSequence mFormat24;
    private ContentObserver mFormatChangeObserver;

    @ViewDebug.ExportedProperty private boolean mHasSeconds;
    private final BroadcastReceiver mIntentReceiver;
    private boolean mRegistered;
    private boolean mShouldChooseFormat;
    private boolean mShouldRunTicker;
    private boolean mShowCurrentUserTime;
    private boolean mStopTicking;
    private final Runnable mTicker;
    private Calendar mTime;
    private String mTimeZone;

    public final class InspectionCompanion
            implements android.view.inspector.InspectionCompanion<TextClock> {
        private int mFormat12HourId;
        private int mFormat24HourId;
        private int mIs24HourModeEnabledId;
        private boolean mPropertiesMapped = false;
        private int mTimeZoneId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mFormat12HourId = propertyMapper.mapObject("format12Hour", 16843722);
            this.mFormat24HourId = propertyMapper.mapObject("format24Hour", 16843723);
            this.mIs24HourModeEnabledId = propertyMapper.mapBoolean("is24HourModeEnabled", 0);
            this.mTimeZoneId = propertyMapper.mapObject("timeZone", 16843724);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(TextClock node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mFormat12HourId, node.getFormat12Hour());
            propertyReader.readObject(this.mFormat24HourId, node.getFormat24Hour());
            propertyReader.readBoolean(this.mIs24HourModeEnabledId, node.is24HourModeEnabled());
            propertyReader.readObject(this.mTimeZoneId, node.getTimeZone());
        }
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            if (TextClock.this.mShouldRunTicker) {
                TextClock.this.chooseFormat();
            } else if (!TextClock.this.mShouldChooseFormat) {
                TextClock.this.mShouldChooseFormat = true;
            }
            TextClock.this.onTimeChanged();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            if (TextClock.this.mShouldRunTicker) {
                TextClock.this.chooseFormat();
            } else if (!TextClock.this.mShouldChooseFormat) {
                TextClock.this.mShouldChooseFormat = true;
            }
            TextClock.this.onTimeChanged();
        }
    }

    public TextClock(Context context) {
        super(context);
        this.mIntentReceiver =
                new BroadcastReceiver() { // from class: android.widget.TextClock.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        if (TextClock.this.mStopTicking) {
                            return;
                        }
                        if (TextClock.this.mTimeZone == null
                                && Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                            String timeZone = intent.getStringExtra(Intent.EXTRA_TIMEZONE);
                            TextClock.this.createTime(timeZone);
                        } else if (!TextClock.this.mShouldRunTicker
                                && Intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {
                            return;
                        }
                        TextClock.this.onTimeChanged();
                    }
                };
        this.mTicker =
                new Runnable() { // from class: android.widget.TextClock.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ZonedDateTime nextTick;
                        TextClock.this.removeCallbacks(this);
                        if (TextClock.this.mStopTicking || !TextClock.this.mShouldRunTicker) {
                            return;
                        }
                        TextClock.this.onTimeChanged();
                        Instant now = TextClock.this.mTime.toInstant();
                        ZoneId zone = TextClock.this.mTime.getTimeZone().toZoneId();
                        if (TextClock.this.mHasSeconds) {
                            nextTick = now.atZone(zone).plusSeconds(1L).withNano(0);
                        } else {
                            ZonedDateTime nextTick2 = now.atZone(zone);
                            nextTick = nextTick2.plusMinutes(1L).withSecond(0).withNano(0);
                        }
                        long millisUntilNextTick =
                                Duration.between(now, nextTick.toInstant()).toMillis();
                        if (millisUntilNextTick <= 0) {
                            millisUntilNextTick = 1000;
                        }
                        TextClock.this.postDelayed(this, millisUntilNextTick);
                    }
                };
        init();
    }

    public TextClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextClock(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mIntentReceiver =
                new BroadcastReceiver() { // from class: android.widget.TextClock.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        if (TextClock.this.mStopTicking) {
                            return;
                        }
                        if (TextClock.this.mTimeZone == null
                                && Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                            String timeZone = intent.getStringExtra(Intent.EXTRA_TIMEZONE);
                            TextClock.this.createTime(timeZone);
                        } else if (!TextClock.this.mShouldRunTicker
                                && Intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {
                            return;
                        }
                        TextClock.this.onTimeChanged();
                    }
                };
        this.mTicker =
                new Runnable() { // from class: android.widget.TextClock.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ZonedDateTime nextTick;
                        TextClock.this.removeCallbacks(this);
                        if (TextClock.this.mStopTicking || !TextClock.this.mShouldRunTicker) {
                            return;
                        }
                        TextClock.this.onTimeChanged();
                        Instant now = TextClock.this.mTime.toInstant();
                        ZoneId zone = TextClock.this.mTime.getTimeZone().toZoneId();
                        if (TextClock.this.mHasSeconds) {
                            nextTick = now.atZone(zone).plusSeconds(1L).withNano(0);
                        } else {
                            ZonedDateTime nextTick2 = now.atZone(zone);
                            nextTick = nextTick2.plusMinutes(1L).withSecond(0).withNano(0);
                        }
                        long millisUntilNextTick =
                                Duration.between(now, nextTick.toInstant()).toMillis();
                        if (millisUntilNextTick <= 0) {
                            millisUntilNextTick = 1000;
                        }
                        TextClock.this.postDelayed(this, millisUntilNextTick);
                    }
                };
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.TextClock, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(
                context, R.styleable.TextClock, attrs, a, defStyleAttr, defStyleRes);
        try {
            this.mFormat12 = a.getText(0);
            this.mFormat24 = a.getText(1);
            this.mTimeZone = a.getString(2);
            a.recycle();
            init();
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    private void init() {
        if (this.mFormat12 == null) {
            this.mFormat12 = getBestDateTimePattern("hm");
        }
        if (this.mFormat24 == null) {
            this.mFormat24 = getBestDateTimePattern("Hm");
        }
        this.mClockEventDelegate = new ClockEventDelegate(getContext());
        createTime(this.mTimeZone);
        chooseFormat();
    }

    public void createTime(String timeZone) {
        if (timeZone != null) {
            this.mTime = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        } else {
            this.mTime = Calendar.getInstance();
        }
    }

    @ViewDebug.ExportedProperty
    public CharSequence getFormat12Hour() {
        return this.mFormat12;
    }

    @RemotableViewMethod
    public void setFormat12Hour(CharSequence format) {
        this.mFormat12 = format;
        chooseFormat();
        onTimeChanged();
    }

    public void setContentDescriptionFormat12Hour(CharSequence format) {
        this.mDescFormat12 = format;
        chooseFormat();
        onTimeChanged();
    }

    @ViewDebug.ExportedProperty
    public CharSequence getFormat24Hour() {
        return this.mFormat24;
    }

    @RemotableViewMethod
    public void setFormat24Hour(CharSequence format) {
        this.mFormat24 = format;
        chooseFormat();
        onTimeChanged();
    }

    public void setContentDescriptionFormat24Hour(CharSequence format) {
        this.mDescFormat24 = format;
        chooseFormat();
        onTimeChanged();
    }

    public void setShowCurrentUserTime(boolean showCurrentUserTime) {
        this.mShowCurrentUserTime = showCurrentUserTime;
        chooseFormat();
        onTimeChanged();
        unregisterObserver();
        registerObserver();
    }

    public void setClockEventDelegate(ClockEventDelegate delegate) {
        Preconditions.checkState(!this.mRegistered, "Clock events already registered");
        this.mClockEventDelegate = delegate;
    }

    public void refreshTime() {
        onTimeChanged();
        invalidate();
    }

    public boolean is24HourModeEnabled() {
        if (this.mShowCurrentUserTime) {
            return DateFormat.is24HourFormat(getContext(), ActivityManager.getCurrentUser());
        }
        return DateFormat.is24HourFormat(getContext());
    }

    public String getTimeZone() {
        return this.mTimeZone;
    }

    @RemotableViewMethod
    public void setTimeZone(String timeZone) {
        this.mTimeZone = timeZone;
        createTime(timeZone);
        onTimeChanged();
    }

    public CharSequence getFormat() {
        return this.mFormat;
    }

    public void chooseFormat() {
        boolean format24Requested = is24HourModeEnabled();
        if (format24Requested) {
            this.mFormat = abc(this.mFormat24, this.mFormat12, getBestDateTimePattern("Hm"));
            this.mDescFormat = abc(this.mDescFormat24, this.mDescFormat12, this.mFormat);
        } else {
            this.mFormat = abc(this.mFormat12, this.mFormat24, getBestDateTimePattern("hm"));
            this.mDescFormat = abc(this.mDescFormat12, this.mDescFormat24, this.mFormat);
        }
        boolean hadSeconds = this.mHasSeconds;
        this.mHasSeconds = DateFormat.hasSeconds(this.mFormat);
        if (this.mShouldRunTicker && hadSeconds != this.mHasSeconds) {
            this.mTicker.run();
        }
    }

    private String getBestDateTimePattern(String skeleton) {
        DateTimePatternGenerator dtpg =
                DateTimePatternGenerator.getInstance(
                        getContext().getResources().getConfiguration().locale);
        return dtpg.getBestPattern(skeleton);
    }

    private static CharSequence abc(CharSequence a, CharSequence b, CharSequence c) {
        return a == null ? b == null ? c : b : a;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mRegistered) {
            this.mRegistered = true;
            this.mClockEventDelegate.registerTimeChangeReceiver(this.mIntentReceiver, getHandler());
            registerObserver();
            createTime(this.mTimeZone);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (!this.mShouldRunTicker && isVisible) {
            this.mShouldRunTicker = true;
            if (this.mShouldChooseFormat) {
                this.mShouldChooseFormat = false;
                chooseFormat();
            }
            this.mTicker.run();
            return;
        }
        if (this.mShouldRunTicker && !isVisible) {
            this.mShouldRunTicker = false;
            removeCallbacks(this.mTicker);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mRegistered) {
            this.mClockEventDelegate.unregisterTimeChangeReceiver(this.mIntentReceiver);
            unregisterObserver();
            this.mRegistered = false;
        }
    }

    public void disableClockTick() {
        this.mStopTicking = true;
    }

    private void registerObserver() {
        if (this.mRegistered) {
            if (this.mFormatChangeObserver == null) {
                this.mFormatChangeObserver = new FormatChangeObserver(getHandler());
            }
            int userHandle = this.mShowCurrentUserTime ? -1 : UserHandle.myUserId();
            this.mClockEventDelegate.registerFormatChangeObserver(
                    this.mFormatChangeObserver, userHandle);
        }
    }

    private void unregisterObserver() {
        if (this.mFormatChangeObserver != null) {
            this.mClockEventDelegate.unregisterFormatChangeObserver(this.mFormatChangeObserver);
        }
    }

    public void onTimeChanged() {
        this.mTime.setTimeInMillis(System.currentTimeMillis());
        if (!TextUtils.isEmpty(this.mFormat) && this.mFormat.toString().contains("per")) {
            lambda$setTextAsync$0(calcPersiCalendar(this.mTime));
        } else {
            lambda$setTextAsync$0(DateFormat.format(this.mFormat, this.mTime));
        }
        setContentDescription(DateFormat.format(this.mDescFormat, this.mTime));
    }

    @Override // android.widget.TextView, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder stream) {
        super.encodeProperties(stream);
        CharSequence s = getFormat12Hour();
        stream.addProperty("format12Hour", s == null ? null : s.toString());
        CharSequence s2 = getFormat24Hour();
        stream.addProperty("format24Hour", s2 == null ? null : s2.toString());
        stream.addProperty(
                Telephony.CellBroadcasts.MESSAGE_FORMAT,
                this.mFormat != null ? this.mFormat.toString() : null);
        stream.addProperty("hasSeconds", this.mHasSeconds);
    }

    private String calcPersiCalendar(Calendar calendar) {
        int ld;
        int month;
        int date;
        int date2;
        int date3;
        int date4;
        String monthStr;
        int ld2;
        int month2;
        Calendar adjustPrimaryCalendarStart = Calendar.getInstance();
        Calendar adjustPrimaryCalendarEnd = Calendar.getInstance();
        Calendar adjustSecondaryCalendarStart = Calendar.getInstance();
        Calendar adjustSecondaryCalendarEnd = Calendar.getInstance();
        adjustPrimaryCalendarStart.set(2029, 2, 19);
        adjustPrimaryCalendarEnd.set(2030, 2, 20);
        adjustSecondaryCalendarStart.set(2033, 2, 19);
        adjustSecondaryCalendarEnd.set(2034, 2, 20);
        boolean isFakeCalendar = false;
        if ((calendar.after(adjustPrimaryCalendarStart)
                        && calendar.before(adjustPrimaryCalendarEnd))
                || (calendar.after(adjustSecondaryCalendarStart)
                        && calendar.before(adjustSecondaryCalendarEnd))) {
            calendar.add(5, 1);
            isFakeCalendar = true;
        }
        Date today = new Date(calendar.getTimeInMillis());
        int todayYear = today.getYear() + 1900;
        int todayMonth = today.getMonth() + 1;
        int todayDate = today.getDate();
        int[] cal1 = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int[] cal2 = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
        if (todayYear % 4 != 0) {
            int date5 = cal1[todayMonth - 1] + todayDate;
            if (date5 > 79) {
                int date6 = date5 - 79;
                if (date6 <= 186) {
                    switch (date6 % 31) {
                        case 0:
                            month = date6 / 31;
                            date2 = 31;
                            break;
                        default:
                            month = (date6 / 31) + 1;
                            date2 = date6 % 31;
                            break;
                    }
                    int i = todayYear - 621;
                } else {
                    int date7 = date6 - 186;
                    switch (date7 % 30) {
                        case 0:
                            int month3 = date7 / 30;
                            month = month3 + 6;
                            date2 = 30;
                            break;
                        default:
                            month = (date7 / 30) + 7;
                            date2 = date7 % 30;
                            break;
                    }
                    int i2 = todayYear - 621;
                }
            } else {
                if (todayYear > 1996 && todayYear % 4 == 1) {
                    ld2 = 11;
                } else {
                    ld2 = 10;
                }
                int date8 = date5 + ld2;
                switch (date8 % 30) {
                    case 0:
                        int month4 = date8 / 30;
                        month2 = month4 + 9;
                        date2 = 30;
                        break;
                    default:
                        month2 = (date8 / 30) + 10;
                        date2 = date8 % 30;
                        break;
                }
                int i3 = todayYear - 622;
                month = month2;
            }
        } else {
            int year = todayMonth - 1;
            int date9 = cal2[year] + todayDate;
            if (todayYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (date9 > ld) {
                int date10 = date9 - ld;
                if (date10 <= 186) {
                    switch (date10 % 31) {
                        case 0:
                            month = date10 / 31;
                            date4 = 31;
                            break;
                        default:
                            month = (date10 / 31) + 1;
                            date4 = date10 % 31;
                            break;
                    }
                    int i4 = todayYear - 621;
                    date2 = date4;
                } else {
                    int date11 = date10 - 186;
                    switch (date11 % 30) {
                        case 0:
                            int month5 = date11 / 30;
                            month = month5 + 6;
                            date3 = 30;
                            break;
                        default:
                            month = (date11 / 30) + 7;
                            date3 = date11 % 30;
                            break;
                    }
                    int i5 = todayYear - 621;
                    date2 = date3;
                }
            } else {
                int date12 = date9 + 10;
                switch (date12 % 30) {
                    case 0:
                        int month6 = date12 / 30;
                        month = month6 + 9;
                        date = 30;
                        break;
                    default:
                        month = (date12 / 30) + 10;
                        date = date12 % 30;
                        break;
                }
                int i6 = todayYear - 622;
                date2 = date;
            }
        }
        if (!isFakeCalendar
                && ((todayYear == 2030 || todayYear == 2034)
                        && todayMonth == 3
                        && todayDate == 20)) {
            date2++;
        }
        boolean isLanguageEnglish = false;
        if (!TextUtils.isEmpty(this.mFormat) && this.mFormat.toString().contains("eng")) {
            isLanguageEnglish = true;
        }
        switch (month) {
            case 1:
                monthStr = isLanguageEnglish ? "Farvardin" : "?ر?رد??";
                break;
            case 2:
                monthStr = isLanguageEnglish ? "Ordibehesht" : "ارد?ب?شت";
                break;
            case 3:
                monthStr = isLanguageEnglish ? "Khordad" : "خرداد";
                break;
            case 4:
                monthStr = isLanguageEnglish ? "Tir" : "ت?ر";
                break;
            case 5:
                monthStr = isLanguageEnglish ? "Mordad" : "?رداد";
                break;
            case 6:
                monthStr = isLanguageEnglish ? "Shahrivar" : "ش?ر??ر";
                break;
            case 7:
                monthStr = isLanguageEnglish ? "Mehr" : "??ر";
                break;
            case 8:
                monthStr = isLanguageEnglish ? "Aban" : "آبا?";
                break;
            case 9:
                monthStr = isLanguageEnglish ? "Azar" : "آذر";
                break;
            case 10:
                monthStr = isLanguageEnglish ? "Dey" : "د?";
                break;
            case 11:
                monthStr = isLanguageEnglish ? "Bahman" : "ب???";
                break;
            case 12:
                monthStr = isLanguageEnglish ? "Esfand" : "اس??د";
                break;
            default:
                monthStr = "";
                break;
        }
        return NavigationBarInflaterView.KEY_CODE_START
                + String.format("%d", Integer.valueOf(date2))
                + " "
                + monthStr
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static class ClockEventDelegate {
        private final Context mContext;

        public ClockEventDelegate(Context context) {
            this.mContext = context;
        }

        public void registerTimeChangeReceiver(BroadcastReceiver receiver, Handler handler) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            this.mContext.registerReceiverAsUser(
                    receiver, Process.myUserHandle(), filter, null, handler);
        }

        public void unregisterTimeChangeReceiver(BroadcastReceiver receiver) {
            this.mContext.unregisterReceiver(receiver);
        }

        public void registerFormatChangeObserver(ContentObserver observer, int userHandle) {
            Uri uri = Settings.System.getUriFor(Settings.System.TIME_12_24);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(uri, true, observer, userHandle);
        }

        public void unregisterFormatChangeObserver(ContentObserver observer) {
            this.mContext.getContentResolver().unregisterContentObserver(observer);
        }
    }
}
