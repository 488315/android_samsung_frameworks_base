package com.android.systemui.audio.soundcraft;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBinding;
import java.util.Arrays;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftCoverView extends LinearLayout {
    public final PowerManager powerManager;
    public View view;
    public SoundCraftViewComponent viewComponent;

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

    public SoundCraftCoverView(Context context, AttributeSet attributeSet, PowerManager powerManager) {
        super(context, attributeSet);
        this.powerManager = powerManager;
        Log.d("SoundCraftCoverView", "init()");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (CollectionsKt___CollectionsKt.contains(Arrays.asList(0, 1), motionEvent != null ? Integer.valueOf(motionEvent.getAction()) : null)) {
            this.powerManager.userActivity(SystemClock.uptimeMillis(), false);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        Display display;
        View view;
        SoundCraftViewComponent soundCraftViewComponent = this.viewComponent;
        if (soundCraftViewComponent == null) {
            soundCraftViewComponent = null;
        }
        soundCraftViewComponent.updateModel(2);
        ArrayIterator arrayIterator = new ArrayIterator(((DisplayManager) getContext().getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN"));
        while (true) {
            if (!arrayIterator.hasNext()) {
                display = null;
                break;
            }
            display = (Display) arrayIterator.next();
            if (display.getDisplayId() == 1 && display.getState() == 2) {
                break;
            }
        }
        if (display != null) {
            Context createWindowContext = getContext().createWindowContext(display, 2020, null);
            if (createWindowContext == null) {
                view = new View(createWindowContext);
            } else {
                Log.d("SoundCraftCoverView", "createDetailView, parent=null");
                SoundCraftViewComponent soundCraftViewComponent2 = this.viewComponent;
                if (soundCraftViewComponent2 == null) {
                    soundCraftViewComponent2 = null;
                }
                soundCraftViewComponent2.onCreate(createWindowContext, null);
                SoundCraftViewComponent soundCraftViewComponent3 = this.viewComponent;
                if (soundCraftViewComponent3 == null) {
                    soundCraftViewComponent3 = null;
                }
                SoundCraftViewBinding soundCraftViewBinding = soundCraftViewComponent3.binding;
                soundCraftViewBinding.getClass();
                view = soundCraftViewBinding.root;
            }
            this.view = view;
            addView(view != null ? view : null);
        }
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("SoundCraftCoverView", "onDetachedFromWindow");
        SoundCraftViewComponent soundCraftViewComponent = this.viewComponent;
        if (soundCraftViewComponent == null) {
            soundCraftViewComponent = null;
        }
        soundCraftViewComponent.onDestroy();
        View view = this.view;
        removeView(view != null ? view : null);
    }
}
