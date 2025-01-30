package kotlin.coroutines.jvm.internal;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ModuleNameRetriever;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class BaseContinuationImpl implements Continuation<Object>, CoroutineStackFrame, Serializable {
    private final Continuation<Object> completion;

    public BaseContinuationImpl(Continuation<Object> continuation) {
        this.completion = continuation;
    }

    public Continuation create(Continuation continuation) {
        throw new UnsupportedOperationException("create(Continuation) has not been overridden");
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public CoroutineStackFrame getCallerFrame() {
        Continuation<Object> continuation = this.completion;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    public final Continuation getCompletion() {
        return this.completion;
    }

    public StackTraceElement getStackTraceElement() {
        int i;
        String str;
        DebugMetadata debugMetadata = (DebugMetadata) getClass().getAnnotation(DebugMetadata.class);
        String str2 = null;
        if (debugMetadata == null) {
            return null;
        }
        int m280v = debugMetadata.m280v();
        if (m280v > 1) {
            throw new IllegalStateException(("Debug metadata version mismatch. Expected: 1, got " + m280v + ". Please update the Kotlin standard library.").toString());
        }
        try {
            Field declaredField = getClass().getDeclaredField("label");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this);
            Integer num = obj instanceof Integer ? (Integer) obj : null;
            i = (num != null ? num.intValue() : 0) - 1;
        } catch (Exception unused) {
            i = -1;
        }
        int i2 = i >= 0 ? debugMetadata.m278l()[i] : -1;
        ModuleNameRetriever.INSTANCE.getClass();
        ModuleNameRetriever.Cache cache = ModuleNameRetriever.cache;
        ModuleNameRetriever.Cache cache2 = ModuleNameRetriever.notOnJava9;
        if (cache == null) {
            try {
                ModuleNameRetriever.Cache cache3 = new ModuleNameRetriever.Cache(Class.class.getDeclaredMethod("getModule", new Class[0]), getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor", new Class[0]), getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name", new Class[0]));
                ModuleNameRetriever.cache = cache3;
                cache = cache3;
            } catch (Exception unused2) {
                ModuleNameRetriever.cache = cache2;
                cache = cache2;
            }
        }
        if (cache != cache2) {
            Method method = cache.getModuleMethod;
            Object invoke = method != null ? method.invoke(getClass(), new Object[0]) : null;
            if (invoke != null) {
                Method method2 = cache.getDescriptorMethod;
                Object invoke2 = method2 != null ? method2.invoke(invoke, new Object[0]) : null;
                if (invoke2 != null) {
                    Method method3 = cache.nameMethod;
                    Object invoke3 = method3 != null ? method3.invoke(invoke2, new Object[0]) : null;
                    if (invoke3 instanceof String) {
                        str2 = (String) invoke3;
                    }
                }
            }
        }
        if (str2 == null) {
            str = debugMetadata.m276c();
        } else {
            str = str2 + '/' + debugMetadata.m276c();
        }
        return new StackTraceElement(str, debugMetadata.m279m(), debugMetadata.m277f(), i2);
    }

    public abstract Object invokeSuspend(Object obj);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, kotlin.coroutines.Continuation, kotlin.coroutines.Continuation<java.lang.Object>] */
    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        while (true) {
            BaseContinuationImpl baseContinuationImpl = this;
            ?? r0 = baseContinuationImpl.completion;
            Intrinsics.checkNotNull(r0);
            try {
                obj = baseContinuationImpl.invokeSuspend(obj);
            } catch (Throwable th) {
                int i = Result.$r8$clinit;
                obj = new Result.Failure(th);
            }
            if (obj == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return;
            }
            int i2 = Result.$r8$clinit;
            baseContinuationImpl.releaseIntercepted();
            if (!(r0 instanceof BaseContinuationImpl)) {
                r0.resumeWith(obj);
                return;
            }
            this = r0;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Continuation at ");
        Object stackTraceElement = getStackTraceElement();
        if (stackTraceElement == null) {
            stackTraceElement = getClass().getName();
        }
        sb.append(stackTraceElement);
        return sb.toString();
    }

    public Continuation create(Object obj, Continuation continuation) {
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }

    public void releaseIntercepted() {
    }
}
