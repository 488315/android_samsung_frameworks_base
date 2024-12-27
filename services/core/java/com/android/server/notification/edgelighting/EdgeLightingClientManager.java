package com.android.server.notification.edgelighting;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Pair;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.edge.IEdgeLightingCallback;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class EdgeLightingClientManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public int mCondition;
    public final Context mContext;
    public final EdgeLightingSettingObserver mEdgeLightingSettingObserver;
    public final AnonymousClass1 mHandler;
    public boolean mIsConnectedMode = false;
    public boolean mNeedToRecheckSetting = true;
    public final List mEdgeLightingList = new ArrayList();
    public final ArrayList mHosts = new ArrayList();
    public final EdgeLightingListenerManager mEdgeLightingListenerManager =
            new EdgeLightingListenerManager();

    /* renamed from: com.android.server.notification.edgelighting.EdgeLightingClientManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends Handler {
        public final /* synthetic */ int $r8$classId = 1;
        public final /* synthetic */ Object this$0;

        public AnonymousClass1(EdgeLightingHost edgeLightingHost, Looper looper) {
            super(looper);
            this.this$0 = edgeLightingHost;
        }

        public AnonymousClass1(EdgeLightingClientManager edgeLightingClientManager) {
            this.this$0 = edgeLightingClientManager;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Object obj = this.this$0;
            switch (this.$r8$classId) {
                case 0:
                    super.handleMessage(message);
                    int i = message.arg1;
                    Pair pair = (Pair) message.obj;
                    Intent intent = new Intent();
                    intent.setClassName(
                            Constants.SYSTEMUI_PACKAGE_NAME,
                            "com.android.systemui.edgelighting.EdgeLightingService");
                    intent.putExtra("packagename", (String) pair.first);
                    intent.putExtra("info", (Parcelable) pair.second);
                    intent.putExtra("reason", i);
                    boolean z = EdgeLightingClientManager.DEBUG;
                    Slog.d(
                            "EdgeLightingClientManager",
                            "startService packagename=" + ((String) pair.first) + ",reason=" + i);
                    try {
                        ((EdgeLightingClientManager) obj)
                                .mContext.startServiceAsUser(intent, UserHandle.SEM_OWNER);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                default:
                    super.handleMessage(message);
                    int i2 = message.what;
                    EdgeLightingHost edgeLightingHost = (EdgeLightingHost) obj;
                    if (i2 == 1) {
                        Object obj2 = message.obj;
                        if (obj2 instanceof EdgeLightingHost.HostLightingInfo) {
                            EdgeLightingHost.HostLightingInfo hostLightingInfo =
                                    (EdgeLightingHost.HostLightingInfo) obj2;
                            String str = hostLightingInfo.mPackageName;
                            SemEdgeLightingInfo semEdgeLightingInfo = hostLightingInfo.mInfo;
                            int i3 = message.arg1;
                            edgeLightingHost.getClass();
                            try {
                                IEdgeLightingCallback asInterface =
                                        IEdgeLightingCallback.Stub.asInterface(
                                                edgeLightingHost.token);
                                if (asInterface != null) {
                                    asInterface.onStartEdgeLighting(str, semEdgeLightingInfo, i3);
                                    break;
                                }
                            } catch (RemoteException e2) {
                                boolean z2 = EdgeLightingClientManager.DEBUG;
                                Slog.e(
                                        "EdgeLightingClientManager",
                                        "onStartEdgeLighting : RemoteException : ",
                                        e2);
                            }
                        }
                    } else if (i2 == 2) {
                        Object obj3 = message.obj;
                        if (obj3 instanceof String) {
                            String str2 = (String) obj3;
                            int i4 = message.arg1;
                            edgeLightingHost.getClass();
                            try {
                                IEdgeLightingCallback asInterface2 =
                                        IEdgeLightingCallback.Stub.asInterface(
                                                edgeLightingHost.token);
                                if (asInterface2 != null) {
                                    asInterface2.onStopEdgeLighting(str2, i4);
                                    break;
                                }
                            } catch (RemoteException e3) {
                                boolean z3 = EdgeLightingClientManager.DEBUG;
                                Slog.e(
                                        "EdgeLightingClientManager",
                                        "onStopEdgeLighting : RemoteException : ",
                                        e3);
                                return;
                            }
                        }
                    } else if (i2 == 3) {
                        boolean z4 = message.arg1 == 1;
                        edgeLightingHost.getClass();
                        try {
                            IEdgeLightingCallback asInterface3 =
                                    IEdgeLightingCallback.Stub.asInterface(edgeLightingHost.token);
                            if (asInterface3 != null) {
                                asInterface3.onScreenChanged(z4);
                                break;
                            }
                        } catch (RemoteException e4) {
                            boolean z5 = EdgeLightingClientManager.DEBUG;
                            Slog.e(
                                    "EdgeLightingClientManager",
                                    "onScreenChanged : RemoteException : ",
                                    e4);
                            return;
                        }
                    }
                    break;
            }
        }
    }

    public final class EdgeLightingHost implements IBinder.DeathRecipient {
        public final ComponentName component;
        public int condition;
        public final AnonymousClass1 mHandler;
        public final int pid;
        public final IBinder token;
        public final int uid;

        public final class HostLightingInfo {
            public SemEdgeLightingInfo mInfo;
            public String mPackageName;
        }

        public EdgeLightingHost(
                IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            this.mHandler =
                    new AnonymousClass1(this, EdgeLightingClientManager.this.mHandler.getLooper());
            this.token = iBinder;
            this.component = componentName;
            this.condition = i;
            this.pid = i2;
            this.uid = i3;
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    boolean z = EdgeLightingClientManager.DEBUG;
                    Slog.e("EdgeLightingClientManager", "EdgeLightingHost : linkToDeath error");
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = EdgeLightingClientManager.DEBUG;
            Slog.v(
                    "EdgeLightingClientManager",
                    "binderDied : component = " + this.component.toShortString());
            synchronized (EdgeLightingClientManager.this.mEdgeLightingList) {
                try {
                    int size =
                            ((ArrayList) EdgeLightingClientManager.this.mEdgeLightingList).size();
                    ((ArrayList) EdgeLightingClientManager.this.mEdgeLightingList).clear();
                    if (size != 0
                            && ((ArrayList) EdgeLightingClientManager.this.mEdgeLightingList).size()
                                    == 0) {
                        EdgeLightingClientManager.this.mEdgeLightingListenerManager
                                .stopEdgeLighting();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mHandler.removeCallbacksAndMessages(null);
            synchronized (EdgeLightingClientManager.this.mHosts) {
                EdgeLightingClientManager.this.mHosts.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
            EdgeLightingHistory.getInstance()
                    .updateHostHistory(this.component.getPackageName(), "binderDied.");
            if (EdgeLightingClientManager.this.mIsConnectedMode) {
                final String packageName = this.component.getPackageName();
                this.mHandler.postDelayed(
                        new Runnable() { // from class:
                                         // com.android.server.notification.edgelighting.EdgeLightingClientManager.EdgeLightingHost.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                EdgeLightingHost edgeLightingHost = EdgeLightingHost.this;
                                String str = packageName;
                                edgeLightingHost.getClass();
                                if (Constants.SYSTEMUI_PACKAGE_NAME.equals(str)
                                        && Settings.System.getIntForUser(
                                                        EdgeLightingClientManager.this.mContext
                                                                .getContentResolver(),
                                                        "edge_lighting",
                                                        1,
                                                        -2)
                                                == 1) {
                                    boolean z2 = EdgeLightingClientManager.DEBUG;
                                    Slog.v("EdgeLightingClientManager", "startEdgeLightingService");
                                    EdgeLightingClientManager.this.createEdgeLightingService(
                                            UserHandle.SEM_OWNER);
                                }
                            }
                        },
                        1000L);
            }
        }

        public final void onStartEdgeLighting(
                String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingClientManager.DEBUG) {
                boolean z = EdgeLightingClientManager.DEBUG;
                StringBuilder sb = new StringBuilder("onStartEdgeLighting pkg = ");
                sb.append(str);
                sb.append(", info = ");
                sb.append(semEdgeLightingInfo);
                sb.append(", reason = ");
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        sb, i, "EdgeLightingClientManager");
            }
            if (this.token == null) {
                Slog.w("EdgeLightingClientManager", "onStartEdgeLighting : token is null");
                return;
            }
            AnonymousClass1 anonymousClass1 = this.mHandler;
            HostLightingInfo hostLightingInfo = new HostLightingInfo();
            hostLightingInfo.mPackageName = str;
            hostLightingInfo.mInfo = semEdgeLightingInfo;
            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(1, i, 0, hostLightingInfo));
        }

        public final void onStopEdgeLighting(int i, String str) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingClientManager.DEBUG) {
                boolean z = EdgeLightingClientManager.DEBUG;
                Slog.d(
                        "EdgeLightingClientManager",
                        "onStopEdgeLighting pkg = " + str + ", reason = " + i);
            }
            if (this.token == null) {
                Slog.w("EdgeLightingClientManager", "onStopEdgeLighting : token is null");
            } else {
                AnonymousClass1 anonymousClass1 = this.mHandler;
                anonymousClass1.sendMessage(anonymousClass1.obtainMessage(2, i, 0, str));
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("  [Host: component:(");
            sb.append(this.component.toShortString());
            sb.append(") condition:(");
            sb.append(this.condition);
            sb.append(") pid:(");
            sb.append(this.pid);
            sb.append(") uid:(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")]");
        }
    }

    public final class EdgeLightingSettingObserver extends ContentObserver {
        public final Uri EDGE_LIGHTING_SETTING;
        public final Uri EDGE_LIGHTING_SHOW_CONDITION;
        public final String defaultCarrierEdgeLighting;
        public final String defaultEdgeLighting;
        public int mDefaultValue;
        public boolean mLastEnabled;

        public EdgeLightingSettingObserver(AnonymousClass1 anonymousClass1) {
            super(anonymousClass1);
            this.mLastEnabled = false;
            Uri uriFor = Settings.System.getUriFor("edge_lighting");
            this.EDGE_LIGHTING_SETTING = uriFor;
            Uri uriFor2 = Settings.System.getUriFor("edge_lighting_show_condition");
            this.EDGE_LIGHTING_SHOW_CONDITION = uriFor2;
            String string =
                    SemCscFeature.getInstance()
                            .getString("CscFeature_Framework_ConfigDefStatusEdgeLighting");
            this.defaultEdgeLighting = string;
            String string2 =
                    SemCarrierFeature.getInstance()
                            .getString(
                                    0,
                                    "CarrierFeature_SystemUI_ConfigDefStatusEdgeLighting",
                                    "",
                                    false);
            this.defaultCarrierEdgeLighting = string2;
            this.mDefaultValue =
                    ((string == null || !string.contains("-defaulton"))
                                    && (string2 == null || !string2.contains("-defaulton")))
                            ? 1
                            : 0;
            this.mLastEnabled =
                    Settings.System.getIntForUser(
                                    EdgeLightingClientManager.this.mContext.getContentResolver(),
                                    "edge_lighting",
                                    this.mDefaultValue,
                                    -2)
                            == 1;
            ContentResolver contentResolver =
                    EdgeLightingClientManager.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(uriFor, false, this, -1);
            contentResolver.registerContentObserver(uriFor2, false, this, -1);
            boolean z = EdgeLightingClientManager.DEBUG;
            StringBuilder sb = new StringBuilder("EdgeLightingSettingObserver : mLastEnabled = ");
            sb.append(this.mLastEnabled);
            sb.append(" mDefaultValue = ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    sb, this.mDefaultValue, "EdgeLightingClientManager");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null || this.EDGE_LIGHTING_SETTING.equals(uri)) {
                boolean z2 =
                        Settings.System.getIntForUser(
                                        EdgeLightingClientManager.this.mContext
                                                .getContentResolver(),
                                        "edge_lighting",
                                        this.mDefaultValue,
                                        -2)
                                == 1;
                boolean z3 = EdgeLightingClientManager.DEBUG;
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "EdgeLightingClientManager",
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "onChange - edge_lighting : newValue = ", " mLastEnabled = ", z2),
                        this.mLastEnabled);
                if (z2 != this.mLastEnabled) {
                    this.mLastEnabled = z2;
                    if (z2) {
                        Intent intent = new Intent();
                        intent.setClassName(
                                Constants.SYSTEMUI_PACKAGE_NAME,
                                "com.android.systemui.edgelighting.EdgeLightingService");
                        try {
                            EdgeLightingClientManager.this.mContext.startServiceAsUser(
                                    intent, UserHandle.SEM_OWNER);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (uri == null || this.EDGE_LIGHTING_SHOW_CONDITION.equals(uri)) {
                int intForUser =
                        Settings.System.getIntForUser(
                                EdgeLightingClientManager.this.mContext.getContentResolver(),
                                "edge_lighting_show_condition",
                                0,
                                -2);
                if (intForUser != 0) {
                    if (intForUser == 1) {
                        EdgeLightingClientManager.this.mCondition = 1;
                    } else if (intForUser != 2) {
                        EdgeLightingClientManager.this.mCondition = 0;
                    }
                    boolean z4 = EdgeLightingClientManager.DEBUG;
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "onChange - edge_lighting_show_condition : mCondition = "),
                            EdgeLightingClientManager.this.mCondition,
                            "EdgeLightingClientManager");
                }
                EdgeLightingClientManager.this.mCondition = 3;
                boolean z42 = EdgeLightingClientManager.DEBUG;
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder(
                                "onChange - edge_lighting_show_condition : mCondition = "),
                        EdgeLightingClientManager.this.mCondition,
                        "EdgeLightingClientManager");
            }
        }

        public final void recheckEdgeLightingDefaultValue() {
            String str;
            String str2 = this.defaultEdgeLighting;
            this.mDefaultValue =
                    ((str2 == null || !str2.contains("-defaulton"))
                                    && ((str = this.defaultCarrierEdgeLighting) == null
                                            || !str.contains("-defaulton")))
                            ? 1
                            : 0;
            boolean z = EdgeLightingClientManager.DEBUG;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("recheckEdgeLightingDefaultValue : mDefaultValue = "),
                    this.mDefaultValue,
                    "EdgeLightingClientManager");
            try {
                this.mLastEnabled =
                        Settings.System.getIntForUser(
                                        EdgeLightingClientManager.this.mContext
                                                .getContentResolver(),
                                        "edge_lighting",
                                        -2)
                                == 1;
                Slog.d(
                        "EdgeLightingClientManager",
                        "recheckEdgeLightingDefaultValue : mLastEnabled = " + this.mLastEnabled);
            } catch (Settings.SettingNotFoundException unused) {
                this.mLastEnabled = this.mDefaultValue == 1;
                Settings.System.putIntForUser(
                        EdgeLightingClientManager.this.mContext.getContentResolver(),
                        "edge_lighting",
                        this.mDefaultValue,
                        -2);
                boolean z2 = EdgeLightingClientManager.DEBUG;
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "EdgeLightingClientManager",
                        new StringBuilder(
                                "recheckEdgeLightingDefaultValue - SettingNotFoundException :"
                                    + " mLastEnabled = "),
                        this.mLastEnabled);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0034, code lost:

       if (r5 != 2) goto L12;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public EdgeLightingClientManager(android.content.Context r5) {
        /*
            r4 = this;
            r4.<init>()
            r0 = 0
            r4.mIsConnectedMode = r0
            r1 = 1
            r4.mNeedToRecheckSetting = r1
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r4.mEdgeLightingList = r2
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r4.mHosts = r2
            r4.mContext = r5
            com.android.server.notification.edgelighting.EdgeLightingListenerManager r2 = new com.android.server.notification.edgelighting.EdgeLightingListenerManager
            r2.<init>()
            r4.mEdgeLightingListenerManager = r2
            android.content.ContentResolver r5 = r5.getContentResolver()
            r2 = -2
            java.lang.String r3 = "edge_lighting_show_condition"
            int r5 = android.provider.Settings.System.getIntForUser(r5, r3, r0, r2)
            r2 = -1
            if (r5 != r2) goto L2f
            goto L3a
        L2f:
            if (r5 == 0) goto L39
            if (r5 == r1) goto L37
            r1 = 2
            if (r5 == r1) goto L39
            goto L3a
        L37:
            r0 = r1
            goto L3a
        L39:
            r0 = 3
        L3a:
            r4.mCondition = r0
            com.android.server.notification.edgelighting.EdgeLightingClientManager$1 r5 = new com.android.server.notification.edgelighting.EdgeLightingClientManager$1
            r5.<init>(r4)
            r4.mHandler = r5
            com.android.server.notification.edgelighting.EdgeLightingClientManager$EdgeLightingSettingObserver r0 = new com.android.server.notification.edgelighting.EdgeLightingClientManager$EdgeLightingSettingObserver
            r0.<init>(r5)
            r4.mEdgeLightingSettingObserver = r0
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.edgelighting.EdgeLightingClientManager.<init>(android.content.Context):void");
    }

    public final void createEdgeLightingService(UserHandle userHandle) {
        if (this.mIsConnectedMode) {
            Intent intent = new Intent();
            intent.setClassName(
                    Constants.SYSTEMUI_PACKAGE_NAME,
                    "com.android.systemui.edgelighting.EdgeLightingService");
            try {
                this.mContext.startServiceAsUser(intent, userHandle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final int getEdgeLightingCondition() {
        synchronized (this.mHosts) {
            try {
                if (this.mHosts.size() < 1) {
                    if (this.mIsConnectedMode) {
                        return 0;
                    }
                    return this.mCondition;
                }
                Iterator it = this.mHosts.iterator();
                if (!it.hasNext()) {
                    return 0;
                }
                return ((EdgeLightingHost) it.next()).condition;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isAvailableEdgeLighting(int i) {
        synchronized (this.mHosts) {
            try {
                if (this.mNeedToRecheckSetting) {
                    this.mNeedToRecheckSetting = false;
                    this.mEdgeLightingSettingObserver.recheckEdgeLightingDefaultValue();
                }
                if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                    Slog.d(
                            "EdgeLightingClientManager",
                            "isAvailableEdgeLighting: condition="
                                    + i
                                    + " mCondition="
                                    + this.mCondition
                                    + " mIsConnected="
                                    + this.mIsConnectedMode
                                    + this.mHosts.size());
                }
                if (!this.mEdgeLightingSettingObserver.mLastEnabled) {
                    return false;
                }
                if (!this.mIsConnectedMode) {
                    int i2 = this.mCondition;
                    return i2 == 7 || (i2 & i) != 0;
                }
                if (this.mHosts.size() < 1) {
                    return false;
                }
                if (i == 7) {
                    return true;
                }
                Iterator it = this.mHosts.iterator();
                while (it.hasNext()) {
                    int i3 = ((EdgeLightingHost) it.next()).condition;
                    if (i3 == 7 || (i3 & i) != 0) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onScreenChanged(boolean z) {
        synchronized (this.mHosts) {
            try {
                Iterator it = this.mHosts.iterator();
                while (it.hasNext()) {
                    EdgeLightingHost edgeLightingHost = (EdgeLightingHost) it.next();
                    if (edgeLightingHost != null) {
                        edgeLightingHost.getClass();
                        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    "onScreenChanged on = ", "EdgeLightingClientManager", z);
                        }
                        if (edgeLightingHost.token == null) {
                            Slog.w("EdgeLightingClientManager", "onScreenChanged : token is null");
                        } else {
                            AnonymousClass1 anonymousClass1 = edgeLightingHost.mHandler;
                            anonymousClass1.sendMessage(
                                    anonymousClass1.obtainMessage(3, z ? 1 : 0, 0));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startEdgeLightingInternal(
            String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            packageManager.getApplicationInfo(str, 0);
            if (packageManager.isPackageSuspended(str)) {
                EdgeLightingHistory.getInstance()
                        .updateRejectHistory(
                                "startEdgeLightingInternal() reject edge lighting because"
                                    + " application suspend package="
                                        + str);
                return;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        synchronized (this.mHosts) {
            try {
                if (!this.mIsConnectedMode && this.mHosts.size() < 1) {
                    startEdgeLightingService(str, semEdgeLightingInfo, i);
                }
                Iterator it = this.mHosts.iterator();
                while (it.hasNext()) {
                    EdgeLightingHost edgeLightingHost = (EdgeLightingHost) it.next();
                    if (edgeLightingHost != null) {
                        edgeLightingHost.onStartEdgeLighting(str, semEdgeLightingInfo, i);
                    }
                }
                EdgeLightingHistory.getInstance()
                        .updateEdgeLightingHistory(
                                "start : "
                                        + str
                                        + " : "
                                        + semEdgeLightingInfo.toString()
                                        + ","
                                        + i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startEdgeLightingService(
            String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            StringBuilder sb = new StringBuilder("startEdgeLightingService : pkg = ");
            sb.append(str);
            sb.append(", info = ");
            sb.append(semEdgeLightingInfo);
            sb.append(", reason = ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, i, "EdgeLightingClientManager");
        }
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.sendMessage(
                anonymousClass1.obtainMessage(0, i, 0, new Pair(str, semEdgeLightingInfo)));
    }

    public final void stopEdgeLightingInternal(int i, String str) {
        synchronized (this.mHosts) {
            try {
                if (!this.mIsConnectedMode && this.mHosts.size() < 1) {
                    Slog.e(
                            "EdgeLightingClientManager",
                            "stopEdgeLightingInternal mHosts is null. pkg=" + str + ",reason=" + i);
                }
                Iterator it = this.mHosts.iterator();
                while (it.hasNext()) {
                    EdgeLightingHost edgeLightingHost = (EdgeLightingHost) it.next();
                    if (edgeLightingHost != null) {
                        edgeLightingHost.onStopEdgeLighting(i, str);
                    }
                }
                EdgeLightingHistory.getInstance()
                        .updateEdgeLightingHistory("stop : " + str + "," + i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
