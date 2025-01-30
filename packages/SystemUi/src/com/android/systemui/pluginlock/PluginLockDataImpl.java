package com.android.systemui.pluginlock;

import android.content.Context;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockDataImpl implements PluginLockData, PluginLockListener$State {
    public final Context mContext;
    public DynamicLockData mData;

    public PluginLockDataImpl(Context context, PluginLockMediator pluginLockMediator) {
        this.mContext = context;
        ((PluginLockMediatorImpl) pluginLockMediator).registerStateCallback(this);
    }

    public final int getPaddingStart(int i) {
        if (this.mData == null) {
            return -1;
        }
        if (isLandscape()) {
            if (i == 1) {
                return this.mData.getServiceBoxData().getClockInfo().getPaddingStartLand().intValue();
            }
            if (i == 3) {
                return this.mData.getNotificationData().getIconOnlyData().getPaddingStartLand().intValue();
            }
            if (i != 4) {
                return -1;
            }
            return this.mData.getNotificationData().getCardData().getPaddingStartLand().intValue();
        }
        if (i == 1) {
            return this.mData.getServiceBoxData().getClockInfo().getPaddingStart().intValue();
        }
        if (i == 3) {
            return this.mData.getNotificationData().getIconOnlyData().getPaddingStart().intValue();
        }
        if (i != 4) {
            return -1;
        }
        return this.mData.getNotificationData().getCardData().getPaddingStart().intValue();
    }

    public final int getTop(int i) {
        if (this.mData == null) {
            return -1;
        }
        if (isLandscape()) {
            if (i == 1) {
                return this.mData.getServiceBoxData().getTopYLand().intValue();
            }
            if (i == 2) {
                return this.mData.getMusicData().getTopYLand().intValue();
            }
            if (i == 3) {
                return this.mData.getNotificationData().getIconOnlyData().getTopYLand().intValue();
            }
            if (i != 4) {
                return -1;
            }
            return this.mData.getNotificationData().getCardData().getTopYLand().intValue();
        }
        if (i == 1) {
            return this.mData.getServiceBoxData().getTopY().intValue();
        }
        if (i == 2) {
            return this.mData.getMusicData().getTopY().intValue();
        }
        if (i == 3) {
            return this.mData.getNotificationData().getIconOnlyData().getTopY().intValue();
        }
        if (i != 4) {
            return -1;
        }
        return this.mData.getNotificationData().getCardData().getTopY().intValue();
    }

    public final int getVisibility(int i) {
        int intValue;
        if (this.mData == null) {
            return -1;
        }
        if (!isLandscape()) {
            switch (i) {
                case 1:
                    return this.mData.getServiceBoxData().getVisibility().intValue();
                case 2:
                    return this.mData.getMusicData().getVisibility().intValue();
                case 3:
                case 4:
                    return this.mData.getNotificationData().getVisibility().intValue();
                case 5:
                    return this.mData.getIndicationData().getHelpTextData().getVisibility().intValue();
                case 6:
                    return this.mData.getShortcutData().getVisibility().intValue();
                case 7:
                    return this.mData.getIndicationData().getLockIconData().getVisibility().intValue();
                default:
                    return -1;
            }
        }
        switch (i) {
            case 1:
                intValue = this.mData.getServiceBoxData().getVisibilityLand().intValue();
                break;
            case 2:
                intValue = this.mData.getMusicData().getVisibilityLand().intValue();
                break;
            case 3:
            case 4:
                intValue = this.mData.getNotificationData().getVisibility().intValue();
                break;
            case 5:
                intValue = this.mData.getIndicationData().getHelpTextData().getVisibilityLand().intValue();
                break;
            case 6:
                intValue = this.mData.getShortcutData().getVisibility().intValue();
                break;
            case 7:
                intValue = this.mData.getIndicationData().getLockIconData().getVisibilityLand().intValue();
                break;
            default:
                return -1;
        }
        return intValue;
    }

    public final boolean isAvailable() {
        DynamicLockData dynamicLockData = this.mData;
        if (dynamicLockData == null || dynamicLockData.isDlsData()) {
            return false;
        }
        boolean isLandscape = isLandscape();
        DynamicLockData dynamicLockData2 = this.mData;
        return isLandscape ? dynamicLockData2.isLandscapeAvailable() : dynamicLockData2.isPortraitAvailable();
    }

    public final boolean isLandscape() {
        Context context = this.mContext;
        return context != null && context.getResources().getConfiguration().orientation == 2;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void onPluginLockReset() {
        this.mData = null;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void setDynamicLockData(String str) {
        LogUtil.m223d("PluginLockDataImpl", KeyAttributes$$ExternalSyntheticOutline0.m21m("setDynamicLockData : ", str), new Object[0]);
        this.mData = DynamicLockData.fromJSon(str);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void updateDynamicLockData(String str) {
        LogUtil.m223d("PluginLockDataImpl", KeyAttributes$$ExternalSyntheticOutline0.m21m("updateDynamicLockData : ", str), new Object[0]);
        this.mData = DynamicLockData.fromJSon(str);
    }
}
