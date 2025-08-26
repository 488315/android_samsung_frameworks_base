package com.android.internal.alsa;

import android.app.jank.AppJankStats;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.Downloads;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class AlsaDevicesParser {
    protected static final boolean DEBUG = false;
    public static final int SCANSTATUS_EMPTY = 2;
    public static final int SCANSTATUS_FAIL = 1;
    public static final int SCANSTATUS_NOTSCANNED = -1;
    public static final int SCANSTATUS_SUCCESS = 0;
    private static final String TAG = "AlsaDevicesParser";
    private static final String kDevicesFilePath = "/proc/asound/devices";
    private static final int kEndIndex_CardNum = 8;
    private static final int kEndIndex_DeviceNum = 11;
    private static final int kIndex_CardDeviceField = 5;
    private static final int kStartIndex_CardNum = 6;
    private static final int kStartIndex_DeviceNum = 9;
    private static final int kStartIndex_Type = 14;
    private static LineTokenizer mTokenizer = new LineTokenizer(" :[]-");
    private boolean mHasCaptureDevices = false;
    private boolean mHasPlaybackDevices = false;
    private boolean mHasMIDIDevices = false;
    private int mScanStatus = -1;
    private final ArrayList<AlsaDeviceRecord> mDeviceRecords = new ArrayList<>();

    private void Log(String str) {
    }

    public int getDefaultDeviceNum(int i) {
        return 0;
    }

    public class AlsaDeviceRecord {
        public static final int kDeviceDir_Capture = 0;
        public static final int kDeviceDir_Playback = 1;
        public static final int kDeviceDir_Unknown = -1;
        public static final int kDeviceType_Audio = 0;
        public static final int kDeviceType_Control = 1;
        public static final int kDeviceType_MIDI = 2;
        public static final int kDeviceType_Unknown = -1;
        int mCardNum = -1;
        int mDeviceNum = -1;
        int mDeviceType = -1;
        int mDeviceDir = -1;

        public AlsaDeviceRecord() {
        }

        public boolean parse(String str) {
            int i = 0;
            int i2 = 0;
            while (true) {
                int iNextToken = AlsaDevicesParser.mTokenizer.nextToken(str, i);
                if (iNextToken == -1) {
                    return true;
                }
                int iNextDelimiter = AlsaDevicesParser.mTokenizer.nextDelimiter(str, iNextToken);
                int length = iNextDelimiter == -1 ? str.length() : iNextDelimiter;
                String strSubstring = str.substring(iNextToken, length);
                if (i2 == 1) {
                    this.mCardNum = Integer.parseInt(strSubstring);
                    if (str.charAt(length) != '-') {
                        i2++;
                    }
                } else if (i2 == 2) {
                    this.mDeviceNum = Integer.parseInt(strSubstring);
                } else if (i2 != 3) {
                    if (i2 != 4) {
                        if (i2 != 5) {
                            continue;
                        } else {
                            try {
                                if (strSubstring.equals("capture")) {
                                    this.mDeviceDir = 0;
                                    AlsaDevicesParser.this.mHasCaptureDevices = true;
                                } else if (strSubstring.equals(AppJankStats.WIDGET_STATE_PLAYBACK)) {
                                    this.mDeviceDir = 1;
                                    AlsaDevicesParser.this.mHasPlaybackDevices = true;
                                }
                            } catch (NumberFormatException unused) {
                                Slog.e(AlsaDevicesParser.TAG, "Failed to parse token " + i2 + " of /proc/asound/devices token: " + strSubstring);
                                return false;
                            }
                        }
                    } else if (strSubstring.equals("audio")) {
                        this.mDeviceType = 0;
                    } else if (strSubstring.equals("midi")) {
                        this.mDeviceType = 2;
                        AlsaDevicesParser.this.mHasMIDIDevices = true;
                    }
                } else if (!strSubstring.equals("digital")) {
                    if (strSubstring.equals(Downloads.Impl.COLUMN_CONTROL)) {
                        this.mDeviceType = 1;
                    } else {
                        strSubstring.equals("raw");
                    }
                }
                i2++;
                i = length;
            }
        }

        public String textFormat() {
            StringBuilder sb = new StringBuilder();
            sb.append(NavigationBarInflaterView.SIZE_MOD_START + this.mCardNum + ":" + this.mDeviceNum + NavigationBarInflaterView.SIZE_MOD_END);
            int i = this.mDeviceType;
            if (i == 0) {
                sb.append(" Audio");
            } else if (i == 1) {
                sb.append(" Control");
            } else if (i != 2) {
                sb.append(" N/A");
            } else {
                sb.append(" MIDI");
            }
            int i2 = this.mDeviceDir;
            if (i2 == 0) {
                sb.append(" Capture");
            } else if (i2 != 1) {
                sb.append(" N/A");
            } else {
                sb.append(" Playback");
            }
            return sb.toString();
        }
    }

    public boolean hasPlaybackDevices(int i) {
        Iterator<AlsaDeviceRecord> it = this.mDeviceRecords.iterator();
        while (it.hasNext()) {
            AlsaDeviceRecord next = it.next();
            if (next.mCardNum == i && next.mDeviceType == 0 && next.mDeviceDir == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCaptureDevices(int i) {
        Iterator<AlsaDeviceRecord> it = this.mDeviceRecords.iterator();
        while (it.hasNext()) {
            AlsaDeviceRecord next = it.next();
            if (next.mCardNum == i && next.mDeviceType == 0 && next.mDeviceDir == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMIDIDevices(int i) {
        Iterator<AlsaDeviceRecord> it = this.mDeviceRecords.iterator();
        while (it.hasNext()) {
            AlsaDeviceRecord next = it.next();
            if (next.mCardNum == i && next.mDeviceType == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isLineDeviceRecord(String str) {
        return str.charAt(5) == '[';
    }

    public int scan() throws IOException {
        this.mDeviceRecords.clear();
        try {
            FileReader fileReader = new FileReader(new File(kDevicesFilePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (isLineDeviceRecord(line)) {
                    AlsaDeviceRecord alsaDeviceRecord = new AlsaDeviceRecord();
                    alsaDeviceRecord.parse(line);
                    Slog.i(TAG, alsaDeviceRecord.textFormat());
                    this.mDeviceRecords.add(alsaDeviceRecord);
                }
            }
            fileReader.close();
            if (this.mDeviceRecords.size() > 0) {
                this.mScanStatus = 0;
            } else {
                this.mScanStatus = 2;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.mScanStatus = 1;
        } catch (IOException e2) {
            e2.printStackTrace();
            this.mScanStatus = 1;
        }
        return this.mScanStatus;
    }

    public int getScanStatus() {
        return this.mScanStatus;
    }
}
