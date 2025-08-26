package com.android.systemui.common.data.repository;

import android.content.pm.PackageInstaller;
import android.text.TextUtils;
import com.android.systemui.common.data.repository.PackageInstallerMonitor;
import com.android.systemui.common.shared.model.PackageInstallSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class PackageInstallerMonitor$_installSessions$1$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ PackageInstallerMonitor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageInstallerMonitor$_installSessions$1$3(PackageInstallerMonitor packageInstallerMonitor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = packageInstallerMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PackageInstallerMonitor$_installSessions$1$3 packageInstallerMonitor$_installSessions$1$3 = new PackageInstallerMonitor$_installSessions$1$3(this.this$0, continuation);
        packageInstallerMonitor$_installSessions$1$3.Z$0 = ((Boolean) obj).booleanValue();
        return packageInstallerMonitor$_installSessions$1$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((PackageInstallerMonitor$_installSessions$1$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.Z$0) {
            PackageInstallerMonitor packageInstallerMonitor = this.this$0;
            synchronized (packageInstallerMonitor.sessions) {
                try {
                    Map map = packageInstallerMonitor.sessions;
                    List<PackageInstaller.SessionInfo> allSessions = packageInstallerMonitor.packageInstaller.getAllSessions();
                    ArrayList arrayList = new ArrayList();
                    for (PackageInstaller.SessionInfo sessionInfo : allSessions) {
                        PackageInstallerMonitor.Companion companion = PackageInstallerMonitor.Companion;
                        sessionInfo.getClass();
                        companion.getClass();
                        PackageInstallSession packageInstallSession = TextUtils.isEmpty(sessionInfo.appPackageName) ? null : new PackageInstallSession(sessionInfo.sessionId, sessionInfo.appPackageName, sessionInfo.getAppIcon(), sessionInfo.getUser());
                        if (packageInstallSession != null) {
                            arrayList.add(packageInstallSession);
                        }
                    }
                    int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    if (iMapCapacity < 16) {
                        iMapCapacity = 16;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj2 = arrayList.get(i);
                        i++;
                        linkedHashMap.put(new Integer(((PackageInstallSession) obj2).sessionId), obj2);
                    }
                    map.putAll(linkedHashMap);
                    packageInstallerMonitor.updateInstallerSessionsFlow();
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            PackageInstallerMonitor packageInstallerMonitor2 = this.this$0;
            packageInstallerMonitor2.packageInstaller.registerSessionCallback(packageInstallerMonitor2, packageInstallerMonitor2.bgHandler);
        } else {
            PackageInstallerMonitor packageInstallerMonitor3 = this.this$0;
            synchronized (packageInstallerMonitor3.sessions) {
                ((LinkedHashMap) packageInstallerMonitor3.sessions).clear();
                packageInstallerMonitor3.updateInstallerSessionsFlow();
                Unit unit2 = Unit.INSTANCE;
            }
            PackageInstallerMonitor packageInstallerMonitor4 = this.this$0;
            packageInstallerMonitor4.packageInstaller.unregisterSessionCallback(packageInstallerMonitor4);
        }
        return Unit.INSTANCE;
    }
}
