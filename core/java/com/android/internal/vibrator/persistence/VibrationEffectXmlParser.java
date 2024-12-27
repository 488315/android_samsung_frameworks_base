package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;

import com.android.modules.utils.TypedXmlPullParser;

import java.io.IOException;

/* loaded from: classes5.dex */
public class VibrationEffectXmlParser {
    public static XmlSerializedVibration<VibrationEffect> parseTag(
            TypedXmlPullParser parser, int flags) throws XmlParserException, IOException {
        XmlValidator.checkStartTag(parser, XmlConstants.TAG_VIBRATION_EFFECT);
        XmlValidator.checkTagHasNoUnexpectedAttributes(parser, new String[0]);
        return parseVibrationContent(parser, flags);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003e, code lost:

       if (r2.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_PREDEFINED_EFFECT) != false) goto L15;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.android.internal.vibrator.persistence.SerializedVibrationEffect
            parseVibrationContent(com.android.modules.utils.TypedXmlPullParser r6, int r7)
                    throws com.android.internal.vibrator.persistence.XmlParserException,
                            java.io.IOException {
        /*
            java.lang.String r0 = r6.getName()
            int r1 = r6.getDepth()
            boolean r2 = com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(r6, r1)
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]
            java.lang.String r5 = "Unsupported empty vibration tag"
            com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(r2, r5, r4)
            java.lang.String r2 = r6.getName()
            int r4 = r2.hashCode()
            switch(r4) {
                case -1327271112: goto L37;
                case -764216799: goto L2c;
                case 149296439: goto L21;
                default: goto L20;
            }
        L20:
            goto L41
        L21:
            java.lang.String r3 = "primitive-effect"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L20
            r3 = 1
            goto L42
        L2c:
            java.lang.String r3 = "waveform-effect"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L20
            r3 = 2
            goto L42
        L37:
            java.lang.String r4 = "predefined-effect"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L20
            goto L42
        L41:
            r3 = -1
        L42:
            switch(r3) {
                case 0: goto L9b;
                case 1: goto L76;
                case 2: goto L6c;
                default: goto L45;
            }
        L45:
            com.android.internal.vibrator.persistence.XmlParserException r2 = new com.android.internal.vibrator.persistence.XmlParserException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unexpected tag "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r6.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " in vibration tag "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L6c:
            com.android.internal.vibrator.persistence.SerializedVibrationEffect r2 = new com.android.internal.vibrator.persistence.SerializedVibrationEffect
            com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform r3 = com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Parser.parseNext(r6)
            r2.<init>(r3)
            goto La5
        L76:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
        L7b:
            com.android.internal.vibrator.persistence.SerializedCompositionPrimitive r3 = com.android.internal.vibrator.persistence.SerializedCompositionPrimitive.Parser.parseNext(r6)
            r2.add(r3)
            boolean r3 = com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(r6, r1)
            if (r3 != 0) goto L7b
            com.android.internal.vibrator.persistence.SerializedVibrationEffect r3 = new com.android.internal.vibrator.persistence.SerializedVibrationEffect
            int r4 = r2.size()
            com.android.internal.vibrator.persistence.SerializedVibrationEffect$SerializedSegment[] r4 = new com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[r4]
            java.lang.Object[] r4 = r2.toArray(r4)
            com.android.internal.vibrator.persistence.SerializedVibrationEffect$SerializedSegment[] r4 = (com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[]) r4
            r3.<init>(r4)
            r2 = r3
            goto La5
        L9b:
            com.android.internal.vibrator.persistence.SerializedVibrationEffect r2 = new com.android.internal.vibrator.persistence.SerializedVibrationEffect
            com.android.internal.vibrator.persistence.SerializedPredefinedEffect r3 = com.android.internal.vibrator.persistence.SerializedPredefinedEffect.Parser.parseNext(r6, r7)
            r2.<init>(r3)
        La5:
            com.android.internal.vibrator.persistence.XmlReader.readEndTag(r6, r0, r1)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.vibrator.persistence.VibrationEffectXmlParser.parseVibrationContent(com.android.modules.utils.TypedXmlPullParser,"
                    + " int):com.android.internal.vibrator.persistence.SerializedVibrationEffect");
    }
}
