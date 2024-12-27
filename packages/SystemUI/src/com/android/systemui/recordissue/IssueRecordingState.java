package com.android.systemui.recordissue;

import android.content.SharedPreferences;
import com.android.systemui.R;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.traceur.TraceUtils$PresetTraceType;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IssueRecordingState {
    public boolean isRecording;
    public final CopyOnWriteArrayList listeners = new CopyOnWriteArrayList();
    public final SharedPreferences prefs;
    public static final Companion Companion = new Companion(null);
    public static final Map ALL_ISSUE_TYPES = MapsKt__MapsKt.hashMapOf(new Pair(Integer.valueOf(R.string.performance), TraceUtils$PresetTraceType.PERFORMANCE), new Pair(Integer.valueOf(R.string.user_interface), TraceUtils$PresetTraceType.UI), new Pair(Integer.valueOf(R.string.battery), TraceUtils$PresetTraceType.BATTERY), new Pair(Integer.valueOf(R.string.thermal), TraceUtils$PresetTraceType.THERMAL));

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public IssueRecordingState(UserTracker userTracker, UserFileManager userFileManager) {
        this.prefs = ((UserFileManagerImpl) userFileManager).getSharedPreferences$1(((UserTrackerImpl) userTracker).getUserId(), "record_issue");
    }
}
