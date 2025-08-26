package androidx.compose.ui.platform;

import android.view.View;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
public interface PlatformTextInputSession {
    View getView();

    CoroutineSingletons startInputMethod(PlatformTextInputMethodRequest platformTextInputMethodRequest, ContinuationImpl continuationImpl);
}
