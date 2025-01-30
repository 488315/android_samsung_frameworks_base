package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.component.PluginLockFaceWidget;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.pluginlock.model.ServiceBoxData;
import com.android.systemui.plugins.keyguardstatusview.PluginClockProvider;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockFaceWidget extends AbstractPluginLockItem {
    public final C19181 mClockCallback;
    public int mClockGravity;
    public int mClockGravityLand;
    public final ExternalClockProvider mClockProvider;
    public int mClockRecoverType;
    public float mClockScale;
    public float mClockScaleLand;
    public int mClockType;
    public int mClockVisibility;
    public int mClockVisibilityLand;
    public boolean mIsDlsData;
    public boolean mIsLandscapeAvailable;
    public boolean mIsPortraitAvailable;
    public final PluginLockMediator mMediator;
    public int mServiceBoxBottom;
    public int mServiceBoxBottomLand;
    public int mServiceBoxPaddingEnd;
    public int mServiceBoxPaddingEndLand;
    public int mServiceBoxPaddingStart;
    public int mServiceBoxPaddingStartLand;
    public int mServiceBoxTop;
    public int mServiceBoxTopLand;
    public List mStateListenerList;

    /* renamed from: -$$Nest$mupdateLockStarData, reason: not valid java name */
    public static void m1584$$Nest$mupdateLockStarData(PluginLockFaceWidget pluginLockFaceWidget, String str, Object obj) {
        PluginLockMediator pluginLockMediator = pluginLockFaceWidget.mMediator;
        if (pluginLockMediator == null) {
            Log.w("PluginLockFaceWidget", "updateLockStarData, mediator is null");
            return;
        }
        Log.i("PluginLockFaceWidget", "updateLockStarData() " + str + "=" + obj);
        Bundle bundle = new Bundle();
        bundle.putString("action", "update_lockstar_data");
        Bundle bundle2 = new Bundle();
        bundle2.putString("update_lockstar_data_item", str);
        if (obj instanceof Integer) {
            bundle2.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Float) {
            bundle2.putFloat(str, ((Float) obj).floatValue());
        } else {
            if (!(obj instanceof Boolean)) {
                Log.e("PluginLockFaceWidget", "updateLockStarData() unexpected value type");
                return;
            }
            bundle2.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        bundle.putBundle("extras", bundle2);
        ((PluginLockMediatorImpl) pluginLockMediator).onEventReceived(bundle);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.pluginlock.component.PluginLockFaceWidget$1] */
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
            public final void onAODClockStyleChanged() {
                Log.i("PluginLockFaceWidget", "onAODClockStyleChanged() ");
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public final void onClockColorChanged() {
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
                Log.i("PluginLockFaceWidget", sb.toString());
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public final void onClockFontChanged() {
                StringBuilder sb = new StringBuilder("onClockFontChanged() ");
                PluginClockProvider pluginClockProvider = PluginLockFaceWidget.this.mClockProvider.mClockProvider;
                TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, pluginClockProvider == null ? 0 : pluginClockProvider.getClockFontType(), "PluginLockFaceWidget");
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public final void onClockPackageChanged() {
                Log.i("PluginLockFaceWidget", "onClockPackageChanged() ");
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public final void onClockPositionChanged(boolean z) {
                ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onClockPositionChanged() isCustomPositionChanged=", z, "PluginLockFaceWidget");
                PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w("PluginLockFaceWidget", "onClockPositionChanged() wrong state");
                } else if (System.currentTimeMillis() - pluginLockFaceWidget.mCallbackRegisterTime < 8000) {
                    Log.i("PluginLockFaceWidget", "onClockPositionChanged() ignored");
                } else {
                    if (pluginLockFaceWidget.mIsDlsData) {
                        return;
                    }
                    PluginLockFaceWidget.m1584$$Nest$mupdateLockStarData(pluginLockFaceWidget, "clock_position_changed", Boolean.valueOf(z));
                }
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public final void onClockScaleChanged() {
                PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                PluginClockProvider pluginClockProvider = pluginLockFaceWidget.mClockProvider.mClockProvider;
                float clockScale = pluginClockProvider == null ? 1.0f : pluginClockProvider.getClockScale();
                Log.i("PluginLockFaceWidget", "onClockScaleChanged() " + clockScale);
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w("PluginLockFaceWidget", "onClockScaleChanged() wrong state");
                } else if (System.currentTimeMillis() - pluginLockFaceWidget.mCallbackRegisterTime < 8000) {
                    Log.i("PluginLockFaceWidget", "onClockScaleChanged() ignored");
                } else {
                    if (pluginLockFaceWidget.mIsDlsData) {
                        return;
                    }
                    PluginLockFaceWidget.m1584$$Nest$mupdateLockStarData(pluginLockFaceWidget, "clock_scale", Float.valueOf(clockScale));
                }
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public final void onClockStyleChanged(boolean z) {
                PluginLockInstanceData.Data.RecoverData recoverData;
                StringBuilder sb = new StringBuilder("onClockStyleChanged() mCallbackRegisterTime: ");
                final PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                sb.append(pluginLockFaceWidget.mCallbackRegisterTime);
                Log.i("PluginLockFaceWidget", sb.toString());
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w("PluginLockFaceWidget", "onClockStyleChanged() wrong state");
                    return;
                }
                if (System.currentTimeMillis() - pluginLockFaceWidget.mCallbackRegisterTime < 8000) {
                    Log.i("PluginLockFaceWidget", "onClockStyleChanged() ignored");
                    return;
                }
                if (!pluginLockFaceWidget.mIsDlsData) {
                    PluginClockProvider pluginClockProvider = pluginLockFaceWidget.mClockProvider.mClockProvider;
                    PluginLockFaceWidget.m1584$$Nest$mupdateLockStarData(pluginLockFaceWidget, "clock_type", Integer.valueOf(pluginClockProvider != null ? pluginClockProvider.getLockClockType() : 2));
                }
                if (pluginLockFaceWidget.mClockRecoverType != 2) {
                    if (z) {
                        pluginLockFaceWidget.reset(false);
                        return;
                    }
                    RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("recover() mClockRecoverType: "), pluginLockFaceWidget.mClockRecoverType, "PluginLockFaceWidget");
                    if (pluginLockFaceWidget.mClockRecoverType != 2) {
                        pluginLockFaceWidget.setClockBackupValue(-1);
                        PluginLockInstanceState pluginLockInstanceState2 = pluginLockFaceWidget.mInstanceState;
                        if (pluginLockInstanceState2 != null && (recoverData = pluginLockInstanceState2.getRecoverData()) != null) {
                            recoverData.setClockState(-2);
                            pluginLockFaceWidget.mInstanceState.updateDb();
                        }
                        pluginLockFaceWidget.putSettingsSecure(-1, "plugin_lock_clock");
                        pluginLockFaceWidget.mCallbackValue = -1;
                        pluginLockFaceWidget.mCallbackRegisterTime = 0L;
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.pluginlock.component.PluginLockFaceWidget$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                PluginLockFaceWidget pluginLockFaceWidget2 = PluginLockFaceWidget.this;
                                PluginLockFaceWidget.C19181 c19181 = pluginLockFaceWidget2.mClockCallback;
                                ExternalClockProvider externalClockProvider2 = pluginLockFaceWidget2.mClockProvider;
                                PluginClockProvider pluginClockProvider2 = externalClockProvider2.mClockProvider;
                                if (pluginClockProvider2 != null) {
                                    pluginClockProvider2.unregisterClockChangedCallback(c19181);
                                }
                                ((ArrayList) externalClockProvider2.mClockCallbacks).remove(c19181);
                            }
                        });
                    }
                }
            }

            @Override // com.android.systemui.plugins.keyguardstatusview.PluginClockProvider.ClockCallback
            public final void onClockVisibilityChanged() {
                PluginLockFaceWidget pluginLockFaceWidget = PluginLockFaceWidget.this;
                PluginClockProvider pluginClockProvider = pluginLockFaceWidget.mClockProvider.mClockProvider;
                int clockVisibility = pluginClockProvider == null ? 0 : pluginClockProvider.getClockVisibility();
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("onClockVisibilityChanged() ", clockVisibility, "PluginLockFaceWidget");
                if (pluginLockFaceWidget.mCallbackRegisterTime == 0 || pluginLockFaceWidget.mCallbackValue == -1) {
                    Log.w("PluginLockFaceWidget", "onClockVisibilityChanged() wrong state");
                } else if (System.currentTimeMillis() - pluginLockFaceWidget.mCallbackRegisterTime < 8000) {
                    Log.i("PluginLockFaceWidget", "onClockVisibilityChanged() ignored");
                } else {
                    if (pluginLockFaceWidget.mIsDlsData) {
                        return;
                    }
                    PluginLockFaceWidget.m1584$$Nest$mupdateLockStarData(pluginLockFaceWidget, "clock_visibility", Integer.valueOf(clockVisibility));
                }
            }
        };
        this.mClockProvider = externalClockProvider;
        this.mMediator = pluginLockMediator;
    }

    public static String getClockData(DynamicLockData dynamicLockData) {
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

    public final void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        loadClockData(dynamicLockData, dynamicLockData2);
        int clockState = getClockState();
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("apply() "), this.mClockType, "PluginLockFaceWidget");
        if (clockState == -2) {
            Log.d("PluginLockFaceWidget", "apply() skip!");
            return;
        }
        if (clockState == -3) {
            update(dynamicLockData, dynamicLockData2);
            return;
        }
        if (this.mClockType != -1) {
            ExternalClockProvider externalClockProvider = this.mClockProvider;
            PluginClockProvider pluginClockProvider = externalClockProvider.mClockProvider;
            int lockClockType = pluginClockProvider != null ? pluginClockProvider.getLockClockType() : 2;
            Log.d("PluginLockFaceWidget", "apply() current clock: " + lockClockType);
            setClockBackupValue(lockClockType);
            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("apply() dls clock setClockType: "), this.mClockType, "PluginLockFaceWidget");
            int i = this.mClockType;
            PluginClockProvider pluginClockProvider2 = externalClockProvider.mClockProvider;
            if (pluginClockProvider2 != null) {
                pluginClockProvider2.setClockType(i);
            }
            putSettingsSecure(this.mClockType, "plugin_lock_clock");
            this.mCallbackValue = this.mClockType;
            this.mCallbackRegisterTime = System.currentTimeMillis();
            PluginClockProvider pluginClockProvider3 = externalClockProvider.mClockProvider;
            C19181 c19181 = this.mClockCallback;
            if (pluginClockProvider3 != null) {
                pluginClockProvider3.registerClockChangedCallback(c19181);
            }
            ArrayList arrayList = (ArrayList) externalClockProvider.mClockCallbacks;
            if (arrayList.contains(c19181)) {
                return;
            }
            arrayList.add(c19181);
        }
    }

    public final void loadClockData(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        PluginLockListener$State pluginLockListener$State;
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
        String clockData = getClockData(dynamicLockData);
        String clockData2 = getClockData(dynamicLockData2);
        boolean z = (clockData == null || clockData2 == null || !clockData.equals(clockData2)) ? false : true;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("loadClockData, isEqual: ", z, "PluginLockFaceWidget");
        if (z) {
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
        Log.d("PluginLockFaceWidget", "loadClockData() bundle: " + bundle.toString());
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference weakReference = (WeakReference) this.mStateListenerList.get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.onClockChanged(bundle);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x011e A[Catch: all -> 0x0128, TryCatch #0 {all -> 0x0128, blocks: (B:23:0x00f0, B:27:0x0105, B:29:0x011e, B:30:0x0121, B:32:0x00f5, B:34:0x00fb), top: B:22:0x00f0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reset(boolean z) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        int intValue;
        PluginClockProvider pluginClockProvider;
        PluginLockListener$State pluginLockListener$State;
        Log.d("PluginLockFaceWidget", "resetClockData()");
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
            WeakReference weakReference = (WeakReference) this.mStateListenerList.get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.onClockChanged(bundle);
            }
        }
        this.mCallbackValue = -1;
        this.mCallbackRegisterTime = 0L;
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        PluginClockProvider pluginClockProvider2 = externalClockProvider.mClockProvider;
        C19181 c19181 = this.mClockCallback;
        if (pluginClockProvider2 != null) {
            pluginClockProvider2.unregisterClockChangedCallback(c19181);
        }
        ((ArrayList) externalClockProvider.mClockCallbacks).remove(c19181);
        int clockState = getClockState();
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("reset() state: ", clockState, "PluginLockFaceWidget");
        if (z) {
            return;
        }
        if (clockState != -1 && clockState != -2) {
            try {
                PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
                if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
                    intValue = recoverData.getClock().intValue();
                    Log.d("PluginLockFaceWidget", "reset() setClockType: " + intValue);
                    pluginClockProvider = externalClockProvider.mClockProvider;
                    if (pluginClockProvider != null) {
                        pluginClockProvider.setClockType(intValue);
                    }
                    putSettingsSecure(-1, "plugin_lock_clock");
                }
                intValue = -1;
                Log.d("PluginLockFaceWidget", "reset() setClockType: " + intValue);
                pluginClockProvider = externalClockProvider.mClockProvider;
                if (pluginClockProvider != null) {
                }
                putSettingsSecure(-1, "plugin_lock_clock");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        setClockBackupValue(-1);
    }

    public final void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        Log.d("PluginLockFaceWidget", "update()");
        loadClockData(dynamicLockData, dynamicLockData2);
        int clockState = getClockState();
        if (clockState == -2 && this.mIsDlsData) {
            Log.d("PluginLockFaceWidget", "update() skip!");
            return;
        }
        if (clockState == -1) {
            apply(dynamicLockData, dynamicLockData2);
            return;
        }
        this.mCallbackValue = -1;
        this.mCallbackRegisterTime = 0L;
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        PluginClockProvider pluginClockProvider = externalClockProvider.mClockProvider;
        C19181 c19181 = this.mClockCallback;
        if (pluginClockProvider != null) {
            pluginClockProvider.unregisterClockChangedCallback(c19181);
        }
        ((ArrayList) externalClockProvider.mClockCallbacks).remove(c19181);
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("update() mClockType: "), this.mClockType, "PluginLockFaceWidget");
        if (this.mClockType == -1) {
            PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
            int intValue = (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) ? -1 : recoverData.getClock().intValue();
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("update() backupClockType: ", intValue, "PluginLockFaceWidget");
            PluginClockProvider pluginClockProvider2 = externalClockProvider.mClockProvider;
            if (pluginClockProvider2 != null) {
                pluginClockProvider2.setClockType(intValue);
            }
            putSettingsSecure(intValue, "plugin_lock_clock");
            setClockBackupValue(-1);
            return;
        }
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("update() setClockType: "), this.mClockType, "PluginLockFaceWidget");
        int i = this.mClockType;
        PluginClockProvider pluginClockProvider3 = externalClockProvider.mClockProvider;
        if (pluginClockProvider3 != null) {
            pluginClockProvider3.setClockType(i);
        }
        putSettingsSecure(this.mClockType, "plugin_lock_clock");
        this.mCallbackValue = this.mClockType;
        this.mCallbackRegisterTime = System.currentTimeMillis();
        PluginClockProvider pluginClockProvider4 = externalClockProvider.mClockProvider;
        if (pluginClockProvider4 != null) {
            pluginClockProvider4.registerClockChangedCallback(c19181);
        }
        ArrayList arrayList = (ArrayList) externalClockProvider.mClockCallbacks;
        if (arrayList.contains(c19181)) {
            return;
        }
        arrayList.add(c19181);
    }
}
