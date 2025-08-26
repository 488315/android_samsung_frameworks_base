package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.os.vibrator.Flags;
import com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform;
import com.android.internal.vibrator.persistence.SerializedBasicEnvelopeEffect;
import com.android.internal.vibrator.persistence.SerializedComposedEffect;
import com.android.internal.vibrator.persistence.SerializedCompositionPrimitive;
import com.android.internal.vibrator.persistence.SerializedPredefinedEffect;
import com.android.internal.vibrator.persistence.SerializedRepeatingEffect;
import com.android.internal.vibrator.persistence.SerializedVendorEffect;
import com.android.internal.vibrator.persistence.SerializedWaveformEnvelopeEffect;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class VibrationEffectXmlParser {
    public static XmlSerializedVibration<? extends VibrationEffect> parseTag(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
        XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_VIBRATION_EFFECT);
        XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
        return parseVibrationContent(typedXmlPullParser, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static XmlSerializedVibration<? extends VibrationEffect> parseVibrationContent(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
        XmlSerializedVibration<? extends VibrationEffect> serializedComposedEffect;
        String name = typedXmlPullParser.getName();
        int depth = typedXmlPullParser.getDepth();
        char c = 0;
        XmlValidator.checkParserCondition(XmlReader.readNextTagWithin(typedXmlPullParser, depth), "Unsupported empty vibration tag", new Object[0]);
        String name2 = typedXmlPullParser.getName();
        name2.hashCode();
        switch (name2.hashCode()) {
            case -2119736689:
                if (!name2.equals(XmlConstants.TAG_BASIC_ENVELOPE_EFFECT)) {
                    c = 65535;
                    break;
                }
                break;
            case -1327271112:
                if (name2.equals(XmlConstants.TAG_PREDEFINED_EFFECT)) {
                    c = 1;
                    break;
                }
                break;
            case -764216799:
                if (name2.equals(XmlConstants.TAG_WAVEFORM_EFFECT)) {
                    c = 2;
                    break;
                }
                break;
            case -454069833:
                if (name2.equals(XmlConstants.TAG_REPEATING_EFFECT)) {
                    c = 3;
                    break;
                }
                break;
            case -352223818:
                if (name2.equals(XmlConstants.TAG_VENDOR_EFFECT)) {
                    c = 4;
                    break;
                }
                break;
            case 149296439:
                if (name2.equals(XmlConstants.TAG_PRIMITIVE_EFFECT)) {
                    c = 5;
                    break;
                }
                break;
            case 1239288734:
                if (name2.equals(XmlConstants.TAG_WAVEFORM_ENVELOPE_EFFECT)) {
                    c = 6;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (Flags.normalizedPwleEffects()) {
                    serializedComposedEffect = new SerializedComposedEffect(SerializedBasicEnvelopeEffect.Parser.parseNext(typedXmlPullParser, i));
                } else {
                    if (Flags.normalizedPwleEffects()) {
                        serializedComposedEffect = new SerializedComposedEffect(SerializedRepeatingEffect.Parser.parseNext(typedXmlPullParser, i));
                    }
                    throw new XmlParserException("Unexpected tag " + typedXmlPullParser.getName() + " in vibration tag " + name);
                }
                XmlReader.readEndTag(typedXmlPullParser, name, depth);
                return serializedComposedEffect;
            case 1:
                serializedComposedEffect = new SerializedComposedEffect(SerializedPredefinedEffect.Parser.parseNext(typedXmlPullParser, i));
                XmlReader.readEndTag(typedXmlPullParser, name, depth);
                return serializedComposedEffect;
            case 2:
                serializedComposedEffect = new SerializedComposedEffect(SerializedAmplitudeStepWaveform.Parser.parseNext(typedXmlPullParser));
                XmlReader.readEndTag(typedXmlPullParser, name, depth);
                return serializedComposedEffect;
            case 3:
                break;
            case 4:
                if (Flags.vendorVibrationEffects()) {
                    serializedComposedEffect = SerializedVendorEffect.Parser.parseNext(typedXmlPullParser, i);
                }
                XmlReader.readEndTag(typedXmlPullParser, name, depth);
                return serializedComposedEffect;
            case 5:
                ArrayList arrayList = new ArrayList();
                do {
                    arrayList.add(SerializedCompositionPrimitive.Parser.parseNext(typedXmlPullParser));
                } while (XmlReader.readNextTagWithin(typedXmlPullParser, depth));
                serializedComposedEffect = new SerializedComposedEffect((SerializedComposedEffect.SerializedSegment[]) arrayList.toArray(new SerializedComposedEffect.SerializedSegment[arrayList.size()]));
                XmlReader.readEndTag(typedXmlPullParser, name, depth);
                return serializedComposedEffect;
            case 6:
                if (Flags.normalizedPwleEffects()) {
                    serializedComposedEffect = new SerializedComposedEffect(SerializedWaveformEnvelopeEffect.Parser.parseNext(typedXmlPullParser, i));
                }
                XmlReader.readEndTag(typedXmlPullParser, name, depth);
                return serializedComposedEffect;
            default:
                throw new XmlParserException("Unexpected tag " + typedXmlPullParser.getName() + " in vibration tag " + name);
        }
    }
}
