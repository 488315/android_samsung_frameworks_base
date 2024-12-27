package com.android.systemui.plugins.keyguardstatusview;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginFaceWidgetMediaData {
    List<PluginFaceWidgetMediaAction> actions;
    List<Integer> actionsToShowInCompact;
    boolean active;
    String app;
    Drawable appIcon;
    CharSequence artist;
    Icon artwork;
    int backgroundColor;
    PendingIntent clickIntent;
    PluginFaceWidgetMediaDeviceData device;
    int foregroundColor;
    boolean hasCheckedForResume;
    boolean initialized;
    boolean isPlaying;
    String notificationKey;
    String packageName;
    Runnable resumeAction;
    boolean resumption;
    CharSequence song;
    MediaSession.Token token;
    int userId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class PluginFaceWidgetMediaAction {
        Runnable action;
        CharSequence contentDescription;
        Drawable drawable;

        public PluginFaceWidgetMediaAction(Drawable drawable, Runnable runnable, CharSequence charSequence) {
            this.drawable = drawable;
            this.action = runnable;
            this.contentDescription = charSequence;
        }

        public Runnable getAction() {
            return this.action;
        }

        public CharSequence getContentDescription() {
            return this.contentDescription;
        }

        public Drawable getDrawable() {
            return this.drawable;
        }

        public void setAction(Runnable runnable) {
            this.action = runnable;
        }

        public void setContentDescription(CharSequence charSequence) {
            this.contentDescription = charSequence;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class PluginFaceWidgetMediaDeviceData {
        int deviceType;
        boolean enabled;
        Drawable icon;
        String name;

        public PluginFaceWidgetMediaDeviceData(boolean z, Drawable drawable, String str, int i) {
            this.enabled = z;
            this.icon = drawable;
            this.name = str;
            this.deviceType = i;
        }

        public int getDeviceType() {
            return this.deviceType;
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public String getName() {
            return this.name;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setDeviceType(int i) {
            this.deviceType = i;
        }

        public void setEnabled(boolean z) {
            this.enabled = z;
        }

        public void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public void setName(String str) {
            this.name = str;
        }
    }

    public PluginFaceWidgetMediaData(int i, boolean z, int i2, int i3, String str, Drawable drawable, CharSequence charSequence, CharSequence charSequence2, Icon icon, List<PluginFaceWidgetMediaAction> list, List<Integer> list2, String str2, MediaSession.Token token, PendingIntent pendingIntent, PluginFaceWidgetMediaDeviceData pluginFaceWidgetMediaDeviceData, boolean z2, Runnable runnable, boolean z3, String str3, boolean z4, boolean z5) {
        this.userId = i;
        this.initialized = z;
        this.backgroundColor = i2;
        this.foregroundColor = i3;
        this.app = str;
        this.appIcon = drawable;
        this.artist = charSequence;
        this.song = charSequence2;
        this.artwork = icon;
        this.actions = list;
        this.actionsToShowInCompact = list2;
        this.packageName = str2;
        this.token = token;
        this.clickIntent = pendingIntent;
        this.device = pluginFaceWidgetMediaDeviceData;
        this.active = z2;
        this.resumeAction = runnable;
        this.resumption = z3;
        this.notificationKey = str3;
        this.hasCheckedForResume = z4;
        this.isPlaying = z5;
    }

    public List<PluginFaceWidgetMediaAction> getActions() {
        return this.actions;
    }

    public List<Integer> getActionsToShowInCompact() {
        return this.actionsToShowInCompact;
    }

    public String getApp() {
        return this.app;
    }

    public Drawable getAppIcon() {
        return this.appIcon;
    }

    public CharSequence getArtist() {
        return this.artist;
    }

    public Icon getArtwork() {
        return this.artwork;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public PendingIntent getClickIntent() {
        return this.clickIntent;
    }

    public PluginFaceWidgetMediaDeviceData getDevice() {
        return this.device;
    }

    public int getForegroundColor() {
        return this.foregroundColor;
    }

    public String getNotificationKey() {
        return this.notificationKey;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Runnable getResumeAction() {
        return this.resumeAction;
    }

    public CharSequence getSong() {
        return this.song;
    }

    public MediaSession.Token getToken() {
        return this.token;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isHasCheckedForResume() {
        return this.hasCheckedForResume;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public boolean isResumption() {
        return this.resumption;
    }

    public void setActions(List<PluginFaceWidgetMediaAction> list) {
        this.actions = list;
    }

    public void setActionsToShowInCompact(List<Integer> list) {
        this.actionsToShowInCompact = list;
    }

    public void setActive(boolean z) {
        this.active = z;
    }

    public void setApp(String str) {
        this.app = str;
    }

    public void setAppIcon(Drawable drawable) {
        this.appIcon = drawable;
    }

    public void setArtist(CharSequence charSequence) {
        this.artist = charSequence;
    }

    public void setArtwork(Icon icon) {
        this.artwork = icon;
    }

    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
    }

    public void setClickIntent(PendingIntent pendingIntent) {
        this.clickIntent = pendingIntent;
    }

    public void setDevice(PluginFaceWidgetMediaDeviceData pluginFaceWidgetMediaDeviceData) {
        this.device = pluginFaceWidgetMediaDeviceData;
    }

    public void setForegroundColor(int i) {
        this.foregroundColor = i;
    }

    public void setHasCheckedForResume(boolean z) {
        this.hasCheckedForResume = z;
    }

    public void setInitialized(boolean z) {
        this.initialized = z;
    }

    public void setNotificationKey(String str) {
        this.notificationKey = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPlaying(boolean z) {
        this.isPlaying = z;
    }

    public void setResumeAction(Runnable runnable) {
        this.resumeAction = runnable;
    }

    public void setResumption(boolean z) {
        this.resumption = z;
    }

    public void setSong(CharSequence charSequence) {
        this.song = charSequence;
    }

    public void setToken(MediaSession.Token token) {
        this.token = token;
    }

    public void setUserId(int i) {
        this.userId = i;
    }
}
