package com.android.server;

public interface IWorkOnAt {
    String getCmd();

    String processCmd(String str);
}
