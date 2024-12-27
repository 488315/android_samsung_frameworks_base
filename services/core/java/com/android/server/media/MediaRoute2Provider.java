package com.android.server.media;

import android.content.ComponentName;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.RouteDiscoveryPreference;
import android.media.RoutingSessionInfo;
import android.os.Bundle;
import android.os.UserHandle;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class MediaRoute2Provider {
    public Callback mCallback;
    public final ComponentName mComponentName;
    public boolean mIsSystemRouteProvider;
    public volatile MediaRoute2ProviderInfo mProviderInfo;
    public final String mUniqueId;
    public final Object mLock = new Object();
    public final List mSessionInfos = new ArrayList();

    public interface Callback {}

    public final class SessionCreationOrTransferRequest {
        public final long mRequestId;
        public final String mTargetOriginalRouteId;
        public final String mTransferInitiatorPackageName;
        public final UserHandle mTransferInitiatorUserHandle;
        public final int mTransferReason;

        public SessionCreationOrTransferRequest(
                long j, String str, int i, UserHandle userHandle, String str2) {
            this.mRequestId = j;
            this.mTargetOriginalRouteId = str;
            this.mTransferReason = i;
            this.mTransferInitiatorUserHandle = userHandle;
            this.mTransferInitiatorPackageName = str2;
        }
    }

    public MediaRoute2Provider(ComponentName componentName) {
        Objects.requireNonNull(componentName, "Component name must not be null.");
        this.mComponentName = componentName;
        this.mUniqueId = componentName.flattenToShortString();
    }

    public abstract void deselectRoute(String str, long j, String str2);

    public final void dump(PrintWriter printWriter, String str) {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m.append(getDebugString());
        printWriter.println(m.toString());
        String str2 = str + "  ";
        if (this.mProviderInfo == null) {
            printWriter.println(str2 + "<provider info not received, yet>");
        } else if (this.mProviderInfo.getRoutes().isEmpty()) {
            printWriter.println(str2 + "<provider info has no routes>");
        } else {
            for (MediaRoute2Info mediaRoute2Info : this.mProviderInfo.getRoutes()) {
                printWriter.printf(
                        "%s%s | %s\n", str2, mediaRoute2Info.getId(), mediaRoute2Info.getName());
            }
        }
        printWriter.println(str2 + "Active routing sessions:");
        synchronized (this.mLock) {
            try {
                if (((ArrayList) this.mSessionInfos).isEmpty()) {
                    printWriter.println(str2 + "  <no active routing sessions>");
                } else {
                    Iterator it = ((ArrayList) this.mSessionInfos).iterator();
                    while (it.hasNext()) {
                        ((RoutingSessionInfo) it.next()).dump(printWriter, str2 + "  ");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract String getDebugString();

    public final List getSessionInfos() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mSessionInfos);
        }
        return arrayList;
    }

    public final void notifyProviderState() {
        Callback callback = this.mCallback;
        if (callback != null) {
            MediaRouter2ServiceImpl.UserHandler userHandler =
                    (MediaRouter2ServiceImpl.UserHandler) callback;
            userHandler.getClass();
            userHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(2),
                            userHandler,
                            this));
        }
    }

    public abstract void prepareReleaseSession(String str);

    public abstract void releaseSession(long j, String str);

    public abstract void requestCreateSession(
            long j,
            String str,
            String str2,
            Bundle bundle,
            int i,
            UserHandle userHandle,
            String str3);

    public abstract void selectRoute(String str, long j, String str2);

    public final void setProviderState(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
        if (mediaRoute2ProviderInfo == null) {
            this.mProviderInfo = null;
        } else {
            this.mProviderInfo =
                    new MediaRoute2ProviderInfo.Builder(mediaRoute2ProviderInfo)
                            .setUniqueId(this.mComponentName.getPackageName(), this.mUniqueId)
                            .setSystemRouteProvider(this.mIsSystemRouteProvider)
                            .build();
        }
    }

    public abstract void setRouteVolume(int i, String str, long j);

    public abstract void setSessionVolume(int i, String str, long j);

    public final String toString() {
        return getDebugString();
    }

    public abstract void transferToRoute(
            long j, UserHandle userHandle, String str, String str2, String str3, int i);

    public abstract void updateDiscoveryPreference(
            Set set, RouteDiscoveryPreference routeDiscoveryPreference);
}
