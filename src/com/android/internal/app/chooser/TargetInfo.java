package com.android.internal.app.chooser;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.app.ResolverActivity;
import com.android.internal.hidden_from_bootclasspath.android.security.Flags;
import java.util.List;

/* loaded from: classes5.dex */
public interface TargetInfo {
    TargetInfo cloneFilledIn(Intent intent, int i);

    List<Intent> getAllSourceIntents();

    Drawable getDisplayIcon(Context context);

    CharSequence getDisplayLabel();

    CharSequence getExtendedInfo();

    ResolveInfo getResolveInfo();

    ComponentName getResolvedComponentName();

    Intent getResolvedIntent();

    boolean isPinned();

    boolean isSuspended();

    default void setSkipFixUris(boolean z) {
    }

    boolean start(Activity activity, Bundle bundle);

    boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int i);

    boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle);

    static void prepareIntentForCrossProfileLaunch(Intent intent, int i) {
        int iMyUserId = UserHandle.myUserId();
        if (i != iMyUserId) {
            intent.fixUris(iMyUserId);
        }
    }

    static void refreshIntentCreatorToken(Intent intent) {
        if (Flags.preventIntentRedirect()) {
            try {
                intent.setCreatorToken(ActivityManager.getService().refreshIntentCreatorToken(intent.cloneForCreatorToken()));
            } catch (RemoteException e) {
                throw new RuntimeException("Failure from system", e);
            }
        }
    }
}
