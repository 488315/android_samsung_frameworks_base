package com.android.server.media;

import android.content.ComponentName;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.RouteDiscoveryPreference;
import android.media.RoutingSessionInfo;
import android.os.Bundle;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class MediaRoute2Provider {
    public Callback mCallback;
    public final ComponentName mComponentName;
    public boolean mIsSystemRouteProvider;
    public volatile MediaRoute2ProviderInfo mProviderInfo;
    public final String mUniqueId;
    public final Object mLock = new Object();
    public final List mSessionInfos = new ArrayList();

    public interface Callback {
        void onProviderStateChanged(MediaRoute2Provider mediaRoute2Provider);

        void onRequestFailed(MediaRoute2Provider mediaRoute2Provider, long j, int i);

        void onSessionCreated(MediaRoute2Provider mediaRoute2Provider, long j, RoutingSessionInfo routingSessionInfo);

        void onSessionReleased(MediaRoute2Provider mediaRoute2Provider, RoutingSessionInfo routingSessionInfo);

        void onSessionUpdated(MediaRoute2Provider mediaRoute2Provider, RoutingSessionInfo routingSessionInfo);
    }

    public abstract void deselectRoute(long j, String str, String str2);

    public abstract String getDebugString();

    public abstract void prepareReleaseSession(String str);

    public abstract void releaseSession(long j, String str);

    public abstract void requestCreateSession(long j, String str, String str2, Bundle bundle);

    public abstract void selectRoute(long j, String str, String str2);

    public abstract void setRouteVolume(long j, String str, int i);

    public abstract void setSessionVolume(long j, String str, int i);

    public abstract void transferToRoute(long j, String str, String str2);

    public abstract void updateDiscoveryPreference(Set set, RouteDiscoveryPreference routeDiscoveryPreference);

    public MediaRoute2Provider(ComponentName componentName) {
        Objects.requireNonNull(componentName, "Component name must not be null.");
        this.mComponentName = componentName;
        this.mUniqueId = componentName.flattenToShortString();
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public String getUniqueId() {
        return this.mUniqueId;
    }

    public MediaRoute2ProviderInfo getProviderInfo() {
        return this.mProviderInfo;
    }

    public List getSessionInfos() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mSessionInfos);
        }
        return arrayList;
    }

    public void setProviderState(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
        if (mediaRoute2ProviderInfo == null) {
            this.mProviderInfo = null;
        } else {
            this.mProviderInfo = new MediaRoute2ProviderInfo.Builder(mediaRoute2ProviderInfo).setUniqueId(this.mComponentName.getPackageName(), this.mUniqueId).setSystemRouteProvider(this.mIsSystemRouteProvider).build();
        }
    }

    public void notifyProviderState() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onProviderStateChanged(this);
        }
    }

    public void setAndNotifyProviderState(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
        setProviderState(mediaRoute2ProviderInfo);
        notifyProviderState();
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + getDebugString());
        String str2 = str + "  ";
        if (this.mProviderInfo == null) {
            printWriter.println(str2 + "<provider info not received, yet>");
            return;
        }
        if (this.mProviderInfo.getRoutes().isEmpty()) {
            printWriter.println(str2 + "<provider info has no routes>");
            return;
        }
        for (MediaRoute2Info mediaRoute2Info : this.mProviderInfo.getRoutes()) {
            printWriter.printf("%s%s | %s\n", str2, mediaRoute2Info.getId(), mediaRoute2Info.getName());
        }
    }

    public String toString() {
        return getDebugString();
    }
}
