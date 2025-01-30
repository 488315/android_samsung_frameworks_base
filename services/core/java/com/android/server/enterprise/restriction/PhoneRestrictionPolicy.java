package com.android.server.enterprise.restriction;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
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
import android.os.RemoteException;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.HexDump;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
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
import com.samsung.android.knox.restriction.IPhoneRestrictionPolicy;
import com.samsung.android.knox.restriction.ISimPinPolicy;
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

/* loaded from: classes2.dex */
public class PhoneRestrictionPolicy extends IPhoneRestrictionPolicy.Stub implements EnterpriseServiceCallback {
    public final BroadcastReceiver mBroadcastReceiver;
    public final Context mContext;
    public boolean mDataCheckboxPreviousState;
    public boolean mDataLimitEnabled;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final Injector mInjector;
    public volatile boolean mIsPhoneShuttingDown;
    public KeyguardManager mKeyguardManager;
    public final BroadcastReceiver mRcsReceiver;
    public final BroadcastReceiver mReceiver;
    public final SimDBProxy mSimDbProxy;
    public final BroadcastReceiver mSimLockBroadcast;
    public final Runnable mSimPinBind;
    public final Handler mSimPinHandler;
    public ISimPinPolicy mSimPinService;
    public final ServiceConnection mSimPinServiceConn;
    public final SmsMmsDeliveryHandler mSmsMmsDeliveryHandler;
    public SubscriptionManager mSubMgr;
    public final TelephonyManager mTelMgr;
    public final BroadcastReceiver subChangedReceiver;

    /* renamed from: tm */
    public TelephonyManager f1697tm;
    public static final Uri RCS_URI = Uri.parse("content://com.samsung.rcs.im/message");
    public static final String[] RCS_PROJ = {"is_filetransfer", "remote_uri", "sender_alias", "content_type", "body", "inserted_timestamp", "sent_timestamp", "direction", "file_path", "thumbnail_path"};

    public final boolean isRCSFeature(int i) {
        return i == 1;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public EdmStorageProvider getEdmStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }
    }

    public PhoneRestrictionPolicy(Context context) {
        this(new Injector(context));
    }

    public PhoneRestrictionPolicy(Injector injector) {
        this.mEDM = null;
        this.mDataLimitEnabled = false;
        this.mDataCheckboxPreviousState = false;
        this.mSmsMmsDeliveryHandler = new SmsMmsDeliveryHandler();
        this.mIsPhoneShuttingDown = false;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                    PhoneRestrictionPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                }
            }
        };
        this.mReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Bundle extras = intent.getExtras();
                Log.d("PhoneRestrictionPolicy", "RcsReceiver, intent received : " + intent);
                if (action == null || extras == null) {
                    Log.d("PhoneRestrictionPolicy", "No data arrived at mRcsReceiver");
                } else if ("com.samsung.rcs.framework.instantmessaging.action.RECEIVE_SEND_MESSAGE_RESPONSE".equals(action) && !extras.getBoolean("response_status")) {
                    Log.d("PhoneRestrictionPolicy", "rcs message sent fail case, return");
                } else {
                    PhoneRestrictionPolicy.this.sendRcsBroadcast(action, extras);
                }
            }
        };
        this.mRcsReceiver = broadcastReceiver2;
        BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int phoneId = SubscriptionManager.getPhoneId(intent.getIntExtra("subscription", -1));
                if (phoneId == 0 || phoneId == 1) {
                    boolean isDataAllowedFromSimSlot = PhoneRestrictionPolicy.this.isDataAllowedFromSimSlot(phoneId);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (!isDataAllowedFromSimSlot) {
                            try {
                                SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService("sem_wifi");
                                if (semWifiManager != null) {
                                    int wifiApState = semWifiManager.getWifiApState();
                                    if (wifiApState == 13 || wifiApState == 12) {
                                        semWifiManager.setWifiApEnabled((SoftApConfiguration) null, isDataAllowedFromSimSlot);
                                    }
                                } else {
                                    Log.d("PhoneRestrictionPolicy", "Mobile Hotspot cannot be disabled");
                                }
                            } catch (Exception e) {
                                Log.e("PhoneRestrictionPolicy", "setCellularDataAllowed failed ", e);
                            }
                        }
                        SystemUIAdapter systemUIAdapter = SystemUIAdapter.getInstance(PhoneRestrictionPolicy.this.mContext);
                        systemUIAdapter.setCellularDataAllowedAsUser(0, isDataAllowedFromSimSlot);
                        systemUIAdapter.setWifiTetheringAllowedAsUser(0, isDataAllowedFromSimSlot);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        };
        this.subChangedReceiver = broadcastReceiver3;
        BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    Log.d("PhoneRestrictionPolicy", "action is null");
                    return;
                }
                Log.d("PhoneRestrictionPolicy", action);
                if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                    PhoneRestrictionPolicy.this.mSmsMmsDeliveryHandler.sendMessage(Message.obtain(PhoneRestrictionPolicy.this.mSmsMmsDeliveryHandler, 2));
                    PhoneRestrictionPolicy.this.notifyRcsChangesAllUser();
                    PhoneRestrictionPolicy.this.notifySmsPatternCheck();
                    return;
                }
                if (action.equals("android.intent.action.ACTION_SHUTDOWN") || action.equals("android.intent.action.REBOOT")) {
                    PhoneRestrictionPolicy.this.mIsPhoneShuttingDown = true;
                } else if ("com.samsung.android.knox.intent.action.PHONE_READY_INTERNAL".equals(action)) {
                    PhoneRestrictionPolicy.this.mSimPinHandler.postDelayed(PhoneRestrictionPolicy.this.mSimPinBind, 0L);
                }
            }
        };
        this.mBroadcastReceiver = broadcastReceiver4;
        BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("PhoneRestrictionPolicy", action);
                if ("com.samsung.android.knox.intent.action.PHONE_READY_INTERNAL".equals(action) || "android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)) {
                    PhoneRestrictionPolicy.this.setLockedIccIdsSystemUI(context.getUserId());
                } else if ("android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    Log.i("PhoneRestrictionPolicy", "Received ACTION_SIM_STATE_CHANGED broadcast");
                    PhoneRestrictionPolicy.this.unlockAllSimCards();
                }
            }
        };
        this.mSimLockBroadcast = broadcastReceiver5;
        this.mSimPinHandler = new Handler();
        this.mSimPinBind = new Runnable() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.7
            @Override // java.lang.Runnable
            public void run() {
                Log.d("PhoneRestrictionPolicy", "Trying to connect to EnterpriseSimPin Service");
                ComponentName componentName = new ComponentName("com.sec.enterprise.mdm.services.simpin", "com.sec.enterprise.mdm.services.simpin.EnterpriseSimPin");
                PhoneRestrictionPolicy.this.mSimPinHandler.removeCallbacks(this);
                try {
                    PhoneRestrictionPolicy.this.mContext.bindService(new Intent("com.sec.enterprise.SimPinCode").setComponent(componentName), PhoneRestrictionPolicy.this.mSimPinServiceConn, 1);
                } catch (SecurityException unused) {
                    Log.e("PhoneRestrictionPolicy", "Failed to bind Sim Pin Service");
                }
            }
        };
        this.mSimPinServiceConn = new ServiceConnection() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.8
            @Override // android.content.ServiceConnection
            public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PhoneRestrictionPolicy.this.mSimPinService = ISimPinPolicy.Stub.asInterface(iBinder);
                Log.d("PhoneRestrictionPolicy", "SIM Pin Service connected");
                PhoneRestrictionPolicy.this.unlockAllSimCards();
            }

            @Override // android.content.ServiceConnection
            public synchronized void onServiceDisconnected(ComponentName componentName) {
                Log.d("PhoneRestrictionPolicy", "SIM Pin Service has unexpectedly disconnected!");
                PhoneRestrictionPolicy.this.mSimPinService = null;
                PhoneRestrictionPolicy.this.mSimPinHandler.removeCallbacks(PhoneRestrictionPolicy.this.mSimPinBind);
                PhoneRestrictionPolicy.this.mSimPinHandler.postDelayed(PhoneRestrictionPolicy.this.mSimPinBind, 1000L);
            }
        };
        Log.w("PhoneRestrictionPolicy", " >>> PhoneRestrictionPolicy.PhoneRestrictionPolicy()");
        this.mInjector = injector;
        Context context = injector.mContext;
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mEdmStorageProvider = injector.getEdmStorageProvider();
        this.mDataLimitEnabled = getDataCallLimitEnabled(null);
        this.mDataCheckboxPreviousState = getDataCheckboxState();
        this.mTelMgr = (TelephonyManager) context.getSystemService("phone");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.REBOOT");
        intentFilter.addAction("com.samsung.android.knox.intent.action.PHONE_READY_INTERNAL");
        context.registerReceiver(broadcastReceiver4, intentFilter);
        this.mSimDbProxy = SimDBProxy.getInstance(context);
        this.mSubMgr = (SubscriptionManager) context.getSystemService("telephony_subscription_service");
        this.f1697tm = (TelephonyManager) context.getSystemService("phone");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.android.knox.intent.action.PHONE_READY_INTERNAL");
        intentFilter2.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter2.addAction("android.intent.action.SIM_STATE_CHANGED");
        context.registerReceiver(broadcastReceiver5, intentFilter2);
        context.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"));
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addCategory("com.samsung.rcs.framework.instantmessaging.category.ACTION");
        intentFilter3.addAction("com.samsung.rcs.framework.instantmessaging.action.RECEIVE_NEW_MESSAGE");
        intentFilter3.addAction("com.samsung.rcs.framework.instantmessaging.action.RECEIVE_SEND_MESSAGE_RESPONSE");
        intentFilter3.addCategory("com.samsung.rcs.framework.filetransfer.category.NOTIFICATION");
        intentFilter3.addAction("com.samsung.rcs.framework.filetransfer.notification.TRANSFER_INCOMING");
        intentFilter3.addAction("com.samsung.rcs.framework.filetransfer.notification.TRANSFER_COMPLETED");
        context.registerReceiver(broadcastReceiver2, intentFilter3, "com.samsung.rcs.im.READ_PERMISSION", null);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        context.registerReceiver(broadcastReceiver3, intentFilter4);
    }

    public final void sendRcsBroadcast(String str, Bundle bundle) {
        Intent intent = new Intent();
        if ("com.samsung.rcs.framework.instantmessaging.action.RECEIVE_NEW_MESSAGE".equals(str) || "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_SEND_MESSAGE_RESPONSE".equals(str)) {
            intent.putExtra("id", bundle.getLong("message_id", -1L));
            if ("com.samsung.rcs.framework.instantmessaging.action.RECEIVE_NEW_MESSAGE".equals(str)) {
                intent.setAction("com.samsung.android.knox.intent.action.RCS_MSG_TEXT_RECEIVED");
            } else {
                intent.setAction("com.samsung.android.knox.intent.action.RCS_MSG_TEXT_SENT");
            }
        } else if ("com.samsung.rcs.framework.filetransfer.notification.TRANSFER_INCOMING".equals(str) || "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_COMPLETED".equals(str)) {
            intent.putExtra("id", bundle.getLong("sessionId", -1L));
            if ("com.samsung.rcs.framework.filetransfer.notification.TRANSFER_INCOMING".equals(str)) {
                intent.setAction("com.samsung.android.knox.intent.action.RCS_MSG_FILE_THUMBNAIL_RECEIVED");
            } else if (bundle.getInt("sessionDirection", 0) == 0) {
                intent.setAction("com.samsung.android.knox.intent.action.RCS_MSG_FILE_RECEIVED");
            } else {
                intent.setAction("com.samsung.android.knox.intent.action.RCS_MSG_FILE_SENT");
            }
        }
        Log.d("PhoneRestrictionPolicy", "RcsReceiver, sendRcsBroadcast");
        this.mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION");
    }

    public Bundle getRCSMessage(ContextInfo contextInfo, long j) {
        Log.d("PhoneRestrictionPolicy", "getRCS, Start");
        getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
        String[] strArr = {Long.toString(j)};
        Log.d("PhoneRestrictionPolicy", "getRCS, Start query");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Bundle bundle = null;
        try {
            try {
                Cursor query = this.mContext.getContentResolver().query(RCS_URI, RCS_PROJ, "_id=?", strArr, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            Log.d("PhoneRestrictionPolicy", "getRCS, collect data");
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("remote-uri", query.getString(query.getColumnIndex("remote_uri")));
                            bundle2.putString("sender-alias", query.getString(query.getColumnIndex("sender_alias")));
                            bundle2.putString("content-type", query.getString(query.getColumnIndex("content_type")));
                            bundle2.putString("body", query.getString(query.getColumnIndex("body")));
                            if (query.getInt(query.getColumnIndex("direction")) == 0) {
                                bundle2.putLong("timestamp", query.getLong(query.getColumnIndex("inserted_timestamp")));
                            } else {
                                bundle2.putLong("timestamp", query.getLong(query.getColumnIndex("sent_timestamp")));
                            }
                            if (query.getInt(query.getColumnIndex("is_filetransfer")) == 1) {
                                bundle2.putString("file-path", query.getString(query.getColumnIndex("file_path")));
                                bundle2.putString("thumbnail-path", query.getString(query.getColumnIndex("thumbnail_path")));
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

    public final ContextInfo enforceOwnerOnlyAndPhonePermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_PHONE_RESTRICTION")));
    }

    public final ContextInfo enforceOwnerOnlyAndSimRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SIM_RESTRICTION")));
    }

    public final void enforceSystemUser() {
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Can only be called by system user");
        }
    }

    public final void enforcePhoneApp() {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1001) {
            throw new SecurityException("Can only be called by internal phone");
        }
    }

    public final ContextInfo enforcePhoneAppOrOwnerOnlyAndPhonePermission(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        return UserHandle.getAppId(contextInfo.mCallerUid) != 1001 ? enforceOwnerOnlyAndPhonePermission(contextInfo) : contextInfo;
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final String removeWhiteSpaces(String str) {
        return str != null ? str.replaceAll("\\s+", "") : str;
    }

    public String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getOutgoingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, z, "outgoingPattern");
        }
        return null;
    }

    public String getIncomingCallRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getIncomingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, z, "incomingPattern");
        }
        return null;
    }

    public boolean removeOutgoingCallRestriction(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.removeOutgoingCallRestriction()");
        return setOutgoingCallRestriction(contextInfo, "");
    }

    public boolean removeIncomingCallRestriction(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.removeIncomingCallRestriction()");
        return setIncomingCallRestriction(contextInfo, "");
    }

    public boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addOutgoingCallRestriction()");
        if (str == null) {
            return false;
        }
        String outgoingCallRestriction = getOutgoingCallRestriction(contextInfo, false);
        if (outgoingCallRestriction == null || outgoingCallRestriction.isEmpty()) {
            return setOutgoingCallRestriction(contextInfo, str);
        }
        return setOutgoingCallRestriction(contextInfo, outgoingCallRestriction.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean addIncomingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addIncomingCallRestriction()");
        if (str == null) {
            return false;
        }
        String incomingCallRestriction = getIncomingCallRestriction(contextInfo, false);
        if (incomingCallRestriction == null || incomingCallRestriction.isEmpty()) {
            return setIncomingCallRestriction(contextInfo, str);
        }
        return setIncomingCallRestriction(contextInfo, incomingCallRestriction.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setOutgoingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "outgoingPattern", removeWhiteSpaces(str));
    }

    public boolean setIncomingCallRestriction(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setIncomingCallRestriction()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "incomingPattern", removeWhiteSpaces(str));
    }

    public boolean canOutgoingCall(String str) {
        boolean z;
        int limitOfOutgoingCalls;
        if (str == null || !this.mTelMgr.isVoiceCapable()) {
            return true;
        }
        boolean isNumberAllowed = isNumberAllowed(str, "outgoingPattern", "outgoingCallExceptionPattern");
        if (isLimitNumberOfCallsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                updateDateAndCounters();
                int limitOfOutgoingCalls2 = getLimitOfOutgoingCalls(null, 0);
                if ((Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingCallCountDay")) < limitOfOutgoingCalls2 || limitOfOutgoingCalls2 < 1) && (Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingCallCountWeek")) < (limitOfOutgoingCalls = getLimitOfOutgoingCalls(null, 1)) || limitOfOutgoingCalls < 1)) {
                    int limitOfOutgoingCalls3 = getLimitOfOutgoingCalls(null, 2);
                    if (Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingCallCountMonth")) < limitOfOutgoingCalls3 || limitOfOutgoingCalls3 < 1) {
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
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.canOutgoingCall >>>> " + z2);
        return z2;
    }

    public boolean canIncomingCall(String str) {
        boolean z;
        int limitOfIncomingCalls;
        if (!this.mTelMgr.isVoiceCapable() || str == null) {
            return true;
        }
        if (this.mTelMgr.isNetworkRoaming() && !getEDM().getRoamingPolicy().isRoamingVoiceCallsEnabled()) {
            return false;
        }
        boolean isNumberAllowed = isNumberAllowed(str, "incomingPattern", "incomingCallExceptionPattern");
        if (isLimitNumberOfCallsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                updateDateAndCounters();
                int limitOfIncomingCalls2 = getLimitOfIncomingCalls(null, 0);
                if ((Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingCallCountDay")) < limitOfIncomingCalls2 || limitOfIncomingCalls2 < 1) && (Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingCallCountWeek")) < (limitOfIncomingCalls = getLimitOfIncomingCalls(null, 1)) || limitOfIncomingCalls < 1)) {
                    int limitOfIncomingCalls3 = getLimitOfIncomingCalls(null, 2);
                    if (Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingCallCountMonth")) < limitOfIncomingCalls3 || limitOfIncomingCalls3 < 1) {
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
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.canIncomingCall >>>>" + z2);
        return z2;
    }

    public final boolean isNumberAllowed(String str, String str2, String str3) {
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("PHONERESTRICTION", new String[]{"adminUid", str2});
        if (valuesList != null && !valuesList.isEmpty()) {
            for (ContentValues contentValues : valuesList) {
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
                                String string = this.mEdmStorageProvider.getString(asInteger.intValue(), "PHONERESTRICTION", str3);
                                if (TextUtils.isEmpty(string)) {
                                    return false;
                                }
                                try {
                                    if (!Pattern.compile(string).matcher(str).matches()) {
                                        return false;
                                    }
                                } catch (PatternSyntaxException unused) {
                                    Log.e("PhoneRestrictionPolicy", "failed to compile pattern for exception");
                                    return false;
                                }
                            } else {
                                continue;
                            }
                        } catch (PatternSyntaxException unused2) {
                            Log.e("PhoneRestrictionPolicy", "failed to compile pattern for restriction");
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "emergencyCallOnly", z);
        notifyRcsChangesAllUser();
        return putBoolean;
    }

    public boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
        boolean z2 = false;
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        try {
            if (!z) {
                int callingUserId = UserHandle.getCallingUserId();
                if (contextInfo == null) {
                    contextInfo = new ContextInfo(Binder.getCallingUid());
                }
                if (contextInfo.mParent) {
                    return this.mEdmStorageProvider.getBoolean(EnterpriseDeviceManagerService.getInstance().getPseudoAdminUid(), "PHONERESTRICTION", "emergencyCallOnly");
                }
                if (callingUserId != 0 || UserHandle.getUserId(contextInfo.mCallerUid) != 0 || contextInfo.mContainerId > 0) {
                    throw new SecurityException("Operation supported only on owner space");
                }
                z2 = this.mEdmStorageProvider.getBoolean(contextInfo.mCallerUid, "PHONERESTRICTION", "emergencyCallOnly");
            } else {
                Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "emergencyCallOnly").iterator();
                while (it.hasNext()) {
                    if (((Boolean) it.next()).booleanValue()) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        Log.e("PhoneRestrictionPolicy", "getEmergencyCall >>" + z2);
        return z2;
    }

    public final String getRestrictionPattern(ContextInfo contextInfo, boolean z, String str) {
        if (!z) {
            return this.mEdmStorageProvider.getString(enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid, "PHONERESTRICTION", str);
        }
        String str2 = "";
        for (String str3 : this.mEdmStorageProvider.getStringList("PHONERESTRICTION", str)) {
            if (!TextUtils.isEmpty(str3)) {
                str2 = str2.concat(str3).concat("|");
            }
        }
        if (str2.endsWith("|")) {
            return str2.substring(0, str2.length() - 1);
        }
        return null;
    }

    public String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, z, "smsRestrictionOutgoingPattern");
        }
        return null;
    }

    public String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, z, "smsRestrictionIncomingPattern");
        }
        return null;
    }

    public boolean removeOutgoingSmsRestriction(ContextInfo contextInfo) {
        return setOutgoingSmsRestriction(contextInfo, "");
    }

    public boolean removeIncomingSmsRestriction(ContextInfo contextInfo) {
        return setIncomingSmsRestriction(contextInfo, "");
    }

    public boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        String restrictionPattern = getRestrictionPattern(contextInfo, false, "smsRestrictionOutgoingPattern");
        if (restrictionPattern == null || restrictionPattern.isEmpty()) {
            return setOutgoingSmsRestriction(contextInfo, str);
        }
        return setOutgoingSmsRestriction(contextInfo, restrictionPattern.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        String restrictionPattern = getRestrictionPattern(contextInfo, false, "smsRestrictionIncomingPattern");
        if (restrictionPattern == null || restrictionPattern.isEmpty()) {
            return setIncomingSmsRestriction(contextInfo, str);
        }
        return setIncomingSmsRestriction(contextInfo, restrictionPattern.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        boolean putString = this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "smsRestrictionOutgoingPattern", removeWhiteSpaces(str));
        notifySmsPatternCheck();
        return putString;
    }

    public boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        boolean putString = this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "smsRestrictionIncomingPattern", removeWhiteSpaces(str));
        notifySmsPatternCheck();
        return putString;
    }

    public boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Log.d("PhoneRestrictionPolicy", " >>>> enableLimitNumberOfSMS ");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (z && !isLimitNumberOfSmsEnabled(enforceOwnerOnlyAndPhonePermission)) {
            resetSmsCount(enforceOwnerOnlyAndPhonePermission);
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            long timeInMillis = calendar.getTimeInMillis();
            if (!(this.mEdmStorageProvider.putGenericValue("smsDateDay", String.valueOf(timeInMillis)) && this.mEdmStorageProvider.putGenericValue("smsDateWeek", String.valueOf(timeInMillis))) || !this.mEdmStorageProvider.putGenericValue("smsDateMonth", String.valueOf(timeInMillis))) {
                z2 = false;
                Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfSMS  >>>>>");
                return z2 && this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "limitSmsEnable", z);
            }
        }
        z2 = true;
        Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfSMS  >>>>>");
        if (z2) {
            return false;
        }
    }

    public boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo) {
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "limitSmsEnable");
        if (booleanList.isEmpty()) {
            return false;
        }
        Iterator it = booleanList.iterator();
        while (it.hasNext()) {
            if (((Boolean) it.next()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean resetSmsCount(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.putGenericValue("outgoingSmsCountMonth", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("incomingSmsCountDay", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("outgoingSmsCountDay", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("incomingSmsCountWeek", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("outgoingSmsCountWeek", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("incomingSmsCountMonth", String.valueOf(0));
    }

    public boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3) {
        int i4 = enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid;
        if (i < 0 || i2 < 0 || i3 < 0) {
            return false;
        }
        return (this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "incomingSmsByDay", i) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "incomingSmsByWeek", i2)) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "incomingSmsByMonth", i3);
    }

    public int getLimitOfIncomingSms(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intList;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        if (i == 0) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "incomingSmsByDay");
        } else if (i == 1) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "incomingSmsByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "incomingSmsByMonth");
        }
        int i2 = 0;
        if (intList != null && !intList.isEmpty()) {
            for (Integer num : intList) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3) {
        int i4 = enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid;
        if (i < 0 || i2 < 0 || i3 < 0) {
            return false;
        }
        return (this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "outgoingSmsByDay", i) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "outgoingSmsByWeek", i2)) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "outgoingSmsByMonth", i3);
    }

    public int getLimitOfOutgoingSms(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intList;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        if (i == 0) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "outgoingSmsByDay");
        } else if (i == 1) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "outgoingSmsByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "outgoingSmsByMonth");
        }
        int i2 = 0;
        if (intList != null && !intList.isEmpty()) {
            for (Integer num : intList) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public boolean addNumberOfIncomingSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SMSRestrictionPolicy.addNumberOfIncomingSMS()");
        enforceSms();
        if (!isLimitNumberOfSmsEnabled(null)) {
            return false;
        }
        boolean putGenericValue = this.mEdmStorageProvider.putGenericValue("incomingSmsCountMonth", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingSmsCountMonth")) + 1)) & this.mEdmStorageProvider.putGenericValue("incomingSmsCountDay", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingSmsCountDay")) + 1)) & this.mEdmStorageProvider.putGenericValue("incomingSmsCountWeek", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingSmsCountWeek")) + 1));
        Log.d("PhoneRestrictionPolicy", "SMSRestrictionPolicy.addNumberOfIncomingSMS() >>>");
        return putGenericValue;
    }

    public boolean addNumberOfOutgoingSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SmsRestrictionPolicy.addNumberOfOutgoingSms()");
        enforceSms();
        if (!isLimitNumberOfSmsEnabled(null)) {
            return false;
        }
        boolean putGenericValue = this.mEdmStorageProvider.putGenericValue("outgoingSmsCountMonth", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountMonth")) + 1)) & this.mEdmStorageProvider.putGenericValue("outgoingSmsCountDay", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountDay")) + 1)) & this.mEdmStorageProvider.putGenericValue("outgoingSmsCountWeek", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountWeek")) + 1));
        Log.d("PhoneRestrictionPolicy", "SmsRestrictionPolicy.addNumberOfOutgoingSms >>>");
        return putGenericValue;
    }

    public boolean decreaseNumberOfOutgoingSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SmsRestrictionPolicy.decreaseNumberOfOutgoingSms()");
        enforceSms();
        if (!isLimitNumberOfSmsEnabled(null)) {
            return false;
        }
        boolean putGenericValue = this.mEdmStorageProvider.putGenericValue("outgoingSmsCountMonth", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountMonth")) - 1)) & this.mEdmStorageProvider.putGenericValue("outgoingSmsCountDay", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountDay")) - 1)) & this.mEdmStorageProvider.putGenericValue("outgoingSmsCountWeek", String.valueOf(Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountWeek")) - 1));
        Log.d("PhoneRestrictionPolicy", "SmsRestrictionPolicy.addNumberOfOutgoingSms >>>");
        return putGenericValue;
    }

    public final void enforceSms() {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1001) {
            throw new SecurityException("Can only receive SMS by internal phone");
        }
    }

    public boolean canIncomingSms(String str) {
        boolean z;
        int limitOfIncomingSms;
        int limitOfIncomingSms2;
        if (str == null) {
            return true;
        }
        boolean isNumberAllowed = isNumberAllowed(str, "smsRestrictionIncomingPattern", "incomingSmsExceptionPattern");
        if (isLimitNumberOfSmsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d("PhoneRestrictionPolicy", "Limit of sms is enabled!!!");
                updateDateAndCountersSms();
                int limitOfIncomingSms3 = getLimitOfIncomingSms(null, 0);
                Log.d("PhoneRestrictionPolicy", "canIncomingSms - limitDay = " + limitOfIncomingSms3);
                int parseInt = Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingSmsCountDay"));
                Log.d("PhoneRestrictionPolicy", "canIncomingSms - countDay = " + parseInt);
                if ((parseInt >= limitOfIncomingSms3 && limitOfIncomingSms3 >= 1) || ((Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingSmsCountWeek")) >= (limitOfIncomingSms = getLimitOfIncomingSms(null, 1)) && limitOfIncomingSms >= 1) || (Integer.parseInt(this.mEdmStorageProvider.getGenericValue("incomingSmsCountMonth")) >= (limitOfIncomingSms2 = getLimitOfIncomingSms(null, 2)) && limitOfIncomingSms2 >= 1))) {
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

    public boolean canOutgoingSms(String str) {
        boolean z;
        int limitOfOutgoingSms;
        int limitOfOutgoingSms2;
        if (str == null) {
            return true;
        }
        boolean isNumberAllowed = isNumberAllowed(str, "smsRestrictionOutgoingPattern", "outgoingSmsExceptionPattern");
        if (isLimitNumberOfSmsEnabled(null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d("PhoneRestrictionPolicy", "Limit of sms is enabled!!!");
                updateDateAndCountersSms();
                int limitOfOutgoingSms3 = getLimitOfOutgoingSms(null, 0);
                Log.d("PhoneRestrictionPolicy", "canOutgoingSms - limitDay = " + limitOfOutgoingSms3);
                int parseInt = Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountDay"));
                Log.d("PhoneRestrictionPolicy", "canOutgoingSms - countDay = " + parseInt);
                if ((parseInt >= limitOfOutgoingSms3 && limitOfOutgoingSms3 >= 1) || ((Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountWeek")) >= (limitOfOutgoingSms = getLimitOfOutgoingSms(null, 1)) && limitOfOutgoingSms >= 1) || (Integer.parseInt(this.mEdmStorageProvider.getGenericValue("outgoingSmsCountMonth")) >= (limitOfOutgoingSms2 = getLimitOfOutgoingSms(null, 2)) && limitOfOutgoingSms2 >= 1))) {
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
        Log.d("PhoneRestrictionPolicy", "SmsRestrictionPolicy.canOutgoingSms >>>>" + isNumberAllowed + " >>> " + z);
        return isNumberAllowed && z;
    }

    public final void updateDateAndCountersSms() {
        Log.d("PhoneRestrictionPolicy", ">>> SmsRestrictionPolicy.updateDateAndCountersSms()");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("smsDateDay")));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("smsDateWeek")));
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("smsDateMonth")));
        long timeInMillis = calendar.getTimeInMillis();
        Log.d("PhoneRestrictionPolicy", "current time " + timeInMillis);
        if (calendar.after(calendar2)) {
            this.mEdmStorageProvider.putGenericValue("smsDateDay", String.valueOf(timeInMillis));
            this.mEdmStorageProvider.putGenericValue("incomingSmsCountDay", String.valueOf(0));
            this.mEdmStorageProvider.putGenericValue("outgoingSmsCountDay", String.valueOf(0));
            if (calendar3.get(3) != calendar.get(3) || (calendar3.get(3) == calendar.get(3) && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValue("smsDateWeek", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValue("incomingSmsCountWeek", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValue("outgoingSmsCountWeek", String.valueOf(0));
            }
            if (calendar4.get(2) != calendar.get(2) || (calendar3.get(2) == calendar.get(2) && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValue("smsDateMonth", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValue("incomingSmsCountMonth", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValue("outgoingSmsCountMonth", String.valueOf(0));
            }
        }
        Log.d("PhoneRestrictionPolicy", "SmsRestrictionPolicy.updateDateAndCountersSms() >>>");
    }

    public boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Log.d("PhoneRestrictionPolicy", " >>>> enableLimitNumberOfCalls ");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
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
            if (!(this.mEdmStorageProvider.putGenericValue("dateDay", String.valueOf(timeInMillis)) && this.mEdmStorageProvider.putGenericValue("dateWeek", String.valueOf(timeInMillis))) || !this.mEdmStorageProvider.putGenericValue("dateMonth", String.valueOf(timeInMillis))) {
                z2 = false;
                Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfCalls  >>>>>");
                return z2 && this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "limitCallEnable", z);
            }
        }
        z2 = true;
        Log.d("PhoneRestrictionPolicy", "enableLimitNumberOfCalls  >>>>>");
        if (z2) {
            return false;
        }
    }

    public boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo) {
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "limitCallEnable");
        if (!booleanList.isEmpty()) {
            Iterator it = booleanList.iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
        int i4 = enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid;
        if (this.mTelMgr.isVoiceCapable() && i >= 0 && i2 >= 0 && i3 >= 0) {
            return (this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "incomingCallByDay", i) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "incomingCallByWeek", i2)) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "incomingCallByMonth", i3);
        }
        return false;
    }

    public int getLimitOfIncomingCalls(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intList;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return -1;
        }
        if (i == 0) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "incomingCallByDay");
        } else if (i == 1) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "incomingCallByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "incomingCallByMonth");
        }
        int i2 = 0;
        if (intList != null && !intList.isEmpty()) {
            for (Integer num : intList) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
        int i4 = enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid;
        if (this.mTelMgr.isVoiceCapable() && i >= 0 && i2 >= 0 && i3 >= 0) {
            return (this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "outgoingCallByDay", i) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "outgoingCallByWeek", i2)) && this.mEdmStorageProvider.putInt(i4, "PHONERESTRICTION", "outgoingCallByMonth", i3);
        }
        return false;
    }

    public int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i) {
        ArrayList<Integer> intList;
        enforcePhoneAppOrOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return -1;
        }
        if (i == 0) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "outgoingCallByDay");
        } else if (i == 1) {
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "outgoingCallByWeek");
        } else {
            if (i != 2) {
                return -1;
            }
            intList = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", "outgoingCallByMonth");
        }
        int i2 = 0;
        if (intList != null && !intList.isEmpty()) {
            for (Integer num : intList) {
                if (num.intValue() != 0 && (num.intValue() < i2 || i2 == 0)) {
                    i2 = num.intValue();
                }
            }
        }
        return i2;
    }

    public boolean addNumberOfIncomingCalls() {
        int i;
        int i2;
        int i3;
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addNumberOfIncomingCalls()");
        enforcePhoneApp();
        int i4 = 0;
        if (!this.mTelMgr.isVoiceCapable() || !isLimitNumberOfCallsEnabled(null)) {
            return false;
        }
        String genericValue = this.mEdmStorageProvider.getGenericValue("incomingCallCountDay");
        String genericValue2 = this.mEdmStorageProvider.getGenericValue("incomingCallCountWeek");
        String genericValue3 = this.mEdmStorageProvider.getGenericValue("incomingCallCountMonth");
        if (genericValue == null || genericValue2 == null || genericValue3 == null) {
            i = 0;
            i2 = 0;
        } else {
            try {
                i3 = Integer.parseInt(genericValue);
                try {
                    i2 = Integer.parseInt(genericValue2);
                } catch (NumberFormatException unused) {
                    i2 = 0;
                }
            } catch (NumberFormatException unused2) {
                i3 = 0;
                i2 = 0;
            }
            try {
                i4 = Integer.parseInt(genericValue3);
            } catch (NumberFormatException unused3) {
                Log.e("PhoneRestrictionPolicy", "addNumberOfIncomingCalls - exception");
                int i5 = i4;
                i4 = i3;
                i = i5;
                boolean putGenericValue = this.mEdmStorageProvider.putGenericValue("incomingCallCountMonth", String.valueOf(i + 1)) & this.mEdmStorageProvider.putGenericValue("incomingCallCountDay", String.valueOf(i4 + 1)) & this.mEdmStorageProvider.putGenericValue("incomingCallCountWeek", String.valueOf(i2 + 1));
                Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.addNumberOfIncomingCalls() >>>");
                return putGenericValue;
            }
            int i52 = i4;
            i4 = i3;
            i = i52;
        }
        boolean putGenericValue2 = this.mEdmStorageProvider.putGenericValue("incomingCallCountMonth", String.valueOf(i + 1)) & this.mEdmStorageProvider.putGenericValue("incomingCallCountDay", String.valueOf(i4 + 1)) & this.mEdmStorageProvider.putGenericValue("incomingCallCountWeek", String.valueOf(i2 + 1));
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.addNumberOfIncomingCalls() >>>");
        return putGenericValue2;
    }

    public boolean addNumberOfOutgoingCalls() {
        int i;
        int i2;
        int i3;
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addNumberOfOutgoingCalls()");
        enforcePhoneApp();
        int i4 = 0;
        if (!this.mTelMgr.isVoiceCapable() || !isLimitNumberOfCallsEnabled(null)) {
            return false;
        }
        String genericValue = this.mEdmStorageProvider.getGenericValue("outgoingCallCountDay");
        String genericValue2 = this.mEdmStorageProvider.getGenericValue("outgoingCallCountWeek");
        String genericValue3 = this.mEdmStorageProvider.getGenericValue("outgoingCallCountMonth");
        if (genericValue == null || genericValue2 == null || genericValue3 == null) {
            i = 0;
            i2 = 0;
        } else {
            try {
                i3 = Integer.parseInt(genericValue);
                try {
                    i2 = Integer.parseInt(genericValue2);
                    try {
                        i4 = Integer.parseInt(genericValue3);
                    } catch (NumberFormatException unused) {
                        Log.e("PhoneRestrictionPolicy", "addNumberOfOutgoingCalls - exception");
                        int i5 = i4;
                        i4 = i3;
                        i = i5;
                        boolean putGenericValue = this.mEdmStorageProvider.putGenericValue("outgoingCallCountMonth", String.valueOf(i + 1)) & this.mEdmStorageProvider.putGenericValue("outgoingCallCountDay", String.valueOf(i4 + 1)) & this.mEdmStorageProvider.putGenericValue("outgoingCallCountWeek", String.valueOf(i2 + 1));
                        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.addNumberOfOutgoingCalls >>>");
                        return putGenericValue;
                    }
                } catch (NumberFormatException unused2) {
                    i2 = 0;
                }
            } catch (NumberFormatException unused3) {
                i3 = 0;
                i2 = 0;
            }
            int i52 = i4;
            i4 = i3;
            i = i52;
        }
        boolean putGenericValue2 = this.mEdmStorageProvider.putGenericValue("outgoingCallCountMonth", String.valueOf(i + 1)) & this.mEdmStorageProvider.putGenericValue("outgoingCallCountDay", String.valueOf(i4 + 1)) & this.mEdmStorageProvider.putGenericValue("outgoingCallCountWeek", String.valueOf(i2 + 1));
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.addNumberOfOutgoingCalls >>>");
        return putGenericValue2;
    }

    public boolean resetCallsCount(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putGenericValue("outgoingCallCountMonth", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("incomingCallCountDay", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("outgoingCallCountDay", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("incomingCallCountWeek", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("outgoingCallCountWeek", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("incomingCallCountMonth", String.valueOf(0));
    }

    public final void updateDateAndCounters() {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.updateDateAndCounters()");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("dateDay")));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("dateWeek")));
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("dateMonth")));
        long timeInMillis = calendar.getTimeInMillis();
        if (calendar.after(calendar2)) {
            this.mEdmStorageProvider.putGenericValue("dateDay", String.valueOf(timeInMillis));
            this.mEdmStorageProvider.putGenericValue("incomingCallCountDay", String.valueOf(0));
            this.mEdmStorageProvider.putGenericValue("outgoingCallCountDay", String.valueOf(0));
            if (calendar3.get(3) != calendar.get(3) || (calendar3.get(3) == calendar.get(3) && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValue("dateWeek", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValue("incomingCallCountWeek", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValue("outgoingCallCountWeek", String.valueOf(0));
            }
            if (calendar4.get(2) != calendar.get(2) || (calendar3.get(2) == calendar.get(2) && calendar3.get(1) != calendar.get(1))) {
                this.mEdmStorageProvider.putGenericValue("dateMonth", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValue("incomingCallCountMonth", String.valueOf(0));
                this.mEdmStorageProvider.putGenericValue("outgoingCallCountMonth", String.valueOf(0));
            }
        }
        Log.d("PhoneRestrictionPolicy", "PhoneRestrictionPolicy.updateDateAndCounters() >>>");
    }

    public boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        boolean z2 = true;
        if (z && !this.mDataLimitEnabled) {
            resetDataCallLimitCounter(enforceOwnerOnlyAndPhonePermission);
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            long timeInMillis = calendar.getTimeInMillis();
            z2 = true & this.mEdmStorageProvider.putGenericValue("dataCallDateDay", String.valueOf(timeInMillis)) & this.mEdmStorageProvider.putGenericValue("dataCallDateWeek", String.valueOf(timeInMillis)) & this.mEdmStorageProvider.putGenericValue("dataCallDateMonth", String.valueOf(timeInMillis));
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "enableLimitDataCall", z) & z2;
        if (putBoolean) {
            boolean dataCallLimitEnabled = getDataCallLimitEnabled(enforceOwnerOnlyAndPhonePermission);
            this.mDataLimitEnabled = dataCallLimitEnabled;
            if (!dataCallLimitEnabled) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                restorePacketDataNetworkSetting();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            getEDM().getDeviceInventory().dataUsageTimerActivation();
        }
        return putBoolean;
    }

    public boolean getDataCallLimitEnabled(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "enableLimitDataCall").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3) {
        int i = enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid;
        if (j < 0 || j2 < 0 || j3 < 0) {
            return false;
        }
        return this.mEdmStorageProvider.putLong(i, "PHONERESTRICTION", "dataCallByMonth", j3) & this.mEdmStorageProvider.putLong(i, "PHONERESTRICTION", "dataCallByDay", j) & this.mEdmStorageProvider.putLong(i, "PHONERESTRICTION", "dataCallByWeek", j2);
    }

    public long getLimitOfDataCalls(ContextInfo contextInfo, int i) {
        ArrayList longList;
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (i == 0) {
            longList = this.mEdmStorageProvider.getLongList("PHONERESTRICTION", "dataCallByDay");
        } else if (i == 1) {
            longList = this.mEdmStorageProvider.getLongList("PHONERESTRICTION", "dataCallByWeek");
        } else {
            if (i != 2) {
                return -1L;
            }
            longList = this.mEdmStorageProvider.getLongList("PHONERESTRICTION", "dataCallByMonth");
        }
        if (longList == null || longList.isEmpty()) {
            return 0L;
        }
        Iterator it = longList.iterator();
        long j = 0;
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            if (longValue != 0 && (longValue < j || j == 0)) {
                j = longValue;
            }
        }
        return j;
    }

    public boolean resetDataCallLimitCounter(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.putGenericValue("dataCallByteCountByMonth", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("dataCallBytesCountByDay", String.valueOf(0)) & this.mEdmStorageProvider.putGenericValue("dataCallBytesCountByWeek", String.valueOf(0));
    }

    public boolean checkEnableUseOfPacketData(boolean z) {
        boolean z2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        updateDateAndDataCallCounters(0L);
        if (checkDataCallLimit() || !getEDM().getRestrictionPolicy().isCellularDataAllowed()) {
            if (z) {
                RestrictionToastManager.show(R.string.face_name_template);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public boolean checkDataCallLimit() {
        long j;
        long j2;
        long j3;
        enforceSystemUser();
        if (!this.mDataLimitEnabled) {
            return false;
        }
        long limitOfDataCalls = getLimitOfDataCalls(null, 0);
        long limitOfDataCalls2 = getLimitOfDataCalls(null, 1);
        long limitOfDataCalls3 = getLimitOfDataCalls(null, 2);
        try {
            j = Long.parseLong(this.mEdmStorageProvider.getGenericValue("dataCallBytesCountByDay"));
        } catch (NumberFormatException unused) {
            Log.d("PhoneRestrictionPolicy", "Could not read from Storage");
            j = 0;
        }
        try {
            j2 = Long.parseLong(this.mEdmStorageProvider.getGenericValue("dataCallBytesCountByWeek"));
        } catch (NumberFormatException unused2) {
            Log.d("PhoneRestrictionPolicy", "Could not read from Storage");
            j2 = 0;
        }
        try {
            j3 = Long.parseLong(this.mEdmStorageProvider.getGenericValue("dataCallByteCountByMonth"));
        } catch (NumberFormatException unused3) {
            Log.d("PhoneRestrictionPolicy", "Could not read from Storage");
            j3 = 0;
        }
        return (0 < limitOfDataCalls && j > limitOfDataCalls) || (0 < limitOfDataCalls2 && j2 > limitOfDataCalls2) || (0 < limitOfDataCalls3 && j3 > limitOfDataCalls3);
    }

    public void updateDateAndDataCallCounters(long j) {
        String str;
        enforceSystemUser();
        if (this.mDataLimitEnabled) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            Calendar calendar2 = Calendar.getInstance();
            try {
                calendar2.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("dataCallDateDay")));
            } catch (NumberFormatException unused) {
            }
            Calendar calendar3 = Calendar.getInstance();
            try {
                calendar3.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("dataCallDateWeek")));
            } catch (NumberFormatException unused2) {
            }
            Calendar calendar4 = Calendar.getInstance();
            try {
                calendar4.setTimeInMillis(Long.parseLong(this.mEdmStorageProvider.getGenericValue("dataCallDateMonth")));
            } catch (NumberFormatException unused3) {
            }
            long timeInMillis = calendar.getTimeInMillis();
            if (calendar.after(calendar2)) {
                this.mEdmStorageProvider.putGenericValue("dataCallDateDay", String.valueOf(timeInMillis));
                this.mEdmStorageProvider.putGenericValue("dataCallBytesCountByDay", String.valueOf(0));
                str = "dataCallBytesCountByDay";
                if (calendar3.get(3) != calendar.get(3) || (calendar3.get(3) == calendar.get(3) && calendar3.get(1) != calendar.get(1))) {
                    this.mEdmStorageProvider.putGenericValue("dataCallDateWeek", String.valueOf(timeInMillis));
                    this.mEdmStorageProvider.putGenericValue("dataCallBytesCountByWeek", String.valueOf(0));
                }
                if (calendar4.get(2) != calendar.get(2) || (calendar3.get(2) == calendar.get(2) && calendar3.get(1) != calendar.get(1))) {
                    this.mEdmStorageProvider.putGenericValue("dataCallDateMonth", String.valueOf(timeInMillis));
                    this.mEdmStorageProvider.putGenericValue("dataCallByteCountByMonth", String.valueOf(0));
                }
            } else {
                str = "dataCallBytesCountByDay";
            }
            if (0 < j) {
                dataLimitCounter(str, j);
                dataLimitCounter("dataCallBytesCountByWeek", j);
                dataLimitCounter("dataCallByteCountByMonth", j);
            }
            if (checkDataCallLimit()) {
                blockDataNetwork();
            } else {
                restorePacketDataNetworkSetting();
            }
        }
    }

    public final void dataLimitCounter(String str, long j) {
        long j2;
        try {
            j2 = Long.parseLong(this.mEdmStorageProvider.getGenericValue(str));
        } catch (NumberFormatException unused) {
            j2 = 0;
        }
        this.mEdmStorageProvider.putGenericValue(str, Long.toString(j2 + j));
    }

    public final boolean blockDataNetwork() {
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        if (telephonyManager == null) {
            Log.d("PhoneRestrictionPolicy", "Failed to get Telephony Manager");
            return false;
        }
        if (telephonyManager.getDataEnabled()) {
            this.mDataCheckboxPreviousState = true;
            putDataCheckboxState(true);
            telephonyManager.setDataEnabled(false);
        }
        return true;
    }

    public final boolean restorePacketDataNetworkSetting() {
        if (this.mDataCheckboxPreviousState && getEDM().getRestrictionPolicy().isCellularDataAllowed()) {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            if (telephonyManager == null) {
                Log.d("PhoneRestrictionPolicy", "Failed to get Telephony Manager");
                return false;
            }
            if (!telephonyManager.getDataEnabled()) {
                telephonyManager.setDataEnabled(true);
            }
            this.mDataCheckboxPreviousState = false;
            putDataCheckboxState(false);
        }
        return true;
    }

    public final boolean getDataCheckboxState() {
        return Boolean.parseBoolean(this.mEdmStorageProvider.getGenericValue("dataCallPacketDataCheckBox"));
    }

    public final boolean putDataCheckboxState(boolean z) {
        return this.mEdmStorageProvider.putGenericValue("dataCallPacketDataCheckBox", String.valueOf(z));
    }

    public void updateDataLimitState() {
        enforceSystemUser();
        this.mDataLimitEnabled = getDataCallLimitEnabled(null);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        updateDataLimitState();
        this.mSmsMmsDeliveryHandler.sendMessage(Message.obtain(this.mSmsMmsDeliveryHandler, 2));
        this.mSimDbProxy.removeSimcardsByAdmin(i);
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
        }
        notifyRcsChangesAllUser();
    }

    public boolean allowIncomingSms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid, "PHONERESTRICTION", "allowIncomingSms", z);
    }

    public boolean allowOutgoingSms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid, "PHONERESTRICTION", "allowOutgoingSms", z);
    }

    public boolean isCopyContactToSimAllowed(ContextInfo contextInfo) {
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "copyContactToSimEnabled").iterator();
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

    public boolean isCopyContactToSimAllowed(int i) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "copyContactToSimEnabled").iterator();
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
        if (!z) {
            if (i == 1) {
                Log.d("PhoneRestrictionPolicy", "Access to PB ADD ");
                RestrictionToastManager.show(R.string.biometric_error_canceled);
            } else if (i == 3) {
                Log.d("PhoneRestrictionPolicy", "Access to PB Edit ");
                RestrictionToastManager.show(R.string.importance_from_person);
            } else {
                Log.d("PhoneRestrictionPolicy", "isCopyContactToSimAllowed wrong message value: " + i);
            }
        }
        return z;
    }

    public boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z) {
        int i = enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(i, "PHONERESTRICTION", "copyContactToSimEnabled", z);
        Uri parse = Uri.parse("content://com.sec.knox.provider2/PhoneRestrictionPolicy");
        if (parse != null) {
            this.mContext.getContentResolver().insert(parse, null);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public boolean isIncomingSmsAllowed(ContextInfo contextInfo) {
        return getSmsMmsAllowed("allowIncomingSms");
    }

    public boolean isOutgoingSmsAllowed(ContextInfo contextInfo) {
        return getSmsMmsAllowed("allowOutgoingSms");
    }

    public boolean allowIncomingMms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid, "PHONERESTRICTION", "allowIncomingMms", z);
    }

    public boolean allowOutgoingMms(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid, "PHONERESTRICTION", "allowOutgoingMms", z);
    }

    public boolean isIncomingMmsAllowed(ContextInfo contextInfo) {
        return getSmsMmsAllowed("allowIncomingMms");
    }

    public boolean isOutgoingMmsAllowed(ContextInfo contextInfo) {
        if (getEmergencyCallOnly(null, true)) {
            return false;
        }
        return getSmsMmsAllowed("allowOutgoingMms");
    }

    public final boolean getSmsMmsAllowed(String str) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", str).iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "blockSmsWithStorage", z);
        if (!z && putBoolean && !isBlockSmsWithStorageEnabled(enforceOwnerOnlyAndPhonePermission)) {
            this.mSmsMmsDeliveryHandler.sendMessage(Message.obtain(this.mSmsMmsDeliveryHandler, 0));
        }
        return putBoolean;
    }

    public boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo) {
        return this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "blockSmsWithStorage").contains(Boolean.TRUE);
    }

    public boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "blockMmsWithStorage", z);
        if (!z && putBoolean && !isBlockMmsWithStorageEnabled(enforceOwnerOnlyAndPhonePermission)) {
            this.mSmsMmsDeliveryHandler.sendMessage(Message.obtain(this.mSmsMmsDeliveryHandler, 1));
        }
        return putBoolean;
    }

    public boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo) {
        return this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "blockMmsWithStorage").contains(Boolean.TRUE);
    }

    public boolean clearStoredBlockedSms(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.deleteDataByFields("SMSMMSBlockedDelivery", new String[]{"smsMmsType"}, new String[]{"1"});
    }

    public boolean clearStoredBlockedMms(ContextInfo contextInfo) {
        enforceOwnerOnlyAndPhonePermission(contextInfo);
        return this.mEdmStorageProvider.deleteDataByFields("SMSMMSBlockedDelivery", new String[]{"smsMmsType"}, new String[]{"0"});
    }

    public void storeBlockedSmsMms(boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) {
        enforcePhoneApp();
        String hexString = HexDump.toHexString(bArr);
        ContentValues contentValues = new ContentValues();
        contentValues.put("smsMmsPdu", hexString);
        contentValues.put("smsMmsSendType", Integer.valueOf(i));
        contentValues.put("smsMmsType", Integer.valueOf(z ? 1 : 0));
        contentValues.put("smsMmsOrigAddress", str);
        if (!z) {
            contentValues.put("smsMmsTimeStamp", str2);
            contentValues.put("smsMmsMessageId", str3);
            contentValues.put("smsMmsSubId", str4);
        }
        try {
            this.mEdmStorageProvider.insertConfiguration("SMSMMSBlockedDelivery", contentValues);
            Log.w("PhoneRestrictionPolicy", "sms/mms stored successfully");
        } catch (Exception unused) {
            Log.w("PhoneRestrictionPolicy", "could not write sms/mms into edm database");
        }
    }

    public class SmsMmsDeliveryHandler extends Handler {
        public SmsMmsDeliveryHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.w("PhoneRestrictionPolicy", "Message received - msg " + message);
            final int i = message.what;
            new Thread(new Runnable() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.SmsMmsDeliveryHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    int i2 = i;
                    if (i2 == 0) {
                        PhoneRestrictionPolicy.this.deliveryBlockedMsgs(0);
                        return;
                    }
                    if (i2 == 1) {
                        PhoneRestrictionPolicy.this.deliveryBlockedMsgs(1);
                    } else {
                        if (i2 != 2) {
                            return;
                        }
                        PhoneRestrictionPolicy.this.deliveryBlockedMsgs(0);
                        PhoneRestrictionPolicy.this.deliveryBlockedMsgs(1);
                    }
                }
            }).start();
        }
    }

    public final synchronized void deliveryBlockedMsgs(int i) {
        Log.w("PhoneRestrictionPolicy", " >>>> deliveryBlockedMsgs");
        String[] strArr = {"smsMmsBlockedRowId", "smsMmsSendType", "smsMmsPdu", "smsMmsTimeStamp", "smsMmsOrigAddress", "smsMmsMessageId", "smsMmsSubId"};
        String str = i == 0 ? "1" : "0";
        ContentValues contentValues = new ContentValues();
        contentValues.put("smsMmsType", str);
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("SMSMMSBlockedDelivery", strArr, contentValues);
        Log.w("PhoneRestrictionPolicy", "cvList size " + valuesList.size());
        if (valuesList.isEmpty()) {
            return;
        }
        for (ContentValues contentValues2 : valuesList) {
            if (this.mIsPhoneShuttingDown) {
                Log.w("PhoneRestrictionPolicy", "Phone is shutting down ...quitting");
                return;
            }
            Intent intent = new Intent();
            if (i == 0) {
                if (isBlockSmsWithStorageEnabled(null)) {
                    Log.w("PhoneRestrictionPolicy", "Block SMS with storage applies... quitting");
                    return;
                }
                String asString = contentValues2.getAsString("smsMmsOrigAddress");
                if (!getEmergencyCallOnly(null, true) && isIncomingSmsAllowed(null) && canIncomingSms(asString)) {
                    intent.setAction("com.samsung.android.knox.intent.action.SEND_BLOCKED_SMS_INTERNAL");
                }
                Log.w("PhoneRestrictionPolicy", "Other incoming SMS policies apply... delete message and continue");
                deleteMessageFromStorageProvider(contentValues2.getAsString("smsMmsBlockedRowId"));
            } else {
                if (isBlockMmsWithStorageEnabled(null)) {
                    Log.w("PhoneRestrictionPolicy", "Block MMS with storage applies... quitting");
                    return;
                }
                if (!getEmergencyCallOnly(null, true) && isIncomingMmsAllowed(null) && ((!this.mTelMgr.isNetworkRoaming() || this.mEDM.getRoamingPolicy().isRoamingPushEnabled()) && this.mEDM.getPhoneRestrictionPolicy().isWapPushAllowed())) {
                    intent.putExtra("com.samsung.android.knox.intent.extra.TIME_STAMP_INTERNAL", contentValues2.getAsString("smsMmsTimeStamp"));
                    intent.putExtra("com.samsung.android.knox.intent.extra.ORIG_ADDRESS_INTERNAL", contentValues2.getAsString("smsMmsOrigAddress"));
                    intent.putExtra("com.samsung.android.knox.intent.extra.MESSAGE_ID_INTERNAL", contentValues2.getAsString("smsMmsMessageId"));
                    intent.putExtra("com.samsung.android.knox.intent.extra.SUB_ID_INTERNAL", contentValues2.getAsString("smsMmsSubId"));
                    intent.setAction("com.samsung.android.knox.intent.action.SEND_BLOCKED_MMS_INTERNAL");
                }
                Log.w("PhoneRestrictionPolicy", "Other incoming MMS policies apply... delete message and continue");
                deleteMessageFromStorageProvider(contentValues2.getAsString("smsMmsBlockedRowId"));
            }
            deleteMessageFromStorageProvider(contentValues2.getAsString("smsMmsBlockedRowId"));
            String asString2 = contentValues2.getAsString("smsMmsPdu");
            if (asString2 != null) {
                byte[] hexStringToByteArray = HexDump.hexStringToByteArray(asString2);
                Integer asInteger = contentValues2.getAsInteger("smsMmsSendType");
                int intValue = asInteger != null ? asInteger.intValue() : -1;
                intent.putExtra("com.samsung.android.knox.intent.extra.PDU_INTERNAL", hexStringToByteArray);
                intent.putExtra("com.samsung.android.knox.intent.extra.SEND_TYPE_INTERNAL", intValue);
                final Object obj = new Object();
                this.mContext.sendOrderedBroadcast(intent, "com.samsung.android.knox.permission.KNOX_RECEIVE_BLOCKED_SMS_MMS_INTERNAL", new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.PhoneRestrictionPolicy.4
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent2) {
                        if (getResultExtras(true).getBoolean("smsBlockHandler", false)) {
                            synchronized (obj) {
                                obj.notify();
                            }
                        }
                    }
                }, null, -1, null, null);
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
    }

    public final void deleteMessageFromStorageProvider(String str) {
        this.mEdmStorageProvider.deleteDataByFields("SMSMMSBlockedDelivery", new String[]{"smsMmsBlockedRowId"}, new String[]{str});
    }

    public boolean allowWapPush(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid, "PHONERESTRICTION", "wapPushEnabled", z);
    }

    public boolean isWapPushAllowed(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "wapPushEnabled").iterator();
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
        Log.d("PhoneRestrictionPolicy", "isWapPushAllowed : " + z);
        return z;
    }

    public boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndPhonePermission(contextInfo).mCallerUid, "PHONERESTRICTION", "allowCallerID", z);
        } catch (Exception e) {
            Log.w("PhoneRestrictionPolicy", e.toString());
            return false;
        }
    }

    public boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) {
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "allowCallerID").iterator();
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

    public String getOutgoingCallExceptionPatterns(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getOutgoingCallExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, true, "outgoingCallExceptionPattern");
        }
        return null;
    }

    public String getIncomingCallExceptionPatterns(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getIncomingCallExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, true, "incomingCallExceptionPattern");
        }
        return null;
    }

    public boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.removeOutgoingCallExceptionPattern()");
        return setOutgoingCallExceptionPattern(contextInfo, "");
    }

    public boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.removeIncomingCallExceptionPattern()");
        return setIncomingCallExceptionPattern(contextInfo, "");
    }

    public boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addOutgoingCallExceptionPattern()");
        if (str == null) {
            return false;
        }
        String outgoingCallExceptionPatterns = getOutgoingCallExceptionPatterns(contextInfo);
        if (outgoingCallExceptionPatterns == null || outgoingCallExceptionPatterns.isEmpty()) {
            return setOutgoingCallExceptionPattern(contextInfo, str);
        }
        return setOutgoingCallExceptionPattern(contextInfo, outgoingCallExceptionPatterns.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addIncomingCallExceptionPattern()");
        if (str == null) {
            return false;
        }
        String incomingCallExceptionPatterns = getIncomingCallExceptionPatterns(contextInfo);
        if (incomingCallExceptionPatterns == null || incomingCallExceptionPatterns.isEmpty()) {
            return setIncomingCallExceptionPattern(contextInfo, str);
        }
        return setIncomingCallExceptionPattern(contextInfo, incomingCallExceptionPatterns.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setOutgoingCallExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "outgoingCallExceptionPattern", removeWhiteSpaces(str));
    }

    public boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setIncomingCallExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "incomingCallExceptionPattern", removeWhiteSpaces(str));
    }

    public String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getOutgoingSmsExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, true, "outgoingSmsExceptionPattern");
        }
        return null;
    }

    public String getIncomingSmsExceptionPatterns(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getIncomingSmsExceptionPatterns()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return getRestrictionPattern(enforceOwnerOnlyAndPhonePermission, true, "incomingSmsExceptionPattern");
        }
        return null;
    }

    public boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.removeOutgoingSmsExceptionPattern()");
        return setOutgoingSmsExceptionPattern(contextInfo, "");
    }

    public boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.removeIncomingSmsExceptionPattern()");
        return setIncomingSmsExceptionPattern(contextInfo, "");
    }

    public boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addOutgoingSmsExceptionPattern()");
        if (str == null) {
            return false;
        }
        String outgoingSmsExceptionPatterns = getOutgoingSmsExceptionPatterns(contextInfo);
        if (outgoingSmsExceptionPatterns == null || outgoingSmsExceptionPatterns.isEmpty()) {
            return setOutgoingSmsExceptionPattern(contextInfo, str);
        }
        return setOutgoingSmsExceptionPattern(contextInfo, outgoingSmsExceptionPatterns.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.addIncomingSmsExceptionPattern()");
        if (str == null) {
            return false;
        }
        String incomingSmsExceptionPatterns = getIncomingSmsExceptionPatterns(contextInfo);
        if (incomingSmsExceptionPatterns == null || incomingSmsExceptionPatterns.isEmpty()) {
            return setIncomingSmsExceptionPattern(contextInfo, str);
        }
        return setIncomingSmsExceptionPattern(contextInfo, incomingSmsExceptionPatterns.concat("|").concat(removeWhiteSpaces(str)));
    }

    public boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setOutgoingSmsExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "outgoingSmsExceptionPattern", removeWhiteSpaces(str));
    }

    public boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setOutgoingSmsExceptionPattern()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "incomingSmsExceptionPattern", removeWhiteSpaces(str));
    }

    public final void notifySmsPatternCheck() {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.REQUEST_SMS_PATTERN_CHECK_INTERNAL");
        intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, isSmsPatternCheckRequired());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcast(intent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isSmsPatternCheckRequired() {
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("PHONERESTRICTION", new String[]{"adminUid", "smsRestrictionIncomingPattern"});
        if (valuesList != null && !valuesList.isEmpty()) {
            for (ContentValues contentValues : valuesList) {
                if (contentValues.getAsInteger("adminUid") != null && !TextUtils.isEmpty(contentValues.getAsString("smsRestrictionIncomingPattern"))) {
                    return true;
                }
            }
        }
        List<ContentValues> valuesList2 = this.mEdmStorageProvider.getValuesList("PHONERESTRICTION", new String[]{"adminUid", "smsRestrictionOutgoingPattern"});
        if (valuesList2 == null || valuesList2.isEmpty()) {
            return false;
        }
        for (ContentValues contentValues2 : valuesList2) {
            if (contentValues2.getAsInteger("adminUid") != null && !TextUtils.isEmpty(contentValues2.getAsString("smsRestrictionOutgoingPattern"))) {
                return true;
            }
        }
        return false;
    }

    public synchronized int lockUnlockCorporateSimCard(ContextInfo contextInfo, String str, String str2, boolean z) {
        boolean removeSimcard;
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard lock " + z + " iccId " + str);
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (!validatePinCode(str2)) {
            logError(2);
            return 2;
        }
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard valid pincode");
        if (TextUtils.isEmpty(str)) {
            SubscriptionInfo activeSubscriptionInfo = this.mSubMgr.getActiveSubscriptionInfo(SubscriptionManager.getDefaultSubscriptionId());
            if (activeSubscriptionInfo == null) {
                return 9;
            }
            str = activeSubscriptionInfo.getIccId();
            if (str == null) {
                logError(9);
                return 9;
            }
        }
        int simSubId = getSimSubId(str);
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim subscription ID " + simSubId);
        if (simSubId == -1) {
            logError(13);
            return 13;
        }
        int adminBySimcard = this.mSimDbProxy.getAdminBySimcard(str);
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim owner " + adminBySimcard);
        boolean isSimLocked = isSimLocked(simSubId);
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard isSimLocked  " + isSimLocked);
        if (z) {
            if (isSimLocked) {
                if (adminBySimcard == -1) {
                    logError(4);
                    return 4;
                }
                if (adminBySimcard == i) {
                    logError(11);
                    return 11;
                }
                if (adminBySimcard != i) {
                    logError(12);
                    return 12;
                }
            } else if (adminBySimcard != -1) {
                if (adminBySimcard == i) {
                    this.mSimDbProxy.removeSimcard(i, str);
                } else if (adminBySimcard != i) {
                    logError(12);
                    return 12;
                }
            }
        } else if (!isSimLocked) {
            logError(5);
            if (adminBySimcard == -1) {
                return 5;
            }
            if (adminBySimcard == i) {
                this.mSimDbProxy.removeSimcard(i, str);
                return 5;
            }
            if (adminBySimcard != i) {
                return 5;
            }
        } else if (adminBySimcard != -1 && adminBySimcard != i && adminBySimcard != i) {
            logError(12);
            return 12;
        }
        int subIdLockEnabled = setSubIdLockEnabled(simSubId, z, str2);
        logError(subIdLockEnabled);
        if (subIdLockEnabled == 0) {
            if (!z) {
                removeSimcard = adminBySimcard != -1 ? this.mSimDbProxy.removeSimcard(i, str) : true;
            } else if (adminBySimcard == -1) {
                removeSimcard = this.mSimDbProxy.addSimcard(i, str, str2);
            } else {
                removeSimcard = this.mSimDbProxy.setPincode(i, str, str2);
            }
            setLockedIccIdsSystemUI(Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndPhonePermission));
            if (!removeSimcard) {
                logError(10);
                return 10;
            }
        }
        return subIdLockEnabled;
    }

    public final void logError(int i) {
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
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin exceeded max retries");
                break;
            case 9:
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim not ready");
                break;
            case 10:
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin database error");
                break;
            case 11:
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin already locked by admin");
                break;
            case 12:
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim pin owned by another admin");
                break;
            case 13:
                Log.e("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim not inserted");
                break;
        }
    }

    public int changeSimPinCode(ContextInfo contextInfo, String str, String str2, String str3) {
        boolean pincode;
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        int i = enforceOwnerOnlyAndPhonePermission.mCallerUid;
        if (!validatePinCode(str2) || !validatePinCode(str3)) {
            return 2;
        }
        if (TextUtils.isEmpty(str)) {
            SubscriptionInfo activeSubscriptionInfo = this.mSubMgr.getActiveSubscriptionInfo(SubscriptionManager.getDefaultSubscriptionId());
            if (activeSubscriptionInfo == null) {
                return 9;
            }
            str = activeSubscriptionInfo.getIccId();
            if (str == null) {
                Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim not inserted");
                return 9;
            }
        }
        int simSubId = getSimSubId(str);
        Log.d("PhoneRestrictionPolicy", "lockUnlockCorporateSimCard Sim subscription ID " + simSubId);
        if (simSubId == -1) {
            return 13;
        }
        if (!isSimLocked(simSubId)) {
            return 5;
        }
        int adminBySimcard = this.mSimDbProxy.getAdminBySimcard(str);
        if (adminBySimcard != -1 && adminBySimcard != i) {
            return 12;
        }
        int changeSimPinCodeService = changeSimPinCodeService(simSubId, str2, str3);
        if (changeSimPinCodeService == 0) {
            if (!isSimLockedByAdmin(str)) {
                pincode = this.mSimDbProxy.addSimcard(i, str, str3);
            } else {
                pincode = this.mSimDbProxy.setPincode(i, str, str3);
            }
            if (!pincode) {
                return 10;
            }
        }
        Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndPhonePermission);
        setLockedIccIdsSystemUI(0);
        return changeSimPinCodeService;
    }

    public final int setSubIdLockEnabled(int i, boolean z, String str) {
        Log.d("PhoneRestrictionPolicy", "setSubIdLockEnabled ");
        ISimPinPolicy iSimPinPolicy = this.mSimPinService;
        if (iSimPinPolicy != null) {
            try {
                return iSimPinPolicy.setSubIdLockEnabled(i, z, str);
            } catch (RemoteException unused) {
                Log.w("PhoneRestrictionPolicy", "Failed to communicate with Sim Pin Service");
                return 100;
            }
        }
        this.mSimPinHandler.postDelayed(this.mSimPinBind, 1000L);
        return 100;
    }

    public final boolean isSimLocked(int i) {
        ISimPinPolicy iSimPinPolicy = this.mSimPinService;
        if (iSimPinPolicy != null) {
            try {
                return iSimPinPolicy.isSimLocked(i);
            } catch (RemoteException unused) {
                Log.w("PhoneRestrictionPolicy", "Failed to communicate with Sim Pin Service");
                return false;
            }
        }
        this.mSimPinHandler.postDelayed(this.mSimPinBind, 1000L);
        return false;
    }

    public final int getSimSubId(String str) {
        List<SubscriptionInfo> activeSubscriptionInfoList = this.mSubMgr.getActiveSubscriptionInfoList();
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

    public String getPinCode(String str) {
        int i;
        int callingUid = Binder.getCallingUid();
        boolean z = false;
        try {
            i = this.mContext.getPackageManager().getPackageUidAsUser("com.android.systemui", 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("PhoneRestrictionPolicy", "Unable to resolve SystemUI's UID.", e);
            i = -1;
        }
        int appId = UserHandle.getAppId(callingUid);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid != null) {
            int lastIndexOf = nameForUid.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            if (nameForUid.equals("android.uid.systemui") && appId == i) {
                z = true;
            }
        }
        if (callingUid != Process.myUid() && callingUid != 1001 && !z) {
            throw new SecurityException("Can only be called by System, Phone or System UI");
        }
        return this.mSimDbProxy.getPincode(str);
    }

    public boolean isSimLockedByAdmin(String str) {
        if (str == null) {
            return this.mSimDbProxy.hasAnySimcard();
        }
        String pincode = this.mSimDbProxy.getPincode(str);
        return (pincode == null || pincode.isEmpty()) ? false : true;
    }

    public final int changeSimPinCodeService(int i, String str, String str2) {
        ISimPinPolicy iSimPinPolicy = this.mSimPinService;
        if (iSimPinPolicy != null) {
            try {
                return iSimPinPolicy.changeSimPinCode(i, str, str2);
            } catch (RemoteException unused) {
                Log.w("PhoneRestrictionPolicy", "Failed to communicate with Sim Pin Service");
                return 100;
            }
        }
        this.mSimPinHandler.postDelayed(this.mSimPinBind, 1000L);
        return 100;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0075, code lost:
    
        r2 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void unlockAllSimCards() {
        KeyguardManager keyguardManager;
        if (this.mSimDbProxy.hasAnySimcard()) {
            List<SubscriptionInfo> activeSubscriptionInfoList = this.mSubMgr.getActiveSubscriptionInfoList();
            boolean z = false;
            if (activeSubscriptionInfoList != null) {
                boolean z2 = false;
                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                    String iccId = subscriptionInfo.getIccId();
                    boolean z3 = TelephonyManager.getSimStateForSlotIndex(subscriptionInfo.getSimSlotIndex()) == 2;
                    int subscriptionId = subscriptionInfo.getSubscriptionId();
                    Log.d("PhoneRestrictionPolicy", "unlockAllSimCards Got iccId = " + iccId + " for subscriptionId = " + subscriptionId);
                    boolean isSimLockedByAdmin = isSimLockedByAdmin(subscriptionInfo.getIccId());
                    if (iccId != null && z3 && isSimLockedByAdmin) {
                        String pinCode = getPinCode(iccId);
                        if (pinCode != null) {
                            supplyPinReportResultForSubscriber(pinCode, subscriptionId);
                        }
                    }
                }
                z = z2;
            } else {
                Log.d("PhoneRestrictionPolicy", "unlockAllSimCards subInfoList list is null");
            }
            getKeyguardManager();
            if (z && (keyguardManager = this.mKeyguardManager) != null && !keyguardManager.isKeyguardLocked()) {
                this.mContext.sendBroadcast(new Intent("com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL"));
            }
        }
    }

    public final KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        }
        return this.mKeyguardManager;
    }

    public boolean supplyPinReportResultForSubscriber(String str, int i) {
        Log.w("PhoneRestrictionPolicy", "supplyPinReportResultForSubscriber start");
        if (this.mSimPinService != null) {
            try {
                Log.w("PhoneRestrictionPolicy", "supplyPinReportResultForSubscriber call");
                return this.mSimPinService.supplyPinReportResultForSubscriber(str, i);
            } catch (RemoteException unused) {
                Log.w("PhoneRestrictionPolicy", "Failed to communicate with Sim Pin Service");
                return false;
            }
        }
        Log.w("PhoneRestrictionPolicy", "supplyPinReportResultForSubscriber schedule");
        this.mSimPinHandler.postDelayed(this.mSimPinBind, 1000L);
        return false;
    }

    public boolean isSubIdLockedByAdmin(int i) {
        SubscriptionInfo activeSubscriptionInfo = this.mSubMgr.getActiveSubscriptionInfo(i);
        return isSimLockedByAdmin(activeSubscriptionInfo != null ? activeSubscriptionInfo.getIccId() : null);
    }

    public final boolean validatePinCode(String str) {
        if (str == null || str.length() < 4 || str.length() > 8) {
            return false;
        }
        try {
            return Integer.parseInt(str) >= 0;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public final void setLockedIccIdsSystemUI(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SystemUIAdapter.getInstance(this.mContext).setLockedIccIdsAsUser(i, this.mSimDbProxy.getIccIdListByAdmin());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setDisclaimerText(ContextInfo contextInfo, String str) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setDisclaimerText()");
        ContextInfo enforceOwnerOnlyAndPhonePermission = enforceOwnerOnlyAndPhonePermission(contextInfo);
        String disclaimerText = getDisclaimerText(enforceOwnerOnlyAndPhonePermission);
        if (str != null && str.length() > 60) {
            Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setDisclaimerText() was failed - text is over 60 char");
            return false;
        }
        if (disclaimerText != null) {
            int adminByField = this.mEdmStorageProvider.getAdminByField("PHONERESTRICTION", "disclaimerText", disclaimerText);
            int i = enforceOwnerOnlyAndPhonePermission.mCallerUid;
            if (i == adminByField) {
                return this.mEdmStorageProvider.putString(i, "PHONERESTRICTION", "disclaimerText", str);
            }
            Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setDisclaimerText() was failed - other admin already set text");
            return false;
        }
        return this.mEdmStorageProvider.putString(enforceOwnerOnlyAndPhonePermission.mCallerUid, "PHONERESTRICTION", "disclaimerText", str);
    }

    public String getDisclaimerText(ContextInfo contextInfo) {
        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.getDisclaimerText()");
        for (String str : this.mEdmStorageProvider.getStringList("PHONERESTRICTION", "disclaimerText")) {
            if (str != null) {
                return str;
            }
        }
        return null;
    }

    public final void updateSystemUIMonitor(int i) {
        setLockedIccIdsSystemUI(i);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump " + PhoneRestrictionPolicy.class.getSimpleName());
            return;
        }
        new EnterpriseDumpHelper(this.mContext).dumpTable(printWriter, "SimTable", new String[]{"adminUid", "SimIccId"});
        for (int i = 0; i < 2; i++) {
            printWriter.write("RCSEnabled for sim slot " + i + ": " + isRCSEnabledBySimSlot(null, 1, false, i) + System.lineSeparator());
        }
    }

    public int setRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
        return setRcsEnabledInternal(enforceOwnerOnlyAndPhonePermission(contextInfo), i, z, "enableRCS");
    }

    public int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
        ContextInfo enforceOwnerOnlyAndSimRestrictionPermission = enforceOwnerOnlyAndSimRestrictionPermission(contextInfo);
        if (i2 != 0 && i2 != 1) {
            Log.i("PhoneRestrictionPolicy", "setRCSEnabledBySimSlot(): failed. invalid simSlotId : " + i2);
            return -1;
        }
        return setRcsEnabledInternal(enforceOwnerOnlyAndSimRestrictionPermission, i, z, i2 == 1 ? "enableRCSForSimSlot1" : "enableRCSForSimSlot0");
    }

    public final int setRcsEnabledInternal(ContextInfo contextInfo, int i, boolean z, String str) {
        int i2;
        boolean z2;
        Log.i("PhoneRestrictionPolicy", "setRCSEnabledInternal(): " + z + ", columnName: " + str);
        if (!isRCSFeature(i)) {
            Log.i("PhoneRestrictionPolicy", "setRCSEnabledInternal(): failed. not support feature = " + i);
            return -1;
        }
        int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        try {
            i2 = this.mEdmStorageProvider.getInt(contextInfo.mCallerUid, "PHONERESTRICTION", str);
        } catch (Exception unused) {
            i2 = 255;
        }
        if (i2 >= 0) {
            i3 = i2;
        }
        try {
            z2 = this.mEdmStorageProvider.putInt(contextInfo.mCallerUid, "PHONERESTRICTION", str, z ? i | i3 : (~i) & i3);
        } catch (Exception e) {
            Log.i("PhoneRestrictionPolicy", "setRCSEnabledInternal(): failed. unexpected exception", e);
            z2 = false;
        }
        if (z2) {
            notifyRcsChangesAllUser();
        }
        return z2 ? 0 : -2;
    }

    public boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
        return isRCSEnabledInternal(i, z, "enableRCS");
    }

    public boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
        if (i2 != 0 && i2 != 1) {
            Log.i("PhoneRestrictionPolicy", "isRCSEnabledBySimSlot(): failed. invalid simSlotId : " + i2);
            return false;
        }
        return isRCSEnabledInternal(i, z, i2 == 1 ? "enableRCSForSimSlot1" : "enableRCSForSimSlot0");
    }

    public final boolean isRCSEnabledInternal(int i, boolean z, String str) {
        Log.i("PhoneRestrictionPolicy", "isRCSEnabledInternal(), showMsg: " + z + ", columnName: " + str);
        if (!isRCSFeature(i)) {
            Log.i("PhoneRestrictionPolicy", "isRCSEnabledInternal(): allowed. not support feature = " + i);
            return true;
        }
        if (getEmergencyCallOnly(null, true)) {
            if (z) {
                RestrictionToastManager.show(17042454);
            }
            return false;
        }
        Iterator it = this.mEdmStorageProvider.getIntList("PHONERESTRICTION", str).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() >= 0 && (num.intValue() & i) != i) {
                Log.i("PhoneRestrictionPolicy", "isRCSEnabledInternal(): disallowed by EDM");
                if (z) {
                    RestrictionToastManager.show(17042454);
                }
                return false;
            }
        }
        return true;
    }

    public final void notifyRcsChangesAllUser() {
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/PhoneRestrictionPolicy/isRCSEnabled"));
    }

    public boolean isDataAllowedFromSimSlot(int i) {
        return isOperationAllowed(1, i);
    }

    public boolean isIncomingCallAllowedFromSimSlot(int i) {
        return isOperationAllowed(2, i);
    }

    public boolean isOutgoingCallAllowedFromSimSlot(int i) {
        return isOperationAllowed(3, i);
    }

    public boolean isIncomingSmsAllowedFromSimSlot(int i) {
        return isOperationAllowed(4, i);
    }

    public boolean isOutgoingSmsAllowedFromSimSlot(int i) {
        return isOperationAllowed(5, i);
    }

    public boolean isMmsAllowedFromSimSlot(int i) {
        return isOperationAllowed(6, i);
    }

    public final boolean updateDataSimSlotValueSystemUI(int i, boolean z) {
        if (this.mSubMgr == null) {
            this.mSubMgr = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        }
        SubscriptionInfo activeSubscriptionInfo = this.mSubMgr.getActiveSubscriptionInfo(SubscriptionManager.getDefaultDataSubscriptionId());
        if (activeSubscriptionInfo != null) {
            int callingUserId = UserHandle.getCallingUserId();
            if (activeSubscriptionInfo.getSimSlotIndex() == i) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!z) {
                        try {
                            SemWifiManager semWifiManager = (SemWifiManager) this.mContext.getSystemService("sem_wifi");
                            if (semWifiManager != null) {
                                int wifiApState = semWifiManager.getWifiApState();
                                if (wifiApState == 13 || wifiApState == 12) {
                                    semWifiManager.setWifiApEnabled((SoftApConfiguration) null, z);
                                }
                            } else {
                                Log.d("PhoneRestrictionPolicy", "Mobile Hotspot cannot be disabled");
                            }
                        } catch (Exception e) {
                            Log.e("PhoneRestrictionPolicy", "updateDataSimSlotValueSystemUI failed ", e);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                    }
                    SystemUIAdapter systemUIAdapter = SystemUIAdapter.getInstance(this.mContext);
                    systemUIAdapter.setCellularDataAllowedAsUser(callingUserId, z);
                    systemUIAdapter.setWifiTetheringAllowedAsUser(callingUserId, z);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        return true;
    }

    public final boolean isOperationAllowed(int i, int i2) {
        if (i2 != 0 && i2 != 1) {
            Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.isOperationAllowed() was failed - SlotID is invalid " + i2);
            return true;
        }
        Log.d("PhoneRestrictionPolicy", "isOperationAllowed >>> slotNum: " + i2 + " function: " + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("slotId", Integer.valueOf(i2));
        Cursor cursor = this.mEdmStorageProvider.getCursor("PHONERESTRICTION_SLOTID", new String[]{"slotId", "allowData", "allowIncomingCall", "allowOutgoingCall", "allowIncomingSMS", "allowOutgoingSMS", "allowMMS", "adminUid"}, contentValues, null);
        if (cursor == null) {
            Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.isOperationAllowed() Cursor is Null");
            return true;
        }
        cursor.moveToFirst();
        boolean z = true;
        while (!cursor.isAfterLast()) {
            try {
                try {
                    Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.isOperationAllowed() - dB have some valid values");
                    boolean z2 = false;
                    if (i == 1) {
                        Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.isOperationAllowed() - Result is - " + cursor.getInt(cursor.getColumnIndex("allowData")));
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
                    Log.e("PhoneRestrictionPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                }
            } finally {
                cursor.close();
            }
        }
        return z;
    }

    public int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        int dynamicSimControl = setDynamicSimControl(enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, 1, z);
        if (dynamicSimControl == 0) {
            updateDataSimSlotValueSystemUI(i, z);
        }
        return dynamicSimControl;
    }

    public int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, 2, z);
    }

    public int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, 3, z);
    }

    public int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, 4, z);
    }

    public int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, 5, z);
    }

    public int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
        return setDynamicSimControl(enforceOwnerOnlyAndSimRestrictionPermission(contextInfo), i, 6, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x014f A[Catch: Exception -> 0x014a, TRY_LEAVE, TryCatch #0 {Exception -> 0x014a, blocks: (B:60:0x013f, B:49:0x014f), top: B:59:0x013f }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int setDynamicSimControl(ContextInfo contextInfo, int i, int i2, boolean z) {
        Log.d("PhoneRestrictionPolicy", "setDynamicSimControl >>> slotNum: " + i + " function: " + i2 + " state: " + z);
        boolean z2 = true;
        if (i != 0 && i != 1) {
            Log.d("PhoneRestrictionPolicy", ">>> PhoneRestrictionPolicy.setDynamicSimControl() was failed - SlotID is invalid");
            return -1;
        }
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        ContentValues contentValues = new ContentValues();
        List<SubscriptionInfo> activeSubscriptionInfoList = this.mSubMgr.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            Log.d("PhoneRestrictionPolicy", "CurrentSims list is null");
            return -2;
        }
        boolean z3 = true;
        int i3 = 0;
        while (true) {
            if (i3 >= activeSubscriptionInfoList.size()) {
                break;
            }
            int simSlotIndex = activeSubscriptionInfoList.get(i3).getSimSlotIndex();
            if (simSlotIndex == i) {
                int subscriptionId = activeSubscriptionInfoList.get(i3).getSubscriptionId();
                if (!z && SubscriptionManager.getDefaultDataSubscriptionId() == subscriptionId && i2 == 1 && telephonyManager != null && telephonyManager.getDataEnabled()) {
                    Log.d("PhoneRestrictionPolicy", "Current Sim " + simSlotIndex + "is request for Data Block");
                    telephonyManager.setDataEnabled(false);
                    if (telephonyManager.isNetworkRoaming()) {
                        Log.d("PhoneRestrictionPolicy", "Current Sim " + simSlotIndex + " is on roaming.");
                        telephonyManager.setDataRoamingEnabled(false);
                    }
                }
                z3 = true;
            } else {
                Log.d("PhoneRestrictionPolicy", "Current Sim is " + simSlotIndex + ". Requested block Sim is not present in SlotId" + i);
                i3++;
                z3 = false;
            }
        }
        if (i2 == 1) {
            contentValues.put("allowData", Boolean.valueOf(z));
        }
        if (i2 == 2) {
            contentValues.put("allowIncomingCall", Boolean.valueOf(z));
        }
        if (i2 == 3) {
            contentValues.put("allowOutgoingCall", Boolean.valueOf(z));
        }
        if (i2 == 4) {
            contentValues.put("allowIncomingSMS", Boolean.valueOf(z));
        }
        if (i2 == 5) {
            contentValues.put("allowOutgoingSMS", Boolean.valueOf(z));
        }
        if (i2 == 6) {
            contentValues.put("allowMMS", Boolean.valueOf(z));
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        contentValues2.put("slotId", Integer.valueOf(i));
        if (z3) {
            try {
                if (this.mEdmStorageProvider.put("PHONERESTRICTION_SLOTID", contentValues, contentValues2)) {
                    if (i2 == 6) {
                        Intent intent = new Intent("com.samsung.knox.action.KNOX_MMS_CONTROL");
                        intent.putExtra("setPhoneId", i);
                        intent.putExtra("setMmsControlFlag", z);
                        this.mContext.sendBroadcast(intent);
                        Log.d("PhoneRestrictionPolicy", "setDynamicSimControl >>> sendBroadcast: com.samsung.knox.action.KNOX_MMS_CONTROL");
                        Log.d("PhoneRestrictionPolicy", "setPhoneId: " + i + ", setMmsControlFlag: " + z);
                    }
                    return !z2 ? 0 : -2;
                }
            } catch (Exception e) {
                Log.i("PhoneRestrictionPolicy", "setDynamicSimControl(): failed. unexpected exception - dB error", e);
                return -4;
            }
        }
        z2 = false;
        if (i2 == 6) {
        }
        if (!z2) {
        }
    }
}
