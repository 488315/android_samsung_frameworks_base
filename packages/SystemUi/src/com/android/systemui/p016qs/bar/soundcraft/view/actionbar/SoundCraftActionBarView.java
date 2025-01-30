package com.android.systemui.p016qs.bar.soundcraft.view.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.p016qs.bar.soundcraft.viewbinding.SoundCraftActionBarBinding;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundCraftActionBarView extends LinearLayout implements LifecycleOwner {
    public final LifecycleRegistry registry;
    public SoundCraftActionBarBinding viewBinding;
    public SoundCraftActionBarViewModel viewModel;

    public SoundCraftActionBarView(Context context, AttributeSet attributeSet) {
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
