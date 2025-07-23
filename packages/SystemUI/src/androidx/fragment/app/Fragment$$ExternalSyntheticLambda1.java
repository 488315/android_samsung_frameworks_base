package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import java.lang.ref.WeakReference;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class Fragment$$ExternalSyntheticLambda1 implements DisposableHandle {
    public final /* synthetic */ Fragment f$0;
    public final /* synthetic */ WeakReference f$1;

    public /* synthetic */ Fragment$$ExternalSyntheticLambda1(Fragment fragment, WeakReference weakReference) {
        this.f$0 = fragment;
        this.f$1 = weakReference;
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        Animation animation;
        WeakReference weakReference = this.f$1;
        Fragment fragment = this.f$0;
        fragment.getClass();
        View view = (View) weakReference.get();
        if (view != null && (animation = view.getAnimation()) != null && !animation.hasEnded()) {
            Log.d("FragmentManager", "Fragment Animation was canceled by back press");
            view.clearAnimation();
        }
        fragment.mDisposableHandle = null;
    }
}
