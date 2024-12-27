package com.android.server.enterprise.restriction;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.wifi.SoftApConfiguration;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.HexDump;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.restriction.IPhoneRestrictionPolicy;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.wifi.SemWifiManager;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class PhoneRestrictionPolicy extends IPhoneRestrictionPolicy.Stub
        implements EnterpriseServiceCallback {
    public final AnonymousClass1 mBroadcastReceiver;
    public final Context mContext;
    public boolean mDataCheckboxPreviousState;
    public boolean mDataLimitEnabled;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseSimPin mEnterpriseSimPin;
    public KeyguardManager mKeyguardManager;
    public final AnonymousClass1 mRcsReceiver;
    public final AnonymousClass1 mReceiver;
    public final SimDBProxy mSimDbProxy;
    public final AnonymousClass1 mSimLockBroadcast;
    public SubscriptionManager mSubMgr;
    public final TelephonyManager mTelMgr;
    public final AnonymousClass1 subChangedReceiver;
    public static final Uri RCS_URI = Uri.parse("content://com.samsung.rcs.im/message");
    public static final String[] RCS_PROJ = {
        "is_filetransfer",
        "remote_uri",
        "sender_alias",
        "content_type",
        "body",
        "inserted_timestamp",
        "sent_timestamp",
        "direction",
        "file_path",
        "thumbnail_path"
    };
    public EnterpriseDeviceManager mEDM = null;
    public final SmsMmsDeliveryHandler mSmsMmsDeliveryHandler = new SmsMmsDeliveryHandler();
    public volatile boolean mIsPhoneShuttingDown = false;

    /* renamed from: com.android.server.enterprise.restriction.PhoneRestrictionPolicy$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        /* JADX WARN: Code restructure failed: missing block: B:44:0x00d0, code lost:

           r0 = true;
        */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r10, android.content.Intent r11) {
            /*
                Method dump skipped, instructions count: 656
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.enterprise.restriction.PhoneRestrictionPolicy.AnonymousClass1.onReceive(android.content.Context,"
                        + " android.content.Intent):void");
        }
    }

    class Injector {}

    public final class SmsMmsDeliveryHandler extends Handler {
        public SmsMmsDeliveryHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Log.w("PhoneRestrictionPolicy", "Message received - msg " + message);
            final int i = message.what;
            new Thread(
                            new Runnable() { // from class:
                                             // com.android.server.enterprise.restriction.PhoneRestrictionPolicy.SmsMmsDeliveryHandler.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i2 = i;
                                    if (i2 == 0) {
                                        PhoneRestrictionPolicy.m520$$Nest$mdeliveryBlockedMsgs(
                                                PhoneRestrictionPolicy.this, 0);
                                        return;
                                    }
                                    if (i2 == 1) {
                                        PhoneRestrictionPolicy.m520$$Nest$mdeliveryBlockedMsgs(
                                                PhoneRestrictionPolicy.this, 1);
                                    } else {
                                        if (i2 != 2) {
                                            return;
                                        }
                                        PhoneRestrictionPolicy.m520$$Nest$mdeliveryBlockedMsgs(
                                                PhoneRestrictionPolicy.this, 0);
                                        PhoneRestrictionPolicy.m520$$Nest$mdeliveryBlockedMsgs(
                                                PhoneRestrictionPolicy.this, 1);
                                    }
                                }
                            })
                    .start();
        }
    }

    /* renamed from: -$$Nest$mdeliveryBlockedMsgs, reason: not valid java name */
    public static void m520$$Nest$mdeliveryBlockedMsgs(
            PhoneRestrictionPolicy phoneRestrictionPolicy, int i) {
        synchronized (phoneRestrictionPolicy) {
            try {
                Log.w("PhoneRestrictionPolicy", " >>>> deliveryBlockedMsgs");
                String[] strArr = {
                    "smsMmsBlockedRowId",
                    "smsMmsSendType",
                    "smsMmsPdu",
                    "smsMmsTimeStamp",
                    "smsMmsOrigAddress",
                    "smsMmsMessageId",
                    "smsMmsSubId"
                };
                String str = i == 0 ? "1" : "0";
                ContentValues contentValues = new ContentValues();
                contentValues.put("smsMmsType", str);
                List valuesList =
                        phoneRestrictionPolicy.mEdmStorageProvider.getValuesList(
                                "SMSMMSBlockedDelivery", strArr, contentValues);
                StringBuilder sb = new StringBuilder("cvList size ");
                ArrayList arrayList = (ArrayList) valuesList;
                sb.append(arrayList.size());
                Log.w("PhoneRestrictionPolicy", sb.toString());
                if (arrayList.isEmpty()) {
                    return;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues2 = (ContentValues) it.next();
                    if (phoneRestrictionPolicy.mIsPhoneShuttingDown) {
                        Log.w("PhoneRestrictionPolicy", "Phone is shutting down ...quitting");
                        return;
                    }
                    Intent intent = new Intent();
                    if (i == 0) {
                        if (phoneRestrictionPolicy.isBlockSmsWithStorageEnabled(null)) {
                            Log.w(
                                    "PhoneRestrictionPolicy",
                                    "Block SMS with storage applies... quitting");
                            return;
                        }
                        String asString = contentValues2.getAsString("smsMmsOrigAddress");
                        if (!phoneRestrictionPolicy.getEmergencyCallOnly(null, true)
                                && phoneRestrictionPolicy.getSmsMmsAllowed("allowIncomingSms")
                                && phoneRestrictionPolicy.canIncomingSms(asString)) {
                            intent.setAction(
                                    "com.samsung.android.knox.intent.action.SEND_BLOCKED_SMS_INTERNAL");
                        }
                        Log.w(
                                "PhoneRestrictionPolicy",
                                "Other incoming SMS policies apply... delete message and continue");
                        phoneRestrictionPolicy.mEdmStorageProvider.deleteDataByFields(
                                "SMSMMSBlockedDelivery",
                                new String[] {"smsMmsBlockedRowId"},
                                new String[] {contentValues2.getAsString("smsMmsBlockedRowId")});
                    } else {
                        if (phoneRestrictionPolicy.isBlockMmsWithStorageEnabled(null)) {
                            Log.w(
                                    "PhoneRestrictionPolicy",
                                    "Block MMS with storage applies... quitting");
                            return;
                        }
                        if (!phoneRestrictionPolicy.getEmergencyCallOnly(null, true)
                                && phoneRestrictionPolicy.getSmsMmsAllowed("allowIncomingMms")
                                && ((!phoneRestrictionPolicy.mTelMgr.isNetworkRoaming()
                                                || phoneRestrictionPolicy
                                                        .mEDM
                                                        .getRoamingPolicy()
                                                        .isRoamingPushEnabled())
                                        && phoneRestrictionPolicy
                                                .mEDM
                                                .getPhoneRestrictionPolicy()
                                                .isWapPushAllowed())) {
                            intent.putExtra(
                                    "com.samsung.android.knox.intent.extra.TIME_STAMP_INTERNAL",
                                    contentValues2.getAsString("smsMmsTimeStamp"));
                            intent.putExtra(
                                    "com.samsung.android.knox.intent.extra.ORIG_ADDRESS_INTERNAL",
                                    contentValues2.getAsString("smsMmsOrigAddress"));
                            intent.putExtra(
                                    "com.samsung.android.knox.intent.extra.MESSAGE_ID_INTERNAL",
                                    contentValues2.getAsString("smsMmsMessageId"));
                            intent.putExtra(
                                    "com.samsung.android.knox.intent.extra.SUB_ID_INTERNAL",
                                    contentValues2.getAsString("smsMmsSubId"));
                            intent.setAction(
                                    "com.samsung.android.knox.intent.action.SEND_BLOCKED_MMS_INTERNAL");
                        }
                        Log.w(
                                "PhoneRestrictionPolicy",
                                "Other incoming MMS policies apply... delete message and continue");
                        phoneRestrictionPolicy.mEdmStorageProvider.deleteDataByFields(
                                "SMSMMSBlockedDelivery",
                                new String[] {"smsMmsBlockedRowId"},
                                new String[] {contentValues2.getAsString("smsMmsBlockedRowId")});
                    }
                    phoneRestrictionPolicy.mEdmStorageProvider.deleteDataByFields(
                            "SMSMMSBlockedDelivery",
                            new String[] {"smsMmsBlockedRowId"},
                            new String[] {contentValues2.getAsString("smsMmsBlockedRowId")});
                    String asString2 = contentValues2.getAsString("smsMmsPdu");
                    if (asString2 != null) {
                        byte[] hexStringToByteArray = HexDump.hexStringToByteArray(asString2);
                        Integer asInteger = contentValues2.getAsInteger("smsMmsSendType");
                        int intValue = asInteger != null ? asInteger.intValue() : -1;
                        intent.putExtra(
                                "com.samsung.android.knox.intent.extra.PDU_INTERNAL",
                                hexStringToByteArray);
                        intent.putExtra(
                                "com.samsung.android.knox.intent.extra.SEND_TYPE_INTERNAL",
                                intValue);
                        Object obj = new Object();
                        phoneRestrictionPolicy.mContext.sendOrderedBroadcast(
                                intent,
                                "com.samsung.android.knox.permission.KNOX_RECEIVE_BLOCKED_SMS_MMS_INTERNAL",
                                new AnonymousClass1(5, obj),
                                null,
                                -1,
                                null,
                                null);
                        try {
                            synchronized (obj) {
                                obj.wait(500L);
                            }
                        } catch (InterruptedException e) {
                            Log.e("PhoneRestrictionPolicy", "", e);
                        }
                    } else {
                        continue;
                    }
                }
                Log.w("PhoneRestrictionPolicy", " deliveryBlockedMsgs >>>> ");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public PhoneRestrictionPolicy(Context context) {
        this.mDataLimitEnabled = false;
        this.mDataCheckboxPreviousState = false;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(0, this);
        AnonymousClass1 anonymousClass12 = new AnonymousClass1(1, this);
        AnonymousClass1 anonymousClass13 = new AnonymousClass1(2, this);
        AnonymousClass1 anonymousClass14 = new AnonymousClass1(3, this);
        AnonymousClass1 anonymousClass15 = new AnonymousClass1(4, this);
        Log.w("PhoneRestrictionPolicy", " >>> PhoneRestrictionPolicy.PhoneRestrictionPolicy()");
        Objects.requireNonNull(context);
        this.mContext = context;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        this.mDataLimitEnabled = getDataCallLimitEnabled(null);
        this.mDataCheckboxPreviousState =
                Boolean.parseBoolean(
                        edmStorageProvider.getGenericValueAsUser(0, "dataCallPacketDataCheckBox"));
        this.mTelMgr = (TelephonyManager) context.getSystemService("phone");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.REBOOT");
        intentFilter.addAction("com.samsung.android.knox.intent.action.PHONE_READY_INTERNAL");
        context.registerReceiver(anonymousClass14, intentFilter, 2);
        if (SimDBProxy.mSimDBProxy == null) {
            SimDBProxy.mSimDBProxy = new SimDBProxy(context);
        }
        this.mSimDbProxy = SimDBProxy.mSimDBProxy;
        this.mSubMgr =
                (SubscriptionManager) context.getSystemService("telephony_subscription_service");
        context.registerReceiver(
                anonymousClass15,
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.knox.intent.action.PHONE_READY_INTERNAL",
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        Constants.SIM_STATE_CHANGED),
                2);
        context.registerReceiver(
                anonymousClass1,
                new IntentFilter(
                        "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"),
                2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addCategory("com.samsung.rcs.framework.instantmessaging.category.ACTION");
        intentFilter2.addAction(
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_NEW_MESSAGE");
        intentFilter2.addAction(
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_SEND_MESSAGE_RESPONSE");
        intentFilter2.addCategory("com.samsung.rcs.framework.filetransfer.category.NOTIFICATION");
        intentFilter2.addAction(
                "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_INCOMING");
        intentFilter2.addAction(
                "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_COMPLETED");
        context.registerReceiver(
                anonymousClass12, intentFilter2, "com.samsung.rcs.im.READ_PERMISSION", null, 2);
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        EnterpriseSimPin enterpriseSimPin = new EnterpriseSimPin();
        enterpriseSimPin.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        enterpriseSimPin.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService("telephony_subscription_service");
        this.mEnterpriseSimPin = enterpriseSimPin;
        context.registerReceiver(anonymousClass13, m, 2);
    }

    public static void enforcePhoneApp() {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1001) {
            throw new SecurityException("Can only be called by internal phone");
        }
    }

    public static void enforceSms() {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1001) {
            throw new SecurityException("Can only receive SMS by internal phone");
        }
    }

    public static void enforceSystemUser$2() {
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Can only be called by system user");
        }
    }

    public static void logError(int i) {
        if (i == 2) {
            Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard invalid pincode");
        }
        if (i == 100) {
            Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin unknown");
            return;
        }
        if (i == 4) {
            Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin already locked");
            return;
        }
        if (i == 5) {
            Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin already unlocked");
            return;
        }
        if (i == 6) {
            Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin blocked by puk");
            return;
        }
        switch (i) {
            case 8:
                Log.e(
                        "PhoneRestrictionPolicy",
                        "lockUnlockCorporateSimCard Sim pin exceeded max retries");
                break;
            case 9:
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim not ready");
                break;
            case 10:
                Log.e(
                        "PhoneRestrictionPolicy",
                        "lockUnlockCorporateSimCard Sim pin database error");
                break;
            case 11:
                Log.e(
                        "PhoneRestrictionPolicy",
                        "lockUnlockCorporateSimCard Sim pin already locked by admin");
                break;
            case 12:
                Log.e(
                        "PhoneRestrictionPolicy",
                        "lockUnlockCorporateSimCard Sim pin owned by another admin");
                break;
            case 13:
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim not inserted");
                break;
        }
    }

    public static String removeWhiteSpaces(String str) {
        return str != null ? str.replaceAll("\\s+", "") : str;
    }

    public static boolean validatePinCode(String str) {
        if (str == null || str.length() < 4 || str.length() > 8) {
            return false;
        }
        try {
            return Integer.parseInt(str) >= 0;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public final boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.addIncomingCallExceptionPattern()");
        if (str == null) {
            return false;
        }
        String incomingCallExceptionPatterns = getIncomingCallExceptionPatterns(contextInfo);
        if (incomingCallExceptionPatterns == null || incomingCallExceptionPatterns.isEmpty()) {
            return setIncomingCallExceptionPattern(contextInfo, str);
        }
        return setIncomingCallExceptionPattern(
                contextInfo,
                incomingCallExceptionPatterns.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean addIncomingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addIncomingCallRestriction()");
        if (str == null) {
            return false;
        }
        String incomingCallRestriction = getIncomingCallRestriction(contextInfo, false);
        if (incomingCallRestriction == null || incomingCallRestriction.isEmpty()) {
            return setIncomingCallRestriction(contextInfo, str);
        }
        return setIncomingCallRestriction(
                contextInfo,
                incomingCallRestriction.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.addIncomingSmsExceptionPattern()");
        if (str == null) {
            return false;
        }
        String incomingSmsExceptionPatterns = getIncomingSmsExceptionPatterns(contextInfo);
        if (incomingSmsExceptionPatterns == null || incomingSmsExceptionPatterns.isEmpty()) {
            return setIncomingSmsExceptionPattern(contextInfo, str);
        }
        return setIncomingSmsExceptionPattern(
                contextInfo,
                incomingSmsExceptionPatterns.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        String restrictionPattern =
                getRestrictionPattern(contextInfo, "smsRestrictionIncomingPattern", false);
        if (restrictionPattern == null || restrictionPattern.isEmpty()) {
            return setIncomingSmsRestriction(contextInfo, str);
        }
        return setIncomingSmsRestriction(
                contextInfo, restrictionPattern.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean addNumberOfIncomingCalls() {
        int i;
        int i2;
        int i3;
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addNumberOfIncomingCalls()");
        enforcePhoneApp();
        if (!this.mTelMgr.isVoiceCapable() || !isLimitNumberOfCallsEnabled(null)) {
            return false;
        }
        String genericValueAsUser =
                this.mEdmStorageProvider.getGenericValueAsUser(0, "incomingCallCountDay");
        String genericValueAsUser2 =
                this.mEdmStorageProvider.getGenericValueAsUser(0, "incomingCallCountWeek");
        String genericValueAsUser3 =
                this.mEdmStorageProvider.getGenericValueAsUser(0, "incomingCallCountMonth");
        if (genericValueAsUser == null
                || genericValueAsUser2 == null
                || genericValueAsUser3 == null) {
            i = 0;
            i2 = 0;
            i3 = 0;
        } else {
            try {
                i = Integer.parseInt(genericValueAsUser);
                try {
                    i2 = Integer.parseInt(genericValueAsUser2);
                } catch (NumberFormatException unused) {
                    i2 = 0;
                }
            } catch (NumberFormatException unused2) {
                i = 0;
                i2 = 0;
            }
            try {
                i3 = Integer.parseInt(genericValueAsUser3);
            } catch (NumberFormatException unused3) {
                Log.e("PhoneRestrictionPolicy", "addNumberOfIncomingCalls - exception");
                i3 = 0;
                boolean putGenericValueAsUser =
                        this.mEdmStorageProvider.putGenericValueAsUser(
                                        0, "incomingCallCountMonth", String.valueOf(i3 + 1))
                                & this.mEdmStorageProvider.putGenericValueAsUser(
                                        0, "incomingCallCountDay", String.valueOf(i + 1))
                                & this.mEdmStorageProvider.putGenericValueAsUser(
                                        0, "incomingCallCountWeek", String.valueOf(i2 + 1));
                Log.d(
                        "PhoneRestrictionPolicy",
                        "PhoneRestrictionPolicy.addNumberOfIncomingCalls() >>>");
                return putGenericValueAsUser;
            }
        }
        boolean putGenericValueAsUser2 =
                this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "incomingCallCountMonth", String.valueOf(i3 + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "incomingCallCountDay", String.valueOf(i + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "incomingCallCountWeek", String.valueOf(i2 + 1));
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.addNumberOfIncomingCalls() >>>");
        return putGenericValueAsUser2;
    }

    public final boolean addNumberOfIncomingSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SMSRestrictionPolicy.addNumberOfIncomingSMS()");
        enforceSms();
        if (!isLimitNumberOfSmsEnabled(null)) {
            return false;
        }
        boolean putGenericValueAsUser =
                this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "incomingSmsCountMonth",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "incomingSmsCountMonth"))
                                                + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "incomingSmsCountDay",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "incomingSmsCountDay"))
                                                + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "incomingSmsCountWeek",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "incomingSmsCountWeek"))
                                                + 1));
        Log.d("PhoneRestrictionPolicy", "SMSRestrictionPolicy.addNumberOfIncomingSMS() >>>");
        return putGenericValueAsUser;
    }

    public final boolean addNumberOfOutgoingCalls() {
        int i;
        int i2;
        int i3;
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addNumberOfOutgoingCalls()");
        enforcePhoneApp();
        if (!this.mTelMgr.isVoiceCapable() || !isLimitNumberOfCallsEnabled(null)) {
            return false;
        }
        String genericValueAsUser =
                this.mEdmStorageProvider.getGenericValueAsUser(0, "outgoingCallCountDay");
        String genericValueAsUser2 =
                this.mEdmStorageProvider.getGenericValueAsUser(0, "outgoingCallCountWeek");
        String genericValueAsUser3 =
                this.mEdmStorageProvider.getGenericValueAsUser(0, "outgoingCallCountMonth");
        if (genericValueAsUser == null
                || genericValueAsUser2 == null
                || genericValueAsUser3 == null) {
            i = 0;
            i2 = 0;
            i3 = 0;
        } else {
            try {
                i = Integer.parseInt(genericValueAsUser);
                try {
                    i2 = Integer.parseInt(genericValueAsUser2);
                } catch (NumberFormatException unused) {
                    i2 = 0;
                }
            } catch (NumberFormatException unused2) {
                i = 0;
                i2 = 0;
            }
            try {
                i3 = Integer.parseInt(genericValueAsUser3);
            } catch (NumberFormatException unused3) {
                Log.e("PhoneRestrictionPolicy", "addNumberOfOutgoingCalls - exception");
                i3 = 0;
                boolean putGenericValueAsUser =
                        this.mEdmStorageProvider.putGenericValueAsUser(
                                        0, "outgoingCallCountMonth", String.valueOf(i3 + 1))
                                & this.mEdmStorageProvider.putGenericValueAsUser(
                                        0, "outgoingCallCountDay", String.valueOf(i + 1))
                                & this.mEdmStorageProvider.putGenericValueAsUser(
                                        0, "outgoingCallCountWeek", String.valueOf(i2 + 1));
                Log.d(
                        "PhoneRestrictionPolicy",
                        "PhoneRestrictionPolicy.addNumberOfOutgoingCalls >>>");
                return putGenericValueAsUser;
            }
        }
        boolean putGenericValueAsUser2 =
                this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "outgoingCallCountMonth", String.valueOf(i3 + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "outgoingCallCountDay", String.valueOf(i + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "outgoingCallCountWeek", String.valueOf(i2 + 1));
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.addNumberOfOutgoingCalls >>>");
        return putGenericValueAsUser2;
    }

    public final boolean addNumberOfOutgoingSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SmsRestrictionPolicy.addNumberOfOutgoingSms()");
        enforceSms();
        if (!isLimitNumberOfSmsEnabled(null)) {
            return false;
        }
        boolean putGenericValueAsUser =
                this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "outgoingSmsCountMonth",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountMonth"))
                                                + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "outgoingSmsCountDay",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountDay"))
                                                + 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "outgoingSmsCountWeek",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountWeek"))
                                                + 1));
        Log.d("PhoneRestrictionPolicy", "SmsRestrictionPolicy.addNumberOfOutgoingSms >>>");
        return putGenericValueAsUser;
    }

    public final boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.addOutgoingCallExceptionPattern()");
        if (str == null) {
            return false;
        }
        String outgoingCallExceptionPatterns = getOutgoingCallExceptionPatterns(contextInfo);
        if (outgoingCallExceptionPatterns == null || outgoingCallExceptionPatterns.isEmpty()) {
            return setOutgoingCallExceptionPattern(contextInfo, str);
        }
        return setOutgoingCallExceptionPattern(
                contextInfo,
                outgoingCallExceptionPatterns.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addOutgoingCallRestriction()");
        if (str == null) {
            return false;
        }
        String outgoingCallRestriction = getOutgoingCallRestriction(contextInfo, false);
        if (outgoingCallRestriction == null || outgoingCallRestriction.isEmpty()) {
            return setOutgoingCallRestriction(contextInfo, str);
        }
        return setOutgoingCallRestriction(
                contextInfo,
                outgoingCallRestriction.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.addOutgoingSmsExceptionPattern()");
        if (str == null) {
            return false;
        }
        String outgoingSmsExceptionPatterns = getOutgoingSmsExceptionPatterns(contextInfo);
        if (outgoingSmsExceptionPatterns == null || outgoingSmsExceptionPatterns.isEmpty()) {
            return setOutgoingSmsExceptionPattern(contextInfo, str);
        }
        return setOutgoingSmsExceptionPattern(
                contextInfo,
                outgoingSmsExceptionPatterns.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        String restrictionPattern =
                getRestrictionPattern(contextInfo, "smsRestrictionOutgoingPattern", false);
        if (restrictionPattern == null || restrictionPattern.isEmpty()) {
            return setOutgoingSmsRestriction(contextInfo, str);
        }
        return setOutgoingSmsRestriction(
                contextInfo, restrictionPattern.concat("|").concat(str.replaceAll("\\s+", "")));
    }

    public final boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean(
                    "PHONERESTRICTION",
                    enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid,
                    z,
                    0,
                    "allowCallerID");
        } catch (Exception e) {
            Log.w("PhoneRestrictionPolicy", e.toString());
            return false;
        }
    }

    public final boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z) {
        int i = enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "PHONERESTRICTION", i, z, 0, "copyContactToSimEnabled");
        Uri parse = Uri.parse("content://com.sec.knox.provider2/PhoneRestrictionPolicy");
        if (parse != null) {
            this.mContext.getContentResolver().insert(parse, null);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public final int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        int dynamicSimControl =
                setDynamicSimControl(
                        enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, z, 1);
        if (dynamicSimControl == 0) {
            if (this.mSubMgr == null) {
                this.mSubMgr =
                        (SubscriptionManager)
                                this.mContext.getSystemService("telephony_subscription_service");
            }
            SubscriptionInfo activeSubscriptionInfo =
                    this.mSubMgr.getActiveSubscriptionInfo(
                            SubscriptionManager.getDefaultDataSubscriptionId());
            if (activeSubscriptionInfo != null && activeSubscriptionInfo.getSimSlotIndex() == i) {
                setNetworkState(UserHandle.getCallingUserId(), z);
            }
        }
        return dynamicSimControl;
    }

    public final int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(
                enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, z, 2);
    }

    public final boolean allowIncomingMms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(
                "PHONERESTRICTION",
                enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid,
                z,
                0,
                "allowIncomingMms");
    }

    public final boolean allowIncomingSms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(
                "PHONERESTRICTION",
                enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid,
                z,
                0,
                "allowIncomingSms");
    }

    public final int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(
                enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, z, 4);
    }

    public final int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(
                enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, z, 6);
    }

    public final int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(
                enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, z, 3);
    }

    public final boolean allowOutgoingMms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(
                "PHONERESTRICTION",
                enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid,
                z,
                0,
                "allowOutgoingMms");
    }

    public final boolean allowOutgoingSms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(
                "PHONERESTRICTION",
                enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid,
                z,
                0,
                "allowOutgoingSms");
    }

    public final int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(
                enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, z, 5);
    }

    public final boolean allowWapPush(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(
                "PHONERESTRICTION",
                enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid,
                z,
                0,
                "wapPushEnabled");
    }

    public final boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "PHONERESTRICTION",
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        z,
                        0,
                        "blockMmsWithStorage");
        if (!z && putBoolean && !isBlockMmsWithStorageEnabled(enforceOwnerOnlyAndPhonePermission)) {
            this.mSmsMmsDeliveryHandler.sendMessage(Message.obtain(this.mSmsMmsDeliveryHandler, 1));
        }
        return putBoolean;
    }

    public final boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "PHONERESTRICTION",
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        z,
                        0,
                        "blockSmsWithStorage");
        if (!z && putBoolean && !isBlockSmsWithStorageEnabled(enforceOwnerOnlyAndPhonePermission)) {
            this.mSmsMmsDeliveryHandler.sendMessage(Message.obtain(this.mSmsMmsDeliveryHandler, 0));
        }
        return putBoolean;
    }

    public final boolean canIncomingCall(String str) {
        boolean z;
        int limitOfIncomingCalls;
        if (!this.mTelMgr.isVoiceCapable() || str == null) {
            return true;
        }
        if (this.mTelMgr.isNetworkRoaming()
                && !getEDM$24().getRoamingPolicy().isRoamingVoiceCallsEnabled()) {
            KnoxsdkFileLog.d("PhoneRestrictionPolicy", "canIncomingCall returning false ");
            return false;
        }
        boolean isNumberAllowed =
                isNumberAllowed(str, "incomingPattern", "incomingCallExceptionPattern");
        if (isLimitNumberOfCallsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                updateDateAndCounters();
                int limitOfIncomingCalls2 = getLimitOfIncomingCalls(null, 0);
                if ((Integer.parseInt(
                                                this.mEdmStorageProvider.getGenericValueAsUser(
                                                        0, "incomingCallCountDay"))
                                        < limitOfIncomingCalls2
                                || limitOfIncomingCalls2 < 1)
                        && (Integer.parseInt(
                                                this.mEdmStorageProvider.getGenericValueAsUser(
                                                        0, "incomingCallCountWeek"))
                                        < (limitOfIncomingCalls = getLimitOfIncomingCalls(null, 1))
                                || limitOfIncomingCalls < 1)) {
                    int limitOfIncomingCalls3 = getLimitOfIncomingCalls(null, 2);
                    if (Integer.parseInt(
                                            this.mEdmStorageProvider.getGenericValueAsUser(
                                                    0, "incomingCallCountMonth"))
                                    < limitOfIncomingCalls3
                            || limitOfIncomingCalls3 < 1) {
                        z = true;
                    }
                }
                z = false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            KnoxsdkFileLog.d("PhoneRestrictionPolicy", "underLimit set to true.");
            z = true;
        }
        boolean z2 = isNumberAllowed && z;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "PhoneRestrictionPolicy.canIncomingCall >>>>", "PhoneRestrictionPolicy", z2);
        return z2;
    }

    public final boolean canIncomingSms(String str) {
        boolean z;
        int limitOfIncomingSms;
        int limitOfIncomingSms2;
        if (str == null) {
            return true;
        }
        boolean isNumberAllowed =
                isNumberAllowed(
                        str, "smsRestrictionIncomingPattern", "incomingSmsExceptionPattern");
        if (isLimitNumberOfSmsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d("PhoneRestrictionPolicy", "Limit of sms is enabled!!!");
                updateDateAndCountersSms();
                int limitOfIncomingSms3 = getLimitOfIncomingSms(null, 0);
                Log.d(
                        "PhoneRestrictionPolicy",
                        "canIncomingSms - limitDay = " + limitOfIncomingSms3);
                int parseInt =
                        Integer.parseInt(
                                this.mEdmStorageProvider.getGenericValueAsUser(
                                        0, "incomingSmsCountDay"));
                Log.d("PhoneRestrictionPolicy", "canIncomingSms - countDay = " + parseInt);
                if ((parseInt >= limitOfIncomingSms3 && limitOfIncomingSms3 >= 1)
                        || ((Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "incomingSmsCountWeek"))
                                                >= (limitOfIncomingSms =
                                                        getLimitOfIncomingSms(null, 1))
                                        && limitOfIncomingSms >= 1)
                                || (Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "incomingSmsCountMonth"))
                                                >= (limitOfIncomingSms2 =
                                                        getLimitOfIncomingSms(null, 2))
                                        && limitOfIncomingSms2 >= 1))) {
                    z = false;
                }
                Log.d("PhoneRestrictionPolicy", "canIncomingSms - limit = true");
                z = true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            z = true;
        }
        return isNumberAllowed && z;
    }

    public final boolean canOutgoingCall(String str) {
        boolean z;
        int limitOfOutgoingCalls;
        if (str == null || !this.mTelMgr.isVoiceCapable()) {
            return true;
        }
        boolean isNumberAllowed =
                isNumberAllowed(str, "outgoingPattern", "outgoingCallExceptionPattern");
        if (isLimitNumberOfCallsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                updateDateAndCounters();
                int limitOfOutgoingCalls2 = getLimitOfOutgoingCalls(null, 0);
                if ((Integer.parseInt(
                                                this.mEdmStorageProvider.getGenericValueAsUser(
                                                        0, "outgoingCallCountDay"))
                                        < limitOfOutgoingCalls2
                                || limitOfOutgoingCalls2 < 1)
                        && (Integer.parseInt(
                                                this.mEdmStorageProvider.getGenericValueAsUser(
                                                        0, "outgoingCallCountWeek"))
                                        < (limitOfOutgoingCalls = getLimitOfOutgoingCalls(null, 1))
                                || limitOfOutgoingCalls < 1)) {
                    int limitOfOutgoingCalls3 = getLimitOfOutgoingCalls(null, 2);
                    if (Integer.parseInt(
                                            this.mEdmStorageProvider.getGenericValueAsUser(
                                                    0, "outgoingCallCountMonth"))
                                    < limitOfOutgoingCalls3
                            || limitOfOutgoingCalls3 < 1) {
                        z = true;
                    }
                }
                z = false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            z = true;
        }
        boolean z2 = isNumberAllowed && z;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "PhoneRestrictionPolicy.canOutgoingCall >>>> ", "PhoneRestrictionPolicy", z2);
        return z2;
    }

    public final boolean canOutgoingSms(String str) {
        boolean z;
        int limitOfOutgoingSms;
        int limitOfOutgoingSms2;
        if (str == null) {
            return true;
        }
        boolean isNumberAllowed =
                isNumberAllowed(
                        str, "smsRestrictionOutgoingPattern", "outgoingSmsExceptionPattern");
        if (isLimitNumberOfSmsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d("PhoneRestrictionPolicy", "Limit of sms is enabled!!!");
                updateDateAndCountersSms();
                int limitOfOutgoingSms3 = getLimitOfOutgoingSms(null, 0);
                Log.d(
                        "PhoneRestrictionPolicy",
                        "canOutgoingSms - limitDay = " + limitOfOutgoingSms3);
                int parseInt =
                        Integer.parseInt(
                                this.mEdmStorageProvider.getGenericValueAsUser(
                                        0, "outgoingSmsCountDay"));
                Log.d("PhoneRestrictionPolicy", "canOutgoingSms - countDay = " + parseInt);
                if ((parseInt >= limitOfOutgoingSms3 && limitOfOutgoingSms3 >= 1)
                        || ((Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountWeek"))
                                                >= (limitOfOutgoingSms =
                                                        getLimitOfOutgoingSms(null, 1))
                                        && limitOfOutgoingSms >= 1)
                                || (Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountMonth"))
                                                >= (limitOfOutgoingSms2 =
                                                        getLimitOfOutgoingSms(null, 2))
                                        && limitOfOutgoingSms2 >= 1))) {
                    z = false;
                }
                Log.d("PhoneRestrictionPolicy", "canOutgoingSms - limit = true");
                z = true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            z = true;
        }
        Log.d(
                "PhoneRestrictionPolicy",
                "SmsRestrictionPolicy.canOutgoingSms >>>>" + isNumberAllowed + " >>> " + z);
        return isNumberAllowed && z;
    }

    public final int changeSimPinCode(
            ContextInfo contextInfo, String str, String str2, String str3) {
        int i;
        boolean putValuesForAdminAndField;
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i2 = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (!validatePinCode(str2) || !validatePinCode(str3)) {
            return 2;
        }
        if (TextUtils.isEmpty(str)) {
            SubscriptionInfo activeSubscriptionInfo =
                    this.mSubMgr.getActiveSubscriptionInfo(
                            SubscriptionManager.getDefaultSubscriptionId());
            if (activeSubscriptionInfo == null) {
                return 9;
            }
            str = activeSubscriptionInfo.getIccId();
            if (str == null) {
                Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim not inserted");
                return 9;
            }
        }
        String str4 = str;
        int simSubId = getSimSubId(str4);
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                simSubId,
                "lockUnlockCorporateSimCard Sim subscription ID ",
                "PhoneRestrictionPolicy");
        if (simSubId == -1) {
            return 13;
        }
        if (!isSimLocked(simSubId)) {
            return 5;
        }
        int adminByField =
                this.mSimDbProxy.mEdmStorageProvider.getAdminByField("SimTable", "SimIccId", str4);
        if (adminByField != -1 && adminByField != i2) {
            return 12;
        }
        Log.d("PhoneRestrictionPolicy", "changeSimPinCodeService");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i = this.mEnterpriseSimPin.changeSimPinCode(simSubId, str2, str3);
            } catch (IllegalStateException | SecurityException e) {
                Log.e(
                        "PhoneRestrictionPolicy",
                        "Failed to changeSimPinCodeService: " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = 100;
            }
            if (i == 0) {
                if (isSimLockedByAdmin(str4)) {
                    SimDBProxy simDBProxy = this.mSimDbProxy;
                    simDBProxy.getClass();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("SimPinCode", str3);
                    putValuesForAdminAndField =
                            simDBProxy.mEdmStorageProvider.putValuesForAdminAndField(
                                    i2, contentValues, "SimTable", "SimIccId", str4);
                } else {
                    putValuesForAdminAndField = this.mSimDbProxy.addSimcard(i2, str4, str3);
                }
                if (!putValuesForAdminAndField) {
                    return 10;
                }
            }
            Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndPhonePermission);
            setLockedIccIdsSystemUI(0);
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean checkDataCallLimit() {
        long j;
        long j2;
        long j3;
        enforceSystemUser$2();
        if (!this.mDataLimitEnabled) {
            return false;
        }
        long limitOfDataCalls = getLimitOfDataCalls(null, 0);
        long limitOfDataCalls2 = getLimitOfDataCalls(null, 1);
        long limitOfDataCalls3 = getLimitOfDataCalls(null, 2);
        try {
            j =
                    Long.parseLong(
                            this.mEdmStorageProvider.getGenericValueAsUser(
                                    0, "dataCallBytesCountByDay"));
        } catch (NumberFormatException unused) {
            Log.d("PhoneRestrictionPolicy", "Could not read from Storage");
            j = 0;
        }
        try {
            j2 =
                    Long.parseLong(
                            this.mEdmStorageProvider.getGenericValueAsUser(
                                    0, "dataCallBytesCountByWeek"));
        } catch (NumberFormatException unused2) {
            Log.d("PhoneRestrictionPolicy", "Could not read from Storage");
            j2 = 0;
        }
        try {
            j3 =
                    Long.parseLong(
                            this.mEdmStorageProvider.getGenericValueAsUser(
                                    0, "dataCallByteCountByMonth"));
        } catch (NumberFormatException unused3) {
            Log.d("PhoneRestrictionPolicy", "Could not read from Storage");
            j3 = 0;
        }
        return (0 < limitOfDataCalls && j > limitOfDataCalls)
                || (0 < limitOfDataCalls2 && j2 > limitOfDataCalls2)
                || (0 < limitOfDataCalls3 && j3 > limitOfDataCalls3);
    }

    public final boolean checkEnableUseOfPacketData(boolean z) {
        boolean z2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        updateDateAndDataCallCounters(0L);
        if (checkDataCallLimit() || !getEDM$24().getRestrictionPolicy().isCellularDataAllowed()) {
            if (z) {
                RestrictionToastManager.show(R.string.fingerprint_error_bad_calibration);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean clearStoredBlockedMms(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.deleteDataByFields(
                "SMSMMSBlockedDelivery", new String[] {"smsMmsType"}, new String[] {"0"});
    }

    public final boolean clearStoredBlockedSms(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.deleteDataByFields(
                "SMSMMSBlockedDelivery", new String[] {"smsMmsType"}, new String[] {"1"});
    }

    public final void dataLimitCounter(long j, String str) {
        long j2;
        try {
            j2 = Long.parseLong(this.mEdmStorageProvider.getGenericValueAsUser(0, str));
        } catch (NumberFormatException unused) {
            j2 = 0;
        }
        this.mEdmStorageProvider.putGenericValueAsUser(0, str, Long.toString(j2 + j));
    }

    public final boolean decreaseNumberOfOutgoingSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SmsRestrictionPolicy.decreaseNumberOfOutgoingSms()");
        enforceSms();
        if (!isLimitNumberOfSmsEnabled(null)) {
            return false;
        }
        boolean putGenericValueAsUser =
                this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "outgoingSmsCountMonth",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountMonth"))
                                                - 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "outgoingSmsCountDay",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountDay"))
                                                - 1))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0,
                                "outgoingSmsCountWeek",
                                String.valueOf(
                                        Integer.parseInt(
                                                        this.mEdmStorageProvider
                                                                .getGenericValueAsUser(
                                                                        0, "outgoingSmsCountWeek"))
                                                - 1));
        Log.d("PhoneRestrictionPolicy", "SmsRestrictionPolicy.addNumberOfOutgoingSms >>>");
        return putGenericValueAsUser;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump PhoneRestrictionPolicy");
            return;
        }
        new EnterpriseDumpHelper(this.mContext)
                .dumpTable(printWriter, "SimTable", new String[] {"adminUid", "SimIccId"}, null);
        for (int i = 0; i < 2; i++) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "RCSEnabled for sim slot ", ": ");
            m.append(isRCSEnabledBySimSlot(null, 1, false, i));
            m.append(System.lineSeparator());
            printWriter.write(m.toString());
        }
    }

    public final boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Log.d("PhoneRestrictionPolicy", " >>>> enableLimitNumberOfCalls ");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        if (z && !isLimitNumberOfCallsEnabled(enforceOwnerOnlyAndPhonePermission)) {
            resetCallsCount(enforceOwnerOnlyAndPhonePermission);
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            long timeInMillis = calendar.getTimeInMillis();
            if (!this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "dateDay", String.valueOf(timeInMillis))
                    || !this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "dateWeek", String.valueOf(timeInMillis))
                    || !this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "dateMonth", String.valueOf(timeInMillis))) {
                z2 = false;
                Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfCalls  >>>>>");
                return z2
                        && this.mEdmStorageProvider.putBoolean(
                                "PHONERESTRICTION",
                                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                                z,
                                0,
                                "limitCallEnable");
            }
        }
        z2 = true;
        Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfCalls  >>>>>");
        if (z2) {
            return false;
        }
    }

    public final boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Log.d("PhoneRestrictionPolicy", " >>>> enableLimitNumberOfSMS ");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (z && !isLimitNumberOfSmsEnabled(enforceOwnerOnlyAndPhonePermission)) {
            resetSmsCount(enforceOwnerOnlyAndPhonePermission);
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            long timeInMillis = calendar.getTimeInMillis();
            if (!this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "smsDateDay", String.valueOf(timeInMillis))
                    || !this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "smsDateWeek", String.valueOf(timeInMillis))
                    || !this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "smsDateMonth", String.valueOf(timeInMillis))) {
                z2 = false;
                Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfSMS  >>>>>");
                return z2
                        && this.mEdmStorageProvider.putBoolean(
                                "PHONERESTRICTION",
                                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                                z,
                                0,
                                "limitSmsEnable");
            }
        }
        z2 = true;
        Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfSMS  >>>>>");
        if (z2) {
            return false;
        }
    }

    public final ContextInfo enforceOwnerOnlyAndPhonePermission(ContextInfo contextInfo) {
        return getEDM$24()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_PHONE_RESTRICTION")));
    }

    public final ContextInfo enforceOwnerOnlyAndSimRestrictionPermission(ContextInfo contextInfo) {
        return getEDM$24()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_SIM_RESTRICTION")));
    }

    public final void enforcePhoneAppOrOwnerOnlyAndPhonePermission(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        if (UserHandle.getAppId(contextInfo.mCallerUid) != 1001) {
            enforceOwnerOnlyAndPhonePermission(contextInfo);
        }
    }

    public final boolean getDataCallLimitEnabled(ContextInfo contextInfo) {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "PHONERESTRICTION", "enableLimitDataCall")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public final String getDisclaimerText(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getDisclaimerText()");
        Iterator it =
                ((ArrayList)
                                this.mEdmStorageProvider.getStringListAsUser(
                                        0, "PHONERESTRICTION", "disclaimerText"))
                        .iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null) {
                return str;
            }
        }
        return null;
    }

    public final EnterpriseDeviceManager getEDM$24() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
        boolean z2 = false;
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        try {
            if (z) {
                Iterator it =
                        this.mEdmStorageProvider
                                .getBooleanListAsUser(0, "PHONERESTRICTION", "emergencyCallOnly")
                                .iterator();
                while (it.hasNext()) {
                    if (((Boolean) it.next()).booleanValue()) {
                        return true;
                    }
                }
            } else {
                int callingUserId = UserHandle.getCallingUserId();
                if (contextInfo == null) {
                    contextInfo = new ContextInfo(Binder.getCallingUid());
                }
                if (contextInfo.mParent) {
                    int i = EnterpriseDeviceManagerService.$r8$clinit;
                    return this.mEdmStorageProvider.getBoolean(
                            ((EnterpriseDeviceManagerServiceImpl)
                                            ((EnterpriseDeviceManagerService)
                                                    EnterpriseService.sEdmsInstance))
                                    .mPseudoAdminUid,
                            0,
                            "PHONERESTRICTION",
                            "emergencyCallOnly");
                }
                if (callingUserId != 0
                        || UserHandle.getUserId(contextInfo.mCallerUid) != 0
                        || contextInfo.mContainerId > 0) {
                    throw new SecurityException("Operation supported only on owner space");
                }
                z2 =
                        this.mEdmStorageProvider.getBoolean(
                                contextInfo.mCallerUid, 0, "PHONERESTRICTION", "emergencyCallOnly");
            }
        } catch (Exception unused) {
        }
        Log.e("PhoneRestrictionPolicy", "getEmergencyCall >>" + z2);
        return z2;
    }

    public final String getIncomingCallExceptionPatterns(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.getIncomingCallExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(
                    enforceOwnerOnlyAndPhonePermission, "incomingCallExceptionPattern", true);
        }
        return null;
    }

    public final String getIncomingCallRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getIncomingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, "incomingPattern", z);
        }
        return null;
    }

    public final String getIncomingSmsExceptionPatterns(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.getIncomingSmsExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(
                    enforceOwnerOnlyAndPhonePermission, "incomingSmsExceptionPattern", true);
        }
        return null;
    }

    public final String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(
                    enforceOwnerOnlyAndPhonePermission, "smsRestrictionIncomingPattern", z);
        }
        return null;
    }

    public final long getLimitOfDataCalls(ContextInfo contextInfo, int i) {
        ArrayList longListAsUser;
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (i == 0) {
            longListAsUser =
                    this.mEdmStorageProvider.getLongListAsUser(
                            0, "PHONERESTRICTION", "dataCallByDay");
        } else if (i == 1) {
            longListAsUser =
                    this.mEdmStorageProvider.getLongListAsUser(
                            0, "PHONERESTRICTION", "dataCallByWeek");
        } else {
            if (i != 2) {
                return -1L;
            }
            longListAsUser =
                    this.mEdmStorageProvider.getLongListAsUser(
                            0, "PHONERESTRICTION", "dataCallByMonth");
        }
        if (longListAsUser.isEmpty()) {
            return 0L;
        }
        Iterator it = longListAsUser.iterator();
        long j = 0;
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            if (longValue != 0 && (longValue < j || j == 0)) {
                j = longValue;
            }
        }
        return j;
    }

    public final int getLimitOfIncomingCalls(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intListAsUser;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return -1;
        }
        int i2 = 0;
        if (i == 0) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "incomingCallByDay");
        } else if (i == 1) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "incomingCallByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "incomingCallByMonth");
        }
        if (!intListAsUser.isEmpty()) {
            for (Integer num : intListAsUser) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public final int getLimitOfIncomingSms(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intListAsUser;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        int i2 = 0;
        if (i == 0) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "incomingSmsByDay");
        } else if (i == 1) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "incomingSmsByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "incomingSmsByMonth");
        }
        if (!intListAsUser.isEmpty()) {
            for (Integer num : intListAsUser) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public final int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intListAsUser;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return -1;
        }
        int i2 = 0;
        if (i == 0) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "outgoingCallByDay");
        } else if (i == 1) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "outgoingCallByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "outgoingCallByMonth");
        }
        if (!intListAsUser.isEmpty()) {
            for (Integer num : intListAsUser) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public final int getLimitOfOutgoingSms(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intListAsUser;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        int i2 = 0;
        if (i == 0) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "outgoingSmsByDay");
        } else if (i == 1) {
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "outgoingSmsByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intListAsUser =
                    this.mEdmStorageProvider.getIntListAsUser(
                            0, 0, "PHONERESTRICTION", "outgoingSmsByMonth");
        }
        if (!intListAsUser.isEmpty()) {
            for (Integer num : intListAsUser) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public final String getOutgoingCallExceptionPatterns(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.getOutgoingCallExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(
                    enforceOwnerOnlyAndPhonePermission, "outgoingCallExceptionPattern", true);
        }
        return null;
    }

    public final String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getOutgoingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, "outgoingPattern", z);
        }
        return null;
    }

    public final String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.getOutgoingSmsExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(
                    enforceOwnerOnlyAndPhonePermission, "outgoingSmsExceptionPattern", true);
        }
        return null;
    }

    public final String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(
                    enforceOwnerOnlyAndPhonePermission, "smsRestrictionOutgoingPattern", z);
        }
        return null;
    }

    public final String getPinCode(String str) {
        int i;
        int callingUid = Binder.getCallingUid();
        boolean z = false;
        try {
            i =
                    this.mContext
                            .getPackageManager()
                            .getPackageUidAsUser(Constants.SYSTEMUI_PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("PhoneRestrictionPolicy", "Unable to resolve SystemUI's UID.", e);
            i = -1;
        }
        int appId = UserHandle.getAppId(callingUid);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid != null) {
            int lastIndexOf = nameForUid.lastIndexOf(":");
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            if (nameForUid.equals("android.uid.systemui") && appId == i) {
                z = true;
            }
        }
        if (callingUid == Process.myUid() || callingUid == 1001 || z) {
            return this.mSimDbProxy.getPincode(str);
        }
        throw new SecurityException("Can only be called by System, Phone or System UI");
    }

    public final Bundle getRCSMessage(ContextInfo contextInfo, long j) {
        Log.d("PhoneRestrictionPolicy", "getRCS, Start");
        getEDM$24()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
        String[] strArr = {Long.toString(j)};
        Log.d("PhoneRestrictionPolicy", "getRCS, Start query");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Bundle bundle = null;
        try {
            try {
                Cursor query =
                        this.mContext
                                .getContentResolver()
                                .query(RCS_URI, RCS_PROJ, "_id=?", strArr, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            Log.d("PhoneRestrictionPolicy", "getRCS, collect data");
                            Bundle bundle2 = new Bundle();
                            bundle2.putString(
                                    "remote-uri",
                                    query.getString(query.getColumnIndex("remote_uri")));
                            bundle2.putString(
                                    "sender-alias",
                                    query.getString(query.getColumnIndex("sender_alias")));
                            bundle2.putString(
                                    "content-type",
                                    query.getString(query.getColumnIndex("content_type")));
                            bundle2.putString(
                                    "body", query.getString(query.getColumnIndex("body")));
                            if (query.getInt(query.getColumnIndex("direction")) == 0) {
                                bundle2.putLong(
                                        "timestamp",
                                        query.getLong(query.getColumnIndex("inserted_timestamp")));
                            } else {
                                bundle2.putLong(
                                        "timestamp",
                                        query.getLong(query.getColumnIndex("sent_timestamp")));
                            }
                            if (query.getInt(query.getColumnIndex("is_filetransfer")) == 1) {
                                bundle2.putString(
                                        "file-path",
                                        query.getString(query.getColumnIndex("file_path")));
                                bundle2.putString(
                                        "thumbnail-path",
                                        query.getString(query.getColumnIndex("thumbnail_path")));
                            }
                            bundle = bundle2;
                        }
                    } catch (Throwable th) {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                Log.e("PhoneRestrictionPolicy", e.getMessage());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.d("PhoneRestrictionPolicy", "getRCS, End");
            return bundle;
        } catch (Throwable th3) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th3;
        }
    }

    public final String getRestrictionPattern(ContextInfo contextInfo, String str, boolean z) {
        if (!z) {
            return this.mEdmStorageProvider.getString(
                    enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid,
                    0,
                    "PHONERESTRICTION",
                    str);
        }
        Iterator it =
                ((ArrayList)
                                this.mEdmStorageProvider.getStringListAsUser(
                                        0, "PHONERESTRICTION", str))
                        .iterator();
        String str2 = "";
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (!TextUtils.isEmpty(str3)) {
                str2 = str2.concat(str3).concat("|");
            }
        }
        if (str2.endsWith("|")) {
            return DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, 0, str2);
        }
        return null;
    }

    public final int getSimSubId(String str) {
        List<SubscriptionInfo> activeSubscriptionInfoList =
                this.mSubMgr.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            return -1;
        }
        for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
            if (str.equals(subscriptionInfo.getIccId())) {
                return subscriptionInfo.getSubscriptionId();
            }
        }
        return -1;
    }

    public final boolean getSmsMmsAllowed(String str) {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "PHONERESTRICTION", str)
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo) {
        return this.mEdmStorageProvider
                .getBooleanListAsUser(0, "PHONERESTRICTION", "blockMmsWithStorage")
                .contains(Boolean.TRUE);
    }

    public final boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo) {
        return this.mEdmStorageProvider
                .getBooleanListAsUser(0, "PHONERESTRICTION", "blockSmsWithStorage")
                .contains(Boolean.TRUE);
    }

    public final boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) {
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "PHONERESTRICTION", "allowCallerID")
                            .iterator();
            while (it.hasNext()) {
                boolean booleanValue = ((Boolean) it.next()).booleanValue();
                if (!booleanValue) {
                    return booleanValue;
                }
            }
            return true;
        } catch (Exception e) {
            Log.w("PhoneRestrictionPolicy", e.toString());
            return true;
        }
    }

    public final boolean isCopyContactToSimAllowed(ContextInfo contextInfo) {
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "PHONERESTRICTION", "copyContactToSimEnabled")
                            .iterator();
            while (it.hasNext()) {
                boolean booleanValue = ((Boolean) it.next()).booleanValue();
                if (!booleanValue) {
                    return booleanValue;
                }
            }
            return true;
        } catch (SQLiteException e) {
            Log.w("PhoneRestrictionPolicy", "Error on isCopyContactToSimAllowed. ", e);
            return true;
        }
    }

    public final boolean isDataAllowedFromSimSlot(int i) {
        return isOperationAllowed(1, i);
    }

    public final boolean isIncomingCallAllowedFromSimSlot(int i) {
        return isOperationAllowed(2, i);
    }

    public final boolean isIncomingMmsAllowed(ContextInfo contextInfo) {
        return getSmsMmsAllowed("allowIncomingMms");
    }

    public final boolean isIncomingSmsAllowed(ContextInfo contextInfo) {
        return getSmsMmsAllowed("allowIncomingSms");
    }

    public final boolean isIncomingSmsAllowedFromSimSlot(int i) {
        return isOperationAllowed(4, i);
    }

    public final boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo) {
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        ArrayList booleanListAsUser =
                this.mEdmStorageProvider.getBooleanListAsUser(
                        0, "PHONERESTRICTION", "limitCallEnable");
        if (!booleanListAsUser.isEmpty()) {
            Iterator it = booleanListAsUser.iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo) {
        ArrayList booleanListAsUser =
                this.mEdmStorageProvider.getBooleanListAsUser(
                        0, "PHONERESTRICTION", "limitSmsEnable");
        if (!booleanListAsUser.isEmpty()) {
            Iterator it = booleanListAsUser.iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isMmsAllowedFromSimSlot(int i) {
        return isOperationAllowed(6, i);
    }

    public final boolean isNumberAllowed(String str, String str2, String str3) {
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValuesListAsUser(
                                0, 0, "PHONERESTRICTION", new String[] {"adminUid", str2});
        if (arrayList.isEmpty()) {
            return true;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            Integer asInteger = contentValues.getAsInteger("adminUid");
            if (asInteger != null) {
                String asString = contentValues.getAsString(str2);
                if (TextUtils.isEmpty(asString)) {
                    continue;
                } else {
                    try {
                        Pattern compile = Pattern.compile(asString);
                        str = removeWhiteSpaces(str);
                        if (compile.matcher(str).matches()) {
                            String string =
                                    this.mEdmStorageProvider.getString(
                                            asInteger.intValue(), 0, "PHONERESTRICTION", str3);
                            if (TextUtils.isEmpty(string)) {
                                return false;
                            }
                            try {
                                if (!Pattern.compile(string).matcher(str).matches()) {
                                    return false;
                                }
                            } catch (PatternSyntaxException unused) {
                                Log.e(
                                        "PhoneRestrictionPolicy",
                                        "failed to compile pattern for exception");
                                return false;
                            }
                        } else {
                            continue;
                        }
                    } catch (PatternSyntaxException unused2) {
                        Log.e(
                                "PhoneRestrictionPolicy",
                                "failed to compile pattern for restriction");
                    }
                }
            }
        }
        return true;
    }

    public final boolean isOperationAllowed(int i, int i2) {
        if (i2 != 0 && i2 != 1) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i2,
                    ">>> PhoneRestrictionPolicy.isOperationAllowed() was failed - SlotID is invalid"
                        + " ",
                    "PhoneRestrictionPolicy");
            return true;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                i2, i, "isOperationAllowed >>> slotNum: ", " function: ", "PhoneRestrictionPolicy");
        ContentValues contentValues = new ContentValues();
        contentValues.put("slotId", Integer.valueOf(i2));
        Cursor cursor =
                this.mEdmStorageProvider.getCursor(
                        "PHONERESTRICTION_SLOTID",
                        new String[] {
                            "slotId",
                            "allowData",
                            "allowIncomingCall",
                            "allowOutgoingCall",
                            "allowIncomingSMS",
                            "allowOutgoingSMS",
                            "allowMMS",
                            "adminUid"
                        },
                        contentValues);
        if (cursor == null) {
            Log.d(
                    "PhoneRestrictionPolicy",
                    ">>> PhoneRestrictionPolicy.isOperationAllowed() Cursor is Null");
            return true;
        }
        cursor.moveToFirst();
        boolean z = true;
        while (!cursor.isAfterLast()) {
            try {
                try {
                    Log.d(
                            "PhoneRestrictionPolicy",
                            ">>> PhoneRestrictionPolicy.isOperationAllowed() - dB have some valid"
                                + " values");
                    boolean z2 = false;
                    if (i == 1) {
                        Log.d(
                                "PhoneRestrictionPolicy",
                                ">>> PhoneRestrictionPolicy.isOperationAllowed() - Result is - "
                                        + cursor.getInt(cursor.getColumnIndex("allowData")));
                        z = z && cursor.getInt(cursor.getColumnIndex("allowData")) > 0;
                    }
                    if (i == 2) {
                        z = z && cursor.getInt(cursor.getColumnIndex("allowIncomingCall")) > 0;
                    }
                    if (i == 3) {
                        z = z && cursor.getInt(cursor.getColumnIndex("allowOutgoingCall")) > 0;
                    }
                    if (i == 4) {
                        z = z && cursor.getInt(cursor.getColumnIndex("allowIncomingSMS")) > 0;
                    }
                    if (i == 5) {
                        z = z && cursor.getInt(cursor.getColumnIndex("allowOutgoingSMS")) > 0;
                    }
                    if (i == 6) {
                        if (z && cursor.getInt(cursor.getColumnIndex("allowMMS")) > 0) {
                            z2 = true;
                        }
                        z = z2;
                    }
                    cursor.moveToNext();
                } catch (SQLException e) {
                    Log.e(
                            "PhoneRestrictionPolicy",
                            "Exception occurred accessing Enterprise db " + e.getMessage());
                }
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
        cursor.close();
        return z;
    }

    public final boolean isOutgoingCallAllowedFromSimSlot(int i) {
        return isOperationAllowed(3, i);
    }

    public final boolean isOutgoingMmsAllowed(ContextInfo contextInfo) {
        if (getEmergencyCallOnly(null, true)) {
            return false;
        }
        return getSmsMmsAllowed("allowOutgoingMms");
    }

    public final boolean isOutgoingSmsAllowed(ContextInfo contextInfo) {
        return getSmsMmsAllowed("allowOutgoingSms");
    }

    public final boolean isOutgoingSmsAllowedFromSimSlot(int i) {
        return isOperationAllowed(5, i);
    }

    public final boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
        return isRCSEnabledInternal(i, "enableRCS", z);
    }

    public final boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
        if (i2 == 0 || i2 == 1) {
            return isRCSEnabledInternal(
                    i, i2 == 1 ? "enableRCSForSimSlot1" : "enableRCSForSimSlot0", z);
        }
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i2,
                "isRCSEnabledBySimSlot(): failed. invalid simSlotId : ",
                "PhoneRestrictionPolicy");
        return false;
    }

    public final boolean isRCSEnabledInternal(int i, String str, boolean z) {
        Log.i(
                "PhoneRestrictionPolicy",
                "isRCSEnabledInternal(), showMsg: " + z + ", columnName: " + str);
        if (!(i == 1)) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i,
                    "isRCSEnabledInternal(): allowed. not support feature = ",
                    "PhoneRestrictionPolicy");
            return true;
        }
        if (getEmergencyCallOnly(null, true)) {
            if (z) {
                RestrictionToastManager.show(17042612);
            }
            return false;
        }
        Iterator it =
                this.mEdmStorageProvider.getIntListAsUser(0, 0, "PHONERESTRICTION", str).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() >= 0 && (num.intValue() & i) != i) {
                Log.i("PhoneRestrictionPolicy", "isRCSEnabledInternal(): disallowed by EDM");
                if (z) {
                    RestrictionToastManager.show(17042612);
                }
                return false;
            }
        }
        return true;
    }

    public final boolean isSimLocked(int i) {
        Log.d("PhoneRestrictionPolicy", "isSimLocked");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mEnterpriseSimPin.isSimLocked(i);
            } catch (IllegalStateException | SecurityException e) {
                Log.e("PhoneRestrictionPolicy", "Failed to check isSimLocked: " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSimLockedByAdmin(String str) {
        if (str == null) {
            return this.mSimDbProxy.mEdmStorageProvider.getCount("SimTable", null) > 0;
        }
        String pincode = this.mSimDbProxy.getPincode(str);
        return (pincode == null || pincode.isEmpty()) ? false : true;
    }

    public final boolean isSmsPatternCheckRequired() {
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValuesListAsUser(
                                0,
                                0,
                                "PHONERESTRICTION",
                                new String[] {"adminUid", "smsRestrictionIncomingPattern"});
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues.getAsInteger("adminUid") != null
                        && !TextUtils.isEmpty(
                                contentValues.getAsString("smsRestrictionIncomingPattern"))) {
                    return true;
                }
            }
        }
        ArrayList arrayList2 =
                (ArrayList)
                        this.mEdmStorageProvider.getValuesListAsUser(
                                0,
                                0,
                                "PHONERESTRICTION",
                                new String[] {"adminUid", "smsRestrictionOutgoingPattern"});
        if (!arrayList2.isEmpty()) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it2.next();
                if (contentValues2.getAsInteger("adminUid") != null
                        && !TextUtils.isEmpty(
                                contentValues2.getAsString("smsRestrictionOutgoingPattern"))) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isSubIdLockedByAdmin(int i) {
        SubscriptionInfo activeSubscriptionInfo = this.mSubMgr.getActiveSubscriptionInfo(i);
        return isSimLockedByAdmin(
                activeSubscriptionInfo != null ? activeSubscriptionInfo.getIccId() : null);
    }

    public final boolean isWapPushAllowed(ContextInfo contextInfo) {
        boolean z;
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "PHONERESTRICTION", "wapPushEnabled")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isWapPushAllowed : ", "PhoneRestrictionPolicy", z);
        return z;
    }

    public final synchronized int lockUnlockCorporateSimCard(
            ContextInfo contextInfo, String str, String str2, boolean z) {
        boolean removeByAdminAndField;
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard lock " + z + " iccId " + str);
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (!validatePinCode(str2)) {
            logError(2);
            return 2;
        }
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard valid pincode");
        if (TextUtils.isEmpty(str)) {
            SubscriptionInfo activeSubscriptionInfo =
                    this.mSubMgr.getActiveSubscriptionInfo(
                            SubscriptionManager.getDefaultSubscriptionId());
            if (activeSubscriptionInfo == null) {
                return 9;
            }
            str = activeSubscriptionInfo.getIccId();
            if (str == null) {
                logError(9);
                return 9;
            }
        }
        String str3 = str;
        int simSubId = getSimSubId(str3);
        Log.d(
                "PhoneRestrictionPolicy",
                "lockUnlockCorporateSimCard Sim subscription ID " + simSubId);
        if (simSubId == -1) {
            logError(13);
            return 13;
        }
        int adminByField =
                this.mSimDbProxy.mEdmStorageProvider.getAdminByField("SimTable", "SimIccId", str3);
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim owner " + adminByField);
        boolean isSimLocked = isSimLocked(simSubId);
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard isSimLocked  " + isSimLocked);
        if (z) {
            if (isSimLocked) {
                if (adminByField == -1) {
                    logError(4);
                    return 4;
                }
                if (adminByField == i) {
                    logError(11);
                    return 11;
                }
                if (adminByField != i) {
                    logError(12);
                    return 12;
                }
            } else if (adminByField != -1) {
                if (adminByField == i) {
                    this.mSimDbProxy.mEdmStorageProvider.removeByAdminAndField(
                            i, "SimTable", "SimIccId", str3);
                } else if (adminByField != i) {
                    logError(12);
                    return 12;
                }
            }
        } else if (!isSimLocked) {
            logError(5);
            if (adminByField == -1) {
                return 5;
            }
            if (adminByField == i) {
                this.mSimDbProxy.mEdmStorageProvider.removeByAdminAndField(
                        i, "SimTable", "SimIccId", str3);
                return 5;
            }
            if (adminByField != i) {
                return 5;
            }
        } else if (adminByField != -1 && adminByField != i && adminByField != i) {
            logError(12);
            return 12;
        }
        int subIdLockEnabled = setSubIdLockEnabled(simSubId, str2, z);
        logError(subIdLockEnabled);
        if (subIdLockEnabled == 0) {
            if (!z) {
                removeByAdminAndField =
                        adminByField != -1
                                ? this.mSimDbProxy.mEdmStorageProvider.removeByAdminAndField(
                                        i, "SimTable", "SimIccId", str3)
                                : true;
            } else if (adminByField == -1) {
                removeByAdminAndField = this.mSimDbProxy.addSimcard(i, str3, str2);
            } else {
                SimDBProxy simDBProxy = this.mSimDbProxy;
                simDBProxy.getClass();
                ContentValues contentValues = new ContentValues();
                contentValues.put("SimPinCode", str2);
                removeByAdminAndField =
                        simDBProxy.mEdmStorageProvider.putValuesForAdminAndField(
                                i, contentValues, "SimTable", "SimIccId", str3);
            }
            setLockedIccIdsSystemUI(
                    Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndPhonePermission));
            if (!removeByAdminAndField) {
                logError(10);
                return 10;
            }
        }
        return subIdLockEnabled;
    }

    public final void logToKnoxsdkFile$2(int i, String str, String str2, Boolean bool) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(
                    "callerId: "
                            + i
                            + ", pkgName: "
                            + this.mContext.getPackageManager().getNameForUid(i));
            sb.append(", api: ".concat(str));
            if (str2 != null) {
                sb.append(", param: ".concat(str2));
            }
            sb.append(", result: " + bool);
        } catch (Exception e) {
            KnoxsdkFileLog.d(
                    "PhoneRestrictionPolicy",
                    "logToKnoxsdkFile failed due to unexpected exception",
                    e);
        }
        KnoxsdkFileLog.d("PhoneRestrictionPolicy", sb.toString());
    }

    public final void notifySmsPatternCheck() {
        Intent intent =
                new Intent(
                        "com.samsung.android.knox.intent.action.REQUEST_SMS_PATTERN_CHECK_INTERNAL");
        intent.putExtra(
                LauncherConfigurationInternal.KEY_STATE_BOOLEAN, isSmsPatternCheckRequired());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcast(intent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        updateDataLimitState();
        this.mSmsMmsDeliveryHandler.sendMessage(Message.obtain(this.mSmsMmsDeliveryHandler, 2));
        this.mSimDbProxy.mEdmStorageProvider.removeByAdmin(i, 0, "SimTable");
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            setLockedIccIdsSystemUI(callingOrCurrentUserId);
        }
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse("content://com.sec.knox.provider2/PhoneRestrictionPolicy/isRCSEnabled"));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.removeIncomingCallExceptionPattern()");
        return setIncomingCallExceptionPattern(contextInfo, "");
    }

    public final boolean removeIncomingCallRestriction(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.removeIncomingCallRestriction()");
        return setIncomingCallRestriction(contextInfo, "");
    }

    public final boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.removeIncomingSmsExceptionPattern()");
        return setIncomingSmsExceptionPattern(contextInfo, "");
    }

    public final boolean removeIncomingSmsRestriction(ContextInfo contextInfo) {
        return setIncomingSmsRestriction(contextInfo, "");
    }

    public final boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.removeOutgoingCallExceptionPattern()");
        return setOutgoingCallExceptionPattern(contextInfo, "");
    }

    public final boolean removeOutgoingCallRestriction(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.removeOutgoingCallRestriction()");
        return setOutgoingCallRestriction(contextInfo, "");
    }

    public final boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.removeOutgoingSmsExceptionPattern()");
        return setOutgoingSmsExceptionPattern(contextInfo, "");
    }

    public final boolean removeOutgoingSmsRestriction(ContextInfo contextInfo) {
        return setOutgoingSmsRestriction(contextInfo, "");
    }

    public final boolean resetCallsCount(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingCallCountMonth", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingCallCountDay", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingCallCountDay", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingCallCountWeek", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingCallCountWeek", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingCallCountMonth", String.valueOf(0));
    }

    public final boolean resetDataCallLimitCounter(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dataCallByteCountByMonth", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dataCallBytesCountByDay", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dataCallBytesCountByWeek", String.valueOf(0));
    }

    public final boolean resetSmsCount(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingSmsCountMonth", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingSmsCountDay", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingSmsCountDay", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingSmsCountWeek", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingSmsCountWeek", String.valueOf(0))
                & this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingSmsCountMonth", String.valueOf(0));
    }

    public final void restorePacketDataNetworkSetting() {
        if (this.mDataCheckboxPreviousState
                && getEDM$24().getRestrictionPolicy().isCellularDataAllowed()) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mContext.getSystemService("phone");
            if (telephonyManager == null) {
                Log.d("PhoneRestrictionPolicy", "Failed to get Telephony Manager");
                return;
            }
            if (!telephonyManager.getDataEnabled()) {
                telephonyManager.setDataEnabled(true);
            }
            this.mDataCheckboxPreviousState = false;
            this.mEdmStorageProvider.putGenericValueAsUser(
                    0, "dataCallPacketDataCheckBox", String.valueOf(false));
        }
    }

    public final boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!z || this.mDataLimitEnabled) {
            z2 = true;
        } else {
            resetDataCallLimitCounter(enforceOwnerOnlyAndPhonePermission);
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            long timeInMillis = calendar.getTimeInMillis();
            z2 =
                    this.mEdmStorageProvider.putGenericValueAsUser(
                                    0, "dataCallDateMonth", String.valueOf(timeInMillis))
                            & this.mEdmStorageProvider.putGenericValueAsUser(
                                    0, "dataCallDateDay", String.valueOf(timeInMillis))
                            & this.mEdmStorageProvider.putGenericValueAsUser(
                                    0, "dataCallDateWeek", String.valueOf(timeInMillis));
        }
        boolean putBoolean =
                z2
                        & this.mEdmStorageProvider.putBoolean(
                                "PHONERESTRICTION",
                                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                                z,
                                0,
                                "enableLimitDataCall");
        if (putBoolean) {
            boolean dataCallLimitEnabled =
                    getDataCallLimitEnabled(enforceOwnerOnlyAndPhonePermission);
            this.mDataLimitEnabled = dataCallLimitEnabled;
            if (!dataCallLimitEnabled) {
                KnoxsdkFileLog.d(
                        "PhoneRestrictionPolicy",
                        "DataLimitEnabled so restoring data netwotk setting");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                restorePacketDataNetworkSetting();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            getEDM$24().getDeviceInventory().dataUsageTimerActivation();
        }
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setDataCallLimitEnabled",
                Boolean.toString(z),
                Boolean.valueOf(putBoolean));
        return putBoolean;
    }

    public final boolean setDisclaimerText(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setDisclaimerText()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        String disclaimerText = getDisclaimerText(enforceOwnerOnlyAndPhonePermission);
        boolean z = false;
        if (str != null && str.length() > 60) {
            Log.d(
                    "PhoneRestrictionPolicy",
                    ">>> PhoneRestrictionPolicy.setDisclaimerText() was failed - text is over 60"
                        + " char");
            return false;
        }
        if (disclaimerText != null) {
            int adminByField =
                    this.mEdmStorageProvider.getAdminByField(
                            "PHONERESTRICTION", "disclaimerText", disclaimerText);
            int i = enforceOwnerOnlyAndPhonePermission.mCallerUid;
            if (i == adminByField) {
                z =
                        this.mEdmStorageProvider.putString(
                                i, 0, "PHONERESTRICTION", "disclaimerText", str);
            } else {
                Log.d(
                        "PhoneRestrictionPolicy",
                        ">>> PhoneRestrictionPolicy.setDisclaimerText() was failed - other admin"
                            + " already set text");
            }
        } else {
            z =
                    this.mEdmStorageProvider.putString(
                            enforceOwnerOnlyAndPhonePermission.mCallerUid,
                            0,
                            "PHONERESTRICTION",
                            "disclaimerText",
                            str);
        }
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setDisclaimerText",
                str,
                Boolean.valueOf(z));
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0106 A[Catch: Exception -> 0x0101, TRY_LEAVE, TryCatch #0 {Exception -> 0x0101, blocks: (B:61:0x00f6, B:49:0x0106), top: B:60:0x00f6 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setDynamicSimControl(
            com.samsung.android.knox.ContextInfo r12, int r13, boolean r14, int r15) {
        /*
            Method dump skipped, instructions count: 359
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.PhoneRestrictionPolicy.setDynamicSimControl(com.samsung.android.knox.ContextInfo,"
                    + " int, boolean, int):int");
    }

    public final boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "PHONERESTRICTION",
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        z,
                        0,
                        "emergencyCallOnly");
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setEmergencyCallOnly",
                Boolean.toString(z),
                Boolean.valueOf(putBoolean));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse("content://com.sec.knox.provider2/PhoneRestrictionPolicy/isRCSEnabled"));
        return putBoolean;
    }

    public final boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.setIncomingCallExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "incomingCallExceptionPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setIncomingCallExceptionPattern",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        return putString;
    }

    public final boolean setIncomingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setIncomingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "incomingPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setIncomingCallRestriction",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        return putString;
    }

    public final boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.setOutgoingSmsExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "incomingSmsExceptionPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setIncomingSmsExceptionPattern",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        return putString;
    }

    public final boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "smsRestrictionIncomingPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setIncomingSmsRestriction",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        notifySmsPatternCheck();
        return putString;
    }

    public final boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (j < 0 || j2 < 0 || j3 < 0) {
            return false;
        }
        boolean putLong =
                this.mEdmStorageProvider.putLong(i, "PHONERESTRICTION", j3, "dataCallByMonth")
                        & this.mEdmStorageProvider.putLong(
                                i, "PHONERESTRICTION", j, "dataCallByDay")
                        & this.mEdmStorageProvider.putLong(
                                i, "PHONERESTRICTION", j2, "dataCallByWeek");
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setLimitOfDataCalls",
                Long.toString(j) + "," + Long.toString(j2) + "," + Long.toString(j3),
                Boolean.valueOf(putLong));
        return putLong;
    }

    public final boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i4 = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (!this.mTelMgr.isVoiceCapable() || i < 0 || i2 < 0 || i3 < 0) {
            return false;
        }
        boolean z =
                this.mEdmStorageProvider.putInt(i4, 0, i, "PHONERESTRICTION", "incomingCallByDay")
                        && this.mEdmStorageProvider.putInt(
                                i4, 0, i2, "PHONERESTRICTION", "incomingCallByWeek");
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setLimitOfIncomingCalls",
                Long.toString(i) + "," + Long.toString(i2) + "," + Long.toString(i3),
                Boolean.valueOf(z));
        return z
                && this.mEdmStorageProvider.putInt(
                        i4, 0, i3, "PHONERESTRICTION", "incomingCallByMonth");
    }

    public final boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i4 = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (i < 0 || i2 < 0 || i3 < 0) {
            return false;
        }
        boolean z =
                this.mEdmStorageProvider.putInt(i4, 0, i, "PHONERESTRICTION", "incomingSmsByDay")
                        && this.mEdmStorageProvider.putInt(
                                i4, 0, i2, "PHONERESTRICTION", "incomingSmsByWeek");
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setLimitOfIncomingSms",
                Long.toString(i) + "," + Long.toString(i2) + "," + Long.toString(i3),
                Boolean.valueOf(z));
        return z
                && this.mEdmStorageProvider.putInt(
                        i4, 0, i3, "PHONERESTRICTION", "incomingSmsByMonth");
    }

    public final boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i4 = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (!this.mTelMgr.isVoiceCapable() || i < 0 || i2 < 0 || i3 < 0) {
            return false;
        }
        boolean z =
                this.mEdmStorageProvider.putInt(i4, 0, i, "PHONERESTRICTION", "outgoingCallByDay")
                        && this.mEdmStorageProvider.putInt(
                                i4, 0, i2, "PHONERESTRICTION", "outgoingCallByWeek");
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setLimitOfOutgoingCalls",
                Long.toString(i) + "," + Long.toString(i2) + "," + Long.toString(i3),
                Boolean.valueOf(z));
        return z
                && this.mEdmStorageProvider.putInt(
                        i4, 0, i3, "PHONERESTRICTION", "outgoingCallByMonth");
    }

    public final boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i4 = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (i < 0 || i2 < 0 || i3 < 0) {
            return false;
        }
        boolean z =
                this.mEdmStorageProvider.putInt(i4, 0, i, "PHONERESTRICTION", "outgoingSmsByDay")
                        && this.mEdmStorageProvider.putInt(
                                i4, 0, i2, "PHONERESTRICTION", "outgoingSmsByWeek");
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setLimitOfOutgoingSms",
                Long.toString(i) + "," + Long.toString(i2) + "," + Long.toString(i3),
                Boolean.valueOf(z));
        return z
                && this.mEdmStorageProvider.putInt(
                        i4, 0, i3, "PHONERESTRICTION", "outgoingSmsByMonth");
    }

    public final void setLockedIccIdsSystemUI(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList =
                    (ArrayList)
                            this.mSimDbProxy.mEdmStorageProvider.getValues(
                                    "SimTable", new String[] {"SimIccId"}, new ContentValues());
            int size = arrayList.size();
            String[] strArr = new String[size];
            for (int i2 = 0; i2 < size; i2++) {
                strArr[i2] = ((ContentValues) arrayList.get(i2)).getAsString("SimIccId");
            }
            SystemUIAdapter.getInstance(this.mContext).setLockedIccIdsAsUser(i, strArr);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setNetworkState(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z) {
            try {
                try {
                    SemWifiManager semWifiManager =
                            (SemWifiManager) this.mContext.getSystemService("sem_wifi");
                    if (semWifiManager != null) {
                        int wifiApState = semWifiManager.getWifiApState();
                        if (wifiApState == 13 || wifiApState == 12) {
                            semWifiManager.setWifiApEnabled((SoftApConfiguration) null, false);
                        }
                    } else {
                        Log.d("PhoneRestrictionPolicy", "Mobile Hotspot cannot be disabled");
                    }
                } catch (Exception e) {
                    Log.e(
                            "PhoneRestrictionPolicy",
                            String.format(
                                    "setNetworkState(%s, %d) failed ",
                                    Boolean.valueOf(z), Integer.valueOf(i)),
                            e);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        SystemUIAdapter systemUIAdapter = SystemUIAdapter.getInstance(this.mContext);
        systemUIAdapter.setCellularDataAllowedAsUser(i, z);
        systemUIAdapter.setWifiTetheringAllowedAsUser(i, z);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.setOutgoingCallExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "outgoingCallExceptionPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setOutgoingCallExceptionPattern",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        return putString;
    }

    public final boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setOutgoingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "outgoingPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setOutgoingCallRestriction",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        return putString;
    }

    public final boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d(
                "PhoneRestrictionPolicy",
                ">>> PhoneRestrictionPolicy.setOutgoingSmsExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "outgoingSmsExceptionPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setOutgoingSmsExceptionPattern",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        return putString;
    }

    public final boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndPhonePermission =
                enforceOwnerOnlyAndPhonePermission(contextInfo);
        String removeWhiteSpaces = removeWhiteSpaces(str);
        boolean putString =
                this.mEdmStorageProvider.putString(
                        enforceOwnerOnlyAndPhonePermission.mCallerUid,
                        0,
                        "PHONERESTRICTION",
                        "smsRestrictionOutgoingPattern",
                        removeWhiteSpaces);
        logToKnoxsdkFile$2(
                enforceOwnerOnlyAndPhonePermission.mCallerUid,
                "setOutgoingSmsRestriction",
                removeWhiteSpaces,
                Boolean.valueOf(putString));
        notifySmsPatternCheck();
        return putString;
    }

    public final int setRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
        return setRcsEnabledInternal(
                enforceOwnerOnlyAndPhonePermission(contextInfo), i, z, "enableRCS");
    }

    public final int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
        ContextInfo enforceOwnerOnlyAndSimRestrictionPermission =
                enforceOwnerOnlyAndSimRestrictionPermission(contextInfo);
        if (i2 == 0 || i2 == 1) {
            return setRcsEnabledInternal(
                    enforceOwnerOnlyAndSimRestrictionPermission,
                    i,
                    z,
                    i2 == 1 ? "enableRCSForSimSlot1" : "enableRCSForSimSlot0");
        }
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i2,
                "setRCSEnabledBySimSlot(): failed. invalid simSlotId : ",
                "PhoneRestrictionPolicy");
        return -1;
    }

    public final int setRcsEnabledInternal(ContextInfo contextInfo, int i, boolean z, String str) {
        int i2;
        boolean z2;
        Log.i("PhoneRestrictionPolicy", "setRCSEnabledInternal(): " + z + ", columnName: " + str);
        if (i != 1) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i,
                    "setRCSEnabledInternal(): failed. not support feature = ",
                    "PhoneRestrictionPolicy");
            return -1;
        }
        int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        try {
            i2 =
                    this.mEdmStorageProvider.getInt(
                            contextInfo.mCallerUid, 0, "PHONERESTRICTION", str);
        } catch (Exception unused) {
            i2 = 255;
        }
        if (i2 >= 0) {
            i3 = i2;
        }
        try {
            z2 =
                    this.mEdmStorageProvider.putInt(
                            contextInfo.mCallerUid,
                            0,
                            z ? i | i3 : (~i) & i3,
                            "PHONERESTRICTION",
                            str);
        } catch (Exception e) {
            Log.i(
                    "PhoneRestrictionPolicy",
                    "setRCSEnabledInternal(): failed. unexpected exception",
                    e);
            z2 = false;
        }
        if (z2) {
            SecContentProviderUtil.notifyPolicyChangesAllUser(
                    this.mContext,
                    Uri.parse(
                            "content://com.sec.knox.provider2/PhoneRestrictionPolicy/isRCSEnabled"));
        }
        return z2 ? 0 : -2;
    }

    public final int setSubIdLockEnabled(int i, String str, boolean z) {
        Log.d("PhoneRestrictionPolicy", "setSubIdLockEnabled");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mEnterpriseSimPin.setSubIdLockEnabled(i, str, z);
            } catch (IllegalStateException | SecurityException e) {
                Log.e("PhoneRestrictionPolicy", "Failed to setSubIdLockEnabled: " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 100;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}

    public final void updateDataLimitState() {
        enforceSystemUser$2();
        this.mDataLimitEnabled = getDataCallLimitEnabled(null);
    }

    public final void updateDateAndCounters() {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.updateDateAndCounters()");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(
                Long.parseLong(this.mEdmStorageProvider.getGenericValueAsUser(0, "dateDay")));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(
                Long.parseLong(this.mEdmStorageProvider.getGenericValueAsUser(0, "dateWeek")));
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTimeInMillis(
                Long.parseLong(this.mEdmStorageProvider.getGenericValueAsUser(0, "dateMonth")));
        long timeInMillis = calendar.getTimeInMillis();
        if (calendar.after(calendar2)) {
            this.mEdmStorageProvider.putGenericValueAsUser(
                    0, "dateDay", String.valueOf(timeInMillis));
            this.mEdmStorageProvider.putGenericValueAsUser(
                    0, "incomingCallCountDay", String.valueOf(0));
            this.mEdmStorageProvider.putGenericValueAsUser(
                    0, "outgoingCallCountDay", String.valueOf(0));
            if (calendar3.get(3) != calendar.get(3)
                    || (calendar3.get(3) == calendar.get(3)
                            && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dateWeek", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingCallCountWeek", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingCallCountWeek", String.valueOf(0));
            }
            if (calendar4.get(2) != calendar.get(2)
                    || (calendar3.get(2) == calendar.get(2)
                            && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dateMonth", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingCallCountMonth", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingCallCountMonth", String.valueOf(0));
            }
        }
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.updateDateAndCounters() >>>");
    }

    public final void updateDateAndCountersSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SmsRestrictionPolicy.updateDateAndCountersSms()");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(
                Long.parseLong(this.mEdmStorageProvider.getGenericValueAsUser(0, "smsDateDay")));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(
                Long.parseLong(this.mEdmStorageProvider.getGenericValueAsUser(0, "smsDateWeek")));
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTimeInMillis(
                Long.parseLong(this.mEdmStorageProvider.getGenericValueAsUser(0, "smsDateMonth")));
        long timeInMillis = calendar.getTimeInMillis();
        Log.d("PhoneRestrictionPolicy", "current time " + timeInMillis);
        if (calendar.after(calendar2)) {
            this.mEdmStorageProvider.putGenericValueAsUser(
                    0, "smsDateDay", String.valueOf(timeInMillis));
            this.mEdmStorageProvider.putGenericValueAsUser(
                    0, "incomingSmsCountDay", String.valueOf(0));
            this.mEdmStorageProvider.putGenericValueAsUser(
                    0, "outgoingSmsCountDay", String.valueOf(0));
            if (calendar3.get(3) != calendar.get(3)
                    || (calendar3.get(3) == calendar.get(3)
                            && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "smsDateWeek", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingSmsCountWeek", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingSmsCountWeek", String.valueOf(0));
            }
            if (calendar4.get(2) != calendar.get(2)
                    || (calendar3.get(2) == calendar.get(2)
                            && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "smsDateMonth", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "incomingSmsCountMonth", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "outgoingSmsCountMonth", String.valueOf(0));
            }
        }
        Log.d("PhoneRestrictionPolicy", "SmsRestrictionPolicy.updateDateAndCountersSms() >>>");
    }

    public final void updateDateAndDataCallCounters(long j) {
        enforceSystemUser$2();
        if (this.mDataLimitEnabled) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            Calendar calendar2 = Calendar.getInstance();
            try {
                calendar2.setTimeInMillis(
                        Long.parseLong(
                                this.mEdmStorageProvider.getGenericValueAsUser(
                                        0, "dataCallDateDay")));
            } catch (NumberFormatException unused) {
            }
            Calendar calendar3 = Calendar.getInstance();
            try {
                calendar3.setTimeInMillis(
                        Long.parseLong(
                                this.mEdmStorageProvider.getGenericValueAsUser(
                                        0, "dataCallDateWeek")));
            } catch (NumberFormatException unused2) {
            }
            Calendar calendar4 = Calendar.getInstance();
            try {
                calendar4.setTimeInMillis(
                        Long.parseLong(
                                this.mEdmStorageProvider.getGenericValueAsUser(
                                        0, "dataCallDateMonth")));
            } catch (NumberFormatException unused3) {
            }
            long timeInMillis = calendar.getTimeInMillis();
            if (calendar.after(calendar2)) {
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dataCallDateDay", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dataCallBytesCountByDay", String.valueOf(0));
                if (calendar3.get(3) != calendar.get(3)
                        || (calendar3.get(3) == calendar.get(3)
                                && calendar3.get(1) != calendar.get(1))) {
                    this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "dataCallDateWeek", String.valueOf(timeInMillis));
                    this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "dataCallBytesCountByWeek", String.valueOf(0));
                }
                if (calendar4.get(2) != calendar.get(2)
                        || (calendar3.get(2) == calendar.get(2)
                                && calendar3.get(1) != calendar.get(1))) {
                    this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "dataCallDateMonth", String.valueOf(timeInMillis));
                    this.mEdmStorageProvider.putGenericValueAsUser(
                            0, "dataCallByteCountByMonth", String.valueOf(0));
                }
            }
            if (0 < j) {
                dataLimitCounter(j, "dataCallBytesCountByDay");
                dataLimitCounter(j, "dataCallBytesCountByWeek");
                dataLimitCounter(j, "dataCallByteCountByMonth");
            }
            if (!checkDataCallLimit()) {
                restorePacketDataNetworkSetting();
                return;
            }
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mContext.getSystemService("phone");
            if (telephonyManager == null) {
                Log.d("PhoneRestrictionPolicy", "Failed to get Telephony Manager");
            } else if (telephonyManager.getDataEnabled()) {
                this.mDataCheckboxPreviousState = true;
                this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "dataCallPacketDataCheckBox", String.valueOf(true));
                telephonyManager.setDataEnabled(false);
            }
        }
    }
}
