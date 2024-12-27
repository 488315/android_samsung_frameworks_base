package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.UriGrantsManager;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
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
import android.media.MediaDescription;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener$getResumeAction$1;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaDataProvider;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.time.SystemClock;
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
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;
import kotlin.text.StringsKt__StringsJVMKt;

public final class LegacyMediaDataManagerImpl implements Dumpable, BcSmartspaceDataPlugin.SmartspaceTargetListener, MediaDataManager {
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
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final MediaUiEventLogger logger;
    public final MediaControllerFactory mediaControllerFactory;
    public final LegacyMediaDataFilterImpl mediaDataFilter;
    public final MediaDeviceManager mediaDeviceManager;
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        SMARTSPACE_UI_SURFACE_LABEL = BcSmartspaceDataPlugin.UI_SURFACE_MEDIA;
        EXTRAS_MEDIA_SOURCE_PACKAGE_NAME = "package_name";
        MAX_COMPACT_ACTIONS = 3;
        MediaViewHolder.Companion.getClass();
        MAX_NOTIFICATION_ACTIONS = MediaViewHolder.genericButtonIds.size();
    }

    public LegacyMediaDataManagerImpl(Context context, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, MediaTimeoutListener mediaTimeoutListener, final MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, LegacyMediaDataFilterImpl legacyMediaDataFilterImpl, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, boolean z, boolean z2, SystemClock systemClock, TunerService tunerService, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.context = context;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.foregroundExecutor = delayableExecutor;
        this.mediaControllerFactory = mediaControllerFactory;
        this.mediaDeviceManager = mediaDeviceManager;
        this.mediaDataFilter = legacyMediaDataFilterImpl;
        this.activityStarter = activityStarter;
        this.smartspaceMediaDataProvider = smartspaceMediaDataProvider;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.themeText = Utils.getColorAttr(R.attr.textColorPrimary, context).getDefaultColor();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.internalListeners = linkedHashSet;
        this.mediaEntries = new LinkedHashMap();
        this.smartspaceMediaData = LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA;
        this.allowMediaRecommendations = com.android.systemui.util.Utils.useQsMediaPlayer(context) && Settings.Secure.getInt(context.getContentResolver(), "qs_media_recommend", 1) > 0;
        this.artworkWidth = context.getResources().getDimensionPixelSize(R.dimen.config_scrollbarSize);
        this.artworkHeight = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_media_session_height_expanded);
        this.statusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$appChangeReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String[] stringArrayExtra;
                String encodedSchemeSpecificPart;
                String action = intent.getAction();
                if (action != null) {
                    int hashCode = action.hashCode();
                    if (hashCode != -1001645458) {
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
                        LegacyMediaDataManagerImpl.access$removeAllForPackage(LegacyMediaDataManagerImpl.this, encodedSchemeSpecificPart);
                        return;
                    }
                    if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                        for (String str : stringArrayExtra) {
                            Intrinsics.checkNotNull(str);
                            LegacyMediaDataManagerImpl.access$removeAllForPackage(legacyMediaDataManagerImpl, str);
                        }
                    }
                }
            }
        };
        DumpManager.registerDumpable$default(dumpManager, "MediaDataManager", this);
        linkedHashSet.add(mediaTimeoutListener);
        linkedHashSet.add(mediaResumeListener);
        linkedHashSet.add(mediaSessionBasedFilter);
        mediaSessionBasedFilter.listeners.add(mediaDeviceManager);
        mediaSessionBasedFilter.listeners.add(mediaDataCombineLatest);
        mediaDeviceManager.listeners.add(mediaDataCombineLatest);
        mediaDataCombineLatest.listeners.add(legacyMediaDataFilterImpl);
        mediaTimeoutListener.timeoutCallback = new Function2() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                MediaDataManager.Companion companion = MediaDataManager.Companion;
                legacyMediaDataManagerImpl.setInactive((String) obj, booleanValue, false);
                return Unit.INSTANCE;
            }
        };
        mediaTimeoutListener.stateCallback = new Function2() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                String str;
                MediaData copy$default;
                String str2 = (String) obj;
                PlaybackState playbackState = (PlaybackState) obj2;
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                MediaData mediaData = (MediaData) legacyMediaDataManagerImpl.mediaEntries.get(str2);
                if (mediaData != null) {
                    MediaSession.Token token = mediaData.token;
                    if (token == null) {
                        Log.d("MediaDataManager", "State updated, but token was null");
                    } else {
                        MediaButton createActionsFromState$1 = legacyMediaDataManagerImpl.createActionsFromState$1(mediaData.packageName, legacyMediaDataManagerImpl.mediaControllerFactory.create(token), new UserHandle(mediaData.userId));
                        if (createActionsFromState$1 != null) {
                            EmptyList emptyList = EmptyList.INSTANCE;
                            Pair generateActionsFromSemantic$1 = LegacyMediaDataManagerImpl.generateActionsFromSemantic$1(createActionsFromState$1);
                            MediaData copy$default2 = MediaData.copy$default(mediaData, (List) generateActionsFromSemantic$1.getFirst(), (List) generateActionsFromSemantic$1.getSecond(), createActionsFromState$1, null, null, null, false, null, false, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, 0L, 0L, null, 0, 267385983);
                            str = "MediaDataManager";
                            copy$default = copy$default2;
                        } else {
                            str = "MediaDataManager";
                            copy$default = MediaData.copy$default(mediaData, null, null, null, null, null, null, false, null, false, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, 0L, 0L, null, 0, 267386879);
                        }
                        Log.d(str, "State updated outside of notification");
                        legacyMediaDataManagerImpl.onMediaDataLoaded(str2, str2, copy$default);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        mediaTimeoutListener.sessionCallback = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String str = (String) obj;
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                String str2 = LegacyMediaDataManagerImpl.SMARTSPACE_UI_SURFACE_LABEL;
                legacyMediaDataManagerImpl.getClass();
                Log.d("MediaDataManager", "session destroyed for ".concat(str));
                MediaData mediaData = (MediaData) legacyMediaDataManagerImpl.mediaEntries.remove(str);
                if (mediaData != null) {
                    MediaData copy$default = MediaData.copy$default(mediaData, null, null, null, null, null, null, false, null, false, false, null, false, 0L, 0L, null, 0, 268433407);
                    boolean z3 = copy$default.token != null;
                    MediaButton mediaButton = copy$default.semanticActions;
                    if (z3 && mediaButton != null) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Notification removed but using session actions ", str, "MediaDataManager");
                        legacyMediaDataManagerImpl.mediaEntries.put(str, copy$default);
                        legacyMediaDataManagerImpl.notifyMediaDataLoaded$1(str, str, copy$default);
                    } else if (mediaButton == null) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Session destroyed but using notification actions ", str, "MediaDataManager");
                        legacyMediaDataManagerImpl.mediaEntries.put(str, copy$default);
                        legacyMediaDataManagerImpl.notifyMediaDataLoaded$1(str, str, copy$default);
                    } else {
                        boolean z4 = copy$default.active;
                        MediaUiEventLogger mediaUiEventLogger2 = legacyMediaDataManagerImpl.logger;
                        String str3 = copy$default.packageName;
                        int i = copy$default.appUid;
                        if (!z4 || legacyMediaDataManagerImpl.isAbleToResume$1(copy$default)) {
                            MediaFlags mediaFlags2 = legacyMediaDataManagerImpl.mediaFlags;
                            mediaFlags2.getClass();
                            Flags.INSTANCE.getClass();
                            mediaFlags2.featureFlags.getClass();
                            if (legacyMediaDataManagerImpl.isAbleToResume$1(copy$default)) {
                                Log.d("MediaDataManager", "Notification (false) and/or session (" + z3 + ") gone for inactive player " + str);
                                legacyMediaDataManagerImpl.convertToResumePlayer$1(copy$default, str);
                            } else {
                                Log.d("MediaDataManager", "Removing player " + str);
                                LegacyMediaDataManagerImpl.notifyMediaDataRemoved$default(legacyMediaDataManagerImpl, str);
                                mediaUiEventLogger2.logMediaRemoved(i, str3, copy$default.instanceId);
                            }
                        } else {
                            Log.d("MediaDataManager", "Removing still-active player " + str);
                            LegacyMediaDataManagerImpl.notifyMediaDataRemoved$default(legacyMediaDataManagerImpl, str);
                            mediaUiEventLogger2.logMediaRemoved(i, str3, copy$default.instanceId);
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        };
        mediaResumeListener.mediaDataManager = this;
        mediaResumeListener.tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$setManager$1
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                MediaResumeListener mediaResumeListener2 = MediaResumeListener.this;
                mediaResumeListener2.useMediaResumption = com.android.systemui.util.Utils.useMediaResumption(mediaResumeListener2.context);
                MediaDataManager mediaDataManager = mediaResumeListener2.mediaDataManager;
                if (mediaDataManager == null) {
                    mediaDataManager = null;
                }
                mediaDataManager.setMediaResumptionEnabled(mediaResumeListener2.useMediaResumption);
            }
        }, "qs_media_resumption");
        legacyMediaDataFilterImpl.mediaDataManager = this;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, new IntentFilter("android.intent.action.PACKAGES_SUSPENDED"), null, UserHandle.ALL, 0, null, 48);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(broadcastReceiver, intentFilter);
        smartspaceMediaDataProvider.registerListener(this);
        SmartspaceSession createSmartspaceSession = smartspaceManager != null ? smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(context, SMARTSPACE_UI_SURFACE_LABEL).build()) : null;
        this.smartspaceSession = createSmartspaceSession;
        if (createSmartspaceSession != null) {
            createSmartspaceSession.addOnTargetsAvailableListener(executor2, new SmartspaceSession.OnTargetsAvailableListener() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$4$1
                public final void onTargetsAvailable(List list) {
                    LegacyMediaDataManagerImpl.this.smartspaceMediaDataProvider.onTargetsAvailable(list);
                }
            });
        }
        SmartspaceSession smartspaceSession = this.smartspaceSession;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
        tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.6
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                Context context2 = legacyMediaDataManagerImpl.context;
                String[] strArr = LegacyMediaDataManagerImplKt.ART_URIS;
                boolean z3 = com.android.systemui.util.Utils.useQsMediaPlayer(context2) && Settings.Secure.getInt(context2.getContentResolver(), "qs_media_recommend", 1) > 0;
                legacyMediaDataManagerImpl.allowMediaRecommendations = z3;
                if (z3) {
                    return;
                }
                legacyMediaDataManagerImpl.dismissSmartspaceRecommendation(legacyMediaDataManagerImpl.smartspaceMediaData.targetId, 0L);
            }
        }, "qs_media_recommend");
    }

    public static final void access$removeAllForPackage(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str) {
        legacyMediaDataManagerImpl.getClass();
        Assert.isMainThread();
        LinkedHashMap linkedHashMap = legacyMediaDataManagerImpl.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).packageName, str)) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap2.entrySet().iterator();
        while (it.hasNext()) {
            removeEntry$default(legacyMediaDataManagerImpl, (String) ((Map.Entry) it.next()).getKey(), false, 6);
        }
    }

    public static final boolean access$sendPendingIntent(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, PendingIntent pendingIntent) {
        legacyMediaDataManagerImpl.getClass();
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setInteractive(true);
            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(makeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException e) {
            Log.d("MediaDataManager", "Intent canceled", e);
            return false;
        }
    }

    public static final MediaAction createActionsFromState$nextCustomAction(TransformingSequence$iterator$1 transformingSequence$iterator$1) {
        if (transformingSequence$iterator$1.iterator.hasNext()) {
            return (MediaAction) transformingSequence$iterator$1.next();
        }
        return null;
    }

    public static Pair generateActionsFromSemantic$1(MediaButton mediaButton) {
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

    public static void notifyMediaDataRemoved$default(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str) {
        Iterator it = legacyMediaDataManagerImpl.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, false);
        }
    }

    public static void removeEntry$default(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, boolean z, int i) {
        if ((i & 4) != 0) {
            z = false;
        }
        MediaData mediaData = (MediaData) legacyMediaDataManagerImpl.mediaEntries.remove(str);
        if (mediaData != null) {
            InstanceId instanceId = mediaData.instanceId;
            legacyMediaDataManagerImpl.logger.logMediaRemoved(mediaData.appUid, mediaData.packageName, instanceId);
        }
        Iterator it = legacyMediaDataManagerImpl.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void addListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.add(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void addResumptionControls(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, final String str2) {
        int i2;
        InstanceId instanceId;
        MediaUiEventLogger mediaUiEventLogger;
        if (!this.mediaEntries.containsKey(str2)) {
            MediaUiEventLogger mediaUiEventLogger2 = this.logger;
            InstanceId newInstanceId = mediaUiEventLogger2.instanceIdSequence.newInstanceId();
            try {
                ApplicationInfo applicationInfo = this.context.getPackageManager().getApplicationInfo(str2, 0);
                Integer valueOf = applicationInfo != null ? Integer.valueOf(applicationInfo.uid) : null;
                Intrinsics.checkNotNull(valueOf);
                i2 = valueOf.intValue();
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaDataManager", "Could not get app UID for ".concat(str2), e);
                i2 = -1;
            }
            this.mediaEntries.put(str2, MediaData.copy$default(LegacyMediaDataManagerImplKt.LOADING, null, null, null, str2, null, null, false, (MediaResumeListener$getResumeAction$1) runnable, false, true, null, false, 0L, this.systemClock.currentTimeMillis(), newInstanceId, i2, 209157119));
            if (this.mediaEntries.size() == 1) {
                mediaUiEventLogger = mediaUiEventLogger2;
                instanceId = newInstanceId;
                mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_SINGLE_PLAYER, i2, str2, instanceId);
            } else {
                instanceId = newInstanceId;
                mediaUiEventLogger = mediaUiEventLogger2;
                if (this.mediaEntries.size() == 2) {
                    mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_MULTIPLE_PLAYERS, i2, str2, instanceId);
                }
            }
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.RESUME_MEDIA_ADDED, i2, str2, instanceId);
        }
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$addResumptionControls$1
            @Override // java.lang.Runnable
            public final void run() {
                InstanceId newInstanceId2;
                final LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                final int i3 = i;
                final MediaDescription mediaDescription2 = mediaDescription;
                final Runnable runnable2 = runnable;
                final MediaSession.Token token2 = token;
                final String str3 = str;
                final PendingIntent pendingIntent2 = pendingIntent;
                final String str4 = str2;
                String str5 = LegacyMediaDataManagerImpl.SMARTSPACE_UI_SURFACE_LABEL;
                legacyMediaDataManagerImpl.getClass();
                CharSequence title = mediaDescription2.getTitle();
                if (title == null || StringsKt__StringsJVMKt.isBlank(title)) {
                    Log.e("MediaDataManager", "Description incomplete");
                    legacyMediaDataManagerImpl.mediaEntries.remove(str4);
                    return;
                }
                Log.d("MediaDataManager", "adding track for " + i3 + " from browser: " + mediaDescription2);
                MediaData mediaData = (MediaData) legacyMediaDataManagerImpl.mediaEntries.get(str4);
                final int i4 = mediaData != null ? mediaData.appUid : -1;
                Bitmap iconBitmap = mediaDescription2.getIconBitmap();
                if (iconBitmap == null && mediaDescription2.getIconUri() != null) {
                    Uri iconUri = mediaDescription2.getIconUri();
                    Intrinsics.checkNotNull(iconUri);
                    try {
                        UriGrantsManager.getService().checkGrantUriPermission_ignoreNonSystem(i4, str4, ContentProvider.getUriWithoutUserId(iconUri), 1, ContentProvider.getUserIdFromUri(iconUri, i3));
                        iconBitmap = legacyMediaDataManagerImpl.loadBitmapFromUri$1(iconUri);
                    } catch (SecurityException e2) {
                        Log.e("MediaDataManager", "Failed to get URI permission: " + e2);
                        iconBitmap = null;
                    }
                }
                Icon createWithBitmap = iconBitmap != null ? Icon.createWithBitmap(iconBitmap) : null;
                if (mediaData == null || (newInstanceId2 = mediaData.instanceId) == null) {
                    newInstanceId2 = legacyMediaDataManagerImpl.logger.instanceIdSequence.newInstanceId();
                }
                final InstanceId instanceId2 = newInstanceId2;
                Bundle extras = mediaDescription2.getExtras();
                final boolean z = extras != null && extras.getLong("android.media.IS_EXPLICIT") == 1;
                MediaFlags mediaFlags = legacyMediaDataManagerImpl.mediaFlags;
                mediaFlags.getClass();
                Flags.INSTANCE.getClass();
                final Double descriptionProgress = ((FeatureFlagsClassicRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_RESUME_PROGRESS) ? MediaDataUtils.getDescriptionProgress(mediaDescription2.getExtras()) : null;
                final MediaAction resumeMediaAction$1 = legacyMediaDataManagerImpl.getResumeMediaAction$1(runnable2);
                final long elapsedRealtime = legacyMediaDataManagerImpl.systemClock.elapsedRealtime();
                final long j = mediaData != null ? mediaData.createdTimestampMillis : 0L;
                final Icon icon = createWithBitmap;
                legacyMediaDataManagerImpl.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$loadMediaDataInBgForResumption$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl2 = LegacyMediaDataManagerImpl.this;
                        String str6 = str4;
                        int i5 = i3;
                        String str7 = str3;
                        CharSequence subtitle = mediaDescription2.getSubtitle();
                        CharSequence title2 = mediaDescription2.getTitle();
                        Icon icon2 = icon;
                        List singletonList = Collections.singletonList(resumeMediaAction$1);
                        List singletonList2 = Collections.singletonList(0);
                        MediaButton mediaButton = new MediaButton(resumeMediaAction$1, null, null, null, null, false, false, 126, null);
                        String str8 = str4;
                        legacyMediaDataManagerImpl2.onMediaDataLoaded(str6, null, new MediaData(i5, true, str7, null, subtitle, title2, icon2, singletonList, singletonList2, mediaButton, str8, token2, pendingIntent2, null, false, runnable2, 0, true, str8, true, null, false, elapsedRealtime, j, instanceId2, i4, z, descriptionProgress, 3211264, null));
                    }
                });
            }
        });
    }

    public final void convertToResumePlayer$1(MediaData mediaData, String str) {
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Converting ", str, " to resume", "MediaDataManager");
        CharSequence charSequence = mediaData.song;
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        String str2 = mediaData.packageName;
        if (charSequence == null || StringsKt__StringsJVMKt.isBlank(charSequence)) {
            Log.e("MediaDataManager", "Description incomplete");
            notifyMediaDataRemoved$default(this, str);
            mediaUiEventLogger.logMediaRemoved(mediaData.appUid, str2, mediaData.instanceId);
            return;
        }
        Runnable runnable = mediaData.resumeAction;
        MediaAction resumeMediaAction$1 = runnable != null ? getResumeMediaAction$1(runnable) : null;
        List singletonList = resumeMediaAction$1 != null ? Collections.singletonList(resumeMediaAction$1) : EmptyList.INSTANCE;
        Intent launchIntentForPackage = this.context.getPackageManager().getLaunchIntentForPackage(str2);
        MediaData copy$default = MediaData.copy$default(mediaData, singletonList, Collections.singletonList(0), new MediaButton(resumeMediaAction$1, null, null, null, null, false, false, 126, null), null, launchIntentForPackage != null ? PendingIntent.getActivity(this.context, 0, launchIntentForPackage, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) : null, null, false, null, true, false, Boolean.FALSE, true, mediaData.active ? this.systemClock.elapsedRealtime() : mediaData.lastActive, 0L, null, 0, 260940927);
        boolean z = this.mediaEntries.put(str2, copy$default) == null;
        StringBuilder sb = new StringBuilder("migrating? ");
        sb.append(z);
        sb.append(" from ");
        sb.append(str);
        sb.append(" -> ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "MediaDataManager");
        if (z) {
            notifyMediaDataLoaded$1(str2, str, copy$default);
        } else {
            notifyMediaDataRemoved$default(this, str);
            notifyMediaDataLoaded$1(str2, str2, copy$default);
        }
        mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.ACTIVE_TO_RESUME, copy$default.appUid, str2, copy$default.instanceId);
        LinkedHashMap linkedHashMap = this.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((MediaData) entry.getValue()).resumption) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        int size = linkedHashMap2.size();
        if (size > 5) {
            for (Pair pair : CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap2), new Comparator() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$convertToResumePlayer$$inlined$sortedBy$1
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
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Removing excess control ", str3, "MediaDataManager");
                this.mediaEntries.remove(str3);
                notifyMediaDataRemoved$default(this, str3);
                mediaUiEventLogger.logMediaRemoved(mediaData2.appUid, mediaData2.packageName, mediaData2.instanceId);
            }
        }
    }

    public final MediaButton createActionsFromState$1(final String str, final MediaController mediaController, UserHandle userHandle) {
        boolean z;
        MediaAction standardAction$1;
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
                    Drawable drawable = this.context.getDrawable(R.drawable.spinner_48_inner_holo);
                    ((Animatable) drawable).start();
                    standardAction$1 = new MediaAction(drawable, null, this.context.getString(com.android.systemui.R.string.controls_media_button_connecting), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_connecting_container), Integer.valueOf(R.drawable.spinner_48_inner_holo));
                } else {
                    standardAction$1 = NotificationMediaManager.isPlayingState(playbackState.getState()) ? getStandardAction$1(mediaController, playbackState.getActions(), 2L) : getStandardAction$1(mediaController, playbackState.getActions(), 4L);
                }
                MediaAction mediaAction = standardAction$1;
                MediaAction standardAction$12 = getStandardAction$1(mediaController, playbackState.getActions(), 16L);
                MediaAction standardAction$13 = getStandardAction$1(mediaController, playbackState.getActions(), 32L);
                TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(SequencesKt___SequencesKt.filterNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(playbackState.getCustomActions())), new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$createActionsFromState$customActions$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        final PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) obj;
                        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                        String str2 = str;
                        final MediaController mediaController2 = mediaController;
                        String str3 = LegacyMediaDataManagerImpl.SMARTSPACE_UI_SURFACE_LABEL;
                        legacyMediaDataManagerImpl.getClass();
                        return new MediaAction(Icon.createWithResource(str2, customAction.getIcon()).loadDrawable(legacyMediaDataManagerImpl.context), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$getCustomAction$1
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
                MediaAction createActionsFromState$nextCustomAction2 = standardAction$12 != null ? standardAction$12 : !z2 ? createActionsFromState$nextCustomAction(transformingSequence$iterator$1) : null;
                if (standardAction$13 != null) {
                    createActionsFromState$nextCustomAction = standardAction$13;
                } else {
                    createActionsFromState$nextCustomAction = z3 ? null : createActionsFromState$nextCustomAction(transformingSequence$iterator$1);
                }
                return new MediaButton(mediaAction, createActionsFromState$nextCustomAction, createActionsFromState$nextCustomAction2, createActionsFromState$nextCustomAction(transformingSequence$iterator$1), createActionsFromState$nextCustomAction(transformingSequence$iterator$1), z3, z2);
            }
        }
        return null;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean dismissMediaData(final String str, long j, final boolean z) {
        boolean z2 = this.mediaEntries.get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$dismissMediaData$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSession.Token token;
                MediaData mediaData = (MediaData) LegacyMediaDataManagerImpl.this.mediaEntries.get(str);
                if (mediaData != null) {
                    LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                    if (mediaData.playbackLocation != 0 || (token = mediaData.token) == null) {
                        return;
                    }
                    legacyMediaDataManagerImpl.mediaControllerFactory.create(token).getTransportControls().stop();
                }
            }
        });
        this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$dismissMediaData$2
            @Override // java.lang.Runnable
            public final void run() {
                LegacyMediaDataManagerImpl.removeEntry$default(LegacyMediaDataManagerImpl.this, str, z, 2);
            }
        }, j);
        return z2;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void dismissSmartspaceRecommendation(String str, long j) {
        if (Intrinsics.areEqual(this.smartspaceMediaData.targetId, str) && this.smartspaceMediaData.isValid()) {
            Log.d("MediaDataManager", "Dismissing Smartspace media target");
            SmartspaceMediaData smartspaceMediaData = this.smartspaceMediaData;
            if (smartspaceMediaData.isActive) {
                this.smartspaceMediaData = SmartspaceMediaData.copy$default(LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceMediaData.targetId, false, null, 0L, smartspaceMediaData.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
            }
            this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$dismissSmartspaceRecommendation$1
                @Override // java.lang.Runnable
                public final void run() {
                    LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                    legacyMediaDataManagerImpl.notifySmartspaceMediaDataRemoved$1(legacyMediaDataManagerImpl.smartspaceMediaData.targetId, true);
                }
            }, j);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("internalListeners: " + this.internalListeners);
        printWriter.println("externalListeners: " + CollectionsKt___CollectionsKt.toSet(this.mediaDataFilter._listeners));
        printWriter.println("mediaEntries: " + this.mediaEntries);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("useMediaResumption: ", this.useMediaResumption, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("allowMediaRecommendations: ", this.allowMediaRecommendations, printWriter);
        this.mediaDeviceManager.dump(printWriter);
    }

    public final MediaAction getResumeMediaAction$1(Runnable runnable) {
        return new MediaAction(Icon.createWithResource(this.context, com.android.systemui.R.drawable.ic_media_play).setTint(this.themeText).loadDrawable(this.context), runnable, this.context.getString(com.android.systemui.R.string.controls_media_resume), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null);
    }

    public final MediaAction getStandardAction$1(final MediaController mediaController, long j, long j2) {
        if (((j2 != 4 && j2 != 2) || (j & 512) <= 0) && (j & j2) == 0) {
            return null;
        }
        if (j2 == 4) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_player), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$getStandardAction$1
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().play();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_play), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null);
        }
        if (j2 == 2) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$getStandardAction$2
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().pause();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_pause), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_pause_container), null, 16, null);
        }
        if (j2 == 16) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_preview), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$getStandardAction$3
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToPrevious();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_prev), null, null, 16, null);
        }
        if (j2 == 32) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_next), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$getStandardAction$4
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToNext();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_next), null, null, 16, null);
        }
        return null;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasActiveMediaOrRecommendation() {
        LegacyMediaDataFilterImpl legacyMediaDataFilterImpl = this.mediaDataFilter;
        LinkedHashMap linkedHashMap = legacyMediaDataFilterImpl.userEntries;
        if (!linkedHashMap.isEmpty()) {
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                if (((MediaData) ((Map.Entry) it.next()).getValue()).active) {
                    break;
                }
            }
        }
        SmartspaceMediaData smartspaceMediaData = legacyMediaDataFilterImpl.smartspaceMediaData;
        return smartspaceMediaData.isActive && (smartspaceMediaData.isValid() || legacyMediaDataFilterImpl.reactivatedKey != null);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasAnyMediaOrRecommendation() {
        LegacyMediaDataFilterImpl legacyMediaDataFilterImpl = this.mediaDataFilter;
        legacyMediaDataFilterImpl.mediaFlags.isPersistentSsCardEnabled();
        SmartspaceMediaData smartspaceMediaData = legacyMediaDataFilterImpl.smartspaceMediaData;
        return (legacyMediaDataFilterImpl.userEntries.isEmpty() ^ true) || (smartspaceMediaData.isActive && smartspaceMediaData.isValid());
    }

    public final boolean isAbleToResume$1(MediaData mediaData) {
        boolean z;
        if (mediaData.playbackLocation == 0) {
            z = true;
        } else {
            MediaFlags mediaFlags = this.mediaFlags;
            mediaFlags.getClass();
            Flags.INSTANCE.getClass();
            mediaFlags.featureFlags.getClass();
            z = false;
        }
        return this.useMediaResumption && mediaData.resumeAction != null && z;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean isRecommendationActive() {
        return this.smartspaceMediaData.isActive;
    }

    public final Bitmap loadBitmapFromUri$1(Uri uri) {
        if (uri.getScheme() == null) {
            return null;
        }
        if (!StringsKt__StringsJVMKt.equals(uri.getScheme(), "content", false) && !StringsKt__StringsJVMKt.equals(uri.getScheme(), "android.resource", false) && !StringsKt__StringsJVMKt.equals(uri.getScheme(), "file", false)) {
            return null;
        }
        try {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.context.getContentResolver(), uri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$loadBitmapFromUri$1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    int width = imageInfo.getSize().getWidth();
                    int height = imageInfo.getSize().getHeight();
                    float scaleFactor = MediaDataUtils.getScaleFactor(new android.util.Pair(Integer.valueOf(width), Integer.valueOf(height)), new android.util.Pair(Integer.valueOf(LegacyMediaDataManagerImpl.this.artworkWidth), Integer.valueOf(LegacyMediaDataManagerImpl.this.artworkHeight)));
                    if (scaleFactor != 0.0f && scaleFactor < 1.0f) {
                        imageDecoder.setTargetSize((int) (width * scaleFactor), (int) (scaleFactor * height));
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

    public final void notifyMediaDataLoaded$1(String str, String str2, MediaData mediaData) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 0, false, 56);
        }
    }

    public final void notifySmartspaceMediaDataRemoved$1(String str, boolean z) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaDataManager#onMediaDataLoaded");
        }
        try {
            Assert.isMainThread();
            if (this.mediaEntries.containsKey(str)) {
                this.mediaEntries.put(str, mediaData);
                notifyMediaDataLoaded$1(str, str2, mediaData);
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationAdded(final String str, final StatusBarNotification statusBarNotification) {
        final String str2;
        if (this.useQsMediaPlayer) {
            MediaDataManager.Companion.getClass();
            if (statusBarNotification.getNotification().isMediaNotification()) {
                Assert.isMainThread();
                String packageName = statusBarNotification.getPackageName();
                if (this.mediaEntries.containsKey(str)) {
                    str2 = str;
                } else {
                    if (!this.mediaEntries.containsKey(packageName)) {
                        packageName = null;
                    }
                    str2 = packageName;
                }
                boolean z = true;
                if (str2 == null) {
                    InstanceId newInstanceId = this.logger.instanceIdSequence.newInstanceId();
                    MediaData mediaData = LegacyMediaDataManagerImplKt.LOADING;
                    String packageName2 = statusBarNotification.getPackageName();
                    long currentTimeMillis = this.systemClock.currentTimeMillis();
                    Intrinsics.checkNotNull(packageName2);
                    this.mediaEntries.put(str, MediaData.copy$default(mediaData, null, null, null, packageName2, null, null, false, null, false, false, null, false, 0L, currentTimeMillis, newInstanceId, 0, 243268607));
                } else if (str2.equals(str)) {
                    z = false;
                } else {
                    Object remove = this.mediaEntries.remove(str2);
                    Intrinsics.checkNotNull(remove);
                    this.mediaEntries.put(str, (MediaData) remove);
                }
                final boolean z2 = z;
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$loadMediaData$1
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            Method dump skipped, instructions count: 1121
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$loadMediaData$1.run():void");
                    }
                });
                return;
            }
        }
        onNotificationRemoved(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationRemoved(String str) {
        Assert.isMainThread();
        MediaData mediaData = (MediaData) this.mediaEntries.remove(str);
        if (mediaData == null) {
            return;
        }
        boolean isUserInLockdown = this.keyguardUpdateMonitor.isUserInLockdown(mediaData.userId);
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        String str2 = mediaData.packageName;
        int i = mediaData.appUid;
        if (isUserInLockdown) {
            mediaUiEventLogger.logMediaRemoved(i, str2, mediaData.instanceId);
            return;
        }
        if (isAbleToResume$1(mediaData)) {
            convertToResumePlayer$1(mediaData, str);
            return;
        }
        MediaFlags mediaFlags = this.mediaFlags;
        mediaFlags.getClass();
        Flags.INSTANCE.getClass();
        mediaFlags.featureFlags.getClass();
        notifyMediaDataRemoved$default(this, str);
        mediaUiEventLogger.logMediaRemoved(i, str2, mediaData.instanceId);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        String string;
        String str;
        Bundle extras;
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
                SmartspaceMediaData smartspaceMediaData = LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA;
                SmartspaceMediaData smartspaceMediaData2 = this.smartspaceMediaData;
                SmartspaceMediaData copy$default = SmartspaceMediaData.copy$default(smartspaceMediaData, smartspaceMediaData2.targetId, false, null, 0L, smartspaceMediaData2.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
                this.smartspaceMediaData = copy$default;
                notifySmartspaceMediaDataRemoved$1(copy$default.targetId, false);
                return;
            }
            return;
        }
        if (size != 1) {
            Log.wtf("MediaDataManager", "More than 1 Smartspace Media Update. Resetting the status...");
            notifySmartspaceMediaDataRemoved$1(this.smartspaceMediaData.targetId, false);
            this.smartspaceMediaData = LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA;
            return;
        }
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) arrayList.get(0);
        if (Intrinsics.areEqual(this.smartspaceMediaData.targetId, smartspaceTarget.getSmartspaceTargetId())) {
            return;
        }
        Log.d("MediaDataManager", "Forwarding Smartspace media update.");
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Intent intent = (baseAction == null || (extras = baseAction.getExtras()) == null) ? null : (Intent) extras.getParcelable("dismiss_intent");
        mediaFlags.isPersistentSsCardEnabled();
        List iconGrid = smartspaceTarget.getIconGrid();
        if (iconGrid.isEmpty()) {
            Log.w("MediaDataManager", "Empty or null media recommendation list.");
        } else {
            Iterator it = iconGrid.iterator();
            while (it.hasNext()) {
                Bundle extras2 = ((SmartspaceAction) it.next()).getExtras();
                if (extras2 != null && (string = extras2.getString(EXTRAS_MEDIA_SOURCE_PACKAGE_NAME)) != null) {
                    str = string;
                    break;
                }
            }
            Log.w("MediaDataManager", "No valid package name is provided.");
        }
        str = null;
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        SmartspaceMediaData smartspaceMediaData3 = str != null ? new SmartspaceMediaData(smartspaceTarget.getSmartspaceTargetId(), true, str, smartspaceTarget.getBaseAction(), smartspaceTarget.getIconGrid(), intent, smartspaceTarget.getCreationTimeMillis(), mediaUiEventLogger.instanceIdSequence.newInstanceId(), smartspaceTarget.getExpiryTimeMillis()) : SmartspaceMediaData.copy$default(LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceTarget.getSmartspaceTargetId(), true, intent, smartspaceTarget.getCreationTimeMillis(), mediaUiEventLogger.instanceIdSequence.newInstanceId(), smartspaceTarget.getExpiryTimeMillis(), 28);
        this.smartspaceMediaData = smartspaceMediaData3;
        Iterator it2 = this.internalListeners.iterator();
        while (it2.hasNext()) {
            ((MediaDataManager.Listener) it2.next()).onSmartspaceMediaDataLoaded(smartspaceMediaData3.targetId, smartspaceMediaData3);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onSwipeToDismiss() {
        LegacyMediaDataFilterImpl legacyMediaDataFilterImpl = this.mediaDataFilter;
        legacyMediaDataFilterImpl.getClass();
        Log.d("MediaDataFilter", "Media carousel swiped away");
        Iterator it = CollectionsKt___CollectionsKt.toSet(legacyMediaDataFilterImpl.userEntries.keySet()).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            MediaDataManager mediaDataManager = legacyMediaDataFilterImpl.mediaDataManager;
            if (mediaDataManager != null) {
                r3 = mediaDataManager;
            }
            Intrinsics.checkNotNull(str);
            r3.setInactive(str, true, true);
        }
        SmartspaceMediaData smartspaceMediaData = legacyMediaDataFilterImpl.smartspaceMediaData;
        if (smartspaceMediaData.isActive) {
            Intent intent = smartspaceMediaData.dismissIntent;
            if (intent == null) {
                Log.w("MediaDataFilter", "Cannot create dismiss action click action: extras missing dismiss_intent.");
            } else {
                ComponentName component = intent.getComponent();
                if (Intrinsics.areEqual(component != null ? component.getClassName() : null, "com.google.android.apps.gsa.staticplugins.opa.smartspace.ExportedSmartspaceTrampolineActivity")) {
                    legacyMediaDataFilterImpl.context.startActivity(intent);
                } else {
                    legacyMediaDataFilterImpl.broadcastSender.sendBroadcast(intent);
                }
            }
            legacyMediaDataFilterImpl.mediaFlags.isPersistentSsCardEnabled();
            SmartspaceMediaData smartspaceMediaData2 = LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA;
            SmartspaceMediaData smartspaceMediaData3 = legacyMediaDataFilterImpl.smartspaceMediaData;
            SmartspaceMediaData copy$default = SmartspaceMediaData.copy$default(smartspaceMediaData2, smartspaceMediaData3.targetId, false, null, 0L, smartspaceMediaData3.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
            legacyMediaDataFilterImpl.smartspaceMediaData = copy$default;
            MediaDataManager mediaDataManager2 = legacyMediaDataFilterImpl.mediaDataManager;
            (mediaDataManager2 != null ? mediaDataManager2 : null).dismissSmartspaceRecommendation(copy$default.targetId, 0L);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void removeListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.remove(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void setInactive(String str, boolean z, boolean z2) {
        MediaData mediaData = (MediaData) this.mediaEntries.get(str);
        if (mediaData != null) {
            if (z && !z2) {
                this.logger.logger.logWithInstanceId(MediaUiEvent.MEDIA_TIMEOUT, mediaData.appUid, mediaData.packageName, mediaData.instanceId);
            }
            boolean z3 = mediaData.active;
            boolean z4 = !z;
            if (z3 == z4 && !z2) {
                if (mediaData.resumption) {
                    Log.d("MediaDataManager", "timing out resume player ".concat(str));
                    dismissMediaData(str, 0L, false);
                    return;
                }
                return;
            }
            if (z3) {
                mediaData.lastActive = this.systemClock.elapsedRealtime();
            }
            mediaData.active = z4;
            Log.d("MediaDataManager", "Updating " + str + " timedOut: " + z);
            onMediaDataLoaded(str, str, mediaData);
        }
        if (str.equals(this.smartspaceMediaData.targetId)) {
            Log.d("MediaDataManager", "smartspace card expired");
            dismissSmartspaceRecommendation(str, 0L);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void setMediaResumptionEnabled(boolean z) {
        if (this.useMediaResumption == z) {
            return;
        }
        this.useMediaResumption = z;
        if (z) {
            return;
        }
        LinkedHashMap linkedHashMap = this.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (!((MediaData) entry.getValue()).active) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
            this.mediaEntries.remove(entry2.getKey());
            notifyMediaDataRemoved$default(this, (String) entry2.getKey());
            this.logger.logMediaRemoved(((MediaData) entry2.getValue()).appUid, ((MediaData) entry2.getValue()).packageName, ((MediaData) entry2.getValue()).instanceId);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void setResumeAction(Runnable runnable, String str) {
        MediaData mediaData = (MediaData) this.mediaEntries.get(str);
        if (mediaData != null) {
            mediaData.resumeAction = runnable;
            mediaData.hasCheckedForResume = true;
        }
    }

    public LegacyMediaDataManagerImpl(Context context, ThreadFactory threadFactory, Executor executor, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, LegacyMediaDataFilterImpl legacyMediaDataFilterImpl, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, SystemClock systemClock, TunerService tunerService, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this(context, threadFactory.buildExecutorOnNewThread("MediaDataManager"), executor, delayableExecutor, mediaControllerFactory, broadcastDispatcher, dumpManager, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, legacyMediaDataFilterImpl, activityStarter, smartspaceMediaDataProvider, com.android.systemui.util.Utils.useMediaResumption(context), com.android.systemui.util.Utils.useQsMediaPlayer(context), systemClock, tunerService, mediaFlags, mediaUiEventLogger, smartspaceManager, keyguardUpdateMonitor);
    }
}
