package android.widget;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
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
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class TextClock extends TextView {

    @Deprecated
    public static final CharSequence DEFAULT_FORMAT_12_HOUR = "h:mm a";

    @Deprecated
    public static final CharSequence DEFAULT_FORMAT_24_HOUR = "H:mm";
    private ClockEventDelegate mClockEventDelegate;
    private CharSequence mDescFormat;
    private CharSequence mDescFormat12;
    private CharSequence mDescFormat24;

    @ViewDebug.ExportedProperty
    private CharSequence mFormat;
    private CharSequence mFormat12;
    private CharSequence mFormat24;
    private ContentObserver mFormatChangeObserver;

    @ViewDebug.ExportedProperty
    private boolean mHasSeconds;
    private final BroadcastReceiver mIntentReceiver;
    private boolean mRegisterActionForComplicationWidget;
    private boolean mRegistered;
    private boolean mShouldChooseFormat;
    private boolean mShouldRunTicker;
    private boolean mShowCurrentUserTime;
    private boolean mStopTicking;
    private final Runnable mTicker;
    private Calendar mTime;
    private String mTimeZone;

    private static CharSequence abc(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return charSequence == null ? charSequence2 == null ? charSequence3 : charSequence2 : charSequence;
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<TextClock> {
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
        public void readProperties(TextClock textClock, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mFormat12HourId, textClock.getFormat12Hour());
            propertyReader.readObject(this.mFormat24HourId, textClock.getFormat24Hour());
            propertyReader.readBoolean(this.mIs24HourModeEnabledId, textClock.is24HourModeEnabled());
            propertyReader.readObject(this.mTimeZoneId, textClock.getTimeZone());
        }
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (TextClock.this.mShouldRunTicker) {
                TextClock.this.chooseFormat();
            } else if (!TextClock.this.mShouldChooseFormat) {
                TextClock.this.mShouldChooseFormat = true;
            }
            TextClock.this.onTimeChanged();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
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
        this.mIntentReceiver = new BroadcastReceiver() { // from class: android.widget.TextClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (TextClock.this.mStopTicking) {
                    return;
                }
                if (TextClock.this.mTimeZone == null && Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                    TextClock.this.createTime(intent.getStringExtra(Intent.EXTRA_TIMEZONE));
                } else if (!TextClock.this.mShouldRunTicker && (Intent.ACTION_TIME_TICK.equals(intent.getAction()) || Intent.ACTION_TIME_CHANGED.equals(intent.getAction()) || Intent.ACTION_SCREEN_ON.equals(intent.getAction()))) {
                    return;
                }
                TextClock.this.onTimeChanged();
            }
        };
        this.mTicker = new Runnable() { // from class: android.widget.TextClock.2
            @Override // java.lang.Runnable
            public void run() {
                ZonedDateTime zonedDateTimeWithNano;
                TextClock.this.removeCallbacks(this);
                if (TextClock.this.mStopTicking || !TextClock.this.mShouldRunTicker) {
                    return;
                }
                TextClock.this.onTimeChanged();
                Instant instant = TextClock.this.mTime.toInstant();
                ZoneId zoneId = TextClock.this.mTime.getTimeZone().toZoneId();
                if (TextClock.this.mHasSeconds) {
                    zonedDateTimeWithNano = instant.atZone(zoneId).plusSeconds(1L).withNano(0);
                } else {
                    zonedDateTimeWithNano = instant.atZone(zoneId).plusMinutes(1L).withSecond(0).withNano(0);
                }
                long millis = Duration.between(instant, zonedDateTimeWithNano.toInstant()).toMillis();
                if (millis <= 0) {
                    millis = 1000;
                }
                TextClock.this.postDelayed(this, millis);
            }
        };
        init();
    }

    public TextClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextClock(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TextClock(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIntentReceiver = new BroadcastReceiver() { // from class: android.widget.TextClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (TextClock.this.mStopTicking) {
                    return;
                }
                if (TextClock.this.mTimeZone == null && Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                    TextClock.this.createTime(intent.getStringExtra(Intent.EXTRA_TIMEZONE));
                } else if (!TextClock.this.mShouldRunTicker && (Intent.ACTION_TIME_TICK.equals(intent.getAction()) || Intent.ACTION_TIME_CHANGED.equals(intent.getAction()) || Intent.ACTION_SCREEN_ON.equals(intent.getAction()))) {
                    return;
                }
                TextClock.this.onTimeChanged();
            }
        };
        this.mTicker = new Runnable() { // from class: android.widget.TextClock.2
            @Override // java.lang.Runnable
            public void run() {
                ZonedDateTime zonedDateTimeWithNano;
                TextClock.this.removeCallbacks(this);
                if (TextClock.this.mStopTicking || !TextClock.this.mShouldRunTicker) {
                    return;
                }
                TextClock.this.onTimeChanged();
                Instant instant = TextClock.this.mTime.toInstant();
                ZoneId zoneId = TextClock.this.mTime.getTimeZone().toZoneId();
                if (TextClock.this.mHasSeconds) {
                    zonedDateTimeWithNano = instant.atZone(zoneId).plusSeconds(1L).withNano(0);
                } else {
                    zonedDateTimeWithNano = instant.atZone(zoneId).plusMinutes(1L).withSecond(0).withNano(0);
                }
                long millis = Duration.between(instant, zonedDateTimeWithNano.toInstant()).toMillis();
                if (millis <= 0) {
                    millis = 1000;
                }
                TextClock.this.postDelayed(this, millis);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TextClock, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.TextClock, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        try {
            this.mFormat12 = typedArrayObtainStyledAttributes.getText(0);
            this.mFormat24 = typedArrayObtainStyledAttributes.getText(1);
            this.mTimeZone = typedArrayObtainStyledAttributes.getString(2);
            typedArrayObtainStyledAttributes.recycle();
            init();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void createTime(String str) {
        TimeZone timeZone;
        if (str == null) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = TimeZone.getTimeZone(str);
            try {
                timeZone.toZoneId();
            } catch (DateTimeException unused) {
                timeZone = TimeZone.getDefault();
                this.mTimeZone = timeZone.getID();
            }
        }
        this.mTime = Calendar.getInstance(timeZone);
    }

    @ViewDebug.ExportedProperty
    public CharSequence getFormat12Hour() {
        return this.mFormat12;
    }

    @RemotableViewMethod
    public void setFormat12Hour(CharSequence charSequence) {
        this.mFormat12 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    public void setContentDescriptionFormat12Hour(CharSequence charSequence) {
        this.mDescFormat12 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    @ViewDebug.ExportedProperty
    public CharSequence getFormat24Hour() {
        return this.mFormat24;
    }

    @RemotableViewMethod
    public void setFormat24Hour(CharSequence charSequence) {
        this.mFormat24 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    public void setContentDescriptionFormat24Hour(CharSequence charSequence) {
        this.mDescFormat24 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    public void setShowCurrentUserTime(boolean z) {
        this.mShowCurrentUserTime = z;
        chooseFormat();
        onTimeChanged();
        unregisterObserver();
        registerObserver();
    }

    public void setClockEventDelegate(ClockEventDelegate clockEventDelegate) {
        Preconditions.checkState(!this.mRegistered, "Clock events already registered");
        this.mClockEventDelegate = clockEventDelegate;
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
    public void setTimeZone(String str) {
        this.mTimeZone = str;
        createTime(str);
        onTimeChanged();
    }

    public CharSequence getFormat() {
        return this.mFormat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseFormat() {
        if (is24HourModeEnabled()) {
            CharSequence charSequenceAbc = abc(this.mFormat24, this.mFormat12, getBestDateTimePattern("Hm"));
            this.mFormat = charSequenceAbc;
            this.mDescFormat = abc(this.mDescFormat24, this.mDescFormat12, charSequenceAbc);
        } else {
            CharSequence charSequenceAbc2 = abc(this.mFormat12, this.mFormat24, getBestDateTimePattern("hm"));
            this.mFormat = charSequenceAbc2;
            this.mDescFormat = abc(this.mDescFormat12, this.mDescFormat24, charSequenceAbc2);
        }
        boolean z = this.mHasSeconds;
        boolean zHasSeconds = DateFormat.hasSeconds(this.mFormat);
        this.mHasSeconds = zHasSeconds;
        if (!this.mShouldRunTicker || z == zHasSeconds) {
            return;
        }
        this.mTicker.run();
    }

    private String getBestDateTimePattern(String str) {
        return DateTimePatternGenerator.getInstance(getContext().getResources().getConfiguration().locale).getBestPattern(str);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mRegistered) {
            return;
        }
        this.mRegistered = true;
        if (this.mRegisterActionForComplicationWidget) {
            this.mClockEventDelegate.registerComplicationTimeChangeReceiver(this.mIntentReceiver, getHandler());
        } else {
            this.mClockEventDelegate.registerTimeChangeReceiver(this.mIntentReceiver, getHandler());
        }
        registerObserver();
        createTime(this.mTimeZone);
    }

    @Override // android.widget.TextView, android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        boolean z2 = this.mShouldRunTicker;
        if (z2 || !z) {
            if (!z2 || z) {
                return;
            }
            this.mShouldRunTicker = false;
            removeCallbacks(this.mTicker);
            return;
        }
        this.mShouldRunTicker = true;
        if (this.mShouldChooseFormat) {
            this.mShouldChooseFormat = false;
            chooseFormat();
        }
        this.mTicker.run();
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
            this.mClockEventDelegate.registerFormatChangeObserver(this.mFormatChangeObserver, this.mShowCurrentUserTime ? -1 : UserHandle.myUserId());
        }
    }

    private void unregisterObserver() {
        ContentObserver contentObserver = this.mFormatChangeObserver;
        if (contentObserver != null) {
            this.mClockEventDelegate.unregisterFormatChangeObserver(contentObserver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
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
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
        super.encodeProperties(viewHierarchyEncoder);
        CharSequence format12Hour = getFormat12Hour();
        viewHierarchyEncoder.addProperty("format12Hour", format12Hour == null ? null : format12Hour.toString());
        CharSequence format24Hour = getFormat24Hour();
        viewHierarchyEncoder.addProperty("format24Hour", format24Hour == null ? null : format24Hour.toString());
        CharSequence charSequence = this.mFormat;
        viewHierarchyEncoder.addProperty(Telephony.CellBroadcasts.MESSAGE_FORMAT, charSequence != null ? charSequence.toString() : null);
        viewHierarchyEncoder.addProperty("hasSeconds", this.mHasSeconds);
    }

    private String calcPersiCalendar(Calendar calendar) {
        boolean z;
        int i;
        int i2;
        int i3;
        String str;
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        Calendar calendar4 = Calendar.getInstance();
        Calendar calendar5 = Calendar.getInstance();
        calendar2.set(2029, 2, 19);
        calendar3.set(2030, 2, 20);
        calendar4.set(2033, 2, 19);
        calendar5.set(2034, 2, 20);
        if ((calendar.after(calendar2) && calendar.before(calendar3)) || (calendar.after(calendar4) && calendar.before(calendar5))) {
            calendar.add(5, 1);
            z = true;
        } else {
            z = false;
        }
        Date date = new Date(calendar.getTimeInMillis());
        int year = date.getYear() + 1900;
        int month = date.getMonth();
        int i4 = month + 1;
        int date2 = date.getDate();
        int[] iArr = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int[] iArr2 = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
        int i5 = year % 4;
        int i6 = 31;
        int i7 = 30;
        if (i5 != 0) {
            int i8 = iArr[month] + date2;
            if (i8 > 79) {
                int i9 = i8 - 79;
                if (i9 <= 186) {
                    int i10 = i9 % 31;
                    if (i10 == 0) {
                        i3 = i9 / 31;
                    } else {
                        i3 = (i9 / 31) + 1;
                        i6 = i10;
                    }
                } else {
                    int i11 = i8 - 265;
                    int i12 = i11 % 30;
                    if (i12 == 0) {
                        i = (i11 / 30) + 6;
                    } else {
                        i = (i11 / 30) + 7;
                        i7 = i12;
                    }
                }
            } else {
                int i13 = i8 + ((year <= 1996 || i5 != 1) ? 10 : 11);
                int i14 = i13 % 30;
                if (i14 == 0) {
                    i = (i13 / 30) + 9;
                } else {
                    i = (i13 / 30) + 10;
                    i7 = i14;
                }
            }
            i3 = i;
            i6 = i7;
        } else {
            int i15 = iArr2[month] + date2;
            int i16 = year < 1996 ? 80 : 79;
            if (i15 > i16) {
                int i17 = i15 - i16;
                if (i17 <= 186) {
                    int i18 = i17 % 31;
                    if (i18 == 0) {
                        i2 = i17 / 31;
                    } else {
                        i2 = (i17 / 31) + 1;
                        i6 = i18;
                    }
                    i3 = i2;
                } else {
                    int i19 = i17 - 186;
                    int i20 = i19 % 30;
                    if (i20 == 0) {
                        i = (i19 / 30) + 6;
                    } else {
                        i = (i19 / 30) + 7;
                        i7 = i20;
                    }
                }
            } else {
                int i21 = i15 + 10;
                int i22 = i21 % 30;
                if (i22 == 0) {
                    i = (i21 / 30) + 9;
                } else {
                    i = (i21 / 30) + 10;
                    i7 = i22;
                }
            }
            i3 = i;
            i6 = i7;
        }
        if (!z && ((year == 2030 || year == 2034) && i4 == 3 && date2 == 20)) {
            i6++;
        }
        boolean z2 = !TextUtils.isEmpty(this.mFormat) && this.mFormat.toString().contains("eng");
        switch (i3) {
            case 1:
                if (!z2) {
                    str = "?ر?رد??";
                    break;
                } else {
                    str = "Farvardin";
                    break;
                }
            case 2:
                if (!z2) {
                    str = "ارد?ب?شت";
                    break;
                } else {
                    str = "Ordibehesht";
                    break;
                }
            case 3:
                if (!z2) {
                    str = "خرداد";
                    break;
                } else {
                    str = "Khordad";
                    break;
                }
            case 4:
                if (!z2) {
                    str = "ت?ر";
                    break;
                } else {
                    str = "Tir";
                    break;
                }
            case 5:
                if (!z2) {
                    str = "?رداد";
                    break;
                } else {
                    str = "Mordad";
                    break;
                }
            case 6:
                if (!z2) {
                    str = "ش?ر??ر";
                    break;
                } else {
                    str = "Shahrivar";
                    break;
                }
            case 7:
                if (!z2) {
                    str = "??ر";
                    break;
                } else {
                    str = "Mehr";
                    break;
                }
            case 8:
                if (!z2) {
                    str = "آبا?";
                    break;
                } else {
                    str = "Aban";
                    break;
                }
            case 9:
                if (!z2) {
                    str = "آذر";
                    break;
                } else {
                    str = "Azar";
                    break;
                }
            case 10:
                if (!z2) {
                    str = "د?";
                    break;
                } else {
                    str = "Dey";
                    break;
                }
            case 11:
                if (!z2) {
                    str = "ب???";
                    break;
                } else {
                    str = "Bahman";
                    break;
                }
            case 12:
                if (!z2) {
                    str = "اس??د";
                    break;
                } else {
                    str = "Esfand";
                    break;
                }
            default:
                str = "";
                break;
        }
        return NavigationBarInflaterView.KEY_CODE_START + String.format("%d", Integer.valueOf(i6)) + " " + str + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static class ClockEventDelegate {
        private final Context mContext;

        public ClockEventDelegate(Context context) {
            this.mContext = context;
        }

        public void registerTimeChangeReceiver(BroadcastReceiver broadcastReceiver, Handler handler) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            this.mContext.registerReceiverAsUser(broadcastReceiver, Process.myUserHandle(), intentFilter, null, handler);
        }

        public void registerComplicationTimeChangeReceiver(BroadcastReceiver broadcastReceiver, Handler handler) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_TIME_TICK);
            this.mContext.registerReceiverAsUser(broadcastReceiver, Process.myUserHandle(), intentFilter, null, handler);
        }

        public void unregisterTimeChangeReceiver(BroadcastReceiver broadcastReceiver) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }

        public void registerFormatChangeObserver(ContentObserver contentObserver, int i) {
            this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.TIME_12_24), true, contentObserver, i);
        }

        public void unregisterFormatChangeObserver(ContentObserver contentObserver) {
            this.mContext.getContentResolver().unregisterContentObserver(contentObserver);
        }
    }

    @RemotableViewMethod
    public void hidden_semRegisterActionForComplicationWidget(boolean z) {
        if (this.mRegisterActionForComplicationWidget != z) {
            this.mRegisterActionForComplicationWidget = z;
            if (this.mRegistered) {
                this.mClockEventDelegate.unregisterTimeChangeReceiver(this.mIntentReceiver);
            }
            this.mClockEventDelegate.registerComplicationTimeChangeReceiver(this.mIntentReceiver, getHandler());
            this.mRegistered = true;
        }
    }
}
