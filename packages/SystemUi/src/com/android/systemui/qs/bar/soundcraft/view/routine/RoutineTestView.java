package com.android.systemui.qs.bar.soundcraft.view.routine;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.qs.bar.soundcraft.viewbinding.RoutineTestViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RoutineTestView extends LinearLayout implements LifecycleOwner {
    public final LifecycleRegistry registry;
    public RoutineTestViewBinding viewBinding;
    public RoutineTestViewModel viewModel;

    public RoutineTestView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.registry = new LifecycleRegistry(this);
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
}
