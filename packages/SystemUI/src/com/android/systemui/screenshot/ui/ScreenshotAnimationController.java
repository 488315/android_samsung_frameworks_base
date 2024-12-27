package com.android.systemui.screenshot.ui;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenshotAnimationController {
    public final View actionContainer;
    public Animator animator;
    public final List fadeUI;
    public final View flashView;
    public final ScreenshotShelfView view;
    public final ScreenshotViewModel viewModel;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ScreenshotAnimationController(ScreenshotShelfView screenshotShelfView, ScreenshotViewModel screenshotViewModel) {
        this.view = screenshotShelfView;
        this.viewModel = screenshotViewModel;
        this.flashView = screenshotShelfView.requireViewById(R.id.screenshot_flash);
        this.actionContainer = screenshotShelfView.requireViewById(R.id.actions_container_background);
        AnimationUtils.loadInterpolator(screenshotShelfView.getContext(), android.R.interpolator.fast_out_slow_in);
        CollectionsKt__CollectionsKt.listOf(screenshotShelfView.requireViewById(R.id.screenshot_preview_border), screenshotShelfView.requireViewById(R.id.screenshot_badge), screenshotShelfView.requireViewById(R.id.screenshot_dismiss_button));
        this.fadeUI = CollectionsKt__CollectionsKt.listOf(screenshotShelfView.requireViewById(R.id.screenshot_preview_border), screenshotShelfView.requireViewById(R.id.actions_container_background), screenshotShelfView.requireViewById(R.id.screenshot_badge), screenshotShelfView.requireViewById(R.id.screenshot_dismiss_button), screenshotShelfView.requireViewById(R.id.screenshot_message_container));
    }
}
