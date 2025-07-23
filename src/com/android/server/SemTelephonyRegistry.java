package com.android.server;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.PropertyInvalidatedCache;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import android.util.LocalLog;
import com.android.internal.telephony.ISemTelephonyRegistry;
import com.android.internal.telephony.ITiantongSatelliteChangeListener;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import dalvik.annotation.optimization.NeverCompile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: classes6.dex */
public class SemTelephonyRegistry extends ISemTelephonyRegistry.Stub {
    private static final boolean DBG = false;
    private static final boolean DBG_LOC = false;
    private static final int MSG_UPDATE_DEFAULT_SUB = 2;
    private static final String TAG = "SemTelephonyRegistry";
    private static final boolean VDBG = false;
    private final AppOpsManager mAppOps;
    private ConfigurationProvider mConfigurationProvider;
    private final Context mContext;
    private int mNumPhones;
    private SemSatelliteServiceState mSatServiceState;
    private SemSatelliteSignalStrength mSatSignalStrength;
    private final ArrayList<IBinder> mRemoveList = new ArrayList<>();
    private final ArrayList<Record> mRecords = new ArrayList<>();
    private final LocalLog mLocalLog = new LocalLog(256);
    private int mLastSatPhoneId = 0;
    private int mLastSatSubId = 0;
    private final Handler mHandler = new Handler(this) { // from class: com.android.server.SemTelephonyRegistry.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
        }
    };

    private boolean doesLimitApplyForListeners(int i, int i2) {
        return (i == 1000 || i == 1001 || i == i2) ? false : true;
    }

    public void systemRunning() {
    }

    private static class Record {
        IBinder binder;
        int callerPid;
        int callerUid;
        String callingFeatureId;
        String callingPackage;
        Context context;
        SemTelephonyRegistryDeathRecipient deathRecipient;
        int phoneId;
        int subId;
        ITiantongSatelliteChangeListener tiantongSatelliteChangeListener;

        private Record() {
            this.subId = -1;
            this.phoneId = -1;
        }

        boolean matchTiantongSatelliteChangeListener() {
            return this.tiantongSatelliteChangeListener != null;
        }

        public String toString() {
            return "{callingPackage=" + SemTelephonyRegistry.pii(this.callingPackage) + " callerUid=" + this.callerUid + " binder=" + this.binder + " subId=" + this.subId + " phoneId=" + this.phoneId + "}";
        }
    }

    public static class ConfigurationProvider {
        public int getRegistrationLimit() {
            return ((Integer) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.SemTelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingSupplier
                public final Object getOrThrow() {
                    Integer valueOf;
                    valueOf = Integer.valueOf(DeviceConfig.getInt(PropertyInvalidatedCache.MODULE_TELEPHONY, TelephonyCallback.FLAG_PER_PID_REGISTRATION_LIMIT, 50));
                    return valueOf;
                }
            })).intValue();
        }

        public boolean isRegistrationLimitEnabledInPlatformCompat(final int i) {
            return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.SemTelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingSupplier
                public final Object getOrThrow() {
                    Boolean valueOf;
                    valueOf = Boolean.valueOf(CompatChanges.isChangeEnabled(TelephonyCallback.PHONE_STATE_LISTENER_LIMIT_CHANGE_ID, i));
                    return valueOf;
                }
            })).booleanValue();
        }
    }

    private class SemTelephonyRegistryDeathRecipient implements IBinder.DeathRecipient {
        private final IBinder binder;

        SemTelephonyRegistryDeathRecipient(IBinder iBinder) {
            this.binder = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            SemTelephonyRegistry.this.remove(this.binder);
        }
    }

    private TelephonyManager getTelephonyManager() {
        return (TelephonyManager) this.mContext.getSystemService("phone");
    }

    public SemTelephonyRegistry(Context context, ConfigurationProvider configurationProvider) {
        this.mSatServiceState = null;
        this.mSatSignalStrength = null;
        this.mContext = context;
        this.mConfigurationProvider = configurationProvider;
        this.mSatServiceState = new SemSatelliteServiceState();
        this.mSatSignalStrength = new SemSatelliteSignalStrength();
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    }

    private Record add(IBinder iBinder, int i, int i2, boolean z) {
        synchronized (this.mRecords) {
            int size = this.mRecords.size();
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                Record record = this.mRecords.get(i4);
                if (iBinder == record.binder) {
                    return record;
                }
                if (record.callerPid == i2) {
                    i3++;
                }
            }
            int registrationLimit = this.mConfigurationProvider.getRegistrationLimit();
            if (z && registrationLimit >= 1 && i3 >= registrationLimit) {
                String str = "Pid " + i2 + " has exceeded the number of permissible registered listeners. Ignoring request to add.";
                loge(str);
                if (this.mConfigurationProvider.isRegistrationLimitEnabledInPlatformCompat(i)) {
                    throw new IllegalStateException(str);
                }
            } else if (i3 >= 25) {
                Rlog.w(TAG, "Pid " + i2 + " has exceeded half the number of permissible registered listeners. Now at " + i3);
            }
            Record record2 = new Record();
            record2.binder = iBinder;
            record2.deathRecipient = new SemTelephonyRegistryDeathRecipient(iBinder);
            try {
                iBinder.linkToDeath(record2.deathRecipient, 0);
                this.mRecords.add(record2);
                return record2;
            } catch (RemoteException unused) {
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remove(IBinder iBinder) {
        synchronized (this.mRecords) {
            int size = this.mRecords.size();
            for (int i = 0; i < size; i++) {
                Record record = this.mRecords.get(i);
                if (record.binder == iBinder) {
                    if (record.deathRecipient != null) {
                        try {
                            iBinder.unlinkToDeath(record.deathRecipient, 0);
                        } catch (NoSuchElementException unused) {
                        }
                    }
                    this.mRecords.remove(i);
                    return;
                }
            }
        }
    }

    @Override // com.android.internal.telephony.ISemTelephonyRegistry
    public void addTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str, String str2) {
        UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        if (checkSatellitePermission("addTiantongSatelliteChangeListener")) {
            synchronized (this.mRecords) {
                Record add = add(iTiantongSatelliteChangeListener.asBinder(), Binder.getCallingUid(), Binder.getCallingPid(), doesLimitApplyForListeners(Binder.getCallingUid(), Process.myUid()));
                if (add == null) {
                    loge("Can not create Record instance!");
                    return;
                }
                add.context = this.mContext;
                add.tiantongSatelliteChangeListener = iTiantongSatelliteChangeListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = Binder.getCallingUid();
                add.callerPid = Binder.getCallingPid();
                try {
                    add.tiantongSatelliteChangeListener.onSemSatelliteServiceStateChanged(this.mLastSatPhoneId, this.mLastSatSubId, this.mSatServiceState);
                    add.tiantongSatelliteChangeListener.onSemSatelliteSignalStrengthChanged(this.mLastSatPhoneId, this.mLastSatSubId, this.mSatSignalStrength);
                } catch (RemoteException unused) {
                    remove(add.binder);
                }
            }
        }
    }

    @Override // com.android.internal.telephony.ISemTelephonyRegistry
    public void removeTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str) {
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        remove(iTiantongSatelliteChangeListener.asBinder());
    }

    @Override // com.android.internal.telephony.ISemTelephonyRegistry
    public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) {
        if (!validatePhoneId(i)) {
            throw new IllegalArgumentException("Invalid phoneId: " + i);
        }
        if (checkSatellitePermission("notifySemSatelliteServiceStateChanged")) {
            synchronized (this.mRecords) {
                this.mSatServiceState = new SemSatelliteServiceState(semSatelliteServiceState);
                this.mLastSatPhoneId = i;
                this.mLastSatSubId = i2;
                this.mRemoveList.clear();
                Iterator<Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record next = it.next();
                    if (next.matchTiantongSatelliteChangeListener()) {
                        try {
                            next.tiantongSatelliteChangeListener.onSemSatelliteServiceStateChanged(i, i2, semSatelliteServiceState);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    @Override // com.android.internal.telephony.ISemTelephonyRegistry
    public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) {
        if (!validatePhoneId(i)) {
            throw new IllegalArgumentException("Invalid phoneId: " + i);
        }
        if (checkSatellitePermission("notifySemSatelliteSignalStrengthChanged")) {
            synchronized (this.mRecords) {
                this.mSatSignalStrength = new SemSatelliteSignalStrength(semSatelliteSignalStrength);
                this.mLastSatPhoneId = i;
                this.mLastSatSubId = i2;
                this.mRemoveList.clear();
                Iterator<Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record next = it.next();
                    if (next.matchTiantongSatelliteChangeListener()) {
                        try {
                            next.tiantongSatelliteChangeListener.onSemSatelliteSignalStrengthChanged(i, i2, semSatelliteSignalStrength);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    private boolean checkSatellitePermission(String str) {
        if (this.mContext.checkCallingOrSelfPermission(Manifest.permission.SATELLITE_COMMUNICATION) == 0) {
            return true;
        }
        Binder.getCallingPid();
        Binder.getCallingUid();
        return false;
    }

    @Override // android.os.Binder
    @NeverCompile
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        if (DumpUtils.checkDumpPermission(this.mContext, TAG, indentingPrintWriter)) {
            synchronized (this.mRecords) {
                int size = this.mRecords.size();
                indentingPrintWriter.println("last known state:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < getTelephonyManager().getActiveModemCount(); i++) {
                    indentingPrintWriter.println("Phone Id=" + i);
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("local logs:");
                indentingPrintWriter.increaseIndent();
                this.mLocalLog.dump(fileDescriptor, indentingPrintWriter, strArr);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("registrations: count=" + size);
                indentingPrintWriter.increaseIndent();
                Iterator<Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println(it.next());
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    private void handleRemoveListLocked() {
        if (this.mRemoveList.size() > 0) {
            Iterator<IBinder> it = this.mRemoveList.iterator();
            while (it.hasNext()) {
                remove(it.next());
            }
            this.mRemoveList.clear();
        }
    }

    private boolean validatePhoneId(int i) {
        return i >= 0 && i < getTelephonyManager().getActiveModemCount();
    }

    private static void log(String str) {
        Rlog.d(TAG, str);
    }

    private static void loge(String str) {
        Rlog.e(TAG, str);
    }

    private int getPhoneIdFromSubId(int i) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        if (subscriptionManager == null) {
            return -1;
        }
        if (i == Integer.MAX_VALUE) {
            i = SubscriptionManager.getDefaultSubscriptionId();
        }
        SubscriptionInfo activeSubscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(i);
        if (activeSubscriptionInfo == null) {
            return -1;
        }
        return activeSubscriptionInfo.getSimSlotIndex();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String pii(String str) {
        return Build.IS_DEBUGGABLE ? str : "***";
    }

    private static String pii(List<String> list) {
        if (list.isEmpty() || Build.IS_DEBUGGABLE) {
            return list.toString();
        }
        return "[***, size=" + list.size() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
