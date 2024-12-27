package com.android.systemui.screenshot.appclips;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.screenshot.ImageExporter;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AppClipsViewModel extends ViewModel {
    public final AppClipsCrossProcessHelper mAppClipsCrossProcessHelper;
    public final Executor mBgExecutor;
    public final ImageExporter mImageExporter;
    public final Executor mMainExecutor;
    public final MutableLiveData mScreenshotLiveData = new MutableLiveData();
    public final MutableLiveData mResultLiveData = new MutableLiveData();
    public final MutableLiveData mErrorLiveData = new MutableLiveData();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory implements ViewModelProvider.Factory {
        public final AppClipsCrossProcessHelper mAppClipsCrossProcessHelper;
        public final Executor mBgExecutor;
        public final ImageExporter mImageExporter;
        public final Executor mMainExecutor;

        public Factory(AppClipsCrossProcessHelper appClipsCrossProcessHelper, ImageExporter imageExporter, Executor executor, Executor executor2) {
            this.mAppClipsCrossProcessHelper = appClipsCrossProcessHelper;
            this.mImageExporter = imageExporter;
            this.mMainExecutor = executor;
            this.mBgExecutor = executor2;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            if (cls != AppClipsViewModel.class) {
                throw new IllegalArgumentException();
            }
            return new AppClipsViewModel(this.mAppClipsCrossProcessHelper, this.mImageExporter, this.mMainExecutor, this.mBgExecutor);
        }
    }

    public AppClipsViewModel(AppClipsCrossProcessHelper appClipsCrossProcessHelper, ImageExporter imageExporter, Executor executor, Executor executor2) {
        this.mAppClipsCrossProcessHelper = appClipsCrossProcessHelper;
        this.mImageExporter = imageExporter;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
    }
}
