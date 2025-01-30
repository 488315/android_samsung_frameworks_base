package com.android.server.notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
public class ConcurrentList implements List {
  public final List list;
  public final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  public ConcurrentList(List list) {
    this.list = list;
  }

  @Override // java.util.List, java.util.Collection
  public int size() {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.size();
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean isEmpty() {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.isEmpty();
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean contains(Object obj) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.contains(obj);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection, java.lang.Iterable
  public Iterator iterator() {
    this.readWriteLock.readLock().lock();
    try {
      return new ArrayList(this.list).iterator();
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public Object[] toArray() {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.toArray();
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public Object[] toArray(Object[] objArr) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.toArray(objArr);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean add(Object obj) {
    this.readWriteLock.writeLock().lock();
    try {
      return this.list.add(obj);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean remove(Object obj) {
    this.readWriteLock.writeLock().lock();
    try {
      return this.list.remove(obj);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean containsAll(Collection collection) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.containsAll(collection);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean addAll(Collection collection) {
    this.readWriteLock.writeLock().lock();
    try {
      return this.list.addAll(collection);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List
  public boolean addAll(int i, Collection collection) {
    this.readWriteLock.writeLock().lock();
    try {
      return this.list.addAll(i, collection);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean removeAll(Collection collection) {
    this.readWriteLock.writeLock().lock();
    try {
      return this.list.removeAll(collection);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public boolean retainAll(Collection collection) {
    this.readWriteLock.writeLock().lock();
    try {
      return this.list.retainAll(collection);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List, java.util.Collection
  public void clear() {
    this.readWriteLock.writeLock().lock();
    try {
      this.list.clear();
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List
  public Object get(int i) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.get(i);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List
  public Object set(int i, Object obj) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.set(i, obj);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List
  public void add(int i, Object obj) {
    this.readWriteLock.writeLock().lock();
    try {
      this.list.add(i, obj);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List
  public Object remove(int i) {
    this.readWriteLock.writeLock().lock();
    try {
      return this.list.remove(i);
    } finally {
      this.readWriteLock.writeLock().unlock();
    }
  }

  @Override // java.util.List
  public int indexOf(Object obj) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.indexOf(obj);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List
  public int lastIndexOf(Object obj) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.lastIndexOf(obj);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List
  public ListIterator listIterator() {
    this.readWriteLock.readLock().lock();
    try {
      return new ArrayList(this.list).listIterator();
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List
  public ListIterator listIterator(int i) {
    this.readWriteLock.readLock().lock();
    try {
      return new ArrayList(this.list).listIterator(i);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }

  @Override // java.util.List
  public List subList(int i, int i2) {
    this.readWriteLock.readLock().lock();
    try {
      return this.list.subList(i, i2);
    } finally {
      this.readWriteLock.readLock().unlock();
    }
  }
}
