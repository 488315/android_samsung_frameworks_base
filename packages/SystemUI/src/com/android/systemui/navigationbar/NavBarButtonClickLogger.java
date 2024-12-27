package com.android.systemui.navigationbar;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NavBarButtonClickLogger {
    public final LogBuffer buffer;

    public NavBarButtonClickLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logBackButtonClick() {
        LogLevel logLevel = LogLevel.DEBUG;
        NavBarButtonClickLogger$logBackButtonClick$2 navBarButtonClickLogger$logBackButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logBackButtonClick$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Back Button Triggered";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$logBackButtonClick$2, null));
    }

    public final void logHomeButtonClick() {
        LogLevel logLevel = LogLevel.DEBUG;
        NavBarButtonClickLogger$logHomeButtonClick$2 navBarButtonClickLogger$logHomeButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logHomeButtonClick$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Home Button Triggered";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$logHomeButtonClick$2, null));
    }
}
