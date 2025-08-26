package com.android.systemui.screenshot.appclips;

import android.app.IActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.screenshot.AssistContentRequester;
import com.android.systemui.screenshot.ImageExporter;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class AppClipsViewModel extends ViewModel {
    public final AppClipsCrossProcessHelper mAppClipsCrossProcessHelper;
    public final AssistContentRequester mAssistContentRequester;
    public final IActivityTaskManager mAtmService;
    public final MutableLiveData mBacklinksLiveData;
    public final Executor mBgExecutor;
    public final Context mContext;
    public final MutableLiveData mErrorLiveData;
    public final ImageExporter mImageExporter;
    public final Executor mMainExecutor;
    public final MutableLiveData mResultLiveData;
    public final MutableLiveData mScreenshotLiveData;
    public final MutableLiveData mSelectedBacklinksLiveData;

    public final class Factory implements ViewModelProvider.Factory {
        public final AppClipsCrossProcessHelper mAppClipsCrossProcessHelper;
        public final AssistContentRequester mAssistContentRequester;
        public final IActivityTaskManager mAtmService;
        public final Executor mBgExecutor;
        public final Context mContext;
        public final ImageExporter mImageExporter;
        public final Executor mMainExecutor;

        public Factory(AppClipsCrossProcessHelper appClipsCrossProcessHelper, ImageExporter imageExporter, IActivityTaskManager iActivityTaskManager, AssistContentRequester assistContentRequester, Context context, Executor executor, Executor executor2) {
            this.mAppClipsCrossProcessHelper = appClipsCrossProcessHelper;
            this.mImageExporter = imageExporter;
            this.mAtmService = iActivityTaskManager;
            this.mAssistContentRequester = assistContentRequester;
            this.mContext = context;
            this.mMainExecutor = executor;
            this.mBgExecutor = executor2;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            if (cls != AppClipsViewModel.class) {
                throw new IllegalArgumentException();
            }
            IActivityTaskManager iActivityTaskManager = this.mAtmService;
            Context context = this.mContext;
            Executor executor = this.mMainExecutor;
            Executor executor2 = this.mBgExecutor;
            return new AppClipsViewModel(this.mAppClipsCrossProcessHelper, this.mImageExporter, iActivityTaskManager, this.mAssistContentRequester, context, executor, executor2, 0);
        }
    }

    public /* synthetic */ AppClipsViewModel(AppClipsCrossProcessHelper appClipsCrossProcessHelper, ImageExporter imageExporter, IActivityTaskManager iActivityTaskManager, AssistContentRequester assistContentRequester, Context context, Executor executor, Executor executor2, int i) {
        this(appClipsCrossProcessHelper, imageExporter, iActivityTaskManager, assistContentRequester, context, executor, executor2);
    }

    public static Intent getMainLauncherIntentForTask(PackageManager packageManager, String str) {
        Intent intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(str);
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, 0);
        if (resolveInfoResolveActivity != null) {
            intent.setComponent(resolveInfoResolveActivity.getComponentInfo().getComponentName());
        }
        return intent;
    }

    public final BacklinkDisplayInfo getInfoThatResolvesIntent(Intent intent, InternalTaskInfo internalTaskInfo) {
        PackageManager packageManager = internalTaskInfo.packageManager;
        List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
        if (listQueryIntentActivities.isEmpty()) {
            DebugLogger.INSTANCE.getClass();
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
            return null;
        }
        ActivityInfo activityInfo = listQueryIntentActivities.get(0).activityInfo;
        if (activityInfo == null) {
            DebugLogger.INSTANCE.getClass();
            boolean z2 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
            return null;
        }
        if (getMainLauncherIntentForTask(packageManager, activityInfo.packageName).resolveActivity(packageManager) != null) {
            return new BacklinkDisplayInfo(activityInfo.loadIcon(packageManager), activityInfo.loadLabel(packageManager).toString());
        }
        DebugLogger.INSTANCE.getClass();
        boolean z3 = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
        return null;
    }

    private AppClipsViewModel(AppClipsCrossProcessHelper appClipsCrossProcessHelper, ImageExporter imageExporter, IActivityTaskManager iActivityTaskManager, AssistContentRequester assistContentRequester, Context context, Executor executor, Executor executor2) {
        this.mAppClipsCrossProcessHelper = appClipsCrossProcessHelper;
        this.mImageExporter = imageExporter;
        this.mAtmService = iActivityTaskManager;
        this.mAssistContentRequester = assistContentRequester;
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mScreenshotLiveData = new MutableLiveData();
        this.mResultLiveData = new MutableLiveData();
        this.mErrorLiveData = new MutableLiveData();
        this.mBacklinksLiveData = new MutableLiveData();
        this.mSelectedBacklinksLiveData = new MutableLiveData();
    }
}
