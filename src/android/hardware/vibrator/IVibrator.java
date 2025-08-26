package android.hardware.vibrator;

import android.hardware.vibrator.IVibratorCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IVibrator extends IInterface {
    public static final int CAP_ALWAYS_ON_CONTROL = 64;
    public static final int CAP_AMPLITUDE_CONTROL = 4;
    public static final int CAP_COMPOSE_EFFECTS = 32;
    public static final int CAP_COMPOSE_PWLE_EFFECTS = 1024;
    public static final int CAP_COMPOSE_PWLE_EFFECTS_V2 = 4096;
    public static final int CAP_EXTERNAL_AMPLITUDE_CONTROL = 16;
    public static final int CAP_EXTERNAL_CONTROL = 8;
    public static final int CAP_FREQUENCY_CONTROL = 512;
    public static final int CAP_GET_Q_FACTOR = 256;
    public static final int CAP_GET_RESONANT_FREQUENCY = 128;
    public static final int CAP_ON_CALLBACK = 1;
    public static final int CAP_PERFORM_CALLBACK = 2;
    public static final int CAP_PERFORM_VENDOR_EFFECTS = 2048;
    public static final String DESCRIPTOR = "android$hardware$vibrator$IVibrator".replace('$', '.');
    public static final String HASH = "720a16b521507c378f14c516749ae178a60dfc44";
    public static final int VERSION = 3;

    void alwaysOnDisable(int i) throws RemoteException;

    void alwaysOnEnable(int i, int i2, byte b) throws RemoteException;

    void compose(CompositeEffect[] compositeEffectArr, IVibratorCallback iVibratorCallback) throws RemoteException;

    @Deprecated
    void composePwle(PrimitivePwle[] primitivePwleArr, IVibratorCallback iVibratorCallback) throws RemoteException;

    void composePwleV2(CompositePwleV2 compositePwleV2, IVibratorCallback iVibratorCallback) throws RemoteException;

    @Deprecated
    float[] getBandwidthAmplitudeMap() throws RemoteException;

    int getCapabilities() throws RemoteException;

    int getCompositionDelayMax() throws RemoteException;

    int getCompositionSizeMax() throws RemoteException;

    @Deprecated
    float getFrequencyMinimum() throws RemoteException;

    @Deprecated
    float getFrequencyResolution() throws RemoteException;

    List<FrequencyAccelerationMapEntry> getFrequencyToOutputAccelerationMap() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getPrimitiveDuration(int i) throws RemoteException;

    @Deprecated
    int getPwleCompositionSizeMax() throws RemoteException;

    @Deprecated
    int getPwlePrimitiveDurationMax() throws RemoteException;

    int getPwleV2CompositionSizeMax() throws RemoteException;

    int getPwleV2PrimitiveDurationMaxMillis() throws RemoteException;

    int getPwleV2PrimitiveDurationMinMillis() throws RemoteException;

    float getQFactor() throws RemoteException;

    float getResonantFrequency() throws RemoteException;

    int[] getSupportedAlwaysOnEffects() throws RemoteException;

    @Deprecated
    int[] getSupportedBraking() throws RemoteException;

    int[] getSupportedEffects() throws RemoteException;

    int[] getSupportedPrimitives() throws RemoteException;

    void off() throws RemoteException;

    void on(int i, IVibratorCallback iVibratorCallback) throws RemoteException;

    int perform(int i, byte b, IVibratorCallback iVibratorCallback) throws RemoteException;

    void performVendorEffect(VendorEffect vendorEffect, IVibratorCallback iVibratorCallback) throws RemoteException;

    void setAmplitude(float f) throws RemoteException;

    void setExternalControl(boolean z) throws RemoteException;

    public static class Default implements IVibrator {
        @Override // android.hardware.vibrator.IVibrator
        public void alwaysOnDisable(int i) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void alwaysOnEnable(int i, int i2, byte b) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void compose(CompositeEffect[] compositeEffectArr, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void composePwle(PrimitivePwle[] primitivePwleArr, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void composePwleV2(CompositePwleV2 compositePwleV2, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public float[] getBandwidthAmplitudeMap() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCapabilities() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCompositionDelayMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCompositionSizeMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getFrequencyMinimum() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getFrequencyResolution() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public List<FrequencyAccelerationMapEntry> getFrequencyToOutputAccelerationMap() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPrimitiveDuration(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleCompositionSizeMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwlePrimitiveDurationMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleV2CompositionSizeMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleV2PrimitiveDurationMaxMillis() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleV2PrimitiveDurationMinMillis() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getQFactor() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getResonantFrequency() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedAlwaysOnEffects() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedBraking() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedEffects() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedPrimitives() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void off() throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void on(int i, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public int perform(int i, byte b, IVibratorCallback iVibratorCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void performVendorEffect(VendorEffect vendorEffect, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void setAmplitude(float f) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void setExternalControl(boolean z) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IVibrator {
        static final int TRANSACTION_alwaysOnDisable = 15;
        static final int TRANSACTION_alwaysOnEnable = 14;
        static final int TRANSACTION_compose = 12;
        static final int TRANSACTION_composePwle = 24;
        static final int TRANSACTION_composePwleV2 = 30;
        static final int TRANSACTION_getBandwidthAmplitudeMap = 20;
        static final int TRANSACTION_getCapabilities = 1;
        static final int TRANSACTION_getCompositionDelayMax = 8;
        static final int TRANSACTION_getCompositionSizeMax = 9;
        static final int TRANSACTION_getFrequencyMinimum = 19;
        static final int TRANSACTION_getFrequencyResolution = 18;
        static final int TRANSACTION_getFrequencyToOutputAccelerationMap = 26;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPrimitiveDuration = 11;
        static final int TRANSACTION_getPwleCompositionSizeMax = 22;
        static final int TRANSACTION_getPwlePrimitiveDurationMax = 21;
        static final int TRANSACTION_getPwleV2CompositionSizeMax = 28;
        static final int TRANSACTION_getPwleV2PrimitiveDurationMaxMillis = 27;
        static final int TRANSACTION_getPwleV2PrimitiveDurationMinMillis = 29;
        static final int TRANSACTION_getQFactor = 17;
        static final int TRANSACTION_getResonantFrequency = 16;
        static final int TRANSACTION_getSupportedAlwaysOnEffects = 13;
        static final int TRANSACTION_getSupportedBraking = 23;
        static final int TRANSACTION_getSupportedEffects = 5;
        static final int TRANSACTION_getSupportedPrimitives = 10;
        static final int TRANSACTION_off = 2;
        static final int TRANSACTION_on = 3;
        static final int TRANSACTION_perform = 4;
        static final int TRANSACTION_performVendorEffect = 25;
        static final int TRANSACTION_setAmplitude = 6;
        static final int TRANSACTION_setExternalControl = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IVibrator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVibrator)) {
                return (IVibrator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int capabilities = getCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(capabilities);
                    return true;
                case 2:
                    off();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    IVibratorCallback iVibratorCallbackAsInterface = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    on(i3, iVibratorCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    byte b = parcel.readByte();
                    IVibratorCallback iVibratorCallbackAsInterface2 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iPerform = perform(i4, b, iVibratorCallbackAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPerform);
                    return true;
                case 5:
                    int[] supportedEffects = getSupportedEffects();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedEffects);
                    return true;
                case 6:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAmplitude(f);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setExternalControl(z);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int compositionDelayMax = getCompositionDelayMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(compositionDelayMax);
                    return true;
                case 9:
                    int compositionSizeMax = getCompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(compositionSizeMax);
                    return true;
                case 10:
                    int[] supportedPrimitives = getSupportedPrimitives();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedPrimitives);
                    return true;
                case 11:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int primitiveDuration = getPrimitiveDuration(i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(primitiveDuration);
                    return true;
                case 12:
                    CompositeEffect[] compositeEffectArr = (CompositeEffect[]) parcel.createTypedArray(CompositeEffect.CREATOR);
                    IVibratorCallback iVibratorCallbackAsInterface3 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    compose(compositeEffectArr, iVibratorCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] supportedAlwaysOnEffects = getSupportedAlwaysOnEffects();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedAlwaysOnEffects);
                    return true;
                case 14:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    byte b2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    alwaysOnEnable(i6, i7, b2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    alwaysOnDisable(i8);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    float resonantFrequency = getResonantFrequency();
                    parcel2.writeNoException();
                    parcel2.writeFloat(resonantFrequency);
                    return true;
                case 17:
                    float qFactor = getQFactor();
                    parcel2.writeNoException();
                    parcel2.writeFloat(qFactor);
                    return true;
                case 18:
                    float frequencyResolution = getFrequencyResolution();
                    parcel2.writeNoException();
                    parcel2.writeFloat(frequencyResolution);
                    return true;
                case 19:
                    float frequencyMinimum = getFrequencyMinimum();
                    parcel2.writeNoException();
                    parcel2.writeFloat(frequencyMinimum);
                    return true;
                case 20:
                    float[] bandwidthAmplitudeMap = getBandwidthAmplitudeMap();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(bandwidthAmplitudeMap);
                    return true;
                case 21:
                    int pwlePrimitiveDurationMax = getPwlePrimitiveDurationMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwlePrimitiveDurationMax);
                    return true;
                case 22:
                    int pwleCompositionSizeMax = getPwleCompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleCompositionSizeMax);
                    return true;
                case 23:
                    int[] supportedBraking = getSupportedBraking();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedBraking);
                    return true;
                case 24:
                    PrimitivePwle[] primitivePwleArr = (PrimitivePwle[]) parcel.createTypedArray(PrimitivePwle.CREATOR);
                    IVibratorCallback iVibratorCallbackAsInterface4 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    composePwle(primitivePwleArr, iVibratorCallbackAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    VendorEffect vendorEffect = (VendorEffect) parcel.readTypedObject(VendorEffect.CREATOR);
                    IVibratorCallback iVibratorCallbackAsInterface5 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    performVendorEffect(vendorEffect, iVibratorCallbackAsInterface5);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    List<FrequencyAccelerationMapEntry> frequencyToOutputAccelerationMap = getFrequencyToOutputAccelerationMap();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(frequencyToOutputAccelerationMap, 1);
                    return true;
                case 27:
                    int pwleV2PrimitiveDurationMaxMillis = getPwleV2PrimitiveDurationMaxMillis();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleV2PrimitiveDurationMaxMillis);
                    return true;
                case 28:
                    int pwleV2CompositionSizeMax = getPwleV2CompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleV2CompositionSizeMax);
                    return true;
                case 29:
                    int pwleV2PrimitiveDurationMinMillis = getPwleV2PrimitiveDurationMinMillis();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleV2PrimitiveDurationMinMillis);
                    return true;
                case 30:
                    CompositePwleV2 compositePwleV2 = (CompositePwleV2) parcel.readTypedObject(CompositePwleV2.CREATOR);
                    IVibratorCallback iVibratorCallbackAsInterface6 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    composePwleV2(compositePwleV2, iVibratorCallbackAsInterface6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVibrator {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCapabilities() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getCapabilities is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void off() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method off is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void on(int i, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method on is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int perform(int i, byte b, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method perform is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedEffects() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSupportedEffects is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void setAmplitude(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setAmplitude is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void setExternalControl(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setExternalControl is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCompositionDelayMax() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getCompositionDelayMax is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCompositionSizeMax() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getCompositionSizeMax is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedPrimitives() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSupportedPrimitives is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPrimitiveDuration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getPrimitiveDuration is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void compose(CompositeEffect[] compositeEffectArr, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(compositeEffectArr, 0);
                    parcelObtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method compose is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedAlwaysOnEffects() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSupportedAlwaysOnEffects is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void alwaysOnEnable(int i, int i2, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByte(b);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method alwaysOnEnable is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void alwaysOnDisable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method alwaysOnDisable is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getResonantFrequency() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getResonantFrequency is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getQFactor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getQFactor is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getFrequencyResolution() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getFrequencyResolution is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getFrequencyMinimum() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getFrequencyMinimum is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float[] getBandwidthAmplitudeMap() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getBandwidthAmplitudeMap is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwlePrimitiveDurationMax() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getPwlePrimitiveDurationMax is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleCompositionSizeMax() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getPwleCompositionSizeMax is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedBraking() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(23, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSupportedBraking is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void composePwle(PrimitivePwle[] primitivePwleArr, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(primitivePwleArr, 0);
                    parcelObtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(24, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method composePwle is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void performVendorEffect(VendorEffect vendorEffect, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(vendorEffect, 0);
                    parcelObtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(25, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method performVendorEffect is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public List<FrequencyAccelerationMapEntry> getFrequencyToOutputAccelerationMap() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getFrequencyToOutputAccelerationMap is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(FrequencyAccelerationMapEntry.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleV2PrimitiveDurationMaxMillis() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getPwleV2PrimitiveDurationMaxMillis is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleV2CompositionSizeMax() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(28, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getPwleV2CompositionSizeMax is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleV2PrimitiveDurationMinMillis() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getPwleV2PrimitiveDurationMinMillis is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void composePwleV2(CompositePwleV2 compositePwleV2, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(compositePwleV2, 0);
                    parcelObtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(30, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method composePwleV2 is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.vibrator.IVibrator
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
