package com.android.internal.app;

import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.AppPredictionServiceResolverComparator;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.TargetInfo;
import com.google.android.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
class AppPredictionServiceResolverComparator extends AbstractResolverComparator {
    private static final String TAG = "APSResolverComparator";
    private final AppPredictor mAppPredictor;
    private ResolverComparatorModel mComparatorModel;
    private final Context mContext;
    private final Intent mIntent;
    private final ModelBuilder mModelBuilder;
    private final String mReferrerPackage;
    private ResolverRankerServiceResolverComparator mResolverRankerService;
    private ResolverAppPredictorCallback mSortingCallback;
    private final UserHandle mUser;

    AppPredictionServiceResolverComparator(Context context, Intent intent, String str, AppPredictor appPredictor, UserHandle userHandle, ChooserActivityLogger chooserActivityLogger) {
        super(context, intent, Lists.newArrayList(userHandle));
        this.mContext = context;
        this.mIntent = intent;
        this.mAppPredictor = appPredictor;
        this.mUser = userHandle;
        this.mReferrerPackage = str;
        setChooserActivityLogger(chooserActivityLogger);
        ModelBuilder modelBuilder = new ModelBuilder(appPredictor, userHandle);
        this.mModelBuilder = modelBuilder;
        this.mComparatorModel = modelBuilder.buildFromRankedList(Collections.EMPTY_LIST);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void destroy() {
        ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = this.mResolverRankerService;
        if (resolverRankerServiceResolverComparator != null) {
            resolverRankerServiceResolverComparator.destroy();
            this.mResolverRankerService = null;
            this.mComparatorModel = this.mModelBuilder.buildFallbackModel(null);
        }
        ResolverAppPredictorCallback resolverAppPredictorCallback = this.mSortingCallback;
        if (resolverAppPredictorCallback != null) {
            resolverAppPredictorCallback.destroy();
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        return this.mComparatorModel.getComparator().compare(resolveInfo, resolveInfo2);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    float getScore(TargetInfo targetInfo) {
        return this.mComparatorModel.getScore(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void updateModel(TargetInfo targetInfo) {
        this.mComparatorModel.notifyOnTargetSelected(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void handleResultMessage(Message message) {
        if (message.what == 0 && message.obj != null) {
            this.mComparatorModel = this.mModelBuilder.buildFromRankedList((List) message.obj);
        } else if (message.obj == null && this.mResolverRankerService == null) {
            Log.e(TAG, "Unexpected null result");
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void doCompute(final List<ResolverActivity.ResolvedComponentInfo> list) {
        if (list.isEmpty()) {
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
            arrayList.add(new AppTarget.Builder(new AppTargetId(resolvedComponentInfo.name.flattenToString()), resolvedComponentInfo.name.getPackageName(), this.mUser).setClassName(resolvedComponentInfo.name.getClassName()).build());
        }
        ResolverAppPredictorCallback resolverAppPredictorCallback = this.mSortingCallback;
        if (resolverAppPredictorCallback != null) {
            resolverAppPredictorCallback.destroy();
        }
        this.mSortingCallback = new ResolverAppPredictorCallback(new Consumer() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppPredictionServiceResolverComparator.this.lambda$doCompute$0(list, (List) obj);
            }
        });
        this.mAppPredictor.sortTargets(arrayList, Executors.newSingleThreadExecutor(), this.mSortingCallback.asConsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doCompute$0(List list, List list2) {
        if (list2.isEmpty()) {
            Log.i(TAG, "AppPredictionService disabled. Using resolver.");
            setupFallbackModel(list);
        } else {
            Log.i(TAG, "AppPredictionService response received");
            handleResult(list2);
        }
    }

    private void setupFallbackModel(List<ResolverActivity.ResolvedComponentInfo> list) {
        ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = new ResolverRankerServiceResolverComparator(this.mContext, this.mIntent, this.mReferrerPackage, new AbstractResolverComparator.AfterCompute() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$$ExternalSyntheticLambda1
            @Override // com.android.internal.app.AbstractResolverComparator.AfterCompute
            public final void afterCompute() {
                AppPredictionServiceResolverComparator.this.lambda$setupFallbackModel$1();
            }
        }, getChooserActivityLogger(), this.mUser);
        this.mResolverRankerService = resolverRankerServiceResolverComparator;
        this.mComparatorModel = this.mModelBuilder.buildFallbackModel(resolverRankerServiceResolverComparator);
        this.mResolverRankerService.compute(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupFallbackModel$1() {
        this.mHandler.sendEmptyMessage(0);
    }

    private void handleResult(List<AppTarget> list) {
        if (this.mHandler.hasMessages(1)) {
            this.mComparatorModel = this.mModelBuilder.buildFromRankedList(list);
            this.mHandler.removeMessages(1);
            afterCompute();
        }
    }

    static class ModelBuilder {
        private final AppPredictor mAppPredictor;
        private final UserHandle mUser;

        ModelBuilder(AppPredictor appPredictor, UserHandle userHandle) {
            this.mAppPredictor = appPredictor;
            this.mUser = userHandle;
        }

        ResolverComparatorModel buildFromRankedList(List<AppTarget> list) {
            return new AppPredictionServiceComparatorModel(this.mAppPredictor, this.mUser, buildTargetRanksMapFromSortedTargets(list));
        }

        ResolverComparatorModel buildFallbackModel(ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator) {
            return adaptLegacyResolverComparatorToComparatorModel(resolverRankerServiceResolverComparator);
        }

        private Map<ComponentName, Integer> buildTargetRanksMapFromSortedTargets(List<AppTarget> list) {
            HashMap hashMap = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                ComponentName componentName = new ComponentName(list.get(i).getPackageName(), list.get(i).getClassName());
                hashMap.put(componentName, Integer.valueOf(i));
                Log.i(AppPredictionServiceResolverComparator.TAG, "handleSortedAppTargets, sortedAppTargets #" + i + ": " + componentName);
            }
            return hashMap;
        }

        /* renamed from: com.android.internal.app.AppPredictionServiceResolverComparator$ModelBuilder$1, reason: invalid class name */
        class AnonymousClass1 implements ResolverComparatorModel {
            final /* synthetic */ AbstractResolverComparator val$comparator;

            AnonymousClass1(ModelBuilder modelBuilder, AbstractResolverComparator abstractResolverComparator) {
                this.val$comparator = abstractResolverComparator;
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public Comparator<ResolveInfo> getComparator() {
                final AbstractResolverComparator abstractResolverComparator = this.val$comparator;
                return new Comparator() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$ModelBuilder$1$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compare;
                        compare = AbstractResolverComparator.this.compare((ResolveInfo) obj, (ResolveInfo) obj2);
                        return compare;
                    }
                };
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public float getScore(TargetInfo targetInfo) {
                return this.val$comparator.getScore(targetInfo);
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public void notifyOnTargetSelected(TargetInfo targetInfo) {
                this.val$comparator.updateModel(targetInfo);
            }
        }

        private ResolverComparatorModel adaptLegacyResolverComparatorToComparatorModel(AbstractResolverComparator abstractResolverComparator) {
            return new AnonymousClass1(this, abstractResolverComparator);
        }
    }

    static class AppPredictionServiceComparatorModel implements ResolverComparatorModel {
        private final AppPredictor mAppPredictor;
        private final Map<ComponentName, Integer> mTargetRanks;
        private final UserHandle mUser;

        AppPredictionServiceComparatorModel(AppPredictor appPredictor, UserHandle userHandle, Map<ComponentName, Integer> map) {
            this.mAppPredictor = appPredictor;
            this.mUser = userHandle;
            this.mTargetRanks = map;
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public Comparator<ResolveInfo> getComparator() {
            return new Comparator() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$AppPredictionServiceComparatorModel$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$getComparator$0;
                    lambda$getComparator$0 = AppPredictionServiceResolverComparator.AppPredictionServiceComparatorModel.this.lambda$getComparator$0((ResolveInfo) obj, (ResolveInfo) obj2);
                    return lambda$getComparator$0;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getComparator$0(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
            Integer num = this.mTargetRanks.get(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            Integer num2 = this.mTargetRanks.get(new ComponentName(resolveInfo2.activityInfo.packageName, resolveInfo2.activityInfo.name));
            if (num == null && num2 == null) {
                return 0;
            }
            if (num == null) {
                return -1;
            }
            if (num2 == null) {
                return 1;
            }
            return num.intValue() - num2.intValue();
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public float getScore(TargetInfo targetInfo) {
            if (this.mTargetRanks.get(targetInfo.getResolvedComponentName()) == null) {
                Log.w(AppPredictionServiceResolverComparator.TAG, "Score requested for unknown component. Did you call compute yet?");
                return 0.0f;
            }
            return 1.0f - (r2.intValue() / (((this.mTargetRanks.size() - 1) * this.mTargetRanks.size()) / 2));
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public void notifyOnTargetSelected(TargetInfo targetInfo) {
            this.mAppPredictor.notifyAppTargetEvent(new AppTargetEvent.Builder(new AppTarget.Builder(new AppTargetId(targetInfo.getResolvedComponentName().toString()), targetInfo.getResolvedComponentName().getPackageName(), this.mUser).setClassName(targetInfo.getResolvedComponentName().getClassName()).build(), 1).build());
        }
    }
}
