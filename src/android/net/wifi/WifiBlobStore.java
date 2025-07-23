package android.net.wifi;

import android.os.ServiceManager;
import android.os.SystemProperties;
import android.security.legacykeystore.ILegacyKeystore;
import android.util.Log;
import com.android.internal.net.ConnectivityBlobStore;

/* loaded from: classes3.dex */
public class WifiBlobStore extends ConnectivityBlobStore {
    private static final String DB_NAME = "WifiBlobStore.db";
    private static final String LEGACY_KEYSTORE_SERVICE_NAME = "android.security.legacykeystore";
    private static final String TAG = "WifiBlobStore";
    private static WifiBlobStore sInstance;
    private static final boolean sIsVendorApiLevelGreaterThanT = isVendorApiLevelGreaterThanT();

    private WifiBlobStore() {
        super(DB_NAME);
    }

    private static boolean isVendorApiLevelGreaterThanT() {
        String[] strArr = {"ro.board.api_level", "ro.board.first_api_level", "ro.vndk.version"};
        for (int i = 0; i < 3; i++) {
            int i2 = SystemProperties.getInt(strArr[i], -1);
            if (i2 != -1) {
                Log.i(TAG, "Retrieved API level property, value=" + i2);
                return i2 > 33;
            }
        }
        Log.i(TAG, "No API level properties are defined");
        return true;
    }

    public static boolean supplicantCanAccessBlobstore() {
        return sIsVendorApiLevelGreaterThanT;
    }

    public static WifiBlobStore getInstance() {
        if (sInstance == null) {
            sInstance = new WifiBlobStore();
        }
        return sInstance;
    }

    public static ILegacyKeystore getLegacyKeystore() {
        return ILegacyKeystore.Stub.asInterface(ServiceManager.checkService(LEGACY_KEYSTORE_SERVICE_NAME));
    }
}
