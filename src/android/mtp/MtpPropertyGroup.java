package android.mtp;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.drm.DrmInfoRequest;
import android.media.MediaMetrics;
import android.mtp.MtpStorageManager;
import android.os.RemoteException;
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0163  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getPropertyList(ContentProviderClient contentProviderClient, String str, MtpStorageManager.MtpObject mtpObject, MtpPropertyList mtpPropertyList) {
        String string;
        int id = mtpObject.getId();
        String string2 = mtpObject.getPath().toString();
        Property[] propertyArr = this.mProperties;
        int length = propertyArr.length;
        Cursor cursorQuery = null;
        int i = 0;
        while (i < length) {
            Property property = propertyArr[i];
            if (property.column != -1 && cursorQuery == null) {
                try {
                    try {
                        try {
                            cursorQuery = contentProviderClient.query(MtpDatabase.getObjectPropertiesUri(mtpObject.getFormat(), str), this.mColumns, PATH_WHERE, new String[]{string2}, null, null);
                            if (cursorQuery != null && !cursorQuery.moveToNext()) {
                                cursorQuery.close();
                                cursorQuery = null;
                            }
                        } catch (RemoteException unused) {
                            Log.e(TAG, "Mediaprovider lookup failed");
                            Cursor cursor = cursorQuery;
                            switch (property.code) {
                                case MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                                    break;
                                case MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                                    break;
                                case MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                                    break;
                                case MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                                    break;
                                case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                                case MtpConstants.PROPERTY_NAME /* 56388 */:
                                case MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                                    break;
                                case MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                                case MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                                    break;
                                case MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                                    break;
                                case MtpConstants.PROPERTY_HIDDEN /* 56333 */:
                                    break;
                                case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                                    break;
                                case MtpConstants.PROPERTY_TRACK /* 56459 */:
                                    break;
                                case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                                    break;
                                case MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
                                case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                                    break;
                                case MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
                                case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
                                case MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                                    break;
                            }
                            i++;
                            cursorQuery = cursor;
                        }
                    } catch (IllegalArgumentException unused2) {
                        return MtpConstants.RESPONSE_INVALID_OBJECT_PROP_CODE;
                    }
                } catch (RemoteException unused3) {
                }
            }
            Cursor cursor2 = cursorQuery;
            switch (property.code) {
                case MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getStorageId());
                    break;
                case MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getFormat());
                    break;
                case MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                    mtpPropertyList.append(id, property.code, property.type, 0L);
                    break;
                case MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getSize());
                    break;
                case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                case MtpConstants.PROPERTY_NAME /* 56388 */:
                case MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                    mtpPropertyList.append(id, property.code, mtpObject.getName());
                    break;
                case MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                case MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                    mtpPropertyList.append(id, property.code, format_date_time(mtpObject.getModifiedTime()));
                    break;
                case MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                    int i2 = property.code;
                    long id2 = 0;
                    int i3 = property.type;
                    if (!mtpObject.getParent().isRoot()) {
                        id2 = mtpObject.getParent().getId();
                    }
                    mtpPropertyList.append(id, i2, i3, id2);
                    break;
                case MtpConstants.PROPERTY_HIDDEN /* 56333 */:
                    mtpPropertyList.append(id, property.code, 4, mtpObject.getName().startsWith(MediaMetrics.SEPARATOR) ? 1L : 0L);
                    break;
                case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                    mtpPropertyList.append(id, property.code, property.type, (mtpObject.getPath().toString().hashCode() << 32) + mtpObject.getModifiedTime());
                    break;
                case MtpConstants.PROPERTY_TRACK /* 56459 */:
                    mtpPropertyList.append(id, property.code, 4, (cursor2 != null ? cursor2.getInt(property.column) : 0) % 1000);
                    break;
                case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                    mtpPropertyList.append(id, property.code, Integer.toString(cursor2 != null ? cursor2.getInt(property.column) : 0) + "0101T000000");
                    break;
                case MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
                case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                    mtpPropertyList.append(id, property.code, 4, 0L);
                    break;
                case MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
                case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
                case MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                    mtpPropertyList.append(id, property.code, 6, 0L);
                    break;
                default:
                    long j = 0;
                    int i4 = property.type;
                    if (i4 == 0) {
                        mtpPropertyList.append(id, property.code, property.type, 0L);
                        break;
                    } else if (i4 == 65535) {
                        if (cursor2 == null) {
                            string = "";
                        } else {
                            string = cursor2.getString(property.column);
                        }
                        mtpPropertyList.append(id, property.code, string);
                        break;
                    } else {
                        if (cursor2 != null) {
                            j = cursor2.getLong(property.column);
                        }
                        mtpPropertyList.append(id, property.code, property.type, j);
                        break;
                    }
            }
            i++;
            cursorQuery = cursor2;
        }
        if (cursorQuery == null) {
            return 8193;
        }
        cursorQuery.close();
        return 8193;
    }
}
