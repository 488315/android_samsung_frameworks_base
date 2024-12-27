package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.systemui.media.mediaoutput.dagger.SavedStateHandleAssisted;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.Result;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ViewModelFactory implements ViewModelProvider.Factory {
    public final Map creators;

    public ViewModelFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.creators = MapsKt__MapsKt.mapOf(new Pair(MediaSessionViewModel.class, provider), new Pair(SessionAudioPathViewModel.class, provider2), new Pair(MediaDeviceViewModel.class, provider3), new Pair(DeviceAudioPathViewModel.class, provider4), new Pair(SettingViewModel.class, provider5), new Pair(LabsViewModel.class, provider6));
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls, CreationExtras creationExtras) {
        Object failure;
        Object obj = getCreator(cls).get();
        if (obj instanceof SavedStateHandleAssisted) {
            SavedStateHandleAssisted savedStateHandleAssisted = (SavedStateHandleAssisted) obj;
            try {
                int i = Result.$r8$clinit;
                failure = SavedStateHandleSupport.createSavedStateHandle(creationExtras);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            obj = savedStateHandleAssisted.create((SavedStateHandle) failure);
        }
        return (ViewModel) obj;
    }

    public final Provider getCreator(Class cls) {
        Object obj;
        Provider provider = (Provider) this.creators.get(cls);
        if (provider == null) {
            Iterator it = this.creators.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (cls.isAssignableFrom((Class) ((Map.Entry) obj).getKey())) {
                    break;
                }
            }
            Map.Entry entry = (Map.Entry) obj;
            provider = entry != null ? (Provider) entry.getValue() : null;
            if (provider == null) {
                throw new IllegalArgumentException("unknown model class " + cls);
            }
        }
        return provider;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        return (ViewModel) getCreator(cls).get();
    }
}
