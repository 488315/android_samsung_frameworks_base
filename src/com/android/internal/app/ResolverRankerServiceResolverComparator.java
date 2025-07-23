package com.android.internal.app;

import android.app.usage.UsageStats;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.metrics.LogMaker;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.resolver.IResolverRankerResult;
import android.service.resolver.IResolverRankerService;
import android.service.resolver.ResolverRankerService;
import android.service.resolver.ResolverTarget;
import android.util.Log;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverRankerServiceResolverComparator;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.logging.MetricsLogger;
import com.google.android.collect.Lists;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
class ResolverRankerServiceResolverComparator extends AbstractResolverComparator {
    private static final int CONNECTION_COST_TIMEOUT_MILLIS = 200;
    private static final boolean DEBUG = false;
    private static final float RECENCY_MULTIPLIER = 2.0f;
    private static final long RECENCY_TIME_PERIOD = 43200000;
    private static final String TAG = "RRSResolverComparator";
    private static final long USAGE_STATS_PERIOD = 604800000;
    private String mAction;
    private final Collator mCollator;
    private ResolverRankerServiceComparatorModel mComparatorModel;
    private CountDownLatch mConnectSignal;
    private ResolverRankerServiceConnection mConnection;
    private Context mContext;
    private final long mCurrentTime;
    private final Object mLock;
    private IResolverRankerService mRanker;
    private ComponentName mRankerServiceName;
    private final String mReferrerPackage;
    private ComponentName mResolvedRankerName;
    private final long mSinceTime;
    private final Map<UserHandle, Map<String, UsageStats>> mStatsPerUser;
    private ArrayList<ResolverTarget> mTargets;
    private final Map<UserHandle, LinkedHashMap<ComponentName, ResolverTarget>> mTargetsDictPerUser;

    public ResolverRankerServiceResolverComparator(Context context, Intent intent, String str, AbstractResolverComparator.AfterCompute afterCompute, ChooserActivityLogger chooserActivityLogger, UserHandle userHandle) {
        this(context, intent, str, afterCompute, chooserActivityLogger, Lists.newArrayList(userHandle));
    }

    public ResolverRankerServiceResolverComparator(Context context, Intent intent, String str, AbstractResolverComparator.AfterCompute afterCompute, ChooserActivityLogger chooserActivityLogger, List<UserHandle> list) {
        super(context, intent, list);
        this.mLock = new Object();
        this.mCollator = Collator.getInstance(context.getResources().getConfiguration().locale);
        this.mReferrerPackage = str;
        this.mContext = context;
        long currentTimeMillis = System.currentTimeMillis();
        this.mCurrentTime = currentTimeMillis;
        this.mSinceTime = currentTimeMillis - 604800000;
        this.mStatsPerUser = new HashMap();
        this.mTargetsDictPerUser = new HashMap();
        for (UserHandle userHandle : list) {
            this.mStatsPerUser.put(userHandle, this.mUsmMap.get(userHandle).queryAndAggregateUsageStats(this.mSinceTime, this.mCurrentTime));
            this.mTargetsDictPerUser.put(userHandle, new LinkedHashMap<>());
        }
        this.mAction = intent.getAction();
        this.mRankerServiceName = new ComponentName(this.mContext, getClass());
        setCallBack(afterCompute);
        setChooserActivityLogger(chooserActivityLogger);
        this.mComparatorModel = buildUpdatedModel();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void handleResultMessage(Message message) {
        if (message.what != 0) {
            return;
        }
        if (message.obj == null) {
            Log.e(TAG, "Receiving null prediction results.");
            return;
        }
        List list = (List) message.obj;
        if (list != null && this.mTargets != null && list.size() == this.mTargets.size()) {
            int size = this.mTargets.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                float selectProbability = ((ResolverTarget) list.get(i)).getSelectProbability();
                if (selectProbability != this.mTargets.get(i).getSelectProbability()) {
                    this.mTargets.get(i).setSelectProbability(selectProbability);
                    z = true;
                }
            }
            if (z) {
                this.mRankerServiceName = this.mResolvedRankerName;
                this.mComparatorModel = buildUpdatedModel();
                return;
            }
            return;
        }
        Log.e(TAG, "Sizes of sent and received ResolverTargets diff.");
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void doCompute(List<ResolverActivity.ResolvedComponentInfo> list) {
        float f;
        long j = this.mCurrentTime - 43200000;
        float f2 = 1.0f;
        float f3 = 1.0f;
        float f4 = 1.0f;
        float f5 = 1.0f;
        for (ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
            ResolverTarget resolverTarget = new ResolverTarget();
            int i = 0;
            LinkedHashMap<ComponentName, ResolverTarget> linkedHashMap = this.mTargetsDictPerUser.get(resolvedComponentInfo.getResolveInfoAt(0).userHandle);
            Map<String, UsageStats> map = this.mStatsPerUser.get(resolvedComponentInfo.getResolveInfoAt(0).userHandle);
            if (linkedHashMap != null && map != null) {
                linkedHashMap.put(resolvedComponentInfo.name, resolverTarget);
                UsageStats usageStats = map.get(resolvedComponentInfo.name.getPackageName());
                if (usageStats != null) {
                    if (!resolvedComponentInfo.name.getPackageName().equals(this.mReferrerPackage) && !isPersistentProcess(resolvedComponentInfo)) {
                        float max = Math.max(usageStats.getLastTimeUsed() - j, 0L);
                        resolverTarget.setRecencyScore(max);
                        if (max > f2) {
                            f2 = max;
                        }
                    }
                    float totalTimeInForeground = usageStats.getTotalTimeInForeground();
                    resolverTarget.setTimeSpentScore(totalTimeInForeground);
                    if (totalTimeInForeground > f3) {
                        f3 = totalTimeInForeground;
                    }
                    float f6 = usageStats.mLaunchCount;
                    resolverTarget.setLaunchScore(f6);
                    if (f6 > f4) {
                        f4 = f6;
                    }
                    if (usageStats.mChooserCounts == null || this.mAction == null || usageStats.mChooserCounts.get(this.mAction) == null) {
                        f = 0.0f;
                    } else {
                        f = usageStats.mChooserCounts.get(this.mAction).getOrDefault(this.mContentType, 0).intValue();
                        if (this.mAnnotations != null) {
                            int i2 = 0;
                            while (i2 < this.mAnnotations.length) {
                                f += usageStats.mChooserCounts.get(this.mAction).getOrDefault(this.mAnnotations[i2], Integer.valueOf(r17)).intValue();
                                i2++;
                                i = i;
                            }
                        }
                    }
                    resolverTarget.setChooserScore(f);
                    if (f > f5) {
                        f5 = f;
                    }
                }
            }
        }
        this.mTargets = new ArrayList<>();
        Iterator<UserHandle> it = this.mTargetsDictPerUser.keySet().iterator();
        while (it.hasNext()) {
            this.mTargets.addAll(this.mTargetsDictPerUser.get(it.next()).values());
        }
        Iterator<ResolverTarget> it2 = this.mTargets.iterator();
        while (it2.hasNext()) {
            ResolverTarget next = it2.next();
            float recencyScore = next.getRecencyScore() / f2;
            setFeatures(next, recencyScore * recencyScore * 2.0f, next.getLaunchScore() / f4, next.getTimeSpentScore() / f3, next.getChooserScore() / f5);
            addDefaultSelectProbability(next);
        }
        predictSelectProbabilities(this.mTargets);
        this.mComparatorModel = buildUpdatedModel();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        return this.mComparatorModel.getComparator().compare(resolveInfo, resolveInfo2);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public float getScore(TargetInfo targetInfo) {
        return this.mComparatorModel.getScore(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void updateModel(TargetInfo targetInfo) {
        synchronized (this.mLock) {
            this.mComparatorModel.notifyOnTargetSelected(targetInfo);
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void destroy() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        ResolverRankerServiceConnection resolverRankerServiceConnection = this.mConnection;
        if (resolverRankerServiceConnection != null) {
            this.mContext.unbindService(resolverRankerServiceConnection);
            this.mConnection.destroy();
        }
        afterCompute();
    }

    private void initRanker(Context context) {
        synchronized (this.mLock) {
            if (this.mConnection == null || this.mRanker == null) {
                Intent resolveRankerService = resolveRankerService();
                if (resolveRankerService == null) {
                    return;
                }
                this.mConnectSignal = new CountDownLatch(1);
                ResolverRankerServiceConnection resolverRankerServiceConnection = new ResolverRankerServiceConnection(this.mConnectSignal);
                this.mConnection = resolverRankerServiceConnection;
                context.bindServiceAsUser(resolveRankerService, resolverRankerServiceConnection, 1, UserHandle.SYSTEM);
            }
        }
    }

    private Intent resolveRankerService() {
        Intent intent = new Intent(ResolverRankerService.SERVICE_INTERFACE);
        for (ResolveInfo resolveInfo : this.mContext.getPackageManager().queryIntentServices(intent, 0)) {
            if (resolveInfo != null && resolveInfo.serviceInfo != null && resolveInfo.serviceInfo.applicationInfo != null) {
                ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.applicationInfo.packageName, resolveInfo.serviceInfo.name);
                try {
                    if (!"android.permission.BIND_RESOLVER_RANKER_SERVICE".equals(this.mContext.getPackageManager().getServiceInfo(componentName, 0).permission)) {
                        Log.w(TAG, "ResolverRankerService " + componentName + " does not require permission android.permission.BIND_RESOLVER_RANKER_SERVICE - this service will not be queried for ResolverRankerServiceResolverComparator. add android:permission=\"android.permission.BIND_RESOLVER_RANKER_SERVICE\" to the <service> tag for " + componentName + " in the manifest.");
                    } else {
                        if (this.mContext.getPackageManager().checkPermission("android.permission.PROVIDE_RESOLVER_RANKER_SERVICE", resolveInfo.serviceInfo.packageName) == 0) {
                            this.mResolvedRankerName = componentName;
                            intent.setComponent(componentName);
                            return intent;
                        }
                        Log.w(TAG, "ResolverRankerService " + componentName + " does not hold permission android.permission.PROVIDE_RESOLVER_RANKER_SERVICE - this service will not be queried for ResolverRankerServiceResolverComparator.");
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e(TAG, "Could not look up service " + componentName + "; component name not found");
                }
            }
        }
        return null;
    }

    private class ResolverRankerServiceConnection implements ServiceConnection {
        private final CountDownLatch mConnectSignal;
        public final IResolverRankerResult resolverRankerResult = new IResolverRankerResult.Stub() { // from class: com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceConnection.1
            @Override // android.service.resolver.IResolverRankerResult
            public void sendResult(List<ResolverTarget> list) throws RemoteException {
                synchronized (ResolverRankerServiceResolverComparator.this.mLock) {
                    Message obtain = Message.obtain();
                    obtain.what = 0;
                    obtain.obj = list;
                    ResolverRankerServiceResolverComparator.this.mHandler.sendMessage(obtain);
                }
            }
        };

        public ResolverRankerServiceConnection(CountDownLatch countDownLatch) {
            this.mConnectSignal = countDownLatch;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (ResolverRankerServiceResolverComparator.this.mLock) {
                ResolverRankerServiceResolverComparator.this.mRanker = IResolverRankerService.Stub.asInterface(iBinder);
                ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = ResolverRankerServiceResolverComparator.this;
                resolverRankerServiceResolverComparator.mComparatorModel = resolverRankerServiceResolverComparator.buildUpdatedModel();
                this.mConnectSignal.countDown();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (ResolverRankerServiceResolverComparator.this.mLock) {
                destroy();
            }
        }

        public void destroy() {
            synchronized (ResolverRankerServiceResolverComparator.this.mLock) {
                ResolverRankerServiceResolverComparator.this.mRanker = null;
                ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = ResolverRankerServiceResolverComparator.this;
                resolverRankerServiceResolverComparator.mComparatorModel = resolverRankerServiceResolverComparator.buildUpdatedModel();
            }
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void beforeCompute() {
        super.beforeCompute();
        Iterator<UserHandle> it = this.mTargetsDictPerUser.keySet().iterator();
        while (it.hasNext()) {
            this.mTargetsDictPerUser.get(it.next()).clear();
        }
        this.mTargets = null;
        this.mRankerServiceName = new ComponentName(this.mContext, getClass());
        this.mComparatorModel = buildUpdatedModel();
        this.mResolvedRankerName = null;
        initRanker(this.mContext);
    }

    private void predictSelectProbabilities(List<ResolverTarget> list) {
        if (this.mConnection != null) {
            try {
                this.mConnectSignal.await(200L, TimeUnit.MILLISECONDS);
                synchronized (this.mLock) {
                    IResolverRankerService iResolverRankerService = this.mRanker;
                    if (iResolverRankerService != null) {
                        iResolverRankerService.predict(list, this.mConnection.resolverRankerResult);
                        return;
                    }
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Error in Predict: " + e);
            } catch (InterruptedException unused) {
                Log.e(TAG, "Error in Wait for Service Connection.");
            }
        }
        afterCompute();
    }

    private void addDefaultSelectProbability(ResolverTarget resolverTarget) {
        resolverTarget.setSelectProbability((float) (1.0d / (Math.exp(1.6568f - ((((resolverTarget.getLaunchScore() * 2.5543f) + (resolverTarget.getTimeSpentScore() * 2.8412f)) + (resolverTarget.getRecencyScore() * 0.269f)) + (resolverTarget.getChooserScore() * 4.2222f))) + 1.0d)));
    }

    private void setFeatures(ResolverTarget resolverTarget, float f, float f2, float f3, float f4) {
        resolverTarget.setRecencyScore(f);
        resolverTarget.setLaunchScore(f2);
        resolverTarget.setTimeSpentScore(f3);
        resolverTarget.setChooserScore(f4);
    }

    static boolean isPersistentProcess(ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        return (resolvedComponentInfo == null || resolvedComponentInfo.getCount() <= 0 || (resolvedComponentInfo.getResolveInfoAt(0).activityInfo.applicationInfo.flags & 8) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ResolverRankerServiceComparatorModel buildUpdatedModel() {
        return new ResolverRankerServiceComparatorModel(this.mStatsPerUser, this.mTargetsDictPerUser, this.mTargets, this.mCollator, this.mRanker, this.mRankerServiceName, this.mAnnotations != null, this.mPmMap);
    }

    static class ResolverRankerServiceComparatorModel implements ResolverComparatorModel {
        private final boolean mAnnotationsUsed;
        private final Collator mCollator;
        private final Map<UserHandle, PackageManager> mPmMap;
        private final IResolverRankerService mRanker;
        private final ComponentName mRankerServiceName;
        private final Map<UserHandle, Map<String, UsageStats>> mStatsPerUser;
        private final List<ResolverTarget> mTargets;
        private final Map<UserHandle, LinkedHashMap<ComponentName, ResolverTarget>> mTargetsDictPerUser;

        ResolverRankerServiceComparatorModel(Map<UserHandle, Map<String, UsageStats>> map, Map<UserHandle, LinkedHashMap<ComponentName, ResolverTarget>> map2, List<ResolverTarget> list, Collator collator, IResolverRankerService iResolverRankerService, ComponentName componentName, boolean z, Map<UserHandle, PackageManager> map3) {
            this.mStatsPerUser = map;
            this.mTargetsDictPerUser = map2;
            this.mTargets = list;
            this.mCollator = collator;
            this.mRanker = iResolverRankerService;
            this.mRankerServiceName = componentName;
            this.mAnnotationsUsed = z;
            this.mPmMap = map3;
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public Comparator<ResolveInfo> getComparator() {
            return new Comparator() { // from class: com.android.internal.app.ResolverRankerServiceResolverComparator$ResolverRankerServiceComparatorModel$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$getComparator$0;
                    lambda$getComparator$0 = ResolverRankerServiceResolverComparator.ResolverRankerServiceComparatorModel.this.lambda$getComparator$0((ResolveInfo) obj, (ResolveInfo) obj2);
                    return lambda$getComparator$0;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getComparator$0(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
            int compare;
            ResolverTarget activityResolverTargetForUser = getActivityResolverTargetForUser(resolveInfo.activityInfo, resolveInfo.userHandle);
            ResolverTarget activityResolverTargetForUser2 = getActivityResolverTargetForUser(resolveInfo2.activityInfo, resolveInfo2.userHandle);
            if (activityResolverTargetForUser != null && activityResolverTargetForUser2 != null && (compare = Float.compare(activityResolverTargetForUser2.getSelectProbability(), activityResolverTargetForUser.getSelectProbability())) != 0) {
                return compare > 0 ? 1 : -1;
            }
            CharSequence loadLabel = this.mPmMap.containsKey(resolveInfo.userHandle) ? resolveInfo.loadLabel(this.mPmMap.get(resolveInfo.userHandle)) : null;
            if (loadLabel == null) {
                loadLabel = resolveInfo.activityInfo.name;
            }
            CharSequence loadLabel2 = this.mPmMap.containsKey(resolveInfo2.userHandle) ? resolveInfo2.loadLabel(this.mPmMap.get(resolveInfo2.userHandle)) : null;
            if (loadLabel2 == null) {
                loadLabel2 = resolveInfo2.activityInfo.name;
            }
            return this.mCollator.compare(loadLabel.toString().trim(), loadLabel2.toString().trim());
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public float getScore(TargetInfo targetInfo) {
            if (!this.mTargetsDictPerUser.containsKey(targetInfo.getResolveInfo().userHandle) || this.mTargetsDictPerUser.get(targetInfo.getResolveInfo().userHandle).get(targetInfo.getResolvedComponentName()) == null) {
                return 0.0f;
            }
            return this.mTargetsDictPerUser.get(targetInfo.getResolveInfo().userHandle).get(targetInfo.getResolvedComponentName()).getSelectProbability();
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public void notifyOnTargetSelected(TargetInfo targetInfo) {
            if (this.mRanker != null) {
                try {
                    int indexOf = this.mTargetsDictPerUser.containsKey(targetInfo.getResolveInfo().userHandle) ? new ArrayList(this.mTargetsDictPerUser.get(targetInfo.getResolveInfo().userHandle).keySet()).indexOf(targetInfo.getResolvedComponentName()) : -1;
                    if (indexOf < 0 || this.mTargets == null) {
                        return;
                    }
                    float score = getScore(targetInfo);
                    Iterator<ResolverTarget> it = this.mTargets.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        if (it.next().getSelectProbability() > score) {
                            i++;
                        }
                    }
                    logMetrics(i);
                    this.mRanker.train(this.mTargets, indexOf);
                } catch (RemoteException e) {
                    Log.e(ResolverRankerServiceResolverComparator.TAG, "Error in Train: " + e);
                }
            }
        }

        private void logMetrics(int i) {
            if (this.mRankerServiceName != null) {
                MetricsLogger metricsLogger = new MetricsLogger();
                LogMaker logMaker = new LogMaker(1085);
                logMaker.setComponentName(this.mRankerServiceName);
                logMaker.addTaggedData(1086, Integer.valueOf(this.mAnnotationsUsed ? 1 : 0));
                logMaker.addTaggedData(1087, Integer.valueOf(i));
                metricsLogger.write(logMaker);
            }
        }

        private ResolverTarget getActivityResolverTargetForUser(ActivityInfo activityInfo, UserHandle userHandle) {
            if (this.mStatsPerUser == null || !this.mTargetsDictPerUser.containsKey(userHandle)) {
                return null;
            }
            return this.mTargetsDictPerUser.get(userHandle).get(new ComponentName(activityInfo.packageName, activityInfo.name));
        }
    }
}
