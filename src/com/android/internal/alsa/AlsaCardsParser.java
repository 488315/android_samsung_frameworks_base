package com.android.internal.alsa;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class AlsaCardsParser {
    protected static final boolean DEBUG = true;
    public static final int SCANSTATUS_EMPTY = 2;
    public static final int SCANSTATUS_FAIL = 1;
    public static final int SCANSTATUS_NOTSCANNED = -1;
    public static final int SCANSTATUS_SUCCESS = 0;
    private static final String TAG = "AlsaCardsParser";
    private static final String kAlsaFolderPath = "/proc/asound";
    private static final String kCardsFilePath = "/proc/asound/cards";
    private static final String kDeviceAddressPrefix = "/dev/bus/usb/";
    private static LineTokenizer mTokenizer = new LineTokenizer(" :[]");
    private ArrayList<AlsaCardRecord> mCardRecords = new ArrayList<>();
    private int mScanStatus = -1;

    public class AlsaCardRecord {
        private static final String TAG = "AlsaCardRecord";
        private static final String kUsbCardKeyStr = "at usb-";
        int mCardNum = -1;
        String mField1 = "";
        String mCardName = "";
        String mCardDescription = "";
        private String mUsbDeviceAddress = null;

        public AlsaCardRecord(AlsaCardsParser alsaCardsParser) {
        }

        public int getCardNum() {
            return this.mCardNum;
        }

        public String getCardName() {
            return this.mCardName;
        }

        public String getCardDescription() {
            return this.mCardDescription;
        }

        public void setDeviceAddress(String str) {
            this.mUsbDeviceAddress = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean parse(String str, int i) {
            int iNextToken;
            int iIndexOf;
            if (i == 0) {
                int iNextToken2 = AlsaCardsParser.mTokenizer.nextToken(str, 0);
                int iNextDelimiter = AlsaCardsParser.mTokenizer.nextDelimiter(str, iNextToken2);
                try {
                    this.mCardNum = Integer.parseInt(str.substring(iNextToken2, iNextDelimiter));
                    int iNextToken3 = AlsaCardsParser.mTokenizer.nextToken(str, iNextDelimiter);
                    int iNextDelimiter2 = AlsaCardsParser.mTokenizer.nextDelimiter(str, iNextToken3);
                    this.mField1 = str.substring(iNextToken3, iNextDelimiter2);
                    this.mCardName = str.substring(AlsaCardsParser.mTokenizer.nextToken(str, iNextDelimiter2));
                } catch (NumberFormatException unused) {
                    Slog.e(TAG, "Failed to parse line " + i + " of /proc/asound/cards: " + str.substring(iNextToken2, iNextDelimiter));
                    return false;
                }
            } else if (i == 1 && (iNextToken = AlsaCardsParser.mTokenizer.nextToken(str, 0)) != -1 && (iIndexOf = str.indexOf(kUsbCardKeyStr)) != -1) {
                this.mCardDescription = str.substring(iNextToken, iIndexOf - 1);
            }
            return true;
        }

        boolean isUsb() {
            return this.mUsbDeviceAddress != null;
        }

        public String textFormat() {
            return this.mCardName + " : " + this.mCardDescription + " [addr:" + this.mUsbDeviceAddress + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public void log(int i) {
            Slog.d(TAG, "" + i + " [" + this.mCardNum + " " + this.mCardName + " : " + this.mCardDescription + " usb:" + isUsb());
        }
    }

    public int scan() throws IOException {
        Slog.d(TAG, "AlsaCardsParser.scan()....");
        this.mCardRecords = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(new File(kCardsFilePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                AlsaCardRecord alsaCardRecord = new AlsaCardRecord(this);
                Slog.d(TAG, "  " + line);
                alsaCardRecord.parse(line, 0);
                String line2 = bufferedReader.readLine();
                if (line2 == null) {
                    break;
                }
                Slog.d(TAG, "  " + line2);
                alsaCardRecord.parse(line2, 1);
                File file = new File(("/proc/asound/card" + alsaCardRecord.mCardNum) + "/usbbus");
                if (file.exists()) {
                    FileReader fileReader2 = new FileReader(file);
                    String line3 = new BufferedReader(fileReader2).readLine();
                    if (line3 != null) {
                        alsaCardRecord.setDeviceAddress(kDeviceAddressPrefix + line3);
                    }
                    fileReader2.close();
                }
                this.mCardRecords.add(alsaCardRecord);
            }
            fileReader.close();
            if (this.mCardRecords.size() > 0) {
                this.mScanStatus = 0;
            } else {
                this.mScanStatus = 2;
            }
        } catch (FileNotFoundException unused) {
            this.mScanStatus = 1;
        } catch (IOException unused2) {
            this.mScanStatus = 1;
        }
        Slog.d(TAG, "  status:" + this.mScanStatus);
        return this.mScanStatus;
    }

    public int getScanStatus() {
        return this.mScanStatus;
    }

    public AlsaCardRecord findCardNumFor(String str) {
        Iterator<AlsaCardRecord> it = this.mCardRecords.iterator();
        while (it.hasNext()) {
            AlsaCardRecord next = it.next();
            if (next.isUsb() && next.mUsbDeviceAddress.equals(str)) {
                return next;
            }
        }
        return null;
    }

    private void Log(String str) {
        Slog.d(TAG, str);
        Iterator<AlsaCardRecord> it = this.mCardRecords.iterator();
        while (it.hasNext()) {
            Slog.d(TAG, it.next().textFormat());
        }
    }
}
