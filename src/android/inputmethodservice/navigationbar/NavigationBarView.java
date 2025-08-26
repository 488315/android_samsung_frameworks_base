package android.inputmethodservice.navigationbar;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.android.internal.R;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class NavigationBarView extends FrameLayout {
    private static final boolean DEBUG = false;
    private static final Interpolator FAST_OUT_SLOW_IN = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final String TAG = "NavBarView";
    private KeyButtonDrawable mBackIcon;
    private final SparseArray<ButtonDispatcher> mButtonDispatchers;
    private Configuration mConfiguration;
    private int mCurrentRotation;
    View mCurrentView;
    private final int mDarkIconColor;
    private final DeadZone mDeadZone;
    private boolean mDeadZoneConsuming;
    int mDisabledFlags;
    private View mHorizontal;
    private KeyButtonDrawable mImeSwitcherIcon;
    private Context mLightContext;
    private final int mLightIconColor;
    private final int mNavBarMode;
    private int mNavbarFlags;
    private NavigationBarInflaterView mNavigationInflaterView;
    private Configuration mTmpLastConfiguration;

    public interface ButtonClickListener {
        void onImeSwitchButtonClick(View view);

        boolean onImeSwitchButtonLongClick(View view);
    }

    public static boolean isGesturalMode(int i) {
        return i == 2;
    }

    public NavigationBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentView = null;
        this.mCurrentRotation = -1;
        this.mDisabledFlags = 0;
        this.mNavBarMode = 2;
        this.mDeadZoneConsuming = false;
        SparseArray<ButtonDispatcher> sparseArray = new SparseArray<>();
        this.mButtonDispatchers = sparseArray;
        this.mLightContext = context;
        this.mLightIconColor = -1;
        this.mDarkIconColor = -1728053248;
        this.mConfiguration = new Configuration();
        this.mTmpLastConfiguration = new Configuration();
        this.mConfiguration.updateFrom(context.getResources().getConfiguration());
        sparseArray.put(R.id.input_method_nav_back, new ButtonDispatcher(R.id.input_method_nav_back));
        sparseArray.put(R.id.input_method_nav_ime_switcher, new ButtonDispatcher(R.id.input_method_nav_ime_switcher));
        sparseArray.put(R.id.input_method_nav_home_handle, new ButtonDispatcher(R.id.input_method_nav_home_handle));
        this.mDeadZone = new DeadZone(this);
    }

    public void prepareNavButtons(final ButtonClickListener buttonClickListener) {
        getBackButton().setLongClickable(false);
        if (Flags.imeSwitcherRevamp()) {
            ButtonDispatcher imeSwitchButton = getImeSwitchButton();
            imeSwitchButton.setLongClickable(true);
            Objects.requireNonNull(buttonClickListener);
            imeSwitchButton.setOnClickListener(new View.OnClickListener() { // from class: android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    buttonClickListener.onImeSwitchButtonClick(view);
                }
            });
            Objects.requireNonNull(buttonClickListener);
            imeSwitchButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return buttonClickListener.onImeSwitchButtonLongClick(view);
                }
            });
            return;
        }
        ButtonDispatcher imeSwitchButton2 = getImeSwitchButton();
        imeSwitchButton2.setLongClickable(false);
        imeSwitchButton2.setOnClickListener(new View.OnClickListener() { // from class: android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((InputMethodManager) view.getContext().getSystemService(InputMethodManager.class)).showInputMethodPicker();
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return shouldDeadZoneConsumeTouchEvents(motionEvent) || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        shouldDeadZoneConsumeTouchEvents(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    private boolean shouldDeadZoneConsumeTouchEvents(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDeadZoneConsuming = false;
        }
        if (!this.mDeadZone.onTouchEvent(motionEvent) && !this.mDeadZoneConsuming) {
            return false;
        }
        if (actionMasked == 0) {
            this.mDeadZoneConsuming = true;
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.mDeadZoneConsuming = false;
        }
        return true;
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public void forEachView(Consumer<View> consumer) {
        View view = this.mHorizontal;
        if (view != null) {
            consumer.accept(view);
        }
    }

    public ButtonDispatcher getBackButton() {
        return this.mButtonDispatchers.get(R.id.input_method_nav_back);
    }

    public ButtonDispatcher getImeSwitchButton() {
        return this.mButtonDispatchers.get(R.id.input_method_nav_ime_switcher);
    }

    public ButtonDispatcher getHomeHandle() {
        return this.mButtonDispatchers.get(R.id.input_method_nav_home_handle);
    }

    public SparseArray<ButtonDispatcher> getButtonDispatchers() {
        return this.mButtonDispatchers;
    }

    private void reloadNavIcons() {
        updateIcons(Configuration.EMPTY);
    }

    private void updateIcons(Configuration configuration) {
        boolean z = configuration.orientation != this.mConfiguration.orientation;
        boolean z2 = configuration.densityDpi != this.mConfiguration.densityDpi;
        boolean z3 = configuration.getLayoutDirection() != this.mConfiguration.getLayoutDirection();
        if (z2 || z3) {
            this.mImeSwitcherIcon = getDrawable(Flags.imeSwitcherRevamp() ? R.drawable.ic_ime_switcher_new : R.drawable.ic_ime_switcher);
        }
        if (z || z2 || z3) {
            this.mBackIcon = getBackDrawable();
        }
    }

    private KeyButtonDrawable getBackDrawable() {
        KeyButtonDrawable drawable = getDrawable(R.drawable.ic_ime_nav_back);
        orientBackButton(drawable);
        return drawable;
    }

    private void orientBackButton(KeyButtonDrawable keyButtonDrawable) {
        float f;
        boolean z = (this.mNavbarFlags & 1) != 0;
        boolean z2 = this.mConfiguration.getLayoutDirection() == 1;
        if (z) {
            f = z2 ? 90 : -90;
        } else {
            f = 0.0f;
        }
        if (keyButtonDrawable.getRotation() == f) {
            return;
        }
        if (isGesturalMode(2)) {
            keyButtonDrawable.setRotation(f);
            return;
        }
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(keyButtonDrawable, PropertyValuesHolder.ofFloat(KeyButtonDrawable.KEY_DRAWABLE_ROTATE, f), PropertyValuesHolder.ofFloat(KeyButtonDrawable.KEY_DRAWABLE_TRANSLATE_Y, z ? -NavigationBarUtils.dpToPx(2.0f, getResources()) : 0.0f));
        objectAnimatorOfPropertyValuesHolder.setInterpolator(FAST_OUT_SLOW_IN);
        objectAnimatorOfPropertyValuesHolder.setDuration(200L);
        objectAnimatorOfPropertyValuesHolder.start();
    }

    private KeyButtonDrawable getDrawable(int i) {
        return KeyButtonDrawable.create(this.mLightContext, this.mLightIconColor, this.mDarkIconColor, i, true, null);
    }

    @Override // android.view.View
    public void setLayoutDirection(int i) {
        reloadNavIcons();
        super.setLayoutDirection(i);
    }

    public void setNavbarFlags(int i) {
        if (i == this.mNavbarFlags) {
            return;
        }
        this.mNavbarFlags = i;
        updateNavButtonIcons();
    }

    private void updateNavButtonIcons() {
        KeyButtonDrawable keyButtonDrawable = this.mBackIcon;
        orientBackButton(keyButtonDrawable);
        getBackButton().setImageDrawable(keyButtonDrawable);
        getImeSwitchButton().setImageDrawable(this.mImeSwitcherIcon);
        getImeSwitchButton().setVisibility((this.mNavbarFlags & 4) != 0 ? 0 : 4);
        getBackButton().setVisibility(0);
        getHomeHandle().setVisibility(4);
    }

    private Display getContextDisplay() {
        return getContext().getDisplay();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        NavigationBarInflaterView navigationBarInflaterView = (NavigationBarInflaterView) findViewById(R.id.input_method_nav_inflater);
        this.mNavigationInflaterView = navigationBarInflaterView;
        navigationBarInflaterView.setButtonDispatchers(this.mButtonDispatchers);
        updateOrientationViews();
        reloadNavIcons();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.mDeadZone.onDraw(canvas);
        super.onDraw(canvas);
    }

    private void updateOrientationViews() {
        this.mHorizontal = findViewById(R.id.input_method_nav_horizontal);
        updateCurrentView();
    }

    private void updateCurrentView() {
        resetViews();
        View view = this.mHorizontal;
        this.mCurrentView = view;
        view.setVisibility(0);
        int rotation = getContextDisplay().getRotation();
        this.mCurrentRotation = rotation;
        this.mNavigationInflaterView.setAlternativeOrder(rotation == 1);
        this.mNavigationInflaterView.updateButtonDispatchersCurrentView();
    }

    private void resetViews() {
        this.mHorizontal.setVisibility(8);
    }

    private void reorient() {
        updateCurrentView();
        ((NavigationBarFrame) getRootView().findViewByPredicate(new Predicate() { // from class: android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NavigationBarView.lambda$reorient$1((View) obj);
            }
        })).setDeadZone(this.mDeadZone);
        this.mDeadZone.onConfigurationChanged(this.mCurrentRotation);
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
        }
        updateNavButtonIcons();
    }

    static /* synthetic */ boolean lambda$reorient$1(View view) {
        return view instanceof NavigationBarFrame;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mTmpLastConfiguration.updateFrom(this.mConfiguration);
        this.mConfiguration.updateFrom(configuration);
        updateIcons(this.mTmpLastConfiguration);
        if (this.mTmpLastConfiguration.densityDpi == this.mConfiguration.densityDpi && this.mTmpLastConfiguration.getLayoutDirection() == this.mConfiguration.getLayoutDirection()) {
            return;
        }
        updateNavButtonIcons();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestApplyInsets();
        reorient();
        updateNavButtonIcons();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            this.mButtonDispatchers.valueAt(i).onDestroy();
        }
    }

    public void setDarkIntensity(float f) {
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            this.mButtonDispatchers.valueAt(i).setDarkIntensity(f);
        }
    }
}
