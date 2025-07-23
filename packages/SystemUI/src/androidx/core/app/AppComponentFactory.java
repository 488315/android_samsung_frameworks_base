package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppComponentFactory extends android.app.AppComponentFactory {
    @Override // android.app.AppComponentFactory
    public final Activity instantiateActivity(ClassLoader classLoader, String str, Intent intent) {
        return (Activity) CoreComponentFactory.checkCompatWrapper(instantiateActivityCompat(classLoader, str, intent));
    }

    public Activity instantiateActivityCompat(ClassLoader classLoader, String str, Intent intent) {
        try {
            Class[] clsArr = new Class[0];
            return (Activity) Class.forName(str, false, classLoader).asSubclass(Activity.class).getDeclaredConstructor(null).newInstance(null);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @Override // android.app.AppComponentFactory
    public final Application instantiateApplication(ClassLoader classLoader, String str) {
        return (Application) CoreComponentFactory.checkCompatWrapper(instantiateApplicationCompat(classLoader, str));
    }

    public Application instantiateApplicationCompat(ClassLoader classLoader, String str) {
        try {
            Class[] clsArr = new Class[0];
            return (Application) Class.forName(str, false, classLoader).asSubclass(Application.class).getDeclaredConstructor(null).newInstance(null);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @Override // android.app.AppComponentFactory
    public final ContentProvider instantiateProvider(ClassLoader classLoader, String str) {
        return (ContentProvider) CoreComponentFactory.checkCompatWrapper(instantiateProviderCompat(classLoader, str));
    }

    public ContentProvider instantiateProviderCompat(ClassLoader classLoader, String str) {
        try {
            Class[] clsArr = new Class[0];
            return (ContentProvider) Class.forName(str, false, classLoader).asSubclass(ContentProvider.class).getDeclaredConstructor(null).newInstance(null);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @Override // android.app.AppComponentFactory
    public final BroadcastReceiver instantiateReceiver(ClassLoader classLoader, String str, Intent intent) {
        return (BroadcastReceiver) CoreComponentFactory.checkCompatWrapper(instantiateReceiverCompat(classLoader, str, intent));
    }

    public BroadcastReceiver instantiateReceiverCompat(ClassLoader classLoader, String str, Intent intent) {
        try {
            Class[] clsArr = new Class[0];
            return (BroadcastReceiver) Class.forName(str, false, classLoader).asSubclass(BroadcastReceiver.class).getDeclaredConstructor(null).newInstance(null);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @Override // android.app.AppComponentFactory
    public final Service instantiateService(ClassLoader classLoader, String str, Intent intent) {
        return (Service) CoreComponentFactory.checkCompatWrapper(instantiateServiceCompat(classLoader, str, intent));
    }

    public Service instantiateServiceCompat(ClassLoader classLoader, String str, Intent intent) {
        try {
            Class[] clsArr = new Class[0];
            return (Service) Class.forName(str, false, classLoader).asSubclass(Service.class).getDeclaredConstructor(null).newInstance(null);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }
}
