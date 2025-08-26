package com.android.systemui.communal.data.repository;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return MutableVectorKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Updated spanY of widget ", " to ", ".");
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed reading restore data from disk: ", logMessage.getStr1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Restored users map: ", logMessage.getStr1());
            case 3:
                return MutableVectorKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Skipped restoring widget (old:", " new:", ") because it is not registered with host");
            case 4:
                return MutableVectorKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Skipped restoring widget ", " because its user ", " is not registered");
            case 5:
                return MutableVectorKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Skipped restoring widget ", " for now because its new user ", " is secondary. This widget will be bound later.");
            case 6:
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(int1, int2, "Binding secondary user (", ") widget ", ": ");
                sbM.append(str1);
                return sbM.toString();
            case 7:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Deleting widget ", " from host since it has not been restored");
            default:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Updated the order of widget list with ids: ", logMessage.getStr1(), ".");
        }
    }
}
