package com.android.systemui.mediaprojection.appselector.data;

import android.content.pm.PackageManager;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
