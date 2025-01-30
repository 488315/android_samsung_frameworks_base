package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.os.Bundle;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.lang.ref.WeakReference;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockMusic extends AbstractPluginLockItem {
    public boolean mIsLandscapeAvailable;
    public boolean mIsPortraitAvailable;
    public int mMusicGravity;
    public int mMusicGravityLand;
    public int mMusicPaddingEnd;
    public int mMusicPaddingEndLand;
    public int mMusicPaddingStart;
    public int mMusicPaddingStartLand;
    public int mMusicPaddingTop;
    public int mMusicPaddingTopLand;
    public int mMusicVisibility;
    public int mMusicVisibilityLand;
    public List mStateListenerList;

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

    public final void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
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

    public final void loadMusicData() {
        PluginLockListener$State pluginLockListener$State;
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
        LogUtil.m223d("PluginLockMusic", "loadMusicData bundle: " + bundle.toString(), new Object[0]);
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference weakReference = (WeakReference) this.mStateListenerList.get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.onMusicChanged(bundle);
            }
        }
    }
}
