package com.android.systemui;

import android.os.DeadObjectException;
import android.os.Parcel;
import android.util.Log;
import com.android.systemui.shared.launcher.dex.ITaskbarStatusIcon$Stub;
import com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener$Stub$Proxy;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public final class TaskbarIndicatorController extends ITaskbarStatusIcon$Stub {
    public final String TAG = "TaskbarIndicatorController";
    public List mDesktopStatusBarIconCallback;
    public ITaskbarStatusIconListener$Stub$Proxy taskbarStatusIconListener;

    /* renamed from: com.android.systemui.TaskbarIndicatorController$requestStatusIcons$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TaskbarIndicatorController.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            List list = TaskbarIndicatorController.this.mDesktopStatusBarIconCallback;
            if (list != null) {
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    ((StatusBarSignalPolicy.DesktopCallback) obj2).updateDesktopStatusBarIcons();
                }
            }
            return Unit.INSTANCE;
        }
    }

    public final void requestStatusIcons() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new AnonymousClass1(null), 3);
    }

    public final void setDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback) {
        if (this.mDesktopStatusBarIconCallback == null) {
            this.mDesktopStatusBarIconCallback = new ArrayList();
        }
        if (desktopCallback != null) {
            List list = this.mDesktopStatusBarIconCallback;
            list.getClass();
            list.add(desktopCallback);
        }
    }

    public final void setWifiIcon(boolean z, int i, int i2) {
        try {
            ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = this.taskbarStatusIconListener;
            if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                Parcel parcelObtain = Parcel.obtain(iTaskbarStatusIconListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    iTaskbarStatusIconListener$Stub$Proxy.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        } catch (DeadObjectException unused) {
            Log.e(this.TAG, "setWifiIcon taskbarStatusIconListener was dead, but non-null");
        }
    }
}
