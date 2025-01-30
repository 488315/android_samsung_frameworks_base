package com.android.server.pm.parsing;

import android.util.Pair;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.component.ParsedComponent;

/* loaded from: classes3.dex */
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
                .getOverrideLabelIconForComponent(parsedComponent.getComponentName());
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
