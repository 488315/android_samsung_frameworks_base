package com.samsung.android.allshare.media;

import com.samsung.android.allshare.ServiceProvider;

public abstract class MediaServiceProvider extends ServiceProvider {
    @Override // com.samsung.android.allshare.ServiceProvider
    public abstract MediaDeviceFinder getDeviceFinder();
}
