package com.samsung.android.sume.core.service;

import android.content.Context;
import android.p009os.IBinder;
import com.samsung.android.sume.core.controller.MediaController;
import com.samsung.android.sume.core.functional.ExceptionHandler;
import com.samsung.android.sume.core.message.Request;
import com.samsung.android.sume.core.message.Response;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public interface ServiceProxy {
  public static final String ACTION_START_FOREGROUND = "start-foreground";
  public static final String ACTION_STOP_FOREGROUND = "start-foreground";
  public static final int OPTION_AS_DAEMON = 1;
  public static final int OPTION_AS_FOREGROUND = 0;

  @Retention(RetentionPolicy.SOURCE)
  public @interface Action {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface Option {}

  IBinder getBinder();

  ExceptionHandler getExceptionHandler();

  void release();

  Future<Response> request(Request request);

  void setEventListener(MediaController.OnEventListener onEventListener);

  void setExceptionHandler(ExceptionHandler exceptionHandler);

  /* renamed from: of */
  static ServiceProxySupplier m460of(Context context) throws IllegalArgumentException {
    return new ServiceProxySupplier.PlaceHolderImpl(context);
  }

  /* renamed from: of */
  static ServiceProxySupplier m461of(Context context, Class<?> clazz)
      throws IllegalArgumentException {
    return new ServiceProxySupplier(context, clazz);
  }

  /* renamed from: of */
  static ServiceProxySupplier m462of(Context context, String className)
      throws ClassNotFoundException {
    return m461of(context, Class.forName(className));
  }

  /* renamed from: of */
  static ServiceProxySupplier m463of(Context context, String packageName, String serviceName) {
    return new ServiceProxySupplier(context, packageName, serviceName);
  }
}
