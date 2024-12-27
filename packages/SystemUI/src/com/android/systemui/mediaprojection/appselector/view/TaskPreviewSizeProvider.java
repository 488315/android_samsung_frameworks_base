package com.android.systemui.mediaprojection.appselector.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.WindowInsets;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TaskPreviewSizeProvider implements CallbackController, ConfigurationController.ConfigurationListener, DefaultLifecycleObserver {
    public final ConfigurationController configurationController;
    public final Context context;
    public final WindowMetricsProvider windowMetricsProvider;
    public final Rect size = calculateSize();
    public final ArrayList listeners = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface TaskPreviewSizeListener {
        void onTaskSizeChanged();
    }

    public TaskPreviewSizeProvider(Context context, WindowMetricsProvider windowMetricsProvider, ConfigurationController configurationController) {
        this.context = context;
        this.windowMetricsProvider = windowMetricsProvider;
        this.configurationController = configurationController;
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
        int i = Utilities.isLargeScreen(this.context) ? height - ((WindowMetricsProviderImpl) windowMetricsProvider).windowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.tappableElement()).bottom : height;
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
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((TaskPreviewSizeListener) it.next()).onTaskSizeChanged();
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
