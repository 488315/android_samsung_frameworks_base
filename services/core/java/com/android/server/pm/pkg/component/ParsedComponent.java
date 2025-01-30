package com.android.server.pm.pkg.component;

import android.content.ComponentName;
import android.os.Bundle;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface ParsedComponent {
    int getBanner();

    ComponentName getComponentName();

    int getDescriptionRes();

    int getFlags();

    int getIcon();

    List getIntents();

    int getLabelRes();

    int getLogo();

    Bundle getMetaData();

    String getName();

    CharSequence getNonLocalizedLabel();

    String getPackageName();

    Map getProperties();
}
