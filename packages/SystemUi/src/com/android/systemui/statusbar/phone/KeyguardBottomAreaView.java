package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.LockIconView;
import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.R;
import com.android.systemui.keyguard.p009ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.p009ui.binder.KeyguardBottomAreaViewBinder$bind$2;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardBottomAreaView extends FrameLayout {
    public View ambientIndicationArea;
    public final KeyguardBottomAreaViewBinder binder;
    public KeyguardBottomAreaViewBinder.Binding binding;
    public final Lazy indicationArea$delegate;
    public final Lazy leftView$delegate;
    public SecLockIconViewController lockIconViewController;
    public PluginLockData pluginLockData;
    public final Lazy rightView$delegate;
    public static final Companion Companion = new Companion(null);
    public static final String CAMERA_LAUNCH_SOURCE_POWER_DOUBLE_TAP = "power_double_tap";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public void init(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, FalsingManager falsingManager, SecLockIconViewController secLockIconViewController, final MessageDisplayer messageDisplayer, VibratorHelper vibratorHelper, ActivityStarter activityStarter) {
        if (this.binding != null) {
            getBinding().destroy();
        }
        setBinding(getBinder().bind(this, keyguardBottomAreaViewModel, falsingManager, vibratorHelper, activityStarter, new Function1() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView$init$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                KeyguardBottomAreaView.MessageDisplayer messageDisplayer2 = KeyguardBottomAreaView.MessageDisplayer.this;
                if (messageDisplayer2 != null) {
                    ((NotificationPanelViewController$$ExternalSyntheticLambda2) messageDisplayer2).f$0.mKeyguardIndicationController.showTransientIndication(intValue);
                }
                return Unit.INSTANCE;
            }
        }));
        this.lockIconViewController = secLockIconViewController;
    }

    public boolean isInEmergencyButtonArea(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        int safeInsetBottom = displayCutout != null ? displayCutout.getSafeInsetBottom() : 0;
        if (isPaddingRelative()) {
            setPaddingRelative(getPaddingStart(), getPaddingTop(), getPaddingEnd(), safeInsetBottom);
        } else {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), safeInsetBottom);
        }
        return windowInsets;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.binding != null) {
            getBinding().onConfigurationChanged();
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.ambientIndicationArea = findViewById(R.id.ambient_indication_container);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Number number;
        super.onLayout(z, i, i2, i3, i4);
        View findViewById = findViewById(R.id.ambient_indication_container);
        if (findViewById != null) {
            int[] locationOnScreen = findViewById.getLocationOnScreen();
            int i5 = 0;
            int i6 = locationOnScreen[0];
            int i7 = locationOnScreen[1];
            if (getBinding().shouldConstrainToTopOfLockIcon()) {
                SecLockIconViewController secLockIconViewController = this.lockIconViewController;
                if (secLockIconViewController != null) {
                    i5 = (int) (r1.mLockIconCenter.y + ((LockIconView) secLockIconViewController.mView).mRadius);
                }
                findViewById.layout(i6, i5, i3 - i6, findViewById.getMeasuredHeight() + i7);
                return;
            }
            SecLockIconViewController secLockIconViewController2 = this.lockIconViewController;
            if (secLockIconViewController2 != null) {
                number = Float.valueOf(r1.mLockIconCenter.y - ((LockIconView) secLockIconViewController2.mView).mRadius);
            } else {
                number = 0;
            }
            findViewById.layout(i6, number.intValue() - findViewById.getMeasuredHeight(), i3 - i6, number.intValue());
        }
    }

    public void setBinding(KeyguardBottomAreaViewBinder$bind$2 keyguardBottomAreaViewBinder$bind$2) {
        this.binding = keyguardBottomAreaViewBinder$bind$2;
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
