package com.android.internal.telephony;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.ContactsContract;
import android.util.SparseIntArray;
import com.android.internal.R;
import com.android.internal.telephony.cdma.sms.UserData;
import com.android.internal.telephony.util.TelephonyUtils;
import com.android.internal.telephony.util.XmlUtils;
import com.android.telephony.Rlog;

/* loaded from: classes4.dex */
public class Sms7BitEncodingTranslator {
    private static final String TAG = "Sms7BitEncodingTranslator";
    private static final String XML_CHARACTOR_TAG = "Character";
    private static final String XML_FROM_TAG = "from";
    private static final String XML_START_TAG = "SmsEnforce7BitTranslationTable";
    private static final String XML_TO_TAG = "to";
    private static final String XML_TRANSLATION_TYPE_TAG = "TranslationType";
    private static final boolean DBG = TelephonyUtils.IS_DEBUGGABLE;
    private static boolean mIs7BitTranslationTableLoaded = false;
    private static SparseIntArray mTranslationTable = null;
    private static SparseIntArray mTranslationTableCommon = null;
    private static SparseIntArray mTranslationTableGSM = null;
    private static SparseIntArray mTranslationTableCDMA = null;

    public static String translate(CharSequence charSequence, boolean z) {
        SparseIntArray sparseIntArray;
        SparseIntArray sparseIntArray2;
        if (charSequence == null) {
            Rlog.w(TAG, "Null message can not be translated");
            return null;
        }
        int length = charSequence.length();
        if (length <= 0) {
            return "";
        }
        ensure7BitTranslationTableLoaded();
        SparseIntArray sparseIntArray3 = mTranslationTableCommon;
        if ((sparseIntArray3 == null || sparseIntArray3.size() <= 0) && (((sparseIntArray = mTranslationTableGSM) == null || sparseIntArray.size() <= 0) && ((sparseIntArray2 = mTranslationTableCDMA) == null || sparseIntArray2.size() <= 0))) {
            return null;
        }
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = translateIfNeeded(charSequence.charAt(i), z);
        }
        return String.valueOf(cArr);
    }

    private static char translateIfNeeded(char c, boolean z) {
        if (noTranslationNeeded(c, z)) {
            if (DBG) {
                Rlog.v(TAG, "No translation needed for " + Integer.toHexString(c));
            }
            return c;
        }
        ensure7BitTranslationTableLoaded();
        SparseIntArray sparseIntArray = mTranslationTableCommon;
        int i = sparseIntArray != null ? sparseIntArray.get(c, -1) : -1;
        if (i == -1) {
            if (z) {
                SparseIntArray sparseIntArray2 = mTranslationTableCDMA;
                if (sparseIntArray2 != null) {
                    i = sparseIntArray2.get(c, -1);
                }
            } else {
                SparseIntArray sparseIntArray3 = mTranslationTableGSM;
                if (sparseIntArray3 != null) {
                    i = sparseIntArray3.get(c, -1);
                }
            }
        }
        if (i != -1) {
            if (DBG) {
                Rlog.v(TAG, Integer.toHexString(c) + " (" + c + ") translated to " + Integer.toHexString(i) + " (" + ((char) i) + NavigationBarInflaterView.KEY_CODE_END);
            }
            return (char) i;
        }
        if (!DBG) {
            return ' ';
        }
        Rlog.w(TAG, "No translation found for " + Integer.toHexString(c) + "! Replacing for empty space");
        return ' ';
    }

    private static boolean noTranslationNeeded(char c, boolean z) {
        if (z) {
            return GsmAlphabet.isGsmSeptets(c) && UserData.charToAscii.get(c, -1) != -1;
        }
        return GsmAlphabet.isGsmSeptets(c);
    }

    private static void ensure7BitTranslationTableLoaded() {
        synchronized (Sms7BitEncodingTranslator.class) {
            if (!mIs7BitTranslationTableLoaded) {
                mTranslationTableCommon = new SparseIntArray();
                mTranslationTableGSM = new SparseIntArray();
                mTranslationTableCDMA = new SparseIntArray();
                load7BitTranslationTableFromXml();
                mIs7BitTranslationTableLoaded = true;
            }
        }
    }

    private static void load7BitTranslationTableFromXml() throws Resources.NotFoundException {
        boolean z;
        Resources system = Resources.getSystem();
        if (DBG) {
            Rlog.d(TAG, "load7BitTranslationTableFromXml: open normal file");
        }
        XmlResourceParser xml = system.getXml(R.xml.sms_7bit_translation_table);
        try {
            try {
                XmlUtils.beginDocument(xml, XML_START_TAG);
                while (true) {
                    XmlUtils.nextElement(xml);
                    String name = xml.getName();
                    z = DBG;
                    if (z) {
                        Rlog.d(TAG, "tag: " + name);
                    }
                    if (XML_TRANSLATION_TYPE_TAG.equals(name)) {
                        String attributeValue = xml.getAttributeValue(null, "Type");
                        if (z) {
                            Rlog.d(TAG, "type: " + attributeValue);
                        }
                        if (attributeValue.equals(ContactsContract.CommonDataKinds.PACKAGE_COMMON)) {
                            mTranslationTable = mTranslationTableCommon;
                        } else if (attributeValue.equals("gsm")) {
                            mTranslationTable = mTranslationTableGSM;
                        } else if (attributeValue.equals("cdma")) {
                            mTranslationTable = mTranslationTableCDMA;
                        } else {
                            Rlog.e(TAG, "Error Parsing 7BitTranslationTable: found incorrect type" + attributeValue);
                        }
                    } else {
                        if (!XML_CHARACTOR_TAG.equals(name) || mTranslationTable == null) {
                            break;
                        }
                        int attributeUnsignedIntValue = xml.getAttributeUnsignedIntValue(null, XML_FROM_TAG, -1);
                        int attributeUnsignedIntValue2 = xml.getAttributeUnsignedIntValue(null, XML_TO_TAG, -1);
                        if (attributeUnsignedIntValue != -1 && attributeUnsignedIntValue2 != -1) {
                            if (z) {
                                Rlog.d(TAG, "Loading mapping " + Integer.toHexString(attributeUnsignedIntValue).toUpperCase() + " -> " + Integer.toHexString(attributeUnsignedIntValue2).toUpperCase());
                            }
                            mTranslationTable.put(attributeUnsignedIntValue, attributeUnsignedIntValue2);
                        } else {
                            Rlog.d(TAG, "Invalid translation table file format");
                        }
                    }
                }
                if (z) {
                    Rlog.d(TAG, "load7BitTranslationTableFromXml: parsing successful, file loaded");
                }
                if (xml instanceof XmlResourceParser) {
                    xml.close();
                }
            } catch (Exception e) {
                Rlog.e(TAG, "Got exception while loading 7BitTranslationTable file.", e);
                if (xml instanceof XmlResourceParser) {
                    xml.close();
                }
            }
        } catch (Throwable th) {
            if (xml instanceof XmlResourceParser) {
                xml.close();
            }
            throw th;
        }
    }
}
