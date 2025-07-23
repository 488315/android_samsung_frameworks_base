package com.android.internal.util;

import android.os.Message;

/* loaded from: classes4.dex */
public class State implements IState {
    @Override // com.android.internal.util.IState
    public void enter() {
    }

    @Override // com.android.internal.util.IState
    public void exit() {
    }

    @Override // com.android.internal.util.IState
    public boolean processMessage(Message message) {
        return false;
    }

    protected State() {
    }

    @Override // com.android.internal.util.IState
    public String getName() {
        String name = getClass().getName();
        return name.substring(name.lastIndexOf(36) + 1);
    }
}
