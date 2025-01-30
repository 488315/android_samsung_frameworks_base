package android.content.res;


/* loaded from: classes.dex */
public class ConfigurationBoundResourceCache<T> extends ThemedResourceCache<ConstantState<T>> {
  @Override // android.content.res.ThemedResourceCache
  public /* bridge */ /* synthetic */ void clear() {
    super.clear();
  }

  @Override // android.content.res.ThemedResourceCache
  public /* bridge */ /* synthetic */ Object get(long j, Resources.Theme theme) {
    return super.get(j, theme);
  }

  @Override // android.content.res.ThemedResourceCache
  public /* bridge */ /* synthetic */ int getGeneration() {
    return super.getGeneration();
  }

  @Override // android.content.res.ThemedResourceCache
  public /* bridge */ /* synthetic */ void onConfigurationChange(int i) {
    super.onConfigurationChange(i);
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // android.content.res.ThemedResourceCache
  public /* bridge */ /* synthetic */ void put(long j, Resources.Theme theme, Object obj, int i) {
    super.put(j, theme, obj, i);
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // android.content.res.ThemedResourceCache
  public /* bridge */ /* synthetic */ void put(
      long j, Resources.Theme theme, Object obj, int i, boolean z) {
    super.put(j, theme, obj, i, z);
  }

  public T getInstance(long key, Resources resources, Resources.Theme theme) {
    ConstantState<T> entry = (ConstantState) get(key, theme);
    if (entry != null) {
      return entry.newInstance2(resources, theme);
    }
    return null;
  }

  @Override // android.content.res.ThemedResourceCache
  public boolean shouldInvalidateEntry(ConstantState<T> entry, int configChanges) {
    return Configuration.needNewResources(configChanges, entry.getChangingConfigurations());
  }
}
