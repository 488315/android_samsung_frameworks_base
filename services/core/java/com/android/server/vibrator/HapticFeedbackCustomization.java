package com.android.server.vibrator;

import android.R;
import android.content.res.Resources;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import android.os.vibrator.Flags;
import android.os.vibrator.persistence.ParsedVibration;
import android.os.vibrator.persistence.VibrationXmlParser;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;

import com.android.internal.vibrator.persistence.XmlReader;
import com.android.internal.vibrator.persistence.XmlValidator;
import com.android.modules.utils.TypedXmlPullParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class HapticFeedbackCustomization {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class CustomizationParserException extends Exception {
        private CustomizationParserException(String str) {
            super(str);
        }

        public /* synthetic */ CustomizationParserException(String str, int i) {
            this(str);
        }
    }

    public static SparseArray loadVibrationsInternal(
            Resources resources, VibratorInfo vibratorInfo) {
        if (!Flags.hapticFeedbackVibrationOemCustomizationEnabled()) {
            Slog.d(
                    "HapticFeedbackCustomization",
                    "Haptic feedback customization feature is not enabled.");
            return null;
        }
        String string = resources.getString(R.string.duration_years_shortest_future);
        if (TextUtils.isEmpty(string)) {
            Slog.d("HapticFeedbackCustomization", "Customization file not configured.");
            return null;
        }
        try {
            FileReader fileReader = new FileReader(string);
            TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
            newFastPullParser.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            newFastPullParser.setInput(fileReader);
            XmlReader.readDocumentStartTag(newFastPullParser, "haptic-feedback-constants");
            int i = 0;
            XmlValidator.checkTagHasNoUnexpectedAttributes(newFastPullParser, new String[0]);
            int depth = newFastPullParser.getDepth();
            SparseArray sparseArray = new SparseArray();
            while (XmlReader.readNextTagWithin(newFastPullParser, depth)) {
                XmlValidator.checkStartTag(newFastPullParser, "constant");
                int depth2 = newFastPullParser.getDepth();
                XmlValidator.checkTagHasNoUnexpectedAttributes(
                        newFastPullParser, new String[] {"id"});
                int readAttributeIntNonNegative =
                        XmlReader.readAttributeIntNonNegative(newFastPullParser, "id");
                if (sparseArray.contains(readAttributeIntNonNegative)) {
                    throw new CustomizationParserException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    readAttributeIntNonNegative,
                                    "Multiple customizations found for effect "),
                            i);
                }
                XmlValidator.checkParserCondition(
                        XmlReader.readNextTagWithin(newFastPullParser, depth2),
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                readAttributeIntNonNegative,
                                "Unsupported empty customization tag for effect "),
                        new Object[0]);
                ParsedVibration parseElement =
                        VibrationXmlParser.parseElement(newFastPullParser, 1);
                if (parseElement == null) {
                    throw new CustomizationParserException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    readAttributeIntNonNegative,
                                    "Unable to parse vibration element for effect "),
                            i);
                }
                VibrationEffect resolve = parseElement.resolve(vibratorInfo);
                if (resolve != null) {
                    if (resolve.getDuration() == Long.MAX_VALUE) {
                        throw new CustomizationParserException(
                                String.format(
                                        "Vibration for effect ID %d is repeating, which is not"
                                            + " allowed as a haptic feedback: %s",
                                        Integer.valueOf(readAttributeIntNonNegative), resolve),
                                i);
                    }
                    sparseArray.put(readAttributeIntNonNegative, resolve);
                }
                XmlReader.readEndTag(newFastPullParser, "constant", depth2);
            }
            XmlReader.readEndTag(newFastPullParser, "haptic-feedback-constants", depth);
            XmlReader.readDocumentEndTag(newFastPullParser);
            return sparseArray;
        } catch (FileNotFoundException unused) {
            Slog.d("HapticFeedbackCustomization", "Specified customization file not found.");
            return null;
        }
    }
}
