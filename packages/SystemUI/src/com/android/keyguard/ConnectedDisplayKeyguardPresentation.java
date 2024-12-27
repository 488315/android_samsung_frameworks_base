package com.android.keyguard;

import android.app.Presentation;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.shared.clocks.ClockRegistry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConnectedDisplayKeyguardPresentation extends Presentation {
    public View clock;
    public final ClockEventController clockEventController;
    public final ClockRegistry clockRegistry;
    public final ClockFaceController faceController;
    public final KeyguardStatusViewComponent.Factory keyguardStatusViewComponentFactory;
    public final ConnectedDisplayKeyguardPresentation$layoutChangeListener$1 layoutChangeListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ConnectedDisplayKeyguardPresentation create(Display display);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.keyguard.ConnectedDisplayKeyguardPresentation$layoutChangeListener$1] */
    public ConnectedDisplayKeyguardPresentation(Display display, Context context, KeyguardStatusViewComponent.Factory factory, ClockRegistry clockRegistry, ClockEventController clockEventController) {
        super(context, display, R.style.Theme_SystemUI_KeyguardPresentation, 2009);
        this.keyguardStatusViewComponentFactory = factory;
        this.clockRegistry = clockRegistry;
        new ClockRegistry.ClockChangeListener() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$clockChangedListener$1
            @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
            public final void onCurrentClockChanged() {
                ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation = ConnectedDisplayKeyguardPresentation.this;
                connectedDisplayKeyguardPresentation.clockRegistry.createCurrentClock();
                View view = connectedDisplayKeyguardPresentation.clock;
                if (view != null) {
                    view.removeOnLayoutChangeListener(connectedDisplayKeyguardPresentation.layoutChangeListener);
                }
                throw null;
            }
        };
        this.layoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$layoutChangeListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation = ConnectedDisplayKeyguardPresentation.this;
                View view2 = connectedDisplayKeyguardPresentation.clock;
                if (view2 != null) {
                    ClockFaceController clockFaceController = connectedDisplayKeyguardPresentation.faceController;
                    if (clockFaceController == null) {
                        clockFaceController = null;
                    }
                    clockFaceController.getEvents().onTargetRegionChanged(new Rect(view2.getLeft(), view2.getTop(), view2.getWidth(), view2.getHeight()));
                }
            }
        };
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onAttachedToWindow() {
        Flags.migrateClocksToBlueprint();
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Flags.migrateClocksToBlueprint();
        setContentView(LayoutInflater.from(getContext()).inflate(R.layout.keyguard_clock_presentation, (ViewGroup) null));
        Window window = getWindow();
        if (window == null) {
            throw new IllegalStateException("no window available.".toString());
        }
        window.getDecorView().setSystemUiVisibility(1792);
        window.getAttributes().setFitInsetsTypes(0);
        window.setNavigationBarContrastEnforced(false);
        window.setNavigationBarColor(0);
        View requireViewById = requireViewById(R.id.clock);
        this.clock = requireViewById;
        FrameLayout frameLayout = requireViewById != null ? (FrameLayout) requireViewById.findViewById(R.id.lockscreen_clock_view) : null;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) (frameLayout != null ? frameLayout.getLayoutParams() : null);
        layoutParams.removeRule(20);
        layoutParams.addRule(13, -1);
        frameLayout.setLayoutParams(layoutParams);
        KeyguardStatusViewController keyguardStatusViewController = this.keyguardStatusViewComponentFactory.build((KeyguardStatusView) this.clock, getDisplay()).getKeyguardStatusViewController();
        keyguardStatusViewController.mKeyguardClockSwitchController.mShownOnSecondaryDisplay = true;
        keyguardStatusViewController.init();
        Window window2 = getWindow();
        Intrinsics.checkNotNull(window2);
        window2.getDecorView().semSetRoundedCorners(0);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        Flags.migrateClocksToBlueprint();
        super.onDetachedFromWindow();
    }

    @Override // android.app.Presentation
    public final void onDisplayChanged() {
        Window window = getWindow();
        if (window == null) {
            throw new IllegalStateException("no window available.".toString());
        }
        window.getDecorView().requestLayout();
    }
}
