package com.android.server.backup.transport;

import android.util.AndroidException;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class TransportNotRegisteredException extends AndroidException {
    public TransportNotRegisteredException(String str) {
        super(XmlUtils$$ExternalSyntheticOutline0.m("Transport ", str, " not registered"));
    }
}
