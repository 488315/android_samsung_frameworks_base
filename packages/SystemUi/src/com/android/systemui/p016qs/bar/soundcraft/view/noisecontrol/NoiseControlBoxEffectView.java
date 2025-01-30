package com.android.systemui.p016qs.bar.soundcraft.view.noisecontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseControlBoxEffectView extends LinearLayout implements LifecycleOwner {
    public final LifecycleRegistry registry;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public NoiseControlBoxEffectView(Context context) {
        super(context);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.registry;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.registry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.registry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    public NoiseControlBoxEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public NoiseControlBoxEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }
}
