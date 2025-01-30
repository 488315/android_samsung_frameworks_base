package com.android.systemui.media.controls.pipeline;

import android.R;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.models.player.MediaAction;
import com.android.systemui.media.controls.models.player.MediaButton;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.models.player.MediaViewHolder;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaDataProvider;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.resume.MediaResumeListener;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaDataManager implements Dumpable, BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public static final String EXTRAS_MEDIA_SOURCE_PACKAGE_NAME;
    public static final int MAX_COMPACT_ACTIONS;
    public static final int MAX_NOTIFICATION_ACTIONS;
    public static final String SMARTSPACE_UI_SURFACE_LABEL;
    public final ActivityStarter activityStarter;
    public boolean allowMediaRecommendations;
    public final int artworkHeight;
    public final int artworkWidth;
    public final Executor backgroundExecutor;
    public final Context context;
    public final DelayableExecutor foregroundExecutor;
    public final Set internalListeners;
    public final MediaUiEventLogger logger;
    public final MediaControllerFactory mediaControllerFactory;
    public final MediaDataFilter mediaDataFilter;
    public final LinkedHashMap mediaEntries;
    public final MediaFlags mediaFlags;
    public SmartspaceMediaData smartspaceMediaData;
    public final SmartspaceMediaDataProvider smartspaceMediaDataProvider;
    private SmartspaceSession smartspaceSession;
    public final StatusBarManager statusBarManager;
    public final SystemClock systemClock;
    public final int themeText;
    public final Executor uiExecutor;
    public boolean useMediaResumption;
    public final boolean useQsMediaPlayer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public abstract class DefaultImpls {
            public static /* synthetic */ void onMediaDataLoaded$default(Listener listener, String str, String str2, MediaData mediaData, boolean z, int i, boolean z2, int i2) {
                if ((i2 & 8) != 0) {
                    z = true;
                }
                listener.onMediaDataLoaded(str, str2, mediaData, z, (i2 & 16) != 0 ? 0 : i, (i2 & 32) != 0 ? false : z2);
            }
        }

        void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2);

        void onMediaDataRemoved(String str);

        void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData);

        void onSmartspaceMediaDataRemoved(String str, boolean z);
    }

    static {
        new Companion(null);
        SMARTSPACE_UI_SURFACE_LABEL = BcSmartspaceDataPlugin.UI_SURFACE_MEDIA;
        EXTRAS_MEDIA_SOURCE_PACKAGE_NAME = "package_name";
        MAX_COMPACT_ACTIONS = 3;
        MediaViewHolder.Companion.getClass();
        MAX_NOTIFICATION_ACTIONS = MediaViewHolder.genericButtonIds.size();
    }

    public MediaDataManager(Context context, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, MediaTimeoutListener mediaTimeoutListener, final MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilter mediaDataFilter, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, boolean z, boolean z2, SystemClock systemClock, TunerService tunerService, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.context = context;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.foregroundExecutor = delayableExecutor;
        this.mediaControllerFactory = mediaControllerFactory;
        this.mediaDataFilter = mediaDataFilter;
        this.activityStarter = activityStarter;
        this.smartspaceMediaDataProvider = smartspaceMediaDataProvider;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.themeText = Utils.getColorAttr(R.attr.textColorPrimary, context).getDefaultColor();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.internalListeners = linkedHashSet;
        this.mediaEntries = new LinkedHashMap();
        this.smartspaceMediaData = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
        this.allowMediaRecommendations = com.android.systemui.util.Utils.useQsMediaPlayer(context) && Settings.Secure.getInt(context.getContentResolver(), "qs_media_recommend", 1) > 0;
        this.artworkWidth = context.getResources().getDimensionPixelSize(R.dimen.config_hapticChannelMaxVibrationAmplitude);
        this.artworkHeight = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_media_session_height_expanded);
        this.statusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$appChangeReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String[] stringArrayExtra;
                String encodedSchemeSpecificPart;
                String action = intent.getAction();
                if (action != null) {
                    int hashCode = action.hashCode();
                    if (hashCode == -1001645458) {
                        if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                            MediaDataManager mediaDataManager = MediaDataManager.this;
                            for (String str : stringArrayExtra) {
                                MediaDataManager.access$removeAllForPackage(mediaDataManager, str);
                            }
                            return;
                        }
                        return;
                    }
                    if (hashCode != -757780528) {
                        if (hashCode != 525384130 || !action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            return;
                        }
                    } else if (!action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        return;
                    }
                    Uri data = intent.getData();
                    if (data == null || (encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart()) == null) {
                        return;
                    }
                    MediaDataManager.access$removeAllForPackage(MediaDataManager.this, encodedSchemeSpecificPart);
                }
            }
        };
        DumpManager.registerDumpable$default(dumpManager, "MediaDataManager", this);
        linkedHashSet.add(mediaTimeoutListener);
        linkedHashSet.add(mediaSessionBasedFilter);
        mediaSessionBasedFilter.listeners.add(mediaDeviceManager);
        mediaSessionBasedFilter.listeners.add(mediaDataCombineLatest);
        mediaDeviceManager.listeners.add(mediaDataCombineLatest);
        mediaDataCombineLatest.listeners.add(mediaDataFilter);
        mediaTimeoutListener.timeoutCallback = new Function2() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                String str = (String) obj;
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                MediaDataManager mediaDataManager = MediaDataManager.this;
                String str2 = MediaDataManager.SMARTSPACE_UI_SURFACE_LABEL;
                MediaData mediaData = (MediaData) mediaDataManager.mediaEntries.get(str);
                if (mediaData != null) {
                    if (booleanValue) {
                        mediaDataManager.logger.logger.logWithInstanceId(MediaUiEvent.MEDIA_TIMEOUT, mediaData.appUid, mediaData.packageName, mediaData.instanceId);
                    }
                    boolean z3 = !booleanValue;
                    if (mediaData.active == z3) {
                        if (mediaData.resumption) {
                            Log.d("MediaDataManager", "timing out resume player ".concat(str));
                            mediaDataManager.dismissMediaData(str, false);
                        }
                        return Unit.INSTANCE;
                    }
                    mediaData.active = z3;
                    Log.d("MediaDataManager", "Updating " + str + " timedOut: " + booleanValue);
                    mediaDataManager.onMediaDataLoaded(str, str, mediaData);
                }
                if (Intrinsics.areEqual(str, mediaDataManager.smartspaceMediaData.targetId)) {
                    Log.d("MediaDataManager", "smartspace card expired");
                    mediaDataManager.dismissSmartspaceRecommendation(str);
                }
                return Unit.INSTANCE;
            }
        };
        mediaTimeoutListener.stateCallback = new Function2() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager.2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MediaData copy$default;
                String str = (String) obj;
                PlaybackState playbackState = (PlaybackState) obj2;
                MediaDataManager mediaDataManager = MediaDataManager.this;
                MediaData mediaData = (MediaData) mediaDataManager.mediaEntries.get(str);
                if (mediaData != null) {
                    MediaSession.Token token = mediaData.token;
                    if (token == null) {
                        Log.d("MediaDataManager", "State updated, but token was null");
                    } else {
                        MediaControllerFactory mediaControllerFactory2 = mediaDataManager.mediaControllerFactory;
                        mediaControllerFactory2.getClass();
                        MediaButton createActionsFromState = mediaDataManager.createActionsFromState(mediaData.packageName, new MediaController(mediaControllerFactory2.mContext, token), new UserHandle(mediaData.userId));
                        if (createActionsFromState != null) {
                            EmptyList emptyList = EmptyList.INSTANCE;
                            Pair generateActionsFromSemantic = MediaDataManager.generateActionsFromSemantic(createActionsFromState);
                            copy$default = MediaData.copy$default(mediaData, (List) generateActionsFromSemantic.getFirst(), (List) generateActionsFromSemantic.getSecond(), createActionsFromState, null, null, null, false, null, false, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, null, 0, 267385983);
                        } else {
                            copy$default = MediaData.copy$default(mediaData, null, null, null, null, null, null, false, null, false, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, null, 0, 267386879);
                        }
                        Log.d("MediaDataManager", "State updated outside of notification");
                        mediaDataManager.onMediaDataLoaded(str, str, copy$default);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        mediaTimeoutListener.sessionCallback = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                MediaFlags mediaFlags2 = MediaDataManager.this.mediaFlags;
                mediaFlags2.getClass();
                Flags.INSTANCE.getClass();
                mediaFlags2.featureFlags.getClass();
                return Unit.INSTANCE;
            }
        };
        mediaResumeListener.mediaDataManager = this;
        mediaResumeListener.tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$setManager$1
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                MediaResumeListener mediaResumeListener2 = MediaResumeListener.this;
                mediaResumeListener2.useMediaResumption = com.android.systemui.util.Utils.useMediaResumption(mediaResumeListener2.context);
                MediaDataManager mediaDataManager = mediaResumeListener2.mediaDataManager;
                if (mediaDataManager == null) {
                    mediaDataManager = null;
                }
                boolean z3 = mediaResumeListener2.useMediaResumption;
                if (mediaDataManager.useMediaResumption == z3) {
                    return;
                }
                mediaDataManager.useMediaResumption = z3;
                if (z3) {
                    return;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                LinkedHashMap linkedHashMap2 = mediaDataManager.mediaEntries;
                for (Map.Entry entry : linkedHashMap2.entrySet()) {
                    if (!((MediaData) entry.getValue()).active) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                    linkedHashMap2.remove(entry2.getKey());
                    mediaDataManager.notifyMediaDataRemoved((String) entry2.getKey());
                    mediaDataManager.logger.logMediaRemoved(((MediaData) entry2.getValue()).appUid, ((MediaData) entry2.getValue()).packageName, ((MediaData) entry2.getValue()).instanceId);
                }
            }
        }, "qs_media_resumption");
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, new IntentFilter("android.intent.action.PACKAGES_SUSPENDED"), null, UserHandle.ALL, 0, null, 48);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(broadcastReceiver, intentFilter);
        smartspaceMediaDataProvider.registerListener(this);
        SmartspaceSession createSmartspaceSession = smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(context, SMARTSPACE_UI_SURFACE_LABEL).build());
        this.smartspaceSession = createSmartspaceSession;
        if (createSmartspaceSession != null) {
            createSmartspaceSession.addOnTargetsAvailableListener(executor2, new SmartspaceSession.OnTargetsAvailableListener() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$4$1
                public final void onTargetsAvailable(List list) {
                    MediaDataManager.this.smartspaceMediaDataProvider.onTargetsAvailable(list);
                }
            });
        }
        SmartspaceSession smartspaceSession = this.smartspaceSession;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
        tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager.6
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                MediaDataManager mediaDataManager = MediaDataManager.this;
                Context context2 = mediaDataManager.context;
                String[] strArr = MediaDataManagerKt.ART_URIS;
                boolean z3 = com.android.systemui.util.Utils.useQsMediaPlayer(context2) && Settings.Secure.getInt(context2.getContentResolver(), "qs_media_recommend", 1) > 0;
                mediaDataManager.allowMediaRecommendations = z3;
                if (z3) {
                    return;
                }
                mediaDataManager.dismissSmartspaceRecommendation(mediaDataManager.smartspaceMediaData.targetId);
            }
        }, "qs_media_recommend");
    }

    public static final void access$removeAllForPackage(MediaDataManager mediaDataManager, String str) {
        mediaDataManager.getClass();
        Assert.isMainThread();
        LinkedHashMap linkedHashMap = mediaDataManager.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).packageName, str)) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap2.entrySet().iterator();
        while (it.hasNext()) {
            removeEntry$default(mediaDataManager, (String) ((Map.Entry) it.next()).getKey());
        }
    }

    public static final boolean access$sendPendingIntent(MediaDataManager mediaDataManager, PendingIntent pendingIntent) {
        mediaDataManager.getClass();
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setInteractive(true);
            pendingIntent.send(makeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException e) {
            Log.d("MediaDataManager", "Intent canceled", e);
            return false;
        }
    }

    public static final MediaAction createActionsFromState$nextCustomAction(TransformingSequence$iterator$1 transformingSequence$iterator$1) {
        if (transformingSequence$iterator$1.hasNext()) {
            return (MediaAction) transformingSequence$iterator$1.next();
        }
        return null;
    }

    public static Pair generateActionsFromSemantic(MediaButton mediaButton) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        MediaAction mediaAction = mediaButton.custom0;
        if (mediaAction != null) {
            arrayList.add(mediaAction);
        }
        MediaAction mediaAction2 = mediaButton.prevOrCustom;
        if (mediaAction2 != null) {
            arrayList.add(mediaAction2);
            arrayList2.add(Integer.valueOf(arrayList.indexOf(mediaAction2)));
        }
        MediaAction mediaAction3 = mediaButton.playOrPause;
        if (mediaAction3 != null) {
            arrayList.add(mediaAction3);
            arrayList2.add(Integer.valueOf(arrayList.indexOf(mediaAction3)));
        }
        MediaAction mediaAction4 = mediaButton.nextOrCustom;
        if (mediaAction4 != null) {
            arrayList.add(mediaAction4);
            arrayList2.add(Integer.valueOf(arrayList.indexOf(mediaAction4)));
        }
        MediaAction mediaAction5 = mediaButton.custom1;
        if (mediaAction5 != null) {
            arrayList.add(mediaAction5);
        }
        return new Pair(arrayList, arrayList2);
    }

    public static void removeEntry$default(MediaDataManager mediaDataManager, String str) {
        MediaData mediaData = (MediaData) mediaDataManager.mediaEntries.remove(str);
        if (mediaData != null) {
            mediaDataManager.logger.logMediaRemoved(mediaData.appUid, mediaData.packageName, mediaData.instanceId);
        }
        mediaDataManager.notifyMediaDataRemoved(str);
    }

    public final void convertToResumePlayer(MediaData mediaData, String str) {
        AbstractC0689x6838b71d.m68m("Converting ", str, " to resume", "MediaDataManager");
        CharSequence charSequence = mediaData.song;
        boolean z = charSequence == null || StringsKt__StringsJVMKt.isBlank(charSequence);
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        String str2 = mediaData.packageName;
        if (z) {
            Log.e("MediaDataManager", "Description incomplete");
            notifyMediaDataRemoved(str);
            mediaUiEventLogger.logMediaRemoved(mediaData.appUid, str2, mediaData.instanceId);
            return;
        }
        Runnable runnable = mediaData.resumeAction;
        MediaAction resumeMediaAction = runnable != null ? getResumeMediaAction(runnable) : null;
        List singletonList = resumeMediaAction != null ? Collections.singletonList(resumeMediaAction) : EmptyList.INSTANCE;
        Context context = this.context;
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str2);
        MediaData copy$default = MediaData.copy$default(mediaData, singletonList, Collections.singletonList(0), new MediaButton(resumeMediaAction, null, null, null, null, false, false, 126, null), null, launchIntentForPackage != null ? PendingIntent.getActivity(context, 0, launchIntentForPackage, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) : null, null, false, null, true, false, Boolean.FALSE, true, null, 0, 265135231);
        LinkedHashMap linkedHashMap = this.mediaEntries;
        boolean z2 = linkedHashMap.put(str2, copy$default) == null;
        StringBuilder sb = new StringBuilder("migrating? ");
        sb.append(z2);
        sb.append(" from ");
        sb.append(str);
        sb.append(" -> ");
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str2, "MediaDataManager");
        if (z2) {
            notifyMediaDataLoaded(str2, str, copy$default);
        } else {
            notifyMediaDataRemoved(str);
            notifyMediaDataLoaded(str2, str2, copy$default);
        }
        mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.ACTIVE_TO_RESUME, copy$default.appUid, str2, copy$default.instanceId);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((MediaData) entry.getValue()).resumption) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        int size = linkedHashMap2.size();
        if (size > 5) {
            for (Pair pair : CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap2), new Comparator() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$convertToResumePlayer$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    Pair pair2 = (Pair) obj;
                    Long valueOf = Long.valueOf(((MediaData) pair2.component2()).lastActive);
                    Pair pair3 = (Pair) obj2;
                    return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Long.valueOf(((MediaData) pair3.component2()).lastActive));
                }
            }).subList(0, size - 5)) {
                String str3 = (String) pair.component1();
                MediaData mediaData2 = (MediaData) pair.component2();
                Log.d("MediaDataManager", "Removing excess control " + str3);
                linkedHashMap.remove(str3);
                notifyMediaDataRemoved(str3);
                mediaUiEventLogger.logMediaRemoved(mediaData2.appUid, mediaData2.packageName, mediaData2.instanceId);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final MediaButton createActionsFromState(final String str, final MediaController mediaController, UserHandle userHandle) {
        boolean z;
        MediaAction standardAction;
        MediaAction createActionsFromState$nextCustomAction;
        final PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState != null) {
            MediaFlags mediaFlags = this.mediaFlags;
            mediaFlags.getClass();
            if (StatusBarManager.useMediaSessionActionsForApp(str, userHandle)) {
                z = true;
            } else {
                Flags.INSTANCE.getClass();
                mediaFlags.featureFlags.getClass();
                z = false;
            }
            if (z) {
                if (NotificationMediaManager.CONNECTING_MEDIA_STATES.contains(Integer.valueOf(playbackState.getState()))) {
                    Context context = this.context;
                    Drawable drawable = context.getDrawable(R.drawable.pointer_wait_vector_85);
                    ((Animatable) drawable).start();
                    standardAction = new MediaAction(drawable, null, context.getString(com.android.systemui.R.string.controls_media_button_connecting), context.getDrawable(com.android.systemui.R.drawable.ic_media_connecting_container), Integer.valueOf(R.drawable.pointer_wait_vector_85));
                } else {
                    standardAction = NotificationMediaManager.isPlayingState(playbackState.getState()) ? getStandardAction(mediaController, playbackState.getActions(), 2L) : getStandardAction(mediaController, playbackState.getActions(), 4L);
                }
                MediaAction mediaAction = standardAction;
                MediaAction standardAction2 = getStandardAction(mediaController, playbackState.getActions(), 16L);
                MediaAction standardAction3 = getStandardAction(mediaController, playbackState.getActions(), 32L);
                TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(SequencesKt___SequencesKt.filterNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(playbackState.getCustomActions())), new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromState$customActions$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        final PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) obj;
                        MediaDataManager mediaDataManager = MediaDataManager.this;
                        String str2 = str;
                        final MediaController mediaController2 = mediaController;
                        String str3 = MediaDataManager.SMARTSPACE_UI_SURFACE_LABEL;
                        mediaDataManager.getClass();
                        return new MediaAction(Icon.createWithResource(str2, customAction.getIcon()).loadDrawable(mediaDataManager.context), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getCustomAction$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaController.TransportControls transportControls = mediaController2.getTransportControls();
                                PlaybackState.CustomAction customAction2 = customAction;
                                transportControls.sendCustomAction(customAction2, customAction2.getExtras());
                            }
                        }, customAction.getName(), null, null, 16, null);
                    }
                }));
                Bundle extras = mediaController.getExtras();
                boolean z2 = extras != null && extras.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS");
                Bundle extras2 = mediaController.getExtras();
                boolean z3 = extras2 != null && extras2.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT");
                MediaAction createActionsFromState$nextCustomAction2 = standardAction2 != null ? standardAction2 : !z2 ? createActionsFromState$nextCustomAction(transformingSequence$iterator$1) : null;
                if (standardAction3 != null) {
                    createActionsFromState$nextCustomAction = standardAction3;
                } else {
                    createActionsFromState$nextCustomAction = z3 ? null : createActionsFromState$nextCustomAction(transformingSequence$iterator$1);
                }
                return new MediaButton(mediaAction, createActionsFromState$nextCustomAction, createActionsFromState$nextCustomAction2, createActionsFromState$nextCustomAction(transformingSequence$iterator$1), createActionsFromState$nextCustomAction(transformingSequence$iterator$1), z3, z2);
            }
        }
        return null;
    }

    public final boolean dismissMediaData(final String str, boolean z) {
        boolean z2 = this.mediaEntries.get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$dismissMediaData$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSession.Token token;
                MediaData mediaData = (MediaData) MediaDataManager.this.mediaEntries.get(str);
                if (mediaData != null) {
                    MediaDataManager mediaDataManager = MediaDataManager.this;
                    if (!(mediaData.playbackLocation == 0) || (token = mediaData.token) == null) {
                        return;
                    }
                    MediaControllerFactory mediaControllerFactory = mediaDataManager.mediaControllerFactory;
                    mediaControllerFactory.getClass();
                    new MediaController(mediaControllerFactory.mContext, token).getTransportControls().stop();
                }
            }
        });
        if (z) {
            removeEntry$default(this, str);
        } else {
            this.foregroundExecutor.executeDelayed(0L, new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$dismissMediaData$2
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDataManager.removeEntry$default(MediaDataManager.this, str);
                }
            });
        }
        return z2;
    }

    public final void dismissSmartspaceRecommendation(String str) {
        if (Intrinsics.areEqual(this.smartspaceMediaData.targetId, str) && this.smartspaceMediaData.isValid()) {
            Log.d("MediaDataManager", "Dismissing Smartspace media target");
            SmartspaceMediaData smartspaceMediaData = this.smartspaceMediaData;
            if (smartspaceMediaData.isActive) {
                this.smartspaceMediaData = SmartspaceMediaData.copy$default(MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceMediaData.targetId, false, null, 0L, smartspaceMediaData.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
            }
            this.foregroundExecutor.executeDelayed(0L, new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$dismissSmartspaceRecommendation$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDataManager mediaDataManager = MediaDataManager.this;
                    mediaDataManager.notifySmartspaceMediaDataRemoved(mediaDataManager.smartspaceMediaData.targetId, true);
                }
            });
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("internalListeners: " + this.internalListeners);
        printWriter.println("externalListeners: " + this.mediaDataFilter.m157xef59304f());
        printWriter.println("mediaEntries: " + this.mediaEntries);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("useMediaResumption: ", this.useMediaResumption, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("allowMediaRecommendations: ", this.allowMediaRecommendations, printWriter);
    }

    public final MediaAction getResumeMediaAction(Runnable runnable) {
        Context context = this.context;
        return new MediaAction(Icon.createWithResource(context, com.android.systemui.R.drawable.ic_media_play).setTint(this.themeText).loadDrawable(context), runnable, context.getString(com.android.systemui.R.string.controls_media_resume), context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null);
    }

    public final MediaAction getStandardAction(final MediaController mediaController, long j, long j2) {
        if (!(((j2 == 4 || j2 == 2) && (j & 512) > 0) || (j & j2) != 0)) {
            return null;
        }
        Context context = this.context;
        if (j2 == 4) {
            return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_player), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$1
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().play();
                }
            }, context.getString(com.android.systemui.R.string.controls_media_button_play), context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null);
        }
        if (j2 == 2) {
            return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$2
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().pause();
                }
            }, context.getString(com.android.systemui.R.string.controls_media_button_pause), context.getDrawable(com.android.systemui.R.drawable.ic_media_pause_container), null, 16, null);
        }
        if (j2 == 16) {
            return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_preview), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$3
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToPrevious();
                }
            }, context.getString(com.android.systemui.R.string.controls_media_button_prev), null, null, 16, null);
        }
        if (j2 == 32) {
            return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_next), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$4
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToNext();
                }
            }, context.getString(com.android.systemui.R.string.controls_media_button_next), null, null, 16, null);
        }
        return null;
    }

    public final boolean hasActiveMediaOrRecommendation() {
        boolean z;
        MediaDataFilter mediaDataFilter = this.mediaDataFilter;
        LinkedHashMap linkedHashMap = mediaDataFilter.userEntries;
        if (!linkedHashMap.isEmpty()) {
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                if (((MediaData) ((Map.Entry) it.next()).getValue()).active) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (z) {
            return true;
        }
        SmartspaceMediaData smartspaceMediaData = mediaDataFilter.smartspaceMediaData;
        return smartspaceMediaData.isActive && (smartspaceMediaData.isValid() || mediaDataFilter.reactivatedKey != null);
    }

    public final Bitmap loadBitmapFromUri(Uri uri) {
        if (uri.getScheme() == null) {
            return null;
        }
        if (!uri.getScheme().equals("content") && !uri.getScheme().equals("android.resource") && !uri.getScheme().equals("file")) {
            return null;
        }
        try {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.context.getContentResolver(), uri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadBitmapFromUri$1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    int width = imageInfo.getSize().getWidth();
                    int height = imageInfo.getSize().getHeight();
                    android.util.Pair pair = new android.util.Pair(Integer.valueOf(width), Integer.valueOf(height));
                    android.util.Pair pair2 = new android.util.Pair(Integer.valueOf(MediaDataManager.this.artworkWidth), Integer.valueOf(MediaDataManager.this.artworkHeight));
                    float intValue = ((Integer) pair.first).intValue();
                    float intValue2 = ((Integer) pair.second).intValue();
                    float intValue3 = ((Integer) pair2.first).intValue();
                    float intValue4 = ((Integer) pair2.second).intValue();
                    float f = (intValue == 0.0f || intValue2 == 0.0f || intValue3 == 0.0f || intValue4 == 0.0f) ? 0.0f : intValue / intValue2 > intValue3 / intValue4 ? intValue4 / intValue2 : intValue3 / intValue;
                    if (!(f == 0.0f) && f < 1.0f) {
                        imageDecoder.setTargetSize((int) (width * f), (int) (f * height));
                    }
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (IOException e) {
            Log.e("MediaDataManager", "Unable to load bitmap", e);
            return null;
        } catch (RuntimeException e2) {
            Log.e("MediaDataManager", "Unable to load bitmap", e2);
            return null;
        }
    }

    public final void logSingleVsMultipleMediaAdded(int i, String str, InstanceId instanceId) {
        LinkedHashMap linkedHashMap = this.mediaEntries;
        int size = linkedHashMap.size();
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        if (size == 1) {
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_SINGLE_PLAYER, i, str, instanceId);
        } else if (linkedHashMap.size() == 2) {
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_MULTIPLE_PLAYERS, i, str, instanceId);
        }
    }

    public final void notifyMediaDataLoaded(String str, String str2, MediaData mediaData) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            Listener.DefaultImpls.onMediaDataLoaded$default((Listener) it.next(), str, str2, mediaData, false, 0, false, 56);
        }
    }

    public final void notifyMediaDataRemoved(String str) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onMediaDataRemoved(str);
        }
    }

    public final void notifySmartspaceMediaDataRemoved(String str, boolean z) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        LinkedHashMap linkedHashMap = this.mediaEntries;
        if (!isTagEnabled) {
            Assert.isMainThread();
            if (linkedHashMap.containsKey(str)) {
                linkedHashMap.put(str, mediaData);
                notifyMediaDataLoaded(str, str2, mediaData);
                return;
            }
            return;
        }
        Trace.traceBegin(4096L, "MediaDataManager#onMediaDataLoaded");
        try {
            Assert.isMainThread();
            if (linkedHashMap.containsKey(str)) {
                linkedHashMap.put(str, mediaData);
                notifyMediaDataLoaded(str, str2, mediaData);
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final void onMediaDataRemoved(String str) {
        Log.d("MediaDataManager", "onMediaDataRemoved key=".concat(str));
        Assert.isMainThread();
        dismissMediaData(str, true);
    }

    public final void onNotificationAdded(final String str, final StatusBarNotification statusBarNotification) {
        final String str2;
        boolean z;
        if (this.useQsMediaPlayer) {
            String[] strArr = MediaDataManagerKt.ART_URIS;
            if (statusBarNotification.getNotification().isMediaNotification()) {
                Assert.isMainThread();
                String packageName = statusBarNotification.getPackageName();
                LinkedHashMap linkedHashMap = this.mediaEntries;
                if (linkedHashMap.containsKey(str)) {
                    str2 = str;
                } else {
                    if (!linkedHashMap.containsKey(packageName)) {
                        packageName = null;
                    }
                    str2 = packageName;
                }
                if (str2 == null) {
                    linkedHashMap.put(str, MediaData.copy$default(MediaDataManagerKt.LOADING, null, null, null, statusBarNotification.getPackageName(), null, null, false, null, false, false, null, false, this.logger.getNewInstanceId(), 0, 260045823));
                } else {
                    if (Intrinsics.areEqual(str2, str)) {
                        z = false;
                        final boolean z2 = z;
                        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaData$1
                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Removed duplicated region for block: B:105:0x02a0  */
                            /* JADX WARN: Removed duplicated region for block: B:145:0x042e  */
                            /* JADX WARN: Removed duplicated region for block: B:149:0x044d  */
                            /* JADX WARN: Removed duplicated region for block: B:156:0x0476  */
                            /* JADX WARN: Removed duplicated region for block: B:159:0x0481  */
                            /* JADX WARN: Removed duplicated region for block: B:174:0x04ab  */
                            /* JADX WARN: Removed duplicated region for block: B:192:0x047b  */
                            /* JADX WARN: Removed duplicated region for block: B:194:0x045a  */
                            /* JADX WARN: Removed duplicated region for block: B:195:0x0431  */
                            /* JADX WARN: Removed duplicated region for block: B:206:0x03d9  */
                            /* JADX WARN: Removed duplicated region for block: B:207:0x027c  */
                            /* JADX WARN: Removed duplicated region for block: B:209:0x01d1  */
                            /* JADX WARN: Removed duplicated region for block: B:212:0x01bf  */
                            /* JADX WARN: Removed duplicated region for block: B:213:0x0170  */
                            /* JADX WARN: Removed duplicated region for block: B:52:0x0125  */
                            /* JADX WARN: Removed duplicated region for block: B:62:0x0153  */
                            /* JADX WARN: Removed duplicated region for block: B:66:0x015f  */
                            /* JADX WARN: Removed duplicated region for block: B:70:0x016b  */
                            /* JADX WARN: Removed duplicated region for block: B:73:0x019b  */
                            /* JADX WARN: Removed duplicated region for block: B:81:0x01ca  */
                            /* JADX WARN: Removed duplicated region for block: B:88:0x01e2  */
                            /* JADX WARN: Removed duplicated region for block: B:94:0x0209  */
                            /* JADX WARN: Type inference failed for: r0v11, types: [T, java.lang.Object] */
                            /* JADX WARN: Type inference failed for: r0v56, types: [T, java.lang.Object] */
                            /* JADX WARN: Type inference failed for: r11v0 */
                            /* JADX WARN: Type inference failed for: r11v1, types: [T, java.lang.CharSequence] */
                            /* JADX WARN: Type inference failed for: r11v39, types: [T, java.lang.String] */
                            /* JADX WARN: Type inference failed for: r11v47, types: [T] */
                            /* JADX WARN: Type inference failed for: r11v52 */
                            /* JADX WARN: Type inference failed for: r11v53, types: [T] */
                            /* JADX WARN: Type inference failed for: r11v60 */
                            /* JADX WARN: Type inference failed for: r11v61 */
                            /* JADX WARN: Type inference failed for: r11v62 */
                            /* JADX WARN: Type inference failed for: r11v63 */
                            /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
                            /* JADX WARN: Type inference failed for: r1v12, types: [T, java.lang.Object] */
                            /* JADX WARN: Type inference failed for: r6v17, types: [java.util.List] */
                            /* JADX WARN: Type inference failed for: r6v2 */
                            /* JADX WARN: Type inference failed for: r6v29, types: [T, com.android.systemui.media.controls.models.player.MediaDeviceData] */
                            /* JADX WARN: Type inference failed for: r6v3, types: [T, java.lang.CharSequence] */
                            /* JADX WARN: Type inference failed for: r6v34, types: [T] */
                            /* JADX WARN: Type inference failed for: r6v40 */
                            /* JADX WARN: Type inference failed for: r6v41 */
                            /* JADX WARN: Type inference failed for: r6v42 */
                            /* JADX WARN: Type inference failed for: r6v9, types: [T, kotlin.collections.EmptyList] */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() {
                                MediaSession.Token token;
                                Bitmap bitmap;
                                MediaFlags mediaFlags;
                                String str3;
                                Icon icon;
                                ?? string;
                                Ref$ObjectRef ref$ObjectRef;
                                Ref$BooleanRef ref$BooleanRef;
                                Notification notification2;
                                final MediaButton createActionsFromState;
                                ApplicationInfo applicationInfo;
                                boolean z3;
                                String str4;
                                String str5;
                                Ref$ObjectRef ref$ObjectRef2;
                                int i;
                                MediaData mediaData;
                                MediaUiEventLogger mediaUiEventLogger;
                                MediaUiEvent mediaUiEvent;
                                MediaUiEvent mediaUiEvent2;
                                ArrayList arrayList;
                                int i2;
                                ArrayList arrayList2;
                                int i3;
                                Icon icon2;
                                boolean z4;
                                String[] strArr2;
                                final MediaDataManager mediaDataManager = MediaDataManager.this;
                                String str6 = str;
                                final StatusBarNotification statusBarNotification2 = statusBarNotification;
                                String str7 = str2;
                                boolean z5 = z2;
                                mediaDataManager.getClass();
                                MediaSession.Token token2 = (MediaSession.Token) statusBarNotification2.getNotification().extras.getParcelable("android.mediaSession", MediaSession.Token.class);
                                if (token2 == null) {
                                    return;
                                }
                                MediaControllerFactory mediaControllerFactory = mediaDataManager.mediaControllerFactory;
                                mediaControllerFactory.getClass();
                                MediaController mediaController = new MediaController(mediaControllerFactory.mContext, token2);
                                MediaMetadata metadata = mediaController.getMetadata();
                                Notification notification3 = statusBarNotification2.getNotification();
                                ApplicationInfo applicationInfo2 = (ApplicationInfo) notification3.extras.getParcelable("android.appInfo", ApplicationInfo.class);
                                Context context = mediaDataManager.context;
                                if (applicationInfo2 == null) {
                                    String packageName2 = statusBarNotification2.getPackageName();
                                    try {
                                        applicationInfo2 = context.getPackageManager().getApplicationInfo(packageName2, 0);
                                    } catch (PackageManager.NameNotFoundException e) {
                                        Log.w("MediaDataManager", "Could not get app info for ".concat(packageName2), e);
                                        applicationInfo2 = null;
                                    }
                                }
                                String string2 = statusBarNotification2.getNotification().extras.getString("android.substName");
                                if (string2 == null) {
                                    string2 = applicationInfo2 != null ? context.getPackageManager().getApplicationLabel(applicationInfo2).toString() : statusBarNotification2.getPackageName();
                                }
                                final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
                                ?? string3 = metadata != null ? metadata.getString("android.media.metadata.DISPLAY_TITLE") : 0;
                                ref$ObjectRef3.element = string3;
                                if (string3 == 0 || StringsKt__StringsJVMKt.isBlank(string3)) {
                                    ref$ObjectRef3.element = metadata != null ? metadata.getString("android.media.metadata.TITLE") : 0;
                                }
                                CharSequence charSequence = (CharSequence) ref$ObjectRef3.element;
                                if (charSequence == null || StringsKt__StringsJVMKt.isBlank(charSequence)) {
                                    CharSequence charSequence2 = notification3.extras.getCharSequence("android.title");
                                    ?? r11 = charSequence2;
                                    if (charSequence2 == null) {
                                        r11 = notification3.extras.getCharSequence("android.title.big");
                                    }
                                    ref$ObjectRef3.element = r11;
                                }
                                CharSequence charSequence3 = (CharSequence) ref$ObjectRef3.element;
                                if (charSequence3 == null || StringsKt__StringsJVMKt.isBlank(charSequence3)) {
                                    ref$ObjectRef3.element = context.getString(com.android.systemui.R.string.controls_media_empty_title, string2);
                                    try {
                                        token = token2;
                                    } catch (RuntimeException unused) {
                                        token = token2;
                                    }
                                    try {
                                        mediaDataManager.statusBarManager.logBlankMediaTitle(statusBarNotification2.getPackageName(), statusBarNotification2.getUser().getIdentifier());
                                    } catch (RuntimeException unused2) {
                                        Log.e("MediaDataManager", "Error reporting blank media title for package " + statusBarNotification2.getPackageName());
                                        if (metadata != null) {
                                        }
                                        bitmap = null;
                                        if (bitmap == null) {
                                        }
                                        if (bitmap == null) {
                                        }
                                        if (bitmap != null) {
                                        }
                                        Icon smallIcon = statusBarNotification2.getNotification().getSmallIcon();
                                        Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                                        final Icon icon3 = r11;
                                        mediaFlags = mediaDataManager.mediaFlags;
                                        mediaFlags.getClass();
                                        Flags.INSTANCE.getClass();
                                        if (((FeatureFlagsRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_EXPLICIT_INDICATOR)) {
                                        }
                                        Ref$ObjectRef ref$ObjectRef4 = new Ref$ObjectRef();
                                        if (metadata == null) {
                                        }
                                        ref$ObjectRef4.element = string;
                                        if (string != 0 || StringsKt__StringsJVMKt.isBlank(string)) {
                                        }
                                        Ref$ObjectRef ref$ObjectRef5 = new Ref$ObjectRef();
                                        if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                                        }
                                        final Ref$ObjectRef ref$ObjectRef6 = new Ref$ObjectRef();
                                        ?? r6 = EmptyList.INSTANCE;
                                        ref$ObjectRef6.element = r6;
                                        final Ref$ObjectRef ref$ObjectRef7 = new Ref$ObjectRef();
                                        ref$ObjectRef7.element = r6;
                                        createActionsFromState = mediaDataManager.createActionsFromState(statusBarNotification2.getPackageName(), mediaController, statusBarNotification2.getUser());
                                        if (createActionsFromState != null) {
                                        }
                                        Log.d("MediaDataManager", "semanticActions=" + createActionsFromState);
                                        Log.d("MediaDataManager", "actionsToShowCollapsed=" + ref$ObjectRef7.element + ", actionIcons=" + ref$ObjectRef6.element);
                                        if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                                        }
                                        final int i4 = i;
                                        PlaybackState playbackState = mediaController.getPlaybackState();
                                        final Boolean valueOf = playbackState == null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())) : null;
                                        final String str8 = str4;
                                        mediaData = (MediaData) mediaDataManager.mediaEntries.get(str8);
                                        mediaUiEventLogger = mediaDataManager.logger;
                                        if (mediaData != null) {
                                        }
                                        InstanceId newInstanceId = mediaUiEventLogger.getNewInstanceId();
                                        final InstanceId instanceId = newInstanceId;
                                        final int i5 = applicationInfo == null ? applicationInfo.uid : -1;
                                        if (!z3) {
                                        }
                                        ((SystemClockImpl) mediaDataManager.systemClock).getClass();
                                        final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                                        final String str9 = str5;
                                        final String str10 = str3;
                                        final Icon icon4 = icon;
                                        final Ref$ObjectRef ref$ObjectRef8 = ref$ObjectRef;
                                        final Ref$ObjectRef ref$ObjectRef9 = ref$ObjectRef2;
                                        final Ref$BooleanRef ref$BooleanRef3 = ref$BooleanRef;
                                        final MediaSession.Token token3 = token;
                                        final Notification notification4 = notification2;
                                        ((ExecutorImpl) mediaDataManager.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBg$2
                                            /* JADX WARN: Multi-variable type inference failed */
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                MediaData mediaData2 = (MediaData) MediaDataManager.this.mediaEntries.get(str8);
                                                Runnable runnable = mediaData2 != null ? mediaData2.resumeAction : null;
                                                MediaData mediaData3 = (MediaData) MediaDataManager.this.mediaEntries.get(str8);
                                                boolean z6 = mediaData3 != null && mediaData3.hasCheckedForResume;
                                                MediaData mediaData4 = (MediaData) MediaDataManager.this.mediaEntries.get(str8);
                                                MediaDataManager.this.onMediaDataLoaded(str8, str9, new MediaData(statusBarNotification2.getNormalizedUserId(), true, str10, icon4, (CharSequence) ref$ObjectRef8.element, (CharSequence) ref$ObjectRef3.element, icon3, (List) ref$ObjectRef6.element, (List) ref$ObjectRef7.element, createActionsFromState, statusBarNotification2.getPackageName(), token3, notification4.contentIntent, (MediaDeviceData) ref$ObjectRef9.element, mediaData4 != null ? mediaData4.active : true, runnable, i4, false, str8, z6, valueOf, !statusBarNotification2.isOngoing(), elapsedRealtime, instanceId, i5, ref$BooleanRef3.element, null, null, 201457664, null));
                                            }
                                        });
                                    }
                                } else {
                                    token = token2;
                                }
                                if (metadata != null) {
                                    String[] strArr3 = MediaDataManagerKt.ART_URIS;
                                    int i6 = 0;
                                    while (i6 < 3) {
                                        String str11 = strArr3[i6];
                                        String string4 = metadata.getString(str11);
                                        if (!TextUtils.isEmpty(string4)) {
                                            strArr2 = strArr3;
                                            bitmap = mediaDataManager.loadBitmapFromUri(Uri.parse(string4));
                                            if (bitmap != null) {
                                                AbstractC0000x2c234b15.m3m("loaded art from ", str11, "MediaDataManager");
                                                break;
                                            }
                                        } else {
                                            strArr2 = strArr3;
                                        }
                                        i6++;
                                        strArr3 = strArr2;
                                    }
                                }
                                bitmap = null;
                                if (bitmap == null) {
                                    bitmap = metadata != null ? metadata.getBitmap("android.media.metadata.ART") : null;
                                }
                                if (bitmap == null) {
                                    bitmap = metadata != null ? metadata.getBitmap("android.media.metadata.ALBUM_ART") : null;
                                }
                                Icon largeIcon = bitmap != null ? notification3.getLargeIcon() : Icon.createWithBitmap(bitmap);
                                Icon smallIcon2 = statusBarNotification2.getNotification().getSmallIcon();
                                Ref$BooleanRef ref$BooleanRef22 = new Ref$BooleanRef();
                                final Icon icon32 = largeIcon;
                                mediaFlags = mediaDataManager.mediaFlags;
                                mediaFlags.getClass();
                                Flags.INSTANCE.getClass();
                                if (((FeatureFlagsRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_EXPLICIT_INDICATOR)) {
                                    str3 = string2;
                                    icon = smallIcon2;
                                } else {
                                    MediaMetadataCompat fromMediaMetadata = MediaMetadataCompat.fromMediaMetadata(metadata);
                                    if (fromMediaMetadata != null) {
                                        icon = smallIcon2;
                                        str3 = string2;
                                        if (fromMediaMetadata.mBundle.getLong("android.media.IS_EXPLICIT", 0L) == 1) {
                                            z4 = true;
                                            ref$BooleanRef22.element = z4;
                                        }
                                    } else {
                                        str3 = string2;
                                        icon = smallIcon2;
                                    }
                                    z4 = false;
                                    ref$BooleanRef22.element = z4;
                                }
                                Ref$ObjectRef ref$ObjectRef42 = new Ref$ObjectRef();
                                string = metadata == null ? metadata.getString("android.media.metadata.ARTIST") : 0;
                                ref$ObjectRef42.element = string;
                                if (string != 0 || StringsKt__StringsJVMKt.isBlank(string)) {
                                    CharSequence charSequence4 = notification3.extras.getCharSequence("android.text");
                                    ?? r62 = charSequence4;
                                    if (charSequence4 == null) {
                                        r62 = notification3.extras.getCharSequence("android.bigText");
                                    }
                                    ref$ObjectRef42.element = r62;
                                }
                                Ref$ObjectRef ref$ObjectRef52 = new Ref$ObjectRef();
                                if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                                    ref$ObjectRef = ref$ObjectRef42;
                                    ref$BooleanRef = ref$BooleanRef22;
                                    notification2 = notification3;
                                } else {
                                    Bundle bundle = statusBarNotification2.getNotification().extras;
                                    CharSequence charSequence5 = bundle.getCharSequence("android.mediaRemoteDevice", null);
                                    ref$BooleanRef = ref$BooleanRef22;
                                    notification2 = notification3;
                                    int i7 = bundle.getInt("android.mediaRemoteIcon", -1);
                                    ref$ObjectRef = ref$ObjectRef42;
                                    PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
                                    Log.d("MediaDataManager", str6 + " is RCN for " + ((Object) charSequence5));
                                    if (charSequence5 != null && i7 > -1) {
                                        ref$ObjectRef52.element = new MediaDeviceData(pendingIntent != null && pendingIntent.isActivity(), Icon.createWithResource(statusBarNotification2.getPackageName(), i7).loadDrawable(statusBarNotification2.getPackageContext(context)), charSequence5, pendingIntent, null, false, null, 80, null);
                                    }
                                }
                                final Ref$ObjectRef<List<MediaAction>> ref$ObjectRef62 = new Ref$ObjectRef();
                                ?? r63 = EmptyList.INSTANCE;
                                ref$ObjectRef62.element = r63;
                                final Ref$ObjectRef<List<Integer>> ref$ObjectRef72 = new Ref$ObjectRef();
                                ref$ObjectRef72.element = r63;
                                createActionsFromState = mediaDataManager.createActionsFromState(statusBarNotification2.getPackageName(), mediaController, statusBarNotification2.getUser());
                                if (createActionsFromState != null) {
                                    Notification notification5 = statusBarNotification2.getNotification();
                                    ArrayList arrayList3 = new ArrayList();
                                    ref$ObjectRef2 = ref$ObjectRef52;
                                    Notification.Action[] actionArr = notification5.actions;
                                    str5 = str7;
                                    int[] intArray = notification5.extras.getIntArray("android.compactActions");
                                    if (intArray != null) {
                                        z3 = z5;
                                        arrayList = new ArrayList(intArray.length);
                                        applicationInfo = applicationInfo2;
                                        int i8 = 0;
                                        for (int length = intArray.length; i8 < length; length = length) {
                                            arrayList.add(Integer.valueOf(intArray[i8]));
                                            i8++;
                                        }
                                    } else {
                                        applicationInfo = applicationInfo2;
                                        z3 = z5;
                                        arrayList = new ArrayList();
                                    }
                                    int size = arrayList.size();
                                    int i9 = MediaDataManager.MAX_COMPACT_ACTIONS;
                                    if (size > i9) {
                                        str4 = str6;
                                        Log.e("MediaDataManager", "Too many compact actions for " + statusBarNotification2.getKey() + ",limiting to first " + i9);
                                        i2 = 0;
                                        arrayList2 = arrayList.subList(0, i9);
                                    } else {
                                        str4 = str6;
                                        i2 = 0;
                                        arrayList2 = arrayList;
                                    }
                                    if (actionArr != null) {
                                        int length2 = actionArr.length;
                                        while (true) {
                                            if (i2 >= length2) {
                                                break;
                                            }
                                            final Notification.Action action = actionArr[i2];
                                            int i10 = MediaDataManager.MAX_NOTIFICATION_ACTIONS;
                                            if (i2 == i10) {
                                                Log.w("MediaDataManager", "Too many notification actions for " + statusBarNotification2.getKey() + ", limiting to first " + i10);
                                                break;
                                            }
                                            if (action.getIcon() == null) {
                                                i3 = length2;
                                                Log.i("MediaDataManager", "No icon for action " + i2 + " " + ((Object) action.title));
                                                arrayList2.remove(Integer.valueOf(i2));
                                            } else {
                                                i3 = length2;
                                                Runnable runnable = action.actionIntent != null ? new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        if (action.actionIntent.isActivity()) {
                                                            mediaDataManager.activityStarter.startPendingIntentDismissingKeyguard(action.actionIntent);
                                                            return;
                                                        }
                                                        if (!action.isAuthenticationRequired()) {
                                                            MediaDataManager.access$sendPendingIntent(mediaDataManager, action.actionIntent);
                                                            return;
                                                        }
                                                        final MediaDataManager mediaDataManager2 = mediaDataManager;
                                                        ActivityStarter activityStarter = mediaDataManager2.activityStarter;
                                                        final Notification.Action action2 = action;
                                                        activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.1
                                                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                                            public final boolean onDismiss() {
                                                                return MediaDataManager.access$sendPendingIntent(MediaDataManager.this, action2.actionIntent);
                                                            }
                                                        }, new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.2
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                            }
                                                        }, true);
                                                    }
                                                } : null;
                                                Icon icon5 = action.getIcon();
                                                if (icon5 != null && icon5.getType() == 2) {
                                                    String packageName3 = statusBarNotification2.getPackageName();
                                                    Icon icon6 = action.getIcon();
                                                    Intrinsics.checkNotNull(icon6);
                                                    icon2 = Icon.createWithResource(packageName3, icon6.getResId());
                                                } else {
                                                    icon2 = action.getIcon();
                                                }
                                                arrayList3.add(new MediaAction(icon2.setTint(mediaDataManager.themeText).loadDrawable(context), runnable, action.title, null, null, 16, null));
                                            }
                                            i2++;
                                            length2 = i3;
                                        }
                                    }
                                    Pair pair = new Pair(arrayList3, arrayList2);
                                    ref$ObjectRef62.element = pair.getFirst();
                                    ref$ObjectRef72.element = pair.getSecond();
                                } else {
                                    applicationInfo = applicationInfo2;
                                    z3 = z5;
                                    str4 = str6;
                                    str5 = str7;
                                    ref$ObjectRef2 = ref$ObjectRef52;
                                    Pair generateActionsFromSemantic = MediaDataManager.generateActionsFromSemantic(createActionsFromState);
                                    ref$ObjectRef62.element = generateActionsFromSemantic.getFirst();
                                    ref$ObjectRef72.element = generateActionsFromSemantic.getSecond();
                                }
                                Log.d("MediaDataManager", "semanticActions=" + createActionsFromState);
                                Log.d("MediaDataManager", "actionsToShowCollapsed=" + ref$ObjectRef72.element + ", actionIcons=" + ref$ObjectRef62.element);
                                if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                                    MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
                                    i = playbackInfo != null && playbackInfo.getPlaybackType() == 1 ? 0 : 1;
                                } else {
                                    i = 2;
                                }
                                final int i42 = i;
                                PlaybackState playbackState2 = mediaController.getPlaybackState();
                                final Boolean valueOf2 = playbackState2 == null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState2.getState())) : null;
                                final String str82 = str4;
                                mediaData = (MediaData) mediaDataManager.mediaEntries.get(str82);
                                mediaUiEventLogger = mediaDataManager.logger;
                                if (mediaData != null || (newInstanceId = mediaData.instanceId) == null) {
                                    InstanceId newInstanceId2 = mediaUiEventLogger.getNewInstanceId();
                                }
                                final InstanceId instanceId2 = newInstanceId2;
                                final int i52 = applicationInfo == null ? applicationInfo.uid : -1;
                                if (!z3) {
                                    mediaDataManager.logSingleVsMultipleMediaAdded(i52, statusBarNotification2.getPackageName(), instanceId2);
                                    String packageName4 = statusBarNotification2.getPackageName();
                                    mediaUiEventLogger.getClass();
                                    if (i42 == 0) {
                                        mediaUiEvent2 = MediaUiEvent.LOCAL_MEDIA_ADDED;
                                    } else if (i42 == 1) {
                                        mediaUiEvent2 = MediaUiEvent.CAST_MEDIA_ADDED;
                                    } else {
                                        if (i42 != 2) {
                                            throw new IllegalArgumentException("Unknown playback location");
                                        }
                                        mediaUiEvent2 = MediaUiEvent.REMOTE_MEDIA_ADDED;
                                    }
                                    mediaUiEventLogger.logger.logWithInstanceId(mediaUiEvent2, i52, packageName4, instanceId2);
                                } else if (!(mediaData != null && i42 == mediaData.playbackLocation)) {
                                    String packageName5 = statusBarNotification2.getPackageName();
                                    mediaUiEventLogger.getClass();
                                    if (i42 == 0) {
                                        mediaUiEvent = MediaUiEvent.TRANSFER_TO_LOCAL;
                                    } else if (i42 == 1) {
                                        mediaUiEvent = MediaUiEvent.TRANSFER_TO_CAST;
                                    } else {
                                        if (i42 != 2) {
                                            throw new IllegalArgumentException("Unknown playback location");
                                        }
                                        mediaUiEvent = MediaUiEvent.TRANSFER_TO_REMOTE;
                                    }
                                    mediaUiEventLogger.logger.logWithInstanceId(mediaUiEvent, i52, packageName5, instanceId2);
                                }
                                ((SystemClockImpl) mediaDataManager.systemClock).getClass();
                                final long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                                final String str92 = str5;
                                final String str102 = str3;
                                final Icon icon42 = icon;
                                final Ref$ObjectRef<CharSequence> ref$ObjectRef82 = ref$ObjectRef;
                                final Ref$ObjectRef<MediaDeviceData> ref$ObjectRef92 = ref$ObjectRef2;
                                final Ref$BooleanRef ref$BooleanRef32 = ref$BooleanRef;
                                final MediaSession.Token token32 = token;
                                final Notification notification42 = notification2;
                                ((ExecutorImpl) mediaDataManager.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBg$2
                                    /* JADX WARN: Multi-variable type inference failed */
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MediaData mediaData2 = (MediaData) MediaDataManager.this.mediaEntries.get(str82);
                                        Runnable runnable2 = mediaData2 != null ? mediaData2.resumeAction : null;
                                        MediaData mediaData3 = (MediaData) MediaDataManager.this.mediaEntries.get(str82);
                                        boolean z6 = mediaData3 != null && mediaData3.hasCheckedForResume;
                                        MediaData mediaData4 = (MediaData) MediaDataManager.this.mediaEntries.get(str82);
                                        MediaDataManager.this.onMediaDataLoaded(str82, str92, new MediaData(statusBarNotification2.getNormalizedUserId(), true, str102, icon42, (CharSequence) ref$ObjectRef82.element, (CharSequence) ref$ObjectRef3.element, icon32, (List) ref$ObjectRef62.element, (List) ref$ObjectRef72.element, createActionsFromState, statusBarNotification2.getPackageName(), token32, notification42.contentIntent, (MediaDeviceData) ref$ObjectRef92.element, mediaData4 != null ? mediaData4.active : true, runnable2, i42, false, str82, z6, valueOf2, !statusBarNotification2.isOngoing(), elapsedRealtime2, instanceId2, i52, ref$BooleanRef32.element, null, null, 201457664, null));
                                    }
                                });
                            }
                        });
                        return;
                    }
                    Object remove = linkedHashMap.remove(str2);
                    Intrinsics.checkNotNull(remove);
                    linkedHashMap.put(str, (MediaData) remove);
                }
                z = true;
                final boolean z22 = z;
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaData$1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Removed duplicated region for block: B:105:0x02a0  */
                    /* JADX WARN: Removed duplicated region for block: B:145:0x042e  */
                    /* JADX WARN: Removed duplicated region for block: B:149:0x044d  */
                    /* JADX WARN: Removed duplicated region for block: B:156:0x0476  */
                    /* JADX WARN: Removed duplicated region for block: B:159:0x0481  */
                    /* JADX WARN: Removed duplicated region for block: B:174:0x04ab  */
                    /* JADX WARN: Removed duplicated region for block: B:192:0x047b  */
                    /* JADX WARN: Removed duplicated region for block: B:194:0x045a  */
                    /* JADX WARN: Removed duplicated region for block: B:195:0x0431  */
                    /* JADX WARN: Removed duplicated region for block: B:206:0x03d9  */
                    /* JADX WARN: Removed duplicated region for block: B:207:0x027c  */
                    /* JADX WARN: Removed duplicated region for block: B:209:0x01d1  */
                    /* JADX WARN: Removed duplicated region for block: B:212:0x01bf  */
                    /* JADX WARN: Removed duplicated region for block: B:213:0x0170  */
                    /* JADX WARN: Removed duplicated region for block: B:52:0x0125  */
                    /* JADX WARN: Removed duplicated region for block: B:62:0x0153  */
                    /* JADX WARN: Removed duplicated region for block: B:66:0x015f  */
                    /* JADX WARN: Removed duplicated region for block: B:70:0x016b  */
                    /* JADX WARN: Removed duplicated region for block: B:73:0x019b  */
                    /* JADX WARN: Removed duplicated region for block: B:81:0x01ca  */
                    /* JADX WARN: Removed duplicated region for block: B:88:0x01e2  */
                    /* JADX WARN: Removed duplicated region for block: B:94:0x0209  */
                    /* JADX WARN: Type inference failed for: r0v11, types: [T, java.lang.Object] */
                    /* JADX WARN: Type inference failed for: r0v56, types: [T, java.lang.Object] */
                    /* JADX WARN: Type inference failed for: r11v0 */
                    /* JADX WARN: Type inference failed for: r11v1, types: [T, java.lang.CharSequence] */
                    /* JADX WARN: Type inference failed for: r11v39, types: [T, java.lang.String] */
                    /* JADX WARN: Type inference failed for: r11v47, types: [T] */
                    /* JADX WARN: Type inference failed for: r11v52 */
                    /* JADX WARN: Type inference failed for: r11v53, types: [T] */
                    /* JADX WARN: Type inference failed for: r11v60 */
                    /* JADX WARN: Type inference failed for: r11v61 */
                    /* JADX WARN: Type inference failed for: r11v62 */
                    /* JADX WARN: Type inference failed for: r11v63 */
                    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
                    /* JADX WARN: Type inference failed for: r1v12, types: [T, java.lang.Object] */
                    /* JADX WARN: Type inference failed for: r6v17, types: [java.util.List] */
                    /* JADX WARN: Type inference failed for: r6v2 */
                    /* JADX WARN: Type inference failed for: r6v29, types: [T, com.android.systemui.media.controls.models.player.MediaDeviceData] */
                    /* JADX WARN: Type inference failed for: r6v3, types: [T, java.lang.CharSequence] */
                    /* JADX WARN: Type inference failed for: r6v34, types: [T] */
                    /* JADX WARN: Type inference failed for: r6v40 */
                    /* JADX WARN: Type inference failed for: r6v41 */
                    /* JADX WARN: Type inference failed for: r6v42 */
                    /* JADX WARN: Type inference failed for: r6v9, types: [T, kotlin.collections.EmptyList] */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        MediaSession.Token token;
                        Bitmap bitmap;
                        MediaFlags mediaFlags;
                        String str3;
                        Icon icon;
                        ?? string;
                        Ref$ObjectRef ref$ObjectRef;
                        Ref$BooleanRef ref$BooleanRef;
                        Notification notification2;
                        final MediaButton createActionsFromState;
                        ApplicationInfo applicationInfo;
                        boolean z3;
                        String str4;
                        String str5;
                        Ref$ObjectRef ref$ObjectRef2;
                        int i;
                        MediaData mediaData;
                        MediaUiEventLogger mediaUiEventLogger;
                        MediaUiEvent mediaUiEvent;
                        MediaUiEvent mediaUiEvent2;
                        ArrayList arrayList;
                        int i2;
                        ArrayList arrayList2;
                        int i3;
                        Icon icon2;
                        boolean z4;
                        String[] strArr2;
                        final MediaDataManager mediaDataManager = MediaDataManager.this;
                        String str6 = str;
                        final StatusBarNotification statusBarNotification2 = statusBarNotification;
                        String str7 = str2;
                        boolean z5 = z22;
                        mediaDataManager.getClass();
                        MediaSession.Token token2 = (MediaSession.Token) statusBarNotification2.getNotification().extras.getParcelable("android.mediaSession", MediaSession.Token.class);
                        if (token2 == null) {
                            return;
                        }
                        MediaControllerFactory mediaControllerFactory = mediaDataManager.mediaControllerFactory;
                        mediaControllerFactory.getClass();
                        MediaController mediaController = new MediaController(mediaControllerFactory.mContext, token2);
                        MediaMetadata metadata = mediaController.getMetadata();
                        Notification notification3 = statusBarNotification2.getNotification();
                        ApplicationInfo applicationInfo2 = (ApplicationInfo) notification3.extras.getParcelable("android.appInfo", ApplicationInfo.class);
                        Context context = mediaDataManager.context;
                        if (applicationInfo2 == null) {
                            String packageName2 = statusBarNotification2.getPackageName();
                            try {
                                applicationInfo2 = context.getPackageManager().getApplicationInfo(packageName2, 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.w("MediaDataManager", "Could not get app info for ".concat(packageName2), e);
                                applicationInfo2 = null;
                            }
                        }
                        String string2 = statusBarNotification2.getNotification().extras.getString("android.substName");
                        if (string2 == null) {
                            string2 = applicationInfo2 != null ? context.getPackageManager().getApplicationLabel(applicationInfo2).toString() : statusBarNotification2.getPackageName();
                        }
                        final Ref$ObjectRef<CharSequence> ref$ObjectRef3 = new Ref$ObjectRef();
                        ?? string3 = metadata != null ? metadata.getString("android.media.metadata.DISPLAY_TITLE") : 0;
                        ref$ObjectRef3.element = string3;
                        if (string3 == 0 || StringsKt__StringsJVMKt.isBlank(string3)) {
                            ref$ObjectRef3.element = metadata != null ? metadata.getString("android.media.metadata.TITLE") : 0;
                        }
                        CharSequence charSequence = (CharSequence) ref$ObjectRef3.element;
                        if (charSequence == null || StringsKt__StringsJVMKt.isBlank(charSequence)) {
                            CharSequence charSequence2 = notification3.extras.getCharSequence("android.title");
                            ?? r11 = charSequence2;
                            if (charSequence2 == null) {
                                r11 = notification3.extras.getCharSequence("android.title.big");
                            }
                            ref$ObjectRef3.element = r11;
                        }
                        CharSequence charSequence3 = (CharSequence) ref$ObjectRef3.element;
                        if (charSequence3 == null || StringsKt__StringsJVMKt.isBlank(charSequence3)) {
                            ref$ObjectRef3.element = context.getString(com.android.systemui.R.string.controls_media_empty_title, string2);
                            try {
                                token = token2;
                            } catch (RuntimeException unused) {
                                token = token2;
                            }
                            try {
                                mediaDataManager.statusBarManager.logBlankMediaTitle(statusBarNotification2.getPackageName(), statusBarNotification2.getUser().getIdentifier());
                            } catch (RuntimeException unused2) {
                                Log.e("MediaDataManager", "Error reporting blank media title for package " + statusBarNotification2.getPackageName());
                                if (metadata != null) {
                                }
                                bitmap = null;
                                if (bitmap == null) {
                                }
                                if (bitmap == null) {
                                }
                                if (bitmap != null) {
                                }
                                Icon smallIcon2 = statusBarNotification2.getNotification().getSmallIcon();
                                Ref$BooleanRef ref$BooleanRef22 = new Ref$BooleanRef();
                                final Icon icon32 = largeIcon;
                                mediaFlags = mediaDataManager.mediaFlags;
                                mediaFlags.getClass();
                                Flags.INSTANCE.getClass();
                                if (((FeatureFlagsRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_EXPLICIT_INDICATOR)) {
                                }
                                Ref$ObjectRef ref$ObjectRef42 = new Ref$ObjectRef();
                                if (metadata == null) {
                                }
                                ref$ObjectRef42.element = string;
                                if (string != 0 || StringsKt__StringsJVMKt.isBlank(string)) {
                                }
                                Ref$ObjectRef ref$ObjectRef52 = new Ref$ObjectRef();
                                if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                                }
                                final Ref$ObjectRef<List<MediaAction>> ref$ObjectRef62 = new Ref$ObjectRef();
                                ?? r63 = EmptyList.INSTANCE;
                                ref$ObjectRef62.element = r63;
                                final Ref$ObjectRef<List<Integer>> ref$ObjectRef72 = new Ref$ObjectRef();
                                ref$ObjectRef72.element = r63;
                                createActionsFromState = mediaDataManager.createActionsFromState(statusBarNotification2.getPackageName(), mediaController, statusBarNotification2.getUser());
                                if (createActionsFromState != null) {
                                }
                                Log.d("MediaDataManager", "semanticActions=" + createActionsFromState);
                                Log.d("MediaDataManager", "actionsToShowCollapsed=" + ref$ObjectRef72.element + ", actionIcons=" + ref$ObjectRef62.element);
                                if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                                }
                                final int i42 = i;
                                PlaybackState playbackState2 = mediaController.getPlaybackState();
                                final Boolean valueOf2 = playbackState2 == null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState2.getState())) : null;
                                final String str82 = str4;
                                mediaData = (MediaData) mediaDataManager.mediaEntries.get(str82);
                                mediaUiEventLogger = mediaDataManager.logger;
                                if (mediaData != null) {
                                }
                                InstanceId newInstanceId2 = mediaUiEventLogger.getNewInstanceId();
                                final InstanceId instanceId2 = newInstanceId2;
                                final int i52 = applicationInfo == null ? applicationInfo.uid : -1;
                                if (!z3) {
                                }
                                ((SystemClockImpl) mediaDataManager.systemClock).getClass();
                                final long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                                final String str92 = str5;
                                final String str102 = str3;
                                final Icon icon42 = icon;
                                final Ref$ObjectRef<CharSequence> ref$ObjectRef82 = ref$ObjectRef;
                                final Ref$ObjectRef<MediaDeviceData> ref$ObjectRef92 = ref$ObjectRef2;
                                final Ref$BooleanRef ref$BooleanRef32 = ref$BooleanRef;
                                final MediaSession.Token token32 = token;
                                final Notification notification42 = notification2;
                                ((ExecutorImpl) mediaDataManager.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBg$2
                                    /* JADX WARN: Multi-variable type inference failed */
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MediaData mediaData2 = (MediaData) MediaDataManager.this.mediaEntries.get(str82);
                                        Runnable runnable2 = mediaData2 != null ? mediaData2.resumeAction : null;
                                        MediaData mediaData3 = (MediaData) MediaDataManager.this.mediaEntries.get(str82);
                                        boolean z6 = mediaData3 != null && mediaData3.hasCheckedForResume;
                                        MediaData mediaData4 = (MediaData) MediaDataManager.this.mediaEntries.get(str82);
                                        MediaDataManager.this.onMediaDataLoaded(str82, str92, new MediaData(statusBarNotification2.getNormalizedUserId(), true, str102, icon42, (CharSequence) ref$ObjectRef82.element, (CharSequence) ref$ObjectRef3.element, icon32, (List) ref$ObjectRef62.element, (List) ref$ObjectRef72.element, createActionsFromState, statusBarNotification2.getPackageName(), token32, notification42.contentIntent, (MediaDeviceData) ref$ObjectRef92.element, mediaData4 != null ? mediaData4.active : true, runnable2, i42, false, str82, z6, valueOf2, !statusBarNotification2.isOngoing(), elapsedRealtime2, instanceId2, i52, ref$BooleanRef32.element, null, null, 201457664, null));
                                    }
                                });
                            }
                        } else {
                            token = token2;
                        }
                        if (metadata != null) {
                            String[] strArr3 = MediaDataManagerKt.ART_URIS;
                            int i6 = 0;
                            while (i6 < 3) {
                                String str11 = strArr3[i6];
                                String string4 = metadata.getString(str11);
                                if (!TextUtils.isEmpty(string4)) {
                                    strArr2 = strArr3;
                                    bitmap = mediaDataManager.loadBitmapFromUri(Uri.parse(string4));
                                    if (bitmap != null) {
                                        AbstractC0000x2c234b15.m3m("loaded art from ", str11, "MediaDataManager");
                                        break;
                                    }
                                } else {
                                    strArr2 = strArr3;
                                }
                                i6++;
                                strArr3 = strArr2;
                            }
                        }
                        bitmap = null;
                        if (bitmap == null) {
                            bitmap = metadata != null ? metadata.getBitmap("android.media.metadata.ART") : null;
                        }
                        if (bitmap == null) {
                            bitmap = metadata != null ? metadata.getBitmap("android.media.metadata.ALBUM_ART") : null;
                        }
                        Icon largeIcon = bitmap != null ? notification3.getLargeIcon() : Icon.createWithBitmap(bitmap);
                        Icon smallIcon22 = statusBarNotification2.getNotification().getSmallIcon();
                        Ref$BooleanRef ref$BooleanRef222 = new Ref$BooleanRef();
                        final Icon icon322 = largeIcon;
                        mediaFlags = mediaDataManager.mediaFlags;
                        mediaFlags.getClass();
                        Flags.INSTANCE.getClass();
                        if (((FeatureFlagsRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_EXPLICIT_INDICATOR)) {
                            str3 = string2;
                            icon = smallIcon22;
                        } else {
                            MediaMetadataCompat fromMediaMetadata = MediaMetadataCompat.fromMediaMetadata(metadata);
                            if (fromMediaMetadata != null) {
                                icon = smallIcon22;
                                str3 = string2;
                                if (fromMediaMetadata.mBundle.getLong("android.media.IS_EXPLICIT", 0L) == 1) {
                                    z4 = true;
                                    ref$BooleanRef222.element = z4;
                                }
                            } else {
                                str3 = string2;
                                icon = smallIcon22;
                            }
                            z4 = false;
                            ref$BooleanRef222.element = z4;
                        }
                        Ref$ObjectRef ref$ObjectRef422 = new Ref$ObjectRef();
                        string = metadata == null ? metadata.getString("android.media.metadata.ARTIST") : 0;
                        ref$ObjectRef422.element = string;
                        if (string != 0 || StringsKt__StringsJVMKt.isBlank(string)) {
                            CharSequence charSequence4 = notification3.extras.getCharSequence("android.text");
                            ?? r62 = charSequence4;
                            if (charSequence4 == null) {
                                r62 = notification3.extras.getCharSequence("android.bigText");
                            }
                            ref$ObjectRef422.element = r62;
                        }
                        Ref$ObjectRef ref$ObjectRef522 = new Ref$ObjectRef();
                        if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                            ref$ObjectRef = ref$ObjectRef422;
                            ref$BooleanRef = ref$BooleanRef222;
                            notification2 = notification3;
                        } else {
                            Bundle bundle = statusBarNotification2.getNotification().extras;
                            CharSequence charSequence5 = bundle.getCharSequence("android.mediaRemoteDevice", null);
                            ref$BooleanRef = ref$BooleanRef222;
                            notification2 = notification3;
                            int i7 = bundle.getInt("android.mediaRemoteIcon", -1);
                            ref$ObjectRef = ref$ObjectRef422;
                            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
                            Log.d("MediaDataManager", str6 + " is RCN for " + ((Object) charSequence5));
                            if (charSequence5 != null && i7 > -1) {
                                ref$ObjectRef522.element = new MediaDeviceData(pendingIntent != null && pendingIntent.isActivity(), Icon.createWithResource(statusBarNotification2.getPackageName(), i7).loadDrawable(statusBarNotification2.getPackageContext(context)), charSequence5, pendingIntent, null, false, null, 80, null);
                            }
                        }
                        final Ref$ObjectRef<List<MediaAction>> ref$ObjectRef622 = new Ref$ObjectRef();
                        ?? r632 = EmptyList.INSTANCE;
                        ref$ObjectRef622.element = r632;
                        final Ref$ObjectRef<List<Integer>> ref$ObjectRef722 = new Ref$ObjectRef();
                        ref$ObjectRef722.element = r632;
                        createActionsFromState = mediaDataManager.createActionsFromState(statusBarNotification2.getPackageName(), mediaController, statusBarNotification2.getUser());
                        if (createActionsFromState != null) {
                            Notification notification5 = statusBarNotification2.getNotification();
                            ArrayList arrayList3 = new ArrayList();
                            ref$ObjectRef2 = ref$ObjectRef522;
                            Notification.Action[] actionArr = notification5.actions;
                            str5 = str7;
                            int[] intArray = notification5.extras.getIntArray("android.compactActions");
                            if (intArray != null) {
                                z3 = z5;
                                arrayList = new ArrayList(intArray.length);
                                applicationInfo = applicationInfo2;
                                int i8 = 0;
                                for (int length = intArray.length; i8 < length; length = length) {
                                    arrayList.add(Integer.valueOf(intArray[i8]));
                                    i8++;
                                }
                            } else {
                                applicationInfo = applicationInfo2;
                                z3 = z5;
                                arrayList = new ArrayList();
                            }
                            int size = arrayList.size();
                            int i9 = MediaDataManager.MAX_COMPACT_ACTIONS;
                            if (size > i9) {
                                str4 = str6;
                                Log.e("MediaDataManager", "Too many compact actions for " + statusBarNotification2.getKey() + ",limiting to first " + i9);
                                i2 = 0;
                                arrayList2 = arrayList.subList(0, i9);
                            } else {
                                str4 = str6;
                                i2 = 0;
                                arrayList2 = arrayList;
                            }
                            if (actionArr != null) {
                                int length2 = actionArr.length;
                                while (true) {
                                    if (i2 >= length2) {
                                        break;
                                    }
                                    final Notification.Action action = actionArr[i2];
                                    int i10 = MediaDataManager.MAX_NOTIFICATION_ACTIONS;
                                    if (i2 == i10) {
                                        Log.w("MediaDataManager", "Too many notification actions for " + statusBarNotification2.getKey() + ", limiting to first " + i10);
                                        break;
                                    }
                                    if (action.getIcon() == null) {
                                        i3 = length2;
                                        Log.i("MediaDataManager", "No icon for action " + i2 + " " + ((Object) action.title));
                                        arrayList2.remove(Integer.valueOf(i2));
                                    } else {
                                        i3 = length2;
                                        Runnable runnable = action.actionIntent != null ? new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                if (action.actionIntent.isActivity()) {
                                                    mediaDataManager.activityStarter.startPendingIntentDismissingKeyguard(action.actionIntent);
                                                    return;
                                                }
                                                if (!action.isAuthenticationRequired()) {
                                                    MediaDataManager.access$sendPendingIntent(mediaDataManager, action.actionIntent);
                                                    return;
                                                }
                                                final MediaDataManager mediaDataManager2 = mediaDataManager;
                                                ActivityStarter activityStarter = mediaDataManager2.activityStarter;
                                                final Notification.Action action2 = action;
                                                activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.1
                                                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                                    public final boolean onDismiss() {
                                                        return MediaDataManager.access$sendPendingIntent(MediaDataManager.this, action2.actionIntent);
                                                    }
                                                }, new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.2
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                    }
                                                }, true);
                                            }
                                        } : null;
                                        Icon icon5 = action.getIcon();
                                        if (icon5 != null && icon5.getType() == 2) {
                                            String packageName3 = statusBarNotification2.getPackageName();
                                            Icon icon6 = action.getIcon();
                                            Intrinsics.checkNotNull(icon6);
                                            icon2 = Icon.createWithResource(packageName3, icon6.getResId());
                                        } else {
                                            icon2 = action.getIcon();
                                        }
                                        arrayList3.add(new MediaAction(icon2.setTint(mediaDataManager.themeText).loadDrawable(context), runnable, action.title, null, null, 16, null));
                                    }
                                    i2++;
                                    length2 = i3;
                                }
                            }
                            Pair pair = new Pair(arrayList3, arrayList2);
                            ref$ObjectRef622.element = pair.getFirst();
                            ref$ObjectRef722.element = pair.getSecond();
                        } else {
                            applicationInfo = applicationInfo2;
                            z3 = z5;
                            str4 = str6;
                            str5 = str7;
                            ref$ObjectRef2 = ref$ObjectRef522;
                            Pair generateActionsFromSemantic = MediaDataManager.generateActionsFromSemantic(createActionsFromState);
                            ref$ObjectRef622.element = generateActionsFromSemantic.getFirst();
                            ref$ObjectRef722.element = generateActionsFromSemantic.getSecond();
                        }
                        Log.d("MediaDataManager", "semanticActions=" + createActionsFromState);
                        Log.d("MediaDataManager", "actionsToShowCollapsed=" + ref$ObjectRef722.element + ", actionIcons=" + ref$ObjectRef622.element);
                        if (statusBarNotification2.getNotification().extras.containsKey("android.mediaRemoteDevice")) {
                            MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
                            i = playbackInfo != null && playbackInfo.getPlaybackType() == 1 ? 0 : 1;
                        } else {
                            i = 2;
                        }
                        final int i422 = i;
                        PlaybackState playbackState22 = mediaController.getPlaybackState();
                        final Boolean valueOf22 = playbackState22 == null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState22.getState())) : null;
                        final String str822 = str4;
                        mediaData = (MediaData) mediaDataManager.mediaEntries.get(str822);
                        mediaUiEventLogger = mediaDataManager.logger;
                        if (mediaData != null || (newInstanceId2 = mediaData.instanceId) == null) {
                            InstanceId newInstanceId22 = mediaUiEventLogger.getNewInstanceId();
                        }
                        final InstanceId instanceId22 = newInstanceId22;
                        final int i522 = applicationInfo == null ? applicationInfo.uid : -1;
                        if (!z3) {
                            mediaDataManager.logSingleVsMultipleMediaAdded(i522, statusBarNotification2.getPackageName(), instanceId22);
                            String packageName4 = statusBarNotification2.getPackageName();
                            mediaUiEventLogger.getClass();
                            if (i422 == 0) {
                                mediaUiEvent2 = MediaUiEvent.LOCAL_MEDIA_ADDED;
                            } else if (i422 == 1) {
                                mediaUiEvent2 = MediaUiEvent.CAST_MEDIA_ADDED;
                            } else {
                                if (i422 != 2) {
                                    throw new IllegalArgumentException("Unknown playback location");
                                }
                                mediaUiEvent2 = MediaUiEvent.REMOTE_MEDIA_ADDED;
                            }
                            mediaUiEventLogger.logger.logWithInstanceId(mediaUiEvent2, i522, packageName4, instanceId22);
                        } else if (!(mediaData != null && i422 == mediaData.playbackLocation)) {
                            String packageName5 = statusBarNotification2.getPackageName();
                            mediaUiEventLogger.getClass();
                            if (i422 == 0) {
                                mediaUiEvent = MediaUiEvent.TRANSFER_TO_LOCAL;
                            } else if (i422 == 1) {
                                mediaUiEvent = MediaUiEvent.TRANSFER_TO_CAST;
                            } else {
                                if (i422 != 2) {
                                    throw new IllegalArgumentException("Unknown playback location");
                                }
                                mediaUiEvent = MediaUiEvent.TRANSFER_TO_REMOTE;
                            }
                            mediaUiEventLogger.logger.logWithInstanceId(mediaUiEvent, i522, packageName5, instanceId22);
                        }
                        ((SystemClockImpl) mediaDataManager.systemClock).getClass();
                        final long elapsedRealtime22 = android.os.SystemClock.elapsedRealtime();
                        final String str922 = str5;
                        final String str1022 = str3;
                        final Icon icon422 = icon;
                        final Ref$ObjectRef<CharSequence> ref$ObjectRef822 = ref$ObjectRef;
                        final Ref$ObjectRef<MediaDeviceData> ref$ObjectRef922 = ref$ObjectRef2;
                        final Ref$BooleanRef ref$BooleanRef322 = ref$BooleanRef;
                        final MediaSession.Token token322 = token;
                        final Notification notification422 = notification2;
                        ((ExecutorImpl) mediaDataManager.foregroundExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBg$2
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaData mediaData2 = (MediaData) MediaDataManager.this.mediaEntries.get(str822);
                                Runnable runnable2 = mediaData2 != null ? mediaData2.resumeAction : null;
                                MediaData mediaData3 = (MediaData) MediaDataManager.this.mediaEntries.get(str822);
                                boolean z6 = mediaData3 != null && mediaData3.hasCheckedForResume;
                                MediaData mediaData4 = (MediaData) MediaDataManager.this.mediaEntries.get(str822);
                                MediaDataManager.this.onMediaDataLoaded(str822, str922, new MediaData(statusBarNotification2.getNormalizedUserId(), true, str1022, icon422, (CharSequence) ref$ObjectRef822.element, (CharSequence) ref$ObjectRef3.element, icon322, (List) ref$ObjectRef622.element, (List) ref$ObjectRef722.element, createActionsFromState, statusBarNotification2.getPackageName(), token322, notification422.contentIntent, (MediaDeviceData) ref$ObjectRef922.element, mediaData4 != null ? mediaData4.active : true, runnable2, i422, false, str822, z6, valueOf22, !statusBarNotification2.isOngoing(), elapsedRealtime22, instanceId22, i522, ref$BooleanRef322.element, null, null, 201457664, null));
                            }
                        });
                    }
                });
                return;
            }
        }
        onNotificationRemoved(str);
    }

    public final void onNotificationRemoved(String str) {
        boolean z;
        Assert.isMainThread();
        MediaData mediaData = (MediaData) this.mediaEntries.remove(str);
        if (mediaData == null) {
            return;
        }
        boolean z2 = mediaData.playbackLocation == 0;
        MediaFlags mediaFlags = this.mediaFlags;
        if (z2) {
            z = true;
        } else {
            mediaFlags.getClass();
            Flags.INSTANCE.getClass();
            mediaFlags.featureFlags.getClass();
            z = false;
        }
        if (this.useMediaResumption && mediaData.resumeAction != null && z) {
            convertToResumePlayer(mediaData, str);
            return;
        }
        mediaFlags.getClass();
        Flags.INSTANCE.getClass();
        mediaFlags.featureFlags.getClass();
        notifyMediaDataRemoved(str);
        this.logger.logMediaRemoved(mediaData.appUid, mediaData.packageName, mediaData.instanceId);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        String str;
        String string;
        if (!this.allowMediaRecommendations) {
            Log.d("MediaDataManager", "Smartspace recommendation is disabled in Settings.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof SmartspaceTarget) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        MediaFlags mediaFlags = this.mediaFlags;
        if (size == 0) {
            if (this.smartspaceMediaData.isActive) {
                Log.d("MediaDataManager", "Set Smartspace media to be inactive for the data update");
                mediaFlags.isPersistentSsCardEnabled();
                SmartspaceMediaData smartspaceMediaData = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
                SmartspaceMediaData smartspaceMediaData2 = this.smartspaceMediaData;
                SmartspaceMediaData copy$default = SmartspaceMediaData.copy$default(smartspaceMediaData, smartspaceMediaData2.targetId, false, null, 0L, smartspaceMediaData2.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
                this.smartspaceMediaData = copy$default;
                notifySmartspaceMediaDataRemoved(copy$default.targetId, false);
                return;
            }
            return;
        }
        if (size != 1) {
            Log.wtf("MediaDataManager", "More than 1 Smartspace Media Update. Resetting the status...");
            notifySmartspaceMediaDataRemoved(this.smartspaceMediaData.targetId, false);
            this.smartspaceMediaData = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
            return;
        }
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) arrayList.get(0);
        if (Intrinsics.areEqual(this.smartspaceMediaData.targetId, smartspaceTarget.getSmartspaceTargetId())) {
            return;
        }
        Log.d("MediaDataManager", "Forwarding Smartspace media update.");
        Intent intent = (smartspaceTarget.getBaseAction() == null || smartspaceTarget.getBaseAction().getExtras() == null) ? null : (Intent) smartspaceTarget.getBaseAction().getExtras().getParcelable("dismiss_intent");
        mediaFlags.isPersistentSsCardEnabled();
        List iconGrid = smartspaceTarget.getIconGrid();
        if (iconGrid == null || iconGrid.isEmpty()) {
            Log.w("MediaDataManager", "Empty or null media recommendation list.");
        } else {
            Iterator it = iconGrid.iterator();
            while (it.hasNext()) {
                Bundle extras = ((SmartspaceAction) it.next()).getExtras();
                if (extras != null && (string = extras.getString(EXTRAS_MEDIA_SOURCE_PACKAGE_NAME)) != null) {
                    str = string;
                    break;
                }
            }
            Log.w("MediaDataManager", "No valid package name is provided.");
        }
        str = null;
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        SmartspaceMediaData smartspaceMediaData3 = str != null ? new SmartspaceMediaData(smartspaceTarget.getSmartspaceTargetId(), true, str, smartspaceTarget.getBaseAction(), smartspaceTarget.getIconGrid(), intent, smartspaceTarget.getCreationTimeMillis(), mediaUiEventLogger.getNewInstanceId(), smartspaceTarget.getExpiryTimeMillis()) : SmartspaceMediaData.copy$default(MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceTarget.getSmartspaceTargetId(), true, intent, smartspaceTarget.getCreationTimeMillis(), mediaUiEventLogger.getNewInstanceId(), smartspaceTarget.getExpiryTimeMillis(), 28);
        this.smartspaceMediaData = smartspaceMediaData3;
        Iterator it2 = this.internalListeners.iterator();
        while (it2.hasNext()) {
            ((Listener) it2.next()).onSmartspaceMediaDataLoaded(smartspaceMediaData3.targetId, smartspaceMediaData3);
        }
    }

    public MediaDataManager(Context context, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilter mediaDataFilter, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, SystemClock systemClock, TunerService tunerService, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this(context, executor, executor2, delayableExecutor, mediaControllerFactory, broadcastDispatcher, dumpManager, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, mediaDataFilter, activityStarter, smartspaceMediaDataProvider, com.android.systemui.util.Utils.useMediaResumption(context), com.android.systemui.util.Utils.useQsMediaPlayer(context), systemClock, tunerService, mediaFlags, mediaUiEventLogger, smartspaceManager, keyguardUpdateMonitor);
    }
}
