package com.android.systemui.media.controls.resume;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.models.player.MediaAction;
import com.android.systemui.media.controls.models.player.MediaButton;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaDataManagerKt;
import com.android.systemui.media.controls.pipeline.MediaTimeoutListenerKt;
import com.android.systemui.media.controls.resume.ResumeMediaBrowser;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaResumeListener implements MediaDataManager.Listener, Dumpable {
    public final Executor backgroundExecutor;
    public final Context context;
    public int currentUserId;
    public final Executor mainExecutor;
    public ResumeMediaBrowser mediaBrowser;
    public final MediaResumeListener$mediaBrowserCallback$1 mediaBrowserCallback;
    public final ResumeMediaBrowserFactory mediaBrowserFactory;
    public MediaDataManager mediaDataManager;
    public final MediaFlags mediaFlags;
    public final ConcurrentLinkedQueue resumeComponents = new ConcurrentLinkedQueue();
    public final SystemClock systemClock;
    public final TunerService tunerService;
    public boolean useMediaResumption;
    public final MediaResumeListener$userTrackerCallback$1 userTrackerCallback;
    public final MediaResumeListener$userUnlockReceiver$1 userUnlockReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.media.controls.resume.MediaResumeListener$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.media.controls.resume.MediaResumeListener$mediaBrowserCallback$1] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.android.systemui.media.controls.resume.MediaResumeListener$userUnlockReceiver$1] */
    public MediaResumeListener(Context context, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, Executor executor, Executor executor2, TunerService tunerService, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock, MediaFlags mediaFlags) {
        this.context = context;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.tunerService = tunerService;
        this.mediaBrowserFactory = resumeMediaBrowserFactory;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.useMediaResumption = Utils.useMediaResumption(context);
        this.currentUserId = context.getUserId();
        ?? r4 = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$userUnlockReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Intrinsics.areEqual("android.intent.action.USER_UNLOCKED", intent.getAction())) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                    if (intExtra == mediaResumeListener.currentUserId && mediaResumeListener.useMediaResumption) {
                        PackageManager packageManager = mediaResumeListener.context.getPackageManager();
                        ((SystemClockImpl) mediaResumeListener.systemClock).getClass();
                        long currentTimeMillis = System.currentTimeMillis();
                        Iterator it = mediaResumeListener.resumeComponents.iterator();
                        while (it.hasNext()) {
                            Pair pair = (Pair) it.next();
                            if (currentTimeMillis - ((Number) pair.getSecond()).longValue() <= MediaTimeoutListenerKt.RESUME_MEDIA_TIMEOUT) {
                                Intent intent2 = new Intent("android.media.browse.MediaBrowserService");
                                intent2.setComponent((ComponentName) pair.getFirst());
                                if (packageManager.resolveServiceAsUser(intent2, 0, mediaResumeListener.currentUserId) != null) {
                                    ComponentName componentName = (ComponentName) pair.getFirst();
                                    int i = mediaResumeListener.currentUserId;
                                    MediaResumeListener$mediaBrowserCallback$1 mediaResumeListener$mediaBrowserCallback$1 = mediaResumeListener.mediaBrowserCallback;
                                    ResumeMediaBrowserFactory resumeMediaBrowserFactory2 = mediaResumeListener.mediaBrowserFactory;
                                    ResumeMediaBrowser resumeMediaBrowser = new ResumeMediaBrowser(resumeMediaBrowserFactory2.mContext, mediaResumeListener$mediaBrowserCallback$1, componentName, resumeMediaBrowserFactory2.mBrowserFactory, resumeMediaBrowserFactory2.mLogger, i);
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean("android.service.media.extra.RECENT", true);
                                    MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser.mBrowserFactory;
                                    mediaBrowserFactory.getClass();
                                    resumeMediaBrowser.connectBrowser(new MediaBrowser(mediaBrowserFactory.mContext, resumeMediaBrowser.mComponentName, resumeMediaBrowser.mConnectionCallback, bundle), "findRecentMedia");
                                } else {
                                    Log.d("MediaResumeListener", "User " + mediaResumeListener.currentUserId + " does not have component " + pair.getFirst());
                                }
                            }
                        }
                    }
                }
            }
        };
        this.userUnlockReceiver = r4;
        ?? r2 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                mediaResumeListener.currentUserId = i;
                mediaResumeListener.loadSavedComponents();
            }
        };
        this.userTrackerCallback = r2;
        this.mediaBrowserCallback = new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$mediaBrowserCallback$1
            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
            public final void addTrack(final MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
                int i;
                final MediaSession.Token sessionToken = !resumeMediaBrowser.isBrowserConnected() ? null : resumeMediaBrowser.mMediaBrowser.getSessionToken();
                Context context2 = resumeMediaBrowser.mContext;
                final PendingIntent activity = PendingIntent.getActivity(context2, 0, context2.getPackageManager().getLaunchIntentForPackage(resumeMediaBrowser.mComponentName.getPackageName()), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
                MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                PackageManager packageManager = mediaResumeListener.context.getPackageManager();
                CharSequence packageName = componentName.getPackageName();
                final MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1 = new MediaResumeListener$getResumeAction$1(mediaResumeListener, componentName);
                try {
                    packageName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(componentName.getPackageName(), 0));
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("MediaResumeListener", "Error getting package information", e);
                }
                Log.d("MediaResumeListener", "Adding resume controls for " + resumeMediaBrowser.mUserId + ": " + mediaDescription);
                MediaDataManager mediaDataManager = mediaResumeListener.mediaDataManager;
                MediaDataManager mediaDataManager2 = mediaDataManager == null ? null : mediaDataManager;
                final int i2 = resumeMediaBrowser.mUserId;
                final String obj = packageName.toString();
                final String packageName2 = componentName.getPackageName();
                LinkedHashMap linkedHashMap = mediaDataManager2.mediaEntries;
                if (!linkedHashMap.containsKey(packageName2)) {
                    MediaUiEventLogger mediaUiEventLogger = mediaDataManager2.logger;
                    InstanceId newInstanceId = mediaUiEventLogger.getNewInstanceId();
                    try {
                        ApplicationInfo applicationInfo = mediaDataManager2.context.getPackageManager().getApplicationInfo(packageName2, 0);
                        Integer valueOf = applicationInfo != null ? Integer.valueOf(applicationInfo.uid) : null;
                        Intrinsics.checkNotNull(valueOf);
                        i = valueOf.intValue();
                    } catch (PackageManager.NameNotFoundException e2) {
                        Log.w("MediaDataManager", "Could not get app UID for ".concat(packageName2), e2);
                        i = -1;
                    }
                    linkedHashMap.put(packageName2, MediaData.copy$default(MediaDataManagerKt.LOADING, null, null, null, packageName2, null, null, false, mediaResumeListener$getResumeAction$1, false, true, null, false, newInstanceId, i, 242711551));
                    mediaDataManager2.logSingleVsMultipleMediaAdded(i, packageName2, newInstanceId);
                    mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.RESUME_MEDIA_ADDED, i, packageName2, newInstanceId);
                }
                final MediaDataManager mediaDataManager3 = mediaDataManager2;
                mediaDataManager2.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$addResumptionControls$1
                    /* JADX WARN: Removed duplicated region for block: B:44:0x00eb  */
                    /* JADX WARN: Removed duplicated region for block: B:62:0x011e  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        InstanceId newInstanceId2;
                        boolean z;
                        Double d;
                        Bundle extras;
                        int i3;
                        Double valueOf2;
                        final MediaDataManager mediaDataManager4 = MediaDataManager.this;
                        final int i4 = i2;
                        final MediaDescription mediaDescription2 = mediaDescription;
                        final Runnable runnable = mediaResumeListener$getResumeAction$1;
                        final MediaSession.Token token = sessionToken;
                        final String str = obj;
                        final PendingIntent pendingIntent = activity;
                        final String str2 = packageName2;
                        String str3 = MediaDataManager.SMARTSPACE_UI_SURFACE_LABEL;
                        mediaDataManager4.getClass();
                        CharSequence title = mediaDescription2.getTitle();
                        boolean z2 = title == null || StringsKt__StringsJVMKt.isBlank(title);
                        LinkedHashMap linkedHashMap2 = mediaDataManager4.mediaEntries;
                        if (z2) {
                            Log.e("MediaDataManager", "Description incomplete");
                            linkedHashMap2.remove(str2);
                            return;
                        }
                        Log.d("MediaDataManager", "adding track for " + i4 + " from browser: " + mediaDescription2);
                        Bitmap iconBitmap = mediaDescription2.getIconBitmap();
                        if (iconBitmap == null && mediaDescription2.getIconUri() != null) {
                            Uri iconUri = mediaDescription2.getIconUri();
                            Intrinsics.checkNotNull(iconUri);
                            iconBitmap = mediaDataManager4.loadBitmapFromUri(iconUri);
                        }
                        Icon createWithBitmap = iconBitmap != null ? Icon.createWithBitmap(iconBitmap) : null;
                        MediaData mediaData = (MediaData) linkedHashMap2.get(str2);
                        if (mediaData == null || (newInstanceId2 = mediaData.instanceId) == null) {
                            newInstanceId2 = mediaDataManager4.logger.getNewInstanceId();
                        }
                        final InstanceId instanceId = newInstanceId2;
                        final int i5 = mediaData != null ? mediaData.appUid : -1;
                        Bundle extras2 = mediaDescription2.getExtras();
                        boolean z3 = extras2 != null && extras2.getLong("android.media.IS_EXPLICIT") == 1;
                        MediaFlags mediaFlags2 = mediaDataManager4.mediaFlags;
                        if (z3) {
                            mediaFlags2.getClass();
                            Flags.INSTANCE.getClass();
                            if (((FeatureFlagsRelease) mediaFlags2.featureFlags).isEnabled(Flags.MEDIA_EXPLICIT_INDICATOR)) {
                                z = true;
                                mediaFlags2.getClass();
                                Flags.INSTANCE.getClass();
                                if (((FeatureFlagsRelease) mediaFlags2.featureFlags).isEnabled(Flags.MEDIA_RESUME_PROGRESS) && (extras = mediaDescription2.getExtras()) != null && extras.containsKey("android.media.extra.PLAYBACK_STATUS")) {
                                    i3 = extras.getInt("android.media.extra.PLAYBACK_STATUS");
                                    if (i3 != 0) {
                                        valueOf2 = Double.valueOf(0.0d);
                                    } else if (i3 != 1) {
                                        if (i3 == 2) {
                                            valueOf2 = Double.valueOf(1.0d);
                                        }
                                    } else if (extras.containsKey("androidx.media.MediaItem.Extras.COMPLETION_PERCENTAGE")) {
                                        double d2 = extras.getDouble("androidx.media.MediaItem.Extras.COMPLETION_PERCENTAGE");
                                        valueOf2 = Double.valueOf(d2 >= 0.0d ? d2 > 1.0d ? 1.0d : d2 : 0.0d);
                                    } else {
                                        valueOf2 = Double.valueOf(0.5d);
                                    }
                                    d = valueOf2;
                                    final MediaAction resumeMediaAction = mediaDataManager4.getResumeMediaAction(runnable);
                                    ((SystemClockImpl) mediaDataManager4.systemClock).getClass();
                                    final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                                    final Icon icon = createWithBitmap;
                                    final boolean z4 = z;
                                    final Double d3 = d;
                                    ((ExecutorImpl) mediaDataManager4.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBgForResumption$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            MediaDataManager mediaDataManager5 = MediaDataManager.this;
                                            String str4 = str2;
                                            int i6 = i4;
                                            String str5 = str;
                                            CharSequence subtitle = mediaDescription2.getSubtitle();
                                            CharSequence title2 = mediaDescription2.getTitle();
                                            Icon icon2 = icon;
                                            List singletonList = Collections.singletonList(resumeMediaAction);
                                            List singletonList2 = Collections.singletonList(0);
                                            MediaButton mediaButton = new MediaButton(resumeMediaAction, null, null, null, null, false, false, 126, null);
                                            String str6 = str2;
                                            mediaDataManager5.onMediaDataLoaded(str4, null, new MediaData(i6, true, str5, null, subtitle, title2, icon2, singletonList, singletonList2, mediaButton, str6, token, pendingIntent, null, false, runnable, 0, true, str6, true, null, false, elapsedRealtime, instanceId, i5, z4, d3, null, 137428992, null));
                                        }
                                    });
                                }
                                d = null;
                                final MediaAction resumeMediaAction2 = mediaDataManager4.getResumeMediaAction(runnable);
                                ((SystemClockImpl) mediaDataManager4.systemClock).getClass();
                                final long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                                final Icon icon2 = createWithBitmap;
                                final boolean z42 = z;
                                final Double d32 = d;
                                ((ExecutorImpl) mediaDataManager4.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBgForResumption$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MediaDataManager mediaDataManager5 = MediaDataManager.this;
                                        String str4 = str2;
                                        int i6 = i4;
                                        String str5 = str;
                                        CharSequence subtitle = mediaDescription2.getSubtitle();
                                        CharSequence title2 = mediaDescription2.getTitle();
                                        Icon icon22 = icon2;
                                        List singletonList = Collections.singletonList(resumeMediaAction2);
                                        List singletonList2 = Collections.singletonList(0);
                                        MediaButton mediaButton = new MediaButton(resumeMediaAction2, null, null, null, null, false, false, 126, null);
                                        String str6 = str2;
                                        mediaDataManager5.onMediaDataLoaded(str4, null, new MediaData(i6, true, str5, null, subtitle, title2, icon22, singletonList, singletonList2, mediaButton, str6, token, pendingIntent, null, false, runnable, 0, true, str6, true, null, false, elapsedRealtime2, instanceId, i5, z42, d32, null, 137428992, null));
                                    }
                                });
                            }
                        }
                        z = false;
                        mediaFlags2.getClass();
                        Flags.INSTANCE.getClass();
                        if (((FeatureFlagsRelease) mediaFlags2.featureFlags).isEnabled(Flags.MEDIA_RESUME_PROGRESS)) {
                            i3 = extras.getInt("android.media.extra.PLAYBACK_STATUS");
                            if (i3 != 0) {
                            }
                            d = valueOf2;
                            final MediaAction resumeMediaAction22 = mediaDataManager4.getResumeMediaAction(runnable);
                            ((SystemClockImpl) mediaDataManager4.systemClock).getClass();
                            final long elapsedRealtime22 = android.os.SystemClock.elapsedRealtime();
                            final Icon icon22 = createWithBitmap;
                            final boolean z422 = z;
                            final Double d322 = d;
                            ((ExecutorImpl) mediaDataManager4.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBgForResumption$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MediaDataManager mediaDataManager5 = MediaDataManager.this;
                                    String str4 = str2;
                                    int i6 = i4;
                                    String str5 = str;
                                    CharSequence subtitle = mediaDescription2.getSubtitle();
                                    CharSequence title2 = mediaDescription2.getTitle();
                                    Icon icon222 = icon22;
                                    List singletonList = Collections.singletonList(resumeMediaAction22);
                                    List singletonList2 = Collections.singletonList(0);
                                    MediaButton mediaButton = new MediaButton(resumeMediaAction22, null, null, null, null, false, false, 126, null);
                                    String str6 = str2;
                                    mediaDataManager5.onMediaDataLoaded(str4, null, new MediaData(i6, true, str5, null, subtitle, title2, icon222, singletonList, singletonList2, mediaButton, str6, token, pendingIntent, null, false, runnable, 0, true, str6, true, null, false, elapsedRealtime22, instanceId, i5, z422, d322, null, 137428992, null));
                                }
                            });
                        }
                        d = null;
                        final MediaAction resumeMediaAction222 = mediaDataManager4.getResumeMediaAction(runnable);
                        ((SystemClockImpl) mediaDataManager4.systemClock).getClass();
                        final long elapsedRealtime222 = android.os.SystemClock.elapsedRealtime();
                        final Icon icon222 = createWithBitmap;
                        final boolean z4222 = z;
                        final Double d3222 = d;
                        ((ExecutorImpl) mediaDataManager4.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBgForResumption$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaDataManager mediaDataManager5 = MediaDataManager.this;
                                String str4 = str2;
                                int i6 = i4;
                                String str5 = str;
                                CharSequence subtitle = mediaDescription2.getSubtitle();
                                CharSequence title2 = mediaDescription2.getTitle();
                                Icon icon2222 = icon222;
                                List singletonList = Collections.singletonList(resumeMediaAction222);
                                List singletonList2 = Collections.singletonList(0);
                                MediaButton mediaButton = new MediaButton(resumeMediaAction222, null, null, null, null, false, false, 126, null);
                                String str6 = str2;
                                mediaDataManager5.onMediaDataLoaded(str4, null, new MediaData(i6, true, str5, null, subtitle, title2, icon2222, singletonList, singletonList2, mediaButton, str6, token, pendingIntent, null, false, runnable, 0, true, str6, true, null, false, elapsedRealtime222, instanceId, i5, z4222, d3222, null, 137428992, null));
                            }
                        });
                    }
                });
            }
        };
        if (this.useMediaResumption) {
            DumpManager.registerDumpable$default(dumpManager, "MediaResumeListener", this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r4, intentFilter, null, UserHandle.ALL, 0, null, 48);
            ((UserTrackerImpl) userTracker).addCallback(r2, executor);
            loadSavedComponents();
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("resumeComponents: " + this.resumeComponents);
    }

    public final void loadSavedComponents() {
        long currentTimeMillis;
        long j;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.resumeComponents;
        concurrentLinkedQueue.clear();
        boolean z = false;
        Iterable iterable = null;
        String string = this.context.getSharedPreferences("media_control_prefs", 0).getString("browser_components_" + this.currentUserId, null);
        if (string != null) {
            List split = new Regex(":").split(string);
            if (!split.isEmpty()) {
                ListIterator listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (!(((String) listIterator.previous()).length() == 0)) {
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
                        j = Long.parseLong((String) split$default.get(2));
                    } catch (NumberFormatException unused) {
                        ((SystemClockImpl) systemClock).getClass();
                        currentTimeMillis = System.currentTimeMillis();
                    }
                    concurrentLinkedQueue.add(new Pair(componentName, Long.valueOf(j)));
                } else {
                    ((SystemClockImpl) systemClock).getClass();
                    currentTimeMillis = System.currentTimeMillis();
                }
                j = currentTimeMillis;
                z2 = true;
                concurrentLinkedQueue.add(new Pair(componentName, Long.valueOf(j)));
            }
            z = z2;
        }
        Log.d("MediaResumeListener", "loaded resume components for " + this.currentUserId + ": " + Arrays.toString(concurrentLinkedQueue.toArray()));
        if (z) {
            writeSharedPrefs();
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(final String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        boolean z3;
        if (this.useMediaResumption) {
            final ArrayList arrayList = null;
            if (!str.equals(str2)) {
                setMediaBrowser(null);
            }
            if (mediaData.playbackLocation == 0) {
                z3 = true;
            } else {
                MediaFlags mediaFlags = this.mediaFlags;
                mediaFlags.getClass();
                Flags.INSTANCE.getClass();
                mediaFlags.featureFlags.getClass();
                z3 = false;
            }
            if (mediaData.resumeAction == null && !mediaData.hasCheckedForResume && z3) {
                MediaDataManager mediaDataManager = this.mediaDataManager;
                if (mediaDataManager == null) {
                    mediaDataManager = null;
                }
                MediaData mediaData2 = (MediaData) mediaDataManager.mediaEntries.get(str);
                if (mediaData2 != null) {
                    mediaData2.resumeAction = null;
                    mediaData2.hasCheckedForResume = true;
                }
                StringBuilder sb = new StringBuilder("Checking for service component for ");
                String str3 = mediaData.packageName;
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str3, "MediaResumeListener");
                List queryIntentServicesAsUser = this.context.getPackageManager().queryIntentServicesAsUser(new Intent("android.media.browse.MediaBrowserService"), 0, this.currentUserId);
                if (queryIntentServicesAsUser != null) {
                    arrayList = new ArrayList();
                    for (Object obj : queryIntentServicesAsUser) {
                        if (Intrinsics.areEqual(((ResolveInfo) obj).serviceInfo.packageName, str3)) {
                            arrayList.add(obj);
                        }
                    }
                }
                if (arrayList == null || arrayList.size() <= 0) {
                    return;
                }
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$onMediaDataLoaded$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                        final String str4 = str;
                        List list = arrayList;
                        Intrinsics.checkNotNull(list);
                        final ComponentName componentName = ((ResolveInfo) list.get(0)).getComponentInfo().getComponentName();
                        mediaResumeListener.getClass();
                        Log.d("MediaResumeListener", "Testing if we can connect to " + componentName);
                        ResumeMediaBrowser.Callback callback = new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$tryUpdateResumptionList$1
                            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
                            public final void addTrack(MediaDescription mediaDescription, ComponentName componentName2, ResumeMediaBrowser resumeMediaBrowser) {
                                Object obj2;
                                StringBuilder sb2 = new StringBuilder("Can get resumable media for ");
                                sb2.append(resumeMediaBrowser.mUserId);
                                sb2.append(" from ");
                                ComponentName componentName3 = componentName;
                                sb2.append(componentName3);
                                Log.d("MediaResumeListener", sb2.toString());
                                MediaResumeListener mediaResumeListener2 = mediaResumeListener;
                                MediaDataManager mediaDataManager2 = mediaResumeListener2.mediaDataManager;
                                if (mediaDataManager2 == null) {
                                    mediaDataManager2 = null;
                                }
                                MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1 = new MediaResumeListener$getResumeAction$1(mediaResumeListener2, componentName3);
                                MediaData mediaData3 = (MediaData) mediaDataManager2.mediaEntries.get(str4);
                                if (mediaData3 != null) {
                                    mediaData3.resumeAction = mediaResumeListener$getResumeAction$1;
                                    mediaData3.hasCheckedForResume = true;
                                }
                                ConcurrentLinkedQueue concurrentLinkedQueue = mediaResumeListener2.resumeComponents;
                                Iterator it = concurrentLinkedQueue.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        obj2 = null;
                                        break;
                                    } else {
                                        obj2 = it.next();
                                        if (((ComponentName) ((Pair) obj2).getFirst()).equals(componentName3)) {
                                            break;
                                        }
                                    }
                                }
                                concurrentLinkedQueue.remove(obj2);
                                ((SystemClockImpl) mediaResumeListener2.systemClock).getClass();
                                concurrentLinkedQueue.add(new Pair(componentName3, Long.valueOf(System.currentTimeMillis())));
                                if (concurrentLinkedQueue.size() > 5) {
                                    concurrentLinkedQueue.remove();
                                }
                                mediaResumeListener2.writeSharedPrefs();
                                mediaResumeListener2.setMediaBrowser(null);
                            }

                            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
                            public final void onConnected() {
                                Log.d("MediaResumeListener", "Connected to " + componentName);
                            }

                            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
                            public final void onError() {
                                Log.e("MediaResumeListener", "Cannot resume with " + componentName);
                                mediaResumeListener.setMediaBrowser(null);
                            }
                        };
                        int i2 = mediaResumeListener.currentUserId;
                        ResumeMediaBrowserFactory resumeMediaBrowserFactory = mediaResumeListener.mediaBrowserFactory;
                        mediaResumeListener.setMediaBrowser(new ResumeMediaBrowser(resumeMediaBrowserFactory.mContext, callback, componentName, resumeMediaBrowserFactory.mBrowserFactory, resumeMediaBrowserFactory.mLogger, i2));
                        ResumeMediaBrowser resumeMediaBrowser = mediaResumeListener.mediaBrowser;
                        if (resumeMediaBrowser != null) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("android.service.media.extra.RECENT", true);
                            MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser.mBrowserFactory;
                            mediaBrowserFactory.getClass();
                            resumeMediaBrowser.connectBrowser(new MediaBrowser(mediaBrowserFactory.mContext, resumeMediaBrowser.mComponentName, resumeMediaBrowser.mConnectionCallback, bundle), "testConnection");
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
        this.context.getSharedPreferences("media_control_prefs", 0).edit().putString(AbstractC0000x2c234b15.m0m("browser_components_", this.currentUserId), sb.toString()).apply();
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str) {
    }

    public static /* synthetic */ void getUserUnlockReceiver$annotations() {
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
    }
}
