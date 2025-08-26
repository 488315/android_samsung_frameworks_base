package com.airbnb.lottie.compose;

import android.content.Context;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda0;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda1;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda2;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.compose.LottieCompositionSpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public abstract class RememberLottieCompositionKt {

    /* renamed from: com.airbnb.lottie.compose.RememberLottieCompositionKt$rememberLottieComposition$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            ((Number) obj).intValue();
            new AnonymousClass1((Continuation) obj3).invokeSuspend(Unit.INSTANCE);
            return Boolean.FALSE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.FALSE;
        }
    }

    /* renamed from: com.airbnb.lottie.compose.RememberLottieCompositionKt$rememberLottieComposition$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $cacheKey;
        final /* synthetic */ Context $context;
        final /* synthetic */ String $fontAssetsFolder;
        final /* synthetic */ String $fontFileExtension;
        final /* synthetic */ String $imageAssetsFolder;
        final /* synthetic */ Function3 $onRetry;
        final /* synthetic */ MutableState<LottieCompositionResultImpl> $result$delegate;
        final /* synthetic */ LottieCompositionSpec $spec;
        int I$0;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Function3 function3, Context context, LottieCompositionSpec lottieCompositionSpec, String str, String str2, String str3, String str4, MutableState<LottieCompositionResultImpl> mutableState, Continuation continuation) {
            super(2, continuation);
            this.$onRetry = function3;
            this.$context = context;
            this.$spec = lottieCompositionSpec;
            this.$imageAssetsFolder = str;
            this.$fontAssetsFolder = str2;
            this.$fontFileExtension = str3;
            this.$cacheKey = str4;
            this.$result$delegate = mutableState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$onRetry, this.$context, this.$spec, this.$imageAssetsFolder, this.$fontAssetsFolder, this.$fontFileExtension, this.$cacheKey, this.$result$delegate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(11:24|(1:99)|26|101|27|(1:33)|32|35|36|95|37) */
        /* JADX WARN: Can't wrap try/catch for region: R(11:24|99|26|101|27|(1:33)|32|35|36|95|37) */
        /* JADX WARN: Can't wrap try/catch for region: R(19:6|(3:97|7|8)|91|40|87|41|103|c3|43|(1:45)(2:47|48)|49|50|16|(2:(1:19)(11:24|99|26|101|27|(1:33)|32|35|36|95|37)|39)|25|69|(2:72|124)|85|86) */
        /* JADX WARN: Can't wrap try/catch for region: R(21:6|97|7|8|91|40|87|41|103|c3|43|(1:45)(2:47|48)|49|50|16|(2:(1:19)(11:24|99|26|101|27|(1:33)|32|35|36|95|37)|39)|25|69|(2:72|124)|85|86) */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0064, code lost:
        
            if (r14 == r1) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x006d, code lost:
        
            if (((java.lang.Boolean) r14).booleanValue() != false) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00b6, code lost:
        
            if (r14 == r1) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00eb, code lost:
        
            r14 = r13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00ee, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00ef, code lost:
        
            r13 = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00f1, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00f2, code lost:
        
            r13 = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00f4, code lost:
        
            r0 = th;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00f5, code lost:
        
            r14 = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00f8, code lost:
        
            r0 = th;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00fd, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00fe, code lost:
        
            r11 = r13;
            r13 = r0;
         */
        /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x011a A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:89:0x0125 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x00b6 -> B:91:0x00b9). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x00f5 -> B:11:0x001b). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:67:0x00fe -> B:55:0x00eb). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Throwable th;
            int i;
            AnonymousClass3 anonymousClass3;
            LottieCompositionResultImpl lottieCompositionResultImpl;
            AnonymousClass3 anonymousClass32;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                th = null;
                i = 0;
                if (!((Boolean) ((LottieCompositionResultImpl) this.$result$delegate.getValue()).isSuccess$delegate.getValue()).booleanValue()) {
                }
                anonymousClass3 = this;
                if (!((Boolean) ((LottieCompositionResultImpl) anonymousClass3.$result$delegate.getValue()).isComplete$delegate.getValue()).booleanValue()) {
                    lottieCompositionResultImpl = (LottieCompositionResultImpl) anonymousClass3.$result$delegate.getValue();
                    synchronized (lottieCompositionResultImpl) {
                    }
                }
                return Unit.INSTANCE;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                int i3 = this.I$0;
                Throwable th2 = (Throwable) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    anonymousClass32 = this;
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    anonymousClass32 = this;
                    int i4 = i3;
                    th = th4;
                    i = i4 + 1;
                    this = anonymousClass32;
                    if (!((Boolean) ((LottieCompositionResultImpl) this.$result$delegate.getValue()).isSuccess$delegate.getValue()).booleanValue()) {
                    }
                    anonymousClass3 = this;
                    if (!((Boolean) ((LottieCompositionResultImpl) anonymousClass3.$result$delegate.getValue()).isComplete$delegate.getValue()).booleanValue()) {
                    }
                    return Unit.INSTANCE;
                }
                LottieComposition lottieComposition = (LottieComposition) obj;
                LottieCompositionResultImpl lottieCompositionResultImpl2 = (LottieCompositionResultImpl) anonymousClass32.$result$delegate.getValue();
                synchronized (lottieCompositionResultImpl2) {
                }
                if (!((Boolean) lottieCompositionResultImpl2.isComplete$delegate.getValue()).booleanValue()) {
                    ((SnapshotMutableStateImpl) lottieCompositionResultImpl2.value$delegate).setValue(lottieComposition);
                    lottieCompositionResultImpl2.compositionDeferred.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(lottieComposition);
                }
                int i5 = i3;
                th = th2;
                i = i5;
                this = anonymousClass32;
                if (!((Boolean) ((LottieCompositionResultImpl) this.$result$delegate.getValue()).isSuccess$delegate.getValue()).booleanValue()) {
                    if (i != 0) {
                        Function3 function3 = this.$onRetry;
                        Integer num = new Integer(i);
                        th.getClass();
                        this.L$0 = th;
                        this.I$0 = i;
                        this.label = 1;
                        obj = function3.invoke(num, th, this);
                    } else {
                        Throwable th5 = th;
                        i3 = i;
                        th2 = th5;
                        try {
                        } catch (Throwable th6) {
                            Throwable th7 = th6;
                            anonymousClass32 = this;
                        }
                        Context context = this.$context;
                        LottieCompositionSpec lottieCompositionSpec = this.$spec;
                        String strAccess$ensureTrailingSlash = RememberLottieCompositionKt.access$ensureTrailingSlash(this.$imageAssetsFolder);
                        String strAccess$ensureTrailingSlash2 = RememberLottieCompositionKt.access$ensureTrailingSlash(this.$fontAssetsFolder);
                        String str = this.$fontFileExtension;
                        if (!StringsKt__StringsKt.isBlank(str) && !str.startsWith(".")) {
                            str = "." + ((Object) str);
                        }
                        String str2 = str;
                        String str3 = this.$cacheKey;
                        this.L$0 = th2;
                        this.I$0 = i3;
                        this.label = 2;
                        anonymousClass32 = this;
                        obj = RememberLottieCompositionKt.access$lottieComposition(context, lottieCompositionSpec, strAccess$ensureTrailingSlash, strAccess$ensureTrailingSlash2, str2, str3, anonymousClass32);
                    }
                    return coroutineSingletons;
                }
                anonymousClass3 = this;
                if (!((Boolean) ((LottieCompositionResultImpl) anonymousClass3.$result$delegate.getValue()).isComplete$delegate.getValue()).booleanValue() && th != null) {
                    lottieCompositionResultImpl = (LottieCompositionResultImpl) anonymousClass3.$result$delegate.getValue();
                    synchronized (lottieCompositionResultImpl) {
                        if (!((Boolean) lottieCompositionResultImpl.isComplete$delegate.getValue()).booleanValue()) {
                            ((SnapshotMutableStateImpl) lottieCompositionResultImpl.error$delegate).setValue(th);
                            lottieCompositionResultImpl.compositionDeferred.completeExceptionally(th);
                        }
                    }
                }
                return Unit.INSTANCE;
            }
            i = this.I$0;
            th = (Throwable) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
    }

    public static final String access$ensureTrailingSlash(String str) {
        if (str == null || StringsKt__StringsKt.isBlank(str)) {
            return null;
        }
        return (str.length() <= 0 || !CharsKt__CharKt.equals(str.charAt(str.length() + (-1)), '/', false)) ? str.concat("/") : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0112 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$lottieComposition(Context context, LottieCompositionSpec lottieCompositionSpec, String str, String str2, String str3, String str4, ContinuationImpl continuationImpl) throws Throwable {
        RememberLottieCompositionKt$lottieComposition$1 rememberLottieCompositionKt$lottieComposition$1;
        String str5;
        String str6;
        Context context2;
        String str7;
        Object objWithContext;
        Context context3;
        LottieComposition lottieComposition;
        String str8;
        Object objWithContext2;
        if (continuationImpl instanceof RememberLottieCompositionKt$lottieComposition$1) {
            rememberLottieCompositionKt$lottieComposition$1 = (RememberLottieCompositionKt$lottieComposition$1) continuationImpl;
            int i = rememberLottieCompositionKt$lottieComposition$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                rememberLottieCompositionKt$lottieComposition$1.label = i - Integer.MIN_VALUE;
            } else {
                rememberLottieCompositionKt$lottieComposition$1 = new RememberLottieCompositionKt$lottieComposition$1(continuationImpl);
            }
        }
        Object result = rememberLottieCompositionKt$lottieComposition$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = rememberLottieCompositionKt$lottieComposition$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(result);
            LottieTask lottieTask = lottieTask(context, lottieCompositionSpec, str4, false);
            if (lottieTask == null) {
                throw new IllegalArgumentException(("Unable to create parsing task for " + lottieCompositionSpec + '.').toString());
            }
            rememberLottieCompositionKt$lottieComposition$1.L$0 = context;
            rememberLottieCompositionKt$lottieComposition$1.L$1 = str;
            str5 = str2;
            rememberLottieCompositionKt$lottieComposition$1.L$2 = str5;
            str6 = str3;
            rememberLottieCompositionKt$lottieComposition$1.L$3 = str6;
            rememberLottieCompositionKt$lottieComposition$1.label = 1;
            final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(rememberLottieCompositionKt$lottieComposition$1), 1);
            cancellableContinuationImpl.initCancellability();
            lottieTask.addListener(new LottieListener() { // from class: com.airbnb.lottie.compose.RememberLottieCompositionKt$await$2$1
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    if (cancellableContinuation.isCompleted()) {
                        return;
                    }
                    int i3 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(obj);
                }
            });
            lottieTask.addFailureListener(new LottieListener() { // from class: com.airbnb.lottie.compose.RememberLottieCompositionKt$await$2$2
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    Throwable th = (Throwable) obj;
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    if (cancellableContinuation.isCompleted()) {
                        return;
                    }
                    int i3 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(new Result.Failure(th));
                }
            });
            result = cancellableContinuationImpl.getResult();
            if (result != coroutineSingletons) {
                context2 = context;
                str7 = str;
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                LottieComposition lottieComposition2 = (LottieComposition) rememberLottieCompositionKt$lottieComposition$1.L$0;
                ResultKt.throwOnFailure(result);
                return lottieComposition2;
            }
            lottieComposition = (LottieComposition) rememberLottieCompositionKt$lottieComposition$1.L$3;
            str8 = (String) rememberLottieCompositionKt$lottieComposition$1.L$2;
            str5 = (String) rememberLottieCompositionKt$lottieComposition$1.L$1;
            context3 = (Context) rememberLottieCompositionKt$lottieComposition$1.L$0;
            ResultKt.throwOnFailure(result);
            rememberLottieCompositionKt$lottieComposition$1.L$0 = lottieComposition;
            rememberLottieCompositionKt$lottieComposition$1.L$1 = null;
            rememberLottieCompositionKt$lottieComposition$1.L$2 = null;
            rememberLottieCompositionKt$lottieComposition$1.L$3 = null;
            rememberLottieCompositionKt$lottieComposition$1.label = 3;
            if (((HashMap) lottieComposition.fonts).isEmpty()) {
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                objWithContext2 = BuildersKt.withContext(DefaultIoScheduler.INSTANCE, new RememberLottieCompositionKt$loadFontsFromAssets$2(lottieComposition, context3, str5, str8, null), rememberLottieCompositionKt$lottieComposition$1);
                if (objWithContext2 != coroutineSingletons) {
                    objWithContext2 = Unit.INSTANCE;
                }
            } else {
                objWithContext2 = Unit.INSTANCE;
            }
            return objWithContext2 != coroutineSingletons ? coroutineSingletons : lottieComposition;
        }
        String str9 = (String) rememberLottieCompositionKt$lottieComposition$1.L$3;
        String str10 = (String) rememberLottieCompositionKt$lottieComposition$1.L$2;
        String str11 = (String) rememberLottieCompositionKt$lottieComposition$1.L$1;
        Context context4 = (Context) rememberLottieCompositionKt$lottieComposition$1.L$0;
        ResultKt.throwOnFailure(result);
        str5 = str10;
        str7 = str11;
        str6 = str9;
        context2 = context4;
        LottieComposition lottieComposition3 = (LottieComposition) result;
        rememberLottieCompositionKt$lottieComposition$1.L$0 = context2;
        rememberLottieCompositionKt$lottieComposition$1.L$1 = str5;
        rememberLottieCompositionKt$lottieComposition$1.L$2 = str6;
        rememberLottieCompositionKt$lottieComposition$1.L$3 = lottieComposition3;
        rememberLottieCompositionKt$lottieComposition$1.label = 2;
        if (((HashMap) lottieComposition3.images).isEmpty()) {
            objWithContext = Unit.INSTANCE;
        } else {
            DefaultScheduler defaultScheduler2 = Dispatchers.Default;
            objWithContext = BuildersKt.withContext(DefaultIoScheduler.INSTANCE, new RememberLottieCompositionKt$loadImagesFromAssets$2(lottieComposition3, context2, str7, null), rememberLottieCompositionKt$lottieComposition$1);
            if (objWithContext != coroutineSingletons) {
                objWithContext = Unit.INSTANCE;
            }
        }
        if (objWithContext != coroutineSingletons) {
            context3 = context2;
            lottieComposition = lottieComposition3;
            str8 = str6;
            rememberLottieCompositionKt$lottieComposition$1.L$0 = lottieComposition;
            rememberLottieCompositionKt$lottieComposition$1.L$1 = null;
            rememberLottieCompositionKt$lottieComposition$1.L$2 = null;
            rememberLottieCompositionKt$lottieComposition$1.L$3 = null;
            rememberLottieCompositionKt$lottieComposition$1.label = 3;
            if (((HashMap) lottieComposition.fonts).isEmpty()) {
            }
            if (objWithContext2 != coroutineSingletons) {
            }
        }
    }

    public static final LottieTask lottieTask(Context context, LottieCompositionSpec lottieCompositionSpec, String str, boolean z) throws FileNotFoundException {
        if (lottieCompositionSpec instanceof LottieCompositionSpec.RawRes) {
            return Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__") ? LottieCompositionFactory.fromRawRes(((LottieCompositionSpec.RawRes) lottieCompositionSpec).resId, context) : LottieCompositionFactory.fromRawRes(context, str, ((LottieCompositionSpec.RawRes) lottieCompositionSpec).resId);
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.Url) {
            if (!Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda0(context, ((LottieCompositionSpec.Url) lottieCompositionSpec).url, str, 0), null);
            }
            String str2 = ((LottieCompositionSpec.Url) lottieCompositionSpec).url;
            Map map = LottieCompositionFactory.taskCache;
            String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("url_", str2);
            return LottieCompositionFactory.cache(strM, new LottieCompositionFactory$$ExternalSyntheticLambda0(context, str2, strM, 0), null);
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.File) {
            if (z) {
                return null;
            }
            LottieCompositionSpec.File file = (LottieCompositionSpec.File) lottieCompositionSpec;
            FileInputStream fileInputStream = new FileInputStream(file.fileName);
            String str3 = file.fileName;
            if (!str3.endsWith("zip")) {
                if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                    str = str3;
                }
                return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(fileInputStream, str), new LottieCompositionFactory$$ExternalSyntheticLambda2(fileInputStream));
            }
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                str = str3;
            }
            return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(zipInputStream, str), new LottieCompositionFactory$$ExternalSyntheticLambda2(zipInputStream));
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.Asset) {
            if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                return LottieCompositionFactory.fromAsset(context, ((LottieCompositionSpec.Asset) lottieCompositionSpec).assetName);
            }
            String str4 = ((LottieCompositionSpec.Asset) lottieCompositionSpec).assetName;
            Map map2 = LottieCompositionFactory.taskCache;
            return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda0(context.getApplicationContext(), str4, str, 1), null);
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.JsonString) {
            if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                str = String.valueOf(((LottieCompositionSpec.JsonString) lottieCompositionSpec).jsonString.hashCode());
            }
            return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(((LottieCompositionSpec.JsonString) lottieCompositionSpec).jsonString, str), null);
        }
        if (!(lottieCompositionSpec instanceof LottieCompositionSpec.ContentProvider)) {
            throw new NoWhenBranchMatchedException();
        }
        LottieCompositionSpec.ContentProvider contentProvider = (LottieCompositionSpec.ContentProvider) lottieCompositionSpec;
        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(contentProvider.uri);
        if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
            str = contentProvider.uri.toString();
        }
        return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(inputStreamOpenInputStream, str), new LottieCompositionFactory$$ExternalSyntheticLambda2(inputStreamOpenInputStream));
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final LottieCompositionResultImpl rememberLottieComposition(LottieCompositionSpec.RawRes rawRes, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(1388713460);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceableGroup(-3686930);
        boolean zChanged = composerImpl.changed(rawRes);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(new LottieCompositionResultImpl());
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        MutableState mutableState = (MutableState) objRememberedValue;
        composerImpl.startReplaceableGroup(-3686552);
        boolean zChanged2 = composerImpl.changed(rawRes) | composerImpl.changed("__LottieInternalDefaultCacheKey__");
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChanged2) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                composerImpl.updateRememberedValue(lottieTask(context, rawRes, "__LottieInternalDefaultCacheKey__", true));
            }
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(rawRes, "__LottieInternalDefaultCacheKey__", new AnonymousClass3(anonymousClass1, context, rawRes, null, "fonts/", ".ttf", "__LottieInternalDefaultCacheKey__", mutableState, null), composerImpl);
        LottieCompositionResultImpl lottieCompositionResultImpl = (LottieCompositionResultImpl) mutableState.getValue();
        composerImpl.end(false);
        return lottieCompositionResultImpl;
    }
}
