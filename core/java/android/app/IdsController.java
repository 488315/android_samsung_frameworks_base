package android.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.p009os.Process;
import android.p009os.SystemProperties;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class IdsController {
    private static final String IDS_KEY = "IDSCount";
    private static final String IDS_URI = "android.app.ActivityThread.IDS";
    private static final int NO_IDS = -1;
    public static final int REASON_BACKGROUND_CHANGED = 0;
    public static final int REASON_BACKGROUND_COLOR_CHANGED = 1;
    public static final int REASON_LAYOUT_CHANGED = 2;
    public static final int REASON_SETCONTENTVIEW = 3;
    public static final int REASON_VIEW_INFLATED = 4;
    private static final String TAG = "IDS_TAG";
    private Context ctx;
    private AtomicBoolean hasUiUpdated = new AtomicBoolean();
    private AtomicBoolean idsWindow = new AtomicBoolean();
    private static final int DO_IDS = SystemProperties.getInt("debug.ids.setWindowSize", 10);
    private static boolean clearData = false;

    public IdsController(Context app) {
        this.ctx = app;
    }

    void openIdsWindow(View v, Choreographer mChoreographer) {
        if (clearData) {
            Log.m98i(TAG, "Clearing training data of " + this.ctx);
            Context context = this.ctx;
            if (context != null) {
                context.deleteSharedPreferences(IDS_URI);
            }
        }
        Log.m98i(TAG, "Starting IDS observe window");
        registerLayoutListener(v, mChoreographer);
        setIdsWindowActive(true);
    }

    public void closeIdsWindow() {
        int idsCount;
        int idsCount2;
        try {
            if (isIdsWindowActive()) {
                Log.m98i(TAG, "Closing IDS observe window");
                setIdsWindowActive(false);
                SharedPreferences sp = getIdsSharedPreference();
                if (sp != null && (idsCount = sp.getInt(IDS_KEY, 0)) < DO_IDS && idsCount != -1) {
                    if (getUiUpdated()) {
                        idsCount2 = -1;
                        Log.m98i(TAG, "IDS disabled for " + this.ctx);
                    } else {
                        idsCount2 = idsCount + 1;
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(IDS_KEY, idsCount2);
                    editor.apply();
                    Log.m98i(TAG, "IDS count updated to " + idsCount2 + " for " + this.ctx);
                }
            }
        } catch (Exception e) {
            Log.m98i(TAG, "Clearing data while writing to SP of " + this.ctx);
            Context context = this.ctx;
            if (context != null) {
                context.deleteSharedPreferences(IDS_URI);
            }
        }
    }

    public boolean doIds() {
        SharedPreferences sp = getIdsSharedPreference();
        if (sp != null) {
            int idsCount = sp.getInt(IDS_KEY, 0);
            if (idsCount == DO_IDS) {
                Log.m98i(TAG, "App " + this.ctx + " being boosted by IDS");
                return true;
            }
            if (idsCount == -1) {
                Log.m98i(TAG, "App " + this.ctx + " in NO_IDS list");
                return false;
            }
        }
        Log.m98i(TAG, "App " + this.ctx + " has not finished training");
        return false;
    }

    public static void clearTrainingData(boolean flag) {
        clearData = flag;
    }

    private SharedPreferences getIdsSharedPreference() {
        try {
            Log.m98i(TAG, "Getting Shared Preference for " + this.ctx + " uid = " + Process.myUid());
            if (Process.myUid() == 1000) {
                Log.m96e(TAG, "System UID: no SharedPreferences here, no IDS");
                return null;
            }
            return this.ctx.getSharedPreferences(IDS_URI, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void registerLayoutListener(View v, Choreographer mChoreographer) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.app.IdsController.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                IdsController.this.uiUpdated(2);
            }
        });
    }

    private boolean getUiUpdated() {
        return this.hasUiUpdated.get();
    }

    public void uiUpdated(int reason) {
        if (isIdsWindowActive()) {
            Log.m98i(TAG, getReasonForUpdate(reason) + " updated UI for IDS");
            this.hasUiUpdated.set(true);
        }
    }

    private String getReasonForUpdate(int reason) {
        switch (reason) {
            case 0:
                return "Background Changed";
            case 1:
                return "Background Color Changed";
            case 2:
                return "Layout Changed";
            case 3:
                return "setContentView";
            case 4:
                return "View Inflated";
            default:
                return "Invalid reason";
        }
    }

    private boolean isIdsWindowActive() {
        return this.idsWindow.get();
    }

    private void setIdsWindowActive(boolean active) {
        this.idsWindow.set(active);
    }
}
