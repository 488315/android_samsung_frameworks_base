package com.android.internal.telephony.gsm;

import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsCbEtwsInfo;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.ims.settings.SemImsProfile;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes4.dex */
public class SmsCbHeader {
    public static final int FORMAT_ETWS_PRIMARY = 3;
    public static final int FORMAT_GSM = 1;
    public static final int FORMAT_UMTS = 2;
    private static final String[] LANGUAGE_CODES_GROUP_0 = {Locale.GERMAN.getLanguage(), Locale.ENGLISH.getLanguage(), Locale.ITALIAN.getLanguage(), Locale.FRENCH.getLanguage(), new Locale("es").getLanguage(), new Locale("nl").getLanguage(), new Locale("sv").getLanguage(), new Locale("da").getLanguage(), new Locale("pt").getLanguage(), new Locale("fi").getLanguage(), new Locale("nb").getLanguage(), new Locale("el").getLanguage(), new Locale("tr").getLanguage(), new Locale("hu").getLanguage(), new Locale("pl").getLanguage(), null};
    private static final String[] LANGUAGE_CODES_GROUP_2 = {new Locale("cs").getLanguage(), new Locale("he").getLanguage(), new Locale("ar").getLanguage(), new Locale("ru").getLanguage(), new Locale(SemImsProfile.ImsFeature.IS).getLanguage(), null, null, null, null, null, null, null, null, null, null, null};
    private static final int MESSAGE_TYPE_CBS_MESSAGE = 1;
    public static final int PDU_HEADER_LENGTH = 6;
    private static final int PDU_LENGTH_ETWS = 56;
    private static final int PDU_LENGTH_GSM = 88;
    private final SmsCbCmasInfo mCmasInfo;
    private final int mDataCodingScheme;
    private DataCodingScheme mDataCodingSchemeStructedData;
    private final SmsCbEtwsInfo mEtwsInfo;
    private final int mFormat;
    private final int mGeographicalScope;
    private final int mMessageIdentifier;
    private final int mNrOfPages;
    private final int mPageIndex;
    private final int mSerialNumber;

    public SmsCbHeader(byte[] bArr) throws IllegalArgumentException {
        if (bArr == null || bArr.length < 6) {
            throw new IllegalArgumentException("Illegal PDU");
        }
        int i = 1;
        if (bArr.length <= 88) {
            byte b = bArr[0];
            this.mGeographicalScope = (b & MidiConstants.STATUS_PROGRAM_CHANGE) >>> 6;
            this.mSerialNumber = ((b & 255) << 8) | (bArr[1] & 255);
            this.mMessageIdentifier = ((bArr[2] & 255) << 8) | (bArr[3] & 255);
            if (isEtwsMessage() && bArr.length <= 56) {
                this.mFormat = 3;
                this.mDataCodingScheme = -1;
                this.mPageIndex = -1;
                this.mNrOfPages = -1;
                byte b2 = bArr[4];
                this.mEtwsInfo = new SmsCbEtwsInfo((b2 & MidiConstants.STATUS_ACTIVE_SENSING) >>> 1, (b2 & 1) != 0, (bArr[5] & 128) != 0, true, bArr.length > 6 ? Arrays.copyOfRange(bArr, 6, bArr.length) : null);
                this.mCmasInfo = null;
                return;
            }
            this.mFormat = 1;
            this.mDataCodingScheme = bArr[4] & 255;
            byte b3 = bArr[5];
            int i2 = (b3 & 240) >>> 4;
            int i3 = b3 & 15;
            if (i2 == 0 || i3 == 0 || i2 > i3) {
                i3 = 1;
            } else {
                i = i2;
            }
            this.mPageIndex = i;
            this.mNrOfPages = i3;
        } else {
            this.mFormat = 2;
            byte b4 = bArr[0];
            if (b4 != 1) {
                throw new IllegalArgumentException("Unsupported message type " + ((int) b4));
            }
            this.mMessageIdentifier = ((bArr[1] & 255) << 8) | (bArr[2] & 255);
            byte b5 = bArr[3];
            this.mGeographicalScope = (b5 & MidiConstants.STATUS_PROGRAM_CHANGE) >>> 6;
            this.mSerialNumber = ((b5 & 255) << 8) | (bArr[4] & 255);
            this.mDataCodingScheme = bArr[5] & 255;
            this.mPageIndex = 1;
            this.mNrOfPages = 1;
        }
        if (this.mDataCodingScheme != -1) {
            this.mDataCodingSchemeStructedData = new DataCodingScheme(this.mDataCodingScheme);
        }
        if (isEtwsMessage()) {
            this.mEtwsInfo = new SmsCbEtwsInfo(getEtwsWarningType(), isEtwsEmergencyUserAlert(), isEtwsPopupAlert(), false, null);
            this.mCmasInfo = null;
        } else {
            if (isCmasMessage()) {
                int cmasMessageClass = getCmasMessageClass();
                int cmasSeverity = getCmasSeverity();
                int cmasUrgency = getCmasUrgency();
                int cmasCertainty = getCmasCertainty();
                this.mEtwsInfo = null;
                this.mCmasInfo = new SmsCbCmasInfo(cmasMessageClass, -1, -1, cmasSeverity, cmasUrgency, cmasCertainty);
                return;
            }
            this.mEtwsInfo = null;
            this.mCmasInfo = null;
        }
    }

    public int getGeographicalScope() {
        return this.mGeographicalScope;
    }

    public int getSerialNumber() {
        return this.mSerialNumber;
    }

    public int getServiceCategory() {
        return this.mMessageIdentifier;
    }

    public int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    public DataCodingScheme getDataCodingSchemeStructedData() {
        return this.mDataCodingSchemeStructedData;
    }

    public int getPageIndex() {
        return this.mPageIndex;
    }

    public int getNumberOfPages() {
        return this.mNrOfPages;
    }

    public SmsCbEtwsInfo getEtwsInfo() {
        return this.mEtwsInfo;
    }

    public SmsCbCmasInfo getCmasInfo() {
        return this.mCmasInfo;
    }

    public boolean isEmergencyMessage() {
        int i = this.mMessageIdentifier;
        return i >= 4352 && i <= 6399;
    }

    private boolean isEtwsMessage() {
        return (this.mMessageIdentifier & SmsCbConstants.MESSAGE_ID_ETWS_TYPE_MASK) == 4352;
    }

    public boolean isEtwsPrimaryNotification() {
        return this.mFormat == 3;
    }

    public boolean isUmtsFormat() {
        return this.mFormat == 2;
    }

    private boolean isCmasMessage() {
        int i = this.mMessageIdentifier;
        return i >= 4370 && i <= 4400;
    }

    private boolean isEtwsPopupAlert() {
        return (this.mSerialNumber & 4096) != 0;
    }

    private boolean isEtwsEmergencyUserAlert() {
        return (this.mSerialNumber & 8192) != 0;
    }

    private int getEtwsWarningType() {
        return this.mMessageIdentifier - 4352;
    }

    private int getCmasMessageClass() {
        switch (this.mMessageIdentifier) {
            case 4370:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL_LANGUAGE /* 4383 */:
                return 0;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
                return 1;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                return 2;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY /* 4379 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY_LANGUAGE /* 4392 */:
                return 3;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST /* 4380 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST_LANGUAGE /* 4393 */:
                return 4;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE /* 4381 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE_LANGUAGE /* 4394 */:
                return 5;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE /* 4382 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE_LANGUAGE /* 4395 */:
                return 6;
            default:
                return -1;
        }
    }

    private int getCmasSeverity() {
        int i = this.mMessageIdentifier;
        switch (i) {
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
                return 0;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
                return 1;
            default:
                switch (i) {
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
                        return 0;
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                        return 1;
                    default:
                        return -1;
                }
        }
    }

    private int getCmasUrgency() {
        int i = this.mMessageIdentifier;
        switch (i) {
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
                return 0;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
                return 1;
            default:
                switch (i) {
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
                        return 0;
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                        return 1;
                    default:
                        return -1;
                }
        }
    }

    private int getCmasCertainty() {
        int i = this.mMessageIdentifier;
        switch (i) {
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
                return 0;
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
                return 1;
            default:
                switch (i) {
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
                        return 0;
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
                    case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                        return 1;
                    default:
                        return -1;
                }
        }
    }

    public String toString() {
        return "SmsCbHeader{GS=" + this.mGeographicalScope + ", serialNumber=0x" + Integer.toHexString(this.mSerialNumber) + ", messageIdentifier=0x" + Integer.toHexString(this.mMessageIdentifier) + ", format=" + this.mFormat + ", DCS=0x" + Integer.toHexString(this.mDataCodingScheme) + ", page " + this.mPageIndex + " of " + this.mNrOfPages + '}';
    }

    public static final class DataCodingScheme {
        public final int encoding;
        public final boolean hasLanguageIndicator;
        public final String language;

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0042, code lost:
        
            if (((r8 & 4) >> 2) == 1) goto L23;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public DataCodingScheme(int r8) {
            /*
                r7 = this;
                r7.<init>()
                r0 = r8 & 240(0xf0, float:3.36E-43)
                int r0 = r0 >> 4
                r1 = 9
                if (r0 == r1) goto L4c
                r1 = 14
                if (r0 == r1) goto L4c
                r1 = 15
                r2 = 2
                r3 = 1
                r4 = 0
                r5 = 0
                if (r0 == r1) goto L3f
                r6 = 3
                switch(r0) {
                    case 0: goto L35;
                    case 1: goto L2e;
                    case 2: goto L26;
                    case 3: goto L3c;
                    case 4: goto L1c;
                    case 5: goto L1c;
                    case 6: goto L4c;
                    case 7: goto L4c;
                    default: goto L1b;
                }
            L1b:
                goto L3c
            L1c:
                r8 = r8 & 12
                int r8 = r8 >> r2
                if (r8 == r3) goto L3d
                if (r8 == r2) goto L24
                goto L3c
            L24:
                r3 = r5
                goto L31
            L26:
                java.lang.String[] r0 = com.android.internal.telephony.gsm.SmsCbHeader.m8312$$Nest$sfgetLANGUAGE_CODES_GROUP_2()
                r8 = r8 & r1
                r4 = r0[r8]
                goto L3c
            L2e:
                r8 = r8 & r1
                if (r8 != r3) goto L33
            L31:
                r2 = r6
                goto L45
            L33:
                r2 = r3
                goto L45
            L35:
                java.lang.String[] r0 = com.android.internal.telephony.gsm.SmsCbHeader.m8311$$Nest$sfgetLANGUAGE_CODES_GROUP_0()
                r8 = r8 & r1
                r4 = r0[r8]
            L3c:
                r2 = r3
            L3d:
                r3 = r5
                goto L45
            L3f:
                r8 = r8 & 4
                int r8 = r8 >> r2
                if (r8 != r3) goto L3c
                goto L3d
            L45:
                r7.encoding = r2
                r7.language = r4
                r7.hasLanguageIndicator = r3
                return
            L4c:
                java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Unsupported GSM dataCodingScheme "
                r0.<init>(r1)
                r0.append(r8)
                java.lang.String r8 = r0.toString()
                r7.<init>(r8)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsCbHeader.DataCodingScheme.<init>(int):void");
        }
    }
}
