package com.android.systemui.navigationbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.buttons.ReverseLinearLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.system.QuickStepContract;
import com.sec.ims.settings.ImsProfile;
import java.lang.ref.WeakReference;

public class NavigationBarInflaterView extends FrameLayout {
    public boolean mAlternativeOrder;
    SparseArray<ButtonDispatcher> mButtonDispatchers;
    public String mCurrentLayout;
    public FrameLayout mHorizontal;
    public boolean mIsVertical;
    public LayoutInflater mLandscapeInflater;
    public View mLastLandscape;
    public View mLastPortrait;
    public LayoutInflater mLayoutInflater;
    public final Listener mListener;
    public int mNavBarMode;
    public final OverviewProxyService mOverviewProxyService;
    public FrameLayout mVertical;

    public final class Listener implements NavigationModeController.ModeChangedListener {
        public final WeakReference mSelf;

        public Listener(NavigationBarInflaterView navigationBarInflaterView) {
            this.mSelf = new WeakReference(navigationBarInflaterView);
        }

        @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
        public final void onNavigationModeChanged(int i) {
            NavigationBarInflaterView navigationBarInflaterView = (NavigationBarInflaterView) this.mSelf.get();
            if (navigationBarInflaterView != null) {
                navigationBarInflaterView.mNavBarMode = i;
            }
        }
    }

    public NavigationBarInflaterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNavBarMode = 0;
        createInflaters();
        this.mOverviewProxyService = (OverviewProxyService) Dependency.sDependency.getDependencyInner(OverviewProxyService.class);
        Listener listener = new Listener(this);
        this.mListener = listener;
        this.mNavBarMode = ((NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class)).addListener(listener);
    }

    public static void addAll(ButtonDispatcher buttonDispatcher, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i).getId() == buttonDispatcher.mId) {
                buttonDispatcher.addView(viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                addAll(buttonDispatcher, (ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

    public static String extractButton(String str) {
        return !str.contains("[") ? str : str.substring(0, str.indexOf("["));
    }

    public static String extractImage(String str) {
        if (str.contains(":")) {
            return str.substring(str.indexOf(":") + 1, str.indexOf(")"));
        }
        return null;
    }

    public static int extractKeycode(String str) {
        if (str.contains("(")) {
            return Integer.parseInt(str.substring(str.indexOf("(") + 1, str.indexOf(":")));
        }
        return 1;
    }

    public final void addGravitySpacer(LinearLayout linearLayout) {
        linearLayout.addView(new Space(((FrameLayout) this).mContext), new LinearLayout.LayoutParams(0, 0, 1.0f));
    }

    public final void addToDispatchers(View view) {
        SparseArray<ButtonDispatcher> sparseArray = this.mButtonDispatchers;
        if (sparseArray != null) {
            int indexOfKey = sparseArray.indexOfKey(view.getId());
            if (indexOfKey >= 0) {
                this.mButtonDispatchers.valueAt(indexOfKey).addView(view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addToDispatchers(viewGroup.getChildAt(i));
                }
            }
        }
    }

    public final View applySize(View view, String str, boolean z, boolean z2) {
        String substring = !str.contains("[") ? null : str.substring(str.indexOf("[") + 1, str.indexOf("]"));
        if (substring == null) {
            return view;
        }
        if (!substring.contains("W") && !substring.contains(ImsProfile.TIMER_NAME_A)) {
            float parseFloat = Float.parseFloat(substring);
            view.getLayoutParams().width = (int) (r7.width * parseFloat);
            return view;
        }
        ReverseLinearLayout.ReverseRelativeLayout reverseRelativeLayout = new ReverseLinearLayout.ReverseRelativeLayout(((FrameLayout) this).mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getLayoutParams());
        int i = z ? z2 ? 48 : 80 : z2 ? 8388611 : 8388613;
        if (substring.endsWith("WC")) {
            i = 17;
        } else if (substring.endsWith(ImsProfile.TIMER_NAME_C)) {
            i = 16;
        }
        reverseRelativeLayout.mDefaultGravity = i;
        reverseRelativeLayout.setGravity(i);
        reverseRelativeLayout.addView(view, layoutParams);
        if (substring.contains("W")) {
            reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -1, Float.parseFloat(substring.substring(0, substring.indexOf("W")))));
        } else {
            reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams((int) (Float.parseFloat(substring.substring(0, substring.indexOf(ImsProfile.TIMER_NAME_A))) * ((FrameLayout) this).mContext.getResources().getDisplayMetrics().density), -1));
        }
        reverseRelativeLayout.setClipChildren(false);
        reverseRelativeLayout.setClipToPadding(false);
        return reverseRelativeLayout;
    }

    public final void clearDispatcherViews() {
        if (this.mButtonDispatchers != null) {
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).mViews.clear();
            }
        }
    }

    public final void clearViews() {
        clearDispatcherViews();
        ViewGroup viewGroup = (ViewGroup) this.mHorizontal.findViewById(R.id.nav_buttons);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ((ViewGroup) viewGroup.getChildAt(i)).removeAllViews();
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mVertical.findViewById(R.id.nav_buttons);
        for (int i2 = 0; i2 < viewGroup2.getChildCount(); i2++) {
            ((ViewGroup) viewGroup2.getChildAt(i2)).removeAllViews();
        }
    }

    public void createInflaters() {
        this.mLayoutInflater = LayoutInflater.from(((FrameLayout) this).mContext);
        Configuration configuration = new Configuration();
        configuration.setTo(((FrameLayout) this).mContext.getResources().getConfiguration());
        configuration.orientation = 2;
        this.mLandscapeInflater = LayoutInflater.from(((FrameLayout) this).mContext.createConfigurationContext(configuration));
    }

    public View createView(String str, ViewGroup viewGroup, LayoutInflater layoutInflater) {
        String extractButton = extractButton(str);
        if ("left".equals(extractButton)) {
            extractButton = extractButton("space");
        } else if ("right".equals(extractButton)) {
            extractButton = extractButton("menu_ime");
        }
        if (BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN.equals(extractButton)) {
            return layoutInflater.inflate(R.layout.home, viewGroup, false);
        }
        if ("back".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.back, viewGroup, false);
        }
        if ("recent".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.recent_apps, viewGroup, false);
        }
        if ("menu_ime".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.menu_ime, viewGroup, false);
        }
        if ("space".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.nav_key_space, viewGroup, false);
        }
        if ("clipboard".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.clipboard, viewGroup, false);
        }
        if ("contextual".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.contextual, viewGroup, false);
        }
        if ("home_handle".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.home_handle, viewGroup, false);
        }
        if ("ime_switcher".equals(extractButton)) {
            return layoutInflater.inflate(R.layout.ime_switcher, viewGroup, false);
        }
        if (!extractButton.startsWith("key")) {
            return null;
        }
        String extractImage = extractImage(extractButton);
        int extractKeycode = extractKeycode(extractButton);
        View inflate = layoutInflater.inflate(R.layout.custom_key, viewGroup, false);
        final KeyButtonView keyButtonView = (KeyButtonView) inflate;
        keyButtonView.mCode = extractKeycode;
        if (extractImage != null) {
            if (extractImage.contains(":")) {
                new AsyncTask() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonView.2
                    public AnonymousClass2() {
                    }

                    @Override // android.os.AsyncTask
                    public final Object doInBackground(Object[] objArr) {
                        return ((Icon[]) objArr)[0].loadDrawable(((ImageView) KeyButtonView.this).mContext);
                    }

                    @Override // android.os.AsyncTask
                    public final void onPostExecute(Object obj) {
                        KeyButtonView.this.setImageDrawable((Drawable) obj);
                    }
                }.execute(Icon.createWithContentUri(extractImage));
            } else if (extractImage.contains("/")) {
                int indexOf = extractImage.indexOf(47);
                new AsyncTask() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonView.2
                    public AnonymousClass2() {
                    }

                    @Override // android.os.AsyncTask
                    public final Object doInBackground(Object[] objArr) {
                        return ((Icon[]) objArr)[0].loadDrawable(((ImageView) KeyButtonView.this).mContext);
                    }

                    @Override // android.os.AsyncTask
                    public final void onPostExecute(Object obj) {
                        KeyButtonView.this.setImageDrawable((Drawable) obj);
                    }
                }.execute(Icon.createWithResource(extractImage.substring(0, indexOf), Integer.parseInt(extractImage.substring(indexOf + 1))));
            }
        }
        return inflate;
    }

    public String getDefaultLayout() {
        return getContext().getString(QuickStepContract.isGesturalMode(this.mNavBarMode) ? R.string.config_navBarLayoutHandle : this.mOverviewProxyService.shouldShowSwipeUpUI() ? R.string.config_navBarLayoutQuickstep : R.string.config_navBarLayout);
    }

    public void inflateButton(String str, ViewGroup viewGroup, boolean z, boolean z2) {
        View createView = createView(str, viewGroup, z ? this.mLandscapeInflater : this.mLayoutInflater);
        if (createView == null) {
            return;
        }
        View applySize = applySize(createView, str, z, z2);
        viewGroup.addView(applySize);
        addToDispatchers(applySize);
        View view = z ? this.mLastLandscape : this.mLastPortrait;
        if (applySize instanceof ReverseLinearLayout.ReverseRelativeLayout) {
            applySize = ((ReverseLinearLayout.ReverseRelativeLayout) applySize).getChildAt(0);
        }
        if (view != null) {
            applySize.setAccessibilityTraversalAfter(view.getId());
        }
        if (z) {
            this.mLastLandscape = applySize;
        } else {
            this.mLastPortrait = applySize;
        }
    }

    public final void inflateButtons(String[] strArr, ViewGroup viewGroup, boolean z, boolean z2) {
        for (String str : strArr) {
            inflateButton(str, viewGroup, z, z2);
        }
    }

    public void inflateChildren() {
        removeAllViews();
        FrameLayout frameLayout = (FrameLayout) this.mLayoutInflater.inflate(R.layout.navigation_layout, (ViewGroup) this, false);
        this.mHorizontal = frameLayout;
        addView(frameLayout);
        FrameLayout frameLayout2 = (FrameLayout) this.mLayoutInflater.inflate(R.layout.navigation_layout_vertical, (ViewGroup) this, false);
        this.mVertical = frameLayout2;
        addView(frameLayout2);
        updateAlternativeOrder();
    }

    public void inflateLayout(String str) {
        this.mCurrentLayout = str;
        if (str == null) {
            str = getDefaultLayout();
        }
        String[] split = str.split(";", 3);
        if (split.length != 3) {
            Log.d("NavBarInflater", "Invalid layout.");
            split = getDefaultLayout().split(";", 3);
        }
        String[] split2 = split[0].split(",");
        String[] split3 = split[1].split(",");
        String[] split4 = split[2].split(",");
        inflateButtons(split2, (ViewGroup) this.mHorizontal.findViewById(R.id.ends_group), false, true);
        inflateButtons(split2, (ViewGroup) this.mVertical.findViewById(R.id.ends_group), true, true);
        inflateButtons(split3, (ViewGroup) this.mHorizontal.findViewById(R.id.center_group), false, false);
        inflateButtons(split3, (ViewGroup) this.mVertical.findViewById(R.id.center_group), true, false);
        addGravitySpacer((LinearLayout) this.mHorizontal.findViewById(R.id.ends_group));
        addGravitySpacer((LinearLayout) this.mVertical.findViewById(R.id.ends_group));
        inflateButtons(split4, (ViewGroup) this.mHorizontal.findViewById(R.id.ends_group), false, false);
        inflateButtons(split4, (ViewGroup) this.mVertical.findViewById(R.id.ends_group), true, false);
        updateButtonDispatchersCurrentView();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        ((NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class)).removeListener(this.mListener);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        inflateChildren();
        clearViews();
        inflateLayout(getDefaultLayout());
    }

    public final void setButtonDispatchers(SparseArray sparseArray) {
        this.mButtonDispatchers = sparseArray;
        clearDispatcherViews();
        for (int i = 0; i < sparseArray.size(); i++) {
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) sparseArray.valueAt(i);
            addAll(buttonDispatcher, (ViewGroup) this.mHorizontal.findViewById(R.id.ends_group));
            addAll(buttonDispatcher, (ViewGroup) this.mHorizontal.findViewById(R.id.center_group));
            addAll(buttonDispatcher, (ViewGroup) this.mVertical.findViewById(R.id.ends_group));
            addAll(buttonDispatcher, (ViewGroup) this.mVertical.findViewById(R.id.center_group));
        }
    }

    public final void updateAlternativeOrder() {
        updateAlternativeOrder(this.mHorizontal.findViewById(R.id.ends_group));
        updateAlternativeOrder(this.mHorizontal.findViewById(R.id.center_group));
        updateAlternativeOrder(this.mVertical.findViewById(R.id.ends_group));
        updateAlternativeOrder(this.mVertical.findViewById(R.id.center_group));
    }

    public final void updateButtonDispatchersCurrentView() {
        if (this.mButtonDispatchers != null) {
            FrameLayout frameLayout = this.mIsVertical ? this.mVertical : this.mHorizontal;
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                ButtonDispatcher valueAt = this.mButtonDispatchers.valueAt(i);
                View findViewById = frameLayout.findViewById(valueAt.mId);
                valueAt.mCurrentView = findViewById;
                KeyButtonDrawable keyButtonDrawable = valueAt.mImageDrawable;
                if (keyButtonDrawable != null) {
                    keyButtonDrawable.setCallback(findViewById);
                }
                View view = valueAt.mCurrentView;
                if (view != null) {
                    view.setTranslationX(0.0f);
                    valueAt.mCurrentView.setTranslationY(0.0f);
                    valueAt.mCurrentView.setTranslationZ(0.0f);
                }
            }
        }
    }

    public final void updateAlternativeOrder(View view) {
        if (view instanceof ReverseLinearLayout) {
            ReverseLinearLayout reverseLinearLayout = (ReverseLinearLayout) view;
            reverseLinearLayout.mIsAlternativeOrder = this.mAlternativeOrder;
            reverseLinearLayout.updateOrder();
        }
    }

    public void updateLayoutProviderView() {
    }
}
