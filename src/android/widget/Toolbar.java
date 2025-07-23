package android.widget;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.tv.TvContract;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.CollapsibleActionView;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ActionMenuView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;
import com.android.internal.widget.DecorToolbar;
import com.android.internal.widget.ToolbarWidgetWrapper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class Toolbar extends ViewGroup {
    private static final int[] ATTRS = {16843499};
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private int mBackgroundResourceId;
    private int mButtonGravity;
    private ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Handler mCollapseHandler;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private boolean mIsCustomNavIcon;
    private boolean mIsSetOpenTheme;
    private boolean mIsThemeDeviceDefaultFamily;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private float mMaxFontScale;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private int mNavButtonStyle;
    private ImageButton mNavButtonView;
    private int mNavIconResourceId;
    private int mNavigationBarHeight;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Runnable mPerformToCollapse;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<Toolbar> {
        private int mCollapseContentDescriptionId;
        private int mCollapseIconId;
        private int mContentInsetEndId;
        private int mContentInsetEndWithActionsId;
        private int mContentInsetLeftId;
        private int mContentInsetRightId;
        private int mContentInsetStartId;
        private int mContentInsetStartWithNavigationId;
        private int mLogoDescriptionId;
        private int mLogoId;
        private int mNavigationContentDescriptionId;
        private int mNavigationIconId;
        private int mPopupThemeId;
        private boolean mPropertiesMapped = false;
        private int mSubtitleId;
        private int mTitleId;
        private int mTitleMarginBottomId;
        private int mTitleMarginEndId;
        private int mTitleMarginStartId;
        private int mTitleMarginTopId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mCollapseContentDescriptionId = propertyMapper.mapObject("collapseContentDescription", 16843984);
            this.mCollapseIconId = propertyMapper.mapObject("collapseIcon", 16844031);
            this.mContentInsetEndId = propertyMapper.mapInt("contentInsetEnd", 16843860);
            this.mContentInsetEndWithActionsId = propertyMapper.mapInt("contentInsetEndWithActions", 16844067);
            this.mContentInsetLeftId = propertyMapper.mapInt("contentInsetLeft", 16843861);
            this.mContentInsetRightId = propertyMapper.mapInt("contentInsetRight", 16843862);
            this.mContentInsetStartId = propertyMapper.mapInt("contentInsetStart", 16843859);
            this.mContentInsetStartWithNavigationId = propertyMapper.mapInt("contentInsetStartWithNavigation", 16844066);
            this.mLogoId = propertyMapper.mapObject(TvContract.Channels.Logo.CONTENT_DIRECTORY, 16843454);
            this.mLogoDescriptionId = propertyMapper.mapObject("logoDescription", 16844009);
            this.mNavigationContentDescriptionId = propertyMapper.mapObject("navigationContentDescription", 16843969);
            this.mNavigationIconId = propertyMapper.mapObject("navigationIcon", 16843968);
            this.mPopupThemeId = propertyMapper.mapInt("popupTheme", 16843945);
            this.mSubtitleId = propertyMapper.mapObject("subtitle", 16843473);
            this.mTitleId = propertyMapper.mapObject("title", 16843233);
            this.mTitleMarginBottomId = propertyMapper.mapInt("titleMarginBottom", 16844028);
            this.mTitleMarginEndId = propertyMapper.mapInt("titleMarginEnd", 16844026);
            this.mTitleMarginStartId = propertyMapper.mapInt("titleMarginStart", 16844025);
            this.mTitleMarginTopId = propertyMapper.mapInt("titleMarginTop", 16844027);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(Toolbar toolbar, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mCollapseContentDescriptionId, toolbar.getCollapseContentDescription());
            propertyReader.readObject(this.mCollapseIconId, toolbar.getCollapseIcon());
            propertyReader.readInt(this.mContentInsetEndId, toolbar.getContentInsetEnd());
            propertyReader.readInt(this.mContentInsetEndWithActionsId, toolbar.getContentInsetEndWithActions());
            propertyReader.readInt(this.mContentInsetLeftId, toolbar.getContentInsetLeft());
            propertyReader.readInt(this.mContentInsetRightId, toolbar.getContentInsetRight());
            propertyReader.readInt(this.mContentInsetStartId, toolbar.getContentInsetStart());
            propertyReader.readInt(this.mContentInsetStartWithNavigationId, toolbar.getContentInsetStartWithNavigation());
            propertyReader.readObject(this.mLogoId, toolbar.getLogo());
            propertyReader.readObject(this.mLogoDescriptionId, toolbar.getLogoDescription());
            propertyReader.readObject(this.mNavigationContentDescriptionId, toolbar.getNavigationContentDescription());
            propertyReader.readObject(this.mNavigationIconId, toolbar.getNavigationIcon());
            propertyReader.readInt(this.mPopupThemeId, toolbar.getPopupTheme());
            propertyReader.readObject(this.mSubtitleId, toolbar.getSubtitle());
            propertyReader.readObject(this.mTitleId, toolbar.getTitle());
            propertyReader.readInt(this.mTitleMarginBottomId, toolbar.getTitleMarginBottom());
            propertyReader.readInt(this.mTitleMarginEndId, toolbar.getTitleMarginEnd());
            propertyReader.readInt(this.mTitleMarginStartId, toolbar.getTitleMarginStart());
            propertyReader.readInt(this.mTitleMarginTopId, toolbar.getTitleMarginTop());
        }
    }

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843946);
    }

    public Toolbar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Toolbar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNavIconResourceId = 0;
        this.mIsCustomNavIcon = false;
        this.mNavigationBarHeight = 0;
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList<>();
        this.mHiddenViews = new ArrayList<>();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() { // from class: android.widget.Toolbar.1
            @Override // android.widget.ActionMenuView.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Toolbar.this.mOnMenuItemClickListener != null) {
                    return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
                }
                return false;
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable() { // from class: android.widget.Toolbar.2
            @Override // java.lang.Runnable
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        this.mMaxFontScale = 1.3f;
        this.mCollapseHandler = new Handler(Looper.getMainLooper());
        this.mPerformToCollapse = new Runnable() { // from class: android.widget.Toolbar.3
            @Override // java.lang.Runnable
            public void run() {
                Toolbar.this.collapseActionView();
            }
        };
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        boolean z = typedValue.data != 0;
        this.mIsThemeDeviceDefaultFamily = z;
        this.mIsSetOpenTheme = z && context.getResources().getAssets().getSamsungThemeOverlays().size() > 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Toolbar, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.Toolbar, attributeSet, obtainStyledAttributes, i, i2);
        this.mTitleTextAppearance = obtainStyledAttributes.getResourceId(4, 0);
        this.mSubtitleTextAppearance = obtainStyledAttributes.getResourceId(5, 0);
        this.mNavButtonStyle = obtainStyledAttributes.getResourceId(27, 0);
        this.mGravity = obtainStyledAttributes.getInteger(0, this.mGravity);
        this.mButtonGravity = obtainStyledAttributes.getInteger(23, 48);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(17, 0);
        this.mTitleMarginBottom = dimensionPixelOffset;
        this.mTitleMarginTop = dimensionPixelOffset;
        this.mTitleMarginEnd = dimensionPixelOffset;
        this.mTitleMarginStart = dimensionPixelOffset;
        int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(18, -1);
        if (dimensionPixelOffset2 >= 0) {
            this.mTitleMarginStart = dimensionPixelOffset2;
        }
        int dimensionPixelOffset3 = obtainStyledAttributes.getDimensionPixelOffset(19, -1);
        if (dimensionPixelOffset3 >= 0) {
            this.mTitleMarginEnd = dimensionPixelOffset3;
        }
        int dimensionPixelOffset4 = obtainStyledAttributes.getDimensionPixelOffset(20, -1);
        if (dimensionPixelOffset4 >= 0) {
            this.mTitleMarginTop = dimensionPixelOffset4;
        }
        int dimensionPixelOffset5 = obtainStyledAttributes.getDimensionPixelOffset(21, -1);
        if (dimensionPixelOffset5 >= 0) {
            this.mTitleMarginBottom = dimensionPixelOffset5;
        }
        this.mMaxButtonHeight = this.mIsThemeDeviceDefaultFamily ? obtainStyledAttributes.getDimensionPixelSize(22, -1) : -1;
        int dimensionPixelOffset6 = obtainStyledAttributes.getDimensionPixelOffset(6, Integer.MIN_VALUE);
        int dimensionPixelOffset7 = obtainStyledAttributes.getDimensionPixelOffset(7, Integer.MIN_VALUE);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        ensureContentInsets();
        this.mContentInsets.setAbsolute(dimensionPixelSize, dimensionPixelSize2);
        if (dimensionPixelOffset6 != Integer.MIN_VALUE || dimensionPixelOffset7 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(dimensionPixelOffset6, dimensionPixelOffset7);
        }
        this.mContentInsetStartWithNavigation = obtainStyledAttributes.getDimensionPixelOffset(25, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = obtainStyledAttributes.getDimensionPixelOffset(26, Integer.MIN_VALUE);
        Drawable drawable = obtainStyledAttributes.getDrawable(24);
        this.mCollapseIcon = drawable;
        if (this.mIsSetOpenTheme && (drawable instanceof BitmapDrawable)) {
            ((BitmapDrawable) drawable).setAutoMirrored(true);
        }
        this.mCollapseDescription = obtainStyledAttributes.getText(13);
        CharSequence text = obtainStyledAttributes.getText(1);
        if (!TextUtils.isEmpty(text)) {
            setTitle(text);
        }
        CharSequence text2 = obtainStyledAttributes.getText(3);
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(text2);
        }
        this.mPopupContext = this.mContext;
        setPopupTheme(obtainStyledAttributes.getResourceId(10, 0));
        Drawable drawable2 = obtainStyledAttributes.getDrawable(11);
        if (drawable2 != null) {
            setNavigationIcon(drawable2);
        }
        CharSequence text3 = obtainStyledAttributes.getText(12);
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(text3);
        }
        Drawable drawable3 = obtainStyledAttributes.getDrawable(2);
        if (drawable3 != null) {
            setLogo(drawable3);
        }
        CharSequence text4 = obtainStyledAttributes.getText(16);
        if (!TextUtils.isEmpty(text4)) {
            setLogoDescription(text4);
        }
        if (obtainStyledAttributes.hasValue(14)) {
            setTitleTextColor(obtainStyledAttributes.getColor(14, -1));
        }
        if (obtainStyledAttributes.hasValue(15)) {
            setSubtitleTextColor(obtainStyledAttributes.getColor(15, -1));
        }
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(new int[]{16843531});
        this.mNavIconResourceId = obtainStyledAttributes2.getResourceId(0, 0);
        obtainStyledAttributes2.recycle();
        if (this.mNavIconResourceId != 17304869) {
            this.mIsCustomNavIcon = true;
        }
        if (this.mIsSetOpenTheme) {
            TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, R.styleable.View, i, i2);
            int resourceId = obtainStyledAttributes3.getResourceId(13, 0);
            this.mBackgroundResourceId = resourceId;
            if (resourceId == 17304054) {
                setBackground(context.getDrawable(R.drawable.tw_action_bar_background));
                this.mBackgroundResourceId = R.drawable.tw_action_bar_background;
            } else if (resourceId == 17304055) {
                setBackground(context.getDrawable(R.drawable.tw_action_bar_background_dark));
                this.mBackgroundResourceId = R.drawable.tw_action_bar_background_dark;
            }
            obtainStyledAttributes3.recycle();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        ImageButton imageButton;
        super.onConfigurationChanged(configuration);
        if (this.mIsThemeDeviceDefaultFamily) {
            TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(ATTRS);
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            obtainStyledAttributes.recycle();
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = dimensionPixelSize;
            setLayoutParams(layoutParams);
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(null, R.styleable.Toolbar, 16843946, 0);
            int dimensionPixelSize2 = obtainStyledAttributes2.getDimensionPixelSize(22, -1);
            if (dimensionPixelSize2 >= 0) {
                this.mMaxButtonHeight = dimensionPixelSize2;
            }
            obtainStyledAttributes2.recycle();
            TypedArray obtainStyledAttributes3 = getContext().obtainStyledAttributes(null, R.styleable.View, 16843946, 0);
            int dimensionPixelSize3 = obtainStyledAttributes3.getDimensionPixelSize(37, -1);
            if (dimensionPixelSize3 >= 0) {
                setMinimumHeight(dimensionPixelSize3);
            }
            obtainStyledAttributes3.recycle();
            int dimensionPixelSize4 = getContext().getResources().getDimensionPixelSize(R.dimen.sem_toolbar_navigation_button_width);
            if (dimensionPixelSize4 >= 0 && (imageButton = this.mNavButtonView) != null) {
                imageButton.setMinimumWidth(dimensionPixelSize4);
            }
            if (!this.mIsCustomNavIcon && this.mNavIconResourceId > 0 && this.mNavButtonView != null) {
                Drawable drawable = getContext().getDrawable(this.mNavIconResourceId);
                if (this.mIsSetOpenTheme && (drawable instanceof BitmapDrawable)) {
                    ((BitmapDrawable) drawable).setAutoMirrored(true);
                } else if (drawable != null) {
                    this.mNavButtonView.lambda$setImageURIAsync$0(drawable);
                }
            }
            if (!this.mIsSetOpenTheme || this.mBackgroundResourceId == 0) {
                return;
            }
            setBackground(this.mContext.getDrawable(this.mBackgroundResourceId));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        while (parent != null && (parent instanceof ViewGroup)) {
            ViewGroup viewGroup = (ViewGroup) parent;
            if (viewGroup.isKeyboardNavigationCluster()) {
                setKeyboardNavigationCluster(false);
                if (viewGroup.getTouchscreenBlocksFocus()) {
                    setTouchscreenBlocksFocus(false);
                    return;
                }
                return;
            }
            parent = viewGroup.getParent();
        }
    }

    public void setPopupTheme(int i) {
        if (this.mPopupTheme != i) {
            this.mPopupTheme = i;
            if (i == 0) {
                this.mPopupContext = this.mContext;
            } else {
                this.mPopupContext = new ContextThemeWrapper(this.mContext, i);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setTitleMargin(int i, int i2, int i3, int i4) {
        this.mTitleMarginStart = i;
        this.mTitleMarginTop = i2;
        this.mTitleMarginEnd = i3;
        this.mTitleMarginBottom = i4;
        requestLayout();
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public void setTitleMarginStart(int i) {
        this.mTitleMarginStart = i;
        requestLayout();
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    public void setTitleMarginTop(int i) {
        this.mTitleMarginTop = i;
        requestLayout();
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public void setTitleMarginEnd(int i) {
        this.mTitleMarginEnd = i;
        requestLayout();
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public void setTitleMarginBottom(int i) {
        this.mTitleMarginBottom = i;
        requestLayout();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        ensureContentInsets();
        this.mContentInsets.setDirection(i == 1);
    }

    public void setLogo(int i) {
        setLogo(getContext().getDrawable(i));
    }

    public boolean canShowOverflowMenu() {
        ActionMenuView actionMenuView;
        return getVisibility() == 0 && (actionMenuView = this.mMenuView) != null && actionMenuView.isOverflowReserved();
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuView actionMenuView = this.mMenuView;
        return actionMenuView != null && actionMenuView.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuView actionMenuView = this.mMenuView;
        return actionMenuView != null && actionMenuView.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        ActionMenuView actionMenuView = this.mMenuView;
        return actionMenuView != null && actionMenuView.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        ActionMenuView actionMenuView = this.mMenuView;
        return actionMenuView != null && actionMenuView.hideOverflowMenu();
    }

    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        if (menuBuilder == null && this.mMenuView == null) {
            return;
        }
        ensureMenuView();
        MenuBuilder peekMenu = this.mMenuView.peekMenu();
        if (peekMenu == menuBuilder) {
            return;
        }
        if (peekMenu != null) {
            peekMenu.removeMenuPresenter(this.mOuterActionMenuPresenter);
            peekMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter.setExpandedActionViewsExclusive(true);
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            actionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            actionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(actionMenuPresenter);
        this.mOuterActionMenuPresenter = actionMenuPresenter;
    }

    public void dismissPopupMenus() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            actionMenuView.dismissPopupMenus();
        }
    }

    public boolean isTitleTruncated() {
        Layout layout;
        TextView textView = this.mTitleTextView;
        if (textView == null || (layout = textView.getLayout()) == null) {
            return false;
        }
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (layout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            ensureLogoView();
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else {
            ImageView imageView = this.mLogoView;
            if (imageView != null && isChildOrHidden(imageView)) {
                removeView(this.mLogoView);
                this.mHiddenViews.remove(this.mLogoView);
            }
        }
        ImageView imageView2 = this.mLogoView;
        if (imageView2 != null) {
            imageView2.lambda$setImageURIAsync$0(drawable);
        }
    }

    public Drawable getLogo() {
        ImageView imageView = this.mLogoView;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    public void setLogoDescription(int i) {
        setLogoDescription(getContext().getText(i));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureLogoView();
        }
        ImageView imageView = this.mLogoView;
        if (imageView != null) {
            imageView.setContentDescription(charSequence);
        }
    }

    public CharSequence getLogoDescription() {
        ImageView imageView = this.mLogoView;
        if (imageView != null) {
            return imageView.getContentDescription();
        }
        return null;
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new ImageView(getContext());
        }
    }

    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        return (expandedActionViewMenuPresenter == null || expandedActionViewMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public void collapseActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        MenuItemImpl menuItemImpl = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public void setTitle(int i) {
        setTitle(getContext().getText(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mTitleTextView == null) {
                TextView textView = new TextView(getContext());
                this.mTitleTextView = textView;
                textView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.mTitleTextAppearance;
                if (i != 0) {
                    this.mTitleTextView.setTextAppearance(i);
                }
                int i2 = this.mTitleTextColor;
                if (i2 != 0) {
                    this.mTitleTextView.setTextColor(i2);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        } else {
            TextView textView2 = this.mTitleTextView;
            if (textView2 != null && isChildOrHidden(textView2)) {
                removeView(this.mTitleTextView);
                this.mHiddenViews.remove(this.mTitleTextView);
            }
        }
        TextView textView3 = this.mTitleTextView;
        if (textView3 != null) {
            textView3.lambda$setTextAsync$0(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public void setSubtitle(int i) {
        setSubtitle(getContext().getText(i));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mSubtitleTextView == null) {
                TextView textView = new TextView(getContext());
                this.mSubtitleTextView = textView;
                textView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.mSubtitleTextAppearance;
                if (i != 0) {
                    this.mSubtitleTextView.setTextAppearance(i);
                }
                int i2 = this.mSubtitleTextColor;
                if (i2 != 0) {
                    this.mSubtitleTextView.setTextColor(i2);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        } else {
            TextView textView2 = this.mSubtitleTextView;
            if (textView2 != null && isChildOrHidden(textView2)) {
                removeView(this.mSubtitleTextView);
                this.mHiddenViews.remove(this.mSubtitleTextView);
            }
        }
        TextView textView3 = this.mSubtitleTextView;
        if (textView3 != null) {
            textView3.lambda$setTextAsync$0(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setTitleTextAppearance(Context context, int i) {
        this.mTitleTextAppearance = i;
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setTextAppearance(i);
        }
    }

    public void setSubtitleTextAppearance(Context context, int i) {
        this.mSubtitleTextAppearance = i;
        TextView textView = this.mSubtitleTextView;
        if (textView != null) {
            textView.setTextAppearance(i);
        }
    }

    public void setTitleTextColor(int i) {
        this.mTitleTextColor = i;
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public void setSubtitleTextColor(int i) {
        this.mSubtitleTextColor = i;
        TextView textView = this.mSubtitleTextView;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public CharSequence getNavigationContentDescription() {
        ImageButton imageButton = this.mNavButtonView;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureNavButtonView();
        }
        ImageButton imageButton = this.mNavButtonView;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
            this.mNavButtonView.semSetHoverPopupType(0);
            this.mNavButtonView.setTooltipText(charSequence);
            setTooltipOffset(this.mNavButtonView);
        }
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        ensureNavButtonView();
        int action = motionEvent.getAction();
        if (action == 9) {
            ImageButton imageButton = this.mNavButtonView;
            if (imageButton != null) {
                imageButton.setTooltipText(getNavigationContentDescription());
            }
            setTooltipNull(false);
        } else if (action == 10) {
            setTooltipNull(true);
        }
        ImageButton imageButton2 = this.mNavButtonView;
        if (imageButton2 != null) {
            setTooltipOffset(imageButton2);
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    private void setTooltipOffset(View view) {
        int i;
        float f;
        float f2;
        if (view == null) {
            return;
        }
        Context context = getContext();
        Resources resources = context.getResources();
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int width = view.getWidth();
        int height = getHeight();
        int[] iArr2 = new int[2];
        getLocationInWindow(iArr2);
        int[] iArr3 = new int[2];
        view.getLocationInWindow(iArr3);
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        int rotation = defaultDisplay.getRotation();
        int dimension = (int) resources.getDimension(R.dimen.navigation_bar_height);
        ActionMenuView actionMenuView = this.mMenuView;
        View view2 = actionMenuView != null ? (View) actionMenuView.getParent() : null;
        int i2 = (!(view2 instanceof Toolbar) || view2.getWidth() >= rect.right - rect.left) ? 0 : (iArr[0] - iArr2[0]) - rect.left;
        int i3 = iArr2[1] + height;
        if (getLayoutDirection() == 0) {
            i = ((rect.right - rect.left) - (iArr3[0] + width)) - i2;
            if (rotation == 3 && checkNaviBarForLandscape()) {
                f = resources.getDisplayMetrics().density;
                f2 = displayMetrics.density;
                if (getNavigationBarHeight() < dimension) {
                    i = (i - ((int) ((dimension / f) * f2))) - (width / 2);
                }
                i -= (int) ((dimension / f) * f2);
            }
            setTooltipPosition(i, i3);
        }
        i = iArr3[0] - width;
        if (rotation == 1 && checkNaviBarForLandscape()) {
            f = resources.getDisplayMetrics().density;
            f2 = displayMetrics.density;
            i -= (int) ((dimension / f) * f2);
        }
        setTooltipPosition(i, i3);
    }

    boolean checkNaviBarForLandscape() {
        Context context = getContext();
        Resources resources = context.getResources();
        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int rotation = defaultDisplay.getRotation();
        int dimension = (int) resources.getDimension(R.dimen.navigation_bar_height);
        if (rotation == 1 && rect.right + dimension >= point.x) {
            setNavigationBarHeight(point.x - rect.right);
            return true;
        }
        if (rotation != 3 || rect.left > dimension) {
            return false;
        }
        setNavigationBarHeight(rect.left);
        return true;
    }

    private void setNavigationBarHeight(int i) {
        this.mNavigationBarHeight = i;
    }

    private int getNavigationBarHeight() {
        return this.mNavigationBarHeight;
    }

    public void setNavigationIcon(int i) {
        int i2 = this.mNavIconResourceId;
        if (i2 > 0 && i2 == 17304869 && i == 17304869) {
            this.mIsCustomNavIcon = false;
        } else {
            this.mIsCustomNavIcon = true;
        }
        setNavigationIcon(getContext().getDrawable(i));
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            if (this.mIsSetOpenTheme && (drawable instanceof BitmapDrawable)) {
                ((BitmapDrawable) drawable).setAutoMirrored(true);
            }
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else {
            ImageButton imageButton = this.mNavButtonView;
            if (imageButton != null && isChildOrHidden(imageButton)) {
                removeView(this.mNavButtonView);
                this.mHiddenViews.remove(this.mNavButtonView);
            }
        }
        ImageButton imageButton2 = this.mNavButtonView;
        if (imageButton2 != null) {
            imageButton2.lambda$setImageURIAsync$0(drawable);
        }
    }

    public Drawable getNavigationIcon() {
        ImageButton imageButton = this.mNavButtonView;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public View getNavigationView() {
        return this.mNavButtonView;
    }

    public CharSequence getCollapseContentDescription() {
        ImageButton imageButton = this.mCollapseButtonView;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public void setCollapseContentDescription(int i) {
        setCollapseContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setCollapseContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureCollapseButtonView();
        }
        ImageButton imageButton = this.mCollapseButtonView;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public Drawable getCollapseIcon() {
        ImageButton imageButton = this.mCollapseButtonView;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public void setCollapseIcon(int i) {
        setCollapseIcon(getContext().getDrawable(i));
    }

    public void setCollapseIcon(Drawable drawable) {
        if (drawable != null) {
            ensureCollapseButtonView();
            this.mCollapseButtonView.lambda$setImageURIAsync$0(drawable);
        } else {
            ImageButton imageButton = this.mCollapseButtonView;
            if (imageButton != null) {
                imageButton.lambda$setImageURIAsync$0(this.mCollapseIcon);
            }
        }
    }

    public Menu getMenu() {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    public void setOverflowIcon(Drawable drawable) {
        ensureMenu();
        this.mMenuView.setOverflowIcon(drawable);
    }

    public Drawable getOverflowIcon() {
        ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    private void ensureMenu() {
        ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder) this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            ActionMenuView actionMenuView = new ActionMenuView(getContext());
            this.mMenuView = actionMenuView;
            actionMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (this.mButtonGravity & 112) | Gravity.END;
            this.mMenuView.setLayoutParams(generateDefaultLayoutParams);
            addSystemView(this.mMenuView, false);
        }
    }

    private MenuInflater getMenuInflater() {
        return new MenuInflater(getContext());
    }

    public void inflateMenu(int i) {
        getMenuInflater().inflate(i, getMenu());
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setContentInsetsRelative(int i, int i2) {
        ensureContentInsets();
        this.mContentInsets.setRelative(i, i2);
    }

    public int getContentInsetStart() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper != null) {
            return rtlSpacingHelper.getStart();
        }
        return 0;
    }

    public int getContentInsetEnd() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper != null) {
            return rtlSpacingHelper.getEnd();
        }
        return 0;
    }

    public void setContentInsetsAbsolute(int i, int i2) {
        ensureContentInsets();
        this.mContentInsets.setAbsolute(i, i2);
    }

    public int getContentInsetLeft() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper != null) {
            return rtlSpacingHelper.getLeft();
        }
        return 0;
    }

    public int getContentInsetRight() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper != null) {
            return rtlSpacingHelper.getRight();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int i = this.mContentInsetStartWithNavigation;
        return i != Integer.MIN_VALUE ? i : getContentInsetStart();
    }

    public void setContentInsetStartWithNavigation(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getContentInsetEndWithActions() {
        int i = this.mContentInsetEndWithActions;
        return i != Integer.MIN_VALUE ? i : getContentInsetEnd();
    }

    public void setContentInsetEndWithActions(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getCurrentContentInsetStart() {
        if (getNavigationIcon() != null) {
            return Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        MenuBuilder peekMenu;
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null && (peekMenu = actionMenuView.peekMenu()) != null && peekMenu.hasVisibleItems()) {
            return Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (isLayoutRtl()) {
            return getCurrentContentInsetEnd();
        }
        return getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (isLayoutRtl()) {
            return getCurrentContentInsetStart();
        }
        return getCurrentContentInsetEnd();
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new ImageButton(getContext(), null, 0, this.mNavButtonStyle);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (this.mButtonGravity & 112) | Gravity.START;
            this.mNavButtonView.setLayoutParams(generateDefaultLayoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureCollapseButtonView() {
        String string = getContext().getResources().getString(R.string.action_bar_up_description);
        if (this.mCollapseButtonView == null) {
            ImageButton imageButton = new ImageButton(getContext(), null, 0, this.mNavButtonStyle);
            this.mCollapseButtonView = imageButton;
            imageButton.lambda$setImageURIAsync$0(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (this.mButtonGravity & 112) | Gravity.START;
            generateDefaultLayoutParams.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams(generateDefaultLayoutParams);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener() { // from class: android.widget.Toolbar.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (Toolbar.this.mIsThemeDeviceDefaultFamily) {
                        InputMethodManager peekInstance = InputMethodManager.peekInstance();
                        if (peekInstance != null && peekInstance.isActive(Toolbar.this.mExpandedActionView)) {
                            peekInstance.hideSoftInputFromWindow(Toolbar.this.mExpandedActionView.getWindowToken(), 0);
                        }
                        Toolbar.this.mCollapseHandler.postDelayed(Toolbar.this.mPerformToCollapse, 100L);
                        return;
                    }
                    Toolbar.this.collapseActionView();
                }
            });
            this.mCollapseButtonView.setContentDescription(string);
        }
    }

    private void addSystemView(View view, boolean z) {
        LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 == null) {
            layoutParams = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(layoutParams2)) {
            layoutParams = generateLayoutParams(layoutParams2);
        } else {
            layoutParams = (LayoutParams) layoutParams2;
        }
        layoutParams.mViewType = 1;
        if (z && this.mExpandedActionView != null) {
            view.setLayoutParams(layoutParams);
            this.mHiddenViews.add(view);
        } else {
            addView(view, layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = isOverflowMenuShowing();
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem findItem;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        ActionMenuView actionMenuView = this.mMenuView;
        MenuBuilder peekMenu = actionMenuView != null ? actionMenuView.peekMenu() : null;
        if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && peekMenu != null && (findItem = peekMenu.findItem(savedState.expandedMenuItemId)) != null) {
            findItem.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    private void postShowOverflowMenu() {
        removeCallbacks(this.mShowOverflowMenuRunnable);
        post(this.mShowOverflowMenuRunnable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.mEatingTouch = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    @Override // android.view.ViewGroup
    protected void onSetLayoutParams(View view, ViewGroup.LayoutParams layoutParams) {
        if (checkLayoutParams(layoutParams)) {
            return;
        }
        view.setLayoutParams(generateLayoutParams(layoutParams));
    }

    private void measureChildConstrained(View view, int i, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width);
        int childMeasureSpec2 = getChildMeasureSpec(i3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i5);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    private int measureChildCollapseMargins(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int max = Math.max(0, i5) + Math.max(0, i6);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + max + i2, marginLayoutParams.width), getChildMeasureSpec(i3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + max;
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (shouldLayout(childAt) && childAt.getMeasuredWidth() > 0 && childAt.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr;
        int i7;
        int i8;
        int[] iArr2 = this.mTempMargins;
        boolean isLayoutRtl = isLayoutRtl();
        int i9 = !isLayoutRtl ? 1 : 0;
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, i, 0, i2, 0, this.mMaxButtonHeight);
            i3 = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
            i4 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
            i5 = combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
        } else {
            i3 = 0;
            i4 = 0;
            i5 = 0;
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            measureChildConstrained(this.mCollapseButtonView, i, 0, i2, 0, this.mMaxButtonHeight);
            i3 = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView);
            i4 = Math.max(i4, this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView));
            i5 = combineMeasuredStates(i5, this.mCollapseButtonView.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int max = Math.max(currentContentInsetStart, i3);
        iArr2[isLayoutRtl ? 1 : 0] = Math.max(0, currentContentInsetStart - i3);
        if (shouldLayout(this.mMenuView)) {
            measureChildConstrained(this.mMenuView, i, max, i2, 0, this.mMaxButtonHeight);
            i6 = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
            i4 = Math.max(i4, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView));
            i5 = combineMeasuredStates(i5, this.mMenuView.getMeasuredState());
        } else {
            i6 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int max2 = max + Math.max(currentContentInsetEnd, i6);
        iArr2[i9] = Math.max(0, currentContentInsetEnd - i6);
        if (shouldLayout(this.mExpandedActionView)) {
            iArr = iArr2;
            max2 += measureChildCollapseMargins(this.mExpandedActionView, i, max2, i2, 0, iArr);
            i4 = Math.max(i4, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
            i5 = combineMeasuredStates(i5, this.mExpandedActionView.getMeasuredState());
        } else {
            iArr = iArr2;
        }
        if (shouldLayout(this.mLogoView)) {
            max2 += measureChildCollapseMargins(this.mLogoView, i, max2, i2, 0, iArr);
            i4 = Math.max(i4, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
            i5 = combineMeasuredStates(i5, this.mLogoView.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (((LayoutParams) childAt.getLayoutParams()).mViewType == 0 && shouldLayout(childAt)) {
                max2 += measureChildCollapseMargins(childAt, i, max2, i2, 0, iArr);
                int max3 = Math.max(i4, childAt.getMeasuredHeight() + getVerticalMargins(childAt));
                i5 = combineMeasuredStates(i5, childAt.getMeasuredState());
                i4 = max3;
            } else {
                max2 = max2;
            }
        }
        int i11 = max2;
        int i12 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int i13 = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            if (this.mIsThemeDeviceDefaultFamily) {
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mTitleTextAppearance, R.styleable.TextAppearance);
                float complexToFloat = TypedValue.complexToFloat(obtainStyledAttributes.peekValue(0).data);
                obtainStyledAttributes.recycle();
                if (TextUtils.isEmpty(this.mSubtitleText)) {
                    float f = getContext().getResources().getConfiguration().fontScale;
                    float f2 = this.mMaxFontScale;
                    if (f > f2) {
                        f = f2;
                    }
                    this.mTitleTextView.setTextSize(1, complexToFloat * f);
                } else {
                    this.mTitleTextView.setTextSize(1, complexToFloat);
                }
            }
            measureChildCollapseMargins(this.mTitleTextView, i, i11 + i13, i2, i12, iArr);
            int measuredWidth = this.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
            int measuredHeight = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            i5 = combineMeasuredStates(i5, this.mTitleTextView.getMeasuredState());
            i7 = measuredWidth;
            i8 = measuredHeight;
        } else {
            i7 = 0;
            i8 = 0;
        }
        if (shouldLayout(this.mSubtitleTextView)) {
            i7 = Math.max(i7, measureChildCollapseMargins(this.mSubtitleTextView, i, i11 + i13, i2, i12 + i8, iArr));
            i8 += this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
            i5 = combineMeasuredStates(i5, this.mSubtitleTextView.getMeasuredState());
        }
        setMeasuredDimension(resolveSizeAndState(Math.max(i11 + i7 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, (-16777216) & i5), shouldCollapse() ? 0 : resolveSizeAndState(Math.max(Math.max(i4, i8) + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, i5 << 16));
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x029c A[LOOP:0: B:40:0x029a->B:41:0x029c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x02ba A[LOOP:1: B:44:0x02b8->B:45:0x02ba, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x02f1 A[LOOP:2: B:53:0x02ef->B:54:0x02f1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0222  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 774
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    private int getViewListMeasuredWidth(List<View> list, int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int size = list.size();
        int i3 = 0;
        int i4 = 0;
        while (i3 < size) {
            View view = list.get(i3);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int i5 = layoutParams.leftMargin - i;
            int i6 = layoutParams.rightMargin - i2;
            int max = Math.max(0, i5);
            int max2 = Math.max(0, i6);
            int max3 = Math.max(0, -i5);
            int max4 = Math.max(0, -i6);
            i4 += max + view.getMeasuredWidth() + max2;
            i3++;
            i2 = max4;
            i = max3;
        }
        return i4;
    }

    private int layoutChildLeft(View view, int i, int[] iArr, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = layoutParams.leftMargin - iArr[0];
        int max = i + Math.max(0, i3);
        iArr[0] = Math.max(0, -i3);
        int childTop = getChildTop(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max, childTop, max + measuredWidth, view.getMeasuredHeight() + childTop);
        return max + measuredWidth + layoutParams.rightMargin;
    }

    private int layoutChildRight(View view, int i, int[] iArr, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = layoutParams.rightMargin - iArr[1];
        int max = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int childTop = getChildTop(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max - measuredWidth, childTop, max, view.getMeasuredHeight() + childTop);
        return max - (measuredWidth + layoutParams.leftMargin);
    }

    private int getChildTop(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        int childVerticalGravity = getChildVerticalGravity(layoutParams.gravity);
        if (childVerticalGravity == 48) {
            return getPaddingTop() - i2;
        }
        if (childVerticalGravity == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - layoutParams.bottomMargin) - i2;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int i3 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        if (i3 < layoutParams.topMargin) {
            i3 = layoutParams.topMargin;
        } else {
            int i4 = (((height - paddingBottom) - measuredHeight) - i3) - paddingTop;
            if (i4 < layoutParams.bottomMargin) {
                i3 = Math.max(0, i3 - (layoutParams.bottomMargin - i4));
            }
        }
        return paddingTop + i3;
    }

    private int getChildVerticalGravity(int i) {
        int i2 = i & 112;
        return (i2 == 16 || i2 == 48 || i2 == 80) ? i2 : this.mGravity & 112;
    }

    private void addCustomViewsWithGravity(List<View> list, int i) {
        boolean z = getLayoutDirection() == 1;
        int childCount = getChildCount();
        int absoluteGravity = Gravity.getAbsoluteGravity(i, getLayoutDirection());
        list.clear();
        if (!z) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.mViewType == 0 && shouldLayout(childAt) && getChildHorizontalGravity(layoutParams.gravity) == absoluteGravity) {
                    list.add(childAt);
                }
            }
            return;
        }
        for (int i3 = childCount - 1; i3 >= 0; i3--) {
            View childAt2 = getChildAt(i3);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            if (layoutParams2.mViewType == 0 && shouldLayout(childAt2) && getChildHorizontalGravity(layoutParams2.gravity) == absoluteGravity) {
                list.add(childAt2);
            }
        }
    }

    private int getChildHorizontalGravity(int i) {
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(i, layoutDirection) & 7;
        return (absoluteGravity == 1 || absoluteGravity == 3 || absoluteGravity == 5) ? absoluteGravity : layoutDirection == 1 ? 5 : 3;
    }

    private boolean shouldLayout(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    private int getHorizontalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.getMarginStart() + marginLayoutParams.getMarginEnd();
    }

    private int getVerticalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof LayoutParams);
    }

    private static boolean isCustomView(View view) {
        return ((LayoutParams) view.getLayoutParams()).mViewType == 0;
    }

    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    void removeChildrenForExpandedActionView() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (((LayoutParams) childAt.getLayoutParams()).mViewType != 2 && !childAt.equals(this.mMenuView)) {
                removeViewAt(childCount);
                this.mHiddenViews.add(childAt);
            }
        }
    }

    void addChildrenForExpandedActionView() {
        for (int size = this.mHiddenViews.size() - 1; size >= 0; size--) {
            addView(this.mHiddenViews.get(size));
        }
        this.mHiddenViews.clear();
    }

    private boolean isChildOrHidden(View view) {
        return view.getParent() == this || this.mHiddenViews.contains(view);
    }

    public void setCollapsible(boolean z) {
        this.mCollapsible = z;
        requestLayout();
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            actionMenuView.setMenuCallbacks(callback, callback2);
        }
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }

    public static class LayoutParams extends ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mViewType = 0;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mViewType = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.mViewType = 0;
            this.gravity = i3;
        }

        public LayoutParams(int i) {
            this(-2, -1, i);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ActionBar.LayoutParams) layoutParams);
            this.mViewType = 0;
            this.mViewType = layoutParams.mViewType;
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mViewType = 0;
            copyMarginsFrom(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = 0;
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.Toolbar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int expandedMenuItemId;
        public boolean isOverflowOpen;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
        }
    }

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void setCallback(MenuPresenter.Callback callback) {
        }

        private ExpandedActionViewMenuPresenter() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            MenuBuilder menuBuilder2 = this.mMenu;
            if (menuBuilder2 != null && (menuItemImpl = this.mCurrentExpandedItem) != null) {
                menuBuilder2.collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.mCurrentExpandedItem != null) {
                MenuBuilder menuBuilder = this.mMenu;
                if (menuBuilder != null) {
                    int size = menuBuilder.size();
                    for (int i = 0; i < size; i++) {
                        if (this.mMenu.getItem(i).equals(this.mCurrentExpandedItem)) {
                            return;
                        }
                    }
                }
                collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            Toolbar.this.ensureCollapseButtonView();
            ViewParent parent = Toolbar.this.mCollapseButtonView.getParent();
            Toolbar toolbar = Toolbar.this;
            if (parent != toolbar) {
                toolbar.addView(toolbar.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = menuItemImpl.getActionView();
            this.mCurrentExpandedItem = menuItemImpl;
            ViewParent parent2 = Toolbar.this.mExpandedActionView.getParent();
            Toolbar toolbar2 = Toolbar.this;
            if (parent2 != toolbar2) {
                LayoutParams generateDefaultLayoutParams = toolbar2.generateDefaultLayoutParams();
                generateDefaultLayoutParams.gravity = (Toolbar.this.mButtonGravity & 112) | Gravity.START;
                generateDefaultLayoutParams.mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams(generateDefaultLayoutParams);
                Toolbar toolbar3 = Toolbar.this;
                toolbar3.addView(toolbar3.mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            Toolbar toolbar = Toolbar.this;
            toolbar.removeView(toolbar.mExpandedActionView);
            Toolbar toolbar2 = Toolbar.this;
            toolbar2.removeView(toolbar2.mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }
    }
}
