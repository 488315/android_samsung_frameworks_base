package android.inputmethodservice.navigationbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.ReverseLinearLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import com.android.internal.R;

/* loaded from: classes2.dex */
public final class NavigationBarInflaterView extends FrameLayout {
    private static final String ABSOLUTE_SUFFIX = "A";
    private static final String ABSOLUTE_VERTICAL_CENTERED_SUFFIX = "C";
    public static final String BACK = "back";
    public static final String BUTTON_SEPARATOR = ",";
    public static final String CLIPBOARD = "clipboard";
    private static final String CONFIG_NAV_BAR_LAYOUT_HANDLE = "back[70AC];home_handle;ime_switcher[70AC]";
    public static final String CONTEXTUAL = "contextual";
    public static final String GRAVITY_SEPARATOR = ";";
    public static final String HOME = "home";
    public static final String HOME_HANDLE = "home_handle";
    public static final String IME_SWITCHER = "ime_switcher";
    public static final String KEY = "key";
    public static final String KEY_CODE_END = ")";
    public static final String KEY_CODE_START = "(";
    public static final String KEY_IMAGE_DELIM = ":";
    public static final String LEFT = "left";
    public static final String MENU_IME_ROTATE = "menu_ime";
    public static final String NAVSPACE = "space";
    public static final String NAV_BAR_LEFT = "sysui_nav_bar_left";
    public static final String NAV_BAR_RIGHT = "sysui_nav_bar_right";
    public static final String NAV_BAR_VIEWS = "sysui_nav_bar";
    public static final String RECENT = "recent";
    public static final String RIGHT = "right";
    public static final String SIZE_MOD_END = "]";
    public static final String SIZE_MOD_START = "[";
    private static final String TAG = "NavBarInflater";
    private static final String WEIGHT_CENTERED_SUFFIX = "WC";
    private static final String WEIGHT_SUFFIX = "W";
    private boolean mAlternativeOrder;
    SparseArray<ButtonDispatcher> mButtonDispatchers;
    protected FrameLayout mHorizontal;
    protected LayoutInflater mLandscapeInflater;
    private View mLastLandscape;
    private View mLastPortrait;
    protected LayoutInflater mLayoutInflater;

    public NavigationBarInflaterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        createInflaters();
    }

    void createInflaters() {
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        Configuration configuration = new Configuration();
        configuration.setTo(this.mContext.getResources().getConfiguration());
        configuration.orientation = 2;
        this.mLandscapeInflater = LayoutInflater.from(this.mContext.createConfigurationContext(configuration));
    }

    @Override // android.view.View
    protected void onFinishInflate() throws Resources.NotFoundException, NumberFormatException {
        super.onFinishInflate();
        inflateChildren();
        clearViews();
        inflateLayout(getDefaultLayout());
    }

    private void inflateChildren() {
        removeAllViews();
        FrameLayout frameLayout = (FrameLayout) this.mLayoutInflater.inflate(R.layout.input_method_navigation_layout, (ViewGroup) this, false);
        this.mHorizontal = frameLayout;
        addView(frameLayout);
        updateAlternativeOrder();
    }

    String getDefaultLayout() {
        return CONFIG_NAV_BAR_LAYOUT_HANDLE;
    }

    void setButtonDispatchers(SparseArray<ButtonDispatcher> sparseArray) {
        this.mButtonDispatchers = sparseArray;
        for (int i = 0; i < sparseArray.size(); i++) {
            initiallyFill(sparseArray.valueAt(i));
        }
    }

    void updateButtonDispatchersCurrentView() {
        if (this.mButtonDispatchers != null) {
            FrameLayout frameLayout = this.mHorizontal;
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).setCurrentView(frameLayout);
            }
        }
    }

    void setAlternativeOrder(boolean z) {
        if (z != this.mAlternativeOrder) {
            this.mAlternativeOrder = z;
            updateAlternativeOrder();
        }
    }

    private void updateAlternativeOrder() {
        updateAlternativeOrder(this.mHorizontal.findViewById(R.id.input_method_nav_ends_group));
        updateAlternativeOrder(this.mHorizontal.findViewById(R.id.input_method_nav_center_group));
    }

    private void updateAlternativeOrder(View view) {
        if (view instanceof ReverseLinearLayout) {
            ((ReverseLinearLayout) view).setAlternativeOrder(this.mAlternativeOrder);
        }
    }

    private void initiallyFill(ButtonDispatcher buttonDispatcher) throws Resources.NotFoundException {
        addAll(buttonDispatcher, (ViewGroup) this.mHorizontal.findViewById(R.id.input_method_nav_ends_group));
        addAll(buttonDispatcher, (ViewGroup) this.mHorizontal.findViewById(R.id.input_method_nav_center_group));
    }

    private void addAll(ButtonDispatcher buttonDispatcher, ViewGroup viewGroup) throws Resources.NotFoundException {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i).getId() == buttonDispatcher.getId()) {
                buttonDispatcher.addView(viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                addAll(buttonDispatcher, (ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

    protected void inflateLayout(String str) throws Resources.NotFoundException, NumberFormatException {
        if (str == null) {
            str = getDefaultLayout();
        }
        String[] strArrSplit = str.split(GRAVITY_SEPARATOR, 3);
        if (strArrSplit.length != 3) {
            Log.d(TAG, "Invalid layout.");
            strArrSplit = getDefaultLayout().split(GRAVITY_SEPARATOR, 3);
        }
        String[] strArrSplit2 = strArrSplit[0].split(",");
        String[] strArrSplit3 = strArrSplit[1].split(",");
        String[] strArrSplit4 = strArrSplit[2].split(",");
        inflateButtons(strArrSplit2, (ViewGroup) this.mHorizontal.findViewById(R.id.input_method_nav_ends_group), false, true);
        inflateButtons(strArrSplit3, (ViewGroup) this.mHorizontal.findViewById(R.id.input_method_nav_center_group), false, false);
        addGravitySpacer((LinearLayout) this.mHorizontal.findViewById(R.id.input_method_nav_ends_group));
        inflateButtons(strArrSplit4, (ViewGroup) this.mHorizontal.findViewById(R.id.input_method_nav_ends_group), false, false);
        updateButtonDispatchersCurrentView();
    }

    private void addGravitySpacer(LinearLayout linearLayout) {
        linearLayout.addView(new Space(this.mContext), new LinearLayout.LayoutParams(0, 0, 1.0f));
    }

    private void inflateButtons(String[] strArr, ViewGroup viewGroup, boolean z, boolean z2) throws Resources.NotFoundException, NumberFormatException {
        for (String str : strArr) {
            inflateButton(str, viewGroup, z, z2);
        }
    }

    private ViewGroup.LayoutParams copy(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return new LinearLayout.LayoutParams(layoutParams.width, layoutParams.height, ((LinearLayout.LayoutParams) layoutParams).weight);
        }
        return new FrameLayout.LayoutParams(layoutParams.width, layoutParams.height);
    }

    protected View inflateButton(String str, ViewGroup viewGroup, boolean z, boolean z2) throws Resources.NotFoundException, NumberFormatException {
        View viewCreateView = createView(str, viewGroup, z ? this.mLandscapeInflater : this.mLayoutInflater);
        if (viewCreateView == null) {
            return null;
        }
        View viewApplySize = applySize(viewCreateView, str, z, z2);
        viewGroup.addView(viewApplySize);
        addToDispatchers(viewApplySize);
        View view = z ? this.mLastLandscape : this.mLastPortrait;
        View childAt = viewApplySize instanceof ReverseLinearLayout.ReverseRelativeLayout ? ((ReverseLinearLayout.ReverseRelativeLayout) viewApplySize).getChildAt(0) : viewApplySize;
        if (view != null) {
            childAt.setAccessibilityTraversalAfter(view.getId());
        }
        if (z) {
            this.mLastLandscape = childAt;
            return viewApplySize;
        }
        this.mLastPortrait = childAt;
        return viewApplySize;
    }

    private View applySize(View view, String str, boolean z, boolean z2) throws NumberFormatException {
        String strExtractSize = extractSize(str);
        if (strExtractSize == null) {
            return view;
        }
        if (strExtractSize.contains("W") || strExtractSize.contains("A")) {
            ReverseLinearLayout.ReverseRelativeLayout reverseRelativeLayout = new ReverseLinearLayout.ReverseRelativeLayout(this.mContext);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getLayoutParams());
            int i = z ? z2 ? 48 : 80 : z2 ? Gravity.START : Gravity.END;
            if (strExtractSize.endsWith(WEIGHT_CENTERED_SUFFIX)) {
                i = 17;
            } else if (strExtractSize.endsWith("C")) {
                i = 16;
            }
            reverseRelativeLayout.setDefaultGravity(i);
            reverseRelativeLayout.setGravity(i);
            reverseRelativeLayout.addView(view, layoutParams);
            if (strExtractSize.contains("W")) {
                reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -1, Float.parseFloat(strExtractSize.substring(0, strExtractSize.indexOf("W")))));
            } else {
                reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams((int) convertDpToPx(this.mContext, Float.parseFloat(strExtractSize.substring(0, strExtractSize.indexOf("A")))), -1));
            }
            reverseRelativeLayout.setClipChildren(false);
            reverseRelativeLayout.setClipToPadding(false);
            return reverseRelativeLayout;
        }
        float f = Float.parseFloat(strExtractSize);
        view.getLayoutParams().width = (int) (r7.width * f);
        return view;
    }

    View createView(String str, ViewGroup viewGroup, LayoutInflater layoutInflater) {
        String strExtractButton = extractButton(str);
        if ("left".equals(strExtractButton)) {
            strExtractButton = extractButton(NAVSPACE);
        } else if ("right".equals(strExtractButton)) {
            strExtractButton = extractButton(MENU_IME_ROTATE);
        }
        if (!"home".equals(strExtractButton)) {
            if (BACK.equals(strExtractButton)) {
                return layoutInflater.inflate(R.layout.input_method_nav_back, viewGroup, false);
            }
            if (!RECENT.equals(strExtractButton) && !MENU_IME_ROTATE.equals(strExtractButton) && !NAVSPACE.equals(strExtractButton) && !"clipboard".equals(strExtractButton) && !CONTEXTUAL.equals(strExtractButton)) {
                if (HOME_HANDLE.equals(strExtractButton)) {
                    return layoutInflater.inflate(R.layout.input_method_nav_home_handle, viewGroup, false);
                }
                if (IME_SWITCHER.equals(strExtractButton)) {
                    return layoutInflater.inflate(R.layout.input_method_nav_ime_switcher, viewGroup, false);
                }
                strExtractButton.startsWith("key");
                return null;
            }
        }
        return null;
    }

    private static String extractSize(String str) {
        if (str.contains(SIZE_MOD_START)) {
            return str.substring(str.indexOf(SIZE_MOD_START) + 1, str.indexOf(SIZE_MOD_END));
        }
        return null;
    }

    private static String extractButton(String str) {
        return !str.contains(SIZE_MOD_START) ? str : str.substring(0, str.indexOf(SIZE_MOD_START));
    }

    private void addToDispatchers(View view) throws Resources.NotFoundException {
        SparseArray<ButtonDispatcher> sparseArray = this.mButtonDispatchers;
        if (sparseArray != null) {
            int iIndexOfKey = sparseArray.indexOfKey(view.getId());
            if (iIndexOfKey >= 0) {
                this.mButtonDispatchers.valueAt(iIndexOfKey).addView(view);
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

    private void clearViews() throws Resources.NotFoundException {
        if (this.mButtonDispatchers != null) {
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).clear();
            }
        }
        clearAllChildren((ViewGroup) this.mHorizontal.findViewById(R.id.input_method_nav_buttons));
    }

    private void clearAllChildren(ViewGroup viewGroup) throws Resources.NotFoundException {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ((ViewGroup) viewGroup.getChildAt(i)).removeAllViews();
        }
    }

    private static float convertDpToPx(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }
}
