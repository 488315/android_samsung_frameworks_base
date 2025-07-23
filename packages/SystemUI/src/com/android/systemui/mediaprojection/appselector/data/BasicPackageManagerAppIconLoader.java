package com.android.systemui.mediaprojection.appselector.data;

import android.content.pm.PackageManager;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BasicPackageManagerAppIconLoader implements BasicAppIconLoader {
    public final CoroutineDispatcher backgroundDispatcher;
    public final PackageManager packageManager;
    public final PackageManagerWrapper packageManagerWrapper;

    public BasicPackageManagerAppIconLoader(CoroutineDispatcher coroutineDispatcher, PackageManagerWrapper packageManagerWrapper, PackageManager packageManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.packageManagerWrapper = packageManagerWrapper;
        this.packageManager = packageManager;
    }
}
