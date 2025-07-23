package com.android.systemui.screenshot.ui;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotAnimationController {
    public final View actionContainer;
    public Animator animator;
    public final List fadeUI;
    public final Interpolator fastOutSlowIn;
    public final View flashView;
    public final ImageView screenshotPreview;
    public final ImageView scrollTransitionPreview;
    public final ImageView scrollingScrim;
    public final List staticUI;
    public final ScreenshotShelfView view;
    public final ScreenshotViewModel viewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ScreenshotAnimationController(ScreenshotShelfView screenshotShelfView, ScreenshotViewModel screenshotViewModel) {
        this.view = screenshotShelfView;
        this.viewModel = screenshotViewModel;
        this.screenshotPreview = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_preview);
        this.scrollingScrim = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_scrolling_scrim);
        this.scrollTransitionPreview = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_scrollable_preview);
        this.flashView = screenshotShelfView.requireViewById(R.id.screenshot_flash);
        this.actionContainer = screenshotShelfView.requireViewById(R.id.actions_container_background);
        this.fastOutSlowIn = AnimationUtils.loadInterpolator(screenshotShelfView.getContext(), android.R.interpolator.fast_out_slow_in);
        this.staticUI = Arrays.asList(screenshotShelfView.requireViewById(R.id.screenshot_preview_border), screenshotShelfView.requireViewById(R.id.screenshot_badge), screenshotShelfView.requireViewById(R.id.screenshot_dismiss_button));
        this.fadeUI = Arrays.asList(screenshotShelfView.requireViewById(R.id.screenshot_preview_border), screenshotShelfView.requireViewById(R.id.actions_container_background), screenshotShelfView.requireViewById(R.id.screenshot_badge), screenshotShelfView.requireViewById(R.id.screenshot_dismiss_button), screenshotShelfView.requireViewById(R.id.screenshot_message_container));
    }
}
