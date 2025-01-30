package android.mtp;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.drm.DrmInfoRequest;
import android.media.MediaMetrics;
import android.mtp.MtpStorageManager;
import android.p009os.RemoteException;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class MtpPropertyGroup {
    private static final String PATH_WHERE = "_data=?";
    private static final String TAG = MtpPropertyGroup.class.getSimpleName();
    private String[] mColumns;
    private final Property[] mProperties;

    private native String format_date_time(long j);

    private class Property {
        int code;
        int column;
        int type;

        Property(int code, int type, int column) {
            this.code = code;
            this.type = type;
            this.column = column;
        }
    }

    public MtpPropertyGroup(int[] properties) {
        int count = properties.length;
        ArrayList<String> columns = new ArrayList<>(count);
        columns.add("_id");
        this.mProperties = new Property[count];
        for (int i = 0; i < count; i++) {
            this.mProperties[i] = createProperty(properties[i], columns);
        }
        int count2 = columns.size();
        this.mColumns = new String[count2];
        for (int i2 = 0; i2 < count2; i2++) {
            this.mColumns[i2] = columns.get(i2);
        }
    }

    private Property createProperty(int code, ArrayList<String> columns) {
        int type;
        String column = null;
        switch (code) {
            case MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                type = 6;
                break;
            case MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                type = 4;
                break;
            case MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                type = 4;
                break;
            case MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                type = 8;
                break;
            case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                type = 6;
                break;
            case MtpConstants.PROPERTY_HIDDEN /* 56333 */:
                type = 4;
                break;
            case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                type = 10;
                break;
            case MtpConstants.PROPERTY_NAME /* 56388 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ARTIST /* 56390 */:
                column = "artist";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DESCRIPTION /* 56392 */:
                column = "description";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DURATION /* 56457 */:
                column = "duration";
                type = 6;
                break;
            case MtpConstants.PROPERTY_TRACK /* 56459 */:
                column = "track";
                type = 4;
                break;
            case MtpConstants.PROPERTY_GENRE /* 56460 */:
                column = "genre";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_COMPOSER /* 56470 */:
                column = "composer";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                column = DrmInfoRequest.SEM_YEAR;
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_NAME /* 56474 */:
                column = "album";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_ARTIST /* 56475 */:
                column = "album_artist";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
            case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                type = 4;
                break;
            case MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
            case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
            case MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                type = 6;
                break;
            default:
                type = 0;
                Log.m96e(TAG, "unsupported property " + code);
                break;
        }
        if (column != null) {
            columns.add(column);
            return new Property(code, type, columns.size() - 1);
        }
        return new Property(code, type, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x018b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getPropertyList(ContentProviderClient contentProviderClient, String str, MtpStorageManager.MtpObject mtpObject, MtpPropertyList mtpPropertyList) {
        int i;
        long j;
        Cursor cursor = null;
        int id = mtpObject.getId();
        String path = mtpObject.getPath().toString();
        Property[] propertyArr = this.mProperties;
        int length = propertyArr.length;
        int i2 = 0;
        while (true) {
            Cursor cursor2 = cursor;
            if (i2 >= length) {
                if (cursor2 != null) {
                    cursor2.close();
                    return 8193;
                }
                return 8193;
            }
            Property property = propertyArr[i2];
            if (property.column != -1 && cursor2 == null) {
                try {
                } catch (RemoteException e) {
                } catch (IllegalArgumentException e2) {
                    return MtpConstants.RESPONSE_INVALID_OBJECT_PROP_CODE;
                }
                try {
                    cursor2 = contentProviderClient.query(MtpDatabase.getObjectPropertiesUri(mtpObject.getFormat(), str), this.mColumns, PATH_WHERE, new String[]{path}, null, null);
                    if (cursor2 != null && !cursor2.moveToNext()) {
                        cursor2.close();
                        cursor2 = null;
                    }
                    cursor = cursor2;
                } catch (RemoteException e3) {
                    Log.m96e(TAG, "Mediaprovider lookup failed");
                    cursor = cursor2;
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
                    i2++;
                } catch (IllegalArgumentException e4) {
                    return MtpConstants.RESPONSE_INVALID_OBJECT_PROP_CODE;
                }
            } else {
                cursor = cursor2;
            }
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
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getParent().isRoot() ? 0L : mtpObject.getParent().getId());
                    break;
                case MtpConstants.PROPERTY_HIDDEN /* 56333 */:
                    mtpPropertyList.append(id, property.code, 4, mtpObject.getName().startsWith(MediaMetrics.SEPARATOR) ? 1L : 0L);
                    break;
                case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                    mtpPropertyList.append(id, property.code, property.type, (mtpObject.getPath().toString().hashCode() << 32) + mtpObject.getModifiedTime());
                    break;
                case MtpConstants.PROPERTY_TRACK /* 56459 */:
                    if (cursor == null) {
                        i = 0;
                    } else {
                        i = cursor.getInt(property.column);
                    }
                    mtpPropertyList.append(id, property.code, 4, i % 1000);
                    break;
                case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                    int i3 = 0;
                    if (cursor != null) {
                        i3 = cursor.getInt(property.column);
                    }
                    mtpPropertyList.append(id, property.code, Integer.toString(i3) + "0101T000000");
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
                    switch (property.type) {
                        case 0:
                            mtpPropertyList.append(id, property.code, property.type, 0L);
                            break;
                        case 65535:
                            String str2 = "";
                            if (cursor != null) {
                                str2 = cursor.getString(property.column);
                            }
                            mtpPropertyList.append(id, property.code, str2);
                            break;
                        default:
                            if (cursor == null) {
                                j = 0;
                            } else {
                                j = cursor.getLong(property.column);
                            }
                            mtpPropertyList.append(id, property.code, property.type, j);
                            break;
                    }
            }
            i2++;
        }
    }
}
