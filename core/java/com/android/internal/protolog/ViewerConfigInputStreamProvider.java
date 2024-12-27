package com.android.internal.protolog;

import android.util.proto.ProtoInputStream;

public interface ViewerConfigInputStreamProvider {
    ProtoInputStream getInputStream();
}
