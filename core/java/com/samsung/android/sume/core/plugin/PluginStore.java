package com.samsung.android.sume.core.plugin;

import android.content.Context;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda1;
import com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda5;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public abstract class PluginStore {
  private static final String TAG = Def.tagOf((Class<?>) PluginStore.class);
  protected Context context;
  protected Map<String, Entry> registry;

  protected abstract PluginStore add(PluginFixture<?> pluginFixture);

  public abstract Entry get(MFDescriptor mFDescriptor);

  public abstract PluginFixture<?> remove(PluginFixture<?> pluginFixture);

  public static class Entry {
    MFDescriptor descriptor;
    PluginFixture<?> pluginFixture;

    public Entry(MFDescriptor descriptor, PluginFixture<?> pluginFixture) {
      this.descriptor = descriptor;
      this.pluginFixture = pluginFixture;
    }

    public MFDescriptor getDescriptor() {
      return this.descriptor;
    }

    public void setDescriptor(MFDescriptor descriptor) {
      this.descriptor = descriptor;
    }

    public PluginFixture<?> getPluginFixture() {
      return this.pluginFixture;
    }

    public void setPluginFixture(PluginFixture<?> pluginFixture) {
      this.pluginFixture = pluginFixture;
    }
  }

  public PluginStore(Context context) {
    this.registry = new LinkedHashMap();
    this.context = context;
  }

  public PluginStore(Context context, Map<String, Entry> registry) {
    this.context = context;
    this.registry = registry;
  }

  private String getPluginName(Plugin<?> plugin) {
    if (plugin instanceof PluginAdapter) {
      return ((PluginAdapter) plugin).getPluginType().getName();
    }
    Class<?> objClass = plugin.getClass();
    return (String)
        Arrays.stream(objClass.getGenericInterfaces())
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda2
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return PluginStore.lambda$getPluginName$0((Type) obj);
                  }
                })
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda3
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    String typeName;
                    typeName =
                        ((ParameterizedType) ((Type) obj))
                            .getActualTypeArguments()[0].getTypeName();
                    return typeName;
                  }
                })
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda4
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean startsWith;
                    startsWith = ((String) obj).startsWith("com.samsung.android");
                    return startsWith;
                  }
                })
            .findFirst()
            .orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda5());
  }

  static /* synthetic */ boolean lambda$getPluginName$0(Type it) {
    return it instanceof ParameterizedType;
  }

  public PluginStore add(Plugin<?>... plugins) {
    Arrays.stream(plugins)
        .flatMap(
            new Function() { // from class:
                             // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda0
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                return ((Plugin) obj).stream();
              }
            })
        .forEach(
            new Consumer() { // from class:
                             // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                PluginStore.this.m9219lambda$add$3$comsamsungandroidsumecorepluginPluginStore(
                    (Plugin) obj);
              }
            });
    return this;
  }

  /* renamed from: lambda$add$3$com-samsung-android-sume-core-plugin-PluginStore, reason: not valid java name */
  /* synthetic */ void m9219lambda$add$3$comsamsungandroidsumecorepluginPluginStore(Plugin it) {
    PluginFixture<?> fixture;
    try {
      String typeName = getPluginName(it);
      Log.m94d(TAG, "found plugin type:" + typeName);
      if (ImgpPlugin.class.getName().equals(typeName)) {
        fixture = new ImgpPlugin(it);
      } else if (NNPlugin.class.getName().equals(typeName)) {
        fixture = new NNPlugin(it);
      } else {
        throw new IllegalArgumentException("unknown plugin type: " + typeName);
      }
      fixture.init(this.context);
      add(fixture);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public synchronized void clear() {
    this.registry
        .values()
        .forEach(
            new Consumer() { // from class:
                             // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda6
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((PluginStore.Entry) obj).getPluginFixture().release();
              }
            });
    this.registry.clear();
  }

  public Context getContext() {
    return this.context;
  }

  public Stream<String> keyStream() {
    return this.registry.keySet().stream();
  }

  /* renamed from: of */
  public static PluginStore m447of() {
    return m448of((Context) null);
  }

  /* renamed from: of */
  public static PluginStore m448of(Context context) {
    return new StaplePluginStore(context);
  }

  /* renamed from: of */
  public static PluginStore m450of(PluginStore... pluginStores) {
    return m449of((List<PluginStore>) Arrays.asList(pluginStores));
  }

  /* renamed from: of */
  public static PluginStore m449of(List<PluginStore> pluginStores) {
    Def.require(!pluginStores.isEmpty());
    Context context =
        (Context)
            pluginStores.stream()
                .filter(
                    new Predicate() { // from class:
                                      // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda7
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return PluginStore.lambda$of$5((PluginStore) obj);
                      }
                    })
                .findFirst()
                .flatMap(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda8
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Optional ofNullable;
                        ofNullable = Optional.ofNullable(((PluginStore) obj).getContext());
                        return ofNullable;
                      }
                    })
                .orElse(null);
    PluginStore pluginStore = m448of(context);
    if (pluginStores.size() == 1) {
      pluginStore.registry = pluginStores.get(0).registry;
    } else {
      pluginStore.registry =
          (Map)
              pluginStores.stream()
                  .map(
                      new Function() { // from class:
                                       // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda9
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          Map map;
                          map = ((PluginStore) obj).registry;
                          return map;
                        }
                      })
                  .reduce(
                      new BinaryOperator() { // from class:
                                             // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda10
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                          return PluginStore.lambda$of$8((Map) obj, (Map) obj2);
                        }
                      })
                  .orElse(new HashMap());
    }
    return pluginStore;
  }

  static /* synthetic */ boolean lambda$of$5(PluginStore e) {
    return e.context != null;
  }

  static /* synthetic */ Map lambda$of$8(Map x, Map y) {
    return (Map)
        Stream.concat(x.entrySet().stream(), y.entrySet().stream())
            .collect(
                Collectors.toMap(
                    new MediaBufferBase$$ExternalSyntheticLambda1(),
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda5
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return (PluginStore.Entry) ((Map.Entry) obj).getValue();
                      }
                    }));
  }
}
