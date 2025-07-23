package com.android.systemui.audio.soundcraft;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBinding;
import java.util.Arrays;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftNowBarView extends LinearLayout {
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

    public SoundCraftNowBarView(Context context, AttributeSet attributeSet, PowerManager powerManager) {
        super(context, attributeSet);
        this.powerManager = powerManager;
        Log.d("SoundCraftNowBarView", "init()");
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
        View view;
        SoundCraftViewComponent soundCraftViewComponent = this.viewComponent;
        if (soundCraftViewComponent == null) {
            soundCraftViewComponent = null;
        }
        soundCraftViewComponent.updateModel(1);
        Context context = getContext();
        if (context == null) {
            view = new View(context);
        } else {
            Log.d("SoundCraftNowBarView", "createDetailView, parent=null");
            SoundCraftViewComponent soundCraftViewComponent2 = this.viewComponent;
            if (soundCraftViewComponent2 == null) {
                soundCraftViewComponent2 = null;
            }
            soundCraftViewComponent2.onCreate(context, null);
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
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("SoundCraftNowBarView", "onDetachedFromWindow");
        SoundCraftViewComponent soundCraftViewComponent = this.viewComponent;
        if (soundCraftViewComponent == null) {
            soundCraftViewComponent = null;
        }
        soundCraftViewComponent.onDestroy();
        View view = this.view;
        removeView(view != null ? view : null);
    }
}
