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
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.android.internal.R;

import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class NavigationBarView extends FrameLayout {
    private static final boolean DEBUG = false;
    private static final Interpolator FAST_OUT_SLOW_IN =
            new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
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
    int mNavigationIconHints;
    private NavigationBarInflaterView mNavigationInflaterView;
    private Configuration mTmpLastConfiguration;

    public NavigationBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCurrentView = null;
        this.mCurrentRotation = -1;
        this.mDisabledFlags = 0;
        this.mNavigationIconHints = 1;
        this.mNavBarMode = 2;
        this.mDeadZoneConsuming = false;
        this.mButtonDispatchers = new SparseArray<>();
        this.mLightContext = context;
        this.mLightIconColor = -1;
        this.mDarkIconColor = -1728053248;
        this.mConfiguration = new Configuration();
        this.mTmpLastConfiguration = new Configuration();
        this.mConfiguration.updateFrom(context.getResources().getConfiguration());
        this.mButtonDispatchers.put(
                R.id.input_method_nav_back, new ButtonDispatcher(R.id.input_method_nav_back));
        this.mButtonDispatchers.put(
                R.id.input_method_nav_ime_switcher,
                new ButtonDispatcher(R.id.input_method_nav_ime_switcher));
        this.mButtonDispatchers.put(
                R.id.input_method_nav_home_handle,
                new ButtonDispatcher(R.id.input_method_nav_home_handle));
        this.mDeadZone = new DeadZone(this);
        getBackButton().setLongClickable(false);
        ButtonDispatcher imeSwitchButton = getImeSwitchButton();
        imeSwitchButton.setLongClickable(false);
        imeSwitchButton.setOnClickListener(
                new View.OnClickListener() { // from class:
                    // android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ((InputMethodManager)
                                        view.getContext()
                                                .getSystemService(InputMethodManager.class))
                                .showInputMethodPicker();
                    }
                });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return shouldDeadZoneConsumeTouchEvents(event) || super.onInterceptTouchEvent(event);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        shouldDeadZoneConsumeTouchEvents(event);
        return super.onTouchEvent(event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean shouldDeadZoneConsumeTouchEvents(android.view.MotionEvent r4) {
        /*
            r3 = this;
            int r0 = r4.getActionMasked()
            r1 = 0
            if (r0 != 0) goto L9
            r3.mDeadZoneConsuming = r1
        L9:
            android.inputmethodservice.navigationbar.DeadZone r2 = r3.mDeadZone
            boolean r2 = r2.onTouchEvent(r4)
            if (r2 != 0) goto L17
            boolean r2 = r3.mDeadZoneConsuming
            if (r2 == 0) goto L16
            goto L17
        L16:
            return r1
        L17:
            r2 = 1
            switch(r0) {
                case 0: goto L1f;
                case 1: goto L1c;
                case 2: goto L1b;
                case 3: goto L1c;
                default: goto L1b;
            }
        L1b:
            goto L22
        L1c:
            r3.mDeadZoneConsuming = r1
            goto L22
        L1f:
            r3.mDeadZoneConsuming = r2
        L22:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.inputmethodservice.navigationbar.NavigationBarView.shouldDeadZoneConsumeTouchEvents(android.view.MotionEvent):boolean");
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public void forEachView(Consumer<View> consumer) {
        if (this.mHorizontal != null) {
            consumer.accept(this.mHorizontal);
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

    private void updateIcons(Configuration oldConfig) {
        boolean orientationChange = oldConfig.orientation != this.mConfiguration.orientation;
        boolean densityChange = oldConfig.densityDpi != this.mConfiguration.densityDpi;
        boolean dirChange =
                oldConfig.getLayoutDirection() != this.mConfiguration.getLayoutDirection();
        if (densityChange || dirChange) {
            this.mImeSwitcherIcon = getDrawable(R.drawable.ic_ime_switcher);
        }
        if (orientationChange || densityChange || dirChange) {
            this.mBackIcon = getBackDrawable();
        }
    }

    private KeyButtonDrawable getBackDrawable() {
        KeyButtonDrawable drawable = getDrawable(R.drawable.ic_ime_nav_back);
        orientBackButton(drawable);
        return drawable;
    }

    public static boolean isGesturalMode(int mode) {
        return mode == 2;
    }

    private void orientBackButton(KeyButtonDrawable drawable) {
        float degrees;
        boolean useAltBack = (this.mNavigationIconHints & 1) != 0;
        boolean isRtl = this.mConfiguration.getLayoutDirection() == 1;
        float targetY = 0.0f;
        if (useAltBack) {
            degrees = isRtl ? 90 : -90;
        } else {
            degrees = 0.0f;
        }
        if (drawable.getRotation() == degrees) {
            return;
        }
        if (isGesturalMode(2)) {
            drawable.setRotation(degrees);
            return;
        }
        if (useAltBack) {
            targetY = -NavigationBarUtils.dpToPx(2.0f, getResources());
        }
        ObjectAnimator navBarAnimator =
                ObjectAnimator.ofPropertyValuesHolder(
                        drawable,
                        PropertyValuesHolder.ofFloat(
                                KeyButtonDrawable.KEY_DRAWABLE_ROTATE, degrees),
                        PropertyValuesHolder.ofFloat(
                                KeyButtonDrawable.KEY_DRAWABLE_TRANSLATE_Y, targetY));
        navBarAnimator.setInterpolator(FAST_OUT_SLOW_IN);
        navBarAnimator.setDuration(200L);
        navBarAnimator.start();
    }

    private KeyButtonDrawable getDrawable(int icon) {
        return KeyButtonDrawable.create(
                this.mLightContext, this.mLightIconColor, this.mDarkIconColor, icon, true, null);
    }

    @Override // android.view.View
    public void setLayoutDirection(int layoutDirection) {
        reloadNavIcons();
        super.setLayoutDirection(layoutDirection);
    }

    public void setNavigationIconHints(int hints) {
        if (hints == this.mNavigationIconHints) {
            return;
        }
        if ((hints & 1) != 0) {}
        if ((this.mNavigationIconHints & 1) != 0) {}
        this.mNavigationIconHints = hints;
        updateNavButtonIcons();
    }

    private void updateNavButtonIcons() {
        KeyButtonDrawable backIcon = this.mBackIcon;
        orientBackButton(backIcon);
        getBackButton().setImageDrawable(backIcon);
        getImeSwitchButton().setImageDrawable(this.mImeSwitcherIcon);
        boolean imeSwitcherVisible = (this.mNavigationIconHints & 4) != 0;
        getImeSwitchButton().setVisibility(imeSwitcherVisible ? 0 : 4);
        getBackButton().setVisibility(0);
        getHomeHandle().setVisibility(4);
    }

    private Display getContextDisplay() {
        return getContext().getDisplay();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mNavigationInflaterView =
                (NavigationBarInflaterView) findViewById(R.id.input_method_nav_inflater);
        this.mNavigationInflaterView.setButtonDispatchers(this.mButtonDispatchers);
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
        this.mCurrentView = this.mHorizontal;
        this.mCurrentView.setVisibility(0);
        this.mCurrentRotation = getContextDisplay().getRotation();
        this.mNavigationInflaterView.setAlternativeOrder(this.mCurrentRotation == 1);
        this.mNavigationInflaterView.updateButtonDispatchersCurrentView();
    }

    private void resetViews() {
        this.mHorizontal.setVisibility(8);
    }

    private void reorient() {
        updateCurrentView();
        NavigationBarFrame frame =
                (NavigationBarFrame)
                        getRootView()
                                .findViewByPredicate(
                                        new Predicate() { // from class:
                                            // android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return NavigationBarView.lambda$reorient$1(
                                                        (View) obj);
                                            }
                                        });
        frame.setDeadZone(this.mDeadZone);
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
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mTmpLastConfiguration.updateFrom(this.mConfiguration);
        this.mConfiguration.updateFrom(newConfig);
        updateIcons(this.mTmpLastConfiguration);
        if (this.mTmpLastConfiguration.densityDpi != this.mConfiguration.densityDpi
                || this.mTmpLastConfiguration.getLayoutDirection()
                        != this.mConfiguration.getLayoutDirection()) {
            updateNavButtonIcons();
        }
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

    public void setDarkIntensity(float intensity) {
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            this.mButtonDispatchers.valueAt(i).setDarkIntensity(intensity);
        }
    }
}
