package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.shared.system.PackageManagerWrapper;
import javax.inject.Provider;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IconLoaderLibAppIconLoader implements AppIconLoader {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final Provider iconFactoryProvider;
    public final PackageManager packageManager;
    public final PackageManagerWrapper packageManagerWrapper;

    public IconLoaderLibAppIconLoader(CoroutineDispatcher coroutineDispatcher, Context context, PackageManagerWrapper packageManagerWrapper, PackageManager packageManager, Provider provider) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.context = context;
        this.packageManagerWrapper = packageManagerWrapper;
        this.packageManager = packageManager;
        this.iconFactoryProvider = provider;
    }

    public final Object loadIcon(int i, ComponentName componentName, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new IconLoaderLibAppIconLoader$loadIcon$2(this, componentName, i, null), continuation);
    }
}
