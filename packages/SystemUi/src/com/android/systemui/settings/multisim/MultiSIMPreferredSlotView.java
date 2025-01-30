package com.android.systemui.settings.multisim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
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
import com.android.systemui.Dependency;
import com.android.systemui.DualToneHandler;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.MultiSIMPreferredSlotView;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.connectivity.p020ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.p028ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.WifiUiAdapter;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.volte2.data.VolteConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MultiSIMPreferredSlotView extends LinearLayout implements MultiSIMController.MultiSIMDataChangedCallback, MultiSIMController.MultiSIMVisibilityChangedCallback {
    public static final MultiSIMController.ButtonType[] DATA_ONLY_BUTTON_LIST;
    public static final MultiSIMController.ButtonType[] PREFERRED_BUTTON_LIST;
    public static final MultiSIMController.ButtonType[] SIM_INFO_BUTTON_LIST;
    public boolean mChangedByDataButton;
    public final Context mContext;
    public MultiSIMController mController;
    public int mCurrentOrientation;
    public MultiSIMData mData;
    public DualToneHandler mDualToneHandler;
    public TypedArray mESIMIconArray;
    public final C24181 mIntentReceiver;
    public Locale mLocale;
    public boolean mNightModeOn;
    public TypedArray mPSimIconArray;
    public PrefferedSlotPopupWindow mPopupWindow;
    public final SecTouchLogHelper mSecTouchLogHelper;
    public LinearLayout mSlotButtonGroup;
    public int mSlotButtonTextColor;
    public final ArrayList mSlotButtons;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.settings.multisim.MultiSIMPreferredSlotView$2 */
    public abstract /* synthetic */ class AbstractC24192 {

        /* renamed from: $SwitchMap$com$android$systemui$settings$multisim$MultiSIMController$ButtonType */
        public static final /* synthetic */ int[] f340x9f70d468;

        static {
            int[] iArr = new int[MultiSIMController.ButtonType.values().length];
            f340x9f70d468 = iArr;
            try {
                iArr[MultiSIMController.ButtonType.VOICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f340x9f70d468[MultiSIMController.ButtonType.SMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f340x9f70d468[MultiSIMController.ButtonType.DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f340x9f70d468[MultiSIMController.ButtonType.SIMINFO1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f340x9f70d468[MultiSIMController.ButtonType.SIMINFO2.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PrefferedSlotButton {
        public final ViewGroup mButtonView;
        public TextView mCarrierNameText;
        public TextView mCategoryText;
        public final Context mContext;
        public SIMInfoIconManager mIconManager;
        public ViewGroup mImsDataInfoLine;
        public ImageView mSimImageForDataInfo;
        public ImageView mSimImageForSimName;
        public ViewGroup mSimNameAndImageLine;
        public TextView mSimNameOrAskText;
        public TextView mSimNameText;
        public final int mSimSlotId;
        public TextView mTextSimPrimary;
        public final MultiSIMController.ButtonType mType;

        public PrefferedSlotButton(MultiSIMController.ButtonType buttonType, Context context, ViewGroup viewGroup) {
            ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.qs_panel_multi_sim_preffered_slot_button, (ViewGroup) null, false);
            this.mButtonView = viewGroup2;
            this.mType = buttonType;
            if (buttonType == MultiSIMController.ButtonType.SIMINFO2) {
                this.mSimSlotId = 1;
            } else {
                this.mSimSlotId = 0;
            }
            this.mContext = context;
            this.mImsDataInfoLine = (ViewGroup) viewGroup2.findViewById(R.id.slot_button_ims_data_info_line);
            this.mSimNameAndImageLine = (ViewGroup) viewGroup2.findViewById(R.id.slot_button_sim_name_and_image_line);
            this.mCategoryText = (TextView) viewGroup2.findViewById(R.id.slot_button_category_text_line);
            this.mSimNameText = (TextView) viewGroup2.findViewById(R.id.slot_button_sim_name_text);
            this.mCarrierNameText = (TextView) viewGroup2.findViewById(R.id.slot_button_carrier_name_text);
            this.mSimImageForDataInfo = (ImageView) viewGroup2.findViewById(R.id.slot_button_preferred_sim_image_for_data_info);
            this.mSimImageForSimName = (ImageView) viewGroup2.findViewById(R.id.slot_button_preferred_sim_image_for_sim_name);
            this.mSimNameOrAskText = (TextView) viewGroup2.findViewById(R.id.slot_button_sim_name_or_ask_text);
            this.mTextSimPrimary = (TextView) viewGroup2.findViewById(R.id.slot_button_primary_sim_text);
            if (isSimInfoButton()) {
                StatusIconContainer statusIconContainer = (StatusIconContainer) viewGroup2.findViewById(R.id.slotNetworkIcons);
                statusIconContainer.mShouldRestrictIcons = false;
                SIMInfoIconManager.Factory factory = ((MultiSIMController) Dependency.get(MultiSIMController.class)).mSIMInfoIconManagerFactory;
                SIMInfoIconManager sIMInfoIconManager = new SIMInfoIconManager(statusIconContainer, StatusBarLocation.HOME, factory.mStatusBarPipelineFlags, factory.mWifiUiAdapter, factory.mMobileUiAdapter, factory.mMobileContextProvider, factory.mBtTetherUiAdapter, this.mSimSlotId);
                this.mIconManager = sIMInfoIconManager;
                sIMInfoIconManager.setTint(MultiSIMPreferredSlotView.this.mDualToneHandler.getSingleColor(0.0f));
            }
            TextView textView = this.mCategoryText;
            int i = AbstractC24192.f340x9f70d468[buttonType.ordinal()];
            textView.setText(i != 1 ? i != 2 ? i != 3 ? "" : context.getString(R.string.qs_multisim_data_preffered_btn_title) : context.getString(R.string.qs_multisim_sms_preffered_btn_title) : context.getString(R.string.qs_multisim_voice_preffered_btn_title));
            updateTextColor();
            refreshSlotInfo();
            viewGroup2.setOnClickListener(new ViewOnClickListenerC2415x920e5b30(this, 0));
            viewGroup2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.settings.multisim.MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    MultiSIMPreferredSlotView multiSIMPreferredSlotView = MultiSIMPreferredSlotView.this;
                    MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = multiSIMPreferredSlotView.mPopupWindow;
                    if (prefferedSlotPopupWindow != null && prefferedSlotPopupWindow.isShowing()) {
                        multiSIMPreferredSlotView.mPopupWindow.dismiss();
                    }
                    multiSIMPreferredSlotView.mController.launchSimManager();
                    SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1015", "QUICK_PANEL_LAYOUT");
                    return false;
                }
            });
            if (isSimInfoButton()) {
                viewGroup2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(MultiSIMPreferredSlotView.this) { // from class: com.android.systemui.settings.multisim.MultiSIMPreferredSlotView.PrefferedSlotButton.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewAttachedToWindow(View view) {
                        PrefferedSlotButton prefferedSlotButton = PrefferedSlotButton.this;
                        if (prefferedSlotButton.mIconManager != null) {
                            ((StatusBarIconControllerImpl) ((StatusBarIconController) Dependency.get(StatusBarIconController.class))).addIconGroup(prefferedSlotButton.mIconManager);
                        }
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewDetachedFromWindow(View view) {
                        PrefferedSlotButton prefferedSlotButton = PrefferedSlotButton.this;
                        if (prefferedSlotButton.mIconManager != null) {
                            ((StatusBarIconControllerImpl) ((StatusBarIconController) Dependency.get(StatusBarIconController.class))).removeIconGroup(prefferedSlotButton.mIconManager);
                        }
                        view.removeOnAttachStateChangeListener(this);
                    }
                });
            }
            viewGroup.addView(viewGroup2, new LinearLayout.LayoutParams(0, -1, 1.0f));
        }

        public final boolean isSimInfoButton() {
            if (Operator.supportNetworkInfoAtMultisimBar()) {
                MultiSIMController.ButtonType buttonType = MultiSIMController.ButtonType.SIMINFO1;
                MultiSIMController.ButtonType buttonType2 = this.mType;
                if (buttonType2 == buttonType || buttonType2 == MultiSIMController.ButtonType.SIMINFO2) {
                    return true;
                }
            }
            return false;
        }

        public final void refreshSlotInfo() {
            int[] iArr = AbstractC24192.f340x9f70d468;
            MultiSIMController.ButtonType buttonType = this.mType;
            int i = iArr[buttonType.ordinal()];
            boolean z = true;
            MultiSIMPreferredSlotView multiSIMPreferredSlotView = MultiSIMPreferredSlotView.this;
            int i2 = i != 1 ? i != 2 ? (i == 3 || i == 4 || i == 5) ? multiSIMPreferredSlotView.mData.defaultDataSimId : 0 : multiSIMPreferredSlotView.mData.defaultSmsSimId : multiSIMPreferredSlotView.mData.defaultVoiceSimId;
            MultiSIMController.ButtonType buttonType2 = MultiSIMController.ButtonType.VOICE;
            if (buttonType == buttonType2) {
                i2--;
            } else if (i2 < 0 || i2 > 1) {
                i2 = 0;
            }
            Context context = this.mContext;
            if (buttonType == buttonType2 && i2 < 0) {
                this.mSimNameAndImageLine.setVisibility(8);
                if (!Operator.isChinaQsTileBranding()) {
                    if (!(Operator.QUICK_IS_BRI_BRANDING || Operator.QUICK_IS_TGY_BRANDING)) {
                        z = false;
                    }
                }
                if (z) {
                    this.mSimNameOrAskText.setText(context.getString(R.string.qs_multisim_voice_show_all_sim));
                } else {
                    this.mSimNameOrAskText.setText(context.getString(R.string.qs_multisim_voice_ask_always));
                }
                this.mSimNameOrAskText.setVisibility(0);
                this.mCarrierNameText.setVisibility(8);
                return;
            }
            if (buttonType == buttonType2 && i2 > 1) {
                this.mSimNameAndImageLine.setVisibility(8);
                this.mSimNameOrAskText.setText(context.getString(R.string.qs_multisim_voice_others));
                this.mSimNameOrAskText.setVisibility(0);
                this.mCarrierNameText.setVisibility(8);
                return;
            }
            MultiSIMController.ButtonType buttonType3 = MultiSIMController.ButtonType.DATA;
            if (buttonType == buttonType3 && (true ^ Operator.isKoreaQsTileBranding()) && !multiSIMPreferredSlotView.mData.isDataEnabled && !multiSIMPreferredSlotView.mChangedByDataButton) {
                this.mSimNameAndImageLine.setVisibility(8);
                this.mSimNameOrAskText.setText(context.getString(R.string.qs_multisim_data_turned_off));
                this.mSimNameOrAskText.setVisibility(0);
                this.mCarrierNameText.setVisibility(8);
                return;
            }
            if (buttonType == buttonType3 && multiSIMPreferredSlotView.mController.isDataBlocked(multiSIMPreferredSlotView.mData.defaultDataSimId)) {
                this.mSimNameAndImageLine.setVisibility(8);
                this.mSimNameOrAskText.setVisibility(8);
                this.mCarrierNameText.setVisibility(8);
                return;
            }
            if (buttonType == buttonType2 || buttonType == MultiSIMController.ButtonType.SMS || buttonType == buttonType3) {
                this.mSimNameOrAskText.setVisibility(8);
                this.mSimImageForSimName.setImageResource(MultiSIMPreferredSlotView.m1643$$Nest$mgetSimIcon(multiSIMPreferredSlotView, i2));
                this.mSimNameText.setText(multiSIMPreferredSlotView.mData.simName[i2]);
                this.mSimNameAndImageLine.setVisibility(0);
                this.mCarrierNameText.setText(multiSIMPreferredSlotView.mData.carrierName[i2]);
                this.mCarrierNameText.setVisibility(0);
                return;
            }
            this.mImsDataInfoLine.setVisibility(0);
            this.mCategoryText.setVisibility(8);
            ImageView imageView = this.mSimImageForDataInfo;
            int i3 = this.mSimSlotId;
            imageView.setImageResource(MultiSIMPreferredSlotView.m1643$$Nest$mgetSimIcon(multiSIMPreferredSlotView, i3));
            if (i3 == i2) {
                this.mTextSimPrimary.setVisibility(0);
            } else {
                this.mTextSimPrimary.setVisibility(8);
            }
            this.mSimNameOrAskText.setText(multiSIMPreferredSlotView.mData.simName[i3]);
            this.mSimNameOrAskText.setVisibility(0);
            this.mSimNameAndImageLine.setVisibility(8);
            this.mCarrierNameText.setText(multiSIMPreferredSlotView.mData.carrierName[i3]);
            this.mCarrierNameText.setVisibility(0);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0097  */
        /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateTextColor() {
            SIMInfoIconManager sIMInfoIconManager;
            TextView textView = this.mCategoryText;
            MultiSIMPreferredSlotView multiSIMPreferredSlotView = MultiSIMPreferredSlotView.this;
            textView.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            this.mSimNameText.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            this.mCarrierNameText.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            this.mSimNameOrAskText.setTextColor(multiSIMPreferredSlotView.mSlotButtonTextColor);
            if (isSimInfoButton() && (sIMInfoIconManager = this.mIconManager) != null) {
                sIMInfoIconManager.setTint(multiSIMPreferredSlotView.mDualToneHandler.getSingleColor(0.0f));
            }
            this.mCategoryText.setAlpha(0.74f);
            MultiSIMController.ButtonType buttonType = MultiSIMController.ButtonType.DATA;
            MultiSIMController.ButtonType buttonType2 = this.mType;
            ViewGroup viewGroup = this.mButtonView;
            if (buttonType2 != buttonType || (!multiSIMPreferredSlotView.mChangedByDataButton && !multiSIMPreferredSlotView.mData.changingNetMode)) {
                MultiSIMData multiSIMData = multiSIMPreferredSlotView.mData;
                if (!multiSIMData.airplaneMode && !multiSIMData.isCalling && !multiSIMData.isSRoaming && !multiSIMData.isRestrictionsForMmsUse) {
                    this.mSimNameText.setAlpha(0.74f);
                    this.mCarrierNameText.setAlpha(0.74f);
                    this.mSimNameOrAskText.setAlpha(0.74f);
                    this.mSimImageForSimName.setAlpha(0.74f);
                    if (viewGroup == null) {
                        int i = AbstractC24192.f340x9f70d468[buttonType2.ordinal()];
                        Context context = this.mContext;
                        if (i != 1) {
                            if (i == 2) {
                                viewGroup.setBackground(context.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_middle_ripple_bg));
                                return;
                            }
                            if (i != 3) {
                                if (i != 4) {
                                    if (i != 5) {
                                        return;
                                    }
                                }
                            }
                            viewGroup.setBackground(context.getResources().getDrawable(R.drawable.qs_panel_multi_sim_button_right_ripple_bg));
                            return;
                        }
                        viewGroup.setBackground(context.getResources().getDrawable(R.drawable.qs_panel_multi_sim_button_left_ripple_bg));
                        return;
                    }
                    return;
                }
            }
            this.mSimNameText.setAlpha(0.35f);
            this.mCarrierNameText.setAlpha(0.35f);
            this.mSimNameOrAskText.setAlpha(0.35f);
            this.mSimImageForSimName.setAlpha(0.35f);
            if (viewGroup != null) {
                viewGroup.setFocusable(false);
                viewGroup.setBackground(null);
            }
            if (viewGroup == null) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PrefferedSlotPopupWindow extends PopupWindow {
        public final Context mContext;
        public View mPopupContentView;
        public int mPopupNormalTextColor;
        public int mPopupSelectedTextColor;
        public int mPopupWindowTopMargin;
        public ViewGroup mSlotListAskButtonGroup;
        public TextView mSlotListAskButtonText;
        public ImageView mSlotListAskCheckedImage;
        public ViewGroup mSlotListButton1Group;
        public ViewGroup mSlotListButton2Group;
        public ImageView mSlotListButtonCheckedImage1;
        public ImageView mSlotListButtonCheckedImage2;
        public ImageView mSlotListButtonImage1;
        public ImageView mSlotListButtonImage2;
        public TextView mSlotListButtonText1;
        public TextView mSlotListButtonText2;
        public TextView mSlotListCarrierName1;
        public TextView mSlotListCarrierName2;
        public ViewGroup mSlotListOthersButtonGroup;
        public TextView mSlotListOthersButtonText;
        public ImageView mSlotListOthersCheckedImage;
        public TextView mSlotListPhoneNumber1;
        public TextView mSlotListPhoneNumber2;
        public Typeface mPopupSelectedFont = Typeface.create(Typeface.create("sec", 1), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        public Typeface mPopupNonSelectedFont = Typeface.create(Typeface.create("sec", 0), 400, false);

        /* JADX WARN: Code restructure failed: missing block: B:8:0x0128, code lost:
        
            if ((com.android.systemui.Operator.QUICK_IS_BRI_BRANDING || com.android.systemui.Operator.QUICK_IS_TGY_BRANDING) != false) goto L12;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public PrefferedSlotPopupWindow(Context context) {
            this.mContext = context;
            this.mPopupWindowTopMargin = context.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_top_margin);
            this.mPopupNormalTextColor = context.getResources().getColor(R.color.sec_qs_multisim_preffered_slot_popup_text_color, null);
            this.mPopupSelectedTextColor = context.getResources().getColor(R.color.sec_qs_multisim_preffered_slot_popup_text_color_select, null);
            boolean z = false;
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
            if (!Operator.isChinaQsTileBranding()) {
            }
            z = true;
            if (z) {
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
            updateSlotListPopupContents();
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

        public final void setSlotListMenuFont(MultiSIMController.ButtonType buttonType, int i) {
            int i2 = AbstractC24192.f340x9f70d468[buttonType.ordinal()];
            if (i2 != 1) {
                if (i2 == 2 || i2 == 3) {
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

        public final void updateSlotListPopupContents() {
            this.mSlotListButtonImage1.setImageResource(MultiSIMPreferredSlotView.m1643$$Nest$mgetSimIcon(MultiSIMPreferredSlotView.this, 0));
            this.mSlotListButtonText1.setText(MultiSIMPreferredSlotView.this.mData.simName[0]);
            this.mSlotListCarrierName1.setText(MultiSIMPreferredSlotView.this.mData.carrierName[0]);
            this.mSlotListPhoneNumber1.setText(MultiSIMPreferredSlotView.this.mData.phoneNumber[0]);
            this.mSlotListButtonImage2.setImageResource(MultiSIMPreferredSlotView.m1643$$Nest$mgetSimIcon(MultiSIMPreferredSlotView.this, 1));
            this.mSlotListButtonText2.setText(MultiSIMPreferredSlotView.this.mData.simName[1]);
            this.mSlotListCarrierName2.setText(MultiSIMPreferredSlotView.this.mData.carrierName[1]);
            this.mSlotListPhoneNumber2.setText(MultiSIMPreferredSlotView.this.mData.phoneNumber[1]);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SIMInfoIconManager extends StatusBarIconController.TintedIconManager {
        public boolean mBlocked;
        public String mSlot;
        public final int mSlotId;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Factory {
            public final BTTetherUiAdapter mBtTetherUiAdapter;
            public final MobileContextProvider mMobileContextProvider;
            public final MobileUiAdapter mMobileUiAdapter;
            public final StatusBarPipelineFlags mStatusBarPipelineFlags;
            public final WifiUiAdapter mWifiUiAdapter;

            public Factory(StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter) {
                this.mStatusBarPipelineFlags = statusBarPipelineFlags;
                this.mWifiUiAdapter = wifiUiAdapter;
                this.mMobileUiAdapter = mobileUiAdapter;
                this.mMobileContextProvider = mobileContextProvider;
                this.mBtTetherUiAdapter = bTTetherUiAdapter;
            }
        }

        public SIMInfoIconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter, int i) {
            super(viewGroup, statusBarLocation, statusBarPipelineFlags, wifiUiAdapter, mobileUiAdapter, mobileContextProvider, bTTetherUiAdapter);
            this.mSlotId = i;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final LinearLayout.LayoutParams onCreateLayoutParams() {
            return (this.mBlocked && ("mobile".equals(this.mSlot) || "mobile2".equals(this.mSlot))) ? new LinearLayout.LayoutParams(0, 0) : new LinearLayout.LayoutParams(-2, this.mContext.getResources().getDimensionPixelSize(17106181));
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.TintedIconManager, com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
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

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final void onSetIconHolder(int i, StatusBarIconHolder statusBarIconHolder) {
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) this.mGroup.getChildAt(i);
            if (statusIconDisplayable != null) {
                if (this.mSlotId != 0 ? statusIconDisplayable.getSlot().equals("ims_volte2") || statusIconDisplayable.getSlot().equals("mobile2") : statusIconDisplayable.getSlot().equals("ims_volte") || statusIconDisplayable.getSlot().equals("mobile")) {
                    super.onSetIconHolder(i, statusBarIconHolder);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mgetSimIcon, reason: not valid java name */
    public static int m1643$$Nest$mgetSimIcon(MultiSIMPreferredSlotView multiSIMPreferredSlotView, int i) {
        multiSIMPreferredSlotView.getClass();
        if (DeviceType.isSupportESim() && multiSIMPreferredSlotView.mData.isESimSlot[i]) {
            if (multiSIMPreferredSlotView.mESIMIconArray == null) {
                multiSIMPreferredSlotView.mESIMIconArray = multiSIMPreferredSlotView.mContext.getResources().obtainTypedArray(R.array.multisim_esim_icon_res_id_list);
            }
            return multiSIMPreferredSlotView.mESIMIconArray.getResourceId(multiSIMPreferredSlotView.mData.simImageIdx[i], i);
        }
        if (multiSIMPreferredSlotView.mPSimIconArray == null) {
            multiSIMPreferredSlotView.mPSimIconArray = multiSIMPreferredSlotView.mContext.getResources().obtainTypedArray(R.array.multisim_psim_icon_res_id_list);
        }
        return multiSIMPreferredSlotView.mPSimIconArray.getResourceId(multiSIMPreferredSlotView.mData.simImageIdx[i], i);
    }

    static {
        MultiSIMController.ButtonType buttonType = MultiSIMController.ButtonType.VOICE;
        MultiSIMController.ButtonType buttonType2 = MultiSIMController.ButtonType.SMS;
        MultiSIMController.ButtonType buttonType3 = MultiSIMController.ButtonType.DATA;
        PREFERRED_BUTTON_LIST = new MultiSIMController.ButtonType[]{buttonType, buttonType2, buttonType3};
        SIM_INFO_BUTTON_LIST = new MultiSIMController.ButtonType[]{MultiSIMController.ButtonType.SIMINFO1, MultiSIMController.ButtonType.SIMINFO2};
        DATA_ONLY_BUTTON_LIST = new MultiSIMController.ButtonType[]{buttonType3};
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.settings.multisim.MultiSIMPreferredSlotView$1] */
    public MultiSIMPreferredSlotView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSlotButtons = new ArrayList();
        this.mNightModeOn = false;
        this.mLocale = null;
        this.mCurrentOrientation = 0;
        this.mChangedByDataButton = false;
        this.mSecTouchLogHelper = new SecTouchLogHelper();
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.settings.multisim.MultiSIMPreferredSlotView.1
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

    @Override // com.android.systemui.settings.multisim.MultiSIMController.MultiSIMDataChangedCallback
    public final boolean isPhoneNumberNeeded() {
        PrefferedSlotPopupWindow prefferedSlotPopupWindow = this.mPopupWindow;
        return prefferedSlotPopupWindow != null && prefferedSlotPopupWindow.isShowing();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MultiSIMController multiSIMController = (MultiSIMController) Dependency.get(MultiSIMController.class);
        this.mController = multiSIMController;
        if (multiSIMController.mData == null) {
            multiSIMController.mData = new MultiSIMData();
        }
        MultiSIMData multiSIMData = new MultiSIMData();
        multiSIMData.copyFrom(multiSIMController.mData);
        multiSIMController.reverseSlotIfNeed(multiSIMData);
        this.mData = multiSIMData;
        MultiSIMController multiSIMController2 = this.mController;
        int i = 0;
        while (true) {
            ArrayList arrayList = multiSIMController2.mVisCallbacks;
            if (i >= arrayList.size()) {
                arrayList.add(new WeakReference(this));
                multiSIMController2.mVisCallbacks.removeIf(new MultiSIMController$$ExternalSyntheticLambda0(null, 1));
                break;
            } else if (((WeakReference) arrayList.get(i)).get() == this) {
                break;
            } else {
                i++;
            }
        }
        this.mController.registerCallback(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mIntentReceiver);
        updateButtonList();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        super.onConfigurationChanged(configuration);
        int i = this.mCurrentOrientation;
        int i2 = configuration.orientation;
        boolean z2 = true;
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
            Iterator it = this.mSlotButtons.iterator();
            while (it.hasNext()) {
                ((PrefferedSlotButton) it.next()).updateTextColor();
            }
            PrefferedSlotPopupWindow prefferedSlotPopupWindow2 = this.mPopupWindow;
            if (prefferedSlotPopupWindow2 != null) {
                prefferedSlotPopupWindow2.dismiss();
                this.mPopupWindow = null;
            }
        }
    }

    @Override // com.android.systemui.settings.multisim.MultiSIMController.MultiSIMDataChangedCallback
    public final void onDataChanged(MultiSIMData multiSIMData) {
        this.mData.copyFrom(multiSIMData);
        this.mChangedByDataButton = this.mData.changingDataInternally;
        Iterator it = this.mSlotButtons.iterator();
        while (it.hasNext()) {
            PrefferedSlotButton prefferedSlotButton = (PrefferedSlotButton) it.next();
            prefferedSlotButton.updateTextColor();
            prefferedSlotButton.refreshSlotInfo();
        }
        PrefferedSlotPopupWindow prefferedSlotPopupWindow = this.mPopupWindow;
        if (prefferedSlotPopupWindow != null) {
            prefferedSlotPopupWindow.updateSlotListPopupContents();
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
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mIntentReceiver);
        MultiSIMController multiSIMController = this.mController;
        multiSIMController.getClass();
        multiSIMController.mVisCallbacks.removeIf(new MultiSIMController$$ExternalSyntheticLambda0(this, 1));
        MultiSIMController multiSIMController2 = this.mController;
        multiSIMController2.getClass();
        multiSIMController2.mDataCallbacks.removeIf(new MultiSIMController$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDualToneHandler = new DualToneHandler(new ContextThemeWrapper(this.mContext, 2132018544));
        this.mNightModeOn = (this.mContext.getResources().getConfiguration().uiMode & 32) != 0;
        this.mCurrentOrientation = this.mContext.getResources().getConfiguration().orientation;
        this.mLocale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.slot_button_group);
        this.mSlotButtonGroup = linearLayout;
        linearLayout.setDividerPadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.multi_sim_bar_divider_padding));
        this.mSlotButtonTextColor = this.mContext.getResources().getColor(R.color.qs_multisim_preffered_slot_text_color, null);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        SecTouchLogHelper secTouchLogHelper = this.mSecTouchLogHelper;
        StringBuilder sb = new StringBuilder("return:");
        sb.append(!isEnabled());
        secTouchLogHelper.printOnInterceptTouchEventLog(motionEvent, "MultiSIMPreferredSlotView", sb.toString());
        return !isEnabled();
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mSecTouchLogHelper.printOnTouchEventLog(motionEvent, "MultiSIMPreferredSlotView", "");
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
    }

    public final void updateButtonList() {
        MultiSIMController.ButtonType[] buttonTypeArr;
        this.mSlotButtons.clear();
        this.mSlotButtonGroup.removeAllViews();
        if (this.mController.mUIVisible) {
            if (Operator.supportNetworkInfoAtMultisimBar()) {
                buttonTypeArr = SIM_INFO_BUTTON_LIST;
            } else {
                Point point = DeviceState.sDisplaySize;
                buttonTypeArr = DeviceType.isTablet() && !DeviceState.isVoiceCapable(this.mContext) ? DATA_ONLY_BUTTON_LIST : PREFERRED_BUTTON_LIST;
            }
            for (MultiSIMController.ButtonType buttonType : buttonTypeArr) {
                PrefferedSlotButton prefferedSlotButton = new PrefferedSlotButton(buttonType, this.mContext, this.mSlotButtonGroup);
                this.mSlotButtons.add(prefferedSlotButton);
                prefferedSlotButton.updateTextColor();
                prefferedSlotButton.refreshSlotInfo();
            }
        }
    }
}
