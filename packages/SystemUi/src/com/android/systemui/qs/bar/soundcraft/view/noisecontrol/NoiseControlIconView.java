package com.android.systemui.qs.bar.soundcraft.view.noisecontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.utils.LayoutHelperUtil;
import com.android.systemui.qs.bar.soundcraft.viewbinding.NoiseControlIconViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel;
import com.android.systemui.util.DeviceState;
import com.android.systemui.volume.util.ContextUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseControlIconView {
    public final NoiseControlIconViewBinding binding;
    public final Context context;
    public final LifecycleOwner lifecycleOwner;
    public final NoiseControlIconViewModel viewModel;

    public NoiseControlIconView(Context context, LifecycleOwner lifecycleOwner, NoiseControlIconViewModel noiseControlIconViewModel) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = noiseControlIconViewModel;
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        NoiseControlIconViewBinding noiseControlIconViewBinding = new NoiseControlIconViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_noise_control_icon, (ViewGroup) null, false));
        this.binding = noiseControlIconViewBinding;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlIconView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NoiseControlIconView.this.viewModel.onClick();
            }
        };
        LinearLayout linearLayout = noiseControlIconViewBinding.root;
        linearLayout.setOnClickListener(onClickListener);
        noiseControlIconViewModel.icon.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlIconView.2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView noiseControlIconView = NoiseControlIconView.this;
                noiseControlIconView.binding.root.setGravity(17);
                NoiseControlIconViewBinding noiseControlIconViewBinding2 = noiseControlIconView.binding;
                noiseControlIconViewBinding2.icon.setImageResource(((Integer) obj).intValue());
                noiseControlIconViewBinding2.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });
        noiseControlIconViewModel.name.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlIconView.3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView.this.binding.name.setText((String) obj);
            }
        });
        noiseControlIconViewModel.iconColor.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlIconView.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView noiseControlIconView = NoiseControlIconView.this;
                noiseControlIconView.binding.icon.setColorFilter(noiseControlIconView.context.getResources().getColor(((Integer) obj).intValue(), null));
            }
        });
        noiseControlIconViewModel.background.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlIconView.5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView.this.binding.icon.setBackgroundResource(((Integer) obj).intValue());
            }
        });
        LayoutHelperUtil.INSTANCE.getClass();
        int i2 = DeviceState.getDisplayWidth(context) > ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_width, context) ? R.dimen.soundcraft_noise_effect_box_icon_long : R.dimen.soundcraft_noise_effect_box_icon_short;
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = ContextUtils.getDimenInt(i2, context);
        }
        ViewGroup.LayoutParams layoutParams2 = noiseControlIconViewBinding.name.getLayoutParams();
        if (layoutParams2 == null) {
            return;
        }
        layoutParams2.width = ContextUtils.getDimenInt(i2, context);
    }
}
