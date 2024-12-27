package com.android.systemui.audio.soundcraft;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBinding;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

public final class SoundCraftNowBarView extends LinearLayout {
    public Job job;
    public View view;
    public SoundCraftViewComponent viewComponent;

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

    public SoundCraftNowBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d("SoundCraftNowBarView", "init()");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        View view;
        SoundCraftViewComponent soundCraftViewComponent = this.viewComponent;
        if (soundCraftViewComponent == null) {
            soundCraftViewComponent = null;
        }
        soundCraftViewComponent.updateModel(true);
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
            Intrinsics.checkNotNull(soundCraftViewBinding);
            view = soundCraftViewBinding.root;
        }
        this.view = view;
        if (view == null) {
            view = null;
        }
        addView(view);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.job = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new SoundCraftNowBarView$onAttachedToWindow$1(this, null), 3);
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
        if (view == null) {
            view = null;
        }
        removeView(view);
        Job job = this.job;
        if (job != null) {
            job.cancel(null);
        }
    }
}
