package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class ToolbarWidgetWrapper {
    public ActionMenuPresenter mActionMenuPresenter;
    public final View mCustomView;
    public final int mDefaultNavigationContentDescription;
    public final Drawable mDefaultNavigationIcon;
    public int mDisplayOpts;
    public final CharSequence mHomeDescription;
    public final Drawable mIcon;
    public final Drawable mLogo;
    public boolean mMenuPrepared;
    public final Drawable mNavIcon;
    public final CharSequence mSubtitle;
    public CharSequence mTitle;
    public boolean mTitleSet;
    public final Toolbar mToolbar;
    public Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z) {
        this(toolbar, z, R.string.sesl_action_bar_up_description, R.drawable.sesl_ic_ab_back_light);
    }

    public final void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOpts ^ i;
        this.mDisplayOpts = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    updateHomeAccessibility();
                }
                int i3 = this.mDisplayOpts & 4;
                Toolbar toolbar = this.mToolbar;
                if (i3 != 0) {
                    Drawable drawable = this.mNavIcon;
                    if (drawable == null) {
                        drawable = this.mDefaultNavigationIcon;
                    }
                    toolbar.setNavigationIcon(drawable);
                } else {
                    toolbar.setNavigationIcon(null);
                }
            }
            if ((i2 & 3) != 0) {
                updateToolbarLogo();
            }
            int i4 = i2 & 8;
            Toolbar toolbar2 = this.mToolbar;
            if (i4 != 0) {
                if ((i & 8) != 0) {
                    toolbar2.setTitle(this.mTitle);
                    toolbar2.setSubtitle(this.mSubtitle);
                } else {
                    toolbar2.setTitle(null);
                    toolbar2.setSubtitle(null);
                }
            }
            if ((i2 & 16) == 0 || (view = this.mCustomView) == null) {
                return;
            }
            if ((i & 16) != 0) {
                toolbar2.addView(view);
            } else {
                toolbar2.removeView(view);
            }
        }
    }

    public final void updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) != 0) {
            boolean zIsEmpty = TextUtils.isEmpty(this.mHomeDescription);
            Toolbar toolbar = this.mToolbar;
            if (!zIsEmpty) {
                toolbar.setNavigationContentDescription(this.mHomeDescription);
            } else {
                int i = this.mDefaultNavigationContentDescription;
                toolbar.setNavigationContentDescription(i != 0 ? toolbar.getContext().getText(i) : null);
            }
        }
    }

    public final void updateToolbarLogo() {
        Drawable drawable;
        int i = this.mDisplayOpts;
        if ((i & 2) == 0) {
            drawable = null;
        } else if ((i & 1) == 0 || (drawable = this.mLogo) == null) {
            drawable = this.mIcon;
        }
        this.mToolbar.setLogo(drawable);
    }

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z, int i, int i2) {
        Drawable drawable;
        this.mDefaultNavigationContentDescription = 0;
        this.mToolbar = toolbar;
        CharSequence charSequence = toolbar.mTitleText;
        this.mTitle = charSequence;
        this.mSubtitle = toolbar.mSubtitleText;
        this.mTitleSet = charSequence != null;
        this.mNavIcon = toolbar.getNavigationIcon();
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(toolbar.getContext(), null, R$styleable.ActionBar, R.attr.actionBarStyle, 0);
        int i3 = 15;
        this.mDefaultNavigationIcon = tintTypedArrayObtainStyledAttributes.getDrawable(15);
        if (z) {
            CharSequence text = tintTypedArrayObtainStyledAttributes.mWrapped.getText(27);
            if (!TextUtils.isEmpty(text)) {
                this.mTitleSet = true;
                this.mTitle = text;
                if ((this.mDisplayOpts & 8) != 0) {
                    Toolbar toolbar2 = this.mToolbar;
                    toolbar2.setTitle(text);
                    if (this.mTitleSet) {
                        ViewCompat.setAccessibilityPaneTitle(toolbar2.getRootView(), text);
                    }
                }
            }
            CharSequence text2 = tintTypedArrayObtainStyledAttributes.mWrapped.getText(25);
            if (!TextUtils.isEmpty(text2)) {
                this.mSubtitle = text2;
                if ((this.mDisplayOpts & 8) != 0) {
                    toolbar.setSubtitle(text2);
                }
            }
            Drawable drawable2 = tintTypedArrayObtainStyledAttributes.getDrawable(20);
            if (drawable2 != null) {
                this.mLogo = drawable2;
                updateToolbarLogo();
            }
            Drawable drawable3 = tintTypedArrayObtainStyledAttributes.getDrawable(17);
            if (drawable3 != null) {
                this.mIcon = drawable3;
                updateToolbarLogo();
            }
            if (this.mNavIcon == null && (drawable = this.mDefaultNavigationIcon) != null) {
                this.mNavIcon = drawable;
                int i4 = this.mDisplayOpts & 4;
                Toolbar toolbar3 = this.mToolbar;
                if (i4 != 0) {
                    toolbar3.setNavigationIcon(drawable);
                } else {
                    toolbar3.setNavigationIcon(null);
                }
            }
            setDisplayOptions(tintTypedArrayObtainStyledAttributes.mWrapped.getInt(10, 0));
            int resourceId = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(9, 0);
            if (resourceId != 0) {
                View viewInflate = LayoutInflater.from(toolbar.getContext()).inflate(resourceId, (ViewGroup) toolbar, false);
                View view = this.mCustomView;
                if (view != null && (this.mDisplayOpts & 16) != 0) {
                    toolbar.removeView(view);
                }
                this.mCustomView = viewInflate;
                if (viewInflate != null && (this.mDisplayOpts & 16) != 0) {
                    toolbar.addView(viewInflate);
                }
                setDisplayOptions(this.mDisplayOpts | 16);
            }
            int layoutDimension = tintTypedArrayObtainStyledAttributes.mWrapped.getLayoutDimension(13, 0);
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutDimension;
                toolbar.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = tintTypedArrayObtainStyledAttributes.mWrapped.getDimensionPixelOffset(7, -1);
            int dimensionPixelOffset2 = tintTypedArrayObtainStyledAttributes.mWrapped.getDimensionPixelOffset(3, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                int iMax = Math.max(dimensionPixelOffset, 0);
                int iMax2 = Math.max(dimensionPixelOffset2, 0);
                if (toolbar.mContentInsets == null) {
                    toolbar.mContentInsets = new RtlSpacingHelper();
                }
                toolbar.mContentInsets.setRelative(iMax, iMax2);
            }
            int resourceId2 = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(28, 0);
            if (resourceId2 != 0) {
                Context context = toolbar.getContext();
                toolbar.mTitleTextAppearance = resourceId2;
                AppCompatTextView appCompatTextView = toolbar.mTitleTextView;
                if (appCompatTextView != null) {
                    appCompatTextView.setTextAppearance(context, resourceId2);
                }
            }
            int resourceId3 = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(26, 0);
            if (resourceId3 != 0) {
                Context context2 = toolbar.getContext();
                toolbar.mSubtitleTextAppearance = resourceId3;
                AppCompatTextView appCompatTextView2 = toolbar.mSubtitleTextView;
                if (appCompatTextView2 != null) {
                    appCompatTextView2.setTextAppearance(context2, resourceId3);
                }
            }
            int resourceId4 = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(22, 0);
            if (resourceId4 != 0 && toolbar.mPopupTheme != resourceId4) {
                toolbar.mPopupTheme = resourceId4;
                if (resourceId4 == 0) {
                    toolbar.mPopupContext = toolbar.getContext();
                } else {
                    toolbar.mPopupContext = new ContextThemeWrapper(toolbar.getContext(), resourceId4);
                }
            }
        } else {
            if (toolbar.getNavigationIcon() != null) {
                this.mDefaultNavigationIcon = toolbar.getNavigationIcon();
            } else {
                i3 = 11;
            }
            this.mDisplayOpts = i3;
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        if (i != this.mDefaultNavigationContentDescription) {
            this.mDefaultNavigationContentDescription = i;
            AppCompatImageButton appCompatImageButton = toolbar.mNavButtonView;
            if (TextUtils.isEmpty(appCompatImageButton != null ? appCompatImageButton.getContentDescription() : null)) {
                int i5 = this.mDefaultNavigationContentDescription;
                this.mHomeDescription = i5 == 0 ? null : toolbar.getContext().getString(i5);
                updateHomeAccessibility();
            }
        }
        AppCompatImageButton appCompatImageButton2 = toolbar.mNavButtonView;
        this.mHomeDescription = appCompatImageButton2 != null ? appCompatImageButton2.getContentDescription() : null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.ToolbarWidgetWrapper.1
            public final ActionMenuItem mNavItem;

            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, android.R.id.home, 0, 0, ToolbarWidgetWrapper.this.mTitle);
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ToolbarWidgetWrapper toolbarWidgetWrapper = ToolbarWidgetWrapper.this;
                Window.Callback callback = toolbarWidgetWrapper.mWindowCallback;
                if (callback == null || !toolbarWidgetWrapper.mMenuPrepared) {
                    return;
                }
                callback.onMenuItemSelected(0, this.mNavItem);
            }
        });
    }
}
