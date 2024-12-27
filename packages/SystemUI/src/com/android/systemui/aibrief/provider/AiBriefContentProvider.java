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

public final class AiBriefContentProvider extends ContentProvider {
    public static final int $stable = 0;
    public static final String CLEAR_SUGGESTION_NOW_BAR = "CLEAR_SUGGESTION_NOW_BAR";
    public static final String CLEAR_SUGGESTION_REMOTE_NOW_BAR = "CLEAR_SUGGESTION_REMOTE_NOW_BAR";
    public static final String CREATE_SUGGESTION_NOW_BAR = "CREATE_SUGGESTION_NOW_BAR";
    public static final String CREATE_SUGGESTION_REMOTE_NOW_BAR = "CREATE_SUGGESTION_REMOTE_NOW_BAR";
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        AiBriefManager aiBriefManager;
        AiBriefManager aiBriefManager2;
        String string = bundle != null ? bundle.getString("data") : null;
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("call( method:", str, ", arg:", str2, ", extrasBundle:");
        m.append(string);
        m.append(" )");
        Log.d("AiBriefContentProvider", m.toString());
        switch (str.hashCode()) {
            case -1538361350:
                if (str.equals(CLEAR_SUGGESTION_REMOTE_NOW_BAR) && (aiBriefManager = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class)) != null) {
                    aiBriefManager.hideRemoteNowBar();
                    break;
                }
                break;
            case -822060535:
                if (str.equals(CREATE_SUGGESTION_REMOTE_NOW_BAR)) {
                    if (bundle == null) {
                        Log.e("AiBriefContentProvider", "ERROR call() extras is null");
                        break;
                    } else {
                        AiBriefManager aiBriefManager3 = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class);
                        if (aiBriefManager3 != null) {
                            aiBriefManager3.createRemoteNowBar(bundle);
                            break;
                        }
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
                        AiBriefManager aiBriefManager4 = (AiBriefManager) Dependency.sDependency.getDependencyInner(AiBriefManager.class);
                        if (aiBriefManager4 != null) {
                            aiBriefManager4.createNowBar(bundle);
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
