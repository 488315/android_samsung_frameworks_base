package com.android.server.pm.parsing;

import android.util.Pair;

import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.server.pm.pkg.PackageStateInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ParsedComponentStateUtils {
    public static Pair getNonLocalizedLabelAndIcon(
            ParsedComponent parsedComponent, PackageStateInternal packageStateInternal, int i) {
        CharSequence nonLocalizedLabel = parsedComponent.getNonLocalizedLabel();
        int icon = parsedComponent.getIcon();
        Pair overrideLabelIconForComponent =
                packageStateInternal == null
                        ? null
                        : packageStateInternal
                                .getUserStateOrDefault(i)
                                .getOverrideLabelIconForComponent(
                                        parsedComponent.getComponentName());
        if (overrideLabelIconForComponent != null) {
            Object obj = overrideLabelIconForComponent.first;
            if (obj != null) {
                nonLocalizedLabel = (CharSequence) obj;
            }
            Object obj2 = overrideLabelIconForComponent.second;
            if (obj2 != null) {
                icon = ((Integer) obj2).intValue();
            }
        }
        return Pair.create(nonLocalizedLabel, Integer.valueOf(icon));
    }
}
