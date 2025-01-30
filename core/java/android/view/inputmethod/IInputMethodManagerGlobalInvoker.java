package android.view.inputmethod;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.window.ImeOnBackInvokedDispatcher;
import com.android.internal.inputmethod.IImeTracker;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InputBindResult;
import com.android.internal.view.IInputMethodManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
final class IInputMethodManagerGlobalInvoker {
  private static volatile IInputMethodManager sServiceCache = null;
  private static volatile IImeTracker sTrackerServiceCache = null;

  IInputMethodManagerGlobalInvoker() {}

  static boolean isAvailable() {
    return getService() != null;
  }

  static IInputMethodManager getService() {
    IInputMethodManager service = sServiceCache;
    if (service == null) {
      if (InputMethodManager.isInEditModeInternal()
          || (service =
                  IInputMethodManager.Stub.asInterface(
                      ServiceManager.getService(Context.INPUT_METHOD_SERVICE)))
              == null) {
        return null;
      }
      sServiceCache = service;
    }
    return service;
  }

  private static void handleRemoteExceptionOrRethrow(
      RemoteException e, Consumer<RemoteException> exceptionHandler) {
    if (exceptionHandler != null) {
      exceptionHandler.accept(e);
      return;
    }
    throw e.rethrowFromSystemServer();
  }

  static void startProtoDump(
      byte[] protoDump, int source, String where, Consumer<RemoteException> exceptionHandler) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.startProtoDump(protoDump, source, where);
    } catch (RemoteException e) {
      handleRemoteExceptionOrRethrow(e, exceptionHandler);
    }
  }

  static void startImeTrace(Consumer<RemoteException> exceptionHandler) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.startImeTrace();
    } catch (RemoteException e) {
      handleRemoteExceptionOrRethrow(e, exceptionHandler);
    }
  }

  static void stopImeTrace(Consumer<RemoteException> exceptionHandler) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.stopImeTrace();
    } catch (RemoteException e) {
      handleRemoteExceptionOrRethrow(e, exceptionHandler);
    }
  }

  static boolean isImeTraceEnabled() {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.isImeTraceEnabled();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void removeImeSurface(Consumer<RemoteException> exceptionHandler) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.removeImeSurface();
    } catch (RemoteException e) {
      handleRemoteExceptionOrRethrow(e, exceptionHandler);
    }
  }

  static void addClient(
      IInputMethodClient client,
      IRemoteInputConnection fallbackInputConnection,
      int untrustedDisplayId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.addClient(client, fallbackInputConnection, untrustedDisplayId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static InputMethodInfo getCurrentInputMethodInfoAsUser(int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return null;
    }
    try {
      return service.getCurrentInputMethodInfoAsUser(userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static List<InputMethodInfo> getInputMethodList(int userId, int directBootAwareness) {
    IInputMethodManager service = getService();
    if (service == null) {
      return new ArrayList();
    }
    try {
      return service.getInputMethodList(userId, directBootAwareness);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static List<InputMethodInfo> getEnabledInputMethodList(int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return new ArrayList();
    }
    try {
      return service.getEnabledInputMethodList(userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static List<InputMethodSubtype> getEnabledInputMethodSubtypeList(
      String imiId, boolean allowsImplicitlyEnabledSubtypes, int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return new ArrayList();
    }
    try {
      return service.getEnabledInputMethodSubtypeList(
          imiId, allowsImplicitlyEnabledSubtypes, userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static InputMethodSubtype getLastInputMethodSubtype(int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return null;
    }
    try {
      return service.getLastInputMethodSubtype(userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean showSoftInput(
      IInputMethodClient client,
      IBinder windowToken,
      ImeTracker.Token statsToken,
      int flags,
      int lastClickToolType,
      ResultReceiver resultReceiver,
      int reason) {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.showSoftInput(
          client, windowToken, statsToken, flags, lastClickToolType, resultReceiver, reason);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean hideSoftInput(
      IInputMethodClient client,
      IBinder windowToken,
      ImeTracker.Token statsToken,
      int flags,
      ResultReceiver resultReceiver,
      int reason) {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.hideSoftInput(client, windowToken, statsToken, flags, resultReceiver, reason);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static InputBindResult startInputOrWindowGainedFocus(
      int startInputReason,
      IInputMethodClient client,
      IBinder windowToken,
      int startInputFlags,
      int softInputMode,
      int windowFlags,
      EditorInfo editorInfo,
      IRemoteInputConnection remoteInputConnection,
      IRemoteAccessibilityInputConnection remoteAccessibilityInputConnection,
      int unverifiedTargetSdkVersion,
      int userId,
      ImeOnBackInvokedDispatcher imeDispatcher) {
    IInputMethodManager service = getService();
    if (service == null) {
      return InputBindResult.NULL;
    }
    try {
      return service.startInputOrWindowGainedFocus(
          startInputReason,
          client,
          windowToken,
          startInputFlags,
          softInputMode,
          windowFlags,
          editorInfo,
          remoteInputConnection,
          remoteAccessibilityInputConnection,
          unverifiedTargetSdkVersion,
          userId,
          imeDispatcher);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void showInputMethodPickerFromClient(IInputMethodClient client, int auxiliarySubtypeMode) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.showInputMethodPickerFromClient(client, auxiliarySubtypeMode);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void showInputMethodPickerFromSystem(int auxiliarySubtypeMode, int displayId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.showInputMethodPickerFromSystem(auxiliarySubtypeMode, displayId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean isInputMethodPickerShownForTest() {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.isInputMethodPickerShownForTest();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static InputMethodSubtype getCurrentInputMethodSubtype(int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return null;
    }
    try {
      return service.getCurrentInputMethodSubtype(userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void setAdditionalInputMethodSubtypes(
      String imeId, InputMethodSubtype[] subtypes, int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.setAdditionalInputMethodSubtypes(imeId, subtypes, userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void setExplicitlyEnabledInputMethodSubtypes(
      String imeId, int[] subtypeHashCodes, int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.setExplicitlyEnabledInputMethodSubtypes(imeId, subtypeHashCodes, userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static int getInputMethodWindowVisibleHeight(IInputMethodClient client) {
    IInputMethodManager service = getService();
    if (service == null) {
      return 0;
    }
    try {
      return service.getInputMethodWindowVisibleHeight(client);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void reportVirtualDisplayGeometryAsync(
      IInputMethodClient client, int childDisplayId, float[] matrixValues) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.reportVirtualDisplayGeometryAsync(client, childDisplayId, matrixValues);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void reportPerceptibleAsync(IBinder windowToken, boolean perceptible) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.reportPerceptibleAsync(windowToken, perceptible);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void removeImeSurfaceFromWindowAsync(IBinder windowToken) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.removeImeSurfaceFromWindowAsync(windowToken);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void startStylusHandwriting(IInputMethodClient client) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.startStylusHandwriting(client);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void prepareStylusHandwritingDelegation(
      IInputMethodClient client,
      int userId,
      String delegatePackageName,
      String delegatorPackageName) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.prepareStylusHandwritingDelegation(
          client, userId, delegatePackageName, delegatorPackageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean acceptStylusHandwritingDelegation(
      IInputMethodClient client,
      int userId,
      String delegatePackageName,
      String delegatorPackageName) {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.acceptStylusHandwritingDelegation(
          client, userId, delegatePackageName, delegatorPackageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean isStylusHandwritingAvailableAsUser(int userId) {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.isStylusHandwritingAvailableAsUser(userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void addVirtualStylusIdForTestSession(IInputMethodClient client) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.addVirtualStylusIdForTestSession(client);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void setStylusWindowIdleTimeoutForTest(IInputMethodClient client, long timeout) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.setStylusWindowIdleTimeoutForTest(client, timeout);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static ImeTracker.Token onRequestShow(String tag, int uid, int origin, int reason) {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return new ImeTracker.Token(new Binder(), tag);
    }
    try {
      return service.onRequestShow(tag, uid, origin, reason);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static ImeTracker.Token onRequestHide(String tag, int uid, int origin, int reason) {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return new ImeTracker.Token(new Binder(), tag);
    }
    try {
      return service.onRequestHide(tag, uid, origin, reason);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void onProgress(IBinder binder, int phase) {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return;
    }
    try {
      service.onProgress(binder, phase);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void onFailed(ImeTracker.Token statsToken, int phase) {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return;
    }
    try {
      service.onFailed(statsToken, phase);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void onCancelled(ImeTracker.Token statsToken, int phase) {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return;
    }
    try {
      service.onCancelled(statsToken, phase);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void onShown(ImeTracker.Token statsToken) {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return;
    }
    try {
      service.onShown(statsToken);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void onHidden(ImeTracker.Token statsToken) {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return;
    }
    try {
      service.onHidden(statsToken);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean hasPendingImeVisibilityRequests() {
    IImeTracker service = getImeTrackerService();
    if (service == null) {
      return true;
    }
    try {
      return service.hasPendingImeVisibilityRequests();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  private static IImeTracker getImeTrackerService() {
    IImeTracker trackerService = sTrackerServiceCache;
    if (trackerService == null) {
      IInputMethodManager service = getService();
      if (service == null) {
        return null;
      }
      try {
        trackerService = service.getImeTrackerService();
        if (trackerService == null) {
          return null;
        }
        sTrackerServiceCache = trackerService;
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    return trackerService;
  }

  static boolean minimizeSoftInput(IInputMethodClient client, int height) {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.minimizeSoftInput(client, height);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void undoMinimizeSoftInput() {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.undoMinimizeSoftInput();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static int isAccessoryKeyboard() {
    IInputMethodManager service = getService();
    if (service == null) {
      return 0;
    }
    try {
      return service.isAccessoryKeyboard();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean getWACOMPen() {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.getWACOMPen();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean isInputMethodShown() {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.isInputMethodShown();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean isCurrentInputMethodAsSamsungKeyboard() {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.isCurrentInputMethodAsSamsungKeyboard();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static boolean getDexSettingsValue(String key, String defaultKey) {
    IInputMethodManager service = getService();
    if (service == null) {
      return false;
    }
    try {
      return service.getDexSettingsValue(key, defaultKey);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void setInputMethodSwitchDisable(IInputMethodClient client, boolean disable) {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.setInputMethodSwitchDisable(client, disable);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void dismissAndShowAgainInputMethodPicker() {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.dismissAndShowAgainInputMethodPicker();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static int getCurrentFocusDisplayID() {
    IInputMethodManager service = getService();
    if (service == null) {
      return 0;
    }
    try {
      return service.getCurrentFocusDisplayID();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static int getCurTokenDisplayId() {
    IInputMethodManager service = getService();
    if (service == null) {
      return 0;
    }
    try {
      return service.getCurTokenDisplayId();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static void handleVoiceHWKey() {
    IInputMethodManager service = getService();
    if (service == null) {
      return;
    }
    try {
      service.handleVoiceHWKey();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }
}
