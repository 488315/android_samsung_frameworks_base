package android.drm;

import java.util.HashMap;
import java.util.Iterator;

@Deprecated
/* loaded from: classes.dex */
public class DrmInfoRequest {
    public static final String ACCOUNT_ID = "account_id";
    public static final String SEM_DAY = "day";
    public static final String SEM_DRM_PATH = "drm_path";
    public static final String SEM_FAIL = "fail";
    public static final String SEM_HOUR = "hour";
    public static final String SEM_MINUTE = "minute";
    public static final String SEM_MONTH = "month";
    public static final String SEM_SECOND = "second";
    public static final String SEM_STATUS = "status";
    public static final String SEM_SUCCESS = "success";
    public static final int SEM_TYPE_CONVERT_DRM_FILE = 7;
    public static final int SEM_TYPE_GET_DECRYPT_IMAGE = 10;
    public static final int SEM_TYPE_GET_DRMFILE_INFO = 14;
    public static final int SEM_TYPE_GET_OPTION_MENU = 16;
    public static final int SEM_TYPE_GET_PERMISSION_TYPE = 15;
    public static final int SEM_TYPE_HANDLE_TVOUT = 11;
    public static final int SEM_TYPE_HANDLE_TVOUT_UNPLUGGED = 12;
    public static final int SEM_TYPE_IS_CONVERTED_FL = 17;
    public static final int SEM_TYPE_SET_SECURE_CLOCK = 35;
    public static final int SEM_TYPE_UPDATE_SECURE_CLOCK = 36;
    public static final String SEM_YEAR = "year";
    public static final String SUBSCRIPTION_ID = "subscription_id";
    public static final int TYPE_REGISTRATION_INFO = 1;
    public static final int TYPE_RIGHTS_ACQUISITION_INFO = 3;
    public static final int TYPE_RIGHTS_ACQUISITION_PROGRESS_INFO = 4;
    public static final int TYPE_UNREGISTRATION_INFO = 2;
    private final int mInfoType;
    private final String mMimeType;
    private final HashMap<String, Object> mRequestInformation = new HashMap<>();

    static boolean isValidType(int i) {
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 7 && i != 35 && i != 36) {
            switch (i) {
                default:
                    switch (i) {
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            break;
                        default:
                            return false;
                    }
                case 10:
                case 11:
                case 12:
                    return true;
            }
        }
        return true;
    }

    public DrmInfoRequest(int i, String str) {
        this.mInfoType = i;
        this.mMimeType = str;
        if (isValid()) {
            return;
        }
        throw new IllegalArgumentException("infoType: " + i + ",mimeType: " + str);
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public int getInfoType() {
        return this.mInfoType;
    }

    public void put(String str, Object obj) {
        this.mRequestInformation.put(str, obj);
    }

    public Object get(String str) {
        return this.mRequestInformation.get(str);
    }

    public Iterator<String> keyIterator() {
        return this.mRequestInformation.keySet().iterator();
    }

    public Iterator<Object> iterator() {
        return this.mRequestInformation.values().iterator();
    }

    boolean isValid() {
        String str = this.mMimeType;
        return (str == null || str.equals("") || this.mRequestInformation == null || !isValidType(this.mInfoType)) ? false : true;
    }
}
