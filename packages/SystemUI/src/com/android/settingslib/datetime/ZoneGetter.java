package com.android.settingslib.datetime;

import android.content.Context;
import android.content.res.Resources;
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

/* loaded from: classes.dex */
public class ZoneGetter {

    public final class ZoneGetterData {
        public final CharSequence[] gmtOffsetTexts;
        public final String[] olsonIdsToDisplay;
        public final TimeZone[] timeZones;
        public final int zoneCount;

        /* JADX WARN: Finally extract failed */
        public ZoneGetterData(Context context) throws Resources.NotFoundException {
            String strSubstring;
            String str;
            TimeZoneFormat.GMTOffsetPatternType gMTOffsetPatternType;
            int i;
            Locale locale;
            TimeZoneFormat timeZoneFormat;
            Date date;
            int i2;
            String str2;
            int i3;
            Locale locale2 = context.getResources().getConfiguration().locale;
            TimeZoneFormat timeZoneFormat2 = TimeZoneFormat.getInstance(locale2);
            Date date2 = new Date();
            ArrayList arrayList = new ArrayList();
            int i4 = 1;
            int i5 = 0;
            try {
                XmlResourceParser xml = context.getResources().getXml(R.xml.timezones);
                do {
                    try {
                    } catch (Throwable th) {
                        if (xml == null) {
                            throw th;
                        }
                        try {
                            xml.close();
                            throw th;
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                            throw th;
                        }
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
                        arrayList.add(xml.getAttributeValue(0));
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
            int size = arrayList.size();
            this.zoneCount = size;
            this.olsonIdsToDisplay = new String[size];
            this.timeZones = new TimeZone[size];
            this.gmtOffsetTexts = new CharSequence[size];
            int i6 = 0;
            while (i6 < this.zoneCount) {
                String str3 = (String) arrayList.get(i6);
                this.olsonIdsToDisplay[i6] = str3;
                TimeZone timeZone = TimeZone.getTimeZone(str3);
                this.timeZones[i6] = timeZone;
                CharSequence[] charSequenceArr = this.gmtOffsetTexts;
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                String gMTPattern = timeZoneFormat2.getGMTPattern();
                int iIndexOf = gMTPattern.indexOf("{0}");
                if (iIndexOf == -1) {
                    str = "GMT";
                    strSubstring = "";
                } else {
                    String strSubstring2 = gMTPattern.substring(i5, iIndexOf);
                    strSubstring = gMTPattern.substring(iIndexOf + 3);
                    str = strSubstring2;
                }
                if (!str.isEmpty()) {
                    ZoneGetter.appendWithTtsSpan(spannableStringBuilder, str, new TtsSpan.TextBuilder(str).build());
                }
                int offset = timeZone.getOffset(date2.getTime());
                if (offset < 0) {
                    offset = -offset;
                    gMTOffsetPatternType = TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HM;
                } else {
                    gMTOffsetPatternType = TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HM;
                }
                String gMTOffsetPattern = timeZoneFormat2.getGMTOffsetPattern(gMTOffsetPatternType);
                String gMTOffsetDigits = timeZoneFormat2.getGMTOffsetDigits();
                long j = offset;
                int i7 = i4;
                int i8 = (int) (j / 3600000);
                int iAbs = Math.abs((int) (j / 60000)) % 60;
                int i9 = 0;
                while (i9 < gMTOffsetPattern.length()) {
                    char cCharAt = gMTOffsetPattern.charAt(i9);
                    int i10 = i7;
                    if (cCharAt == '+' || cCharAt == '-' || cCharAt == 8722) {
                        i = i6;
                        locale = locale2;
                        timeZoneFormat = timeZoneFormat2;
                        date = date2;
                        String strValueOf = String.valueOf(cCharAt);
                        ZoneGetter.appendWithTtsSpan(spannableStringBuilder, strValueOf, new TtsSpan.VerbatimBuilder(strValueOf).build());
                    } else if (cCharAt == 'H' || cCharAt == 'm') {
                        int i11 = i9 + 1;
                        if (i11 >= gMTOffsetPattern.length() || gMTOffsetPattern.charAt(i11) != cCharAt) {
                            i2 = i10;
                        } else {
                            i9 = i11;
                            i2 = 2;
                        }
                        if (cCharAt == 'H') {
                            str2 = "hour";
                            i3 = i8;
                        } else {
                            str2 = "minute";
                            i3 = iAbs;
                        }
                        i = i6;
                        int i12 = i3 / 10;
                        locale = locale2;
                        int i13 = i3 % 10;
                        timeZoneFormat = timeZoneFormat2;
                        StringBuilder sb = new StringBuilder(i2);
                        date = date2;
                        if (i3 >= 10 || i2 == 2) {
                            sb.append(gMTOffsetDigits.charAt(i12));
                        }
                        sb.append(gMTOffsetDigits.charAt(i13));
                        ZoneGetter.appendWithTtsSpan(spannableStringBuilder, sb.toString(), new TtsSpan.MeasureBuilder().setNumber(i3).setUnit(str2).build());
                    } else {
                        spannableStringBuilder.append(cCharAt);
                        i = i6;
                        locale = locale2;
                        timeZoneFormat = timeZoneFormat2;
                        date = date2;
                    }
                    i9++;
                    i7 = i10;
                    i6 = i;
                    locale2 = locale;
                    timeZoneFormat2 = timeZoneFormat;
                    date2 = date;
                }
                int i14 = i6;
                Locale locale3 = locale2;
                TimeZoneFormat timeZoneFormat3 = timeZoneFormat2;
                Date date3 = date2;
                int i15 = i7;
                if (!strSubstring.isEmpty()) {
                    ZoneGetter.appendWithTtsSpan(spannableStringBuilder, strSubstring, new TtsSpan.TextBuilder(strSubstring).build());
                }
                charSequenceArr[i14] = BidiFormatter.getInstance().unicodeWrap(new SpannableString(spannableStringBuilder), TextUtils.getLayoutDirectionFromLocale(locale3) == i15 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR);
                i6 = i14 + 1;
                i4 = i15;
                locale2 = locale3;
                timeZoneFormat2 = timeZoneFormat3;
                date2 = date3;
                i5 = 0;
            }
            List<String> listLookupTimeZoneIdsByCountry = lookupTimeZoneIdsByCountry(locale2.getCountry());
            if (listLookupTimeZoneIdsByCountry != null) {
                new HashSet(listLookupTimeZoneIdsByCountry);
            } else {
                new HashSet();
            }
        }

        public List<String> lookupTimeZoneIdsByCountry(String str) {
            CountryTimeZones countryTimeZonesLookupCountryTimeZones = TimeZoneFinder.getInstance().lookupCountryTimeZones(str);
            if (countryTimeZonesLookupCountryTimeZones == null) {
                return null;
            }
            List timeZoneMappings = countryTimeZonesLookupCountryTimeZones.getTimeZoneMappings();
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
