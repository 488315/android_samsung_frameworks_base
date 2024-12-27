package android.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.concurrent.atomic.AtomicBoolean;

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
    private Context mContext;
    private AtomicBoolean mHasUiUpdated = new AtomicBoolean();
    private AtomicBoolean mIdsWindow = new AtomicBoolean();
    private static final int DO_IDS = SystemProperties.getInt("debug.ids.setWindowSize", 10);
    private static boolean sClearData = false;

    public IdsController(Context app) {
        this.mContext = app;
    }

    void openIdsWindow(View v, Choreographer mChoreographer) {
        if (sClearData) {
            Log.i(TAG, "Clearing training data of " + this.mContext);
            if (this.mContext != null) {
                this.mContext.deleteSharedPreferences(IDS_URI);
            }
        }
        Log.i(TAG, "Starting IDS observe window");
        registerLayoutListener(v, mChoreographer);
        setIdsWindowActive(true);
    }

    public void closeIdsWindow() {
        int idsCount;
        int idsCount2;
        try {
            if (isIdsWindowActive()) {
                Log.i(TAG, "Closing IDS observe window");
                setIdsWindowActive(false);
                SharedPreferences sp = getIdsSharedPreference();
                if (sp != null && (idsCount = sp.getInt(IDS_KEY, 0)) < DO_IDS && idsCount != -1) {
                    if (getUiUpdated()) {
                        idsCount2 = -1;
                        Log.i(TAG, "IDS disabled for " + this.mContext);
                    } else {
                        idsCount2 = idsCount + 1;
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(IDS_KEY, idsCount2);
                    editor.apply();
                    Log.i(TAG, "IDS count updated to " + idsCount2 + " for " + this.mContext);
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Clearing data while writing to SP of " + this.mContext);
            if (this.mContext != null) {
                this.mContext.deleteSharedPreferences(IDS_URI);
            }
        }
    }

    public boolean doIds() {
        SharedPreferences sp = getIdsSharedPreference();
        if (sp != null) {
            int idsCount = sp.getInt(IDS_KEY, 0);
            if (idsCount == DO_IDS) {
                Log.i(TAG, "App " + this.mContext + " being boosted by IDS");
                return true;
            }
            if (idsCount == -1) {
                Log.i(TAG, "App " + this.mContext + " in NO_IDS list");
                return false;
            }
        }
        Log.i(TAG, "App " + this.mContext + " has not finished training");
        return false;
    }

    public static void clearTrainingData(boolean flag) {
        sClearData = flag;
    }

    private SharedPreferences getIdsSharedPreference() {
        try {
            Log.i(
                    TAG,
                    "Getting Shared Preference for " + this.mContext + " uid = " + Process.myUid());
            if (Process.myUid() == 1000) {
                Log.e(TAG, "System UID: no SharedPreferences here, no IDS");
                return null;
            }
            return this.mContext.getSharedPreferences(IDS_URI, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void registerLayoutListener(View v, Choreographer mChoreographer) {
        v.getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() { // from class:
                            // android.app.IdsController.1
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public void onGlobalLayout() {
                                IdsController.this.uiUpdated(2);
                            }
                        });
    }

    private boolean getUiUpdated() {
        return this.mHasUiUpdated.get();
    }

    public void uiUpdated(int reason) {
        if (isIdsWindowActive()) {
            Log.i(TAG, getReasonForUpdate(reason) + " updated UI for IDS");
            this.mHasUiUpdated.set(true);
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
        return this.mIdsWindow.get();
    }

    private void setIdsWindowActive(boolean active) {
        this.mIdsWindow.set(active);
    }
}
