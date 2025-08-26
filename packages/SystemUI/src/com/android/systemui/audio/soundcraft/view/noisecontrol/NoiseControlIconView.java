package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.utils.ContextUtils;
import com.android.systemui.audio.soundcraft.utils.LayoutHelperUtil;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlIconViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel;
import com.android.systemui.util.DeviceState;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class NoiseControlIconView {
    public final NoiseControlIconViewBinding binding;
    public final Context context;
    public final LifecycleOwner lifecycleOwner;
    public final NoiseControlIconViewModel viewModel;

    public NoiseControlIconView(Context context, LifecycleOwner lifecycleOwner, NoiseControlIconViewModel noiseControlIconViewModel) {
        final int i = 0;
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = noiseControlIconViewModel;
        int i2 = SoundCraftViewBindingFactory.$r8$clinit;
        NoiseControlIconViewBinding noiseControlIconViewBinding = new NoiseControlIconViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_noise_control_icon, (ViewGroup) null, false));
        this.binding = noiseControlIconViewBinding;
        noiseControlIconViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NoiseControlIconView.this.viewModel.onClick();
            }
        });
        noiseControlIconViewModel.icon.observe(lifecycleOwner, new NoiseControlIconView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlIconView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i) {
                    case 0:
                        Integer num = (Integer) obj;
                        NoiseControlIconView noiseControlIconView = this.f$0;
                        noiseControlIconView.binding.root.setGravity(17);
                        NoiseControlIconViewBinding noiseControlIconViewBinding2 = noiseControlIconView.binding;
                        ImageView imageView = noiseControlIconViewBinding2.icon;
                        num.getClass();
                        imageView.setImageResource(num.intValue());
                        noiseControlIconViewBinding2.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView2 = this.f$0;
                        NoiseControlIconViewBinding noiseControlIconViewBinding3 = noiseControlIconView2.binding;
                        noiseControlIconViewBinding3.name.setText((String) obj);
                        if (noiseControlIconView2.viewModel.getModelProvider().isFromCover) {
                            noiseControlIconViewBinding3.name.setVisibility(8);
                        }
                        break;
                    case 2:
                        Integer num2 = (Integer) obj;
                        NoiseControlIconView noiseControlIconView3 = this.f$0;
                        ImageView imageView2 = noiseControlIconView3.binding.icon;
                        Resources resources = noiseControlIconView3.context.getResources();
                        num2.getClass();
                        imageView2.setColorFilter(resources.getColor(num2.intValue(), null), PorterDuff.Mode.SRC_IN);
                        break;
                    default:
                        Integer num3 = (Integer) obj;
                        ImageView imageView3 = this.f$0.binding.icon;
                        num3.getClass();
                        imageView3.setBackgroundResource(num3.intValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i3 = 1;
        noiseControlIconViewModel.name.observe(lifecycleOwner, new NoiseControlIconView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlIconView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i3) {
                    case 0:
                        Integer num = (Integer) obj;
                        NoiseControlIconView noiseControlIconView = this.f$0;
                        noiseControlIconView.binding.root.setGravity(17);
                        NoiseControlIconViewBinding noiseControlIconViewBinding2 = noiseControlIconView.binding;
                        ImageView imageView = noiseControlIconViewBinding2.icon;
                        num.getClass();
                        imageView.setImageResource(num.intValue());
                        noiseControlIconViewBinding2.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView2 = this.f$0;
                        NoiseControlIconViewBinding noiseControlIconViewBinding3 = noiseControlIconView2.binding;
                        noiseControlIconViewBinding3.name.setText((String) obj);
                        if (noiseControlIconView2.viewModel.getModelProvider().isFromCover) {
                            noiseControlIconViewBinding3.name.setVisibility(8);
                        }
                        break;
                    case 2:
                        Integer num2 = (Integer) obj;
                        NoiseControlIconView noiseControlIconView3 = this.f$0;
                        ImageView imageView2 = noiseControlIconView3.binding.icon;
                        Resources resources = noiseControlIconView3.context.getResources();
                        num2.getClass();
                        imageView2.setColorFilter(resources.getColor(num2.intValue(), null), PorterDuff.Mode.SRC_IN);
                        break;
                    default:
                        Integer num3 = (Integer) obj;
                        ImageView imageView3 = this.f$0.binding.icon;
                        num3.getClass();
                        imageView3.setBackgroundResource(num3.intValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i4 = 2;
        noiseControlIconViewModel.iconColor.observe(lifecycleOwner, new NoiseControlIconView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlIconView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i4) {
                    case 0:
                        Integer num = (Integer) obj;
                        NoiseControlIconView noiseControlIconView = this.f$0;
                        noiseControlIconView.binding.root.setGravity(17);
                        NoiseControlIconViewBinding noiseControlIconViewBinding2 = noiseControlIconView.binding;
                        ImageView imageView = noiseControlIconViewBinding2.icon;
                        num.getClass();
                        imageView.setImageResource(num.intValue());
                        noiseControlIconViewBinding2.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView2 = this.f$0;
                        NoiseControlIconViewBinding noiseControlIconViewBinding3 = noiseControlIconView2.binding;
                        noiseControlIconViewBinding3.name.setText((String) obj);
                        if (noiseControlIconView2.viewModel.getModelProvider().isFromCover) {
                            noiseControlIconViewBinding3.name.setVisibility(8);
                        }
                        break;
                    case 2:
                        Integer num2 = (Integer) obj;
                        NoiseControlIconView noiseControlIconView3 = this.f$0;
                        ImageView imageView2 = noiseControlIconView3.binding.icon;
                        Resources resources = noiseControlIconView3.context.getResources();
                        num2.getClass();
                        imageView2.setColorFilter(resources.getColor(num2.intValue(), null), PorterDuff.Mode.SRC_IN);
                        break;
                    default:
                        Integer num3 = (Integer) obj;
                        ImageView imageView3 = this.f$0.binding.icon;
                        num3.getClass();
                        imageView3.setBackgroundResource(num3.intValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i5 = 3;
        noiseControlIconViewModel.background.observe(lifecycleOwner, new NoiseControlIconView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlIconView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlIconView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i5) {
                    case 0:
                        Integer num = (Integer) obj;
                        NoiseControlIconView noiseControlIconView = this.f$0;
                        noiseControlIconView.binding.root.setGravity(17);
                        NoiseControlIconViewBinding noiseControlIconViewBinding2 = noiseControlIconView.binding;
                        ImageView imageView = noiseControlIconViewBinding2.icon;
                        num.getClass();
                        imageView.setImageResource(num.intValue());
                        noiseControlIconViewBinding2.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView2 = this.f$0;
                        NoiseControlIconViewBinding noiseControlIconViewBinding3 = noiseControlIconView2.binding;
                        noiseControlIconViewBinding3.name.setText((String) obj);
                        if (noiseControlIconView2.viewModel.getModelProvider().isFromCover) {
                            noiseControlIconViewBinding3.name.setVisibility(8);
                        }
                        break;
                    case 2:
                        Integer num2 = (Integer) obj;
                        NoiseControlIconView noiseControlIconView3 = this.f$0;
                        ImageView imageView2 = noiseControlIconView3.binding.icon;
                        Resources resources = noiseControlIconView3.context.getResources();
                        num2.getClass();
                        imageView2.setColorFilter(resources.getColor(num2.intValue(), null), PorterDuff.Mode.SRC_IN);
                        break;
                    default:
                        Integer num3 = (Integer) obj;
                        ImageView imageView3 = this.f$0.binding.icon;
                        num3.getClass();
                        imageView3.setBackgroundResource(num3.intValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        LayoutHelperUtil.INSTANCE.getClass();
        int i6 = DeviceState.getDisplayWidth(context) > ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_width, context) ? R.dimen.soundcraft_noise_effect_box_icon_long : R.dimen.soundcraft_noise_effect_box_icon_short;
        ViewGroup.LayoutParams layoutParams = noiseControlIconViewBinding.root.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = context.getResources().getDimensionPixelSize(i6);
        }
        ViewGroup.LayoutParams layoutParams2 = noiseControlIconViewBinding.name.getLayoutParams();
        if (layoutParams2 != null) {
            layoutParams2.width = context.getResources().getDimensionPixelSize(i6);
        }
    }
}
