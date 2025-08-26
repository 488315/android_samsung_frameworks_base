package android.media;

import android.os.Parcel;
import android.util.Log;
import android.util.MathUtils;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.TimeZone;

@Deprecated
/* loaded from: classes2.dex */
public class Metadata {
    public static final int ALBUM = 8;
    public static final int ALBUM_ART = 18;
    public static final int ANY = 0;
    public static final int ARTIST = 9;
    public static final int AUDIO_BIT_RATE = 21;
    public static final int AUDIO_CODEC = 26;
    public static final int AUDIO_SAMPLE_RATE = 23;
    public static final int AUTHOR = 10;
    public static final int BIT_RATE = 20;
    public static final int BOOLEAN_VAL = 3;
    public static final int BYTE_ARRAY_VAL = 7;
    public static final int CD_TRACK_MAX = 16;
    public static final int CD_TRACK_NUM = 15;
    public static final int COMMENT = 6;
    public static final int COMPOSER = 11;
    public static final int COPYRIGHT = 7;
    public static final int DATE = 13;
    public static final int DATE_VAL = 6;
    public static final int DOUBLE_VAL = 5;
    public static final int DRM_CRIPPLED = 31;
    public static final int DURATION = 14;
    private static final int FIRST_CUSTOM = 8192;
    public static final int GENRE = 12;
    public static final int INTEGER_VAL = 2;
    private static final int LAST_SYSTEM = 31;
    private static final int LAST_TYPE = 7;
    public static final int LONG_VAL = 4;
    public static final int MIME_TYPE = 25;
    public static final int NUM_TRACKS = 30;
    public static final int PAUSE_AVAILABLE = 1;
    public static final int RATING = 17;
    public static final int SEEK_AVAILABLE = 4;
    public static final int SEEK_BACKWARD_AVAILABLE = 2;
    public static final int SEEK_FORWARD_AVAILABLE = 3;
    public static final int STRING_VAL = 1;
    private static final String TAG = "media.Metadata";
    public static final int TITLE = 5;
    public static final int VIDEO_BIT_RATE = 22;
    public static final int VIDEO_CODEC = 27;
    public static final int VIDEO_FRAME = 19;
    public static final int VIDEO_FRAME_RATE = 24;
    public static final int VIDEO_HEIGHT = 28;
    public static final int VIDEO_WIDTH = 29;
    private static final int kInt32Size = 4;
    private static final int kMetaHeaderSize = 8;
    private static final int kMetaMarker = 1296389185;
    private static final int kRecordHeaderSize = 12;
    private final HashMap<Integer, Integer> mKeyToPosMap = new HashMap<>();
    private Parcel mParcel;
    public static final Set<Integer> MATCH_NONE = Collections.EMPTY_SET;
    public static final Set<Integer> MATCH_ALL = Collections.singleton(0);

    public static int firstCustomId() {
        return 8192;
    }

    public static int lastSytemId() {
        return 31;
    }

    public static int lastType() {
        return 7;
    }

    private boolean scanAllRecords(Parcel parcel, int i) {
        boolean z;
        this.mKeyToPosMap.clear();
        int i2 = 0;
        while (i > 12) {
            int iDataPosition = parcel.dataPosition();
            int i3 = parcel.readInt();
            if (i3 <= 12) {
                Log.e(TAG, "Record is too short");
            } else {
                int i4 = parcel.readInt();
                if (checkMetadataId(i4)) {
                    if (this.mKeyToPosMap.containsKey(Integer.valueOf(i4))) {
                        Log.e(TAG, "Duplicate metadata ID found");
                    } else {
                        this.mKeyToPosMap.put(Integer.valueOf(i4), Integer.valueOf(parcel.dataPosition()));
                        int i5 = parcel.readInt();
                        if (i5 <= 0 || i5 > 7) {
                            Log.e(TAG, "Invalid metadata type " + i5);
                        } else {
                            try {
                                parcel.setDataPosition(MathUtils.addOrThrow(iDataPosition, i3));
                                i -= i3;
                                i2++;
                            } catch (IllegalArgumentException e) {
                                Log.e(TAG, "Invalid size: " + e.getMessage());
                            }
                        }
                    }
                }
            }
            z = true;
        }
        z = false;
        if (i == 0 && !z) {
            return true;
        }
        Log.e(TAG, "Ran out of data or error on record " + i2);
        this.mKeyToPosMap.clear();
        return false;
    }

    public boolean parse(Parcel parcel) {
        if (parcel.dataAvail() < 8) {
            Log.e(TAG, "Not enough data " + parcel.dataAvail());
            return false;
        }
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        if (parcel.dataAvail() + 4 < i || i < 8) {
            Log.e(TAG, "Bad size " + i + " avail " + parcel.dataAvail() + " position " + iDataPosition);
            parcel.setDataPosition(iDataPosition);
            return false;
        }
        int i2 = parcel.readInt();
        if (i2 != kMetaMarker) {
            Log.e(TAG, "Marker missing " + Integer.toHexString(i2));
            parcel.setDataPosition(iDataPosition);
            return false;
        }
        if (!scanAllRecords(parcel, i - 8)) {
            parcel.setDataPosition(iDataPosition);
            return false;
        }
        this.mParcel = parcel;
        return true;
    }

    public Set<Integer> keySet() {
        return this.mKeyToPosMap.keySet();
    }

    public boolean has(int i) {
        if (!checkMetadataId(i)) {
            throw new IllegalArgumentException("Invalid key: " + i);
        }
        return this.mKeyToPosMap.containsKey(Integer.valueOf(i));
    }

    public String getString(int i) {
        checkType(i, 1);
        return this.mParcel.readString();
    }

    public int getInt(int i) {
        checkType(i, 2);
        return this.mParcel.readInt();
    }

    public boolean getBoolean(int i) {
        checkType(i, 3);
        return this.mParcel.readInt() == 1;
    }

    public long getLong(int i) {
        checkType(i, 4);
        return this.mParcel.readLong();
    }

    public double getDouble(int i) {
        checkType(i, 5);
        return this.mParcel.readDouble();
    }

    public byte[] getByteArray(int i) {
        checkType(i, 7);
        return this.mParcel.createByteArray();
    }

    public Date getDate(int i) {
        checkType(i, 6);
        long j = this.mParcel.readLong();
        String string = this.mParcel.readString();
        if (string.length() == 0) {
            return new Date(j);
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(string));
        calendar.setTimeInMillis(j);
        return calendar.getTime();
    }

    private boolean checkMetadataId(int i) {
        if (i > 0 && (31 >= i || i >= 8192)) {
            return true;
        }
        Log.e(TAG, "Invalid metadata ID " + i);
        return false;
    }

    private void checkType(int i, int i2) {
        this.mParcel.setDataPosition(this.mKeyToPosMap.get(Integer.valueOf(i)).intValue());
        int i3 = this.mParcel.readInt();
        if (i3 == i2) {
            return;
        }
        throw new IllegalStateException("Wrong type " + i2 + " but got " + i3);
    }
}
