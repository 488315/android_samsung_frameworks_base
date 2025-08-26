package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import com.android.systemui.util.kotlin.DisposableHandles;

/* loaded from: classes2.dex */
public abstract class NotificationStackScrollLayoutSection extends KeyguardSection {
    public final Context context;
    public DisposableHandles disposableHandle;
    public final NotificationPanelView notificationPanelView;
    public final int placeHolderId = R.id.nssl_placeholder;
    public final SharedNotificationContainer sharedNotificationContainer;
    public final SharedNotificationContainerBinder sharedNotificationContainerBinder;
    public final SharedNotificationContainerViewModel sharedNotificationContainerViewModel;

    public NotificationStackScrollLayoutSection(Context context, NotificationPanelView notificationPanelView, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder) {
        this.context = context;
        this.notificationPanelView = notificationPanelView;
        this.sharedNotificationContainer = sharedNotificationContainer;
        this.sharedNotificationContainerViewModel = sharedNotificationContainerViewModel;
        this.sharedNotificationContainerBinder = sharedNotificationContainerBinder;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        View viewFindViewById = this.notificationPanelView.findViewById(R.id.notification_stack_scroller);
        if (viewFindViewById != null) {
            ((ViewGroup) viewFindViewById.getParent()).removeView(viewFindViewById);
            this.sharedNotificationContainer.addView(viewFindViewById);
        }
        View view = new View(this.context, null);
        view.setId(this.placeHolderId);
        constraintLayout.addView(view);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        DisposableHandles disposableHandles = this.disposableHandle;
        if (disposableHandles != null) {
            disposableHandles.dispose();
        }
        this.disposableHandle = this.sharedNotificationContainerBinder.bind(this.sharedNotificationContainer, this.sharedNotificationContainerViewModel);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        DisposableHandles disposableHandles = this.disposableHandle;
        if (disposableHandles != null) {
            disposableHandles.dispose();
        }
        this.disposableHandle = null;
        ExtensionsKt.removeView(constraintLayout, this.placeHolderId);
    }
}
