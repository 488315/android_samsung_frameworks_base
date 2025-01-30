package com.android.wm.shell.controlpanel.utils;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.SparseArray;
import com.android.systemui.R;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CheckControlWindowState {
    public static final SparseArray mActionViewIdMap;

    static {
        SparseArray sparseArray = new SparseArray();
        mActionViewIdMap = sparseArray;
        sparseArray.put(R.id.action_skip_previous, 16L);
        sparseArray.put(R.id.action_skip_next, 32L);
        sparseArray.put(R.id.action_resume, 4L);
        sparseArray.put(R.id.action_pause, 2L);
    }

    public static MediaController getMediaController(Context context, MediaSessionManager mediaSessionManager) {
        String packageNameForMediaPanel = ControlPanelUtils.getPackageNameForMediaPanel(context, true);
        String packageNameForMediaPanel2 = ControlPanelUtils.getPackageNameForMediaPanel(context, false);
        List activeSessionsForUser = mediaSessionManager.getActiveSessionsForUser(null, new UserHandle(ControlPanelUtils.getTopTaskUserId(context)));
        int size = activeSessionsForUser != null ? activeSessionsForUser.size() : 0;
        Log.i("CheckControlWindowState", "CheckControlWindowState getMediaController topPackage : " + packageNameForMediaPanel + ", controllerSize : " + size);
        for (int i = 0; i < size; i++) {
            Log.i("CheckControlWindowState", "CheckControlWindowState getMediaController controllers.getPackageName : " + ((MediaController) activeSessionsForUser.get(i)).getPackageName());
            if (packageNameForMediaPanel.equals(((MediaController) activeSessionsForUser.get(i)).getPackageName()) || packageNameForMediaPanel2.equals(((MediaController) activeSessionsForUser.get(i)).getPackageName())) {
                MediaController mediaController = new MediaController(context, ((MediaController) activeSessionsForUser.get(i)).getSessionToken());
                Log.i("CheckControlWindowState", "CheckControlWindowState getMediaController mediaController : " + mediaController.getPackageName() + ", getPlaybackState() : " + mediaController.getPlaybackState());
                if (mediaController.getPlaybackState() != null && mediaController.getMetadata() != null) {
                    return mediaController;
                }
            }
        }
        return null;
    }

    public static boolean isMediaPanelRequestedState(Context context, MediaController mediaController) {
        boolean contains;
        String packageNameForMediaPanel = ControlPanelUtils.getPackageNameForMediaPanel(context, true);
        Log.d("CheckControlWindowState", "isMediaPanelRequestedState() start : " + packageNameForMediaPanel);
        String[] stringArray = context.getResources().getStringArray(R.array.config_MediaPanel_Exclude);
        if (stringArray == null) {
            Log.d("CheckControlWindowState", "excludeMediaApp packageList is null");
            contains = false;
        } else {
            contains = Arrays.asList(stringArray).contains(packageNameForMediaPanel);
        }
        if (contains) {
            AbstractC0000x2c234b15.m3m("media app category 0 is in foreground : ", packageNameForMediaPanel, "CheckControlWindowState");
            return false;
        }
        if (mediaController == null || mediaController.getMetadata() == null) {
            return false;
        }
        AbstractC0000x2c234b15.m3m("media app category 1 is in foreground : ", packageNameForMediaPanel, "CheckControlWindowState");
        return true;
    }

    public static boolean isSupportButton(MediaController mediaController) {
        if (mediaController == null || mediaController.getPlaybackState() == null) {
            Log.i("CheckControlWindowState", "isSupportButton mediaController or getPlaybackState is null");
            return false;
        }
        long actions = mediaController.getPlaybackState().getActions();
        if (mediaController.getPlaybackState().getState() == 0) {
            return false;
        }
        int i = 0;
        while (true) {
            SparseArray sparseArray = mActionViewIdMap;
            if (i >= sparseArray.size()) {
                Log.i("CheckControlWindowState", "isSupportButton actionSupported false");
                return false;
            }
            if ((((Long) sparseArray.valueAt(i)).longValue() & actions) != 0) {
                Log.i("CheckControlWindowState", "isSupportButton actionSupported true");
                return true;
            }
            i++;
        }
    }
}
