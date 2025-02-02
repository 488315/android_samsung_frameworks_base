package com.samsung.android.biometrics.app.setting.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.widget.LockscreenCredential;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* loaded from: classes.dex */
public final class UCMUtils {
    private static final boolean DBG = Debug.semIsProductDev();

    public static LockscreenCredential generatePassword(int i, String str) {
        IUcmService uCMService = getUCMService();
        if (uCMService == null) {
            if (DBG) {
                Log.e("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "failed to get UCM service");
            }
            return null;
        }
        try {
            Bundle generateKeyguardPassword = uCMService.generateKeyguardPassword(i, str, (Bundle) null);
            if (generateKeyguardPassword == null) {
                return null;
            }
            return LockscreenCredential.createSmartcardPassword(generateKeyguardPassword.getByteArray("bytearrayresponse"));
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void getDataFromServiceResponse(int[] iArr, Bundle bundle) {
        iArr[3] = bundle.getInt("minPinLength", -1);
        iArr[4] = bundle.getInt("maxPinLength", -1);
        iArr[5] = bundle.getInt("minPukLength", -1);
        iArr[6] = bundle.getInt("maxPukLength", -1);
        if (DBG) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "pin min = " + iArr[3] + " pin max = " + iArr[4] + " puk min = " + iArr[5] + " puk max = " + iArr[6]);
        }
    }

    private static int[] getDefaultStatus() {
        return new int[]{132, -1, -1, -1, -1, -1, -1};
    }

    public static String getErrorMessage(Context context, int i) {
        String str = "\n(" + String.format("0x%08X", Integer.valueOf(i)) + ")";
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                break;
            default:
                switch (i) {
                    case 257:
                    case 258:
                    case 259:
                    case 260:
                    case 261:
                    case 262:
                    case 263:
                    case 264:
                    case 265:
                    case 266:
                    case 267:
                    case 268:
                    case 269:
                    case 270:
                    case 271:
                        break;
                    default:
                        switch (i) {
                            case 4096:
                            case 8191:
                            case 150994944:
                            case 201326848:
                            case 201327104:
                                break;
                            case 16777472:
                            case 16777728:
                            case 16777984:
                            case 16778240:
                            case 33554945:
                                break;
                            case 134217728:
                                return context.getResources().getString(R.string.ucm_smartcard_error) + str;
                            default:
                                switch (i) {
                                    case 33554689:
                                    case 33554690:
                                        break;
                                    default:
                                        switch (i) {
                                            case 33555201:
                                            case 33555202:
                                            case 33555203:
                                            case 33555204:
                                            case 33555205:
                                            case 33555206:
                                                break;
                                            default:
                                                switch (i) {
                                                    case 33555457:
                                                    case 33555458:
                                                    case 33555459:
                                                    case 33555460:
                                                    case 33555461:
                                                    case 33555462:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case 33555713:
                                                            case 33555714:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case 50331648:
                                                                    case 50331649:
                                                                    case 50331650:
                                                                    case 50331651:
                                                                    case 50331652:
                                                                        break;
                                                                    default:
                                                                        if (134217728 >= i || 134283264 <= i) {
                                                                            return context.getResources().getString(R.string.ucm_unknown_error) + str;
                                                                        }
                                                                        String format = String.format("0x%08X", Integer.valueOf(i));
                                                                        return context.getResources().getString(R.string.ucm_smartcard_error) + "\n(" + format.substring(format.length() - 4, format.length()) + ")";
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                        return context.getResources().getString(R.string.ucm_communication_error) + str;
                }
        }
        return context.getResources().getString(R.string.ucm_internal_error) + str;
    }

    private static int[] getKeyguardStatusFromServiceResponse(Bundle bundle) {
        int[] defaultStatus = getDefaultStatus();
        bundle.getString("miscInfo", "NONE");
        defaultStatus[0] = bundle.getInt("state", -1);
        defaultStatus[1] = bundle.getInt("remainCnt", -1);
        defaultStatus[2] = bundle.getInt("errorresponse", -1);
        if (DBG) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "remain count = " + defaultStatus[1] + "state = " + defaultStatus[0] + "error code = " + defaultStatus[2]);
        }
        return defaultStatus;
    }

    public static int[] getStatus() {
        String uCMUri = getUCMUri();
        int[] defaultStatus = getDefaultStatus();
        IUcmService uCMService = getUCMService();
        boolean z = DBG;
        if (uCMService == null) {
            if (!z) {
                return defaultStatus;
            }
            Log.e("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "failed to get UCM service");
            return defaultStatus;
        }
        try {
            Bundle status = uCMService.getStatus(uCMUri);
            if (status == null) {
                return defaultStatus;
            }
            if (z) {
                Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "status not null");
            }
            defaultStatus = getKeyguardStatusFromServiceResponse(status);
            getDataFromServiceResponse(defaultStatus, status);
            return defaultStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultStatus;
        }
    }

    public static String getUCMKeyguardStorageForUser() {
        return getUCMKeyguardStorageForUser(UserHandle.myUserId());
    }

    public static String getUCMKeyguardVendorName() {
        String uCMKeyguardStorageForUser = getUCMKeyguardStorageForUser(UserHandle.myUserId());
        if (uCMKeyguardStorageForUser == null) {
            return "";
        }
        if (DBG) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "UCM Keyguard : ".concat(uCMKeyguardStorageForUser));
        }
        int lastIndexOf = uCMKeyguardStorageForUser.lastIndexOf(":");
        return lastIndexOf != -1 ? uCMKeyguardStorageForUser.substring(lastIndexOf + 1, uCMKeyguardStorageForUser.length()) : uCMKeyguardStorageForUser;
    }

    private static IUcmService getUCMService() {
        return IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
    }

    public static String getUCMUri() {
        String uCMKeyguardStorageForUser = getUCMKeyguardStorageForUser();
        if (uCMKeyguardStorageForUser == null) {
            return null;
        }
        String uri = UniversalCredentialUtil.getUri(uCMKeyguardStorageForUser, "");
        if (DBG) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "getStatus, uri: " + uri);
        }
        return uri;
    }

    public static boolean isSupportBiometricForUCM() {
        IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.e("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "Failed to get UCM Service");
            return false;
        }
        try {
            Bundle agentInfo = asInterface.getAgentInfo(getUCMUri());
            if (agentInfo == null) {
                return false;
            }
            return agentInfo.getBoolean("isSupportBiometricForUCM", false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUCMKeyguardEnabled() {
        int myUserId = UserHandle.myUserId();
        if (getUCMKeyguardStorageForUser(myUserId) == null) {
            return false;
        }
        if (DBG) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "UCM Keyguard is enabled for user [" + myUserId + "]");
        }
        return true;
    }

    public static int[] verfiyODEPin(int i, String str, String str2) {
        int[] defaultStatus = getDefaultStatus();
        IUcmService uCMService = getUCMService();
        boolean z = DBG;
        if (uCMService == null) {
            if (z) {
                Log.e("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "failed to get UCM service");
            }
            return defaultStatus;
        }
        try {
            Bundle verifyPin = uCMService.verifyPin(i, str2, str, (Bundle) null);
            if (verifyPin != null) {
                if (z) {
                    Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "status not null");
                }
                int[] keyguardStatusFromServiceResponse = getKeyguardStatusFromServiceResponse(verifyPin);
                try {
                    int i2 = keyguardStatusFromServiceResponse[0];
                    if (i2 != 131) {
                        if (i2 != 132) {
                            if (z) {
                                Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "Mostly has gone to PUK case");
                            }
                        } else if (z) {
                            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "PIN verfication failed");
                        }
                    } else if (z) {
                        Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "PIN verfication succeed");
                    }
                    return keyguardStatusFromServiceResponse;
                } catch (Exception e) {
                    defaultStatus = keyguardStatusFromServiceResponse;
                    e = e;
                    e.printStackTrace();
                    return defaultStatus;
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        return defaultStatus;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x001f, code lost:
    
        if (r4.trim().length() > 0) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int[] verifyPUK(String str, String str2, String str3) {
        String trim;
        int i;
        boolean z = DBG;
        if (z) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "verifyPUK enterd");
        }
        int[] defaultStatus = getDefaultStatus();
        String str4 = null;
        if (str2 != null) {
            try {
                trim = str2.trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        trim = null;
        if (z) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "tempPUK : " + trim);
        }
        if (str3 != null) {
            try {
                String trim2 = str3.trim();
                if (trim2.trim().length() > 0) {
                    str4 = trim2;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (z) {
            Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "tempPIN : " + str4);
        }
        if (trim != null && str4 != null) {
            IUcmService uCMService = getUCMService();
            if (uCMService == null) {
                if (z) {
                    Log.e("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "failed to get UCM service");
                }
                return defaultStatus;
            }
            try {
                Bundle verifyPuk = uCMService.verifyPuk(str, str2, str3);
                if (verifyPuk != null && ((i = (defaultStatus = getKeyguardStatusFromServiceResponse(verifyPuk))[0]) == 132 || i == 131)) {
                    defaultStatus[0] = 0;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return defaultStatus;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0059, code lost:
    
        android.util.Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "getUCMKeyguardStorageForUser. UCM Keyguard disabled for user [" + r9 + "]");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String getUCMKeyguardStorageForUser(int i) {
        IUcmService uCMService = getUCMService();
        boolean z = DBG;
        if (uCMService == null) {
            if (z) {
                Log.e("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "failed to get UCM service");
            }
            return null;
        }
        try {
            String keyguardStorageForCurrentUser = uCMService.getKeyguardStorageForCurrentUser(i);
            if (!TextUtils.isEmpty(keyguardStorageForCurrentUser) && !keyguardStorageForCurrentUser.equalsIgnoreCase("none")) {
                if (z) {
                    Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "getUCMKeyguardStorageForUser. UCM Keyguard enabled for user [" + i + "]");
                }
                if (z) {
                    Log.d("com.samsung.android.biometrics.app.setting.knox.UCMUtils", "getUCMKeyguardStorageForUser. keyguardCSName [" + keyguardStorageForCurrentUser + "]");
                }
                return keyguardStorageForCurrentUser;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
