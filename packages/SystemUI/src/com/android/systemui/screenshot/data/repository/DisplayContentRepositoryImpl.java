package com.android.systemui.screenshot.data.repository;

import android.app.IActivityTaskManager;
import android.util.Log;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.screenshot.data.model.DisplayContentModel;
import com.android.systemui.screenshot.data.model.SystemUiState;
import com.android.systemui.screenshot.proxy.ScreenshotProxy;
import com.android.systemui.screenshot.proxy.ScreenshotProxyClient;
import java.util.List;
import java.util.function.BiConsumer;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class DisplayContentRepositoryImpl implements DisplayContentRepository {
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher background;
    public final ScreenshotProxy screenshotProxy;

    /* renamed from: com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl$getDisplayContent$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $displayId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$displayId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DisplayContentRepositoryImpl.this.new AnonymousClass2(this.$displayId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            List allRootTaskInfosOnDisplay = DisplayContentRepositoryImpl.this.atmService.getAllRootTaskInfosOnDisplay(this.$displayId);
            DisplayContentRepositoryImpl displayContentRepositoryImpl = DisplayContentRepositoryImpl.this;
            int i2 = this.$displayId;
            allRootTaskInfosOnDisplay.getClass();
            this.label = 1;
            Object objAccess$toDisplayTasksModel = DisplayContentRepositoryImpl.access$toDisplayTasksModel(displayContentRepositoryImpl, i2, allRootTaskInfosOnDisplay, this);
            return objAccess$toDisplayTasksModel == coroutineSingletons ? coroutineSingletons : objAccess$toDisplayTasksModel;
        }
    }

    public DisplayContentRepositoryImpl(IActivityTaskManager iActivityTaskManager, ScreenshotProxy screenshotProxy, CoroutineDispatcher coroutineDispatcher) {
        this.atmService = iActivityTaskManager;
        this.screenshotProxy = screenshotProxy;
        this.background = coroutineDispatcher;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$toDisplayTasksModel(DisplayContentRepositoryImpl displayContentRepositoryImpl, int i, List list, ContinuationImpl continuationImpl) {
        DisplayContentRepositoryImpl$toDisplayTasksModel$1 displayContentRepositoryImpl$toDisplayTasksModel$1;
        displayContentRepositoryImpl.getClass();
        if (continuationImpl instanceof DisplayContentRepositoryImpl$toDisplayTasksModel$1) {
            displayContentRepositoryImpl$toDisplayTasksModel$1 = (DisplayContentRepositoryImpl$toDisplayTasksModel$1) continuationImpl;
            int i2 = displayContentRepositoryImpl$toDisplayTasksModel$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                displayContentRepositoryImpl$toDisplayTasksModel$1.label = i2 - Integer.MIN_VALUE;
            } else {
                displayContentRepositoryImpl$toDisplayTasksModel$1 = new DisplayContentRepositoryImpl$toDisplayTasksModel$1(displayContentRepositoryImpl, continuationImpl);
            }
        }
        Object orThrow = displayContentRepositoryImpl$toDisplayTasksModel$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = displayContentRepositoryImpl$toDisplayTasksModel$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(orThrow);
            displayContentRepositoryImpl$toDisplayTasksModel$1.L$0 = list;
            displayContentRepositoryImpl$toDisplayTasksModel$1.I$0 = i;
            displayContentRepositoryImpl$toDisplayTasksModel$1.label = 1;
            ScreenshotProxyClient screenshotProxyClient = (ScreenshotProxyClient) displayContentRepositoryImpl.screenshotProxy;
            screenshotProxyClient.getClass();
            final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(displayContentRepositoryImpl$toDisplayTasksModel$1));
            AndroidFuture androidFuturePostForResult = screenshotProxyClient.proxyConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1
                public final Object run(Object obj) {
                    return Boolean.valueOf(((IScreenshotProxy) obj).isNotificationShadeExpanded());
                }
            });
            final Function2 function2 = new Function2() { // from class: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Boolean bool = (Boolean) obj;
                    Throwable th = (Throwable) obj2;
                    if (th != null) {
                        Log.wtf("SystemUiProxy", "isNotificationShadeExpanded", th);
                    }
                    int i4 = Result.$r8$clinit;
                    safeContinuation.resumeWith(Boolean.valueOf(bool != null ? bool.booleanValue() : false));
                    return Unit.INSTANCE;
                }
            };
            androidFuturePostForResult.whenComplete(new BiConsumer() { // from class: com.android.systemui.screenshot.proxy.ScreenshotProxyClientKt$sam$java_util_function_BiConsumer$0
                @Override // java.util.function.BiConsumer
                public final /* synthetic */ void accept(Object obj, Object obj2) {
                    function2.invoke(obj, obj2);
                }
            });
            orThrow = safeContinuation.getOrThrow();
            if (orThrow == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = displayContentRepositoryImpl$toDisplayTasksModel$1.I$0;
            list = (List) displayContentRepositoryImpl$toDisplayTasksModel$1.L$0;
            ResultKt.throwOnFailure(orThrow);
        }
        return new DisplayContentModel(i, new SystemUiState(((Boolean) orThrow).booleanValue()), list);
    }

    public final Object getDisplayContent(int i, Continuation continuation) {
        return BuildersKt.withContext(this.background, new AnonymousClass2(i, null), continuation);
    }
}
