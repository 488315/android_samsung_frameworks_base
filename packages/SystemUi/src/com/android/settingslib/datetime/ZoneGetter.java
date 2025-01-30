package com.android.settingslib.datetime;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.icu.text.TimeZoneFormat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.Log;
import androidx.core.text.BidiFormatter;
import androidx.core.text.TextDirectionHeuristicsCompat;
import com.android.i18n.timezone.CountryTimeZones;
import com.android.i18n.timezone.TimeZoneFinder;
import com.android.systemui.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ZoneGetter {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ZoneGetterData {
        public final CharSequence[] gmtOffsetTexts;
        public final String[] olsonIdsToDisplay;
        public final TimeZone[] timeZones;
        public final int zoneCount;

        public ZoneGetterData(Context context) {
            String substring;
            String str;
            TimeZoneFormat.GMTOffsetPatternType gMTOffsetPatternType;
            String str2;
            int i;
            int i2;
            TimeZoneFormat timeZoneFormat;
            ArrayList arrayList;
            int i3;
            String str3;
            int i4;
            Locale locale = context.getResources().getConfiguration().locale;
            TimeZoneFormat timeZoneFormat2 = TimeZoneFormat.getInstance(locale);
            Date date = new Date();
            ArrayList arrayList2 = new ArrayList();
            int i5 = 0;
            int i6 = 1;
            try {
                XmlResourceParser xml = context.getResources().getXml(R.xml.timezones);
                do {
                    try {
                    } finally {
                    }
                } while (xml.next() != 2);
                xml.next();
                loop1: while (xml.getEventType() != 3) {
                    while (xml.getEventType() != 2) {
                        if (xml.getEventType() == 1) {
                            break loop1;
                        } else {
                            xml.next();
                        }
                    }
                    if (xml.getName().equals("timezone")) {
                        arrayList2.add(xml.getAttributeValue(0));
                    }
                    while (xml.getEventType() != 3) {
                        xml.next();
                    }
                    xml.next();
                }
                xml.close();
            } catch (IOException unused) {
                Log.e("ZoneGetter", "Unable to read timezones.xml file");
            } catch (XmlPullParserException unused2) {
                Log.e("ZoneGetter", "Ill-formatted timezones.xml file");
            }
            int size = arrayList2.size();
            this.zoneCount = size;
            this.olsonIdsToDisplay = new String[size];
            this.timeZones = new TimeZone[size];
            this.gmtOffsetTexts = new CharSequence[size];
            int i7 = 0;
            while (i5 < this.zoneCount) {
                String str4 = (String) arrayList2.get(i5);
                this.olsonIdsToDisplay[i5] = str4;
                TimeZone timeZone = TimeZone.getTimeZone(str4);
                this.timeZones[i5] = timeZone;
                CharSequence[] charSequenceArr = this.gmtOffsetTexts;
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                String gMTPattern = timeZoneFormat2.getGMTPattern();
                int indexOf = gMTPattern.indexOf("{0}");
                if (indexOf == -1) {
                    str = "GMT";
                    substring = "";
                } else {
                    String substring2 = gMTPattern.substring(i7, indexOf);
                    substring = gMTPattern.substring(indexOf + 3);
                    str = substring2;
                }
                if (!str.isEmpty()) {
                    ZoneGetter.appendWithTtsSpan(spannableStringBuilder, str, new TtsSpan.TextBuilder(str).build());
                }
                int offset = timeZone.getOffset(date.getTime());
                if ((offset < 0 ? i6 : i7) != 0) {
                    offset = -offset;
                    gMTOffsetPatternType = TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HM;
                } else {
                    gMTOffsetPatternType = TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HM;
                }
                String gMTOffsetPattern = timeZoneFormat2.getGMTOffsetPattern(gMTOffsetPatternType);
                String gMTOffsetDigits = timeZoneFormat2.getGMTOffsetDigits();
                long j = offset;
                TimeZoneFormat timeZoneFormat3 = timeZoneFormat2;
                Date date2 = date;
                int i8 = (int) (j / 3600000);
                int abs = Math.abs((int) (j / 60000)) % 60;
                int i9 = 0;
                while (i9 < gMTOffsetPattern.length()) {
                    char charAt = gMTOffsetPattern.charAt(i9);
                    if (charAt == '+' || charAt == '-' || charAt == 8722) {
                        str2 = gMTOffsetPattern;
                        i = i8;
                        i2 = abs;
                        timeZoneFormat = timeZoneFormat3;
                        arrayList = arrayList2;
                        String valueOf = String.valueOf(charAt);
                        ZoneGetter.appendWithTtsSpan(spannableStringBuilder, valueOf, new TtsSpan.VerbatimBuilder(valueOf).build());
                    } else if (charAt == 'H' || charAt == 'm') {
                        int i10 = i9 + 1;
                        i = i8;
                        if (i10 >= gMTOffsetPattern.length() || gMTOffsetPattern.charAt(i10) != charAt) {
                            i3 = 1;
                        } else {
                            i3 = 2;
                            i9 = i10;
                        }
                        if (charAt == 'H') {
                            str3 = "hour";
                            i4 = i;
                        } else {
                            str3 = "minute";
                            i4 = abs;
                        }
                        str2 = gMTOffsetPattern;
                        int i11 = i4 / 10;
                        i2 = abs;
                        int i12 = i4 % 10;
                        timeZoneFormat = timeZoneFormat3;
                        StringBuilder sb = new StringBuilder(i3);
                        arrayList = arrayList2;
                        if (i4 >= 10 || i3 == 2) {
                            sb.append(gMTOffsetDigits.charAt(i11));
                        }
                        ZoneGetter.appendWithTtsSpan(spannableStringBuilder, ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(gMTOffsetDigits, i12, sb), new TtsSpan.MeasureBuilder().setNumber(i4).setUnit(str3).build());
                    } else {
                        spannableStringBuilder.append(charAt);
                        str2 = gMTOffsetPattern;
                        i = i8;
                        i2 = abs;
                        timeZoneFormat = timeZoneFormat3;
                        arrayList = arrayList2;
                    }
                    i9++;
                    gMTOffsetPattern = str2;
                    i8 = i;
                    abs = i2;
                    timeZoneFormat3 = timeZoneFormat;
                    arrayList2 = arrayList;
                }
                TimeZoneFormat timeZoneFormat4 = timeZoneFormat3;
                ArrayList arrayList3 = arrayList2;
                if (!substring.isEmpty()) {
                    ZoneGetter.appendWithTtsSpan(spannableStringBuilder, substring, new TtsSpan.TextBuilder(substring).build());
                }
                i6 = 1;
                charSequenceArr[i5] = BidiFormatter.getInstance().unicodeWrap(new SpannableString(spannableStringBuilder), TextUtils.getLayoutDirectionFromLocale(locale) == 1 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR);
                i5++;
                i7 = 0;
                date = date2;
                timeZoneFormat2 = timeZoneFormat4;
                arrayList2 = arrayList3;
            }
            List<String> lookupTimeZoneIdsByCountry = lookupTimeZoneIdsByCountry(locale.getCountry());
            if (lookupTimeZoneIdsByCountry != null) {
                new HashSet(lookupTimeZoneIdsByCountry);
            } else {
                new HashSet();
            }
        }

        public List<String> lookupTimeZoneIdsByCountry(String str) {
            CountryTimeZones lookupCountryTimeZones = TimeZoneFinder.getInstance().lookupCountryTimeZones(str);
            if (lookupCountryTimeZones == null) {
                return null;
            }
            List timeZoneMappings = lookupCountryTimeZones.getTimeZoneMappings();
            ArrayList arrayList = new ArrayList(timeZoneMappings.size());
            Iterator it = timeZoneMappings.iterator();
            while (it.hasNext()) {
                arrayList.add(((CountryTimeZones.TimeZoneMapping) it.next()).getTimeZoneId());
            }
            return Collections.unmodifiableList(arrayList);
        }
    }

    public static void appendWithTtsSpan(SpannableStringBuilder spannableStringBuilder, CharSequence charSequence, TtsSpan ttsSpan) {
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(charSequence);
        spannableStringBuilder.setSpan(ttsSpan, length, spannableStringBuilder.length(), 0);
    }
}
