package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.os.Bundle;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.lang.ref.WeakReference;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginLockMusic extends AbstractPluginLockItem {
    private static final String TAG = "PluginLockMusic";
    private boolean mIsLandscapeAvailable;
    private boolean mIsPortraitAvailable;
    private int mMusicGravity;
    private int mMusicGravityLand;
    private int mMusicPaddingEnd;
    private int mMusicPaddingEndLand;
    private int mMusicPaddingStart;
    private int mMusicPaddingStartLand;
    private int mMusicPaddingTop;
    private int mMusicPaddingTopLand;
    private int mMusicVisibility;
    private int mMusicVisibilityLand;
    private List<WeakReference<PluginLockListener.State>> mStateListenerList;

    public PluginLockMusic(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mMusicPaddingTop = -1;
        this.mMusicPaddingStart = -1;
        this.mMusicPaddingEnd = -1;
        this.mMusicVisibility = 0;
        this.mMusicGravity = 17;
        this.mMusicPaddingTopLand = -1;
        this.mMusicPaddingStartLand = -1;
        this.mMusicPaddingEndLand = -1;
        this.mMusicVisibilityLand = 0;
        this.mMusicGravityLand = 17;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        this.mMusicPaddingTop = dynamicLockData2.getMusicData().getTopY().intValue();
        this.mMusicPaddingStart = dynamicLockData2.getMusicData().getPaddingStart().intValue();
        this.mMusicPaddingEnd = dynamicLockData2.getMusicData().getPaddingEnd().intValue();
        this.mMusicVisibility = dynamicLockData2.getMusicData().getVisibility().intValue();
        this.mMusicGravity = dynamicLockData2.getMusicData().getGravity().intValue();
        this.mMusicPaddingTopLand = dynamicLockData2.getMusicData().getTopYLand().intValue();
        this.mMusicPaddingStartLand = dynamicLockData2.getMusicData().getPaddingStartLand().intValue();
        this.mMusicPaddingEndLand = dynamicLockData2.getMusicData().getPaddingEndLand().intValue();
        this.mMusicVisibilityLand = dynamicLockData2.getMusicData().getVisibilityLand().intValue();
        this.mMusicGravityLand = dynamicLockData2.getMusicData().getGravityLand().intValue();
        this.mIsPortraitAvailable = dynamicLockData2.isPortraitAvailable();
        this.mIsLandscapeAvailable = dynamicLockData2.isLandscapeAvailable();
        loadMusicData();
    }

    public void loadMusicData() {
        PluginLockListener.State state;
        Bundle bundle = new Bundle();
        bundle.putInt(PluginLock.KEY_MUSIC_TOP_PADDING, this.mMusicPaddingTop);
        bundle.putInt(PluginLock.KEY_MUSIC_VISIBILITY, this.mMusicVisibility);
        bundle.putInt(PluginLock.KEY_MUSIC_START_PADDING, this.mMusicPaddingStart);
        bundle.putInt(PluginLock.KEY_MUSIC_END_PADDING, this.mMusicPaddingEnd);
        bundle.putInt(PluginLock.KEY_MUSIC_GRAVITY, this.mMusicGravity);
        bundle.putInt(PluginLock.KEY_MUSIC_TOP_PADDING_LAND, this.mMusicPaddingTopLand);
        bundle.putInt(PluginLock.KEY_MUSIC_VISIBILITY_LAND, this.mMusicVisibilityLand);
        bundle.putInt(PluginLock.KEY_MUSIC_START_PADDING_LAND, this.mMusicPaddingStartLand);
        bundle.putInt(PluginLock.KEY_MUSIC_END_PADDING_LAND, this.mMusicPaddingEndLand);
        bundle.putInt(PluginLock.KEY_MUSIC_GRAVITY_LAND, this.mMusicGravityLand);
        bundle.putBoolean(PluginLock.KEY_MUSIC_AVAILABLE, this.mIsPortraitAvailable);
        bundle.putBoolean(PluginLock.KEY_MUSIC_AVAILABLE_LAND, this.mIsLandscapeAvailable);
        LogUtil.d(TAG, "loadMusicData bundle: " + bundle.toString(), new Object[0]);
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.onMusicChanged(bundle);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
        reset(false);
    }

    public void registerStateCallback(List<WeakReference<PluginLockListener.State>> list) {
        this.mStateListenerList = list;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        this.mMusicPaddingTop = -1;
        this.mMusicPaddingStart = -1;
        this.mMusicPaddingEnd = -1;
        this.mMusicVisibility = 0;
        this.mMusicPaddingTopLand = -1;
        this.mMusicPaddingStartLand = -1;
        this.mMusicPaddingEndLand = -1;
        this.mMusicVisibilityLand = 0;
        this.mMusicGravityLand = 17;
        loadMusicData();
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(i, pluginLockInstanceState);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        apply(dynamicLockData, dynamicLockData2);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(pluginLockInstanceState);
    }
}
