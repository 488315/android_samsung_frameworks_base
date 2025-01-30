package com.android.systemui.shade;

import android.view.ViewStub;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.systemui.R;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeModule_Companion_ProvidesShadeHeaderViewFactory implements Provider {
    public final Provider notificationShadeWindowViewProvider;

    public ShadeModule_Companion_ProvidesShadeHeaderViewFactory(Provider provider) {
        this.notificationShadeWindowViewProvider = provider;
    }

    public static MotionLayout providesShadeHeaderView(NotificationShadeWindowView notificationShadeWindowView) {
        ShadeModule.Companion.getClass();
        ViewStub viewStub = (ViewStub) notificationShadeWindowView.findViewById(R.id.qs_header_stub);
        viewStub.setLayoutResource(R.layout.combined_qs_header);
        return (MotionLayout) viewStub.inflate();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesShadeHeaderView((NotificationShadeWindowView) this.notificationShadeWindowViewProvider.get());
    }
}
