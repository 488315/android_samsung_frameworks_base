package com.android.systemui.settings.multisim.ui.view;

import android.animation.ArgbEvaluator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Dependency;
import com.android.systemui.DualToneHandler;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.ButtonType;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;
import com.android.systemui.settings.multisim.ui.viewmodel.SlotItem;
import com.android.systemui.settings.multisim.ui.viewmodel.SlotsView;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateListener;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.feature.SemCscFeature;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MultiSIMPreferredSlotView extends LinearLayout implements SlotsView, SecQSExpansionStateListener {
    public final Context mContext;
    public int mCurrentOrientation;
    public DualToneHandler mDualToneHandler;
    public final AnonymousClass1 mIntentReceiver;
    public Locale mLocale;
    public boolean mNightModeOn;
    public PrefferedSlotPopupWindow mPopupWindow;
    public LinearLayout mSlotButtonGroup;
    public int mSlotButtonTextColor;
    public final ArrayList mSlotButtons;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$Button$Layout;
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType;

        static {
            int[] iArr = new int[Button.Layout.values().length];
            $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$Button$Layout = iArr;
            try {
                iArr[Button.Layout.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$Button$Layout[Button.Layout.SIMINFO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$Button$Layout[Button.Layout.TEXTONLY_1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$Button$Layout[Button.Layout.TEXTONLY_2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$Button$Layout[Button.Layout.HIDEDETAILS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[ButtonType.values().length];
            $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType = iArr2;
            try {
                iArr2[ButtonType.VOICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType[ButtonType.SIMINFO1.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType[ButtonType.SMS.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType[ButtonType.DATA.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType[ButtonType.SIMINFO2.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PrefferedSlotButton implements Button {
        public final ViewGroup mButtonView;
        public final TextView mCarrierNameText;
        public final TextView mCategoryText;
        public final Context mContext;
        public final SIMInfoIconManager mIconManager;
        public final ViewGroup mImsDataInfoLine;
        public final ImageView mSimImageForDataInfo;
        public final ImageView mSimImageForSimName;
        public final ViewGroup mSimNameAndImageLine;
        public final TextView mSimNameOrAskText;
        public final TextView mSimNameText;
        public final int mSimSlotId;
        public final ViewGroup mSlotButtonCategoryTextLine;
        public final TextView mTextSimPrimary;
        public final ButtonType mType;

        public PrefferedSlotButton(ButtonType buttonType, Context context, ViewGroup viewGroup) {
            ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.qs_panel_multi_sim_preffered_slot_button, (ViewGroup) null, false);
            this.mButtonView = viewGroup2;
            this.mType = buttonType;
            if (buttonType == ButtonType.SIMINFO2) {
                this.mSimSlotId = 1;
            } else {
                this.mSimSlotId = 0;
            }
            this.mContext = context;
            this.mSlotButtonCategoryTextLine = (ViewGroup) viewGroup2.findViewById(R.id.slot_button_category_text_line);
            this.mSimNameAndImageLine = (ViewGroup) viewGroup2.findViewById(R.id.slot_button_sim_name_and_image_line);
            this.mCategoryText = (TextView) viewGroup2.findViewById(R.id.slot_button_category_text);
            this.mSimNameText = (TextView) viewGroup2.findViewById(R.id.slot_button_sim_name_text);
            this.mCarrierNameText = (TextView) viewGroup2.findViewById(R.id.slot_button_carrier_name_text);
            this.mSimImageForSimName = (ImageView) viewGroup2.findViewById(R.id.slot_button_preferred_sim_image_for_sim_name);
            this.mSimNameOrAskText = (TextView) viewGroup2.findViewById(R.id.slot_button_sim_name_or_ask_text);
            this.mImsDataInfoLine = (ViewGroup) viewGroup2.findViewById(R.id.slot_button_ims_data_info_line);
            this.mSimImageForDataInfo = (ImageView) viewGroup2.findViewById(R.id.slot_button_preferred_sim_image_for_data_info);
            this.mTextSimPrimary = (TextView) viewGroup2.findViewById(R.id.slot_button_primary_sim_text);
            if (isSimInfoButton()) {
                StatusIconContainer statusIconContainer = (StatusIconContainer) viewGroup2.findViewById(R.id.slotNetworkIcons);
                statusIconContainer.mShouldRestrictIcons = false;
                SIMInfoIconManager.Factory factory = ((MultiSIMController) Dependency.sDependency.getDependencyInner(MultiSIMController.class)).mSIMInfoIconManagerFactory;
                SIMInfoIconManager sIMInfoIconManager = new SIMInfoIconManager(statusIconContainer, StatusBarLocation.HOME, factory.mWifiUiAdapter, factory.mMobileUiAdapter, factory.mMobileUiAdapterKairos, factory.mMobileContextProvider, factory.mKairosNetwork, factory.mAppScope, factory.mBTTetherUiAdapter, this.mSimSlotId);
                this.mIconManager = sIMInfoIconManager;
                DualToneHandler dualToneHandler = MultiSIMPreferredSlotView.this.mDualToneHandler;
                DualToneHandler.Color color = dualToneHandler.lightColor;
                int i = (color == null ? null : color).single;
                DualToneHandler.Color color2 = dualToneHandler.darkColor;
                int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(0.0f, Integer.valueOf(i), Integer.valueOf((color2 == null ? null : color2).single))).intValue();
                DualToneHandler dualToneHandler2 = MultiSIMPreferredSlotView.this.mDualToneHandler;
                DualToneHandler.Color color3 = dualToneHandler2.lightColor;
                int i2 = (color3 == null ? null : color3).single;
                DualToneHandler.Color color4 = dualToneHandler2.darkColor;
                sIMInfoIconManager.setTint(intValue, ((Integer) ArgbEvaluator.getInstance().evaluate(0.0f, Integer.valueOf(i2), Integer.valueOf((color4 != null ? color4 : null).single))).intValue());
            }
            TextView textView = this.mCategoryText;
            int[] iArr = AnonymousClass2.$SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType;
            int i3 = iArr[buttonType.ordinal()];
            textView.setText(i3 != 1 ? i3 != 3 ? i3 != 4 ? "" : context.getString(R.string.qs_multisim_data_preffered_btn_title) : context.getString(R.string.qs_multisim_sms_preffered_btn_title) : context.getString(R.string.qs_multisim_voice_preffered_btn_title));
            if (isSimInfoButton()) {
                changeLayout(Button.Layout.SIMINFO);
            } else {
                changeLayout(Button.Layout.NORMAL);
            }
            int i4 = iArr[buttonType.ordinal()];
            if (i4 == 1 || i4 == 2) {
                viewGroup2.setBackground(context.getResources().getDrawable(R.drawable.qs_panel_multi_sim_button_left_ripple_bg));
            } else if (i4 == 3) {
                viewGroup2.setBackground(context.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_middle_ripple_bg));
            } else if (i4 == 4 || i4 == 5) {
                viewGroup2.setBackground(context.getResources().getDrawable(R.drawable.qs_panel_multi_sim_button_right_ripple_bg));
            }
            updateTextColor();
            this.mSimNameText.setAlpha(0.8f);
            this.mCarrierNameText.setAlpha(0.6f);
            this.mSimNameOrAskText.setAlpha(0.8f);
            this.mSimImageForSimName.setAlpha(0.8f);
            viewGroup.addView(viewGroup2, new LinearLayout.LayoutParams(0, -1, 1.0f));
        }

        public final void changeLayout(Button.Layout layout) {
            boolean z;
            int i = AnonymousClass2.$SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$Button$Layout[layout.ordinal()];
            boolean z2 = true;
            if (i == 1) {
                this.mSimNameAndImageLine.setVisibility(0);
                this.mSimNameText.setVisibility(0);
                this.mCarrierNameText.setVisibility(0);
                this.mSimImageForSimName.setVisibility(0);
                this.mSimNameOrAskText.setVisibility(8);
                return;
            }
            if (i == 2) {
                this.mSlotButtonCategoryTextLine.setVisibility(8);
                this.mSimNameAndImageLine.setVisibility(0);
                this.mCarrierNameText.setVisibility(0);
                this.mSimNameOrAskText.setVisibility(0);
                this.mImsDataInfoLine.setVisibility(0);
                return;
            }
            ButtonType buttonType = this.mType;
            if (i == 3) {
                if (buttonType == ButtonType.VOICE) {
                    if (!Operator.isChinaQsTileBranding() && !Operator.QUICK_IS_BRI_BRANDING && !Operator.QUICK_IS_TGY_BRANDING) {
                        z2 = false;
                    }
                    if (z2) {
                        this.mSimNameOrAskText.setText(this.mContext.getString(R.string.qs_multisim_voice_show_all_sim));
                    } else {
                        this.mSimNameOrAskText.setText(this.mContext.getString(R.string.qs_multisim_voice_ask_always));
                    }
                }
                this.mSimNameAndImageLine.setVisibility(8);
                this.mSimImageForSimName.setVisibility(8);
                this.mCarrierNameText.setVisibility(8);
                this.mSimNameOrAskText.setVisibility(0);
                return;
            }
            if (i != 4) {
                if (i != 5) {
                    return;
                }
                this.mSimNameAndImageLine.setVisibility(8);
                this.mSimImageForSimName.setVisibility(8);
                this.mCarrierNameText.setVisibility(8);
                this.mSimNameOrAskText.setVisibility(8);
                return;
            }
            if (buttonType == ButtonType.VOICE) {
                this.mSimNameOrAskText.setText(this.mContext.getString(R.string.qs_multisim_voice_others));
                z = true;
            } else {
                z = false;
            }
            if (buttonType != ButtonType.DATA || Operator.isKoreaQsTileBranding()) {
                z2 = z;
            } else {
                this.mSimNameOrAskText.setText(this.mContext.getString(R.string.qs_multisim_data_turned_off));
            }
            if (z2) {
                this.mSimNameAndImageLine.setVisibility(8);
                this.mSimImageForSimName.setVisibility(8);
                this.mCarrierNameText.setVisibility(8);
                this.mSimNameOrAskText.setVisibility(0);
            }
        }

        public final boolean isSimInfoButton() {
            ButtonType buttonType = ButtonType.SIMINFO1;
            ButtonType buttonType2 = this.mType;
            return buttonType2 == buttonType || buttonType2 == ButtonType.SIMINFO2;
        }

        public final void refreshIconGroup(boolean z) {
            if (!isSimInfoButton() || this.mIconManager == null) {
                return;
            }
            if (z) {
                ((StatusBarIconControllerImpl) ((StatusBarIconController) Dependency.sDependency.getDependencyInner(StatusBarIconController.class))).addIconGroup(this.mIconManager);
            } else {
                ((StatusBarIconControllerImpl) ((StatusBarIconController) Dependency.sDependency.getDependencyInner(StatusBarIconController.class))).removeIconGroup(this.mIconManager);
            }
        }

        public final void setClickListener(final Button.ClickListener clickListener) {
            if (clickListener == null) {
                this.mButtonView.setOnClickListener(null);
                this.mButtonView.setOnLongClickListener(null);
            } else {
                this.mButtonView.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(this, clickListener, 0));
                this.mButtonView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton = MultiSIMPreferredSlotView.PrefferedSlotButton.this;
                        Button.ClickListener clickListener2 = clickListener;
                        MultiSIMPreferredSlotView multiSIMPreferredSlotView = MultiSIMPreferredSlotView.this;
                        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = multiSIMPreferredSlotView.mPopupWindow;
                        if (prefferedSlotPopupWindow != null && prefferedSlotPopupWindow.isShowing()) {
                            multiSIMPreferredSlotView.mPopupWindow.dismiss();
                        }
                        ((MultiSIMViewModelImpl) clickListener2).launchSimManager();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MULTISIM_BAR_LAUNCH_SIM_MANAGER, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                        return false;
                    }
                });
            }
        }

        public final void updateEdgePadding(boolean z) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_preffered_slot_button_padding_edge);
            if (z) {
                ViewGroup viewGroup = this.mButtonView;
                viewGroup.setPaddingRelative(dimensionPixelSize, viewGroup.getPaddingTop(), this.mButtonView.getPaddingEnd(), this.mButtonView.getPaddingBottom());
            } else {
                ViewGroup viewGroup2 = this.mButtonView;
                viewGroup2.setPaddingRelative(viewGroup2.getPaddingStart(), this.mButtonView.getPaddingTop(), dimensionPixelSize, this.mButtonView.getPaddingBottom());
            }
        }

        public final void updateTextColor() {
            SIMInfoIconManager sIMInfoIconManager;
            TextView textView = this.mCategoryText;
            MultiSIMPreferredSlotView multiSIMPreferredSlotView = MultiSIMPreferredSlotView.this;
            textView.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            this.mSimNameText.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            this.mCarrierNameText.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            this.mSimNameOrAskText.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            if (!isSimInfoButton() || (sIMInfoIconManager = this.mIconManager) == null) {
                return;
            }
            DualToneHandler dualToneHandler = multiSIMPreferredSlotView.mDualToneHandler;
            DualToneHandler.Color color = dualToneHandler.lightColor;
            if (color == null) {
                color = null;
            }
            int i = color.single;
            DualToneHandler.Color color2 = dualToneHandler.darkColor;
            if (color2 == null) {
                color2 = null;
            }
            int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(0.0f, Integer.valueOf(i), Integer.valueOf(color2.single))).intValue();
            DualToneHandler dualToneHandler2 = multiSIMPreferredSlotView.mDualToneHandler;
            DualToneHandler.Color color3 = dualToneHandler2.lightColor;
            if (color3 == null) {
                color3 = null;
            }
            int i2 = color3.single;
            DualToneHandler.Color color4 = dualToneHandler2.darkColor;
            sIMInfoIconManager.setTint(intValue, ((Integer) ArgbEvaluator.getInstance().evaluate(0.0f, Integer.valueOf(i2), Integer.valueOf((color4 != null ? color4 : null).single))).intValue());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PrefferedSlotPopupWindow extends PopupWindow {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Context mContext;
        public final View mPopupContentView;
        public final int mPopupNormalTextColor;
        public final int mPopupSelectedTextColor;
        public final int mPopupWindowTopMargin;
        public final ViewGroup mSlotListAskButtonGroup;
        public final TextView mSlotListAskButtonText;
        public final ImageView mSlotListAskCheckedImage;
        public final ViewGroup mSlotListButton1Group;
        public final ViewGroup mSlotListButton2Group;
        public final ImageView mSlotListButtonCheckedImage1;
        public final ImageView mSlotListButtonCheckedImage2;
        public final ImageView mSlotListButtonImage1;
        public final ImageView mSlotListButtonImage2;
        public final TextView mSlotListButtonText1;
        public final TextView mSlotListButtonText2;
        public final TextView mSlotListCarrierName1;
        public final TextView mSlotListCarrierName2;
        public final ViewGroup mSlotListOthersButtonGroup;
        public final TextView mSlotListOthersButtonText;
        public final ImageView mSlotListOthersCheckedImage;
        public final TextView mSlotListPhoneNumber1;
        public final TextView mSlotListPhoneNumber2;
        public final Typeface mPopupSelectedFont = Typeface.create(Typeface.create("sec", 1), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        public final Typeface mPopupNonSelectedFont = Typeface.create(Typeface.create("sec", 0), 400, false);

        public PrefferedSlotPopupWindow(Context context) {
            this.mContext = context;
            this.mPopupWindowTopMargin = context.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_top_margin);
            this.mPopupNormalTextColor = context.getResources().getColor(R.color.sec_qs_multisim_preffered_slot_popup_text_color, null);
            this.mPopupSelectedTextColor = context.getResources().getColor(R.color.sec_qs_multisim_preffered_slot_popup_text_color_select, null);
            View inflate = View.inflate(context, R.layout.qs_panel_multi_sim_preffered_slot_popup_menu, null);
            this.mPopupContentView = inflate;
            this.mSlotListButton1Group = (ViewGroup) inflate.findViewById(R.id.slot1_button);
            this.mSlotListButtonText1 = (TextView) this.mPopupContentView.findViewById(R.id.slot1_button_text);
            this.mSlotListButtonImage1 = (ImageView) this.mPopupContentView.findViewById(R.id.slot1_button_image);
            this.mSlotListButtonCheckedImage1 = (ImageView) this.mPopupContentView.findViewById(R.id.slot1_button_checked_image);
            this.mSlotListCarrierName1 = (TextView) this.mPopupContentView.findViewById(R.id.slot1_button_popup_menu_carrier_name);
            this.mSlotListPhoneNumber1 = (TextView) this.mPopupContentView.findViewById(R.id.slot1_button_popup_menu_phone_number);
            this.mSlotListButton2Group = (ViewGroup) this.mPopupContentView.findViewById(R.id.slot2_button);
            this.mSlotListButtonText2 = (TextView) this.mPopupContentView.findViewById(R.id.slot2_button_text);
            this.mSlotListButtonImage2 = (ImageView) this.mPopupContentView.findViewById(R.id.slot2_button_image);
            this.mSlotListButtonCheckedImage2 = (ImageView) this.mPopupContentView.findViewById(R.id.slot2_button_checked_image);
            this.mSlotListCarrierName2 = (TextView) this.mPopupContentView.findViewById(R.id.slot2_button_popup_menu_carrier_name);
            this.mSlotListPhoneNumber2 = (TextView) this.mPopupContentView.findViewById(R.id.slot2_button_popup_menu_phone_number);
            this.mSlotListAskButtonGroup = (ViewGroup) this.mPopupContentView.findViewById(R.id.ask_button);
            this.mSlotListAskButtonText = (TextView) this.mPopupContentView.findViewById(R.id.ask_button_text);
            this.mSlotListAskCheckedImage = (ImageView) this.mPopupContentView.findViewById(R.id.ask_button_image);
            if (Operator.isChinaQsTileBranding() || Operator.QUICK_IS_BRI_BRANDING || Operator.QUICK_IS_TGY_BRANDING) {
                this.mSlotListAskButtonText.setText(context.getString(R.string.qs_multisim_voice_show_all_sim));
            } else {
                this.mSlotListAskButtonText.setText(context.getString(R.string.qs_multisim_voice_ask_always));
            }
            this.mSlotListOthersButtonGroup = (ViewGroup) this.mPopupContentView.findViewById(R.id.others_button);
            this.mSlotListOthersButtonText = (TextView) this.mPopupContentView.findViewById(R.id.others_button_text);
            this.mSlotListOthersCheckedImage = (ImageView) this.mPopupContentView.findViewById(R.id.others_button_image);
            setContentView(this.mPopupContentView);
            setHeight(-2);
            setWidth(-2);
            setFocusable(true);
            setBackgroundDrawable(context.getResources().getDrawable(R.drawable.qs_panel_multi_sim_popup_menu_bg));
            setElevation(context.getResources().getDimension(R.dimen.qs_multisim_popup_menu_elevation));
            setInputMethodMode(2);
        }

        @Override // android.widget.PopupWindow
        public final void dismiss() {
            super.dismiss();
            this.mSlotListAskButtonGroup.setOnClickListener(null);
            this.mSlotListButton1Group.setOnClickListener(null);
            this.mSlotListButton2Group.setOnClickListener(null);
            this.mSlotListOthersButtonGroup.setOnClickListener(null);
        }

        public final void setSlotListMenuColor(int i, int i2) {
            if (i == 0) {
                this.mSlotListButtonText1.setTextColor(i2);
                this.mSlotListCarrierName1.setTextColor(i2);
                this.mSlotListPhoneNumber1.setTextColor(i2);
            } else {
                if (i != 1) {
                    return;
                }
                this.mSlotListButtonText2.setTextColor(i2);
                this.mSlotListCarrierName2.setTextColor(i2);
                this.mSlotListPhoneNumber2.setTextColor(i2);
            }
        }

        public final void setSlotListMenuFont(ButtonType buttonType, int i) {
            int i2 = AnonymousClass2.$SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType[buttonType.ordinal()];
            if (i2 != 1) {
                if (i2 == 3 || i2 == 4) {
                    if (i == 0) {
                        this.mSlotListButtonText1.setTypeface(this.mPopupSelectedFont);
                        this.mSlotListButtonText2.setTypeface(this.mPopupNonSelectedFont);
                        return;
                    } else {
                        this.mSlotListButtonText1.setTypeface(this.mPopupNonSelectedFont);
                        this.mSlotListButtonText2.setTypeface(this.mPopupSelectedFont);
                        return;
                    }
                }
                return;
            }
            if (i == 0) {
                this.mSlotListAskButtonText.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListButtonText1.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListButtonText2.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListOthersButtonText.setTypeface(this.mPopupNonSelectedFont);
                return;
            }
            if (i == 1) {
                this.mSlotListAskButtonText.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListButtonText1.setTypeface(this.mPopupSelectedFont);
                this.mSlotListButtonText2.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListOthersButtonText.setTypeface(this.mPopupNonSelectedFont);
                return;
            }
            if (i == 2) {
                this.mSlotListAskButtonText.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListButtonText1.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListButtonText2.setTypeface(this.mPopupSelectedFont);
                this.mSlotListOthersButtonText.setTypeface(this.mPopupNonSelectedFont);
                return;
            }
            if (i == 3) {
                this.mSlotListAskButtonText.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListButtonText1.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListButtonText2.setTypeface(this.mPopupNonSelectedFont);
                this.mSlotListOthersButtonText.setTypeface(this.mPopupSelectedFont);
            }
        }

        public final void updateSlotListPopupContents(List list) {
            SlotItem slotItem = (SlotItem) list.get(0);
            this.mSlotListButtonImage1.setImageResource(slotItem.simIconRes);
            this.mSlotListButtonText1.setText(slotItem.simName);
            this.mSlotListCarrierName1.setText(slotItem.carrierName);
            this.mSlotListPhoneNumber1.setText(slotItem.phoneNumber);
            SlotItem slotItem2 = (SlotItem) list.get(1);
            this.mSlotListButtonImage2.setImageResource(slotItem2.simIconRes);
            this.mSlotListButtonText2.setText(slotItem2.simName);
            this.mSlotListCarrierName2.setText(slotItem2.carrierName);
            this.mSlotListPhoneNumber2.setText(slotItem2.phoneNumber);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SIMInfoIconManager extends TintedIconManager {
        public boolean mBlocked;
        public String mSlot;
        public final int mSlotId;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Factory {
            public final CoroutineScope mAppScope;
            public final BTTetherUiAdapter mBTTetherUiAdapter;
            public final KairosNetwork mKairosNetwork;
            public final MobileContextProvider mMobileContextProvider;
            public final MobileUiAdapter mMobileUiAdapter;
            public final Lazy mMobileUiAdapterKairos;
            public final WifiUiAdapter mWifiUiAdapter;

            public Factory(WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, Lazy lazy, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, BTTetherUiAdapter bTTetherUiAdapter) {
                this.mWifiUiAdapter = wifiUiAdapter;
                this.mMobileUiAdapter = mobileUiAdapter;
                this.mMobileContextProvider = mobileContextProvider;
                this.mMobileUiAdapterKairos = lazy;
                this.mKairosNetwork = kairosNetwork;
                this.mAppScope = coroutineScope;
                this.mBTTetherUiAdapter = bTTetherUiAdapter;
            }
        }

        public SIMInfoIconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, Lazy lazy, MobileContextProvider mobileContextProvider, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, BTTetherUiAdapter bTTetherUiAdapter, int i) {
            super(viewGroup, statusBarLocation, wifiUiAdapter, mobileUiAdapter, lazy, mobileContextProvider, kairosNetwork, coroutineScope, bTTetherUiAdapter);
            this.mSlotId = i;
        }

        @Override // com.android.systemui.statusbar.phone.ui.IconManager
        public final LinearLayout.LayoutParams onCreateLayoutParams(StatusBarIcon.Shape shape) {
            return (this.mBlocked && ("mobile".equals(this.mSlot) || "mobile2".equals(this.mSlot))) ? new LinearLayout.LayoutParams(0, 0) : new LinearLayout.LayoutParams(-2, this.mContext.getResources().getDimensionPixelSize(17106382));
        }

        @Override // com.android.systemui.statusbar.phone.ui.TintedIconManager, com.android.systemui.statusbar.phone.ui.IconManager
        public final void onIconAdded(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
            if (this.mSlotId != 0 ? !("ims_volte2".equals(str) || "mobile2".equals(str)) : !("ims_volte".equals(str) || "mobile".equals(str))) {
                z = true;
            }
            this.mBlocked = z;
            this.mSlot = str;
            boolean isVisible = statusBarIconHolder.isVisible();
            statusBarIconHolder.setVisible(isVisible && !z);
            super.onIconAdded(i, str, z, statusBarIconHolder);
            statusBarIconHolder.setVisible(isVisible);
        }

        @Override // com.android.systemui.statusbar.phone.ui.IconManager
        public final void onSetIconHolder(int i, StatusBarIconHolder statusBarIconHolder) {
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) this.mGroup.getChildAt(i);
            if (statusIconDisplayable != null) {
                if (this.mSlotId == 0) {
                    if (!statusIconDisplayable.getSlot().equals("ims_volte") && !statusIconDisplayable.getSlot().equals("mobile")) {
                        return;
                    }
                } else if (!statusIconDisplayable.getSlot().equals("ims_volte2") && !statusIconDisplayable.getSlot().equals("mobile2")) {
                    return;
                }
                super.onSetIconHolder(i, statusBarIconHolder);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView$1] */
    public MultiSIMPreferredSlotView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSlotButtons = new ArrayList();
        this.mNightModeOn = false;
        this.mLocale = null;
        this.mCurrentOrientation = 0;
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                PrefferedSlotPopupWindow prefferedSlotPopupWindow = MultiSIMPreferredSlotView.this.mPopupWindow;
                if (prefferedSlotPopupWindow != null) {
                    prefferedSlotPopupWindow.dismiss();
                }
            }
        };
        this.mContext = context;
    }

    public final PrefferedSlotButton getButton(ButtonType buttonType) {
        PrefferedSlotButton prefferedSlotButton = new PrefferedSlotButton(buttonType, this.mContext, this.mSlotButtonGroup);
        prefferedSlotButton.mButtonView.setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(this.mContext));
        return prefferedSlotButton;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mIntentReceiver);
        SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
        ((List) secQSExpansionStateInteractor.expansionStateListeners$delegate.getValue()).add(this);
        new SecQSExpansionStateChangeEvent(secQSExpansionStateInteractor.getRepository().expandedWithCustomizerOrDetail);
        ArrayList arrayList = this.mSlotButtons;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PrefferedSlotButton) obj).refreshIconGroup(true);
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        super.onConfigurationChanged(configuration);
        int i = this.mCurrentOrientation;
        int i2 = configuration.orientation;
        boolean z2 = true;
        int i3 = 0;
        if (i != i2) {
            this.mCurrentOrientation = i2;
            PrefferedSlotPopupWindow prefferedSlotPopupWindow = this.mPopupWindow;
            if (prefferedSlotPopupWindow != null) {
                prefferedSlotPopupWindow.dismiss();
            }
            z = true;
        } else {
            z = false;
        }
        boolean z3 = (configuration.uiMode & 32) != 0;
        if (this.mNightModeOn != z3) {
            this.mNightModeOn = z3;
            z = true;
        }
        Locale locale = configuration.getLocales().get(0);
        if (locale.equals(this.mLocale)) {
            z2 = z;
        } else {
            this.mLocale = locale;
        }
        if (z2) {
            Log.d("MultiSIMPreferredSlotView", "updateResources()");
            this.mSlotButtonTextColor = this.mContext.getResources().getColor(R.color.qs_multisim_preffered_slot_text_color, null);
            LinearLayout linearLayout = this.mSlotButtonGroup;
            if (linearLayout != null) {
                linearLayout.setDividerDrawable(this.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_button_divider));
                this.mSlotButtonGroup.setDividerPadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.multi_sim_bar_divider_padding));
            }
            ArrayList arrayList = this.mSlotButtons;
            int size = arrayList.size();
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                ((PrefferedSlotButton) obj).updateTextColor();
            }
            PrefferedSlotPopupWindow prefferedSlotPopupWindow2 = this.mPopupWindow;
            if (prefferedSlotPopupWindow2 != null) {
                prefferedSlotPopupWindow2.dismiss();
                this.mPopupWindow = null;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PrefferedSlotPopupWindow prefferedSlotPopupWindow = this.mPopupWindow;
        if (prefferedSlotPopupWindow != null) {
            prefferedSlotPopupWindow.dismiss();
            this.mPopupWindow = null;
        }
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mIntentReceiver);
        ((List) ((SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class)).expansionStateListeners$delegate.getValue()).remove(this);
        ArrayList arrayList = this.mSlotButtons;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PrefferedSlotButton) obj).refreshIconGroup(false);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mNightModeOn = (this.mContext.getResources().getConfiguration().uiMode & 32) != 0;
        this.mCurrentOrientation = this.mContext.getResources().getConfiguration().orientation;
        this.mLocale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.slot_button_group);
        this.mSlotButtonGroup = linearLayout;
        linearLayout.setDividerPadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.multi_sim_bar_divider_padding));
        this.mSlotButtonTextColor = this.mContext.getResources().getColor(R.color.qs_multisim_preffered_slot_text_color, null);
        this.mDualToneHandler = new DualToneHandler(new ContextThemeWrapper(this.mContext, R.style.Theme_SystemUI_QuickSettings_Header));
        this.mSlotButtons.clear();
        this.mSlotButtonGroup.removeAllViews();
        boolean z = Operator.QUICK_IS_VZW_BRANDING;
        if ("US".equals(SemCscFeature.getInstance().getString("CountryISO", "")) || "CA".equals(SemCscFeature.getInstance().getString("CountryISO", ""))) {
            this.mSlotButtons.add(getButton(ButtonType.SIMINFO1));
            this.mSlotButtons.add(getButton(ButtonType.SIMINFO2));
        } else if (!DeviceState.isTablet() || DeviceState.isVoiceCapable(this.mContext)) {
            this.mSlotButtons.add(getButton(ButtonType.VOICE));
            this.mSlotButtons.add(getButton(ButtonType.SMS));
            this.mSlotButtons.add(getButton(ButtonType.DATA));
        } else {
            this.mSlotButtons.add(getButton(ButtonType.DATA));
        }
        ((PrefferedSlotButton) this.mSlotButtons.get(0)).updateEdgePadding(true);
        ((PrefferedSlotButton) AlertController$$ExternalSyntheticOutline0.m(this.mSlotButtons, 1)).updateEdgePadding(false);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return !isEnabled();
    }

    @Override // com.android.systemui.shade.domain.interactor.SecQSExpansionStateListener
    public final void onQSExpansionStateChanged(SecQSExpansionStateChangeEvent secQSExpansionStateChangeEvent) {
        PrefferedSlotPopupWindow prefferedSlotPopupWindow = this.mPopupWindow;
        if (prefferedSlotPopupWindow != null) {
            prefferedSlotPopupWindow.dismiss();
            this.mPopupWindow = null;
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        PrefferedSlotPopupWindow prefferedSlotPopupWindow;
        super.onVisibilityChanged(view, i);
        if (i == 0 || (prefferedSlotPopupWindow = this.mPopupWindow) == null) {
            return;
        }
        prefferedSlotPopupWindow.dismiss();
        this.mPopupWindow = null;
    }
}
