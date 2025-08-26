package android.content.pm;

import android.content.Context;
import android.content.pm.IASKSManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.util.Xml;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ASKSManager {
    public static final int ASKS_UNKNOWN_BLOCKBYLIST = 1;
    public static final int ASKS_UNKNOWN_BLOCKED_BYRAMPART = 127;
    public static final int ASKS_UNKNOWN_BLOCK_DETAIL_GLOBAL_1 = 150;
    public static final int ASKS_UNKNOWN_BLOCK_DETAIL_GLOBAL_2 = 151;
    public static final int ASKS_UNKNOWN_DANGEROUSWARNING = 102;
    public static final int ASKS_UNKNOWN_EXCEPT = 0;
    public static final int ASKS_UNKNOWN_EXECUTE_ALLOW = 126;
    public static final int ASKS_UNKNOWN_EXECUTE_BLOCK = 125;
    public static final int ASKS_UNKNOWN_NO_TARGET = 129;
    public static final int ASKS_UNKNOWN_TARGET = 128;
    public static final int ASKS_UNKNOWN_TARGET_NO_POPUP = 130;
    public static final int ASKS_UNKNOWN_WARNING = 100;
    public static final int ASKS_UNKNOWN_WARNING_DETAIL_GLOBAL_1 = 140;
    public static final int ASKS_UNKNOWN_WARNING_DETAIL_GLOBAL_2 = 141;
    public static final int ASKS_UNKNOWN_WARNING_GLOBAL = 101;
    private static final String TAG = "ASKSManager";
    public static final String TYPE_DENY = "DENY";
    public static final String TYPE_REVOKE = "REVOKE";
    private static boolean hasBlockedPolicy = true;
    private static boolean isExactlyTargetDevice = false;
    static volatile IASKSManager sASKSManager;
    private static HashMap<String, String> mASKSRestrictedPackages = new HashMap<>();
    private static HashMap<Integer, String> mASKSPidMap = new HashMap<>();
    private static ArrayList<String> mIMEIList = new ArrayList<>();

    public static synchronized IASKSManager getASKSManager() {
        if (sASKSManager != null) {
            return sASKSManager;
        }
        IBinder service = ServiceManager.getService("asks");
        Slog.v(TAG, "default service binder = " + service);
        sASKSManager = IASKSManager.Stub.asInterface(service);
        Slog.v(TAG, "default service = " + sASKSManager);
        return sASKSManager;
    }

    public static boolean isRestrictedTarget(String str, String str2) {
        boolean z = false;
        if (str == null || str2 == null) {
            return false;
        }
        synchronized (mASKSRestrictedPackages) {
            if (mASKSRestrictedPackages.containsKey(str) && str2 != null && str2.equals(mASKSRestrictedPackages.get(str))) {
                z = true;
            }
        }
        return z;
    }

    public static void updateRestrictedTargetPackages(HashMap<String, String> map) {
        synchronized (mASKSRestrictedPackages) {
            mASKSRestrictedPackages.clear();
            mASKSRestrictedPackages.putAll(map);
        }
    }

    public static void addPackageWithPid(int i, String str) {
        synchronized (mASKSPidMap) {
            if (str != null) {
                mASKSPidMap.put(Integer.valueOf(i), str);
            }
        }
    }

    public static void removePackageWithPid(int i) {
        synchronized (mASKSPidMap) {
            if (mASKSPidMap.containsKey(Integer.valueOf(i))) {
                mASKSPidMap.remove(Integer.valueOf(i));
            }
        }
    }

    public static String getPackageNameFromPid(int i) {
        String str;
        synchronized (mASKSPidMap) {
            str = mASKSPidMap.get(Integer.valueOf(i));
        }
        return str;
    }

    public static String getASKSerrorDetail(int i) {
        switch (i) {
            case PackageManager.INSTALL_FAILED_BLOCKED_CROSS_DOWN /* -3006 */:
                return "INSTALL_FAILED_BLOCKED_CROSS_DOWN";
            case PackageManager.INSTALL_FAILED_ADP_VERSION_LOCKED /* -3005 */:
                return "INSTALL_FAILED_ADP_VERSION_LOCKED";
            case PackageManager.INSTALL_FAILED_AUTH_ASKSTOKEN /* -3004 */:
            case PackageManager.INSTALL_FAILED_MISSING_ASKSTOKEN /* -3003 */:
            case PackageManager.INSTALL_FAILED_MISSING_CERTIFICATION /* -3002 */:
                return "INSTALL_FAILED_MISSING_CERTIFICATION";
            case PackageManager.INSTALL_FAILED_REJECTED_BY_BUILDTYPE /* -3001 */:
                return "INSTALL_FAILED_REJECTED_BY_BUILDTYPE";
            case PackageManager.INSTALL_FAILED_REJECTED_BY_DATE /* -3000 */:
                return "INSTALL_FAILED_REJECTED_BY_DATE";
            default:
                return "Unknown Reason";
        }
    }

    public static boolean hasBlockPolicy() {
        return hasBlockedPolicy;
    }

    public static boolean isBlockTarget(int i, String str) throws XmlPullParserException, IOException {
        if (!isExactlyTargetDevice) {
            HashMap map = new HashMap();
            getASKSIDataFromXML(map);
            if (!map.isEmpty()) {
                if (sASKSManager != null && mIMEIList.isEmpty()) {
                    try {
                        List<String> iMEIList = sASKSManager.getIMEIList();
                        if (!iMEIList.isEmpty()) {
                            mIMEIList.addAll(iMEIList);
                        }
                    } catch (RemoteException unused) {
                    }
                }
                Iterator<String> it = mIMEIList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (map.containsKey(it.next())) {
                        Slog.i(TAG, "blocking target matched");
                        isExactlyTargetDevice = true;
                        break;
                    }
                }
            } else {
                Slog.i(TAG, "identMap is empty");
            }
        }
        if (isExactlyTargetDevice) {
            if ((i > 10000 && !"com.samsung.android.messaging".equals(str) && !"com.wsomacp".equals(str) && !"com.samsung.android.dialer".equals(str)) || str.contains(Context.CAMERA_SERVICE)) {
                return true;
            }
        } else if (!mIMEIList.isEmpty()) {
            File file = new File("/data/system/.aasa/AASApolicy/ASKSI.xml");
            if (file.exists()) {
                Slog.i(TAG, "This is not target device");
                file.delete();
            }
        }
        return false;
    }

    private static void getASKSIDataFromXML(HashMap<String, ArrayList<String>> map) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        arrayList.add("IDENT");
        arrayList.add("DUMMY");
        File file = new File("/data/system/.aasa/AASApolicy/ASKSI.xml");
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
            file.getParentFile().setReadable(true, false);
        }
        if (!file.exists()) {
            hasBlockedPolicy = false;
            return;
        }
        try {
            FileReader fileReader = new FileReader(file);
            try {
                XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                xmlPullParserNewPullParser.setInput(fileReader);
                String attributeValue = "";
                ArrayList<String> arrayList2 = null;
                for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
                    String name = xmlPullParserNewPullParser.getName();
                    if (eventType == 2) {
                        if (((String) arrayList.get(0)).equals(name)) {
                            if (xmlPullParserNewPullParser.getAttributeValue(0) != null) {
                                attributeValue = xmlPullParserNewPullParser.getAttributeValue(0);
                            }
                            arrayList2 = new ArrayList<>();
                        } else if (arrayList.contains(name) && xmlPullParserNewPullParser.getAttributeValue(0) != null && arrayList2 != null) {
                            arrayList2.add(xmlPullParserNewPullParser.getAttributeValue(0));
                        }
                    } else if (eventType == 3 && ((String) arrayList.get(0)).equals(name) && map != null) {
                        map.put(attributeValue, arrayList2);
                    }
                }
                fileReader.close();
            } catch (IOException e) {
                try {
                    fileReader.close();
                } catch (IOException unused) {
                }
                e.printStackTrace();
            } catch (XmlPullParserException e2) {
                try {
                    fileReader.close();
                } catch (IOException unused2) {
                }
                e2.printStackTrace();
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        }
    }
}
