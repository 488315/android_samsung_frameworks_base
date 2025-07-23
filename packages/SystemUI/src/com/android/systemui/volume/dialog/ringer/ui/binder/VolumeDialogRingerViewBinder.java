package com.android.systemui.volume.dialog.ringer.ui.binder;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSDetailController$$ExternalSyntheticOutline0;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$2;
import com.android.systemui.volume.dialog.ringer.ui.util.RingerDrawerConstraintsUtilsKt;
import com.android.systemui.volume.dialog.ringer.ui.util.VolumeDialogRingerDrawerTransitionListener;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerButtonUiModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerButtonViewModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerDrawerState;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerViewModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel;
import com.android.systemui.volume.dialog.ui.binder.ViewBinder;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogRingerViewBinder implements ViewBinder {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final SpringForce colorSpringForce;
    public final VolumeDialogViewModel dialogViewModel;
    public final ArgbEvaluator rgbEvaluator;
    public final SpringForce roundnessSpringForce;
    public final VolumeDialogRingerDrawerViewModel viewModel;

    static {
        MutablePropertyReference0Impl mutablePropertyReference0Impl = new MutablePropertyReference0Impl(VolumeDialogRingerViewBinder.class, "backgroundAnimationProgress", "<v#0>", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference0Impl};
    }

    public VolumeDialogRingerViewBinder(VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, VolumeDialogViewModel volumeDialogViewModel) {
        this.viewModel = volumeDialogRingerDrawerViewModel;
        this.dialogViewModel = volumeDialogViewModel;
        SpringForce springForce = new SpringForce(1.0f);
        springForce.setStiffness(800.0f);
        springForce.setDampingRatio(0.6f);
        this.roundnessSpringForce = springForce;
        SpringForce springForce2 = new SpringForce(1.0f);
        springForce2.setStiffness(3800.0f);
        springForce2.setDampingRatio(1.0f);
        this.colorSpringForce = springForce2;
        this.rgbEvaluator = new ArgbEvaluator();
    }

    public static final Object access$animateTo(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, Function2 function2, SuspendLambda suspendLambda) {
        volumeDialogRingerViewBinder.getClass();
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder(0.0f), 1.0f);
        springAnimation.mSpring = volumeDialogRingerViewBinder.roundnessSpringForce;
        SpringAnimation springAnimation2 = new SpringAnimation(new FloatValueHolder(0.0f), 1.0f);
        springAnimation2.mSpring = volumeDialogRingerViewBinder.colorSpringForce;
        float cornerRadius = ((GradientDrawable) imageButton.getBackground()).getCornerRadius();
        float cornerRadius2 = ringerButtonUiModel.cornerRadius - ((GradientDrawable) imageButton.getBackground()).getCornerRadius();
        springAnimation.setMinimumVisibleChange(0.05f);
        springAnimation2.setMinimumVisibleChange(0.05f);
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new VolumeDialogRingerViewBinder$animateTo$3(springAnimation, springAnimation2, volumeDialogRingerViewBinder, imageButton, ringerButtonUiModel, function2, cornerRadius2, cornerRadius, null), suspendLambda);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public static final void access$closeDrawer(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, MotionLayout motionLayout, View view, int i, int i2) {
        Throwable th;
        volumeDialogRingerViewBinder.getClass();
        motionLayout.setTransition(R.id.close_to_open_transition);
        MotionScene.Transition transition = motionLayout.getTransition(R.id.close_to_open_transition);
        transition.mDefaultInterpolator = -2;
        Throwable th2 = null;
        transition.mDefaultInterpolatorString = null;
        transition.mDefaultInterpolatorID = R.anim.volume_dialog_ringer_close;
        ConstraintSet cloneConstraintSet = motionLayout.cloneConstraintSet(R.id.volume_dialog_ringer_drawer_close);
        cloneConstraintSet.setVisibility(view.getId(), 0);
        int i3 = 0;
        for (Object obj : ConvenienceExtensionsKt.getChildren(motionLayout)) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                Throwable th3 = th2;
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw th3;
            }
            View view2 = (View) obj;
            if (view2.getId() != R.id.ringer_buttons_background) {
                cloneConstraintSet.setMargin(view2.getId(), 7, 0);
                cloneConstraintSet.setMargin(view2.getId(), 4, 0);
                if (i2 != 1) {
                    th = th2;
                    if (i2 == 2) {
                        RingerDrawerConstraintsUtilsKt.setButtonPositionLandscapeConstraints(cloneConstraintSet, motionLayout, i3, view2);
                        if (i != (motionLayout.getChildCount() - i3) - 1) {
                            cloneConstraintSet.setAlpha(view2.getId(), 0.0f);
                            cloneConstraintSet.constrainWidth(view2.getId(), (int) TypedValue.applyDimension(1, 1.0f, motionLayout.getContext().getResources().getDisplayMetrics()));
                        } else {
                            cloneConstraintSet.connect(view2.getId(), 7, motionLayout.getId(), 7);
                            cloneConstraintSet.setAlpha(view2.getId(), 1.0f);
                            cloneConstraintSet.constrainWidth(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                        }
                        cloneConstraintSet.constrainHeight(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                    }
                } else {
                    th = th2;
                    RingerDrawerConstraintsUtilsKt.setButtonPositionPortraitConstraints(cloneConstraintSet, motionLayout, i3, view2);
                    if (i != (motionLayout.getChildCount() - i3) - 1) {
                        cloneConstraintSet.setAlpha(view2.getId(), 0.0f);
                        cloneConstraintSet.constrainHeight(view2.getId(), (int) TypedValue.applyDimension(1, 1.0f, motionLayout.getContext().getResources().getDisplayMetrics()));
                    } else {
                        cloneConstraintSet.setAlpha(view2.getId(), 1.0f);
                        cloneConstraintSet.constrainHeight(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                    }
                    cloneConstraintSet.constrainWidth(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                }
            } else {
                th = th2;
                cloneConstraintSet.constrainWidth(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_width));
                cloneConstraintSet.connect(view2.getId(), 4, motionLayout.getId(), 4);
                cloneConstraintSet.connect(view2.getId(), 6, motionLayout.getChildAt((motionLayout.getChildCount() - i) - 1).getId(), 6);
                cloneConstraintSet.connect(view2.getId(), 7, motionLayout.getId(), 7);
                cloneConstraintSet.connect(view2.getId(), 3, motionLayout.getChildAt((motionLayout.getChildCount() - i) - 1).getId(), 3);
            }
            th2 = th;
            i3 = i4;
        }
        motionLayout.updateState(R.id.volume_dialog_ringer_drawer_close, cloneConstraintSet);
        motionLayout.transitionToState(R.id.volume_dialog_ringer_drawer_close);
    }

    public static void bindButtons(MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, RingerViewModel ringerViewModel, Runnable runnable, boolean z) {
        int size = ringerViewModel.availableButtons.size();
        List list = ringerViewModel.availableButtons;
        int size2 = list.size();
        for (int i = 0; i < size2; i++) {
            RingerButtonViewModel ringerButtonViewModel = (RingerButtonViewModel) list.get(i);
            ImageButton imageButton = (ImageButton) motionLayout.getChildAt(size - i);
            boolean z2 = ringerViewModel.drawerState instanceof RingerDrawerState.Open;
            if (i == ringerViewModel.currentButtonIndex) {
                if (!z2) {
                    ringerButtonViewModel = ringerViewModel.selectedButton;
                }
                bindDrawerButton(imageButton, ringerButtonViewModel, volumeDialogRingerDrawerViewModel, z2, true, z);
            } else {
                bindDrawerButton(imageButton, ringerButtonViewModel, volumeDialogRingerDrawerViewModel, z2, false, z);
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void bindDrawerButton(ImageButton imageButton, final RingerButtonViewModel ringerButtonViewModel, final VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, boolean z, final boolean z2, boolean z3) {
        imageButton.setSelected(z2);
        String string = imageButton.getContext().getString(ringerButtonViewModel.contentDescriptionResId);
        imageButton.setImageResource(ringerButtonViewModel.imageResId);
        if (z2 && !z) {
            string = imageButton.getContext().getString(R.string.volume_ringer_drawer_closed_content_description, string);
        }
        imageButton.setContentDescription(string);
        if (z2 && !z3) {
            imageButton.setBackgroundResource(R.drawable.volume_drawer_selection_bg);
            imageButton.setColorFilter(imageButton.getContext().getColor(android.R.color.resolver_profile_tab_text));
            imageButton.setBackground(imageButton.getBackground().mutate());
        } else if (!z3) {
            imageButton.setBackgroundResource(R.drawable.volume_ringer_item_bg);
            imageButton.setColorFilter(imageButton.getContext().getColor(android.R.color.search_url_text_material_light));
            imageButton.setBackground(imageButton.getBackground().mutate());
        }
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bindDrawerButton$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VolumeDialogRingerDrawerViewModel.this.m3201onRingerButtonClicked28s9KyU(ringerButtonViewModel.ringerMode, z2);
            }
        });
    }

    public final Object animateAndBindDrawerButtons(MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, RingerViewModel ringerViewModel, RingerButtonUiModel ringerButtonUiModel, RingerButtonUiModel ringerButtonUiModel2, Function2 function2, VolumeDialogRingerViewBinder$bind$2.AnonymousClass2 anonymousClass2, Continuation continuation) {
        int childCount = (motionLayout.getChildCount() - ringerViewModel.availableButtons.size()) - 1;
        int i = 0;
        if (childCount > 0) {
            motionLayout.removeViews(0, childCount);
        } else if (childCount < 0) {
            LayoutInflater from = LayoutInflater.from(motionLayout.getContext());
            int i2 = -childCount;
            for (int i3 = 0; i3 < i2; i3++) {
                from.inflate(R.layout.volume_ringer_button, (ViewGroup) motionLayout, true);
                motionLayout.getChildAt(motionLayout.getChildCount() - 1).setId(View.generateViewId());
            }
        }
        RingerDrawerState ringerDrawerState = ringerViewModel.drawerState;
        if (ringerDrawerState instanceof RingerDrawerState.Closed) {
            RingerDrawerState.Closed closed = (RingerDrawerState.Closed) ringerDrawerState;
            int i4 = closed.currentMode;
            int i5 = closed.previousMode;
            Set set = RingerMode.supportedRingerModes;
            if (i4 != i5) {
                int size = ringerViewModel.availableButtons.size();
                ImageButton imageButton = (ImageButton) motionLayout.getChildAt(size - ringerViewModel.currentButtonIndex);
                Iterator it = ringerViewModel.availableButtons.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    }
                    if (((RingerButtonViewModel) it.next()).ringerMode == closed.previousMode) {
                        break;
                    }
                    i++;
                }
                int i6 = i;
                Object coroutineScope = CoroutineScopeKt.coroutineScope(new VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3(imageButton, ringerButtonUiModel, (ImageButton) motionLayout.getChildAt(size - i6), ringerButtonUiModel2, this, ringerViewModel, size, function2, i6, motionLayout, volumeDialogRingerDrawerViewModel, anonymousClass2, null), continuation);
                return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
            }
        }
        bindButtons(motionLayout, volumeDialogRingerDrawerViewModel, ringerViewModel, anonymousClass2, false);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$$inlined$observable$1, kotlin.properties.ReadWriteProperty] */
    @Override // com.android.systemui.volume.dialog.ui.binder.ViewBinder
    public final void bind(CoroutineScope coroutineScope, View view) {
        View requireViewById = view.requireViewById(R.id.volume_dialog_background);
        final View requireViewById2 = view.requireViewById(R.id.ringer_buttons_background);
        MotionLayout motionLayout = (MotionLayout) view.requireViewById(R.id.volume_ringer_drawer);
        RingerButtonUiModel.Companion companion = RingerButtonUiModel.Companion;
        Context context = view.getContext();
        companion.getClass();
        RingerButtonUiModel ringerButtonUiModel = new RingerButtonUiModel(context.getColor(android.R.color.search_url_text_material_light), context.getColor(android.R.color.suggestion_highlight_text), context.getResources().getDimensionPixelSize(R.dimen.volume_dialog_background_square_corner_radius));
        Context context2 = view.getContext();
        RingerButtonUiModel ringerButtonUiModel2 = new RingerButtonUiModel(context2.getColor(android.R.color.resolver_profile_tab_text), context2.getColor(android.R.color.secondary_text_inverse_when_activated_material), context2.getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_selected_button_background_radius));
        final int m = SecQSDetailController$$ExternalSyntheticOutline0.m(view, R.dimen.volume_dialog_background_square_corner_radius);
        final int m2 = SecQSDetailController$$ExternalSyntheticOutline0.m(view, R.dimen.volume_dialog_background_corner_radius);
        float f = m2;
        float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f, f, f, f, f};
        Delegates delegates = Delegates.INSTANCE;
        final Float valueOf = Float.valueOf(0.0f);
        final ?? r0 = new ObservableProperty(valueOf) { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$$inlined$observable$1
            @Override // kotlin.properties.ObservableProperty
            public final void afterChange(Object obj, Object obj2) {
                float floatValue = ((Number) obj2).floatValue();
                ((Number) obj).floatValue();
                View view2 = requireViewById2;
                int i = m2;
                int i2 = i - m;
                KProperty[] kPropertyArr = VolumeDialogRingerViewBinder.$$delegatedProperties;
                this.getClass();
                ((GradientDrawable) view2.getBackground()).setCornerRadius(i - (floatValue * i2));
                view2.getBackground().invalidateSelf();
            }
        };
        VolumeDialogRingerDrawerTransitionListener volumeDialogRingerDrawerTransitionListener = new VolumeDialogRingerDrawerTransitionListener(new Function1() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f2 = (Float) obj;
                f2.floatValue();
                setValue(null, VolumeDialogRingerViewBinder.$$delegatedProperties[0], f2);
                return Unit.INSTANCE;
            }
        });
        motionLayout.setTransitionListener(volumeDialogRingerDrawerTransitionListener);
        requireViewById.setBackground(requireViewById.getBackground().mutate());
        requireViewById2.setBackground(requireViewById2.getBackground().mutate());
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new VolumeDialogRingerViewBinder$bind$1(this, requireViewById2, null), 6);
        CoroutineTracingKt.launchInTraced(FlowKt.mapLatest(this.viewModel.ringerViewModel, new VolumeDialogRingerViewBinder$bind$2(view, motionLayout, requireViewById, fArr, this, ringerButtonUiModel2, ringerButtonUiModel, volumeDialogRingerDrawerTransitionListener, requireViewById2, r0, null)), coroutineScope);
    }
}
