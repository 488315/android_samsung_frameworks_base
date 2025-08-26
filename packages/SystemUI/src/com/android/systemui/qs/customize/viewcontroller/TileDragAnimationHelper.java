package com.android.systemui.qs.customize.viewcontroller;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.CustomTileInfo;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.MessageObjectAnim;
import com.android.systemui.qs.customize.SecCustomizeTileView;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.view.QSTileCustomizerBase;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* loaded from: classes2.dex */
public final class TileDragAnimationHelper {
    public final Context context;
    public final QSTileCustomizerInteractionManager interactionManager;
    public final ScrollView mActiveScrollView;
    public final CustomizerTileViewPager mActiveTileLayout;
    public final CustomizerTileViewPager mAvailableTileLayout;
    public final Handler mHandler;
    public final boolean mIsTopEdit;
    public final int mTopMinMaxNum;
    public final QSTileCustomizerBase mView;

    public TileDragAnimationHelper(QSTileCustomizerBase qSTileCustomizerBase, Handler handler, QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager) {
        this.mView = qSTileCustomizerBase;
        this.mHandler = handler;
        this.interactionManager = qSTileCustomizerInteractionManager;
        this.context = qSTileCustomizerBase.getContext();
        this.mActiveScrollView = (ScrollView) qSTileCustomizerBase.findViewById(R.id.qs_active_page_scrollview);
        this.mActiveTileLayout = (CustomizerTileViewPager) qSTileCustomizerBase.findViewById(R.id.qs_customizer_active_pager);
        this.mAvailableTileLayout = (CustomizerTileViewPager) qSTileCustomizerBase.findViewById(R.id.qs_customizer_available_pager);
        this.mIsTopEdit = qSTileCustomizerBase.mIsTopEdit;
        this.mTopMinMaxNum = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQsTileMinNum(qSTileCustomizerBase.getContext());
    }

    public final void animateArea(CustomTileInfo customTileInfo, int i, int i2) {
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.animationType = i;
        messageObjectAnim.touchedPos = i2;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        int value = CustomizerInteractionType.MSG_HANDLE_ANIMATE_AREA.getValue();
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(value, messageObjectAnim), 100L);
    }

    public final void animateCurrentPage(CustomTileInfo customTileInfo, int i) {
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.touchedPos = i;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        messageObjectAnim.animationType = 202;
        CustomizerInteractionType customizerInteractionType = CustomizerInteractionType.MSG_HANDLE_ANIMATE_PAGE;
        int value = customizerInteractionType.getValue();
        Handler handler = this.mHandler;
        if (handler.hasMessages(value)) {
            handler.removeMessages(customizerInteractionType.getValue());
        }
        handler.sendMessage(handler.obtainMessage(customizerInteractionType.getValue(), messageObjectAnim));
    }

    public final void animationDrop(CustomTileInfo customTileInfo) {
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.animationType = 201;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        CustomizerInteractionType customizerInteractionType = CustomizerInteractionType.MSG_HANDLE_ANIMATE_DROP;
        int value = customizerInteractionType.getValue();
        Handler handler = this.mHandler;
        if (handler.hasMessages(value)) {
            handler.removeMessages(customizerInteractionType.getValue());
        }
        handler.sendMessageDelayed(handler.obtainMessage(customizerInteractionType.getValue(), messageObjectAnim), 0L);
    }

    public final void animationDropOtherPage(CustomTileInfo customTileInfo) {
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.animationType = 210;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        CustomizerInteractionType customizerInteractionType = CustomizerInteractionType.MSG_HANDLE_ANIMATE_DROP;
        int value = customizerInteractionType.getValue();
        Handler handler = this.mHandler;
        if (handler.hasMessages(value)) {
            handler.removeMessages(customizerInteractionType.getValue());
        }
        handler.sendMessage(handler.obtainMessage(customizerInteractionType.getValue(), messageObjectAnim));
    }

    public final void animationStart(CustomTileInfo customTileInfo, boolean z) {
        SecCustomizeTileView secCustomizeTileView;
        TextView textView;
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.animationType = z ? IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState : 200;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        CustomizerInteractionType customizerInteractionType = CustomizerInteractionType.MSG_HANDLE_ANIMATE_START;
        int value = customizerInteractionType.getValue();
        Handler handler = this.mHandler;
        if (handler.hasMessages(value)) {
            handler.removeMessages(customizerInteractionType.getValue());
        }
        handler.sendMessage(handler.obtainMessage(customizerInteractionType.getValue(), messageObjectAnim));
        if (customTileInfo == null || (secCustomizeTileView = customTileInfo.customTileView) == null) {
            return;
        }
        int i = StringCompanionObject.$r8$clinit;
        String string = this.context.getString(R.string.qs_custom_action_long_pressed);
        SecCustomizeTileView secCustomizeTileView2 = customTileInfo.customTileView;
        secCustomizeTileView.announceForAccessibility(String.format(string, Arrays.copyOf(new Object[]{(secCustomizeTileView2 == null || (textView = secCustomizeTileView2.mLabel) == null) ? null : textView.getText()}, 1)) + this.context.getString(R.string.qs_custom_action_drag_to_move));
    }

    public final void moveToArea(MessageObjectAnim messageObjectAnim, SecQSCustomizerTileAdapter secQSCustomizerTileAdapter) {
        CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
        int i = messageObjectAnim.animationType;
        CustomizerInteractionType customizerInteractionType = CustomizerInteractionType.ACTIVE_TO_AVAILABLE;
        int value = customizerInteractionType.getValue();
        CustomizerTileViewPager customizerTileViewPager = this.mAvailableTileLayout;
        CustomizerTileViewPager customizerTileViewPager2 = this.mActiveTileLayout;
        if (i == value) {
            customTileInfo.customizeTileContentDes = this.context.getString(R.string.qs_edit_setting_available_area_tapped) + " " + ((Object) customTileInfo.state.label) + ", ";
            customTileInfo.isActive = false;
            customizerTileViewPager2.removeTile(customTileInfo);
            customizerTileViewPager.addTile(customTileInfo, messageObjectAnim.touchedPos);
        } else {
            boolean z = this.mIsTopEdit;
            if (z) {
                int minimumTileNum = customizerTileViewPager2.getMinimumTileNum();
                int i2 = this.mTopMinMaxNum;
                if (minimumTileNum >= i2 && customizerTileViewPager2.mDummyTile == null) {
                    QSTileCustomizerBase qSTileCustomizerBase = this.mView;
                    qSTileCustomizerBase.mMinNum = i2;
                    Resources resources = qSTileCustomizerBase.mContext.getResources();
                    int i3 = qSTileCustomizerBase.mMinNum;
                    String quantityString = resources.getQuantityString(R.plurals.sec_qs_unable_add_maximum, i3, Integer.valueOf(i3));
                    if (qSTileCustomizerBase.mToast == null) {
                        qSTileCustomizerBase.mToast = Toast.makeText(qSTileCustomizerBase.mContext, "", 0);
                    }
                    qSTileCustomizerBase.mToast.setText(quantityString);
                    qSTileCustomizerBase.mToast.show();
                    return;
                }
            }
            if (!z && messageObjectAnim.touchedPos >= 9999) {
                this.mActiveScrollView.fullScroll(130);
            }
            customTileInfo.customizeTileContentDes = this.context.getString(R.string.qs_edit_setting_active_area_tapped) + " " + ((Object) customTileInfo.state.label) + ", ";
            customTileInfo.isActive = true;
            customizerTileViewPager2.addTile(customTileInfo, messageObjectAnim.touchedPos);
            customizerTileViewPager.removeTile(customTileInfo);
        }
        secQSCustomizerTileAdapter.mIsReset = false;
        this.interactionManager.mWhereAmI = (i == customizerInteractionType.getValue() ? CustomizerInteractionType.AREA_AVAILABLE : CustomizerInteractionType.AREA_ACTIVE).getValue();
    }

    public final void removeAreaAnimationMessage() {
        CustomizerInteractionType customizerInteractionType = CustomizerInteractionType.MSG_HANDLE_ANIMATE_AREA;
        int value = customizerInteractionType.getValue();
        Handler handler = this.mHandler;
        if (handler.hasMessages(value)) {
            handler.removeMessages(customizerInteractionType.getValue());
        }
    }
}
