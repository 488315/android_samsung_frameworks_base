package com.samsung.android.lib.dexcontrol.uvdm.response;

public interface IResponseListener {
    void onFail(int i, ResponseResult responseResult);

    void onSuccess(ResponseResult responseResult);
}
