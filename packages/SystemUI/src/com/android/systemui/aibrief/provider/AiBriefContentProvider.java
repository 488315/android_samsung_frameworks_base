package com.android.systemui.aibrief.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.aibrief.AiBriefManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AiBriefContentProvider extends ContentProvider {
    public static final int $stable = 0;
    public static final String CLEAR_SUGGESTION_NOW_BAR = "CLEAR_SUGGESTION_NOW_BAR";
    public static final String CLEAR_SUGGESTION_REMOTE_NOW_BAR = "CLEAR_SUGGESTION_REMOTE_NOW_BAR";
    public static final String CREATE_SUGGESTION_NOW_BAR = "CREATE_SUGGESTION_NOW_BAR";
    public static final String CREATE_SUGGESTION_REMOTE_NOW_BAR = "CREATE_SUGGESTION_REMOTE_NOW_BAR";
    public static final Companion Companion = new Companion(null);
    public static final String GET_SUGGESTION_REMOTE_SPORTS_SCORE = "GET_SUGGESTION_REMOTE_SPORTS_SCORE";
    public static final String UPDATE_SUGGESTION_NOW_BAR_NEED_TO_UNLOCK = "UPDATE_SUGGESTION_NOW_BAR_NEED_TO_UNLOCK";

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        AiBriefManager aiBriefManager;
        AiBriefManager aiBriefManager2;
        String string = bundle != null ? bundle.getString("data") : null;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("call( method:", str, ", arg:", str2, ", extrasBundle:");
        sbM.append(string);
        sbM.append(" )");
        Log.d("AiBriefContentProvider", sbM.toString());
        switch (str.hashCode()) {
            case -1538361350:
                if (str.equals(CLEAR_SUGGESTION_REMOTE_NOW_BAR) && (aiBriefManager = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class)) != null) {
                    aiBriefManager.hideRemoteNowBar();
                    break;
                }
                break;
            case -1014604359:
                if (str.equals(UPDATE_SUGGESTION_NOW_BAR_NEED_TO_UNLOCK)) {
                    if (bundle == null) {
                        Log.e("AiBriefContentProvider", "ERROR call() extras is null");
                        break;
                    } else {
                        AiBriefManager aiBriefManager3 = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class);
                        if (aiBriefManager3 != null) {
                            aiBriefManager3.updateNowBarNeedToUnlock(bundle);
                            break;
                        }
                    }
                }
                break;
            case -822060535:
                if (str.equals(CREATE_SUGGESTION_REMOTE_NOW_BAR)) {
                    if (bundle == null) {
                        Log.e("AiBriefContentProvider", "ERROR call() extras is null");
                        break;
                    } else {
                        AiBriefManager aiBriefManager4 = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class);
                        if (aiBriefManager4 != null) {
                            aiBriefManager4.createRemoteNowBar(bundle);
                            break;
                        }
                    }
                }
                break;
            case -682157959:
                if (str.equals(GET_SUGGESTION_REMOTE_SPORTS_SCORE)) {
                    if (bundle == null) {
                        Log.e("AiBriefContentProvider", "ERROR call() extras is null");
                        break;
                    } else {
                        AiBriefManager aiBriefManager5 = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class);
                        if (aiBriefManager5 != null) {
                            return aiBriefManager5.findSportsScoreRemoteViews(bundle);
                        }
                        return null;
                    }
                }
                break;
            case -371678463:
                if (str.equals(CLEAR_SUGGESTION_NOW_BAR) && (aiBriefManager2 = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class)) != null) {
                    aiBriefManager2.hideNowBar();
                    break;
                }
                break;
            case 1363436498:
                if (str.equals(CREATE_SUGGESTION_NOW_BAR)) {
                    if (bundle == null) {
                        Log.e("AiBriefContentProvider", "ERROR call() extras is null");
                        break;
                    } else {
                        AiBriefManager aiBriefManager6 = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class);
                        if (aiBriefManager6 != null) {
                            aiBriefManager6.createNowBar(bundle);
                            break;
                        }
                    }
                }
                break;
        }
        return super.call(str, str2, bundle);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        Log.d("AiBriefContentProvider", "delete()");
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Log.d("AiBriefContentProvider", "getType()");
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d("AiBriefContentProvider", "insert()");
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Log.d("AiBriefContentProvider", "query()");
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Log.d("AiBriefContentProvider", "update()");
        return 0;
    }
}
