package com.android.systemui.util.service.dagger;

import android.content.ComponentName;
import com.android.systemui.util.service.PackageObserver;

public interface PackageObserverComponent {

    public interface Factory {
        PackageObserverComponent create(ComponentName componentName);
    }

    PackageObserver getPackageObserver();
}
