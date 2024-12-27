package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.pluginlock.model.ServiceBoxData;
import com.android.systemui.plugins.keyguardstatusview.PluginClockProvider;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PluginLockFaceWidget extends AbstractPluginLockItem {
    private static final String KEY_CLOCK_POS_CHANGED = "clock_position_changed";
    private static final String KEY_CLOCK_SCALE = "clock_scale";
    private static final String KEY_CLOCK_TYPE = "clock_type";
    private static final String KEY_CLOCK_VISIBILITY = "clock_visibility";
    private static final String KEY_PLUGIN_LOCK_CLOCK = "plugin_lock_clock";
    private static final String TAG = "PluginLockFaceWidget";
    private static final String UPDATE_LOCKSTAR_CLOCK = "update_lockstar_clock";
    private static final String UPDATE_LOCKSTAR_DATA = "update_lockstar_data";
    private static final String UPDATE_LOCKSTAR_DATA_ITEM = "update_lockstar_data_item";
    private final PluginClockProvider.ClockCallback mClockCallback;
    private int mClockGravity;
    private int mClockGravityLand;
    private final ExternalClockProvider mClockProvider;
    private int mClockRecoverType;
    private float mClockScale;
    private float mClockScaleLand;
    private int mClockType;
    private int mClockVisibility;
    private int mClockVisibilityLand;
    private boolean mIsDlsData;
    private boolean mIsLandscapeAvailable;
    private boolean mIsPortraitAvailable;
    private final PluginLockMediator mMediator;
    private int mServiceBoxBottom;
    private int mServiceBoxBottomLand;
    private int mServiceBoxPaddingEnd;
    private int mServiceBoxPaddingEndLand;
    private int mServiceBoxPaddingStart;
    private int mServiceBoxPaddingStartLand;
    private int mServiceBoxTop;
    private int mServiceBoxTopLand;
    private List<WeakReference<PluginLockListener.State>> mStateListenerList;

    public PluginLockFaceWidget(Context context, PluginLockInstanceState pluginLockInstanceState, ExternalClockProvider externalClockProvider, SettingsHelper settingsHelper, PluginLockMediator pluginLockMediator) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mIsDlsData = true;
        this.mClockType = 0;
        this.mClockRecoverType = 1;
        this.mClockScale = -1.0f;
        this.mClockGravity = 1;
        this.mClockVisibility = -1;
        this.mServiceBoxPaddingStart = -1;
        this.mServiceBoxPaddingEnd = -1;
        this.mServiceBoxTop = -1;
        this.mServiceBoxBottom = -1;
        this.mClockScaleLand = -1.0f;
        this.mClockGravityLand = 1;
        this.mClockVisibilityLand = -1;
        this.mServiceBoxPaddingStartLand = -1;
        this.mServiceBoxPaddingEndLand = -1;
        this.mServiceBoxTopLand = -1;
        this.mServiceBoxBottomLand = -1;
        this.mClockCallback = new PluginClockProvider.ClockCallback() { // from class: com.android.systemui.pluginlock.component.PluginLockFaceWidget.1
            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onAODClockStyleChanged() {
                Log.i(PluginLockFaceWidget.TAG, "onAODClockStyleChanged() ");
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onClockColorChanged() {
                int parseColor;
                StringBuilder sb = new StringBuilder("onClockColorChanged() #");
                PluginClockProvider pluginClockProvider = PluginLockFaceWidget.this.mClockProvider.mClockProvider;
                if (pluginClockProvider == null) {
                    parseColor = Color.parseColor("#FFFAFAFA");
                } else {
                    try {
                        parseColor = pluginClockProvider.getClockDateColor();
                    } catch (Error unused) {
                        parseColor = Color.parseColor("#FFFAFAFA");
                    }
                }
                sb.append(Integer.toHexString(parseColor));
                Log.i(PluginLockFaceWidget.TAG, sb.toString());
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onClockFontChanged() {
                StringBuilder sb = new StringBuilder("onClockFontChanged() ");
                PluginClockProvider pluginClockProvider = PluginLockFaceWidget.this.mClockProvider.mClockProvider;
                TooltipPopup$$ExternalSyntheticOutline0.m(pluginClockProvider == null ? 0 : pluginClockProvider.getClockFontType(), PluginLockFaceWidget.TAG, sb);
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onClockPackageChanged() {
                Log.i(PluginLockFaceWidget.TAG, "onClockPackageChanged() ");
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onClockPositionChanged(boolean z) {
                SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onClockPositionChanged() isCustomPositionChanged=", PluginLockFaceWidget.TAG, z);
                PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w(PluginLockFaceWidget.TAG, "onClockPositionChanged() wrong state");
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                PluginLockFaceWidget pluginLockFaceWidget2 = PluginLockFaceWidget.this;
                if (currentTimeMillis - pluginLockFaceWidget2.mCallbackRegisterTime < 8000) {
                    Log.i(PluginLockFaceWidget.TAG, "onClockPositionChanged() ignored");
                } else {
                    if (pluginLockFaceWidget2.mIsDlsData) {
                        return;
                    }
                    PluginLockFaceWidget.this.updateLockStarData(PluginLockFaceWidget.KEY_CLOCK_POS_CHANGED, Boolean.valueOf(z));
                }
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onClockScaleChanged() {
                PluginClockProvider pluginClockProvider = PluginLockFaceWidget.this.mClockProvider.mClockProvider;
                float clockScale = pluginClockProvider == null ? 1.0f : pluginClockProvider.getClockScale();
                Log.i(PluginLockFaceWidget.TAG, "onClockScaleChanged() " + clockScale);
                PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w(PluginLockFaceWidget.TAG, "onClockScaleChanged() wrong state");
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                PluginLockFaceWidget pluginLockFaceWidget2 = PluginLockFaceWidget.this;
                if (currentTimeMillis - pluginLockFaceWidget2.mCallbackRegisterTime < 8000) {
                    Log.i(PluginLockFaceWidget.TAG, "onClockScaleChanged() ignored");
                } else {
                    if (pluginLockFaceWidget2.mIsDlsData) {
                        return;
                    }
                    PluginLockFaceWidget.this.updateLockStarData(PluginLockFaceWidget.KEY_CLOCK_SCALE, Float.valueOf(clockScale));
                }
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onClockStyleChanged(boolean z) {
                Log.i(PluginLockFaceWidget.TAG, "onClockStyleChanged() mCallbackRegisterTime: " + PluginLockFaceWidget.this.mCallbackRegisterTime);
                PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w(PluginLockFaceWidget.TAG, "onClockStyleChanged() wrong state");
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                PluginLockFaceWidget pluginLockFaceWidget2 = PluginLockFaceWidget.this;
                if (currentTimeMillis - pluginLockFaceWidget2.mCallbackRegisterTime < 8000) {
                    Log.i(PluginLockFaceWidget.TAG, "onClockStyleChanged() ignored");
                    return;
                }
                if (!pluginLockFaceWidget2.mIsDlsData) {
                    PluginLockFaceWidget pluginLockFaceWidget3 = PluginLockFaceWidget.this;
                    PluginClockProvider pluginClockProvider = pluginLockFaceWidget3.mClockProvider.mClockProvider;
                    pluginLockFaceWidget3.updateLockStarData(PluginLockFaceWidget.KEY_CLOCK_TYPE, Integer.valueOf(pluginClockProvider != null ? pluginClockProvider.getLockClockType() : 2));
                }
                if (PluginLockFaceWidget.this.mClockRecoverType != 2) {
                    if (z) {
                        PluginLockFaceWidget.this.reset(false);
                    } else {
                        PluginLockFaceWidget.this.recover();
                    }
                }
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public void onClockVisibilityChanged() {
                PluginClockProvider pluginClockProvider = PluginLockFaceWidget.this.mClockProvider.mClockProvider;
                int clockVisibility = pluginClockProvider == null ? 0 : pluginClockProvider.getClockVisibility();
                KeyguardSecPatternView$$ExternalSyntheticOutline0.m(clockVisibility, "onClockVisibilityChanged() ", PluginLockFaceWidget.TAG);
                PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w(PluginLockFaceWidget.TAG, "onClockVisibilityChanged() wrong state");
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                PluginLockFaceWidget pluginLockFaceWidget2 = PluginLockFaceWidget.this;
                if (currentTimeMillis - pluginLockFaceWidget2.mCallbackRegisterTime < 8000) {
                    Log.i(PluginLockFaceWidget.TAG, "onClockVisibilityChanged() ignored");
                } else {
                    if (pluginLockFaceWidget2.mIsDlsData) {
                        return;
                    }
                    PluginLockFaceWidget.this.updateLockStarData(PluginLockFaceWidget.KEY_CLOCK_VISIBILITY, Integer.valueOf(clockVisibility));
                }
            }
        };
        this.mClockProvider = externalClockProvider;
        this.mMediator = pluginLockMediator;
    }

    private String getClockData(DynamicLockData dynamicLockData) {
        if (dynamicLockData == null) {
            return null;
        }
        ServiceBoxData.ClockInfo clockInfo = dynamicLockData.getServiceBoxData().getClockInfo();
        StringBuilder sb = new StringBuilder();
        sb.append(dynamicLockData.isDlsData());
        sb.append(clockInfo.getClockType());
        sb.append(clockInfo.getRecoverType());
        sb.append(clockInfo.getGravity());
        sb.append(clockInfo.getScale());
        sb.append(dynamicLockData.getServiceBoxData().getVisibility());
        sb.append(dynamicLockData.getServiceBoxData().getTopY());
        sb.append(dynamicLockData.getServiceBoxData().getBottomY());
        sb.append(clockInfo.getPaddingStart());
        sb.append(clockInfo.getPaddingEnd());
        sb.append(clockInfo.getGravityLand());
        sb.append(clockInfo.getScaleLand());
        sb.append(dynamicLockData.getServiceBoxData().getVisibilityLand());
        sb.append(dynamicLockData.getServiceBoxData().getTopYLand());
        sb.append(dynamicLockData.getServiceBoxData().getBottomYLand());
        sb.append(clockInfo.getPaddingStartLand());
        sb.append(clockInfo.getPaddingEndLand());
        sb.append(dynamicLockData.isPortraitAvailable());
        sb.append(dynamicLockData.isLandscapeAvailable());
        return sb.toString();
    }

    private boolean isEqual(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        String clockData = getClockData(dynamicLockData);
        String clockData2 = getClockData(dynamicLockData2);
        return (clockData == null || clockData2 == null || !clockData.equals(clockData2)) ? false : true;
    }

    public void lambda$unregisterClockCallbackRecover$0() {
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        PluginClockProvider.ClockCallback clockCallback = this.mClockCallback;
        PluginClockProvider pluginClockProvider = externalClockProvider.mClockProvider;
        if (pluginClockProvider != null) {
            pluginClockProvider.unregisterClockChangedCallback(clockCallback);
        }
        ((ArrayList) externalClockProvider.mClockCallbacks).remove(clockCallback);
    }

    private void registerClockCallback(int i) {
        this.mCallbackValue = i;
        this.mCallbackRegisterTime = System.currentTimeMillis();
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        PluginClockProvider.ClockCallback clockCallback = this.mClockCallback;
        PluginClockProvider pluginClockProvider = externalClockProvider.mClockProvider;
        if (pluginClockProvider != null) {
            pluginClockProvider.registerClockChangedCallback(clockCallback);
        }
        if (((ArrayList) externalClockProvider.mClockCallbacks).contains(clockCallback)) {
            return;
        }
        ((ArrayList) externalClockProvider.mClockCallbacks).add(clockCallback);
    }

    private void resetClockData(boolean z) {
        PluginLockListener.State state;
        if (this.mIsDlsData || z) {
            Log.d(TAG, "resetClockData()");
            this.mClockType = -1;
            this.mClockRecoverType = 1;
            this.mServiceBoxPaddingStart = -1;
            this.mServiceBoxPaddingEnd = -1;
            this.mClockGravity = -1;
            this.mClockScale = -1.0f;
            this.mClockVisibility = -1;
            this.mServiceBoxTop = -1;
            this.mServiceBoxBottom = -1;
            this.mServiceBoxPaddingStartLand = -1;
            this.mServiceBoxPaddingEndLand = -1;
            this.mClockGravityLand = -1;
            this.mClockScaleLand = -1.0f;
            this.mClockVisibilityLand = -1;
            this.mServiceBoxTopLand = -1;
            this.mServiceBoxBottomLand = -1;
            this.mIsPortraitAvailable = false;
            this.mIsLandscapeAvailable = false;
            Bundle bundle = new Bundle();
            bundle.putInt(PluginLock.KEY_PAGE_GRAVITY, this.mClockGravity);
            bundle.putFloat(PluginLock.KEY_PAGE_SCALE, this.mClockScale);
            bundle.putInt(PluginLock.KEY_VISIBILITY, this.mClockVisibility);
            bundle.putInt(PluginLock.KEY_PAGE_TOP_PADDING, this.mServiceBoxTop);
            bundle.putInt(PluginLock.KEY_PAGE_BOTTOM_PADDING, this.mServiceBoxBottom);
            bundle.putInt(PluginLock.KEY_PAGE_START_PADDING, this.mServiceBoxPaddingStart);
            bundle.putInt(PluginLock.KEY_PAGE_END_PADDING, this.mServiceBoxPaddingEnd);
            bundle.putInt(PluginLock.KEY_PAGE_GRAVITY_LAND, this.mClockGravityLand);
            bundle.putFloat(PluginLock.KEY_PAGE_SCALE_LAND, this.mClockScaleLand);
            bundle.putInt(PluginLock.KEY_VISIBILITY_LAND, this.mClockVisibilityLand);
            bundle.putInt(PluginLock.KEY_PAGE_TOP_PADDING_LAND, this.mServiceBoxTopLand);
            bundle.putInt(PluginLock.KEY_PAGE_BOTTOM_PADDING_LAND, this.mServiceBoxBottomLand);
            bundle.putInt(PluginLock.KEY_PAGE_START_PADDING, this.mServiceBoxPaddingStartLand);
            bundle.putInt(PluginLock.KEY_PAGE_END_PADDING, this.mServiceBoxPaddingEndLand);
            bundle.putBoolean(PluginLock.KEY_LOCK_STAR_CLOCK, false);
            bundle.putBoolean(PluginLock.KEY_PAGE_AVAILABLE, this.mIsPortraitAvailable);
            bundle.putBoolean(PluginLock.KEY_PAGE_AVAILABLE_LAND, this.mIsLandscapeAvailable);
            for (int i = 0; i < this.mStateListenerList.size(); i++) {
                WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
                if (weakReference != null && (state = weakReference.get()) != null) {
                    state.onClockChanged(bundle);
                }
            }
        }
    }

    private void unregisterClockCallback() {
        this.mCallbackValue = -1;
        this.mCallbackRegisterTime = 0L;
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        PluginClockProvider.ClockCallback clockCallback = this.mClockCallback;
        PluginClockProvider pluginClockProvider = externalClockProvider.mClockProvider;
        if (pluginClockProvider != null) {
            pluginClockProvider.unregisterClockChangedCallback(clockCallback);
        }
        ((ArrayList) externalClockProvider.mClockCallbacks).remove(clockCallback);
    }

    private void unregisterClockCallbackRecover() {
        this.mCallbackValue = -1;
        this.mCallbackRegisterTime = 0L;
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.pluginlock.component.PluginLockFaceWidget$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PluginLockFaceWidget.this.lambda$unregisterClockCallbackRecover$0();
            }
        });
    }

    public void updateLockStarData(String str, Object obj) {
        if (this.mMediator == null) {
            Log.w(TAG, "updateLockStarData, mediator is null");
            return;
        }
        Log.i(TAG, "updateLockStarData() " + str + "=" + obj);
        Bundle bundle = new Bundle();
        bundle.putString("action", UPDATE_LOCKSTAR_DATA);
        Bundle bundle2 = new Bundle();
        bundle2.putString(UPDATE_LOCKSTAR_DATA_ITEM, str);
        if (obj instanceof Integer) {
            bundle2.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Float) {
            bundle2.putFloat(str, ((Float) obj).floatValue());
        } else {
            if (!(obj instanceof Boolean)) {
                Log.e(TAG, "updateLockStarData() unexpected value type");
                return;
            }
            bundle2.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        bundle.putBundle("extras", bundle2);
        this.mMediator.onEventReceived(bundle);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        loadClockData(dynamicLockData, dynamicLockData2);
        int clockState = getClockState();
        RecyclerView$$ExternalSyntheticOutline0.m(this.mClockType, TAG, new StringBuilder("apply() "));
        if (clockState == -2) {
            Log.d(TAG, "apply() skip!");
            return;
        }
        if (clockState == -3) {
            update(dynamicLockData, dynamicLockData2);
            return;
        }
        if (this.mClockType != -1) {
            PluginClockProvider pluginClockProvider = this.mClockProvider.mClockProvider;
            int lockClockType = pluginClockProvider != null ? pluginClockProvider.getLockClockType() : 2;
            ListPopupWindow$$ExternalSyntheticOutline0.m(lockClockType, "apply() current clock: ", TAG);
            setClockBackupValue(lockClockType);
            RecyclerView$$ExternalSyntheticOutline0.m(this.mClockType, TAG, new StringBuilder("apply() dls clock setClockType: "));
            ExternalClockProvider externalClockProvider = this.mClockProvider;
            int i = this.mClockType;
            PluginClockProvider pluginClockProvider2 = externalClockProvider.mClockProvider;
            if (pluginClockProvider2 != null) {
                pluginClockProvider2.setClockType(i);
            }
            putSettingsSecure("plugin_lock_clock", this.mClockType);
            registerClockCallback(this.mClockType);
        }
    }

    public void loadClockData(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        PluginLockListener.State state;
        this.mIsDlsData = dynamicLockData2.isDlsData();
        ServiceBoxData.ClockInfo clockInfo = dynamicLockData2.getServiceBoxData().getClockInfo();
        this.mClockType = clockInfo.getClockType().intValue();
        this.mClockRecoverType = clockInfo.getRecoverType().intValue();
        this.mClockGravity = clockInfo.getGravity().intValue();
        this.mClockScale = clockInfo.getScale();
        this.mClockVisibility = dynamicLockData2.getServiceBoxData().getVisibility().intValue();
        this.mServiceBoxTop = dynamicLockData2.getServiceBoxData().getTopY().intValue();
        this.mServiceBoxBottom = dynamicLockData2.getServiceBoxData().getBottomY().intValue();
        this.mServiceBoxPaddingStart = clockInfo.getPaddingStart().intValue();
        this.mServiceBoxPaddingEnd = clockInfo.getPaddingEnd().intValue();
        this.mClockGravityLand = clockInfo.getGravityLand().intValue();
        this.mClockScaleLand = clockInfo.getScaleLand();
        this.mClockVisibilityLand = dynamicLockData2.getServiceBoxData().getVisibilityLand().intValue();
        this.mServiceBoxTopLand = dynamicLockData2.getServiceBoxData().getTopYLand().intValue();
        this.mServiceBoxBottomLand = dynamicLockData2.getServiceBoxData().getBottomYLand().intValue();
        this.mServiceBoxPaddingStartLand = clockInfo.getPaddingStartLand().intValue();
        this.mServiceBoxPaddingEndLand = clockInfo.getPaddingEndLand().intValue();
        this.mIsPortraitAvailable = dynamicLockData2.isPortraitAvailable();
        this.mIsLandscapeAvailable = dynamicLockData2.isLandscapeAvailable();
        boolean isEqual = isEqual(dynamicLockData, dynamicLockData2);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("loadClockData, isEqual: ", TAG, isEqual);
        if (isEqual) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(PluginLock.KEY_PAGE_GRAVITY, this.mClockGravity);
        bundle.putFloat(PluginLock.KEY_PAGE_SCALE, this.mClockScale);
        bundle.putInt(PluginLock.KEY_VISIBILITY, this.mClockVisibility);
        bundle.putInt(PluginLock.KEY_PAGE_TOP_PADDING, this.mServiceBoxTop);
        bundle.putInt(PluginLock.KEY_PAGE_BOTTOM_PADDING, this.mServiceBoxBottom);
        bundle.putInt(PluginLock.KEY_PAGE_START_PADDING, this.mServiceBoxPaddingStart);
        bundle.putInt(PluginLock.KEY_PAGE_END_PADDING, this.mServiceBoxPaddingEnd);
        bundle.putInt(PluginLock.KEY_PAGE_GRAVITY_LAND, this.mClockGravityLand);
        bundle.putFloat(PluginLock.KEY_PAGE_SCALE_LAND, this.mClockScaleLand);
        bundle.putInt(PluginLock.KEY_VISIBILITY_LAND, this.mClockVisibilityLand);
        bundle.putInt(PluginLock.KEY_PAGE_TOP_PADDING_LAND, this.mServiceBoxTopLand);
        bundle.putInt(PluginLock.KEY_PAGE_BOTTOM_PADDING_LAND, this.mServiceBoxBottomLand);
        bundle.putInt(PluginLock.KEY_PAGE_START_PADDING_LAND, this.mServiceBoxPaddingStartLand);
        bundle.putInt(PluginLock.KEY_PAGE_END_PADDING_LAND, this.mServiceBoxPaddingEndLand);
        bundle.putBoolean(PluginLock.KEY_PAGE_AVAILABLE, this.mIsPortraitAvailable);
        bundle.putBoolean(PluginLock.KEY_PAGE_AVAILABLE_LAND, this.mIsLandscapeAvailable);
        bundle.putBoolean(PluginLock.KEY_LOCK_STAR_CLOCK, !this.mIsDlsData);
        Log.d(TAG, "loadClockData() bundle: " + bundle.toString());
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.onClockChanged(bundle);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
        RecyclerView$$ExternalSyntheticOutline0.m(this.mClockRecoverType, TAG, new StringBuilder("recover() mClockRecoverType: "));
        if (this.mClockRecoverType != 2) {
            setClockBackupValue(-1);
            setClockState(-2);
            putSettingsSecure("plugin_lock_clock", -1);
            unregisterClockCallbackRecover();
        }
    }

    public void registerStateCallback(List<WeakReference<PluginLockListener.State>> list) {
        this.mStateListenerList = list;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        resetClockData(true);
        unregisterClockCallback();
        int clockState = getClockState();
        ListPopupWindow$$ExternalSyntheticOutline0.m(clockState, "reset() state: ", TAG);
        if (z) {
            return;
        }
        if (clockState != -1 && clockState != -2) {
            try {
                int clockBackupValue = getClockBackupValue();
                Log.d(TAG, "reset() setClockType: " + clockBackupValue);
                PluginClockProvider pluginClockProvider = this.mClockProvider.mClockProvider;
                if (pluginClockProvider != null) {
                    pluginClockProvider.setClockType(clockBackupValue);
                }
                putSettingsSecure("plugin_lock_clock", -1);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        setClockBackupValue(-1);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(i, pluginLockInstanceState);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "update()");
        loadClockData(dynamicLockData, dynamicLockData2);
        int clockState = getClockState();
        if (clockState == -2 && this.mIsDlsData) {
            Log.d(TAG, "update() skip!");
            return;
        }
        if (clockState == -1) {
            apply(dynamicLockData, dynamicLockData2);
            return;
        }
        unregisterClockCallback();
        RecyclerView$$ExternalSyntheticOutline0.m(this.mClockType, TAG, new StringBuilder("update() mClockType: "));
        if (this.mClockType == -1) {
            int clockBackupValue = getClockBackupValue();
            ListPopupWindow$$ExternalSyntheticOutline0.m(clockBackupValue, "update() backupClockType: ", TAG);
            PluginClockProvider pluginClockProvider = this.mClockProvider.mClockProvider;
            if (pluginClockProvider != null) {
                pluginClockProvider.setClockType(clockBackupValue);
            }
            putSettingsSecure("plugin_lock_clock", clockBackupValue);
            setClockBackupValue(-1);
            return;
        }
        RecyclerView$$ExternalSyntheticOutline0.m(this.mClockType, TAG, new StringBuilder("update() setClockType: "));
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        int i = this.mClockType;
        PluginClockProvider pluginClockProvider2 = externalClockProvider.mClockProvider;
        if (pluginClockProvider2 != null) {
            pluginClockProvider2.setClockType(i);
        }
        putSettingsSecure("plugin_lock_clock", this.mClockType);
        registerClockCallback(this.mClockType);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(pluginLockInstanceState);
    }
}
