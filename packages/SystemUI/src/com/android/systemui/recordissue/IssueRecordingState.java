package com.android.systemui.recordissue;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.ArraySet;
import com.android.systemui.R;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.traceur.PresetTraceConfigs;
import com.android.traceur.TraceConfig;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IssueRecordingState {
    public static final LinkedHashMap ALL_ISSUE_TYPES;
    public static final Companion Companion = new Companion(null);
    public final GlobalSettings globalSettings;
    public boolean isRecording;
    public final IssueRecordingState$onRecordingChangeListener$1 onRecordingChangeListener;
    public final ContentResolver resolver;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;
    public final CustomTraceState customTraceState = new CustomTraceState(getPrefs());
    public final CopyOnWriteArrayList listeners = new CopyOnWriteArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Integer valueOf = Integer.valueOf(R.string.performance);
        if (PresetTraceConfigs.mPerformanceTagList == null) {
            ArraySet arraySet = new ArraySet(PresetTraceConfigs.PERFORMANCE_TRACE_TAGS);
            PresetTraceConfigs.mPerformanceTagList = arraySet;
            PresetTraceConfigs.updateTagsIfUserBuild(arraySet);
        }
        Pair pair = new Pair(valueOf, new TraceConfig(PresetTraceConfigs.PERFORMANCE_TRACE_OPTIONS, PresetTraceConfigs.mPerformanceTagList));
        Integer valueOf2 = Integer.valueOf(R.string.user_interface);
        if (PresetTraceConfigs.mUiTagList == null) {
            ArraySet arraySet2 = new ArraySet(PresetTraceConfigs.UI_TRACE_TAGS);
            PresetTraceConfigs.mUiTagList = arraySet2;
            PresetTraceConfigs.updateTagsIfUserBuild(arraySet2);
        }
        Pair pair2 = new Pair(valueOf2, new TraceConfig(PresetTraceConfigs.UI_TRACE_OPTIONS, PresetTraceConfigs.mUiTagList));
        Integer valueOf3 = Integer.valueOf(R.string.battery);
        if (PresetTraceConfigs.mBatteryTagList == null) {
            ArraySet arraySet3 = new ArraySet(PresetTraceConfigs.BATTERY_TRACE_TAGS);
            PresetTraceConfigs.mBatteryTagList = arraySet3;
            PresetTraceConfigs.updateTagsIfUserBuild(arraySet3);
        }
        Pair pair3 = new Pair(valueOf3, new TraceConfig(PresetTraceConfigs.BATTERY_TRACE_OPTIONS, PresetTraceConfigs.mBatteryTagList));
        Integer valueOf4 = Integer.valueOf(R.string.thermal);
        if (PresetTraceConfigs.mThermalTagList == null) {
            ArraySet arraySet4 = new ArraySet(PresetTraceConfigs.THERMAL_TRACE_TAGS);
            PresetTraceConfigs.mThermalTagList = arraySet4;
            PresetTraceConfigs.updateTagsIfUserBuild(arraySet4);
        }
        Pair[] pairArr = {pair, pair2, pair3, new Pair(valueOf4, new TraceConfig(PresetTraceConfigs.THERMAL_TRACE_OPTIONS, PresetTraceConfigs.mThermalTagList)), new Pair(Integer.valueOf(R.string.custom), null)};
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(5));
        MapsKt__MapsKt.putAll(linkedHashMap, pairArr);
        ALL_ISSUE_TYPES = linkedHashMap;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.recordissue.IssueRecordingState$onRecordingChangeListener$1] */
    public IssueRecordingState(UserTracker userTracker, UserFileManager userFileManager, final Handler handler, ContentResolver contentResolver, GlobalSettings globalSettings) {
        this.userTracker = userTracker;
        this.userFileManager = userFileManager;
        this.resolver = contentResolver;
        this.globalSettings = globalSettings;
        this.onRecordingChangeListener = new ContentObserver(handler) { // from class: com.android.systemui.recordissue.IssueRecordingState$onRecordingChangeListener$1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v0 */
            /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
            /* JADX WARN: Type inference failed for: r2v2 */
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                IssueRecordingState issueRecordingState = this;
                ?? r2 = issueRecordingState.globalSettings.getInt("issueRecordingOngoing", 0) == 1 ? 1 : 0;
                issueRecordingState.globalSettings.putInt("issueRecordingOngoing", r2);
                issueRecordingState.isRecording = r2;
                Iterator it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        };
    }

    public final void addListener(Runnable runnable) {
        if (this.listeners.isEmpty()) {
            this.resolver.registerContentObserver(this.globalSettings.getUriFor("issueRecordingOngoing"), false, this.onRecordingChangeListener);
        }
        this.listeners.add(runnable);
    }

    public final int getIssueTypeRes() {
        if (getPrefs().getInt("key_issueTypeIndex", -1) == -1) {
            return -1;
        }
        return CollectionsKt___CollectionsKt.toIntArray(ALL_ISSUE_TYPES.keySet())[getPrefs().getInt("key_issueTypeIndex", -1)];
    }

    public final SharedPreferences getPrefs() {
        return ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(((UserTrackerImpl) this.userTracker).getUserId(), "record_issue");
    }

    public static /* synthetic */ void getOnRecordingChangeListener$annotations() {
    }
}
