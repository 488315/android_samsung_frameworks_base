package com.android.settingslib.satellite;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public final class SatelliteDialogUtils {
    public static final SatelliteDialogUtils INSTANCE = new SatelliteDialogUtils();

    /* renamed from: com.android.settingslib.satellite.SatelliteDialogUtils$mayStartSatelliteWarningDialog$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $allowClick;
        final /* synthetic */ Context $context;
        final /* synthetic */ int $type;
        Object L$0;
        Object L$1;
        int label;

        /* renamed from: com.android.settingslib.satellite.SatelliteDialogUtils$mayStartSatelliteWarningDialog$1$1, reason: invalid class name and collision with other inner class name */
        final class C00491 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function1 $allowClick;
            final /* synthetic */ Ref$BooleanRef $isSatelliteModeOn;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00491(Function1 function1, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
                super(2, continuation);
                this.$allowClick = function1;
                this.$isSatelliteModeOn = ref$BooleanRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00491(this.$allowClick, this.$isSatelliteModeOn, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00491) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$allowClick.mo781invoke(Boolean.valueOf(!this.$isSatelliteModeOn.element));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, int i, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$type = i;
            this.$allowClick = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$context, this.$type, this.$allowClick, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:38:0x00d5, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r9, r1, r8) != r0) goto L40;
         */
        /* JADX WARN: Removed duplicated region for block: B:36:0x008f  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Ref$BooleanRef ref$BooleanRef;
            Ref$BooleanRef ref$BooleanRef2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ref$BooleanRef = new Ref$BooleanRef();
                try {
                    SatelliteDialogUtils satelliteDialogUtils = SatelliteDialogUtils.INSTANCE;
                    Context context = this.$context;
                    this.L$0 = ref$BooleanRef;
                    this.L$1 = ref$BooleanRef;
                    this.label = 1;
                    obj = SatelliteDialogUtils.access$requestIsSessionStarted(satelliteDialogUtils, context, this);
                    if (obj != coroutineSingletons) {
                        ref$BooleanRef2 = ref$BooleanRef;
                    }
                } catch (InterruptedException e) {
                    e = e;
                    ref$BooleanRef2 = ref$BooleanRef;
                    Log.w("SatelliteDialogUtils", "Error to get satellite status : " + e);
                    if (ref$BooleanRef2.element) {
                    }
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
                    C00491 c00491 = new C00491(this.$allowClick, ref$BooleanRef2, null);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.label = 2;
                } catch (ExecutionException e2) {
                    e = e2;
                    ref$BooleanRef2 = ref$BooleanRef;
                    Log.w("SatelliteDialogUtils", "Error to get satellite status : " + e);
                    if (ref$BooleanRef2.element) {
                    }
                    DefaultScheduler defaultScheduler2 = Dispatchers.Default;
                    HandlerContext handlerContext2 = MainDispatcherLoader.dispatcher;
                    C00491 c004912 = new C00491(this.$allowClick, ref$BooleanRef2, null);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.label = 2;
                } catch (TimeoutException e3) {
                    e = e3;
                    ref$BooleanRef2 = ref$BooleanRef;
                    Log.w("SatelliteDialogUtils", "Error to get satellite status : " + e);
                    if (ref$BooleanRef2.element) {
                    }
                    DefaultScheduler defaultScheduler22 = Dispatchers.Default;
                    HandlerContext handlerContext22 = MainDispatcherLoader.dispatcher;
                    C00491 c0049122 = new C00491(this.$allowClick, ref$BooleanRef2, null);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.label = 2;
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ref$BooleanRef = (Ref$BooleanRef) this.L$1;
            ref$BooleanRef2 = (Ref$BooleanRef) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (InterruptedException e4) {
                e = e4;
                Log.w("SatelliteDialogUtils", "Error to get satellite status : " + e);
                if (ref$BooleanRef2.element) {
                }
                DefaultScheduler defaultScheduler222 = Dispatchers.Default;
                HandlerContext handlerContext222 = MainDispatcherLoader.dispatcher;
                C00491 c00491222 = new C00491(this.$allowClick, ref$BooleanRef2, null);
                this.L$0 = null;
                this.L$1 = null;
                this.label = 2;
            } catch (ExecutionException e5) {
                e = e5;
                Log.w("SatelliteDialogUtils", "Error to get satellite status : " + e);
                if (ref$BooleanRef2.element) {
                }
                DefaultScheduler defaultScheduler2222 = Dispatchers.Default;
                HandlerContext handlerContext2222 = MainDispatcherLoader.dispatcher;
                C00491 c004912222 = new C00491(this.$allowClick, ref$BooleanRef2, null);
                this.L$0 = null;
                this.L$1 = null;
                this.label = 2;
            } catch (TimeoutException e6) {
                e = e6;
                Log.w("SatelliteDialogUtils", "Error to get satellite status : " + e);
                if (ref$BooleanRef2.element) {
                }
                DefaultScheduler defaultScheduler22222 = Dispatchers.Default;
                HandlerContext handlerContext22222 = MainDispatcherLoader.dispatcher;
                C00491 c0049122222 = new C00491(this.$allowClick, ref$BooleanRef2, null);
                this.L$0 = null;
                this.L$1 = null;
                this.label = 2;
            }
            ref$BooleanRef.element = ((Boolean) obj).booleanValue();
            if (ref$BooleanRef2.element) {
                SatelliteDialogUtils satelliteDialogUtils2 = SatelliteDialogUtils.INSTANCE;
                Context context2 = this.$context;
                int i2 = this.$type;
                satelliteDialogUtils2.getClass();
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.network.SatelliteWarningDialogActivity"));
                intent.putExtra("dialog_window_type", 2009);
                intent.putExtra("extra_type_of_satellite_warning_dialog", i2);
                intent.addFlags(805306368);
                context2.startActivity(intent);
            }
            DefaultScheduler defaultScheduler222222 = Dispatchers.Default;
            HandlerContext handlerContext222222 = MainDispatcherLoader.dispatcher;
            C00491 c00491222222 = new C00491(this.$allowClick, ref$BooleanRef2, null);
            this.L$0 = null;
            this.L$1 = null;
            this.label = 2;
        }
    }

    private SatelliteDialogUtils() {
    }

    public static final Object access$requestIsSessionStarted(SatelliteDialogUtils satelliteDialogUtils, Context context, Continuation continuation) {
        satelliteDialogUtils.getClass();
        return BuildersKt.withContext(Dispatchers.Default, new SatelliteDialogUtils$requestIsSessionStarted$2(context, null), continuation);
    }

    public static final StandaloneCoroutine mayStartSatelliteWarningDialog(Context context, CoroutineScope coroutineScope, Function1 function1) {
        return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(context, 0, function1, null), 3);
    }
}
