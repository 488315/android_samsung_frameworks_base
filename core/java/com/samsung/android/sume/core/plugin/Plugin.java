package com.samsung.android.sume.core.plugin;

import java.util.stream.Stream;

/* loaded from: classes4.dex */
public interface Plugin<T extends PluginFixture<?>> {
  void bindToFixture(T t);

  default Stream<Plugin<? extends PluginFixture<?>>> stream() {
    return Stream.of(this);
  }
}
