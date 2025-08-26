package com.android.internal.app;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.TargetInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes5.dex */
public class ResolverListController {
    private static final boolean DEBUG = false;
    private static final String TAG = "ResolverListController";
    private boolean isComputed;
    private final Context mContext;
    private final int mLaunchedFromUid;
    private final UserHandle mQueryIntentsAsUser;
    private final String mReferrerPackage;
    private AbstractResolverComparator mResolverComparator;
    private boolean mSupportAlwaysUseOption;
    private final Intent mTargetIntent;
    private final UserHandle mUserHandle;
    private final PackageManager mpm;

    boolean isComponentFiltered(ComponentName componentName) {
        return false;
    }

    public boolean isComponentPinned(ComponentName componentName) {
        return false;
    }

    public boolean isFixedAtTop(ComponentName componentName) {
        return false;
    }

    public ResolverListController(Context context, PackageManager packageManager, Intent intent, String str, int i, UserHandle userHandle, UserHandle userHandle2) {
        this(context, packageManager, intent, str, i, userHandle, new ResolverRankerServiceResolverComparator(context, intent, str, (AbstractResolverComparator.AfterCompute) null, (ChooserActivityLogger) null, userHandle), userHandle2);
    }

    public ResolverListController(Context context, PackageManager packageManager, Intent intent, String str, int i, UserHandle userHandle, AbstractResolverComparator abstractResolverComparator, UserHandle userHandle2) {
        this.isComputed = false;
        this.mSupportAlwaysUseOption = false;
        this.mContext = context;
        this.mpm = packageManager;
        this.mLaunchedFromUid = i;
        this.mTargetIntent = intent;
        this.mReferrerPackage = str;
        this.mUserHandle = userHandle;
        this.mResolverComparator = abstractResolverComparator;
        this.mQueryIntentsAsUser = userHandle2;
    }

    public ResolveInfo getLastChosen() throws RemoteException {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        Intent intent = this.mTargetIntent;
        return packageManager.getLastChosenActivity(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 65536);
    }

    public void setLastChosen(Intent intent, IntentFilter intentFilter, int i) throws RemoteException {
        AppGlobals.getPackageManager().setLastChosenActivity(intent, intent.resolveType(this.mContext.getContentResolver()), 65536, intentFilter, i, intent.getComponent());
    }

    public List<ResolverActivity.ResolvedComponentInfo> getResolversForIntent(boolean z, boolean z2, boolean z3, List<Intent> list) {
        return getResolversForIntentAsUser(z, z2, z3, list, this.mQueryIntentsAsUser);
    }

    public List<ResolverActivity.ResolvedComponentInfo> getResolversForIntentAsUser(boolean z, boolean z2, boolean z3, List<Intent> list, UserHandle userHandle) {
        return getResolversForIntentAsUserInternal(list, userHandle, (z ? 64 : 0) | (z3 ? 65536 : 0) | 786432 | (z2 ? 128 : 0) | 536870912);
    }

    private List<ResolverActivity.ResolvedComponentInfo> getResolversForIntentAsUserInternal(List<Intent> list, UserHandle userHandle, int i) {
        int size = list.size();
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = list.get(i2);
            int i3 = (intent.isWebIntent() || (intent.getFlags() & 2048) != 0) ? 8388608 | i : i;
            if (intent.getClass() != Intent.class) {
                intent = new Intent(intent);
            }
            List<ResolveInfo> listQueryIntentActivitiesAsUser = this.mpm.queryIntentActivitiesAsUser(intent, i3, userHandle);
            if (listQueryIntentActivitiesAsUser != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                addResolveListDedupe(arrayList, intent, listQueryIntentActivitiesAsUser);
            }
        }
        return arrayList;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public void addResolveListDedupe(List<ResolverActivity.ResolvedComponentInfo> list, Intent intent, List<ResolveInfo> list2) {
        int size = list2.size();
        int size2 = list.size();
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = list2.get(i);
            if (resolveInfo.userHandle != null) {
                int i2 = 0;
                while (true) {
                    if (i2 < size2) {
                        ResolverActivity.ResolvedComponentInfo resolvedComponentInfo = list.get(i2);
                        if (isSameResolvedComponent(resolveInfo, resolvedComponentInfo)) {
                            resolvedComponentInfo.add(intent, resolveInfo);
                            break;
                        }
                        i2++;
                    } else {
                        ComponentName componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                        ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2 = new ResolverActivity.ResolvedComponentInfo(componentName, intent, resolveInfo);
                        resolvedComponentInfo2.setPinned(isComponentPinned(componentName));
                        resolvedComponentInfo2.setFixedAtTop(isFixedAtTop(componentName));
                        list.add(resolvedComponentInfo2);
                        break;
                    }
                }
            } else {
                Log.w(TAG, "Skipping ResolveInfo with no userHandle: " + resolveInfo);
            }
        }
    }

    public ArrayList<ResolverActivity.ResolvedComponentInfo> filterIneligibleActivities(List<ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        ArrayList<ResolverActivity.ResolvedComponentInfo> arrayList = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            ActivityInfo activityInfo = list.get(size).getResolveInfoAt(0).activityInfo;
            if (ActivityManager.checkComponentPermission(activityInfo.permission, this.mLaunchedFromUid, activityInfo.applicationInfo.uid, activityInfo.exported) != 0 || isComponentFiltered(activityInfo.getComponentName())) {
                if (z && arrayList == null) {
                    arrayList = new ArrayList<>(list);
                }
                list.remove(size);
            }
        }
        return arrayList;
    }

    public ArrayList<ResolverActivity.ResolvedComponentInfo> filterLowPriority(List<ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        ResolveInfo resolveInfoAt = list.get(0).getResolveInfoAt(0);
        int size = list.size();
        ArrayList<ResolverActivity.ResolvedComponentInfo> arrayList = null;
        for (int i = 1; i < size; i++) {
            ResolveInfo resolveInfoAt2 = list.get(i).getResolveInfoAt(0);
            if (resolveInfoAt.priority != resolveInfoAt2.priority || resolveInfoAt.isDefault != resolveInfoAt2.isDefault) {
                while (i < size) {
                    if (z && arrayList == null) {
                        arrayList = new ArrayList<>(list);
                    }
                    list.remove(i);
                    size--;
                }
            }
        }
        return arrayList;
    }

    private class ComputeCallback implements AbstractResolverComparator.AfterCompute {
        private CountDownLatch mFinishComputeSignal;

        public ComputeCallback(ResolverListController resolverListController, CountDownLatch countDownLatch) {
            this.mFinishComputeSignal = countDownLatch;
        }

        @Override // com.android.internal.app.AbstractResolverComparator.AfterCompute
        public void afterCompute() {
            this.mFinishComputeSignal.countDown();
        }
    }

    private void compute(List<ResolverActivity.ResolvedComponentInfo> list) throws InterruptedException {
        if (this.mResolverComparator == null) {
            Log.d(TAG, "Comparator has already been destroyed; skipped.");
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mResolverComparator.setCallBack(new ComputeCallback(this, countDownLatch));
        this.mResolverComparator.compute(list);
        countDownLatch.await();
        this.isComputed = true;
    }

    public void sort(List<ResolverActivity.ResolvedComponentInfo> list) {
        try {
            System.currentTimeMillis();
            if (!this.isComputed) {
                compute(list);
            }
            Collections.sort(list, this.mResolverComparator);
            System.currentTimeMillis();
        } catch (InterruptedException e) {
            Log.e(TAG, "Compute & Sort was interrupted: " + e);
        }
    }

    public void topK(List<ResolverActivity.ResolvedComponentInfo> list, int i) {
        int i2;
        if (list == null || list.isEmpty() || i <= 0) {
            return;
        }
        if (list.size() <= i) {
            sort(list);
            return;
        }
        try {
            System.currentTimeMillis();
            if (!this.isComputed) {
                compute(list);
            }
            PriorityQueue priorityQueue = new PriorityQueue(i, new Comparator() { // from class: com.android.internal.app.ResolverListController$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.lambda$topK$0((ResolverActivity.ResolvedComponentInfo) obj, (ResolverActivity.ResolvedComponentInfo) obj2);
                }
            });
            int size = list.size();
            int i3 = size - 1;
            int i4 = size - i;
            priorityQueue.addAll(list.subList(i4, size));
            for (int i5 = i4 - 1; i5 >= 0; i5--) {
                ResolverActivity.ResolvedComponentInfo resolvedComponentInfo = list.get(i5);
                if ((-this.mResolverComparator.compare(resolvedComponentInfo, (ResolverActivity.ResolvedComponentInfo) priorityQueue.peek())) > 0) {
                    i2 = i3 - 1;
                    list.set(i3, (ResolverActivity.ResolvedComponentInfo) priorityQueue.poll());
                    priorityQueue.add(resolvedComponentInfo);
                } else {
                    i2 = i3 - 1;
                    list.set(i3, resolvedComponentInfo);
                }
                i3 = i2;
            }
            while (!priorityQueue.isEmpty()) {
                list.set(i3, (ResolverActivity.ResolvedComponentInfo) priorityQueue.poll());
                i3--;
            }
            System.currentTimeMillis();
        } catch (InterruptedException e) {
            Log.e(TAG, "Compute & greatestOf was interrupted: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$topK$0(ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2) {
        return -this.mResolverComparator.compare(resolvedComponentInfo, resolvedComponentInfo2);
    }

    private static boolean isSameResolvedComponent(ResolveInfo resolveInfo, ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        return activityInfo.packageName.equals(resolvedComponentInfo.name.getPackageName()) && activityInfo.name.equals(resolvedComponentInfo.name.getClassName());
    }

    public float getScore(DisplayResolveInfo displayResolveInfo) {
        return this.mResolverComparator.getScore(displayResolveInfo);
    }

    public float getScore(TargetInfo targetInfo) {
        return this.mResolverComparator.getScore(targetInfo);
    }

    public void updateModel(TargetInfo targetInfo) {
        this.mResolverComparator.updateModel(targetInfo);
    }

    public void updateChooserCounts(String str, UserHandle userHandle, String str2) {
        this.mResolverComparator.updateChooserCounts(str, userHandle, str2);
    }

    public void destroy() {
        this.mResolverComparator.destroy();
    }

    public void semSetSupportAlwaysUseOption(boolean z) {
        this.mSupportAlwaysUseOption = z;
    }
}
