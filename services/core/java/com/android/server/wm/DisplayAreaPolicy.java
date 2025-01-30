package com.android.server.wm;

import android.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class DisplayAreaPolicy {
  public final RootDisplayArea mRoot;
  public final WindowManagerService mWmService;

  public abstract DisplayArea.Tokens findAreaForWindowType(
      int i, Bundle bundle, boolean z, boolean z2);

  public abstract TaskDisplayArea getDefaultTaskDisplayArea();

  public abstract List getDisplayAreas(int i);

  public abstract TaskDisplayArea getTaskDisplayArea(Bundle bundle);

  public DisplayAreaPolicy(
      WindowManagerService windowManagerService, RootDisplayArea rootDisplayArea) {
    this.mWmService = windowManagerService;
    this.mRoot = rootDisplayArea;
  }

  public final class DefaultProvider implements Provider {
    @Override // com.android.server.wm.DisplayAreaPolicy.Provider
    public DisplayAreaPolicy instantiate(
        WindowManagerService windowManagerService,
        DisplayContent displayContent,
        RootDisplayArea rootDisplayArea,
        DisplayArea.Tokens tokens) {
      String str;
      StringBuilder sb = new StringBuilder();
      sb.append("DefaultTaskDisplayArea");
      if (displayContent != null) {
        str = "_d" + displayContent.getDisplayId();
      } else {
        str = "";
      }
      sb.append(str);
      TaskDisplayArea taskDisplayArea =
          new TaskDisplayArea(displayContent, windowManagerService, sb.toString(), 1);
      ArrayList arrayList = new ArrayList();
      arrayList.add(taskDisplayArea);
      DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder =
          new DisplayAreaPolicyBuilder.HierarchyBuilder(rootDisplayArea);
      hierarchyBuilder.setImeContainer(tokens).setTaskDisplayAreas(arrayList);
      if (displayContent.isTrusted()) {
        configureTrustedHierarchyBuilder(hierarchyBuilder, windowManagerService, displayContent);
      }
      return new DisplayAreaPolicyBuilder()
          .setRootHierarchy(hierarchyBuilder)
          .build(windowManagerService);
    }

    public final void configureTrustedHierarchyBuilder(
        DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder,
        WindowManagerService windowManagerService,
        DisplayContent displayContent) {
      hierarchyBuilder.addFeature(
          new DisplayAreaPolicyBuilder.Feature.Builder(
                  windowManagerService.mPolicy, "WindowedMagnification", 4)
              .upTo(2039)
              .except(2039)
              .setNewDisplayAreaSupplier(
                  new DisplayAreaPolicyBuilder
                      .NewDisplayAreaSupplier() { // from class:
                                                  // com.android.server.wm.DisplayAreaPolicy$DefaultProvider$$ExternalSyntheticLambda0
                    @Override // com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier
                    public final DisplayArea create(
                        WindowManagerService windowManagerService2,
                        DisplayArea.Type type,
                        String str,
                        int i) {
                      return new DisplayArea.Dimmable(windowManagerService2, type, str, i);
                    }
                  })
              .build());
      if (displayContent.isDefaultDisplay) {
        hierarchyBuilder
            .addFeature(
                new DisplayAreaPolicyBuilder.Feature.Builder(
                        windowManagerService.mPolicy, "HideDisplayCutout", 6)
                    .all()
                    .except(2019, 2024, 2000, 2040)
                    .build())
            .addFeature(
                new DisplayAreaPolicyBuilder.Feature.Builder(
                        windowManagerService.mPolicy, "OneHanded", 3)
                    .all()
                    .except(2019, 2024, 2015)
                    .build());
        if (CoreRune.FW_REMOTE_WALLPAPER_ANIM) {
          hierarchyBuilder.addFeature(
              new DisplayAreaPolicyBuilder.Feature.Builder(
                      windowManagerService.mPolicy, "RemoteWallpaperAnim", 10002)
                  .and(2013)
                  .build());
        }
      }
      hierarchyBuilder
          .addFeature(
              new DisplayAreaPolicyBuilder.Feature.Builder(
                      windowManagerService.mPolicy, "FullscreenMagnification", 5)
                  .all()
                  .except(2039, 2011, 2012, 2027, 2019, 2024)
                  .build())
          .addFeature(
              new DisplayAreaPolicyBuilder.Feature.Builder(
                      windowManagerService.mPolicy, "ImePlaceholder", 7)
                  .and(2011, 2012)
                  .build());
    }
  }

  public interface Provider {
    DisplayAreaPolicy instantiate(
        WindowManagerService windowManagerService,
        DisplayContent displayContent,
        RootDisplayArea rootDisplayArea,
        DisplayArea.Tokens tokens);

    static Provider fromResources(Resources resources) {
      String string = resources.getString(R.string.deleteText);
      if (TextUtils.isEmpty(string)) {
        return new DefaultProvider();
      }
      try {
        return (Provider) Class.forName(string).newInstance();
      } catch (ClassCastException | ReflectiveOperationException e) {
        throw new IllegalStateException(
            "Couldn't instantiate class "
                + string
                + " for config_deviceSpecificDisplayAreaPolicyProvider: make sure it has a public"
                + " zero-argument constructor and implements DisplayAreaPolicy.Provider",
            e);
      }
    }
  }
}
