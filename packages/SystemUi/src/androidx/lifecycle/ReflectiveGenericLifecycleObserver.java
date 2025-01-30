package androidx.lifecycle;

import androidx.lifecycle.ClassesInfoCache;
import androidx.lifecycle.Lifecycle;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes.dex */
class ReflectiveGenericLifecycleObserver implements LifecycleEventObserver {
    public final ClassesInfoCache.CallbackInfo mInfo;
    public final Object mWrapped;

    public ReflectiveGenericLifecycleObserver(Object obj) {
        this.mWrapped = obj;
        this.mInfo = ClassesInfoCache.sInstance.getInfo(obj.getClass());
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        HashMap hashMap = (HashMap) this.mInfo.mEventToHandlers;
        List list = (List) hashMap.get(event);
        Object obj = this.mWrapped;
        ClassesInfoCache.CallbackInfo.invokeMethodsForEvent(list, lifecycleOwner, event, obj);
        ClassesInfoCache.CallbackInfo.invokeMethodsForEvent((List) hashMap.get(Lifecycle.Event.ON_ANY), lifecycleOwner, event, obj);
    }
}
