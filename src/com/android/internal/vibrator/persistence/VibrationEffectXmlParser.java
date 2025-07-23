package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.IOException;

/* loaded from: classes4.dex */
public class VibrationEffectXmlParser {
    public static XmlSerializedVibration<? extends VibrationEffect> parseTag(TypedXmlPullParser typedXmlPullParser, int i) throws XmlParserException, IOException {
        XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_VIBRATION_EFFECT);
        XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
        return parseVibrationContent(typedXmlPullParser, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0073, code lost:
    
        if (r2.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_BASIC_ENVELOPE_EFFECT) == false) goto L4;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.android.internal.vibrator.persistence.XmlSerializedVibration<? extends android.os.VibrationEffect> parseVibrationContent(com.android.modules.utils.TypedXmlPullParser r6, int r7) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 322
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.vibrator.persistence.VibrationEffectXmlParser.parseVibrationContent(com.android.modules.utils.TypedXmlPullParser, int):com.android.internal.vibrator.persistence.XmlSerializedVibration");
    }
}
