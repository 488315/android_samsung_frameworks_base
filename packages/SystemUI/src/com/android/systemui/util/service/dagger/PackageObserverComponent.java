package com.android.systemui.util.service.dagger;

import android.content.ComponentName;
import com.android.systemui.util.service.PackageObserver;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface PackageObserverComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        PackageObserverComponent create(ComponentName componentName);
    }

    PackageObserver getPackageObserver();
}
