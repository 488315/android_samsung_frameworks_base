package com.android.systemui.common.data.repository;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.common.data.repository.PackageInstallerMonitor;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class PackageInstallerMonitor$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                PackageInstallerMonitor.Companion companion = PackageInstallerMonitor.Companion;
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "session badging changed ");
            case 1:
                PackageInstallerMonitor.Companion companion2 = PackageInstallerMonitor.Companion;
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "session finished ");
            default:
                PackageInstallerMonitor.Companion companion3 = PackageInstallerMonitor.Companion;
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "session created ");
        }
    }
}
