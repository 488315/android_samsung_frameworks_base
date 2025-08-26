package com.android.systemui.shared.clocks;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Calendar;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes3.dex */
public final class DigitalTimespecHandler extends TimespecHandler {
    public DateFormat contentDescriptionFormat;
    public DateFormat dateFormat;
    public boolean is24Hr;
    public final String timeFormat;
    public final DigitalTimespec timespec;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DigitalTimespec.values().length];
            try {
                iArr[DigitalTimespec.TIME_FULL_FORMAT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DigitalTimespec.FIRST_DIGIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DigitalTimespec.SECOND_DIGIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DigitalTimespec.DIGIT_PAIR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ DigitalTimespecHandler(DigitalTimespec digitalTimespec, String str, Calendar calendar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(digitalTimespec, str, (i & 4) != 0 ? Calendar.getInstance() : calendar);
    }

    public final void applyPattern() {
        String str = this.timeFormat;
        String strReplace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(str, "hh", "h"), "h", "HH");
        if (this.is24Hr) {
            str = strReplace$default;
        }
        ((SimpleDateFormat) this.dateFormat).applyPattern(str);
        DateFormat dateFormat = this.contentDescriptionFormat;
        SimpleDateFormat simpleDateFormat = dateFormat instanceof SimpleDateFormat ? (SimpleDateFormat) dateFormat : null;
        if (simpleDateFormat != null) {
            simpleDateFormat.applyPattern(this.is24Hr ? "HH:mm" : "hh:mm");
        }
    }

    public final String getContentDescription() {
        if (WhenMappings.$EnumSwitchMapping$0[this.timespec.ordinal()] != 1) {
            return null;
        }
        DateFormat dateFormat = this.contentDescriptionFormat;
        return String.valueOf(dateFormat != null ? dateFormat.format(this.cal.getTime()) : null);
    }

    public final void onTimeZoneChanged() {
        this.dateFormat.setTimeZone(TimeZone.getTimeZone(this.cal.getTimeZone().getID()));
        DateFormat dateFormat = this.contentDescriptionFormat;
        if (dateFormat != null) {
            dateFormat.setTimeZone(TimeZone.getTimeZone(this.cal.getTimeZone().getID()));
        }
        applyPattern();
    }

    public DigitalTimespecHandler(DigitalTimespec digitalTimespec, String str, Calendar calendar) {
        DateFormat instanceForSkeleton;
        super(calendar);
        this.timespec = digitalTimespec;
        this.timeFormat = str;
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            instanceForSkeleton = new SimpleDateFormat(str, str, ULocale.forLocale(locale));
        } else {
            instanceForSkeleton = SimpleDateFormat.getInstanceForSkeleton(str, locale);
        }
        this.dateFormat = instanceForSkeleton;
        this.contentDescriptionFormat = WhenMappings.$EnumSwitchMapping$0[digitalTimespec.ordinal()] == 1 ? SimpleDateFormat.getInstanceForSkeleton("hh:mm", Locale.getDefault()) : null;
        applyPattern();
    }
}
