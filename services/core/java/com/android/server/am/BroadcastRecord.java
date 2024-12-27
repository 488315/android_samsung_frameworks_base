package com.android.server.am;

import android.app.BackgroundStartPrivileges;
import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.PrintWriterPrinter;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BroadcastRecord extends Binder {
    public static boolean CORE_DEFER_UNTIL_ACTIVE;
    public static final List EMPTY_RECEIVERS = List.of();
    public final boolean alarm;
    public int anrCount;
    public final int appOp;
    public int beyondCount;
    public final int[] blockedUntilBeyondCount;
    public final ProcessRecord callerApp;
    public final String callerFeatureId;
    public final boolean callerInstantApp;
    public final boolean callerInstrumented;
    public final String callerPackage;
    public final int callerProcState;
    public final int callingPid;
    public final int callingUid;
    public final boolean deferUntilActive;
    public final int[] delivery;
    public final String[] deliveryReasons;
    public long dispatchClockTime;
    public long dispatchRealTime;
    public long dispatchTime;
    public long enqueueClockTime;
    public long enqueueRealTime;
    public long enqueueTime;
    public final String[] excludedPackages;
    public final String[] excludedPermissions;
    public final BiFunction filterExtrasForReceiver;
    public long finishTime;
    public boolean hadResultTo;
    public final boolean initialSticky;
    public final Intent intent;
    public final boolean interactive;
    public final BackgroundStartPrivileges mBackgroundStartPrivileges;
    public String mCachedToShortString;
    public String mCachedToString;
    public boolean mCounted;
    public final ArrayList mMARsTargetReceiver;
    public ArrayMap mMatchingRecordsCache;
    public int nextReceiver;
    public final BroadcastOptions options;
    public final boolean ordered;
    public long originalEnqueueClockTime;
    public final int originalStickyCallingUid;
    public final boolean prioritized;
    public final boolean pushMessage;
    public final boolean pushMessageOverQuota;
    public final BroadcastQueue queue;
    public final long receiverTime;
    public final List receivers;
    public final long[] receiversDispatchTime;
    public final String[] receiversExtraTime;
    public final long[] receiversFinishTime;
    public final String[] requiredPermissions;
    public final String resolvedType;
    public boolean resultAbort;
    public int resultCode;
    public String resultData;
    public Bundle resultExtras;
    public IIntentReceiver resultTo;
    public final ProcessRecord resultToApp;
    public final long[] scheduledTime;
    public final boolean shareIdentity;
    public final int state;
    public final boolean sticky;
    public final ComponentName targetComp;
    public int terminalCount;
    public final long[] terminalTime;
    public final boolean timeoutExempt;
    public final boolean urgent;
    public final int userId;

    public BroadcastRecord(BroadcastQueue broadcastQueue, Intent intent, ProcessRecord processRecord, String str, String str2, int i, int i2, boolean z, String str3, String[] strArr, String[] strArr2, String[] strArr3, int i3, BroadcastOptions broadcastOptions, List list, ProcessRecord processRecord2, IIntentReceiver iIntentReceiver, int i4, String str4, Bundle bundle, boolean z2, boolean z3, boolean z4, int i5, int i6, BackgroundStartPrivileges backgroundStartPrivileges, boolean z5, BiFunction biFunction, int i7) {
        if (intent == null) {
            throw new NullPointerException("Can't construct with a null intent");
        }
        this.queue = broadcastQueue;
        this.intent = intent;
        this.targetComp = intent.getComponent();
        this.callerApp = processRecord;
        this.callerPackage = str;
        this.callerFeatureId = str2;
        this.callingPid = i;
        this.callingUid = i2;
        this.callerProcState = i7;
        this.callerInstantApp = z;
        int appId = UserHandle.getAppId(i2);
        this.callerInstrumented = appId == 0 || appId == 2000 || !(processRecord == null || processRecord.mInstr == null);
        this.resolvedType = str3;
        this.requiredPermissions = strArr;
        this.excludedPermissions = strArr2;
        this.excludedPackages = strArr3;
        this.appOp = i3;
        this.options = broadcastOptions;
        List list2 = list != null ? list : EMPTY_RECEIVERS;
        this.receivers = list2;
        this.mMARsTargetReceiver = new ArrayList(Collections.nCopies(list2.size(), Boolean.FALSE));
        int size = list != null ? list.size() : 0;
        this.delivery = new int[size];
        this.deliveryReasons = new String[size];
        boolean calculateUrgent = calculateUrgent(intent, broadcastOptions);
        this.urgent = calculateUrgent;
        this.deferUntilActive = calculateDeferUntilActive(i2, broadcastOptions, iIntentReceiver, z2, calculateUrgent);
        int[] calculateBlockedUntilBeyondCount = calculateBlockedUntilBeyondCount(list2, z2);
        this.blockedUntilBeyondCount = calculateBlockedUntilBeyondCount;
        this.scheduledTime = new long[size];
        this.terminalTime = new long[size];
        this.resultToApp = processRecord2;
        this.resultTo = iIntentReceiver;
        this.resultCode = i4;
        this.resultData = str4;
        this.resultExtras = bundle;
        this.ordered = z2;
        this.sticky = z3;
        this.initialSticky = z4;
        this.prioritized = isPrioritized(calculateBlockedUntilBeyondCount, z2);
        this.userId = i5;
        this.nextReceiver = 0;
        this.state = 0;
        this.mBackgroundStartPrivileges = backgroundStartPrivileges;
        this.timeoutExempt = z5;
        this.alarm = broadcastOptions != null && broadcastOptions.isAlarmBroadcast();
        this.pushMessage = broadcastOptions != null && broadcastOptions.isPushMessagingBroadcast();
        this.pushMessageOverQuota = broadcastOptions != null && broadcastOptions.isPushMessagingOverQuotaBroadcast();
        this.interactive = broadcastOptions != null && broadcastOptions.isInteractive();
        this.shareIdentity = broadcastOptions != null && broadcastOptions.isShareIdentityEnabled();
        this.filterExtrasForReceiver = biFunction;
        this.originalStickyCallingUid = i6;
        this.receiversDispatchTime = new long[list != null ? list.size() : 0];
        this.receiversFinishTime = new long[list != null ? list.size() : 0];
        this.receiversExtraTime = new String[list != null ? list.size() : 0];
    }

    public BroadcastRecord(BroadcastRecord broadcastRecord, Intent intent) {
        Objects.requireNonNull(intent);
        this.intent = intent;
        this.targetComp = intent.getComponent();
        this.callerApp = broadcastRecord.callerApp;
        this.callerPackage = broadcastRecord.callerPackage;
        this.callerFeatureId = broadcastRecord.callerFeatureId;
        this.callingPid = broadcastRecord.callingPid;
        this.callingUid = broadcastRecord.callingUid;
        this.callerProcState = broadcastRecord.callerProcState;
        this.callerInstantApp = broadcastRecord.callerInstantApp;
        this.callerInstrumented = broadcastRecord.callerInstrumented;
        this.ordered = broadcastRecord.ordered;
        this.sticky = broadcastRecord.sticky;
        this.initialSticky = broadcastRecord.initialSticky;
        this.prioritized = broadcastRecord.prioritized;
        this.userId = broadcastRecord.userId;
        this.resolvedType = broadcastRecord.resolvedType;
        this.requiredPermissions = broadcastRecord.requiredPermissions;
        this.excludedPermissions = broadcastRecord.excludedPermissions;
        this.excludedPackages = broadcastRecord.excludedPackages;
        this.appOp = broadcastRecord.appOp;
        this.options = broadcastRecord.options;
        List list = broadcastRecord.receivers;
        this.receivers = list;
        this.mMARsTargetReceiver = new ArrayList(Collections.nCopies(list.size(), Boolean.FALSE));
        this.delivery = broadcastRecord.delivery;
        this.deliveryReasons = broadcastRecord.deliveryReasons;
        this.deferUntilActive = broadcastRecord.deferUntilActive;
        this.blockedUntilBeyondCount = broadcastRecord.blockedUntilBeyondCount;
        this.scheduledTime = broadcastRecord.scheduledTime;
        this.terminalTime = broadcastRecord.terminalTime;
        this.resultToApp = broadcastRecord.resultToApp;
        this.resultTo = broadcastRecord.resultTo;
        this.enqueueTime = broadcastRecord.enqueueTime;
        this.enqueueRealTime = broadcastRecord.enqueueRealTime;
        this.enqueueClockTime = broadcastRecord.enqueueClockTime;
        this.dispatchTime = broadcastRecord.dispatchTime;
        this.dispatchRealTime = broadcastRecord.dispatchRealTime;
        this.dispatchClockTime = broadcastRecord.dispatchClockTime;
        this.receiverTime = broadcastRecord.receiverTime;
        this.finishTime = broadcastRecord.finishTime;
        this.resultCode = broadcastRecord.resultCode;
        this.resultData = broadcastRecord.resultData;
        this.resultExtras = broadcastRecord.resultExtras;
        this.resultAbort = broadcastRecord.resultAbort;
        this.nextReceiver = broadcastRecord.nextReceiver;
        this.state = broadcastRecord.state;
        this.anrCount = broadcastRecord.anrCount;
        this.queue = broadcastRecord.queue;
        this.mBackgroundStartPrivileges = broadcastRecord.mBackgroundStartPrivileges;
        this.timeoutExempt = broadcastRecord.timeoutExempt;
        this.alarm = broadcastRecord.alarm;
        this.pushMessage = broadcastRecord.pushMessage;
        this.pushMessageOverQuota = broadcastRecord.pushMessageOverQuota;
        this.interactive = broadcastRecord.interactive;
        this.shareIdentity = broadcastRecord.shareIdentity;
        this.urgent = broadcastRecord.urgent;
        this.filterExtrasForReceiver = broadcastRecord.filterExtrasForReceiver;
        this.originalStickyCallingUid = broadcastRecord.originalStickyCallingUid;
        this.receiversDispatchTime = broadcastRecord.receiversDispatchTime;
        this.receiversFinishTime = broadcastRecord.receiversFinishTime;
        this.receiversExtraTime = broadcastRecord.receiversExtraTime;
    }

    public static int[] calculateBlockedUntilBeyondCount(List list, boolean z) {
        int size = list.size();
        int[] iArr = new int[size];
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (z) {
                iArr[i3] = i3;
            } else {
                Object obj = list.get(i3);
                int priority = obj instanceof BroadcastFilter ? ((BroadcastFilter) obj).getPriority() : ((ResolveInfo) obj).priority;
                if (i3 == 0 || priority != i) {
                    iArr[i3] = i3;
                    i2 = i3;
                    i = priority;
                } else {
                    iArr[i3] = i2;
                }
            }
        }
        if (size > 0 && iArr[size - 1] == 0) {
            Arrays.fill(iArr, -1);
        }
        return iArr;
    }

    public static boolean calculateDeferUntilActive(int i, BroadcastOptions broadcastOptions, IIntentReceiver iIntentReceiver, boolean z, boolean z2) {
        if (z) {
            return false;
        }
        if (!z && iIntentReceiver != null) {
            return true;
        }
        if (broadcastOptions != null) {
            int deferralPolicy = broadcastOptions.getDeferralPolicy();
            if (deferralPolicy == 1) {
                return false;
            }
            if (deferralPolicy == 2) {
                return true;
            }
        }
        return !z2 && CORE_DEFER_UNTIL_ACTIVE && UserHandle.isCore(i);
    }

    public static boolean calculateUrgent(Intent intent, BroadcastOptions broadcastOptions) {
        if ((intent.getFlags() & 268435456) != 0) {
            return true;
        }
        if (broadcastOptions != null) {
            return broadcastOptions.isInteractive() || broadcastOptions.isAlarmBroadcast();
        }
        return false;
    }

    public static String deliveryStateToString(int i) {
        switch (i) {
            case 0:
                return "PENDING";
            case 1:
                return "DELIVERED";
            case 2:
                return "SKIPPED";
            case 3:
                return "TIMEOUT";
            case 4:
                return "SCHEDULED";
            case 5:
                return "FAILURE";
            case 6:
                return "DEFERRED";
            default:
                return Integer.toString(i);
        }
    }

    public static String getReceiverPackageName(Object obj) {
        return obj instanceof BroadcastFilter ? ((BroadcastFilter) obj).receiverList.app.info.packageName : ((ResolveInfo) obj).activityInfo.packageName;
    }

    public static int getReceiverUid(Object obj) {
        return obj instanceof BroadcastFilter ? ((BroadcastFilter) obj).owningUid : ((ResolveInfo) obj).activityInfo.applicationInfo.uid;
    }

    public static boolean isDeliveryStateTerminal(int i) {
        return i == 1 || i == 2 || i == 3 || i == 5;
    }

    public static boolean isPrioritized(int[] iArr, boolean z) {
        return (z || iArr.length <= 0 || iArr[0] == -1) ? false : true;
    }

    public static boolean isReceiverEquals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (!(obj instanceof ResolveInfo) || !(obj2 instanceof ResolveInfo)) {
            return false;
        }
        ResolveInfo resolveInfo = (ResolveInfo) obj;
        ResolveInfo resolveInfo2 = (ResolveInfo) obj2;
        return Objects.equals(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName) && Objects.equals(resolveInfo.activityInfo.name, resolveInfo2.activityInfo.name);
    }

    public boolean cleanupDisabledPackageReceiversLocked(String str, Set set, int i, boolean z) {
        List list = this.receivers;
        boolean z2 = false;
        if (list == null) {
            return false;
        }
        boolean z3 = i == -1;
        int i2 = this.userId;
        boolean z4 = i2 == -1;
        if (i2 != i && !z3 && !z4) {
            return false;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            Object obj = this.receivers.get(size);
            if (obj instanceof ResolveInfo) {
                ActivityInfo activityInfo = ((ResolveInfo) obj).activityInfo;
                if ((str == null || (activityInfo.applicationInfo.packageName.equals(str) && (set == null || set.contains(activityInfo.name)))) && (z3 || UserHandle.getUserId(activityInfo.applicationInfo.uid) == i)) {
                    if (!z) {
                        return true;
                    }
                    this.receivers.remove(size);
                    int i3 = this.nextReceiver;
                    if (size < i3) {
                        this.nextReceiver = i3 - 1;
                    }
                    z2 = true;
                }
            }
        }
        this.nextReceiver = Math.min(this.nextReceiver, this.receivers.size());
        return z2;
    }

    public final boolean containsReceiver(Object obj) {
        for (int size = this.receivers.size() - 1; size >= 0; size--) {
            if (isReceiverEquals(obj, this.receivers.get(size))) {
                return true;
            }
        }
        return false;
    }

    @NeverCompile
    public final void dump(PrintWriter printWriter, SimpleDateFormat simpleDateFormat) {
        long uptimeMillis = SystemClock.uptimeMillis();
        printWriter.print("    ");
        printWriter.print(this);
        printWriter.print(" to user ");
        printWriter.println(this.userId);
        printWriter.print("    ");
        printWriter.println(this.intent.toString());
        ComponentName componentName = this.targetComp;
        if (componentName != null && componentName != this.intent.getComponent()) {
            printWriter.print("    ");
            printWriter.print("  targetComp: ");
            printWriter.println(this.targetComp.toShortString());
        }
        this.intent.getExtras();
        printWriter.print("    ");
        printWriter.print("caller=");
        printWriter.print(this.callerPackage);
        printWriter.print(" ");
        ProcessRecord processRecord = this.callerApp;
        printWriter.print(processRecord != null ? processRecord.toShortString() : "null");
        printWriter.print(" pid=");
        printWriter.print(this.callingPid);
        printWriter.print(" uid=");
        printWriter.println(this.callingUid);
        String[] strArr = this.requiredPermissions;
        if ((strArr != null && strArr.length > 0) || this.appOp != -1) {
            printWriter.print("    ");
            printWriter.print("requiredPermissions=");
            printWriter.print(Arrays.toString(this.requiredPermissions));
            printWriter.print("  appOp=");
            printWriter.println(this.appOp);
        }
        String[] strArr2 = this.excludedPermissions;
        if (strArr2 != null && strArr2.length > 0) {
            printWriter.print("    ");
            printWriter.print("excludedPermissions=");
            printWriter.print(Arrays.toString(this.excludedPermissions));
        }
        String[] strArr3 = this.excludedPackages;
        if (strArr3 != null && strArr3.length > 0) {
            printWriter.print("    ");
            printWriter.print("excludedPackages=");
            printWriter.print(Arrays.toString(this.excludedPackages));
        }
        if (this.options != null) {
            printWriter.print("    ");
            printWriter.print("options=");
            printWriter.println(this.options.toBundle());
        }
        printWriter.print("    ");
        printWriter.print("enqueueClockTime=");
        printWriter.print(simpleDateFormat.format(new Date(this.enqueueClockTime)));
        printWriter.print(" dispatchClockTime=");
        printWriter.print(simpleDateFormat.format(new Date(this.dispatchClockTime)));
        long j = 0;
        if (this.originalEnqueueClockTime > 0) {
            printWriter.print(" originalEnqueueClockTime=");
            printWriter.print(simpleDateFormat.format(new Date(this.originalEnqueueClockTime)));
        }
        printWriter.println();
        printWriter.print("    ");
        printWriter.print("dispatchTime=");
        TimeUtils.formatDuration(this.dispatchTime, uptimeMillis, printWriter);
        printWriter.print(" (");
        TimeUtils.formatDuration(this.dispatchTime - this.enqueueTime, printWriter);
        printWriter.print(" since enq)");
        if (this.finishTime != 0) {
            printWriter.print(" finishTime=");
            TimeUtils.formatDuration(this.finishTime, uptimeMillis, printWriter);
            printWriter.print(" (");
            TimeUtils.formatDuration(this.finishTime - this.dispatchTime, printWriter);
            printWriter.print(" since disp)");
        } else {
            printWriter.print(" receiverTime=");
            TimeUtils.formatDuration(this.receiverTime, uptimeMillis, printWriter);
        }
        printWriter.println("");
        if (this.anrCount != 0) {
            printWriter.print("    ");
            printWriter.print("anrCount=");
            printWriter.println(this.anrCount);
        }
        if (this.resultTo != null || this.resultCode != -1 || this.resultData != null || this.hadResultTo) {
            printWriter.print("    ");
            printWriter.print("resultTo=");
            printWriter.print(this.resultTo);
            printWriter.print(" resultCode=");
            printWriter.print(this.resultCode);
            printWriter.print(" hadResultTo=");
            printWriter.print(this.hadResultTo);
            if (this.resultData != null) {
                printWriter.println(" resultData=(has data)");
            } else {
                printWriter.print(" resultData=");
                printWriter.println(this.resultData);
            }
        }
        if (this.resultExtras != null) {
            printWriter.print("    ");
            printWriter.println("resultExtras=(has extras)");
        }
        if (this.resultAbort || this.ordered || this.sticky || this.initialSticky) {
            printWriter.print("    ");
            printWriter.print("resultAbort=");
            printWriter.print(this.resultAbort);
            printWriter.print(" ordered=");
            printWriter.print(this.ordered);
            printWriter.print(" sticky=");
            printWriter.print(this.sticky);
            printWriter.print(" initialSticky=");
            printWriter.print(this.initialSticky);
            printWriter.print(" originalStickyCallingUid=");
            printWriter.println(this.originalStickyCallingUid);
        }
        if (this.nextReceiver != 0) {
            printWriter.print("    ");
            printWriter.print("nextReceiver=");
            printWriter.println(this.nextReceiver);
        }
        int i = this.state;
        if (i != 0) {
            String str = i != 1 ? i != 2 ? i != 3 ? i != 4 ? " (?)" : " (WAITING_SERVICES)" : " (CALL_DONE_RECEIVE)" : " (CALL_IN_RECEIVE)" : " (APP_RECEIVE)";
            printWriter.print("    ");
            printWriter.print("state=");
            printWriter.print(this.state);
            printWriter.println(str);
        }
        printWriter.print("    ");
        printWriter.print("terminalCount=");
        printWriter.println(this.terminalCount);
        List list = this.receivers;
        int size = list != null ? list.size() : 0;
        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(printWriter);
        int i2 = 0;
        while (i2 < size) {
            Object obj = this.receivers.get(i2);
            printWriter.print("    ");
            printWriter.print(deliveryStateToString(this.delivery[i2]));
            printWriter.print(' ');
            if (this.scheduledTime[i2] != j) {
                printWriter.print("scheduled ");
                TimeUtils.formatDuration(this.scheduledTime[i2] - this.enqueueTime, printWriter);
                printWriter.print(' ');
            }
            if (this.terminalTime[i2] != j) {
                printWriter.print("terminal ");
                TimeUtils.formatDuration(this.terminalTime[i2] - this.scheduledTime[i2], printWriter);
                printWriter.print(' ');
            }
            printWriter.print("(");
            printWriter.print(this.blockedUntilBeyondCount[i2]);
            printWriter.print(") ");
            printWriter.print("#");
            printWriter.print(i2);
            printWriter.print(": ");
            if (obj instanceof BroadcastFilter) {
                printWriter.print("/ s:");
                printWriter.print(simpleDateFormat.format(Long.valueOf(this.receiversDispatchTime[i2])));
                printWriter.print("/ e:");
                printWriter.print(simpleDateFormat.format(Long.valueOf(this.receiversFinishTime[i2])));
                printWriter.print("/ o:");
                printWriter.print(this.receiversExtraTime[i2]);
                printWriter.print("/ ");
                printWriter.println(obj);
                BroadcastFilter broadcastFilter = (BroadcastFilter) obj;
                if (broadcastFilter.requiredPermission != null) {
                    printWriter.print("      ");
                    printWriter.print("requiredPermission=");
                    printWriter.println(broadcastFilter.requiredPermission);
                }
            } else if (obj instanceof ResolveInfo) {
                printWriter.print("/ s:");
                printWriter.print(simpleDateFormat.format(Long.valueOf(this.receiversDispatchTime[i2])));
                printWriter.print("/ e:");
                printWriter.print(simpleDateFormat.format(Long.valueOf(this.receiversFinishTime[i2])));
                printWriter.print("/ o:");
                printWriter.print(this.receiversExtraTime[i2]);
                printWriter.print("/ ");
                printWriter.print("(manifest)");
                ((ResolveInfo) obj).dump(printWriterPrinter, "      ", 0);
            } else {
                printWriter.println(obj);
            }
            if (this.deliveryReasons[i2] != null) {
                printWriter.print("      ");
                printWriter.print("reason: ");
                printWriter.println(this.deliveryReasons[i2]);
            }
            i2++;
            j = 0;
        }
    }

    @NeverCompile
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.userId);
        protoOutputStream.write(1138166333442L, this.intent.getAction());
        protoOutputStream.end(start);
    }

    public final Intent getReceiverIntent(Object obj) {
        Bundle extras;
        Intent intent = null;
        if (this.filterExtrasForReceiver != null && (extras = this.intent.getExtras()) != null) {
            Bundle bundle = (Bundle) this.filterExtrasForReceiver.apply(Integer.valueOf(getReceiverUid(obj)), extras);
            if (bundle == null) {
                return null;
            }
            intent = new Intent(this.intent);
            intent.replaceExtras(bundle);
        }
        if (obj instanceof ResolveInfo) {
            if (intent == null) {
                intent = new Intent(this.intent);
            }
            intent.setComponent(((ResolveInfo) obj).activityInfo.getComponentName());
        }
        return intent != null ? intent : this.intent;
    }

    public final boolean isForeground() {
        return (this.intent.getFlags() & 268435456) != 0;
    }

    public final String toShortString() {
        if (this.mCachedToShortString == null) {
            String action = this.intent.getAction();
            if (action == null) {
                action = this.intent.toString();
            }
            this.mCachedToShortString = Integer.toHexString(System.identityHashCode(this)) + " " + action + "/u" + this.userId;
        }
        return this.mCachedToShortString;
    }

    public final String toString() {
        if (this.mCachedToString == null) {
            if (this.intent.getAction() == null) {
                this.intent.toString();
            }
            this.mCachedToString = "BroadcastRecord{" + toShortString() + "}";
        }
        return this.mCachedToString;
    }
}
