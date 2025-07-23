package com.android.systemui.media.controls.domain.resume;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListenerKt;
import com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaResumeListener implements MediaDataManager.Listener, Dumpable {
    public final Executor backgroundExecutor;
    public final Context context;
    public int currentUserId;
    public final Executor mainExecutor;
    public ResumeMediaBrowser mediaBrowser;
    public final MediaResumeListener$mediaBrowserCallback$1 mediaBrowserCallback;
    public final ResumeMediaBrowserFactory mediaBrowserFactory;
    public LegacyMediaDataManagerImpl mediaDataManager;
    public final ConcurrentLinkedQueue resumeComponents = new ConcurrentLinkedQueue();
    public final SystemClock systemClock;
    public final TunerService tunerService;
    public boolean useMediaResumption;
    public final UserTracker userTracker;
    public final MediaResumeListener$userTrackerCallback$1 userTrackerCallback;
    public final MediaResumeListener$userUnlockReceiver$1 userUnlockReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v4, types: [com.android.systemui.media.controls.domain.resume.MediaResumeListener$mediaBrowserCallback$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.systemui.media.controls.domain.resume.MediaResumeListener$userUnlockReceiver$1] */
    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.media.controls.domain.resume.MediaResumeListener$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    public MediaResumeListener(Context context, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, Executor executor, Executor executor2, TunerService tunerService, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock) {
        this.context = context;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.tunerService = tunerService;
        this.mediaBrowserFactory = resumeMediaBrowserFactory;
        this.systemClock = systemClock;
        this.useMediaResumption = Utils.useMediaResumption(context);
        this.currentUserId = context.getUserId();
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$userUnlockReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                    if (intExtra == mediaResumeListener.currentUserId && mediaResumeListener.useMediaResumption) {
                        PackageManager packageManager = mediaResumeListener.context.getPackageManager();
                        long currentTimeMillis = mediaResumeListener.systemClock.currentTimeMillis();
                        Iterator it = mediaResumeListener.resumeComponents.iterator();
                        while (it.hasNext()) {
                            Pair pair = (Pair) it.next();
                            if (currentTimeMillis - ((Number) pair.getSecond()).longValue() <= MediaTimeoutListenerKt.RESUME_MEDIA_TIMEOUT) {
                                Intent intent2 = new Intent("android.media.browse.MediaBrowserService");
                                intent2.setComponent((ComponentName) pair.getFirst());
                                if (packageManager.resolveServiceAsUser(intent2, 0, mediaResumeListener.currentUserId) != null) {
                                    ComponentName componentName = (ComponentName) pair.getFirst();
                                    int i = mediaResumeListener.currentUserId;
                                    ResumeMediaBrowserFactory resumeMediaBrowserFactory2 = mediaResumeListener.mediaBrowserFactory;
                                    ResumeMediaBrowser resumeMediaBrowser = new ResumeMediaBrowser(resumeMediaBrowserFactory2.mContext, mediaResumeListener.mediaBrowserCallback, componentName, resumeMediaBrowserFactory2.mBrowserFactory, resumeMediaBrowserFactory2.mLogger, i);
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean("android.service.media.extra.RECENT", true);
                                    ComponentName componentName2 = resumeMediaBrowser.mComponentName;
                                    ResumeMediaBrowser.AnonymousClass2 anonymousClass2 = resumeMediaBrowser.mConnectionCallback;
                                    MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser.mBrowserFactory;
                                    mediaBrowserFactory.getClass();
                                    resumeMediaBrowser.connectBrowser(new MediaBrowser(mediaBrowserFactory.mContext, componentName2, anonymousClass2, bundle), "findRecentMedia");
                                } else if (Log.isLoggable("MediaResumeListener", 3)) {
                                    Log.d("MediaResumeListener", "User " + mediaResumeListener.currentUserId + " does not have component " + pair.getFirst());
                                }
                            }
                        }
                    }
                }
            }
        };
        this.userUnlockReceiver = r1;
        ?? r9 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                mediaResumeListener.currentUserId = i;
                mediaResumeListener.loadSavedComponents();
            }
        };
        this.userTrackerCallback = r9;
        this.mediaBrowserCallback = new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$mediaBrowserCallback$1
            @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
            public final void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
                MediaSession.Token sessionToken = !resumeMediaBrowser.isBrowserConnected() ? null : resumeMediaBrowser.mMediaBrowser.getSessionToken();
                PendingIntent activity = PendingIntent.getActivity(resumeMediaBrowser.mContext, 0, resumeMediaBrowser.mContext.getPackageManager().getLaunchIntentForPackage(resumeMediaBrowser.mComponentName.getPackageName()), 67108864);
                MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                PackageManager packageManager = mediaResumeListener.context.getPackageManager();
                CharSequence packageName = componentName.getPackageName();
                MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1 = new MediaResumeListener$getResumeAction$1(mediaResumeListener, componentName);
                try {
                    packageName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(componentName.getPackageName(), 0));
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("MediaResumeListener", "Error getting package information", e);
                }
                if (Log.isLoggable("MediaResumeListener", 3)) {
                    Log.d("MediaResumeListener", "Adding resume controls for " + resumeMediaBrowser.mUserId + ": " + mediaDescription);
                }
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = mediaResumeListener.mediaDataManager;
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl2 = legacyMediaDataManagerImpl != null ? legacyMediaDataManagerImpl : null;
                sessionToken.getClass();
                String obj = packageName.toString();
                activity.getClass();
                legacyMediaDataManagerImpl2.addResumptionControls(resumeMediaBrowser.mUserId, mediaDescription, mediaResumeListener$getResumeAction$1, sessionToken, obj, activity, componentName.getPackageName());
            }
        };
        if (this.useMediaResumption) {
            dumpManager.registerNormalDumpable("MediaResumeListener", this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r1, intentFilter, executor2, UserHandle.ALL, 0, null, 48);
            ((UserTrackerImpl) userTracker).addCallback(r9, executor);
            loadSavedComponents();
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("resumeComponents: " + this.resumeComponents);
    }

    public final void loadSavedComponents() {
        long currentTimeMillis;
        List split;
        this.resumeComponents.clear();
        boolean z = false;
        Iterable iterable = null;
        String string = this.context.getSharedPreferences("media_control_prefs", 0).getString("browser_components_" + this.currentUserId, null);
        if (string != null && (split = new Regex(":").split(string)) != null) {
            if (!split.isEmpty()) {
                ListIterator listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (((String) listIterator.previous()).length() != 0) {
                        iterable = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            iterable = EmptyList.INSTANCE;
        }
        if (iterable != null) {
            Iterator it = iterable.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                List split$default = StringsKt__StringsKt.split$default((String) it.next(), new String[]{"/"}, 0, 6);
                ComponentName componentName = new ComponentName((String) split$default.get(0), (String) split$default.get(1));
                int size = split$default.size();
                SystemClock systemClock = this.systemClock;
                if (size == 3) {
                    try {
                        currentTimeMillis = Long.parseLong((String) split$default.get(2));
                    } catch (NumberFormatException unused) {
                        currentTimeMillis = systemClock.currentTimeMillis();
                    }
                    this.resumeComponents.add(new Pair(componentName, Long.valueOf(currentTimeMillis)));
                } else {
                    currentTimeMillis = systemClock.currentTimeMillis();
                }
                z2 = true;
                this.resumeComponents.add(new Pair(componentName, Long.valueOf(currentTimeMillis)));
            }
            z = z2;
        }
        if (Log.isLoggable("MediaResumeListener", 3)) {
            KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(this.currentUserId, "loaded resume components for ", ": ", Arrays.toString(this.resumeComponents.toArray()), "MediaResumeListener");
        }
        if (z) {
            writeSharedPrefs();
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(final String str, String str2, final MediaData mediaData, boolean z) {
        if (this.useMediaResumption) {
            if (!str.equals(str2)) {
                setMediaBrowser(null);
            }
            boolean z2 = mediaData.playbackLocation == 0;
            if (mediaData.resumeAction == null && !mediaData.hasCheckedForResume && z2) {
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$onMediaDataLoaded$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = MediaResumeListener.this.mediaDataManager;
                        if (legacyMediaDataManagerImpl == null) {
                            legacyMediaDataManagerImpl = null;
                        }
                        legacyMediaDataManagerImpl.setResumeAction(null, str);
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Checking for service component for ", mediaData.packageName, "MediaResumeListener");
                        List queryIntentServicesAsUser = MediaResumeListener.this.context.getPackageManager().queryIntentServicesAsUser(new Intent("android.media.browse.MediaBrowserService"), 0, MediaResumeListener.this.currentUserId);
                        MediaData mediaData2 = mediaData;
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : queryIntentServicesAsUser) {
                            if (Intrinsics.areEqual(((ResolveInfo) obj).serviceInfo.packageName, mediaData2.packageName)) {
                                arrayList.add(obj);
                            }
                        }
                        if (arrayList.size() > 0) {
                            final MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                            final String str3 = str;
                            final ComponentName componentName = ((ResolveInfo) arrayList.get(0)).getComponentInfo().getComponentName();
                            mediaResumeListener.getClass();
                            Log.d("MediaResumeListener", "Testing if we can connect to " + componentName);
                            ResumeMediaBrowser.Callback callback = new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$tryUpdateResumptionList$1
                                @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
                                public final void addTrack(MediaDescription mediaDescription, ComponentName componentName2, ResumeMediaBrowser resumeMediaBrowser) {
                                    Object obj2;
                                    ComponentName componentName3 = componentName;
                                    if (Log.isLoggable("MediaResumeListener", 3)) {
                                        Log.d("MediaResumeListener", "Can get resumable media for " + resumeMediaBrowser.mUserId + " from " + componentName3);
                                    }
                                    MediaResumeListener mediaResumeListener2 = mediaResumeListener;
                                    LegacyMediaDataManagerImpl legacyMediaDataManagerImpl2 = mediaResumeListener2.mediaDataManager;
                                    if (legacyMediaDataManagerImpl2 == null) {
                                        legacyMediaDataManagerImpl2 = null;
                                    }
                                    legacyMediaDataManagerImpl2.setResumeAction(new MediaResumeListener$getResumeAction$1(mediaResumeListener2, componentName), str3);
                                    ComponentName componentName4 = componentName;
                                    ConcurrentLinkedQueue concurrentLinkedQueue = mediaResumeListener2.resumeComponents;
                                    Iterator it = concurrentLinkedQueue.iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            obj2 = null;
                                            break;
                                        } else {
                                            obj2 = it.next();
                                            if (((ComponentName) ((Pair) obj2).getFirst()).equals(componentName4)) {
                                                break;
                                            }
                                        }
                                    }
                                    concurrentLinkedQueue.remove(obj2);
                                    mediaResumeListener2.resumeComponents.add(new Pair(componentName4, Long.valueOf(mediaResumeListener2.systemClock.currentTimeMillis())));
                                    if (mediaResumeListener2.resumeComponents.size() > 5) {
                                        mediaResumeListener2.resumeComponents.remove();
                                    }
                                    mediaResumeListener2.writeSharedPrefs();
                                    mediaResumeListener2.setMediaBrowser(null);
                                }

                                @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
                                public final void onConnected() {
                                    ComponentName componentName2 = componentName;
                                    if (Log.isLoggable("MediaResumeListener", 3)) {
                                        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Connected to ", componentName2, "MediaResumeListener");
                                    }
                                }

                                @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
                                public final void onError() {
                                    Log.e("MediaResumeListener", "Cannot resume with " + componentName);
                                    mediaResumeListener.setMediaBrowser(null);
                                }
                            };
                            int i = mediaResumeListener.currentUserId;
                            ResumeMediaBrowserFactory resumeMediaBrowserFactory = mediaResumeListener.mediaBrowserFactory;
                            mediaResumeListener.setMediaBrowser(new ResumeMediaBrowser(resumeMediaBrowserFactory.mContext, callback, componentName, resumeMediaBrowserFactory.mBrowserFactory, resumeMediaBrowserFactory.mLogger, i));
                            ResumeMediaBrowser resumeMediaBrowser = mediaResumeListener.mediaBrowser;
                            if (resumeMediaBrowser != null) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("android.service.media.extra.RECENT", true);
                                ComponentName componentName2 = resumeMediaBrowser.mComponentName;
                                ResumeMediaBrowser.AnonymousClass2 anonymousClass2 = resumeMediaBrowser.mConnectionCallback;
                                MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser.mBrowserFactory;
                                mediaBrowserFactory.getClass();
                                resumeMediaBrowser.connectBrowser(new MediaBrowser(mediaBrowserFactory.mContext, componentName2, anonymousClass2, bundle), "testConnection");
                            }
                        }
                    }
                });
            }
        }
    }

    public final void setMediaBrowser(ResumeMediaBrowser resumeMediaBrowser) {
        ResumeMediaBrowser resumeMediaBrowser2 = this.mediaBrowser;
        if (resumeMediaBrowser2 != null) {
            resumeMediaBrowser2.disconnect();
        }
        this.mediaBrowser = resumeMediaBrowser;
    }

    public final void writeSharedPrefs() {
        StringBuilder sb = new StringBuilder();
        for (Pair pair : this.resumeComponents) {
            sb.append(((ComponentName) pair.getFirst()).flattenToString());
            sb.append("/");
            sb.append(((Number) pair.getSecond()).longValue());
            sb.append(":");
        }
        this.context.getSharedPreferences("media_control_prefs", 0).edit().putString(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(this.currentUserId, "browser_components_"), sb.toString()).apply();
    }

    public static /* synthetic */ void getUserUnlockReceiver$annotations() {
    }
}
