package com.samsung.android.allshare.extension;

import android.net.Uri;
import com.samsung.android.allshare.Item;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class UniqueItemArray extends ArrayList<Item> {
  private static final long serialVersionUID = 1;
  private HashMap<String, WeakReference<Item>> mCurrentItems = new HashMap<>();
  private Object mAccessLock = new Object();

  @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
  public void add(int index, Item object) {
    if (object == null) {
      return;
    }
    synchronized (this.mAccessLock) {
      if (!isItemContained(object)) {
        super.add(index, (int) object);
      }
    }
  }

  @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection,
            // java.util.Collection, java.util.List
  public boolean add(Item object) {
    if (object == null) {
      return false;
    }
    synchronized (this.mAccessLock) {
      if (isItemContained(object)) {
        return false;
      }
      return super.add((UniqueItemArray) object);
    }
  }

  @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection,
            // java.util.List
  @Deprecated
  public boolean addAll(Collection<? extends Item> collection) {
    if (collection == null) {
      return false;
    }
    if (collection.isEmpty()) {
      return true;
    }
    Iterator<? extends Item> it = collection.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      if (isItemContained(item)) {
        it.remove();
      }
    }
    return super.addAll(collection);
  }

  @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
  public boolean addAll(int index, Collection<? extends Item> collection) {
    if (collection == null) {
      return false;
    }
    if (collection.isEmpty()) {
      return true;
    }
    Iterator<? extends Item> it = collection.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      if (isItemContained(item)) {
        it.remove();
      }
    }
    return super.addAll(index, collection);
  }

  @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection,
            // java.util.Collection, java.util.List
  @Deprecated
  public void clear() {
    synchronized (this.mAccessLock) {
      this.mCurrentItems.clear();
    }
    super.clear();
  }

  private String generateKey(Item item) {
    Uri uri = item.getURI();
    StringBuilder sb =
        new StringBuilder().append(item.getTitle()).append(item.getType().enumToString());
    if (uri != null) {
      sb = sb.append(uri.toString());
    }
    return sb.toString();
  }

  private boolean isItemContained(Item item) {
    String key = generateKey(item);
    if (this.mCurrentItems.containsKey(key)) {
      WeakReference<Item> ref = this.mCurrentItems.get(key);
      if (ref.get() != null) {
        return true;
      }
      return false;
    }
    this.mCurrentItems.put(key, new WeakReference<>(item));
    return false;
  }
}
