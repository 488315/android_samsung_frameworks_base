package com.samsung.android.graphics.spr.cache;

import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import com.samsung.android.graphics.spr.document.debug.SprDebug;

import java.util.ArrayList;
import java.util.Iterator;

public class SprCacheManager {
    private ArrayList<SprCache> mCacheList = new ArrayList<>();
    private String mHashCode;
    private String mName;

    private static class SprCache {
        public final Bitmap bitmap;
        public final int dpi;
        public final int height;
        public int refCount;
        public final int width;

        public SprCache(Bitmap b, int d) {
            this.refCount = 0;
            this.bitmap = b;
            this.width = this.bitmap.getWidth();
            this.height = this.bitmap.getHeight();
            this.dpi = d;
            this.refCount = 0;
        }

        public synchronized void lock() {
            this.refCount++;
        }

        public synchronized void unlock() {
            this.refCount--;
        }
    }

    public SprCacheManager(String name, int hashCode) {
        this.mName = null;
        this.mHashCode = null;
        this.mName = name;
        this.mHashCode = String.valueOf(hashCode % 10000);
    }

    public void printDebug() {
        synchronized (this.mCacheList) {
            Log.d(
                    "SprDrawable",
                    this.mName
                            + NavigationBarInflaterView.KEY_CODE_START
                            + this.mHashCode
                            + ") printDebug start");
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (it.hasNext()) {
                SprCache cache = it.next();
                Log.d(
                        "SprDrawable",
                        this.mName
                                + NavigationBarInflaterView.KEY_CODE_START
                                + this.mHashCode
                                + ")Cache ("
                                + cache.width
                                + ", "
                                + cache.height
                                + NavigationBarInflaterView.SIZE_MOD_START
                                + cache.dpi
                                + "]) "
                                + cache.refCount);
            }
            Log.d(
                    "SprDrawable",
                    this.mName
                            + NavigationBarInflaterView.KEY_CODE_START
                            + this.mHashCode
                            + ") printDebug end");
        }
    }

    public void addCache(Bitmap bitmap, int dpi) {
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        synchronized (this.mCacheList) {
            boolean hasCache = false;
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SprCache cache = it.next();
                if (cache.width == width && cache.height == height && cache.dpi == dpi) {
                    hasCache = true;
                    break;
                }
            }
            if (!hasCache) {
                this.mCacheList.add(new SprCache(bitmap, dpi));
            }
        }
    }

    public Bitmap getCache(int width, int height, int dpi) {
        Bitmap bitmap = null;
        synchronized (this.mCacheList) {
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SprCache cache = it.next();
                if (cache.width == width && cache.height == height && cache.dpi == dpi) {
                    bitmap = cache.bitmap;
                    break;
                }
            }
        }
        return bitmap;
    }

    public void lock(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        synchronized (this.mCacheList) {
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SprCache cache = it.next();
                if (cache.bitmap == bitmap) {
                    cache.lock();
                    break;
                }
            }
        }
        if (SprDebug.IsDebug) {
            Log.d("SprDrawable", "-lock--------------------------");
            printDebug();
        }
    }

    public void unlock(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        synchronized (this.mCacheList) {
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SprCache cache = it.next();
                if (cache.bitmap == bitmap) {
                    cache.unlock();
                    if (cache.refCount == 0) {
                        this.mCacheList.remove(cache);
                    }
                }
            }
        }
        if (SprDebug.IsDebug) {
            Log.d("SprDrawable", "-unlock------------------------");
            printDebug();
        }
    }
}
