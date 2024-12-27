package com.android.server.enterprise.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.auditlog.AuditLogService;
import com.android.server.enterprise.bluetooth.BluetoothPolicy;
import com.android.server.enterprise.browser.BrowserPolicy;
import com.android.server.enterprise.certificate.CertificatePolicy;
import com.android.server.enterprise.general.MiscPolicy;
import com.android.server.enterprise.profile.ProfilePolicyService;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.security.SecurityPolicy;
import com.samsung.android.knox.ContextInfo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class SecContentProvider extends ContentProvider {
    public static final UriMatcher URI_MATCHER;
    public Context mContext;

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        URI_MATCHER = uriMatcher;
        uriMatcher.addURI("com.sec.knox.provider", "AuditLog", 2);
        uriMatcher.addURI("com.sec.knox.provider", "BluetoothPolicy", 3);
        uriMatcher.addURI("com.sec.knox.provider", "BluetoothUtils", 4);
        uriMatcher.addURI("com.sec.knox.provider", "BrowserPolicy", 5);
        uriMatcher.addURI("com.sec.knox.provider", "CertificatePolicy", 6);
        uriMatcher.addURI("com.sec.knox.provider", "FirewallPolicy", 10);
        uriMatcher.addURI("com.sec.knox.provider", "LocationPolicy", 12);
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

    public static int getArrayLength(String[] strArr) {
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

    public static boolean restrictionPolicyIsPowerOffAllowed(String[] strArr, int i, RestrictionPolicy restrictionPolicy) {
        int i2;
        boolean z = false;
        if (strArr != null) {
            try {
                i2 = Array.getLength(strArr);
            } catch (Exception e) {
                Slog.e("SecContentProvider", "getArrayLength() return 0 but some exception occurs with invalid request.", e);
                i2 = 0;
            }
            if (i2 > 0) {
                z = Boolean.parseBoolean(strArr[0]);
            }
        }
        return restrictionPolicy.isPowerOffAllowed(new ContextInfo(i), z);
    }

    public static boolean restrictionPolicyisPowerSavingModeAllowed(int i, RestrictionPolicy restrictionPolicy) {
        boolean isPowerSavingModeAllowed = restrictionPolicy.isPowerSavingModeAllowed(new ContextInfo(i));
        if (!isPowerSavingModeAllowed) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("POWER_SAVING_MODE_ALLOWED cursor return ", "SecContentProvider", isPowerSavingModeAllowed);
        }
        return isPowerSavingModeAllowed;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        String asString;
        SecurityPolicy securityPolicy;
        String asString2;
        int callingUid = Binder.getCallingUid();
        StringBuilder sb = new StringBuilder("insert(), uri = ");
        UriMatcher uriMatcher = URI_MATCHER;
        sb.append(uriMatcher.match(uri));
        Log.d("SecContentProvider", sb.toString());
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            nameForUid = "fail to get caller name";
        }
        Slog.d("SecContentProvider", "called from ".concat(nameForUid));
        int match = uriMatcher.match(uri);
        if (match == 2) {
            AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
            if (auditLogService == null) {
                return null;
            }
            if (contentValues.containsKey("tag")) {
                ArrayList arrayList = new ArrayList();
                String asString3 = contentValues.getAsString("params");
                if (asString3 != null) {
                    arrayList.addAll(Arrays.asList(asString3.split(",")));
                }
                if (contentValues.containsKey("userid")) {
                    auditLogService.logEventAsUser(null, contentValues.getAsInteger("userid").intValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsInteger("tag").intValue(), arrayList);
                    return null;
                }
                auditLogService.logEventAsUser(null, -1, contentValues.getAsInteger("uid").intValue(), contentValues.getAsInteger("tag").intValue(), arrayList);
                return null;
            }
            if (contentValues.containsKey("redactedMessage")) {
                if (contentValues.containsKey("userid")) {
                    auditLogService.redactedAuditLoggerAsUser(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"), contentValues.getAsString("redactedMessage"), contentValues.getAsInteger("userid").intValue());
                    return null;
                }
                auditLogService.redactedAuditLogger(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"), contentValues.getAsString("redactedMessage"));
                return null;
            }
            if (contentValues.containsKey("userid")) {
                auditLogService.redactedAuditLoggerAsUser(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"), null, contentValues.getAsInteger("userid").intValue());
                return null;
            }
            auditLogService.redactedAuditLogger(new ContextInfo(callingUid), contentValues.getAsInteger("severity").intValue(), contentValues.getAsInteger("group").intValue(), contentValues.getAsBoolean("outcome").booleanValue(), contentValues.getAsInteger("uid").intValue(), contentValues.getAsString("component"), contentValues.getAsString("message"), null);
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
        if (match != 14) {
            if (match != 20 || (securityPolicy = (SecurityPolicy) EnterpriseService.getPolicyService("security_policy")) == null || (asString2 = contentValues.getAsString("API")) == null || !asString2.equals("setDodBannerVisibleStatus")) {
                return null;
            }
            securityPolicy.setDodBannerVisibleStatus(new ContextInfo(callingUid), contentValues.getAsBoolean("isVisible").booleanValue());
            return null;
        }
        PasswordPolicy passwordPolicy = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
        if (passwordPolicy == null || (asString = contentValues.getAsString("API")) == null || !asString.equals("setPwdChangeRequested")) {
            return null;
        }
        passwordPolicy.setPwdChangeRequested(new ContextInfo(callingUid), contentValues.getAsInteger("flag").intValue());
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        this.mContext = getContext();
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        boolean isLocationProviderBlockedAsUser;
        boolean restrictionPolicyisPowerSavingModeAllowed;
        ProfilePolicyService profilePolicyService;
        int maximumFailedPasswordsForDisable;
        boolean isPasswordVisibilityEnabled;
        boolean isMockLocationEnabled;
        boolean isKillingActivitiesOnLeaveAllowed;
        boolean z;
        char c;
        char c2;
        boolean z2;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        StringBuilder sb = new StringBuilder("query(), uri = ");
        UriMatcher uriMatcher = URI_MATCHER;
        sb.append(uriMatcher.match(uri));
        sb.append(" selection = ");
        sb.append(str);
        Log.d("SecContentProvider", sb.toString());
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            nameForUid = "fail to get caller name";
        }
        Slog.d("SecContentProvider", "called from ".concat(nameForUid));
        int match = uriMatcher.match(uri);
        if (match == 2) {
            AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
            if (auditLogService != null && str != null && str.equals("isAuditLogEnabled")) {
                boolean isAuditLogEnabledAsUser = auditLogService.isAuditLogEnabledAsUser(userId);
                MatrixCursor matrixCursor = new MatrixCursor(new String[]{"isAuditLogEnabled"});
                matrixCursor.addRow(new Boolean[]{Boolean.valueOf(isAuditLogEnabledAsUser)});
                return matrixCursor;
            }
        } else if (match == 3) {
            BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
            if (bluetoothPolicy != null && str != null) {
                switch (str) {
                    case "isBluetoothEnabled":
                        boolean isBluetoothEnabled = bluetoothPolicy.isBluetoothEnabled(new ContextInfo(callingUid));
                        Log.d("SecContentProvider", "isBluetoothEnabled = " + isBluetoothEnabled);
                        MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"isBluetoothEnabled"});
                        matrixCursor2.addRow(new Boolean[]{Boolean.valueOf(isBluetoothEnabled)});
                        return matrixCursor2;
                    case "isDesktopConnectivityEnabled":
                        if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                            return null;
                        }
                        boolean isDesktopConnectivityEnabled = bluetoothPolicy.isDesktopConnectivityEnabled(Boolean.parseBoolean(strArr2[0]));
                        MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"isDesktopConnectivityEnabled"});
                        matrixCursor3.addRow(new Boolean[]{Boolean.valueOf(isDesktopConnectivityEnabled)});
                        return matrixCursor3;
                    case "isDiscoverableEnabled":
                        boolean isDiscoverableEnabled = bluetoothPolicy.isDiscoverableEnabled(new ContextInfo(callingUid));
                        MatrixCursor matrixCursor4 = new MatrixCursor(new String[]{"isDiscoverableEnabled"});
                        matrixCursor4.addRow(new Boolean[]{Boolean.valueOf(isDiscoverableEnabled)});
                        return matrixCursor4;
                    case "isBLEAllowed":
                        boolean isBLEAllowed = bluetoothPolicy.isBLEAllowed(new ContextInfo(callingUid));
                        MatrixCursor matrixCursor5 = new MatrixCursor(new String[]{"isBLEAllowed"});
                        matrixCursor5.addRow(new Boolean[]{Boolean.valueOf(isBLEAllowed)});
                        return matrixCursor5;
                    case "isOutgoingCallsAllowed":
                        if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                            return null;
                        }
                        boolean isOutgoingCallsAllowed = bluetoothPolicy.isOutgoingCallsAllowed(Boolean.parseBoolean(strArr2[0]));
                        Log.d("SecContentProvider", "isOutgoingCallsAllowed = " + isOutgoingCallsAllowed);
                        MatrixCursor matrixCursor6 = new MatrixCursor(new String[]{"isOutgoingCallsAllowed"});
                        matrixCursor6.addRow(new Boolean[]{Boolean.valueOf(isOutgoingCallsAllowed)});
                        return matrixCursor6;
                    default:
                        return null;
                }
            }
        } else if (match != 4) {
            if (match == 5) {
                BrowserPolicy browserPolicy = (BrowserPolicy) EnterpriseService.getPolicyService("browser_policy");
                if (browserPolicy != null && str != null) {
                    switch (str) {
                        case "getPopupsSetting":
                            boolean browserSettingStatus = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 1);
                            MatrixCursor matrixCursor7 = new MatrixCursor(new String[]{"getPopupsSetting"});
                            matrixCursor7.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus)});
                            return matrixCursor7;
                        case "getAutoFillSetting":
                            boolean browserSettingStatus2 = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 4);
                            MatrixCursor matrixCursor8 = new MatrixCursor(new String[]{"getAutoFillSetting"});
                            matrixCursor8.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus2)});
                            return matrixCursor8;
                        case "getJavaScriptSetting":
                            boolean browserSettingStatus3 = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 16);
                            MatrixCursor matrixCursor9 = new MatrixCursor(new String[]{"getJavaScriptSetting"});
                            matrixCursor9.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus3)});
                            return matrixCursor9;
                        case "getHttpProxy":
                            String httpProxy = browserPolicy.getHttpProxy(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor10 = new MatrixCursor(new String[]{"getHttpProxy"});
                            matrixCursor10.addRow(new String[]{httpProxy});
                            return matrixCursor10;
                        case "getCookiesSetting":
                            boolean browserSettingStatus4 = browserPolicy.getBrowserSettingStatus(new ContextInfo(callingUid), 2);
                            MatrixCursor matrixCursor11 = new MatrixCursor(new String[]{"getCookiesSetting"});
                            matrixCursor11.addRow(new Boolean[]{Boolean.valueOf(browserSettingStatus4)});
                            return matrixCursor11;
                        default:
                            return null;
                    }
                }
            } else if (match == 6) {
                CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
                if (certificatePolicy != null && str != null) {
                    switch (str) {
                        case "isOcspCheckEnabled":
                            boolean isOcspCheckEnabled = certificatePolicy.isOcspCheckEnabled(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor12 = new MatrixCursor(new String[]{"isOcspCheckEnabled"});
                            matrixCursor12.addRow(new Boolean[]{Boolean.valueOf(isOcspCheckEnabled)});
                            return matrixCursor12;
                        case "isUserRemoveCertificatesAllowed":
                            boolean isUserRemoveCertificatesAllowed = (strArr2 == null || strArr2.length != 1) ? certificatePolicy.isUserRemoveCertificatesAllowed(new ContextInfo(callingUid)) : certificatePolicy.isUserRemoveCertificatesAllowedAsUser(Integer.parseInt(strArr2[0]));
                            MatrixCursor matrixCursor13 = new MatrixCursor(new String[]{"isUserRemoveCertificatesAllowed"});
                            matrixCursor13.addRow(new Boolean[]{Boolean.valueOf(isUserRemoveCertificatesAllowed)});
                            return matrixCursor13;
                        case "isCaCertificateDisabledAsUser":
                            if (strArr2 == null || getArrayLength(strArr2) <= 0) {
                                return null;
                            }
                            if (getArrayLength(strArr2) > 1) {
                                userId = Integer.parseInt(strArr2[1]);
                            }
                            boolean isCaCertificateDisabledAsUser = certificatePolicy.isCaCertificateDisabledAsUser(strArr2[0], userId);
                            MatrixCursor matrixCursor14 = new MatrixCursor(new String[]{"isCaCertificateDisabledAsUser"});
                            matrixCursor14.addRow(new Boolean[]{Boolean.valueOf(isCaCertificateDisabledAsUser)});
                            return matrixCursor14;
                        case "notifyCertificateFailure":
                            if (strArr2 == null || getArrayLength(strArr2) <= 2) {
                                return null;
                            }
                            certificatePolicy.notifyCertificateFailure(strArr2[0], strArr2[1], Boolean.parseBoolean(strArr2[2]));
                            return null;
                        case "isRevocationCheckEnabled":
                            boolean isRevocationCheckEnabled = certificatePolicy.isRevocationCheckEnabled(new ContextInfo(callingUid));
                            MatrixCursor matrixCursor15 = new MatrixCursor(new String[]{"isRevocationCheckEnabled"});
                            matrixCursor15.addRow(new Boolean[]{Boolean.valueOf(isRevocationCheckEnabled)});
                            return matrixCursor15;
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
                                break;
                            }
                            break;
                        case -1053530777:
                            if (str.equals("isGlobalProxyAllowed")) {
                                break;
                            }
                            break;
                        case 1000596208:
                            if (str.equals("getURLFilterEnabled")) {
                                break;
                            }
                            break;
                        case 1865999324:
                            if (str.equals("getURLFilterReportEnabled")) {
                                break;
                            }
                            break;
                    }
                    /*  JADX ERROR: Method code generation error
                        java.lang.NullPointerException: Switch insn not found in header
                        	at java.base/java.util.Objects.requireNonNull(Objects.java:235)
                        	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                        	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                        	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                        	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                        	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                        	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        */
                    /*
                        Method dump skipped, instructions count: 7988
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.content.SecContentProvider.query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String):android.database.Cursor");
                }

                @Override // android.content.ContentProvider
                public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
                    return 0;
                }
            }
