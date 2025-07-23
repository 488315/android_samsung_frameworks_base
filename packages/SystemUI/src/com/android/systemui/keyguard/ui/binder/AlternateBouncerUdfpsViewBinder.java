package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlternateBouncerUdfpsViewBinder {
    static {
        new AlternateBouncerUdfpsViewBinder();
    }

    private AlternateBouncerUdfpsViewBinder() {
    }

    public static final void bind(DeviceEntryIconView deviceEntryIconView, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel) {
        ImageView imageView = deviceEntryIconView.iconView;
        ImageView imageView2 = deviceEntryIconView.bgView;
        RepeatWhenAttachedKt.repeatWhenAttached(deviceEntryIconView, EmptyCoroutineContext.INSTANCE, new AlternateBouncerUdfpsViewBinder$bind$1(deviceEntryIconView, alternateBouncerUdfpsIconViewModel, null));
        RepeatWhenAttachedKt.repeatWhenAttached(imageView, EmptyCoroutineContext.INSTANCE, new AlternateBouncerUdfpsViewBinder$bind$2(alternateBouncerUdfpsIconViewModel, imageView, deviceEntryIconView, null));
        imageView2.setVisibility(0);
        RepeatWhenAttachedKt.repeatWhenAttached(imageView2, EmptyCoroutineContext.INSTANCE, new AlternateBouncerUdfpsViewBinder$bind$3(alternateBouncerUdfpsIconViewModel, imageView2, null));
    }
}
