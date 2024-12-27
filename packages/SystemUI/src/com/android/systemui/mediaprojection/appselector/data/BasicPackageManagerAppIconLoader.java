package com.android.systemui.mediaprojection.appselector.data;

import android.content.pm.PackageManager;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlinx.coroutines.CoroutineDispatcher;

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
