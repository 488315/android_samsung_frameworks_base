package com.android.settingslib.datetime;

import android.text.SpannableStringBuilder;
import android.text.style.TtsSpan;
import com.android.i18n.timezone.CountryTimeZones;
import com.android.i18n.timezone.TimeZoneFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ZoneGetter {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ZoneGetterData {
        public final CharSequence[] gmtOffsetTexts;
        public final String[] olsonIdsToDisplay;
        public final TimeZone[] timeZones;
        public final int zoneCount;

        /* JADX WARN: Code restructure failed: missing block: B:58:0x0189, code lost:
        
            if (r15 != 2) goto L82;
         */
        /* JADX WARN: Finally extract failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public ZoneGetterData(android.content.Context r23) {
            /*
                Method dump skipped, instructions count: 560
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.datetime.ZoneGetter.ZoneGetterData.<init>(android.content.Context):void");
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
