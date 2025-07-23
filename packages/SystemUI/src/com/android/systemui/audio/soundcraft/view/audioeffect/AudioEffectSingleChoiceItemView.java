package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectSingleChoiceItemViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel;
import com.android.systemui.qs.customize.QSBlurPopUpMenu;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioEffectSingleChoiceItemView extends BaseAudioEffectItemView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioEffectSingleChoiceItemViewBinding binding;
    public QSBlurPopUpMenu chooserMenu;
    public final Context context;
    public final LifecycleOwner lifecycleOwner;
    public final ViewGroup parent;
    public final BaseSingleChoiceViewModel viewModel;

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

    public AudioEffectSingleChoiceItemView(Context context, LifecycleOwner lifecycleOwner, BaseSingleChoiceViewModel baseSingleChoiceViewModel, ViewGroup viewGroup) {
        final int i = 0;
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = baseSingleChoiceViewModel;
        this.parent = viewGroup;
        int i2 = SoundCraftViewBindingFactory.$r8$clinit;
        AudioEffectSingleChoiceItemViewBinding audioEffectSingleChoiceItemViewBinding = new AudioEffectSingleChoiceItemViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_audio_effect_single_choice_chooser_item, viewGroup, false));
        this.binding = audioEffectSingleChoiceItemViewBinding;
        audioEffectSingleChoiceItemViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectSingleChoiceItemView.this.viewModel.onClick();
            }
        });
        baseSingleChoiceViewModel.showChooser.observe(lifecycleOwner, new AudioEffectSingleChoiceItemView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectSingleChoiceItemView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final AudioEffectSingleChoiceItemView audioEffectSingleChoiceItemView = this.f$0;
                switch (i) {
                    case 0:
                        int i3 = AudioEffectSingleChoiceItemView.$r8$clinit;
                        if (((Boolean) obj).booleanValue()) {
                            QSBlurPopUpMenu qSBlurPopUpMenu = audioEffectSingleChoiceItemView.chooserMenu;
                            if (qSBlurPopUpMenu != null) {
                                qSBlurPopUpMenu.dismiss();
                            }
                            LinearLayout linearLayout = audioEffectSingleChoiceItemView.binding.root;
                            if (linearLayout.getWindowToken() != null) {
                                BaseSingleChoiceViewModel baseSingleChoiceViewModel2 = audioEffectSingleChoiceItemView.viewModel;
                                Object value = baseSingleChoiceViewModel2.getOptionNames().getValue();
                                value.getClass();
                                Iterable<String> iterable = (Iterable) value;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                                for (String str : iterable) {
                                    arrayList.add(new QSBlurPopUpMenu.PopUpContent(str, Intrinsics.areEqual(baseSingleChoiceViewModel2.selectedOptionName.getValue(), str)));
                                }
                                QSBlurPopUpMenu qSBlurPopUpMenu2 = new QSBlurPopUpMenu(audioEffectSingleChoiceItemView.context);
                                qSBlurPopUpMenu2.setWidth(-2);
                                qSBlurPopUpMenu2.setAnchorView(linearLayout);
                                qSBlurPopUpMenu2.setDropDownGravity(8388611);
                                qSBlurPopUpMenu2.setAdapter(new QSBlurPopUpMenu.PopupListAdapter(audioEffectSingleChoiceItemView.context, arrayList));
                                qSBlurPopUpMenu2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$1
                                    @Override // android.widget.AdapterView.OnItemClickListener
                                    public final void onItemClick(AdapterView adapterView, View view, int i4, long j) {
                                        AudioEffectSingleChoiceItemView.this.viewModel.onItemSelected(i4);
                                    }
                                });
                                qSBlurPopUpMenu2.dismissListener = new PopupWindow.OnDismissListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$2
                                    @Override // android.widget.PopupWindow.OnDismissListener
                                    public final void onDismiss() {
                                        AudioEffectSingleChoiceItemView.this.viewModel.dismiss();
                                    }
                                };
                                qSBlurPopUpMenu2.show();
                                audioEffectSingleChoiceItemView.chooserMenu = qSBlurPopUpMenu2;
                            }
                        } else {
                            QSBlurPopUpMenu qSBlurPopUpMenu3 = audioEffectSingleChoiceItemView.chooserMenu;
                            if (qSBlurPopUpMenu3 != null) {
                                qSBlurPopUpMenu3.dismiss();
                            }
                            audioEffectSingleChoiceItemView.chooserMenu = null;
                        }
                        break;
                    case 1:
                        audioEffectSingleChoiceItemView.binding.name.setText((String) obj);
                        break;
                    default:
                        audioEffectSingleChoiceItemView.binding.status.setText((String) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i3 = 1;
        baseSingleChoiceViewModel.getTitle().observe(lifecycleOwner, new AudioEffectSingleChoiceItemView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectSingleChoiceItemView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final AudioEffectSingleChoiceItemView audioEffectSingleChoiceItemView = this.f$0;
                switch (i3) {
                    case 0:
                        int i32 = AudioEffectSingleChoiceItemView.$r8$clinit;
                        if (((Boolean) obj).booleanValue()) {
                            QSBlurPopUpMenu qSBlurPopUpMenu = audioEffectSingleChoiceItemView.chooserMenu;
                            if (qSBlurPopUpMenu != null) {
                                qSBlurPopUpMenu.dismiss();
                            }
                            LinearLayout linearLayout = audioEffectSingleChoiceItemView.binding.root;
                            if (linearLayout.getWindowToken() != null) {
                                BaseSingleChoiceViewModel baseSingleChoiceViewModel2 = audioEffectSingleChoiceItemView.viewModel;
                                Object value = baseSingleChoiceViewModel2.getOptionNames().getValue();
                                value.getClass();
                                Iterable<String> iterable = (Iterable) value;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                                for (String str : iterable) {
                                    arrayList.add(new QSBlurPopUpMenu.PopUpContent(str, Intrinsics.areEqual(baseSingleChoiceViewModel2.selectedOptionName.getValue(), str)));
                                }
                                QSBlurPopUpMenu qSBlurPopUpMenu2 = new QSBlurPopUpMenu(audioEffectSingleChoiceItemView.context);
                                qSBlurPopUpMenu2.setWidth(-2);
                                qSBlurPopUpMenu2.setAnchorView(linearLayout);
                                qSBlurPopUpMenu2.setDropDownGravity(8388611);
                                qSBlurPopUpMenu2.setAdapter(new QSBlurPopUpMenu.PopupListAdapter(audioEffectSingleChoiceItemView.context, arrayList));
                                qSBlurPopUpMenu2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$1
                                    @Override // android.widget.AdapterView.OnItemClickListener
                                    public final void onItemClick(AdapterView adapterView, View view, int i4, long j) {
                                        AudioEffectSingleChoiceItemView.this.viewModel.onItemSelected(i4);
                                    }
                                });
                                qSBlurPopUpMenu2.dismissListener = new PopupWindow.OnDismissListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$2
                                    @Override // android.widget.PopupWindow.OnDismissListener
                                    public final void onDismiss() {
                                        AudioEffectSingleChoiceItemView.this.viewModel.dismiss();
                                    }
                                };
                                qSBlurPopUpMenu2.show();
                                audioEffectSingleChoiceItemView.chooserMenu = qSBlurPopUpMenu2;
                            }
                        } else {
                            QSBlurPopUpMenu qSBlurPopUpMenu3 = audioEffectSingleChoiceItemView.chooserMenu;
                            if (qSBlurPopUpMenu3 != null) {
                                qSBlurPopUpMenu3.dismiss();
                            }
                            audioEffectSingleChoiceItemView.chooserMenu = null;
                        }
                        break;
                    case 1:
                        audioEffectSingleChoiceItemView.binding.name.setText((String) obj);
                        break;
                    default:
                        audioEffectSingleChoiceItemView.binding.status.setText((String) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i4 = 2;
        baseSingleChoiceViewModel.selectedOptionName.observe(lifecycleOwner, new AudioEffectSingleChoiceItemView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectSingleChoiceItemView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final AudioEffectSingleChoiceItemView audioEffectSingleChoiceItemView = this.f$0;
                switch (i4) {
                    case 0:
                        int i32 = AudioEffectSingleChoiceItemView.$r8$clinit;
                        if (((Boolean) obj).booleanValue()) {
                            QSBlurPopUpMenu qSBlurPopUpMenu = audioEffectSingleChoiceItemView.chooserMenu;
                            if (qSBlurPopUpMenu != null) {
                                qSBlurPopUpMenu.dismiss();
                            }
                            LinearLayout linearLayout = audioEffectSingleChoiceItemView.binding.root;
                            if (linearLayout.getWindowToken() != null) {
                                BaseSingleChoiceViewModel baseSingleChoiceViewModel2 = audioEffectSingleChoiceItemView.viewModel;
                                Object value = baseSingleChoiceViewModel2.getOptionNames().getValue();
                                value.getClass();
                                Iterable<String> iterable = (Iterable) value;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                                for (String str : iterable) {
                                    arrayList.add(new QSBlurPopUpMenu.PopUpContent(str, Intrinsics.areEqual(baseSingleChoiceViewModel2.selectedOptionName.getValue(), str)));
                                }
                                QSBlurPopUpMenu qSBlurPopUpMenu2 = new QSBlurPopUpMenu(audioEffectSingleChoiceItemView.context);
                                qSBlurPopUpMenu2.setWidth(-2);
                                qSBlurPopUpMenu2.setAnchorView(linearLayout);
                                qSBlurPopUpMenu2.setDropDownGravity(8388611);
                                qSBlurPopUpMenu2.setAdapter(new QSBlurPopUpMenu.PopupListAdapter(audioEffectSingleChoiceItemView.context, arrayList));
                                qSBlurPopUpMenu2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$1
                                    @Override // android.widget.AdapterView.OnItemClickListener
                                    public final void onItemClick(AdapterView adapterView, View view, int i42, long j) {
                                        AudioEffectSingleChoiceItemView.this.viewModel.onItemSelected(i42);
                                    }
                                });
                                qSBlurPopUpMenu2.dismissListener = new PopupWindow.OnDismissListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectSingleChoiceItemView$showChooser$1$2
                                    @Override // android.widget.PopupWindow.OnDismissListener
                                    public final void onDismiss() {
                                        AudioEffectSingleChoiceItemView.this.viewModel.dismiss();
                                    }
                                };
                                qSBlurPopUpMenu2.show();
                                audioEffectSingleChoiceItemView.chooserMenu = qSBlurPopUpMenu2;
                            }
                        } else {
                            QSBlurPopUpMenu qSBlurPopUpMenu3 = audioEffectSingleChoiceItemView.chooserMenu;
                            if (qSBlurPopUpMenu3 != null) {
                                qSBlurPopUpMenu3.dismiss();
                            }
                            audioEffectSingleChoiceItemView.chooserMenu = null;
                        }
                        break;
                    case 1:
                        audioEffectSingleChoiceItemView.binding.name.setText((String) obj);
                        break;
                    default:
                        audioEffectSingleChoiceItemView.binding.status.setText((String) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
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
