package com.android.server.enterprise.content;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.os.ParcelUuid;
import android.os.UserHandle;
import android.sec.enterprise.BluetoothUtils;
import android.util.Log;
import android.util.Slog;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.auditlog.AuditLogService;
import com.android.server.enterprise.bluetooth.BluetoothPolicy;
import com.android.server.enterprise.browser.BrowserPolicy;
import com.android.server.enterprise.certificate.CertificatePolicy;
import com.android.server.enterprise.datetime.DateTimePolicy;
import com.android.server.enterprise.dex.DexPolicy;
import com.android.server.enterprise.firewall.Firewall;
import com.android.server.enterprise.general.MiscPolicy;
import com.android.server.enterprise.location.LocationPolicy;
import com.android.server.enterprise.profile.ProfilePolicyService;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.restriction.RoamingPolicy;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.security.SecurityPolicy;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.wifi.WifiPolicy;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.keystore.CertificateInfo;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SecContentProvider extends ContentProvider {
    public static final UriMatcher URI_MATCHER;
    public Context mContext;

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        URI_MATCHER = uriMatcher;
        uriMatcher.addURI("com.sec.knox.provider", "AdvancedRestrictionPolicy", 1);
        uriMatcher.addURI("com.sec.knox.provider", "AuditLog", 2);
        uriMatcher.addURI("com.sec.knox.provider", "BluetoothPolicy", 3);
        uriMatcher.addURI("com.sec.knox.provider", "BluetoothUtils", 4);
        uriMatcher.addURI("com.sec.knox.provider", "BrowserPolicy", 5);
        uriMatcher.addURI("com.sec.knox.provider", "CertificatePolicy", 6);
        uriMatcher.addURI("com.sec.knox.provider", "ContainerApplicationPolicy", 7);
        uriMatcher.addURI("com.sec.knox.provider", "DeviceSettingsPolicy", 8);
        uriMatcher.addURI("com.sec.knox.provider", "EnterpriseKnoxManagerPolicy", 9);
        uriMatcher.addURI("com.sec.knox.provider", "FirewallPolicy", 10);
        uriMatcher.addURI("com.sec.knox.provider", "KnoxConfigurationType", 11);
        uriMatcher.addURI("com.sec.knox.provider", "LocationPolicy", 12);
        uriMatcher.addURI("com.sec.knox.provider", "PasswordPolicy1", 13);
        uriMatcher.addURI("com.sec.knox.provider", "PasswordPolicy2", 14);
        uriMatcher.addURI("com.sec.knox.provider", "RestrictionPolicy1", 15);
        uriMatcher.addURI("com.sec.knox.provider", "RestrictionPolicy2", 16);
        uriMatcher.addURI("com.sec.knox.provider", "RestrictionPolicy3", 17);
        uriMatcher.addURI("com.sec.knox.provider", "RestrictionPolicy4", 18);
        uriMatcher.addURI("com.sec.knox.provider", "RoamingPolicy", 19);
        uriMatcher.addURI("com.sec.knox.provider", "SecurityPolicy", 20);
        uriMatcher.addURI("com.sec.knox.provider", "DateTimePolicy", 24);
        uriMatcher.addURI("com.sec.knox.provider", "DlpPolicy", 25);
        uriMatcher.addURI("com.sec.knox.provider", "DomainFilterPolicy", 26);
        uriMatcher.addURI("com.sec.knox.provider", "DexPolicy", 27);
        uriMatcher.addURI("com.sec.knox.provider", "RestrictionPolicy", 30);
        uriMatcher.addURI("com.sec.knox.provider", "ProfilePolicy", 31);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mContext = getContext();
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:1009:0x1b66, code lost:
    
        if (r25.equals("getVirtualMacAddress") == false) goto L1043;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1077:0x1cd0, code lost:
    
        if (r25.equals("getURLFilterEnabled") == false) goto L1097;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1192:0x1e19, code lost:
    
        if (r25.equals("isUserRemoveCertificatesAllowed") == false) goto L1142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1334:0x21a8, code lost:
    
        if (r25.equals("isPairingAllowedbySecurityPolicy") == false) goto L1301;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1405:0x23e8, code lost:
    
        if (r25.equals("isBluetoothLogEnabled") == false) goto L1415;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x029b, code lost:
    
        if (r25.equals("isWallpaperChangeAllowed") == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:349:0x064a, code lost:
    
        if (r25.equals("isPowerOffAllowed") == false) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:495:0x0a78, code lost:
    
        if (r25.equals("isMicrophoneEnabledAsUser") == false) goto L372;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012a, code lost:
    
        if (r25.equals("isRoamingDataEnabled") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:646:0x0f50, code lost:
    
        if (r25.equals("isClipboardAllowedAsUser") == false) goto L527;
     */
    /* JADX WARN: Code restructure failed: missing block: B:744:0x131f, code lost:
    
        if (r25.equals("getMaximumFailedPasswordsForWipe") == false) goto L688;
     */
    /* JADX WARN: Code restructure failed: missing block: B:852:0x169f, code lost:
    
        if (r25.equals("getMaximumNumericSequenceLength") == false) goto L793;
     */
    /* JADX WARN: Code restructure failed: missing block: B:974:0x1a20, code lost:
    
        if (r25.equals("isLocalContactStorageAllowed") == false) goto L953;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        boolean isUserRemoveCertificatesAllowed;
        boolean restrictionPolicyisPowerSavingModeAllowed;
        ProfilePolicyService profilePolicyService;
        boolean isLocationProviderBlockedAsUser;
        int maximumFailedPasswordsForDisable;
        boolean isExternalStorageForFailedPasswordsWipeExcluded;
        boolean isPasswordVisibilityEnabled;
        int maximumFailedPasswordsForWipe;
        boolean isClipboardAllowed;
        boolean isMicrophoneEnabled;
        boolean isLockScreenViewAllowed;
        boolean isNonMarketAppAllowed;
        boolean isMockLocationEnabled;
        boolean isKillingActivitiesOnLeaveAllowed;
        boolean isShareListAllowed;
        boolean isSettingsChangesAllowed;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        StringBuilder sb = new StringBuilder();
        sb.append("query(), uri = ");
        UriMatcher uriMatcher = URI_MATCHER;
        sb.append(uriMatcher.match(uri));
        sb.append(" selection = ");
        sb.append(str);
        Log.d("SecContentProvider", sb.toString());
        Slog.d("SecContentProvider", "called from " + getCallerName(callingUid));
        int match = uriMatcher.match(uri);
        char c = 2;
        if (match != 2) {
            char c2 = 4;
            char c3 = 3;
            if (match == 3) {
                BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
                if (bluetoothPolicy != null && str != null) {
                    switch (str.hashCode()) {
                        case -1650172419:
                            if (str.equals("isBluetoothEnabled")) {
                                c3 = 0;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -1397722082:
                            if (str.equals("isBluetoothEnabledWithMsg")) {
                                c3 = 1;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -1394196042:
                            if (str.equals("bluetoothLog")) {
                                c3 = 2;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -1321177311:
                            break;
                        case -294813544:
                            if (str.equals("isDesktopConnectivityEnabled")) {
                                c3 = 4;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 86845300:
                            if (str.equals("isDiscoverableEnabled")) {
                                c3 = 5;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 913371287:
                            if (str.equals("isBLEAllowed")) {
                                c3 = 6;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 962777550:
                            if (str.equals("isLimitedDiscoverableEnabled")) {
                                c3 = 7;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 1680002025:
                            if (str.equals("isOutgoingCallsAllowed")) {
                                c3 = '\b';
                                break;
                            }
                            c3 = 65535;
                            break;
                        default:
                            c3 = 65535;
                            break;
                    }
                    switch (c3) {
                        case 0:
                            boolean isBluetoothEnabled = bluetoothPolicy.isBluetoothEnabled(new ContextInfo(callingUid));
                            Log.d("SecContentProvider", "isBluetoothEnabled = " + isBluetoothEnabled);
                            MatrixCursor matrixCursor = new MatrixCursor(new String[]{"isBluetoothEnabled"});
                            matrixCursor.addRow(new Boolean[]{Boolean.valueOf(isBluetoothEnabled)});
                            return matrixCursor;
                        case 1:
                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                return null;
                            }
                            boolean isBluetoothEnabledWithMsg = bluetoothPolicy.isBluetoothEnabledWithMsg(Boolean.parseBoolean(strArr2[0]));
                            Log.d("SecContentProvider", "isBluetoothEnabled = " + isBluetoothEnabledWithMsg);
                            MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"isBluetoothEnabledWithMsg"});
                            matrixCursor2.addRow(new Boolean[]{Boolean.valueOf(isBluetoothEnabledWithMsg)});
                            return matrixCursor2;
                        case 2:
                            if (strArr2 == null || getArrayLength(strArr2) < 2) {
                                return null;
                            }
                            boolean bluetoothLog = bluetoothPolicy.bluetoothLog(new ContextInfo(callingUid), strArr2[0], strArr2[1]);
                            MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"bluetoothLog"});
                            matrixCursor3.addRow(new Boolean[]{Boolean.valueOf(bluetoothLog)});
                            return matrixCursor3;
                        case 3:
                            boolean isBluetoothLogEnabled = bluetoothPolicy.isBluetoothLogEnabled(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor4 = new MatrixCursor(new String[]{"isBluetoothLogEnabled"});
                            matrixCursor4.addRow(new Boolean[]{Boolean.valueOf(isBluetoothLogEnabled)});
                            return matrixCursor4;
                        case 4:
                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                return null;
                            }
                            boolean isDesktopConnectivityEnabled = bluetoothPolicy.isDesktopConnectivityEnabled(Boolean.parseBoolean(strArr2[0]));
                            MatrixCursor matrixCursor5 = new MatrixCursor(new String[]{"isDesktopConnectivityEnabled"});
                            matrixCursor5.addRow(new Boolean[]{Boolean.valueOf(isDesktopConnectivityEnabled)});
                            return matrixCursor5;
                        case 5:
                            boolean isDiscoverableEnabled = bluetoothPolicy.isDiscoverableEnabled(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor6 = new MatrixCursor(new String[]{"isDiscoverableEnabled"});
                            matrixCursor6.addRow(new Boolean[]{Boolean.valueOf(isDiscoverableEnabled)});
                            return matrixCursor6;
                        case 6:
                            boolean isBLEAllowed = bluetoothPolicy.isBLEAllowed(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor7 = new MatrixCursor(new String[]{"isBLEAllowed"});
                            matrixCursor7.addRow(new Boolean[]{Boolean.valueOf(isBLEAllowed)});
                            return matrixCursor7;
                        case 7:
                            boolean isLimitedDiscoverableEnabled = bluetoothPolicy.isLimitedDiscoverableEnabled(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor8 = new MatrixCursor(new String[]{"isLimitedDiscoverableEnabled"});
                            matrixCursor8.addRow(new Boolean[]{Boolean.valueOf(isLimitedDiscoverableEnabled)});
                            return matrixCursor8;
                        case '\b':
                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                return null;
                            }
                            boolean isOutgoingCallsAllowed = bluetoothPolicy.isOutgoingCallsAllowed(Boolean.parseBoolean(strArr2[0]));
                            Log.d("SecContentProvider", "isOutgoingCallsAllowed = " + isOutgoingCallsAllowed);
                            MatrixCursor matrixCursor9 = new MatrixCursor(new String[]{"isOutgoingCallsAllowed"});
                            matrixCursor9.addRow(new Boolean[]{Boolean.valueOf(isOutgoingCallsAllowed)});
                            return matrixCursor9;
                        default:
                            return null;
                    }
                }
            } else if (match != 4) {
                if (match == 5) {
                    BrowserPolicy browserPolicy = (BrowserPolicy) EnterpriseService.getPolicyService("browser_policy");
                    if (browserPolicy != null && str != null) {
                        switch (str) {
                            case "getForceFraudWarningSetting":
                                boolean browserSettingStatus = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 8);
                                MatrixCursor matrixCursor10 = new MatrixCursor(new String[]{"getForceFraudWarningSetting"});
                                matrixCursor10.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus)});
                                return matrixCursor10;
                            case "getPopupsSetting":
                                boolean browserSettingStatus2 = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 1);
                                MatrixCursor matrixCursor11 = new MatrixCursor(new String[]{"getPopupsSetting"});
                                matrixCursor11.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus2)});
                                return matrixCursor11;
                            case "getAutoFillSetting":
                                boolean browserSettingStatus3 = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 4);
                                MatrixCursor matrixCursor12 = new MatrixCursor(new String[]{"getAutoFillSetting"});
                                matrixCursor12.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus3)});
                                return matrixCursor12;
                            case "getJavaScriptSetting":
                                boolean browserSettingStatus4 = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 16);
                                MatrixCursor matrixCursor13 = new MatrixCursor(new String[]{"getJavaScriptSetting"});
                                matrixCursor13.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus4)});
                                return matrixCursor13;
                            case "getHttpProxy":
                                String httpProxy = browserPolicy.getHttpProxy(new ContextInfo(callingUid));
                                MatrixCursor matrixCursor14 = new MatrixCursor(new String[]{"getHttpProxy"});
                                matrixCursor14.addRow(new String[]{httpProxy});
                                return matrixCursor14;
                            case "getCookiesSetting":
                                boolean browserSettingStatus5 = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 2);
                                MatrixCursor matrixCursor15 = new MatrixCursor(new String[]{"getCookiesSetting"});
                                matrixCursor15.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus5)});
                                return matrixCursor15;
                            default:
                                return null;
                        }
                    }
                } else if (match == 6) {
                    CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
                    if (certificatePolicy != null && str != null) {
                        switch (str.hashCode()) {
                            case -2137531713:
                                if (str.equals("isCertificateValidationAtInstallEnabled")) {
                                    c3 = 0;
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case -1870151609:
                                if (str.equals("validateCertificateAtInstall")) {
                                    c3 = 1;
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case -1372337100:
                                if (str.equals("isOcspCheckEnabled")) {
                                    c3 = 2;
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case -1301070189:
                                break;
                            case -1024177691:
                                if (str.equals("isCaCertificateTrustedAsUser")) {
                                    c3 = 4;
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case -1013752713:
                                if (str.equals("getIdentitiesFromSignatures")) {
                                    c3 = 5;
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case -1006854168:
                                if (str.equals("isCaCertificateDisabledAsUser")) {
                                    c3 = 6;
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case -1002842335:
                                if (str.equals("isSignatureIdentityInformationEnabled")) {
                                    c3 = 7;
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case -927640804:
                                if (str.equals("notifyCertificateFailure")) {
                                    c3 = '\b';
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case 1661319901:
                                if (str.equals("validateChainAtInstall")) {
                                    c3 = '\t';
                                    break;
                                }
                                c3 = 65535;
                                break;
                            case 1764132257:
                                if (str.equals("isRevocationCheckEnabled")) {
                                    c3 = '\n';
                                    break;
                                }
                                c3 = 65535;
                                break;
                            default:
                                c3 = 65535;
                                break;
                        }
                        switch (c3) {
                            case 0:
                                boolean isCertificateValidationAtInstallEnabled = certificatePolicy.isCertificateValidationAtInstallEnabled(new ContextInfo(callingUid));
                                MatrixCursor matrixCursor16 = new MatrixCursor(new String[]{"isCertificateValidationAtInstallEnabled"});
                                matrixCursor16.addRow(new Boolean[]{Boolean.valueOf(isCertificateValidationAtInstallEnabled)});
                                return matrixCursor16;
                            case 1:
                                if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                    return null;
                                }
                                int validateCertificateAtInstall = certificatePolicy.validateCertificateAtInstall((CertificateInfo) Utils.deserializeObjectFromString(strArr2[0]));
                                MatrixCursor matrixCursor17 = new MatrixCursor(new String[]{"validateCertificateAtInstall"});
                                matrixCursor17.addRow(new Integer[]{Integer.valueOf(validateCertificateAtInstall)});
                                return matrixCursor17;
                            case 2:
                                boolean isOcspCheckEnabled = certificatePolicy.isOcspCheckEnabled(new ContextInfo(callingUid));
                                MatrixCursor matrixCursor18 = new MatrixCursor(new String[]{"isOcspCheckEnabled"});
                                matrixCursor18.addRow(new Boolean[]{Boolean.valueOf(isOcspCheckEnabled)});
                                return matrixCursor18;
                            case 3:
                                if (strArr2 != null && strArr2.length == 1) {
                                    isUserRemoveCertificatesAllowed = certificatePolicy.isUserRemoveCertificatesAllowedAsUser(Integer.parseInt(strArr2[0]));
                                } else {
                                    isUserRemoveCertificatesAllowed = certificatePolicy.isUserRemoveCertificatesAllowed(new ContextInfo(callingUid));
                                }
                                MatrixCursor matrixCursor19 = new MatrixCursor(new String[]{"isUserRemoveCertificatesAllowed"});
                                matrixCursor19.addRow(new Boolean[]{Boolean.valueOf(isUserRemoveCertificatesAllowed)});
                                return matrixCursor19;
                            case 4:
                                if (strArr2 == null || getArrayLength(strArr2) <= 1) {
                                    return null;
                                }
                                if (getArrayLength(strArr2) > 2) {
                                    userId = Integer.parseInt(strArr2[2]);
                                }
                                boolean isCaCertificateTrustedAsUser = certificatePolicy.isCaCertificateTrustedAsUser((CertificateInfo) Utils.deserializeObjectFromString(strArr2[0]), Boolean.parseBoolean(strArr2[1]), userId);
                                MatrixCursor matrixCursor20 = new MatrixCursor(new String[]{"isCaCertificateTrustedAsUser"});
                                matrixCursor20.addRow(new Boolean[]{Boolean.valueOf(isCaCertificateTrustedAsUser)});
                                return matrixCursor20;
                            case 5:
                                if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                    return null;
                                }
                                List<String[]> identitiesFromSignatures = certificatePolicy.getIdentitiesFromSignatures(new ContextInfo(callingUid), (Signature[]) Utils.deserializeObjectFromString(strArr2[0]));
                                MatrixCursor matrixCursor21 = new MatrixCursor(new String[]{"getIdentitiesFromSignatures"});
                                if (identitiesFromSignatures == null || identitiesFromSignatures.isEmpty()) {
                                    return matrixCursor21;
                                }
                                for (String[] strArr3 : identitiesFromSignatures) {
                                    for (String str3 : strArr3) {
                                        matrixCursor21.addRow(new String[]{str3});
                                    }
                                }
                                return matrixCursor21;
                            case 6:
                                if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                    return null;
                                }
                                if (getArrayLength(strArr2) > 1) {
                                    userId = Integer.parseInt(strArr2[1]);
                                }
                                boolean isCaCertificateDisabledAsUser = certificatePolicy.isCaCertificateDisabledAsUser(strArr2[0], userId);
                                MatrixCursor matrixCursor22 = new MatrixCursor(new String[]{"isCaCertificateDisabledAsUser"});
                                matrixCursor22.addRow(new Boolean[]{Boolean.valueOf(isCaCertificateDisabledAsUser)});
                                return matrixCursor22;
                            case 7:
                                if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                    return null;
                                }
                                boolean isSignatureIdentityInformationEnabled = certificatePolicy.isSignatureIdentityInformationEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                MatrixCursor matrixCursor23 = new MatrixCursor(new String[]{"isSignatureIdentityInformationEnabled"});
                                matrixCursor23.addRow(new Boolean[]{Boolean.valueOf(isSignatureIdentityInformationEnabled)});
                                return matrixCursor23;
                            case '\b':
                                if (strArr2 == null || getArrayLength(strArr2) <= 2) {
                                    return null;
                                }
                                certificatePolicy.notifyCertificateFailure(strArr2[0], strArr2[1], Boolean.parseBoolean(strArr2[2]));
                                return null;
                            case '\t':
                                if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                    return null;
                                }
                                int validateChainAtInstall = certificatePolicy.validateChainAtInstall((List) Utils.deserializeObjectFromString(strArr2[0]));
                                MatrixCursor matrixCursor24 = new MatrixCursor(new String[]{"validateChainAtInstall"});
                                matrixCursor24.addRow(new Integer[]{Integer.valueOf(validateChainAtInstall)});
                                return matrixCursor24;
                            case '\n':
                                boolean isRevocationCheckEnabled = certificatePolicy.isRevocationCheckEnabled(new ContextInfo(callingUid));
                                MatrixCursor matrixCursor25 = new MatrixCursor(new String[]{"isRevocationCheckEnabled"});
                                matrixCursor25.addRow(new Boolean[]{Boolean.valueOf(isRevocationCheckEnabled)});
                                return matrixCursor25;
                            default:
                                return null;
                        }
                    }
                } else if (match == 10) {
                    BrowserPolicy browserPolicy2 = (BrowserPolicy) EnterpriseService.getPolicyService("browser_policy");
                    MiscPolicy miscPolicy = (MiscPolicy) EnterpriseService.getPolicyService("misc_policy");
                    if (browserPolicy2 != null && str != null && miscPolicy != null) {
                        switch (str.hashCode()) {
                            case -2075521201:
                                if (str.equals("getURLFilterList")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1053530777:
                                if (str.equals("isGlobalProxyAllowed")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1000596208:
                                break;
                            case 1650820679:
                                if (str.equals("isUrlBlocked")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1865999324:
                                if (str.equals("getURLFilterReportEnabled")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                List uRLFilterListEnforcingFirewallPermission = browserPolicy2.getURLFilterListEnforcingFirewallPermission(new ContextInfo(callingUid), true, false);
                                MatrixCursor matrixCursor26 = new MatrixCursor(new String[]{"getURLFilterList"});
                                if (uRLFilterListEnforcingFirewallPermission == null || uRLFilterListEnforcingFirewallPermission.isEmpty()) {
                                    return matrixCursor26;
                                }
                                Iterator it = uRLFilterListEnforcingFirewallPermission.iterator();
                                while (it.hasNext()) {
                                    matrixCursor26.addRow(new String[]{(String) it.next()});
                                }
                                return matrixCursor26;
                            case 1:
                                Boolean valueOf = Boolean.valueOf(miscPolicy.isGlobalProxyAllowed());
                                MatrixCursor matrixCursor27 = new MatrixCursor(new String[]{"isGlobalProxyAllowed"});
                                matrixCursor27.addRow(new Boolean[]{valueOf});
                                return matrixCursor27;
                            case 2:
                                boolean uRLFilterEnabledEnforcingFirewallPermission = browserPolicy2.getURLFilterEnabledEnforcingFirewallPermission(new ContextInfo(callingUid), true, false);
                                MatrixCursor matrixCursor28 = new MatrixCursor(new String[]{"getURLFilterEnabled"});
                                matrixCursor28.addRow(new Boolean[]{Boolean.valueOf(uRLFilterEnabledEnforcingFirewallPermission)});
                                return matrixCursor28;
                            case 3:
                                if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                    return null;
                                }
                                Boolean valueOf2 = Boolean.valueOf(browserPolicy2.isUrlBlocked(new ContextInfo(callingUid), strArr2[0]));
                                MatrixCursor matrixCursor29 = new MatrixCursor(new String[]{"isUrlBlocked"});
                                matrixCursor29.addRow(new Boolean[]{valueOf2});
                                return matrixCursor29;
                            case 4:
                                Boolean valueOf3 = Boolean.valueOf(browserPolicy2.getURLFilterReportEnabledEnforcingFirewallPermission(new ContextInfo(callingUid), true, false));
                                MatrixCursor matrixCursor30 = new MatrixCursor(new String[]{"getURLFilterReportEnabled"});
                                matrixCursor30.addRow(new Boolean[]{valueOf3});
                                return matrixCursor30;
                            default:
                                return null;
                        }
                    }
                } else if (match == 24) {
                    DateTimePolicy dateTimePolicy = (DateTimePolicy) EnterpriseService.getPolicyService("date_time_policy");
                    if (dateTimePolicy != null && str != null) {
                        if (str.equals("isDateTimeChangeEnalbed")) {
                            boolean isDateTimeChangeEnabled = dateTimePolicy.isDateTimeChangeEnabled(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor31 = new MatrixCursor(new String[]{"isDateTimeChangeEnalbed"});
                            matrixCursor31.addRow(new Boolean[]{Boolean.valueOf(isDateTimeChangeEnabled)});
                            return matrixCursor31;
                        }
                        if (!str.equals("getAutomaticTime")) {
                            return null;
                        }
                        boolean automaticTime = dateTimePolicy.getAutomaticTime(new ContextInfo(callingUid));
                        MatrixCursor matrixCursor32 = new MatrixCursor(new String[]{"getAutomaticTime"});
                        matrixCursor32.addRow(new Boolean[]{Boolean.valueOf(automaticTime)});
                        return matrixCursor32;
                    }
                } else {
                    if (match == 26) {
                        if (str == null || !str.equals("getDefaultCaptivePortalUrl")) {
                            return null;
                        }
                        Log.d("SecContentProvider", "getDefaultCaptivePortalUrl");
                        Firewall firewall = (Firewall) EnterpriseService.getPolicyService("firewall");
                        String defaulCaptivePortalURL = firewall != null ? firewall.getDefaulCaptivePortalURL() : null;
                        MatrixCursor matrixCursor33 = new MatrixCursor(new String[]{"getDefaultCaptivePortalUrl"});
                        matrixCursor33.addRow(new String[]{defaulCaptivePortalURL});
                        return matrixCursor33;
                    }
                    if (match == 27) {
                        DexPolicy dexPolicy = (DexPolicy) EnterpriseService.getPolicyService("dex_policy");
                        if (dexPolicy != null && str != null) {
                            switch (str.hashCode()) {
                                case -900155351:
                                    if (str.equals("isDexDisabled")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 270538573:
                                    if (str.equals("isScreenTimeoutChangeAllowed")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 304101466:
                                    break;
                                case 1328592463:
                                    if (str.equals("isEthernetOnlyEnforced")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2026280936:
                                    if (str.equals("isVirtualMacAddressEnforced")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    boolean isDexDisabled = dexPolicy.isDexDisabled();
                                    MatrixCursor matrixCursor34 = new MatrixCursor(new String[]{"isDexDisabled"});
                                    matrixCursor34.addRow(new Boolean[]{Boolean.valueOf(isDexDisabled)});
                                    return matrixCursor34;
                                case 1:
                                    boolean isScreenTimeoutChangeAllowed = dexPolicy.isScreenTimeoutChangeAllowed();
                                    MatrixCursor matrixCursor35 = new MatrixCursor(new String[]{"isScreenTimeoutChangeAllowed"});
                                    matrixCursor35.addRow(new Boolean[]{Boolean.valueOf(isScreenTimeoutChangeAllowed)});
                                    return matrixCursor35;
                                case 2:
                                    String virtualMacAddress = dexPolicy.getVirtualMacAddress();
                                    MatrixCursor matrixCursor36 = new MatrixCursor(new String[]{"getVirtualMacAddress"});
                                    matrixCursor36.addRow(new String[]{virtualMacAddress});
                                    return matrixCursor36;
                                case 3:
                                    boolean isEthernetOnlyEnforced = dexPolicy.isEthernetOnlyEnforced();
                                    MatrixCursor matrixCursor37 = new MatrixCursor(new String[]{"isEthernetOnlyEnforced"});
                                    matrixCursor37.addRow(new Boolean[]{Boolean.valueOf(isEthernetOnlyEnforced)});
                                    return matrixCursor37;
                                case 4:
                                    boolean isVirtualMacAddressEnforced = dexPolicy.isVirtualMacAddressEnforced();
                                    MatrixCursor matrixCursor38 = new MatrixCursor(new String[]{"isVirtualMacAddressEnforced"});
                                    matrixCursor38.addRow(new Boolean[]{Boolean.valueOf(isVirtualMacAddressEnforced)});
                                    return matrixCursor38;
                                default:
                                    return null;
                            }
                        }
                    } else if (match == 30) {
                        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
                        if (restrictionPolicy != null && str != null) {
                            switch (str.hashCode()) {
                                case -1782208912:
                                    if (str.equals("isPowerSavingModeAllowed")) {
                                        c3 = 0;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -1437543020:
                                    if (str.equals("isPowerOffAllowed")) {
                                        c3 = 1;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -1312648512:
                                    if (str.equals("isWallpaperChangeAllowed")) {
                                        c3 = 2;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -1235035892:
                                    break;
                                case -1095449159:
                                    if (str.equals("isIntelligenceOnlineProcessingAllowed")) {
                                        c3 = 4;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -817968032:
                                    if (str.equals("isWifiDirectAllowed")) {
                                        c3 = 5;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -727198894:
                                    if (str.equals("isCameraEnabled")) {
                                        c3 = 6;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 96378173:
                                    if (str.equals("isHeadPhoneEnabled")) {
                                        c3 = 7;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 736681126:
                                    if (str.equals("showToastIfIntelligenceOnlineProcessingDisallowed")) {
                                        c3 = '\b';
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 1538413106:
                                    if (str.equals("isSettingsChangesAllowed")) {
                                        c3 = '\t';
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                default:
                                    c3 = 65535;
                                    break;
                            }
                            switch (c3) {
                                case 0:
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicyisPowerSavingModeAllowed(callingUid, restrictionPolicy);
                                    break;
                                case 1:
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicyIsPowerOffAllowed(strArr2, callingUid, restrictionPolicy);
                                    break;
                                case 2:
                                    if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                        r11 = Boolean.parseBoolean(strArr2[0]);
                                    }
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isWallpaperChangeAllowed(new ContextInfo(callingUid), r11);
                                    break;
                                case 3:
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isLocalContactStorageAllowed(new ContextInfo(callingUid));
                                    break;
                                case 4:
                                    if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                        restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isIntelligenceOnlineProcessingAllowed(Integer.parseInt(strArr2[0]));
                                        break;
                                    } else {
                                        restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isIntelligenceOnlineProcessingAllowed(new ContextInfo(callingUid));
                                        break;
                                    }
                                    break;
                                case 5:
                                    if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                        r11 = Boolean.parseBoolean(strArr2[0]);
                                    }
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isWifiDirectAllowed(new ContextInfo(callingUid), r11);
                                    break;
                                case 6:
                                    if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                        return null;
                                    }
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isCameraEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                    break;
                                    break;
                                case 7:
                                    if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                        return null;
                                    }
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isHeadphoneEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                    break;
                                case '\b':
                                    restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.showToastIfIntelligenceOnlineProcessingDisallowed(userId);
                                    break;
                                case '\t':
                                    if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                        return null;
                                    }
                                    if (getArrayLength(strArr2) == 2) {
                                        restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isSettingsChangesAllowed(new ContextInfo(Integer.parseInt(strArr2[1])), Boolean.parseBoolean(strArr2[0]));
                                        break;
                                    } else {
                                        restrictionPolicyisPowerSavingModeAllowed = restrictionPolicy.isSettingsChangesAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                        break;
                                    }
                                default:
                                    Log.d("SecContentProvider", "return null");
                                    return null;
                            }
                            MatrixCursor matrixCursor39 = new MatrixCursor(new String[]{str});
                            matrixCursor39.addRow(new Boolean[]{Boolean.valueOf(restrictionPolicyisPowerSavingModeAllowed)});
                            return matrixCursor39;
                        }
                    } else if (match != 31) {
                        switch (match) {
                            case 12:
                                LocationPolicy locationPolicy = (LocationPolicy) EnterpriseService.getPolicyService("location_policy");
                                if (locationPolicy != null && str != null) {
                                    switch (str) {
                                        case "isLocationProviderBlockedAsUser":
                                            if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                                return null;
                                            }
                                            if (getArrayLength(strArr2) == 2) {
                                                int parseInt = Integer.parseInt(strArr2[1]);
                                                if (parseInt == -2) {
                                                    parseInt = ActivityManager.getCurrentUser();
                                                    Log.d("SecContentProvider", "isLocationProviderBlockedAsUser using userId = " + parseInt + " instead of UserHandle.USER_CURRENT");
                                                }
                                                isLocationProviderBlockedAsUser = locationPolicy.isLocationProviderBlockedAsUser(strArr2[0], parseInt);
                                            } else {
                                                isLocationProviderBlockedAsUser = locationPolicy.isLocationProviderBlockedAsUser(strArr2[0], userId);
                                            }
                                            MatrixCursor matrixCursor40 = new MatrixCursor(new String[]{"isLocationProviderBlockedAsUser"});
                                            matrixCursor40.addRow(new Boolean[]{Boolean.valueOf(isLocationProviderBlockedAsUser)});
                                            return matrixCursor40;
                                        case "isGPSStateChangeAllowed":
                                            boolean isGPSStateChangeAllowed = locationPolicy.isGPSStateChangeAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor41 = new MatrixCursor(new String[]{"isGPSStateChangeAllowed"});
                                            matrixCursor41.addRow(new Boolean[]{Boolean.valueOf(isGPSStateChangeAllowed)});
                                            return matrixCursor41;
                                        case "isLocationProviderBlocked":
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isLocationProviderBlocked = locationPolicy.isLocationProviderBlocked(strArr2[0]);
                                            MatrixCursor matrixCursor42 = new MatrixCursor(new String[]{"isLocationProviderBlocked"});
                                            matrixCursor42.addRow(new Boolean[]{Boolean.valueOf(isLocationProviderBlocked)});
                                            return matrixCursor42;
                                        default:
                                            return null;
                                    }
                                }
                                break;
                            case 13:
                                PasswordPolicy passwordPolicy = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
                                if (passwordPolicy != null && str != null) {
                                    switch (str.hashCode()) {
                                        case -2140263545:
                                            if (str.equals("getMinimumCharacterChangeLength")) {
                                                c = 0;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -2083015610:
                                            if (str.equals("getMaximumCharacterSequenceLength")) {
                                                c = 1;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -1865635158:
                                            break;
                                        case -1287116923:
                                            if (str.equals("hasForbiddenStringDistance")) {
                                                c = 3;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -755808165:
                                            if (str.equals("getMaximumCharacterOccurences")) {
                                                c = 4;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 231079631:
                                            if (str.equals("getRequiredPwdPatternRestrictions")) {
                                                c = 5;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 867781567:
                                            if (str.equals("getForbiddenStrings")) {
                                                c = 6;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 914155264:
                                            if (str.equals("getPasswordChangeTimeout")) {
                                                c = 7;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 1179943625:
                                            if (str.equals("hasForbiddenData")) {
                                                c = '\b';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 1429083467:
                                            if (str.equals("hasForbiddenCharacterSequence")) {
                                                c = '\t';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 1769024879:
                                            if (str.equals("hasForbiddenNumericSequence")) {
                                                c = '\n';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 1790232462:
                                            if (str.equals("hasMaxRepeatedCharacters")) {
                                                c = 11;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        default:
                                            c = 65535;
                                            break;
                                    }
                                    switch (c) {
                                        case 0:
                                            int minimumCharacterChangeLength = passwordPolicy.getMinimumCharacterChangeLength(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor43 = new MatrixCursor(new String[]{"getMinimumCharacterChangeLength"});
                                            matrixCursor43.addRow(new Integer[]{Integer.valueOf(minimumCharacterChangeLength)});
                                            return matrixCursor43;
                                        case 1:
                                            int maximumCharacterSequenceLength = passwordPolicy.getMaximumCharacterSequenceLength(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor44 = new MatrixCursor(new String[]{"getMaximumCharacterSequenceLength"});
                                            matrixCursor44.addRow(new Integer[]{Integer.valueOf(maximumCharacterSequenceLength)});
                                            return matrixCursor44;
                                        case 2:
                                            int maximumNumericSequenceLength = passwordPolicy.getMaximumNumericSequenceLength(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor45 = new MatrixCursor(new String[]{"getMaximumNumericSequenceLength"});
                                            matrixCursor45.addRow(new Integer[]{Integer.valueOf(maximumNumericSequenceLength)});
                                            return matrixCursor45;
                                        case 3:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 1) {
                                                return null;
                                            }
                                            boolean hasForbiddenStringDistance = passwordPolicy.hasForbiddenStringDistance(new ContextInfo(callingUid), strArr2[0], strArr2[1]);
                                            MatrixCursor matrixCursor46 = new MatrixCursor(new String[]{"hasForbiddenStringDistance"});
                                            matrixCursor46.addRow(new Boolean[]{Boolean.valueOf(hasForbiddenStringDistance)});
                                            return matrixCursor46;
                                        case 4:
                                            int maximumCharacterOccurences = passwordPolicy.getMaximumCharacterOccurences(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor47 = new MatrixCursor(new String[]{"getMaximumCharacterOccurences"});
                                            matrixCursor47.addRow(new Integer[]{Integer.valueOf(maximumCharacterOccurences)});
                                            return matrixCursor47;
                                        case 5:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            String requiredPwdPatternRestrictions = passwordPolicy.getRequiredPwdPatternRestrictions(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor48 = new MatrixCursor(new String[]{"getRequiredPwdPatternRestrictions"});
                                            matrixCursor48.addRow(new String[]{requiredPwdPatternRestrictions});
                                            return matrixCursor48;
                                        case 6:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            List forbiddenStrings = passwordPolicy.getForbiddenStrings(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor49 = new MatrixCursor(new String[]{"getForbiddenStrings"});
                                            if (forbiddenStrings == null || forbiddenStrings.isEmpty()) {
                                                return matrixCursor49;
                                            }
                                            Iterator it2 = forbiddenStrings.iterator();
                                            while (it2.hasNext()) {
                                                matrixCursor49.addRow(new String[]{(String) it2.next()});
                                            }
                                            return matrixCursor49;
                                        case 7:
                                            int passwordChangeTimeout = passwordPolicy.getPasswordChangeTimeout(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor50 = new MatrixCursor(new String[]{"getPasswordChangeTimeout"});
                                            matrixCursor50.addRow(new Integer[]{Integer.valueOf(passwordChangeTimeout)});
                                            return matrixCursor50;
                                        case '\b':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean hasForbiddenData = passwordPolicy.hasForbiddenData(new ContextInfo(callingUid), strArr2[0]);
                                            MatrixCursor matrixCursor51 = new MatrixCursor(new String[]{"hasForbiddenData"});
                                            matrixCursor51.addRow(new Boolean[]{Boolean.valueOf(hasForbiddenData)});
                                            return matrixCursor51;
                                        case '\t':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean hasForbiddenCharacterSequence = passwordPolicy.hasForbiddenCharacterSequence(new ContextInfo(callingUid), strArr2[0]);
                                            MatrixCursor matrixCursor52 = new MatrixCursor(new String[]{"hasForbiddenCharacterSequence"});
                                            matrixCursor52.addRow(new Boolean[]{Boolean.valueOf(hasForbiddenCharacterSequence)});
                                            return matrixCursor52;
                                        case '\n':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean hasForbiddenNumericSequence = passwordPolicy.hasForbiddenNumericSequence(new ContextInfo(callingUid), strArr2[0]);
                                            MatrixCursor matrixCursor53 = new MatrixCursor(new String[]{"hasForbiddenNumericSequence"});
                                            matrixCursor53.addRow(new Boolean[]{Boolean.valueOf(hasForbiddenNumericSequence)});
                                            return matrixCursor53;
                                        case 11:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean hasMaxRepeatedCharacters = passwordPolicy.hasMaxRepeatedCharacters(new ContextInfo(callingUid), strArr2[0]);
                                            MatrixCursor matrixCursor54 = new MatrixCursor(new String[]{"hasMaxRepeatedCharacters"});
                                            matrixCursor54.addRow(new Boolean[]{Boolean.valueOf(hasMaxRepeatedCharacters)});
                                            return matrixCursor54;
                                        default:
                                            return null;
                                    }
                                }
                                break;
                            case 14:
                                PasswordPolicy passwordPolicy2 = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
                                if (passwordPolicy2 != null && str != null) {
                                    switch (str.hashCode()) {
                                        case -1317369104:
                                            if (str.equals("getMaximumFailedPasswordsForDisable")) {
                                                c3 = 0;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -478760695:
                                            if (str.equals("isExternalStorageForFailedPasswordsWipeExcluded")) {
                                                c3 = 1;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -237908886:
                                            if (str.equals("isPasswordVisibilityEnabled")) {
                                                c3 = 2;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -162822849:
                                            break;
                                        case 401879559:
                                            if (str.equals("getPasswordLockDelay")) {
                                                c3 = 4;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 592157313:
                                            if (str.equals("getCurrentFailedPasswordAttempts")) {
                                                c3 = 5;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1216363284:
                                            if (str.equals("isChangeRequested")) {
                                                c3 = 6;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1310765720:
                                            if (str.equals("isBiometricAuthenticationEnabledAsUser")) {
                                                c3 = 7;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1621724345:
                                            if (str.equals("isPasswordPatternMatched")) {
                                                c3 = '\b';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1694843943:
                                            if (str.equals("isPasswordVisibilityEnabledAsUser")) {
                                                c3 = '\t';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1979261851:
                                            if (str.equals("isBiometricAuthenticationEnabled")) {
                                                c3 = '\n';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        default:
                                            c3 = 65535;
                                            break;
                                    }
                                    switch (c3) {
                                        case 0:
                                            if (strArr2 != null) {
                                                Log.d("SecContentProvider", "PASSWORDPOLICY_MAXIMUMFAILEDPASSWORDSFORDISABLE_METHOD user id = " + strArr2[0]);
                                                maximumFailedPasswordsForDisable = passwordPolicy2.getMaximumFailedPasswordsForDisable(new ContextInfo(Integer.parseInt(strArr2[0])));
                                            } else {
                                                maximumFailedPasswordsForDisable = passwordPolicy2.getMaximumFailedPasswordsForDisable(new ContextInfo(callingUid));
                                            }
                                            Log.d("SecContentProvider", "PASSWORDPOLICY_MAXIMUMFAILEDPASSWORDSFORDISABLE_METHOD return = " + maximumFailedPasswordsForDisable);
                                            MatrixCursor matrixCursor55 = new MatrixCursor(new String[]{"getMaximumFailedPasswordsForDisable"});
                                            matrixCursor55.addRow(new Integer[]{Integer.valueOf(maximumFailedPasswordsForDisable)});
                                            return matrixCursor55;
                                        case 1:
                                            if (strArr2 != null) {
                                                Log.d("SecContentProvider", "PASSWORDPOLICY_EXTERNALSTORAGEFORFAILEDPASSWORDSWIPE_METHOD user id = " + strArr2[0]);
                                                isExternalStorageForFailedPasswordsWipeExcluded = passwordPolicy2.isExternalStorageForFailedPasswordsWipeExcluded(new ContextInfo(Integer.parseInt(strArr2[0])));
                                            } else {
                                                isExternalStorageForFailedPasswordsWipeExcluded = passwordPolicy2.isExternalStorageForFailedPasswordsWipeExcluded(new ContextInfo(callingUid));
                                            }
                                            Log.d("SecContentProvider", "PASSWORDPOLICY_EXTERNALSTORAGEFORFAILEDPASSWORDSWIPE_METHOD return = " + isExternalStorageForFailedPasswordsWipeExcluded);
                                            MatrixCursor matrixCursor56 = new MatrixCursor(new String[]{"isExternalStorageForFailedPasswordsWipeExcluded"});
                                            matrixCursor56.addRow(new Boolean[]{Boolean.valueOf(isExternalStorageForFailedPasswordsWipeExcluded)});
                                            return matrixCursor56;
                                        case 2:
                                            if (strArr2 == null) {
                                                isPasswordVisibilityEnabled = passwordPolicy2.isPasswordVisibilityEnabled(new ContextInfo(callingUid));
                                                Log.d("SecContentProvider", "isPasswordVisibilityEnabled callingUid = " + callingUid);
                                            } else {
                                                isPasswordVisibilityEnabled = passwordPolicy2.isPasswordVisibilityEnabled(new ContextInfo(Integer.parseInt(strArr2[0])));
                                                Log.d("SecContentProvider", "isPasswordVisibilityEnabled callingUid = " + Integer.parseInt(strArr2[0]));
                                            }
                                            Log.d("SecContentProvider", "isPasswordVisibilityEnabled return = " + isPasswordVisibilityEnabled);
                                            MatrixCursor matrixCursor57 = new MatrixCursor(new String[]{"isPasswordVisibilityEnabled"});
                                            matrixCursor57.addRow(new Boolean[]{Boolean.valueOf(isPasswordVisibilityEnabled)});
                                            return matrixCursor57;
                                        case 3:
                                            if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                                return null;
                                            }
                                            String str4 = strArr2[0];
                                            ComponentName unflattenFromString = str4 != null ? ComponentName.unflattenFromString(str4) : null;
                                            if (getArrayLength(strArr2) == 2) {
                                                Log.d("SecContentProvider", "PASSWORDPOLICY_MAXIMUMFAILEDPASSWORDSFORWIPE_METHOD user id = " + strArr2[0]);
                                                maximumFailedPasswordsForWipe = passwordPolicy2.getMaximumFailedPasswordsForWipe(new ContextInfo(Integer.parseInt(strArr2[1])), unflattenFromString);
                                            } else {
                                                maximumFailedPasswordsForWipe = passwordPolicy2.getMaximumFailedPasswordsForWipe(new ContextInfo(callingUid), unflattenFromString);
                                            }
                                            Log.d("SecContentProvider", "PASSWORDPOLICY_MAXIMUMFAILEDPASSWORDSFORWIPE_METHOD return = " + maximumFailedPasswordsForWipe);
                                            MatrixCursor matrixCursor58 = new MatrixCursor(new String[]{"getMaximumFailedPasswordsForWipe"});
                                            matrixCursor58.addRow(new Integer[]{Integer.valueOf(maximumFailedPasswordsForWipe)});
                                            return matrixCursor58;
                                        case 4:
                                            int passwordLockDelay = passwordPolicy2.getPasswordLockDelay(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor59 = new MatrixCursor(new String[]{"getPasswordLockDelay"});
                                            matrixCursor59.addRow(new Integer[]{Integer.valueOf(passwordLockDelay)});
                                            return matrixCursor59;
                                        case 5:
                                            int currentFailedPasswordAttemptsInternal = passwordPolicy2.getCurrentFailedPasswordAttemptsInternal(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor60 = new MatrixCursor(new String[]{str});
                                            matrixCursor60.addRow(new Integer[]{Integer.valueOf(currentFailedPasswordAttemptsInternal)});
                                            return matrixCursor60;
                                        case 6:
                                            int isChangeRequested = passwordPolicy2.isChangeRequested(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor61 = new MatrixCursor(new String[]{"isChangeRequested"});
                                            matrixCursor61.addRow(new Integer[]{Integer.valueOf(isChangeRequested)});
                                            return matrixCursor61;
                                        case 7:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 1) {
                                                return null;
                                            }
                                            boolean isBiometricAuthenticationEnabledAsUser = passwordPolicy2.isBiometricAuthenticationEnabledAsUser(Integer.parseInt(strArr2[0]), Integer.parseInt(strArr2[1]));
                                            MatrixCursor matrixCursor62 = new MatrixCursor(new String[]{"isBiometricAuthenticationEnabledAsUser"});
                                            matrixCursor62.addRow(new Boolean[]{Boolean.valueOf(isBiometricAuthenticationEnabledAsUser)});
                                            return matrixCursor62;
                                        case '\b':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isPasswordPatternMatched = passwordPolicy2.isPasswordPatternMatched(new ContextInfo(callingUid), strArr2[0]);
                                            MatrixCursor matrixCursor63 = new MatrixCursor(new String[]{"isPasswordPatternMatched"});
                                            matrixCursor63.addRow(new Boolean[]{Boolean.valueOf(isPasswordPatternMatched)});
                                            return matrixCursor63;
                                        case '\t':
                                            if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                                return null;
                                            }
                                            boolean isPasswordVisibilityEnabledAsUser = passwordPolicy2.isPasswordVisibilityEnabledAsUser(Integer.parseInt(strArr2[0]));
                                            Log.d("SecContentProvider", "isPasswordVisibilityEnabledAsUser return = " + isPasswordVisibilityEnabledAsUser);
                                            MatrixCursor matrixCursor64 = new MatrixCursor(new String[]{"isPasswordVisibilityEnabledAsUser"});
                                            matrixCursor64.addRow(new Boolean[]{Boolean.valueOf(isPasswordVisibilityEnabledAsUser)});
                                            return matrixCursor64;
                                        case '\n':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isBiometricAuthenticationEnabled = passwordPolicy2.isBiometricAuthenticationEnabled(new ContextInfo(callingUid), Integer.parseInt(strArr2[0]));
                                            MatrixCursor matrixCursor65 = new MatrixCursor(new String[]{"isBiometricAuthenticationEnabled"});
                                            matrixCursor65.addRow(new Boolean[]{Boolean.valueOf(isBiometricAuthenticationEnabled)});
                                            return matrixCursor65;
                                        default:
                                            return null;
                                    }
                                }
                                break;
                            case 15:
                                RestrictionPolicy restrictionPolicy2 = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
                                if (restrictionPolicy2 != null && str != null) {
                                    switch (str.hashCode()) {
                                        case -1702876742:
                                            if (str.equals("isDataSavingAllowed")) {
                                                c3 = 0;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1570760875:
                                            if (str.equals("isBluetoothTetheringEnabled")) {
                                                c3 = 1;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1551817604:
                                            if (str.equals("isClipboardAllowed")) {
                                                c3 = 2;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1274886471:
                                            break;
                                        case -953088694:
                                            if (str.equals("isCellularDataAllowed")) {
                                                c3 = 4;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -915901781:
                                            if (str.equals("isAudioRecordAllowed")) {
                                                c3 = 5;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -880026500:
                                            if (str.equals("isBackupAllowed")) {
                                                c3 = 6;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -759690465:
                                            if (str.equals("isBackgroundDataEnabled")) {
                                                c3 = 7;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -727198894:
                                            if (str.equals("isCameraEnabled")) {
                                                c3 = '\b';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 4064765:
                                            if (str.equals("isBackgroundProcessLimitEnabled")) {
                                                c3 = '\t';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 133006834:
                                            if (str.equals("isClipboardShareAllowedAsUser")) {
                                                c3 = '\n';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 782547109:
                                            if (str.equals("isDeveloperModeAllowed")) {
                                                c3 = 11;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 898878420:
                                            if (str.equals("isAndroidBeamAllowed")) {
                                                c3 = '\f';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1026784253:
                                            if (str.equals("isActivationLockAllowed")) {
                                                c3 = '\r';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1324990969:
                                            if (str.equals("isFactoryResetAllowed")) {
                                                c3 = 14;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1677937823:
                                            if (str.equals("isFastEncryptionAllowed")) {
                                                c3 = 15;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1901462133:
                                            if (str.equals("isClipboardShareAllowed")) {
                                                c3 = 16;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1997945417:
                                            if (str.equals("isAirplaneModeAllowed")) {
                                                c3 = 17;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 2117956569:
                                            if (str.equals("checkPackageSource")) {
                                                c3 = 18;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        default:
                                            c3 = 65535;
                                            break;
                                    }
                                    switch (c3) {
                                        case 0:
                                            boolean isDataSavingAllowed = restrictionPolicy2.isDataSavingAllowed();
                                            MatrixCursor matrixCursor66 = new MatrixCursor(new String[]{"isDataSavingAllowed"});
                                            matrixCursor66.addRow(new Boolean[]{Boolean.valueOf(isDataSavingAllowed)});
                                            return matrixCursor66;
                                        case 1:
                                            boolean isBluetoothTetheringEnabled = restrictionPolicy2.isBluetoothTetheringEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor67 = new MatrixCursor(new String[]{"isBluetoothTetheringEnabled"});
                                            matrixCursor67.addRow(new Boolean[]{Boolean.valueOf(isBluetoothTetheringEnabled)});
                                            return matrixCursor67;
                                        case 2:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                isClipboardAllowed = restrictionPolicy2.isClipboardAllowed(new ContextInfo(callingUid), false);
                                            } else {
                                                isClipboardAllowed = restrictionPolicy2.isClipboardAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            }
                                            MatrixCursor matrixCursor68 = new MatrixCursor(new String[]{"isClipboardAllowed"});
                                            matrixCursor68.addRow(new Boolean[]{Boolean.valueOf(isClipboardAllowed)});
                                            return matrixCursor68;
                                        case 3:
                                            if (strArr2 == null || getArrayLength(strArr2) < 2) {
                                                return null;
                                            }
                                            boolean isClipboardAllowedAsUser = restrictionPolicy2.isClipboardAllowedAsUser(Boolean.parseBoolean(strArr2[0]), Integer.parseInt(strArr2[1]));
                                            Log.d("SecContentProvider", "SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_ALLOWED_AS_USER return = " + isClipboardAllowedAsUser);
                                            MatrixCursor matrixCursor69 = new MatrixCursor(new String[]{"isClipboardAllowedAsUser"});
                                            matrixCursor69.addRow(new Boolean[]{Boolean.valueOf(isClipboardAllowedAsUser)});
                                            return matrixCursor69;
                                        case 4:
                                            boolean isCellularDataAllowed = restrictionPolicy2.isCellularDataAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor70 = new MatrixCursor(new String[]{"isCellularDataAllowed"});
                                            matrixCursor70.addRow(new Boolean[]{Boolean.valueOf(isCellularDataAllowed)});
                                            return matrixCursor70;
                                        case 5:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isAudioRecordAllowed = restrictionPolicy2.isAudioRecordAllowed(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor71 = new MatrixCursor(new String[]{"isAudioRecordAllowed"});
                                            matrixCursor71.addRow(new Boolean[]{Boolean.valueOf(isAudioRecordAllowed)});
                                            return matrixCursor71;
                                        case 6:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isBackupAllowed = restrictionPolicy2.isBackupAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor72 = new MatrixCursor(new String[]{"isBackupAllowed"});
                                            matrixCursor72.addRow(new Boolean[]{Boolean.valueOf(isBackupAllowed)});
                                            return matrixCursor72;
                                        case 7:
                                            boolean isBackgroundDataEnabled = restrictionPolicy2.isBackgroundDataEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor73 = new MatrixCursor(new String[]{"isBackgroundDataEnabled"});
                                            matrixCursor73.addRow(new Boolean[]{Boolean.valueOf(isBackgroundDataEnabled)});
                                            return matrixCursor73;
                                        case '\b':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isCameraEnabled = restrictionPolicy2.isCameraEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor74 = new MatrixCursor(new String[]{"isCameraEnabled"});
                                            matrixCursor74.addRow(new Boolean[]{Boolean.valueOf(isCameraEnabled)});
                                            return matrixCursor74;
                                        case '\t':
                                            boolean isBackgroundProcessLimitAllowed = restrictionPolicy2.isBackgroundProcessLimitAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor75 = new MatrixCursor(new String[]{"isBackgroundProcessLimitEnabled"});
                                            matrixCursor75.addRow(new Boolean[]{Boolean.valueOf(isBackgroundProcessLimitAllowed)});
                                            return matrixCursor75;
                                        case '\n':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isClipboardShareAllowedAsUser = restrictionPolicy2.isClipboardShareAllowedAsUser(Integer.parseInt(strArr2[0]));
                                            MatrixCursor matrixCursor76 = new MatrixCursor(new String[]{"isClipboardShareAllowedAsUser"});
                                            matrixCursor76.addRow(new Boolean[]{Boolean.valueOf(isClipboardShareAllowedAsUser)});
                                            return matrixCursor76;
                                        case 11:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isDeveloperModeAllowed = restrictionPolicy2.isDeveloperModeAllowed(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor77 = new MatrixCursor(new String[]{"isDeveloperModeAllowed"});
                                            matrixCursor77.addRow(new Boolean[]{Boolean.valueOf(isDeveloperModeAllowed)});
                                            return matrixCursor77;
                                        case '\f':
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                Boolean.parseBoolean(strArr2[0]);
                                            }
                                            MatrixCursor matrixCursor78 = new MatrixCursor(new String[]{"isAndroidBeamAllowed"});
                                            matrixCursor78.addRow(new Boolean[]{Boolean.TRUE});
                                            return matrixCursor78;
                                        case '\r':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isActivationLockAllowed = restrictionPolicy2.isActivationLockAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor79 = new MatrixCursor(new String[]{"isActivationLockAllowed"});
                                            matrixCursor79.addRow(new Boolean[]{Boolean.valueOf(isActivationLockAllowed)});
                                            return matrixCursor79;
                                        case 14:
                                            boolean isFactoryResetAllowed = restrictionPolicy2.isFactoryResetAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor80 = new MatrixCursor(new String[]{"isFactoryResetAllowed"});
                                            matrixCursor80.addRow(new Boolean[]{Boolean.valueOf(isFactoryResetAllowed)});
                                            return matrixCursor80;
                                        case 15:
                                            if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                                return null;
                                            }
                                            boolean isFastEncryptionAllowed = restrictionPolicy2.isFastEncryptionAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            Log.d("SecContentProvider", "isFastEncryptionAllowed return = " + isFastEncryptionAllowed);
                                            MatrixCursor matrixCursor81 = new MatrixCursor(new String[]{"isFastEncryptionAllowed"});
                                            matrixCursor81.addRow(new Boolean[]{Boolean.valueOf(isFastEncryptionAllowed)});
                                            return matrixCursor81;
                                        case 16:
                                            boolean isClipboardShareAllowed = restrictionPolicy2.isClipboardShareAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor82 = new MatrixCursor(new String[]{"isClipboardShareAllowed"});
                                            matrixCursor82.addRow(new Boolean[]{Boolean.valueOf(isClipboardShareAllowed)});
                                            return matrixCursor82;
                                        case 17:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isAirplaneModeAllowed = restrictionPolicy2.isAirplaneModeAllowed(r11);
                                            MatrixCursor matrixCursor83 = new MatrixCursor(new String[]{"isAirplaneModeAllowed"});
                                            matrixCursor83.addRow(new Boolean[]{Boolean.valueOf(isAirplaneModeAllowed)});
                                            return matrixCursor83;
                                        case 18:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean checkPackageSource = restrictionPolicy2.checkPackageSource(userId, strArr2[0]);
                                            MatrixCursor matrixCursor84 = new MatrixCursor(new String[]{"checkPackageSource"});
                                            matrixCursor84.addRow(new Boolean[]{Boolean.valueOf(checkPackageSource)});
                                            return matrixCursor84;
                                        default:
                                            return null;
                                    }
                                }
                                break;
                            case 16:
                                RestrictionPolicy restrictionPolicy3 = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
                                if (restrictionPolicy3 != null && str != null) {
                                    switch (str.hashCode()) {
                                        case -1956960037:
                                            if (str.equals("isNFCEnabledWithMsg")) {
                                                c3 = 0;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1897949058:
                                            if (str.equals("isNonTrustedAppInstallBlocked")) {
                                                c3 = 1;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1812838575:
                                            if (str.equals("isGoogleCrashReportedAllowed")) {
                                                c3 = 2;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1766577110:
                                            break;
                                        case -1430015315:
                                            if (str.equals("isMicrophoneEnabled")) {
                                                c3 = 4;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1297242688:
                                            if (str.equals("isLockScreenEnabled")) {
                                                c3 = 5;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1141302770:
                                            if (str.equals("isNewAdminInstallationEnabled")) {
                                                c3 = 6;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -971060053:
                                            if (str.equals("isFirmwareAutoUpdateAllowed")) {
                                                c3 = 7;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -769995198:
                                            if (str.equals("isLockScreenViewAllowed")) {
                                                c3 = '\b';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -448109426:
                                            if (str.equals("isFirmwareRecoveryAllowed")) {
                                                c3 = '\t';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -170231546:
                                            if (str.equals("isNonMarketAppAllowed")) {
                                                c3 = '\n';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 95255019:
                                            if (str.equals("isHomeKeyEnabled")) {
                                                c3 = 11;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 96378173:
                                            if (str.equals("isHeadPhoneEnabled")) {
                                                c3 = '\f';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 643308888:
                                            if (str.equals("isMockLocationEnabled")) {
                                                c3 = '\r';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 921097184:
                                            if (str.equals("isNFCEnabled")) {
                                                c3 = 14;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1272006933:
                                            if (str.equals("isGoogleAccountsAutoSyncAllowed")) {
                                                c3 = 15;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1815831831:
                                            if (str.equals("isKillingActivitiesOnLeaveAllowed")) {
                                                c3 = 16;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        default:
                                            c3 = 65535;
                                            break;
                                    }
                                    switch (c3) {
                                        case 0:
                                            MatrixCursor matrixCursor85 = new MatrixCursor(new String[]{"isNFCEnabledWithMsg"});
                                            matrixCursor85.addRow(new Boolean[]{Boolean.TRUE});
                                            return matrixCursor85;
                                        case 1:
                                            boolean isNonTrustedAppInstallBlocked = restrictionPolicy3.isNonTrustedAppInstallBlocked(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor86 = new MatrixCursor(new String[]{"isNonTrustedAppInstallBlocked"});
                                            matrixCursor86.addRow(new Boolean[]{Boolean.valueOf(isNonTrustedAppInstallBlocked)});
                                            return matrixCursor86;
                                        case 2:
                                            boolean isGoogleCrashReportAllowed = restrictionPolicy3.isGoogleCrashReportAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor87 = new MatrixCursor(new String[]{"isGoogleCrashReportedAllowed"});
                                            matrixCursor87.addRow(new Boolean[]{Boolean.valueOf(isGoogleCrashReportAllowed)});
                                            return matrixCursor87;
                                        case 3:
                                            if (strArr2 == null || getArrayLength(strArr2) < 2) {
                                                return null;
                                            }
                                            boolean isMicrophoneEnabledAsUser = restrictionPolicy3.isMicrophoneEnabledAsUser(Boolean.parseBoolean(strArr2[0]), Integer.parseInt(strArr2[1]));
                                            Log.d("SecContentProvider", "isMicrophoneEnabledAsUser return = " + isMicrophoneEnabledAsUser);
                                            MatrixCursor matrixCursor88 = new MatrixCursor(new String[]{"isMicrophoneEnabledAsUser"});
                                            matrixCursor88.addRow(new Boolean[]{Boolean.valueOf(isMicrophoneEnabledAsUser)});
                                            return matrixCursor88;
                                        case 4:
                                            if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                                return null;
                                            }
                                            if (getArrayLength(strArr2) == 2) {
                                                isMicrophoneEnabled = restrictionPolicy3.isMicrophoneEnabled(new ContextInfo(Integer.parseInt(strArr2[0])), Boolean.parseBoolean(strArr2[1]));
                                            } else {
                                                isMicrophoneEnabled = restrictionPolicy3.isMicrophoneEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            }
                                            Log.d("SecContentProvider", "isMicrophoneEnabled return = " + isMicrophoneEnabled);
                                            MatrixCursor matrixCursor89 = new MatrixCursor(new String[]{"isMicrophoneEnabled"});
                                            matrixCursor89.addRow(new Boolean[]{Boolean.valueOf(isMicrophoneEnabled)});
                                            return matrixCursor89;
                                        case 5:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isLockScreenEnabled = restrictionPolicy3.isLockScreenEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor90 = new MatrixCursor(new String[]{"isLockScreenEnabled"});
                                            matrixCursor90.addRow(new Boolean[]{Boolean.valueOf(isLockScreenEnabled)});
                                            return matrixCursor90;
                                        case 6:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isNewAdminInstallationEnabled = restrictionPolicy3.isNewAdminInstallationEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor91 = new MatrixCursor(new String[]{"isNewAdminInstallationEnabled"});
                                            matrixCursor91.addRow(new Boolean[]{Boolean.valueOf(isNewAdminInstallationEnabled)});
                                            return matrixCursor91;
                                        case 7:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isFirmwareAutoUpdateAllowed = restrictionPolicy3.isFirmwareAutoUpdateAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor92 = new MatrixCursor(new String[]{"isFirmwareAutoUpdateAllowed"});
                                            matrixCursor92.addRow(new Boolean[]{Boolean.valueOf(isFirmwareAutoUpdateAllowed)});
                                            return matrixCursor92;
                                        case '\b':
                                            if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                                return null;
                                            }
                                            if (getArrayLength(strArr2) == 2) {
                                                isLockScreenViewAllowed = restrictionPolicy3.isLockScreenViewAllowed(new ContextInfo(Integer.parseInt(strArr2[0])), Integer.parseInt(strArr2[1]));
                                            } else {
                                                isLockScreenViewAllowed = restrictionPolicy3.isLockScreenViewAllowed(new ContextInfo(callingUid), Integer.parseInt(strArr2[0]));
                                            }
                                            Log.d("SecContentProvider", "isLockScreenViewAllowed return = " + isLockScreenViewAllowed);
                                            MatrixCursor matrixCursor93 = new MatrixCursor(new String[]{"isLockScreenViewAllowed"});
                                            matrixCursor93.addRow(new Boolean[]{Boolean.valueOf(isLockScreenViewAllowed)});
                                            return matrixCursor93;
                                        case '\t':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isFirmwareRecoveryAllowed = restrictionPolicy3.isFirmwareRecoveryAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor94 = new MatrixCursor(new String[]{"isFirmwareRecoveryAllowed"});
                                            matrixCursor94.addRow(new Boolean[]{Boolean.valueOf(isFirmwareRecoveryAllowed)});
                                            return matrixCursor94;
                                        case '\n':
                                            if (strArr2 != null) {
                                                isNonMarketAppAllowed = restrictionPolicy3.isNonMarketAppAllowed(new ContextInfo(Integer.parseInt(strArr2[0])));
                                            } else {
                                                isNonMarketAppAllowed = restrictionPolicy3.isNonMarketAppAllowed(new ContextInfo(callingUid));
                                            }
                                            MatrixCursor matrixCursor95 = new MatrixCursor(new String[]{"isNonMarketAppAllowed"});
                                            matrixCursor95.addRow(new Boolean[]{Boolean.valueOf(isNonMarketAppAllowed)});
                                            return matrixCursor95;
                                        case 11:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isHomeKeyEnabled = restrictionPolicy3.isHomeKeyEnabled(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor96 = new MatrixCursor(new String[]{"isHomeKeyEnabled"});
                                            matrixCursor96.addRow(new Boolean[]{Boolean.valueOf(isHomeKeyEnabled)});
                                            return matrixCursor96;
                                        case '\f':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isHeadphoneEnabled = restrictionPolicy3.isHeadphoneEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor97 = new MatrixCursor(new String[]{"isHeadPhoneEnabled"});
                                            matrixCursor97.addRow(new Boolean[]{Boolean.valueOf(isHeadphoneEnabled)});
                                            return matrixCursor97;
                                        case '\r':
                                            if (strArr2 == null) {
                                                isMockLocationEnabled = restrictionPolicy3.isMockLocationEnabled(new ContextInfo(callingUid));
                                                Log.d("SecContentProvider", "callingUid = " + callingUid);
                                            } else {
                                                isMockLocationEnabled = restrictionPolicy3.isMockLocationEnabled(new ContextInfo(Integer.parseInt(strArr2[0])));
                                                Log.d("SecContentProvider", "callingUid = " + Integer.parseInt(strArr2[0]));
                                            }
                                            Log.d("SecContentProvider", "isMockLocationEnabled return = " + isMockLocationEnabled);
                                            MatrixCursor matrixCursor98 = new MatrixCursor(new String[]{"isMockLocationEnabled"});
                                            matrixCursor98.addRow(new Boolean[]{Boolean.valueOf(isMockLocationEnabled)});
                                            return matrixCursor98;
                                        case 14:
                                            MatrixCursor matrixCursor99 = new MatrixCursor(new String[]{"isNFCEnabled"});
                                            matrixCursor99.addRow(new Boolean[]{Boolean.TRUE});
                                            return matrixCursor99;
                                        case 15:
                                            boolean isGoogleAccountsAutoSyncAllowed = restrictionPolicy3.isGoogleAccountsAutoSyncAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor100 = new MatrixCursor(new String[]{"isGoogleAccountsAutoSyncAllowed"});
                                            matrixCursor100.addRow(new Boolean[]{Boolean.valueOf(isGoogleAccountsAutoSyncAllowed)});
                                            return matrixCursor100;
                                        case 16:
                                            if (strArr2 == null) {
                                                isKillingActivitiesOnLeaveAllowed = restrictionPolicy3.isKillingActivitiesOnLeaveAllowed(new ContextInfo(callingUid));
                                                Log.d("SecContentProvider", "isKillingActivitiesOnLeaveAllowed return = " + isKillingActivitiesOnLeaveAllowed + " callingUid : " + callingUid);
                                            } else {
                                                isKillingActivitiesOnLeaveAllowed = restrictionPolicy3.isKillingActivitiesOnLeaveAllowed(new ContextInfo(Integer.parseInt(strArr2[0])));
                                                Log.d("SecContentProvider", "isKillingActivitiesOnLeaveAllowed return = " + isKillingActivitiesOnLeaveAllowed + " userid : " + Integer.parseInt(strArr2[0]));
                                            }
                                            MatrixCursor matrixCursor101 = new MatrixCursor(new String[]{"isKillingActivitiesOnLeaveAllowed"});
                                            matrixCursor101.addRow(new Boolean[]{Boolean.valueOf(isKillingActivitiesOnLeaveAllowed)});
                                            return matrixCursor101;
                                        default:
                                            Log.d("SecContentProvider", "return null");
                                            return null;
                                    }
                                }
                                break;
                            case 17:
                                RestrictionPolicy restrictionPolicy4 = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
                                if (restrictionPolicy4 != null && str != null) {
                                    switch (str.hashCode()) {
                                        case -1816371435:
                                            if (str.equals("isShareListAllowed")) {
                                                c3 = 0;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1592740144:
                                            if (str.equals("isSBeamAllowed")) {
                                                c3 = 1;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1475729166:
                                            if (str.equals("isScreenPinningAllowedAsUser")) {
                                                c3 = 2;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1437543020:
                                            break;
                                        case -1119463358:
                                            if (str.equals("isStopSystemAppAllowed")) {
                                                c3 = 4;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -1023579618:
                                            if (str.equals("isOTAUpgradeAllowed")) {
                                                c3 = 5;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -857368074:
                                            if (str.equals("isSdCardEnabled")) {
                                                c3 = 6;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -678669329:
                                            if (str.equals("isSettingsChangesAllowedAsUser")) {
                                                c3 = 7;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -450468778:
                                            if (str.equals("isSmartClipModeAllowed")) {
                                                c3 = '\b';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -280519005:
                                            if (str.equals("isOdeTrustedBootVerificationEnabled")) {
                                                c3 = '\t';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case -87760338:
                                            if (str.equals("isSafeModeAllowed")) {
                                                c3 = '\n';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 563678972:
                                            if (str.equals("isIrisCameraEnabledAsUser")) {
                                                c3 = 11;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1049674893:
                                            if (str.equals("isUsbDebuggingEnabled")) {
                                                c3 = '\f';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1079677164:
                                            if (str.equals("isSDCardMoveAllowed")) {
                                                c3 = '\r';
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1408206365:
                                            if (str.equals("isStatusBarExpansionallowedAsUser")) {
                                                c3 = 14;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1538413106:
                                            if (str.equals("isSettingsChangesAllowed")) {
                                                c3 = 15;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1810835697:
                                            if (str.equals("isScreenCaptureEnabled")) {
                                                c3 = 16;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 2084959007:
                                            if (str.equals("isSVoiceAllowed")) {
                                                c3 = 17;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        default:
                                            c3 = 65535;
                                            break;
                                    }
                                    switch (c3) {
                                        case 0:
                                            if (getArrayLength(strArr2) == 1) {
                                                isShareListAllowed = restrictionPolicy4.isShareListAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            } else {
                                                isShareListAllowed = restrictionPolicy4.isShareListAllowed(new ContextInfo(callingUid), false);
                                            }
                                            MatrixCursor matrixCursor102 = new MatrixCursor(new String[]{"isShareListAllowed"});
                                            matrixCursor102.addRow(new Boolean[]{Boolean.valueOf(isShareListAllowed)});
                                            return matrixCursor102;
                                        case 1:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                Boolean.parseBoolean(strArr2[0]);
                                            }
                                            MatrixCursor matrixCursor103 = new MatrixCursor(new String[]{"isSBeamAllowed"});
                                            matrixCursor103.addRow(new Boolean[]{Boolean.TRUE});
                                            return matrixCursor103;
                                        case 2:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            if (strArr2 != null && getArrayLength(strArr2) == 2) {
                                                userId = Integer.parseInt(strArr2[1]);
                                            }
                                            boolean isScreenPinningAllowedAsUser = restrictionPolicy4.isScreenPinningAllowedAsUser(r11, userId);
                                            MatrixCursor matrixCursor104 = new MatrixCursor(new String[]{"isScreenPinningAllowedAsUser"});
                                            matrixCursor104.addRow(new Boolean[]{Boolean.valueOf(isScreenPinningAllowedAsUser)});
                                            return matrixCursor104;
                                        case 3:
                                            boolean restrictionPolicyIsPowerOffAllowed = restrictionPolicyIsPowerOffAllowed(strArr2, callingUid, restrictionPolicy4);
                                            MatrixCursor matrixCursor105 = new MatrixCursor(new String[]{str});
                                            matrixCursor105.addRow(new Boolean[]{Boolean.valueOf(restrictionPolicyIsPowerOffAllowed)});
                                            return matrixCursor105;
                                        case 4:
                                            boolean isStopSystemAppAllowed = restrictionPolicy4.isStopSystemAppAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor106 = new MatrixCursor(new String[]{"isStopSystemAppAllowed"});
                                            matrixCursor106.addRow(new Boolean[]{Boolean.valueOf(isStopSystemAppAllowed)});
                                            return matrixCursor106;
                                        case 5:
                                            boolean isOTAUpgradeAllowed = restrictionPolicy4.isOTAUpgradeAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor107 = new MatrixCursor(new String[]{"isOTAUpgradeAllowed"});
                                            matrixCursor107.addRow(new Boolean[]{Boolean.valueOf(isOTAUpgradeAllowed)});
                                            return matrixCursor107;
                                        case 6:
                                            boolean isSdCardEnabled = restrictionPolicy4.isSdCardEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor108 = new MatrixCursor(new String[]{"isSdCardEnabled"});
                                            matrixCursor108.addRow(new Boolean[]{Boolean.valueOf(isSdCardEnabled)});
                                            return matrixCursor108;
                                        case 7:
                                            if (strArr2 == null || getArrayLength(strArr2) < 2) {
                                                return null;
                                            }
                                            boolean isSettingsChangesAllowedAsUser = restrictionPolicy4.isSettingsChangesAllowedAsUser(Boolean.parseBoolean(strArr2[0]), Integer.parseInt(strArr2[1]));
                                            Log.d("SecContentProvider", "RESTRICTIONPOLICY_SETTINGSCHANGESASUSER_METHOD return = " + isSettingsChangesAllowedAsUser);
                                            MatrixCursor matrixCursor109 = new MatrixCursor(new String[]{"isSettingsChangesAllowedAsUser"});
                                            matrixCursor109.addRow(new Boolean[]{Boolean.valueOf(isSettingsChangesAllowedAsUser)});
                                            return matrixCursor109;
                                        case '\b':
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isSmartClipModeAllowedInternal = restrictionPolicy4.isSmartClipModeAllowedInternal(r11);
                                            MatrixCursor matrixCursor110 = new MatrixCursor(new String[]{"isSmartClipModeAllowed"});
                                            matrixCursor110.addRow(new Boolean[]{Boolean.valueOf(isSmartClipModeAllowedInternal)});
                                            return matrixCursor110;
                                        case '\t':
                                            boolean isODETrustedBootVerificationEnabled = restrictionPolicy4.isODETrustedBootVerificationEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor111 = new MatrixCursor(new String[]{"isOdeTrustedBootVerificationEnabled"});
                                            matrixCursor111.addRow(new Boolean[]{Boolean.valueOf(isODETrustedBootVerificationEnabled)});
                                            return matrixCursor111;
                                        case '\n':
                                            boolean isSafeModeAllowed = restrictionPolicy4.isSafeModeAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor112 = new MatrixCursor(new String[]{"isSafeModeAllowed"});
                                            matrixCursor112.addRow(new Boolean[]{Boolean.valueOf(isSafeModeAllowed)});
                                            return matrixCursor112;
                                        case 11:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isIrisCameraEnabled = restrictionPolicy4.isIrisCameraEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor113 = new MatrixCursor(new String[]{"isIrisCameraEnabledAsUser"});
                                            matrixCursor113.addRow(new Boolean[]{Boolean.valueOf(isIrisCameraEnabled)});
                                            return matrixCursor113;
                                        case '\f':
                                            boolean isUsbDebuggingEnabled = restrictionPolicy4.isUsbDebuggingEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor114 = new MatrixCursor(new String[]{"isUsbDebuggingEnabled"});
                                            matrixCursor114.addRow(new Boolean[]{Boolean.valueOf(isUsbDebuggingEnabled)});
                                            return matrixCursor114;
                                        case '\r':
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isSDCardMoveAllowed = restrictionPolicy4.isSDCardMoveAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor115 = new MatrixCursor(new String[]{"isSDCardMoveAllowed"});
                                            matrixCursor115.addRow(new Boolean[]{Boolean.valueOf(isSDCardMoveAllowed)});
                                            return matrixCursor115;
                                        case 14:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isStatusBarExpansionAllowedAsUser = restrictionPolicy4.isStatusBarExpansionAllowedAsUser(r11, userId);
                                            MatrixCursor matrixCursor116 = new MatrixCursor(new String[]{"isStatusBarExpansionallowedAsUser"});
                                            matrixCursor116.addRow(new Boolean[]{Boolean.valueOf(isStatusBarExpansionAllowedAsUser)});
                                            return matrixCursor116;
                                        case 15:
                                            if (strArr2 == null || getArrayLength(strArr2) < 1) {
                                                return null;
                                            }
                                            if (getArrayLength(strArr2) == 2) {
                                                isSettingsChangesAllowed = restrictionPolicy4.isSettingsChangesAllowed(new ContextInfo(Integer.parseInt(strArr2[1])), Boolean.parseBoolean(strArr2[0]));
                                            } else {
                                                isSettingsChangesAllowed = restrictionPolicy4.isSettingsChangesAllowed(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            }
                                            MatrixCursor matrixCursor117 = new MatrixCursor(new String[]{"isSettingsChangesAllowed"});
                                            matrixCursor117.addRow(new Boolean[]{Boolean.valueOf(isSettingsChangesAllowed)});
                                            return matrixCursor117;
                                        case 16:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isScreenCaptureEnabled = restrictionPolicy4.isScreenCaptureEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor118 = new MatrixCursor(new String[]{"isScreenCaptureEnabled"});
                                            matrixCursor118.addRow(new Boolean[]{Boolean.valueOf(isScreenCaptureEnabled)});
                                            return matrixCursor118;
                                        case 17:
                                            if (strArr2 != null && getArrayLength(strArr2) == 1) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            } else if (strArr2 != null && getArrayLength(strArr2) == 2) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                                callingUid = Integer.parseInt(strArr2[1]);
                                            }
                                            boolean isSVoiceAllowed = restrictionPolicy4.isSVoiceAllowed(new ContextInfo(callingUid), r11);
                                            Log.d("SecContentProvider", "isSVoiceAllowed result = " + isSVoiceAllowed + " uid = " + callingUid);
                                            MatrixCursor matrixCursor119 = new MatrixCursor(new String[]{"isSVoiceAllowed"});
                                            matrixCursor119.addRow(new Boolean[]{Boolean.valueOf(isSVoiceAllowed)});
                                            return matrixCursor119;
                                        default:
                                            return null;
                                    }
                                }
                                break;
                            case 18:
                                RestrictionPolicy restrictionPolicy5 = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
                                if (restrictionPolicy5 != null && str != null) {
                                    switch (str.hashCode()) {
                                        case -1782208912:
                                            if (str.equals("isPowerSavingModeAllowed")) {
                                                c = 0;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -1445958786:
                                            if (str.equals("isUsbMediaPlayerAvailable")) {
                                                c = 1;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -1312648512:
                                            break;
                                        case -1174671834:
                                            if (str.equals("isVideoRecordAllowed")) {
                                                c = 3;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -968171806:
                                            if (str.equals("isWifiEnabled")) {
                                                c = 4;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -925003042:
                                            if (str.equals("isVpnAllowed")) {
                                                c = 5;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -897703570:
                                            if (str.equals("isUserMobileDataLimitAllowed")) {
                                                c = 6;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -817968032:
                                            if (str.equals("isWifiDirectAllowed")) {
                                                c = 7;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -676424940:
                                            if (str.equals("isSDCardWriteAllowed")) {
                                                c = '\b';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -164602096:
                                            if (str.equals("isFotaVersionAllowed")) {
                                                c = '\t';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -81910439:
                                            if (str.equals("isUseSecureKeypadEnabled")) {
                                                c = '\n';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -71588778:
                                            if (str.equals("isGearPolicyEnabled")) {
                                                c = 11;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -15458762:
                                            if (str.equals("getAllowedFOTAInfo")) {
                                                c = '\f';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 314296336:
                                            if (str.equals("isWifiTetheringEnabled")) {
                                                c = '\r';
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 1595372020:
                                            if (str.equals("isUsbMassStorageEnabled")) {
                                                c = 14;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 1930419883:
                                            if (str.equals("isUsbTetheringEnabled")) {
                                                c = 15;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 1963068431:
                                            if (str.equals("isUsbHostStorageAllowed")) {
                                                c = 16;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 2022560462:
                                            if (str.equals("isScreenCaptureEnabledInternal")) {
                                                c = 17;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        default:
                                            c = 65535;
                                            break;
                                    }
                                    switch (c) {
                                        case 0:
                                            boolean restrictionPolicyisPowerSavingModeAllowed2 = restrictionPolicyisPowerSavingModeAllowed(callingUid, restrictionPolicy5);
                                            MatrixCursor matrixCursor120 = new MatrixCursor(new String[]{str});
                                            matrixCursor120.addRow(new Boolean[]{Boolean.valueOf(restrictionPolicyisPowerSavingModeAllowed2)});
                                            return matrixCursor120;
                                        case 1:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isUsbMediaPlayerAvailable = restrictionPolicy5.isUsbMediaPlayerAvailable(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor121 = new MatrixCursor(new String[]{"isUsbMediaPlayerAvailable"});
                                            matrixCursor121.addRow(new Boolean[]{Boolean.valueOf(isUsbMediaPlayerAvailable)});
                                            return matrixCursor121;
                                        case 2:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isWallpaperChangeAllowed = restrictionPolicy5.isWallpaperChangeAllowed(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor122 = new MatrixCursor(new String[]{"isWallpaperChangeAllowed"});
                                            matrixCursor122.addRow(new Boolean[]{Boolean.valueOf(isWallpaperChangeAllowed)});
                                            return matrixCursor122;
                                        case 3:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isVideoRecordAllowed = restrictionPolicy5.isVideoRecordAllowed(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor123 = new MatrixCursor(new String[]{"isVideoRecordAllowed"});
                                            matrixCursor123.addRow(new Boolean[]{Boolean.valueOf(isVideoRecordAllowed)});
                                            return matrixCursor123;
                                        case 4:
                                            WifiPolicy wifiPolicy = (WifiPolicy) EnterpriseService.getPolicyService("wifi_policy");
                                            r11 = (strArr2 != null || getArrayLength(strArr2) > 0) ? Boolean.parseBoolean(strArr2[0]) : false;
                                            if (wifiPolicy == null) {
                                                return null;
                                            }
                                            boolean isWifiAllowed = wifiPolicy.isWifiAllowed(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor124 = new MatrixCursor(new String[]{"isWifiEnabled"});
                                            matrixCursor124.addRow(new Boolean[]{Boolean.valueOf(isWifiAllowed)});
                                            return matrixCursor124;
                                        case 5:
                                            boolean isVpnAllowed = restrictionPolicy5.isVpnAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor125 = new MatrixCursor(new String[]{"isVpnAllowed"});
                                            matrixCursor125.addRow(new Boolean[]{Boolean.valueOf(isVpnAllowed)});
                                            return matrixCursor125;
                                        case 6:
                                            boolean isUserMobileDataLimitAllowed = restrictionPolicy5.isUserMobileDataLimitAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor126 = new MatrixCursor(new String[]{"isUserMobileDataLimitAllowed"});
                                            matrixCursor126.addRow(new Boolean[]{Boolean.valueOf(isUserMobileDataLimitAllowed)});
                                            return matrixCursor126;
                                        case 7:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isWifiDirectAllowed = restrictionPolicy5.isWifiDirectAllowed(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor127 = new MatrixCursor(new String[]{"isWifiDirectAllowed"});
                                            matrixCursor127.addRow(new Boolean[]{Boolean.valueOf(isWifiDirectAllowed)});
                                            return matrixCursor127;
                                        case '\b':
                                            boolean isSDCardWriteAllowed = restrictionPolicy5.isSDCardWriteAllowed(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor128 = new MatrixCursor(new String[]{"isSDCardWriteAllowed"});
                                            matrixCursor128.addRow(new Boolean[]{Boolean.valueOf(isSDCardWriteAllowed)});
                                            return matrixCursor128;
                                        case '\t':
                                            MatrixCursor matrixCursor129 = new MatrixCursor(new String[]{"isFotaVersionAllowed"});
                                            matrixCursor129.addRow(new Boolean[]{Boolean.TRUE});
                                            return matrixCursor129;
                                        case '\n':
                                            boolean isUseSecureKeypadEnabled = restrictionPolicy5.isUseSecureKeypadEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor130 = new MatrixCursor(new String[]{"isUseSecureKeypadEnabled"});
                                            matrixCursor130.addRow(new Boolean[]{Boolean.valueOf(isUseSecureKeypadEnabled)});
                                            return matrixCursor130;
                                        case 11:
                                            boolean isWearablePolicyEnabled = restrictionPolicy5.isWearablePolicyEnabled(new ContextInfo(callingUid), 1);
                                            MatrixCursor matrixCursor131 = new MatrixCursor(new String[]{str});
                                            matrixCursor131.addRow(new Boolean[]{Boolean.valueOf(isWearablePolicyEnabled)});
                                            return matrixCursor131;
                                        case '\f':
                                            List allowedFOTAInfo = restrictionPolicy5.getAllowedFOTAInfo(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor132 = new MatrixCursor(new String[]{"getAllowedFOTAInfo"});
                                            if (allowedFOTAInfo == null || allowedFOTAInfo.isEmpty()) {
                                                return matrixCursor132;
                                            }
                                            Iterator it3 = allowedFOTAInfo.iterator();
                                            while (it3.hasNext()) {
                                                matrixCursor132.addRow(new String[]{(String) it3.next()});
                                            }
                                            return matrixCursor132;
                                        case '\r':
                                            boolean isWifiTetheringEnabled = restrictionPolicy5.isWifiTetheringEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor133 = new MatrixCursor(new String[]{"isWifiTetheringEnabled"});
                                            matrixCursor133.addRow(new Boolean[]{Boolean.valueOf(isWifiTetheringEnabled)});
                                            return matrixCursor133;
                                        case 14:
                                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                                return null;
                                            }
                                            boolean isUsbMassStorageEnabled = restrictionPolicy5.isUsbMassStorageEnabled(new ContextInfo(callingUid), Boolean.parseBoolean(strArr2[0]));
                                            MatrixCursor matrixCursor134 = new MatrixCursor(new String[]{"isUsbMassStorageEnabled"});
                                            matrixCursor134.addRow(new Boolean[]{Boolean.valueOf(isUsbMassStorageEnabled)});
                                            return matrixCursor134;
                                        case 15:
                                            boolean isUsbTetheringEnabled = restrictionPolicy5.isUsbTetheringEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor135 = new MatrixCursor(new String[]{"isUsbTetheringEnabled"});
                                            matrixCursor135.addRow(new Boolean[]{Boolean.valueOf(isUsbTetheringEnabled)});
                                            return matrixCursor135;
                                        case 16:
                                            if (strArr2 != null && getArrayLength(strArr2) > 0) {
                                                r11 = Boolean.parseBoolean(strArr2[0]);
                                            }
                                            boolean isUsbHostStorageAllowed = restrictionPolicy5.isUsbHostStorageAllowed(new ContextInfo(callingUid), r11);
                                            MatrixCursor matrixCursor136 = new MatrixCursor(new String[]{"isUsbHostStorageAllowed"});
                                            matrixCursor136.addRow(new Boolean[]{Boolean.valueOf(isUsbHostStorageAllowed)});
                                            return matrixCursor136;
                                        case 17:
                                            boolean isScreenCaptureEnabledInternal = restrictionPolicy5.isScreenCaptureEnabledInternal((strArr2 != null || getArrayLength(strArr2) > 0) ? Boolean.parseBoolean(strArr2[0]) : false);
                                            MatrixCursor matrixCursor137 = new MatrixCursor(new String[]{str});
                                            matrixCursor137.addRow(new Boolean[]{Boolean.valueOf(isScreenCaptureEnabledInternal)});
                                            return matrixCursor137;
                                        default:
                                            return null;
                                    }
                                }
                                break;
                            case 19:
                                RoamingPolicy roamingPolicy = (RoamingPolicy) EnterpriseService.getPolicyService("roaming_policy");
                                if (roamingPolicy != null && str != null) {
                                    switch (str.hashCode()) {
                                        case -1054814673:
                                            if (str.equals("isRoamingVoiceCallsEnabled")) {
                                                c = 0;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -363038441:
                                            if (str.equals("isRoamingSyncEnabled")) {
                                                c = 1;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -202660568:
                                            break;
                                        case 1195681720:
                                            if (str.equals("isRoamingPushEnabled")) {
                                                c = 3;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        default:
                                            c = 65535;
                                            break;
                                    }
                                    switch (c) {
                                        case 0:
                                            boolean isRoamingVoiceCallsEnabled = roamingPolicy.isRoamingVoiceCallsEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor138 = new MatrixCursor(new String[]{"isRoamingVoiceCallsEnabled"});
                                            matrixCursor138.addRow(new Boolean[]{Boolean.valueOf(isRoamingVoiceCallsEnabled)});
                                            return matrixCursor138;
                                        case 1:
                                            boolean isRoamingSyncEnabled = roamingPolicy.isRoamingSyncEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor139 = new MatrixCursor(new String[]{"isRoamingSyncEnabled"});
                                            matrixCursor139.addRow(new Boolean[]{Boolean.valueOf(isRoamingSyncEnabled)});
                                            return matrixCursor139;
                                        case 2:
                                            boolean isRoamingDataEnabled = roamingPolicy.isRoamingDataEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor140 = new MatrixCursor(new String[]{"isRoamingDataEnabled"});
                                            matrixCursor140.addRow(new Boolean[]{Boolean.valueOf(isRoamingDataEnabled)});
                                            return matrixCursor140;
                                        case 3:
                                            boolean isRoamingPushEnabled = roamingPolicy.isRoamingPushEnabled(new ContextInfo(callingUid));
                                            MatrixCursor matrixCursor141 = new MatrixCursor(new String[]{"isRoamingPushEnabled"});
                                            matrixCursor141.addRow(new Boolean[]{Boolean.valueOf(isRoamingPushEnabled)});
                                            return matrixCursor141;
                                        default:
                                            return null;
                                    }
                                }
                                break;
                            case 20:
                                SecurityPolicy securityPolicy = (SecurityPolicy) EnterpriseService.getPolicyService("security_policy");
                                if (securityPolicy != null && str != null) {
                                    if (str.equals("isDodBannerVisible")) {
                                        boolean isDodBannerVisible = securityPolicy.isDodBannerVisible(new ContextInfo(callingUid));
                                        MatrixCursor matrixCursor142 = new MatrixCursor(new String[]{"isDodBannerVisible"});
                                        matrixCursor142.addRow(new Boolean[]{Boolean.valueOf(isDodBannerVisible)});
                                        return matrixCursor142;
                                    }
                                    if (!str.equals("getCredentialStorageStatus")) {
                                        return null;
                                    }
                                    int credentialStorageStatus = securityPolicy.getCredentialStorageStatus(new ContextInfo(callingUid));
                                    MatrixCursor matrixCursor143 = new MatrixCursor(new String[]{str});
                                    matrixCursor143.addRow(new Integer[]{Integer.valueOf(credentialStorageStatus)});
                                    Log.d("SecContentProvider", "getCredentialStorageStatus = " + credentialStorageStatus);
                                    return matrixCursor143;
                                }
                                break;
                        }
                    } else {
                        if (str == null || (profilePolicyService = (ProfilePolicyService) EnterpriseService.getPolicyService("profilepolicy")) == null || !str.equals("Restriction")) {
                            return null;
                        }
                        String str5 = strArr2[0];
                        boolean restrictionPolicy6 = profilePolicyService.getRestrictionPolicy(null, str5);
                        MatrixCursor matrixCursor144 = new MatrixCursor(new String[]{str5});
                        matrixCursor144.addRow(new Boolean[]{Boolean.valueOf(restrictionPolicy6)});
                        return matrixCursor144;
                    }
                }
            } else if (str != null) {
                switch (str.hashCode()) {
                    case -1962580733:
                        if (str.equals("isProfileAuthorizedBySecurityPolicy")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1394196042:
                        if (str.equals("bluetoothLog")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1321177311:
                        if (str.equals("isBluetoothLogEnabled")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1021593223:
                        if (str.equals("isHeadsetAllowedBySecurityPolicy")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -980051373:
                        break;
                    case -272534589:
                        if (str.equals("bluetoothSocketLog")) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 690962324:
                        if (str.equals("isSvcRfComPortNumberBlockedBySecurityPolicy")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 787428500:
                        if (str.equals("isSocketAllowedBySecurityPolicy")) {
                            c2 = 7;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1702397801:
                        if (str.equals("bluetoothLogForDevice")) {
                            c2 = '\b';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2102944089:
                        if (str.equals("bluetoothLogForRemote")) {
                            c2 = '\t';
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 0:
                        if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                            return null;
                        }
                        boolean isProfileAuthorizedBySecurityPolicy = BluetoothUtils.isProfileAuthorizedBySecurityPolicy(ParcelUuid.fromString(strArr2[0]));
                        MatrixCursor matrixCursor145 = new MatrixCursor(new String[]{"isProfileAuthorizedBySecurityPolicy"});
                        matrixCursor145.addRow(new Boolean[]{Boolean.valueOf(isProfileAuthorizedBySecurityPolicy)});
                        return matrixCursor145;
                    case 1:
                        if (strArr2 == null || getArrayLength(strArr2) < 2 || !BluetoothUtils.isBluetoothLogEnabled()) {
                            return null;
                        }
                        BluetoothUtils.bluetoothLog(strArr2[0], strArr2[1]);
                        return null;
                    case 2:
                        boolean isBluetoothLogEnabled2 = BluetoothUtils.isBluetoothLogEnabled();
                        MatrixCursor matrixCursor146 = new MatrixCursor(new String[]{"isBluetoothLogEnabled"});
                        matrixCursor146.addRow(new Boolean[]{Boolean.valueOf(isBluetoothLogEnabled2)});
                        return matrixCursor146;
                    case 3:
                        if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                            return null;
                        }
                        boolean isHeadsetAllowedBySecurityPolicy = BluetoothUtils.isHeadsetAllowedBySecurityPolicy(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(strArr2[0]));
                        MatrixCursor matrixCursor147 = new MatrixCursor(new String[]{"isHeadsetAllowedBySecurityPolicy"});
                        matrixCursor147.addRow(new Boolean[]{Boolean.valueOf(isHeadsetAllowedBySecurityPolicy)});
                        return matrixCursor147;
                    case 4:
                        if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                            return null;
                        }
                        boolean isPairingAllowedbySecurityPolicy = BluetoothUtils.isPairingAllowedbySecurityPolicy(strArr2[0]);
                        Log.d("SecContentProvider", "PAIRINGALLOWEDBYSECURITY = " + isPairingAllowedbySecurityPolicy);
                        MatrixCursor matrixCursor148 = new MatrixCursor(new String[]{"isPairingAllowedbySecurityPolicy"});
                        matrixCursor148.addRow(new Boolean[]{Boolean.valueOf(isPairingAllowedbySecurityPolicy)});
                        return matrixCursor148;
                    case 5:
                        if (strArr2 == null || getArrayLength(strArr2) < 3) {
                            return null;
                        }
                        BluetoothUtils.bluetoothSocketLog(strArr2[0], BluetoothAdapter.getDefaultAdapter().getRemoteDevice(strArr2[1]), Integer.parseInt(strArr2[2]), Integer.parseInt(strArr2[3]));
                        return null;
                    case 6:
                        if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                            return null;
                        }
                        boolean isSvcRfComPortNumberBlockedBySecurityPolicy = BluetoothUtils.isSvcRfComPortNumberBlockedBySecurityPolicy(Integer.parseInt(strArr2[0]));
                        MatrixCursor matrixCursor149 = new MatrixCursor(new String[]{"isSvcRfComPortNumberBlockedBySecurityPolicy"});
                        matrixCursor149.addRow(new Boolean[]{Boolean.valueOf(isSvcRfComPortNumberBlockedBySecurityPolicy)});
                        return matrixCursor149;
                    case 7:
                        if (strArr2 == null || getArrayLength(strArr2) < 3) {
                            return null;
                        }
                        boolean isSocketAllowedBySecurityPolicy = BluetoothUtils.isSocketAllowedBySecurityPolicy(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(strArr2[0]), Integer.parseInt(strArr2[1]), Integer.parseInt(strArr2[2]), ParcelUuid.fromString(strArr2[3]));
                        MatrixCursor matrixCursor150 = new MatrixCursor(new String[]{"isSocketAllowedBySecurityPolicy"});
                        matrixCursor150.addRow(new Boolean[]{Boolean.valueOf(isSocketAllowedBySecurityPolicy)});
                        return matrixCursor150;
                    case '\b':
                        if (strArr2 == null || getArrayLength(strArr2) < 3 || !BluetoothUtils.isBluetoothLogEnabled()) {
                            return null;
                        }
                        if (strArr2[2] == null) {
                            BluetoothUtils.bluetoothLog(strArr2[0], Integer.parseInt(strArr2[1]), (BluetoothDevice) null);
                            return null;
                        }
                        if (BluetoothAdapter.getDefaultAdapter() != null) {
                            BluetoothUtils.bluetoothLog(strArr2[0], Integer.parseInt(strArr2[1]), BluetoothAdapter.getDefaultAdapter().getRemoteDevice(strArr2[2]));
                            return null;
                        }
                        Log.d("SecContentProvider", "BluetoothAdapter is null");
                        return null;
                    case '\t':
                        if (strArr2 == null || getArrayLength(strArr2) < 3 || !BluetoothUtils.isBluetoothLogEnabled()) {
                            return null;
                        }
                        BluetoothUtils.bluetoothLog(strArr2[0], strArr2[1], strArr2[2]);
                        return null;
                    default:
                        return null;
                }
            }
        } else {
            AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
            if (auditLogService != null && str != null && str.equals("isAuditLogEnabled")) {
                boolean isAuditLogEnabledAsUser = auditLogService.isAuditLogEnabledAsUser(userId);
                MatrixCursor matrixCursor151 = new MatrixCursor(new String[]{"isAuditLogEnabled"});
                matrixCursor151.addRow(new Boolean[]{Boolean.valueOf(isAuditLogEnabledAsUser)});
                return matrixCursor151;
            }
        }
        return null;
    }

    public final boolean restrictionPolicyisPowerSavingModeAllowed(int i, RestrictionPolicy restrictionPolicy) {
        boolean isPowerSavingModeAllowed = restrictionPolicy.isPowerSavingModeAllowed(new ContextInfo(i));
        if (!isPowerSavingModeAllowed) {
            Log.d("SecContentProvider", "POWER_SAVING_MODE_ALLOWED cursor return " + isPowerSavingModeAllowed);
        }
        return isPowerSavingModeAllowed;
    }

    public final boolean restrictionPolicyIsPowerOffAllowed(String[] strArr, int i, RestrictionPolicy restrictionPolicy) {
        boolean z = false;
        if (strArr != null && getArrayLength(strArr) > 0) {
            z = Boolean.parseBoolean(strArr[0]);
        }
        return restrictionPolicy.isPowerOffAllowed(new ContextInfo(i), z);
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        String asString;
        String asString2;
        SecurityPolicy securityPolicy;
        String asString3;
        int callingUid = Binder.getCallingUid();
        StringBuilder sb = new StringBuilder();
        sb.append("insert(), uri = ");
        UriMatcher uriMatcher = URI_MATCHER;
        sb.append(uriMatcher.match(uri));
        Log.d("SecContentProvider", sb.toString());
        Slog.d("SecContentProvider", "called from " + getCallerName(callingUid));
        int match = uriMatcher.match(uri);
        if (match == 2) {
            AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
            if (auditLogService == null) {
                return null;
            }
            if (contentValues.containsKey("redactedMessage") && contentValues.containsKey("userid")) {
                auditLogService.RedactedAuditLoggerAsUser(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"), contentValues.getAsString("redactedMessage"), contentValues.getAsInteger("userid").intValue());
                return null;
            }
            if (contentValues.containsKey("redactedMessage")) {
                auditLogService.RedactedAuditLogger(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"), contentValues.getAsString("redactedMessage"));
                return null;
            }
            if (contentValues.containsKey("userid")) {
                auditLogService.AuditLoggerAsUser(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"), contentValues.getAsInteger("userid").intValue());
                return null;
            }
            auditLogService.AuditLogger(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"));
            return null;
        }
        if (match == 6) {
            CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
            if (certificatePolicy == null) {
                return null;
            }
            String asString4 = contentValues.getAsString("API");
            Integer asInteger = contentValues.getAsInteger("fail");
            String asString5 = contentValues.getAsString("module");
            if (asString4 == null || !asString4.equals("notifyCertificateFailure") || asInteger == null || asString5 == null) {
                return null;
            }
            certificatePolicy.notifyCertificateFailure(asString5, String.valueOf(asInteger), true);
            return null;
        }
        if (match == 10) {
            BrowserPolicy browserPolicy = (BrowserPolicy) EnterpriseService.getPolicyService("browser_policy");
            if (browserPolicy == null || (asString = contentValues.getAsString("API")) == null || !asString.equals("saveURLBlockedReport")) {
                return null;
            }
            browserPolicy.saveURLBlockedReportEnforcingFirewallPermission(new ContextInfo(callingUid), contentValues.getAsString("url"));
            return null;
        }
        if (match == 14) {
            PasswordPolicy passwordPolicy = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
            if (passwordPolicy == null || (asString2 = contentValues.getAsString("API")) == null || !asString2.equals("setPwdChangeRequested")) {
                return null;
            }
            passwordPolicy.setPwdChangeRequested(new ContextInfo(callingUid), contentValues.getAsInteger("flag").intValue());
            return null;
        }
        if (match != 20 || (securityPolicy = (SecurityPolicy) EnterpriseService.getPolicyService("security_policy")) == null || (asString3 = contentValues.getAsString("API")) == null || !asString3.equals("setDodBannerVisibleStatus")) {
            return null;
        }
        securityPolicy.setDodBannerVisibleStatus(new ContextInfo(callingUid), contentValues.getAsBoolean("isVisible").booleanValue());
        return null;
    }

    public int getArrayLength(String[] strArr) {
        if (strArr == null) {
            return 0;
        }
        try {
            return Array.getLength(strArr);
        } catch (Exception e) {
            Slog.e("SecContentProvider", "getArrayLength() return 0 but some exception occurs with invalid request.", e);
            return 0;
        }
    }

    public final String getCallerName(int i) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
        return nameForUid == null ? "fail to get caller name" : nameForUid;
    }
}
