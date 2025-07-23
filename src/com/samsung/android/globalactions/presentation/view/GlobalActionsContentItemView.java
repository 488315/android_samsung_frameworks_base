package com.samsung.android.globalactions.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.android.globalactions.presentation.view.GlobalActionsContentView;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;

/* loaded from: classes6.dex */
public class GlobalActionsContentItemView {
    private final Context mContext;
    private final ViewGroup mParent;
    private final ResourceFactory mResourceFactory;
    private final ActionViewModel mViewModel;
    private final ViewStateController mViewStateController;
    private final boolean mVoiceAssistantMode;
    private final boolean mWhiteTheme;
    private final int LABEL_TEXT_SIZE = 15;
    private final int STATE_TEXT_SIZE = 13;
    private final int KEY_SETTINGS_TEXT_SIZE = 17;
    private final String LABEL_TEXT_COLOR_DEFAULT = "#fafaff";
    private final String LABEL_TEXT_COLOR_WHITE_THEME = "#252528";
    private final String STATE_TEXT_COLOR_DEFAULT = "#99999E";
    private final String STATE_TEXT_COLOR_WHITE_THEME = "#848487";
    private final String BUGREPORT_STATE_TEXT_COLOR_WHITE_THEME = "#929295";
    private final String KEY_SETTINGS_COLOR_WHITE_THEME = "#010102";
    private final String KEY_SETTINGS_COLOR_DARK_THEME = "#fafaff";

    public GlobalActionsContentItemView(Context context, ActionViewModel actionViewModel, ViewGroup viewGroup, ResourceFactory resourceFactory, boolean z, boolean z2, ViewStateController viewStateController) {
        this.mContext = context;
        this.mViewModel = actionViewModel;
        this.mParent = viewGroup;
        this.mResourceFactory = resourceFactory;
        this.mVoiceAssistantMode = z;
        this.mWhiteTheme = z2;
        this.mViewStateController = viewStateController;
    }

    public View createView(boolean z) {
        View inflateView = inflateView();
        setViewAttrs(inflateView, z);
        return inflateView;
    }

    public View inflateView() {
        if (this.mViewModel.getActionInfo().getViewType() == ViewType.TOP_VIEW) {
            return LayoutInflater.from(this.mContext).inflate(this.mResourceFactory.get(ResourceType.LAYOUT_TOP_VIEW), this.mParent, false);
        }
        if (this.mViewModel.getActionInfo().getViewType() == ViewType.BOTTOM_BTN_LIST_VIEW) {
            View inflate = LayoutInflater.from(this.mContext).inflate(this.mResourceFactory.get(ResourceType.LAYOUT_BUGREPORT_VIEW), this.mParent, false);
            inflate.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GlobalActionsContentItemView.this.lambda$inflateView$0(view);
                }
            });
            inflate.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean lambda$inflateView$1;
                    lambda$inflateView$1 = GlobalActionsContentItemView.this.lambda$inflateView$1(view);
                    return lambda$inflateView$1;
                }
            });
            if (this.mWhiteTheme) {
                inflate.setBackground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_BG_RAISED_BTN_LIGHT)));
                inflate.setForeground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_LIGHT)));
            } else {
                inflate.setBackground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_BG_RAISED_BTN_DARK)));
                inflate.setForeground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_DARK)));
            }
            inflate.setFocusable(true);
            return inflate;
        }
        if (this.mViewModel.getActionInfo().getViewType() == ViewType.KEY_SETTINGS_VIEW) {
            View inflate2 = LayoutInflater.from(this.mContext).inflate(this.mResourceFactory.get(ResourceType.LAYOUT_SIDEKEY_SETTINGS_VIEW), this.mParent, false);
            inflate2.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GlobalActionsContentItemView.this.lambda$inflateView$2(view);
                }
            });
            inflate2.setFocusable(true);
            if (this.mWhiteTheme) {
                inflate2.setBackground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_BG_RAISED_BTN_LIGHT)));
                inflate2.setForeground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_LIGHT)));
                return inflate2;
            }
            inflate2.setBackground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_BG_RAISED_BTN_DARK)));
            inflate2.setForeground(this.mContext.getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_DARK)));
            return inflate2;
        }
        if (this.mViewModel.getActionInfo().getViewType() == ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW) {
            return LayoutInflater.from(this.mContext).inflate(this.mResourceFactory.get(ResourceType.LAYOUT_FORCE_RESTART_TEXT_VIEW), this.mParent, false);
        }
        return LayoutInflater.from(this.mContext).inflate(this.mResourceFactory.get(ResourceType.LAYOUT_ITEM_LIST_VIEW), this.mParent, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflateView$0(View view) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onPress();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$inflateView$1(View view) {
        if (this.mViewStateController.getState() != ViewAnimationState.IDLE) {
            return true;
        }
        this.mViewModel.onLongPress();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflateView$2(View view) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onPress();
        }
    }

    public void setViewAttrs(View view, boolean z) {
        TextView textView = (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_LABEL));
        TextView textView2 = (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_STATE));
        ImageView imageView = (ImageView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_ICON));
        if (imageView != null) {
            if (this.mViewModel.getActionInfo().getName() == DefaultActionNames.ACTION_KNOX_CUSTOM) {
                imageView.lambda$setImageURIAsync$0(this.mViewModel.getIcon());
                imageView.setContentDescription(this.mViewModel.getText());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayerType(1, null);
            } else {
                imageView.setImageResource(this.mViewModel.getActionInfo().getIcon());
                imageView.setContentDescription(this.mViewModel.getActionInfo().getLabel());
            }
            imageView.setFocusable(true);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GlobalActionsContentItemView.this.lambda$setViewAttrs$3(view2);
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda4
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view2) {
                    boolean lambda$setViewAttrs$4;
                    lambda$setViewAttrs$4 = GlobalActionsContentItemView.this.lambda$setViewAttrs$4(view2);
                    return lambda$setViewAttrs$4;
                }
            });
            if (this.mVoiceAssistantMode) {
                imageView.setForeground(this.mContext.getResources().getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_ICON_RIPPLE), null));
            } else {
                imageView.setForeground(this.mContext.getResources().getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_ICON_BG_FOCUSED), null));
            }
        }
        if (textView != null) {
            checkSingleLine(textView);
            if (this.mViewModel.getActionInfo().getName() == DefaultActionNames.ACTION_KNOX_CUSTOM) {
                textView.lambda$setTextAsync$0(this.mViewModel.getText());
            } else {
                textView.lambda$setTextAsync$0(this.mViewModel.getActionInfo().getLabel());
            }
            if (this.mViewModel.getActionInfo().getViewType() != ViewType.BOTTOM_BTN_LIST_VIEW) {
                textView.setTextColor(this.mWhiteTheme ? Color.parseColor("#252528") : Color.parseColor("#fafaff"));
                setLimitTextSizeToLarge(textView, 15.0f);
            } else {
                textView.setTextColor(this.mWhiteTheme ? Color.parseColor("#010102") : Color.parseColor("#fafaff"));
            }
        }
        if (textView2 != null) {
            textView2.lambda$setTextAsync$0(this.mViewModel.getActionInfo().getStateLabel());
            if (imageView != null) {
                imageView.setContentDescription(((Object) imageView.getContentDescription()) + "," + ((Object) textView2.getText()));
            }
            if (this.mViewModel.getActionInfo().getViewType() == ViewType.KEY_SETTINGS_VIEW) {
                textView2.setTextColor(this.mWhiteTheme ? Color.parseColor("#010102") : Color.parseColor("#fafaff"));
                setLimitTextSizeToLarge(textView2, 17.0f);
            } else if (this.mViewModel.getActionInfo().getViewType() != ViewType.BOTTOM_BTN_LIST_VIEW) {
                textView2.setTextColor(this.mWhiteTheme ? Color.parseColor("#848487") : Color.parseColor("#99999E"));
                if (this.mViewModel.getActionInfo().getViewType() != ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW) {
                    setLimitTextSizeToLarge(textView2, 13.0f);
                }
            } else {
                textView2.setTextColor(this.mWhiteTheme ? Color.parseColor("#929295") : Color.parseColor("#99999E"));
            }
        }
        if (z) {
            TextView textView3 = (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_DESCRIPTION_TEXT));
            textView3.lambda$setTextAsync$0(this.mViewModel.getActionInfo().getDescription());
            textView3.setTextColor(this.mWhiteTheme ? Color.parseColor("#848487") : Color.parseColor("#99999E"));
            setLimitTextSizeToLarge(textView3, 13.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setViewAttrs$3(View view) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onPress();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setViewAttrs$4(View view) {
        if (this.mViewStateController.getState() != ViewAnimationState.IDLE) {
            return true;
        }
        this.mViewModel.onLongPress();
        return true;
    }

    private void checkSingleLine(TextView textView) {
        ViewGroup viewGroup = this.mParent;
        if (viewGroup instanceof GlobalActionsContentView.SamsungGlobalActionsGridView) {
            if (((GlobalActionsContentView.SamsungGlobalActionsGridView) viewGroup).isVerticalMode()) {
                textView.setSingleLine(false);
            } else {
                textView.setSingleLine(true);
            }
        }
    }

    public void setViewIndex(int i) {
        this.mViewModel.getActionInfo().setViewIndex(i);
    }

    private void setLimitTextSizeToLarge(TextView textView, float f) {
        if (textView == null) {
            return;
        }
        float f2 = textView.getContext().getResources().getConfiguration().fontScale;
        if (f2 > 1.1f) {
            f2 = 1.1f;
        }
        textView.setTextSize(1, f * f2);
    }
}
