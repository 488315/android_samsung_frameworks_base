package androidx.compose.ui.window;

import android.graphics.Outline;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.ComponentDialog;
import androidx.activity.OnBackPressedDispatcherKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.android.systemui.R;
import java.util.UUID;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DialogWrapper extends ComponentDialog {
    public final View composeView;
    public final DialogLayout dialogLayout;
    public Function0 onDismissRequest;
    public DialogProperties properties;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DialogWrapper(Function0 function0, DialogProperties dialogProperties, View view, LayoutDirection layoutDirection, Density density, UUID uuid) {
        super(new ContextThemeWrapper(view.getContext(), dialogProperties.decorFitsSystemWindows ? R.style.DialogWindowTheme : R.style.FloatingDialogWindowTheme), 0, 2, null);
        this.onDismissRequest = function0;
        this.properties = dialogProperties;
        this.composeView = view;
        float f = 8;
        Dp.Companion companion = Dp.Companion;
        Window window = getWindow();
        if (window == null) {
            throw new IllegalStateException("Dialog has no window");
        }
        window.requestFeature(1);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setDecorFitsSystemWindows(this.properties.decorFitsSystemWindows);
        window.setGravity(17);
        DialogLayout dialogLayout = new DialogLayout(getContext(), window);
        dialogLayout.setTag(R.id.compose_view_saveable_id_tag, "Dialog:" + uuid);
        dialogLayout.setClipChildren(false);
        dialogLayout.setElevation(density.mo57toPx0680j_4(f));
        dialogLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: androidx.compose.ui.window.DialogWrapper$1$2
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view2, Outline outline) {
                outline.setRect(0, 0, view2.getWidth(), view2.getHeight());
                outline.setAlpha(0.0f);
            }
        });
        this.dialogLayout = dialogLayout;
        View decorView = window.getDecorView();
        ViewGroup viewGroup = decorView instanceof ViewGroup ? (ViewGroup) decorView : null;
        if (viewGroup != null) {
            _init_$disableClipping(viewGroup);
        }
        setContentView(dialogLayout);
        dialogLayout.setTag(R.id.view_tree_lifecycle_owner, ViewTreeLifecycleOwner.get(view));
        dialogLayout.setTag(R.id.view_tree_view_model_store_owner, ViewTreeViewModelStoreOwner.get(view));
        dialogLayout.setTag(R.id.view_tree_saved_state_registry_owner, ViewTreeSavedStateRegistryOwner.get(view));
        updateParameters(this.onDismissRequest, this.properties, layoutDirection);
        OnBackPressedDispatcherKt.addCallback$default(this.onBackPressedDispatcher, this, new Function1() { // from class: androidx.compose.ui.window.DialogWrapper.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                DialogWrapper dialogWrapper = DialogWrapper.this;
                if (dialogWrapper.properties.dismissOnBackPress) {
                    dialogWrapper.onDismissRequest.invoke();
                }
                return Unit.INSTANCE;
            }
        });
    }

    public static final void _init_$disableClipping(ViewGroup viewGroup) {
        viewGroup.setClipChildren(false);
        if (viewGroup instanceof DialogLayout) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            ViewGroup viewGroup2 = childAt instanceof ViewGroup ? (ViewGroup) childAt : null;
            if (viewGroup2 != null) {
                _init_$disableClipping(viewGroup2);
            }
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!this.properties.dismissOnBackPress || !keyEvent.isTracking() || keyEvent.isCanceled() || i != 111) {
            return super.onKeyUp(i, keyEvent);
        }
        this.onDismissRequest.invoke();
        return true;
    }

    @Override // android.app.Dialog
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        View childAt;
        int roundToInt;
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (!this.properties.dismissOnClickOutside) {
            return onTouchEvent;
        }
        DialogLayout dialogLayout = this.dialogLayout;
        dialogLayout.getClass();
        float x = motionEvent.getX();
        if (!Float.isInfinite(x) && !Float.isNaN(x)) {
            float y = motionEvent.getY();
            if (!Float.isInfinite(y) && !Float.isNaN(y) && (childAt = dialogLayout.getChildAt(0)) != null) {
                int left = childAt.getLeft() + dialogLayout.getLeft();
                int width = childAt.getWidth() + left;
                int top = childAt.getTop() + dialogLayout.getTop();
                int height = childAt.getHeight() + top;
                int roundToInt2 = MathKt__MathJVMKt.roundToInt(motionEvent.getX());
                if (left <= roundToInt2 && roundToInt2 <= width && top <= (roundToInt = MathKt__MathJVMKt.roundToInt(motionEvent.getY())) && roundToInt <= height) {
                    return onTouchEvent;
                }
            }
        }
        this.onDismissRequest.invoke();
        return true;
    }

    public final void updateParameters(Function0 function0, DialogProperties dialogProperties, LayoutDirection layoutDirection) {
        int i;
        this.onDismissRequest = function0;
        this.properties = dialogProperties;
        SecureFlagPolicy secureFlagPolicy = dialogProperties.securePolicy;
        boolean isFlagSecureEnabled = AndroidPopup_androidKt.isFlagSecureEnabled(this.composeView);
        int i2 = SecureFlagPolicy_androidKt$WhenMappings.$EnumSwitchMapping$0[secureFlagPolicy.ordinal()];
        if (i2 == 1) {
            isFlagSecureEnabled = false;
        } else if (i2 == 2) {
            isFlagSecureEnabled = true;
        } else if (i2 != 3) {
            throw new NoWhenBranchMatchedException();
        }
        Window window = getWindow();
        window.getClass();
        window.setFlags(isFlagSecureEnabled ? 8192 : -8193, 8192);
        DialogLayout dialogLayout = this.dialogLayout;
        int i3 = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
        if (i3 == 1) {
            i = 0;
        } else {
            if (i3 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            i = 1;
        }
        dialogLayout.setLayoutDirection(i);
        DialogLayout dialogLayout2 = this.dialogLayout;
        boolean z = dialogLayout2.hasCalledSetLayout;
        boolean z2 = dialogProperties.decorFitsSystemWindows;
        boolean z3 = dialogProperties.usePlatformDefaultWidth;
        boolean z4 = (z && z3 == dialogLayout2.usePlatformDefaultWidth && z2 == dialogLayout2.decorFitsSystemWindows) ? false : true;
        dialogLayout2.usePlatformDefaultWidth = z3;
        dialogLayout2.decorFitsSystemWindows = z2;
        if (z4) {
            WindowManager.LayoutParams attributes = dialogLayout2.window.getAttributes();
            int i4 = z3 ? -2 : -1;
            if (i4 != attributes.width || !dialogLayout2.hasCalledSetLayout) {
                dialogLayout2.window.setLayout(i4, -2);
                dialogLayout2.hasCalledSetLayout = true;
            }
        }
        setCanceledOnTouchOutside(dialogProperties.dismissOnClickOutside);
        Window window2 = getWindow();
        if (window2 != null) {
            window2.setSoftInputMode(z2 ? 0 : 48);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void cancel() {
    }
}
