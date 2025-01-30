package com.android.systemui.settings.multisim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.MultiSIMPreferredSlotView.PrefferedSlotPopupWindow;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.view.SemWindowManager;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.settings.multisim.MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnClickListenerC2415x920e5b30 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ViewOnClickListenerC2415x920e5b30(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0472  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onClick(View view) {
        ViewGroup viewGroup;
        SemBlurInfo.Builder builder;
        Bitmap bitmap;
        boolean z;
        int i = 3;
        final int i2 = 1;
        int i3 = 2;
        final int i4 = 0;
        switch (this.$r8$classId) {
            case 0:
                final MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton = (MultiSIMPreferredSlotView.PrefferedSlotButton) this.f$0;
                MultiSIMPreferredSlotView multiSIMPreferredSlotView = MultiSIMPreferredSlotView.this;
                MultiSIMData multiSIMData = multiSIMPreferredSlotView.mData;
                boolean z2 = multiSIMData.airplaneMode;
                Context context = prefferedSlotButton.mContext;
                if (!z2) {
                    MultiSIMController.ButtonType buttonType = MultiSIMController.ButtonType.DATA;
                    MultiSIMController.ButtonType buttonType2 = prefferedSlotButton.mType;
                    if (buttonType2 != buttonType || !multiSIMPreferredSlotView.mChangedByDataButton) {
                        if ((buttonType2 != buttonType || !multiSIMData.changingNetMode) && !multiSIMData.isCalling && !multiSIMData.isSRoaming && !multiSIMData.isRestrictionsForMmsUse) {
                            if (((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mShowing && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mSecure && !((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mCanDismissLockScreen && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isLockFunctionsEnabled()) {
                                multiSIMPreferredSlotView.mController.launchSimManager();
                                break;
                            } else if (prefferedSlotButton.isSimInfoButton()) {
                                multiSIMPreferredSlotView.mController.launchSimManager();
                                break;
                            } else {
                                multiSIMPreferredSlotView.mController.getClass();
                                if (!MultiSIMController.isBlockedAllMultiSimBar()) {
                                    if (multiSIMPreferredSlotView.mPopupWindow == null) {
                                        multiSIMPreferredSlotView.mPopupWindow = multiSIMPreferredSlotView.new PrefferedSlotPopupWindow(multiSIMPreferredSlotView.mContext);
                                    }
                                    final MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = multiSIMPreferredSlotView.mPopupWindow;
                                    prefferedSlotPopupWindow.getClass();
                                    prefferedSlotPopupWindow.mSlotListButton1Group.setVisibility(0);
                                    prefferedSlotPopupWindow.mSlotListButton2Group.setVisibility(0);
                                    int i5 = MultiSIMPreferredSlotView.AbstractC24192.f340x9f70d468[buttonType2.ordinal()];
                                    int i6 = 4;
                                    if (i5 == 1) {
                                        prefferedSlotPopupWindow.mSlotListAskButtonGroup.setOnClickListener(new ViewOnClickListenerC2415x920e5b30(prefferedSlotPopupWindow, i2));
                                        prefferedSlotPopupWindow.mSlotListButton1Group.setOnClickListener(new ViewOnClickListenerC2415x920e5b30(prefferedSlotPopupWindow, i3));
                                        prefferedSlotPopupWindow.mSlotListButton2Group.setOnClickListener(new ViewOnClickListenerC2415x920e5b30(prefferedSlotPopupWindow, i));
                                        prefferedSlotPopupWindow.mSlotListOthersButtonGroup.setOnClickListener(new ViewOnClickListenerC2415x920e5b30(prefferedSlotPopupWindow, i6));
                                        prefferedSlotPopupWindow.setSlotListMenuFont(buttonType2, MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId);
                                        prefferedSlotPopupWindow.mSlotListAskButtonText.setTextColor(MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 0 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                                        prefferedSlotPopupWindow.setSlotListMenuColor(0, MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 1 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                                        prefferedSlotPopupWindow.setSlotListMenuColor(1, MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 2 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                                        prefferedSlotPopupWindow.mSlotListOthersButtonText.setTextColor(MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 3 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                                        prefferedSlotPopupWindow.mSlotListAskCheckedImage.setVisibility(MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 0 ? 0 : 8);
                                        prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 1 ? 0 : 8);
                                        prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 2 ? 0 : 8);
                                        prefferedSlotPopupWindow.mSlotListOthersCheckedImage.setVisibility(MultiSIMPreferredSlotView.this.mData.defaultVoiceSimId == 3 ? 0 : 8);
                                        SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1016", "isChanged", "calls", "QUICK_PANEL_LAYOUT");
                                    } else if (i5 == 2) {
                                        prefferedSlotPopupWindow.mSlotListButton1Group.setOnClickListener(new ViewOnClickListenerC2415x920e5b30(prefferedSlotPopupWindow, 5));
                                        prefferedSlotPopupWindow.mSlotListButton2Group.setOnClickListener(new ViewOnClickListenerC2415x920e5b30(prefferedSlotPopupWindow, 6));
                                        prefferedSlotPopupWindow.setSlotListMenuColor(0, MultiSIMPreferredSlotView.this.mData.defaultSmsSimId == 1 ? prefferedSlotPopupWindow.mPopupNormalTextColor : prefferedSlotPopupWindow.mPopupSelectedTextColor);
                                        prefferedSlotPopupWindow.setSlotListMenuColor(1, MultiSIMPreferredSlotView.this.mData.defaultSmsSimId == 1 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                                        prefferedSlotPopupWindow.setSlotListMenuFont(buttonType2, MultiSIMPreferredSlotView.this.mData.defaultSmsSimId);
                                        prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(MultiSIMPreferredSlotView.this.mData.defaultSmsSimId == 0 ? 0 : 8);
                                        prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(MultiSIMPreferredSlotView.this.mData.defaultSmsSimId == 1 ? 0 : 8);
                                        SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1016", "isChanged", "text messages", "QUICK_PANEL_LAYOUT");
                                    } else if (i5 == 3) {
                                        final boolean isDataBlocked = MultiSIMPreferredSlotView.this.mController.isDataBlocked(0);
                                        final boolean isDataBlocked2 = MultiSIMPreferredSlotView.this.mController.isDataBlocked(1);
                                        prefferedSlotPopupWindow.mSlotListButton1Group.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.multisim.MultiSIMPreferredSlotView$PrefferedSlotPopupWindow$$ExternalSyntheticLambda0
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view2) {
                                                switch (i4) {
                                                    case 0:
                                                        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow2 = prefferedSlotPopupWindow;
                                                        boolean z3 = isDataBlocked;
                                                        MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton2 = prefferedSlotButton;
                                                        MultiSIMPreferredSlotView multiSIMPreferredSlotView2 = MultiSIMPreferredSlotView.this;
                                                        MultiSIMData multiSIMData2 = multiSIMPreferredSlotView2.mData;
                                                        int i7 = multiSIMData2.defaultDataSimId;
                                                        if (i7 != 0 || !multiSIMData2.isDataEnabled) {
                                                            if (z3) {
                                                                prefferedSlotPopupWindow2.dismiss();
                                                                break;
                                                            } else {
                                                                if (i7 != 0) {
                                                                    multiSIMPreferredSlotView2.mChangedByDataButton = true;
                                                                }
                                                                prefferedSlotButton2.updateTextColor();
                                                                MultiSIMPreferredSlotView.this.mController.setDefaultSlot(MultiSIMController.ButtonType.DATA, 0);
                                                            }
                                                        }
                                                        prefferedSlotPopupWindow2.dismiss();
                                                        break;
                                                    default:
                                                        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow3 = prefferedSlotPopupWindow;
                                                        boolean z4 = isDataBlocked;
                                                        MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton3 = prefferedSlotButton;
                                                        MultiSIMPreferredSlotView multiSIMPreferredSlotView3 = MultiSIMPreferredSlotView.this;
                                                        MultiSIMData multiSIMData3 = multiSIMPreferredSlotView3.mData;
                                                        int i8 = multiSIMData3.defaultDataSimId;
                                                        if (i8 != 1 || !multiSIMData3.isDataEnabled) {
                                                            if (z4) {
                                                                prefferedSlotPopupWindow3.dismiss();
                                                                break;
                                                            } else {
                                                                if (i8 != 1) {
                                                                    multiSIMPreferredSlotView3.mChangedByDataButton = true;
                                                                }
                                                                prefferedSlotButton3.updateTextColor();
                                                                MultiSIMPreferredSlotView.this.mController.setDefaultSlot(MultiSIMController.ButtonType.DATA, 1);
                                                            }
                                                        }
                                                        prefferedSlotPopupWindow3.dismiss();
                                                        break;
                                                }
                                            }
                                        });
                                        prefferedSlotPopupWindow.mSlotListButton2Group.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.multisim.MultiSIMPreferredSlotView$PrefferedSlotPopupWindow$$ExternalSyntheticLambda0
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view2) {
                                                switch (i2) {
                                                    case 0:
                                                        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow2 = prefferedSlotPopupWindow;
                                                        boolean z3 = isDataBlocked2;
                                                        MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton2 = prefferedSlotButton;
                                                        MultiSIMPreferredSlotView multiSIMPreferredSlotView2 = MultiSIMPreferredSlotView.this;
                                                        MultiSIMData multiSIMData2 = multiSIMPreferredSlotView2.mData;
                                                        int i7 = multiSIMData2.defaultDataSimId;
                                                        if (i7 != 0 || !multiSIMData2.isDataEnabled) {
                                                            if (z3) {
                                                                prefferedSlotPopupWindow2.dismiss();
                                                                break;
                                                            } else {
                                                                if (i7 != 0) {
                                                                    multiSIMPreferredSlotView2.mChangedByDataButton = true;
                                                                }
                                                                prefferedSlotButton2.updateTextColor();
                                                                MultiSIMPreferredSlotView.this.mController.setDefaultSlot(MultiSIMController.ButtonType.DATA, 0);
                                                            }
                                                        }
                                                        prefferedSlotPopupWindow2.dismiss();
                                                        break;
                                                    default:
                                                        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow3 = prefferedSlotPopupWindow;
                                                        boolean z4 = isDataBlocked2;
                                                        MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton3 = prefferedSlotButton;
                                                        MultiSIMPreferredSlotView multiSIMPreferredSlotView3 = MultiSIMPreferredSlotView.this;
                                                        MultiSIMData multiSIMData3 = multiSIMPreferredSlotView3.mData;
                                                        int i8 = multiSIMData3.defaultDataSimId;
                                                        if (i8 != 1 || !multiSIMData3.isDataEnabled) {
                                                            if (z4) {
                                                                prefferedSlotPopupWindow3.dismiss();
                                                                break;
                                                            } else {
                                                                if (i8 != 1) {
                                                                    multiSIMPreferredSlotView3.mChangedByDataButton = true;
                                                                }
                                                                prefferedSlotButton3.updateTextColor();
                                                                MultiSIMPreferredSlotView.this.mController.setDefaultSlot(MultiSIMController.ButtonType.DATA, 1);
                                                            }
                                                        }
                                                        prefferedSlotPopupWindow3.dismiss();
                                                        break;
                                                }
                                            }
                                        });
                                        MultiSIMData multiSIMData2 = MultiSIMPreferredSlotView.this.mData;
                                        if (multiSIMData2.isDataEnabled) {
                                            int i7 = multiSIMData2.defaultDataSimId;
                                            if (i7 == 0) {
                                                prefferedSlotPopupWindow.setSlotListMenuColor(0, prefferedSlotPopupWindow.mPopupSelectedTextColor);
                                                prefferedSlotPopupWindow.setSlotListMenuColor(1, prefferedSlotPopupWindow.mPopupNormalTextColor);
                                                prefferedSlotPopupWindow.setSlotListMenuFont(buttonType2, MultiSIMPreferredSlotView.this.mData.defaultDataSimId);
                                                prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(0);
                                                prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(8);
                                            } else if (i7 == 1) {
                                                prefferedSlotPopupWindow.setSlotListMenuColor(0, prefferedSlotPopupWindow.mPopupNormalTextColor);
                                                prefferedSlotPopupWindow.setSlotListMenuColor(1, prefferedSlotPopupWindow.mPopupSelectedTextColor);
                                                prefferedSlotPopupWindow.setSlotListMenuFont(buttonType2, MultiSIMPreferredSlotView.this.mData.defaultDataSimId);
                                                prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(8);
                                                prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(0);
                                            }
                                        } else {
                                            prefferedSlotPopupWindow.setSlotListMenuColor(0, prefferedSlotPopupWindow.mPopupNormalTextColor);
                                            prefferedSlotPopupWindow.setSlotListMenuColor(1, prefferedSlotPopupWindow.mPopupNormalTextColor);
                                            prefferedSlotPopupWindow.mSlotListButtonText1.setTypeface(prefferedSlotPopupWindow.mPopupNonSelectedFont);
                                            prefferedSlotPopupWindow.mSlotListButtonText2.setTypeface(prefferedSlotPopupWindow.mPopupNonSelectedFont);
                                            prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(8);
                                            prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(8);
                                        }
                                        if (isDataBlocked) {
                                            prefferedSlotPopupWindow.mSlotListButton1Group.setVisibility(8);
                                        }
                                        if (isDataBlocked2) {
                                            prefferedSlotPopupWindow.mSlotListButton2Group.setVisibility(8);
                                        }
                                        SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1016", "isChanged", "mobile data", "QUICK_PANEL_LAYOUT");
                                    }
                                    int dimensionPixelSize = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_divider_top_padding);
                                    int dimensionPixelSize2 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_slot_top_padding);
                                    int dimensionPixelSize3 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_horizontal_padding);
                                    int dimensionPixelSize4 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_divider_bottom_padding);
                                    int dimensionPixelSize5 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_slot_bottom_padding);
                                    MultiSIMController.ButtonType buttonType3 = MultiSIMController.ButtonType.VOICE;
                                    if (buttonType2 == buttonType3) {
                                        prefferedSlotPopupWindow.mSlotListAskButtonGroup.setVisibility(0);
                                        prefferedSlotPopupWindow.mSlotListButton1Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize4);
                                        prefferedSlotPopupWindow.mSlotListButton1Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_middle_ripple_bg));
                                    } else {
                                        prefferedSlotPopupWindow.mSlotListAskButtonGroup.setVisibility(8);
                                        prefferedSlotPopupWindow.mSlotListButton1Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize4);
                                        prefferedSlotPopupWindow.mSlotListButton1Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_top_ripple_bg));
                                    }
                                    if (buttonType2 == buttonType3) {
                                        TelecomManager from = TelecomManager.from(MultiSIMPreferredSlotView.this.mController.mContext);
                                        Iterator it = from.getCallCapablePhoneAccounts(true).iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                PhoneAccount phoneAccount = from.getPhoneAccount((PhoneAccountHandle) it.next());
                                                if (phoneAccount != null) {
                                                    if (!((phoneAccount.getCapabilities() & 4) != 0)) {
                                                        Log.d("MultiSIMController", "Support Call preferred Others");
                                                        z = true;
                                                    }
                                                }
                                            } else {
                                                z = false;
                                            }
                                        }
                                        if (z) {
                                            prefferedSlotPopupWindow.mSlotListOthersButtonGroup.setVisibility(0);
                                            prefferedSlotPopupWindow.mSlotListButton2Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize4);
                                            prefferedSlotPopupWindow.mSlotListButton2Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_middle_ripple_bg));
                                            viewGroup = prefferedSlotButton.mButtonView;
                                            if (viewGroup == null) {
                                                View contentView = prefferedSlotPopupWindow.getContentView();
                                                int[] iArr = new int[2];
                                                int[] iArr2 = new int[2];
                                                viewGroup.getLocationOnScreen(iArr2);
                                                int height = viewGroup.getHeight();
                                                int width = viewGroup.getWidth();
                                                contentView.measure(0, 0);
                                                int measuredHeight = contentView.getMeasuredHeight();
                                                int measuredWidth = contentView.getMeasuredWidth();
                                                int i8 = prefferedSlotPopupWindow.mContext.getResources().getDisplayMetrics().heightPixels;
                                                int i9 = prefferedSlotPopupWindow.mContext.getResources().getDisplayMetrics().widthPixels;
                                                int i10 = prefferedSlotPopupWindow.mPopupWindowTopMargin;
                                                int i11 = iArr2[1];
                                                boolean z3 = (i8 - i11) - i10 < measuredHeight;
                                                int i12 = iArr2[0];
                                                if (i9 - i12 < measuredWidth) {
                                                    i12 = (i12 + width) - measuredWidth;
                                                }
                                                iArr[0] = i12;
                                                if (z3) {
                                                    i10 = height - measuredHeight;
                                                }
                                                iArr[1] = i11 + i10;
                                                MultiSIMPreferredSlotView multiSIMPreferredSlotView2 = MultiSIMPreferredSlotView.this;
                                                multiSIMPreferredSlotView2.getClass();
                                                Point point = DeviceState.sDisplaySize;
                                                prefferedSlotPopupWindow.showAtLocation(viewGroup, DeviceType.isTablet() && !DeviceState.isVoiceCapable(multiSIMPreferredSlotView2.mContext) ? 49 : 8388659, iArr[0], iArr[1]);
                                                MultiSIMPreferredSlotView.this.mController.updatePhoneNumberWhenNeeded();
                                                float dimensionPixelSize6 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_radius);
                                                int color = prefferedSlotPopupWindow.mContext.getResources().getColor(R.color.sec_qs_multisim_preffered_slot_background);
                                                if (QpRune.QUICK_PANEL_BLUR_DEFAULT) {
                                                    builder = new SemBlurInfo.Builder(0).setRadius(200).setBackgroundColor(color).setBackgroundCornerRadius(dimensionPixelSize6);
                                                } else {
                                                    if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                                                        int i13 = iArr[0];
                                                        int i14 = iArr[1];
                                                        int measuredWidth2 = prefferedSlotPopupWindow.mPopupContentView.getMeasuredWidth();
                                                        int measuredHeight2 = prefferedSlotPopupWindow.mPopupContentView.getMeasuredHeight();
                                                        try {
                                                            bitmap = SemWindowManager.getInstance().screenshot(((WindowManager) prefferedSlotPopupWindow.mContext.getSystemService("window")).getDefaultDisplay().getDisplayId(), 2036, true, new Rect(i13, i14, i13 + measuredWidth2, i14 + measuredHeight2), measuredWidth2, measuredHeight2, false, 0, true);
                                                        } catch (SecurityException e) {
                                                            e.printStackTrace();
                                                            bitmap = null;
                                                        }
                                                        if (bitmap == null) {
                                                            bitmap = null;
                                                        }
                                                        if (bitmap != null) {
                                                            builder = new SemBlurInfo.Builder(1).setRadius(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend).setBitmap(bitmap);
                                                        }
                                                    }
                                                    builder = null;
                                                }
                                                if (builder != null) {
                                                    prefferedSlotPopupWindow.mPopupContentView.semSetBlurInfo(builder.build());
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    prefferedSlotPopupWindow.mSlotListOthersButtonGroup.setVisibility(8);
                                    prefferedSlotPopupWindow.mSlotListButton2Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize5);
                                    prefferedSlotPopupWindow.mSlotListButton2Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_bottom_ripple_bg));
                                    viewGroup = prefferedSlotButton.mButtonView;
                                    if (viewGroup == null) {
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.qs_multisim_data_switching), 0).show();
                        break;
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.qs_multisim_airplanemode_on), 0).show();
                    break;
                }
                break;
            case 1:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow2 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMPreferredSlotView multiSIMPreferredSlotView3 = MultiSIMPreferredSlotView.this;
                if (multiSIMPreferredSlotView3.mData.defaultVoiceSimId != 0) {
                    multiSIMPreferredSlotView3.mController.setDefaultSlot(MultiSIMController.ButtonType.VOICE, 0);
                    prefferedSlotPopupWindow2.mSlotListAskCheckedImage.setVisibility(0);
                    prefferedSlotPopupWindow2.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow2.mSlotListButtonCheckedImage2.setVisibility(8);
                    prefferedSlotPopupWindow2.mSlotListOthersCheckedImage.setVisibility(8);
                }
                prefferedSlotPopupWindow2.dismiss();
                break;
            case 2:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow3 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMPreferredSlotView multiSIMPreferredSlotView4 = MultiSIMPreferredSlotView.this;
                if (multiSIMPreferredSlotView4.mData.defaultVoiceSimId != 1) {
                    multiSIMPreferredSlotView4.mController.setDefaultSlot(MultiSIMController.ButtonType.VOICE, 1);
                    prefferedSlotPopupWindow3.mSlotListAskCheckedImage.setVisibility(8);
                    prefferedSlotPopupWindow3.mSlotListButtonCheckedImage1.setVisibility(0);
                    prefferedSlotPopupWindow3.mSlotListButtonCheckedImage2.setVisibility(8);
                    prefferedSlotPopupWindow3.mSlotListOthersCheckedImage.setVisibility(8);
                }
                prefferedSlotPopupWindow3.dismiss();
                break;
            case 3:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow4 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMPreferredSlotView multiSIMPreferredSlotView5 = MultiSIMPreferredSlotView.this;
                if (multiSIMPreferredSlotView5.mData.defaultVoiceSimId != 2) {
                    multiSIMPreferredSlotView5.mController.setDefaultSlot(MultiSIMController.ButtonType.VOICE, 2);
                    prefferedSlotPopupWindow4.mSlotListAskCheckedImage.setVisibility(8);
                    prefferedSlotPopupWindow4.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow4.mSlotListButtonCheckedImage2.setVisibility(0);
                    prefferedSlotPopupWindow4.mSlotListOthersCheckedImage.setVisibility(8);
                }
                prefferedSlotPopupWindow4.dismiss();
                break;
            case 4:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow5 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMPreferredSlotView multiSIMPreferredSlotView6 = MultiSIMPreferredSlotView.this;
                if (multiSIMPreferredSlotView6.mData.defaultVoiceSimId != 3) {
                    multiSIMPreferredSlotView6.mController.setDefaultSlot(MultiSIMController.ButtonType.VOICE, 3);
                    prefferedSlotPopupWindow5.mSlotListAskCheckedImage.setVisibility(8);
                    prefferedSlotPopupWindow5.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow5.mSlotListButtonCheckedImage2.setVisibility(8);
                    prefferedSlotPopupWindow5.mSlotListOthersCheckedImage.setVisibility(0);
                }
                prefferedSlotPopupWindow5.dismiss();
                break;
            case 5:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow6 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMPreferredSlotView multiSIMPreferredSlotView7 = MultiSIMPreferredSlotView.this;
                if (multiSIMPreferredSlotView7.mData.defaultSmsSimId != 0) {
                    multiSIMPreferredSlotView7.mController.setDefaultSlot(MultiSIMController.ButtonType.SMS, 0);
                    prefferedSlotPopupWindow6.mSlotListButtonCheckedImage1.setVisibility(0);
                    prefferedSlotPopupWindow6.mSlotListButtonCheckedImage2.setVisibility(8);
                }
                prefferedSlotPopupWindow6.dismiss();
                break;
            default:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow7 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMPreferredSlotView multiSIMPreferredSlotView8 = MultiSIMPreferredSlotView.this;
                if (multiSIMPreferredSlotView8.mData.defaultSmsSimId != 1) {
                    multiSIMPreferredSlotView8.mController.setDefaultSlot(MultiSIMController.ButtonType.SMS, 1);
                    prefferedSlotPopupWindow7.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow7.mSlotListButtonCheckedImage2.setVisibility(0);
                }
                prefferedSlotPopupWindow7.dismiss();
                break;
        }
    }
}
