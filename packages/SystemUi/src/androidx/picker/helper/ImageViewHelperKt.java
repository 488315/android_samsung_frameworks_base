package androidx.picker.helper;

import android.widget.ImageView;
import androidx.picker.loader.AppIconFlow;
import com.facebook.shimmer.ShimmerFrameLayout;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ImageViewHelperKt {
    /* JADX WARN: Type inference failed for: r9v2, types: [androidx.picker.helper.ImageViewHelperKt$$ExternalSyntheticLambda0] */
    public static final ImageViewHelperKt$$ExternalSyntheticLambda0 loadIcon(ImageView imageView, CoroutineDispatcher coroutineDispatcher, AppIconFlow appIconFlow, final ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.setVisibility(0);
        shimmerFrameLayout.startShimmer();
        final StandaloneCoroutine launch$default = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(coroutineDispatcher), null, null, new ImageViewHelperKt$loadIcon$job$1(appIconFlow, coroutineDispatcher, imageView, shimmerFrameLayout, null), 3);
        return new DisposableHandle() { // from class: androidx.picker.helper.ImageViewHelperKt$$ExternalSyntheticLambda0
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                ImageViewHelperKt.m337loadIcon$lambda0(shimmerFrameLayout, launch$default);
            }
        };
    }

    /* renamed from: loadIcon$lambda-0, reason: not valid java name */
    public static final void m337loadIcon$lambda0(ShimmerFrameLayout shimmerFrameLayout, Job job) {
        shimmerFrameLayout.setVisibility(8);
        shimmerFrameLayout.stopShimmer();
        job.cancel(null);
    }
}
