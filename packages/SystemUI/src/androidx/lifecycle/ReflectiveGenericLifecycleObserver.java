package androidx.lifecycle;

import androidx.lifecycle.ClassesInfoCache;
import androidx.lifecycle.Lifecycle;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ReflectiveGenericLifecycleObserver implements LifecycleEventObserver {
    public final ClassesInfoCache.CallbackInfo mInfo;
    public final Object mWrapped;

    public ReflectiveGenericLifecycleObserver(Object obj) {
        this.mWrapped = obj;
        this.mInfo = ClassesInfoCache.sInstance.getInfo(obj.getClass());
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        ClassesInfoCache.CallbackInfo callbackInfo = this.mInfo;
        List list = (List) ((HashMap) callbackInfo.mEventToHandlers).get(event);
        Object obj = this.mWrapped;
        ClassesInfoCache.CallbackInfo.invokeMethodsForEvent(list, lifecycleOwner, event, obj);
        ClassesInfoCache.CallbackInfo.invokeMethodsForEvent((List) ((HashMap) callbackInfo.mEventToHandlers).get(Lifecycle.Event.ON_ANY), lifecycleOwner, event, obj);
    }
}
