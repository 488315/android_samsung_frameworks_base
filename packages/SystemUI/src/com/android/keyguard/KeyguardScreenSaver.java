package com.android.keyguard;

import android.app.Presentation;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.facewidget.dex.DexClockController;
import com.android.systemui.facewidget.dex.DexClockControllerCallback;
import com.android.systemui.facewidget.dex.DexClockControllerImpl;

/* loaded from: classes.dex */
public final class KeyguardScreenSaver extends Presentation {
    public final KeyguardScreenSaver$dexClockChangedCallback$1 dexClockChangedCallback;
    public final DexClockController dexClockController;
    public int mMarginLeft;
    public int mMarginTop;
    public final KeyguardScreenSaver$mMoveTextRunnable$1 mMoveTextRunnable;
    public int mUsableHeight;
    public int mUsableWidth;
    public FrameLayout rootView;
    public View secClock;

    public interface Factory {
        KeyguardScreenSaver create(Display display);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.KeyguardScreenSaver$dexClockChangedCallback$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.keyguard.KeyguardScreenSaver$mMoveTextRunnable$1] */
    public KeyguardScreenSaver(Display display, Context context, DexClockController dexClockController) {
        super(context, display, R.style.Theme_SystemUI_KeyguardPresentation, 2009);
        this.dexClockController = dexClockController;
        this.dexClockChangedCallback = new DexClockControllerCallback() { // from class: com.android.keyguard.KeyguardScreenSaver$dexClockChangedCallback$1
            @Override // com.android.systemui.facewidget.dex.DexClockControllerCallback
            public final void onDexClockChanged(View view) {
                Log.i("KeyguardScreenSaver", "onDexClockChanged: " + view);
                this.this$0.secClock = view;
            }
        };
        this.mMoveTextRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardScreenSaver$mMoveTextRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                View view = this.this$0.secClock;
                if (view == null) {
                    return;
                }
                view.setX(r0.mMarginLeft + ((float) (Math.random() * (this.this$0.mUsableWidth - view.getWidth()))));
                view.setY(this.this$0.mMarginTop + ((float) (Math.random() * (this.this$0.mUsableHeight - view.getHeight()))));
                view.postDelayed(this, 5000L);
            }
        };
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        View view = this.secClock;
        if (view != null) {
            view.removeCallbacks(this.mMoveTextRunnable);
        }
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        Log.d("KeyguardScreenSaver", "onCreate()");
        super.onCreate(bundle);
        FrameLayout frameLayout = new FrameLayout(getContext(), null);
        this.rootView = frameLayout;
        frameLayout.setClipChildren(false);
        Window window = getWindow();
        if (window == null) {
            throw new IllegalStateException("no window available.");
        }
        window.getAttributes().flags |= 526327;
        window.getDecorView().setBackgroundColor(-16777216);
        FrameLayout frameLayout2 = this.rootView;
        if (frameLayout2 == null) {
            frameLayout2 = null;
        }
        setContentView(frameLayout2);
        window.getDecorView().setSystemUiVisibility(1792);
        window.getAttributes().setFitInsetsTypes(0);
        window.setNavigationBarContrastEnforced(false);
        window.setNavigationBarColor(0);
        DexClockController dexClockController = this.dexClockController;
        FrameLayout frameLayout3 = this.rootView;
        ((DexClockControllerImpl) dexClockController).initDexClock(frameLayout3 != null ? frameLayout3 : null, getDisplay(), this.dexClockChangedCallback, true);
        Window window2 = getWindow();
        window2.getClass();
        Rect bounds = window2.getWindowManager().getMaximumWindowMetrics().getBounds();
        this.mUsableWidth = (bounds.width() * 80) / 100;
        this.mUsableHeight = (bounds.height() * 80) / 100;
        this.mMarginLeft = (bounds.width() * 20) / 200;
        this.mMarginTop = (bounds.height() * 20) / 200;
    }

    @Override // android.app.Presentation, android.app.Dialog
    public final void show() {
        super.show();
        View view = this.secClock;
        if (view != null) {
            view.postDelayed(this.mMoveTextRunnable, 5000L);
        }
    }
}
