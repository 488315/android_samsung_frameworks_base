package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

public abstract class UCMUtils {
    public static final boolean DBG = Debug.semIsProductDev();
    public static String mChildSafeMsg = null;

    public static void getDataFromServiceResponse(int[] iArr, Bundle bundle) {
        iArr[3] = bundle.getInt("minPinLength", -1);
        iArr[4] = bundle.getInt("maxPinLength", -1);
        iArr[5] = bundle.getInt("minPukLength", -1);
        iArr[6] = bundle.getInt("maxPukLength", -1);
        if (DBG) {
            Log.d(
                    "com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils",
                    "pin min = "
                            + iArr[3]
                            + " pin max = "
                            + iArr[4]
                            + " puk min = "
                            + iArr[5]
                            + " puk max = "
                            + iArr[6]);
        }
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
                                return context.getResources()
                                                .getString(R.string.ucm_smartcard_error)
                                        + str;
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
                                                                        if (134217728 >= i
                                                                                || 134283264 <= i) {
                                                                            return context.getResources()
                                                                                            .getString(
                                                                                                    R
                                                                                                            .string
                                                                                                            .ucm_unknown_error)
                                                                                    + str;
                                                                        }
                                                                        String format =
                                                                                String.format(
                                                                                        "0x%08X",
                                                                                        Integer
                                                                                                .valueOf(
                                                                                                        i));
                                                                        return context.getResources()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .ucm_smartcard_error)
                                                                                + "\n("
                                                                                + format.substring(
                                                                                        format
                                                                                                        .length()
                                                                                                - 4,
                                                                                        format
                                                                                                .length())
                                                                                + ")";
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                        return context.getResources().getString(R.string.ucm_communication_error)
                                + str;
                }
        }
        return context.getResources().getString(R.string.ucm_internal_error) + str;
    }

    public static int[] getKeyguardStatusFromServiceResponse(Bundle bundle) {
        int[] iArr = {132, -1, -1, -1, -1, -1, -1};
        bundle.getString("miscInfo", "NONE");
        mChildSafeMsg = bundle.getString("lockscreen_message", null);
        iArr[0] = bundle.getInt("state", -1);
        iArr[1] = bundle.getInt("remainCnt", -1);
        iArr[2] = bundle.getInt("errorresponse", -1);
        if (DBG) {
            Log.d(
                    "com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils",
                    "remain count = " + iArr[1] + "state = " + iArr[0] + "error code = " + iArr[2]);
        }
        return iArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0062, code lost:

       android.util.Log.d("com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils", "getUCMKeyguardStorageForUser. UCM Keyguard disabled for user [" + r9 + "]");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getUCMKeyguardStorageForUser(int r9) {
        /*
            java.lang.String r0 = "getUCMKeyguardStorageForUser. UCM Keyguard disabled for user ["
            java.lang.String r1 = "getUCMKeyguardStorageForUser. keyguardCSName ["
            java.lang.String r2 = "getUCMKeyguardStorageForUser. UCM Keyguard enabled for user ["
            java.lang.String r3 = "com.samsung.ucs.ucsservice"
            android.os.IBinder r3 = android.os.ServiceManager.getService(r3)
            com.samsung.android.knox.ucm.core.IUcmService r3 = com.samsung.android.knox.ucm.core.IUcmService.Stub.asInterface(r3)
            r4 = 0
            boolean r5 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils.DBG
            java.lang.String r6 = "com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils"
            if (r3 != 0) goto L1f
            if (r5 == 0) goto L1e
            java.lang.String r9 = "failed to get UCM service"
            android.util.Log.e(r6, r9)
        L1e:
            return r4
        L1f:
            java.lang.String r3 = r3.getKeyguardStorageForCurrentUser(r9)     // Catch: java.lang.Exception -> L49
            boolean r7 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Exception -> L49
            java.lang.String r8 = "]"
            if (r7 != 0) goto L60
            java.lang.String r7 = "none"
            boolean r7 = r3.equalsIgnoreCase(r7)     // Catch: java.lang.Exception -> L49
            if (r7 == 0) goto L34
            goto L60
        L34:
            if (r5 == 0) goto L4b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L49
            r0.<init>(r2)     // Catch: java.lang.Exception -> L49
            r0.append(r9)     // Catch: java.lang.Exception -> L49
            r0.append(r8)     // Catch: java.lang.Exception -> L49
            java.lang.String r9 = r0.toString()     // Catch: java.lang.Exception -> L49
            android.util.Log.d(r6, r9)     // Catch: java.lang.Exception -> L49
            goto L4b
        L49:
            r9 = move-exception
            goto L75
        L4b:
            if (r5 == 0) goto L5f
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L49
            r9.<init>(r1)     // Catch: java.lang.Exception -> L49
            r9.append(r3)     // Catch: java.lang.Exception -> L49
            r9.append(r8)     // Catch: java.lang.Exception -> L49
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> L49
            android.util.Log.d(r6, r9)     // Catch: java.lang.Exception -> L49
        L5f:
            return r3
        L60:
            if (r5 == 0) goto L74
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L49
            r1.<init>(r0)     // Catch: java.lang.Exception -> L49
            r1.append(r9)     // Catch: java.lang.Exception -> L49
            r1.append(r8)     // Catch: java.lang.Exception -> L49
            java.lang.String r9 = r1.toString()     // Catch: java.lang.Exception -> L49
            android.util.Log.d(r6, r9)     // Catch: java.lang.Exception -> L49
        L74:
            return r4
        L75:
            r9.printStackTrace()
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils.getUCMKeyguardStorageForUser(int):java.lang.String");
    }

    public static String getUCMKeyguardVendorName(int i) {
        String uCMKeyguardStorageForUser = getUCMKeyguardStorageForUser(i);
        if (uCMKeyguardStorageForUser == null) {
            return "";
        }
        if (DBG) {
            Log.d(
                    "com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils",
                    "UCM Keyguard : ".concat(uCMKeyguardStorageForUser));
        }
        int lastIndexOf = uCMKeyguardStorageForUser.lastIndexOf(":");
        return lastIndexOf != -1
                ? uCMKeyguardStorageForUser.substring(
                        lastIndexOf + 1, uCMKeyguardStorageForUser.length())
                : uCMKeyguardStorageForUser;
    }

    public static String getUCMUri(int i) {
        String uCMKeyguardStorageForUser = getUCMKeyguardStorageForUser(i);
        if (uCMKeyguardStorageForUser == null) {
            return null;
        }
        String uri = UniversalCredentialUtil.getUri(uCMKeyguardStorageForUser, "");
        if (DBG) {
            Log.d(
                    "com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils",
                    "getStatus, uri: " + uri);
        }
        return uri;
    }

    public static boolean isSupportBiometricForUCM(int i) {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.e(
                    "com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils",
                    "Failed to get UCM Service");
            return false;
        }
        try {
            Bundle agentInfo = asInterface.getAgentInfo(getUCMUri(i));
            if (agentInfo == null) {
                return false;
            }
            return agentInfo.getBoolean("isSupportBiometricForUCM", false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUCMKeyguardEnabled(int i) {
        if (getUCMKeyguardStorageForUser(i) == null) {
            return false;
        }
        if (DBG) {
            Log.d(
                    "com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils",
                    "UCM Keyguard is enabled for user [" + i + "]");
        }
        return true;
    }
}
