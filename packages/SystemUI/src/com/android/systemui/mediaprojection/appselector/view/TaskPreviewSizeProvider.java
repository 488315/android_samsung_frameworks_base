package com.android.systemui.mediaprojection.appselector.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TaskPreviewSizeProvider implements CallbackController, ConfigurationController.ConfigurationListener, DefaultLifecycleObserver {
    public final ConfigurationController configurationController;
    public final Context context;
    public final WindowManager windowManager;
    public final WindowMetricsProvider windowMetricsProvider;
    public final Rect size = calculateSize();
    public final ArrayList listeners = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TaskPreviewSizeListener {
        void onTaskSizeChanged();
    }

    public TaskPreviewSizeProvider(Context context, WindowMetricsProvider windowMetricsProvider, ConfigurationController configurationController, WindowManager windowManager) {
        this.context = context;
        this.windowMetricsProvider = windowMetricsProvider;
        this.configurationController = configurationController;
        this.windowManager = windowManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.listeners.add((TaskPreviewSizeListener) obj);
    }

    public final Rect calculateSize() {
        WindowMetricsProvider windowMetricsProvider = this.windowMetricsProvider;
        Rect bounds = ((WindowMetricsProviderImpl) windowMetricsProvider).windowManager.getMaximumWindowMetrics().getBounds();
        int height = bounds.height();
        int width = bounds.width();
        int i = Utilities.isLargeScreen(this.windowManager, this.context.getResources()) ? height - ((WindowMetricsProviderImpl) windowMetricsProvider).windowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.tappableElement()).bottom : height;
        Rect rect = new Rect(0, 0, width, i);
        rect.scale((i / height) / 4.0f);
        return rect;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        Rect calculateSize = calculateSize();
        if (calculateSize.equals(this.size)) {
            return;
        }
        this.size.set(calculateSize);
        ArrayList arrayList = this.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((TaskPreviewSizeListener) obj).onTaskSizeChanged();
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onCreate(LifecycleOwner lifecycleOwner) {
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onDestroy$1() {
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.listeners.remove((TaskPreviewSizeListener) obj);
    }
}
