package com.android.internal.pm.pkg.component;

import android.content.pm.ActivityInfo;

import java.util.Set;

/* loaded from: classes5.dex */
public interface ParsedActivity extends ParsedMainComponent {
    int getColorMode();

    int getConfigChanges();

    int getDocumentLaunchMode();

    Set<String> getKnownActivityEmbeddingCerts();

    int getLaunchMode();

    int getLockTaskLaunchMode();

    float getMaxAspectRatio();

    int getMaxRecents();

    float getMinAspectRatio();

    String getParentActivityName();

    String getPermission();

    int getPersistableMode();

    int getPrivateFlags();

    String getRequestedVrComponent();

    int getRequireContentUriPermissionFromCaller();

    String getRequiredDisplayCategory();

    int getResizeMode();

    int getRotationAnimation();

    int getScreenOrientation();

    int getSoftInputMode();

    String getTargetActivity();

    String getTaskAffinity();

    int getTheme();

    int getUiOptions();

    ActivityInfo.WindowLayout getWindowLayout();

    boolean isSupportsSizeChanges();
}
