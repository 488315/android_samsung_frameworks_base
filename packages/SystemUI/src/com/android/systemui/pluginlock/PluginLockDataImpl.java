package com.android.systemui.pluginlock;

import android.content.Context;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class PluginLockDataImpl implements PluginLockData, PluginLockListener.State {
    private static final int DEFAULT_GRAVITY = -1;
    private static final int DEFAULT_N_CARD_COUNT = 3;
    private static final int DEFAULT_PADDING = -1;
    private static final int DEFAULT_VISIBILITY = -1;
    public static final String TAG = "PluginLockDataImpl";
    private final Context mContext;
    private DynamicLockData mData;
    private final PluginLockMediator mMediator;

    public PluginLockDataImpl(Context context, PluginLockMediator pluginLockMediator) {
        this.mContext = context;
        this.mMediator = pluginLockMediator;
        pluginLockMediator.registerStateCallback(this);
    }

    private boolean isLandscape() {
        Context context = this.mContext;
        return context != null && context.getResources().getConfiguration().orientation == 2;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getBottom(int i) {
        if (this.mData == null) {
            return -1;
        }
        if (isLandscape()) {
            if (i != 1) {
                return -1;
            }
            return this.mData.getServiceBoxData().getBottomYLand().intValue();
        }
        if (i != 1) {
            return -1;
        }
        return this.mData.getServiceBoxData().getBottomY().intValue();
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getCount(int i) {
        if (this.mData == null) {
            return 3;
        }
        if (isLandscape()) {
            if (i != 4) {
                return 3;
            }
            return this.mData.getNotificationData().getCardData().getNotiCardNumbersLand().intValue();
        }
        if (i != 4) {
            return 3;
        }
        return this.mData.getNotificationData().getCardData().getNotiCardNumbers().intValue();
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getGravity(int i) {
        if (this.mData == null) {
            return -1;
        }
        if (isLandscape()) {
            if (i == 1) {
                return this.mData.getServiceBoxData().getClockInfo().getGravityLand().intValue();
            }
            if (i == 2) {
                return this.mData.getMusicData().getGravityLand().intValue();
            }
            if (i != 3) {
                return -1;
            }
            return this.mData.getNotificationData().getIconOnlyData().getGravityLand().intValue();
        }
        if (i == 1) {
            return this.mData.getServiceBoxData().getClockInfo().getGravity().intValue();
        }
        if (i == 2) {
            return this.mData.getMusicData().getGravity().intValue();
        }
        if (i != 3) {
            return -1;
        }
        return this.mData.getNotificationData().getIconOnlyData().getGravity().intValue();
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getPaddingBottom(int i) {
        if (this.mData == null) {
            return -1;
        }
        if (isLandscape()) {
            if (i != 5) {
                return -1;
            }
            return this.mData.getIndicationData().getHelpTextData().getPaddingBottomLand().intValue();
        }
        if (i != 5) {
            return -1;
        }
        return this.mData.getIndicationData().getHelpTextData().getPaddingBottom().intValue();
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getPaddingEnd(int i) {
        if (this.mData == null) {
            return -1;
        }
        if (isLandscape()) {
            if (i == 1) {
                return this.mData.getServiceBoxData().getClockInfo().getPaddingEndLand().intValue();
            }
            if (i != 3) {
                return -1;
            }
            return this.mData.getNotificationData().getIconOnlyData().getPaddingEndLand().intValue();
        }
        if (i == 1) {
            return this.mData.getServiceBoxData().getClockInfo().getPaddingEnd().intValue();
        }
        if (i != 3) {
            return -1;
        }
        return this.mData.getNotificationData().getIconOnlyData().getPaddingEnd().intValue();
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getPaddingStart(int i) {
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

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getTop(int i) {
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

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getVisibility(int i) {
        if (this.mData == null) {
            return -1;
        }
        if (isLandscape()) {
            switch (i) {
                case 1:
                    return this.mData.getServiceBoxData().getVisibilityLand().intValue();
                case 2:
                    return this.mData.getMusicData().getVisibilityLand().intValue();
                case 3:
                case 4:
                    return this.mData.getNotificationData().getVisibility().intValue();
                case 5:
                    return this.mData.getIndicationData().getHelpTextData().getVisibilityLand().intValue();
                case 6:
                    return this.mData.getShortcutData().getVisibility().intValue();
                case 7:
                    return this.mData.getIndicationData().getLockIconData().getVisibilityLand().intValue();
                default:
                    return -1;
            }
        }
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

    @Override // com.android.systemui.pluginlock.PluginLockData
    public boolean isAvailable() {
        DynamicLockData dynamicLockData = this.mData;
        if (dynamicLockData == null || dynamicLockData.isDlsData()) {
            return false;
        }
        boolean isLandscape = isLandscape();
        DynamicLockData dynamicLockData2 = this.mData;
        return isLandscape ? dynamicLockData2.isLandscapeAvailable() : dynamicLockData2.isPortraitAvailable();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public void onPluginLockReset() {
        this.mData = null;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public void setDynamicLockData(String str) {
        LogUtil.d(TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("setDynamicLockData : ", str), new Object[0]);
        this.mData = DynamicLockData.fromJSon(str);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public void updateDynamicLockData(String str) {
        LogUtil.d(TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("updateDynamicLockData : ", str), new Object[0]);
        this.mData = DynamicLockData.fromJSon(str);
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public boolean isAvailable(int i) {
        DynamicLockData dynamicLockData = this.mData;
        if (dynamicLockData == null || dynamicLockData.isDlsData()) {
            return false;
        }
        if (i == 2) {
            return this.mData.isLandscapeAvailable();
        }
        return this.mData.isPortraitAvailable();
    }
}
