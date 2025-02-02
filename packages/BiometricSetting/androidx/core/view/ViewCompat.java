package androidx.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.drawerlayout.widget.DrawerLayout$$ExternalSyntheticLambda1;
import com.samsung.android.biometrics.app.setting.R;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressLint({"PrivateConstructorForUtilityClass"})
/* loaded from: classes.dex */
public final class ViewCompat {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final ViewCompat$$ExternalSyntheticLambda0 NO_OP_ON_RECEIVE_CONTENT_VIEW_BEHAVIOR;
    private static final AccessibilityPaneVisibilityManager sAccessibilityPaneVisibilityManager;
    private static WeakHashMap<View, ViewPropertyAnimatorCompat> sViewPropertyAnimatorMap;

    /* renamed from: androidx.core.view.ViewCompat$2 */
    final class C01022 extends AccessibilityViewProperty<CharSequence> {
        C01022(Class cls) {
            super(R.id.tag_accessibility_pane_title, cls, 8, 28);
        }

        @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
        final CharSequence frameworkGet(View view) {
            return Api28Impl.getAccessibilityPaneTitle(view);
        }

        @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
        final void frameworkSet(View view, CharSequence charSequence) {
            Api28Impl.setAccessibilityPaneTitle(view, charSequence);
        }

        @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
        final boolean shouldUpdate(CharSequence charSequence, CharSequence charSequence2) {
            return !TextUtils.equals(charSequence, charSequence2);
        }
    }

    /* renamed from: androidx.core.view.ViewCompat$3 */
    final class C01033 extends AccessibilityViewProperty<CharSequence> {
        C01033(Class cls) {
            super(R.id.tag_state_description, cls, 64, 30);
        }

        @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
        final CharSequence frameworkGet(View view) {
            return Api30Impl.getStateDescription(view);
        }

        @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
        final void frameworkSet(View view, CharSequence charSequence) {
            Api30Impl.setStateDescription(view, charSequence);
        }

        @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
        final boolean shouldUpdate(CharSequence charSequence, CharSequence charSequence2) {
            return !TextUtils.equals(charSequence, charSequence2);
        }
    }

    static abstract class AccessibilityViewProperty<T> {
        private final int mContentChangeType;
        private final int mFrameworkMinimumSdk;
        private final int mTagKey;
        private final Class<T> mType;

        AccessibilityViewProperty(int i, Class<T> cls, int i2, int i3) {
            this.mTagKey = i;
            this.mType = cls;
            this.mContentChangeType = i2;
            this.mFrameworkMinimumSdk = i3;
        }

        static boolean booleanNullToFalseEquals(Boolean bool, Boolean bool2) {
            return (bool != null && bool.booleanValue()) == (bool2 != null && bool2.booleanValue());
        }

        abstract T frameworkGet(View view);

        abstract void frameworkSet(View view, T t);

        final T get(View view) {
            if (Build.VERSION.SDK_INT >= this.mFrameworkMinimumSdk) {
                return frameworkGet(view);
            }
            T t = (T) view.getTag(this.mTagKey);
            if (this.mType.isInstance(t)) {
                return t;
            }
            return null;
        }

        final void set(View view, T t) {
            if (Build.VERSION.SDK_INT >= this.mFrameworkMinimumSdk) {
                frameworkSet(view, t);
                return;
            }
            if (shouldUpdate(get(view), t)) {
                AccessibilityDelegateCompat accessibilityDelegate = ViewCompat.getAccessibilityDelegate(view);
                if (accessibilityDelegate == null) {
                    accessibilityDelegate = new AccessibilityDelegateCompat();
                }
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegate);
                view.setTag(this.mTagKey, t);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, this.mContentChangeType);
            }
        }

        abstract boolean shouldUpdate(T t, T t2);
    }

    static class Api15Impl {
        static boolean hasOnClickListeners(View view) {
            return view.hasOnClickListeners();
        }
    }

    static class Api16Impl {
        static boolean getFitsSystemWindows(View view) {
            return view.getFitsSystemWindows();
        }

        static int getImportantForAccessibility(View view) {
            return view.getImportantForAccessibility();
        }

        static int getMinimumHeight(View view) {
            return view.getMinimumHeight();
        }

        static int getMinimumWidth(View view) {
            return view.getMinimumWidth();
        }

        static int getWindowSystemUiVisibility(View view) {
            return view.getWindowSystemUiVisibility();
        }

        static boolean hasTransientState(View view) {
            return view.hasTransientState();
        }

        static void postInvalidateOnAnimation(View view) {
            view.postInvalidateOnAnimation();
        }

        static void postOnAnimation(View view, Runnable runnable) {
            view.postOnAnimation(runnable);
        }

        static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            view.postOnAnimationDelayed(runnable, j);
        }

        static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }

        static void setBackground(View view, Drawable drawable) {
            view.setBackground(drawable);
        }

        static void setImportantForAccessibility(View view, int i) {
            view.setImportantForAccessibility(i);
        }
    }

    static class Api17Impl {
        static Display getDisplay(View view) {
            return view.getDisplay();
        }

        static int getLayoutDirection(View view) {
            return view.getLayoutDirection();
        }
    }

    static class Api19Impl {
        static int getAccessibilityLiveRegion(View view) {
            return view.getAccessibilityLiveRegion();
        }

        static boolean isAttachedToWindow(View view) {
            return view.isAttachedToWindow();
        }

        static boolean isLaidOut(View view) {
            return view.isLaidOut();
        }

        static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) {
            viewParent.notifySubtreeAccessibilityStateChanged(view, view2, i);
        }

        static void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
            accessibilityEvent.setContentChangeTypes(i);
        }
    }

    static class Api20Impl {
        static WindowInsets dispatchApplyWindowInsets(View view, WindowInsets windowInsets) {
            return view.dispatchApplyWindowInsets(windowInsets);
        }

        static WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            return view.onApplyWindowInsets(windowInsets);
        }

        static void requestApplyInsets(View view) {
            view.requestApplyInsets();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Api21Impl {
        static WindowInsetsCompat computeSystemWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, Rect rect) {
            WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
            if (windowInsets != null) {
                return WindowInsetsCompat.toWindowInsetsCompat(view.computeSystemWindowInsets(windowInsets, rect), view);
            }
            rect.setEmpty();
            return windowInsetsCompat;
        }

        static ColorStateList getBackgroundTintList(View view) {
            return view.getBackgroundTintList();
        }

        static PorterDuff.Mode getBackgroundTintMode(View view) {
            return view.getBackgroundTintMode();
        }

        static float getElevation(View view) {
            return view.getElevation();
        }

        static String getTransitionName(View view) {
            return view.getTransitionName();
        }

        static void setBackgroundTintList(View view, ColorStateList colorStateList) {
            view.setBackgroundTintList(colorStateList);
        }

        static void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
            view.setBackgroundTintMode(mode);
        }

        static void setElevation(View view, float f) {
            view.setElevation(f);
        }

        static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            if (onApplyWindowInsetsListener == null) {
                view.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) view.getTag(R.id.tag_window_insets_animation_callback));
            } else {
                view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(view, onApplyWindowInsetsListener) { // from class: androidx.core.view.ViewCompat.Api21Impl.1
                    final /* synthetic */ OnApplyWindowInsetsListener val$listener;

                    {
                        this.val$listener = onApplyWindowInsetsListener;
                    }

                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                        return this.val$listener.onApplyWindowInsets(view2, WindowInsetsCompat.toWindowInsetsCompat(windowInsets, view2)).toWindowInsets();
                    }
                });
            }
        }

        static void setTransitionName(View view, String str) {
            view.setTransitionName(str);
        }

        static void stopNestedScroll(View view) {
            view.stopNestedScroll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Api23Impl {
        public static WindowInsetsCompat getRootWindowInsets(View view) {
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            if (rootWindowInsets == null) {
                return null;
            }
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(rootWindowInsets, null);
            windowInsetsCompat.setRootWindowInsets(windowInsetsCompat);
            windowInsetsCompat.copyRootViewBounds(view.getRootView());
            return windowInsetsCompat;
        }

        static void setScrollIndicators(View view, int i, int i2) {
            view.setScrollIndicators(i, i2);
        }
    }

    static class Api26Impl {
        static int getImportantForAutofill(View view) {
            return view.getImportantForAutofill();
        }

        static void setImportantForAutofill(View view, int i) {
            view.setImportantForAutofill(i);
        }
    }

    static class Api28Impl {
        static CharSequence getAccessibilityPaneTitle(View view) {
            return view.getAccessibilityPaneTitle();
        }

        static boolean isAccessibilityHeading(View view) {
            return view.isAccessibilityHeading();
        }

        static boolean isScreenReaderFocusable(View view) {
            return view.isScreenReaderFocusable();
        }

        static void setAccessibilityHeading(View view, boolean z) {
            view.setAccessibilityHeading(z);
        }

        static void setAccessibilityPaneTitle(View view, CharSequence charSequence) {
            view.setAccessibilityPaneTitle(charSequence);
        }

        static void setScreenReaderFocusable(View view, boolean z) {
            view.setScreenReaderFocusable(z);
        }
    }

    private static class Api29Impl {
        static View.AccessibilityDelegate getAccessibilityDelegate(View view) {
            return view.getAccessibilityDelegate();
        }

        static void saveAttributeDataForStyleable(View view, Context context, int[] iArr, AttributeSet attributeSet, TypedArray typedArray, int i, int i2) {
            view.saveAttributeDataForStyleable(context, iArr, attributeSet, typedArray, i, i2);
        }
    }

    private static class Api30Impl {
        static CharSequence getStateDescription(View view) {
            return view.getStateDescription();
        }

        static void setStateDescription(View view, CharSequence charSequence) {
            view.setStateDescription(charSequence);
        }
    }

    static {
        new AtomicInteger(1);
        sViewPropertyAnimatorMap = null;
        NO_OP_ON_RECEIVE_CONTENT_VIEW_BEHAVIOR = new ViewCompat$$ExternalSyntheticLambda0();
        sAccessibilityPaneVisibilityManager = new AccessibilityPaneVisibilityManager();
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        if (sViewPropertyAnimatorMap == null) {
            sViewPropertyAnimatorMap = new WeakHashMap<>();
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = sViewPropertyAnimatorMap.get(view);
        if (viewPropertyAnimatorCompat != null) {
            return viewPropertyAnimatorCompat;
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view);
        sViewPropertyAnimatorMap.put(view, viewPropertyAnimatorCompat2);
        return viewPropertyAnimatorCompat2;
    }

    public static void computeSystemWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, Rect rect) {
        Api21Impl.computeSystemWindowInsets(view, windowInsetsCompat, rect);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
        if (windowInsets != null) {
            WindowInsets dispatchApplyWindowInsets = Api20Impl.dispatchApplyWindowInsets(view, windowInsets);
            if (!dispatchApplyWindowInsets.equals(windowInsets)) {
                return WindowInsetsCompat.toWindowInsetsCompat(dispatchApplyWindowInsets, view);
            }
        }
        return windowInsetsCompat;
    }

    public static AccessibilityDelegateCompat getAccessibilityDelegate(View view) {
        View.AccessibilityDelegate accessibilityDelegate = Api29Impl.getAccessibilityDelegate(view);
        if (accessibilityDelegate == null) {
            return null;
        }
        return accessibilityDelegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter ? ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter) accessibilityDelegate).mCompat : new AccessibilityDelegateCompat(accessibilityDelegate);
    }

    public static CharSequence getAccessibilityPaneTitle(View view) {
        return new C01022(CharSequence.class).get(view);
    }

    public static ColorStateList getBackgroundTintList(View view) {
        return Api21Impl.getBackgroundTintList(view);
    }

    public static PorterDuff.Mode getBackgroundTintMode(View view) {
        return Api21Impl.getBackgroundTintMode(view);
    }

    public static Display getDisplay(View view) {
        return Api17Impl.getDisplay(view);
    }

    public static float getElevation(View view) {
        return Api21Impl.getElevation(view);
    }

    public static boolean getFitsSystemWindows(View view) {
        return Api16Impl.getFitsSystemWindows(view);
    }

    public static int getImportantForAccessibility(View view) {
        return Api16Impl.getImportantForAccessibility(view);
    }

    @SuppressLint({"InlinedApi"})
    public static int getImportantForAutofill(View view) {
        return Api26Impl.getImportantForAutofill(view);
    }

    public static int getLayoutDirection(View view) {
        return Api17Impl.getLayoutDirection(view);
    }

    public static int getMinimumHeight(View view) {
        return Api16Impl.getMinimumHeight(view);
    }

    public static int getMinimumWidth(View view) {
        return Api16Impl.getMinimumWidth(view);
    }

    public static WindowInsetsCompat getRootWindowInsets(View view) {
        return Api23Impl.getRootWindowInsets(view);
    }

    public static CharSequence getStateDescription(View view) {
        return new C01033(CharSequence.class).get(view);
    }

    public static String getTransitionName(View view) {
        return Api21Impl.getTransitionName(view);
    }

    @Deprecated
    public static int getWindowSystemUiVisibility(View view) {
        return Api16Impl.getWindowSystemUiVisibility(view);
    }

    public static boolean hasOnClickListeners(View view) {
        return Api15Impl.hasOnClickListeners(view);
    }

    public static boolean hasTransientState() {
        return Api16Impl.hasTransientState(null);
    }

    public static boolean isAccessibilityHeading(View view) {
        Boolean bool = new AccessibilityViewProperty<Boolean>() { // from class: androidx.core.view.ViewCompat.4
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            final Boolean frameworkGet(View view2) {
                return Boolean.valueOf(Api28Impl.isAccessibilityHeading(view2));
            }

            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            final void frameworkSet(View view2, Boolean bool2) {
                Api28Impl.setAccessibilityHeading(view2, bool2.booleanValue());
            }

            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            final boolean shouldUpdate(Boolean bool2, Boolean bool3) {
                return !AccessibilityViewProperty.booleanNullToFalseEquals(bool2, bool3);
            }
        }.get(view);
        return bool != null && bool.booleanValue();
    }

    public static boolean isAttachedToWindow(View view) {
        return Api19Impl.isAttachedToWindow(view);
    }

    public static boolean isLaidOut(View view) {
        return Api19Impl.isLaidOut(view);
    }

    public static boolean isScreenReaderFocusable(View view) {
        Boolean bool = new AccessibilityViewProperty<Boolean>() { // from class: androidx.core.view.ViewCompat.1
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            final Boolean frameworkGet(View view2) {
                return Boolean.valueOf(Api28Impl.isScreenReaderFocusable(view2));
            }

            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            final void frameworkSet(View view2, Boolean bool2) {
                Api28Impl.setScreenReaderFocusable(view2, bool2.booleanValue());
            }

            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            final boolean shouldUpdate(Boolean bool2, Boolean bool3) {
                return !AccessibilityViewProperty.booleanNullToFalseEquals(bool2, bool3);
            }
        }.get(view);
        return bool != null && bool.booleanValue();
    }

    static void notifyViewAccessibilityStateChangedIfNeeded(View view, int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            boolean z = getAccessibilityPaneTitle(view) != null && view.isShown() && view.getWindowVisibility() == 0;
            if (Api19Impl.getAccessibilityLiveRegion(view) != 0 || z) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain();
                obtain.setEventType(z ? 32 : 2048);
                Api19Impl.setContentChangeTypes(obtain, i);
                if (z) {
                    obtain.getText().add(getAccessibilityPaneTitle(view));
                    if (Api16Impl.getImportantForAccessibility(view) == 0) {
                        Api16Impl.setImportantForAccessibility(view, 1);
                    }
                    ViewParent parent = view.getParent();
                    while (true) {
                        if (!(parent instanceof View)) {
                            break;
                        }
                        if (Api16Impl.getImportantForAccessibility((View) parent) == 4) {
                            Api16Impl.setImportantForAccessibility(view, 2);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
                view.sendAccessibilityEventUnchecked(obtain);
                return;
            }
            if (i != 32) {
                if (view.getParent() != null) {
                    try {
                        Api19Impl.notifySubtreeAccessibilityStateChanged(view.getParent(), view, view, i);
                        return;
                    } catch (AbstractMethodError e) {
                        Log.e("ViewCompat", view.getParent().getClass().getSimpleName().concat(" does not fully implement ViewParent"), e);
                        return;
                    }
                }
                return;
            }
            AccessibilityEvent obtain2 = AccessibilityEvent.obtain();
            view.onInitializeAccessibilityEvent(obtain2);
            obtain2.setEventType(32);
            Api19Impl.setContentChangeTypes(obtain2, i);
            obtain2.setSource(view);
            view.onPopulateAccessibilityEvent(obtain2);
            obtain2.getText().add(getAccessibilityPaneTitle(view));
            accessibilityManager.sendAccessibilityEvent(obtain2);
        }
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
        if (windowInsets != null) {
            WindowInsets onApplyWindowInsets = Api20Impl.onApplyWindowInsets(view, windowInsets);
            if (!onApplyWindowInsets.equals(windowInsets)) {
                return WindowInsetsCompat.toWindowInsetsCompat(onApplyWindowInsets, view);
            }
        }
        return windowInsetsCompat;
    }

    public static void postInvalidateOnAnimation(View view) {
        Api16Impl.postInvalidateOnAnimation(view);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        Api16Impl.postOnAnimation(view, runnable);
    }

    @SuppressLint({"LambdaLast"})
    public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
        Api16Impl.postOnAnimationDelayed(view, runnable, j);
    }

    public static void removeAccessibilityAction(View view, int i) {
        removeActionWithId(view, i);
        notifyViewAccessibilityStateChangedIfNeeded(view, 0);
    }

    private static void removeActionWithId(View view, int i) {
        ArrayList arrayList = (ArrayList) view.getTag(R.id.tag_accessibility_actions);
        if (arrayList == null) {
            arrayList = new ArrayList();
            view.setTag(R.id.tag_accessibility_actions, arrayList);
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat) arrayList.get(i2)).getId() == i) {
                arrayList.remove(i2);
                return;
            }
        }
    }

    public static void replaceAccessibilityAction(View view, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, DrawerLayout$$ExternalSyntheticLambda1 drawerLayout$$ExternalSyntheticLambda1) {
        if (drawerLayout$$ExternalSyntheticLambda1 == null) {
            removeActionWithId(view, accessibilityActionCompat.getId());
            notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            return;
        }
        AccessibilityNodeInfoCompat.AccessibilityActionCompat createReplacementAction = accessibilityActionCompat.createReplacementAction(drawerLayout$$ExternalSyntheticLambda1);
        AccessibilityDelegateCompat accessibilityDelegate = getAccessibilityDelegate(view);
        if (accessibilityDelegate == null) {
            accessibilityDelegate = new AccessibilityDelegateCompat();
        }
        setAccessibilityDelegate(view, accessibilityDelegate);
        removeActionWithId(view, createReplacementAction.getId());
        ArrayList arrayList = (ArrayList) view.getTag(R.id.tag_accessibility_actions);
        if (arrayList == null) {
            arrayList = new ArrayList();
            view.setTag(R.id.tag_accessibility_actions, arrayList);
        }
        arrayList.add(createReplacementAction);
        notifyViewAccessibilityStateChangedIfNeeded(view, 0);
    }

    public static void requestApplyInsets(View view) {
        Api20Impl.requestApplyInsets(view);
    }

    public static void saveAttributeDataForStyleable(View view, @SuppressLint({"ContextFirst"}) Context context, int[] iArr, AttributeSet attributeSet, TypedArray typedArray, int i, int i2) {
        Api29Impl.saveAttributeDataForStyleable(view, context, iArr, attributeSet, typedArray, i, i2);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        if (accessibilityDelegateCompat == null && (Api29Impl.getAccessibilityDelegate(view) instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter)) {
            accessibilityDelegateCompat = new AccessibilityDelegateCompat();
        }
        view.setAccessibilityDelegate(accessibilityDelegateCompat == null ? null : accessibilityDelegateCompat.getBridge());
    }

    public static void setAccessibilityPaneTitle(View view, CharSequence charSequence) {
        new C01022(CharSequence.class).set(view, charSequence);
        AccessibilityPaneVisibilityManager accessibilityPaneVisibilityManager = sAccessibilityPaneVisibilityManager;
        if (charSequence != null) {
            accessibilityPaneVisibilityManager.addAccessibilityPane(view);
        } else {
            accessibilityPaneVisibilityManager.removeAccessibilityPane(view);
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        Api16Impl.setBackground(view, drawable);
    }

    public static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        Api21Impl.setBackgroundTintList(view, colorStateList);
    }

    public static void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
        Api21Impl.setBackgroundTintMode(view, mode);
    }

    public static void setElevation(View view, float f) {
        Api21Impl.setElevation(view, f);
    }

    public static void setImportantForAccessibility(View view, int i) {
        Api16Impl.setImportantForAccessibility(view, i);
    }

    public static void setImportantForAutofill(View view) {
        Api26Impl.setImportantForAutofill(view, 8);
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        Api21Impl.setOnApplyWindowInsetsListener(view, onApplyWindowInsetsListener);
    }

    public static void setScrollIndicators(View view, int i) {
        Api23Impl.setScrollIndicators(view, i, 3);
    }

    public static void setStateDescription(View view, CharSequence charSequence) {
        new C01033(CharSequence.class).set(view, charSequence);
    }

    public static void setTransitionName(View view, String str) {
        Api21Impl.setTransitionName(view, str);
    }

    static class AccessibilityPaneVisibilityManager implements ViewTreeObserver.OnGlobalLayoutListener, View.OnAttachStateChangeListener {
        private final WeakHashMap<View, Boolean> mPanesToVisible = new WeakHashMap<>();

        AccessibilityPaneVisibilityManager() {
        }

        final void addAccessibilityPane(View view) {
            this.mPanesToVisible.put(view, Boolean.valueOf(view.isShown() && view.getWindowVisibility() == 0));
            view.addOnAttachStateChangeListener(this);
            if (Api19Impl.isAttachedToWindow(view)) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(this);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        final void removeAccessibilityPane(View view) {
            this.mPanesToVisible.remove(view);
            view.removeOnAttachStateChangeListener(this);
            Api16Impl.removeOnGlobalLayoutListener(view.getViewTreeObserver(), this);
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
        }
    }
}
