package android.mtp;

import android.drm.DrmInfoRequest;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes3.dex */
class MtpPropertyGroup {
    private static final String PATH_WHERE = "_data=?";
    private static final String TAG = "MtpPropertyGroup";
    private String[] mColumns;
    private final Property[] mProperties;

    private native String format_date_time(long j);

    private class Property {
        int code;
        int column;
        int type;

        Property(MtpPropertyGroup mtpPropertyGroup, int i, int i2, int i3) {
            this.code = i;
            this.type = i2;
            this.column = i3;
        }
    }

    public MtpPropertyGroup(int[] iArr) {
        int length = iArr.length;
        ArrayList<String> arrayList = new ArrayList<>(length);
        arrayList.add("_id");
        this.mProperties = new Property[length];
        for (int i = 0; i < length; i++) {
            this.mProperties[i] = createProperty(iArr[i], arrayList);
        }
        int size = arrayList.size();
        this.mColumns = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            this.mColumns[i2] = arrayList.get(i2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Property createProperty(int i, ArrayList<String> arrayList) {
        int i2 = 6;
        String str = null;
        switch (i) {
            case MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
            case MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
            case MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
            case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
            case MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                break;
            case MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
            case MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
            case MtpConstants.PROPERTY_HIDDEN /* 56333 */:
            case MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
            case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                i2 = 4;
                break;
            case MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                i2 = 8;
                break;
            case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
            case MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
            case MtpConstants.PROPERTY_NAME /* 56388 */:
            case MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
            case MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                i2 = 65535;
                break;
            case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                i2 = 10;
                break;
            case MtpConstants.PROPERTY_ARTIST /* 56390 */:
                str = "artist";
                i2 = 65535;
                break;
            case MtpConstants.PROPERTY_DESCRIPTION /* 56392 */:
                str = "description";
                i2 = 65535;
                break;
            case MtpConstants.PROPERTY_DURATION /* 56457 */:
                str = "duration";
                break;
            case MtpConstants.PROPERTY_TRACK /* 56459 */:
                str = "track";
                i2 = 4;
                break;
            case MtpConstants.PROPERTY_GENRE /* 56460 */:
                str = "genre";
                i2 = 65535;
                break;
            case MtpConstants.PROPERTY_COMPOSER /* 56470 */:
                str = "composer";
                i2 = 65535;
                break;
            case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                str = DrmInfoRequest.SEM_YEAR;
                i2 = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_NAME /* 56474 */:
                str = "album";
                i2 = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_ARTIST /* 56475 */:
                str = "album_artist";
                i2 = 65535;
                break;
            default:
                Log.e(TAG, "unsupported property " + i);
                i2 = 0;
                break;
        }
        if (str != null) {
            arrayList.add(str);
            return new Property(this, i, i2, arrayList.size() - 1);
        }
        return new Property(this, i, i2, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0163  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getPropertyList(android.content.ContentProviderClient r23, java.lang.String r24, android.mtp.MtpStorageManager.MtpObject r25, android.mtp.MtpPropertyList r26) {
        /*
            Method dump skipped, instructions count: 524
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpPropertyGroup.getPropertyList(android.content.ContentProviderClient, java.lang.String, android.mtp.MtpStorageManager$MtpObject, android.mtp.MtpPropertyList):int");
    }
}
