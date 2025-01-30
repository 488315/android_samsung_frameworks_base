package com.android.server.enterprise.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.secutil.Slog;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.application.ApplicationPolicy;
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
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.lang.reflect.Array;
import java.util.List;

/* loaded from: classes2.dex */
public class SecContentProvider2 extends ContentProvider {
    public static final UriMatcher URI_MATCHER;
    public Context mContext;
    public final boolean DEBUG = false;
    public IKnoxCustomManager mKnoxCustomManagerService = null;

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
        uriMatcher.addURI("com.sec.knox.provider2", "ApplicationPolicy", 1);
        uriMatcher.addURI("com.sec.knox.provider2", "ClientCertificateManager", 2);
        uriMatcher.addURI("com.sec.knox.provider2", "DeviceAccountPolicy", 3);
        uriMatcher.addURI("com.sec.knox.provider2", "EmailPolicy", 6);
        uriMatcher.addURI("com.sec.knox.provider2", "EmailAccountPolicy", 7);
        uriMatcher.addURI("com.sec.knox.provider2", "EnterpriseKnoxManagerPolicy", 8);
        uriMatcher.addURI("com.sec.knox.provider2", "EnterpriseContainerPolicy", 9);
        uriMatcher.addURI("com.sec.knox.provider2", "EnterpriseContainerService", 10);
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
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mContext = getContext();
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:1101:0x1065, code lost:
    
        if (r28.equals("getMaxEmailAgeFilter") == false) goto L917;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1196:0x12c8, code lost:
    
        if (r28.equals("isAdminRemovable") == false) goto L1066;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1277:0x1485, code lost:
    
        if (r28.equals("isEmailNotificationsEnabled") == false) goto L1176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:964:0x0dae, code lost:
    
        if (r28.equals("isPeopleEdgeAllowed") == false) goto L806;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        MatrixCursor matrixCursor;
        List packagesFromDisableClipboardWhiteList;
        List packagesFromDisableClipboardBlackList;
        int i;
        MatrixCursor matrixCursor2;
        boolean isKioskModeEnabled;
        boolean isAirViewModeAllowed;
        boolean isAirCommandModeAllowed;
        char c;
        int callingUid = Binder.getCallingUid();
        UserHandle.getUserId(callingUid);
        StringBuilder sb = new StringBuilder();
        sb.append("query(), uri = ");
        UriMatcher uriMatcher = URI_MATCHER;
        sb.append(uriMatcher.match(uri));
        sb.append(" selection = ");
        sb.append(str);
        Log.d("SecContentProvider2", sb.toString());
        Slog.d("SecContentProvider2", "called from " + getCallerName(callingUid));
        int match = uriMatcher.match(uri);
        char c2 = '\b';
        char c3 = 4;
        char c4 = 3;
        int i2 = 2;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        int i9 = 0;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        int i10 = 0;
        int i11 = 0;
        boolean z7 = false;
        int i12 = 0;
        boolean z8 = false;
        int i13 = 0;
        boolean z9 = false;
        int i14 = 0;
        int i15 = 0;
        boolean z10 = false;
        boolean z11 = false;
        boolean z12 = false;
        boolean z13 = false;
        int i16 = 0;
        String str3 = null;
        Intent intent = null;
        List list = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        List list2 = null;
        List list3 = null;
        List list4 = null;
        List list5 = null;
        List list6 = null;
        if (match == 1) {
            ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
            if (applicationPolicy != null) {
                str.hashCode();
                switch (str.hashCode()) {
                    case -2004117496:
                        if (str.equals("getAppInstallationMode")) {
                            break;
                        }
                        break;
                    case -1979077556:
                        if (str.equals("getPackagesFromDisableClipboardWhiteList")) {
                            break;
                        }
                        break;
                    case -1383025348:
                        if (str.equals("getPackagesFromDisableClipboardWhiteListPerUidInternal")) {
                            break;
                        }
                        break;
                    case -1306972082:
                        if (str.equals("getApplicationComponentState")) {
                            break;
                        }
                        break;
                    case -1131981740:
                        if (str.equals("isApplicationInstalled")) {
                            break;
                        }
                        break;
                    case -1123192942:
                        if (str.equals("getPackagesFromDisableClipboardBlackListPerUidInternal")) {
                            break;
                        }
                        break;
                    case -814619288:
                        if (str.equals("getApplicationNotificationMode")) {
                            break;
                        }
                        break;
                    case -692078081:
                        if (str.equals("getApplicationInstallUninstallList")) {
                            break;
                        }
                        break;
                    case -593555120:
                        if (str.equals("getApplicationUninstallationMode")) {
                            break;
                        }
                        break;
                    case -260970995:
                        if (str.equals("isApplicationClearDataDisabled")) {
                            break;
                        }
                        break;
                    case 319679996:
                        if (str.equals("getApplicationInstallUninstallListAsUser")) {
                            break;
                        }
                        break;
                    case 335627045:
                        if (str.equals("isChangeAssistDefaultAppAllowed")) {
                            break;
                        }
                        break;
                    case 371408591:
                        if (str.equals("getAllPackagesFromBatteryOptimizationWhiteList")) {
                            break;
                        }
                        break;
                    case 440765474:
                        if (str.equals("getDefaultApplicationInternal")) {
                            break;
                        }
                        break;
                    case 506937325:
                        if (str.equals("getApplicationNameFromDb")) {
                            break;
                        }
                        break;
                    case 554124087:
                        if (str.equals("isApplicationClearCacheDisabled")) {
                            break;
                        }
                        break;
                    case 585470634:
                        if (str.equals("getApplicationStateEnabled")) {
                            break;
                        }
                        break;
                    case 595844586:
                        if (str.equals("isApplicationSetToDefault")) {
                            break;
                        }
                        break;
                    case 693304835:
                        if (str.equals("isApplicationForceStopDisabled")) {
                            i = 18;
                            break;
                        }
                        break;
                    case 757815131:
                        if (str.equals("getApplicationIconFromDb")) {
                            i = 19;
                            break;
                        }
                        break;
                    case 769111465:
                        if (str.equals("isChangeSmsDefaultAppAllowed")) {
                            i = 20;
                            break;
                        }
                        break;
                    case 954023525:
                        if (str.equals("isUsbDevicePermittedForPackage")) {
                            i = 21;
                            break;
                        }
                        break;
                    case 1007785250:
                        if (str.equals("getPackagesFromDisableClipboardBlackList")) {
                            i = 22;
                            break;
                        }
                        break;
                    case 1010499842:
                        if (str.equals("isIntentDisabled")) {
                            i = 23;
                            break;
                        }
                        break;
                    case 1028407875:
                        if (str.equals("isPackageUpdateAllowed")) {
                            i = 24;
                            break;
                        }
                        break;
                    case 1031009137:
                        if (str.equals("getApplicationStateDisabledList")) {
                            i = 25;
                            break;
                        }
                        break;
                    case 1238300788:
                        if (str.equals("getApplicationUninstallationEnabled")) {
                            i = 26;
                            break;
                        }
                        break;
                    case 1987712940:
                        if (str.equals("getAppInstallToSdCard")) {
                            i = 27;
                            break;
                        }
                        break;
                }
                /*  JADX ERROR: Method code generation error
                    java.lang.NullPointerException: Switch insn not found in header
                    	at java.base/java.util.Objects.requireNonNull(Objects.java:246)
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
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                    	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                    	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
                    	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
                    	at jadx.core.ProcessClass.process(ProcessClass.java:79)
                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
                    	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:402)
                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:390)
                    	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:340)
                    */
                /*
                    Method dump skipped, instructions count: 8146
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.content.SecContentProvider2.query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String):android.database.Cursor");
            }

            public final Cursor queryMultiUser(String str, String[] strArr, int i) {
                boolean z;
                MultiUserManagerService multiUserManagerService = (MultiUserManagerService) EnterpriseService.getPolicyService("multi_user_manager_service");
                if (multiUserManagerService != null) {
                    str.hashCode();
                    z = false;
                    switch (str) {
                        case "isUserCreationAllowed":
                            if (strArr != null && Array.getLength(strArr) > 0) {
                                z = Boolean.parseBoolean(strArr[0]);
                            }
                            try {
                                return populateCursor(str, multiUserManagerService.isUserCreationAllowed(new ContextInfo(i), z));
                            } catch (Exception unused) {
                                break;
                            }
                        case "isUserRemovalAllowed":
                            if (strArr != null && Array.getLength(strArr) > 0) {
                                z = Boolean.parseBoolean(strArr[0]);
                            }
                            try {
                                return populateCursor(str, multiUserManagerService.isUserRemovalAllowed(new ContextInfo(i), z));
                            } catch (RemoteException unused2) {
                                return null;
                            }
                        case "multipleUsersAllowed":
                            if (strArr != null && Array.getLength(strArr) > 0) {
                                z = Boolean.parseBoolean(strArr[0]);
                            }
                            try {
                                int multipleUsersAllowed = multiUserManagerService.multipleUsersAllowed(new ContextInfo(i), z);
                                MatrixCursor matrixCursor = new MatrixCursor(new String[]{str});
                                matrixCursor.addRow(new Integer[]{Integer.valueOf(multipleUsersAllowed)});
                                return matrixCursor;
                            } catch (RemoteException unused3) {
                                return null;
                            }
                        case "multipleUsersSupported":
                            try {
                                return populateCursor(str, multiUserManagerService.multipleUsersSupported(new ContextInfo(i)));
                            } catch (RemoteException unused4) {
                                return null;
                            }
                        default:
                            return null;
                    }
                }
                return null;
            }

            public final Cursor queryEmailAccount(String str, String[] strArr, int i) {
                MatrixCursor matrixCursor;
                EmailAccountPolicy emailAccountPolicy = (EmailAccountPolicy) EnterpriseService.getPolicyService("email_account_policy");
                if (emailAccountPolicy != null && str != null) {
                    switch (str) {
                        case "setSecurityOutGoingServerPassword":
                            if (strArr != null && Array.getLength(strArr) >= 1) {
                                long securityOutGoingServerPassword = emailAccountPolicy.setSecurityOutGoingServerPassword(new ContextInfo(i), strArr[0]);
                                matrixCursor = new MatrixCursor(new String[]{str});
                                matrixCursor.addRow(new String[]{String.valueOf(securityOutGoingServerPassword)});
                                return matrixCursor;
                            }
                            break;
                        case "setSecurityInComingServerPassword":
                            if (strArr == null || Array.getLength(strArr) < 1) {
                                return null;
                            }
                            long securityInComingServerPassword = emailAccountPolicy.setSecurityInComingServerPassword(new ContextInfo(i), strArr[0]);
                            matrixCursor = new MatrixCursor(new String[]{str});
                            matrixCursor.addRow(new String[]{String.valueOf(securityInComingServerPassword)});
                            return matrixCursor;
                        case "getSecurityOutgoingServerPassword":
                            if (strArr == null || Array.getLength(strArr) <= 0) {
                                return null;
                            }
                            String securityOutGoingServerPassword2 = emailAccountPolicy.getSecurityOutGoingServerPassword(new ContextInfo(i), Long.parseLong(strArr[0]));
                            MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{str});
                            matrixCursor2.addRow(new String[]{securityOutGoingServerPassword2});
                            return matrixCursor2;
                        case "getSecurityIncomingServerPassword":
                            if (strArr == null || Array.getLength(strArr) <= 0) {
                                return null;
                            }
                            String securityInComingServerPassword2 = emailAccountPolicy.getSecurityInComingServerPassword(new ContextInfo(i), Long.parseLong(strArr[0]));
                            MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{str});
                            matrixCursor3.addRow(new String[]{securityInComingServerPassword2});
                            return matrixCursor3;
                        default:
                            return null;
                    }
                }
                return null;
            }

            public final Cursor queryMisc(String str, int i) {
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
                MatrixCursor matrixCursor = new MatrixCursor(new String[]{str});
                matrixCursor.addRow(new String[]{currentLockScreenString});
                return matrixCursor;
            }

            public final Cursor queryDeviceAccount(String str, String[] strArr) {
                DeviceAccountPolicy deviceAccountPolicy = (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
                if (deviceAccountPolicy != null && str != null) {
                    switch (str) {
                        case "isAccountRemovalAllowed":
                            if (strArr != null && Array.getLength(strArr) >= 3) {
                                return populateCursor(str, deviceAccountPolicy.isAccountRemovalAllowed(strArr[0], strArr[1], Boolean.parseBoolean(strArr[2])));
                            }
                            break;
                        case "isAccountRemovalAllowedAsUser":
                            if (strArr == null || Array.getLength(strArr) < 4) {
                                return null;
                            }
                            return populateCursor(str, deviceAccountPolicy.isAccountRemovalAllowedAsUser(strArr[0], strArr[1], Boolean.parseBoolean(strArr[2]), Integer.parseInt(strArr[3])));
                        case "isAccountAdditionAllowed":
                            if (strArr == null || Array.getLength(strArr) < 3) {
                                return null;
                            }
                            return populateCursor(str, deviceAccountPolicy.isAccountAdditionAllowed(strArr[0], strArr[1], Boolean.parseBoolean(strArr[2])));
                        default:
                            return null;
                    }
                }
                return null;
            }

            public final Cursor queryVPN(String str, String[] strArr, int i) {
                boolean z;
                VpnInfoPolicy vpnInfoPolicy = (VpnInfoPolicy) EnterpriseService.getPolicyService("vpn_policy");
                if (vpnInfoPolicy != null) {
                    str.hashCode();
                    z = false;
                    switch (str) {
                        case "checkRacoonSecurity":
                            if (strArr != null && Array.getLength(strArr) > 0) {
                                return populateCursor(str, vpnInfoPolicy.checkRacoonSecurity(new ContextInfo(i), strArr));
                            }
                            break;
                        case "isUserAddProfilesAllowed":
                            if (strArr != null && Array.getLength(strArr) > 0) {
                                z = Boolean.parseBoolean(strArr[0]);
                            }
                            return populateCursor(str, vpnInfoPolicy.isUserAddProfilesAllowed(new ContextInfo(i), z));
                        case "isUserSetAlwaysOnAllowed":
                            if (strArr != null && Array.getLength(strArr) > 0) {
                                z = Boolean.parseBoolean(strArr[0]);
                            }
                            return populateCursor(str, vpnInfoPolicy.isUserSetAlwaysOnAllowed(new ContextInfo(i), z));
                        case "isUserChangeProfilesAllowed":
                            if (strArr != null && Array.getLength(strArr) > 0) {
                                z = Boolean.parseBoolean(strArr[0]);
                            }
                            return populateCursor(str, vpnInfoPolicy.isUserChangeProfilesAllowed(new ContextInfo(i), z));
                        default:
                            return null;
                    }
                }
                return null;
            }

            public final Cursor queryEnterpriseLicense(String str, String[] strArr) {
                EnterpriseLicenseService enterpriseLicenseService = (EnterpriseLicenseService) EnterpriseService.getPolicyService("enterprise_license_policy");
                if (enterpriseLicenseService != null) {
                    str.hashCode();
                    if (str.equals("isServiceAvailable")) {
                        if (strArr != null && Array.getLength(strArr) > 1) {
                            return populateCursor(str, enterpriseLicenseService.isServiceAvailable(strArr[0], strArr[1]));
                        }
                    } else {
                        Log.d("SecContentProvider2", "ENTERPRISELICENSEPOLICY : return null");
                        return null;
                    }
                }
                return null;
            }

            public final Cursor queryPhoneRestriction(String str, String[] strArr, int i) {
                boolean z;
                boolean z2;
                PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
                if (phoneRestrictionPolicy == null) {
                    return null;
                }
                str.hashCode();
                z = true;
                z2 = false;
                switch (str) {
                    case "isBlockMmsWithStorageEnabled":
                        return populateCursor(str, phoneRestrictionPolicy.isBlockMmsWithStorageEnabled(new ContextInfo(i)));
                    case "isWapPushAllowed":
                        return populateCursor(str, phoneRestrictionPolicy.isWapPushAllowed(new ContextInfo(i)));
                    case "isIncomingSmsAllowed":
                        return populateCursor(str, phoneRestrictionPolicy.isIncomingSmsAllowed(new ContextInfo(i)));
                    case "isOutgoingMmsAllowed":
                        return populateCursor(str, phoneRestrictionPolicy.isOutgoingMmsAllowed(new ContextInfo(i)));
                    case "getEmergencyCallOnly":
                        return populateCursor(str, phoneRestrictionPolicy.getEmergencyCallOnly(new ContextInfo(i), true));
                    case "isCallerIDDisplayAllowed":
                        return populateCursor(str, phoneRestrictionPolicy.isCallerIDDisplayAllowed(new ContextInfo(i)));
                    case "isRCSEnabled":
                        int length = Array.getLength(strArr);
                        if (strArr == null || length < 3) {
                            return null;
                        }
                        int parseInt = Integer.parseInt(strArr[0]);
                        boolean parseBoolean = Boolean.parseBoolean(strArr[1]);
                        int parseInt2 = Integer.parseInt(strArr[2]);
                        boolean isRCSEnabled = phoneRestrictionPolicy.isRCSEnabled(new ContextInfo(i), parseInt, parseBoolean);
                        if (isRCSEnabled) {
                            isRCSEnabled = phoneRestrictionPolicy.isRCSEnabledBySimSlot(new ContextInfo(i), parseInt, parseBoolean, parseInt2);
                        }
                        return populateCursor(str, isRCSEnabled);
                    case "isBlockSmsWithStorageEnabled":
                        return populateCursor(str, phoneRestrictionPolicy.isBlockSmsWithStorageEnabled(new ContextInfo(i)));
                    case "isIncomingMmsAllowed":
                        return populateCursor(str, phoneRestrictionPolicy.isIncomingMmsAllowed(new ContextInfo(i)));
                    case "isCopyContactToSimAllowed":
                        boolean isCopyContactToSimAllowed = phoneRestrictionPolicy.isCopyContactToSimAllowed(new ContextInfo(i));
                        Log.d("SecContentProvider2", "isCopyContactToSimAllowed = " + isCopyContactToSimAllowed);
                        return populateCursor(str, isCopyContactToSimAllowed);
                    case "canIncomingSms":
                        if (strArr == null || Array.getLength(strArr) <= 0) {
                            return null;
                        }
                        return populateCursor(str, phoneRestrictionPolicy.canIncomingSms(strArr[0]));
                    case "canOutgoingCall":
                        if (strArr == null || Array.getLength(strArr) <= 0) {
                            return null;
                        }
                        return populateCursor(str, phoneRestrictionPolicy.canOutgoingCall(strArr[0]));
                    case "getDisclaimerText":
                        String disclaimerText = phoneRestrictionPolicy.getDisclaimerText(new ContextInfo(i));
                        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"getDisclaimerText"});
                        matrixCursor.addRow(new String[]{disclaimerText});
                        return matrixCursor;
                    case "isMmsAllowedFromSimSlot1":
                        try {
                            z = phoneRestrictionPolicy.isMmsAllowedFromSimSlot(0);
                            Log.d("SecContentProvider2", "isMmsAllowedFromSimSlot(0) result " + z);
                        } catch (SecurityException e) {
                            Log.w("SecContentProvider2", "SecurityException: " + e);
                        }
                        return populateCursor(str, z);
                    case "isMmsAllowedFromSimSlot2":
                        try {
                            z = phoneRestrictionPolicy.isMmsAllowedFromSimSlot(1);
                            Log.d("SecContentProvider2", "isMmsAllowedFromSimSlot(1) result " + z);
                        } catch (SecurityException e2) {
                            Log.w("SecContentProvider2", "SecurityException: " + e2);
                        }
                        return populateCursor(str, z);
                    case "isDataAllowedFromSimSlot1":
                        try {
                            z = phoneRestrictionPolicy.isDataAllowedFromSimSlot(0);
                            Log.d("SecContentProvider2", "isDataAllowedFromSimSlot(0) result " + z);
                        } catch (SecurityException e3) {
                            Log.w("SecContentProvider2", "SecurityException: " + e3);
                        }
                        return populateCursor(str, z);
                    case "isDataAllowedFromSimSlot2":
                        try {
                            z = phoneRestrictionPolicy.isDataAllowedFromSimSlot(1);
                            Log.d("SecContentProvider2", "isDataAllowedFromSimSlot(1) result " + z);
                        } catch (SecurityException e4) {
                            Log.w("SecContentProvider2", "SecurityException: " + e4);
                        }
                        return populateCursor(str, z);
                    case "canOutgoingSms":
                        if (strArr == null || Array.getLength(strArr) <= 0) {
                            return null;
                        }
                        return populateCursor(str, phoneRestrictionPolicy.canOutgoingSms(strArr[0]));
                    case "isSimLockedByAdmin":
                        if (strArr == null || Array.getLength(strArr) <= 0) {
                            return null;
                        }
                        return populateCursor(str, phoneRestrictionPolicy.isSimLockedByAdmin(strArr[0]));
                    case "isLimitNumberOfSmsEnabled":
                        return populateCursor(str, phoneRestrictionPolicy.isLimitNumberOfSmsEnabled(new ContextInfo(i)));
                    case "canIncomingCall":
                        if (strArr == null || Array.getLength(strArr) <= 0) {
                            return null;
                        }
                        return populateCursor(str, phoneRestrictionPolicy.canIncomingCall(strArr[0]));
                    case "isOutgoingSmsAllowed":
                        return populateCursor(str, phoneRestrictionPolicy.isOutgoingSmsAllowed(new ContextInfo(i)));
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

            public final MatrixCursor populateCursor(String str, boolean z) {
                MatrixCursor matrixCursor = new MatrixCursor(new String[]{str});
                matrixCursor.addRow(new Boolean[]{Boolean.valueOf(z)});
                return matrixCursor;
            }

            @Override // android.content.ContentProvider
            public Uri insert(Uri uri, ContentValues contentValues) {
                String asString;
                int callingUid = Binder.getCallingUid();
                int match = URI_MATCHER.match(uri);
                if (match == 12) {
                    ExchangeAccountPolicy exchangeAccountPolicy = (ExchangeAccountPolicy) EnterpriseService.getPolicyService("eas_account_policy");
                    if (exchangeAccountPolicy != null && (asString = contentValues.getAsString("API")) != null && asString.equals("setAccountEmailPassword")) {
                        exchangeAccountPolicy.setAccountEmailPassword(new ContextInfo(callingUid), contentValues.getAsString("password"));
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

            public final String getCallerName(int i) {
                String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
                return nameForUid == null ? "fail to get caller name" : nameForUid;
            }

            public final IKnoxCustomManager getKnoxCustomManagerService() {
                if (this.mKnoxCustomManagerService == null) {
                    this.mKnoxCustomManagerService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE));
                }
                return this.mKnoxCustomManagerService;
            }
        }
