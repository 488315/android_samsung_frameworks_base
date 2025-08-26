package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.util.theme.SeslThemeResourceHelper;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$OpenThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceDrawable$OpenThemeResourceDrawable;
import androidx.appcompat.util.theme.resource.SeslThemeResourceDrawable$ThemeResourceDrawable;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslHoverPopupWindowReflector;
import com.android.systemui.R;
import com.google.android.material.appbar.model.AppBarModel;
import com.google.android.material.appbar.model.ButtonListModel;
import com.google.android.material.appbar.model.ButtonModel;
import com.google.android.material.appbar.model.ButtonStyle;
import com.google.android.material.appbar.model.SuggestAppBarModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public class SuggestAppBarView extends AppBarView {
    private ViewGroup bottomLayout;
    private final List<Button> buttons;
    private ImageButton close;
    private SuggestAppBarModel<? extends SuggestAppBarView> model;
    private TextView titleView;

    /* JADX WARN: Multi-variable type inference failed */
    public SuggestAppBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    private final void addMargin() {
        View view = new View(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(view.getResources().getDimensionPixelOffset(R.dimen.sesl_appbar_button_side_margin), -1));
        ViewGroup viewGroup = this.bottomLayout;
        if (viewGroup != null) {
            viewGroup.addView(view);
        }
    }

    private final Button generateButton(ButtonModel buttonModel, int i) {
        Button button = new Button(getContext(), null, 0, i);
        button.setText(buttonModel.text);
        String str = buttonModel.contentDescription;
        if (str != null) {
            button.setContentDescription(str);
        }
        button.setOnClickListener(new SuggestAppBarView$$ExternalSyntheticLambda0(buttonModel, this, 0));
        return button;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void generateButton$lambda$9$lambda$8(ButtonModel buttonModel, SuggestAppBarView suggestAppBarView, View view) {
        AppBarModel.OnClickListener onClickListener = buttonModel.clickListener;
        if (onClickListener != null) {
            SuggestAppBarModel<? extends SuggestAppBarView> suggestAppBarModel = suggestAppBarView.model;
            onClickListener.onClick();
        }
    }

    private final int getAppBarSuggestTitleColor(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_suggest_title, R.color.sesl_appbar_suggest_title_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_suggest_title, R.color.sesl_appbar_suggest_title_dark_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    private final Drawable getCloseDrawable(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceDrawable$OpenThemeResourceDrawable seslThemeResourceDrawable$OpenThemeResourceDrawable = new SeslThemeResourceDrawable$OpenThemeResourceDrawable(new SeslThemeResourceDrawable$ThemeResourceDrawable(R.drawable.sesl_close_button_recoil_background, R.drawable.sesl_close_button_recoil_background_dark), new SeslThemeResourceDrawable$ThemeResourceDrawable(R.drawable.sesl_close_button_recoil_background_for_theme, R.drawable.sesl_close_button_recoil_background_dark_for_theme));
        companion.getClass();
        return context.getDrawable(seslThemeResourceDrawable$OpenThemeResourceDrawable.getDrawable(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setCloseClickListener$lambda$4$lambda$3(AppBarModel.OnClickListener onClickListener, SuggestAppBarView suggestAppBarView, View view) {
        if (onClickListener != null) {
            SuggestAppBarModel<? extends SuggestAppBarView> suggestAppBarModel = suggestAppBarView.model;
            onClickListener.onClick();
        }
    }

    public final ViewGroup getBottomLayout() {
        return this.bottomLayout;
    }

    public final List<Button> getButtons() {
        return this.buttons;
    }

    public final ImageButton getClose() {
        return this.close;
    }

    public final TextView getTitleView() {
        return this.titleView;
    }

    public void inflate() throws Resources.NotFoundException {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.sesl_app_bar_suggest, (ViewGroup) this, false);
        ImageButton imageButton = null;
        ViewGroup viewGroup = viewInflate instanceof ViewGroup ? (ViewGroup) viewInflate : null;
        if (viewGroup == null) {
            return;
        }
        this.titleView = (TextView) viewGroup.findViewById(R.id.suggest_app_bar_title);
        ImageButton imageButton2 = (ImageButton) viewGroup.findViewById(R.id.suggest_app_bar_close);
        if (imageButton2 != null) {
            SeslViewReflector.semSetHoverPopupType(imageButton2, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
            imageButton = imageButton2;
        }
        this.close = imageButton;
        this.bottomLayout = (ViewGroup) viewGroup.findViewById(R.id.suggest_app_bar_bottom_layout);
        updateResource(getContext());
        addView(viewGroup);
    }

    public final void setBottomLayout(ViewGroup viewGroup) {
        this.bottomLayout = viewGroup;
    }

    public final void setButtonModules(ButtonListModel buttonListModel) {
        ViewGroup viewGroup = this.bottomLayout;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        this.buttons.clear();
        List list = buttonListModel.buttonModels;
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            ButtonModel buttonModel = (ButtonModel) obj;
            boolean zIsLightTheme = SeslMisc.isLightTheme(getContext());
            ButtonStyle buttonStyle = buttonListModel.buttonStyle;
            Button buttonGenerateButton = generateButton(buttonModel, zIsLightTheme ? buttonStyle.defStyleRes : buttonStyle.defStyleResDark);
            buttonGenerateButton.setMaxWidth(buttonGenerateButton.getResources().getDimensionPixelSize(list.size() > 1 ? R.dimen.sesl_appbar_button_max_width : R.dimen.sesl_appbar_button_max_width_multi));
            if (i != 0) {
                addMargin();
            }
            this.buttons.add(buttonGenerateButton);
            ViewGroup viewGroup2 = this.bottomLayout;
            if (viewGroup2 != null) {
                viewGroup2.addView(buttonGenerateButton);
            }
            i = i2;
        }
    }

    public final void setClose(ImageButton imageButton) {
        this.close = imageButton;
    }

    public final void setCloseClickListener(AppBarModel.OnClickListener onClickListener) {
        ImageButton imageButton = this.close;
        if (imageButton != null) {
            imageButton.setVisibility(onClickListener == null ? 8 : 0);
            imageButton.setOnClickListener(new SuggestAppBarView$$ExternalSyntheticLambda0(onClickListener, this, 1));
        }
    }

    public final void setModel(SuggestAppBarModel<? extends SuggestAppBarView> suggestAppBarModel) {
        this.model = suggestAppBarModel;
    }

    public final void setTitle(String str) {
        TextView textView = this.titleView;
        if (textView != null) {
            textView.setText(str);
            textView.setVisibility(TextUtils.isEmpty(str) ? 8 : 0);
        }
    }

    public final void setTitleView(TextView textView) {
        this.titleView = textView;
    }

    @Override // com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) throws Resources.NotFoundException {
        SeslMisc.isLightTheme(context);
        TextView textView = this.titleView;
        if (textView != null) {
            textView.setTextColor(getAppBarSuggestTitleColor(context));
        }
        ImageButton imageButton = this.close;
        if (imageButton != null) {
            String string = imageButton.getResources().getString(R.string.sesl_appbar_suggest_dismiss);
            imageButton.setTooltipText(string);
            imageButton.setContentDescription(string);
            imageButton.setBackground(getCloseDrawable(context));
        }
    }

    public /* synthetic */ SuggestAppBarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public SuggestAppBarView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.buttons = new ArrayList();
        inflate();
    }
}
