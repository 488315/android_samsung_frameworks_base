package com.android.server.enterprise.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy;
import com.android.server.enterprise.email.EmailAccountPolicy;
import com.android.server.enterprise.email.ExchangeAccountPolicy;
import com.android.server.enterprise.general.MiscPolicy;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.multiuser.MultiUserManagerService;
import com.android.server.enterprise.restriction.PhoneRestrictionPolicy;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.vpn.VpnInfoPolicy;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;

import java.lang.reflect.Array;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class SecContentProvider2 extends ContentProvider {
    public static final UriMatcher URI_MATCHER;
    public Context mContext;
    public IKnoxCustomManager mKnoxCustomManagerService = null;

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        URI_MATCHER = uriMatcher;
        uriMatcher.addURI("com.sec.knox.provider2", "ApplicationPolicy", 1);
        uriMatcher.addURI("com.sec.knox.provider2", "DeviceAccountPolicy", 3);
        uriMatcher.addURI("com.sec.knox.provider2", "EmailPolicy", 6);
        uriMatcher.addURI("com.sec.knox.provider2", "EmailAccountPolicy", 7);
        uriMatcher.addURI("com.sec.knox.provider2", "EnterpriseDeviceManager", 11);
        uriMatcher.addURI("com.sec.knox.provider2", "ExchangeAccountPolicy", 12);
        uriMatcher.addURI("com.sec.knox.provider2", "KioskMode", 13);
        uriMatcher.addURI("com.sec.knox.provider2", "KnoxCustomManagerService1", 14);
        uriMatcher.addURI("com.sec.knox.provider2", "KnoxCustomManagerService2", 15);
        uriMatcher.addURI("com.sec.knox.provider2", "MiscPolicy", 16);
        uriMatcher.addURI("com.sec.knox.provider2", "MultiUserManager", 17);
        uriMatcher.addURI("com.sec.knox.provider2", "PhoneRestrictionPolicy", 18);
        uriMatcher.addURI("com.sec.knox.provider2", "vpnPolicy", 19);
        uriMatcher.addURI("com.sec.knox.provider2", "WifiPolicy", 20);
        uriMatcher.addURI("com.sec.knox.provider2", "EnterpriseLicenseService", 21);
        uriMatcher.addURI("com.sec.knox.provider2", "KnoxMUMContainerPolicy", 22);
    }

    public static MatrixCursor populateCursor(String str, boolean z) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[] {str});
        matrixCursor.addRow(new Boolean[] {Boolean.valueOf(z)});
        return matrixCursor;
    }

    public static Cursor queryDeviceAccount(String str, String[] strArr) {
        DeviceAccountPolicy deviceAccountPolicy =
                (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
        if (deviceAccountPolicy != null && str != null) {
            switch (str) {
                case "isAccountRemovalAllowed":
                    if (strArr != null && Array.getLength(strArr) >= 3) {
                        return populateCursor(
                                str,
                                deviceAccountPolicy.isAccountRemovalAllowed(
                                        strArr[0], strArr[1], Boolean.parseBoolean(strArr[2])));
                    }
                    break;
                case "isAccountRemovalAllowedAsUser":
                    if (strArr == null || Array.getLength(strArr) < 4) {
                        return null;
                    }
                    return populateCursor(
                            str,
                            deviceAccountPolicy.isAccountRemovalAllowedAsUser(
                                    strArr[0],
                                    strArr[1],
                                    Boolean.parseBoolean(strArr[2]),
                                    Integer.parseInt(strArr[3])));
                case "isAccountAdditionAllowed":
                    if (strArr == null || Array.getLength(strArr) < 3) {
                        return null;
                    }
                    return populateCursor(
                            str,
                            deviceAccountPolicy.isAccountAdditionAllowed(
                                    strArr[0], strArr[1], Boolean.parseBoolean(strArr[2])));
                default:
                    return null;
            }
        }
        return null;
    }

    public static Cursor queryEmailAccount(String str, int i, String[] strArr) {
        EmailAccountPolicy emailAccountPolicy =
                (EmailAccountPolicy) EnterpriseService.getPolicyService("email_account_policy");
        if (emailAccountPolicy != null && str != null) {
            if (!str.equals("getSecurityOutgoingServerPassword")) {
                if (!str.equals("getSecurityIncomingServerPassword")
                        || strArr == null
                        || Array.getLength(strArr) <= 0) {
                    return null;
                }
                String securityInComingServerPassword =
                        emailAccountPolicy.getSecurityInComingServerPassword(
                                new ContextInfo(i), Long.parseLong(strArr[0]));
                MatrixCursor matrixCursor = new MatrixCursor(new String[] {str});
                matrixCursor.addRow(new String[] {securityInComingServerPassword});
                return matrixCursor;
            }
            if (strArr != null && Array.getLength(strArr) > 0) {
                String securityOutGoingServerPassword =
                        emailAccountPolicy.getSecurityOutGoingServerPassword(
                                new ContextInfo(i), Long.parseLong(strArr[0]));
                MatrixCursor matrixCursor2 = new MatrixCursor(new String[] {str});
                matrixCursor2.addRow(new String[] {securityOutGoingServerPassword});
                return matrixCursor2;
            }
        }
        return null;
    }

    public static Cursor queryEnterpriseLicense(String str, String[] strArr) {
        EnterpriseLicenseService enterpriseLicenseService =
                (EnterpriseLicenseService)
                        EnterpriseService.getPolicyService("enterprise_license_policy");
        if (enterpriseLicenseService != null) {
            str.getClass();
            if (!str.equals("isServiceAvailable")) {
                Log.d("SecContentProvider2", "ENTERPRISELICENSEPOLICY : return null");
                return null;
            }
            if (strArr != null && Array.getLength(strArr) > 1) {
                return populateCursor(
                        str, enterpriseLicenseService.isServiceAvailable(strArr[0], strArr[1]));
            }
        }
        return null;
    }

    public static Cursor queryMisc(int i, String str) {
        MiscPolicy miscPolicy = (MiscPolicy) EnterpriseService.getPolicyService("misc_policy");
        if (miscPolicy == null || str == null) {
            return null;
        }
        if (!str.equals("getCurrentLockScreenString")) {
            if (str.equals("isNFCStateChangeAllowed")) {
                return populateCursor(str, miscPolicy.isNFCStateChangeAllowed());
            }
            return null;
        }
        String currentLockScreenString = miscPolicy.getCurrentLockScreenString(new ContextInfo(i));
        MatrixCursor matrixCursor = new MatrixCursor(new String[] {str});
        matrixCursor.addRow(new String[] {currentLockScreenString});
        return matrixCursor;
    }

    public static Cursor queryMultiUser(int i, String str) {
        MultiUserManagerService multiUserManagerService =
                (MultiUserManagerService)
                        EnterpriseService.getPolicyService("multi_user_manager_service");
        if (multiUserManagerService != null) {
            str.getClass();
            if (!str.equals("multipleUsersSupported")) {
                return null;
            }
            try {
                return populateCursor(
                        str, multiUserManagerService.multipleUsersSupported(new ContextInfo(i)));
            } catch (RemoteException unused) {
            }
        }
        return null;
    }

    public static Cursor queryMumContainer(int i, String str) {
        KnoxMUMContainerPolicy knoxMUMContainerPolicy =
                (KnoxMUMContainerPolicy) ServiceManager.getService("mum_container_policy");
        if (knoxMUMContainerPolicy == null) {
            Log.e("SecContentProvider2", "container policy is null");
            return null;
        }
        str.getClass();
        if (str.equals("isNFCEnabled")) {
            return populateCursor(str, knoxMUMContainerPolicy.isNFCEnabled(new ContextInfo(i)));
        }
        Log.d("SecContentProvider2", "queryMumContainer - no selection found!");
        return null;
    }

    public static Cursor queryPhoneRestriction(String str, int i, String[] strArr) {
        boolean z = true;
        boolean z2 = false;
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return null;
        }
        str.getClass();
        switch (str) {
            case "isOutgoingMmsAllowed":
                return populateCursor(
                        str, phoneRestrictionPolicy.isOutgoingMmsAllowed(new ContextInfo(i)));
            case "getEmergencyCallOnly":
                return populateCursor(
                        str, phoneRestrictionPolicy.getEmergencyCallOnly(new ContextInfo(i), true));
            case "isRCSEnabled":
                int length = Array.getLength(strArr);
                if (strArr == null || length < 3) {
                    return null;
                }
                int parseInt = Integer.parseInt(strArr[0]);
                boolean parseBoolean = Boolean.parseBoolean(strArr[1]);
                int parseInt2 = Integer.parseInt(strArr[2]);
                new ContextInfo(i);
                boolean isRCSEnabledInternal =
                        phoneRestrictionPolicy.isRCSEnabledInternal(
                                parseInt, "enableRCS", parseBoolean);
                if (isRCSEnabledInternal) {
                    isRCSEnabledInternal =
                            phoneRestrictionPolicy.isRCSEnabledBySimSlot(
                                    new ContextInfo(i), parseInt, parseBoolean, parseInt2);
                }
                return populateCursor(str, isRCSEnabledInternal);
            case "isIncomingMmsAllowed":
                new ContextInfo(i);
                return populateCursor(
                        str, phoneRestrictionPolicy.getSmsMmsAllowed("allowIncomingMms"));
            case "isCopyContactToSimAllowed":
                boolean isCopyContactToSimAllowed =
                        phoneRestrictionPolicy.isCopyContactToSimAllowed(new ContextInfo(i));
                Log.d(
                        "SecContentProvider2",
                        "isCopyContactToSimAllowed = " + isCopyContactToSimAllowed);
                return populateCursor(str, isCopyContactToSimAllowed);
            case "getDisclaimerText":
                String disclaimerText =
                        phoneRestrictionPolicy.getDisclaimerText(new ContextInfo(i));
                MatrixCursor matrixCursor = new MatrixCursor(new String[] {"getDisclaimerText"});
                matrixCursor.addRow(new String[] {disclaimerText});
                return matrixCursor;
            case "isMmsAllowedFromSimSlot1":
                try {
                    z = phoneRestrictionPolicy.isOperationAllowed(6, 0);
                    Log.d("SecContentProvider2", "isMmsAllowedFromSimSlot(0) result " + z);
                } catch (SecurityException e) {
                    Log.w("SecContentProvider2", "SecurityException: " + e);
                }
                return populateCursor(str, z);
            case "isMmsAllowedFromSimSlot2":
                try {
                    z = phoneRestrictionPolicy.isOperationAllowed(6, 1);
                    Log.d("SecContentProvider2", "isMmsAllowedFromSimSlot(1) result " + z);
                } catch (SecurityException e2) {
                    Log.w("SecContentProvider2", "SecurityException: " + e2);
                }
                return populateCursor(str, z);
            case "isDataAllowedFromSimSlot1":
                try {
                    z = phoneRestrictionPolicy.isOperationAllowed(1, 0);
                    Log.d("SecContentProvider2", "isDataAllowedFromSimSlot(0) result " + z);
                } catch (SecurityException e3) {
                    Log.w("SecContentProvider2", "SecurityException: " + e3);
                }
                return populateCursor(str, z);
            case "isDataAllowedFromSimSlot2":
                try {
                    z = phoneRestrictionPolicy.isOperationAllowed(1, 1);
                    Log.d("SecContentProvider2", "isDataAllowedFromSimSlot(1) result " + z);
                } catch (SecurityException e4) {
                    Log.w("SecContentProvider2", "SecurityException: " + e4);
                }
                return populateCursor(str, z);
            case "isSimLockedByAdmin":
                if (strArr == null || Array.getLength(strArr) <= 0) {
                    return null;
                }
                return populateCursor(str, phoneRestrictionPolicy.isSimLockedByAdmin(strArr[0]));
            case "checkEnableUseOfPacketData":
                if (strArr != null && Array.getLength(strArr) > 0) {
                    z2 = Boolean.parseBoolean(strArr[0]);
                }
                return populateCursor(str, phoneRestrictionPolicy.checkEnableUseOfPacketData(z2));
            default:
                Log.d("SecContentProvider2", "return null");
                return null;
        }
    }

    public static Cursor queryVPN(String str, int i, String[] strArr) {
        boolean z = false;
        VpnInfoPolicy vpnInfoPolicy =
                (VpnInfoPolicy) EnterpriseService.getPolicyService("vpn_policy");
        if (vpnInfoPolicy == null) {
            return null;
        }
        str.getClass();
        switch (str) {
            case "isUserAddProfilesAllowed":
                if (strArr != null && Array.getLength(strArr) > 0) {
                    z = Boolean.parseBoolean(strArr[0]);
                }
                return populateCursor(
                        str, vpnInfoPolicy.isUserAddProfilesAllowed(new ContextInfo(i), z));
            case "isUserSetAlwaysOnAllowed":
                if (strArr != null && Array.getLength(strArr) > 0) {
                    z = Boolean.parseBoolean(strArr[0]);
                }
                return populateCursor(
                        str, vpnInfoPolicy.isUserSetAlwaysOnAllowed(new ContextInfo(i), z));
            case "isUserChangeProfilesAllowed":
                if (strArr != null && Array.getLength(strArr) > 0) {
                    z = Boolean.parseBoolean(strArr[0]);
                }
                return populateCursor(
                        str, vpnInfoPolicy.isUserChangeProfilesAllowed(new ContextInfo(i), z));
            default:
                return null;
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public final String getCallerName(int i) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
        return nameForUid == null ? "fail to get caller name" : nameForUid;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        String asString;
        int callingUid = Binder.getCallingUid();
        int match = URI_MATCHER.match(uri);
        if (match == 12) {
            ExchangeAccountPolicy exchangeAccountPolicy =
                    (ExchangeAccountPolicy)
                            EnterpriseService.getPolicyService("eas_account_policy");
            if (exchangeAccountPolicy != null
                    && (asString = contentValues.getAsString("API")) != null
                    && asString.equals("setAccountEmailPassword")) {
                exchangeAccountPolicy.setAccountEmailPassword(
                        new ContextInfo(callingUid), contentValues.getAsString("password"));
            }
        } else if (match == 18) {
            getContext().getContentResolver().notifyChange(uri, null);
        } else if (match == 14) {
            getContext().getContentResolver().notifyChange(uri, null);
            Log.d("SecContentProvider2", "do notifyChange() for knoxCustomManagerService1");
        } else if (match == 15) {
            getContext().getContentResolver().notifyChange(uri, null);
            Log.d("SecContentProvider2", "do notifyChange() for knoxCustomManagerService2");
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        this.mContext = getContext();
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0090, code lost:

       if (r21.equals("isWifiScanningAllowed") == false) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:589:0x0729, code lost:

       if (r21.equals("getTorchOnVolumeButtonsState") == false) goto L393;
    */
    /* JADX WARN: Code restructure failed: missing block: B:828:0x0c5a, code lost:

       if (r21.equals("getRequiredSignedMIMEMessages") == false) goto L664;
    */
    /* JADX WARN: Code restructure failed: missing block: B:935:0x0f89, code lost:

       if (r21.equals("getAllowEmailForwarding") == false) goto L855;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.database.Cursor query(
            android.net.Uri r19,
            java.lang.String[] r20,
            java.lang.String r21,
            java.lang.String[] r22,
            java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 6220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.content.SecContentProvider2.query(android.net.Uri,"
                    + " java.lang.String[], java.lang.String, java.lang.String[],"
                    + " java.lang.String):android.database.Cursor");
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
