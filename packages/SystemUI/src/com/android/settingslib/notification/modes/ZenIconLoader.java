package com.android.settingslib.notification.modes;

import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.text.TextUtils;
import android.util.LruCache;
import com.android.settingslib.notification.modes.ZenIcon;
import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractCatchingFuture;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.ForwardingFluentFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public class ZenIconLoader {
    public static final Drawable MISSING = new ColorDrawable();
    public final ListeningExecutorService mBackgroundExecutor;
    public final LruCache mCache = new LruCache(50);

    public ZenIconLoader(ExecutorService executorService) {
        this.mBackgroundExecutor = MoreExecutors.listeningDecorator(executorService);
    }

    public final ListenableFuture loadIcon(final Context context, final ZenIcon.Key key, final boolean z) {
        synchronized (this.mCache) {
            try {
                Drawable drawable = (Drawable) this.mCache.get(key);
                if (drawable != null) {
                    if (drawable == MISSING) {
                        drawable = null;
                    }
                    return Futures.immediateFuture(drawable);
                }
                ListenableFuture listenableFutureSubmit = ((AbstractListeningExecutorService) this.mBackgroundExecutor).submit(new Callable() { // from class: com.android.settingslib.notification.modes.ZenIconLoader$$ExternalSyntheticLambda2
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        Context context2 = context;
                        Drawable drawable2 = ZenIconLoader.MISSING;
                        ZenIcon.Key key2 = key;
                        if (TextUtils.isEmpty(key2.resPackage)) {
                            return context2.getDrawable(key2.resId);
                        }
                        Drawable drawable3 = context2.createPackageContext(key2.resPackage, 0).getDrawable(key2.resId);
                        if (!z || !(drawable3 instanceof AdaptiveIconDrawable)) {
                            return drawable3;
                        }
                        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable3;
                        return adaptiveIconDrawable.getMonochrome() != null ? new InsetDrawable(adaptiveIconDrawable.getMonochrome(), AdaptiveIconDrawable.getExtraInsetFraction() * (-2.0f)) : drawable3;
                    }
                });
                int i = FluentFuture.$r8$clinit;
                FluentFuture forwardingFluentFuture = listenableFutureSubmit instanceof FluentFuture ? (FluentFuture) listenableFutureSubmit : new ForwardingFluentFuture(listenableFutureSubmit);
                ZenIconLoader$$ExternalSyntheticLambda1 zenIconLoader$$ExternalSyntheticLambda1 = new ZenIconLoader$$ExternalSyntheticLambda1(key);
                Executor executorDirectExecutor = MoreExecutors.directExecutor();
                forwardingFluentFuture.getClass();
                AbstractCatchingFuture.CatchingFuture catchingFuture = new AbstractCatchingFuture.CatchingFuture(forwardingFluentFuture, Exception.class, zenIconLoader$$ExternalSyntheticLambda1);
                forwardingFluentFuture.addListener(catchingFuture, MoreExecutors.rejectionPropagatingExecutor(executorDirectExecutor, catchingFuture));
                return Futures.transform(catchingFuture, new Function() { // from class: com.android.settingslib.notification.modes.ZenIconLoader$$ExternalSyntheticLambda4
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        ZenIconLoader zenIconLoader = this.f$0;
                        ZenIcon.Key key2 = key;
                        Drawable drawable2 = (Drawable) obj;
                        synchronized (zenIconLoader.mCache) {
                            zenIconLoader.mCache.put(key2, drawable2 != null ? drawable2 : ZenIconLoader.MISSING);
                        }
                        return drawable2;
                    }
                }, MoreExecutors.directExecutor());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
