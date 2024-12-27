package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.utils.ContextUtils;
import com.android.systemui.audio.soundcraft.utils.LayoutHelperUtil;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlIconViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel;
import com.android.systemui.util.DeviceState;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        noiseControlIconViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NoiseControlIconView.this.viewModel.onClick();
            }
        });
        noiseControlIconViewModel.icon.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView.2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                NoiseControlIconView noiseControlIconView = NoiseControlIconView.this;
                noiseControlIconView.binding.root.setGravity(17);
                NoiseControlIconViewBinding noiseControlIconViewBinding2 = noiseControlIconView.binding;
                ImageView imageView = noiseControlIconViewBinding2.icon;
                Intrinsics.checkNotNull(num);
                imageView.setImageResource(num.intValue());
                noiseControlIconViewBinding2.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });
        noiseControlIconViewModel.name.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView.3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView.this.binding.name.setText((String) obj);
            }
        });
        noiseControlIconViewModel.iconColor.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                NoiseControlIconView noiseControlIconView = NoiseControlIconView.this;
                ImageView imageView = noiseControlIconView.binding.icon;
                Resources resources = noiseControlIconView.context.getResources();
                Intrinsics.checkNotNull(num);
                imageView.setColorFilter(resources.getColor(num.intValue(), null), PorterDuff.Mode.SRC_IN);
            }
        });
        noiseControlIconViewModel.background.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView.5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                ImageView imageView = NoiseControlIconView.this.binding.icon;
                Intrinsics.checkNotNull(num);
                imageView.setBackgroundResource(num.intValue());
            }
        });
        LayoutHelperUtil.INSTANCE.getClass();
        int i2 = DeviceState.getDisplayWidth(context) > ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_width, context) ? R.dimen.soundcraft_noise_effect_box_icon_long : R.dimen.soundcraft_noise_effect_box_icon_short;
        ViewGroup.LayoutParams layoutParams = noiseControlIconViewBinding.root.getLayoutParams();
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
