package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectSingleChoiceItemViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel;
import com.android.systemui.qs.customize.QSBlurPopUpMenu;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AudioEffectSingleChoiceItemView extends BaseAudioEffectItemView {
    public final AudioEffectSingleChoiceItemViewBinding binding;
    public QSBlurPopUpMenu chooserMenu;
    public final Context context;
    public final LifecycleOwner lifecycleOwner;
    public final ViewGroup parent;
    public final BaseSingleChoiceViewModel viewModel;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public AudioEffectSingleChoiceItemView(Context context, LifecycleOwner lifecycleOwner, BaseSingleChoiceViewModel baseSingleChoiceViewModel, ViewGroup viewGroup) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = baseSingleChoiceViewModel;
        this.parent = viewGroup;
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        AudioEffectSingleChoiceItemViewBinding audioEffectSingleChoiceItemViewBinding = new AudioEffectSingleChoiceItemViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_audio_effect_single_choice_chooser_item, viewGroup, false));
        this.binding = audioEffectSingleChoiceItemViewBinding;
        audioEffectSingleChoiceItemViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectSingleChoiceItemView.this.viewModel.onClick();
            }
        });
        baseSingleChoiceViewModel.showChooser.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView.2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                Intrinsics.checkNotNull(bool);
                boolean booleanValue = bool.booleanValue();
                final AudioEffectSingleChoiceItemView audioEffectSingleChoiceItemView = AudioEffectSingleChoiceItemView.this;
                if (!booleanValue) {
                    QSBlurPopUpMenu qSBlurPopUpMenu = audioEffectSingleChoiceItemView.chooserMenu;
                    if (qSBlurPopUpMenu != null) {
                        qSBlurPopUpMenu.dismiss();
                    }
                    audioEffectSingleChoiceItemView.chooserMenu = null;
                    return;
                }
                QSBlurPopUpMenu qSBlurPopUpMenu2 = audioEffectSingleChoiceItemView.chooserMenu;
                if (qSBlurPopUpMenu2 != null) {
                    qSBlurPopUpMenu2.dismiss();
                }
                LinearLayout linearLayout = audioEffectSingleChoiceItemView.binding.root;
                if (linearLayout.getWindowToken() == null) {
                    return;
                }
                BaseSingleChoiceViewModel baseSingleChoiceViewModel2 = audioEffectSingleChoiceItemView.viewModel;
                Object value = baseSingleChoiceViewModel2.getOptionNames().getValue();
                Intrinsics.checkNotNull(value);
                Iterable<String> iterable = (Iterable) value;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                for (String str : iterable) {
                    arrayList.add(new QSBlurPopUpMenu.PopUpContent(str, Intrinsics.areEqual(baseSingleChoiceViewModel2.selectedOptionName.getValue(), str)));
                }
                QSBlurPopUpMenu qSBlurPopUpMenu3 = new QSBlurPopUpMenu(audioEffectSingleChoiceItemView.context);
                qSBlurPopUpMenu3.setWidth(-2);
                qSBlurPopUpMenu3.setAnchorView(linearLayout);
                qSBlurPopUpMenu3.setDropDownGravity(8388611);
                qSBlurPopUpMenu3.setAdapter(new QSBlurPopUpMenu.PopupListAdapter(audioEffectSingleChoiceItemView.context, arrayList));
                qSBlurPopUpMenu3.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(AdapterView adapterView, View view, int i2, long j) {
                        AudioEffectSingleChoiceItemView.this.viewModel.onItemSelected(i2);
                    }
                });
                qSBlurPopUpMenu3.dismissListener = new PopupWindow.OnDismissListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$2
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public final void onDismiss() {
                        AudioEffectSingleChoiceItemView.this.viewModel.dismiss();
                    }
                };
                qSBlurPopUpMenu3.show();
                audioEffectSingleChoiceItemView.chooserMenu = qSBlurPopUpMenu3;
            }
        });
        baseSingleChoiceViewModel.getTitle().observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView.3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectSingleChoiceItemView.this.binding.name.setText((String) obj);
            }
        });
        baseSingleChoiceViewModel.selectedOptionName.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectSingleChoiceItemView.this.binding.status.setText((String) obj);
            }
        });
        baseSingleChoiceViewModel.notifyChange();
    }

    @Override // com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final void enable(boolean z) {
        AudioEffectSingleChoiceItemViewBinding audioEffectSingleChoiceItemViewBinding = this.binding;
        audioEffectSingleChoiceItemViewBinding.root.setClickable(z);
        audioEffectSingleChoiceItemViewBinding.root.setEnabled(z);
        audioEffectSingleChoiceItemViewBinding.root.setAlpha(z ? 1.0f : 0.5f);
    }

    @Override // com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final ViewGroup getRootView() {
        return this.binding.root;
    }

    @Override // com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final void update() {
        this.viewModel.notifyChange();
    }
}
