package com.android.wm.shell.bubbles.bar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.shared.TypefaceUtils;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleBarMenuView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ViewGroup mActionsSectionView;
    public ImageView mBubbleDismissIconView;
    public ImageView mBubbleIconView;
    public ViewGroup mBubbleSectionView;
    public TextView mBubbleTitleView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MenuAction {
        public final Icon mIcon;
        public final View.OnClickListener mOnClick;
        public final int mTint;
        public final String mTitle;

        public MenuAction(Icon icon, String str, View.OnClickListener onClickListener) {
            this(icon, str, 0, onClickListener);
        }

        public MenuAction(Icon icon, String str, int i, View.OnClickListener onClickListener) {
            this.mIcon = icon;
            this.mTitle = str;
            this.mTint = i;
            this.mOnClick = onClickListener;
        }
    }

    public BubbleBarMenuView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final float getAlpha() {
        return this.mBubbleSectionView.getAlpha();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBubbleSectionView = (ViewGroup) findViewById(R.id.bubble_bar_manage_menu_bubble_section);
        this.mActionsSectionView = (ViewGroup) findViewById(R.id.bubble_bar_manage_menu_actions_section);
        this.mBubbleIconView = (ImageView) findViewById(R.id.bubble_bar_manage_menu_bubble_icon);
        this.mBubbleTitleView = (TextView) findViewById(R.id.bubble_bar_manage_menu_bubble_title);
        TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
        TypefaceUtils.setTypeface();
        this.mBubbleDismissIconView = (ImageView) findViewById(R.id.bubble_bar_manage_menu_dismiss_icon);
        this.mActionsSectionView.getBackground().setTint(((LinearLayout) this).mContext.getColor(android.R.color.side_fps_toast_background));
        this.mBubbleDismissIconView.setImageTintList(ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(android.R.color.search_url_text_material_light)));
        this.mBubbleSectionView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuView.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, BubbleBarMenuView.this.getResources().getString(R.string.bubble_accessibility_action_collapse_menu)));
            }
        });
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        this.mBubbleSectionView.setAlpha(f);
        this.mActionsSectionView.setAlpha(f);
    }

    public final void updateActions(ArrayList arrayList) {
        this.mActionsSectionView.removeAllViews();
        LayoutInflater from = LayoutInflater.from(((LinearLayout) this).mContext);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            MenuAction menuAction = (MenuAction) obj;
            BubbleBarMenuItemView bubbleBarMenuItemView = (BubbleBarMenuItemView) from.inflate(R.layout.bubble_bar_menu_item, this.mActionsSectionView, false);
            Icon icon = menuAction.mIcon;
            int i2 = menuAction.mTint;
            if (i2 == 0) {
                bubbleBarMenuItemView.mTextView.setTextColor(bubbleBarMenuItemView.getContext().getColor(android.R.color.search_url_text_material_light));
            } else {
                bubbleBarMenuItemView.getClass();
                icon.setTint(i2);
                bubbleBarMenuItemView.mTextView.setTextColor(i2);
            }
            bubbleBarMenuItemView.mImageView.setImageIcon(icon);
            bubbleBarMenuItemView.mTextView.setText(menuAction.mTitle);
            bubbleBarMenuItemView.setOnClickListener(menuAction.mOnClick);
            this.mActionsSectionView.addView(bubbleBarMenuItemView);
        }
    }

    public BubbleBarMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleBarMenuView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BubbleBarMenuView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
