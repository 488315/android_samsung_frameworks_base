package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;
import com.android.server.audio.AudioService;
import com.samsung.android.server.audio.SemAudioServiceInternal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ActiveMusicRecordFilter implements IFilter {
    public String FM_RADIO_PACKAGE_NAME;
    public String SILENT_AUDIO_PREX;
    public ArrayMap mActiveMusicRecordPkgs;
    public AudioManager mAudioManager;
    public Context mContext;
    public boolean mIsUsingAudioList;
    public SemAudioServiceInternal mLocalAudioService;
    public ArrayMap mSilentAudioDetectionMap;
    public ArraySet mSlientAudioApp;
    public ArrayMap mTTSPkgs;
    public ArrayList mTTSPkgsUid;
    public ExecutorService threadPool;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ActiveMusicRecordFilterHolder {
        public static final ActiveMusicRecordFilter INSTANCE;

        static {
            ActiveMusicRecordFilter activeMusicRecordFilter = new ActiveMusicRecordFilter();
            activeMusicRecordFilter.mContext = null;
            activeMusicRecordFilter.mAudioManager = null;
            activeMusicRecordFilter.mLocalAudioService = null;
            activeMusicRecordFilter.mActiveMusicRecordPkgs = new ArrayMap();
            activeMusicRecordFilter.mTTSPkgs = new ArrayMap();
            activeMusicRecordFilter.mTTSPkgsUid = new ArrayList();
            activeMusicRecordFilter.mIsUsingAudioList = false;
            activeMusicRecordFilter.FM_RADIO_PACKAGE_NAME = "com.sec.android.app.fm";
            activeMusicRecordFilter.threadPool = null;
            activeMusicRecordFilter.mSilentAudioDetectionMap = new ArrayMap();
            activeMusicRecordFilter.mSlientAudioApp = new ArraySet();
            activeMusicRecordFilter.SILENT_AUDIO_PREX = "g_silent_audio=";
            INSTANCE = activeMusicRecordFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        Context context;
        if (this.mAudioManager == null && (context = this.mContext) != null) {
            this.mAudioManager = (AudioManager) context.getSystemService("audio");
        }
        if (this.FM_RADIO_PACKAGE_NAME.equals(str)) {
            boolean z = true;
            if (this.mAudioManager == null) {
                Slog.w("ActiveMusicRecordFilter", "audio Manager is null");
            } else {
                FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter$$ExternalSyntheticLambda1
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return Boolean.valueOf(ActiveMusicRecordFilter.this.mAudioManager.semIsFmRadioActive());
                    }
                });
                this.threadPool.execute(futureTask);
                try {
                    z = ((Boolean) futureTask.get(1L, TimeUnit.SECONDS)).booleanValue();
                } catch (Exception e) {
                    if (e instanceof TimeoutException) {
                        Slog.w("ActiveMusicRecordFilter", "1 second timeout executing semIsFmRadioActive");
                    }
                    futureTask.cancel(false);
                    e.printStackTrace();
                }
            }
            return z ? 7 : 0;
        }
        if (MARsDebugConfig.DEBUG_MARs) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "filter uid:", "mSlientAudioApp: ");
            m.append(this.mSlientAudioApp.toString());
            Slog.d("ActiveMusicRecordFilter", m.toString());
        }
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        if (MARsPolicyManager.isChinaPolicyEnabled() && i3 != 11 && this.mSlientAudioApp.contains(Integer.valueOf(i2))) {
            return 0;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (((MARsUtils.getScreenOnState() && (i3 == 4 || i3 == 15)) || (i3 == 18 && !this.mIsUsingAudioList)) && isUsingAudio(i2, str)) {
            synchronized (this.mActiveMusicRecordPkgs) {
                this.mActiveMusicRecordPkgs.put(Integer.valueOf(i2), Long.valueOf(elapsedRealtime));
            }
            return 7;
        }
        synchronized (this.mActiveMusicRecordPkgs) {
            try {
                Long l = (Long) this.mActiveMusicRecordPkgs.get(Integer.valueOf(i2));
                long j = !MARsPolicyManager.isChinaPolicyEnabled() ? 300000L : 2000L;
                if (l != null && elapsedRealtime - l.longValue() <= j) {
                    return 7;
                }
                this.mActiveMusicRecordPkgs.remove(Integer.valueOf(i2));
                if (!MARsUtils.isChinaPolicyEnabled() || mARsPolicyManager.isAutoRunOn(i, str)) {
                    synchronized (this.mTTSPkgs) {
                        try {
                            ArrayList arrayList = (ArrayList) this.mTTSPkgs.get(Integer.valueOf(i));
                            if (arrayList != null && arrayList.contains(str)) {
                                return 7;
                            }
                            synchronized (this.mTTSPkgsUid) {
                                try {
                                    if (this.mTTSPkgsUid.contains(Integer.valueOf(i2))) {
                                        return 7;
                                    }
                                } finally {
                                }
                            }
                        } finally {
                        }
                    }
                }
                return 0;
            } finally {
            }
        }
    }

    public final void getUidListUsingAudio() {
        List<Integer> arrayList;
        int clientUid;
        if (this.mLocalAudioService == null) {
            this.mLocalAudioService = (SemAudioServiceInternal) LocalServices.getService(SemAudioServiceInternal.class);
        }
        SemAudioServiceInternal semAudioServiceInternal = this.mLocalAudioService;
        if (semAudioServiceInternal != null) {
            AudioService audioService = (AudioService) semAudioServiceInternal.mAudioService.get();
            if (audioService == null) {
                arrayList = Collections.emptyList();
            } else {
                ArraySet arraySet = new ArraySet();
                Iterator it = ((ArrayList) audioService.mPlaybackMonitor.getActivePlaybackConfigurations(true)).iterator();
                while (it.hasNext()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
                    if (audioPlaybackConfiguration != null && (clientUid = audioPlaybackConfiguration.getClientUid()) > 10000 && (audioPlaybackConfiguration.getPlayerType() == 3 || audioPlaybackConfiguration.getPlayerState() == 2)) {
                        arraySet.add(Integer.valueOf(clientUid));
                    }
                }
                Iterator it2 = audioService.mRecordMonitor.getActiveRecordingConfigurations(true).iterator();
                while (it2.hasNext()) {
                    int clientUid2 = ((AudioRecordingConfiguration) it2.next()).getClientUid();
                    if (clientUid2 > 10000) {
                        arraySet.add(Integer.valueOf(clientUid2));
                    }
                }
                Iterator it3 = arraySet.iterator();
                while (it3.hasNext()) {
                    if (!audioService.isUsingAudio(((Integer) it3.next()).intValue() % 100000)) {
                        it3.remove();
                    }
                }
                arrayList = new ArrayList(arraySet);
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            for (Integer num : arrayList) {
                synchronized (this.mActiveMusicRecordPkgs) {
                    this.mActiveMusicRecordPkgs.put(num, Long.valueOf(elapsedRealtime));
                }
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        Context context2;
        this.mContext = context;
        this.threadPool = Executors.newFixedThreadPool(4, new ThreadFactory() { // from class: com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter.1
            public final AtomicInteger counter = new AtomicInteger();

            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, String.format("freecess-%d", Integer.valueOf(this.counter.incrementAndGet())));
            }
        });
        if (this.mLocalAudioService == null) {
            this.mLocalAudioService = (SemAudioServiceInternal) LocalServices.getService(SemAudioServiceInternal.class);
        }
        if (this.mAudioManager != null || (context2 = this.mContext) == null) {
            return;
        }
        this.mAudioManager = (AudioManager) context2.getSystemService("audio");
    }

    public final boolean isUsingAudio(final int i, final String str) {
        if (this.mAudioManager == null) {
            Slog.w("ActiveMusicRecordFilter", "audio Manager is null");
            return true;
        }
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return Boolean.valueOf(ActiveMusicRecordFilter.this.mAudioManager.isUsingAudio(str, i));
            }
        });
        this.threadPool.execute(futureTask);
        try {
            return ((Boolean) futureTask.get(1L, TimeUnit.SECONDS)).booleanValue();
        } catch (Exception e) {
            if (e instanceof TimeoutException) {
                Slog.w("ActiveMusicRecordFilter", "1 second timeout executing isUsingAudio");
            }
            futureTask.cancel(false);
            e.printStackTrace();
            return true;
        }
    }
}
