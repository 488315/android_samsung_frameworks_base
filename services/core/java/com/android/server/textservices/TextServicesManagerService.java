package com.android.server.textservices;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.SpellCheckerSubtype;

import com.android.internal.content.PackageMonitor;
import com.android.internal.inputmethod.SubtypeLocaleUtils;
import com.android.internal.textservice.ISpellCheckerService;
import com.android.internal.textservice.ISpellCheckerServiceCallback;
import com.android.internal.textservice.ISpellCheckerSession;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesManager;
import com.android.internal.textservice.ITextServicesSessionListener;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class TextServicesManagerService extends ITextServicesManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final UserManager mUserManager;
    public final SparseArray mUserData = new SparseArray();
    public final Object mLock = new Object();

    public final class ISpellCheckerServiceCallbackBinder
            extends ISpellCheckerServiceCallback.Stub {
        public WeakReference mBindGroup;
        public final Object mCallbackLock;
        public WeakReference mRequest;

        public ISpellCheckerServiceCallbackBinder(
                SpellCheckerBindGroup spellCheckerBindGroup, SessionRequest sessionRequest) {
            Object obj = new Object();
            this.mCallbackLock = obj;
            synchronized (obj) {
                this.mBindGroup = new WeakReference(spellCheckerBindGroup);
                this.mRequest = new WeakReference(sessionRequest);
            }
        }

        public final void onSessionCreated(ISpellCheckerSession iSpellCheckerSession) {
            synchronized (this.mCallbackLock) {
                WeakReference weakReference = this.mBindGroup;
                if (weakReference != null && this.mRequest != null) {
                    SpellCheckerBindGroup spellCheckerBindGroup =
                            (SpellCheckerBindGroup) weakReference.get();
                    SessionRequest sessionRequest = (SessionRequest) this.mRequest.get();
                    this.mBindGroup = null;
                    this.mRequest = null;
                    if (spellCheckerBindGroup == null || sessionRequest == null) {
                        return;
                    }
                    synchronized (TextServicesManagerService.this.mLock) {
                        try {
                            if (spellCheckerBindGroup.mUnbindCalled) {
                                return;
                            }
                            if (spellCheckerBindGroup.mOnGoingSessionRequests.remove(
                                    sessionRequest)) {
                                try {
                                    sessionRequest.mTsListener.onServiceConnected(
                                            iSpellCheckerSession);
                                } catch (RemoteException unused) {
                                }
                            }
                            spellCheckerBindGroup.cleanLocked();
                        } finally {
                        }
                    }
                }
            }
        }
    }

    public final class InternalDeathRecipients extends RemoteCallbackList {
        public final SpellCheckerBindGroup mGroup;

        public InternalDeathRecipients(SpellCheckerBindGroup spellCheckerBindGroup) {
            this.mGroup = spellCheckerBindGroup;
        }

        @Override // android.os.RemoteCallbackList
        public final void onCallbackDied(IInterface iInterface) {
            this.mGroup.removeListener((ISpellCheckerSessionListener) iInterface);
        }
    }

    public final class InternalServiceConnection implements ServiceConnection {
        public final String mSciId;
        public final HashMap mSpellCheckerBindGroups;

        public InternalServiceConnection(String str, HashMap hashMap) {
            this.mSciId = str;
            this.mSpellCheckerBindGroups = hashMap;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (TextServicesManagerService.this.mLock) {
                onServiceConnectedInnerLocked(iBinder);
            }
        }

        public final void onServiceConnectedInnerLocked(IBinder iBinder) {
            ISpellCheckerService asInterface = ISpellCheckerService.Stub.asInterface(iBinder);
            SpellCheckerBindGroup spellCheckerBindGroup =
                    (SpellCheckerBindGroup) this.mSpellCheckerBindGroups.get(this.mSciId);
            if (spellCheckerBindGroup == null
                    || this != spellCheckerBindGroup.mInternalConnection
                    || spellCheckerBindGroup.mUnbindCalled) {
                return;
            }
            spellCheckerBindGroup.mSpellChecker = asInterface;
            spellCheckerBindGroup.mConnected = true;
            try {
                int size = spellCheckerBindGroup.mPendingSessionRequests.size();
                for (int i = 0; i < size; i++) {
                    SessionRequest sessionRequest =
                            (SessionRequest) spellCheckerBindGroup.mPendingSessionRequests.get(i);
                    spellCheckerBindGroup.mSpellChecker.getISpellCheckerSession(
                            sessionRequest.mLocale,
                            sessionRequest.mScListener,
                            sessionRequest.mBundle,
                            sessionRequest.mSupportedAttributes,
                            new ISpellCheckerServiceCallbackBinder(
                                    spellCheckerBindGroup, sessionRequest));
                    spellCheckerBindGroup.mOnGoingSessionRequests.add(sessionRequest);
                }
                spellCheckerBindGroup.mPendingSessionRequests.clear();
            } catch (RemoteException unused) {
                spellCheckerBindGroup.removeAllLocked();
            }
            spellCheckerBindGroup.cleanLocked();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (TextServicesManagerService.this.mLock) {
                SpellCheckerBindGroup spellCheckerBindGroup =
                        (SpellCheckerBindGroup) this.mSpellCheckerBindGroups.get(this.mSciId);
                if (spellCheckerBindGroup != null
                        && this == spellCheckerBindGroup.mInternalConnection) {
                    spellCheckerBindGroup.mSpellChecker = null;
                    spellCheckerBindGroup.mConnected = false;
                }
            }
        }
    }

    public final class Lifecycle extends SystemService {
        public final TextServicesManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = new TextServicesManagerService(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            LocalServices.addService(
                    TextServicesManagerInternal.class,
                    new TextServicesManagerInternal() { // from class:
                                                        // com.android.server.textservices.TextServicesManagerService.Lifecycle.1
                        @Override // com.android.server.textservices.TextServicesManagerInternal
                        public final SpellCheckerInfo getCurrentSpellCheckerForUser(int i) {
                            TextServicesManagerService textServicesManagerService =
                                    Lifecycle.this.mService;
                            int i2 = TextServicesManagerService.$r8$clinit;
                            return textServicesManagerService.getCurrentSpellCheckerForUser(i);
                        }
                    });
            publishBinderService("textservices", this.mService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            TextServicesManagerService textServicesManagerService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (textServicesManagerService.mLock) {
                try {
                    TextServicesData textServicesData =
                            (TextServicesData)
                                    textServicesManagerService.mUserData.get(userIdentifier);
                    if (textServicesData == null) {
                        return;
                    }
                    HashMap hashMap = textServicesData.mSpellCheckerBindGroups;
                    Iterator it = hashMap.values().iterator();
                    while (it.hasNext()) {
                        ((SpellCheckerBindGroup) it.next()).removeAllLocked();
                    }
                    hashMap.clear();
                    textServicesManagerService.mUserData.remove(userIdentifier);
                } finally {
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            TextServicesManagerService textServicesManagerService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (textServicesManagerService.mLock) {
                TextServicesData textServicesData =
                        (TextServicesData) textServicesManagerService.mUserData.get(userIdentifier);
                if (textServicesData == null) {
                    textServicesData =
                            new TextServicesData(
                                    textServicesManagerService.mContext, userIdentifier);
                    textServicesManagerService.mUserData.put(userIdentifier, textServicesData);
                }
                TextServicesData.m969$$Nest$minitializeTextServicesData(textServicesData);
                if (textServicesData.getCurrentSpellChecker() == null) {
                    TextServicesManagerService.setCurrentSpellCheckerLocked(
                            textServicesManagerService.findAvailSystemSpellCheckerLocked(
                                    null, textServicesData),
                            textServicesData);
                }
            }
        }
    }

    public final class SessionRequest {
        public final Bundle mBundle;
        public final String mLocale;
        public final ISpellCheckerSessionListener mScListener;
        public final int mSupportedAttributes;
        public final ITextServicesSessionListener mTsListener;
        public final int mUid;

        public SessionRequest(
                int i,
                String str,
                ITextServicesSessionListener iTextServicesSessionListener,
                ISpellCheckerSessionListener iSpellCheckerSessionListener,
                Bundle bundle,
                int i2) {
            this.mUid = i;
            this.mLocale = str;
            this.mTsListener = iTextServicesSessionListener;
            this.mScListener = iSpellCheckerSessionListener;
            this.mBundle = bundle;
            this.mSupportedAttributes = i2;
        }
    }

    public final class SpellCheckerBindGroup {
        public boolean mConnected;
        public final InternalServiceConnection mInternalConnection;
        public ISpellCheckerService mSpellChecker;
        public final HashMap mSpellCheckerBindGroups;
        public boolean mUnbindCalled;
        public final String TAG = SpellCheckerBindGroup.class.getSimpleName();
        public final ArrayList mPendingSessionRequests = new ArrayList();
        public final ArrayList mOnGoingSessionRequests = new ArrayList();
        public final InternalDeathRecipients mListeners = new InternalDeathRecipients(this);

        public SpellCheckerBindGroup(InternalServiceConnection internalServiceConnection) {
            this.mInternalConnection = internalServiceConnection;
            this.mSpellCheckerBindGroups = internalServiceConnection.mSpellCheckerBindGroups;
        }

        public final void cleanLocked() {
            if (!this.mUnbindCalled
                    && this.mListeners.getRegisteredCallbackCount() <= 0
                    && this.mPendingSessionRequests.isEmpty()
                    && this.mOnGoingSessionRequests.isEmpty()) {
                InternalServiceConnection internalServiceConnection = this.mInternalConnection;
                String str = internalServiceConnection.mSciId;
                if (((SpellCheckerBindGroup) this.mSpellCheckerBindGroups.get(str)) == this) {
                    this.mSpellCheckerBindGroups.remove(str);
                }
                TextServicesManagerService.this.mContext.unbindService(internalServiceConnection);
                this.mUnbindCalled = true;
            }
        }

        public final void getISpellCheckerSessionOrQueueLocked(SessionRequest sessionRequest) {
            if (this.mUnbindCalled) {
                return;
            }
            this.mListeners.register(sessionRequest.mScListener);
            if (!this.mConnected) {
                this.mPendingSessionRequests.add(sessionRequest);
                return;
            }
            try {
                this.mSpellChecker.getISpellCheckerSession(
                        sessionRequest.mLocale,
                        sessionRequest.mScListener,
                        sessionRequest.mBundle,
                        sessionRequest.mSupportedAttributes,
                        new ISpellCheckerServiceCallbackBinder(this, sessionRequest));
                this.mOnGoingSessionRequests.add(sessionRequest);
            } catch (RemoteException unused) {
                removeAllLocked();
            }
            cleanLocked();
        }

        public final void removeAllLocked() {
            Slog.e(this.TAG, "Remove the spell checker bind unexpectedly.");
            InternalDeathRecipients internalDeathRecipients = this.mListeners;
            for (int registeredCallbackCount =
                            internalDeathRecipients.getRegisteredCallbackCount() - 1;
                    registeredCallbackCount >= 0;
                    registeredCallbackCount--) {
                internalDeathRecipients.unregister(
                        internalDeathRecipients.getRegisteredCallbackItem(registeredCallbackCount));
            }
            this.mPendingSessionRequests.clear();
            this.mOnGoingSessionRequests.clear();
            cleanLocked();
        }

        public final void removeListener(
                ISpellCheckerSessionListener iSpellCheckerSessionListener) {
            synchronized (TextServicesManagerService.this.mLock) {
                this.mListeners.unregister(iSpellCheckerSessionListener);
                final IBinder asBinder = iSpellCheckerSessionListener.asBinder();
                Predicate predicate =
                        new Predicate() { // from class:
                                          // com.android.server.textservices.TextServicesManagerService$SpellCheckerBindGroup$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((TextServicesManagerService.SessionRequest) obj)
                                                .mScListener.asBinder()
                                        == asBinder;
                            }
                        };
                this.mPendingSessionRequests.removeIf(predicate);
                this.mOnGoingSessionRequests.removeIf(predicate);
                cleanLocked();
            }
        }
    }

    public final class TextServicesData {
        public final Context mContext;
        public final ContentResolver mResolver;
        public final int mUserId;
        public int mUpdateCount = 0;
        public final HashMap mSpellCheckerMap = new HashMap();
        public final ArrayList mSpellCheckerList = new ArrayList();
        public final HashMap mSpellCheckerBindGroups = new HashMap();

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m968$$Nest$mdump(
                TextServicesData textServicesData, PrintWriter printWriter) {
            printWriter.println("  User #" + textServicesData.mUserId);
            printWriter.println("  Spell Checkers:");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  Spell Checkers: mUpdateCount="),
                    textServicesData.mUpdateCount,
                    printWriter);
            int i = 0;
            for (SpellCheckerInfo spellCheckerInfo : textServicesData.mSpellCheckerMap.values()) {
                AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(
                        printWriter, "  Spell Checker #", i);
                spellCheckerInfo.dump(printWriter, "    ");
                i++;
            }
            printWriter.println("");
            printWriter.println("  Spell Checker Bind Groups:");
            for (Map.Entry entry : textServicesData.mSpellCheckerBindGroups.entrySet()) {
                SpellCheckerBindGroup spellCheckerBindGroup =
                        (SpellCheckerBindGroup) entry.getValue();
                StringBuilder m$1 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                printWriter,
                                "    "
                                        + ((String) entry.getKey())
                                        + " "
                                        + spellCheckerBindGroup
                                        + ":",
                                "      mInternalConnection=");
                m$1.append(spellCheckerBindGroup.mInternalConnection);
                printWriter.println(m$1.toString());
                StringBuilder sb = new StringBuilder("      mSpellChecker=");
                sb.append(spellCheckerBindGroup.mSpellChecker);
                printWriter.println(sb.toString());
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("      mUnbindCalled="),
                                spellCheckerBindGroup.mUnbindCalled,
                                printWriter,
                                "      mConnected="),
                        spellCheckerBindGroup.mConnected,
                        printWriter);
                int size = spellCheckerBindGroup.mPendingSessionRequests.size();
                for (int i2 = 0; i2 < size; i2++) {
                    SessionRequest sessionRequest =
                            (SessionRequest) spellCheckerBindGroup.mPendingSessionRequests.get(i2);
                    printWriter.println("      Pending Request #" + i2 + ":");
                    StringBuilder sb2 = new StringBuilder("        mTsListener=");
                    sb2.append(sessionRequest.mTsListener);
                    printWriter.println(sb2.toString());
                    printWriter.println("        mScListener=" + sessionRequest.mScListener);
                    printWriter.println(
                            "        mScLocale="
                                    + sessionRequest.mLocale
                                    + " mUid="
                                    + sessionRequest.mUid);
                }
                int size2 = spellCheckerBindGroup.mOnGoingSessionRequests.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    SessionRequest sessionRequest2 =
                            (SessionRequest) spellCheckerBindGroup.mOnGoingSessionRequests.get(i3);
                    printWriter.println("      On going Request #" + i3 + ":");
                    StringBuilder sb3 = new StringBuilder("        mTsListener=");
                    sb3.append(sessionRequest2.mTsListener);
                    printWriter.println(sb3.toString());
                    printWriter.println("        mScListener=" + sessionRequest2.mScListener);
                    printWriter.println(
                            "        mScLocale="
                                    + sessionRequest2.mLocale
                                    + " mUid="
                                    + sessionRequest2.mUid);
                }
                InternalDeathRecipients internalDeathRecipients = spellCheckerBindGroup.mListeners;
                int registeredCallbackCount = internalDeathRecipients.getRegisteredCallbackCount();
                for (int i4 = 0; i4 < registeredCallbackCount; i4++) {
                    ISpellCheckerSessionListener registeredCallbackItem =
                            internalDeathRecipients.getRegisteredCallbackItem(i4);
                    printWriter.println("      Listener #" + i4 + ":");
                    StringBuilder sb4 = new StringBuilder("        mScListener=");
                    sb4.append(registeredCallbackItem);
                    printWriter.println(sb4.toString());
                    printWriter.println("        mGroup=" + spellCheckerBindGroup);
                }
            }
        }

        /* renamed from: -$$Nest$minitializeTextServicesData, reason: not valid java name */
        public static void m969$$Nest$minitializeTextServicesData(
                TextServicesData textServicesData) {
            textServicesData.mSpellCheckerList.clear();
            textServicesData.mSpellCheckerMap.clear();
            textServicesData.mUpdateCount++;
            List queryIntentServicesAsUser =
                    textServicesData
                            .mContext
                            .getPackageManager()
                            .queryIntentServicesAsUser(
                                    new Intent("android.service.textservice.SpellCheckerService"),
                                    128,
                                    textServicesData.mUserId);
            int size = queryIntentServicesAsUser.size();
            for (int i = 0; i < size; i++) {
                ResolveInfo resolveInfo = (ResolveInfo) queryIntentServicesAsUser.get(i);
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                ComponentName componentName =
                        new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if ("android.permission.BIND_TEXT_SERVICE".equals(serviceInfo.permission)) {
                    try {
                        SpellCheckerInfo spellCheckerInfo =
                                new SpellCheckerInfo(textServicesData.mContext, resolveInfo);
                        if (spellCheckerInfo.getSubtypeCount() <= 0) {
                            int i2 = TextServicesManagerService.$r8$clinit;
                            Slog.w(
                                    "TextServicesManagerService",
                                    "Skipping text service "
                                            + componentName
                                            + ": it does not contain subtypes.");
                        } else {
                            textServicesData.mSpellCheckerList.add(spellCheckerInfo);
                            textServicesData.mSpellCheckerMap.put(
                                    spellCheckerInfo.getId(), spellCheckerInfo);
                        }
                    } catch (IOException e) {
                        int i3 = TextServicesManagerService.$r8$clinit;
                        Slog.w(
                                "TextServicesManagerService",
                                "Unable to load the spell checker " + componentName,
                                e);
                    } catch (XmlPullParserException e2) {
                        int i4 = TextServicesManagerService.$r8$clinit;
                        Slog.w(
                                "TextServicesManagerService",
                                "Unable to load the spell checker " + componentName,
                                e2);
                    }
                } else {
                    int i5 = TextServicesManagerService.$r8$clinit;
                    Slog.w(
                            "TextServicesManagerService",
                            "Skipping text service "
                                    + componentName
                                    + ": it does not require the permission"
                                    + " android.permission.BIND_TEXT_SERVICE");
                }
            }
        }

        public TextServicesData(Context context, int i) {
            this.mUserId = i;
            this.mContext = context;
            this.mResolver = context.getContentResolver();
        }

        public final SpellCheckerInfo getCurrentSpellChecker() {
            String stringForUser =
                    Settings.Secure.getStringForUser(
                            this.mResolver, "selected_spell_checker", this.mUserId);
            if (stringForUser == null) {
                stringForUser = "";
            }
            if (TextUtils.isEmpty(stringForUser)) {
                return null;
            }
            return (SpellCheckerInfo) this.mSpellCheckerMap.get(stringForUser);
        }
    }

    public final class TextServicesMonitor extends PackageMonitor {
        public TextServicesMonitor() {}

        public final void onSomePackagesChanged() {
            SpellCheckerInfo findAvailSystemSpellCheckerLocked;
            int changingUserId = getChangingUserId();
            synchronized (TextServicesManagerService.this.mLock) {
                try {
                    TextServicesData textServicesData =
                            (TextServicesData)
                                    TextServicesManagerService.this.mUserData.get(changingUserId);
                    if (textServicesData == null) {
                        return;
                    }
                    SpellCheckerInfo currentSpellChecker =
                            textServicesData.getCurrentSpellChecker();
                    TextServicesData.m969$$Nest$minitializeTextServicesData(textServicesData);
                    if (Settings.Secure.getIntForUser(
                                    textServicesData.mResolver,
                                    "spell_checker_enabled",
                                    1,
                                    textServicesData.mUserId)
                            == 1) {
                        if (currentSpellChecker == null) {
                            SpellCheckerInfo findAvailSystemSpellCheckerLocked2 =
                                    TextServicesManagerService.this
                                            .findAvailSystemSpellCheckerLocked(
                                                    null, textServicesData);
                            TextServicesManagerService.this.getClass();
                            TextServicesManagerService.setCurrentSpellCheckerLocked(
                                    findAvailSystemSpellCheckerLocked2, textServicesData);
                        } else {
                            String packageName = currentSpellChecker.getPackageName();
                            int isPackageDisappearing = isPackageDisappearing(packageName);
                            if ((isPackageDisappearing == 3 || isPackageDisappearing == 2)
                                    && ((findAvailSystemSpellCheckerLocked =
                                                            TextServicesManagerService.this
                                                                    .findAvailSystemSpellCheckerLocked(
                                                                            packageName,
                                                                            textServicesData))
                                                    == null
                                            || !findAvailSystemSpellCheckerLocked
                                                    .getId()
                                                    .equals(currentSpellChecker.getId()))) {
                                TextServicesManagerService.this.getClass();
                                TextServicesManagerService.setCurrentSpellCheckerLocked(
                                        findAvailSystemSpellCheckerLocked, textServicesData);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public TextServicesManagerService(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        new TextServicesMonitor().register(context, (Looper) null, UserHandle.ALL, true);
    }

    public static void setCurrentSpellCheckerLocked(
            SpellCheckerInfo spellCheckerInfo, TextServicesData textServicesData) {
        if (spellCheckerInfo != null) {
            spellCheckerInfo.getId();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i = textServicesData.mUserId;
        try {
            if (spellCheckerInfo != null) {
                Settings.Secure.putStringForUser(
                        textServicesData.mResolver,
                        "selected_spell_checker",
                        spellCheckerInfo.getId(),
                        i);
            } else {
                Settings.Secure.putStringForUser(
                        textServicesData.mResolver, "selected_spell_checker", "", i);
            }
            Settings.Secure.putIntForUser(
                    textServicesData.mResolver, "selected_spell_checker_subtype", 0, i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean canCallerAccessSpellChecker(
            SpellCheckerInfo spellCheckerInfo, int i, int i2) {
        SpellCheckerInfo currentSpellCheckerForUser = getCurrentSpellCheckerForUser(i2);
        if (currentSpellCheckerForUser == null
                || !currentSpellCheckerForUser.getId().equals(spellCheckerInfo.getId())) {
            return !((PackageManagerInternal)
                            LocalServices.getService(PackageManagerInternal.class))
                    .filterAppAccess(i, i2, spellCheckerInfo.getPackageName(), true);
        }
        return true;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(
                this.mContext, "TextServicesManagerService", printWriter)) {
            if (strArr.length == 0 || (strArr.length == 1 && strArr[0].equals("-a"))) {
                synchronized (this.mLock) {
                    try {
                        printWriter.println("Current Text Services Manager state:");
                        printWriter.println("  Users:");
                        int size = this.mUserData.size();
                        for (int i = 0; i < size; i++) {
                            TextServicesData.m968$$Nest$mdump(
                                    (TextServicesData) this.mUserData.valueAt(i), printWriter);
                        }
                    } finally {
                    }
                }
                return;
            }
            if (strArr.length != 2 || !strArr[0].equals("--user")) {
                printWriter.println("Invalid arguments to text services.");
                return;
            }
            int parseInt = Integer.parseInt(strArr[1]);
            if (this.mUserManager.getUserInfo(parseInt) == null) {
                printWriter.println("Non-existent user.");
                return;
            }
            TextServicesData textServicesData = (TextServicesData) this.mUserData.get(parseInt);
            if (textServicesData == null) {
                printWriter.println("User needs to unlock first.");
                return;
            }
            synchronized (this.mLock) {
                printWriter.println("Current Text Services Manager state:");
                printWriter.println("  User " + parseInt + ":");
                TextServicesData.m968$$Nest$mdump(textServicesData, printWriter);
            }
        }
    }

    public final SpellCheckerInfo findAvailSystemSpellCheckerLocked(
            String str, TextServicesData textServicesData) {
        Locale locale;
        Locale locale2;
        ArrayList arrayList = new ArrayList();
        Iterator it = textServicesData.mSpellCheckerList.iterator();
        while (it.hasNext()) {
            SpellCheckerInfo spellCheckerInfo = (SpellCheckerInfo) it.next();
            if ((1 & spellCheckerInfo.getServiceInfo().applicationInfo.flags) != 0) {
                arrayList.add(spellCheckerInfo);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            Slog.w("TextServicesManagerService", "no available spell checker services found");
            return null;
        }
        if (str != null) {
            for (int i = 0; i < size; i++) {
                SpellCheckerInfo spellCheckerInfo2 = (SpellCheckerInfo) arrayList.get(i);
                if (str.equals(spellCheckerInfo2.getPackageName())) {
                    return spellCheckerInfo2;
                }
            }
        }
        Locale locale3 = this.mContext.getResources().getConfiguration().locale;
        if (locale3 != null) {
            String language = locale3.getLanguage();
            boolean z = !TextUtils.isEmpty(language);
            String country = locale3.getCountry();
            boolean z2 = !TextUtils.isEmpty(country);
            String variant = locale3.getVariant();
            Locale locale4 =
                    (z && z2 && (TextUtils.isEmpty(variant) ^ true))
                            ? new Locale(language, country, variant)
                            : null;
            Locale locale5 = (z && z2) ? new Locale(language, country) : null;
            r1 = z ? new Locale(language) : null;
            locale2 = locale5;
            locale = r1;
            r1 = locale4;
        } else {
            locale = null;
            locale2 = null;
        }
        ArrayList arrayList2 = new ArrayList();
        if (r1 != null) {
            arrayList2.add(r1);
        }
        Locale locale6 = Locale.ENGLISH;
        if (!locale6.equals(locale)) {
            if (locale2 != null) {
                arrayList2.add(locale2);
            }
            if (locale != null) {
                arrayList2.add(locale);
            }
            arrayList2.add(Locale.US);
            arrayList2.add(Locale.UK);
            arrayList2.add(locale6);
        } else if (locale2 != null) {
            arrayList2.add(locale2);
            Locale locale7 = Locale.US;
            if (!locale7.equals(locale2)) {
                arrayList2.add(locale7);
            }
            Locale locale8 = Locale.UK;
            if (!locale8.equals(locale2)) {
                arrayList2.add(locale8);
            }
            arrayList2.add(locale6);
        } else {
            arrayList2.add(locale6);
            arrayList2.add(Locale.US);
            arrayList2.add(Locale.UK);
        }
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            Locale locale9 = (Locale) arrayList2.get(i2);
            for (int i3 = 0; i3 < size; i3++) {
                SpellCheckerInfo spellCheckerInfo3 = (SpellCheckerInfo) arrayList.get(i3);
                int subtypeCount = spellCheckerInfo3.getSubtypeCount();
                for (int i4 = 0; i4 < subtypeCount; i4++) {
                    if (locale9.equals(
                            SubtypeLocaleUtils.constructLocaleFromString(
                                    spellCheckerInfo3.getSubtypeAt(i4).getLocale()))) {
                        return spellCheckerInfo3;
                    }
                }
            }
        }
        if (size > 1) {
            Slog.w(
                    "TextServicesManagerService",
                    "more than one spell checker service found, picking first");
        }
        return (SpellCheckerInfo) arrayList.get(0);
    }

    public final void finishSpellCheckerService(
            int i, ISpellCheckerSessionListener iSpellCheckerSessionListener) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (SpellCheckerBindGroup spellCheckerBindGroup :
                        dataFromCallingUserIdLocked.mSpellCheckerBindGroups.values()) {
                    if (spellCheckerBindGroup != null) {
                        arrayList.add(spellCheckerBindGroup);
                    }
                }
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((SpellCheckerBindGroup) arrayList.get(i2))
                            .removeListener(iSpellCheckerSessionListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SpellCheckerInfo getCurrentSpellChecker(int i, String str) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return null;
                }
                return dataFromCallingUserIdLocked.getCurrentSpellChecker();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SpellCheckerInfo getCurrentSpellCheckerForUser(int i) {
        SpellCheckerInfo currentSpellChecker;
        synchronized (this.mLock) {
            try {
                TextServicesData textServicesData = (TextServicesData) this.mUserData.get(i);
                currentSpellChecker =
                        textServicesData != null ? textServicesData.getCurrentSpellChecker() : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return currentSpellChecker;
    }

    public final SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                SpellCheckerSubtype spellCheckerSubtype = null;
                if (dataFromCallingUserIdLocked == null) {
                    return null;
                }
                int i2 = 0;
                int intForUser =
                        Settings.Secure.getIntForUser(
                                dataFromCallingUserIdLocked.mResolver,
                                "selected_spell_checker_subtype",
                                0,
                                dataFromCallingUserIdLocked.mUserId);
                SpellCheckerInfo currentSpellChecker =
                        dataFromCallingUserIdLocked.getCurrentSpellChecker();
                Locale locale = this.mContext.getResources().getConfiguration().locale;
                if (currentSpellChecker != null && currentSpellChecker.getSubtypeCount() != 0) {
                    if (intForUser == 0 && !z) {
                        return null;
                    }
                    int subtypeCount = currentSpellChecker.getSubtypeCount();
                    if (intForUser != 0) {
                        while (i2 < subtypeCount) {
                            SpellCheckerSubtype subtypeAt = currentSpellChecker.getSubtypeAt(i2);
                            if (subtypeAt.hashCode() == intForUser) {
                                return subtypeAt;
                            }
                            i2++;
                        }
                        return null;
                    }
                    if (locale == null) {
                        return null;
                    }
                    while (i2 < currentSpellChecker.getSubtypeCount()) {
                        SpellCheckerSubtype subtypeAt2 = currentSpellChecker.getSubtypeAt(i2);
                        Locale localeObject = subtypeAt2.getLocaleObject();
                        if (Objects.equals(localeObject, locale)) {
                            return subtypeAt2;
                        }
                        if (spellCheckerSubtype == null
                                && localeObject != null
                                && TextUtils.equals(
                                        locale.getLanguage(), localeObject.getLanguage())) {
                            spellCheckerSubtype = subtypeAt2;
                        }
                        i2++;
                    }
                }
                return spellCheckerSubtype;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final TextServicesData getDataFromCallingUserIdLocked(int i) {
        return (TextServicesData) this.mUserData.get(i);
    }

    public final SpellCheckerInfo[] getEnabledSpellCheckers(int i) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList(dataFromCallingUserIdLocked.mSpellCheckerList);
                int size = arrayList.size();
                int callingUid = Binder.getCallingUid();
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    if (!canCallerAccessSpellChecker(
                            (SpellCheckerInfo) arrayList.get(i2), callingUid, i)) {
                        arrayList.remove(i2);
                    }
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return (SpellCheckerInfo[])
                        arrayList.toArray(new SpellCheckerInfo[arrayList.size()]);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void getSpellCheckerService(
            int i,
            String str,
            String str2,
            ITextServicesSessionListener iTextServicesSessionListener,
            ISpellCheckerSessionListener iSpellCheckerSessionListener,
            Bundle bundle,
            int i2) {
        verifyUser(i);
        if (TextUtils.isEmpty(str)
                || iTextServicesSessionListener == null
                || iSpellCheckerSessionListener == null) {
            Slog.e("TextServicesManagerService", "getSpellCheckerService: Invalid input.");
            return;
        }
        synchronized (this.mLock) {
            try {
                TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return;
                }
                HashMap hashMap = dataFromCallingUserIdLocked.mSpellCheckerMap;
                if (hashMap.containsKey(str)) {
                    SpellCheckerInfo spellCheckerInfo = (SpellCheckerInfo) hashMap.get(str);
                    int callingUid = Binder.getCallingUid();
                    if (canCallerAccessSpellChecker(spellCheckerInfo, callingUid, i)) {
                        SpellCheckerBindGroup spellCheckerBindGroup =
                                (SpellCheckerBindGroup)
                                        dataFromCallingUserIdLocked.mSpellCheckerBindGroups.get(
                                                str);
                        if (spellCheckerBindGroup == null) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                spellCheckerBindGroup =
                                        startSpellCheckerServiceInnerLocked(
                                                spellCheckerInfo, dataFromCallingUserIdLocked);
                                if (spellCheckerBindGroup == null) {
                                    return;
                                }
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        spellCheckerBindGroup.getISpellCheckerSessionOrQueueLocked(
                                new SessionRequest(
                                        callingUid,
                                        str2,
                                        iTextServicesSessionListener,
                                        iSpellCheckerSessionListener,
                                        bundle,
                                        i2));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isSpellCheckerEnabled(int i) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return false;
                }
                return Settings.Secure.getIntForUser(
                                dataFromCallingUserIdLocked.mResolver,
                                "spell_checker_enabled",
                                1,
                                dataFromCallingUserIdLocked.mUserId)
                        == 1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SpellCheckerBindGroup startSpellCheckerServiceInnerLocked(
            SpellCheckerInfo spellCheckerInfo, TextServicesData textServicesData) {
        String id = spellCheckerInfo.getId();
        InternalServiceConnection internalServiceConnection =
                new InternalServiceConnection(id, textServicesData.mSpellCheckerBindGroups);
        Intent intent = new Intent("android.service.textservice.SpellCheckerService");
        intent.setComponent(spellCheckerInfo.getComponent());
        if (!this.mContext.bindServiceAsUser(
                intent,
                internalServiceConnection,
                8388609,
                UserHandle.of(textServicesData.mUserId))) {
            Slog.e("TextServicesManagerService", "Failed to get a spell checker service.");
            return null;
        }
        SpellCheckerBindGroup spellCheckerBindGroup =
                new SpellCheckerBindGroup(internalServiceConnection);
        textServicesData.mSpellCheckerBindGroups.put(id, spellCheckerBindGroup);
        return spellCheckerBindGroup;
    }

    public final void verifyUser(int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (i != callingUserId) {
            this.mContext.enforceCallingPermission(
                    "android.permission.INTERACT_ACROSS_USERS_FULL",
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            callingUserId,
                            "Cross-user interaction requires INTERACT_ACROSS_USERS_FULL. userId=",
                            " callingUserId="));
        }
    }
}
