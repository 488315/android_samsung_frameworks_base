package com.samsung.ucm.ucmservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class UcmAgentWrapper {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public ComponentName componentName;
    public AgentInfo info;
    public IUcmAgentManagerDeleteDelegate mAgentDeleteDelegate;
    public Context mContext;
    public Handler mHandler;
    public IUcmAgentService mUcmAgentService;
    public long RESTART_TIMEOUT_MILLIS = 5000;
    public boolean mBound = false;
    public Queue mEventBoxQueue = new LinkedList();
    public final ServiceConnection mConnection = new ServiceConnection() { // from class: com.samsung.ucm.ucmservice.UcmAgentWrapper.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            String packageName;
            Log.d("UcmAgentWrapper", "onServiceConnected " + componentName);
            UcmAgentWrapper.this.mHandler.removeMessages(4);
            UcmAgentWrapper.this.mUcmAgentService = IUcmAgentService.Stub.asInterface(iBinder);
            ComponentName componentName2 = UcmAgentWrapper.this.componentName;
            if (componentName2 == null || (packageName = componentName2.getPackageName()) == null || packageName.isEmpty()) {
                return;
            }
            if ("com.samsung.ucs.agent.boot".equals(packageName)) {
                Log.d("UcmAgentWrapper", "notify binding done (bootagent refresh) : " + packageName);
                UcmAgentWrapper.this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.BOOTAGENT_REFRESH_DONE"), UserHandle.SYSTEM);
                return;
            }
            Log.d("UcmAgentWrapper", "notify binding done : " + packageName);
            Intent intent = new Intent("com.samsung.android.knox.intent.action.UCM_REFRESH_DONE");
            intent.putExtra("PackageName", packageName);
            UcmAgentWrapper.this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
            while (!UcmAgentWrapper.this.mEventBoxQueue.isEmpty()) {
                EventBox eventBox = (EventBox) UcmAgentWrapper.this.mEventBoxQueue.poll();
                if (eventBox != null) {
                    try {
                        UcmAgentWrapper.this.notifyChange(eventBox.eventId, eventBox.eventData);
                    } catch (RemoteException e) {
                        Log.e("UcmAgentWrapper", "Noti was not sent cause binding, send notifyChange : " + e);
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("UcmAgentWrapper", "onServiceDisconnected " + componentName);
            UcmAgentWrapper.this.mUcmAgentService = null;
            if (UcmAgentWrapper.this.mBound) {
                UcmAgentWrapper.this.scheduleRestart();
            }
        }
    };

    public class EventBox {
        public Bundle eventData;
        public int eventId;

        public EventBox(int i, Bundle bundle) {
            this.eventId = i;
            this.eventData = bundle;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof UcmAgentWrapper) {
            return this.componentName.equals(((UcmAgentWrapper) obj).componentName);
        }
        return false;
    }

    public int hashCode() {
        return this.componentName.hashCode() * 31;
    }

    public UcmAgentWrapper(Context context, IUcmAgentManagerDeleteDelegate iUcmAgentManagerDeleteDelegate, ComponentName componentName) {
        this.mContext = context;
        this.mAgentDeleteDelegate = iUcmAgentManagerDeleteDelegate;
        this.componentName = componentName;
        try {
            createHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void createHandler() {
        Log.d("UcmAgentWrapper", "createHandler. enter");
        HandlerThread handlerThread = new HandlerThread("UcmAgentWrapperHandlerThread");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper()) { // from class: com.samsung.ucm.ucmservice.UcmAgentWrapper.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 4) {
                    return;
                }
                UcmAgentWrapper.this.unbind();
                Log.d("UcmAgentWrapper", "MSG_RESTART_TIMEOUT");
                UcmAgentWrapper.this.mAgentDeleteDelegate.deleteAndRefreshAgents(UcmAgentWrapper.this);
            }
        };
    }

    public void initialize(ResolveInfo resolveInfo, UserHandle userHandle) {
        String str;
        ComponentName componentName = getComponentName(resolveInfo);
        Log.d("UcmAgentWrapper", "initialize " + componentName + " user: " + userHandle);
        this.info = getAgentInfo(resolveInfo, this.mContext);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        AgentInfo agentInfo = this.info;
        if (agentInfo != null && (str = agentInfo.f1778id) != null) {
            if (str.equals("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")) {
                this.RESTART_TIMEOUT_MILLIS = 120000L;
            }
            if (this.info.f1778id.equals("com.samsung.ucs.agent.boot")) {
                this.RESTART_TIMEOUT_MILLIS = 120000L;
            }
        }
        scheduleRestart();
        Context context = this.mContext;
        if (context != null) {
            this.mBound = context.bindServiceAsUser(intent, this.mConnection, 1, userHandle);
        }
        if (this.mBound) {
            return;
        }
        Log.d("UcmAgentWrapper", "not able to bind " + componentName);
    }

    public void unbind() {
        if (!this.mBound) {
            Log.d("UcmAgentWrapper", "it is not bound anymore");
            return;
        }
        Log.d("UcmAgentWrapper", "unbind ");
        try {
            this.mContext.unbindService(this.mConnection);
        } catch (Exception e) {
            Log.e("UcmAgentWrapper", "unbind. Exception [" + e.toString() + "]");
        }
        this.mBound = false;
        this.mUcmAgentService = null;
    }

    public static ComponentName getComponentName(ResolveInfo resolveInfo) {
        if (resolveInfo == null || resolveInfo.serviceInfo == null) {
            return null;
        }
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        return new ComponentName(serviceInfo.packageName, serviceInfo.name);
    }

    public final void scheduleRestart() {
        Log.d("UcmAgentWrapper", "scheduleRestart");
        this.mHandler.removeMessages(4);
        this.mHandler.sendEmptyMessageDelayed(4, this.RESTART_TIMEOUT_MILLIS);
    }

    public boolean isServiceBound() {
        return this.mUcmAgentService != null;
    }

    public int notifyChange(int i, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.notifyChange(i, bundle);
        }
        return -1;
    }

    public int triggerNotifyChange(int i, Bundle bundle) {
        this.mEventBoxQueue.offer(new EventBox(i, bundle));
        return 0;
    }

    public Bundle saw(Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.saw(bundle);
        }
        return null;
    }

    public Bundle delete(String str, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.delete(str, bundle);
        }
        return null;
    }

    public Bundle generateDek() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.generateDek();
        }
        return null;
    }

    public Bundle generateWrappedDek() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.generateWrappedDek();
        }
        return null;
    }

    public Bundle getDek() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getDek();
        }
        return null;
    }

    public Bundle unwrapDek(byte[] bArr) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.unwrapDek(bArr);
        }
        return null;
    }

    public Bundle generateKeyguardPassword(int i, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.generateKeyguardPassword(i, bundle);
        }
        return null;
    }

    public Bundle getCertificateChain(String str, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getCertificateChain(str, bundle);
        }
        return null;
    }

    public Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.decrypt(str, bArr, str2, bundle);
        }
        return null;
    }

    public Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.encrypt(str, bArr, str2, bundle);
        }
        return null;
    }

    public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.setCertificateChain(str, bArr, bundle);
        }
        return null;
    }

    public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.importKeyPair(str, bArr, bArr2, bundle);
        }
        return null;
    }

    public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.installCertificateIfSupported(str, bArr, str2, bundle);
        }
        return null;
    }

    public Bundle importKey(String str, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.importKey(str, bundle);
        }
        return null;
    }

    public Bundle generateKey(String str, String str2, int i, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.generateKey(str, str2, i, bundle);
        }
        return null;
    }

    public Bundle getKeyType(String str, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getKeyType(str, bundle);
        }
        return null;
    }

    public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.generateKeyPair(str, str2, i, bundle);
        }
        return null;
    }

    public Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.sign(str, bArr, str2, z, bundle);
        }
        return null;
    }

    public Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.mac(str, bArr, str2, bundle);
        }
        return null;
    }

    public Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.generateSecureRandom(i, bArr, bundle);
        }
        return null;
    }

    public Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.setCredentialStorageProperty(i, i2, bundle);
        }
        return null;
    }

    public Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getCredentialStorageProperty(i, i2, bundle);
        }
        return null;
    }

    public Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.configureCredentialStoragePlugin(i, bundle, i2);
        }
        return null;
    }

    public Bundle getCredentialStoragePluginConfiguration(int i) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getCredentialStoragePluginConfiguration(i);
        }
        return null;
    }

    public Bundle verifyPin(int i, String str, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.verifyPin(i, str, bundle);
        }
        return null;
    }

    public Bundle verifyPuk(String str, String str2) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.verifyPuk(str, str2);
        }
        return null;
    }

    public Bundle changePin(String str, String str2) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.changePin(str, str2);
        }
        return null;
    }

    public Bundle changePinWithPassword(String str, String str2) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.changePinWithPassword(str, str2);
        }
        return null;
    }

    public Bundle initKeyguardPin(String str, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.initKeyguardPin(str, bundle);
        }
        return null;
    }

    public Bundle setKeyguardPinMaximumRetryCount(int i) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.setKeyguardPinMaximumRetryCount(i);
        }
        return null;
    }

    public Bundle setKeyguardPinMinimumLength(int i) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.setKeyguardPinMinimumLength(i);
        }
        return null;
    }

    public Bundle setKeyguardPinMaximumLength(int i) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.setKeyguardPinMaximumLength(i);
        }
        return null;
    }

    public Bundle getKeyguardPinMaximumRetryCount() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getKeyguardPinMaximumRetryCount();
        }
        return null;
    }

    public Bundle getKeyguardPinCurrentRetryCount() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getKeyguardPinCurrentRetryCount();
        }
        return null;
    }

    public Bundle getKeyguardPinMinimumLength() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getKeyguardPinMinimumLength();
        }
        return null;
    }

    public Bundle getKeyguardPinMaximumLength() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getKeyguardPinMaximumLength();
        }
        return null;
    }

    public Bundle setState(int i) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.setState(i);
        }
        return null;
    }

    public Bundle APDUCommand(byte[] bArr, Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.APDUCommand(bArr, bundle);
        }
        return null;
    }

    public Bundle getInfo() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getInfo();
        }
        return null;
    }

    public Bundle getStatus() {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.getStatus();
        }
        return null;
    }

    public Bundle resetUser(int i) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.resetUser(i);
        }
        return null;
    }

    public Bundle resetUid(int i) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.resetUid(i);
        }
        return null;
    }

    public Bundle containsAlias(String str, int i, int i2) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.containsAlias(str, i, i2);
        }
        return null;
    }

    public String getDetailErrorMessage(int i) {
        try {
            IUcmAgentService iUcmAgentService = this.mUcmAgentService;
            if (iUcmAgentService != null) {
                return iUcmAgentService.getDetailErrorMessage(i);
            }
            return null;
        } catch (AbstractMethodError unused) {
            Log.d("UcmAgentWrapper", "this plugin does not support getDetailErrorMessage API");
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x0334, code lost:
    
        if (r8 == null) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0336, code lost:
    
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0341, code lost:
    
        if (r8 == null) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x033c, code lost:
    
        if (r8 == null) goto L161;
     */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x035f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static AgentInfo getAgentInfo(ResolveInfo resolveInfo, Context context) {
        ServiceInfo serviceInfo;
        XmlResourceParser xmlResourceParser;
        int next;
        String attributeValue;
        String attributeValue2;
        String attributeValue3;
        XmlResourceParser xmlResourceParser2 = null;
        if (context == null) {
            Log.d("UcmAgentWrapper", "Context is null");
            return null;
        }
        if (resolveInfo == null || (serviceInfo = resolveInfo.serviceInfo) == null || serviceInfo.metaData == null) {
            Log.d("UcmAgentWrapper", "resolveInfo null");
            return null;
        }
        AgentInfo agentInfo = new AgentInfo();
        PackageManager packageManager = context.getPackageManager();
        try {
            xmlResourceParser = resolveInfo.serviceInfo.loadXmlMetaData(packageManager, "com.samsung.ucm.agent");
            try {
            } catch (PackageManager.NameNotFoundException e) {
                e = e;
            } catch (IOException e2) {
                e = e2;
            } catch (XmlPullParserException e3) {
                e = e3;
            } catch (Throwable th) {
                th = th;
                xmlResourceParser2 = xmlResourceParser;
                if (xmlResourceParser2 != null) {
                    xmlResourceParser2.close();
                }
                throw th;
            }
        } catch (PackageManager.NameNotFoundException e4) {
            e = e4;
            xmlResourceParser = null;
        } catch (IOException e5) {
            e = e5;
            xmlResourceParser = null;
        } catch (XmlPullParserException e6) {
            e = e6;
            xmlResourceParser = null;
        } catch (Throwable th2) {
            th = th2;
        }
        if (xmlResourceParser == null) {
            Log.w("UcmAgentWrapper", "Can't find com.samsung.ucm.agent meta-data");
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            return null;
        }
        packageManager.getResourcesForApplication(resolveInfo.serviceInfo.applicationInfo);
        do {
            next = xmlResourceParser.next();
            if (next == 1) {
                break;
            }
        } while (next != 2);
        if (!"cred-agent".equals(xmlResourceParser.getName())) {
            Log.w("UcmAgentWrapper", "Meta-data does not start with tag cred-agent");
            xmlResourceParser.close();
            return null;
        }
        String attributeValue4 = xmlResourceParser.getAttributeValue(null, "id");
        agentInfo.agentId = attributeValue4;
        if (attributeValue4 == null) {
            Log.w("UcmAgentWrapper", "Agent Id can't be null..");
            xmlResourceParser.close();
            return null;
        }
        agentInfo.f1778id = resolveInfo.serviceInfo.packageName + XmlUtils.STRING_ARRAY_SEPARATOR + agentInfo.agentId;
        agentInfo.summary = xmlResourceParser.getAttributeValue(null, "summary");
        agentInfo.title = xmlResourceParser.getAttributeValue(null, KnoxCustomManagerService.SHORTCUT_TITLE);
        agentInfo.vendorId = xmlResourceParser.getAttributeValue(null, "vendorId");
        agentInfo.isDetachable = "true".equals(xmlResourceParser.getAttributeValue(null, "isDetachable"));
        agentInfo.reqUserVerification = "true".equals(xmlResourceParser.getAttributeValue(null, "reqUserVerification"));
        agentInfo.isHardwareBacked = "true".equals(xmlResourceParser.getAttributeValue(null, "isHardwareBacked"));
        agentInfo.isReadOnly = "true".equals(xmlResourceParser.getAttributeValue(null, "isReadOnly"));
        if (DEBUG) {
            Log.d("UcmAgentWrapper", "DETAILED agentInfo information : isDetachable = " + xmlResourceParser.getAttributeValue(null, "isDetachable") + " requireUserVerification = " + xmlResourceParser.getAttributeValue(null, "reqUserVerification") + " isHardwareBacked = " + xmlResourceParser.getAttributeValue(null, "isHardwareBacked") + " isReadOnly = " + xmlResourceParser.getAttributeValue(null, "isReadOnly") + " packageName = " + resolveInfo.serviceInfo.packageName);
        }
        agentInfo.configuratorList = xmlResourceParser.getAttributeValue(null, "configuratorList");
        String attributeValue5 = xmlResourceParser.getAttributeValue(null, "isManageable");
        if (attributeValue5 == null || attributeValue5.length() <= 0 || !"false".equals(attributeValue5)) {
            agentInfo.isManageable = true;
        } else {
            agentInfo.isManageable = false;
        }
        agentInfo.enforceManagement = "true".equals(xmlResourceParser.getAttributeValue(null, "enforceManagement"));
        ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
        ApplicationInfo applicationInfo = serviceInfo2.applicationInfo;
        if (applicationInfo != null) {
            agentInfo.serviceUid = applicationInfo.uid;
        }
        agentInfo.packageName = serviceInfo2.packageName;
        String attributeValue6 = xmlResourceParser.getAttributeValue(null, "pinMinimum");
        if (attributeValue6 != null) {
            agentInfo.pinMinLength = Integer.parseInt(attributeValue6);
        } else {
            Log.d("UcmAgentWrapper", "pinMinLength in xml is null");
            agentInfo.pinMinLength = 4;
        }
        String attributeValue7 = xmlResourceParser.getAttributeValue(null, "pinMaximum");
        if (attributeValue7 != null) {
            agentInfo.pinMaxLength = Integer.parseInt(attributeValue7);
        } else {
            Log.d("UcmAgentWrapper", "pinMaxLength in xml is null");
            agentInfo.pinMaxLength = 6;
        }
        String attributeValue8 = xmlResourceParser.getAttributeValue(null, "pukMinimum");
        if (attributeValue8 != null) {
            agentInfo.pukMinLength = Integer.parseInt(attributeValue8);
        } else {
            Log.d("UcmAgentWrapper", "pukMinLength in xml is null");
            agentInfo.pukMinLength = 8;
        }
        String attributeValue9 = xmlResourceParser.getAttributeValue(null, "pukMaximum");
        if (attributeValue9 != null) {
            agentInfo.pukMaxLength = Integer.parseInt(attributeValue9);
        } else {
            Log.d("UcmAgentWrapper", "pukMaxLength in xml is null");
            agentInfo.pukMaxLength = 20;
        }
        String attributeValue10 = xmlResourceParser.getAttributeValue(null, "pinRetrycount");
        if (attributeValue10 != null) {
            agentInfo.authMaxCnt = Integer.parseInt(attributeValue10);
        } else {
            Log.d("UcmAgentWrapper", "authMaxCnt in xml is null");
            agentInfo.authMaxCnt = 5;
        }
        String attributeValue11 = xmlResourceParser.getAttributeValue(null, "checkMode");
        if (attributeValue11 != null) {
            agentInfo.authMode = Integer.parseInt(attributeValue11);
        } else {
            Log.d("UcmAgentWrapper", "authMode in xml is null");
            agentInfo.authMode = 0;
        }
        agentInfo.isGeneratePasswordAvailable = "true".equals(xmlResourceParser.getAttributeValue(null, "isGeneratePasswordSupport"));
        agentInfo.isODESupport = "true".equals(xmlResourceParser.getAttributeValue(null, "isODESupport"));
        String attributeValue12 = xmlResourceParser.getAttributeValue(null, "settingsActivity");
        if (attributeValue12 != null && attributeValue12.indexOf(47) < 0) {
            attributeValue12 = resolveInfo.serviceInfo.packageName + "/" + attributeValue12;
        }
        agentInfo.settingsComponentName = attributeValue12 == null ? null : ComponentName.unflattenFromString(attributeValue12);
        agentInfo.storageType = xmlResourceParser.getAttributeValue(null, "storageType");
        agentInfo.enabledSCP = xmlResourceParser.getAttributeValue(null, "enabledSCP");
        String attributeValue13 = xmlResourceParser.getAttributeValue(null, "enabledWrap");
        if (attributeValue13 != null) {
            agentInfo.enabledWrap = Integer.parseInt(attributeValue13);
        } else {
            Log.d("UcmAgentWrapper", "enabledWrap in xml is null");
            agentInfo.enabledWrap = 0;
        }
        Log.d("UcmAgentWrapper", agentInfo.toString());
        String attributeValue14 = xmlResourceParser.getAttributeValue(null, "AID");
        if (attributeValue14 == null || true == attributeValue14.isEmpty()) {
            Log.d("UcmAgentWrapper", "AID in xml is null");
            agentInfo.AID = null;
        } else {
            agentInfo.AID = hexStringToByteArray(attributeValue14);
        }
        String attributeValue15 = xmlResourceParser.getAttributeValue(null, "isPUKSupported");
        if (attributeValue15 == null || attributeValue15.length() <= 0 || !"false".equals(attributeValue15)) {
            agentInfo.isPUKSupported = true;
        } else {
            agentInfo.isPUKSupported = false;
        }
        String attributeValue16 = xmlResourceParser.getAttributeValue(null, "supportSign");
        if (attributeValue16 == null || attributeValue16.length() <= 0 || !"true".equals(attributeValue16)) {
            agentInfo.supportSign = false;
        } else {
            agentInfo.supportSign = true;
        }
        String attributeValue17 = xmlResourceParser.getAttributeValue(null, "supportKnoxVersion");
        String attributeValue18 = xmlResourceParser.getAttributeValue(null, "supportPinConfiguration");
        if (attributeValue17 == null && (attributeValue18 == null || attributeValue18.length() <= 0 || !"true".equals(attributeValue18))) {
            agentInfo.supportPinConfiguration = false;
            attributeValue = xmlResourceParser.getAttributeValue(null, "isSupportChangePin");
            if (attributeValue == null && "true".equals(attributeValue)) {
                agentInfo.isSupportChangePin = true;
            } else {
                agentInfo.isSupportChangePin = false;
            }
            attributeValue2 = xmlResourceParser.getAttributeValue(null, "isSupportChangePinWithPassword");
            if (attributeValue2 == null && "true".equals(attributeValue2)) {
                agentInfo.isSupportChangePinWithPassword = true;
            } else {
                agentInfo.isSupportChangePinWithPassword = false;
            }
            attributeValue3 = xmlResourceParser.getAttributeValue(null, "isSupportBiometricForUCM");
            if (attributeValue3 == null && "true".equals(attributeValue3)) {
                agentInfo.isSupportBiometricForUCM = true;
            } else {
                agentInfo.isSupportBiometricForUCM = false;
            }
            xmlResourceParser.close();
            e = null;
            if (e == null) {
                Log.w("UcmAgentWrapper", "Error parsing : " + resolveInfo.serviceInfo.packageName, e);
                return null;
            }
            Log.d("UcmAgentWrapper", "agentInfo: " + agentInfo.toString());
            return agentInfo;
        }
        agentInfo.supportPinConfiguration = true;
        attributeValue = xmlResourceParser.getAttributeValue(null, "isSupportChangePin");
        if (attributeValue == null) {
        }
        agentInfo.isSupportChangePin = false;
        attributeValue2 = xmlResourceParser.getAttributeValue(null, "isSupportChangePinWithPassword");
        if (attributeValue2 == null) {
        }
        agentInfo.isSupportChangePinWithPassword = false;
        attributeValue3 = xmlResourceParser.getAttributeValue(null, "isSupportBiometricForUCM");
        if (attributeValue3 == null) {
        }
        agentInfo.isSupportBiometricForUCM = false;
        xmlResourceParser.close();
        e = null;
        if (e == null) {
        }
    }

    public static byte[] hexStringToByteArray(String str) {
        if (DEBUG) {
            Log.d("UcmAgentWrapper", "hexStringToByteArray : " + str);
        }
        if (str == null || str.isEmpty()) {
            Log.d("UcmAgentWrapper", "Input value is empty");
            return null;
        }
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public final class AgentInfo {
        public byte[] AID;
        public String agentId;
        public int authMaxCnt;
        public int authMode;
        public String configuratorList;
        public String enabledSCP;
        public int enabledWrap;
        public boolean enforceManagement;
        public int iconResId;

        /* renamed from: id */
        public String f1778id;
        public boolean isDetachable;
        public boolean isGeneratePasswordAvailable;
        public boolean isHardwareBacked;
        public boolean isManageable;
        public boolean isODESupport;
        public boolean isPUKSupported;
        public boolean isReadOnly;
        public boolean isSupportBiometricForUCM;
        public boolean isSupportChangePin;
        public boolean isSupportChangePinWithPassword;
        public String packageName;
        public int pinMaxLength;
        public int pinMinLength;
        public int pukMaxLength;
        public int pukMinLength;
        public boolean reqUserVerification;
        public int serviceUid;
        public ComponentName settingsComponentName;
        public String storageType;
        public String summary;
        public boolean supportPinConfiguration;
        public boolean supportSign;
        public String title;
        public String vendorId;

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("system unique id:" + this.f1778id);
            stringBuffer.append(", agentId:" + this.agentId);
            stringBuffer.append(", summary:" + this.summary);
            stringBuffer.append(", title:" + this.title);
            stringBuffer.append(", vendorId:" + this.vendorId);
            stringBuffer.append(", isDetachable:" + this.isDetachable);
            stringBuffer.append(", reqUserVerification:" + this.reqUserVerification);
            stringBuffer.append(", iconResId:" + this.iconResId);
            stringBuffer.append(", isHardwareBacked:" + this.isHardwareBacked);
            stringBuffer.append(", pinMinLength:" + this.pinMinLength);
            stringBuffer.append(", pinMaxLength:" + this.pinMaxLength);
            stringBuffer.append(", authMaxCnt:" + this.authMaxCnt);
            stringBuffer.append(", authMode:" + this.authMode);
            stringBuffer.append(", isGeneratePasswordAvailable:" + this.isGeneratePasswordAvailable);
            stringBuffer.append(", isODESupport:" + this.isODESupport);
            stringBuffer.append(", storageType:" + this.storageType);
            stringBuffer.append(", enabledSCP:" + this.enabledSCP);
            stringBuffer.append(", enabledWrap:" + this.enabledWrap);
            if (this.AID != null) {
                try {
                    stringBuffer.append(", AID:" + new String(this.AID, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                stringBuffer.append(", AID: null");
            }
            stringBuffer.append(", isManageable:" + this.isManageable);
            stringBuffer.append(", enforceManagement:" + this.enforceManagement);
            stringBuffer.append(", configuratorList:" + this.configuratorList);
            stringBuffer.append(", serviceUid:" + this.serviceUid);
            stringBuffer.append(", packageName:" + this.packageName);
            stringBuffer.append(", isPUKSupported:" + this.isPUKSupported);
            stringBuffer.append(", supportSign:" + this.supportSign);
            stringBuffer.append(", isSupportChangePin:" + this.isSupportChangePin);
            stringBuffer.append(", isSupportChangePinWithPassword:" + this.isSupportChangePinWithPassword);
            stringBuffer.append(", supportPinConfiguration:" + this.supportPinConfiguration);
            stringBuffer.append(", isSupportBiometricForUCM:" + this.isSupportBiometricForUCM);
            return stringBuffer.toString();
        }
    }
}
