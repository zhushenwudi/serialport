/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class top_keepempty_sph_library_SerialPortJNI */

#ifndef _Included_top_keepempty_sph_library_SerialPortJNI
#define _Included_top_keepempty_sph_library_SerialPortJNI
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     top_keepempty_sph_library_SerialPortJNI
 * Method:    openPort
 * Signature: (Ljava/lang/String;IIICI)I
 */
JNIEXPORT jint JNICALL
Java_top_keepempty_sph_library_SerialPortJNI_openPort(JNIEnv *, jobject, jstring, jint, jint, jint,
                                                      jchar);

/*
 * Class:     top_keepempty_sph_library_SerialPortJNI
 * Method:    readPort
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL
Java_top_keepempty_sph_library_SerialPortJNI_readPort(JNIEnv *, jobject, jint);

/*
 * Class:     top_keepempty_sph_library_SerialPortJNI
 * Method:    writePort
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL
Java_top_keepempty_sph_library_SerialPortJNI_writePort(JNIEnv *, jobject, jbyteArray);

/*
 * Class:     top_keepempty_sph_library_SerialPortJNI
 * Method:    setMode
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL
Java_top_keepempty_sph_library_SerialPortJNI_setMode(JNIEnv *, jobject, jint);


/*
 * Class:     top_keepempty_sph_library_SerialPortJNI
 * Method:    closePort
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_top_keepempty_sph_library_SerialPortJNI_closePort(JNIEnv *, jobject);

JNIEXPORT void JNICALL Java_top_keepempty_sph_library_SerialPortJNI_finalize(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif