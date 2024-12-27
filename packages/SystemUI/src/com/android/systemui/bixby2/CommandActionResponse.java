package com.android.systemui.bixby2;

public class CommandActionResponse {
    public int responseCode;
    public String responseMessage;

    public CommandActionResponse(int i, String str) {
        this.responseCode = i;
        this.responseMessage = str;
    }
}
