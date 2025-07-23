package com.android.systemui.util.service.dagger;

import android.content.ComponentName;
import com.android.systemui.util.service.PackageObserver;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface PackageObserverComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        PackageObserverComponent create(ComponentName componentName);
    }

    PackageObserver getPackageObserver();
}
