package androidx.compose.ui.text.font;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.text.font.TypefaceResult;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.YieldKt;

/* loaded from: classes.dex */
public final class AsyncFontListLoader implements State<Object> {
    public final AsyncTypefaceCache asyncTypefaceCache;
    public boolean cacheable = true;
    public final List fontList;
    public final Function1 onCompletion;
    public final PlatformFontLoader platformFontLoader;
    public final TypefaceRequest typefaceRequest;
    public final MutableState value$delegate;

    /* renamed from: androidx.compose.ui.text.font.AsyncFontListLoader$load$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AsyncFontListLoader.this.load(this);
        }
    }

    public AsyncFontListLoader(List<? extends Font> list, Object obj, TypefaceRequest typefaceRequest, AsyncTypefaceCache asyncTypefaceCache, Function1 function1, PlatformFontLoader platformFontLoader) {
        this.fontList = list;
        this.typefaceRequest = typefaceRequest;
        this.asyncTypefaceCache = asyncTypefaceCache;
        this.onCompletion = function1;
        this.platformFontLoader = platformFontLoader;
        this.value$delegate = SnapshotStateKt.mutableStateOf$default(obj);
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return ((SnapshotMutableStateImpl) this.value$delegate).getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0070 A[Catch: all -> 0x00d6, TryCatch #0 {all -> 0x00d6, blocks: (B:27:0x0070, B:29:0x0083, B:34:0x00a5, B:36:0x00b3, B:41:0x00da, B:25:0x0064), top: B:51:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a5 A[Catch: all -> 0x00d6, TRY_LEAVE, TryCatch #0 {all -> 0x00d6, blocks: (B:27:0x0070, B:29:0x0083, B:34:0x00a5, B:36:0x00b3, B:41:0x00da, B:25:0x0064), top: B:51:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00da A[Catch: all -> 0x00d6, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x00d6, blocks: (B:27:0x0070, B:29:0x0083, B:34:0x00a5, B:36:0x00b3, B:41:0x00da, B:25:0x0064), top: B:51:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x0081 -> B:46:0x00f6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:44:0x00ed -> B:45:0x00f2). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object load(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        List list;
        int size;
        int i;
        Throwable th;
        AsyncFontListLoader asyncFontListLoader;
        Font font;
        List list2;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 != 0) {
            if (i3 == 1) {
                int i4 = anonymousClass1.I$1;
                int i5 = anonymousClass1.I$0;
                Font font2 = (Font) anonymousClass1.L$2;
                list2 = (List) anonymousClass1.L$1;
                AsyncFontListLoader asyncFontListLoader2 = (AsyncFontListLoader) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    size = i4;
                    this = asyncFontListLoader2;
                    font = font2;
                    i = i5;
                    if (obj == null) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    asyncFontListLoader = asyncFontListLoader2;
                }
            } else {
                if (i3 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                int i6 = anonymousClass1.I$1;
                int i7 = anonymousClass1.I$0;
                List list3 = (List) anonymousClass1.L$1;
                asyncFontListLoader = (AsyncFontListLoader) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    list = list3;
                    i = i7;
                    size = i6;
                    this = asyncFontListLoader;
                    i++;
                    if (i < size) {
                        Font font3 = (Font) list.get(i);
                        int iMo759getLoadingStrategyPKNRLFQ = font3.mo759getLoadingStrategyPKNRLFQ();
                        FontLoadingStrategy.Companion.getClass();
                        if (iMo759getLoadingStrategyPKNRLFQ == FontLoadingStrategy.Async) {
                            AsyncTypefaceCache asyncTypefaceCache = this.asyncTypefaceCache;
                            PlatformFontLoader platformFontLoader = this.platformFontLoader;
                            AsyncFontListLoader$load$2$typeface$1 asyncFontListLoader$load$2$typeface$1 = new AsyncFontListLoader$load$2$typeface$1(this, font3, null);
                            anonymousClass1.L$0 = this;
                            anonymousClass1.L$1 = list;
                            anonymousClass1.L$2 = font3;
                            anonymousClass1.I$0 = i;
                            anonymousClass1.I$1 = size;
                            anonymousClass1.label = 1;
                            Object objRunCached = asyncTypefaceCache.runCached(font3, platformFontLoader, asyncFontListLoader$load$2$typeface$1, anonymousClass1);
                            if (objRunCached == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            list2 = list;
                            obj = objRunCached;
                            font = font3;
                            if (obj == null) {
                                TypefaceRequest typefaceRequest = this.typefaceRequest;
                                Object objM770synthesizeTypefaceFxwP2eA = FontSynthesis_androidKt.m770synthesizeTypefaceFxwP2eA(typefaceRequest.fontSynthesis, obj, font, typefaceRequest.fontWeight, typefaceRequest.fontStyle);
                                MutableState mutableState = this.value$delegate;
                                ((SnapshotMutableStateImpl) mutableState).setValue(objM770synthesizeTypefaceFxwP2eA);
                                Unit unit = Unit.INSTANCE;
                                boolean zIsActive = JobKt.isActive(anonymousClass1.getContext());
                                this.cacheable = false;
                                this.onCompletion.mo781invoke(new TypefaceResult.Immutable(((SnapshotMutableStateImpl) mutableState).getValue(), zIsActive));
                                return unit;
                            }
                            anonymousClass1.L$0 = this;
                            anonymousClass1.L$1 = list2;
                            anonymousClass1.L$2 = null;
                            anonymousClass1.I$0 = i;
                            anonymousClass1.I$1 = size;
                            anonymousClass1.label = 2;
                            if (YieldKt.yield(anonymousClass1) != coroutineSingletons) {
                                List list4 = list2;
                                asyncFontListLoader = this;
                                i6 = size;
                                i7 = i;
                                list3 = list4;
                                list = list3;
                                i = i7;
                                size = i6;
                                this = asyncFontListLoader;
                            }
                            return coroutineSingletons;
                        }
                        i++;
                        if (i < size) {
                            boolean zIsActive2 = JobKt.isActive(anonymousClass1.getContext());
                            this.cacheable = false;
                            this.onCompletion.mo781invoke(new TypefaceResult.Immutable(((SnapshotMutableStateImpl) this.value$delegate).getValue(), zIsActive2));
                            return Unit.INSTANCE;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            boolean zIsActive3 = JobKt.isActive(anonymousClass1.getContext());
            asyncFontListLoader.cacheable = false;
            asyncFontListLoader.onCompletion.mo781invoke(new TypefaceResult.Immutable(((SnapshotMutableStateImpl) asyncFontListLoader.value$delegate).getValue(), zIsActive3));
            throw th;
        }
        ResultKt.throwOnFailure(obj);
        try {
            list = this.fontList;
            size = list.size();
            i = 0;
            if (i < size) {
            }
        } catch (Throwable th4) {
            asyncFontListLoader = this;
            th = th4;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadWithTimeoutOrNull$ui_text_release(Font font, ContinuationImpl continuationImpl) {
        AsyncFontListLoader$loadWithTimeoutOrNull$1 asyncFontListLoader$loadWithTimeoutOrNull$1;
        if (continuationImpl instanceof AsyncFontListLoader$loadWithTimeoutOrNull$1) {
            asyncFontListLoader$loadWithTimeoutOrNull$1 = (AsyncFontListLoader$loadWithTimeoutOrNull$1) continuationImpl;
            int i = asyncFontListLoader$loadWithTimeoutOrNull$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                asyncFontListLoader$loadWithTimeoutOrNull$1.label = i - Integer.MIN_VALUE;
            } else {
                asyncFontListLoader$loadWithTimeoutOrNull$1 = new AsyncFontListLoader$loadWithTimeoutOrNull$1(this, continuationImpl);
            }
        }
        Object obj = asyncFontListLoader$loadWithTimeoutOrNull$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = asyncFontListLoader$loadWithTimeoutOrNull$1.label;
        try {
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            AsyncFontListLoader$loadWithTimeoutOrNull$2 asyncFontListLoader$loadWithTimeoutOrNull$2 = new AsyncFontListLoader$loadWithTimeoutOrNull$2(this, font, null);
            asyncFontListLoader$loadWithTimeoutOrNull$1.L$0 = font;
            asyncFontListLoader$loadWithTimeoutOrNull$1.label = 1;
            Object objWithTimeoutOrNull = TimeoutKt.withTimeoutOrNull(15000L, asyncFontListLoader$loadWithTimeoutOrNull$2, asyncFontListLoader$loadWithTimeoutOrNull$1);
            return objWithTimeoutOrNull == coroutineSingletons ? coroutineSingletons : objWithTimeoutOrNull;
        } catch (CancellationException e) {
            if (!JobKt.isActive(asyncFontListLoader$loadWithTimeoutOrNull$1.getContext())) {
                throw e;
            }
            return null;
        } catch (Exception e2) {
            CoroutineExceptionHandler coroutineExceptionHandler = (CoroutineExceptionHandler) asyncFontListLoader$loadWithTimeoutOrNull$1.getContext().get(CoroutineExceptionHandler.Key);
            if (coroutineExceptionHandler != null) {
                coroutineExceptionHandler.handleException(new IllegalStateException("Unable to load font " + font, e2), asyncFontListLoader$loadWithTimeoutOrNull$1.getContext());
            }
            return null;
        }
    }
}
