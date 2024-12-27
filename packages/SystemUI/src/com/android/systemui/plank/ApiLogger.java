package com.android.systemui.plank;

import java.util.ArrayList;
import java.util.List;

public final class ApiLogger {
    public static final ApiLogger INSTANCE = new ApiLogger();
    public static final List list = new ArrayList();

    private ApiLogger() {
    }
}
