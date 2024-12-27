package com.samsung.android.globalactions.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;

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

    public GlobalActionsContentItemView(
            Context context,
            ActionViewModel viewModel,
            ViewGroup viewGroup,
            ResourceFactory resourceFactory,
            boolean isVoiceAssistantMode,
            boolean isWhiteTheme,
            ViewStateController stateController) {
        this.mContext = context;
        this.mViewModel = viewModel;
        this.mParent = viewGroup;
        this.mResourceFactory = resourceFactory;
        this.mVoiceAssistantMode = isVoiceAssistantMode;
        this.mWhiteTheme = isWhiteTheme;
        this.mViewStateController = stateController;
    }

    public View createView(boolean isForConfirming) {
        View view = inflateView();
        setViewAttrs(view, isForConfirming);
        return view;
    }

    public View inflateView() {
        if (this.mViewModel.getActionInfo().getViewType() == ViewType.TOP_VIEW) {
            View view =
                    LayoutInflater.from(this.mContext)
                            .inflate(
                                    this.mResourceFactory.get(ResourceType.LAYOUT_TOP_VIEW),
                                    this.mParent,
                                    false);
            return view;
        }
        if (this.mViewModel.getActionInfo().getViewType() == ViewType.BOTTOM_BTN_LIST_VIEW) {
            View view2 =
                    LayoutInflater.from(this.mContext)
                            .inflate(
                                    this.mResourceFactory.get(ResourceType.LAYOUT_BUGREPORT_VIEW),
                                    this.mParent,
                                    false);
            view2.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            GlobalActionsContentItemView.this.lambda$inflateView$0(view3);
                        }
                    });
            view2.setOnLongClickListener(
                    new View
                            .OnLongClickListener() { // from class:
                                                     // com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view3) {
                            boolean lambda$inflateView$1;
                            lambda$inflateView$1 =
                                    GlobalActionsContentItemView.this.lambda$inflateView$1(view3);
                            return lambda$inflateView$1;
                        }
                    });
            if (this.mWhiteTheme) {
                view2.setBackground(
                        this.mContext.getDrawable(
                                this.mResourceFactory.get(
                                        ResourceType.DRAWABLE_BG_RAISED_BTN_LIGHT)));
                view2.setForeground(
                        this.mContext.getDrawable(
                                this.mResourceFactory.get(
                                        ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_LIGHT)));
            } else {
                view2.setBackground(
                        this.mContext.getDrawable(
                                this.mResourceFactory.get(
                                        ResourceType.DRAWABLE_BG_RAISED_BTN_DARK)));
                view2.setForeground(
                        this.mContext.getDrawable(
                                this.mResourceFactory.get(
                                        ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_DARK)));
            }
            view2.setFocusable(true);
            return view2;
        }
        if (this.mViewModel.getActionInfo().getViewType() == ViewType.KEY_SETTINGS_VIEW) {
            View view3 =
                    LayoutInflater.from(this.mContext)
                            .inflate(
                                    this.mResourceFactory.get(
                                            ResourceType.LAYOUT_SIDEKEY_SETTINGS_VIEW),
                                    this.mParent,
                                    false);
            view3.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view4) {
                            GlobalActionsContentItemView.this.lambda$inflateView$2(view4);
                        }
                    });
            view3.setFocusable(true);
            if (this.mWhiteTheme) {
                view3.setBackground(
                        this.mContext.getDrawable(
                                this.mResourceFactory.get(
                                        ResourceType.DRAWABLE_BG_RAISED_BTN_LIGHT)));
                view3.setForeground(
                        this.mContext.getDrawable(
                                this.mResourceFactory.get(
                                        ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_LIGHT)));
                return view3;
            }
            view3.setBackground(
                    this.mContext.getDrawable(
                            this.mResourceFactory.get(ResourceType.DRAWABLE_BG_RAISED_BTN_DARK)));
            view3.setForeground(
                    this.mContext.getDrawable(
                            this.mResourceFactory.get(
                                    ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_DARK)));
            return view3;
        }
        if (this.mViewModel.getActionInfo().getViewType()
                == ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW) {
            View view4 =
                    LayoutInflater.from(this.mContext)
                            .inflate(
                                    this.mResourceFactory.get(
                                            ResourceType.LAYOUT_FORCE_RESTART_TEXT_VIEW),
                                    this.mParent,
                                    false);
            return view4;
        }
        View view5 =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                this.mResourceFactory.get(ResourceType.LAYOUT_ITEM_LIST_VIEW),
                                this.mParent,
                                false);
        return view5;
    }

    public /* synthetic */ void lambda$inflateView$0(View l) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onPress();
        }
    }

    public /* synthetic */ boolean lambda$inflateView$1(View l) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onLongPress();
            return true;
        }
        return true;
    }

    public /* synthetic */ void lambda$inflateView$2(View l) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onPress();
        }
    }

    public void setViewAttrs(View view, boolean isForConfirming) {
        TextView labelView =
                (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_LABEL));
        TextView stateView =
                (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_STATE));
        ImageView imageView =
                (ImageView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_ICON));
        if (imageView != null) {
            if (this.mViewModel.getActionInfo().getName()
                    == DefaultActionNames.ACTION_KNOX_CUSTOM) {
                imageView.lambda$setImageURIAsync$0(this.mViewModel.getIcon());
                imageView.setContentDescription(this.mViewModel.getText());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayerType(1, null);
            } else {
                imageView.setImageResource(this.mViewModel.getActionInfo().getIcon());
                imageView.setContentDescription(this.mViewModel.getActionInfo().getLabel());
            }
            imageView.setFocusable(true);
            imageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            GlobalActionsContentItemView.this.lambda$setViewAttrs$3(view2);
                        }
                    });
            imageView.setOnLongClickListener(
                    new View
                            .OnLongClickListener() { // from class:
                                                     // com.samsung.android.globalactions.presentation.view.GlobalActionsContentItemView$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view2) {
                            boolean lambda$setViewAttrs$4;
                            lambda$setViewAttrs$4 =
                                    GlobalActionsContentItemView.this.lambda$setViewAttrs$4(view2);
                            return lambda$setViewAttrs$4;
                        }
                    });
            if (this.mVoiceAssistantMode) {
                imageView.setForeground(
                        this.mContext
                                .getResources()
                                .getDrawable(
                                        this.mResourceFactory.get(
                                                ResourceType.DRAWABLE_ICON_RIPPLE),
                                        null));
            } else {
                imageView.setForeground(
                        this.mContext
                                .getResources()
                                .getDrawable(
                                        this.mResourceFactory.get(
                                                ResourceType.DRAWABLE_ICON_BG_FOCUSED),
                                        null));
            }
        }
        if (labelView != null) {
            checkSingleLine(labelView);
            if (this.mViewModel.getActionInfo().getName()
                    == DefaultActionNames.ACTION_KNOX_CUSTOM) {
                labelView.lambda$setTextAsync$0(this.mViewModel.getText());
            } else {
                labelView.lambda$setTextAsync$0(this.mViewModel.getActionInfo().getLabel());
            }
            if (this.mViewModel.getActionInfo().getViewType() != ViewType.BOTTOM_BTN_LIST_VIEW) {
                labelView.setTextColor(
                        this.mWhiteTheme
                                ? Color.parseColor("#252528")
                                : Color.parseColor("#fafaff"));
                setLimitTextSizeToLarge(labelView, 15.0f);
            } else {
                labelView.setTextColor(
                        this.mWhiteTheme
                                ? Color.parseColor("#010102")
                                : Color.parseColor("#fafaff"));
            }
        }
        if (stateView != null) {
            stateView.lambda$setTextAsync$0(this.mViewModel.getActionInfo().getStateLabel());
            if (imageView != null) {
                imageView.setContentDescription(
                        ((Object) imageView.getContentDescription())
                                + ","
                                + ((Object) stateView.getText()));
            }
            if (this.mViewModel.getActionInfo().getViewType() == ViewType.KEY_SETTINGS_VIEW) {
                stateView.setTextColor(
                        this.mWhiteTheme
                                ? Color.parseColor("#010102")
                                : Color.parseColor("#fafaff"));
                setLimitTextSizeToLarge(stateView, 17.0f);
            } else if (this.mViewModel.getActionInfo().getViewType()
                    != ViewType.BOTTOM_BTN_LIST_VIEW) {
                stateView.setTextColor(
                        this.mWhiteTheme
                                ? Color.parseColor("#848487")
                                : Color.parseColor("#99999E"));
                if (this.mViewModel.getActionInfo().getViewType()
                        != ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW) {
                    setLimitTextSizeToLarge(stateView, 13.0f);
                }
            } else {
                stateView.setTextColor(
                        this.mWhiteTheme
                                ? Color.parseColor("#929295")
                                : Color.parseColor("#99999E"));
            }
        }
        if (isForConfirming) {
            TextView descriptionView =
                    (TextView)
                            view.findViewById(
                                    this.mResourceFactory.get(ResourceType.ID_DESCRIPTION_TEXT));
            descriptionView.lambda$setTextAsync$0(this.mViewModel.getActionInfo().getDescription());
            descriptionView.setTextColor(
                    this.mWhiteTheme ? Color.parseColor("#848487") : Color.parseColor("#99999E"));
            setLimitTextSizeToLarge(descriptionView, 13.0f);
        }
    }

    public /* synthetic */ void lambda$setViewAttrs$3(View l) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onPress();
        }
    }

    public /* synthetic */ boolean lambda$setViewAttrs$4(View l) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE) {
            this.mViewModel.onLongPress();
            return true;
        }
        return true;
    }

    private void checkSingleLine(TextView view) {
        if (this.mParent instanceof GlobalActionsContentView.SamsungGlobalActionsGridView) {
            boolean isVertical =
                    ((GlobalActionsContentView.SamsungGlobalActionsGridView) this.mParent)
                            .isVerticalMode();
            if (isVertical) {
                view.setSingleLine(false);
            } else {
                view.setSingleLine(true);
            }
        }
    }

    public void setViewIndex(int index) {
        this.mViewModel.getActionInfo().setViewIndex(index);
    }

    private void setLimitTextSizeToLarge(TextView textView, float textSizeSP) {
        if (textView == null) {
            return;
        }
        float fontScale = textView.getContext().getResources().getConfiguration().fontScale;
        if (fontScale > 1.1f) {
            fontScale = 1.1f;
        }
        textView.setTextSize(1, textSizeSP * fontScale);
    }
}
