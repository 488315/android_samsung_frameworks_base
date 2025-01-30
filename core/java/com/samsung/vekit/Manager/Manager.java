package com.samsung.vekit.Manager;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class Manager<T> extends Element {
  protected int index;
  protected ManagerType managerType;
  protected HashMap<Integer, T> map;

  protected Manager(VEContext context, ManagerType type) {
    super(context, ElementType.MANAGER, 0, "Manager");
    this.map = new HashMap<>();
    this.managerType = type;
    this.index = 0;
  }

  protected int generateUniqueId() throws Exception {
    if (this.map.size() >= 2147483646) {
      throw new Exception("Map is full");
    }
    while (this.map.containsKey(Integer.valueOf(this.index))) {
      int i = this.index + 1;
      this.index = i;
      this.index = i % Integer.MAX_VALUE;
    }
    return this.index;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void add(T t) {
    this.map.put(Integer.valueOf(((Element) t).getId()), t);
    this.context.getNativeInterface().create((Element) t);
  }

  public T get(int id) {
    return this.map.get(Integer.valueOf(id));
  }

  public Map<Integer, T> getMap() {
    return Collections.unmodifiableMap(this.map);
  }

  public void remove(int id) {
    ElementType targetElementType = getTargetElementType(this.managerType);
    if (targetElementType == null) {
      Log.m96e(
          this.TAG,
          "Fail to remove item["
              + id
              + "] with managerType["
              + this.managerType
              + NavigationBarInflaterView.SIZE_MOD_END);
    } else {
      this.map.remove(Integer.valueOf(id));
      this.context.getNativeInterface().remove(targetElementType, id);
    }
  }

  /* renamed from: com.samsung.vekit.Manager.Manager$1 */
  static /* synthetic */ class C53861 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ManagerType;

    static {
      int[] iArr = new int[ManagerType.values().length];
      $SwitchMap$com$samsung$vekit$Common$Type$ManagerType = iArr;
      try {
        iArr[ManagerType.CONTENT.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.ITEM.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.LAYER.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.ANIMATION.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ManagerType[ManagerType.FILTER.ordinal()] = 5;
      } catch (NoSuchFieldError e5) {
      }
    }
  }

  private ElementType getTargetElementType(ManagerType managerType) {
    switch (C53861.$SwitchMap$com$samsung$vekit$Common$Type$ManagerType[managerType.ordinal()]) {
      case 1:
        return ElementType.CONTENT;
      case 2:
        return ElementType.ITEM;
      case 3:
        return ElementType.LAYER;
      case 4:
        return ElementType.ANIMATION;
      case 5:
        return ElementType.FILTER;
      default:
        Log.m94d(this.TAG, "unexpected manager type = " + managerType);
        return null;
    }
  }
}
