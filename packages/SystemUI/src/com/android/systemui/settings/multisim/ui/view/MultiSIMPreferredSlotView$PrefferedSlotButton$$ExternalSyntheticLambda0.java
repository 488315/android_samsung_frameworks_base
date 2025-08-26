package com.android.systemui.settings.multisim.ui.view;

import android.view.View;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.ButtonType;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;

/* loaded from: classes3.dex */
public final /* synthetic */ class MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Button.ClickListener f$1;

    public /* synthetic */ MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(Object obj, Button.ClickListener clickListener, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = clickListener;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton = (MultiSIMPreferredSlotView.PrefferedSlotButton) this.f$0;
                ((MultiSIMViewModelImpl) this.f$1).onSlotButtonClick(prefferedSlotButton.mType, view);
                break;
            case 1:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl = (MultiSIMViewModelImpl) this.f$1;
                int i = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow.getClass();
                if (multiSIMViewModelImpl.onSlotsListItemClick(ButtonType.VOICE, 0)) {
                    prefferedSlotPopupWindow.mSlotListAskCheckedImage.setVisibility(0);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(8);
                    prefferedSlotPopupWindow.mSlotListOthersCheckedImage.setVisibility(8);
                }
                prefferedSlotPopupWindow.dismiss();
                break;
            case 2:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow2 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl2 = (MultiSIMViewModelImpl) this.f$1;
                int i2 = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow2.getClass();
                if (multiSIMViewModelImpl2.onSlotsListItemClick(ButtonType.VOICE, 1)) {
                    prefferedSlotPopupWindow2.mSlotListAskCheckedImage.setVisibility(8);
                    prefferedSlotPopupWindow2.mSlotListButtonCheckedImage1.setVisibility(0);
                    prefferedSlotPopupWindow2.mSlotListButtonCheckedImage2.setVisibility(8);
                    prefferedSlotPopupWindow2.mSlotListOthersCheckedImage.setVisibility(8);
                }
                prefferedSlotPopupWindow2.dismiss();
                break;
            case 3:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow3 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl3 = (MultiSIMViewModelImpl) this.f$1;
                int i3 = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow3.getClass();
                if (multiSIMViewModelImpl3.onSlotsListItemClick(ButtonType.VOICE, 2)) {
                    prefferedSlotPopupWindow3.mSlotListAskCheckedImage.setVisibility(8);
                    prefferedSlotPopupWindow3.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow3.mSlotListButtonCheckedImage2.setVisibility(0);
                    prefferedSlotPopupWindow3.mSlotListOthersCheckedImage.setVisibility(8);
                }
                prefferedSlotPopupWindow3.dismiss();
                break;
            case 4:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow4 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl4 = (MultiSIMViewModelImpl) this.f$1;
                int i4 = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow4.getClass();
                if (multiSIMViewModelImpl4.onSlotsListItemClick(ButtonType.VOICE, 3)) {
                    prefferedSlotPopupWindow4.mSlotListAskCheckedImage.setVisibility(8);
                    prefferedSlotPopupWindow4.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow4.mSlotListButtonCheckedImage2.setVisibility(8);
                    prefferedSlotPopupWindow4.mSlotListOthersCheckedImage.setVisibility(0);
                }
                prefferedSlotPopupWindow4.dismiss();
                break;
            case 5:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow5 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl5 = (MultiSIMViewModelImpl) this.f$1;
                int i5 = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow5.getClass();
                if (multiSIMViewModelImpl5.onSlotsListItemClick(ButtonType.SMS, 0)) {
                    prefferedSlotPopupWindow5.mSlotListButtonCheckedImage1.setVisibility(0);
                    prefferedSlotPopupWindow5.mSlotListButtonCheckedImage2.setVisibility(8);
                }
                prefferedSlotPopupWindow5.dismiss();
                break;
            case 6:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow6 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl6 = (MultiSIMViewModelImpl) this.f$1;
                int i6 = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow6.getClass();
                if (multiSIMViewModelImpl6.onSlotsListItemClick(ButtonType.SMS, 1)) {
                    prefferedSlotPopupWindow6.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow6.mSlotListButtonCheckedImage2.setVisibility(0);
                }
                prefferedSlotPopupWindow6.dismiss();
                break;
            case 7:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow7 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl7 = (MultiSIMViewModelImpl) this.f$1;
                int i7 = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow7.getClass();
                multiSIMViewModelImpl7.onSlotsListItemClick(ButtonType.DATA, 0);
                prefferedSlotPopupWindow7.dismiss();
                break;
            default:
                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow8 = (MultiSIMPreferredSlotView.PrefferedSlotPopupWindow) this.f$0;
                MultiSIMViewModelImpl multiSIMViewModelImpl8 = (MultiSIMViewModelImpl) this.f$1;
                int i8 = MultiSIMPreferredSlotView.PrefferedSlotPopupWindow.$r8$clinit;
                prefferedSlotPopupWindow8.getClass();
                multiSIMViewModelImpl8.onSlotsListItemClick(ButtonType.DATA, 1);
                prefferedSlotPopupWindow8.dismiss();
                break;
        }
    }
}
