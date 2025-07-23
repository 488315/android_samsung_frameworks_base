package com.samsung.android.graphics.spr.cache;

import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.samsung.android.graphics.spr.document.debug.SprDebug;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
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

        public SprCache(Bitmap bitmap, int i) {
            this.refCount = 0;
            this.bitmap = bitmap;
            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();
            this.dpi = i;
            this.refCount = 0;
        }

        public synchronized void lock() {
            this.refCount++;
        }

        public synchronized void unlock() {
            this.refCount--;
        }
    }

    public SprCacheManager(String str, int i) {
        this.mHashCode = null;
        this.mName = str;
        this.mHashCode = String.valueOf(i % 10000);
    }

    public void printDebug() {
        synchronized (this.mCacheList) {
            Log.d("SprDrawable", this.mName + NavigationBarInflaterView.KEY_CODE_START + this.mHashCode + ") printDebug start");
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (it.hasNext()) {
                SprCache next = it.next();
                Log.d("SprDrawable", this.mName + NavigationBarInflaterView.KEY_CODE_START + this.mHashCode + ")Cache (" + next.width + ", " + next.height + NavigationBarInflaterView.SIZE_MOD_START + next.dpi + "]) " + next.refCount);
            }
            Log.d("SprDrawable", this.mName + NavigationBarInflaterView.KEY_CODE_START + this.mHashCode + ") printDebug end");
        }
    }

    public void addCache(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        synchronized (this.mCacheList) {
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (true) {
                if (it.hasNext()) {
                    SprCache next = it.next();
                    if (next.width == width && next.height == height && next.dpi == i) {
                        break;
                    }
                } else {
                    this.mCacheList.add(new SprCache(bitmap, i));
                    break;
                }
            }
        }
    }

    public Bitmap getCache(int i, int i2, int i3) {
        Bitmap bitmap;
        synchronized (this.mCacheList) {
            Iterator<SprCache> it = this.mCacheList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    bitmap = null;
                    break;
                }
                SprCache next = it.next();
                if (next.width == i && next.height == i2 && next.dpi == i3) {
                    bitmap = next.bitmap;
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
                SprCache next = it.next();
                if (next.bitmap == bitmap) {
                    next.lock();
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
                SprCache next = it.next();
                if (next.bitmap == bitmap) {
                    next.unlock();
                    if (next.refCount == 0) {
                        this.mCacheList.remove(next);
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
