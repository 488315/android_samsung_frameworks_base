package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.SecLockIconView;
import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$4;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda7;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardBottomAreaView extends FrameLayout {
    public View ambientIndicationArea;
    public final KeyguardBottomAreaViewBinder binder;
    public KeyguardBottomAreaViewBinder.Binding binding;
    public final Lazy indicationArea$delegate;
    public boolean isLockscreenLandscapeEnabled;
    public View keyguardIndicationArea;
    public final Lazy leftView$delegate;
    public SecLockIconViewController lockIconViewController;
    public PluginLockData pluginLockData;
    public dagger.Lazy pluginLockStarManagerLazy;
    public final Lazy rightView$delegate;
    public static final Companion Companion = new Companion(null);
    public static final String CAMERA_LAUNCH_SOURCE_POWER_DOUBLE_TAP = "power_double_tap";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface MessageDisplayer {
    }

    public KeyguardBottomAreaView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public KeyguardBottomAreaViewBinder getBinder() {
        return this.binder;
    }

    public KeyguardBottomAreaViewBinder.Binding getBinding() {
        KeyguardBottomAreaViewBinder.Binding binding = this.binding;
        if (binding != null) {
            return binding;
        }
        return null;
    }

    public ImageView getLeftView() {
        return (ImageView) this.leftView$delegate.getValue();
    }

    public ImageView getRightView() {
        return (ImageView) this.rightView$delegate.getValue();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void init(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, FalsingManager falsingManager, SecLockIconViewController secLockIconViewController, final MessageDisplayer messageDisplayer, VibratorHelper vibratorHelper, ActivityStarter activityStarter, dagger.Lazy lazy) {
        if (this.binding != null) {
            getBinding().destroy();
        }
        setBinding(getBinder().bind(this, keyguardBottomAreaViewModel, falsingManager, vibratorHelper, activityStarter, new Function1() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView$init$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                KeyguardBottomAreaView.MessageDisplayer messageDisplayer2 = KeyguardBottomAreaView.MessageDisplayer.this;
                if (messageDisplayer2 != null) {
                    ((NotificationPanelViewController$$ExternalSyntheticLambda7) messageDisplayer2).f$0.mKeyguardIndicationController.showTransientIndication(intValue);
                }
                return Unit.INSTANCE;
            }
        }));
        this.lockIconViewController = secLockIconViewController;
        this.pluginLockStarManagerLazy = lazy;
    }

    public boolean isInEmergencyButtonArea(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        View view;
        super.onConfigurationChanged(configuration);
        if (this.binding != null) {
            getBinding().onConfigurationChanged();
        }
        if (!this.isLockscreenLandscapeEnabled || (view = this.keyguardIndicationArea) == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom);
        view.setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.ambientIndicationArea = findViewById(R.id.ambient_indication_container);
        this.keyguardIndicationArea = findViewById(R.id.keyguard_indication_area);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Number number;
        super.onLayout(z, i, i2, i3, i4);
        View findViewById = findViewById(R.id.ambient_indication_container);
        if (findViewById != null) {
            int[] locationOnScreen = findViewById.getLocationOnScreen();
            Intrinsics.checkNotNull(locationOnScreen);
            int i5 = 0;
            int i6 = locationOnScreen[0];
            int i7 = locationOnScreen[1];
            KeyguardBottomAreaViewBinder.Binding binding = getBinding();
            if (binding != null && binding.shouldConstrainToTopOfLockIcon()) {
                SecLockIconViewController secLockIconViewController = this.lockIconViewController;
                if (secLockIconViewController != null) {
                    SecLockIconView secLockIconView = secLockIconViewController.mView;
                    secLockIconView.getClass();
                    secLockIconView.mLockIcon.getGlobalVisibleRect(new Rect());
                }
                findViewById.layout(i6, i5, i3 - i6, findViewById.getMeasuredHeight() + i7);
                return;
            }
            SecLockIconViewController secLockIconViewController2 = this.lockIconViewController;
            if (secLockIconViewController2 != null) {
                SecLockIconView secLockIconView2 = secLockIconViewController2.mView;
                secLockIconView2.getClass();
                secLockIconView2.mLockIcon.getGlobalVisibleRect(new Rect());
                number = Float.valueOf(r4.top);
            } else {
                number = 0;
            }
            findViewById.layout(i6, number.intValue() - findViewById.getMeasuredHeight(), i3 - i6, number.intValue());
        }
    }

    public void setBinding(KeyguardBottomAreaViewBinder$bind$4 keyguardBottomAreaViewBinder$bind$4) {
        this.binding = keyguardBottomAreaViewBinder$bind$4;
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ KeyguardBottomAreaView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.leftView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView$leftView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ImageView) KeyguardBottomAreaView.this.requireViewById(R.id.start_button);
            }
        });
        this.rightView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView$rightView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ImageView) KeyguardBottomAreaView.this.requireViewById(R.id.end_button);
            }
        });
        this.indicationArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView$indicationArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ViewGroup) KeyguardBottomAreaView.this.requireViewById(R.id.keyguard_indication_area);
            }
        });
        KeyguardBottomAreaViewBinder.Companion.getClass();
        this.binder = KeyguardBottomAreaViewBinder.INSTANCE;
    }

    public void initEmergencyButton(EmergencyButtonController.Factory factory) {
    }

    public void setAllChildEnabled(View view, boolean z) {
    }
}
