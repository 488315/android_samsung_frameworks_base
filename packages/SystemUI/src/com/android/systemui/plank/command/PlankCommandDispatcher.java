package com.android.systemui.plank.command;

import android.os.Bundle;

public interface PlankCommandDispatcher {
    Bundle dispatch(Bundle bundle, String str);
}
