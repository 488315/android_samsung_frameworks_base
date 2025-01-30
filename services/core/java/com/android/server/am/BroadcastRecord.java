package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.app.BackgroundStartPrivileges;
import android.app.BroadcastOptions;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.IInstalld;
import android.util.ArrayMap;
import android.util.PrintWriterPrinter;
import android.util.SparseArray;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/* loaded from: classes.dex */
public final class BroadcastRecord extends Binder {
  public static boolean CORE_DEFER_UNTIL_ACTIVE = false;
  public static final List EMPTY_RECEIVERS = List.of();
  public static AtomicInteger sNextToken = new AtomicInteger(1);
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
  public ProcessRecord curApp;
  public int curAppLastProcessState;
  public ComponentName curComponent;
  public BroadcastFilter curFilter;
  public Bundle curFilteredExtras;
  public ActivityInfo curReceiver;
  public final boolean deferUntilActive;
  public boolean deferred;
  public int deferredCount;
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
  public boolean mIsReceiverAppRunning;
  public ArrayList mMARsTargetReceiver;
  public ArrayMap mMatchingRecordsCache;
  public boolean mWasReceiverAppStopped;
  public int manifestCount;
  public int manifestSkipCount;
  public int nextReceiver;
  public final BroadcastOptions options;
  public final boolean ordered;
  public long originalEnqueueClockTime;
  public final int originalStickyCallingUid;
  public final boolean prioritized;
  public final boolean pushMessage;
  public final boolean pushMessageOverQuota;
  public BroadcastQueue queue;
  public long receiverTime;
  public final List receivers;
  public long[] receiversDispatchTime;
  public String[] receiversExtraTime;
  public long[] receiversFinishTime;
  public final String[] requiredPermissions;
  public final String resolvedType;
  public boolean resultAbort;
  public int resultCode;
  public String resultData;
  public Bundle resultExtras;
  public IIntentReceiver resultTo;
  public ProcessRecord resultToApp;
  public final long[] scheduledTime;
  public final boolean shareIdentity;
  public int splitToken;
  public int state;
  public final boolean sticky;
  public final ComponentName targetComp;
  public int terminalCount;
  public final long[] terminalTime;
  public final boolean timeoutExempt;
  public final boolean urgent;
  public final int userId;

  public static boolean isDeliveryStateBeyond(int i) {
    return i == 1 || i == 2 || i == 3 || i == 5 || i == 6;
  }

  public static boolean isDeliveryStateTerminal(int i) {
    return i == 1 || i == 2 || i == 3 || i == 5;
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

  public boolean isAssumedDelivered(int i) {
    return (this.receivers.get(i) instanceof BroadcastFilter)
        && !this.ordered
        && this.resultTo == null;
  }

  @NeverCompile
  public void dump(PrintWriter printWriter, String str, SimpleDateFormat simpleDateFormat) {
    int i;
    int i2;
    String str2;
    PrintWriterPrinter printWriterPrinter;
    Object obj;
    long uptimeMillis = SystemClock.uptimeMillis();
    printWriter.print(str);
    printWriter.print(this);
    printWriter.print(" to user ");
    printWriter.println(this.userId);
    printWriter.print(str);
    printWriter.println(this.intent.toString());
    ComponentName componentName = this.targetComp;
    if (componentName != null && componentName != this.intent.getComponent()) {
      printWriter.print(str);
      printWriter.print("  targetComp: ");
      printWriter.println(this.targetComp.toShortString());
    }
    this.intent.getExtras();
    printWriter.print(str);
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
      printWriter.print(str);
      printWriter.print("requiredPermissions=");
      printWriter.print(Arrays.toString(this.requiredPermissions));
      printWriter.print("  appOp=");
      printWriter.println(this.appOp);
    }
    String[] strArr2 = this.excludedPermissions;
    if (strArr2 != null && strArr2.length > 0) {
      printWriter.print(str);
      printWriter.print("excludedPermissions=");
      printWriter.print(Arrays.toString(this.excludedPermissions));
    }
    String[] strArr3 = this.excludedPackages;
    if (strArr3 != null && strArr3.length > 0) {
      printWriter.print(str);
      printWriter.print("excludedPackages=");
      printWriter.print(Arrays.toString(this.excludedPackages));
    }
    if (this.options != null) {
      printWriter.print(str);
      printWriter.print("options=");
      printWriter.println(this.options.toBundle());
    }
    printWriter.print(str);
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
    printWriter.print(str);
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
      printWriter.print(str);
      printWriter.print("anrCount=");
      printWriter.println(this.anrCount);
    }
    if (this.resultTo != null
        || this.resultCode != -1
        || this.resultData != null
        || this.hadResultTo) {
      printWriter.print(str);
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
      printWriter.print(str);
      printWriter.println("resultExtras=(has extras)");
    }
    if (this.resultAbort || this.ordered || this.sticky || this.initialSticky) {
      printWriter.print(str);
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
      printWriter.print(str);
      printWriter.print("nextReceiver=");
      printWriter.println(this.nextReceiver);
    }
    if (this.curFilter != null) {
      printWriter.print(str);
      printWriter.print("curFilter=");
      printWriter.println(this.curFilter);
    }
    if (this.curReceiver != null) {
      printWriter.print(str);
      printWriter.print("curReceiver=");
      printWriter.println(this.curReceiver);
    }
    if (this.curApp != null) {
      printWriter.print(str);
      printWriter.print("curApp=");
      printWriter.println(this.curApp);
      printWriter.print(str);
      printWriter.print("curComponent=");
      ComponentName componentName2 = this.curComponent;
      printWriter.println(componentName2 != null ? componentName2.toShortString() : "--");
      ActivityInfo activityInfo = this.curReceiver;
      if (activityInfo != null && activityInfo.applicationInfo != null) {
        printWriter.print(str);
        printWriter.print("curSourceDir=");
        printWriter.println(this.curReceiver.applicationInfo.sourceDir);
      }
    }
    if (this.curFilteredExtras != null) {
      printWriter.print(" filtered extras: ");
      printWriter.println(this.curFilteredExtras);
    }
    int i3 = this.state;
    if (i3 != 0) {
      String str3 =
          i3 != 1
              ? i3 != 2
                  ? i3 != 3 ? i3 != 4 ? " (?)" : " (WAITING_SERVICES)" : " (CALL_DONE_RECEIVE)"
                  : " (CALL_IN_RECEIVE)"
              : " (APP_RECEIVE)";
      printWriter.print(str);
      printWriter.print("state=");
      printWriter.print(this.state);
      printWriter.println(str3);
    }
    printWriter.print(str);
    printWriter.print("terminalCount=");
    printWriter.println(this.terminalCount);
    List list = this.receivers;
    int size = list != null ? list.size() : 0;
    String str4 = str + "  ";
    PrintWriterPrinter printWriterPrinter2 = new PrintWriterPrinter(printWriter);
    for (int i4 = 0; i4 < size; i4 = i2 + 1) {
      Object obj2 = this.receivers.get(i4);
      printWriter.print(str);
      printWriter.print(deliveryStateToString(this.delivery[i4]));
      printWriter.print(' ');
      if (this.scheduledTime[i4] != j) {
        printWriter.print("scheduled ");
        i = size;
        TimeUtils.formatDuration(this.scheduledTime[i4] - this.enqueueTime, printWriter);
        printWriter.print(' ');
      } else {
        i = size;
      }
      if (this.terminalTime[i4] != j) {
        printWriter.print("terminal ");
        TimeUtils.formatDuration(this.terminalTime[i4] - this.scheduledTime[i4], printWriter);
        printWriter.print(' ');
      }
      printWriter.print("(");
      printWriter.print(this.blockedUntilBeyondCount[i4]);
      printWriter.print(") ");
      printWriter.print("#");
      printWriter.print(i4);
      printWriter.print(": ");
      if (obj2 instanceof BroadcastFilter) {
        if (!this.queue.mService.isModernBroadcastQueueEnabled()) {
          printWriter.print(obj2);
          obj = obj2;
          i2 = i4;
          this.queue.mService.mExt.printReceiverTime(this, printWriter, i4, uptimeMillis);
          str4 = str4;
          printWriterPrinter2 = printWriterPrinter2;
        } else {
          obj = obj2;
          i2 = i4;
          printWriter.print("/ s:");
          printWriter.print(simpleDateFormat.format(Long.valueOf(this.receiversDispatchTime[i2])));
          printWriter.print("/ e:");
          printWriter.print(simpleDateFormat.format(Long.valueOf(this.receiversFinishTime[i2])));
          printWriter.print("/ o:");
          printWriter.print(this.receiversExtraTime[i2]);
          printWriter.print("/ ");
          printWriter.println(obj);
        }
        ((BroadcastFilter) obj).dumpBrief(printWriter, str4);
        str2 = str4;
        printWriterPrinter = printWriterPrinter2;
      } else {
        i2 = i4;
        if (obj2 instanceof ResolveInfo) {
          if (!this.queue.mService.isModernBroadcastQueueEnabled()) {
            printWriter.print("(manifest)");
            this.queue.mService.mExt.printReceiverTime(this, printWriter, i2, uptimeMillis);
            str2 = str4;
            printWriterPrinter = printWriterPrinter2;
          } else {
            str2 = str4;
            printWriterPrinter = printWriterPrinter2;
            printWriter.print("/ s:");
            printWriter.print(
                simpleDateFormat.format(Long.valueOf(this.receiversDispatchTime[i2])));
            printWriter.print("/ e:");
            printWriter.print(simpleDateFormat.format(Long.valueOf(this.receiversFinishTime[i2])));
            printWriter.print("/ o:");
            printWriter.print(this.receiversExtraTime[i2]);
            printWriter.print("/ ");
            printWriter.print("(manifest)");
          }
          ((ResolveInfo) obj2).dump(printWriterPrinter, str2, 0);
        } else {
          str2 = str4;
          printWriterPrinter = printWriterPrinter2;
          printWriter.println(obj2);
        }
      }
      if (this.deliveryReasons[i2] != null) {
        printWriter.print(str2);
        printWriter.print("reason: ");
        printWriter.println(this.deliveryReasons[i2]);
      }
      str4 = str2;
      printWriterPrinter2 = printWriterPrinter;
      size = i;
      j = 0;
    }
  }

  public BroadcastRecord(
      BroadcastQueue broadcastQueue,
      Intent intent,
      ProcessRecord processRecord,
      String str,
      String str2,
      int i,
      int i2,
      boolean z,
      String str3,
      String[] strArr,
      String[] strArr2,
      String[] strArr3,
      int i3,
      BroadcastOptions broadcastOptions,
      List list,
      ProcessRecord processRecord2,
      IIntentReceiver iIntentReceiver,
      int i4,
      String str4,
      Bundle bundle,
      boolean z2,
      boolean z3,
      boolean z4,
      int i5,
      BackgroundStartPrivileges backgroundStartPrivileges,
      boolean z5,
      BiFunction biFunction,
      int i6) {
    this(
        broadcastQueue,
        intent,
        processRecord,
        str,
        str2,
        i,
        i2,
        z,
        str3,
        strArr,
        strArr2,
        strArr3,
        i3,
        broadcastOptions,
        list,
        processRecord2,
        iIntentReceiver,
        i4,
        str4,
        bundle,
        z2,
        z3,
        z4,
        i5,
        -1,
        backgroundStartPrivileges,
        z5,
        biFunction,
        i6);
  }

  public BroadcastRecord(
      BroadcastQueue broadcastQueue,
      Intent intent,
      ProcessRecord processRecord,
      String str,
      String str2,
      int i,
      int i2,
      boolean z,
      String str3,
      String[] strArr,
      String[] strArr2,
      String[] strArr3,
      int i3,
      BroadcastOptions broadcastOptions,
      List list,
      ProcessRecord processRecord2,
      IIntentReceiver iIntentReceiver,
      int i4,
      String str4,
      Bundle bundle,
      boolean z2,
      boolean z3,
      boolean z4,
      int i5,
      int i6,
      BackgroundStartPrivileges backgroundStartPrivileges,
      boolean z5,
      BiFunction biFunction,
      int i7) {
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
    this.callerInstrumented = isCallerInstrumented(processRecord, i2);
    this.resolvedType = str3;
    this.requiredPermissions = strArr;
    this.excludedPermissions = strArr2;
    this.excludedPackages = strArr3;
    this.appOp = i3;
    this.options = broadcastOptions;
    List list2 = list != null ? list : EMPTY_RECEIVERS;
    this.receivers = list2;
    this.mMARsTargetReceiver = new ArrayList(Collections.nCopies(list2.size(), Boolean.FALSE));
    int[] iArr = new int[list != null ? list.size() : 0];
    this.delivery = iArr;
    this.deliveryReasons = new String[iArr.length];
    boolean calculateUrgent = calculateUrgent(intent, broadcastOptions);
    this.urgent = calculateUrgent;
    this.deferUntilActive =
        calculateDeferUntilActive(i2, broadcastOptions, iIntentReceiver, z2, calculateUrgent);
    int[] calculateBlockedUntilBeyondCount = calculateBlockedUntilBeyondCount(list2, z2);
    this.blockedUntilBeyondCount = calculateBlockedUntilBeyondCount;
    this.scheduledTime = new long[iArr.length];
    this.terminalTime = new long[iArr.length];
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
    this.pushMessageOverQuota =
        broadcastOptions != null && broadcastOptions.isPushMessagingOverQuotaBroadcast();
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
    this.manifestCount = broadcastRecord.manifestCount;
    this.manifestSkipCount = broadcastRecord.manifestSkipCount;
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

  public BroadcastRecord splitRecipientsLocked(int i, int i2) {
    int i3 = i2;
    ArrayList arrayList = null;
    while (i3 < this.receivers.size()) {
      Object obj = this.receivers.get(i3);
      if (getReceiverUid(obj) == i) {
        ArrayList arrayList2 = arrayList == null ? new ArrayList() : arrayList;
        arrayList2.add(obj);
        this.receivers.remove(i3);
        arrayList = arrayList2;
      } else {
        i3++;
      }
    }
    if (arrayList == null) {
      return null;
    }
    BroadcastRecord broadcastRecord =
        new BroadcastRecord(
            this.queue,
            this.intent,
            this.callerApp,
            this.callerPackage,
            this.callerFeatureId,
            this.callingPid,
            this.callingUid,
            this.callerInstantApp,
            this.resolvedType,
            this.requiredPermissions,
            this.excludedPermissions,
            this.excludedPackages,
            this.appOp,
            this.options,
            arrayList,
            this.resultToApp,
            this.resultTo,
            this.resultCode,
            this.resultData,
            this.resultExtras,
            this.ordered,
            this.sticky,
            this.initialSticky,
            this.userId,
            this.mBackgroundStartPrivileges,
            this.timeoutExempt,
            this.filterExtrasForReceiver,
            this.callerProcState);
    broadcastRecord.enqueueTime = this.enqueueTime;
    broadcastRecord.enqueueRealTime = this.enqueueRealTime;
    broadcastRecord.enqueueClockTime = this.enqueueClockTime;
    broadcastRecord.splitToken = this.splitToken;
    return broadcastRecord;
  }

  public SparseArray splitDeferredBootCompletedBroadcastLocked(
      ActivityManagerInternal activityManagerInternal, int i) {
    int i2;
    SparseArray sparseArray = new SparseArray();
    if (i == 0 || this.receivers == null) {
      return sparseArray;
    }
    String action = this.intent.getAction();
    if (!"android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)
        && !"android.intent.action.BOOT_COMPLETED".equals(action)) {
      return sparseArray;
    }
    SparseArray sparseArray2 = new SparseArray();
    int size = this.receivers.size() - 1;
    while (true) {
      if (size < 0) {
        break;
      }
      Object obj = this.receivers.get(size);
      int receiverUid = getReceiverUid(obj);
      if (i != 1) {
        if ((i & 2) != 0 && activityManagerInternal.getRestrictionLevel(receiverUid) < 50) {
          size--;
        }
        if ((i & 4) != 0 && !CompatChanges.isChangeEnabled(203704822L, receiverUid)) {
          size--;
        }
      }
      this.receivers.remove(size);
      List list = (List) sparseArray2.get(receiverUid);
      if (list != null) {
        list.add(0, obj);
      } else {
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, obj);
        sparseArray2.put(receiverUid, arrayList);
      }
      size--;
    }
    int size2 = sparseArray2.size();
    for (i2 = 0; i2 < size2; i2++) {
      BroadcastRecord broadcastRecord =
          new BroadcastRecord(
              this.queue,
              this.intent,
              this.callerApp,
              this.callerPackage,
              this.callerFeatureId,
              this.callingPid,
              this.callingUid,
              this.callerInstantApp,
              this.resolvedType,
              this.requiredPermissions,
              this.excludedPermissions,
              this.excludedPackages,
              this.appOp,
              this.options,
              (List) sparseArray2.valueAt(i2),
              null,
              null,
              this.resultCode,
              this.resultData,
              this.resultExtras,
              this.ordered,
              this.sticky,
              this.initialSticky,
              this.userId,
              this.mBackgroundStartPrivileges,
              this.timeoutExempt,
              this.filterExtrasForReceiver,
              this.callerProcState);
      broadcastRecord.enqueueTime = this.enqueueTime;
      broadcastRecord.enqueueRealTime = this.enqueueRealTime;
      broadcastRecord.enqueueClockTime = this.enqueueClockTime;
      sparseArray.put(sparseArray2.keyAt(i2), broadcastRecord);
    }
    return sparseArray;
  }

  public void setMARsTargetReceiver(int i, boolean z) {
    this.mMARsTargetReceiver.set(i, Boolean.valueOf(z));
  }

  public boolean setDeliveryState(int i, int i2, String str) {
    int i3 = this.delivery[i];
    if (isDeliveryStateTerminal(i3) || i2 == i3) {
      return false;
    }
    if (i3 == 6) {
      this.deferredCount--;
    }
    switch (i2) {
      case 0:
        this.scheduledTime[i] = 0;
        break;
      case 1:
      case 2:
      case 3:
      case 5:
        this.terminalTime[i] = SystemClock.uptimeMillis();
        this.terminalCount++;
        break;
      case 4:
        this.scheduledTime[i] = SystemClock.uptimeMillis();
        break;
      case 6:
        this.deferredCount++;
        break;
    }
    this.delivery[i] = i2;
    this.deliveryReasons[i] = str;
    int i4 = this.beyondCount;
    if (i >= i4) {
      int i5 = i4;
      while (i5 < this.delivery.length && isDeliveryStateBeyond(getDeliveryState(i5))) {
        i5++;
        this.beyondCount = i5;
      }
    }
    return this.beyondCount != i4;
  }

  public int getDeliveryState(int i) {
    return this.delivery[i];
  }

  public boolean isBlocked(int i) {
    return this.beyondCount < this.blockedUntilBeyondCount[i];
  }

  public boolean wasDeliveryAttempted(int i) {
    int deliveryState = getDeliveryState(i);
    return deliveryState == 1 || deliveryState == 3 || deliveryState == 5;
  }

  public void copyEnqueueTimeFrom(BroadcastRecord broadcastRecord) {
    this.originalEnqueueClockTime = this.enqueueClockTime;
    this.enqueueTime = broadcastRecord.enqueueTime;
    this.enqueueRealTime = broadcastRecord.enqueueRealTime;
    this.enqueueClockTime = broadcastRecord.enqueueClockTime;
  }

  public boolean isMARsTargetReceiver(int i) {
    return ((Boolean) this.mMARsTargetReceiver.get(i)).booleanValue();
  }

  public boolean isForeground() {
    return (this.intent.getFlags() & 268435456) != 0;
  }

  public boolean isReplacePending() {
    return (this.intent.getFlags() & 536870912) != 0;
  }

  public boolean isNoAbort() {
    return (this.intent.getFlags() & 134217728) != 0;
  }

  public boolean isOffload() {
    return (this.intent.getFlags() & Integer.MIN_VALUE) != 0;
  }

  public boolean isDeferUntilActive() {
    return this.deferUntilActive;
  }

  public boolean isUrgent() {
    return this.urgent;
  }

  public String getHostingRecordTriggerType() {
    return this.alarm
        ? "alarm"
        : this.pushMessage
            ? "push_message"
            : this.pushMessageOverQuota ? "push_message_over_quota" : "unknown";
  }

  public Intent getReceiverIntent(Object obj) {
    Bundle extras;
    Intent intent = null;
    if (this.filterExtrasForReceiver != null && (extras = this.intent.getExtras()) != null) {
      Bundle bundle =
          (Bundle) this.filterExtrasForReceiver.apply(Integer.valueOf(getReceiverUid(obj)), extras);
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

  public static boolean isCallerInstrumented(ProcessRecord processRecord, int i) {
    int appId = UserHandle.getAppId(i);
    if (appId == 0 || appId == 2000) {
      return true;
    }
    return (processRecord == null || processRecord.getActiveInstrumentation() == null)
        ? false
        : true;
  }

  public static boolean isPrioritized(int[] iArr, boolean z) {
    return (z || iArr.length <= 0 || iArr[0] == -1) ? false : true;
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
        int receiverPriority = getReceiverPriority(list.get(i3));
        if (i3 == 0 || receiverPriority != i) {
          iArr[i3] = i3;
          i2 = i3;
          i = receiverPriority;
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

  public static int getReceiverUid(Object obj) {
    if (obj instanceof BroadcastFilter) {
      return ((BroadcastFilter) obj).owningUid;
    }
    return ((ResolveInfo) obj).activityInfo.applicationInfo.uid;
  }

  public static String getReceiverProcessName(Object obj) {
    if (obj instanceof BroadcastFilter) {
      return ((BroadcastFilter) obj).receiverList.app.processName;
    }
    return ((ResolveInfo) obj).activityInfo.processName;
  }

  public static String getReceiverPackageName(Object obj) {
    if (obj instanceof BroadcastFilter) {
      return ((BroadcastFilter) obj).receiverList.app.info.packageName;
    }
    return ((ResolveInfo) obj).activityInfo.packageName;
  }

  public static String getReceiverClassName(Object obj) {
    if (obj instanceof BroadcastFilter) {
      return ((BroadcastFilter) obj).getReceiverClassName();
    }
    return ((ResolveInfo) obj).activityInfo.name;
  }

  public static int getReceiverPriority(Object obj) {
    if (obj instanceof BroadcastFilter) {
      return ((BroadcastFilter) obj).getPriority();
    }
    return ((ResolveInfo) obj).priority;
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
    return Objects.equals(
            resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName)
        && Objects.equals(resolveInfo.activityInfo.name, resolveInfo2.activityInfo.name);
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

  public static boolean calculateDeferUntilActive(
      int i,
      BroadcastOptions broadcastOptions,
      IIntentReceiver iIntentReceiver,
      boolean z,
      boolean z2) {
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

  public int calculateTypeForLogging() {
    int i = isForeground() ? 2 : 1;
    if (this.alarm) {
      i |= 4;
    }
    if (this.interactive) {
      i |= 8;
    }
    if (this.ordered) {
      i |= 16;
    }
    if (this.prioritized) {
      i |= 32;
    }
    if (this.resultTo != null) {
      i |= 64;
    }
    if (this.deferUntilActive) {
      i |= 128;
    }
    if (this.pushMessage) {
      i |= 256;
    }
    if (this.pushMessageOverQuota) {
      i |= 512;
    }
    if (this.sticky) {
      i |= 1024;
    }
    return this.initialSticky ? i | IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES : i;
  }

  public BroadcastRecord maybeStripForHistory() {
    return !this.intent.canStripForHistory()
        ? this
        : new BroadcastRecord(this, this.intent.maybeStripForHistory());
  }

  public boolean cleanupDisabledPackageReceiversLocked(String str, Set set, int i, boolean z) {
    List list = this.receivers;
    if (list == null) {
      return false;
    }
    boolean z2 = i == -1;
    int i2 = this.userId;
    boolean z3 = i2 == -1;
    if (i2 != i && !z2 && !z3) {
      return false;
    }
    boolean z4 = false;
    for (int size = list.size() - 1; size >= 0; size--) {
      Object obj = this.receivers.get(size);
      if (obj instanceof ResolveInfo) {
        ActivityInfo activityInfo = ((ResolveInfo) obj).activityInfo;
        if ((str == null
                || (activityInfo.applicationInfo.packageName.equals(str)
                    && (set == null || set.contains(activityInfo.name))))
            && (z2 || UserHandle.getUserId(activityInfo.applicationInfo.uid) == i)) {
          if (!z) {
            return true;
          }
          this.receivers.remove(size);
          int i3 = this.nextReceiver;
          if (size < i3) {
            this.nextReceiver = i3 - 1;
          }
          z4 = true;
        }
      }
    }
    this.nextReceiver = Math.min(this.nextReceiver, this.receivers.size());
    return z4;
  }

  public void applySingletonPolicy(ActivityManagerService activityManagerService) {
    boolean z;
    if (this.receivers == null) {
      return;
    }
    for (int i = 0; i < this.receivers.size(); i++) {
      Object obj = this.receivers.get(i);
      if (obj instanceof ResolveInfo) {
        ResolveInfo resolveInfo = (ResolveInfo) obj;
        try {
          ActivityInfo activityInfo = resolveInfo.activityInfo;
          z =
              activityManagerService.isSingleton(
                  activityInfo.processName,
                  activityInfo.applicationInfo,
                  activityInfo.name,
                  activityInfo.flags);
        } catch (SecurityException e) {
          BroadcastQueue.logw(e.getMessage());
          z = false;
        }
        int i2 = resolveInfo.activityInfo.applicationInfo.uid;
        int i3 = this.callingUid;
        if (i3 != 1000 && z && activityManagerService.isValidSingletonCall(i3, i2)) {
          resolveInfo.activityInfo =
              activityManagerService.getActivityInfoForUser(resolveInfo.activityInfo, 0);
        }
      }
    }
  }

  public boolean containsReceiver(Object obj) {
    for (int size = this.receivers.size() - 1; size >= 0; size--) {
      if (isReceiverEquals(obj, this.receivers.get(size))) {
        return true;
      }
    }
    return false;
  }

  public boolean containsAllReceivers(List list) {
    for (int size = list.size() - 1; size >= 0; size--) {
      if (!containsReceiver(list.get(size))) {
        return false;
      }
    }
    return true;
  }

  public int getDeliveryGroupPolicy() {
    BroadcastOptions broadcastOptions = this.options;
    if (broadcastOptions != null) {
      return broadcastOptions.getDeliveryGroupPolicy();
    }
    return 0;
  }

  public boolean matchesDeliveryGroup(BroadcastRecord broadcastRecord) {
    return matchesDeliveryGroup(this, broadcastRecord);
  }

  public static boolean matchesDeliveryGroup(
      BroadcastRecord broadcastRecord, BroadcastRecord broadcastRecord2) {
    IntentFilter deliveryGroupMatchingFilter = getDeliveryGroupMatchingFilter(broadcastRecord);
    if (isMatchingKeyNull(broadcastRecord)
        && isMatchingKeyNull(broadcastRecord2)
        && deliveryGroupMatchingFilter == null) {
      return broadcastRecord.intent.filterEquals(broadcastRecord2.intent);
    }
    if (deliveryGroupMatchingFilter == null
        || deliveryGroupMatchingFilter.asPredicate().test(broadcastRecord2.intent)) {
      return areMatchingKeysEqual(broadcastRecord, broadcastRecord2);
    }
    return false;
  }

  public static boolean isMatchingKeyNull(BroadcastRecord broadcastRecord) {
    return getDeliveryGroupMatchingNamespaceFragment(broadcastRecord) == null
        || getDeliveryGroupMatchingKeyFragment(broadcastRecord) == null;
  }

  public static boolean areMatchingKeysEqual(
      BroadcastRecord broadcastRecord, BroadcastRecord broadcastRecord2) {
    if (Objects.equals(
        getDeliveryGroupMatchingNamespaceFragment(broadcastRecord),
        getDeliveryGroupMatchingNamespaceFragment(broadcastRecord2))) {
      return Objects.equals(
          getDeliveryGroupMatchingKeyFragment(broadcastRecord),
          getDeliveryGroupMatchingKeyFragment(broadcastRecord2));
    }
    return false;
  }

  public static String getDeliveryGroupMatchingNamespaceFragment(BroadcastRecord broadcastRecord) {
    BroadcastOptions broadcastOptions = broadcastRecord.options;
    if (broadcastOptions == null) {
      return null;
    }
    return broadcastOptions.getDeliveryGroupMatchingNamespaceFragment();
  }

  public static String getDeliveryGroupMatchingKeyFragment(BroadcastRecord broadcastRecord) {
    BroadcastOptions broadcastOptions = broadcastRecord.options;
    if (broadcastOptions == null) {
      return null;
    }
    return broadcastOptions.getDeliveryGroupMatchingKeyFragment();
  }

  public static IntentFilter getDeliveryGroupMatchingFilter(BroadcastRecord broadcastRecord) {
    BroadcastOptions broadcastOptions = broadcastRecord.options;
    if (broadcastOptions == null) {
      return null;
    }
    return broadcastOptions.getDeliveryGroupMatchingFilter();
  }

  public boolean allReceiversPending() {
    return this.terminalCount == 0 && this.dispatchTime <= 0;
  }

  public boolean isMatchingRecord(BroadcastRecord broadcastRecord) {
    int indexOfKey = this.mMatchingRecordsCache.indexOfKey(broadcastRecord);
    if (indexOfKey > 0) {
      return ((Boolean) this.mMatchingRecordsCache.valueAt(indexOfKey)).booleanValue();
    }
    boolean z = false;
    boolean z2 = this.receivers.size() == broadcastRecord.receivers.size();
    if (z2) {
      for (int size = this.receivers.size() - 1; size >= 0; size--) {
        if (!isReceiverEquals(this.receivers.get(size), broadcastRecord.receivers.get(size))) {
          break;
        }
      }
    }
    z = z2;
    this.mMatchingRecordsCache.put(broadcastRecord, Boolean.valueOf(z));
    return z;
  }

  public void setMatchingRecordsCache(ArrayMap arrayMap) {
    this.mMatchingRecordsCache = arrayMap;
  }

  public void clearMatchingRecordsCache() {
    this.mMatchingRecordsCache = null;
  }

  public String toString() {
    if (this.mCachedToString == null) {
      if (this.intent.getAction() == null) {
        this.intent.toString();
      }
      this.mCachedToString = "BroadcastRecord{" + toShortString() + "}";
    }
    return this.mCachedToString;
  }

  public String toShortString() {
    if (this.mCachedToShortString == null) {
      String action = this.intent.getAction();
      if (action == null) {
        action = this.intent.toString();
      }
      this.mCachedToShortString =
          Integer.toHexString(System.identityHashCode(this)) + " " + action + "/u" + this.userId;
    }
    return this.mCachedToShortString;
  }

  @NeverCompile
  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1120986464257L, this.userId);
    protoOutputStream.write(1138166333442L, this.intent.getAction());
    protoOutputStream.end(start);
  }
}
