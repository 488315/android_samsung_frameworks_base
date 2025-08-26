package com.android.systemui.util.service.dagger;

import android.content.ComponentName;
import com.android.systemui.util.service.PackageObserver;

/* loaded from: classes3.dex */
public interface PackageObserverComponent {

    public interface Factory {
        PackageObserverComponent create(ComponentName componentName);
    }

    PackageObserver getPackageObserver();
}
