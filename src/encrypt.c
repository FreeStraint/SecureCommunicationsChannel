#include <jni.h>
#include <jni_md.h>
#include <stdio.h>
#include <stdlib.h>
#include "Encrypt.h"

void encrypt (int *v, int *k){
/* TEA encryption algorithm */
unsigned int y = v[0], z=v[1], sum = 0;
unsigned int delta = 0x9e3779b9, n=32;

	while (n-- > 0){
		sum += delta;
		y += (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		z += (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
	}

	v[0] = y;
	v[1] = z;
}

void decrypt (int *v, int *k){
/* TEA decryption routine */
unsigned int n=32, sum, y=v[0], z=v[1];
unsigned int delta=0x9e3779b9l;

	sum = delta<<5;
	while (n-- > 0){
		z -= (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
		y -= (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		sum -= delta;
	}
	v[0] = y;
	v[1] = z;
}

JNIEXPORT jintArray JNICALL Java_Encrypt_encrypt
  (JNIEnv *env, jobject thisObj, jintArray value, jintArray key){

  	printf("encrypt\n");
  	jint *v = (*env)->GetIntArrayElements(env, value, 0);
  	jint *k = (*env)->GetIntArrayElements(env, key, 0);

  	jsize vSize = (*env)->GetArrayLength(env, value);
	
  	encrypt((int *) v, (int *) k);

	jintArray res = (*env)->NewIntArray(env, vSize);
	(*env)->SetIntArrayRegion(env, res, 0, vSize, v);
	return res;
  }


JNIEXPORT jintArray JNICALL Java_Encrypt_decrypt
(JNIEnv *env, jobject thisObj, jintArray value, jintArray key){

  	jint *v = (*env)->GetIntArrayElements(env, value, 0);
  	jint *k = (*env)->GetIntArrayElements(env, key, 0);

  	jsize vSize = (*env)->GetArrayLength(env, value);

	decrypt((int *) v, (int *) k);

	jintArray res = (*env)->NewIntArray(env, vSize);
	(*env)->SetIntArrayRegion(env, res, 0, vSize, v);
	return res;
}



