package com.android.systemui.media.taptotransfer.common;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaTttLoggerUtils$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                MediaTttLoggerUtils mediaTttLoggerUtils = MediaTttLoggerUtils.INSTANCE;
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Cannot display state=", "; aborting");
            case 1:
                MediaTttLoggerUtils mediaTttLoggerUtils2 = MediaTttLoggerUtils.INSTANCE;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Package ", logMessage.getStr1(), " could not be found");
            default:
                MediaTttLoggerUtils mediaTttLoggerUtils3 = MediaTttLoggerUtils.INSTANCE;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("State changed to ", str1, " for ID=", str2, " package=");
                sbM.append(str3);
                return sbM.toString();
        }
    }
}
