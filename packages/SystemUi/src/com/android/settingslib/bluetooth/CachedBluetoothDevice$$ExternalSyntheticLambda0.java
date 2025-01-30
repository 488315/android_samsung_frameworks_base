package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import java.io.IOException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CachedBluetoothDevice f$0;

    public /* synthetic */ CachedBluetoothDevice$$ExternalSyntheticLambda0(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        this.$r8$classId = i;
        this.f$0 = cachedBluetoothDevice;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Pair pair;
        Bitmap bitmap;
        switch (this.$r8$classId) {
            case 0:
                CachedBluetoothDevice cachedBluetoothDevice = this.f$0;
                int i = 1;
                if (BluetoothUtils.isAdvancedDetailsHeader(cachedBluetoothDevice.mDevice)) {
                    String stringMetaData = BluetoothUtils.getStringMetaData(cachedBluetoothDevice.mDevice, 5);
                    Uri parse = stringMetaData == null ? null : Uri.parse(stringMetaData);
                    if (parse != null && cachedBluetoothDevice.mDrawableCache.get(parse.toString()) == null) {
                        LruCache<String, BitmapDrawable> lruCache = cachedBluetoothDevice.mDrawableCache;
                        String uri = parse.toString();
                        Context context = cachedBluetoothDevice.mContext;
                        Pair btClassDrawableWithDescription = BluetoothUtils.getBtClassDrawableWithDescription(context, cachedBluetoothDevice);
                        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.bt_nearby_icon_size);
                        Resources resources = context.getResources();
                        if (BluetoothUtils.isAdvancedDetailsHeader(bluetoothDevice)) {
                            String stringMetaData2 = BluetoothUtils.getStringMetaData(bluetoothDevice, 5);
                            Uri parse2 = stringMetaData2 != null ? Uri.parse(stringMetaData2) : null;
                            if (parse2 != null) {
                                try {
                                    context.getContentResolver().takePersistableUriPermission(parse2, 1);
                                } catch (SecurityException e) {
                                    Log.e("BluetoothUtils", "Failed to take persistable permission for: " + parse2, e);
                                }
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), parse2);
                                } catch (IOException e2) {
                                    Log.e("BluetoothUtils", "Failed to get drawable for: " + parse2, e2);
                                } catch (SecurityException e3) {
                                    Log.e("BluetoothUtils", "Failed to get permission for: " + parse2, e3);
                                }
                                if (bitmap != null) {
                                    Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, dimensionPixelSize, dimensionPixelSize, false);
                                    bitmap.recycle();
                                    pair = new Pair(new BitmapDrawable(resources, createScaledBitmap), (String) btClassDrawableWithDescription.second);
                                    lruCache.put(uri, (BitmapDrawable) pair.first);
                                }
                            }
                        }
                        pair = new Pair((Drawable) btClassDrawableWithDescription.first, (String) btClassDrawableWithDescription.second);
                        lruCache.put(uri, (BitmapDrawable) pair.first);
                    }
                }
                ThreadUtils.postOnMainThread(new CachedBluetoothDevice$$ExternalSyntheticLambda0(cachedBluetoothDevice, i));
                break;
            default:
                this.f$0.dispatchAttributesChanged();
                break;
        }
    }
}
