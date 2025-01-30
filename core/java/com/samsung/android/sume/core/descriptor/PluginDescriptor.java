package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.filter.PluginFilter;
import com.samsung.android.sume.core.types.Version;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public abstract class PluginDescriptor extends MFDescriptorBase {
  private static final String TAG = Def.tagOf((Class<?>) PluginDescriptor.class);

  public Class<?> getPluginClass() {
    return (Class) getAll().get(Integer.valueOf(PLUGIN_CLASS));
  }

  public void setPluginClass(Class<?> pluginClass) {
    getAll().put(Integer.valueOf(PLUGIN_CLASS), pluginClass);
  }

  public String getPluginClassName() {
    return (String)
        Optional.ofNullable(getAll().get(Integer.valueOf(PLUGIN_CLASS_NAME)))
            .orElseGet(
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.descriptor.PluginDescriptor$$ExternalSyntheticLambda0
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    return PluginDescriptor.this.m358x62801927();
                  }
                });
  }

  /* renamed from: lambda$getPluginClassName$0$com-samsung-android-sume-core-descriptor-PluginDescriptor */
  /* synthetic */ Object m358x62801927() {
    return Optional.ofNullable(getPluginClass())
        .map(
            new Function() { // from class:
                             // com.samsung.android.sume.core.descriptor.PluginDescriptor$$ExternalSyntheticLambda3
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                return ((Class) obj).getName();
              }
            })
        .orElse(null);
  }

  public void setPluginClassName(String pluginClassName) {
    getAll().put(Integer.valueOf(PLUGIN_CLASS_NAME), pluginClassName);
  }

  @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase,
            // com.samsung.android.sume.core.descriptor.MFDescriptor
  public String getFilterId() {
    return (String)
        Stream.of(
                (Object[])
                    new String[] {
                      (String)
                          Optional.ofNullable(getPluginId())
                              .map(
                                  new Function() { // from class:
                                                   // com.samsung.android.sume.core.descriptor.PluginDescriptor$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                      return ((Enum) obj).name();
                                    }
                                  })
                              .orElse(""),
                      (String) Optional.ofNullable(getPluginClassName()).orElse("")
                    })
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.descriptor.PluginDescriptor$$ExternalSyntheticLambda2
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return PluginDescriptor.lambda$getFilterId$1((String) obj);
                  }
                })
            .collect(Collectors.joining("#"));
  }

  static /* synthetic */ boolean lambda$getFilterId$1(String it) {
    return !it.isEmpty();
  }

  public Enum<?> getPluginId() {
    return (Enum) get(PLUGIN_ID);
  }

  public void setPluginId(Enum<?> pluginId) {
    getAll().put(Integer.valueOf(PLUGIN_ID), pluginId);
  }

  public Version getVersion() {
    return (Version) getAll().getOrDefault(Integer.valueOf(PLUGIN_VERSION), new Version(0));
  }

  public void setVersion(String version) {
    getAll().put(Integer.valueOf(PLUGIN_VERSION), new Version(version));
  }

  @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase,
            // com.samsung.android.sume.core.descriptor.MFDescriptor
  public Class<?> getFilterType() {
    return PluginFilter.class;
  }
}
