package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.app.BroadcastOptions;
import android.app.IApplicationThread;
import android.app.compat.CompatChanges;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerWhitelistManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.SafeActivityOptions;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class PendingIntentRecord extends IIntentSender.Stub {
    public final PendingIntentController controller;
    public final Key key;
    public String lastTag;
    public String lastTagPrefix;
    public ArrayMap mAllowlistDuration;
    public RemoteCallbackList mCancelCallbacks;
    public String stringName;
    public final int uid;
    public boolean sent = false;
    public boolean canceled = false;
    public int canceledFromUID = -1;
    public int canceledFromPID = -1;
    public ArraySet mAllowBgActivityStartsForActivitySender = new ArraySet();
    public ArraySet mAllowBgActivityStartsForBroadcastSender = new ArraySet();
    public ArraySet mAllowBgActivityStartsForServiceSender = new ArraySet();
    public final WeakReference ref = new WeakReference(this);

    public final class Key {
        public final IBinder activity;
        public Intent[] allIntents;
        public String[] allResolvedTypes;
        public final String featureId;
        public final int flags;
        public final int hashCode;
        public final SafeActivityOptions options;
        public final String packageName;
        public final int requestCode;
        public final Intent requestIntent;
        public final String requestResolvedType;
        public final int type;
        public final int userId;
        public final String who;

        public Key(int i, String str, String str2, IBinder iBinder, String str3, int i2, Intent[] intentArr, String[] strArr, int i3, SafeActivityOptions safeActivityOptions, int i4) {
            this.type = i;
            this.packageName = str;
            this.featureId = str2;
            this.activity = iBinder;
            this.who = str3;
            this.requestCode = i2;
            Intent intent = intentArr != null ? intentArr[intentArr.length - 1] : null;
            this.requestIntent = intent;
            String str4 = strArr != null ? strArr[strArr.length - 1] : null;
            this.requestResolvedType = str4;
            this.allIntents = intentArr;
            this.allResolvedTypes = strArr;
            this.flags = i3;
            this.options = safeActivityOptions;
            this.userId = i4;
            int i5 = ((((851 + i3) * 37) + i2) * 37) + i4;
            i5 = str3 != null ? (i5 * 37) + str3.hashCode() : i5;
            i5 = iBinder != null ? (i5 * 37) + iBinder.hashCode() : i5;
            i5 = intent != null ? (i5 * 37) + intent.filterHashCode() : i5;
            this.hashCode = ((((str4 != null ? (i5 * 37) + str4.hashCode() : i5) * 37) + (str != null ? str.hashCode() : 0)) * 37) + i;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                Key key = (Key) obj;
                if (this.type != key.type || this.userId != key.userId || !Objects.equals(this.packageName, key.packageName) || !Objects.equals(this.featureId, key.featureId) || this.activity != key.activity || !Objects.equals(this.who, key.who) || this.requestCode != key.requestCode) {
                    return false;
                }
                Intent intent = this.requestIntent;
                Intent intent2 = key.requestIntent;
                if (intent != intent2) {
                    if (intent != null) {
                        if (!intent.filterEquals(intent2)) {
                            return false;
                        }
                    } else if (intent2 != null) {
                        return false;
                    }
                }
                if (Objects.equals(this.requestResolvedType, key.requestResolvedType)) {
                    return this.flags == key.flags;
                }
                return false;
            } catch (ClassCastException unused) {
                return false;
            }
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return toSecureString(false);
        }

        public String toSecureString(boolean z) {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("Key{");
            sb.append(typeName());
            sb.append(" pkg=");
            sb.append(this.packageName);
            if (this.featureId != null) {
                str = "/" + this.featureId;
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(" intent=");
            Intent intent = this.requestIntent;
            sb.append(intent != null ? intent.toShortString(z, true, false, false) : "<null>");
            sb.append(" flags=0x");
            sb.append(Integer.toHexString(this.flags));
            sb.append(" u=");
            sb.append(this.userId);
            sb.append("} requestCode=");
            sb.append(this.requestCode);
            return sb.toString();
        }

        public String typeName() {
            int i = this.type;
            return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? Integer.toString(i) : "startForegroundService" : "startService" : "activityResult" : "startActivity" : "broadcastIntent";
        }
    }

    public final class TempAllowListDuration {
        public long duration;
        public String reason;
        public int reasonCode;
        public int type;

        public TempAllowListDuration(long j, int i, int i2, String str) {
            this.duration = j;
            this.type = i;
            this.reasonCode = i2;
            this.reason = str;
        }
    }

    public PendingIntentRecord(PendingIntentController pendingIntentController, Key key, int i) {
        this.controller = pendingIntentController;
        this.key = key;
        this.uid = i;
    }

    public void setAllowlistDurationLocked(IBinder iBinder, long j, int i, int i2, String str) {
        if (j > 0) {
            if (this.mAllowlistDuration == null) {
                this.mAllowlistDuration = new ArrayMap();
            }
            this.mAllowlistDuration.put(iBinder, new TempAllowListDuration(j, i, i2, str));
        } else {
            ArrayMap arrayMap = this.mAllowlistDuration;
            if (arrayMap != null) {
                arrayMap.remove(iBinder);
                if (this.mAllowlistDuration.size() <= 0) {
                    this.mAllowlistDuration = null;
                }
            }
        }
        this.stringName = null;
    }

    public void setAllowBgActivityStarts(IBinder iBinder, int i) {
        if (iBinder == null) {
            return;
        }
        if ((i & 1) != 0) {
            this.mAllowBgActivityStartsForActivitySender.add(iBinder);
        }
        if ((i & 2) != 0) {
            this.mAllowBgActivityStartsForBroadcastSender.add(iBinder);
        }
        if ((i & 4) != 0) {
            this.mAllowBgActivityStartsForServiceSender.add(iBinder);
        }
    }

    public void clearAllowBgActivityStarts(IBinder iBinder) {
        if (iBinder == null) {
            return;
        }
        this.mAllowBgActivityStartsForActivitySender.remove(iBinder);
        this.mAllowBgActivityStartsForBroadcastSender.remove(iBinder);
        this.mAllowBgActivityStartsForServiceSender.remove(iBinder);
    }

    public void registerCancelListenerLocked(IResultReceiver iResultReceiver) {
        if (this.mCancelCallbacks == null) {
            this.mCancelCallbacks = new RemoteCallbackList();
        }
        this.mCancelCallbacks.register(iResultReceiver);
    }

    public void unregisterCancelListenerLocked(IResultReceiver iResultReceiver) {
        RemoteCallbackList remoteCallbackList = this.mCancelCallbacks;
        if (remoteCallbackList == null) {
            return;
        }
        remoteCallbackList.unregister(iResultReceiver);
        if (this.mCancelCallbacks.getRegisteredCallbackCount() <= 0) {
            this.mCancelCallbacks = null;
        }
    }

    public RemoteCallbackList detachCancelListenersLocked() {
        RemoteCallbackList remoteCallbackList = this.mCancelCallbacks;
        this.mCancelCallbacks = null;
        return remoteCallbackList;
    }

    public void send(int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) {
        sendInner(null, i, intent, str, iBinder, iIntentReceiver, str2, null, null, 0, 0, 0, bundle);
    }

    public static boolean isPendingIntentBalAllowedByPermission(ActivityOptions activityOptions) {
        if (activityOptions == null) {
            return false;
        }
        return activityOptions.isPendingIntentBackgroundActivityLaunchAllowedByPermission();
    }

    public static BackgroundStartPrivileges getBackgroundStartPrivilegesAllowedByCaller(ActivityOptions activityOptions, int i, String str) {
        if (activityOptions == null) {
            return getDefaultBackgroundStartPrivileges(i, str);
        }
        return getBackgroundStartPrivilegesAllowedByCaller(activityOptions.toBundle(), i, str);
    }

    public static BackgroundStartPrivileges getBackgroundStartPrivilegesAllowedByCaller(Bundle bundle, int i, String str) {
        if (bundle == null || !bundle.containsKey("android.pendingIntent.backgroundActivityAllowed")) {
            return getDefaultBackgroundStartPrivileges(i, str);
        }
        if (bundle.getBoolean("android.pendingIntent.backgroundActivityAllowed")) {
            return BackgroundStartPrivileges.ALLOW_BAL;
        }
        return BackgroundStartPrivileges.NONE;
    }

    public static BackgroundStartPrivileges getDefaultBackgroundStartPrivileges(int i, String str) {
        boolean isChangeEnabled;
        if (UserHandle.getAppId(i) == 1000) {
            return BackgroundStartPrivileges.ALLOW_BAL;
        }
        if (str != null) {
            isChangeEnabled = CompatChanges.isChangeEnabled(244637991L, str, UserHandle.getUserHandleForUid(i));
        } else {
            isChangeEnabled = CompatChanges.isChangeEnabled(244637991L, i);
        }
        if (isChangeEnabled) {
            return BackgroundStartPrivileges.ALLOW_FGS;
        }
        return BackgroundStartPrivileges.ALLOW_BAL;
    }

    public int sendInner(IApplicationThread iApplicationThread, int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, IBinder iBinder2, String str3, int i2, int i3, int i4, Bundle bundle) {
        return sendInner(iApplicationThread, i, intent, str, iBinder, iIntentReceiver, str2, iBinder2, str3, i2, i3, i4, bundle, -1, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x0509  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int sendInner(IApplicationThread iApplicationThread, int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, IBinder iBinder2, String str3, int i2, int i3, int i4, Bundle bundle, int i5, int i6) {
        String str4;
        String[] strArr;
        int i7;
        int i8;
        Intent intent2;
        int i9;
        PendingIntentRecord pendingIntentRecord;
        int i10;
        int i11;
        int i12;
        int startActivityInPackage;
        Intent[] intentArr;
        boolean z;
        PendingIntentRecord pendingIntentRecord2 = this;
        if (intent != null) {
            intent.setDefusable(true);
        }
        if (bundle != null) {
            bundle.setDefusable(true);
        }
        synchronized (pendingIntentRecord2.controller.mLock) {
            if (pendingIntentRecord2.canceled) {
                Slog.d("ActivityManager", "Received intent " + pendingIntentRecord2.key.type + "," + Integer.toHexString(System.identityHashCode(this)) + " " + pendingIntentRecord2.key.toSecureString(true) + " is canceled from UID:" + pendingIntentRecord2.canceledFromUID + " PID:" + pendingIntentRecord2.canceledFromPID);
                return -96;
            }
            pendingIntentRecord2.sent = true;
            if ((pendingIntentRecord2.key.flags & 1073741824) != 0) {
                pendingIntentRecord2.controller.cancelIntentSender(pendingIntentRecord2, true);
            }
            Intent intent3 = pendingIntentRecord2.key.requestIntent != null ? new Intent(pendingIntentRecord2.key.requestIntent) : new Intent();
            Key key = pendingIntentRecord2.key;
            int i13 = key.flags;
            if ((67108864 & i13) != 0) {
                str4 = key.requestResolvedType;
            } else {
                if (intent != null) {
                    str4 = (intent3.fillIn(intent, i13) & 2) == 0 ? pendingIntentRecord2.key.requestResolvedType : str;
                    if (intent.getLaunchTaskIdForAliasManagedTarget() > 0) {
                        intent3.setLaunchTaskIdForAliasManagedTarget(intent.getLaunchTaskIdForAliasManagedTarget());
                        intent3.setComponent(intent.getComponent());
                        z = true;
                    } else {
                        z = false;
                    }
                    if (intent.getLaunchTaskIdForSingleInstancePerTask() > 0) {
                        intent3.setLaunchTaskIdForSingleInstancePerTask(intent.getLaunchTaskIdForSingleInstancePerTask());
                        z = true;
                    }
                    if (z && bundle != null) {
                        bundle.putBoolean("android:activity.startedFromWindowTypeLauncher", false);
                    }
                } else {
                    str4 = key.requestResolvedType;
                }
                int i14 = i3 & (-196);
                intent3.setFlags(((~i14) & intent3.getFlags()) | (i4 & i14));
            }
            String str5 = str4;
            boolean z2 = intent != null && intent.isRemoteAppLaunch();
            if (z2) {
                intent3.setRemoteAppLaunch(true);
            }
            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
            if (fromBundle != null) {
                intent3.addFlags(fromBundle.getPendingIntentLaunchFlags());
                if (pendingIntentRecord2.key.type == 1 && fromBundle.getLaunchDisplayId() == 2) {
                    try {
                        intent3.putExtra("LAUNCH_DISPLAY_ID", 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            boolean z3 = intent != null && intent.getIntExtra("LAUNCH_FROM_NOTIFICATION", -1) == 1;
            if (CoreRune.FW_APPLOCK && z3) {
                try {
                    intent3.putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            SafeActivityOptions safeActivityOptions = pendingIntentRecord2.key.options;
            if (safeActivityOptions == null) {
                safeActivityOptions = new SafeActivityOptions(fromBundle);
            } else {
                safeActivityOptions.setCallerOptions(fromBundle);
            }
            ArrayMap arrayMap = pendingIntentRecord2.mAllowlistDuration;
            Intent[] intentArr2 = null;
            TempAllowListDuration tempAllowListDuration = arrayMap != null ? (TempAllowListDuration) arrayMap.get(iBinder) : null;
            Key key2 = pendingIntentRecord2.key;
            if (key2.type != 2 || (intentArr = key2.allIntents) == null || intentArr.length <= 1) {
                strArr = null;
            } else {
                int length = intentArr.length;
                Intent[] intentArr3 = new Intent[length];
                int length2 = intentArr.length;
                String[] strArr2 = new String[length2];
                System.arraycopy(intentArr, 0, intentArr3, 0, intentArr.length);
                String[] strArr3 = pendingIntentRecord2.key.allResolvedTypes;
                if (strArr3 != null) {
                    System.arraycopy(strArr3, 0, strArr2, 0, strArr3.length);
                }
                if (z2) {
                    for (int i15 = 0; i15 < length - 1; i15++) {
                        Intent intent4 = intentArr3[i15];
                        if (intent4 != null) {
                            intent4.setRemoteAppLaunch(true);
                        }
                    }
                }
                intentArr3[length - 1] = intent3;
                strArr2[length2 - 1] = str5;
                strArr = strArr2;
                intentArr2 = intentArr3;
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            if (i5 <= 0 || i6 <= 0) {
                i7 = callingUid;
                i8 = callingPid;
            } else {
                i8 = i5;
                i7 = i6;
            }
            if (pendingIntentRecord2.key.type == 1) {
                pendingIntentRecord2.controller.mAmInternal.enforceBroadcastOptionsPermissions(bundle, i7);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (tempAllowListDuration != null) {
                    StringBuilder sb = new StringBuilder(64);
                    sb.append("setPendingIntentAllowlistDuration,reason:");
                    String str6 = tempAllowListDuration.reason;
                    if (str6 == null) {
                        str6 = "";
                    }
                    sb.append(str6);
                    sb.append(",pendingintent:");
                    UserHandle.formatUid(sb, i7);
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                    if (intent3.getAction() != null) {
                        sb.append(intent3.getAction());
                    } else if (intent3.getComponent() != null) {
                        intent3.getComponent().appendShortString(sb);
                    } else if (intent3.getData() != null) {
                        sb.append(intent3.getData().toSafeString());
                    }
                    intent2 = intent3;
                    pendingIntentRecord2.controller.mAmInternal.tempAllowlistForPendingIntent(i8, i7, pendingIntentRecord2.uid, tempAllowListDuration.duration, tempAllowListDuration.type, tempAllowListDuration.reasonCode, sb.toString());
                } else {
                    intent2 = intent3;
                    if (pendingIntentRecord2.key.type == 5 && bundle != null) {
                        BroadcastOptions broadcastOptions = new BroadcastOptions(bundle);
                        if (broadcastOptions.getTemporaryAppAllowlistDuration() > 0) {
                            pendingIntentRecord2.controller.mAmInternal.tempAllowlistForPendingIntent(i8, i7, pendingIntentRecord2.uid, broadcastOptions.getTemporaryAppAllowlistDuration(), broadcastOptions.getTemporaryAppAllowlistType(), broadcastOptions.getTemporaryAppAllowlistReasonCode(), broadcastOptions.getTemporaryAppAllowlistReason());
                        }
                    }
                }
                boolean z4 = iIntentReceiver != null;
                if (iIntentReceiver != null && iApplicationThread == null) {
                    Slog.w("ActivityManager", "Sending of " + intent + " from " + Binder.getCallingUid() + " requested resultTo without an IApplicationThread!", new Throwable());
                }
                int i16 = pendingIntentRecord2.key.userId;
                if (i16 == -2) {
                    i16 = pendingIntentRecord2.controller.mUserController.getCurrentOrTargetUserId();
                }
                int i17 = i16;
                Key key3 = pendingIntentRecord2.key;
                int i18 = key3.type;
                if (i18 != 1) {
                    if (i18 != 2) {
                        if (i18 == 3) {
                            Slog.d("ActivityManager", "Received ACTIVITY RESULT intent 0x" + Integer.toHexString(System.identityHashCode(this)) + " " + pendingIntentRecord2.key.toSecureString(true) + " from uid " + i7);
                            ActivityTaskManagerInternal activityTaskManagerInternal = pendingIntentRecord2.controller.mAtmInternal;
                            Key key4 = pendingIntentRecord2.key;
                            activityTaskManagerInternal.sendActivityResult(-1, key4.activity, key4.who, key4.requestCode, i, intent2);
                        } else if (i18 == 4 || i18 == 5) {
                            try {
                                BackgroundStartPrivileges backgroundStartPrivilegesForActivitySender = pendingIntentRecord2.getBackgroundStartPrivilegesForActivitySender(pendingIntentRecord2.mAllowBgActivityStartsForServiceSender, iBinder, bundle, i7);
                                ActivityManagerInternal activityManagerInternal = pendingIntentRecord2.controller.mAmInternal;
                                int i19 = pendingIntentRecord2.uid;
                                Key key5 = pendingIntentRecord2.key;
                                activityManagerInternal.startServiceInPackage(iApplicationThread, i19, intent2, str5, key5.type == 5, key5.packageName, key5.featureId, i17, backgroundStartPrivilegesForActivitySender);
                                Slog.d("ActivityManager", "Received SERVICE intent 0x" + Integer.toHexString(System.identityHashCode(this)) + " " + pendingIntentRecord2.key.toSecureString(true) + " from uid " + i7);
                            } catch (TransactionTooLargeException unused) {
                                pendingIntentRecord = pendingIntentRecord2;
                                intent = intent2;
                                i10 = -96;
                            } catch (RuntimeException e3) {
                                Slog.w("ActivityManager", "Unable to send startService intent", e3);
                            }
                        }
                        pendingIntentRecord = pendingIntentRecord2;
                        intent = intent2;
                        i9 = 0;
                    } else {
                        try {
                            Intent[] intentArr4 = key3.allIntents;
                            try {
                                if (intentArr4 != null && intentArr4.length > 1) {
                                    i11 = 0;
                                    SafeActivityOptions safeActivityOptions2 = safeActivityOptions;
                                    intent = intent2;
                                    i12 = i7;
                                    startActivityInPackage = pendingIntentRecord2.controller.mAtmInternal.startActivitiesInPackage(pendingIntentRecord2.uid, i8, i7, key3.packageName, key3.featureId, intentArr2, strArr, iBinder2, safeActivityOptions2, i17, false, this, pendingIntentRecord2.getBackgroundStartPrivilegesForActivitySender(iBinder));
                                } else {
                                    int i20 = i7;
                                    intent = intent2;
                                    i11 = 0;
                                    ActivityTaskManagerInternal activityTaskManagerInternal2 = pendingIntentRecord2.controller.mAtmInternal;
                                    int i21 = pendingIntentRecord2.uid;
                                    String str7 = key3.packageName;
                                    String str8 = key3.featureId;
                                    BackgroundStartPrivileges backgroundStartPrivilegesForActivitySender2 = pendingIntentRecord2.getBackgroundStartPrivilegesForActivitySender(iBinder);
                                    SafeActivityOptions safeActivityOptions3 = safeActivityOptions;
                                    i12 = i20;
                                    pendingIntentRecord2 = null;
                                    try {
                                        startActivityInPackage = activityTaskManagerInternal2.startActivityInPackage(i21, i8, i20, str7, str8, intent, str5, iBinder2, str3, i2, 0, safeActivityOptions3, i17, null, "PendingIntentRecord", false, this, backgroundStartPrivilegesForActivitySender2);
                                    } catch (RuntimeException e4) {
                                        e = e4;
                                        pendingIntentRecord2 = this;
                                        i10 = i11;
                                        Slog.w("ActivityManager", "Unable to send startActivity intent", e);
                                        pendingIntentRecord = pendingIntentRecord2;
                                        if (z4) {
                                        }
                                        return i10;
                                    }
                                }
                                i10 = startActivityInPackage;
                                try {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("Received ACTIVITY intent 0x");
                                    sb2.append(Integer.toHexString(System.identityHashCode(this)));
                                    sb2.append(" ");
                                    pendingIntentRecord2 = this;
                                    try {
                                        sb2.append(pendingIntentRecord2.key.toSecureString(true));
                                        sb2.append(" res=");
                                        sb2.append(i10);
                                        sb2.append(" from uid ");
                                        sb2.append(i12);
                                        Slog.d("ActivityManager", sb2.toString());
                                    } catch (RuntimeException e5) {
                                        e = e5;
                                        Slog.w("ActivityManager", "Unable to send startActivity intent", e);
                                        pendingIntentRecord = pendingIntentRecord2;
                                        if (z4) {
                                        }
                                        return i10;
                                    }
                                } catch (RuntimeException e6) {
                                    e = e6;
                                    pendingIntentRecord2 = this;
                                }
                            } catch (RuntimeException e7) {
                                e = e7;
                            }
                        } catch (RuntimeException e8) {
                            e = e8;
                            intent = intent2;
                            i11 = 0;
                        }
                        pendingIntentRecord = pendingIntentRecord2;
                    }
                    if (z4 && i10 != -96) {
                        try {
                            iIntentReceiver.performReceive(new Intent(intent), 0, (String) null, (Bundle) null, false, false, pendingIntentRecord.key.userId);
                        } catch (RemoteException unused2) {
                        }
                    }
                    return i10;
                }
                int i22 = i7;
                intent = intent2;
                i9 = 0;
                try {
                    BackgroundStartPrivileges backgroundStartPrivilegesForActivitySender3 = pendingIntentRecord2.getBackgroundStartPrivilegesForActivitySender(pendingIntentRecord2.mAllowBgActivityStartsForBroadcastSender, iBinder, bundle, i22);
                    ActivityManagerInternal activityManagerInternal2 = pendingIntentRecord2.controller.mAmInternal;
                    Key key6 = pendingIntentRecord2.key;
                    try {
                        int broadcastIntentInPackage = activityManagerInternal2.broadcastIntentInPackage(iApplicationThread, key6.packageName, key6.featureId, pendingIntentRecord2.uid, i22, i8, intent, str5, iApplicationThread, iIntentReceiver, i, (String) null, (Bundle) null, str2, bundle, iIntentReceiver != null, false, i17, backgroundStartPrivilegesForActivitySender3, (int[]) null);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Received BROADCAST intent 0x");
                        sb3.append(Integer.toHexString(System.identityHashCode(this)));
                        sb3.append(" ");
                        pendingIntentRecord = this;
                        try {
                            sb3.append(pendingIntentRecord.key.toSecureString(true));
                            sb3.append(" sent=");
                            sb3.append(broadcastIntentInPackage);
                            sb3.append(" from uid ");
                            sb3.append(i22);
                            Slog.d("ActivityManager", sb3.toString());
                            z4 = broadcastIntentInPackage == 0 ? false : z4;
                        } catch (RuntimeException e9) {
                            e = e9;
                            Slog.w("ActivityManager", "Unable to send startActivity intent", e);
                            i10 = i9;
                            if (z4) {
                            }
                            return i10;
                        }
                    } catch (RuntimeException e10) {
                        e = e10;
                        pendingIntentRecord = this;
                    }
                } catch (RuntimeException e11) {
                    e = e11;
                    pendingIntentRecord = pendingIntentRecord2;
                }
                i10 = i9;
                if (z4) {
                    iIntentReceiver.performReceive(new Intent(intent), 0, (String) null, (Bundle) null, false, false, pendingIntentRecord.key.userId);
                }
                return i10;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final BackgroundStartPrivileges getBackgroundStartPrivilegesForActivitySender(IBinder iBinder) {
        if (this.mAllowBgActivityStartsForActivitySender.contains(iBinder)) {
            return BackgroundStartPrivileges.allowBackgroundActivityStarts(iBinder);
        }
        return BackgroundStartPrivileges.NONE;
    }

    public final BackgroundStartPrivileges getBackgroundStartPrivilegesForActivitySender(ArraySet arraySet, IBinder iBinder, Bundle bundle, int i) {
        if (arraySet.contains(iBinder)) {
            return BackgroundStartPrivileges.allowBackgroundActivityStarts(iBinder);
        }
        if (this.uid != i && this.controller.mAtmInternal.isUidForeground(i)) {
            return getBackgroundStartPrivilegesAllowedByCaller(bundle, i, (String) null);
        }
        return BackgroundStartPrivileges.NONE;
    }

    public void finalize() {
        try {
            if (!this.canceled) {
                this.controller.f1632mH.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.am.PendingIntentRecord$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((PendingIntentRecord) obj).completeFinalize();
                    }
                }, this));
            }
        } finally {
            super/*java.lang.Object*/.finalize();
        }
    }

    public final void completeFinalize() {
        synchronized (this.controller.mLock) {
            if (((WeakReference) this.controller.mIntentSenderRecords.get(this.key)) == this.ref) {
                this.controller.mIntentSenderRecords.remove(this.key);
                this.controller.decrementUidStatLocked(this);
            }
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("uid=");
        printWriter.print(this.uid);
        printWriter.print(" packageName=");
        printWriter.print(this.key.packageName);
        printWriter.print(" featureId=");
        printWriter.print(this.key.featureId);
        printWriter.print(" type=");
        printWriter.print(this.key.typeName());
        printWriter.print(" flags=0x");
        printWriter.println(Integer.toHexString(this.key.flags));
        Key key = this.key;
        if (key.activity != null || key.who != null) {
            printWriter.print(str);
            printWriter.print("activity=");
            printWriter.print(this.key.activity);
            printWriter.print(" who=");
            printWriter.println(this.key.who);
        }
        Key key2 = this.key;
        if (key2.requestCode != 0 || key2.requestResolvedType != null) {
            printWriter.print(str);
            printWriter.print("requestCode=");
            printWriter.print(this.key.requestCode);
            printWriter.print(" requestResolvedType=");
            printWriter.println(this.key.requestResolvedType);
        }
        if (this.key.requestIntent != null) {
            printWriter.print(str);
            printWriter.print("requestIntent=");
            printWriter.println(this.key.requestIntent.toShortString(false, true, true, false));
        }
        if (this.sent || this.canceled) {
            printWriter.print(str);
            printWriter.print("sent=");
            printWriter.print(this.sent);
            printWriter.print(" canceled=");
            printWriter.println(this.canceled);
            if (this.canceledFromUID != -1 || this.canceledFromPID != -1) {
                printWriter.print(str);
                printWriter.print(" cancel uid=");
                printWriter.println(this.canceledFromUID);
                printWriter.print(str);
                printWriter.print(" cancel pid=");
                printWriter.println(this.canceledFromPID);
            }
        }
        if (this.mAllowlistDuration != null) {
            printWriter.print(str);
            printWriter.print("allowlistDuration=");
            for (int i = 0; i < this.mAllowlistDuration.size(); i++) {
                if (i != 0) {
                    printWriter.print(", ");
                }
                TempAllowListDuration tempAllowListDuration = (TempAllowListDuration) this.mAllowlistDuration.valueAt(i);
                printWriter.print(Integer.toHexString(System.identityHashCode(this.mAllowlistDuration.keyAt(i))));
                printWriter.print(XmlUtils.STRING_ARRAY_SEPARATOR);
                TimeUtils.formatDuration(tempAllowListDuration.duration, printWriter);
                printWriter.print("/");
                printWriter.print(tempAllowListDuration.type);
                printWriter.print("/");
                printWriter.print(PowerWhitelistManager.reasonCodeToString(tempAllowListDuration.reasonCode));
                printWriter.print("/");
                printWriter.print(tempAllowListDuration.reason);
            }
            printWriter.println();
        }
        if (this.mCancelCallbacks != null) {
            printWriter.print(str);
            printWriter.println("mCancelCallbacks:");
            for (int i2 = 0; i2 < this.mCancelCallbacks.getRegisteredCallbackCount(); i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.mCancelCallbacks.getRegisteredCallbackItem(i2));
            }
        }
    }

    public String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("PendingIntentRecord{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(' ');
        sb.append(this.key.packageName);
        if (this.key.featureId != null) {
            sb.append('/');
            sb.append(this.key.featureId);
        }
        sb.append(' ');
        sb.append(this.key.typeName());
        if (this.mAllowlistDuration != null) {
            sb.append(" (allowlist: ");
            for (int i = 0; i < this.mAllowlistDuration.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                TempAllowListDuration tempAllowListDuration = (TempAllowListDuration) this.mAllowlistDuration.valueAt(i);
                sb.append(Integer.toHexString(System.identityHashCode(this.mAllowlistDuration.keyAt(i))));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                TimeUtils.formatDuration(tempAllowListDuration.duration, sb);
                sb.append("/");
                sb.append(tempAllowListDuration.type);
                sb.append("/");
                sb.append(PowerWhitelistManager.reasonCodeToString(tempAllowListDuration.reasonCode));
                sb.append("/");
                sb.append(tempAllowListDuration.reason);
            }
            sb.append(")");
        }
        sb.append('}');
        String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    public int getUserId() {
        return this.key.userId;
    }
}
