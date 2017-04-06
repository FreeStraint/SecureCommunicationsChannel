#include <jni.h>
#include <jni_md.h>
#include <stdio.h>
#include <stdlib.h>
#include "Encrypt.h"

void encrypt (long *v, long *k){
/* TEA encryption algorithm */
unsigned long y = v[0], z=v[1], sum = 0;
unsigned long delta = 0x9e3779b9, n=32;

	while (n-- > 0){
		sum += delta;
		y += (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		z += (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
	}

	v[0] = y;
	v[1] = z;
}

void decrypt (long *v, long *k){
/* TEA decryption routine */
unsigned long n=32, sum, y=v[0], z=v[1];
unsigned long delta=0x9e3779b9l;

	sum = delta<<5;
	while (n-- > 0){
		z -= (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
		y -= (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		sum -= delta;
	}
	v[0] = y;
	v[1] = z;
}

JNIEXPORT jlongArray JNICALL Java_Encrypt_encrypt
  (JNIEnv *env, jobject thisObj, jlongArray value, jlongArray key){

  	printf("encrypt\n");
  	jlong *v = (*env)->GetLongArrayElements(env, value, 0);
  	jlong *k = (*env)->GetLongArrayElements(env, key, 0);

  	jsize vSize = (*env)->GetArrayLength(env, value);
	
	//printf("%lX lX\n", v[0], v[1]);

  	encrypt((long *) v, (long *) k);
  	//printf("%lX lX\n", v[0], v[1]);

	jlongArray res = (*env)->NewLongArray(env, vSize);
	(*env)->SetLongArrayRegion(env, res, 0, vSize, v);
	return res;
  }


JNIEXPORT jlongArray JNICALL Java_Encrypt_decrypt
(JNIEnv *env, jobject thisObj, jlongArray value, jlongArray key){

  	jlong *v = (*env)->GetLongArrayElements(env, value, 0);
  	jlong *k = (*env)->GetLongArrayElements(env, key, 0);

  	jsize vSize = (*env)->GetArrayLength(env, value);
	long* a = (long *) v;
	decrypt((long *) v, (long *) k);

	jlongArray res = (*env)->NewLongArray(env, vSize);
	(*env)->SetLongArrayRegion(env, res, 0, vSize, v);
	return res;
}



